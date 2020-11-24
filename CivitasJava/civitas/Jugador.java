package civitas;

import java.util.ArrayList;

public class Jugador implements Comparable<Jugador>{
    
    // Atributos
    protected static int CasasMax = 4;
    protected static int CasasPorHotel = 4;
    protected boolean encarcelado;
    protected static int HotelesMax = 4;
    private String nombre;
    private int numCasillaActual;
    protected static float PasoPorSalida = 1000;
    protected static float PrecioLibertad = 200;
    private boolean puedeComprar;
    private float saldo;
    private float SaldoInicial = 7500;
    ArrayList<TituloPropiedad> propiedades;
    Sorpresa salvoconducto;
    
    // Métodos
    boolean cancelarHipoteca(int ip){
        boolean result = false;
		
        if (encarcelado)
            return result;
        
		if (existeLaPropiedad(ip)){
			TituloPropiedad propiedad = propiedades.get(ip);
			float cantidad = propiedad.getImporteCancelarHipoteca();
			boolean puedoGastar = this.puedoGastar(cantidad);
			if (puedoGastar){
				result = propiedad.cancelarHipoteca(this);
				
				if (result)
					Diario.getInstance().ocurreEvento("El jugador " + nombre + " cancela la hipoteca de la propiedad " + ip);
			}
		}
		
		return result;
    }
    
    /**
     * Método para calcular la cantidad de casas y hoteles de los que dispone el jugador
     * @return entero número de casas y hoteles que posee el jugador
     */
    int cantidadCasasHoteles(){
        int num = 0;
        
        for (int i = 0; i < propiedades.size(); i++){
            num += propiedades.get(i).cantidadCasasHoteles();
        };
        
        return num;
    }
    
    @Override
    public int compareTo(Jugador otro){
        return Float.compare(saldo, otro.getSaldo());
    }
    
    boolean comprar (TituloPropiedad titulo){
        boolean result = false;
		
		if (encarcelado)
			return result;
		
		if (puedeComprar){
			float precio = titulo.getPrecioCompra();
			
			if (puedoGastar(precio)){
				result = titulo.comprar(this);
				
				if (result){
					propiedades.add(titulo);
					String evento = "El jugador " + this + " compra la propiedad " + titulo.toString();
					Diario.getInstance().ocurreEvento(evento);
					puedeComprar = false;
				}
			}
		}
		
		return result;
    }
    
    boolean construirCasa (int ip){
        boolean result = false;
		boolean puedoEdificarCasa = false;
		
		if (encarcelado)
			return result;
		
		boolean existe = this.existeLaPropiedad(ip);
		
		if (existe){
			TituloPropiedad propiedad = propiedades.get(ip);
			puedoEdificarCasa = this.puedoEdificarCasa(propiedad);
			
			if (puedoEdificarCasa)
				result = propiedad.construirCasa(this);
		}
		
		return result;
    }
    
    boolean construirHotel (int ip){
        boolean result = false;
		
		if (encarcelado)
			return result;
		
		if (existeLaPropiedad(ip)){
			TituloPropiedad propiedad = propiedades.get(ip);
			boolean puedoEdificarHotel = this.puedoEdificarHotel(propiedad);
			
			if (puedoEdificarHotel){
				result = propiedad.construirHotel(this);
				int casasPorHotel = getCasasPorHotel();
				propiedad.derruirCasas(casasPorHotel, this);
				Diario.getInstance().ocurreEvento("El jugador " + nombre + " construye hotel en la propiedad " + ip);
			}
		}
		
		return result;
    }
    
    /**
     * Comprueba si el jugador tiene que ir a la cárcel
     * @return true si va a la cárcel
     */
    protected boolean debeSerEncarcelado(){
        if (encarcelado)
            return false;
        else if ( !tieneSalvoConducto())
            return true;
        
        perderSalvoconducto();
        String evento = "El jugador " + nombre + " se libra de la cárcel y pierde su salvoconducto";
        Diario.getInstance().ocurreEvento(evento);
        return false;
    }
    
