require 'singleton'
require 'Diario'

module Civitas
class Dado
  include Singleton
  
  def initialize
    @random = Random.new
    @ultimoResultado = 0 
    @debug = false
    @@SalidaCarcel = 5
  end
  
  def tirar
    if @debug
      @ultimoResultado = 1
    else
      @ultimoResultado = @random.rand(1..6)
    end
    
    @ultimoResultado
  end
  
  def salgo_de_la_carcel
    if @ultimoResultado >= @@SalidaCarcel
      true
    else
      false
    end
  end
  
  def quien_empieza(n)
    @random.rand(0..n-1)
  end
  
  def set_debug(b)
    @debug = b
    
    if @debug
      Diario.instance.ocurre_evento("Modo debug activado")
    else
      Diario.instance.ocurre_evento("Modo debug desactivado")
    end
  end
  
  attr_reader :ultimoResultado
end
end
