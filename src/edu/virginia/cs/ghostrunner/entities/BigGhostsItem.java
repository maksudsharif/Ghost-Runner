package edu.virginia.cs.ghostrunner.entities;

import edu.virginia.cs.ghostrunner.R;
import edu.virginia.cs.ghostrunner.views.GameView;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class BigGhostsItem extends Item {
	

	public BigGhostsItem(float x, float y, GameView gameView) {
		super(x, y, gameView);
		this.pos_x = (int) (Math.random() * gameView.getWidthPixels());
		this.pos_y = 0;
		this.timer = 1000;
		bm = BitmapFactory.decodeResource(gameView.getContext().getResources(),
				R.drawable.ic_launcher);
		
		this.p = new Paint();
		this.p.setColor(Color.YELLOW);
		this.p.setStyle(Style.FILL);
		this.p.setStrokeWidth(10);
	}

	@Override
	public void draw(Canvas c) {
		// For now, just update speed same as ghosts.
		this.pos_y += (int) (gameView.getWidthPixels() * Entity.SPEED) * gameView.getGhostspeedconstant();
		this.rect.set((int) pos_x
				- (int) (gameView.getWidthPixels() * Entity.SCALE), (int) pos_y
				- (int) (gameView.getWidthPixels() * Entity.SCALE),
				(int) (gameView.getWidthPixels() * Entity.SCALE) + (int) pos_x,
				(int) (gameView.getWidthPixels() * Entity.SCALE) + (int) pos_y);
		
		c.drawRect(this.rect, this.p);
	}
	@Override
	public void intersected() {
		//progressive implementation *note* can't remove
		Ghost.SCALE = 1.02 * Ghost.SCALE;
		// hard coded implementation
	//	Ghost.SCALE = .040;
	}

}
