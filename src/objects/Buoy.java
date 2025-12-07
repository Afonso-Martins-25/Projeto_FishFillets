package objects;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

// letra F

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

	@Override
	public boolean isHeavy() {
		return false;
	}

	@Override
	public boolean canBePushedBy(GameObject pusher, Vector2D dir, Room room) {
		
		boolean isHorizontal = dir.equals(Direction.LEFT.asVector()) 
                || dir.equals(Direction.RIGHT.asVector());


		if (isHorizontal) {
			return pusher instanceof BigFish || pusher instanceof SmallFish;
		}

		if (pusher instanceof BigFish) {
			return true;
		}
		
		return false;
        
    }
	
	// funciona para detetar se tem objeto movel em cima, metodo objetivos diferente dos outros
    @Override
    public boolean hasSomethingUp() {
        Point2D pos = this.getPosition();
        Point2D up = new Point2D(pos.getX(), pos.getY() - 1);
        
        if (up.getY() < 0) {
            return false;
        }
        
        GameObject onTop = this.getRoom().getTopObjectAt(up);
        
        // Só conta como peso se for MovableObjects
        if (onTop != null && onTop instanceof MovableObjects) {
            return true;
        }
        
        return false;
    }

    // Usado no room para parar ou continuar a cair
    @Override
    public boolean hasSupport() {
        // Tem um MovableObject em cima a fazer peso
        if (hasSomethingUp()) {
            // Comporta-se como o super MovableObjects
            return super.hasSupport();
        }
        
        // Está livre (tenta flutuar)
        // O suporte teto ou obstáculo
        Point2D pos = this.getPosition();
        Point2D above = new Point2D(pos.getX(), pos.getY() - 1);
        
        // Chegou à superfície -> tem suporte (para de subir)
        if (above.getY() < 0) {
            return true;
        }
        
        GameObject overhead = this.getRoom().getTopObjectAt(above);
        
        // Se não há obstáculo em cima, n tem suporte -> sobe
        if (overhead == null || overhead instanceof Water || overhead.isPassable(this)) {
            return false;
        }
        
        // Se tem parede ou outro obstáculo fixo (não Movable) em cima -> tem suporte (fica parada)
        return true;
        
    }
    
    @Override
    public void fall() {
        // Se tem peso em cima. Comporta-se como o super MovableObjects
        if (hasSomethingUp()) {
            super.fall();
        } else {
        	// em principio resulta 7/12, caso contrario verificações em whatsapp
        	Point2D pos = this.getPosition();
            this.setPosition(pos.getX(), pos.getY() - 1);
        }
    }
    
}