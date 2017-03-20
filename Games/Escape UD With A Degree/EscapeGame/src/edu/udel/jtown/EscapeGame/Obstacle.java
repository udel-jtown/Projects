package edu.udel.jtown.EscapeGame;

import edu.udel.jatlas.gameframework.Position;

public abstract class Obstacle {
	
	private Position position;
	
	public Obstacle(Position position) {
		this.position = position;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public abstract char getSymbol();
	public abstract String toString();
	public abstract void onTick(EscapeGame game);
	public abstract boolean contain(Position p);
}

