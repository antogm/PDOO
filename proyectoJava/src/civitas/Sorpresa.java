package civitas;

import java.util.ArrayList;

public class Sorpresa {
    
    // Atributos
    private String texto;
    private int valor;
    private TipoSorpresa tipo;
    private MazoSorpresas mazo;
    private Tablero tablero;
    
    // Métodos
    void aplicarAJugador (int actual, ArrayList<Jugador> todos){
        switch (tipo){
            case IRCARCEL:
                this.aplicarAJugador_irCarcel(actual, todos);
                break;
                
            case IRCASILLA:
                this.aplicarAJugador_irACasilla(actual, todos);
                break;
                
            case PAGARCOBRAR:
                this.aplicarAJugador_pagarCobrar(actual, todos);
                break;
                
            case PORCASAHOTEL:
                this.aplicarAJugador_porCasaHotel(actual, todos);
                break;
                
            case PORJUGADOR:
                this.aplicarAJugador_porJugador(actual, todos);
                break;
                
            case SALIRCARCEL:
                this.aplicarAJugador_salirCarcel(actual, todos);
                break;
        };
    }
    
    private void aplicarAJugador_irACasilla (int actual, ArrayList<Jugador> todos){
        if ( jugadorCorrecto(actual, todos) ){
            
            informe(actual, todos);
            int casillaActual = todos.get(actual).getNumCasillaActual();
            int tirada = tablero.calcularTirada(casillaActual, valor);
            int nuevaPos = tablero.nuevaPosicion(casillaActual, tirada);
            todos.get(actual).moverACasilla(nuevaPos);
            
            Casilla casillaSorpresa = tablero.getCasilla(nuevaPos);
            casillaSorpresa.recibeJugador(actual, todos);
        }
    }
    
    private void aplicarAJugador_irCarcel (int actual, ArrayList<Jugador> todos){
        if ( jugadorCorrecto(actual, todos) ){
            informe(actual, todos);
            todos.get(actual).encarcelar( tablero.getCarcel() );
        }
            
    }
    
    private void aplicarAJugador_pagarCobrar (int actual, ArrayList<Jugador> todos){
        if ( jugadorCorrecto(actual, todos) ){
            informe(actual, todos);
            todos.get(actual).modificarSaldo(valor);
        }
    }
    
    private void aplicarAJugador_porCasaHotel (int actual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(actual, todos) ){
            informe(actual, todos);
            todos.get(actual).modificarSaldo( valor * todos.get(actual).cantidadCasasHoteles() );
        }
    }
    
    private void aplicarAJugador_porJugador (int actual, ArrayList<Jugador> todos){
        if ( jugadorCorrecto(actual, todos) ){
			informe(actual, todos);
			
			// Cobra al resto de jugadores
            String str = "Pagas " + valor + " al jugador " + todos.get(actual).getNombre() + "\n";
            Sorpresa pagar = new Sorpresa(TipoSorpresa.PAGARCOBRAR, valor * -1, str);
            
            for (int i = 0; i < todos.size(); i++)
                if (i != actual)
                    pagar.aplicarAJugador(i, todos);
			
			// Recibe el dinero
            str = "Recibes " + valor + " de cada jugador (" + valor*(todos.size()-1) + " en total)\n";
            Sorpresa cobrar = new Sorpresa(TipoSorpresa.PAGARCOBRAR, valor * (todos.size()-1), str);
            cobrar.aplicarAJugador(actual, todos);
        }
    }
    
    private void aplicarAJugador_salirCarcel (int actual, ArrayList<Jugador> todos){
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
    
    /**
     * Informa al diario que se está aplicando una sorpresa al jugador actual
     * @param actual índice del jugador actual
     * @param todos ArrayList de jugadores
     */
    private void informe (int actual, ArrayList<Jugador> todos){
        if ( jugadorCorrecto(actual, todos) ){
            String str = "Se ha aplicado una sorpresa " + toString() + " al jugador " + todos.get(actual).getNombre() + "\n";
            Diario.getInstance().ocurreEvento(str);
        }     
    }
    
    private void init(){
        valor = -1;
        mazo = null;
        tablero = null;
    }
    
    /**
     * Comprueba si el primer parámetro es un índice válido para acceder al ArrayList de jugadores
     * @param actual
     * @param todos
     * @return true si es un índice válido
     */
    public boolean jugadorCorrecto (int actual, ArrayList<Jugador> todos){
        if (actual < todos.size() && actual >= 0)
            return true;
        else
            return false;
    }
    
    /**
     * Si el tipo de sorpresa es la que evita la cárcel, la inhabilita en el mazo de sorpresas
     */
    void salirDelMazo(){
        if (tipo == TipoSorpresa.SALIRCARCEL)
            mazo.inhabilitarCartaEspecial(this);
    }
    
    Sorpresa(TipoSorpresa _tipo, Tablero _tablero){   // sorpresa ircárcel
        init();
        tipo = _tipo;
        tablero = _tablero;
    }
    
    Sorpresa(TipoSorpresa _tipo, Tablero _tablero, int _valor, String _texto){  // sorpresa ir a casilla
        init();
        tipo = _tipo;
        tablero = _tablero;
        valor = _valor;
        texto = _texto;
    }
    
    Sorpresa(TipoSorpresa _tipo, int _valor, String _texto){   // resto de sorpresas
        init();
        tipo = _tipo;
        valor = _valor;
        texto = _texto;
    }
    
    Sorpresa (TipoSorpresa _tipo, MazoSorpresas _mazo){   // sorpresa evitar carcel
        init();
        tipo = _tipo;
        mazo = _mazo;
    }
    
    /**
     * @return Nombre de la sorpresa 
     */
    @Override
    public String toString (){
        String str;
        switch (tipo){
            case IRCARCEL:
                str = "IRCARCEL";
                break;
                
            case IRCASILLA:
                str = "IRCASILLA";
                break;
                
            case PAGARCOBRAR:
                str = "PAGARCOBRAR";
                break;
                
            case PORCASAHOTEL:
                str = "PORCASAHOTEL";
                break;
                
            case PORJUGADOR:
                str = "PORJUGADOR";
                break;
                
            case SALIRCARCEL:
                str = "SALIRCARCEL";
                break;
            default: str = " ";
        }
        return str;
    };
    
    /**
     * Si el tipo de sorpresa es la que evita la cárcel, la habilita en el mazo de sorpresas
     */
    void usada(){
        if (tipo == TipoSorpresa.SALIRCARCEL)
            mazo.habilitarCartaEspecial(this);
    }
}
