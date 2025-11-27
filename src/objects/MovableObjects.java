package objects;

import pt.iscte.poo.game.Room;

public abstract class MovableObjects extends GameObject {

	
	public MovableObjects(Room room) {
		super(room);
	}
	
	//Tem gravidade
		public boolean hasGravity() {
		        return true;
		    }
	
	
}
