package civitas;

import java.util.ArrayList;

public class Tablero{
    // Atributos
    private int numCasillaCarcel;
    ArrayList<Casilla> casillas;
    int porSalida;
    boolean tieneJuez;
    
    // Constructor
    public Tablero( int i ){
        if (i >= 1)
            numCasillaCarcel = i;
        else
            numCasillaCarcel = 1;
        
        casillas = new ArrayList<>();
        Casilla casilla_salida = new Casilla("Salida");
        casillas.add(0, casilla_salida);
        
        porSalida = 0;
        tieneJuez = false;
    }
    
    // Métodos
    private boolean correcto(){
        if ( casillas.size() > numCasillaCarcel && tieneJuez )
            return true;
        else
            return false;
    }
    
    private boolean correcto(int numCasilla){
        if ( correcto() && numCasilla < casillas.size() )
            return true;
        else
            return false;
    }
    
    /**
     * Consultor para el atributo numCasillaCarcel
     * @return número entero donde se ubica la cárcel en el tablero
     */
    int getCarcel(){
        return numCasillaCarcel;
    }
    
    int getPorSalida(){
        if (porSalida > 0){
            int temp = porSalida;
            porSalida--;
            return temp;
        }else{
            return porSalida;
        }
    }
    
    void añadeCasilla( Casilla casilla ){
        if ( casillas.size() == numCasillaCarcel ){
            Casilla temp = new Casilla("Cárcel");
            casillas.add(temp);
        };
        
        casillas.add(casilla);
        
        if ( casillas.size() == numCasillaCarcel ){
            Casilla temp = new Casilla("Cárcel");
            casillas.add(temp);
        };        
    }
    
    void añadeJuez( ){
        if (!tieneJuez){
            casillas.add(new CasillaJuez(numCasillaCarcel, "JUEZ"));
            tieneJuez = true;
        }
    }
    
    Casilla getCasilla (int numCasilla){
        if ( correcto(numCasilla) )
            return casillas.get(numCasilla);
        else
            return null;
    }
    
    int nuevaPosicion (int actual, int tirada){
        if ( !correcto() )
            return -1;
        else{
            int nuevaPosi = (actual + tirada) % casillas.size();
            if ( correcto(nuevaPosi) ){
                if (nuevaPosi != (actual + tirada) )
                    porSalida++;
                
                return nuevaPosi;
            }
        }
            
        return -1;
    }
    
    int calcularTirada (int origen, int destino){
        int resta = destino - origen;
        
        if ( resta >= 0)
            return resta;
        else
            return resta + casillas.size();
    }    
}
