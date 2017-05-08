# GifPlayer
Simple and small library to play Graphics Interchange Format (GIF) in Android with Start, Stop, Resume, Pause, Completion the controls with Callbacks.


## Advantages

* Get notify for each action that you perform

Actions | Listners
------------ | -------------
start() | onGifStarted()
stop() | onGifStoped()
pause() | onGifPaused()
resume() | onGifResumed()
 | onGifComplete()
  
NOTE : onGifComplete() will be notified if GIF is in PLAY_ONCE mode.

* Play GIF One time or Repeatedly. 

* It is just a View so you can add this view to any layout in your app such as 
  * Activity
  * Fragment
  * Style Sheets
  * Dialogs
  etc...


## Usage :