    /**
     * Método para comprobar si el jugador está en bancarrota
     * @return true si el saldo es negativo
     */
    boolean enBancarrota(){
        return (saldo < 0);
    }
    
    /**
     * Método para encarcelar a un jugador
     * @param numCasillaCarcel índice del tablero donde se encuentra la cárcel
     * @return true si la operación ha tenido éxito
     */
    boolean encarcelar(int numCasillaCarcel){
        if ( debeSerEncarcelado() ){
            moverACasilla(numCasillaCarcel);
            encarcelado = true;
            String evento = "El jugador " + nombre + " ha sido encarcelado";
            Diario.getInstance().ocurreEvento(evento);
        }
        
        return encarcelado;
    }
    
    /**
     * Comprueba si el parámetro es un índice valido para acceder al ArrayList de propiedades
     * @param ip índice de la propiedad a buscar
     * @return true si ip es un índice válido
     */
    private boolean existeLaPropiedad(int ip){
        return (ip < propiedades.size() && ip >= 0);
    }
    
    /**
     * Consultor para el atributo CasasMax
     * @return int número máximo de casas
     */
    private int getCasasMax(){
        return CasasMax;
    }
    
    /**
     * Consultor para el atributo CasasPorHotel
     * @return int número de casas por hotel permitidas
     */
    int getCasasPorHotel(){
        return CasasPorHotel;
    }
    
    /**
     * Consultor para el atributo HotelesMax
     * @return int número máximo de hoteles
     */
    private int getHotelesMax(){
        return HotelesMax;
    }
    
    /**
     * Consultor para el atributo nombre
     * @return String nombre del jugador
     */
    protected String getNombre(){
        return nombre;
    }
    
    /**
     * Consultor para el atributo numCasillaActual
     * @return int índice del tablero donde se encuentra el jugador actualmente
     */
    int getNumCasillaActual(){
        return numCasillaActual;
    }
    
    /**
     * Consultor para el atributo PrecioLibertad
     * @return float cantidad que el jugador tiene que pagar para salir de prisión
     */
    private float getPrecioLibertad(){
        return PrecioLibertad;
    }
    
    /**
     * Consultor para el atributo PasoPorSalida
     * @return float Cantidad que recibe el jugador al pasar por la casilla de salida
     */
    private float getPremioPasoPorSalida(){
        return PasoPorSalida;
    }
    
    /**
     * Consultor para el atributo ArrayList propiedades
     * @return ArrayList<TituloPropiedad> propiedades del jugador
     */
    public ArrayList<TituloPropiedad> getPropiedades(){
        return propiedades;
    }
    
    /**
     * Consultor para el atributo puedeComprar
     * @return true si el jugador tiene la operación de compra disponible
     */
    boolean getPuedeComprar(){
        return puedeComprar;
    }
    
    /**
     * Consultor para el atributo saldo
     * @return float saldo del jugador
     */
    protected float getSaldo(){
        return saldo;
    }
    
    boolean hipotecar (int ip){
		boolean result = false;
		
		if (encarcelado)
			return result;
		
		if (existeLaPropiedad(ip)){
			TituloPropiedad propiedad = propiedades.get(ip);
			result = propiedad.hipotecar(this);
		}
		
		if (result)
			Diario.getInstance().ocurreEvento("El jugador " + nombre + " hupoteca la propiedad " + ip);
		
		return result;
    }
    
    /**
     * Consultor para el atributo encarcelado
     * @return true si el jugador está en la cárcel
     */
    public boolean isEncarcelado(){
        return encarcelado;
    }
    
    /**
     * Constructor para un objeto de la clase Jugador
     * @param nombre del jugador 
     */
    Jugador (String _nombre){
        nombre = _nombre;
        saldo = SaldoInicial;
        propiedades = new ArrayList<>();
        salvoconducto = null;
        numCasillaActual = 0;
        encarcelado = false;
    }
    
