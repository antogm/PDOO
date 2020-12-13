require_relative 'sorpresa'

module Civitas
class SorpresaPorJugador < Sorpresa
  
  def initialize(valor, texto)
    super(texto)
    @valor = valor
  end
  
  def aplicar_a_jugador(actual, todos)
    if jugador_correcto(actual, todos)
      informe(actual, todos)
      str = "Pagas " + @valor + " al jugador " + todos[actual].nombre
      sorpresa_pagar = Sorpresa.new_resto(TipoSorpresa::PAGARCOBRAR, @valor * -1, str)
      
      for i in 0..3
        if i != actual
          sorpresa_pagar.aplicar_a_jugador(i, todos)
        end
      end
      
      str = "Recibes " + @valor + " de cada jugador (" + @valor*(todos.size()-1) + " en total)"
      sorpresa_cobrar = Sorpresa.new_resto(TipoSorpresa::PAGARCOBRAR, @valor, str)
      sorpresa_cobrar.aaplicar_a_jugador(actual, todos)
    end
  end
  
  public_class_method :new
end
end
