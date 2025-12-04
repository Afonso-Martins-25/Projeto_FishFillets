package objects;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;

public class SteelVertical extends GameObject {
	
	public SteelVertical(Room r) {
		super(r);
	}
	
	@Override
	public String getName() {
		return "steelVertical";
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
