package objects;
import pt.iscte.poo.game.Room;

public class SteelVertical extends SolidObjects {
	
	public SteelVertical(Room r) {
		super(r);
	}
	
	@Override
	public String getName() {
		return "steelVertical";
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
	@Override
	public boolean isPassable(GameObject passer) {
	    return false;
	}
}
