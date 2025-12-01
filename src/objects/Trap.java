package objects;
import pt.iscte.poo.game.Room;


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
		return 1;
	}
	
	@Override
	public boolean isPassable(GameObject passer) {
		if (passer instanceof SmallFish) {
	        return true;  // pequeno pode passar
	    } else {
	        return true; // se for do peixe grande mata falta fazer
	    }

	}
	
	@Override
	public  boolean isPushable() {
        return false;
	}

	@Override
	public boolean isHeavy() {
		return true;
	}
	
	
}

