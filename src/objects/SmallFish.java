package objects;

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
	
	public boolean checkSupport() {
	    // Verifica objetos acima e aplica regras de morte
	    int countLeves = 0;
	    int countPesados = 0;
	    for (GameObject obj : room.getObjects()) {
	        if (obj.getPosition().getX() == getPosition().getX() &&
	            obj.getPosition().getY() < getPosition().getY()) {
	            if (obj instanceof Cup || obj instanceof Bomb) countLeves++;
	            if (obj instanceof Rock || obj instanceof Anchor || obj instanceof Trap) countPesados++;
	        }
	    }
	    if (countPesados > 0 || countLeves > 1) {
	        room.getEngine().restartCurrentLevel();
	        return false;
	    }
	    return true;
	}
	
	
	@Override
	public String getName() {
		return facingRight ? "smallFishRight" : "smallFishLeft";
	}

	@Override
	public int getLayer() {
		return 1;
	}

}
