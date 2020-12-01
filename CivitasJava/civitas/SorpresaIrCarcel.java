package civitas;

import java.util.ArrayList;

public class SorpresaIrCarcel extends Sorpresa {
    private Tablero tablero;
    private int numCasillaCarcel;
    
    SorpresaIrCarcel(Tablero tab, int numCasillaCarcel, String tex){
        super(tex);
        tablero = tab;
        this.numCasillaCarcel = numCasillaCarcel;
    }
    
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos) throws Exception{
        if ( jugadorCorrecto(actual, todos) ){
            informe(actual, todos);
            todos.get(actual).encarcelar( tablero.getCarcel() );
        }       
    }
}