    /**
     * Constructor de copia para la clase Jugador
     * @param otro Objeto a copiar
     */
    protected Jugador(Jugador otro){
        nombre = otro.nombre;
        encarcelado = otro.encarcelado;
        numCasillaActual = otro.numCasillaActual;
        puedeComprar = otro.puedeComprar;
        saldo = otro.saldo;
        salvoconducto = otro.salvoconducto;
        propiedades = new ArrayList<TituloPropiedad>(otro.propiedades);
    }
    
    /**
     * Método para modificar el saldo del jugador
     * @param cantidad a modificar
     * @return true siempre
     */
    boolean modificarSaldo (float cantidad){
        saldo += cantidad;
        String evento = "El jugador " + nombre + " modifica su saldo por " + cantidad + " €";
        Diario.getInstance().ocurreEvento(evento);
        return true;
    }
    
    /**
     * Método para mover al jugador a otra casilla
     * @param numCasilla casilla a la que se quiere mover el jugador
     * @return true si la operación tiene éxito
     */
    boolean moverACasilla (int numCasilla){
        if (isEncarcelado())
            return false;
        else{
            numCasillaActual = numCasilla;
            puedeComprar = false;
            String evento = "El jugador " + nombre + " se ha movido a la casilla " + numCasilla;
            Diario.getInstance().ocurreEvento(evento);
            return true;
        }
    }
    
    /**
     * Método para otorgar un salvoconducto al jugador
     * @pre El jugador no puede estar ya encarcelado
     * @param sorpresa salvoconducto
     * @return true si la operación ha tenido éxito
     */
    boolean obtenerSalvoconducto (Sorpresa sorpresa){
        if (encarcelado)
            return false;
        else{
            salvoconducto = sorpresa;
            return true;
        }
    }
    
    /**
     * Método para restarle dinero al jugador
     * @param cantidad a restar
     * @return true si la operación ha tenido éxito
     */
    boolean paga (float cantidad){
        return modificarSaldo(cantidad * -1);
    }
    
    /**
     * Método para pagar el alquiler de una casilla
     * @param cantidad a pagar
     * @return true si el jugador puede pagarlo
     */
    boolean pagaAlquiler (float cantidad){
        if (isEncarcelado() )
            return false;
        else
            return paga(cantidad);
    }
    
    /**
     * Método para pagar un impuesto
     * @param cantidad a pagar
     * @return true si el jugador tiene que pagarlo y puede hacerlo
     */
    boolean pagaImpuesto (float cantidad){
        if (isEncarcelado() )
            return false;
        else
            return paga(cantidad);
    }
    
    /**
     * Método para procesar el paso por salida del jugador
     * @return true siempre
     */
    boolean pasaPorSalida (){
       modificarSaldo(getPremioPasoPorSalida());
       String evento = "El jugador " + nombre + " ha pasado por la casilla de salida y ha cobrado " + getPremioPasoPorSalida() + "€";
       Diario.getInstance().ocurreEvento(evento);
       return true;
    }
    
    /**
     * Método para quitarle un salvoconducto al jugador
     */
    private void perderSalvoconducto(){
        salvoconducto.usada();
        salvoconducto = null;
    }
    
    /**
     * Método para comprobar si el jugador tiene la operación de compra disponible
     * @return true si puede comprar
     */
    boolean puedeComprarCasilla(){
        if ( isEncarcelado() )
            puedeComprar = false;
        else
            puedeComprar = true;
        
        return puedeComprar;
    }
    
    /**
     * Método para comprobar si el jugador puede salir de la cárcel pagando
     * @return true si el jugador tiene suficiente saldo
     */
    private boolean puedeSalirCarcelPagando (){
        return (saldo >= PrecioLibertad);
    }
    
