# kalexa-sdk
The Kalexa SDK is a Kotlin library that makes easier for developers to work with Amazon Alexa Skill.
This library aims to simplify the skill creation without writing boiler-plate code.
**it only works for amazon lambda** 

## Usage:
You need to add this lib as dependency in your project.
Since it is not in a repository yet, you need to clone this repo and build:
#### Windows:
```
gradlew.bat build install
```
#### Linux
```
gradlew build install
```

This will handle dependencies and also install kotlin-lambda-alexaskill in your local repository.

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

There are two simple things that you need to do in order to fulfil the requirements to run your skill:

- The Intent class MUST match exactly the Intent name created in Alexa skill builder.
- Implement `IntentExecutor` interface

The `onCustomIntent` is the only required method that should be implemented in your intent class.
This method is responsible for every IntentRequest that Alexa sends to your skill.

Your intent can override the `onBuiltInIntent` method to handle Amazon Built In intents properly. 
Or you can just override some basic callbacks that are already implemented and handle as you like.
These basic methods are: `onYesIntent`, `onNoIntent`, `onCancelIntent`, `onStopIntent`, `onHelpIntent`.

If you're working with Display interface, and you need to handle touch events, you can override the `onCustomElementSelected` and handle properly the touch event.

This lib also supports Skill Connector feature. So if you're expecting a response from a skill, the `onCustomLinkResult` is the right method.

`onCustomLaunchIntent` - TBD
`onUnknownIntent` - TBD



