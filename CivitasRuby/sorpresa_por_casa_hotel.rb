require_relative 'sorpresa'

module Civitas
class SorpresaPorCasaHotel < Sorpresa
  
  def initialize(valor, texto)
    super(texto)
    @valor = valor
  end
  
  def aplicar_a_jugador(actual, todos)
    if jugador_correcto(actual, todos)
      informe(actual, todos)
      todos[actual].modificar_saldo( @valor * todos[actual].cantidad_casas_hoteles)
    end
  end
  
  public_class_method :new
end
end
