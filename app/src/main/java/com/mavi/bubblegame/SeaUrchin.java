package com.mavi.bubblegame;

import android.graphics.Bitmap;

public class SeaUrchin {
	public float x;
	public float y;
	public float friction;
	public float drawX;
	public float drawY;
	public float vel;
	public float velX;
	public float velY;
	private int width;
	private int height;
	private int pictureHeight;
	private int pictureWidth;
	public Bitmap pic = null;
	private Bitmap originalPic = null;
	public float radius;
	private float lastGX;
	private float lastGY;
	private boolean noBorderX = false;
	private boolean noBorderY = false;
	public boolean collisionBool = false;
	public boolean sick = false;
	public boolean happy = false;
	public int sicknessAge = 0;
	public int happinessAge = 0;
	
	public SeaUrchin(Bitmap icon, int w, int h){
		pic = icon;
		originalPic = icon;
		pictureHeight = pic.getHeight();
		pictureWidth = pic.getWidth();
		width = w;
		height = h;
		x = 10;
		y = 10;
		radius = pictureHeight / 2;
	}
	
	public void moveUrchin(float inx, float iny, float fric){
		friction = fric;
		lastGX += inx;
		lastGY += iny;
		x += (lastGX)*(1-friction);
		y += (lastGY)*(1-friction);
		if (x > (width-pictureWidth)){
			x = width-pictureWidth;
			lastGX = 0;
			if (noBorderX){
				noBorderX = false;
			} else {
				noBorderX = true;
			}
		}
		else{
			if (x < (0)){
			x = 0;
			lastGX = 0;
			if (noBorderX){
				noBorderX = false;
			} else {
				noBorderX = true;
				}
			}
		}

	    if (y > (height - pictureHeight)){
	    	y = height-pictureHeight;
			lastGY = 0;
			if (noBorderY){
				noBorderY = false;
			} else {
				noBorderY = true;
			}
		}
		else{ if (y < (0)){
			y = 0;
			lastGY = 0;
			if (noBorderY){
				noBorderY = false;
				} else {
					noBorderY = true;
				}
			}
		}
	}
	
	private void draw_coor(){
		drawX = (float) (x + pictureWidth/2);
		drawY = (float) (y + pictureHeight/2);
	}
	
	public void changePic(Bitmap newPic){
		pic = newPic;
	}
	
	public void checkSickUrchin(Bitmap[] sickUrchin){
		if (sick){
			switch(sicknessAge){
			case 0:
				changePic(sickUrchin[0]);
				sicknessAge++;
				break;
			case 10:
				changePic(sickUrchin[1]);
				sicknessAge++;
				break;
			case 20:
				changePic(sickUrchin[2]);
				sicknessAge++;
				break;
			case 30:
				changePic(sickUrchin[3]);
				sicknessAge++;
				break;
			case 40:
				changePic(sickUrchin[4]);
				sicknessAge++;
				break;
			case 50:
				changePic(sickUrchin[5]);
				sicknessAge++;
				break;
			case 60:
				changePic(originalPic);
				sicknessAge = 0;
				sick = false;
				break;
			default:
				sicknessAge++;	
			}
		}
	}
	
	public void checkHappyUrchin(Bitmap[] happyUrchin){
		if (happy){
			switch(happinessAge){
			case 0:
				changePic(happyUrchin[0]);
				happinessAge++;
				break;
			case 10:
				changePic(happyUrchin[1]);
				happinessAge++;
				break;
			case 20:
				changePic(happyUrchin[2]);
				happinessAge++;
				break;
			case 30:
				changePic(happyUrchin[3]);
				happinessAge++;
				break;
			case 40:
				changePic(happyUrchin[4]);
				happinessAge++;
				break;
			case 50:
				changePic(happyUrchin[5]);
				happinessAge++;
				break;
			case 60:
				changePic(happyUrchin[6]);
				happinessAge++;
				break;
			case 70:
				changePic(originalPic);
				happinessAge = 0;
				happy = false;
				break;
			default:
				happinessAge++;	
			}
		}
	}
	
	private float dist(double x2, double y2, double x3, double y3 ){
		float z = (float) (Math.sqrt(Math.pow(x2-x3, 2)+Math.pow(y2-y3, 2)));
		return z;
	}
	
	public boolean collision(Bubble bub2){
		draw_coor();
		float distance = dist(drawX,drawY,bub2.x,bub2.y);
		float safety = radius + bub2.radius;
		if (distance <= safety){
			collisionBool = true;
			if(bub2.idType == "skull"){
				sick = true;
			} else{
				if (bub2.idType == "star"){
					happy = true;
				}
			}
			return true;
		}
		else{
			return false;
		}
	}

}
