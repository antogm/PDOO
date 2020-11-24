# encoding:utf-8
require_relative 'titulo_propiedad'

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
    result = false
		
    if (@encarcelado)
			return result
		end
        
		if (existe_la_propiedad(ip))
			propiedad = @propiedades[ip]
			cantidad = propiedad.get_importe_cancelar_hipoteca()
			puedo_gastar = puedo_gastar(cantidad)

			if (puedo_gastar)
				result = propiedad.cancelar_hipoteca(self)
				
				if (result)
					evento = "El jugador " + nombre + " cancela la hipoteca de la propiedad " + ip.to_s
					Diario.instance.ocurre_evento(evento)
				end
			end
		end
		
		result
  end
  
  def cantidad_casas_hoteles
    num = 0
    
    for i in 0..@propiedades.length-1
     num += @propiedades[i].cantidad_casas_hoteles
    end
    
    return num
  end
  
  def <=> (otro)
    return self.saldo <=> otro.saldo
  end
  
  def compare_to(otro)
    return self <=> otro
  end
  
  def comprar(titulo)
    result = false
		
		if (@encarcelado)
			return result
		end

		if (@puedeComprar)			
			if puedo_gastar(titulo.precioCompra)
				result = titulo.comprar(self)
				
				if (result)
					@propiedades.push(titulo)
					Diario.instance.ocurre_evento("El jugador " + @nombre + " compra la propiedad " + titulo.nombre)
				end
        @puedeComprar = false
			end
		end
		
		result
  end
  
  def construir_casa(ip)
    result = false
		puedo_edificar_casa = false
		
		if (@encarcelado)
			return result
		else
      existe = existe_la_propiedad(ip)
		
      if (existe)
        propiedad = @propiedades[ip]
        puedo_edificar_casa = puedo_edificar_casa(propiedad)
			
        if (puedo_edificar_casa)
          result = propiedad.construir_casa(self)
        end
      end
    end

		result
  end
  
  def construir_hotel(ip)
    result = false
		
		if (@encarcelado)
			return result
    end
		
		if (existe_la_propiedad(ip))
			propiedad = @propiedades[ip]
			puedo_edificar_hotel = puedo_edificar_hotel(propiedad)
			
			if (puedo_edificar_hotel)
				result = propiedad.construir_hotel(self)
				propiedad.derruir_casas(@@CasasPorHotel, self)
				evento = "El jugador " + @nombre + " construye hotel en la propiedad " + ip.to_s
				Diario.instance.ocurre_evento(evento)
			end
		end
		
		return result
  end
  
  def debe_ser_encarcelado
    if @encarcelado
	  return false
    elsif !tiene_salvoconducto
	  return true
    end
    
    perder_salvoconducto
    evento = "El jugador " + @nombre + " se libra de la carcel y pierde su salvoconducto"
    Diario.instance.ocurre_evento(evento)
    return false
  end
  
  def en_bancarrota
    return @saldo < 0
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
    return (ip < @propiedades.length) && (ip >= 0)
  end
  
  def hipotecar(ip)
    result = false
		
		if (@encarcelado)
			return result
		end
		
		if (existe_la_propiedad(ip))
			propiedad = @propiedades[ip]
			result = propiedad.hipotecar(self)
		end
		
		if (result)
			evento = "El jugador " + @nombre + " hupoteca la propiedad " + ip.to_s
			Diario.instance.ocurre_evento(evento)
		end

		result
  end
  
  def modificar_saldo(cantidad)
    @saldo += cantidad
    evento = "El jugador " + @nombre + " modifica su saldo por " + cantidad.to_s + " euros"
    Diario.instance.ocurre_evento(evento)
    return true
  end
  
  def mover_a_casilla(numCasilla)
    if @encarcelado
      return false
    else
      @numCasillaActual = numCasilla
      @puedeComprar = false
      evento = "El jugador " + @nombre + " se ha movido a la casilla " + @numCasillaActual.to_s
      Diario.instance.ocurre_evento(evento)
      return true
    end
  end
  
  def obtener_salvoconducto(sorpresa)
    if @encarcelado
	  return false
    else
      @salvoconducto = sorpresa
      return true
    end
  end
  
  def paga(cantidad)
    modificar_saldo(cantidad * -1)
  end
  
  def paga_alquiler(cantidad)
    if @encarcelado
	  return false
    else
      paga(cantidad)
    end
  end
  
  def paga_impuesto(cantidad)
    if @encarcelado
	  return false
    else
      paga(cantidad)
    end
  end
  
  def pasa_por_salida
    modificar_saldo(@@PasoPorSalida)
    evento = "El jugador " + @nombre + " ha pasado por la casilla de salida y ha cobrado " + @@PasoPorSalida + " euros"
    Diario.instance.ocurre_evento(evento)
    return true
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

    return @puedeComprar
  end
  
  def puede_salir_carcel_pagando
    return saldo >= @@PrecioLibertad
  end
  
  def puedo_edificar_casa(propiedad)
    precio = propiedad.precioEdificar
    
    if puedo_gastar(precio) && propiedad.numCasas < @@CasasMax
    	return true
    else
      return false
    end
  end
  
  def puedo_edificar_hotel(propiedad)
    if propiedad.numHoteles < @@HotelesMax && propiedad.numCasas >= @@CasasPorHotel
	  return true
    else
	  return false
    end
  end
  
  def puedo_gastar(precio)
    if @encarcelado
	  return false
    else
	  return @saldo >= precio
    end
  end
  
  def recibe(cantidad)
    if @encarcelado
	  return false
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
      return true
    else
	  return false
    end
  end
  
  def salir_carcel_tirando
    if Dado.instance.salgo_de_la_carcel
      @encarcelado = false
      evento = "El jugador " + @nombre + " sale de la cárcel con su última tirada"
      Diario.instance.ocurre_evento(evento)
      return true
    else
	  return false
    end
  end
  
  def tiene_algo_que_gestionar
    return @propiedades.length > 0
  end
  
  def tiene_salvoconducto
    return @salvoconducto != nil
  end
  
  def to_string
	str = "Nombre del jugador " + nombre + ", saldo " + saldo + ", está en la casilla " + numCasillaActual
	return str
  end
  
  def vender(ip)
    salida = false
    
    if !@encarcelado && existe_la_propiedad(ip)
      if @propiedades[ip].vender(self)
        evento = "El jugador " + @nombre + " ha vendido la propiedad " + @propiedades[ip].nombre
        Diario.instance.ocurre_evento(evento)
        @propiedades.delete_at(ip)
        salida = true
      end
    end
    
    salida
  end
  
  attr_reader :CasasMax, :CasasPorHotel, :HotelesMax, :nombre, :numCasillaActual, :PrecioLibertad, :PasoPorSalida, :puedeComprar, :saldo, :encarcelado, :propiedades
  
end
end
