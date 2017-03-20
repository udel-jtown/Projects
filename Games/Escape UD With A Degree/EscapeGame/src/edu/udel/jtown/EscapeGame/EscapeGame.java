package edu.udel.jtown.EscapeGame;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.udel.jatlas.gameframework.ConsoleListener;
import edu.udel.jatlas.gameframework.Game;
import edu.udel.jatlas.gameframework.JavaTicker;
import edu.udel.jatlas.gameframework.Position;


public class EscapeGame extends Game {

	// constants
	public static final long SPEED_START = 200;
	public static final float SPEED_INCREASE = .08f;
	private static final float SPEED_LEVEL_INCRAESE = 0.2f;
	public static final int CREDITS_START = 0;
	public static final int CREDITS_PER_LEVEL =30;
	public static final int BULBS_START = 0;
	public static final int BOARD_ROWS = 24;
	public static final int BOARD_COLUMNS = 16;

	// game objects
	private Student student;
	private List<Bulb> bulbs;
	private List<Sides> sides;
	private List<Obstacle> obstacles;
	private List<WallSegment> wallSegments;

	// board size
	private int row;
	private int col;

	// will change with level
	private long speed;

	//GPA IS COMPUTED USING THE SCORE
	// collisions with obstacles and 
	// lightbulbs that are not collected 
	// lowers the score.  
	// when a credit is colllected the score 
	// is used to compute GPA to 4.0 value
	private double gpa;

	// probably 4,freshman thru senior 
	private int level;
	private List<StudentLevel> levels;

	// One bulb is one credit; the value of a
	// credit towards GPA is connected to the score value
	private int credits;
	private int bulbScore;


