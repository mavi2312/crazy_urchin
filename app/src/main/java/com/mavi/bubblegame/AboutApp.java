package com.mavi.bubblegame;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutApp extends Activity {
	
	public AnimationDrawable explodingBack = null;
	public AnimationDrawable erizoFeliz = null;
	public Animation moveUrchin = null;
	public ImageView erizo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.about_app);
		TextView title = (TextView)findViewById(R.id.about_app_title);
		Typeface fontST = Typeface.createFromAsset(getAssets(), "Smoking Tequila.ttf");
		title.setTypeface(fontST);
		ImageButton back = (ImageButton)findViewById(R.id.about_app_back_button);
		explodingBack = (AnimationDrawable)back.getDrawable();
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				explodingBack.start();
				checkIfAnimationDoneBack(explodingBack);
			}
		});
		String pwText = getResources().getString(R.string.about_app_fonts1);
		TextView pwFonts = (TextView)findViewById(R.id.textView_PWFonts);
		pwFonts.setText(Html.fromHtml(pwText));
		pwFonts.setMovementMethod(LinkMovementMethod.getInstance());
		String gemText = getResources().getString(R.string.about_app_fonts2);
		TextView gemFonts = (TextView)findViewById(R.id.textView_GemFonts);
		gemFonts.setText(Html.fromHtml(gemText));
		gemFonts.setMovementMethod(LinkMovementMethod.getInstance());
		erizo = (ImageView)findViewById(R.id.about_app_erizo);
		erizoFeliz = (AnimationDrawable)erizo.getDrawable();
		moveUrchin = AnimationUtils.loadAnimation(this, R.anim.about_urchin);
		
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus){
			erizo.startAnimation(moveUrchin);
			erizoFeliz.start();
			}
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
