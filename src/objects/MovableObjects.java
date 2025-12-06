package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

//implementa a interface pushable

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


    @Override      //interface
    public boolean push(Vector2D dir, GameObject pusher) {
        // Lógica básica de empurrar: verifica se pode mover e move
        Room room = getRoom();
        if (room == null) return false;
        // Verifica se a posição destino está livre
        if (room.isPositionPassable(getPosition().plus(dir), this)) {
            setPosition(getPosition().plus(dir));
            return true;
        }
        return false;
    	
//    	Room room = getRoom();
//        if (room == null || dir == null) return false;
//
//        // pergunta ao objeto se pode ser empurrado
//        if (!canBePushedBy(pusher, dir, room)) {
//            return false;
//        }
//
//        Point2D destination = getPosition().plus(dir);
//        
//        // verifica se destino está passável
//        if (!room.isPositionPassable(destination, this)) {
//            return false;
//        }

//        // move simplesmente
//        setPosition(destination);
//        room.getEngine().updateGUI();
//        return true;
    }
    
	
	
    public void fall() {
    	Point2D pos = this.getPosition();
        this.setPosition(pos.getX(), pos.getY() + 1);
    }
	
	
	
	
	
}
