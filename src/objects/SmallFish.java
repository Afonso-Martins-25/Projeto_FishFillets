package objects;

import pt.iscte.poo.game.Room;

public class SmallFish extends GameCharacter {

	private static SmallFish sf = new SmallFish(null);
	
	private SmallFish(Room room) {
		super(room);
	}

	public static SmallFish getInstance() {
		return sf;
	}
	
	public void resetState() {
        // TODO: repor campos concretos da classe, por exemplo:
        // this.setAlive(true);
        // this.setSelected(false);
        // this.clearCarriedObjects();
    }
	
	@Override
	public String getName() {
		return "smallFishLeft";
	}

	@Override
	public int getLayer() {
		return 1;
	}

}
