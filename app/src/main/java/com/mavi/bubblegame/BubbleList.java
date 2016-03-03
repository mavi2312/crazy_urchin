package com.mavi.bubblegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
//import android.util.Log;

public class BubbleList {
	Bubble first;
	int count = 0;
	int score = 0;
	int lives = 10;
	int lones = 0;
	int stars = 0;

	public BubbleList(){
		first = null;
	}
	
	public boolean emptyBList(){
		if (first == null)
			return true;
		else 
			return false;
	}
	
	public void addBubble(Bubble bub1){
		if (emptyBList()){
			first = bub1;
			count++;
		} else {
			Bubble current = first; 
			while(current.next != null)
				current = current.next;
			current.next = bub1;
			count++;
		}
	}
	
	public Bubble searchBubble(Bubble bub1){
		Bubble current = first;
		while (current.next != null){
			if (current.idNum == bub1.idNum && current.idType == bub1.idType){
				return current;
			} else {
				current = current.next; 
			}
		}
		return null;
	}
	
	public Bubble searchPrevBubble(Bubble bub1){
		Bubble current = first;
		while (current.next != null){
			if (current.next.idNum == bub1.idNum && current.next.idType == bub1.idType){
				return current;
			} else {
				current = current.next; 
			}
		}
		return null;
	}
	
	public Bubble searchBubbleIn(int index){
		Bubble current = first;
		while (current.next != null){
			if (current.idNum == index){
				return current;
			} else {
				current = current.next; 
			}
		}
		return null;
	}
	
	public boolean removeBubble(Bubble bub1){
		if (!emptyBList()){
			if (first.idNum == bub1.idNum && first.idType == bub1.idType){
				first = bub1.next;
				bub1 = null;
				count--;
				return true;
			}
			else{
				Bubble current = searchPrevBubble(bub1);
				current.next = bub1.next;
				bub1 = null;
				count--;
				return true;
			}
		} else {
			return false;
		}
	}
	
	public int countBubble(){
		if (!emptyBList()){
			Bubble current = first;
			int count = 0;
			while (current.next != null){
				count +=1;
				current = current.next;
			}
			return count;
		}
		else
			return 0;
	}
	
	public int countTypeBubble(String type){
		if (!emptyBList()){
			Bubble current = first;
			int count = 0;
			while (current.next != null){
				if (current.idType == type){
					count +=1;
				}
				current = current.next;
			}
			return count;
		}
		else 
			return 0;
	}
	
	public void drawBubbles(Canvas canvas){
		if (!emptyBList()){
			Bubble current = first;		
			do{	
				canvas.drawBitmap(current.pic, current.drawX, current.drawY, null);
				//Log.v("drawBubbles", "Draw bubble: "+current.idNum);
				if(current.next != null){
					current = current.next;
				}
			}while(current.next != null);
		}
	}
	
	public void moveBubbles(){
		if (!emptyBList()){
			Bubble current = first;		
			do{
				current.motion();
				if (current.age >= current.lifespan)
					preRemove(current);
				else 
					current.age++;
				if(current.next != null){
					current = current.next;
				}
			}while (current.next != null);
		}
	}
	
	public void checkCollision(SeaUrchin urchin){
		if (!emptyBList()){
			Bubble current = first;
			do{
				if(!current.colision){
					if (urchin.collision(current)){
						if (current.idType == "lone"){
							score += 10;
							lones ++;
						} else {
							if (current.idType == "star"){
								score += 20;
								stars ++;
							} else {
								if (current.idType == "skull"){
									lives --;
								}
							}
						}
						current.colision = true;
						preRemove(current);
						break;
					}else {
						if(current.next != null){
							current = current.next;
						}
					}
				}else {
					if(current.next != null){
						current = current.next;
					}
				}
			}while (current.next!=null);
		}
	}
	
	public void preRemove(Bubble bubble){
		bubble.explosion = true;
		bubble.vel = 0;
		bubble.velX = 0;
		bubble.velY = 0;
	}
	
	public void checkRemoveSimple(Bitmap[] explosionPics){
		if (!emptyBList()){
			Bubble current = first;		
			do{
				if (current.idType == "lone"){
					if(current.explosion){
						switch(current.explosionAge){
						case 0:
							current.changePic(explosionPics[0]);
							current.explosionAge++;
							break;
						case 1:
							current.changePic(explosionPics[1]);
							current.explosionAge++;
							break;
						case 2:
							current.changePic(explosionPics[2]);
							current.explosionAge++;
							break;
						case 3:
							current.changePic(explosionPics[3]);
							current.explosionAge++;
							break;
						case 4:
							current.changePic(explosionPics[4]);
							current.explosionAge++;
							break;
						case 5:
							removeBubble(current);
							break;
						}
					} 
				}				
				if(current.next != null){
					current = current.next;
				}
			}while (current.next != null);
		}
	}
	
	public void checkRemoveStar(Bitmap[] explosionPics){
		if (!emptyBList()){
			Bubble current = first;		
			do{
				if (current.idType == "star"){
					if(current.explosion){
						switch(current.explosionAge){
						case 0:
							current.changePic(explosionPics[0]);
							current.explosionAge++;
							break;
						case 1:
							current.changePic(explosionPics[1]);
							current.explosionAge++;
							break;
						case 2:
							current.changePic(explosionPics[2]);
							current.explosionAge++;
							break;
						case 3:
							current.changePic(explosionPics[3]);
							current.explosionAge++;
							break;
						case 4:
							current.changePic(explosionPics[4]);
							current.explosionAge++;
							break;
						case 5:
							current.changePic(explosionPics[5]);
							current.explosionAge++;
							break;
						case 6:
							current.changePic(explosionPics[6]);
							current.explosionAge++;
							break;
						case 7:
							current.changePic(explosionPics[7]);
							current.explosionAge++;
							break;
						case 8:
							current.changePic(explosionPics[8]);
							current.explosionAge++;
							break;
						case 9:
							current.changePic(explosionPics[9]);
							current.explosionAge++;
							break;
						case 10:
							removeBubble(current);
							break;
						}
					} 
				}				
				if(current.next != null){
					current = current.next;
				}
			}while (current.next != null);
		}
	}
	
	public void checkRemoveSkull(Bitmap[] explosionPics){
		if (!emptyBList()){
			Bubble current = first;		
			do{
				if (current.idType == "skull"){
					if(current.explosion){
						switch(current.explosionAge){
						case 0:
							current.changePic(explosionPics[0]);
							current.explosionAge++;
							break;
						case 1:
							current.changePic(explosionPics[1]);
							current.explosionAge++;
							break;
						case 2:
							current.changePic(explosionPics[2]);
							current.explosionAge++;
							break;
						case 3:
							current.changePic(explosionPics[3]);
							current.explosionAge++;
							break;
						case 4:
							current.changePic(explosionPics[4]);
							current.explosionAge++;
							break;
						case 5:
							current.changePic(explosionPics[5]);
							current.explosionAge++;
							break;
						case 6:
							current.changePic(explosionPics[6]);
							current.explosionAge++;
							break;
						case 7:
							current.changePic(explosionPics[7]);
							current.explosionAge++;
							break;
						case 8:
							current.changePic(explosionPics[8]);
							current.explosionAge++;
							break;
						case 9:
							current.changePic(explosionPics[9]);
							current.explosionAge++;
							break;
						case 10:
							removeBubble(current);
							break;
						}
					} 
				}				
				if(current.next != null){
					current = current.next;
				}
			}while (current.next != null);
		}
	}
}
