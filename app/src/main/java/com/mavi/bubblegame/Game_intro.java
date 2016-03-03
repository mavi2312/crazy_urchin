package com.mavi.bubblegame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Game_intro extends Activity {

	private static final int ACTION_PLAY = 1;
	private static final int ACTION_SCORE = 2;
	private static final int ACTION_SETTINGS = 3;
	private static final int ACTION_TUTORIAL = 4;
	public AnimationDrawable explodingNewGame = null;
	public AnimationDrawable explodingExit = null;
	public AnimationDrawable explodingHighScore = null;
	public AnimationDrawable explodingSettings = null;
	public AnimationDrawable bubbleTitle = null;
	public int score = 0, newScore;
	public boolean sound = true, newSound, firstTime;
	public float friction, newFriction;
	public String scoreValue = "scoreValue", scoreString="", soundValue="soundValue", frictionValue="frictionValue", firstGameValue="firstGamevalue";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_ball);
		final ImageButton newGame = (ImageButton) findViewById(R.id.newGameImg);
		//newGame.setBackgroundResource(R.drawable.exploding_newgame);
		explodingNewGame = (AnimationDrawable)newGame.getDrawable();
		if(explodingNewGame.isRunning()){
			explodingNewGame.stop();
		}
		newGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				explodingNewGame.start();
				checkIfAnimationDoneNewGame(explodingNewGame);
			}
		});
		final ImageButton exit = (ImageButton) findViewById(R.id.btnExitImg);
		explodingExit = (AnimationDrawable)exit.getDrawable();
		if(explodingExit.isRunning()){
			explodingExit.stop();
		}
		exit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				explodingExit.start();
				checkIfAnimationDoneExit(explodingExit);
			}
		});
		final ImageButton highScore = (ImageButton) findViewById(R.id.highScoreButton);
		explodingHighScore = (AnimationDrawable)highScore.getDrawable();
		if(explodingHighScore.isRunning()){
			explodingHighScore.stop();
		}
		highScore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				explodingHighScore.start();
				Log.v("highScore","empezo la animacion");
				checkIfAnimationDoneHighScore(explodingHighScore);
			}
		});
		final ImageButton settings = (ImageButton) findViewById(R.id.btnSettingsImg);
		explodingSettings = (AnimationDrawable)settings.getDrawable();
		if(explodingSettings.isRunning()){
			explodingSettings.stop();
		}
		settings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				explodingSettings.start();
				checkIfAnimationDoneSettings(explodingSettings);
			}
		});
		ImageView title = (ImageView)findViewById(R.id.about_app_erizo);
		//title.setBackgroundResource(R.drawable.bubble_title);
		bubbleTitle = (AnimationDrawable)title.getDrawable();

		SharedPreferences firstGame = null;
		firstGame = getSharedPreferences(firstGameValue,0);
		firstTime = firstGame.getBoolean(firstGameValue,true);
		SharedPreferences scorePrefs = null;
		scorePrefs = getSharedPreferences(scoreValue, 0);
		score = scorePrefs.getInt(scoreValue, 0);
		SharedPreferences soundPrefs = null;
		soundPrefs = getSharedPreferences(soundValue,0);
		sound = soundPrefs.getBoolean(soundValue,true);
		SharedPreferences fricPrefs = null;
		fricPrefs = getSharedPreferences(frictionValue,0);
		friction = fricPrefs.getFloat(frictionValue,(float)0.8);
		Log.v("onCreate","obtuvo friction: "+String.valueOf(friction));
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus)
			bubbleTitle.start();
	}

	public void startGame(){
		if(firstTime){
			Log.v("newGameTutorial","start new Activity Tutorial");
			Intent i = new Intent(this, Ball.class);
			i.putExtra("sound", sound);
			i.putExtra("friction", (float) 0.9);
			i.putExtra("type",2);
			startActivityForResult(i, ACTION_TUTORIAL);
		}else{
			Log.v("newGame","start new Activity");
			Intent i = new Intent(this, Ball.class);
			i.putExtra("sound", sound);
			i.putExtra("friction", friction);
			i.putExtra("type",1);
			startActivityForResult(i, ACTION_PLAY);
		}
	}

	public void highScore(){
		Log.v("highScore","entro a high score");
		Intent a = new Intent(this, HighScore.class);
		a.putExtra(scoreValue, score);
		startActivityForResult(a, ACTION_SCORE);
	}

	public void settings(){
		Log.v("settings","entro a settings: friction: "+String.valueOf(friction));
		Intent a = new Intent(this, SettingGame.class);
		a.putExtra("sound", sound);
		a.putExtra("friction", friction);
		startActivityForResult(a, ACTION_SETTINGS);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK){
			switch(requestCode){
			case ACTION_PLAY:
				new AlertDialog.Builder(this).setMessage(getResources().getString(R.string.GameEnd)+" "+data.getIntExtra("score", 0)).setPositiveButton(android.R.string.ok,null).show();
				newScore = data.getIntExtra("score",0);
				if (newScore > score){
					SharedPreferences scorePrefs = null;
					scorePrefs = getSharedPreferences(scoreValue,0);
					SharedPreferences.Editor editor = scorePrefs.edit();
					editor.putInt(scoreValue, newScore);
					editor.commit();
					score = newScore;
				}
				break;
			case ACTION_SCORE:
				break;
			case ACTION_SETTINGS:
				newSound = data.getBooleanExtra("sound", true);
				newFriction = data.getFloatExtra("friction", (float) 0.8);
				SharedPreferences soundPrefs = null;
				soundPrefs = getSharedPreferences(soundValue,0);
				SharedPreferences.Editor editorSound = soundPrefs.edit();
				editorSound.putBoolean(soundValue, newSound);
				editorSound.commit();
				sound = newSound;
				SharedPreferences fricPrefs = null;
				fricPrefs = getSharedPreferences(frictionValue,0);
				SharedPreferences.Editor editorFric = fricPrefs.edit();
				editorFric.putFloat(frictionValue, newFriction);
				editorFric.commit();
				friction = newFriction;
				break;
			case ACTION_TUTORIAL:
				firstTime = false;
				SharedPreferences firstGame = null;
				firstGame = getSharedPreferences(firstGameValue,0);
				SharedPreferences.Editor editor = firstGame.edit();
				editor.putBoolean(firstGameValue, firstTime);
				editor.commit();
				break;
			}
		}
		System.out.println("sound = " + sound);
		explodingNewGame.selectDrawable(0);
		explodingHighScore.selectDrawable(0);
		explodingSettings.selectDrawable(0);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ball, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()){
		case R.id.action_about:
			Intent a = new Intent(this, AboutApp.class);
			startActivity(a);
			return true;
		case R.id.action_tutorial:
			Log.v("newGameTutorial","start new Activity Tutorial");
			Intent i = new Intent(this, Ball.class);
			i.putExtra("sound", sound);
			i.putExtra("friction", (float) 0.9);
			i.putExtra("type",2);
			startActivityForResult(i, ACTION_TUTORIAL);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void checkIfAnimationDoneNewGame(AnimationDrawable anim){
		final AnimationDrawable a = anim;
		int timeBetweenChecks = 20;
		Handler h = new Handler();
		h.postDelayed(new Runnable(){
			public void run(){
				if (a.getCurrent() != a.getFrame(a.getNumberOfFrames()-1)){
					checkIfAnimationDoneNewGame(a);
				} else {
					Log.v("newGame","termino la animacion");
					explodingNewGame.stop();
					startGame();
				}
			}
		}, timeBetweenChecks);
	}

	private void checkIfAnimationDoneExit(AnimationDrawable anim){
		final AnimationDrawable a = anim;
		int timeBetweenChecks = 20;
		Handler h2 = new Handler();
		h2.postDelayed(new Runnable(){
			public void run(){
				if (a.getCurrent() != a.getFrame(a.getNumberOfFrames()-1)){
					checkIfAnimationDoneExit(a);
				} else {
					explodingExit.stop();
					finish();
				}
			}
		}, timeBetweenChecks);
	}

	private void checkIfAnimationDoneHighScore(AnimationDrawable anim){
		Log.v("highScore","chequeo si termino la animacion");
		final AnimationDrawable a = anim;
		int timeBetweenChecks = 20;
		Handler h3 = new Handler();
		h3.postDelayed(new Runnable(){
			public void run(){
				if (a.getCurrent() != a.getFrame(a.getNumberOfFrames()-1)){
					checkIfAnimationDoneHighScore(a);
					Log.v("highScore","recurrencia");
				} else {
					Log.v("highScore","termino la animacion");
					explodingHighScore.stop();
					Log.v("highScore","se detuvo la animacion");
					highScore();
				}
			}
		}, timeBetweenChecks);
	}

	private void checkIfAnimationDoneSettings(AnimationDrawable anim){
		Log.v("settings","chequeo si termino la animacion");
		final AnimationDrawable a = anim;
		int timeBetweenChecks = 20;
		Handler h3 = new Handler();
		h3.postDelayed(new Runnable(){
			public void run(){
				if (a.getCurrent() != a.getFrame(a.getNumberOfFrames()-1)){
					checkIfAnimationDoneSettings(a);
					Log.v("settings","recurrencia");
				} else {
					Log.v("settings","termino la animacion");
					explodingSettings.stop();
					Log.v("settings","se detuvo la animacion");
					settings();
				}
			}
		}, timeBetweenChecks);
	}
}
