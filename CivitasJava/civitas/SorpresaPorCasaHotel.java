package civitas;

import java.util.ArrayList;

public class SorpresaPorCasaHotel extends Sorpresa {
    private int valor;
    
    SorpresaPorCasaHotel(int valor, String tex){
        super(tex);
        this.valor = valor;
    }
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) throws Exception{
        if (jugadorCorrecto(actual, todos) ){
            informe(actual, todos);
            todos.get(actual).modificarSaldo( valor * todos.get(actual).cantidadCasasHoteles() );
        }
    }
}
