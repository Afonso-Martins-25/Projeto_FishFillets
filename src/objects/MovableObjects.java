package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

//implementa a interface pushable

public abstract class MovableObjects extends GameObject implements Pushable{

	
	public MovableObjects(Room room) {
		super(room);
	}
	
	//Tem gravidade
	@Override
	public boolean hasGravity() {
		return true;
	}
	
	public abstract boolean isHeavy(); 

	// Chamado quando o objeto se move para baixo
	public void onFall() {}

	// Chamado quando cai em cima de outro objeto
	public void onHit(GameObject below) {}

	// Chamado quando um objeto pesado cai em cima disto
	public void onCrush(GameObject below) {}

	// Apenas bombas utilizam
	public void onExplode(Room room) {}
	
	
	
	@Override
    public boolean isPushable() {
        return true;
    }

    @Override      //interface
    public boolean push(Vector2D dir, GameObject pusher) {
        // Lógica básica de empurrar: verifica se pode mover e move
        Room room = getRoom();
        if (room == null) return false;
        // Verifica se a posição destino está livre
        if (room.isPositionPassable(getPosition().plus(dir), this)) {
            setPosition(getPosition().plus(dir));
            return true;
        }
        return false;
    }
    
	
	@Override
	public boolean isPassable(GameObject passer) {
	    return false;
	}
	
	
	
	
	
	
}
