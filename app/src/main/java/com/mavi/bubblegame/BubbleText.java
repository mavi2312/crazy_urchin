package com.mavi.bubblegame;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BubbleText {

	private Bitmap one = null;
	private Bitmap two = null;
	private Bitmap three = null;
	private Bitmap four = null;
	private Bitmap five = null;
	private Bitmap six = null;
	private Bitmap seven = null;
	private Bitmap eight = null;
	private Bitmap nine = null;
	private Bitmap zero = null;
	private Bitmap por = null;
	private Bitmap equal = null;
	private String number;
	private char[] numbers;
	public int imgWidth;
	
	public BubbleText(Bitmap one, Bitmap two, Bitmap three, Bitmap four, Bitmap five, Bitmap six, Bitmap seven, Bitmap eight, Bitmap nine, Bitmap zero, Bitmap por, Bitmap equal){
		this.one = one;
		this.two = two;
		this.three = three;
		this.four = four;
		this.five = five;
		this.six = six;
		this.seven = seven;
		this.eight = eight;
		this.nine = nine;
		this.zero = zero;
		this.number = "";
		this.por = por;
		this.equal = equal;
		imgWidth = zero.getWidth()/2;
	}
	
	public void drawNumbers(Canvas canvas, String text, int x, int y){
		number = text;
		int size = number.length();
		numbers = number.toCharArray();
		for (int i = 0; i<size ; i++){
			if (numbers[i]=='0'){
				canvas.drawBitmap(zero, x+i*imgWidth, y, null);
			} else if (numbers[i]=='1'){
				canvas.drawBitmap(one, x+i*imgWidth, y, null);
			} else if (numbers[i]=='2'){
				canvas.drawBitmap(two, x+i*imgWidth, y, null);
			} else if (numbers[i]=='3'){
				canvas.drawBitmap(three, x+i*imgWidth, y, null);
			}else if (numbers[i]=='4'){
				canvas.drawBitmap(four, x+i*imgWidth, y, null);
			}else if (numbers[i]=='5'){
				canvas.drawBitmap(five, x+i*imgWidth, y, null);
			}else if (numbers[i]=='6'){
				canvas.drawBitmap(six, x+i*imgWidth, y, null);
			}else if (numbers[i]=='7'){
				canvas.drawBitmap(seven, x+i*imgWidth, y, null);
			}else if (numbers[i]=='8'){
				canvas.drawBitmap(eight, x+i*imgWidth, y, null);
			}else if (numbers[i]=='9'){
				canvas.drawBitmap(nine, x+i*imgWidth, y, null);
			}else if (numbers[i]=='x'){
				canvas.drawBitmap(por, x+i*imgWidth, y, null);
			}else if (numbers[i]=='='){
				canvas.drawBitmap(equal, x+i*imgWidth, y, null);
			}
		}
		
	}
	

}
