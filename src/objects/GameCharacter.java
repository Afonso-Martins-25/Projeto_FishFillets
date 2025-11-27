package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class GameCharacter extends GameObject {     // 25/11/2025
	
	private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 10;
	
	public GameCharacter(Room room) {
		super(room);
	}
	
	protected boolean facingRight = true;
	public boolean hasExited = false; // 25/11/2025
	
		
	
	
	
		public void move(Vector2D dir) {
			Point2D currentPos = getPosition();
			Point2D destination = currentPos.plus(dir);
	
			if (dir == null || room == null || isExited()) {
				return;
			}
	
			if (dir.equals(Direction.RIGHT.asVector())) {
				facingRight = true;
			} else if (dir.equals(Direction.LEFT.asVector())) {
				facingRight = false;
			}
	
			if (isOutOfBounds(destination)) {
				setPosition(destination);
				markAsExited();
				room.getEngine().switchActiveFish();
				room.checkLevelCompletion();
				return;
			}
	
			// Se existe um objecto bloqueador, tentar empurrar
			GameObject destObj = room.getTopObjectAt(destination);
			if (destObj != null && !destObj.isPassable(this)) {
				if (destObj.isPushable()) {
					// tenta empurrar; se sucesso, move o peixe
					if (!room.tryPushObjectAt(destination, dir, this)) {
						return; // bloqueado
					}
				} else {
					return; // bloqueado por objecto não passável e não pushable
				}
			} else {
				// Se é passável, ou não existe objecto, segue
				// se isPositionPassable já permite, pode seguir
				if (!room.isPositionPassable(destination, this)) return;
			}
	
			setPosition(destination);
		}
	
		// ...existing code...
	
	
	 private boolean canMove(Point2D destination) {
	        if (destination == null || room == null) {
	            return false;
	        }
	        if (!room.isPositionPassable(destination, this)) {
	                return false;
	        }
	        
	        return true;
	        }
	 
//	 public boolean canMoveTo(Point2D pos) {
//	        if (isExited()) {
//	            return false; // Peixe saído não pode mover-se
//	        }
//	        return canMove(pos);
//	    }
	
	private boolean isOutOfBounds(Point2D pos) {
        if (pos == null) return true;
        int x = (int) pos.getX();
        int y = (int) pos.getY();
        return x < 0 || x >= GRID_WIDTH || y < 0 || y >= GRID_HEIGHT;
    }
	
	public void markAsExited() {
        this.hasExited = true;
    }
    
    public boolean isExited() {
        return hasExited;
    }
	

	@Override
	public int getLayer() {
		return 2;
	}
	
	//Tem gravidade
			public boolean hasGravity() {
			        return false;
			    }
			
			 public   boolean isPushable() {
				 return false;
			 }
	
}