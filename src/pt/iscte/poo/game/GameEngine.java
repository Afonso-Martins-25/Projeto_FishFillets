package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import objects.SmallFish;
import objects.BigFish;
import objects.GameCharacter;
import objects.HighscoreManager;
import objects.ScoreEntry;
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
	
	private boolean gameFinished = false;
	private int currentLevelNumber = 0;
	private int totalMoves = 0;
	private int totalTime = 0;
	
	
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
	
	public static GameEngine getInstance() throws FileNotFoundException {
		if(instance==null) {
			instance=new GameEngine();
		}
		return instance;
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
            	try {
                    restartCurrentLevel();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                
            } else if (Direction.isDirection(k)) {
                try {
					activeFish.move(Direction.directionFor(k).asVector());
					currentRoom.updateDeaths();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
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
			  if(!gameFinished) {
				  currentRoom.updateFallingObjects();  // chama a lógica que faz os móveis caírem
		          currentRoom.incrementTimeCount();
			  }
		        
		        
		  }
		    
		    updateGUI();
	}

	
    private void loadLevel(int levelNumber, boolean isRestart) throws FileNotFoundException {
        String roomName = "room" + levelNumber + ".txt";
        
        if (!isRestart && !rooms.containsKey(roomName)) {
            // Fim de jogo: não existe próximo nível
            endGameWin(levelNumber - 1);
            return;
        }
        
        File roomFile = new File("./rooms/" + roomName);
        Room loadedRoom = Room.readRoom(roomFile, this);
        rooms.put(roomName, loadedRoom);
        currentRoom = loadedRoom;
        
        // Resetar estado dos peixes
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
        
        //para dar reset e os objetos continuarem com gravidade mesmo com morte de peixe
        //ou tirar setgameFinished em endloss
        setGameFinished(false);
        
        // Atualizar GUI
        updateGUI();
        
        if (isRestart) {
            ImageGUI.getInstance().setStatusMessage("Nivel reiniciado");
        } else {
            ImageGUI.getInstance().setStatusMessage("Nivel " + (levelNumber + 1) + " carregado");
        }
    }
    
    public void loadNextLevel() throws FileNotFoundException {
        totalMoves += currentRoom.getMoveCount();
        totalTime += currentRoom.getTimeCount();
        currentLevelNumber++;
        loadLevel(currentLevelNumber, false);
    }
    
    public void restartCurrentLevel() throws FileNotFoundException {
        loadLevel(currentLevelNumber, true);
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
	
	private void endGameWin(int lastLevelCompleted) throws FileNotFoundException {
		setGameFinished(true);
		 totalMoves += currentRoom.getMoveCount();
		 totalTime += currentRoom.getTimeCount();
		ImageGUI.getInstance().showMessage(
            "Vitória","Parabens! Completou todos os " + (lastLevelCompleted + 1) + " niveis!"
        );
		// Highscores
	    HighscoreManager hm = new HighscoreManager();
	    hm.addScore(new ScoreEntry(totalMoves, totalTime));

	    hm.showHighscoreTable(hm.getTop10());
    }
	
	public void endGameLoss() {
		setGameFinished(true);
		ImageGUI.getInstance().showMessage(
	            "Derrota","Perdestes! Um dos peixes morreu!"
	        );
	}

	
	
	public void updateGUI() {
		if(currentRoom!=null) {
			ImageGUI.getInstance().clearImages();
			ImageGUI.getInstance().addImages(currentRoom.getObjects());
			ImageGUI.getInstance().setStatusMessage(
				    "Level " + currentLevelNumber +
				    " | Movimentos: " + currentRoom.getMoveCount() +
				    " | Tempo: " + currentRoom.getTimeCount()
				);
		}
	}
	
	
	public boolean isGameFinished() {
	    return gameFinished;
	}

	public void setGameFinished(boolean finished) {
	    this.gameFinished = finished;
	}
	
	
	
	
}
