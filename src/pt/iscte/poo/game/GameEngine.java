package pt.iscte.poo.game;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import objects.SmallFish;
import objects.BigFish;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;

public class GameEngine implements Observer {
    
    private static GameEngine instance = null;

    private Map<String,Room> rooms;
    private Room currentRoom;
    private int lastTickProcessed = 0;
    
    // Singleton
    private GameEngine() {
        rooms = new HashMap<String,Room>();
        loadGame();
        currentRoom = rooms.get("room0.txt");
        updateGUI();		
        SmallFish.getInstance().setRoom(currentRoom);
        BigFish.getInstance().setRoom(currentRoom);
    }

    // Thread-safe getter
    public static synchronized GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    private void loadGame() {
        File[] files = new File("./rooms").listFiles();
        if (files == null) return;
        for(File f : files) {
            rooms.put(f.getName(),Room.readRoom(f,this));
        }
    }

    @Override
    public void update(Observed source) {

        ImageGUI gui = ImageGUI.getInstance();

        if (gui.wasKeyPressed()) {
            int key = gui.keyPressed();

            // tecla R
            if (key == KeyEvent.VK_R) {
           //     restartCurrentRoom();
                return;
            }

            // Trocar peixe
            if (key == KeyEvent.VK_SPACE) {
                if (currentRoom != null) currentRoom.switchActiveFish();
                return;
            }

            // Movimento
            if (Direction.isDirection(key)) {
                Direction d = Direction.directionFor(key);
                if (currentRoom != null) currentRoom.moveActiveFish(d);
            }
        }

        // 2. Processar ticks (CP2)
        int t = gui.getTicks();
        while (lastTickProcessed < t) {
            processTick();
        }

        gui.update();
    }
    
    private void processTick() {		
        lastTickProcessed++;
    }

    public void updateGUI() {
        if(currentRoom!=null) {
            ImageGUI.getInstance().clearImages();
            ImageGUI.getInstance().addImages(currentRoom.getObjects());
        }
    }
    
}
