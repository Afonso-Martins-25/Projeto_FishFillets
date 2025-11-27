package objects;

import pt.iscte.poo.game.Room;

public class Cup extends GameObject {

	public Cup(Room room) {
		super(room);
	}
	

    @Override
    public boolean canBeTraversedBy(GameObject mover) {
        return false;
    }
    
    @Override
    public boolean isPushable() {
        return false;
    }

	@Override
	public String getName() {
		return "cup";
	}

	@Override
	public int getLayer() {
		return 1;
	}

}