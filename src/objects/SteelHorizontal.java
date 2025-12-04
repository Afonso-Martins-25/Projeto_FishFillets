package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;

public class SteelHorizontal extends GameObject {

	public SteelHorizontal(Room room) {
		super(room);
	}

	@Override
	public String getName() {
		return "steelHorizontal";
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
	public void fall() {
    	Point2D pos = this.getPosition();
        this.setPosition(pos.getX(), pos.getY() + 1);
    }
	
	
	

}
