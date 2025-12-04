package objects;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Vector2D;

// so o grande movimenta

public class Anchor extends MovableObjects {
	
	public Anchor(Room r) {
		super(r);
	}
	
	@Override
	public String getName() {
		return "anchor";
	}

	@Override
	public int getLayer() {
		return 2;
	}

	@Override
	public boolean isHeavy() {
		return true;
	}
	
	@Override
    public boolean canBePushedBy(GameObject pusher, Vector2D dir, Room room) {
   
        if (!(pusher instanceof BigFish)) {
            return false;
        }
        // Verifica se é horizontal (esquerda ou direita)
        boolean isHorizontal = dir.equals(Direction.LEFT.asVector()) 
                            || dir.equals(Direction.RIGHT.asVector());
        if (!isHorizontal) {
            return false; // não pode subir/descer
        }
        return true;
    }
	
	
}