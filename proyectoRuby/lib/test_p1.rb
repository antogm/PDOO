# encoding :UTF-8
require 'Dado'
require 'Enumerados'
require 'Mazo_Sorpresas'
require 'Sorpresa'
require 'Tablero'

module Civitas
  
  class TestP1
    def initialize
      main
    end
    
    def main
      
      # test quien empieza
      resultados = [0,0,0,0]
      for i in 0..99 do
        resultados[ Dado.instance.quien_empieza(4) ] += 1
      end
      
      puts ("Test quien empieza:")
      for i in 0..resultados.length-1 do
        puts ("Jugador " + (i+1).to_s + " empieza " + resultados[i].to_s + " veces")
      end
      puts ("\n")
      
      
      # test modo debug del dado
      Dado.instance.set_debug(true)
      for i in 0..2 do
        puts ("Tirada en modo debug, resultado: " + Dado.instance.tirar.to_s )
      end
      
      Dado.instance.set_debug(false)
      for i in 0..2 do
        puts ("Tirada sin modo debug, resultado: " + Dado.instance.tirar.to_s )
      end
      puts ("\n")
      
      
      # test ultimo_resultado y salgo_de_la_carcel
      puts ("El ultimo resultado fue: " + Dado.instance.ultimoResultado.to_s)
      puts ("Saldria de la carcel?: " + Dado.instance.salgo_de_la_carcel.to_s)
      puts ("\n")
      
      
      # test enumerados
      enum1 = Enumerados::TipoCasilla::CALLE
      enum2 = Enumerados::TipoSorpresa::PAGARCOBRAR
      enum3 = Enumerados::EstadosJuego::INICIO_TURNO
      puts ("enum1: " + enum1.to_s + "\nenum2: " + enum2.to_s + "\nenum3: " + enum3.to_s + "\n")
      
      
      # test mazo sorpresas
      mazo = MazoSorpresas.new
      sorpresa1 = Sorpresa.new("sorpresa1")
      sorpresa2 = Sorpresa.new("sorpresa2")
      mazo.al_mazo(sorpresa1)
      mazo.al_mazo(sorpresa2)
      
      puts ("la siguiente sorpresa es: " + mazo.siguiente.nombre)
      
      mazo.inhabilitar_carta_especial(sorpresa2)
      mazo.habilitar_carta_especial(sorpresa2)
      
      
      # test diario
      puts ("\nEventos pendientes en el diario:")
      while Diario.instance.eventos_pendientes do
        puts (Diario.instance.leer_evento)
      end
      
      # test tablero
      tablero = Tablero.new(5)
      tablero.añade_juez
  
      for i in 0..9 do
        tablero.añade_casilla(Casilla.new("casillatest"))
      end
   
      puts ("\nDespues de tirar los dados estoy en la casilla: " + tablero.nueva_posicion(0, Dado.instance.tirar).to_s)
    end
  end
  
  TestP1.new
end