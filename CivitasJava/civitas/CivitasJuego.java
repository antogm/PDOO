package civitas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CivitasJuego {
    
    // Atributos
    private int indiceJugadorActual;
    MazoSorpresas mazo;
    Tablero tablero;
    ArrayList<Jugador> jugadores;
    EstadosJuego estado;
    GestorEstados gestorEstados;
    
    // Métodos
   
    private void avanzaJugador(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
	int posicionActual = jugadorActual.getNumCasillaActual();
	int tirada = Dado.getInstance().tirar();
	int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);
	Casilla casilla = tablero.getCasilla(posicionNueva);
	this.contabilizarPasosPorSalida(jugadorActual);
	jugadorActual.moverACasilla(posicionNueva);
        try {
            casilla.recibeJugador(indiceJugadorActual, jugadores);
        } catch (Exception ex){}
	this.contabilizarPasosPorSalida(jugadorActual);
    }
    
    public boolean cancelarHipoteca(int ip){
        return getJugadorActual().cancelarHipoteca(ip);
    }
    
    public CivitasJuego(ArrayList<String> nombres){
        int numJugadores = nombres.size();
        
        // Inicializa los jugadores
        jugadores = new ArrayList<>(numJugadores);
        for (int i = 0; i < numJugadores; i++)
            jugadores.add(new Jugador(nombres.get(i)) );
        
        // Inicializa el gestor de estados y el estado inicial
        gestorEstados = new GestorEstados();
        estado = gestorEstados.estadoInicial();
        
        // Decide qué jugador empieza
        indiceJugadorActual = Dado.getInstance().quienEmpieza(numJugadores);
        
        // Inicializa el tablero y el mazo
        mazo = new MazoSorpresas();
        inicializarTablero(mazo);
        inicializarMazoSorpresas(tablero);    
    }
    
    public boolean comprar(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int numCasillaActual = jugadorActual.getNumCasillaActual();
        Casilla casilla = tablero.getCasilla(numCasillaActual);
        TituloPropiedad titulo = ((CasillaCalle) casilla).getTituloPropiedad();
        return jugadorActual.comprar(titulo);
    }
    
    /**
     * Método para construir una casa en una propiedad del jugador
     * @param ip índice de la propiedad en la lista de propiedades del jugador
     * @return true si la operación tiene éxito
     */
    public boolean construirCasa(int ip){
        return getJugadorActual().construirCasa(ip);
    }
    
    /**
     * Método para construir un hotel en una propiedad del jugador
     * @param ip índice de la propiedad en la lista de propiedades del jugador
     * @return true si la operación tiene éxito
     */
    public boolean construirHotel(int ip){
        return getJugadorActual().construirHotel(ip);
    }
    
    /**
     * Método para tramitar las recompensas de pasar por salida de un jugador
     * @param jugadorActual
     */
    private void contabilizarPasosPorSalida(Jugador jugadorActual){
        while(tablero.getPorSalida() > 0){
            jugadorActual.pasaPorSalida();
        };
    }
    
    public boolean finalDelJuego(){        
        for (int i = 0; i < jugadores.size(); i++)
            if (jugadores.get(i).enBancarrota())
                return true;
        
        return false;
    }
    
    public Casilla getCasillaActual(){
        int numCasilla = getJugadorActual().getNumCasillaActual();
        return tablero.getCasilla(numCasilla);
    }
    
    public Jugador getJugadorActual(){
        return jugadores.get(indiceJugadorActual);
    }
    
    public boolean hipotecar(int ip){
        return getJugadorActual().hipotecar(ip);
    }
    
    public String infoJugadorTexto(){
        String str = "Jugador actual: " + getJugadorActual().getNombre() 
                   + " en la casilla: " + getJugadorActual().getNumCasillaActual();
                
        return str;
    }
    
    /**
     * Método para inicializar el mazo de sorpresas del juego
     * @param tablero de juego
     */
    private void inicializarMazoSorpresas(Tablero tablero){
        mazo.alMazo(new SorpresaSalirCarcel(mazo, "Salvoconducto"));
        mazo.alMazo(new SorpresaPorJugador(-300, "PAGAR_PORJUGADOR_1"));
        mazo.alMazo(new SorpresaPorJugador(300, "COBRAR_PORJUGADOR_1"));
        mazo.alMazo(new SorpresaIrCasilla(tablero, 9, "Ir a casilla 9"));
        mazo.alMazo(new SorpresaIrCasilla(tablero, 19, "Ir a casilla 19"));
        mazo.alMazo(new SorpresaIrCarcel(tablero, tablero.getCarcel(), "Ir a la cárcel"));
        mazo.alMazo(new SorpresaPorCasaHotel(-100, "Pagar 100 por cada casa/hotel"));
        mazo.alMazo(new SorpresaPorCasaHotel(100, "Cobrar 100 por cada casa/hotel"));
        mazo.alMazo(new SorpresaPagarCobrar(500, "Cobrar 500"));
        mazo.alMazo(new SorpresaPagarCobrar(-500, "Pagar 500"));
    }
    
    /**
     * Méteodo para inicializar el tablero de juego
     * @param mazo mazo de sorpresas del juego 
     */
    private void inicializarTablero(MazoSorpresas mazo){
        int numCasillaCarcel = 5;
        tablero = new Tablero(numCasillaCarcel);
        
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle1", 10, 1.1f, 500, 600, 250)));
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle2", 10, 1.1f, 500, 600, 250)));
        tablero.añadeCasilla(new CasillaSorpresa(mazo, "Sorpresa1"));
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle3", 10, 1.1f, 500, 600, 250)));
        // Cárcel
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle4", 10, 1.1f, 500, 600, 250)));
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle5", 10, 1.1f, 500, 600, 250)));
        tablero.añadeCasilla(new CasillaSorpresa(mazo, "Sorpresa2"));
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle6", 10, 1.1f, 500, 600, 250)));
        tablero.añadeCasilla(new Casilla("PARKING"));
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle7", 10, 1.1f, 500, 600, 250)));
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle8", 10, 1.1f, 500, 600, 250)));
        tablero.añadeCasilla(new CasillaSorpresa(mazo, "Sorpresa3"));
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle9", 10, 1.1f, 500, 600, 250)));
        tablero.añadeJuez();
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle10", 10, 1.1f, 500, 600, 250)));
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle11", 10, 1.1f, 500, 600, 250)));
        tablero.añadeCasilla(new CasillaImpuesto(10, "IMPUESTO"));
        tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Calle12", 10, 1.1f, 500, 600, 250)));
    }
    
    /**
     * Método para pasar el turno de un jugador a otro
     */
    private void pasarTurno(){
        if (indiceJugadorActual < (jugadores.size() -1) )
            indiceJugadorActual++;
        else
            indiceJugadorActual = 0;
    }
    
    public ArrayList<Jugador> ranking(){
        ArrayList<Jugador> ranking = new ArrayList<>(jugadores);
        Collections.sort(ranking, Jugador::compareTo);
        return ranking;
    }
    
    public boolean salirCarcelPagando(){
        return getJugadorActual().salirCarcelPagando();
    }
    
    public boolean salirCarcelTirando(){
        return getJugadorActual().salirCarcelTirando();
    }
    
	/**
	 * @return OperacionesJuego siguienteOperacion
	 */
    public OperacionesJuego siguientePaso(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
		OperacionesJuego operacion = gestorEstados.operacionesPermitidas(jugadorActual, estado);
		
		if (operacion == OperacionesJuego.PASAR_TURNO){
			pasarTurno();
			siguientePasoCompletado(operacion);
		} else if (operacion == OperacionesJuego.AVANZAR){
			avanzaJugador();
			siguientePasoCompletado(operacion);
		}
		
		return operacion;
    }

    public void siguientePasoCompletado(OperacionesJuego operacion){
        estado = gestorEstados.siguienteEstado(getJugadorActual(), estado, operacion);
    }
    
    public boolean vender(int ip){
        return getJugadorActual().vender(ip);
    }
}
