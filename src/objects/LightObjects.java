package objects;

import pt.iscte.poo.game.Room;

public abstract class LightObjects extends MovableObjects {
	
	public LightObjects(Room room) {
		super(room);
	}
	
	public  boolean isPushable() {
        return true;
    }
}
