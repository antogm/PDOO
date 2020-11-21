# encoding:utf-8
require_relative 'tipo_casilla'

module Civitas
class Casilla
    
  def self.new_DESCANSO(_nombre)
    @tipo = TipoCasilla::DESCANSO
    new(nil, _nombre, nil, nil, nil)
  end
  
  def self.new_CALLE(titulo)
    @tipo = TipoCasilla::CALLE
    new(nil, titulo.nombre, titulo, nil, nil)
  end
  
  def self.new_IMPUESTO(cantidad, nombre)
    @tipo = TipoCasilla::IMPUESTO
    new(nil, nombre, nil, cantidad, nil)
  end
  
  def self.new_JUEZ(numCasillaCarcel, nombre)
    @tipo = TipoCasilla::JUEZ
    new(nil, nombre, nil, nil, numCasillaCarcel)
  end
  
  def self.new_SORPRESA(mazo, nombre)
    @tipo = TipoCasilla::SORPRESA
    new(mazo, nombre, nil, nil, nil)
  end
  
  # Constructor
  def initialize(mazo, nombre, titulo, cantidad, numCasillaCarcel)
    init
    @nombre = nombre
    @sorpresa = nil #tipo sorpresa
    @mazo = mazo  # tipo sorpresa
    @tituloPropiedad = titulo #tipo calle
    @@carcel = numCasillaCarcel #tipo juez
    @importe = cantidad
  end
  
  private :initialize
  
  # Métodos
  def informe(actual, todos)
    if jugador_correcto(actual, todos)
      nombre_jugador = todos[actual].nombre
      evento = "El jugador " + nombre_jugador + " ha caído en la casilla " + to_string
      Diario.instance.ocurre_evento(evento)
    end
  end
  
  def init
    @importe = 0
    @sorpresa = nil
    @mazo = nil
    @tituloPropiedad = nil
    @tipo = nil
    @@carcel = 0
    @nombre = "casilla"
  end
  
  def jugador_correcto(actual, todos)
    if actual < todos.length && actual >= 0
      return true
    else
      return false
    end
  end
  
  def recibe_jugador(actual, todos)
    #no implementado
  end
  
  def recibe_jugador_calle(actual, todos)
    # no implementado
  end
  
  def recibe_jugador_impuesto(actual, todos)
    if jugador_correcto(actual, todos)
      informe(actual, todos)
      todos[actual].paga_impuesto(@importe)
    end
  end
  
  def recibe_jugador_juez(actual, todos)
    if jugador_correcto(actual, todos)
      informe(actual, todos)
      todos[actual].encarcelar(@@carcel)
    end
  end
  
  def recibe_jugador_sorpresa(actual, todos)
    # no implementado
  end
  
  def to_string
    return @nombre
  end
  
  # Consultor
  attr_reader :nombre, :tituloPropieddad
end
end
