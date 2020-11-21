package juegoTexto;

import civitas.CivitasJuego;
import civitas.Dado;
import java.util.ArrayList;

public class TestP3 {
	public static void main(String[] argv) {
		ArrayList<String> nombres = new ArrayList<>();
		nombres.add("jugador1");
		nombres.add("jugador2");
		nombres.add("jugador3");
		nombres.add("jugador4");
		
		CivitasJuego juego = new CivitasJuego(nombres);
		VistaTextual vista = new VistaTextual();
		Dado.getInstance().setDebug(true);
		Controlador controlador = new Controlador(juego, vista);
		controlador.juega();
	}
}
