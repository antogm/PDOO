# encoding:utf-8
require_relative 'mazo_sorpresas'
require_relative 'jugador'
require_relative 'titulo_propiedad'

module Civitas
class Casilla
  
  # Constructor
  def initialize(nombre)
    @nombre = nombre
  end
  
  private :initialize
  
  # Métodos
  def informe(actual, todos)
    if jugador_correcto(actual, todos)
      nombre_jugador = todos[actual].nombre
      evento = "El jugador " + nombre_jugador + " ha caido en la casilla " + to_string
      Diario.instance.ocurre_evento(evento)
    end
  end
    
  def jugador_correcto(actual, todos)
    if actual < todos.length && actual >= 0
      return true
    else
      return false
    end
  end
  
  def recibe_jugador(actual, todos)
    informe(actual, todos)
  end
          
  def to_string
    return @nombre
  end
  
  # Consultor
  attr_reader :nombre, :tituloPropiedad
end
end
