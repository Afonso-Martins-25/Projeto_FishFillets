package objects;

import java.util.List;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Vector2D;

public class SmallFish extends GameCharacter {

	private static SmallFish sf = new SmallFish(null);
	
	private SmallFish(Room room) {
		super(room);
	}

	public static SmallFish getInstance() {
		return sf;
	}
	
	@Override
	public boolean checkDeath() {
	    List<MovableObjects> carga = room.getVerticalLoadAbove(this);

	    int leves = 0;
	    int pesados = 0;

	    for (MovableObjects m : carga) {
	        if (m.isHeavy())
	            pesados++;
	        else
	            leves++;
	    }

	    if (pesados > 0) return true;       // pesado mata sempre
	    if (leves > 1) return true;         // mais de 1 leve mata

	    return false;
	}
	
	@Override
	public String getName() {
		return facingRight ? "smallFishRight" : "smallFishLeft";
	}
	
	@Override
	public boolean isPassable(GameObject passer) {
	    if (passer instanceof Crab) {
	        return true;
	    }
	    return false;
	}

	@Override
	public int getLayer() {
		return 3;
	}

}
