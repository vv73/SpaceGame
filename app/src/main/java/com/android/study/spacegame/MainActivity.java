package com.android.study.spacegame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;

import com.android.study.spacegame.framework.GameView;
import com.android.study.spacegame.framework.Updatable;


public class MainActivity extends Activity  implements Updatable{

	GameView game;
	static Bitmap asteroidBmp, shipBmp, exp1Bmp, exp2Bmp;
	static TextView scoreTV;
	static int score = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		asteroidBmp = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid1);
		shipBmp = BitmapFactory.decodeResource(getResources(), R.drawable.aim);
		exp1Bmp = BitmapFactory.decodeResource(getResources(), R.drawable.explosion1);
		exp2Bmp = BitmapFactory.decodeResource(getResources(), R.drawable.explosion2);
		// Cоздаем GameView
		//game = new GameView(this);
		// Устанавливаем наш GameView на активность
		setContentView(R.layout.activity_main);
		game = (GameView)findViewById(R.id.game);
		scoreTV = (TextView) findViewById(R.id.score);
	}

	static void changeScore(int s){
		score += s;
		scoreTV.setText(score + "");
	}
	
	@Override
	public void onWindowFocusChanged (boolean hasFocus) {
		//добавляем себя в игру
		game.addObject(this);
		game.addObject(new Stars(game));
		game.addObject(new Aim(game.getWidth()/2, game.getHeight()/2, shipBmp));
}

    float lastTime = 0;
	@Override
	public void update(float deltaTime) {
		lastTime += deltaTime;
		if (lastTime > 3) {
			game.addObject(
					new Asteroid(asteroidBmp, game));
			lastTime = 0;
		}
		if (MainActivity.score < 0) {
			AlertDialog alert = new AlertDialog.Builder(this).create();
			alert.setMessage("You lose!!!");
			alert.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
			        game.timeScale = 0;
					alert.show();
		}
	}
}
