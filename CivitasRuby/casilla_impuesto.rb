require_relative 'casilla'

module Civitas
class CasillaImpuesto < Casilla
  def initialize(importe, nombre)
    super(nombre)
    @importe = importe
  end
  
  def recibe_jugador(actual, todos)
    if jugador_correcto(actual, todos)
      informe(actual, todos)
      todos[actual].paga_impuesto(@importe)
    end
  end
  
end
end
