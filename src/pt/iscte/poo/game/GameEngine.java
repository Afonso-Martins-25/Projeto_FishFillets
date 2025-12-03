package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import objects.SmallFish;
import objects.BigFish;
import objects.GameCharacter;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;

public class GameEngine implements Observer {
	
	
	private static GameEngine instance;
	private Map<String,Room> rooms;
	private Room currentRoom;
	private int lastTickProcessed = 0;	
	private GameCharacter activeFish;
	private GameCharacter inactiveFish;
	
	private int currentLevelNumber = 0;
	
	
	GameEngine() throws FileNotFoundException  {
		rooms = new HashMap<String,Room>();
		loadGame();
		currentRoom = rooms.get("room0.txt");
		updateGUI();		
		SmallFish.getInstance().setRoom(currentRoom);
		BigFish.getInstance().setRoom(currentRoom);
		activeFish = SmallFish.getInstance();  // começa com SmallFish ativo
		inactiveFish = BigFish.getInstance();
	}

	private void loadGame() throws FileNotFoundException  {
		File[] files = new File("./rooms").listFiles();
		for(File f : files) {
			rooms.put(f.getName(),Room.readRoom(f,this));
		}
	}

	@Override
	public void update(Observed source) {

		if (ImageGUI.getInstance().wasKeyPressed()) {
            int k = ImageGUI.getInstance().keyPressed();

            if (k == java.awt.event.KeyEvent.VK_SPACE) {
                switchActiveFish();
                
            } else if (k == java.awt.event.KeyEvent.VK_R) {
                restartCurrentLevel();
                
            } else if (Direction.isDirection(k)) {
                activeFish.move(Direction.directionFor(k).asVector());
            }
        }
		
		int t = ImageGUI.getInstance().getTicks();
		while (lastTickProcessed < t) {
			processTick();
		}
		ImageGUI.getInstance().update();
	}

	private void processTick() {		
		lastTickProcessed++;
		
		  if (currentRoom != null) {
		        currentRoom.updateFallingObjects();  // chama a lógica que faz os móveis caírem
	//	        currentRoom.checkLevelCompletion();
		        
		  }
		    
		    updateGUI();
	}

	
	
	// loadnextlevel semelhante a restart sugest criar um load(level)
	
	public void loadNextLevel() {
        currentLevelNumber++;
        String nextRoomName = "room" + currentLevelNumber + ".txt";
        
        if (!rooms.containsKey(nextRoomName)) {
            // Fim de jogo: não existe próximo nível
            endGame(currentLevelNumber - 1);
            return;
        }
        
        // Carregar novo nível
        currentRoom = rooms.get(nextRoomName);
        
        // Resetar peixes para o novo nível
        SmallFish.getInstance().setRoom(currentRoom);
        BigFish.getInstance().setRoom(currentRoom);
        SmallFish.getInstance().hasExited = false;
        BigFish.getInstance().hasExited = false;
        
        // Posicionar peixes nas coordenadas iniciais
        if (currentRoom.getSmallFishStartingPosition() != null) {
            SmallFish.getInstance().setPosition(currentRoom.getSmallFishStartingPosition());
        }
        if (currentRoom.getBigFishStartingPosition() != null) {
            BigFish.getInstance().setPosition(currentRoom.getBigFishStartingPosition());
        }
        
        // Resetar peixe ativo e inativo
        activeFish = SmallFish.getInstance();
        inactiveFish = BigFish.getInstance();
        
        // Atualizar GUI
        updateGUI();
        ImageGUI.getInstance().setStatusMessage("Nivel " + (currentLevelNumber + 1) + " carregado");
    }
	
	public void restartCurrentLevel() {

	    String currentRoomName = "room" + currentLevelNumber + ".txt";
	    File roomFile = new File("./rooms/" + currentRoomName);

	    // Recarregar Room
	    Room reloadedRoom = Room.readRoom(roomFile, this);

	    rooms.put(currentRoomName, reloadedRoom);
	    currentRoom = reloadedRoom;

	    // Resetar estado dos peixes
	    SmallFish.getInstance().setRoom(currentRoom);
	    BigFish.getInstance().setRoom(currentRoom);
	    SmallFish.getInstance().hasExited = false;
	    BigFish.getInstance().hasExited = false;

	    if (currentRoom.getSmallFishStartingPosition() != null) {
	        SmallFish.getInstance().setPosition(currentRoom.getSmallFishStartingPosition());
	    }
	    if (currentRoom.getBigFishStartingPosition() != null) {
	        BigFish.getInstance().setPosition(currentRoom.getBigFishStartingPosition());
	    }

	    activeFish = SmallFish.getInstance();
	    inactiveFish = BigFish.getInstance();

	    updateGUI();
	    ImageGUI.getInstance().setStatusMessage("Nivel reiniciado");
	}


	
	public void switchActiveFish() {
        // Validar que o outro peixe não saiu
        if (inactiveFish.isExited()) {
            return; // Não pode alternar; outro peixe saiu
        }
        
        GameCharacter temp = activeFish;
        activeFish = inactiveFish;
        inactiveFish = temp;
        
        updateGUI();
    }
	
	private void endGame(int lastLevelCompleted) {
        ImageGUI.getInstance().showMessage(
            "Vitória","Parabens! Completou todos os " + (lastLevelCompleted + 1) + " niveis!"
        );
    }
	
	
	public void updateGUI() {
		if(currentRoom!=null) {
			ImageGUI.getInstance().clearImages();
			ImageGUI.getInstance().addImages(currentRoom.getObjects());
		}
	}
	
	
	public static GameEngine getInstance() throws FileNotFoundException {
		if(instance==null) {
			instance=new GameEngine();
		}
		return instance;
	}
	
}
