package civitas;

import java.util.Random;

class Dado {
    
    // Atributos
    private Random random;
    private int ultimoResultado;
    private boolean debug;
    static final private Dado instance = new Dado();
    static final private int SalidaCarcel = 5;
    
    // Constructor
    private Dado(){
        debug = false;
        ultimoResultado = 0;
        random = new Random();
    }
    
    // Métodos
    static public Dado getInstance(){
        return instance;
    }
    
    int tirar(){
        if (!debug)
            ultimoResultado = random.nextInt(6) +1;
        else
            ultimoResultado = 1;
        
        return ultimoResultado;
    }
    
    boolean salgoDeLaCarcel(){
        if (tirar() >= 5)
            return true;
        
        return false;
    }
    
    int quienEmpieza (int n){
        return random.nextInt(n);
    }
    
    void setDebug (boolean d){
        debug = d;
        
        String str = "Se ha activado el modo debug del dado";
        if (!debug)
            str = "Se ha desactivado el modo debug del dado";
        
        Diario.getInstance().ocurreEvento(str);        
    }
    
    int getUltimoResultado(){
        return ultimoResultado;
    }
}
