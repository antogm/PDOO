package civitas;

import java.util.ArrayList;

public class SorpresaSalirCarcel extends Sorpresa {
    private MazoSorpresas mazo;
    
    SorpresaSalirCarcel(MazoSorpresas mazo, String tex){
        super(tex);
        this.mazo = mazo;
    }
    
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos) throws Exception{
        if ( jugadorCorrecto(actual, todos) ){
            informe(actual, todos);
            
            boolean tieneSalvoconducto = false;
            for (int i = 0; i < todos.size(); i++)
                if (todos.get(i).tieneSalvoConducto() )
                    tieneSalvoconducto = true;
            
            if (!tieneSalvoconducto){
                todos.get(actual).obtenerSalvoconducto(this);
                salirDelMazo();
            }
        }
    }
    
    void salirDelMazo(){
        mazo.inhabilitarCartaEspecial(this);
    }
    
    void usada(){
        mazo.habilitarCartaEspecial(this);
    }
}
