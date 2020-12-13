package civitas;

import java.util.ArrayList;

public class JugadorEspeculador extends Jugador {
    
    private static int FactorEspeculador = 2;
    private float fianza;
    
    protected JugadorEspeculador(Jugador otro, float fianza){
        super(otro);
        this.fianza = fianza;
        
        for(int i = 0; i < propiedades.size(); i++){
            propiedades.get(i).actualizaPropietarioPorConversion(this);
        }
    }
    
    @Override
    boolean encarcelar(int numCasillaCarcel){
        if ( debeSerEncarcelado() ){
            if (puedoGastar(fianza) ){
                paga(fianza);
                Diario.getInstance().ocurreEvento("El jugador especulador " + getNombre() + " se libra de la cárcel pagando fianza");
                
            }else{
                moverACasilla(numCasillaCarcel);
                encarcelado = true;
                Diario.getInstance().ocurreEvento("El jugador especulador " + getNombre() + " ha sido encarcelado");
            }
        }
        
        return encarcelado;
    }
    
    @Override
    boolean pagaImpuesto (float cantidad){
        if (isEncarcelado() )
            return false;
        else
            return paga(cantidad / 2);
    }
    
    @Override
    int getCasasMax(){
        return (CasasMax * FactorEspeculador);
    }
    
    @Override
    int getHotelesMax(){
        return (HotelesMax * FactorEspeculador);
    }
    
    @Override
    public String toString(){
       return ("Jugador especulador " + getNombre() + ", saldo " + getSaldo() + ", está en la casilla " + getNumCasillaActual() + "\n"); 
    }
}
