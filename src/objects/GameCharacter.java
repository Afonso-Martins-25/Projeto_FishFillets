package objects;

import java.io.FileNotFoundException;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class GameCharacter extends GameObject {
	
	private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 10;
	
	public GameCharacter(Room room) {
		super(room);
	}
	
	protected boolean facingRight = true;
	public boolean hasExited = false;
	
	
	public void move(Vector2D dir) throws FileNotFoundException {
	    Point2D currentPos = getPosition();
	    Point2D destination = currentPos.plus(dir);

	    if (dir == null || room == null || isExited()) {
	        return;
	    }

	    if (dir.equals(Direction.RIGHT.asVector())) {
	        facingRight = true;
	    } else if (dir.equals(Direction.LEFT.asVector())) {
	        facingRight = false;
	    }

	    // Verifica se há objeto na posição destino
	    GameObject topObj = room.getTopObjectAt(destination);
	    

//	    if (topObj != null && topObj instanceof Pushable) {
//	        // Tenta empurrar
//	        if (!room.tryPushObjectAt(destination, dir, this)) {
//	            return; // Não conseguiu empurrar
//	        }
//	    } else if (topObj != null && !topObj.isPassable(this)) {
//	        return; // Não pode passar
//	    }
	    
	    if (topObj != null && topObj instanceof Pushable) {
	        // Se movimento é vertical, verifica carga acima
	        if (isVerticalMove(dir)) {
	            validateVerticalLoadOrDie(room);
	        }
	        // Tenta empurrar
	        if (!room.tryPushObjectAt(destination, dir, this)) {
	            return; // Não conseguiu empurrar
	            
	        } 
	    } else if (topObj != null && !topObj.isPassable(this)) {
		        return;
	    }

	    if (isOutOfBounds(destination)) {
	        setPosition(destination);
	        markAsExited();
	        room.getEngine().switchActiveFish();
	        room.checkLevelCompletion();
	        return;
	    }

	    setPosition(destination);
	 // só conta se mexeu
	    if (!currentPos.equals(destination)) {
	        room.incrementMoveCount();
	    }
	}
	
	//rever
	private boolean isVerticalMove(Vector2D dir) {
        return dir.equals(Direction.UP.asVector()) || dir.equals(Direction.DOWN.asVector());
    }

    private void validateVerticalLoadOrDie(Room room) {
        if (this instanceof SmallFish) {
            int leves = 0, pesados = 0;
            Point2D check = getPosition().plus(Direction.UP.asVector());
            while (check.getX() >= 0 && check.getX() < 10 && check.getY() >= 0 && check.getY() < 10) {
                GameObject obj = room.getTopObjectAt(check);
                if (obj instanceof MovableObjects) {
                    MovableObjects m = (MovableObjects) obj;
                    if (m.isHeavy()) pesados++; else leves++;
                    check = check.plus(Direction.UP.asVector());
                } else break;
            }
            if (pesados > 0 || leves > 1) {
                room.getEngine().restartCurrentLevel();
            }
        } else if (this instanceof BigFish) {
            int pesados = 0;
            Point2D check = getPosition().plus(Direction.UP.asVector());
            while (check.getX() >= 0 && check.getX() < 10 && check.getY() >= 0 && check.getY() < 10) {
                GameObject obj = room.getTopObjectAt(check);
                if (obj instanceof MovableObjects) {
                    MovableObjects m = (MovableObjects) obj;
                    if (m.isHeavy()) pesados++;
                    check = check.plus(Direction.UP.asVector());
                } else break;
            }
            if (pesados > 1) {
                room.getEngine().restartCurrentLevel();
            }
        }
    }
    
    //
	
	private boolean isOutOfBounds(Point2D pos) {
        if (pos == null) return true;
        int x = (int) pos.getX();
        int y = (int) pos.getY();
        return x < 0 || x >= GRID_WIDTH || y < 0 || y >= GRID_HEIGHT;
    }
	
	public void markAsExited() {
        this.hasExited = true;
    }
    
    public boolean isExited() {
        return hasExited;
    }
	

	@Override
	public int getLayer() {
		return 2;
	}
	
	@Override
	public boolean isPassable(GameObject passer) {
	    return false;
	}
	
}