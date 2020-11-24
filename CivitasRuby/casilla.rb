# encoding:utf-8
require_relative 'tipo_casilla'
require_relative 'mazo_sorpresas'
require_relative 'jugador'
require_relative 'titulo_propiedad'

module Civitas
class Casilla
    
  def self.new_DESCANSO(_nombre)
    new(nil, TipoCasilla::DESCANSO, _nombre, nil, nil, nil)
  end
  
  def self.new_CALLE(titulo)
    new(nil, TipoCasilla::CALLE, titulo.nombre, titulo, nil, nil)
  end
  
  def self.new_IMPUESTO(cantidad, nombre)
    new(nil, TipoCasilla::IMPUESTO, nombre, nil, cantidad, nil)
  end
  
  def self.new_JUEZ(numCasillaCarcel, nombre)
    new(nil, TipoCasilla::JUEZ, nombre, nil, nil, numCasillaCarcel)
  end
  
  def self.new_SORPRESA(mazo, nombre)
    new(mazo, TipoCasilla::SORPRESA, nombre, nil, nil, nil)
  end
  
  # Constructor
  def initialize(mazo, tipo, nombre, titulo, cantidad, numCasillaCarcel)
    init
    @tipo = tipo
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
      evento = "El jugador " + nombre_jugador + " ha caido en la casilla " + to_string
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
    case @tipo
    when TipoCasilla::CALLE
      recibe_jugador_calle(actual,todos)
    when TipoCasilla::IMPUESTO
      recibe_jugador_impuesto(actual, todos)
    when TipoCasilla::JUEZ
      recibe_jugador_juez(actual, todos)
    when TipoCasilla::SORPRESA
      recibe_jugador_sorpresa(actual, todos)
    end
    informe(actual, todos)
  end
  
  def recibe_jugador_calle(actual, todos)
    if jugador_correcto(actual, todos)
      informe(actual, todos)
      if !@tituloPropiedad.tiene_propietario
        todos[actual].puede_comprar_casilla
      else
        @tituloPropiedad.tramitar_alquiler(todos[actual])
      end
    end
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
    if jugador_correcto(actual, todos)
      sorpresa = @mazo.siguiente
      informe(actual, todos)
      sorpresa.aplicar_a_jugador(actual, todos)
    end
  end
  
  def to_string
    return @nombre
  end
  
  # Consultor
  attr_reader :nombre, :tituloPropiedad
end
end
