package objects;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Vector2D;

//falta coisas explosão

public class Bomb extends MovableObjects {
	
	public Bomb(Room r) {
		super(r);
	}
	
	@Override
	public String getName() {
		return "bomb";
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