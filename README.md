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

 - `LaunchIntent` and  `onLaunchIntent` - Handles the LaunchIntent event.
 - `RecoverIntentContext` and `onUnknownIntentContext` - When a BuiltInIntent comes without a context, you may annotate with `@RecoverIntentContext` to handle the error and respond gracefully.
 - `FallbackIntent` and `onFallbackIntent` - Handles the AMAZON.FallbackIntent
 - `HelpIntent` and `onHelpIntent` - Handles the AMAZON.HelpIntent
 - `FulfillerIntent` and `onConnectionsRequest` - Handles the Skill request from another existent Skill. 
 - `Intent` and `onIntentRequest` - Probably the most important annotation since it's where you will handle all of your Intents. It's basically the entry point of Intents. When an Intent is mapped to your Intent class the `onIntentRequest` will be called. You can also map more than one Intent to an Intent class using the `mapsTo` annotation property.
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

If you're working with Display interface, and you need to handle touch events, you can override the `onElementSelected` and handle properly the touch event.

This lib also supports Skill Connector feature. So if you're expecting a response from an another skill, the `onConnectionsRequest` callback method will be called.

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
