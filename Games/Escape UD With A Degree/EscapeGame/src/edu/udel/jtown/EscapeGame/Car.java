package edu.udel.jtown.EscapeGame;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.udel.jatlas.gameframework.Position;

public class Car extends Obstacle {

	public Car(Position position) {
		super(position);
	}

	@Override
	public char getSymbol() {
		return 'C';
	}

	@Override
	public String toString() {
		return "Car at " + this.getPosition();
	}

	@Override
	public void onTick(EscapeGame game) {
		List<Car> toRemove = new CopyOnWriteArrayList<Car>();
		if (this.getPosition().getRow() > EscapeGame.BOARD_ROWS - 1) {
			toRemove.add(this);
			game.getObstacles().removeAll(toRemove);
			game.addNewObstacle();
		}
		int newrow = this.getPosition().getRow() + 1;
		Position newpos = new Position(this.getPosition().getColumn(), newrow);
		this.setPosition(newpos);
	}

	@Override
	public boolean contain(Position p) {
		return getPosition() == p;
	}

}
