[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.21-blue.svg)](https://kotlinlang.org/)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

# kalexa-sdk
The Kalexa SDK is a very simple library that makes easier for developers to work with Amazon Alexa Skill using Kotlin Language.
This library aims to simplify the skill creation without writing boiler-plate code.
It's also possible to use Java and add kalexa-sdk as dependency.

## Usage:
##### Gradle
add dependency to build.gradle:
```
compile "com.hp.kalexa:kalexa-sdk:0.1.1" 
```
or as separated artifacts:
```
compile "com.hp.kalexa:kalexa-core:0.1.1" 
compile "com.hp.kalexa:kalexa-model:0.1.1" 
```

##### Maven
add dependency to pom.xml:
```
<dependency>
    <groupId>com.hp.kalexa</groupId>
    <artifactId>kalexa-sdk</artifactId>
    <version>0.1.1</version>
</dependency>
```
or as separated artifacts:
```
<dependency>
    <groupId>com.hp.kalexa</groupId>
    <artifactId>kalexa-core</artifactId>
    <version>0.1.1</version>
</dependency>
<dependency>
    <groupId>com.hp.kalexa</groupId>
    <artifactId>kalexa-model</artifactId>
    <version>0.1.1</version>
</dependency>
```

## HOW TO USE IT

#### Entry Point:
For `AWS Lambda`: You just need to extend class `AlexaRequestStreamHandler`.

For `Web Application`: You may instantiate `AlexaWebApplication` class and call `process` method which takes the payload as `String`or `ByteArray` as parameter.

##### Environment variables:
You must export three environment variables on your application before running the skill.

`APPLICATION_ID`:  Corresponds to the skill id that you created on the Alexa Skills Kit Developer Console.

`INTENT_PACKAGE`: Package location where your Intent classes are located.

`SKILL_NAME`: The name of the skill.

##### Add Intent Handler manually to Speech Handler:
If for some reason you need to add Intent Handler instances manually instead of defining the environment 
variable `INTENT_PACKAGE`, you can do it by adding those instances into `SkillConfig` object
before providing it to `AlexaWebApplication`.

```kotlin
val intents = listOf(FirstIntent(), SecondIntent(), ThirdIntent(), FourthIntent())
val skillConfig = SkillConfig(intentHandlers = intents)
val alexaWebApplication = AlexaWebApplication(skillConfig)
```

##### Add Request Interceptors.
Request Interceptor is a way to intercept each request coming from Alexa before reach any Intent.
So it's the perfect place to put a logic that you want to be executed every time a request reaches your skill.
It can be implemented by implementing `RequestInterceptor` interface.

```kotlin
class CustomInterceptor : RequestInterceptor {

    override fun intercept(alexaRequest: AlexaRequest<*>) {
        // logic goes here.
    }
}
```  
After creating your interceptors, you must pass those interceptor instances to the `InterceptorHandler` the same way as
`SpeechHandler`.

```kotlin
val interceptors = listOf(CustomInterceptor())
val skillConfig = SkillConfig(interceptors = interceptors)
val alexaWebApplication = AlexaWebApplication(skillConfig)
```

#### Create Intent:
There are different interfaces for each type of request that you may want to handle:
```
LaunchRequestHandler, IntentHandler, ProviderHandler, RequesterHandler, ListEventsHandler, HelpIntentHandler,
FallbackIntentHandler, CanFulfillIntentHandler, RecoverIntentContextHandler
```
All of them extends from `BaseHandler`.

Implementing one of them will have to handle the required method of the contract. Each interface will handle the
corresponding Request type from Alexa.
For example:
```kotlin
class CustomLaunchRequest : LaunchRequestHandler {
    override fun onLaunchIntent(alexaRequest: AlexaRequest<LaunchRequest>): AlexaResponse {
        //logic goes here
    }
}
```

##### Intent Handler
For Intent Requests, you may use the same class to listen to different Intents created on [Amazon Developer](https://developer.amazon.com)
```kotlin
class CustomIntent : IntentHandler {
    @Intent(mapsTo = ["FirstIntent", "SecondIntent"])
    override fun onIntentRequest(alexaRequest: AlexaRequest<IntentRequest>): AlexaResponse {
        // logic for Intent Request type goes here.
    }
}
```
This peace of code handles FirstIntent, SecondIntent and CustomIntent intents. If you don't annotate at all, it'll map to
the class name. CustomIntent in this case.

##### Supported Annotations and callback methods:
These are the interfaces that you may annotate the method to listen to more than one Intent.
 - `@Intent` and `onIntentRequest` - Handles Intent Requests and the annotation gives you the power of map more than one Intent per class using the `mapsTo` annotation property. 
 - `@CanFulfillIntentRequest` and `onCanFulfillIntent` - Handles the CanFulfill. This request verifies if the skill can understand and fulfill the intent request and slots.
 
#### Overriding Builtin callbacks:
Your intent can override the `onBuiltInIntent` method to handle Amazon Built In intents properly. 
Or you can just override some basic callbacks and handle as you like.
These basic methods are: `onYesIntent`, `onNoIntent`, `onCancelIntent`, `onStopIntent`.

#### Lock Context:
In an interaction, you often need to lock the context (force interaction to go back to the last intent) for when you need an answer from the user.
For that you can use the method `lockIntentContext()` from `BaseHandler` interface. You may remove the lock calling `unlockIntentContext()`
For example:

`Java Code:`
   ```java
   @Intent
   class FoodIntent extends IntentHandler {
         @Override
         public AlexaResponse onIntentRequest(IntentRequest request) {
            lockIntentContext();
            return AlexaResponse.Companion.builder()
                .speech("Do you like Ice cream?")
                .build();   
         }
         @Override
         public AlexaResponse onYesIntent(IntentRequest request) {
            unlockIntentContext();   
            return AlexaResponse.Companion.builder()
                            .speech("Oh, that's great. I like it too!")
                            .build();       
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
If your skill acts as a Provider, you need to implement `ProviderHandler` interface and override `onConnectionsRequest` 
method. In this case, after processing the request, you have to answer back to Alexa using the `SendResponseDirective` directive.

`Kotlin Code:`
```kotlin
    override fun onConnectionsRequest(alexaRequest: AlexaRequest<ConnectionsRequest>): AlexaResponse {
        return alexaResponse {
            response {
                directives {
                    sendResponseDirective {
                        status { ConnectionsStatus("200", "Success") }
                        payload {
                            mapOf("url" to "http://<PDF location>.pdf")
                        }
                    }
                }
            }
        }
    }
```

##### Requester:
If your skill acts as a Requester, just return to Alexa a `SendRequestDirective` with the type of the Entity-Pair object and the Payload:

`Kotlin Code:`
```kotlin
    fun someMethod() {
        return alexaResponse {
            response {
                directives {
                    sendRequestDirective {
                        name = NameType.PRINT
                        printPDFRequest {
                            version { "1" }
                            title {  "Document title" }
                            description { "This is a PDF" }
                            url { "http://<PDF location>.pdf" }
                            context {
                                providerId = ""
                            }
                        }
                    }
                }
                speech { "This is a onConnectionsRequest from FakeIntent" }
            }
        }
    }

```
And then expect the response to be on `onConnectionsResponse` method

```kotlin
    override fun onConnectionsResponse(alexaRequest: AlexaRequest<ConnectionsResponseRequest>): AlexaResponse {
        val msg = if (alexaRequest.request.status.isSuccess()) "Your request was successfull!" else "Sorry, something went wrong."
        return alexaResponse {
            response {
                speech { msg }
            }
        }
    }

 ```
Note that you must implement `RequesterHandler` interface and override `onConnectionsResponse` method in order to be called properly. 
 
#### Response:
For Kotlin, `Kalexa-SDK` has two types of response `Builder` and `DSL`, for Java you can respond using `Builder`.

##### Java:
AlexaBuilder builds the response gracefully to send back to Alexa:
```java
String msg = "Hello, what a beautiful day!"
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
`Kalexa-SDK` supports most of the directives.

Dialog directives such `DelegateDirective`, `ElicitSlotDirective` and `ConfirmIntentDirective` directives.

UI directives: `RenderTemplateDirective` and populate with its Templates.  
With Kotlin, using DSL, it's possible to iterate over a list of items and generate a list item for each element:

`Kotlin code`
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
