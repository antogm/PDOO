module Civitas
class Casilla
  def initialize(s)
    @nombre = s
  end
  
  # Consultor
  attr_reader :nombre
end
end
