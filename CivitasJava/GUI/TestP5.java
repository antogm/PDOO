package GUI;

import java.util.ArrayList;
import civitas.CivitasJuego;

public class TestP5 {
    public static void main(String[] argv) {
        CivitasView vista = new CivitasView();
        Dado.createInstance(vista);
        Dado.getInstance().setDebug(true);
        CapturaNombres capt = new CapturaNombres(vista, true);
        ArrayList<String> nombres = capt.getNombres();
        CivitasJuego juego = new CivitasJuego(nombres);
        Controlador controlador = new Controlador(juego, vista);
        vista.setCivitasJuego(juego);
        controlador.juega();
    }
}
