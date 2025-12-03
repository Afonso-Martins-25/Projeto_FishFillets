package objects;
import pt.iscte.poo.game.Room;
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

	@Override  // confirmar
	public boolean isHeavy() {
		// TODO Auto-generated method stub
		return false;
	}

	// falta fazer
	@Override
	public boolean canBePushedBy(GameObject pusher, Vector2D dir, Room room) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
		

}