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
import objects.Cup;
import objects.GameCharacter;
import objects.GameObject;
import objects.HoledWall;
import objects.Pushable;
import objects.Rock;
import objects.SmallFish;
import objects.SteelHorizontal;
import objects.SteelVertical;
import objects.Trap;
import objects.Trunk;
import objects.Wall;
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
											// 25/11/2025
    private SmallFish smallFish = SmallFish.getInstance();
    private BigFish bigFish = BigFish.getInstance();
    private int currentLevelNumber = 0; // rastreia room0, room1, etc.
	
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
	
	
	// 25/11/2025
	
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

        if (lines.isEmpty()) return r;
		
		int width = 10;  
	    int height = 10;
	    
	    //Prencche com agua
	    for (int y = 0; y < height; y++) {
	        for (int x = 0; x < width; x++) {
	            GameObject water = new Water(r);
	            water.setPosition(x, y);
	            r.addObject(water);
	        }
	    }
		
		
	    for (int y = 0; y < height; y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
				
				switch(c) {
				
				
			case 'W':     //Wall
				GameObject wall = new Wall(r);
				wall.setPosition(x,y);
				r.addObject(wall);
				break;
				
			case 'B': //BIgFish
				GameObject bf = BigFish.getInstance();
				bf.setPosition(x,y);
				r.addObject(bf);
				r.setBigFishStartingPosition(new Point2D(x, y));
				break;
				
			case 'S':  //SmallFish
				GameObject sf = SmallFish.getInstance();
				sf.setPosition(x,y);
				r.addObject(sf);
				r.setSmallFishStartingPosition(new Point2D(x, y));
				break;
				
			case 'H'://StellHorizontal
				GameObject sh =new SteelHorizontal(r);
				sh.setPosition(x,y);
				r.addObject(sh);
				break;
				
			case 'V': //SteelVertical
				GameObject sv= new SteelVertical(r);
				sv.setPosition(x, y);
				r.addObject(sv);
				break;
				
			case 'C': //Cup
				GameObject cup= new Cup(r);
				cup.setPosition(x, y);
				r.addObject(cup);
				break;
				
			case 'R'://Rock
				GameObject rock= new Rock(r);
				rock.setPosition(x, y);
				r.addObject(rock);
				break;
			
			case 'A'://Anchor
				GameObject anchor= new Anchor(r);
				anchor.setPosition(x, y);
				r.addObject(anchor);
				break;
				
			case 'b'://Bomb
				GameObject bomb= new Bomb(r);
				bomb.setPosition(x, y);
				r.addObject(bomb);
				break;
				
			case 'T'://Trap
				GameObject trap= new Trap(r);
				trap.setPosition(x, y);
				r.addObject(trap);
				break;
				
			case 'Y': //Trunk
				GameObject t= new Trunk(r);
				t.setPosition(x, y);
				r.addObject(t);
				break;
				
			case 'X': //HoledWall
				GameObject hw= new HoledWall(r);
				hw.setPosition(x, y);
				r.addObject(hw);
				break;
				//Pode adicionar mais objetos
				}
            }
	    }
	
	    r.smallFish.setRoom(r);
        r.bigFish.setRoom(r);
        r.smallFish.hasExited = false;
        r.bigFish.hasExited = false;
        
        return r;		
    }
	
	
	public boolean isPositionPassable(Point2D pos, GameObject passer) {
	    for (GameObject obj : getObjects()) {
	        if (obj.getPosition().equals(pos)) {
	            if (!obj.isPassable(passer)) {
	                return false; // barreira para este passer
	            }
	        }
	    }
	    return true; // posição livre ou passável para este passer
	}
	
	
	// Atualiza o estado dos objetos móveis, fazendo-os cair se não houver suporte
    public void updateFallingObjects() {
        for (GameObject obj : objects) {
            if (obj.hasGravity() && obj.hasSupport()== false) {
                obj.fall();
            }
        }
    }

    
    
	public void checkLevelCompletion() {    // 25/11/2025
		if (smallFish.isExited() && bigFish.isExited()) {
	     engine.loadNextLevel();
		}
	}      // usado no move gameCharacter não deixa criar o ciclo infinito e inicia proximo nivel.
    
    
    
    
    

    
    
    
    
 
	
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
    
    // Validar destino antes de procurar objetos ^
    private boolean isInBounds(Point2D pos) {
        if (pos == null) return false;
        int x = pos.getX(), y = pos.getY();
        return x >= 0 && x < LENGTH && y >= 0 && y < HEIGHT;
    }
    

    
    // tentativa de empurrar o objecto na posição pos em direcção dir
    public boolean tryPushObjectAt(Point2D pos, Vector2D dir, GameObject pusher) {
        for (GameObject obj : objects) {
            if (obj.getPosition().equals(pos) && obj instanceof Pushable) {
                Pushable pushable = (Pushable) obj;
                if (pushable.isPushable()) {
                    return pushable.push(dir, pusher);
                }
            }
        }
        return false;
    }
    

}
