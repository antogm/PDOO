package civitas;
import java.util.ArrayList;

public class SorpresaIrCasilla extends Sorpresa {
    private int valor;
    private Tablero tablero;
    
    SorpresaIrCasilla(Tablero _tablero, int _valor, String _texto){
        super(_texto);
        tablero = _tablero;
        valor = _valor;
    }
    
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos) throws Exception{
        if (jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            int casillaActual = todos.get(actual).getNumCasillaActual();
            int tirada = tablero.calcularTirada(casillaActual, valor);
            int nuevaPos = tablero.nuevaPosicion(casillaActual, tirada);
            todos.get(actual).moverACasilla(nuevaPos);
            tablero.getCasilla(nuevaPos).recibeJugador(actual, todos);
        }
    }
}
