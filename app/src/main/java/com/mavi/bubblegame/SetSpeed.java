package com.mavi.bubblegame;

import com.devadvance.circularseekbar.CircularSeekBar;
import com.devadvance.circularseekbar.CircularSeekBar.OnCircularSeekBarChangeListener;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class SetSpeed extends Activity {
	public int difficulty;
	public float friction;
	private TextView difficultySelect = null;
	public AnimationDrawable explodingBackSet = null;
	public SettingGame activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.settings2);
		activity = (SettingGame)this.getParent();
		friction = activity.friction;
		Log.v("settings","obtuvo friction: "+String.valueOf(friction));
		TextView diffLabel = (TextView)findViewById(R.id.difficultyLabel);
		Typeface fontST = Typeface.createFromAsset(getAssets(), "Smoking Tequila.ttf");
		diffLabel.setTypeface(fontST);
		difficultySelect = (TextView)findViewById(R.id.difficulty);
		difficultySelect.setTypeface(fontST);
		CircularSeekBar diff = (CircularSeekBar)findViewById(R.id.circularSeekBar1);
		if(friction == (float)0.8){
			difficultySelect.setText("Normal");
			diff.setProgress(1);
		}else{
			if(friction == (float)0.9){
				difficultySelect.setText("Slow");
				diff.setProgress(0);
			}else{
				if(friction == (float)0.7){
					difficultySelect.setText("Fast");
					diff.setProgress(2);
				}else{
					difficultySelect.setText("Error");
					diff.setProgress(1);
				}
			}
		}
		
		diff.setOnSeekBarChangeListener(new CircleSeekBarListener(){
			@Override
			public void onProgressChanged(CircularSeekBar seekBar, int progress, boolean fromUser){
				difficulty = seekBar.getProgress();	
				switch(difficulty){
				case 0:
					friction = (float)0.9;
					difficultySelect.setText("Slow");
					activity.friction = friction;
					break;
				case 1:
					friction = (float)0.8;
					difficultySelect.setText("Normal");
					activity.friction = friction;
					break;
				case 2:
					friction = (float)0.7;
					difficultySelect.setText("Fast");
					activity.friction = friction;
					break;
				default:
					friction = (float)0.8;
					difficultySelect.setText("Error");
					activity.friction = friction;
					break;
				}
				Log.v("SetSpeed", "The friction is:"+String.valueOf(activity.friction));
			}
			@Override
			public void onStartTrackingTouch(CircularSeekBar seekBar){}
			@Override
			public void onStopTrackingTouch(CircularSeekBar seekBar){}
		});
		difficulty = diff.getProgress();
		switch(difficulty){
		case 0:
			friction = (float)0.7;
			break;
		case 1:
			friction = (float)0.8;
			break;
		case 2:
			friction = (float)0.9;
			break;
		default:
			friction = (float)0.8;
			break;
		}
		
	}
	
	
	public class CircleSeekBarListener implements OnCircularSeekBarChangeListener {
	    @Override
	    public void onProgressChanged(CircularSeekBar circularSeekBar, int progress, boolean fromUser) {
	        // TODO Insert your code here

	    }

		@Override
		public void onStopTrackingTouch(CircularSeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStartTrackingTouch(CircularSeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
	}

}
