package objects;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Vector2D;

//falta coisas explosão

public class Bomb extends MovableObjects {
	
	public Bomb(Room r) {
		super(r);
	}
	
	@Override
	public String getName() {
		return "bomb";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public boolean isHeavy() {
		return true;
	}
	
	
	// explode depois de movida
	
	/*public void onPushedBy(GameObject pusher, Vector2D dir) {
	    explode(); // implementa a lógica de explosão
	}

	// ou: impedir/alterar movimento antes de chamar super
	@Override
	public boolean push(Vector2D dir) {
	    if (shouldExplodeImmediately()) {
	        explode();
	        return false; // não se move
	    }
	    return super.push(dir); // delega para Room
	}*/
}