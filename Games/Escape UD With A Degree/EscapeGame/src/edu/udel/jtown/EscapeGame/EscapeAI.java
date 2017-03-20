package edu.udel.jtown.EscapeGame;

import java.util.ArrayList;
import java.util.List;

import edu.udel.jatlas.gameframework.AI;
import edu.udel.jatlas.gameframework.Action;
import edu.udel.jatlas.gameframework.Position;


public class EscapeAI extends AI<EscapeGame>{
	

	    public EscapeAI() {
	        super("AI"); 
	    }

	    
	    /**
	     * Returns a list of all valid moves from a given state.
	     * 
	     * @param state
	     * @return
	     */
	    public List<Action<EscapeGame>> getAllValidActions(EscapeGame game) {
	        List<Action<EscapeGame>> validMoves = new ArrayList<Action<EscapeGame>>();
	        if (!game.isEnd()) {
	            // well a valid move is anything in Escape
	            // (might not be a *good* move though, as you could die on the next tick)
	          validMoves.add(new MoveStudent(Student.DIRECTION_DOWN));
	          validMoves.add(new MoveStudent(Student.DIRECTION_UP));
	          validMoves.add(new MoveStudent(Student.DIRECTION_RIGHT));
	          validMoves.add(new MoveStudent(Student.DIRECTION_LEFT));
	        }
	        return validMoves;
	    }
	
	    
	    public double getHeuristicScore(Action<EscapeGame> action, EscapeGame game) {
	        if (game.isEnd()) {
	            return -1;
	        }

	        MoveStudent m = (MoveStudent)action;
	        Student student = game.getStudent();
	        int leftorright = m.getDirection();
	        Position next = student.getNextPosition(leftorright);

	        
	        // Check if we will hit the side
	        if (game.isSide(next)) {
	            return 0;
	        }
	        // Check if we will hit an Wall
	        for (WallSegment walseg : game.getWallSegments()) {
	        	for (Walls wall : walseg.getWallSegment()) {
	        		if (wall.getPosition() == next) {
	        			return -1;
	        		}
	        	}
	        }
	        // Check if we hit an Obstacle
	        for (Obstacle ob : game.getObstacles()) {
	        	if (ob.getPosition() == next) {
	        		return -1;
	        	}
	        }
	        
	        int maxDistance = game.getRow() + game.getCol();
	        // ok return a score based on distance from the nearest bulb
	        // but prefer moving left/right first 
	        if(leftorright % 2 == 1) maxDistance += 1;	
	        int distance = 10000;
	        for (int i = 0; i < game.getBulbs().size(); i++) {
	        	int bulbposition = game.getBulbs().get(i).getPosition().blockDistance(next);
	        	if (bulbposition < distance) {
	        		distance = bulbposition;
	        	}
	        }
	        return maxDistance - distance;
	    }
	    
}
