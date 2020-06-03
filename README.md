[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.60-blue.svg)](https://kotlinlang.org/)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

# kalexa-sdk
The Kalexa SDK is a very simple library that makes easier for developers to work with Amazon Alexa Skill using Kotlin Language.
This library aims to simplify the skill creation without writing boiler-plate code.
It's also possible to use Java and add kalexa-sdk as dependency.

Please note that this SDK is an ongoing work. So, expect new features to be added anytime, or feel free to contact us to ask 
any feature.

## Usage:
##### Gradle
add dependency to build.gradle:
```
compile "com.hp.kalexa:kalexa-sdk:0.6.0"
```
or as separated artifacts:
```
compile "com.hp.kalexa:kalexa-core:0.6.0"
compile "com.hp.kalexa:kalexa-model:0.6.0"
```

##### Maven
add dependency to pom.xml:
```
<dependency>
    <groupId>com.hp.kalexa</groupId>
    <artifactId>kalexa-sdk</artifactId>
    <version>0.6.0</version>
</dependency>
```
or as separated artifacts:
```
<dependency>
    <groupId>com.hp.kalexa</groupId>
    <artifactId>kalexa-core</artifactId>
    <version>0.6.0</version>
</dependency>
<dependency>
    <groupId>com.hp.kalexa</groupId>
    <artifactId>kalexa-model</artifactId>
    <version>0.6.0</version>
</dependency>
```

## HOW TO USE IT

#### Entry Point:
For `AWS Lambda`: You just need to extend class `AlexaRequestStreamHandler`.

For `Web Application`: You may instantiate `AlexaWebApplication` class and call `process` method which takes the payload as `String`or `ByteArray` as parameter.

##### Environment variables:
You must export three environment variables on your application before running the skill.

`APPLICATION_ID`:  Corresponds to the skill id that you created on the Alexa Skills Kit Developer Console.

`APPLICATION_ID_VERIFICATION`: If you want to disable Application ID Verification, just set this environment variable
as `false`.

`SCAN_PACKAGE`: Package location where your Intents and Interceptors classes are located. It uses reflection to locate it.

`SKILL_NAME`: The name of the skill.

##### Add Intent Handler manually to Speech Handler:
If for some reason you want to add Intent Handler instances manually instead of defining the environment 
variable `SCAN_PACKAGE`, you can do it by adding those instances into `SkillConfig` object
before providing it to `AlexaWebApplication` or `AlexaRequestStreamHandler`.

```kotlin
val intents = listOf(FirstIntent(), SecondIntent(), ThirdIntent(), FourthIntent())
val skillConfig = SkillConfig(intentHandlers = intents)
val alexaWebApplication = AlexaWebApplication(skillConfig)
```

##### Add Request Interceptors.
Request Interceptors are a way to intercept each request coming from Alexa before reach any Intent.
So it's the perfect place to put a logic that you want to be executed every time a request reaches your skill.
It can be done by implementing `RequestInterceptor` interface.

```kotlin
class CustomInterceptor : RequestInterceptor {

    override fun interceptRequest(alexaRequest: AlexaRequest<*>) {
        // logic goes here.
    }
}
```  
And then pass the interceptors instances to the `SkillConfig` object the same way as
`Intent` instances.

```kotlin
val interceptors = listOf(CustomInterceptor())
val skillConfig = SkillConfig(requestInterceptors = interceptors)
val alexaWebApplication = AlexaWebApplication(skillConfig)
```

###### Handling `InterceptorException`:
Since `RequestInterceptor` returns `Unit`, it's not possible to return an instance of AlexaResponse. However there one
way to interrupt and exit the interaction graciously. An `InterceptorException` can be throw by an instance of interceptor.
This exception allows you not only stop the interaction but with a properly response.
The `InterceptorException` receives a callback method that returns an `AlexaResponse` object. It means that if anything go
wrong in the interceptor's logic, you can throw this exception and stop the whole interaction.

```kotlin
class AccessTokenInterceptor : RequestInterceptor {

    override fun intercept(alexaRequest: AlexaRequest<*>) {
            val accessToken = session?.user?.accessToken ?: throw InterceptorException("No access token found") {
                return alexaResponse {
                    response {
                        shouldEndSession = true
                        speech { "I see you're not logged yet. I've sent a card with more linking information." }
                        card { LinkAccountCard() }
                    }
                }
            }
       }
    }
```

##### Add Response Interceptors.
The same way you can use `RequestInterceptor` to centralize the logic in one place, you can also do the same thing for 
Responses. The only difference is that ResponseInterceptors are executed after the intents have been processed.
and each `ResponseInterceptor` must return an instance of `AlexaResponse`. This way, you can add/modify any kind of value 
in the final response.

```kotlin
class CustomResponseInterceptor : ResponseInterceptor {

    override fun intercept(alexaResponse: AlexaResponse): AlexaResponse {
        // logic goes here.
        return alexaResponse
    }
}
```  
To pass response interceptors is similar to `request interceptors`:

```kotlin
val responseInterceptors = listOf(CustomResponseInterceptor())
val skillConfig = SkillConfig(responseInterceptors = responseInterceptors)
val alexaWebApplication = AlexaWebApplication(skillConfig)
```

It's possible to work with both interceptors, as long as you add it in the skillConfig instance:
```kotlin
val skillConfig = SkillConfig(requestInterceptors=requestInterceptors, responseInterceptors = responseInterceptors)
```


#### Create Intent:
There are different interfaces for each type of request that you may want to handle. They are:

* LaunchRequestHandler
* IntentHandler
* FallbackIntentHandler
* HelpIntentHandler
* CanFulfillIntentHandler
* ProviderHandler
* RequesterHandler
* ListEventsHandler
* RecoverIntentContextHandler

All of them extend from `BaseHandler`.

Implementing one of them will force you to handle the required method of the contract. Each interface will handle the
corresponding Request type from Alexa.
For example:
```kotlin
class CustomLaunchRequest : LaunchRequestHandler {
    override fun onLaunchIntent(alexaRequest: AlexaRequest<LaunchRequest>): AlexaResponse {
        //logic goes here
    }
}
```

##### Intent Handler and CanFulfillIntentHandler
For Intent and CanFulfillIntent Requests, you may use the same class to handle different Intents created
on [Amazon Developer](https://developer.amazon.com) by annotating accordingly.
```kotlin
class CustomIntent : IntentHandler, CanFulfillIntentHandler {
    @Intent(mapsTo = ["FirstIntent", "SecondIntent"])
    override fun onIntentRequest(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        // logic for Intent Request type goes here.
    }

    @CanFulfillIntent(intents = ["FirstIntent", "SecondIntent"])
    override fun onCanFulfillIntent(alexaRequest: AlexaRequest<CanFulfillIntentRequest>): AlexaResponse {
        // logic for CanFulfill Request type goes here.
    }
}
```
This peace of code handles FirstIntent, SecondIntent and CustomIntent intents. If you don't annotate at all, it'll map to
the class name. CustomIntent in this case.

##### Supported Annotations and callback methods:
These are the interfaces that you may annotate the method to listen to more than one Intent.
 - `@Intent` and `onIntentRequest` - Handles Intent Requests, and the annotation gives you the power of map more than one Intent per class using the `mapsTo` annotation property.
 - `@CanFulfillIntentRequest` and `onCanFulfillIntent` - Handles the CanFulfill. This request verifies if the skill can understand and fulfill the intent request and slots.
 
#### Overriding Builtin callbacks:
Your intent class can override the `onBuiltInIntent` method to handle Amazon Built In intents properly. 
Or you can just override some basic callbacks and handle as you like.
These basic methods are:
* onYesIntent
* onNoIntent
* onCancelIntent
* onStopIntent

#### Lock Context:
In an interaction, you often need to lock the context (force interaction to go back to the last intent) for when you need an answer from the user.
For that you can use the method `lockIntentContext()` from `BaseHandler` interface. You may remove the lock calling `unlockIntentContext()`
For example:

```kotlin
class FoodIntent : IntentHandler {

    override fun onIntentRequest(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        lockIntentContext(alexaRequest)
        return alexaResponse {
            response {
                speech { "Do you like Ice cream?" }
            }
        }
    }

    override fun onYesIntent(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        unlockIntentContext(alexaRequest)
        return alexaResponse {
            response {
                speech { "Oh, that's great. I like it too!" }
            }
        }
    }
} 
```  
#### Display Interface
If you're working with Display interface, you will probably want to handle touch screen events. To handle it, override the
 `onElementSelected` and handle properly the touch event.

`Kalexa-SDK` will try to map the intent from Locked Context (`lockIntentContext()` method), if no such context exists, 
`Kalexa-SDK` will look for the Token key in the item list object of the request and use its value as the Intent to call its `onElementSelected` method.

`Kalexa-SDK` will use `|` as separator to split the token string in more than one values. But keep in mind that the first value HAS to be the Intent that you want to execute `onElementSelected` method.
For example: `{"token": "MyIntentName|Value|SomeOtherValue"}`

It's possible to verify whether the device has screen support by checking if supportedInterfaces from the context object has templateVersion and markupVersion value or by simply calling `context.hasDisplay()`

#### Skill Connections Support
`Kalexa-SDK` also supports Skill Connections feature. 
Your skill can act as a `Provider` or as a `Requester`
Currently, it only supports *PRINT* connection type. 

##### Provider:
If your skill acts as a Provider, you need to implement `LaunchRequestHandler` interface and override `onLaunchIntent` 
method. This interface is the same that handles incoming `LaunchRequest`. So, basically you should handle the common 'welcome'
and incoming jobs from another skill. The `LaunchRequest` object has a properly called `task` of type `Task`. This is what differs from a
common LaunchRequest. When it's a job request coming from another skill, this object will be populated. This object has all the information
needed to perform the job. 
 After processing the request, you have to answer back to Alexa using the `completeTaskDirective` directive.

```kotlin
class Launcher : LaunchRequestHandler {

    override fun onLaunchIntent(alexaRequest: AlexaRequest<LaunchRequest>): AlexaResponse {
        if (alexaRequest.request.task != null) {
            return processRequesterJob(alexaRequest)
        }
        return alexaResponse {
                   response {
                       speech {
                           "Hello there, my friend. What can I help you?"
                       }
                       shouldEndSession = false
                   }
               }
    }

    private fun processRequesterJob(alexaRequest: AlexaRequest<LaunchRequest>): AlexaResponse {
        return alexaResponse {
            response {
                shouldEndSession = true
                speech { "Your Print job is confirmed!" }
                directives {
                    completeTaskDirective {
                        status {
                            code = "200"
                            message = "All Done."
                        }
                    }
                }
            }
        }
    }
}
```

##### Requester:
If your skill acts as a Requester, just return to Alexa a `startRequestDirective` with the type of the Entity-Pair object and the Payload:

`Kotlin Code:`
```kotlin
    fun someMethod() {
        return alexaResponse {
            response {
                directives {
                    startRequestDirective {
                        uri = UriType.PRINT_IMAGE_VERSION_1
                        token = "PrintSomethingIntent"
                        printPDFRequest {
                            title { "Title" }
                            description { "Description" }
                            url { "http://<PDF location>.pdf" }
                        }
                    }
                }
                speech { "Requester sending Skill Connections request to print PDF" }
            }
        }
    }

```
And then expect the response to be on `onSessionResumedRequest` method

```kotlin
    override fun onSessionResumedRequest(alexaRequest: AlexaRequest<SessionResumedRequest>): AlexaResponse {
        val sessionAttributes = alexaRequest.session?.attributes!!
        println("Session attributes are $sessionAttributes")
        return alexaResponse {
            response {
                speech { "Status code is ${alexaRequest.request.cause.status.code}, message is ${alexaRequest.request.cause.status.message}" }
            }
        }
    }

 ```
Note that you must implement `RequesterHandler` interface and override `onSessionResumedRequest` method in order to be called properly. 
 
#### Response:
For Kotlin, `Kalexa-SDK` has two types of response `Builder` and `DSL`, for Java you can respond using `Builder`.

##### Java:
AlexaBuilder builds the response gracefully to send back to Alexa:
```java
String msg = "Hello, what a beautiful day!";
return AlexaResponse.Companion.builder()
    .speech(msg)
    .simpleCard("Title",msg)
    .build();   
```
##### Kotlin:
You may use AlexaBuilder or DSL:
```kotlin
return alexaResponse {
    response {
        speech {"This is a hello from FakeIntent"}
        simpleCard {
            title = "Hello world"
            content = "This is a content coming from FakeIntent"
            }
        }
    }
}
```

#### Directives
`Kalexa-SDK` supports the follow directives:
* AudioPlayer.ClearQueue -> `ClearQueueDirective`
* AudioPlayer.Stop" -> `StopDirective`
* AudioPlayer.Play" -> `PlayDirective`
* Display.RenderTemplate -> `RenderTemplateDirective`
* GadgetController.SetLight -> `SetLightDirective`
* Hint -> `HintDirective`
* VideoApp.Launch -> `LaunchDirective`
* Connections.StartConnection -> `StartConnectionDirective`
* Tasks.CompleteTask -> `CompleteTaskDirective`
* GameEngine.StartInputHandler -> `StartInputHandlerDirective`
* GameEngine.StopInputHandler -> `StopInputHandlerDirective`
* VoicePlayer.Speak -> `VoicePlayerSpeakDirective`

Also the Dialog directives:
* Dialog.ConfirmIntent -> `ConfirmIntentDirective`
* Dialog.ConfirmSlot -> `ConfirmSlotDirective`
* Dialog.Delegate -> `DelegateDirective`
* Dialog.ElicitSlot -> `ElicitSlotDirective`

UI directives: `RenderTemplateDirective` and populate with its Templates.  
With Kotlin, using DSL, it's possible to iterate over a list of items and generate a list item for each element:

```kotlin
directives {
    renderTemplateDirective {
        listTemplate2 {
            title = "Images/PDFs"
            listItems {
                links.forEach { link ->
                    val file = File(link)
                    listItem {
                        image = if (file.extension != "pdf") {
                            Image(sources = listOf(ImageInstance(link)))
                        } else {
                            Image(sources = listOf(ImageInstance(files[0])))
                        }
                        textContent {
                            primaryText = plainText { file.nameWithoutExtension }
                        }
                        token = "Token"
                    }
                }
            }
        }
    }
}
```



## License

MIT -- see [LICENSE](LICENSE.md)
