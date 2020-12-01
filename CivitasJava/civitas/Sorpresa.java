package civitas;

import java.util.ArrayList;

public abstract class Sorpresa {
    
    // Atributos
    private String texto;
    
    // Métodos
    Sorpresa (String tex){
        texto = tex;
    }
    
    void informe (int actual, ArrayList<Jugador> todos){
        if ( jugadorCorrecto(actual, todos) ){
            String str = "Aplicando sorpresa " + this.toString() + " al jugador " + todos.get(actual).getNombre() + "\n";
            Diario.getInstance().ocurreEvento(str);
        }     
    }
    
    abstract void aplicarAJugador (int actual, ArrayList<Jugador> todos) throws Exception;
        
    public boolean jugadorCorrecto (int actual, ArrayList<Jugador> todos){
        return (actual < todos.size()) && (actual >= 0);
    }
    
    @Override
    public String toString (){
        return texto;
    };
}
