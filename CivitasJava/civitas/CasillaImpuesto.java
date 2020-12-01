package civitas;

import java.util.ArrayList;

public class CasillaImpuesto extends Casilla {
    private float importe;
    
    CasillaImpuesto (float cantidad, String s){
        super(s);
        importe = cantidad;
    }
    
    @Override
    void recibeJugador (int iactual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(iactual,todos)){
            informe(iactual,todos);
            todos.get(iactual).pagaImpuesto(importe);
        }
    }
}
