package GUI;

import java.util.ArrayList;
import civitas.CivitasJuego;
import civitas.GestionesInmobiliarias;
import civitas.OperacionesJuego;
import civitas.OperacionInmobiliaria;
import civitas.Respuestas;
import civitas.SalidasCarcel;
import civitas.Jugador;

public class Controlador {
	private CivitasJuego juego;
	private CivitasView vista;

        Controlador(CivitasJuego _juego, CivitasView _vista){
            juego = _juego;
            vista = _vista;
        }
        
	void juega(){
        vista.setCivitasJuego(juego);

        while (!juego.finalDelJuego()){
            vista.actualizarVista();
            OperacionesJuego siguienteOperacion = juego.siguientePaso();
            vista.mostrarSiguienteOperacion(siguienteOperacion);
            
            if (siguienteOperacion != OperacionesJuego.PASAR_TURNO){
                vista.mostrarEventos();
            }

            if (!juego.finalDelJuego()){
                switch(siguienteOperacion){
                    case COMPRAR:
                        if (vista.comprar() == Respuestas.SI)
                            juego.comprar();

                        juego.siguientePasoCompletado(siguienteOperacion);
                        break;
                    
                    case GESTIONAR:
                        vista.gestionar();
                        int iGestion = vista.getGestionElegida();
                        int ip = vista.getPropiedadElegida();
                        GestionesInmobiliarias gestion = GestionesInmobiliarias.values()[iGestion];
                        OperacionInmobiliaria operacion = new OperacionInmobiliaria(gestion, ip);
                                
                        switch(gestion){
                            case TERMINAR:
                                juego.siguientePasoCompletado(siguienteOperacion);
                                break;
                                    
                            case VENDER:
                                juego.vender(ip);
                                break;
                                        
                            case HIPOTECAR:
                                juego.hipotecar(ip);
                                break;
                                        
                            case CANCELAR_HIPOTECA:
                                juego.cancelarHipoteca(ip);
                                break;
                                        
                            case CONSTRUIR_CASA:
                                juego.construirCasa(ip);
                                break;
                                        
                            case CONSTRUIR_HOTEL:
                                juego.construirHotel(ip);
                                break;
                                        
                            default: break;
                        }
                        break;
                                
                    case SALIR_CARCEL:
                        SalidasCarcel s = vista.salirCarcel();
                        if (s == SalidasCarcel.PAGANDO)
                            juego.salirCarcelPagando();
                        else if (s == SalidasCarcel.TIRANDO)
                            juego.salirCarcelTirando();
                        
                        juego.siguientePasoCompletado(siguienteOperacion);
                        break;
                      
                    default: break;
                }
            }
        }
		
            // final del juego
            ArrayList<Jugador> ranking = new ArrayList<Jugador>(juego.ranking());
            System.out.println("\nEl juego ha finalizado. La clasificacion ha sido:");
                for (int i = 0; i < ranking.size(); i++)
                    System.out.println(i + ") " + ranking.get(i));
	}
}
