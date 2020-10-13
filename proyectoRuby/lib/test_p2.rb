module Civitas

require_relative 'civitas_juego'
  
class TestP2

  def initialize
    main
  end
  
  def main
    puts "TestP2:\n"
    
    nombres = []
    for i in 0..4 do
      str = "Jugador" + i.to_s
      nombres.push(str)
    end
    
    juego = CivitasJuego.new(nombres)
    puts juego.info_jugador_texto
    
    
    
    
  end
end

TestP2.new
end
