package civitas;

import java.util.Random;

public class Dado {
    
    // Atributos
    private Random random;
    private int ultimoResultado;
    private boolean debug;
    static final private Dado instance = new Dado();
    static final private int SalidaCarcel = 5;
    
    // Constructor
    
    /**
     * Constructor privado (sigue el patrón singleton)
     */
    private Dado(){
        debug = false;
        ultimoResultado = 0;
        random = new Random();
    }
    
    // Métodos
    
    /**
     * Método de clase para acceder a la única instancia de la clase
     * @return instancia actual del dado
     */
    static public Dado getInstance(){
        return instance;
    }
    
    /**
     * Método para generar un número aleatorio simulando una tirada de dado
     * @return entero al azar entre 1 y 6 
     */
    int tirar(){
        if (!debug)
            ultimoResultado = random.nextInt(6) +1;
        else
            ultimoResultado = 1;
        
        return ultimoResultado;
    }
    
    /**
     * Método para saber si la última tirada permite al jugador salir de la cárcel
     * @return true si es 5 o 6
     */
    boolean salgoDeLaCarcel(){
        if (tirar() >= 5)
            return true;
        
        return false;
    }
    
    /**
     * Método para saber qué jugador empieza la partida
     * @param n número de jugadores
     * @return entero al azar entre 0 y n-1
     */
    int quienEmpieza (int n){
        return random.nextInt(n);
    }
    
    /**
     * Consultor para activar o desactivar el modo debug del dado
     * @param d valor booleano a establecer 
     */
    void setDebug (boolean d){
        debug = d;
        
        String str = "Se ha activado el modo debug del dado";
        if (!debug)
            str = "Se ha desactivado el modo debug del dado";
        
        Diario.getInstance().ocurreEvento(str);        
    }
    
    /**
     * Consultor para obtener el último resultado del dado
     * @return entero con el valor de ultimoResultado 
     */
    int getUltimoResultado(){
        return ultimoResultado;
    }
}
