# Car-Market-Android

This is an demo mobile app that consumes this [API](https://github.com/ctrl-alt-del/car-market) that I made.

## Setup
**Android SDK**
<br>
First of all, make sure you have the **Android SDK** somewhere in your system.  If you don't have it, you can download it on [Google's developer page](http://developer.android.com/sdk/index.html).  If you need help on setting up the SDK, you can follow the instruction I wrote earlier in [here](https://github.com/ctrl-alt-del/devenv#sdk).

**$PATH setup**
<br>
Also, make sure you have exported the path of your Android SDK as an system environment variable `ANDROID_HOME`.  Gradle needs this variable in order to build the project.  For your convenient, you may also want to export another system variable called `ANDROID_TOOLS`, which contains the path to the tools and platform-tools of your Android SDK.
<br><br>
The easiest way of exporting those two variable is to add them to your `~/.bash_profile` and then do `source ~/.bash_profile`.  If you need help on setting it up, you can check out my other instruction in [here](https://github.com/ctrl-alt-del/devenv#setup-bash_profile).
<br><br>
You can append these two lines to the end of your `~/.bash_profile`, just be aware to change the `<path_to_your_sdk>` to the path of your Android SDK.
```sh
export ANDROID_HOME=<path_to_your_sdk>
export ANDROID_TOOLS=<path_to_your_sdk>/platform-tools:<path_to_your_sdk>/tools/
```
<br>
**Build the project**
<br>
For the first time build after pulling or forking this repository, you need to navigate to the root folder of this repository and run `gradle build` to download all the dependencies to your `libs` folder.  Afterwards, you are free to build the project either using gradle from the command line or your IDE, such as Eclipse and IntelliJ.

If you don't have gradle in you computer yet, you can follow my instruction in [here](https://github.com/ctrl-alt-del/devenv#gradle) on how to get gradle installed on different operating systems.


## Dependencies
**Otto** - An enhanced event bus from [Square](http://square.github.io/otto/)
<br>
When I started using AsyncTasks on my first project, I put them right under their corresponding activity class.  I soon found this way to be very inconvenient because the AsyncTasks have an implicit reference to their activity, and this implicit connection led the AsyncTasks to be destroyed if their activity changed, yet they are not garbage collected (GC) until they finish.  Such behavior could cause potnetial memory issue if there are multiple AsyncTasks on the activity and it could also cause the result of the AsyncTasks to be lost.
<br><br>
As more AsyncTasks needed, I started making classes for different AsyncTasks, but this effort soon became very troublesome to keep track of all the AsyncTasks and make sure their results got delivered properly.
<br><br>
Then, I found Otto, which saved me from headaches.  I use it as a singleton to better organzie AsynTasks. Activities will register themselves onCreate() and unregister themselves onDestroy(), and they will subscribe to the results of AsyncTasks once they are available.

**Apache Commons Validator** - An commons validator project from [Apache](http://commons.apache.org/proper/commons-validator/)
<br>
I use its EmailValidator class to validate the emails inputed by users.

[**Apache Commons Lang**](http://commons.apache.org/proper/commons-lang/)
<br>
An helper utilities with extra methods for the java.lang API.  I mainly use its extra methods to do verification and manipulation on strings.