    /**
     * Método para comprobar si el jugador puede edificar una casa en la propiedad seleccionada
     * @param propiedad en la que se quiere construir la casa
     * @return true si puede construir una casa
     */
    private boolean puedoEdificarCasa (TituloPropiedad propiedad){
        boolean puedoEdificar = false;
		
		float precio = propiedad.getPrecioEdificar();
		
		if (puedoGastar(precio) && (propiedad.getNumCasas() < CasasMax))
            puedoEdificar = true;
			
		return puedoEdificar;
    }
    
    /**
     * Método para comprobar si el jugador puede edificar un hotel en la propiedad seleccionada
     * @param propiedad en la que se quiere construir el hotel
     * @return true si puede construir un hotel
     */
    private boolean puedoEdificarHotel (TituloPropiedad propiedad){
        boolean puedoEdificarHotel = false;
		float precio = propiedad.getPrecioEdificar();
		
		if (puedoGastar(precio) && propiedad.getNumHoteles() < HotelesMax && propiedad.getNumCasas() >= CasasPorHotel)
            puedoEdificarHotel = true;
        
		return puedoEdificarHotel;
    }
    
    /**
     * Método para comprobar si el jugador puede gastar una cantidad de dinero
     * @param precio cantidad a gastar
     * @return true si el saldo es mayor o igual que la cantidad establecida
     */
    private boolean puedoGastar (float precio){
        if (isEncarcelado() )
            return false;
        else          
            return saldo >= precio;
    }
    
    /**
     * Método para añadir dinero al saldo del jugador
     * @param cantidad a recibir
     * @return true si la operación tiene éxito
     */
    boolean recibe (float cantidad){
        if ( isEncarcelado() )
            return false;
        else{
            return modificarSaldo(cantidad);
        }
    }
    
    /**
     * Método para que el jugador salga de la cárcel pagando la fianza
     * @return true si la operación tiene éxito
     */
    boolean salirCarcelPagando(){
        if (isEncarcelado() && puedeSalirCarcelPagando() ){
            paga(PrecioLibertad);
            encarcelado = false;
            String evento = "El jugador " + nombre + " ha pagado para salir de la cárcel";
            Diario.getInstance().ocurreEvento(evento);
            return true;
        }
        
        return false;
    }
    
    /**
     * Método para que el jugador salga de la cárcel tirando el dado
     * @return true si el jugador consigue salir de la cárcel
     */
    boolean salirCarcelTirando(){
        if (Dado.getInstance().salgoDeLaCarcel() ){
            encarcelado = false;
            String evento = "El jugador " + nombre + " sale de la cárcel con su última tirada";
            Diario.getInstance().ocurreEvento(evento);
            return true;
        }
        
        return false;
    }
    
    /**
     * Método para comprobar si el jugador tiene alguna propiedad que gestionar
     * @return true si tiene propiedades
     */
    boolean tieneAlgoQueGestionar(){
        return (propiedades.size() > 0);
    }
    
    /**
     * Método para comprobar si el jugador tiene un salvoconducto
     * @return true si tiene salvoconducto
     */
    boolean tieneSalvoConducto(){
        return (salvoconducto != null);
    }
    
    @Override
    public String toString(){
       return ("Nombre del jugador " + nombre + ", saldo " + saldo + ", está en la casilla " + numCasillaActual + "\n"); 
    }
    
    /**
     * Método para vender una propiedad del jugador
     * @param ip índice de la propiedad que desea vender
     * @return true si la operación tiene éxito
     */
    boolean vender (int ip){
        boolean salida = false;
        
        if (!isEncarcelado() && existeLaPropiedad(ip)){
            if( propiedades.get(ip).vender(this) ){
                String evento = "El jugador " + nombre + " ha vendido la propiedad " + propiedades.get(ip).getNombre();
                Diario.getInstance().ocurreEvento(evento);
                propiedades.remove(ip);
                salida = true;   
            }
        }
        
        return salida;
    }
}
