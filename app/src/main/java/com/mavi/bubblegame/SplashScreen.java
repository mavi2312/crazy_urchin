package com.mavi.bubblegame;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreen extends Activity {
	public TextView loadMsg;
	public Animation loadMsgAnim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.splash_screen);
		final ProgressBar load = (ProgressBar)findViewById(R.id.progressBar1);
		loadMsg = (TextView)findViewById(R.id.labelScore);
		loadMsgAnim = AnimationUtils.loadAnimation(this, R.anim.loading_title);
		Thread logoTimer = new Thread() {
			public void run(){
				try{
					int logoTimer = 0;
					load.getProgressDrawable().setColorFilter(Color.CYAN, Mode.DST_IN);
					int i = 1;
					while(logoTimer < 5000){
						sleep(100);
						logoTimer = logoTimer +100;
						if(logoTimer==i*1000){
							load.setProgress(i);
							i++;
						}
					};
					startActivity(new Intent("com.mavi.CLEARSCREEN"));
				} 
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					finish();
				}
			}
		};
		logoTimer.start();
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus)
			loadMsg.startAnimation(loadMsgAnim);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
}


