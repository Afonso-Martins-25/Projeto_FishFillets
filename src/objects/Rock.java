package objects;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Vector2D;


//o carangueijo sai daqui

public class Rock extends MovableObjects {
		
	
	public Rock(Room r) {
		super(r);
	}
	
	@Override
	public String getName() {
		return "stone";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public boolean isHeavy() {
		return true;
	}
	

	 
}