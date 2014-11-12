package edu.virginia.cs.ghostrunner.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import edu.virginia.cs.ghostrunner.views.GameView;

public class AnimatedEntity extends Entity{
	private Bitmap bitmap;
	private Rect src;
	private int frameNum;
	private int curFrame;
	private long frameTick;
	private int framePeriod;

	private int spriteWidth;
	private int spriteHeight;

	private int x;
	private int y;

	public AnimatedEntity(Bitmap bitmap, int x, int y, int width, int height,
			int fps, int frameCount, GameView view) {
		super(x,y, view);
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		curFrame = 0;
		frameNum = frameCount;
		spriteWidth = bitmap.getWidth() / frameCount;
		spriteHeight =	bitmap.getHeight();
		src = new Rect(0, 0, spriteWidth, spriteHeight);
		framePeriod = 1000 / fps;
		frameTick = 0l;
	}

	public void update(long gameTime) {
		if (gameTime > frameTick + framePeriod) {
			frameTick = gameTime;

			curFrame++;
			if (curFrame >= frameNum) {
				curFrame = 0;
			}
		}
		src.left = curFrame * spriteWidth;
		src.right = src.left + spriteWidth;
	}

	public void draw(Canvas c) {
		rect.set(x, y, x + spriteWidth, y + spriteHeight);
		c.drawBitmap(bitmap,  src,  rect, null);
	}

}
