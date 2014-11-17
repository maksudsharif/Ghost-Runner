package edu.virginia.cs.ghostrunner.entities;

import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.DisplayMetrics;
import android.util.Log;
import edu.virginia.cs.ghostrunner.R;
import edu.virginia.cs.ghostrunner.views.GameView;

public class GhostFriend extends Item {
	
	private Player player;
	private ArrayList<Entity> ghosts;
	private DisplayMetrics dm;


	public GhostFriend(float x, float y, GameView gameView) {
		super(x, y, gameView);
		this.p = new Paint();
		this.p.setColor(Color.MAGENTA);
		this.p.setStyle(Style.FILL);
		
		bm = BitmapFactory.decodeResource(gameView.getContext().getResources(),
				R.drawable.ic_launcher);
	}
	@Override
	public void draw(Canvas c) {
		this.pos_y += (int) (gameView.getWidthPixels() * Entity.SPEED) * gameView.getGhostspeedconstant();
		this.rect.set((int) pos_x
				- (int) (gameView.getWidthPixels() * Entity.SCALE), (int) pos_y
				- (int) (gameView.getWidthPixels() * Entity.SCALE),
				(int) (gameView.getWidthPixels() * Entity.SCALE) + (int) pos_x,
				(int) (gameView.getWidthPixels() * Entity.SCALE) + (int) pos_y);

		 c.drawRect(this.rect, this.p);
		
//		c.drawBitmap(bm, (float) (pos_x - gameView.getWidthPixels()
//				* Entity.SCALE), (float) (pos_y - gameView.getWidthPixels()
//				* Entity.SCALE), p);

	}
	@Override
	public void intersected() {
		int n = 0;
		Rect playerRect;
		Iterator<Entity> iter = ghosts.iterator();
		while (iter.hasNext()) {
			Entity tmp = iter.next();
			playerRect = player.getRect();
			if (playerRect.intersect(tmp.getRect())) {
				iter.remove();
				Log.v("ENTITY", "ghost removed");
				++n;
			}
		}
		if(n != 0 ) {
			//ghost friend leaves
		}
	}
	
}