	public EscapeGame(Student student, List<Bulb> bulbs, List<Sides> sides, 
			List<Obstacle> obstacles, List<WallSegment> walls, int row, int col, long speed, long gpa,
			int level, int credits, int bulbScore, int clock) {
		this.student = student;
		this.bulbs = bulbs;
		this.sides = sides;
		this.obstacles = obstacles;
		this.wallSegments = walls;
		this.row = row;
		this.col = col;
		this.speed = speed;
		this.gpa = gpa;
		this.speed = speed;
		this.level = level;
		this.credits = credits;
		this.bulbScore = bulbScore;

	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<Bulb> getBulbs() {
		return bulbs;
	}

	public List<Obstacle> getObstacles() {
		return obstacles;
	}

	public  List<WallSegment> getWallSegments() {
		return wallSegments;
	}


	public double getGpa() {
		return gpa;
	}
	
	public void setGpa(double gpa) {
		this.gpa = gpa;
	}
	
	public void addToGpa(int value)	{
		this.gpa = value;
	}

	public long getSpeed() {
		return speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<StudentLevel> getLevels() {
		return levels;
	}

	public void setLevels(List<StudentLevel> levels) {
		this.levels = levels;
	}

	public int getCredits() {
		return credits;
	}
	
	public void setCredits(int credits) {
		 this.credits += credits;
	}

	public int getBulbScore() {
		return bulbScore;
	}

	public void setBulbScore(int score) {
		this.bulbScore = score;
	}


	public long getRealTimeTickLength() {
		return getSpeed();
	}

	public List<Sides> getSides() {
		return sides;
	}

	public void setSides(List<Sides> sides) {
		this.sides = sides;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	// Char map of board
	public String toString() {
		char[][] grid = new char[row][col]; 
		for (char[] row : grid) {
			Arrays.fill(row, ' ');
		}
		for (Sides side : sides) {
			for (Position p : side) {
				grid[p.getRow()][p.getColumn()] = 'X';
			}
		}

		// place the student in the grid
		int sr = student.getPosition().getRow();
		int sc = student.getPosition().getColumn();
		grid[sr][sc] = 'S';


		// place the bulbs in the grid
		for (Bulb b : bulbs) {
			int br = b.getPosition().getRow();
			int bc = b.getPosition().getColumn();
			// "learned" bulbs will show up as an L
			grid[br][bc] = (grid[br][bc] == ' ') ? b.getSymbol() : 'L';
		}
		// build a string from the grid
		StringBuilder buffer = new StringBuilder();
		buffer.append(getStatus()).append("\n");
		for (char[] row : grid) {
			buffer.append(row);
			buffer.append('\n'); // new line
		}
		return buffer.toString();
	}


	public String getStatus() {
		return "Credits: " + getCredits() + " " + "GPA: " + getGpa()
				+ '\n' + "Tick " + this.getTickId();
	}


	/**
	 * Helper method that returns true if any of the given walls contain the
	 * given position.
	 * 
	 * @return
	 */
	public static boolean sidesContain(List<Sides> sides, Position p) {
		for (Sides side : sides) {
			if (side.contains(p)) {
				return true;
			}
		}
		return false;
	}

	public static boolean bulbsContain(List<Bulb> bulbs, Position p) {
		for (Bulb b : bulbs) {
			if (b.contain(p)) {
				return true;
			}
		}
		return false;
	}

	public static boolean obstaclesContain(List<Obstacle> obstacles, Position p) {
		for (Obstacle ob : obstacles) {
			if (ob.contain(p)) {
				return true;
			}
		}
		return false;
	}


	public static boolean segmentsContain(List<WallSegment> wallsegs, Position p) {
		for (WallSegment ws : wallsegs) {
			if (ws.contain(p)) {
				return true;
			}
		}
		return false;
	}

	public long speedcount = 1;
	public	int bulbcount = 1;
	public	int obcount = 1;
	public	int wallcount = 1;
	public void onTick() {

		if (this.getTickId() / speedcount == 200) {
			speedcount = speedcount + 2;
			setSpeed((long) ((float) getSpeed() * (1f - SPEED_INCREASE)));
		}
		if (this.getTickId() == 300 * bulbcount) {
			bulbcount = bulbcount + 2;
			addNewBulb();
		}
		if (this.getTickId() == 350 * obcount) {
			obcount = obcount + 2;
			addNewObstacle();
		}
		if (this.getTickId() == 800 * wallcount) {
			wallcount = wallcount + 2;
			addWallSegment();
		}
		getStudent().onTick(this);

		for (Obstacle ob : getObstacles()) {
			ob.onTick(this);
		}

		for (Bulb b : getBulbs()) {
			b.onTick(this);
		}

		for (WallSegment wallseg : getWallSegments()) {
			wallseg.onTick(this);
		}

		// Set Credit amount
		// If credits reaches credits per level, start next level
		if (credits >= CREDITS_PER_LEVEL * level) {
			nextLevel();
		}
		if (getCredits() % 15 == 0) setBulbScore(0);

	}

	public void nextLevel() {
		setLevel(getLevel() + 1);
		setSpeed((long) ((float) getSpeed() * (1f - SPEED_LEVEL_INCRAESE)));
		addNewObstacle();
		addWallSegment();
		addNewBulb();
		addNewBulb();
		broadcastEvent("next_level");
	}

	public boolean isSide(Position p) {
		return sidesContain(sides, p);
	}

	public static List<Sides> makeStartSides() {
		// make left and right walls
		ArrayList<Sides> sides = new ArrayList<Sides>();
		sides.add(new Sides(new Position(0,0), new Position(0, BOARD_ROWS - 1))); // left
		sides.add(new Sides(new Position(BOARD_COLUMNS - 1, 0), 
				new Position(BOARD_COLUMNS - 1, BOARD_ROWS - 1))); // right
		return sides;
	}

	public boolean samePosition(Position p1, Position p2) {
		return p1 == p2;
	}


	public static Student makeStartStudent() {
		Position begin = new Position(BOARD_COLUMNS / 2, BOARD_ROWS - 1);
		int startmove = Student.DIRECTION_UP;
		return new Student(begin, startmove);
	}

	public  void addScore(int score) {
		this.bulbScore += score;
	}

	public void addNewBulb() {
		Bulb bulb = null;
		bulb = makeRandomBulb(student, sides, bulbs);
		bulbs.add(bulb);
	}

	public static Bulb makeRandomBulb(Student student, List<Sides> sides, List<Bulb> bulbs) {
		// make 1 random bulb
		Bulb bulb = null;
		int row = (int)(Math.random() * 4);
		int col = 1 + (int)(Math.random() * (BOARD_COLUMNS));
		Position p = new Position(col, row);

		if (!sidesContain(sides, p) && !bulbsContain(bulbs, p)) {
			bulb = new Bulb(p);
		}
		else {
			bulb = makeRandomBulb(student, sides, bulbs);
		}
		return bulb;
	}

	public void addNewObstacle() {
		Obstacle ob = null;
		ob = makeRandomObstacle(student, sides, bulbs, wallSegments);
		obstacles.add(ob);
	}

	public static Professor makeRandomProfessor(Student student, List<Sides> sides,
			List<Bulb> bulbs, List<WallSegment> wallSegments) {
		Professor prof = null;
		int row = (int)(Math.random() * 2);
		int col = 1 + (int)(Math.random() * (BOARD_COLUMNS));
		Position p = new Position(col, row);

		if (!sidesContain(sides, p) && 
				student.getPosition() != p
				&& !bulbsContain(bulbs, p) 
				&& !segmentsContain(wallSegments, p)
				) {
			prof = new Professor(p);
		}
		else {
			prof =  makeRandomProfessor(student, sides, bulbs, wallSegments);
		}
		return prof;
	}

	public static Car makeRandomCar(Student student, List<Sides> sides,
			List<Bulb> bulbs, List<WallSegment> wallSegments) {
		Car car = null;
		int row = (int)(Math.random() * 2);
		int col = 1 + (int)(Math.random() * (BOARD_COLUMNS));
		Position p = new Position(col, row);
		if (!sidesContain(sides, p) && student.getPosition() != p
				&& !bulbsContain(bulbs, p) && !segmentsContain(wallSegments, p)
				) {
			car = new Car(p);
		}
		else {
			car = makeRandomCar(student, sides, bulbs, wallSegments);
		}
		return car;
	}

	public static Obstacle makeRandomObstacle(Student student, List<Sides> sides, List<Bulb> bulbs,
			List<WallSegment> wallsegments) {
		// Make one of each obstacle that is not a bulb
		Obstacle ob = (int) (Math.random() * 2) % 2 == 0 ? 
				makeRandomProfessor(student, sides, bulbs, wallsegments) : 
					makeRandomCar(student, sides, bulbs, wallsegments); 
				return ob;
	}

	public void addWallSegment() {
		wallSegments.add(makeRandomWallSegment(student, sides, bulbs));
	}

	public static Walls makeRandomWall(Student student, List<Sides> sides) {
		Walls wall = null;
		int row = 1;
		int col = 1 + (int)(Math.random() * (BOARD_COLUMNS));
		Position p = new Position(col, row);
		if (!sidesContain(sides, p) && student.getPosition() != p) {
			wall = new Walls(p);
		}
		else {
			wall = makeRandomWall(student, sides);
		}
		return wall;
	}

	public static WallSegment makeRandomWallSegment(Student student, List<Sides> sides,
			List<Bulb> bulbs) {
		WallSegment wallSeg = null;
		Walls wall = makeRandomWall(student, sides);
		Position p1 = new Position(wall.getPosition().getColumn() + 1, wall.getPosition().getY());
		Position p2 = new Position(wall.getPosition().getColumn() - 1, wall.getPosition().getY());
		if (!sidesContain(sides, p1) && !sidesContain(sides, p2) 
				&& !bulbsContain(bulbs, p1) && !bulbsContain(bulbs, p2) 
				&& student.getPosition() != p1 && student.getPosition() != p2) {
			wallSeg = new WallSegment(wall.getPosition(), new CopyOnWriteArrayList<Walls>());
			wallSeg.getWallSegment().add(wall);
			wallSeg.getWallSegment().add(new Walls(p2));
			wallSeg.getWallSegment().add(new Walls(p1));
		}
		else {
			wallSeg = makeRandomWallSegment(student, sides, bulbs);
		}
		return wallSeg;
	}

	public static EscapeGame makeDefaultStartGame() {
		Student student = makeStartStudent();
		List<Sides> sides = makeStartSides();
		List<Bulb> b = new CopyOnWriteArrayList<Bulb>();
		Bulb bulb1 = makeRandomBulb(student, sides, b);
		Bulb bulb2 = makeRandomBulb(student, sides, b);
		Bulb bulb3 = makeRandomBulb(student, sides, b);
		b.add(bulb1);
		b.add(bulb2);
		b.add(bulb3);
		List<WallSegment> wallseg = new CopyOnWriteArrayList<WallSegment>();
		wallseg.add(makeRandomWallSegment(student, sides, b));
		List<Obstacle> obstacles = new CopyOnWriteArrayList<Obstacle>();
		obstacles.add(makeRandomObstacle(student, sides, b, wallseg));
		obstacles.add(makeRandomObstacle(student, sides, b, wallseg));
		return new EscapeGame(student, b, sides, obstacles, wallseg, BOARD_ROWS, 
				BOARD_COLUMNS, SPEED_START, 
				0, 1, CREDITS_START, BULBS_START, 1000);
	}


	public static EscapeGame makeStartGame(Student student, List<Sides> sides, 
			List<Bulb> bulbs, List<Obstacle> obstacles, List<WallSegment> wallSegments) {
		return new EscapeGame(student, bulbs, sides, obstacles, 
				wallSegments, BOARD_ROWS, BOARD_COLUMNS, SPEED_START,
				0, 1, CREDITS_START, BULBS_START, 10000);
	}

	public boolean isEnd() {

		Position student = this.student.getPosition();
		List<Sides> sides = this.getSides();
		// check if student hit wall

		if (sidesContain(sides, student) || (getCredits() > 125)) {
			return true;
		}
		else {
			return false;
		}

	}

	public static void main(String[] args) {
		EscapeGame game = makeDefaultStartGame();
		game.addGameListener(new ConsoleListener());
		game.addGameListener(new EscapeAI());
		game.start(new JavaTicker());
	}

}
