package civitas;

import java.util.ArrayList;

public class Casilla {
    
    // Atributos
    private String nombre;
    
    /**
     * Constructor
     * @param s nombre de la casilla 
     */
    Casilla ( String s ){
        nombre = s;
    }
          
    protected void informe (int iactual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(iactual, todos)){
            String nombreJugador = todos.get(iactual).getNombre();
            String evento = "El jugador " + nombreJugador + " ha caído en la casilla " + nombre;
            Diario.getInstance().ocurreEvento(evento);
        }
    }
    
    public boolean jugadorCorrecto(int iactual, ArrayList<Jugador> todos){
        if (iactual < todos.size() && iactual >= 0)
            return true;
        else
            return false;
    }
    
    void recibeJugador (int iactual, ArrayList<Jugador> todos) throws Exception{
        informe(iactual, todos);
    }
        
    @Override
    public String toString(){
        return nombre;
    }
}
