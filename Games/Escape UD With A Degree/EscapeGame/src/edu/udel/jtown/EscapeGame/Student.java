package edu.udel.jtown.EscapeGame;


import java.util.Iterator;


import java.util.List;

import edu.udel.jatlas.gameframework.Position;

public class Student {
	
    public static final int DIRECTION_UP = 0;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_DOWN = 2;
    public static final int DIRECTION_LEFT = 3;
    public static final int MOVE_NO = 4;
    public static final char[] DIRECTION_CHARS = {'^','>','v','<', ' '};

	
    private Position position;
    private int direction;

    public Student(Position position, int direction) {
    	this.position = position;
    	this.direction = direction;
    }
    
    
	public Position getPosition() {
		return position;
	}	
	
	
	public void setPosition(Position newpos) {
		this.position = newpos;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	 public int getOppositeDirection() {
	        return (direction + 2) % 4;
	    }
	
	public void onTick(EscapeGame game) {
		Iterator<Bulb> bitr = game.getBulbs().listIterator();
		Iterator<Obstacle> oitr = game.getObstacles().listIterator();
		Position student = game.getStudent().getPosition();
		
		while (bitr.hasNext()) {
			Bulb b = bitr.next();
			if (student.blockDistance(b.getPosition()) == 0) {
				
				double gpa = game.getGpa();
				int score = game.getBulbScore();
				double grade = (4 - Math.abs(score * .1));
				game.setGpa((gpa + grade) / 2.0);
				
				game.setCredits(1); 
				game.getBulbs().remove(b);
				game.addNewBulb();
			}
		}
		while (oitr.hasNext()) {
			Obstacle ob = oitr.next();
			if (student.blockDistance(ob.getPosition()) == 0) {
				game.addScore(-1);
			}
		}
		
		for (WallSegment w : game.getWallSegments()) {
			for (Walls wall : w.getWallSegment()) {
				if (student.blockDistance(wall.getPosition()) == 0) {
					game.addScore(-5);
				}
			}
		}
		
		
//		for (Bulb b : game.getBulbs()) {
//			if (student.blockDistance(b.getPosition()) == 0) {
//				game.addScore(1);
//				toRemove.add(b);
//				toAdd.add(EscapeGame.makeRandomBulb(game.getStudent(), game.getSides()));
//			}
//		}
		
//		while (itr.hasNext()) {
//			Bulb b = itr.next();
//			if (student.blockDistance(b.getPosition()) == 0) {
//				game.addScore(1);
//				toRemove.add(b);
//				toAdd.add(EscapeGame.makeRandomBulb(game.getStudent(), game.getSides()));
//			}
//		}
//		
//		game.getBulbs().removeAll(toRemove);
//		game.getBulbs().addAll(toAdd);
	}
	
    public Position getNextPosition(int direction) {
        Position current = this.getPosition();
        Position next = current;
        
        if (direction == DIRECTION_UP) {
            next = new Position(current.getColumn(), current.getRow() - 1);
        }
        else if (direction == DIRECTION_RIGHT) {
            next = new Position(current.getColumn() + 1, current.getRow());
        }
        else if (direction == DIRECTION_DOWN) {
            next = new Position(current.getColumn(), current.getRow() + 1);
        }
        else if (direction == DIRECTION_LEFT) {
            next = new Position(current.getColumn() - 1, current.getRow());
        }
        
        return next;
    }
    
}
