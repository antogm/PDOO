require_relative 'casilla'

module Civitas
class CasillaJuez < Casilla
  def initialize(carcel, nombre)
    super(nombre)
    @@numCasillaCarcel = carcel 
  end
  
  def recibe_jugador(actual, todos)
    if jugador_correcto(actual, todos)
      informe(actual, todos)
      todos[actual].encarcelar(@@numCasillaCarcel)
    end
  end
  
end
end
