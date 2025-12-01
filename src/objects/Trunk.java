package objects;
import pt.iscte.poo.game.Room;

//falta fazer destruição

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
}

