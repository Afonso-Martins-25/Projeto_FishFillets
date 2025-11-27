package objects;

import pt.iscte.poo.game.Room;

public abstract class HeavyObjects extends MovableObjects {
		
	public HeavyObjects(Room room) {
		super(room);
	}
	
	public  boolean isPushable() {
        
        return true;
    }
	
	
}
