require_relative 'sorpresa'
require_relative 'tablero'

module Civitas
class SorpresaIrCarcel < Sorpresa
  
  def initialize(tablero, carcel, texto)
    super(texto)
    @tablero = tablero
    @numCasillaCarcel = carcel
  end
  
  def aplicar_a_jugador(actual, todos)
    if jugador_correcto(actual, todos)
      informe(actual, todos)
      todos[actual].encarcelar(@tablero.numCasillaCarcel)
    end
  end
  
  public_class_method :new
end
end
