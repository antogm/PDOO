require_relative 'civitas_juego'
require_relative 'vista_textual'
require_relative 'dado'
require_relative 'controlador'

module Civitas
  class TestP3
    def initialize
      main
    end
    
    def main
      nombres = ["Jugador1", "Jugador2", "Jugador3", "Jugador4"]
      juego = CivitasJuego.new(nombres)
      vista = Vista_textual.new()
      Dado.instance.set_debug(true)
      controlador = Controlador.new(juego,vista)
      controlador.juega
    end
  end
  
  TestP3.new
end
