package objects;

import pt.iscte.poo.game.Room;

public class GameObjectFactory {

    public static GameObject create(char symbol, Room r) {

        switch (symbol) {

            case 'W': return new Wall(r);
            case 'B': return BigFish.getInstance();
            case 'S': return SmallFish.getInstance();
            case 'H': return new SteelHorizontal(r);
            case 'V': return new SteelVertical(r);
            case 'C': return new Cup(r);
            case 'R': return new Rock(r);
            case 'A': return new Anchor(r);
            case 'b': return new Bomb(r);
            case 'T': return new Trap(r);
            case 'Y': return new Trunk(r);
            case 'X': return new HoledWall(r);
            case 'F': return new Buoy(r);

            // se for água ou espaço vazio, devolve null
            default: return null;
        }
    }
}