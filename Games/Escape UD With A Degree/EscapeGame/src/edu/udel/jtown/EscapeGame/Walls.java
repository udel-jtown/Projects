package edu.udel.jtown.EscapeGame;

import edu.udel.jatlas.gameframework.Position;

public class Walls extends Obstacle {

	public Walls(Position position) {
		super(position);
	}
	
	@Override
	public char getSymbol() {
		return 'W';
	}

	@Override
	public String toString() {
		return "wall at " + getPosition().toString();
	}

	@Override
	public void onTick(EscapeGame game) {
		int newrow = this.getPosition().getRow() + 1;
		Position newpos = new Position(this.getPosition().getColumn(), newrow);
		this.setPosition(newpos);
	}
	
	@Override
	public boolean contain(Position p) {
		return getPosition() == p;
	}

}
