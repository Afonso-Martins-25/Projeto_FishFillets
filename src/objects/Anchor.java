package objects;
import pt.iscte.poo.game.Room;

// so o grande movimenta

public class Anchor extends MovableObjects {
	
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

	@Override
	public boolean isHeavy() {
		return true;
	}
	
	
}