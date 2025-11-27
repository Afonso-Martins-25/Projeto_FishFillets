package objects;

import pt.iscte.poo.game.Room;

public class SteelHorizontal extends GameObject {

	public SteelHorizontal(Room room) {
		super(room);
	}
	
	@Override
    public boolean supportsObject(GameObject obj) {
        return true;
    }

    @Override
    public boolean canBeTraversedBy(GameObject mover) {
        return false;
    }

	@Override
	public String getName() {
		return "steelHorizontal";
	}

	@Override
	public int getLayer() {
		return 1;
	}

}
