package objects;

import pt.iscte.poo.game.Room;

public class Wall extends SolidObjects {

	public Wall(Room room) {
		super(room);
	}

	@Override
	public String getName() {
		return "wall";
	}	

	@Override
	public int getLayer() {
		return 1;
	}
	
	@Override
	public boolean isPassable(GameObject passer) {
	    return false;
	}

}
