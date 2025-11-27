package objects;

import pt.iscte.poo.game.Room;


public class Rock extends GameObject {

	public Rock(Room room) {
		super(room);
	}
	

    @Override
    public boolean canBeTraversedBy(GameObject mover) {
        return false;
    }

	@Override
	public String getName() {
		return "stone";
	}	

	@Override
	public int getLayer() {
		return 1;
	}

}