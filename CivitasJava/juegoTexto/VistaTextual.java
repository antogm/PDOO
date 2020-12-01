package juegoTexto;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import civitas.Respuestas;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import civitas.Casilla;
import civitas.Jugador;
import civitas.TituloPropiedad;

class VistaTextual{
	CivitasJuego juegoModel; 
	int iGestion=-1;
	int iPropiedad=-1;
	private static String separador = "=====================";
	private Scanner in;
	
	VistaTextual(){
            in = new Scanner(System.in);
	}
	
	void mostrarEstado(String estado){
            System.out.println(estado);
	}
	
	void pausa() {
            System.out.print ("Pulsa una tecla");
            in.nextLine();
	}

	int leeEntero (int max, String msg1, String msg2){
            boolean ok;
            String cadena;
            int numero = -1;
            do{
                System.out.print (msg1);
                cadena = in.nextLine();
                try{
                    numero = Integer.parseInt(cadena);
                    ok = true;
                }catch(NumberFormatException e){
                    System.out.println(msg2);
                    ok = false;  
                }
                if (ok && (numero < 0 || numero >= max)){
                    System.out.println(msg2);
                    ok = false;
                }
            }while (!ok);

            return numero;
	}

	int menu (String titulo, ArrayList<String> lista){
            String tab = "  ";
            int opcion;
            System.out.println(titulo);
            for (int i = 0; i < lista.size(); i++) {
                System.out.println(tab+i+"-"+lista.get(i));
            }

            opcion = leeEntero(lista.size(),
                    "\n"+tab+"Elige una opción: ",
                    tab+"Valor erróneo");
		
            return opcion;
	}

	SalidasCarcel salirCarcel() {
            int opcion = menu ("Elige la forma para intentar salir de la carcel", new ArrayList<> (Arrays.asList("Pagando","Tirando el dado")));
            return (SalidasCarcel.values()[opcion]);
	}

	Respuestas comprar(){
            ArrayList<String> opciones = new ArrayList<>(Arrays.asList("SI", "NO"));
            int opcion = menu("¿Desea comprar la propiedad?", opciones);

            return (Respuestas.values()[opcion]);
	}

	void gestionar(){
            ArrayList<String> opciones = new ArrayList<>(Arrays.asList("VENDER", "HIPOTECAR", "CANCELAR_HIPOTECA", "CONSTRUIR_CASA", "CONSTRUIR_HOTEL",	"TERMINAR"));
            iGestion = menu("Introduzca que gestion desea realizar", opciones);

            if (iGestion != 5){
                ArrayList<String> strPropiedades = new ArrayList<>();
                ArrayList<TituloPropiedad> propiedades = new ArrayList<>(juegoModel.getJugadorActual().getPropiedades());

                for (int i = 0; i < propiedades.size(); i++)
                    strPropiedades.add(propiedades.get(i).getNombre() );

                iPropiedad = menu("Seleccione que propiedad gestionar", strPropiedades);
            }
	}
  
	public int getGestion(){
            return iGestion;
	}
	
	public int getPropiedad(){
            return iPropiedad;
	}
    
	void mostrarSiguienteOperacion(OperacionesJuego operacion){
            System.out.println("La siguiente operación a realizar es: " + operacion.toString());
	}

	void mostrarEventos(){
            while (Diario.getInstance().eventosPendientes()){
                System.out.println(Diario.getInstance().leerEvento());
            }
	}
  
	public void setCivitasJuego(CivitasJuego civitas){ 
            juegoModel = civitas;
            this.actualizarVista();
	}
  
	void actualizarVista(){
            Jugador jugador = juegoModel.getJugadorActual();
            int numPropiedades = jugador.getPropiedades().size();
            Casilla casillaActual = juegoModel.getCasillaActual();

            String info = "Jugador actual: " + jugador + "\n"
                        + "Casilla: " + casillaActual.getNombre() + "\n"
                        + "Num. propiedades: " + numPropiedades + "\n";

            for (int i = 0; i < numPropiedades; i++)
                info += i + ") " + jugador.getPropiedades().get(i).getNombre() + "\n";	

            info += "\n";
	}
}
