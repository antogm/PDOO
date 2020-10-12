package civitas;

import java.util.ArrayList;

public class TestP2 {
    public static void main(String[] argv) {
        System.out.println("TestP2:");
        
        ArrayList<String> nombres = new ArrayList<>(4);
        for (int i = 0; i < 4; i++){
            String nombre = "Jugador" + i;
            nombres.add(nombre);
        }
        
        CivitasJuego juego = new CivitasJuego(nombres);
        
        System.out.println(juego.infoJugadorTexto());
    }
}
