package objects;

import java.util.List;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;


public class Crab extends GameCharacter {

	private static Crab bf = new Crab(null);
	
	public Crab(Room room) {
		super(room);
	}

	public static Crab getInstance() {
		return bf;
	}
	
	public boolean hasGravity() {
		return true;
	}
	
	@Override
	public String getName() {
		return "krab";
	}
	
	public boolean isPassable(GameObject passer) {
		if (passer instanceof GameCharacter)
			return true;
		return false;
	}
	
	@Override
	public boolean checkDeath() {
		
	    // 2. Verificar armadilha na posição atual
	    for (GameObject obj : room.getObjectsAt(getPosition())) {
            if (obj instanceof Trap) return true;
        }
	    
        return false;

	}
 
	@Override
	public int getLayer() {
		return 4;
	}
	
	
}