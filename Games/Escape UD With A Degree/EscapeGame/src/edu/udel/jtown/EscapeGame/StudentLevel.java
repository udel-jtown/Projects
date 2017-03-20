package edu.udel.jtown.EscapeGame;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.udel.jatlas.gameframework.Position;


public class StudentLevel {
	 private List<Sides> sides;
	    
	    
	    public StudentLevel(List<Sides> sides) {
	        this.sides = sides;
	    }

	    /**
	     * Mutates the game so that the game now is at the starting
	     * state for this level.
	     * 
	     * @param game
	     */
	    public void setGameToStartOfLevel(EscapeGame game) {
	        game.setSides(sides);
	        Student student = EscapeGame.makeStartStudent();
	        game.setStudent(student);
	        game.addNewBulb();
	    }
	    
	    public static StudentLevel loadFromStream(InputStream stream, int level) {
	        Scanner scanner = new Scanner(stream);
	        scanner.useDelimiter("\n");
	        // format for file:
	        // has WORLD_HEIGHT lines
	        // each line has WORLD_WIDTH entries
	        //   each entry is one of:
	        //   'W' is wall, ' ' is empty
	        List<Sides> walls = new ArrayList<Sides>();
	        for (int row = 0; row < EscapeGame.BOARD_ROWS; row++) {
	            String line = scanner.next();
	            char[] encoded = line.toCharArray();
	            for (int i = 0; i < encoded.length; i++) {
	                if (encoded[i] == 'W') {
	                    // this is somewhat inefficient (it creates a Wall for each W), but will work
	                    Position p = new Position(i, row);
	                    walls.add(new Sides(p, p));
	                }
	            }
	        }
	        return new StudentLevel(walls);
	    }

}
