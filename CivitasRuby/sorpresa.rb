require_relative 'diario'

module Civitas
class Sorpresa
    
  # Constructor  
  def initialize(texto)
    @texto = texto
  end
    
  # Métodos    
  def informe(actual, todos)
    evento = "Se ha aplicado una sorpresa " + to_string + " al jugador " + todos[actual].get_nombre + "\n"
    Diario.instance.ocurre_evento(evento)
  end
    
  def jugador_correcto(actual, todos)
    return actual < todos.length
  end
    
  def to_string
    @texto
  end
  
  private_class_method :new
end
end