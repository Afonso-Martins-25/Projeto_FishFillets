package objects;
import pt.iscte.poo.game.Room;


public class Trunk extends SolidObjects {
		
	
	public Trunk(Room r) {
		super(r);
	}
	
	@Override
	public String getName() {
		return "trunk";
	}

	@Override
	public int getLayer() {
		return 1;
	}
}

