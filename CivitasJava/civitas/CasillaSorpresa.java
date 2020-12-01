package civitas;

import java.util.ArrayList;

public class CasillaSorpresa extends Casilla {
    MazoSorpresas mazo;
    Sorpresa sorpresa;
    
    CasillaSorpresa(MazoSorpresas m, String s){
        super(s);
        mazo = m;
    }
    
    @Override
    void recibeJugador (int iactual, ArrayList<Jugador> todos) throws Exception{
        if (jugadorCorrecto(iactual, todos)){
            informe(iactual, todos);
            mazo.siguiente().aplicarAJugador(iactual, todos);
	}
    }
}
