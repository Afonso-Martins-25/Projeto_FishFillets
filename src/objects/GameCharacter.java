package objects;

import java.util.Random;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class GameCharacter extends GameObject {
	
	public GameCharacter(Room room) {
		super(room);
	}
	
//	public void move(Vector2D dir) {
//		Random rand = new Random();
//		Point2D destination = new Point2D(rand.nextInt(10), rand.nextInt(10)); 
//		setPosition(destination);		
//	}
	
//    public void move(Vector2D dir) {		
//		// Movimento pelo vetor recebido (não teletransporte aleatório)
//		if (dir == null || getPosition() == null || getRoom() == null)
//			return;
//		Point2D destination = getPosition().plus(dir);
//		// Verifica se a sala permite o movimento
//		if (getRoom().canMoveTo(destination)) {
//			setPosition(destination);
//		}
//    }

	@Override
	public int getLayer() {
		return 2;
	}
	
}