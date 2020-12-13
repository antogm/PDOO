require_relative 'sorpresa'
require_relative 'tablero'

module Civitas
class SorpresaIrCasilla < Sorpresa
  
  def initialize(tablero, valor, texto)
    super(texto)
    @tablero = tablero
    @valor = valor
  end
  
  def aplicar_a_jugador(actual, todos)
    if jugador_correcto(actual, todos)
      informe(actual, todos)
      casilla_actual = todos[actual].numCasillaActual
      tirada = @tablero.calcular_tirada(casilla_actual, @valor)
      nueva_pos = @tablero.nueva_posicion(casilla_actual, tirada)
      todos[actual].mover_a_casilla(nueva_pos)
      casilla = @tablero.get_casilla(nueva_pos)
      casilla.recibe_jugador(actual, todos)
    end
  end
  
  public_class_method :new
end
end
