package objects;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;


public class HoledWall extends GameObject {
		
	
	public HoledWall(Room r) {
		super(r);
	}
	
	@Override
	public String getName() {
		return "holedWall";
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
	@Override
	public boolean isPassable(GameObject passer) {
		if (passer instanceof SmallFish || passer instanceof Cup) {
	        return true;  // pequeno e cup pode passar
	    } else {
	        return false; // grande não pode passar
	    }

	}
	
	public void fall() {
    	Point2D pos = this.getPosition();
        this.setPosition(pos.getX(), pos.getY() + 1);
    }

}

