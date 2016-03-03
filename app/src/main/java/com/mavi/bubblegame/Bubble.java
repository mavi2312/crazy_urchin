package com.mavi.bubblegame;

import java.util.Random;

import android.graphics.Bitmap;

public class Bubble {
	public float x;
	public float y;
	public float drawX;
	public float drawY;
	public float vel;
	public float velX;
	public float velY;
	public double ang;
	private int pictureHeight;
	private int pictureWidth;
	public Bitmap pic = null;
	private int width;
	private int height;
	public float radius;
	public Bubble next;
	public String idType;
	public int idNum;
	public int age = 0;
	public int lifespan = 0;
	public int explosionLife = 0;
	public int explosionAge = 0;
	public boolean explosion = false;
	public boolean colision = false;
	
	public Bubble(IdBubble id, int w, int h, Random r){
		this.pic = id.pic;
		width = w;
		height = h;
		pictureHeight = pic.getHeight();
		pictureWidth = pic.getWidth();
		radius = pictureWidth / 2;
		x = (float) (width*0.5125); 
		y = (float) (height*0.69);
		vel = r.nextFloat() + 1;
		ang = r.nextDouble()*2*(Math.PI);
		velX = (float) (vel*Math.cos(ang));
		velY = (float) (vel*Math.sin(ang));
		drawX = (float) (x - pictureWidth/2);
		drawY = (float) (y - pictureHeight/2);
		next = null;
		idType = id.type;
		idNum = id.num;
		if (idType == "lone"){
			lifespan = 500;
			explosionLife = 5;
		} else {
			if (idType == "star"){
				lifespan = 300;
				explosionLife = 10;
			} else {
				if (idType == "skull"){
					lifespan = 250;
					explosionLife = 10;
				}
			}
		}
	}
	
	public void motion(){
		x += velX;
		y += velY;
		insideScreen();
		draw_coor();
		
	}
	
	private void draw_coor(){
		drawX = (float) (x - pictureWidth/2);
		drawY = (float) (y - pictureHeight/2);
	}
	
	private void insideScreen(){
		if (x < 0)
			x = width + x;
		else 
			x = Math.abs(x) % width;
		if (y < 0)
			y = height + y;
		else
			y = Math.abs(y) % height;
	}
	
	private float dist(double x2, double y2, double x3, double y3 ){
		float z = (float) (Math.sqrt(Math.pow(x2-x3, 2)+Math.pow(y2-y3, 2)));
		return z;
	}
	
	public boolean collision(Bubble bub2){
		float distance = dist(x,y,bub2.x,bub2.y);
		float safety = radius + bub2.radius;
		if (distance <= safety){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void changePic(Bitmap newPic){
		pic = newPic;
	}
	
	private double slope_ang(double x2, double y2, double x3, double y3){
		if (x2 == x3){
			if (y2 < y3)
				return -Math.PI;
			else 
				return Math.PI /2;
		} else {
			float slp = (float) ((y2-y3)/(x2-x3));
			double slp_ang =  Math.atan(slp);
			return slp_ang;
		}
	}
		
	public void changeVelCol(Bubble bub1){
		double slp_ang = slope_ang(x,y,bub1.x,bub1.y);
		double ang_col1, ang_col2;
		
		ang_col1 = ang_x(slp_ang, ang);
		ang_col2 = ang_x(slp_ang, bub1.ang);
		
		float Vpl1 = (float) (vel * Math.cos(ang_col1));
		float Vpp1 = (float) (vel * Math.sin(ang_col1));
		float Vpl2 = (float) (bub1.vel * Math.cos(ang_col2));
		float Vpp2 = (float) (bub1.vel * Math.sin(ang_col2));
		
		vel = (float) Math.sqrt(Math.pow(Vpl2, 2)+Math.pow(Vpp1, 2));
		bub1.vel = (float) Math.sqrt(Math.pow(Vpl1, 2)+Math.pow(Vpp2, 2));
		
		ang = Math.atan(Vpp1/Vpl2);
		bub1.ang = Math.atan(Vpp2/Vpl1);
		
		velX = (float) (vel*Math.cos(ang));
		velY = (float) (vel*Math.sin(ang)); 
		
		bub1.velX = (float) (vel*Math.cos(bub1.ang));
		bub1.velY = (float) (vel*Math.sin(bub1.ang));
		insideScreen();
	}
	
	private double ang_x(double theta, double alpha){
		double beta = 0;
		double x = 0;
		if (theta == 0){
			x = 0;
		} else {
			if (theta == Math.PI/2){
				x = Math.PI /2;
			} else 
				if (theta == - Math.PI/2){
					x = Math.PI/2;
				} else {
					if (theta < Math.PI/2 && theta > 0){
						if (alpha <= Math.PI/2){
							if (alpha <= theta){
								x = theta - alpha;
							} else {
								x = alpha - theta;
							}
						} else {
							if (alpha <= Math.PI){
								beta = Math.PI - alpha;
								if (beta <= theta){
									x = theta + beta;
								} else {
									x = Math.PI - theta - beta;
								}	
							} else {
								if (alpha <= 3*Math.PI/2){
									beta = alpha - Math.PI;
									if (beta <= theta){
										x = theta - beta;
									} else {
										x = beta - theta;
									}
								} else {
									beta = 2*Math.PI - alpha; 
									if (beta <= theta){
										x = beta + theta;
									} else {
										x = Math.PI - beta - theta;
									}
								}
							}
						} 
					} else {
						double theta_abs = Math.abs(theta);
						if (alpha <= Math.PI/2){
							if (alpha <= theta_abs){
								x = alpha + theta_abs;
							} else {
								x = Math.PI - alpha - theta_abs;
							}
						} else {
							if (alpha <= Math.PI){
								beta = Math.PI -alpha;
								if (beta <= theta_abs){
									x = theta_abs - beta;
								} else {
									x = beta - theta_abs;
								}	
							} else {
								if (alpha <= 3*Math.PI/2){
									beta = alpha - Math.PI;
									if (beta <= theta_abs){
										x = theta_abs + beta;
									} else {
										x = Math.PI - theta_abs - beta;
									}
								} else {
									beta = 2*Math.PI - alpha;
									if (alpha <= theta_abs){
										x = theta_abs - beta;
									} else {
										x = beta - theta_abs;
									}
								}
							}
						}
					}
				}
		}
		
		return x;
	}

}
