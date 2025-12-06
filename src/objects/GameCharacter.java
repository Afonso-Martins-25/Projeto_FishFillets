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
	    GameObject dest = room.getTopObjectAt(destination);
	    
//	    if (dest != null && dest instanceof Pushable) {
//	    	
//	        // Tenta empurrar
//	        if (!room.tryPushObjectAt(destination, dir, this)) {
//	            return; // Não conseguiu empurrar
//	            
//	        } 
//	    } else if (dest != null && !dest.isPassable(this)) {
//		        return;
//	    }
	    
	    //4/12
	    //alterei a ordem checar pushable primeiro (armadilha)
	    if (dest != null) {
	        if (!dest.isPassable(this)) {
	            // Só tenta empurrar se não for passável
	            if (dest instanceof Pushable) {
	                if (!room.tryPushObjectAt(destination, dir, this)) {
	                    return; // Não conseguiu empurrar
	                }
	            } else {
	                return; // Bloqueia movimento
	            }
	        }
	        // Se for passável, continua normalmente
	    }
	    

	    if (isOutOfBounds(destination)) {
	        setPosition(destination);
	        markAsExited();
	        room.getEngine().switchActiveFish();
	        room.checkLevelCompletion();
	        return;
	    }

	    // Guarda quem era o top antes de mover - Para a morte (comido)
	    GameObject topBefore = room.getTopObjectAt(destination);

	    // actualiza posição
	    setPosition(destination);

	    // só conta se mexeu
	    if (!currentPos.equals(destination)) {
	        room.incrementMoveCount();
	    }

	    // Resolve entradas/colisões causadas por este mover
	    room.resolveEntry(this, destination, topBefore);
	    
	}
	
	private boolean isOutOfBounds(Point2D pos) {
        if (pos == null) return true;
        int x = (int) pos.getX();
        int y = (int) pos.getY();
        return x < 0 || x >= GRID_WIDTH || y < 0 || y >= GRID_HEIGHT;
    }
	
	// override nos outros regras de peso em cima de cada peixe
	public boolean checkDeath() {
    	return false;
    }
	
	public void die() {
	    room.removeObject(this);

	    if (this instanceof SmallFish || this instanceof BigFish) {
	    	    room.getEngine().endGameLoss();// mudar para endgame (perder) quando fizer
	    		return;
	    }

	    room.getEngine().updateGUI();
	}

	
	public void markAsExited() {
        this.hasExited = true;
    }
    
    public boolean isExited() {
        return hasExited;
    }
	
	public void fall() {
    	Point2D pos = this.getPosition();
        this.setPosition(pos.getX(), pos.getY() + 1);
    }
	
}