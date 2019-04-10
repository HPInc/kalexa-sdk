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
variable `INTENT_PACKAGE`, you can do it by adding those instances into `SpeechHandler` object
before providing it to `AlexaWebApplication`.

```kotlin
val intents = listOf(FirstIntent(), SecondIntent(), ThirdIntent(), FourthIntent())
val speechHandler = SpeechHandler.newInstance(intentHandlerInstances = intents)
val alexaWebApplication = AlexaWebApplication(speechHandler)

```


#### Create Intent:

There are three simple things that you need to follow in order to fulfill the requirements of creating an Intent:

- Implement `IntentHandler` interface.
- Override `IntentHandler` callback methods. 
- Annotate with the desired annotation: `Intent`, `LaunchIntent`, `FallbackIntent`,  `HelpIntent`, `Provider` and `Requester`.

So, the way it works is basically a combination of the `IntentHandler` callbacks and the Annotations. 

For instance: When annotating a class with`@LaunchIntent` you must override the callback method `onLaunchIntent`. 
So when a SessionRequest comes from Alexa to your skill, `Kalexa-SDK` will map the Launch Request to the class annotated with `@LaunchRequest` and the `onLaunchIntent` of that class will be called.

##### Supported Annotations and callback methods:

 - `@LaunchIntent` and  `onLaunchIntent` - Handles the LaunchIntent event.
 - `@Intent` and `onIntentRequest` - Probably the most used annotation since it's where you will handle all of your Intents. When an Intent is mapped to your Intent class the `onIntentRequest` will be called. You can also map more than one Intent per class using the `mapsTo` annotation property.
 - `@FallbackIntent` and `onFallbackIntent` - Handles the BuiltIn intent AMAZON.FallbackIntent
 - `@HelpIntent` and `onHelpIntent` - Handles the AMAZON.HelpIntent
 - `@Provider` and `onConnectionsRequest` - Handles the Skill request from another existent Skill. 
 - `@Requester` and `onConnectionsResponse` - Handles the Skill response from another existent Skill. 
 - `@RecoverIntentContext` and `onUnknownIntentContext` - When a BuiltInIntent comes without a context, you may annotate with `@RecoverIntentContext` to handle the error and respond gracefully.
 - `@CanFulfillIntentRequest` and `onCanFulfillIntent` - Handles the CanFulfill. This request verifies if the skill can understand and fulfill the intent request and slots.

Kotlin Code:
 ```kotlin
 @Intent(mapsTo = ["RecipeIntent", "LaunchIntent"])
 class FoodIntent : IntentHandler() {
     override fun onIntentRequest(request: IntentRequest): AlexaResponse {
        ...
      }
 } 
 ```

Java code:
 ```java
 @Intent(mapsTo = ["RecipeIntent", "LaunchIntent"])
 class FoodIntent extends IntentHandler {
     @Override
        public AlexaResponse onIntentRequest(IntentRequest request) {
        ...
        }
 } 
 ```  
 
 You may annotate with @Intent as many Intent classes you want.
 But, besides `@Intent` annotation, you can only have **ONE** class annotated with the other types in your skill. Otherwise an exception will be thrown.

#### Overriding Builtin callbacks:
Your intent can override the `onBuiltInIntent` method to handle Amazon Built In intents properly. 
Or you can just override some basic callbacks and handle as you like.
These basic methods are: `onYesIntent`, `onNoIntent`, `onCancelIntent`, `onStopIntent`.

#### Lock Context:
In an interaction, you often need to lock the context (force interaction to go back to the last intent) for when you need an answer for the user.
For that you can use the method `lockIntentContext()` from `IntentHandler` class. You may remove the lock calling `unlockIntentContext()`
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
If you're working with Display interface, you will probably want to handle touch screen events. To handle it, override the `onElementSelected` and handle properly the touch event.

`Kalexa-SDK` will try to map the intent from `INTENT_CONTEXT` key in session attributes if no such context exists, Kalexa-SDK will look for the Token key in item list object of the request and use its value as the Intent to call its `onElementSelected` method.

`Kalexa-SDK` will use `|` as separator to split the token string in more than one values. But keep in mind that the first value HAS to be the Intent that you want to execute `onElementSelected` method.
For example: `{"token": "MyIntentName|Value|SomeOtherValue"}`

It's possible to verify whether the device has screen support by checking if supportedInterfaces from the context object has templateVersion and markupVersion value or by simply calling `context.hasDisplay()`

#### Skill Connections Support
`Kalexa-SDK` also supports Skill Connections feature. 
Your skill can act as a `Provider` or as a `Requester`
Currently, it only supports *PRINT* connection type. 
##### Provider:
If your skill acts as a Provider, you need to annotate your class with `@Provider` and override `onConnectionsRequest` callback method. In this case, after processing the request, you have to answer back to Alexa using the `SendResponseDirective` directive.

`Kotlin Code:`
```kotlin
    override fun onConnectionsRequest(request: ConnectionsRequest): AlexaResponse {
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
    override fun onConnectionsRequest(request: ConnectionsRequest): AlexaResponse {
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

`Java code:`
```java
    @NotNull
    @Override
    public AlexaResponse onConnectionsResponse(ConnectionsResponseRequest request) {
        if (request.getStatus().isSuccess()) {
            return new AlexaResponse.Builder()
                    .speech("Your request was successfull!")
                    .build();
        }
        return new AlexaResponse.Builder()
                .speech("Sorry, something went wrong.")
                .build();
    }
 ```
Note that you must annotate your class with `@Requester` so the `onConnectionsResponse` callback can be called properly. 
 #### Response:
For Kotlin, Kalexa-sdk has two types of response `Builder` and `DSL`, for Java you can respond using `Builder`.
##### Java:
AlexaBuilder builds the response gracefully to send back to Alexa:
```java
String msg = "Hello, what a beautiful day!"
AlexaResponse.Companion.builder()
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
Kalexa-sdk supports most of the directives.

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
