package pt.iscte.poo.game;

import objects.*;

/**
 * Factory simples para criar GameObject a partir do caractere do ficheiro de mapa.
 * Retorna null se o carácter não for tratado (log/ignore).
 */
public final class GameObjectFactory {

    private GameObjectFactory() { }

    public static GameObject create(char c, Room room) {
        switch (c) {
            case 'W': {
                Wall w = new Wall(room);
                return w;
            }
            case 'H': {
                SteelHorizontal h = new SteelHorizontal(room);
                return h;
            }
            case 'B': {
                BigFish bf = BigFish.getInstance();
                bf.setRoom(room);
                return bf;
            }
            case 'S': {
                SmallFish sf = SmallFish.getInstance();
                sf.setRoom(room);
                return sf;
            }
            case 'X': {
            	HoleWall hw = new HoleWall(room);
                return hw;
            }
            case 'V': {
            	SteelVertical v = new SteelVertical(room);
                return v;
            }
            
            case 'R': {
            	Rock r = new Rock(room);
            	return r;
            }
            
            
            case ' ':
                // espaço = água -> a água é criada globalmente no layer 0; aqui devolvemos null
                return null;
            default:
                // Caracteres ainda não implementados (C,R,A,b,T,Y,V,X...). Log para debug.
                System.err.println("GameObjectFactory: caractere não mapeado '" + c + "'");
                return null;
        }
    }
}