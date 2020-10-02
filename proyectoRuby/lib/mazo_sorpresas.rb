module Civitas
class MazoSorpresas
   
  private
  def init
    @sorpresas = []
    @cartasEspeciales = []
    @barajada = false
    @usadas = 0
  end
  
  public
  def initialize(b=false)
    @debug = b
    init
    
    if @debug
      Diario.instance.ocurre_evento("Modo debug del mazo de sorpresas activado")
    end
  end
  
  def al_mazo(s)
    if !@barajada
      @sorpresas.push(s)
    end
  end
  
  def siguiente
    if (!@barajada || @usadas == @sorpresas.length) && !@debug
      @sorpresas.shuffle
      @usadas = 0
      @barajada = true
    end
    
    @usadas += 1
    @ultimaSorpresa = @sorpresas[0]
    @sorpresas.delete_at(0)
    @ultimaSorpresa
  end
  
  def inhabilitar_carta_especial(s)
    if @sorpresas.include?(s)
      @sorpresas.delete_at( @sorpresas.index(s) )
      @cartasEspeciales.push(s)
      Diario.instance.ocurre_evento("Carta especial " + s.nombre + " deshabilitada")
    end
  end
  
  def habilitar_carta_especial(s)
    if @cartasEspeciales.include?(s)
      @cartasEspeciales.delete_at( @cartasEspeciales.index(s) )
      @sorpresas.push(s)
      Diario.instance.ocurre_evento("Carta especial " + s.nombre + " habilitada")
    end
  end
end
end
