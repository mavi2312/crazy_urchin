package com.mavi.bubblegame;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {
		private SurfaceHolder surfaceHolder;
		private GroundView panel;
		private boolean run = false;
		
		public DrawThread (SurfaceHolder surfaceHolder, GroundView panel){
			this.surfaceHolder = surfaceHolder;
			this.panel = panel;
		}
		public void setRunning(boolean run){
			this.run = run;
		}
		@SuppressLint("WrongCall")
		@Override
		public void run(){
			Canvas c;
			while (run){
				c = null;
				try {
					c = surfaceHolder.lockCanvas(null);
					synchronized(surfaceHolder){
						panel.onDraw(c);
					}
				} 
				catch(Exception e){
					e.printStackTrace();
				}
				finally {
					if (c!=null){
						surfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}
}
