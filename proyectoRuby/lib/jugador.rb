# encoding:utf-8


module Civitas
class Jugador
  
  @@CasasMax = 4
  @@CasasPorHotel = 4
  @@HotelesMax = 4
  @@PasoPorSalida = 1000
  @@PrecioLibertad = 200
  @@SaldoInicial = 7500
  
  def self.new_copia(otro_jugador)
    new(otro_jugador.nombre)
    @encarcelado = otro_jugador.encarcelado
    @numCasillaActual = otro_jugador.numCasillaActual
    @puedeComprar = otro_jugador.puedeComprar
    @saldo = otro_jugador.saldo
    @salvoconducto = otro_jugador.salvoconducto
    @propiedades = otro_jugador.propiedades[0 .. otro_jugador.propiedades.length-1]
    #@propiedades = otro_jugador.propiedades.clone
  end
  
  def initialize(nombre)
    @nombre = nombre
    @encarcelado = false
    @numCasillaActual = 0
    @puedeComprar = false
    @saldo = @@SaldoInicial
    @propiedades = []
    @salvoconducto = nil
  end
  
  private :initialize
  
  def cancelar_hipoteca(ip)
    # no implementado
  end
  
  def cantidad_casas_hoteles
    num = 0
    
    for i in 0..@propiedades.length do
     num += @propiedades[i].cantidad_casas_hoteles
    end
    
    num
  end
  
  def <=> (otro)
    self.saldo <=> otro.saldo
  end
  
  def compare_to(otro)
    self <=> otro
  end
  
  def comprar(titulo)
    # no implementado
  end
  
  def construir_casa(ip)
    # no implementado
  end
  
  def construir_hotel(ip)
    # no implementado
  end
  
  def debe_ser_encarcelado
    if @encarcelado
      false
    elsif !tiene_salvoconducto
      true
    end
    
    perder_salvoconducto
    evento = "El jugador " + @nombre + " se libra de la carcel y pierde su salvoconducto"
    Diario.instance.ocurre_evento(evento)
    false
  end
  
  def en_bancarrota
    @saldo < 0
  end
  
  def encarcelar(num_casilla_carcel)
    if debe_ser_encarcelado
      mover_a_casilla(num_casilla_carcel)
      @encarcelado = true
      evento = "El jugador " + @nombre + " ha sido encarcelado"
      Diario.instance.ocurre_evento(evento)
    end
  end
  
  def existe_la_propiedad(ip)
    ip < @propiedades.length && ip >= 0
  end
  
  def hipotecar(ip)
    # no implementado
  end
  
  def modificar_saldo(cantidad)
    @saldo += cantidad
    evento = "El jugador " + @nombre + " modifica su saldo por " + cantidad + " euros"
    Diario.instance.ocurre_evento(evento)
    true
  end
  
  def mover_a_casilla(numCasilla)
    if @encarcelado
      false
    else
      @numCasillaActual = numCasilla
      @puedeComprar = false
      evento = "El jugador " + @nombre + " se ha movido a la casilla " + @numCasilla
      Diario.instance.ocurre_evento(evento)
      true
    end
  end
  
  def obtener_salvoconducto(sorpresa)
    if @encarcelado
      false
    else
      @salvoconducto = sorpresa
      true
    end
  end
  
  def paga(cantidad)
    modificar_saldo(cantidad * -1)
  end
  
  def paga_alquiler(cantidad)
    if @encarcelado
      false
    else
      paga(cantidad)
    end
  end
  
  def paga_impuesto(cantidad)
    if @encarcelado
      false
    else
      paga(cantidad)
    end
  end
  
  def pasa_por_salida
    modificar_saldo(@@PasoPorSalida)
    evento = "El jugador " + @nombre + " ha pasado por la casilla de salida y ha cobrado " + @@PasoPorSalida + " euros"
    Diario.instance.ocurre_evento(evento)
    true
  end
  
  def perder_salvoconducto
    @salvoconducto.usada
    @salvoconducto = nil
  end
  
  def puede_comprar_casilla
    if @encarcelado
      @puedeComprar = false
    else
      @puedeComprar = true
    end
    
    @puedeComprar
  end
  
  def puede_salir_carcel_pagando
    saldo >= @@PrecioLibertad
  end
  
  def puedo_edificar_casa(propiedad)
    if propiedad.numCasas < @@CasasMax
      true
    else
      false
    end
  end
  
  def puedo_edificar_hotel(propiedad)
    if propiedad.numHoteles < @@HotelesMax && propiedad.numCasas >= @@CasasPorHotel
      true
    else
      false
    end
  end
  
  def puedo_gastar(precio)
    if @encarcelado
      false
    else
      @saldo >= precio
    end
  end
  
  def recibe(cantidad)
    if @encarcelado
      false
    else
      modificar_saldo(cantidad)
    end
  end
  
  def salir_carcel_pagando
    if @encarcelado && puede_salir_carcel_pagando
      paga(@@PrecioLibertad)
      @encarcelado = false
      evento = "El jugador " + nombre + " ha pagado para salir de la cárcel"
      Diario.instance.ocurre_evento(evento)
      true
    else
      false
    end
  end
  
  def salir_carcel_tirando
    if Dado.instance.salgo_de_la_carcel
      @encarcelado = false
      evento = "El jugador " + @nombre + " sale de la cárcel con su última tirada"
      Diario.instance.ocurre_evento(evento)
      true
    else
      false
    end
  end
  
  def tiene_algo_que_gestionar
    @propiedades.length > 0
  end
  
  def tiene_salvoconducto
    @salvoconducto != nil
  end
  
  def to_string
    "Nombre del jugador " + nombre + ", saldo " + saldo + ", está en la casilla " + numCasillaActual
  end
  
  def vender(ip)
    if @encarcelado
      false
    else
      existe_la_propiedad(ip)
      evento = "El jugador " + @nombre + " ha vendido la propiedad " + @propiedades[ip].nombre
      Diario.instance.ocurre_evento(evento)
      @propiedades.delete_at(ip)
      true
    end
  end
  
  attr_reader :CasasMax, :CasasPorHotel, :HotelesMax, :nombre, :numCasillaActual, :PrecioLibertad, :PasoPorSalida, :propiedades, :puedeComprar, :saldo, :encarcelado
  
end
end
