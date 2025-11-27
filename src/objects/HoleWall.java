package objects;

import pt.iscte.poo.game.Room;

public class HoleWall extends GameObject {

	public HoleWall(Room room) {
		super(room);
	}
	
	@Override
    public boolean supportsObject(GameObject obj) {
        return true;
    }

    @Override
    public boolean canBeTraversedBy(GameObject mover) {
    	if (mover instanceof SmallFish) return true;
        if (mover instanceof HoleTraversable) return true;
        return false;
    }

	@Override
	public String getName() {
		return "holedWall";
	}	

	@Override
	public int getLayer() {
		return 1;
	}

}