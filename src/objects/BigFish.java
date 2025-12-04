package objects;

import java.util.List;

import pt.iscte.poo.game.Room;

public class BigFish extends GameCharacter {

	private static BigFish bf = new BigFish(null);
	
	private BigFish(Room room) {
		super(room);
	}

	public static BigFish getInstance() {
		return bf;
	}
	
	@Override
	public boolean checkDeath() {
	    // 1. Verificar carga acima
	    List<MovableObjects> carga = room.getVerticalLoadAbove(this);
	    int pesados = 0;

	    for (MovableObjects m : carga) {
	        if (m.isHeavy()) pesados++;
	    }

	    if (pesados > 1) return true;  // BigFish suporta só 1 pesado

//	    // 2. Verificar armadilha na posição atual
//	    GameObject top = room.getTopObjectAt(getPosition());
//	    if (top instanceof Trap) { // armadilha = pesado
//	        return true; 
//	    }

	    return false;
	}
	
	@Override
	public String getName() {
		return facingRight ? "bigFishRight" : "bigFishLeft";
	}

	@Override
	public int getLayer() {
		return 5;
	}
	
}
