package edu.udel.jtown.EscapeGame;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.udel.jatlas.gameframework.Position;


public class Bulb  {

	private Position position;
	public static final int BOARD_ROWS = 24;
	public static final int GET_BULB_SCORE = 1;

	public Bulb(Position position) {
		this.position = position;
	}


	public Position getPosition() {
		return position;
	}

	public void setBulb(Position position) {
		this.position = position;
	}

	public char getSymbol() {
		return 'B';
	}

	public String toString() {
		return "Bulb at " + position.toString();
	}

	public void onTick(EscapeGame game) {	
		List<Bulb> toRemove = new CopyOnWriteArrayList<Bulb>();
		if (this.getPosition().getRow() > BOARD_ROWS - 1) {
			toRemove.add(this);
			game.getBulbs().removeAll(toRemove);
			game.addNewBulb();
			game.addScore(-1);
		}
		int newrow = this.getPosition().getRow() + 1;
		Position newpos = new Position(this.position.getColumn(), newrow);
		this.setBulb(newpos);
	}



	public boolean contain(Position p) {
		return position == p;
	}
}

