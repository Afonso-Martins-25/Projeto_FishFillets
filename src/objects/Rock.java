package objects;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Vector2D;


//o carangueijo sai daqui

public class Rock extends MovableObjects {
		
	
	public Rock(Room r) {
		super(r);
	}
	
	@Override
	public String getName() {
		return "stone";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public boolean isHeavy() {
		return true;
	}
	
	@Override
    public boolean canBePushedBy(GameObject pusher, Vector2D dir, Room room) {
        // Pedra é pesada: SmallFish não consegue
        if (pusher instanceof SmallFish) {
            return false;
        }
        // BigFish consegue em qualquer direção
        if (pusher instanceof BigFish) {
            return true;
        }
        return false;
    }
	

	 
}