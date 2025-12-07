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
	
	// Faz o objeto cair uma unidade para baixo
    public abstract void fall();
        
 
    public boolean hasSupport() {
        Point2D pos = this.getPosition();
        Point2D below = new Point2D(pos.getX(), pos.getY() + 1);

        // Chão da grelha → tem suporte
        if (below.getY() >= 10)
            return true;

        // Objeto na layer mais alta na posição abaixo
        GameObject under = this.getRoom().getTopObjectAt(below);

        // Não existe nada → não tem suporte → cai
        if (under == null)
            return false;

        // Água nunca dá suporte (mesmo que seja top layer)
        if (under instanceof Water)
            return false;

        // Se o objeto abaixo NÃO for passável pelo objeto que está a cair → é suporte
        return !under.isPassable(this);
    }
    
    public boolean hasSomethingUp() {
    	Point2D pos=this.getPosition();
    	Point2D up= new Point2D(pos.getX(), pos.getY() + -1);
    	
    	if(up.getY()<0) {
    		return true;
    	}
    	
    	GameObject onTop=this.getRoom().getTopObjectAt(up);
    	
    	
    	
    	if(onTop == null || onTop instanceof Water ) {
    		return false;
    	}
    	
    	return !onTop.isPassable(this);
    }

	
       
}
