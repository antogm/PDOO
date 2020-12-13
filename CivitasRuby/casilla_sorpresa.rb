require_relative 'casilla'

module Civitas
class CasillaSorpresa < Casilla
  def initialize(mazo, nombre)
    super(nombre)
    @mazo = mazo
  end
  
  def recibe_jugador(actual, todos)
    if jugador_correcto(actual, todos)
      sorpresa = @mazo.siguiente
      informe(actual, todos)
      sorpresa.aplicar_a_jugador(actual, todos)
    end
  end
end
end
