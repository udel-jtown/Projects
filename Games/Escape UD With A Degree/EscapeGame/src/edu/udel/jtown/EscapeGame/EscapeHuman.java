package edu.udel.jtown.EscapeGame;

import edu.udel.jatlas.gameframework.Position;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class EscapeHuman  implements View.OnTouchListener, View.OnKeyListener  {

	private EscapeGameActivity activity;

	public EscapeHuman(EscapeGameActivity activity) {
		this.activity = activity;
	}

	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		EscapeGame game = activity.getCurrentGame();

		if (game != null) {
			// is the game ended? if so restart it!
			if (game.isEnd()) {
				activity.restartGame();
			}

			else if (activity.getGameType() == EscapeGameActivity.GAMETYPE_HUMAN
					&& action == MotionEvent.ACTION_DOWN) {
				// where did they click on the board?
				int row = (int)((event.getY() / v.getHeight()) * 
						game.getRow());
				int col = (int)((event.getX() / v.getWidth()) * 
						game.getCol());

				// which direction does that indicate?
				Position student = game.getStudent().getPosition();
				int changeX = col - student.getColumn();
				int changeY = row - student.getRow();
				int direction = game.getStudent().getDirection();

				if((Math.abs(changeY) > Math.abs(changeX))) {
					direction = changeY > 0 ? Student.DIRECTION_DOWN : Student.DIRECTION_UP;
				}
				else if ((Math.abs(changeX) > Math.abs(changeY))) {
					direction = changeX > 0 ? Student.DIRECTION_RIGHT : Student.DIRECTION_LEFT;
				}
				
				game.perform(new MoveStudent(direction));
			}
		}
		return false;
	}



	public boolean onKey(View v, int keyCode, KeyEvent event) {
		int direction = activity.getCurrentGame().getStudent().getDirection();

		if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
			direction = Student.DIRECTION_UP;
		}
		else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
			direction = Student.DIRECTION_DOWN;
		}
		else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
			direction = Student.DIRECTION_LEFT;
		}
		else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
			direction = Student.DIRECTION_RIGHT;
		}

		if (direction != activity.getCurrentGame().getStudent().getDirection() &&
				direction != activity.getCurrentGame().getStudent().getOppositeDirection()) {
			activity.getCurrentGame().perform(new MoveStudent(direction));
		}

		return false;
	}

}

