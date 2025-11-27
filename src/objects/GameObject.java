package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class GameObject implements ImageTile{
	
	private Point2D position;
	protected Room room;
	
	public GameObject(Room room) {
		this.room = room;
	}
	
	public GameObject(Point2D position, Room room) {
		this.position = position;
		this.room = room;
	}

	public void setPosition(int i, int j) {
		position = new Point2D(i, j);
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
	
	
	//Da para mover pelo objeto consoante quem quer passar
	public boolean isPassable(GameObject passer) {
	    return false;  // objetos passaveis
	}
	
	
	//Serve como suporte(caso o objeto caia)
	public boolean isSolid() {
	        return true;
	    }
	
	//Tem gravidade
		public abstract boolean hasGravity();
		        
		    

		// por defeito não é pushable
    public abstract  boolean isPushable();
       

    // default: delegate to Room
    public boolean push(Vector2D dir) {
        if (room == null) return false;
        return room.tryPushObjectAt(getPosition(), dir, this);
    }
    
 // Hook chamado depois do objeto ser movido pela Room (ouoverride para comportamento custom).
    // pusher é quem empurrou (peixe u outro)
    public void onPushedBy(GameObject pusher, pt.iscte.poo.utils.Vector2D dir) {
        // default: não fazer nada
    }
}
