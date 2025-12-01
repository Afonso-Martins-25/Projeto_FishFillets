package objects;

import pt.iscte.poo.game.Room;

public class BigFish extends GameCharacter {

	private static BigFish bf = new BigFish(null);
	
	private BigFish(Room room) {
		super(room);
	}

	public static BigFish getInstance() {
		return bf;
	}
	
	public boolean checkSupport() {
	    int countLeves = 0;
	    int countPesados = 0;
	    for (GameObject obj : room.getObjects()) {
	        if (obj.getPosition().getX() == getPosition().getX() &&
	            obj.getPosition().getY() < getPosition().getY()) {
	            if (obj instanceof Cup || obj instanceof Bomb) countLeves++;
	            if (obj instanceof Rock || obj instanceof Anchor || obj instanceof Trap) countPesados++;
	        }
	    }
	    // BigFish suporta vários leves ou 1 pesado
	    if (countPesados > 1 || (countPesados == 1 && countLeves > 0)) {
	        room.getEngine().restartCurrentLevel();
	        return false;
	    }
	    return true;
	}
	
	@Override
	public String getName() {
		return facingRight ? "bigFishRight" : "bigFishLeft";
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
}
