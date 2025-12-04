package objects;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Vector2D;


//falta coisas

public class Trap extends MovableObjects {
		
	
	public Trap(Room r) {
		super(r);
	}
	
	@Override
	public String getName() {
		return "trap";
	}

	@Override
	public int getLayer() {
		return 2;
	}
	
	@Override
	public boolean isPassable(GameObject passer) {
		return true;
	}

	@Override
	public boolean isHeavy() {
		return true;
	}
	
    // falta fazer
	@Override
	public boolean canBePushedBy(GameObject pusher, Vector2D dir, Room room) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}

