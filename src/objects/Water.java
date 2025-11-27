package objects;

import pt.iscte.poo.game.Room;

public class Water extends GameObject{

	public Water(Room room) {
		super(room);
	}

	@Override
	public String getName() {
		return "water";
	}

	@Override
	public int getLayer() {
		return 0;
	}
	@Override
	public boolean isSolid() {
        return false;
    }
	
	public boolean isPassable(GameObject passer) {
	    return true;  // objetos passaveis
	}

	@Override
	public boolean hasGravity() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPushable() {
		// TODO Auto-generated method stub
		return false;
	}

}
