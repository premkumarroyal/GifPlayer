# GifPlayer
Simple and small library to play Graphics Interchange Format (GIF) in Android with Start, Stop, Resume, Pause, Completion the controls with Callbacks.


## Sample Project output:

![GifPlayer](https://j.gifs.com/nZyRrW.gif "Order Delivery")   ![Alt GifPlayer](https://j.gifs.com/oYzZQB.gif "Go Skate")
![GifPlayer](https://j.gifs.com/AnZ6P3.gif "Ice Cream Delivery") ![GifPlayer](https://j.gifs.com/98ZDYx.gif "Listners")
![GifPlayer](https://j.gifs.com/g5op5k.gif "Dialog")



## Advantages

* Play GIF One time or Repeatedly.

* Get notified for each action that you perform

Actions | Listners
------------ | -------------
start() | onGifStarted()
stop() | onGifStopped()
pause() | onGifPaused()
resume() | onGifResumed()
-- | onGifComplete()

NOTE : onGifComplete() will be notified if GIF is in PLAY_ONCE mode.

* It is just a View so you can add this view to any layout in your app such as 
  * Activity
  * Fragment
  * Style Sheets
  * Dialogs
  etc...


## Usage :

Add Gradle Dependency in your build.gradle file

    compile 'com.whiteelephant:gifplayer:1.2.0'

or Maven

    <dependency>
    <groupId>com.whiteelephant</groupId>
    <artifactId>gifplayer</artifactId>
    <version>1.0.0</version>
    <type>pom</type>
     </dependency>
   


##### Add the GIF view to your layout

    <com.whiteelephant.gifplayer.GifView
       android:id="@+id/gif"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       app:animationSpeed="1"
       app:playMode="PLAY_REPEAT"
       app:src="@raw/car" />
             
            
 #### Code :
 Once your GifView is ready call start() method to start Gif to play
 
     GifView gifView = (GifView) findViewById(R.id.gif);
     gifView.start();
     
 ##### Custom xml attributes:
 
* **animationSpeed** : Speed of GIF. Programmatically increase/decrease the Speed of GIF. The default speed is 1.
* **playMode** : We can play the GIF only once or repeat. You can set the PLAY_REPEAT or PLAY_ONCE.
* **src** : Pass the GIF 
    
##### Set attributes Programmatically:

* setAnimationSpeed(float speed)
* setPlayMode(int playMode)
* setGIFResource(@RawRes gif)
    
##### Add the listeners:

We seriously don't want you to implement unnecessary listeners, just add those which really you use.
    
    addOnStartListener(GifView.GifStartListener onStart)
    addOnStopListener(GifView.GifStartListener onStop)
    addOnResumeListener(GifView.GifResumeListener onResume)
    addOnPauseListener(GifView.GifPauseListener onPause)
    addOnCompletionListener(GifView.GifCompletionListener onCompletion)  
    
 
Icon Credit: http://www.flaticon.com/

