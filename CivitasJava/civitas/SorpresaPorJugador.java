package civitas;

import java.util.ArrayList;

public class SorpresaPorJugador extends Sorpresa {
    private int valor;
    
    SorpresaPorJugador(int val, String tex){
        super(tex);
        valor = val;
    }
    
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos) throws Exception{
        if ( jugadorCorrecto(actual, todos) ){
            informe(actual, todos);
			
            // Cobra al resto de jugadores
            String str = "Pagas " + valor + " al jugador " + todos.get(actual).getNombre() + "\n";
            SorpresaPagarCobrar pagar = new SorpresaPagarCobrar(valor * -1, str);
            
            for (int i = 0; i < todos.size(); i++)
                if (i != actual)
                    pagar.aplicarAJugador(i, todos);
			
            // Recibe el dinero
            str = "Recibes " + valor + " de cada jugador (" + valor*(todos.size()-1) + " en total)\n";
            SorpresaPagarCobrar cobrar = new SorpresaPagarCobrar(valor * (todos.size()-1), str);
            cobrar.aplicarAJugador(actual, todos);
        }
    }
}
