package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;


public abstract class MovableObjects extends GameObject implements Pushable{

	
	public MovableObjects(Room room) {
		super(room);
	}
	
	//Tem gravidade
	@Override
	public boolean hasGravity() {
		return true;
	}
	
	public abstract boolean isHeavy(); 
	
	@Override    //interface
	public boolean push(Vector2D dir, GameObject pusher) {
	    Room room = getRoom();
	    if (room == null) return false;
	    
	    Point2D newPos = getPosition().plus(dir);
	    
	    // Bloqueia empurrar para fora do mapa
	    if (!room.isInBounds(newPos)) {
	        return false;
	    }
	    
	    if (room.isPositionPassable(newPos, this)) {
	        setPosition(newPos);
	        return true;
	    }
	    return false;
	}
    
    public void fall() {
    	Point2D pos = this.getPosition();
        this.setPosition(pos.getX(), pos.getY() + 1);
    }
	
	
	
	
	
}
