package edu.udel.jtown.EscapeGame;

import java.util.Iterator;

import edu.udel.jatlas.gameframework.Action;
import edu.udel.jatlas.gameframework.Position;

public class MoveStudent implements Action<EscapeGame> {

	private int direction;

	public MoveStudent(int direction) {
		this.direction = direction;
	}

	public boolean isValid(EscapeGame state) {
		return true;
	}

	public void update(EscapeGame game) {
		Iterator<Bulb> itr = game.getBulbs().listIterator();
		while (itr.hasNext()) {
			Bulb b = itr.next();
			if (game.getStudent().getPosition().blockDistance(b.getPosition()) == 0) {
				game.addScore(1);
				game.getBulbs().remove(b);
				game.addNewBulb();
			}
		}
		Position newpos = game.getStudent().getNextPosition(direction);
		game.getStudent().setPosition(newpos);  
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	public String toString() {
		return "MoveStudent [direction=" + Student.DIRECTION_CHARS[direction] + "]";
	}

}
