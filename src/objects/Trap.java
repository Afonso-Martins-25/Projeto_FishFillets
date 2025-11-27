package objects;
import pt.iscte.poo.game.Room;


public class Trap extends HeavyObjects {
		
	
	public Trap(Room r) {
		super(r);
	}
	
	@Override
	public String getName() {
		return "trap";
	}

	@Override
	public int getLayer() {
		return 1;
	}
	@Override
	public boolean isPassable(GameObject passer) {
	    if(passer instanceof SmallFish)
	    	return true;
		
		return false;  // objetos passaveis
	}
	
	
}

