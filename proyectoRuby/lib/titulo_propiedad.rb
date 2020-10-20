module Civitas
class TituloPropiedad
  @@factorInteresesHipoteca = 1.1
  
  # Constructor
  def initialize (nombre, ab, fr, hb, pc, pe)
    @nombre = nombre;
    @alquilerBase = ab;
    @factorRevalorizacion = fr;
    @hipotecaBase = hb;
    @hipotecado = false;
    @numCasas = 0;
    @numHoteles = 0;
    @precioCompra = pc;
    @precioEdificar = pe;
  end
  
  # Métodos
  def actualiza_propietario_por_conversion (jugador)
    @propietario = jugador
  end
  
  #def cancelar_hipoteca (jugador)
    
  #end
  
  def cantidad_casas_hoteles
    return @numCasas + @numHoteles
  end
  
  #def comprar (jugador)
    
  #end
  
  #def construir_casa (jugador)
    
  #end
  
  #def construir_hotel (jugador)
    
  #end
  
  def derruir_casas (n, jugador)
    if jugador == @propietario && @numCasas >= n
      @numCasas -= n
	  return true
    end
          
    return false
  end
  
  def es_este_el_propietario (jugador)
    return tiene_propietario && (jugador  == @propietario)
  end
    
  def get_importe_cancelar_hipoteca
    return get_importe_hipoteca * @@factorInteresesHipoteca
  end
  
  def get_importe_hipoteca
    return @hipotecaBase*(1+(@numCasas*0.5)+(@numHoteles*2.5))
  end
      
  def get_precio_alquiler
    if @hipotecado || @propietario.is_encarcelado
      return 0
    else
      return @alquilerBase*(1+(@numCasas*0.5)+(@numHoteles*2.5))
    end
  end
      
  def get_precio_venta
    return @precioCompra + (@@factorRevalorizacion * (@numCasas + 5*@numHoteles) * @precioEdificar)
  end
  
  #def hipotecar(jugador)
    
  #end
  
  def propietario_encarcelado
    if @propietario.is_encarcelado
      return true
    else
      return false
    end
  end
  
  def tiene_propietario
    return propietario != nil
  end
  
  def to_string
    str =  "Nombre de la propiedad: " + @nombre
    str += "\nPrecio de alquiler: " + @alquilerBase
    str += "\nPrecio dde compra: " + @precioCompra
    str += "\nPrecio edificar: " + @precioEdificar + "\n"
    
    return str
  end
  
  def tramitar_alquiler(jugador)
    if tiene_propietario && jugador!=@propietario
      jugador.paga_alquiler(get_precio_alquiler)
      @propietario.recib(get_precio_alquiler)
    end
  end
  
  def vender(jugador)
    if tiene_propietario && @propietario == jugador && !@hipotecado
      @propietario.recibe(get_precio_venta)
      derruir_casas(@numCasas, propietario)
      @numHoteles = 0
      @propietario = nil
      return true
    else
	  return false
    end
  end
  
  attr_reader :precioCompra, :precioEdificar, :propietario, :numHoteles, :numCasas, :nombre, :hipotecado
end
end
