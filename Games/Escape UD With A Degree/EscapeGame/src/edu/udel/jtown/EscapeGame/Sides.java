package edu.udel.jtown.EscapeGame;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.udel.jatlas.gameframework.Position;

public class Sides implements Iterable<Position> {

	// These are the vertical Sides
	// graphic on both sides will reverse on clock
	// to simulate forward motion
	
	private Position top;
    private Position bottom;
    private Set<Position> allPositions;
    private boolean parity;

    
    public Sides(Position top, Position bottom) {
        this.allPositions = new HashSet<Position>();
            // vertical Sides
            int c = top.getColumn();
            int bottom_r = bottom.getRow();    
            for (int r = top.getRow(); r <= bottom_r; r++) {
                allPositions.add(new Position(c, r));   
        }
        this.top = top;
        this.bottom = bottom;
    }
    
    public boolean getParity() {
    	return this.parity;
    }
    
    
	public void onTick(EscapeGame game) {
		parity = (game.getLastTickTime() % 2 == 0); 
	}
    
  
     // Returns true if the Sides contains the position.
    public boolean contains(Position p) {
        return allPositions.contains(p);
    }

    public String toString() {
        return "Sides [top=" + top + ", bottom=" + bottom + "]";
    }

    public Iterator<Position> iterator() {
        return allPositions.iterator();
    }
	
}
