package civitas;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class MazoSorpresas {
    // Atributos
    private ArrayList<Sorpresa> sorpresas;
    private boolean barajada;
    private int usadas;
    private boolean debug;
    private ArrayList<Sorpresa> cartasEspeciales;
    private Sorpresa ultimaSorpresa;
    
    // Métodos
    private void init (){
        sorpresas = new ArrayList<>();
        cartasEspeciales = new ArrayList<>();
        barajada = false;
        usadas = 0;
    }
    
    MazoSorpresas(){
        init();
        debug = false;
    }
    
    MazoSorpresas(boolean b){
        debug = b;
        init();
        
        if (debug){
            Diario.getInstance().ocurreEvento("Se ha activado el modo debug del MazoSorpresas");
        }
    }
    
    void alMazo (Sorpresa s){
        if (!barajada)
            sorpresas.add(s);
    }
    
    Sorpresa siguiente (){
        if ( (!barajada || usadas == sorpresas.size() ) && !debug){
            Collections.shuffle(sorpresas, new Random());
            usadas = 0;
            barajada = true;
        }
            
        usadas++;
        ultimaSorpresa = sorpresas.get(0);
        sorpresas.remove(0);
        sorpresas.add(ultimaSorpresa);
        
        return ultimaSorpresa;
    }
    
    void inhabilitarCartaEspecial (Sorpresa sorpresa){
        int i = sorpresas.indexOf(sorpresa);
        if (i != -1){
            sorpresas.remove(i);
            cartasEspeciales.add(sorpresa);
            Diario.getInstance().ocurreEvento("Carta especial deshabilitada");
        };
    }
    
    void habilitarCartaEspecial (Sorpresa sorpresa){
        int i = cartasEspeciales.indexOf(sorpresa);
        
        if (i != -1){
            cartasEspeciales.remove(i);
            sorpresas.add(sorpresa);
            Diario.getInstance().ocurreEvento("Carta especial habilitada");
        };
    }
}
