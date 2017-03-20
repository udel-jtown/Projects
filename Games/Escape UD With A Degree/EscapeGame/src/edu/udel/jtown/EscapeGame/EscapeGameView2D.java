package edu.udel.jtown.EscapeGame;

import java.util.List;

import edu.udel.jatlas.gameframework.Game;
import edu.udel.jatlas.gameframework.Position;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.view.View;

public class EscapeGameView2D extends View {

	protected EscapeGameActivity activity;

	private Bitmap studentimage;
	private Bitmap wallimage;
	private Bitmap wallimage2;
	private Bitmap bulbimage;
	private Bitmap profimage;
	private Bitmap carimage;

	public EscapeGameView2D(EscapeGameActivity context) {
		super(context);
		activity = context;
		setBackgroundColor(Color.BLACK);
		setFocusable(true);
		setFocusableInTouchMode(true);
		studentimage = loadImage("student");
		wallimage = loadImage("wall");
		wallimage2 = loadImage("wallr");
		bulbimage = loadImage("bulb");
		profimage = loadImage("professor");
		carimage = loadImage("car");
	}

	private Bitmap loadImage(String name) {
		return BitmapFactory.decodeResource(activity.getResources(), 
				activity.getResources().getIdentifier(name, "drawable", getClass().getPackage().getName()));
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.save();
		canvas.scale(
				getWidth() / EscapeGame.BOARD_COLUMNS, 
				(getHeight() / EscapeGame.BOARD_ROWS));

		drawSides(canvas);
		drawStudent(canvas);
		drawWalls(canvas);
		drawObstacles(canvas);
		drawBulbs(canvas);


		canvas.restore();
	}

	// for performance only
	RectF rectF = new RectF();
	private void setRectFromPosition(Position position) {
		rectF.set(position.getColumn(), position.getRow(), position.getColumn()+1f, position.getRow()+1f);
	}

	public void drawStudent(Canvas canvas) {
		Student student = activity.getCurrentGame().getStudent();
		setRectFromPosition(student.getPosition());
		canvas.drawBitmap(studentimage, null, rectF, null);
	}

	public void drawBulbs(Canvas canvas) {
		for (Bulb b : activity.getCurrentGame().getBulbs()) {
			setRectFromPosition(b.getPosition());
			canvas.drawBitmap(bulbimage, null, rectF, null);
		}
	}

	public void drawSides(Canvas canvas) {
		Paint o = new Paint();
		o.setColor(Color.WHITE);
		o.setStrokeWidth(.05f);
		o.setStyle(Style.STROKE);

		for (Sides wall : activity.getCurrentGame().getSides()) {
			//if(Game.getTickId() == 0)
			for (Position p : wall) {
				setRectFromPosition(p);
				if (p.getRow() % 2 == 0) {
					if (activity.getCurrentGame().getTickId() % 2 == 0) {
						canvas.drawBitmap(wallimage, null, rectF, null);
					}
					else {
						canvas.drawBitmap(wallimage2, null, rectF, null);
					}
				}
				if (p.getRow() != 0) {
					if (activity.getCurrentGame().getTickId() % 2 == 0) {
						canvas.drawBitmap(wallimage2, null, rectF, null);
					}
					else {
						canvas.drawBitmap(wallimage, null, rectF, null);
					}
				}
			}
		}
	}

	public void drawObstacles(Canvas canvas) {
		for (Obstacle ob : activity.getCurrentGame().getObstacles()) {
			setRectFromPosition(ob.getPosition());
			if (ob.getSymbol() == 'P') {
				canvas.drawBitmap(profimage, null, rectF, null);
			}
			else if (ob.getSymbol() == 'C') {
				canvas.drawBitmap(carimage, null, rectF, null);
			}
		}
	}

	public void drawWalls(Canvas canvas) {
		for (WallSegment wallseg : activity.getCurrentGame().getWallSegments()) {
			for (Walls w : wallseg.getWallSegment()) {
				setRectFromPosition(w.getPosition());
				canvas.drawBitmap(wallimage, null, rectF, null);
			}
		}
	}
}
