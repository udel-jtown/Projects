package edu.udel.jtown.EscapeGame;

import java.util.concurrent.CopyOnWriteArrayList;

import edu.udel.jatlas.gameframework.Position;



public class WallSegment extends Obstacle {
	private CopyOnWriteArrayList<Walls> wallSegment;

	
	public WallSegment(Position position, CopyOnWriteArrayList<Walls> wallSegment) {
		super(position);
		this.wallSegment = wallSegment;
	}
	
	public CopyOnWriteArrayList<Walls> getWallSegment() {
		return wallSegment;
	}

	@Override
	public char getSymbol() {
		return 'T';
	}
	
	public int getSize() {
		return wallSegment.size();
	}
	
	// Returns the Position of the "Middle" Wall of the Segment
		@Override
		public Position getPosition() {
			return wallSegment.get(1).getPosition();
		}

	@Override
	public String toString() {
		return "Wall Segment at " + this.getPosition();
	}
	
//	if (getWallSegments().get(0).size() >= 1) {
//	if (getWallSegments().get(0).get(0).getPosition().getRow() > BOARD_ROWS - 1) {
//		getWallSegments().clear();
//		getWallSegments().add(makeRandomWallSegment(student, sides));
//	}
//}
//else if (getWallSegments().get(0).size() == 0) {
//	List<List<Walls>> wallSeg = new CopyOnWriteArrayList<List<Walls>>();
//	wallSeg.add(makeRandomWallSegment(student, sides));
//	wallSegments = wallSeg;
//}
//	for (List<Walls> walls : getWallSegments()) {
//	if (walls.get(0).getPosition().getRow() > BOARD_ROWS - 1) {
//		getWallSegments().clear();
//		getWallSegments().add(makeRandomWallSegment(student, sides));
//		break;
//	}
//}

	@Override
	public void onTick(EscapeGame game) {
		if (getSize() > 0) {
			if (getPosition().getRow() > EscapeGame.BOARD_ROWS - 1) {
				game.getWallSegments().remove(this);
				game.getWallSegments().add(EscapeGame.makeRandomWallSegment(game.getStudent(), 
						game.getSides(), game.getBulbs()));
			}
		}
		else if (getSize() == 0) {
			WallSegment wallseg = EscapeGame.makeRandomWallSegment(game.getStudent(),
					game.getSides(), game.getBulbs());
		//	game.getWallSegments().remove(this);
			game.getWallSegments().add(wallseg);
		}
		for (Walls wall : wallSegment) {
			wall.onTick(game);
		}
	}
	
	@Override
	public boolean contain(Position p) {
		for (Walls wall : wallSegment) {
			if (wall.contain(p)) {
				return true;
			}
		}
		return false;
	}
	
}
