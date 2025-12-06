package objects;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;



// letra F

//tenta mover em direção a superficie ---- só o peixe grande empurra para baixo --- na horizontal qualquer um dos peixes consegue
//afunda ao suportar objeto movel


public class Buoy extends MovableObjects {

	public Buoy(Room room) {
		super(room);
	}

	@Override
	public String getName() {
		return "buoy";
	}

	@Override
	public int getLayer() {
		return 2;
	}

	@Override
	public boolean isHeavy() {
		return false;
	}

	@Override
	public boolean canBePushedBy(GameObject pusher, Vector2D dir, Room room) {
		
		boolean isHorizontal = dir.equals(Direction.LEFT.asVector()) 
                || dir.equals(Direction.RIGHT.asVector());


		if (isHorizontal) {
			return pusher instanceof BigFish || pusher instanceof SmallFish;
		}

		if (pusher instanceof BigFish) {
			return true;
		}
		
		return false;
        
    }


	@Override
	public void fall() {
    	Point2D pos = this.getPosition();
        this.setPosition(pos.getX(), pos.getY() - 1);
    }
	
	
		

}