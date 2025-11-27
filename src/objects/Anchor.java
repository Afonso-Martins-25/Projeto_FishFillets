package objects;
import pt.iscte.poo.game.Room;

public class Anchor extends HeavyObjects {
	
	public Anchor(Room r) {
		super(r);
	}
	
	@Override
	public String getName() {
		return "anchor";
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
	
}