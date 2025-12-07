package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import objects.Water;
import objects.Anchor;
import objects.BigFish;
import objects.Bomb;
import objects.Buoy;
import objects.Crab;
import objects.Cup;
import objects.GameCharacter;
import objects.GameObject;
import objects.GameObjectFactory;
import objects.HoledWall;
import objects.MovableObjects;
import objects.Pushable;
import objects.Rock;
import objects.SmallFish;
import objects.SteelHorizontal;
import objects.SteelVertical;
import objects.Trap;
import objects.Trunk;
import objects.Wall;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Room {
	

	private List<GameObject> objects;
	private String roomName;
	private GameEngine engine;
	private Point2D smallFishStartingPosition;
	private Point2D bigFishStartingPosition;
	private final int HEIGHT = 10;
	private final int LENGTH = 10;
    private SmallFish smallFish = SmallFish.getInstance();
    private BigFish bigFish = BigFish.getInstance();
    private int currentLevelNumber = 0; // rastreia room0, room1, etc.
    private int moveCount = 0;
	private int timeCount = 0;


	
	public Room() {
		objects = new ArrayList<GameObject>();
	}
    
    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

	private void setName(String name) {
		roomName = name;
	}
	
	public String getName() {
		return roomName;
	}
	
	private void setEngine(GameEngine engine) {
		this.engine = engine;
	}
	
	public GameEngine getEngine() {
		return engine;
	}

	public void addObject(GameObject obj) {
		objects.add(obj);
		engine.updateGUI();
	}
	
	public void removeObject(GameObject obj) {
		objects.remove(obj);
		engine.updateGUI();
	}
	
	public List<GameObject> getObjects() {
		return objects;
	}

	public void setSmallFishStartingPosition(Point2D heroStartingPosition) {
		this.smallFishStartingPosition = heroStartingPosition;
	}
	
	public Point2D getSmallFishStartingPosition() {
		return smallFishStartingPosition;
	}
	
	public void setBigFishStartingPosition(Point2D heroStartingPosition) {
		this.bigFishStartingPosition = heroStartingPosition;
	}
	
	public Point2D getBigFishStartingPosition() {
		return bigFishStartingPosition;
	}
	
	public int getMoveCount() { 
		return moveCount; 
		}
	
	public int getTimeCount() { 
		return timeCount; 
		}
	
	public void incrementMoveCount() { 
		moveCount++; 
		}
	public void incrementTimeCount() { 
		timeCount++; 
		}
	
	// reset quando a sala é carregada
		public void resetCounters() {
		    moveCount = 0;
		    timeCount = 0;
		}


	
	public static Room readRoom(File f, GameEngine engine) {
        return readRoom(f, engine, 0);
    }
	
	public static Room readRoom(File f, GameEngine engine, int levelNumber){
	    Room r = new Room();
	    r.setEngine(engine);
	    r.setName(f.getName());
	    r.currentLevelNumber = levelNumber;

	    List<String> lines = new ArrayList<>();
	    try (Scanner sc = new Scanner(f)) {
	        while (sc.hasNextLine()) {
	            lines.add(sc.nextLine());
	        }
	    } catch (FileNotFoundException e) {
	        throw new RuntimeException("Room file not found: " + f, e);
	    }

	    int width = 10;
	    int height = 10;

	    // Preencher com água
	    for (int y = 0; y < height; y++) {
	        for (int x = 0; x < width; x++) {
	            GameObject water = new Water(r);
	            water.setPosition(x, y);
	            r.addObject(water);
	        }
	    }

	    // Criar objetos através da fábrica
	    for (int y = 0; y < height; y++) {
	        String line = lines.get(y);
	        for (int x = 0; x < line.length(); x++) {

	            char c = line.charAt(x);
	            GameObject obj = GameObjectFactory.create(c, r);

	            if (obj != null) {
	                obj.setPosition(x, y);
	                
	                if (obj instanceof BigFish) {
	                    r.setBigFishStartingPosition(new Point2D(x, y));
	                   
	                } else if (obj instanceof SmallFish) {
	                    r.setSmallFishStartingPosition(new Point2D(x, y));
	                    
	                } else {
	                    r.addObject(obj);
	                }
	            }
	        }
	    }

	    // Setup dos peixes
	    r.smallFish.setRoom(r);
	    r.bigFish.setRoom(r);
	    r.smallFish.hasExited = false;
	    r.bigFish.hasExited = false;
	    
	    r.resetCounters();
	    return r;
	}
	
	
	public boolean isPositionPassable(Point2D pos, GameObject passer) {
//		for (GameObject obj : getObjectsAt(pos)) { // estava assim estudar erro 7/12
		GameObject obj = getTopObjectAt(pos);
	        if (!obj.isPassable(passer)) {
	            return false; // barreira para este passer
	        }
	    return true; // posição livre ou passável para este passer
	}
	
	
	// Retorna todos os objetos numa posicao. Ver se o bigFish e crab esta na armadilha checkDeath são removidos em
	public List<GameObject> getObjectsAt(Point2D pos) {
		List<GameObject> objectsAtPos = new ArrayList<>();

		for (GameObject obj : objects) { 
		    if (obj.getPosition().equals(pos)) {
		        objectsAtPos.add(obj);
		    }
		}

		return objectsAtPos;
	}
	
	
	// Atualiza o estado dos objetos móveis, fazendo-os cair se não houver suporte
    public void updateFallingObjects() {
    	List<GameObject> toRemove = new ArrayList<>();
    	
        for (GameObject obj : objects) {
        	if (obj.hasGravity()) {
                
                // Destruir trunk ANTES de verificar suporte
                if (obj instanceof MovableObjects mov && mov.isHeavy()) {
                    GameObject below = getTopObjectAt(mov.getPosition().plus(Direction.DOWN.asVector()));
                    if (below instanceof Trunk) {
                        toRemove.add(below); // Marcar para remoção
                    }
                }
                
                // Agora verifica suporte (com trunk já removido se aplicável)
                if (obj.hasSupport() == false) {
                    obj.fall();
                }
            }
        }
        
        for (GameObject obj : toRemove) {
            removeObject(obj);
        }
    }

    
    // usado no move gameCharacter inicia proximo nivel.
	public void checkLevelCompletion() throws FileNotFoundException {
		if (smallFish.isExited() && bigFish.isExited()) {
	     engine.loadNextLevel();
		}
	}
    
	
    // devolve o objecto da layer mais alta nessa posição
    public GameObject getTopObjectAt(Point2D pos) {
        if (!isInBounds(pos)) return null;
        GameObject top = null;
        for (GameObject obj : objects) {
            if (obj.getPosition().equals(pos)) {
                if (top == null || obj.getLayer() >= top.getLayer()) {
                    top = obj;
                }
            }
        }
        return top;
    }
    
    // Validar destino antes de procurar objetos ^ usado em gamecharacter e push
    public boolean isInBounds(Point2D pos) {
        if (pos == null) return false;
        int x = pos.getX(), y = pos.getY();
        return x >= 0 && x < LENGTH && y >= 0 && y < HEIGHT;
    }
    
    
    // resolve a passagem por cima de objetos (com morte)
    public void resolveEntry(GameObject mover, Point2D pos, GameObject topBefore) {
        // topAfter é agora o top
        GameObject topAfter = getTopObjectAt(pos);

        // se antes já tinha alguém e depois temos mover no topo -> significa que mover entrou sobre alguém
        if (topBefore != null && topAfter == mover) {
            // se havia um GameCharacter na layer de baixo
            if (topBefore instanceof GameCharacter gcBelow) {
                resolveCharacterVsCharacter(mover, gcBelow);
            }
        }

        if (mover instanceof GameCharacter) {
            GameCharacter gc = (GameCharacter) mover;
            if (gc.checkDeath()) {
                gc.die();
            }
        }
    }
    
    // resolve a passagem por cima de personagens (com morte)
    private void resolveCharacterVsCharacter(GameObject mover, GameCharacter stationary) {
    	
    	// Crab vs SmallFish (independente de quem se move)
        if ((mover instanceof Crab && stationary instanceof SmallFish) ||
            (mover instanceof SmallFish && stationary instanceof Crab)) {
            // SmallFish sempre morre
            if (mover instanceof SmallFish) {
                ((SmallFish) mover).die();
            } else {
                ((SmallFish) stationary).die();
            }
            return;
        }
        
        if (mover.getLayer() > stationary.getLayer()) {
            // mover passou por cima → stationary morre
            stationary.die();
        } else if (mover.getLayer() < stationary.getLayer()) {
            // mover ficou por baixo do stationary → mover morre
            if (mover instanceof GameCharacter) {
                ((GameCharacter) mover).die();
            }
        }
             
    }
    
    // Usado em checkDeath Peixes retorna lista de objetos acima
    public List<MovableObjects> getVerticalLoadAbove(GameObject base) {
        List<MovableObjects> list = new ArrayList<>();

        Point2D check = base.getPosition().plus(Direction.UP.asVector());

        while (isInBounds(check)) {
            GameObject obj = getTopObjectAt(check);
            if (obj instanceof MovableObjects mov) {
                list.add(mov);
                check = check.plus(Direction.UP.asVector());
            } else {
                break;
            }
        }
        return list;
    }

    // usado em gameengine para remover de forma segura personagens mortas por peso
    public void updateDeaths() {
        List<GameCharacter> toRemove = new ArrayList<>();

        for (GameObject obj : objects) {
            if (obj instanceof GameCharacter gc) {
                if (gc.checkDeath()) {
                    toRemove.add(gc);
                }
            }
        }

        for (GameCharacter gc : toRemove) {
        	if (!(gc instanceof Crab))
        		gc.die();
        }
    }
    
    // move de forma segura todos os carangueijos
    public void moveAllCrabs() throws FileNotFoundException {
        List<GameObject> objectsCopy = new ArrayList<>(objects);
        
        for (GameObject obj : objectsCopy) {
            if (obj instanceof Crab) {
                Crab crab = (Crab) obj;
                crab.moveRandomHorizontally();
            }
        }
    }
    
//    // tentativa de empurrar o objecto na posição pos em direcção dir
//    public boolean tryPushObjectAt(Point2D pos, Vector2D dir, GameObject pusher) {
//        for (GameObject obj : objects) {
//            if (obj.getPosition().equals(pos) && obj instanceof Pushable) {
//                Pushable pushable = (Pushable) obj;
//                if (pushable.canBePushedBy(pusher, dir, this)) {
//                    return pushable.push(dir, pusher);
//                }
//            }
//        }
//        return false;
//    }
    
    public boolean tryPushObjectAt(Point2D pos, Vector2D dir, GameObject pusher) {
        GameObject obj = getTopObjectAt(pos);
        if (obj instanceof Pushable) {
            Pushable pushable = (Pushable) obj;
            return pushable.canBePushedBy(pusher, dir, this) && 
                   pushable.push(dir, pusher);
        }
        return false;
    }
    

}
