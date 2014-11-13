package edu.virginia.cs.ghostrunner.entities;

import android.graphics.Canvas;
import edu.virginia.cs.ghostrunner.views.GameView;

public class Item extends Entity {
	protected int timer;
	
	public Item(float x, float y, GameView gameView) {
		super(x, y, gameView);
		this.timer = 0;
	}

	@Override
	public void draw(Canvas c) {
		// TODO Auto-generated method stub

	}
	public void intersected() {
		
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

}
