package civitas;

import java.util.ArrayList;

public class Casilla {
    
    // Atributos
    static private int carcel;
    private float importe;
    private String nombre;
    TipoCasilla tipo;
    
    MazoSorpresas mazo;                 // tipo sorpresa
    Sorpresa sorpresa;                  // tipo sorpresa
    TituloPropiedad tituloPropiedad;    // tipo calle
    
    /**
     * Constructor
     * @param s nombre de la casilla 
     */
    Casilla ( String s ){   //descanso
        init();
        nombre = s;
        tipo = TipoCasilla.DESCANSO;
    }
    
    Casilla (TituloPropiedad titulo){   // calle
        init();
        nombre = titulo.getNombre();
        tipo = TipoCasilla.CALLE;
        tituloPropiedad = titulo;
    }
    
    Casilla (float cantidad, String s){    // impuesto
        init();
        tipo = TipoCasilla.IMPUESTO;
        importe = cantidad;
        nombre = s;
    }
    
    Casilla (int numCasillaCarcel, String s){   // juez
        init();
        tipo = TipoCasilla.JUEZ;
        carcel = numCasillaCarcel;
        nombre = s;
    }
    
    Casilla (MazoSorpresas m, String s){    // sorpresa
        init();
        tipo = TipoCasilla.SORPRESA;
        mazo = m;
        nombre = s;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    TituloPropiedad getTituloPropiedad(){
        return tituloPropiedad;
    }
    
    private void informe (int iactual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(iactual, todos)){
            String nombreJugador = todos.get(iactual).getNombre();
            String evento = "El jugador " + nombreJugador + " ha caído en la casilla " + nombre;
            Diario.getInstance().ocurreEvento(evento);
        }
    }
    
    private void init (){
        importe = 0;
        nombre = "Casilla sin nombre";
        tipo = TipoCasilla.DESCANSO;
        mazo = new MazoSorpresas();
        tituloPropiedad = new TituloPropiedad("Propiedad sin inicializar", 0, 0, 0, 0, 0);
    }
    
    /**
     * Comprueba si el primer parámetro es un índice válido para acceder al ArrayList de jugadores
     * @param iactual índice del jugador actual
     * @param todos ArrayList de jugadores
     * @return true si es un índice válido
     */
    public boolean jugadorCorrecto(int iactual, ArrayList<Jugador> todos){
        if (iactual < todos.size() && iactual >= 0)
            return true;
        else
            return false;
    }
    
    void recibeJugador (int iactual, ArrayList<Jugador> todos){
        switch(tipo){
        case CALLE:
            recibeJugador_calle(iactual, todos);
            break;
				
        case IMPUESTO:
            recibeJugador_impuesto(iactual, todos);
            break;
				
        case JUEZ:
            recibeJugador_juez(iactual, todos);
            break;
				
        case SORPRESA:
            recibeJugador_sorpresa(iactual, todos);
            break;
				
        default:
            informe(iactual, todos);
            break;
	}
    }
 
    private void recibeJugador_calle (int iactual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(iactual, todos)){
            informe(iactual, todos);
            Jugador jugador = todos.get(iactual);
			
            if (!tituloPropiedad.tienePropietario()){
		        jugador.puedeComprarCasilla();
		        tituloPropiedad.tramitarAlquiler(jugador);
            }
        }
    }
    
    private void recibeJugador_impuesto (int iactual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(iactual,todos)){
            informe(iactual,todos);
            todos.get(iactual).pagaImpuesto(importe);
        }
    }
    
    private void recibeJugador_juez (int iactual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(iactual,todos)){
            informe(iactual,todos);
            todos.get(iactual).encarcelar(carcel);
        }
    }
    
    private void recibeJugador_sorpresa (int iactual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(iactual, todos)){
            Sorpresa sorpresa = mazo.siguiente();
            informe(iactual, todos);
            sorpresa.aplicarAJugador(iactual, todos);
	    }
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
