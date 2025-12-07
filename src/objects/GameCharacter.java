package objects;

import java.io.FileNotFoundException;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class GameCharacter extends GameObject {
	
	
	public GameCharacter(Room room) {
		super(room);
	}
	
	protected boolean facingRight = true;
	public boolean hasExited = false;
	
	
	public void move(Vector2D dir) throws FileNotFoundException {
	    if (dir == null || room == null || isExited()) {
	        return;
	    }

	    // Atualiza direção
	    if (dir.equals(Direction.RIGHT.asVector())) {
	        facingRight = true;
	    } else if (dir.equals(Direction.LEFT.asVector())) {
	        facingRight = false;
	    }

	    Point2D currentPos = getPosition();
	    Point2D destination = currentPos.plus(dir);
	    GameObject destObj = room.getTopObjectAt(destination);

	    // Lida com objetos no destino
	    if (destObj != null && !destObj.isPassable(this)) {
	        if (destObj instanceof Pushable) {
	            if (!room.tryPushObjectAt(destination, dir, this)) {
	                return;
	            }
	        } else {
	            return;
	        }
	    }

	    // Verifica saída
	    if (!room.isInBounds(destination)) {
	        setPosition(destination);
	        markAsExited();
	        room.getEngine().switchActiveFish();
	        room.checkLevelCompletion();
	        return;
	    }

	    // Move e resolve interações
	    GameObject topBefore = room.getTopObjectAt(destination);
	    setPosition(destination);

	    if (!currentPos.equals(destination)) {
	        room.incrementMoveCount();
	    }

	    room.resolveEntry(this, destination, topBefore);
	    
	    if (this instanceof SmallFish || this instanceof BigFish) {
	        room.moveAllCrabs();
	    }
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
	
	public void moveRandomHorizontally() throws FileNotFoundException {
        Direction dir = Direction.random();

        while (dir == Direction.UP || dir == Direction.DOWN) {
            dir = Direction.random();
        }
        
        move(dir.asVector());
    }
	
}