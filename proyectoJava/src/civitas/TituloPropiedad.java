package civitas;

public class TituloPropiedad {
    
    // Atributos
    private float alquilerBase;
    private static float factorInteresesHipoteca = 1.1f;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    Jugador propietario;
    
    // Métodos
    
    /**
     * Cambia el propietario de la casilla por el jugador pasado como parámetro
     * @param jugador nuevo propietario
     */
    void actualizaPropietarioPorConversion (Jugador jugador){
        propietario = jugador;
    }
    
    boolean cancelarHipoteca (Jugador jugador){
        throw new UnsupportedOperationException("No implementado");
    }

    /**
     * Calcula la cantidad de casas y hoteles que tiene la propiedad
     * @return int número de casas sumado al número de hoteles
     */
    int cantidadCasasHoteles (){
        return numCasas + numHoteles;
    }
    
    boolean comprar (Jugador jugador){
        throw new UnsupportedOperationException("No implementado");
    }

    boolean construirCasa (Jugador jugador){
        throw new UnsupportedOperationException("No implementado");
    }

    boolean construirHotel (Jugador jugador){
        throw new UnsupportedOperationException("No implementado");
    }
    
    /**
     * Método para derruir n casas de la propiedad
     * @param n número de casas a derruir
     * @pre 0 < n <= numCasas
     * @param jugador que quiere derruir las casas
     * @return true si la operación se ha realizado
     */
    boolean derruirCasas (int n, Jugador jugador){
        boolean exito = true;
        
        if ( jugador == propietario && numCasas >= n)
            numCasas -= n;
        else
            exito = false;
        
        return exito;
    }
    
    /**
     * Comprueba si el jugador pasado como parámetro es el propietario de la casilla
     * @param jugador a comparar con el propietario
     * @return true si el jugador es el propietario
     */
    private boolean esEsteElPropietario (Jugador jugador){
        return jugador == propietario;
    }
    
    /**
     * Consultor para el atributo privado hipotecado
     * @return true si la propiedad está hipotecada
     */
    public boolean getHipotecado (){
        return hipotecado;
    }
    
    /**
     * Calcula el importe que cuesta cancelar una hipoteca
     * @return float importe cancelar hipoteca
     */
    float getImporteCancelarHipoteca (){
        return factorInteresesHipoteca * getImporteHipoteca();
    }
    
    /**
     * Calcula el importe que se obtiene al hipotecar la propiedad
     * @return float importe hipoteca
     */
    private float getImporteHipoteca (){
        return hipotecaBase*(1+(numCasas*0.5f)+(numHoteles*2.5f));
    }
    
    /**
     * Consultor para el atributo privado nombre
     * @return String nombre de la propiedad 
     */
    String getNombre(){
        return nombre;
    }
    
    /**
     * Consultor para el atributo numCasas
     * @return int número de casas de la propiedad 
     */
    int getNumCasas(){
        return numCasas;
    }
    
    /**
     * Consultor para el atributo numHoteles
     * @return int número de hoteles de la propiedad 
     */
    int getNumHoteles(){
        return numHoteles;
    }
    
    /**
     * Devuelve el precio de alquiler de la propiedad para los jugadores que caigan en ella
     * @return 0 si la propiedad está hipotecada o el propietario encarcelado, float mayor que 0 en otro caso
     */
    private float getPrecioAlquiler (){
        if ( propietarioEncarcelado() || hipotecado )
            return 0;
        else
            return alquilerBase * (1 + (numCasas*0.5f) + (numHoteles*2.5f));
    }
    
    /**
     * Consultor para el atributo precioCompra
     * @return float
     */
    float getPrecioCompra (){
        return precioCompra;
    }
    
    /**
     * Consultor para el atributo precioEdificar
     * @return float
     */
    float getPrecioEdificar (){
        return precioEdificar;
    }
    
    /**
     * Calcula el precio de venta de la propiedad
     * @return float
     */
    private float getPrecioVenta (){
        return precioCompra + (factorRevalorizacion * (numCasas + 5f*numHoteles) * precioEdificar);
    }
    
    /**
     * Consultor para el atributo propietario
     * @return Jugador propietario de la casilla
     */
    Jugador getPropietario (){
        return propietario;
    }
    
    boolean hipotecar (Jugador jugador){
        throw new UnsupportedOperationException("No implementado");
    }
    
    /**
     * Método para saber si el propietario de la casilla está encarcelado
     * @return true si el propietario está en la cárcel
     */
    private boolean propietarioEncarcelado(){
        if ( propietario.isEncarcelado() )
            return true;
        else
            return false;
    }
    
    /**
     * Método para saber si la casilla tiene propietario
     * @return true si tiene propietario
     */
    boolean tienePropietario(){
        return (propietario != null);
    }
    
    /**
     * Constructor para un objeto de la clase TituloPropiedad
     * @param nom Nombre de la propiedad
     * @param ab Precio base del alquiler
     * @param fr Factor de revalorización
     * @param hb Precio base de la hipoteca
     * @param pc Precio de compra de la casilla
     * @param pe Precio de edificación
     */
    TituloPropiedad(String nom, float ab, float fr, float hb, float pc, float pe){
        nombre = nom;
        alquilerBase = ab;
        factorRevalorizacion = fr;
        hipotecaBase = hb;
        precioCompra = pc;
        precioEdificar = pe;
        
        propietario = null;
    }
    
    
    @Override
    public String toString(){
        String str  = "Nombre de la propiedad: " + nombre + "\n"
                    + "Precio de alquiler: " + alquilerBase + "\n"
                    + "Factor de revalorización: " + factorRevalorizacion + "\n"
                    + "Hipoteca base: " + hipotecaBase + "\n"
                    + "Precio compra: " + precioCompra + "\n"
                    + "Precio edificar: " + precioEdificar + "\n";
        
        return str;
    }
    
    /**
     * Método para tramitar el alquiler a un jugador que haya caido en la casilla
     * @pre Si el jugador es el propietario, no paga alquiler
     * @param jugador que ha caído en la casilla
     */
    void tramitarAlquiler (Jugador jugador){
        if( tienePropietario() && jugador != propietario){
            jugador.pagaAlquiler( getPrecioAlquiler() );
            propietario.recibe( getPrecioAlquiler() );
        }
            
    }
    
    /**
     * Método para vender la propiedad
     * @param jugador que quiere vender la propiedad
     * @return true si la operación ha tenido éxito
     */
    boolean vender (Jugador jugador){
        if (jugador == propietario && !hipotecado){
            propietario.recibe( getPrecioVenta() );
            derruirCasas (numCasas, propietario);
            numHoteles = 0;
            propietario = null;
            return true;
        }else
            return false;
    }
}
