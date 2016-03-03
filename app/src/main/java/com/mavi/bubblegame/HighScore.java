package com.mavi.bubblegame;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class HighScore extends Activity {

	public int scoreInt;
	public AnimationDrawable explodingBack = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.high_score_screen);
		Bundle extras = getIntent().getExtras();
		scoreInt = extras.getInt("scoreValue");
		TextView score = (TextView)findViewById(R.id.high_score_label);
		Typeface fontST = Typeface.createFromAsset(getAssets(), "Smoking Tequila.ttf");
		score.setTypeface(fontST);
		score.setText(Integer.toString(scoreInt));
		ImageButton back = (ImageButton)findViewById(R.id.backButtonHS);
		explodingBack = (AnimationDrawable)back.getDrawable();
		if(explodingBack.isRunning()){
			explodingBack.stop();
		}
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				explodingBack.start();
				checkIfAnimationDoneBack(explodingBack);
			}
		});
		
	}
	
	private void checkIfAnimationDoneBack(AnimationDrawable anim){
		final AnimationDrawable a = anim;
		int timeBetweenChecks = 20;
		Handler h = new Handler();
		h.postDelayed(new Runnable(){
			public void run(){
				if (a.getCurrent() != a.getFrame(a.getNumberOfFrames()-1)){
					checkIfAnimationDoneBack(a);
				} else {
					Log.v("newGame","termino la animacion");
					explodingBack.stop();
					goBackMainScreen();
				}
			}
		}, timeBetweenChecks);
	}
	
	public void goBackMainScreen(){
		finish();
	}

	
	

}
