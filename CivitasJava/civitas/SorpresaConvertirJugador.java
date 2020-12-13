package civitas;

import java.util.ArrayList;

public class SorpresaConvertirJugador extends Sorpresa {
    
    private float fianza;
    
    SorpresaConvertirJugador(float fianza, String tex){
        super(tex);
        this.fianza = fianza;
    }

    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos){
        if ( jugadorCorrecto(actual, todos) ){
            informe(actual, todos);
            todos.set(actual, new JugadorEspeculador(todos.get(actual), fianza));
        }       
    }
}
