package com.mavi.bubblegame;

import java.util.Timer;
import java.util.TimerTask;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.os.Handler;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class Ball extends Activity implements SensorEventListener{

	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(ground.bubbles.lives<=0){
			int score = ground.bubbles.score;
			Intent resultIntent = new Intent((String)null);
			resultIntent.putExtra("score", score);
			setResult(RESULT_OK,resultIntent);
		}
		super.onBackPressed();
	}

	public GroundView ground = null;
	private MediaPlayer colSound = null;
	private boolean sound;
	private float friction;
	private int type;
	public TimerTask tarea;
	public Timer timer;
	private long mStartTime;
	
	 private class StartGameCounter extends AsyncTask<Integer, Integer, Integer> {
	     protected Integer doInBackground(Integer... ints) {
	    	 int seconds = 0;
	    	 while(seconds < 4){
	    		final long start = mStartTime;
	 			long millis = SystemClock.elapsedRealtime() - start;
	 			seconds = (int) (millis / 1000);
//	 			int minutes = seconds / 60;
//	 			seconds = seconds % 60;
	 			publishProgress(seconds);
	    	 }
	    	 return seconds;
	     }

	     protected void onProgressUpdate(Integer... progress) {
	    	 //Log.v("newGame","timerCount: "+String.valueOf(progress[0]));
	    	 ground.drawStartNumbers(progress[0]);
	     }

	     protected void onPostExecute(Integer result) {
	    	 Log.v("newGame","register Accelerometer with timer");
	    	 mSensorManager.registerListener(Ball.this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
				
	     }
	 }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		//startGameHandler = new Handler();
		//startGameTime(1000);
		mStartTime = SystemClock.elapsedRealtime();
		//startGameHandler.removeCallbacks(tareaStart);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		Bundle extra = getIntent().getExtras();
		friction = extra.getFloat("friction");
		sound = extra.getBoolean("sound");
		type = extra.getInt("type");
		Log.v("Ball_onCreate", "Sound is: "+String.valueOf(sound));
		Log.v("Ball_onCreate", "Friction is: "+String.valueOf(friction));
		ground = new GroundView(this);
		switch(type){
		case 1:
			ground.isTutorial(false);
			break;
		case 2:
			ground.isTutorial(true);
			break;
		default:
			ground.isTutorial(false);
			break;
		}
		colSound = MediaPlayer.create(this,R.raw.bubble_pop);
		setContentView(ground);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(ground.bubbles.lives<=0){
			int eventaction = event.getAction();

			switch (eventaction) {
			case MotionEvent.ACTION_DOWN: 
				// finger touches the screen
				Log.d("touch", "Down");
				break;

			case MotionEvent.ACTION_MOVE:
				// finger moves on the screen
				Log.d("touch", "Move");
				break;

			case MotionEvent.ACTION_UP:   
				// finger leaves the screen
				Log.d("touch", "Up");
				int score = ground.bubbles.score;
				Intent resultIntent = new Intent((String)null);
				resultIntent.putExtra("score", score);
				setResult(RESULT_OK,resultIntent);
				finish();
				break;
			}
			return true;
		} else{
			return super.onTouchEvent(event);
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy){

	}

	public void onSensorChanged(SensorEvent event){
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			ground.updateMe(event.values[1],event.values[0], friction);
			if(type == 1)
				checkIfFinished();
			else if(type == 2)
				checkIfFinishedTutorial();
			if(sound){
				checkIfSound();
			}
		}
	}

	private void checkIfFinished() {
		// TODO Auto-generated method stub
		//int score = ground.bubbles.score;
		int lives = ground.bubbles.lives;
		if (lives == 0){
			//			Intent resultIntent = new Intent((String)null);
			//			resultIntent.putExtra("score", score);
			//			setResult(RESULT_OK,resultIntent);
			//			finish();
			endGameTime(30000);
		}
	}
	
	private void checkIfFinishedTutorial() {
		// TODO Auto-generated method stub
		//int score = ground.bubbles.score;
		int count = ground.countBubblesTutorial;
		if (count > 3){
			//			Intent resultIntent = new Intent((String)null);
			//			resultIntent.putExtra("score", score);
			//			setResult(RESULT_OK,resultIntent);
			//			finish();
			endGameTime(5000);
		}
	}

	private void checkIfSound(){
		if (ground.urchin.collisionBool){
			colSound.start();
			ground.urchin.collisionBool = false;
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mSensorManager.unregisterListener(this);
		ground.thread.setRunning(false);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//		Log.v("onResume","register Accelerometer without timer");
		//		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
		//startGameTime(1000);
		//startGameHandler.postDelayed(tareaStart, 1000);
		StartGameCounter timerStart =new StartGameCounter();
		timerStart.execute(0);

		ground.thread.setRunning(true);
	}

	public void endGameTime(long delay){
		tarea = new EndGameTask();			
		timer = new Timer();
		timer.schedule(tarea, delay);
	}

	class EndGameTask extends TimerTask{
		@Override
		public void run(){
			int score = ground.bubbles.score;
			Intent resultIntent = new Intent((String)null);
			resultIntent.putExtra("score", score);
			setResult(RESULT_OK,resultIntent);
			finish();
		}
	}
}
