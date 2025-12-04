package objects;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Vector2D;

// passa parede com buraco tbm falta acho que fiz 1/12/25

public class Cup extends MovableObjects {
	
	public Cup(Room r) {
		super(r);
	}
	
	@Override
	public String getName() {
		return "cup";
	}

	@Override
	public int getLayer() {
		return 2;
	}

	@Override
	public boolean isHeavy() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
    public boolean canBePushedBy(GameObject pusher, Vector2D dir, Room room) {
        // Taça é leve, qualquer peixe pode empurrar em qualquer direção
        if (pusher instanceof SmallFish || pusher instanceof BigFish) {
            return true;
        }
        return false;
    }
	
	

	 
}
