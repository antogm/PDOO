package civitas;

import java.util.ArrayList;

public class CasillaJuez extends Casilla {
    static private int carcel;
    
    CasillaJuez(int numCasillaCarcel, String s){
        super(s);
        carcel = numCasillaCarcel;
    }
    
    @Override
    void recibeJugador (int iactual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(iactual,todos)){
            informe(iactual,todos);
            todos.get(iactual).encarcelar(carcel);
        }
    }
}
