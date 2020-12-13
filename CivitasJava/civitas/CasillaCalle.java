package civitas;

import java.util.ArrayList;

public class CasillaCalle extends Casilla {
    
    // Atributos
    TituloPropiedad tituloPropiedad;
    
    // Métodos
    CasillaCalle (TituloPropiedad titulo){
        super(titulo.getNombre());
        tituloPropiedad = titulo;
    }
            
    TituloPropiedad getTituloPropiedad(){
        return tituloPropiedad;
    }
              
    @Override
    void recibeJugador (int iactual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(iactual, todos)){
            informe(iactual, todos);
            Jugador jugador = todos.get(iactual);
              
            if (!tituloPropiedad.tienePropietario()){
		        jugador.puedeComprarCasilla();
            }else{
                tituloPropiedad.tramitarAlquiler(jugador);
            };
        };
    };
};
