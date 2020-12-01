package civitas;

import java.util.ArrayList;

public class SorpresaPagarCobrar extends Sorpresa {
    private int valor;
    
    SorpresaPagarCobrar(int val, String tex){
        super(tex);
        valor = val;
    }
    
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos) throws Exception{
        if ( jugadorCorrecto(actual, todos) ){
            informe(actual, todos);
            todos.get(actual).modificarSaldo(valor);
        }
    }
}
