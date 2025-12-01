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
	    
	public boolean hasGravity() {
	    return false;
	}
	
	public  boolean isPushable() {
        return false;
	}
	
       
}
