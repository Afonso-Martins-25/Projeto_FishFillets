package objects;

import pt.iscte.poo.game.Room;

public abstract class SolidObjects extends GameObject {
	
	public SolidObjects(Room room) {
		super(room);
	}
	
	//Tem gravidade
		public boolean hasGravity() {
		        return false;
		    }
		
		public  boolean isPushable() {
	        return false;
	    }
}
