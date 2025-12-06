package objects;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;


//o carangueijo sai daqui

public class Rock extends MovableObjects {
		
	private boolean crabSpawned = false;
	
	public Rock(Room r) {
		super(r);
	}
	
	@Override
	public String getName() {
		return "stone";
	}

	@Override
	public int getLayer() {
		return 2;
	}

	@Override
	public boolean isHeavy() {
		return true;
	}
	
	@Override
    public boolean canBePushedBy(GameObject pusher, Vector2D dir, Room room) {
        // Pedra é pesada: SmallFish não consegue
        if (pusher instanceof SmallFish) {
            return false;
        }
        // BigFish consegue em qualquer direção
        if (pusher instanceof BigFish) {
            return true;
        }
        return false;
    }
	
	@Override
    public boolean push(Vector2D dir, GameObject pusher) {
        // Verificar se é movimento horizontal
        boolean isHorizontal = dir.equals(Direction.LEFT.asVector()) 
                            || dir.equals(Direction.RIGHT.asVector());
        
        // Se for horizontal e caranguejo ainda não foi criado
        if (isHorizontal && !crabSpawned) {
            spawnCrab();
            crabSpawned = true;
        }
        
        // Chamar o push padrão da classe pai
        return super.push(dir, pusher);
    }
    
    private void spawnCrab() {
        Room room = getRoom();
        Point2D abovePos = getPosition().plus(Direction.UP.asVector());
        
        // Verificar se a posição acima existe, está dentro dos limites e está livre
        if (room.getTopObjectAt(abovePos) instanceof Water) {
            Crab crab = new Crab(room);
            crab.setPosition(abovePos.getX(), abovePos.getY());
            room.addObject(crab);
        }
    }

	 
}