# encoding :UTF-8
require_relative 'casilla'

module Civitas
class Tablero
  
  # Constructor
  def initialize(n)
    if n > 1
      @numCasillaCarcel = n
    else
      @numCasillaCarcel = 1
    end
      
    @casillas = []
    @porSalida = 0
    @tieneJuez = false
    
    # Añade la casilla de salida
    salida = Casilla.new("Salida")
    @casillas.push(salida)
  end
  
  # Métodos
  private
  def correcto(n=0)
    tablero_ok = (@numCasillaCarcel < @casillas.length) && @tieneJuez
    tablero_ok && (n < @casillas.length) && (n >= 0)
  end
  
  public
  def añade_casilla(c)
    num_casillas = @casillas.length
    if (num_casillas == @numCasillaCarcel)
      @casillas.push(Casilla.new("Cárcel"))
    end
    
    @casillas.push(c)
    if (num_casillas == @numCasillaCarcel)
      @casillas.push(Casilla.new("Cárcel"))
    end
  end
  
  def añade_juez
    if !@tieneJuez
      @casillas.push(Casilla.new("Juez"))
      @tieneJuez = true
    end
  end
  
  def nueva_posicion(actual, tirada)
    if !correcto(actual)
      -1
    else
      nueva_posi = (actual + tirada) % @casillas.length
      
      if correcto(nueva_posi)
        if nueva_posi != (actual + tirada)
          @porSalida += 1
        end
        
        nueva_posi
      end
    end
  end
  
  def calcular_tirada(origen, destino)
    resta = destino - origen
    
    if resta >= 0
      resta
    else
      resta + @casillas.length
    end
  end
  
  # Consultores
  attr_reader :numCasillaCarcel
  
  def get_por_salida
    if @porSalida > 0
      temp = @porSalida
      @porSalida -= 1
      temp
    else
      @porSalida
    end
  end
  
  def get_casilla(n)
    if correcto(n)
      @casillas[n]
    else
      nil
    end
  end  
end
end
