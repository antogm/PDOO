require_relative 'casilla'

module Civitas

class CasillaCalle < Casilla
  
  def initialize(titulo)
    super(titulo.nombre)
    @tituloPropiedad = titulo
  end
  
  def recibe_jugador(actual, todos)
    if jugador_correcto(actual, todos)
      informe(actual, todos)
      
      if !@tituloPropiedad.tiene_propietario
        todos[actual].puede_comprar_casilla
      else
        @tituloPropiedad.tramitar_alquiler(todos[actual])
      end
    end
  end
  
  attr_reader :tituloPropiedad
end
end
