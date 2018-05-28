# kalexa-sdk
The Kalexa SDK is a Kotlin library that makes easier for developers to work with Amazon Alexa Skill.
This library aims to simplify the skill creation without writing boiler-plate code.
It's also possible to use Java and add kalexa-sdk as dependency.

Currently the SDK only works with Amazon Lambda.

## Usage:
You need to add this lib as dependency in your project.
Since it is not in a repository yet, you need to clone this repo and build it:
#### Windows:
```
gradlew.bat build install
```
#### Linux
```
gradlew build install
```

This will handle dependencies and also install kalexa-sdk in your local repository.

Then, in your project:
##### Gradle
add `MavenLocal()` to repositories task.  
add dependency to build.gradle
```
compile "com.hp.kalexa:kalexa-sdk:0.0.1" 
```

##### Maven
add dependency to pom.xml
```
<dependency>
    <groupId>com.hp.kalexa</groupId>
    <artifactId>kalexa-sdk</artifactId>
    <version>0.0.1</version>
</dependency>
```

## HOW TO USE IT

#### Entry Point:
You just need to extend class ``AlexaRequestStreamHandler``and nothing more.

##### Environment variables:
There are three environment variables that you must export on your lambda before running the skill.

`APPLICATION_ID`:  Corresponds to the skill id that you created on the Alexa Skills Kit Developer Console.

`INTENT_PACKAGE`: Package location where your Intent classes are located.

`SKILL_NAME`: Skill name

#### Create Intent:

There are three simple things that you need to follow in order to fulfill the requirements to run your skill:

- Extend `IntentExecutor` abstract class
- Override `IntentExector` callback methods. 
- Annotate with the supported annotations: `LaunchIntent`,  `RecoverIntentContext`,  `FallbackIntent`,  `HelpIntent`,  `Intent`, `FulfillerIntent`.

So, the way it works is basically a combination of `IntentExecutor` callbacks and the Annotations. 

For instance: When annotating a class with`@LaunchIntent` you must override the callback method `onLaunchIntent`. 
So when a SessionRequest comes from Alexa to your skill, `Kalexa-SDK` will map the Launch Request to the class annotated with `@LaunchRequest` and `onLaunchIntent` of that class will be called.

 - `@LaunchIntent` and  `onLaunchIntent` - Handles the LaunchIntent event.
 - `@RecoverIntentContext` and `onUnknownIntentContext` - When a BuiltInIntent comes without a context, you may annotate with `@RecoverIntentContext` to handle the error and respond gracefully.
 - `@FallbackIntent` and `onFallbackIntent` - Handles the AMAZON.FallbackIntent
 - `@HelpIntent` and `onHelpIntent` - Handles the AMAZON.HelpIntent
 - `@FulfillerIntent` and `onConnectionsRequest` - Handles the Skill request from another existent Skill. 
 - `@Intent` and `onIntentRequest` - Probably the most important annotation since it's where you will handle all of your Intents. It's basically the entry point of Intents. When an Intent is mapped to your Intent class the `onIntentRequest` will be called. You can also map more than one Intent to an Intent class using the `mapsTo` annotation property.
 ```
 @Intent(mapsTo = ["RecipeIntent", "LaunchIntent"])
 class FoodIntent extends IntentExecutor {
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
For that you can use the method `lockIntentContext()` from `IntentExecutor` class. You may remove the lock calling `unlockIntentContext()`
For example:

`Java Code:`
   ```
   @Intent
   class FoodIntent extends IntentExecutor {
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
For example: `{"token": "MyIntentName|Value|SomeOtherValue}`

It's possible to verify if the device has screen support checking if supportedInterfaces from the context object has templateVersion and markupVersion value or by simply calling `IntentUtil.hasDisplay(context)`

#### Skill Connection Support
`Kalexa-SDK` also supports Skill Connector feature. 
Your skill can act as a `Fulfiller` or as a `Requestor` 
##### Fulfiller:
If your skill acts as a Fulfiller, you need to annotate your class with `@Fulfiller` and override `onConnectionsRequest` callback method will be called. In this case, after processing the request, you have to answer back to Alexa using the `ReturnFromLinkDirective` directive. **This directive is in process of being deprecated**

`Java Code:`
```
    @NotNull
    @Override
    public AlexaResponse onConnectionsRequest(ConnectionsRequest request) {
        // do the logic and reply back to Alexa.
        return new AlexaResponse.Builder().addDirective(
                new ReturnFromLinkDirective(
                        ReturnFromLinkDirective.Status.SUCCESS,
                        new Print<>(new PDF(
                                "Document title",
                                "This is a PDF",
                                "http://<PDF location>.pdf")),
                        ));
    }
```

##### Requestor:
If your skill acts as a Requestor, just send to Alexa a `SendRequestDirective` with the type of the Entity-Pair object and the Payload:

`Java Code`
```
new AlexaResponse.Builder().addDirective(
                new SendRequestDirective(
                        NameType.PRINT,
                        new Print<>(new PDF(
                                "Document title",
                                "This is a PDF",
                                "http://<PDF location>.pdf")),
                        "Token"));

```
And then expect the response to be on `onConnectionsResponse` method

`Java code:`
```
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
Note that token argument passed to `SendRequestDirective` should be the Intent that you want to be called when Alexa sends the response from the Fulfiller. `Kalexa-SDK` use `|` as separator to split the token string in more values. But keep in mind that the first value HAS to be the Intent that you want to execute `onConnectionsResponse` method.
For example: `{"token": "MyIntentName|Value|SomeOtherValue}`
 #### Response:
Kalexa-sdk has two types of responses.
##### Java:
AlexaBuilder builds the response gracefully to send back to Alexa:
```
String msg = "Hello, what a beautiful day!"
AlexaResponse.Companion.builder()
    .speech(msg)
    .simpleCard("Title",msg)
    .build();   
```
##### Kotlin:
You may use AlexaBuilder or DSL:
```
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

And Skill-to-Skill directives: `SendRequestDirective`, `ReturnFromLinkDirective` (this name will probably change).


## License

MIT -- see [LICENSE](LICENSE)
