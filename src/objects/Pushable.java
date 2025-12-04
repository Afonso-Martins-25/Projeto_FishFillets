package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Vector2D;

public interface Pushable {
	
    boolean push(Vector2D dir, GameObject pusher);
    
    boolean canBePushedBy(GameObject pusher, Vector2D dir, Room room);
    
}