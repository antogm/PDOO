require_relative 'sorpresa'
require_relative 'mazo_sorpresas'

module Civitas
class SorpresaSalirCarcel
  
  def initialize(mazo, texto)
    super()
    @mazo = mazo
    @texto = texto
  end
  
   def aplicar_a_jugador(actual, todos)
    if jugador_correcto(actual, todos)
      informe(actual, todos)
      
      tiene_salvoconducto = false
      for i in 0..3
        if (todos[i].tiene_salvoconducto)
          tiene_salvoconducto = true
        end
      end
      
      if (!tiene_salvoconducto)
        todos[actual].obtener_salvoconducto
        salir_del_mazo
      end
    end
  end
  
  def usada
    if @tipo == TipoSorpresa::SALIRCARCEL
      @mazo.habilitar_carta_especial(self)
    end
  end
  
  def  salir_del_mazo
    if @tipo == TipoSorpresa::SALIRCARCEL
      @mazo.inhabilitar_carta_especial(self)
    end
  end
  
  public_class_method :new
end
end
