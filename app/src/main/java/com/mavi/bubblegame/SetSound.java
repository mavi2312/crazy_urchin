package com.mavi.bubblegame;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class SetSound extends Activity {
	public boolean sound;
	private TextView soundOnOff = null;
	private SettingGame activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.settings3);
		activity = (SettingGame)this.getParent();
		sound = activity.sound;
		TextView soundLabel = (TextView)findViewById(R.id.soundLabel);
		Typeface fontST = Typeface.createFromAsset(getAssets(), "Smoking Tequila.ttf");
		soundLabel.setTypeface(fontST);
		soundOnOff = (TextView)findViewById(R.id.soundOnOff);
		soundOnOff.setTypeface(fontST);
		final ImageButton soundBtn = (ImageButton) findViewById(R.id.soundBtn);
		if(sound){
			soundOnOff.setText("ON");
			soundBtn.setImageResource(R.drawable.boton_sonido);
		}else{
			soundOnOff.setText("OFF");
			soundBtn.setImageResource(R.drawable.boton_sonido_apagado);
		}
		soundBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(sound){
					sound = false;
					soundBtn.setImageResource(R.drawable.boton_sonido_apagado);
					soundOnOff.setText("OFF");
					activity.sound = sound;
				} else {
					sound = true;
					soundBtn.setImageResource(R.drawable.boton_sonido);
					soundOnOff.setText("ON");
					activity.sound = sound;
				}
				Log.v("SetSound", "The sound is:"+String.valueOf(activity.sound));

			}
		});

	}


}

