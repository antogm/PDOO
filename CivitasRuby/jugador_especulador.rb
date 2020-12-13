require_relative 'jugador'

module Civitas
class JugadorEspeculador < Jugador

  def initialize(otro_jugador, fianza)
    super(otro_jugador.nombre)
    self.copia(otro_jugador)
    @@FactorEspeculador = 2
    @fianza = fianza
  end
  
  def encarcelar(num_casilla_carcel)
    if debe_ser_encarcelado
      if puedo_gastar(@fianza)
        paga(@fianza)
        Diario.instance.ocurre_evento("El jugador especulador " + @nombre + " se libra de la cárcel pagando fianza de " + @fianza)
      else
        mover_a_casilla(num_casilla_carcel)
        @encarcelado = true
        Diario.instance.ocurre_evento("El jugador " + @nombre + " ha sido encarcelado")
      end
    end
  end
  
  def paga_impuesto(cantidad)
    if @encarcelado
      false;
    else
      self.paga(cantidad / 2);
    end
  end
  
  def to_string
    ("Jugador especulador " + @nombre + ", saldo " + @saldo + ", está en la casilla " + @numCasillaActual)
  end
end
end
