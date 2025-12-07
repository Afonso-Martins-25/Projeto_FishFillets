package objects;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;


public class Trunk extends GameObject {
		
	
	public Trunk(Room r) {
		super(r);
	}
	
	@Override
	public String getName() {
		return "trunk";
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

