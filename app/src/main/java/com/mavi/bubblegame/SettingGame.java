package com.mavi.bubblegame;

import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TabHost;


public class SettingGame extends TabActivity {

	public boolean sound;
	public float friction;
	public AnimationDrawable explodingBackSet = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.settings);
		Bundle extra = getIntent().getExtras();
		sound = extra.getBoolean("sound");
		friction = extra.getFloat("friction");		
		ImageButton backSet = (ImageButton)findViewById(R.id.backButtonSet);
		explodingBackSet = (AnimationDrawable)backSet.getDrawable();
		if(explodingBackSet.isRunning()){
			explodingBackSet.stop();
		}
		backSet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				explodingBackSet.start();
				checkIfAnimationDoneBack(explodingBackSet);
			}
		});
		
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;
		Resources res = getResources();
		intent = new Intent().setClass(this,SetSound.class);
		spec = tabHost.newTabSpec("Sound").setIndicator("Set sound", res.getDrawable(R.drawable.sound)).setContent(intent);
		tabHost.addTab(spec);
		intent = new Intent().setClass(this,SetSpeed.class);
		spec = tabHost.newTabSpec("Speed").setIndicator("Set speed", res.getDrawable(R.drawable.speed)).setContent(intent);
		tabHost.addTab(spec);
//		intent = new Intent().setClass(this,List.class);
//		spec = tabHost.newTabSpec("List").setIndicator("List", res.getDrawable(R.drawable.tab_list)).setContent(intent);
//		tabHost.addTab(spec);
//		intent = new Intent().setClass(this,Relative.class);
//		spec = tabHost.newTabSpec("Relative").setIndicator("Relative", res.getDrawable(R.drawable.tab_relative)).setContent(intent);
//		tabHost.addTab(spec);
//		intent = new Intent().setClass(this,Table.class);
//		spec = tabHost.newTabSpec("Table").setIndicator("Table", res.getDrawable(R.drawable.tab_table)).setContent(intent);
//		tabHost.addTab(spec);
		tabHost.setCurrentTab(2);
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
					Log.v("backSet","termino la animacion");
					explodingBackSet.stop();
					goBackMainScreen();
				}
			}
		}, timeBetweenChecks);
	}
	
	public void goBackMainScreen(){
		Intent resultIntent = new Intent((String)null);
		resultIntent.putExtra("sound", sound);
		resultIntent.putExtra("friction", friction);
		setResult(RESULT_OK,resultIntent);
		finish();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent resultIntent = new Intent((String)null);
		resultIntent.putExtra("sound", sound);
		resultIntent.putExtra("friction", friction);
		setResult(RESULT_OK,resultIntent);
		super.onBackPressed();
	}
	
	
	

}
