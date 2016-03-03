package com.mavi.bubblegame;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class GroundView extends SurfaceView implements SurfaceHolder.Callback{

	private Bitmap icon = null;
	private Bitmap background = null;
	private Bitmap background_scaled = null;
	private Bitmap back_score = null;
	private Bitmap back_score_scaled = null;
	private int width = 0;
	private int height = 0;
	private int conteoInicial = 0;
	public DrawThread thread;
	public BubbleList bubbles = null;
	public SeaUrchin urchin = null;
	private Random r, r2;
	public Timer timer;
	public int countBubbles = 0;
	public int countBubblesTutorial = 0;
	public Paint sText = null;
	public Paint scoreText = null;
	public Paint backText = null;
	public long timerP = 2000;
	public long timerD = 2000;
	public TimerTask tarea;
	public int limit = 1;
	private Bitmap[] simpleBubbleExplosion;
	private Bitmap[] starBubbleExplosion;
	private Bitmap[] skullBubbleExplosion;
	private Bitmap[] sickUrchin;
	private Bitmap[] happyUrchin;
	private Bitmap heart;
	private Bitmap deadUrchin;
	private Bitmap loneBub;
	private Bitmap starBub;
	private Bitmap x;
	private Bitmap equal;
	private Bitmap finalScore;
	private Bitmap goBack;
	private Bitmap msgTutorial1;
	private Bitmap msgTutorial2;
	private Bitmap msgTutorial3;
	private Bitmap msgTutorial4;
	private Bitmap msgTutorial5;
	private Bitmap arrow_up;
	private Bitmap arrow_down;
	private Bitmap arrow_left;
	private Bitmap arrow_right;
	private BubbleText numbers;
	private BubbleText bigNumbers;
	private BubbleText starNumbers;
	private BubbleText finalNumbers;
	private boolean tutorial;

	public GroundView(Context c){
		super(c);
		getHolder().addCallback(this);
		thread = new DrawThread(getHolder(),this);
		Display display = ((WindowManager)c.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		icon = BitmapFactory.decodeResource(getResources(), R.drawable.erizo_proto_3);
		heart = BitmapFactory.decodeResource(getResources(), R.drawable.corazon);
		bubbles = new BubbleList();
		urchin = new SeaUrchin(icon,width,height);
		deadUrchin = BitmapFactory.decodeResource(getResources(), R.drawable.erizo_muerto_proto1);
		loneBub = BitmapFactory.decodeResource(getResources(), R.drawable.burbuja_sola);
		starBub = BitmapFactory.decodeResource(getResources(), R.drawable.estrella_burbuja);
		x = BitmapFactory.decodeResource(getResources(), R.drawable.x_small);
		equal = BitmapFactory.decodeResource(getResources(), R.drawable.igual_small);
		finalScore = BitmapFactory.decodeResource(getResources(), R.drawable.final_score_label);
		goBack = BitmapFactory.decodeResource(getResources(), R.drawable.go_back_label);

		bubbleExplosionPics();
		numberBubblesInit();
		r = new Random(System.currentTimeMillis());
		r2 = new Random(System.currentTimeMillis()+20);
		Typeface fontST = Typeface.createFromAsset(getContext().getAssets(), "PWBubbles.ttf");
		sText = new Paint();
		sText.setStyle(Paint.Style.FILL);
		sText.setStrokeCap(Paint.Cap.ROUND);
		sText.setStrokeJoin(Paint.Join.ROUND);
		sText.setStrokeWidth(1.0f);
		sText.setColor(Color.GREEN);
		sText.setTextSize(20);
		sText.setTextAlign(Paint.Align.LEFT);
		sText.setTypeface(fontST);
		scoreText = new Paint();
		scoreText.setStyle(Paint.Style.FILL);
		scoreText.setStrokeCap(Paint.Cap.BUTT);
		scoreText.setStrokeJoin(Paint.Join.MITER);
		scoreText.setStrokeWidth(2.0f);
		scoreText.setColor(Color.YELLOW);
		scoreText.setTextSize(24);
		scoreText.setTextAlign(Paint.Align.LEFT);
		scoreText.setTypeface(fontST);
		backText = new Paint();
		backText.setStyle(Paint.Style.FILL);
		backText.setStrokeCap(Paint.Cap.ROUND);
		backText.setStrokeJoin(Paint.Join.ROUND);
		backText.setStrokeWidth(1.0f);
		backText.setColor(Color.RED);
		backText.setTextSize(12);
		backText.setTextAlign(Paint.Align.LEFT);
		backText.setTypeface(fontST);

	}
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		background = BitmapFactory.decodeResource(getResources(),R.drawable.fondo_2_efecto);
		background_scaled = Bitmap.createScaledBitmap(background, width, height, true);
		back_score = BitmapFactory.decodeResource(getResources(),R.drawable.fondo_score_juego2);
		back_score_scaled = Bitmap.createScaledBitmap(back_score, width, height, true);
		thread.setRunning(true);
		thread.start();
		setWillNotDraw(false);
		if(tutorial){
			bubbleTimeTutorial(10000);
			msgTutorial1 = BitmapFactory.decodeResource(getResources(),R.drawable.move_urchin_tutorial);
			msgTutorial2 = BitmapFactory.decodeResource(getResources(),R.drawable.lone_bubble_tutorial);
			msgTutorial3 = BitmapFactory.decodeResource(getResources(),R.drawable.star_bubble_tutorial);
			msgTutorial4 = BitmapFactory.decodeResource(getResources(),R.drawable.skull_bubble_tutorial);
			msgTutorial5 = BitmapFactory.decodeResource(getResources(),R.drawable.end_tutorial);
			arrow_up = BitmapFactory.decodeResource(getResources(),R.drawable.go_up_arrow);
			arrow_down = BitmapFactory.decodeResource(getResources(),R.drawable.go_down_arrow);
			arrow_left = BitmapFactory.decodeResource(getResources(),R.drawable.go_left_arrow);
			arrow_right = BitmapFactory.decodeResource(getResources(),R.drawable.go_right_arrow);
		}else{
			bubbleTime(timerD, timerP);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.v("surfaceDestroyed","se borro el fondo :O");
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawColor(0XFFAAAAAA);
		canvas.drawBitmap(background_scaled, 0, 0, null);
		canvas.drawBitmap(urchin.pic, urchin.x, urchin.y, null);
		bubbles.drawBubbles(canvas);
		//		Canvas canvasText = new Canvas();
		//canvas.drawText("Score: "+Integer.toString(bubbles.score), 10, 20, sText);
		canvas.drawBitmap(heart, width-45, 5, null);
		//canvas.drawText(Integer.toString(bubbles.lives), width-25, 20, sText);
		numbers.drawNumbers(canvas, Integer.toString(bubbles.lives), width-30, 5);
		if(tutorial){
			if(conteoInicial >= 1 && conteoInicial <=3){
				switch(conteoInicial){
				case 1:
					bigNumbers.drawNumbers(canvas, Integer.toString(3), (width/2)-(width/15), (height/2)-(height/15));
					break;
				case 2:
					starNumbers.drawNumbers(canvas, Integer.toString(2), (width/2)-(width/15), (height/2)-(height/15));
					break;
				case 3:
					finalNumbers.drawNumbers(canvas, Integer.toString(1), (width/2)-(width/15), (height/2)-(height/15));
					break;
				default:
					break;
				}
			}
			
			switch(countBubblesTutorial){
			case 0:
				//draw how to move the device to move the urchin
				canvas.drawBitmap(arrow_down, (width/2)-50, height-50, null);
				canvas.drawBitmap(arrow_up, (width/2)-50, 0, null);
				canvas.drawBitmap(arrow_left, 0, (height/2)-25, null);
				canvas.drawBitmap(arrow_right, width-100, (height/2)-25 , null);
				canvas.drawBitmap(msgTutorial1, width/15, (height*5)/8, null);
				break;
			case 1:
				//draw how to explode the bubble and get 10 points
				canvas.drawBitmap(msgTutorial2, width/10, (height*5)/8, null);
				break;
			case 2:
				//draw how to explode the star/bubble and get 20 points
				canvas.drawBitmap(msgTutorial3, width/10, (height*5)/8, null);
				break;
			case 3:
				//draw how not to explode the skull/bubble and lose a live
				canvas.drawBitmap(msgTutorial4, width/10, (height*5)/8, null);
				break;
			default:
				canvas.drawBitmap(msgTutorial5, width/10, (height*5)/8, null);
			}
		}else{
			if(conteoInicial >= 1 && conteoInicial <=3){
				switch(conteoInicial){
				case 1:
					bigNumbers.drawNumbers(canvas, Integer.toString(3), (width/2)-(width/15), (height/2)-(height/15));
					break;
				case 2:
					starNumbers.drawNumbers(canvas, Integer.toString(2), (width/2)-(width/15), (height/2)-(height/15));
					break;
				case 3:
					finalNumbers.drawNumbers(canvas, Integer.toString(1), (width/2)-(width/15), (height/2)-(height/15));
					break;
				default:
					break;
				}
			}

			if(bubbles.lives==0){
				canvas.drawBitmap(back_score_scaled, 0, 0, null);
				canvas.drawBitmap(loneBub, width/7, height/8, null);
				canvas.drawBitmap(starBub, width/7, (height*4)/10, null);
				bigNumbers.drawNumbers(canvas, Integer.toString(bubbles.lones)+"x10="+Integer.toString(bubbles.lones*10), width/7+loneBub.getWidth(), height/8);
				starNumbers.drawNumbers(canvas, Integer.toString(bubbles.stars)+"x20="+Integer.toString(bubbles.stars*20), width/7+starBub.getWidth(), (height*4)/10);
				canvas.drawBitmap(finalScore, width/15, (height*11)/20, null);
				finalNumbers.drawNumbers(canvas, Integer.toString(bubbles.score), width/18+finalScore.getWidth(), (height*12)/20);
				canvas.drawBitmap(goBack, width/15, (height*6)/8, null);
			}
		}
	}

	public void isTutorial(boolean tutorial){
		this.tutorial = tutorial;
	}

	public void updateMe(float inx, float iny, float fric){
		urchin.moveUrchin(inx, iny, fric);
		bubbles.moveBubbles();
		if(tutorial){
			//Log.v("updateMe", "Is list empty? "+String.valueOf(bubbles.emptyBList()));
			bubbles.checkCollision(urchin);
			bubbles.checkRemoveSimple(simpleBubbleExplosion);
			bubbles.checkRemoveStar(starBubbleExplosion);
			bubbles.checkRemoveSkull(skullBubbleExplosion);
			if(bubbles.score==10 && bubbles.emptyBList() && bubbles.lives!=0 && countBubblesTutorial==1){
				IdBubble id = createIDTutorial();
				Bubble bub1 = new Bubble(id,width,height,r);
				bubbles.addBubble(bub1);
			}else {
				if (bubbles.score==30 && bubbles.emptyBList() && bubbles.lives!=0 && countBubblesTutorial==2){
					IdBubble id = createIDTutorial();
					Bubble bub1 = new Bubble(id,width,height,r);
					bubbles.addBubble(bub1);
				}else{ 
					if(bubbles.lives == 9 || bubbles.emptyBList() &&  countBubblesTutorial == 3){
						bubbles.lives = 0;
						countBubblesTutorial++;
					}
				}
			}
			if(urchin.sick){
				urchin.checkSickUrchin(sickUrchin);
			}
			else{ 
				if(urchin.happy){
					urchin.checkHappyUrchin(happyUrchin);
				}
			}
		}else{
			if(bubbles.lives>0){
				bubbles.checkCollision(urchin);
				bubbles.checkRemoveSimple(simpleBubbleExplosion);
				bubbles.checkRemoveStar(starBubbleExplosion);
				bubbles.checkRemoveSkull(skullBubbleExplosion);
				if(urchin.sick){
					urchin.checkSickUrchin(sickUrchin);
				}
				else{ 
					if(urchin.happy){
						urchin.checkHappyUrchin(happyUrchin);
					}
				}
			} else {
				urchin.pic=deadUrchin;
				bubbles.checkRemoveSimple(simpleBubbleExplosion);
				bubbles.checkRemoveStar(starBubbleExplosion);
				bubbles.checkRemoveSkull(skullBubbleExplosion);
			}
			//setWillNotDraw(false);
			if(bubbles.score<300){
				changeTime();
			}
		}
		invalidate();
	}

	public void drawStartNumbers(int i){
		conteoInicial = i;
		invalidate();
	}

	public IdBubble createID(){
		countBubbles += 1; 
		int typeNum = probNum();
		String type="";
		int num = 0;
		Bitmap pic = null;
		switch (typeNum){
		case 0:
			type = "lone";
			num = countBubbles; 
			pic = BitmapFactory.decodeResource(getResources(), R.drawable.burbuja_sola);
			break;
		case 1:
			type = "star";
			num = countBubbles;
			pic = BitmapFactory.decodeResource(getResources(), R.drawable.estrella_burbuja);		
			break;
		case 2:
			type = "skull";
			num = countBubbles;
			pic = BitmapFactory.decodeResource(getResources(), R.drawable.calavera_burbuja);
			break;
		}
		IdBubble bub1 = new IdBubble(type, num, pic);
		return bub1;
	}

	public IdBubble createIDTutorial(){
		int typeNum = countBubbles;
		countBubbles += 1; 
		countBubblesTutorial = countBubbles;
		String type="";
		int num = 0;
		Bitmap pic = null;
		switch (typeNum){
		case 0:
			type = "lone";
			num = countBubbles; 
			pic = BitmapFactory.decodeResource(getResources(), R.drawable.burbuja_sola);
			break;
		case 1:
			type = "star";
			num = countBubbles;
			pic = BitmapFactory.decodeResource(getResources(), R.drawable.estrella_burbuja);		
			break;
		case 2:
			type = "skull";
			num = countBubbles;
			pic = BitmapFactory.decodeResource(getResources(), R.drawable.calavera_burbuja);
			break;
		}
		IdBubble bub1 = new IdBubble(type, num, pic);
		return bub1;
	}

	public void bubbleTime(long delay, long period){
		tarea = new BubbleTask();			
		timer = new Timer();
		timer.schedule(tarea, delay, period);
	}

	public void bubbleTimeTutorial(long delay){
		tarea = new BubbleTaskTutorial();
		timer = new Timer();
		timer.schedule(tarea, delay);
		Log.v("bubbleTimeTutorial", "created first timer with delay "+String.valueOf(delay));
	}

	private void bubbleExplosionPics(){
		simpleBubbleExplosion = new Bitmap[5];
		simpleBubbleExplosion[0] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_burbuja_sola20001);
		simpleBubbleExplosion[1] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_burbuja_sola20002);
		simpleBubbleExplosion[2] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_burbuja_sola20003);
		simpleBubbleExplosion[3] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_burbuja_sola20004);
		simpleBubbleExplosion[4] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_burbuja_sola20005);
		starBubbleExplosion = new Bitmap[10];
		starBubbleExplosion[0] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_estrella0001);
		starBubbleExplosion[1] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_estrella0002);
		starBubbleExplosion[2] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_estrella0003);
		starBubbleExplosion[3] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_estrella0004);
		starBubbleExplosion[4] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_estrella0005);
		starBubbleExplosion[5] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_estrella0006);
		starBubbleExplosion[6] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_estrella0007);
		starBubbleExplosion[7] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_estrella0008);
		starBubbleExplosion[8] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_estrella0009);
		starBubbleExplosion[9] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_estrella0010);
		skullBubbleExplosion = new Bitmap[10];
		skullBubbleExplosion[0] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_calavera0001);
		skullBubbleExplosion[1] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_calavera0002);
		skullBubbleExplosion[2] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_calavera0003);
		skullBubbleExplosion[3] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_calavera0004);
		skullBubbleExplosion[4] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_calavera0005);
		skullBubbleExplosion[5] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_calavera0006);
		skullBubbleExplosion[6] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_calavera0007);
		skullBubbleExplosion[7] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_calavera0008);
		skullBubbleExplosion[8] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_calavera0009);
		skullBubbleExplosion[9] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_calavera0010);
		sickUrchin = new Bitmap[6];
		sickUrchin[0] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_erizo_enfermo0001);
		sickUrchin[1] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_erizo_enfermo0002);
		sickUrchin[2] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_erizo_enfermo0003);
		sickUrchin[3] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_erizo_enfermo0004);
		sickUrchin[4] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_erizo_enfermo0005);
		sickUrchin[5] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_erizo_enfermo0006);
		happyUrchin = new Bitmap[7];
		happyUrchin[0] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_erizo_feliz0001);
		happyUrchin[1] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_erizo_feliz0002);
		happyUrchin[2] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_erizo_feliz0003);
		happyUrchin[3] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_erizo_feliz0004);
		happyUrchin[4] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_erizo_feliz0005);
		happyUrchin[5] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_erizo_feliz0006);
		happyUrchin[6] = BitmapFactory.decodeResource(getResources(), R.drawable.animacion_erizo_feliz0007);
	}

	public void numberBubblesInit(){
		Bitmap one = BitmapFactory.decodeResource(getResources(), R.drawable.small_1);
		Bitmap two = BitmapFactory.decodeResource(getResources(), R.drawable.small_2);
		Bitmap three = BitmapFactory.decodeResource(getResources(), R.drawable.small_3);
		Bitmap four = BitmapFactory.decodeResource(getResources(), R.drawable.small_4);
		Bitmap five = BitmapFactory.decodeResource(getResources(), R.drawable.small_5);
		Bitmap six = BitmapFactory.decodeResource(getResources(), R.drawable.small_6);
		Bitmap seven = BitmapFactory.decodeResource(getResources(), R.drawable.small_7);
		Bitmap eight = BitmapFactory.decodeResource(getResources(), R.drawable.small_8);
		Bitmap nine = BitmapFactory.decodeResource(getResources(), R.drawable.small_9);
		Bitmap zero = BitmapFactory.decodeResource(getResources(), R.drawable.small_0);
		x = BitmapFactory.decodeResource(getResources(), R.drawable.x_small);
		equal = BitmapFactory.decodeResource(getResources(), R.drawable.igual_small);

		numbers = new BubbleText(one,two,three,four,five,six,seven,eight,nine,zero,x,equal);

		Bitmap one_big = BitmapFactory.decodeResource(getResources(), R.drawable.medium_1);
		Bitmap two_big = BitmapFactory.decodeResource(getResources(), R.drawable.medium_2);
		Bitmap three_big = BitmapFactory.decodeResource(getResources(), R.drawable.medium_3);
		Bitmap four_big = BitmapFactory.decodeResource(getResources(), R.drawable.medium_4);
		Bitmap five_big = BitmapFactory.decodeResource(getResources(), R.drawable.medium_5);
		Bitmap six_big = BitmapFactory.decodeResource(getResources(), R.drawable.medium_6);
		Bitmap seven_big = BitmapFactory.decodeResource(getResources(), R.drawable.medium_7);
		Bitmap eight_big = BitmapFactory.decodeResource(getResources(), R.drawable.medium_8);
		Bitmap nine_big = BitmapFactory.decodeResource(getResources(), R.drawable.medium_9);
		Bitmap zero_big = BitmapFactory.decodeResource(getResources(), R.drawable.medium_0);
		Bitmap x_big = BitmapFactory.decodeResource(getResources(), R.drawable.medium_x);
		Bitmap equal_big = BitmapFactory.decodeResource(getResources(), R.drawable.medium_igual);

		bigNumbers = new BubbleText(one_big,two_big,three_big,four_big,five_big,six_big,seven_big,eight_big,nine_big,zero_big,x_big,equal_big);

		Bitmap one_star = BitmapFactory.decodeResource(getResources(), R.drawable.star_1);
		Bitmap two_star = BitmapFactory.decodeResource(getResources(), R.drawable.star_2);
		Bitmap three_star = BitmapFactory.decodeResource(getResources(), R.drawable.star_3);
		Bitmap four_star = BitmapFactory.decodeResource(getResources(), R.drawable.star_4);
		Bitmap five_star = BitmapFactory.decodeResource(getResources(), R.drawable.star_5);
		Bitmap six_star = BitmapFactory.decodeResource(getResources(), R.drawable.star_6);
		Bitmap seven_star = BitmapFactory.decodeResource(getResources(), R.drawable.star_7);
		Bitmap eight_star = BitmapFactory.decodeResource(getResources(), R.drawable.star_8);
		Bitmap nine_star = BitmapFactory.decodeResource(getResources(), R.drawable.star_9);
		Bitmap zero_star = BitmapFactory.decodeResource(getResources(), R.drawable.star_0);
		Bitmap x_star = BitmapFactory.decodeResource(getResources(), R.drawable.star_x);
		Bitmap equal_star = BitmapFactory.decodeResource(getResources(), R.drawable.star_igual);

		starNumbers = new BubbleText(one_star,two_star,three_star,four_star,five_star,six_star,seven_star,eight_star,nine_star,zero_star,x_star,equal_star);

		Bitmap one_final = BitmapFactory.decodeResource(getResources(), R.drawable.final_1);
		Bitmap two_final = BitmapFactory.decodeResource(getResources(), R.drawable.final_2);
		Bitmap three_final = BitmapFactory.decodeResource(getResources(), R.drawable.final_3);
		Bitmap four_final = BitmapFactory.decodeResource(getResources(), R.drawable.final_4);
		Bitmap five_final = BitmapFactory.decodeResource(getResources(), R.drawable.final_5);
		Bitmap six_final = BitmapFactory.decodeResource(getResources(), R.drawable.final_6);
		Bitmap seven_final = BitmapFactory.decodeResource(getResources(), R.drawable.final_7);
		Bitmap eight_final = BitmapFactory.decodeResource(getResources(), R.drawable.final_8);
		Bitmap nine_final = BitmapFactory.decodeResource(getResources(), R.drawable.final_9);
		Bitmap zero_final = BitmapFactory.decodeResource(getResources(), R.drawable.final_0);
		Bitmap x_final = BitmapFactory.decodeResource(getResources(), R.drawable.final_x);
		Bitmap equal_final = BitmapFactory.decodeResource(getResources(), R.drawable.final_igual);

		finalNumbers = new BubbleText(one_final,two_final,three_final,four_final,five_final,six_final,seven_final,eight_final,nine_final,zero_final,x_final,equal_final);

	}

	public int probNum(){
		int rand = r2.nextInt(10);
		int x = 4;
		if (rand < 5){
			x = 0;
		} else {
			if (rand < 7){
				x = 1;
			}
			else {
				x = 2;
			}
		}
		return x;
	}

	public void changeTime(){
		if (bubbles.score > 100*limit){
			tarea.cancel();
			timerP = timerP/2;
			tarea = new BubbleTask(); 
			timer.schedule(tarea, timerD, timerP);
			limit++;
		}
	}

	class BubbleTask extends TimerTask{
		@Override
		public void run(){
			IdBubble id = createID();
			Bubble bub1 = new Bubble(id,width,height,r);
			bubbles.addBubble(bub1);
		}
	}

	class BubbleTaskTutorial extends TimerTask{
		@Override
		public void run(){
			Log.v("BubbleTaskTutorial", "First bubble created");
			IdBubble id = createIDTutorial();
			Bubble bub1 = new Bubble(id,width,height,r);
			Log.v("BubbleTaskTutorial", "First bubble created: id type: "+ String.valueOf(bub1.pic.getHeight()));
			bubbles.addBubble(bub1);
			Log.v("BubbleTaskTutorial", "First bubble added: id type: "+ String.valueOf(bubbles.first.pic.getHeight()));
		}
	}
}
