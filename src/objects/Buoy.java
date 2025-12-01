package objects;
import pt.iscte.poo.game.Room;



// falta criar a atribuição da letra para a boia e o case para a construir

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
	
	
		

}