package objects;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Vector2D;


public class Cup extends LightObjects {
	
	public Cup(Room r) {
		super(r);
	}
	
	@Override
	public String getName() {
		return "cup";
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
	

	 
}
