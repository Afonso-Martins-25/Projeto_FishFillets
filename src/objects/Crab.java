package objects;

import pt.iscte.poo.game.Room;


//falta criar a atribuição da letra para a boia e o case para a construir

// apenas um por pedra quando a pedra é arrastada a primeira vez. não cria se não haver espaço em cima ---
// move-se aleatoriamente quando os peixes mexem --- afunda ao n ser suportado -- não é empurrado -- passa a parede com buraco
// mata peixe pequeno se toca nele --- é morto se passar na armadilha ou tocar no peixe grande.

public class Crab extends GameCharacter {

	private static Crab bf = new Crab(null);
	
	private Crab(Room room) {
		super(room);
	}

	public static Crab getInstance() {
		return bf;
	}
	
	@Override
	public String getName() {
		return "krab";
	}

	@Override
	public int getLayer() {
		return 2;
	}
	
}