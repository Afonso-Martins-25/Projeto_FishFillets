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
	// private GameCharacter activeFish;
    private SmallFish smallFish = SmallFish.getInstance();
    private BigFish bigFish = BigFish.getInstance();
    private int currentLevelNumber = 0; // rastreia room0, room1, etc.
	
	public Room() {
		objects = new ArrayList<GameObject>();
	}
											// 25/11/2025
//	public SmallFish getSmallFish() {
//        return smallFish;
//    }
//
//    public BigFish getBigFish() {
//        return bigFish;
//    }
    
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
				
				
			case 'W':     //Wall	    // 25/11/2025
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
	
	public void checkLevelCompletion() {    // 25/11/2025
        if (smallFish.isExited() && bigFish.isExited()) {
            engine.loadNextLevel();
        }
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
            if (obj.hasGravity() && !hasSupport(obj)) {
                fall(obj);
            }
        }
    }
    
    
    public void updateLevel(GameObject f1,GameObject f2) {
    	if(f1.getPosition().getX() > LENGTH && f2.getPosition().getY() > HEIGHT) {
    		//engine.NextLevel();
    	}
    }
    
    
    // Verifica se o objeto tem suporte por baixo 
    private boolean hasSupport(GameObject obj) {
        Point2D pos = obj.getPosition();
        Point2D below = new Point2D(pos.getX(), pos.getY() + 1);
        
        // Se estiver no chão (por exemplo limite da sala)
        if (below.getY() >= HEIGHT) {
            return true;
        }

        for (GameObject other : objects) {
            if (other == obj) continue;
            if (other.getPosition().equals(below) && other.isSolid()) {
                return true;
            }
        }
        return false;
    }
    
    
 // Faz o objeto cair uma unidade para baixo
    private void fall(GameObject obj) {
        Point2D pos = obj.getPosition();
        obj.setPosition(pos.getX(), pos.getY() + 1);
    }
	
	// devolve o objecto da layer mais alta nessa posição (pode ser Water se for o único)
    public GameObject getTopObjectAt(Point2D pos) {
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

    private boolean isInBounds(Point2D pos) {
        if (pos == null) return false;
        int x = (int) pos.getX(), y = (int) pos.getY();
        return x >= 0 && x < LENGTH && y >= 0 && y < HEIGHT;
    }

	 // tentativa de empurrar o objecto na posição pos em direcção dir
    // suporta empurrar em cadeia (recursivo)
    public boolean tryPushObjectAt(Point2D pos, Vector2D dir, GameObject pusher) {
        if (!isInBounds(pos)) return false; // nada a empurrar fora dos limites
        GameObject obj = getTopObjectAt(pos);
        if (obj == null) return false;
        if (!obj.isPushable()) return false;

        Point2D nextPos = pos.plus(dir);
        if (!isInBounds(nextPos)) return false; // não empurrar para fora da sala

        GameObject destTop = getTopObjectAt(nextPos);

        // se destino tem objecto e é pushable, empurra esse primeiro (cadeia)
        if (destTop != null && destTop != obj && !destTop.isPassable(obj)) {
            if (destTop.isPushable()) {
                if (!tryPushObjectAt(nextPos, dir, pusher)) {
                    return false; // não foi possível empurrar cadeia
                }
            } else {
                // se não puder empurrar e não for passável para o obj, bloqueado
                if (!destTop.isPassable(obj)) return false;
            }

			}

        // se chegou aqui, podemos mover o obj para nextPos
        obj.setPosition(nextPos);
     // chamar hook para efeitos especiais (ex.: bomba explode)
        obj.onPushedBy(pusher, dir);
        if (engine != null) engine.updateGUI();
        return true;
    }

}
