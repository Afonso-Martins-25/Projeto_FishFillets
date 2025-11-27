package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import objects.*;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Room {
	
	
	private int widthInTiles = 10;
    private int heightInTiles = 10;
	private List<GameObject> objects;
	private String roomName;
	private GameEngine engine;
	private Point2D smallFishStartingPosition;
	private Point2D bigFishStartingPosition;
	private GameCharacter activeFish;
	private SmallFish smallFish = SmallFish.getInstance();
	private BigFish bigFish = BigFish.getInstance();
	
	
	public Room() {
		objects = new ArrayList<GameObject>();
	}
	
	public SmallFish getSmallFish() {
        return smallFish;
    }

    public BigFish getBigFish() {
        return bigFish;
    }

    public GameCharacter getActiveFish() {
        return activeFish;
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
	
	public static Room readRoom(File f, GameEngine engine) {
        Room r = new Room();
        r.setEngine(engine);
        r.setName(f.getName());

        List<String> lines = new ArrayList<>();
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Room file not found: " + f, e);
        }

        if (lines.isEmpty()) return r;

        // calcular dimensão do mapa (largura = maior linha)
        int height = lines.size();
        int width = 0;
        for (String ln : lines) if (ln.length() > width) width = ln.length();

        // 1) preencher toda a grelha com água (layer 0)
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Water water = new Water(r);
                water.setPosition(x, y);
                r.addObject(water);
            }
        }

        // 2) sobrepor restantes objetos conforme ficheiro (layer 1). ' ' => manter água
        for (int y = 0; y < height; y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c == ' ') continue; // já tem água por baixo
                objects.GameObject obj = GameObjectFactory.create(c, r);
                if (obj != null) {
                    obj.setPosition(x, y);
                    r.addObject(obj);
                }
            }
        }

        
        r.setActiveFish(r.getSmallFish() != null ? r.getSmallFish() : r.getBigFish());
        return r;
    }
	
	public void moveActiveFish(Direction d) {
	    Point2D newPos = activeFish.getPosition().plus(d.asVector());

	    if (canMoveTo(newPos)) {
	        activeFish.setPosition(newPos);
	        engine.updateGUI();
	    }
	}
	
	public void setActiveFish(GameCharacter fish) {
        this.activeFish = fish;
        // opcional: colocar peixe na posição inicial se definida e peixe não tiver posição
        if (fish != null) {
            if (fish instanceof SmallFish && smallFishStartingPosition != null)
                fish.setPosition(smallFishStartingPosition);
            else if (fish instanceof BigFish && bigFishStartingPosition != null)
                fish.setPosition(bigFishStartingPosition);
        }
        if (engine != null) engine.updateGUI();
    }
	
	public void switchActiveFish() {
	    activeFish = (activeFish == smallFish ? bigFish : smallFish);
	}
	
//	private boolean isInside(Point2D p) {
//        if (p == null) return false;
//        int w = (this.widthInTiles > 0) ? this.widthInTiles : 10;
//        int h = (this.heightInTiles > 0) ? this.heightInTiles : 10;
//        return p.getX() >= 0 && p.getX() < w && p.getY() >= 0 && p.getY() < h;
//    }
//	
//	public boolean areBothFishOutside() {
//        // se não existirem instâncias, considera não completo
//        if (smallFish == null || bigFish == null) return false;
//        return !isInside(smallFish.getPosition()) && !isInside(bigFish.getPosition());
//    }
	
	public boolean canMoveTo(Point2D pos) {

        // bloqueia se não há peixe activo
        if (activeFish == null)
            return false;

//        // Se o destino está fora da grelha permitimos o movimento (saída).
//        if (!isInside(pos)) {
//            return true;
//        }

        // 2 — Verifica objetos na posição
        for (GameObject obj : objects) {

            if (!obj.getPosition().equals(pos))
                continue;

            // 2.1 — É outro peixe? Não passa
            if (obj instanceof GameCharacter && obj != activeFish)
                return false;

            // 2.2 — Usa canBeTraversedBy para decidir (Wall/HoleWall/Water/... implementam)
            if (!obj.canBeTraversedBy(activeFish))
                return false;
        }

        // 3 — Se não encontrou nada que bloqueie → pode mover
        return true;
    }

	
}