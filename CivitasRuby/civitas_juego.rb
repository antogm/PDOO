# encoding:utf-8
module Civitas
  
require_relative 'gestor_estados'  
require_relative 'jugador'
require_relative 'mazo_sorpresas'
require_relative 'sorpresa'
require_relative 'dado'
require_relative 'tablero'
require_relative 'titulo_propiedad'
require_relative 'operaciones_juego'
require_relative 'casilla_calle'
require_relative 'casilla_impuesto'
require_relative 'casilla_juez'
require_relative 'casilla_sorpresa'
require_relative 'sorpresa_ir_carcel'
require_relative 'sorpresa_ir_casilla'
require_relative 'sorpresa_pagar_cobrar'
require_relative 'sorpresa_por_casa_hotel'
require_relative 'sorpresa_por_jugador'
require_relative 'sorpresa_salir_carcel'
  
class CivitasJuego
  
  def initialize(nombres)
    @jugadores = []
    num_jugadores = nombres.size
    for i in 0..num_jugadores-1
      @jugadores.push(Jugador.new(nombres[i]))
    end
      
    @gestorEstados = Gestor_estados.new
    @estado = @gestorEstados.estado_inicial
    @indiceJugadorActual = Dado.instance.quien_empieza(num_jugadores)
    
    @mazo = MazoSorpresas.new
    inicializar_tablero(@mazo)
    inicializar_mazo_sorpresas(@tablero)
  end
  
  def avanza_jugador
    jugador_actual = @jugadores[@indiceJugadorActual]
		posicion_actual = jugador_actual.numCasillaActual
		tirada = Dado.instance.tirar()
		posicion_nueva = @tablero.nueva_posicion(posicion_actual, tirada)
		casilla = @tablero.get_casilla(posicion_nueva)
		contabilizar_pasos_por_salida(jugador_actual)
		jugador_actual.mover_a_casilla(posicion_nueva)
		casilla.recibe_jugador(@indiceJugadorActual, @jugadores)
		contabilizar_pasos_por_salida(jugador_actual)
  end
  
  def cancelar_hipoteca(ip)
    get_jugador_actual.cancelar_hipoteca(ip)
  end
  
  def comprar
    jugador_actual = @jugadores[@indiceJugadorActual]
    casilla = @tablero.get_casilla(jugador_actual.numCasillaActual)
    titulo = casilla.tituloPropiedad
    jugador_actual.comprar(titulo)
  end
  
  def construir_casa(ip)
    get_jugador_actual.construir_casa(ip)
  end
  
  def construir_hotel(ip)
    get_jugador_actual.construir_hotel(ip)
  end
  
  def contabilizar_pasos_por_salida(jugador)
    while @tablero.get_por_salida > 0 do
      jugador.pasa_por_salida
    end
  end
  
  def final_del_juego 
    for i in 0..@jugadores.size-1
      if @jugadores[i].en_bancarrota
        return true
      end
    end
    
    return false
  end
  
  def get_casilla_actual
    num_casilla = get_jugador_actual.numCasillaActual
    @tablero.get_casilla(num_casilla)
  end
  
  def get_jugador_actual
    return @jugadores[@indiceJugadorActual]
  end
  
  def hipotecar(ip)
    get_jugador_actual.hipotecar(ip)
  end
  
  def info_jugador_texto
    str = "Jugador actual: " + get_jugador_actual.nombre + " en la casilla: " + get_jugador_actual.numCasillaActual.to_s
    return str
  end
  
  def inicializar_mazo_sorpresas(tablero)
    @mazo.al_mazo( SorpresaIrCarcel.new(tablero, tablero.numCasillaCarcel, "Ir a la carcel") )
    @mazo.al_mazo( SorpresaSalirCarcel.new(@mazo, "Salvoconducto") )
    @mazo.al_mazo( SorpresaPorJugador.new( -300, "Pagas 300 a cada jugador") )
    @mazo.al_mazo( SorpresaPorJugador.new( 300, "Cobras 300 por cada jugador" ) )
    @mazo.al_mazo( SorpresaIrCasilla.new(tablero, 9, "Ir a la casilla 9"))
    @mazo.al_mazo( SorpresaIrCasilla.new(tablero, 19, "Ir a la casilla 19"))
    @mazo.al_mazo( SorpresaPorCasaHotel.new(-100, "Pagas 100 por cada casas u hotel de tu propiedad") )
    @mazo.al_mazo( SorpresaPorCasaHotel.new(100, "Cobras 100 por cada casa u hotel de tu propiedad") )
    @mazo.al_mazo( SorpresaPagarCobrar.new(500, "Cobras 500") )
    @mazo.al_mazo( SorpresaPagarCobrar.new(-500, "Pagas 500") )
  end
  
  def inicializar_tablero(mazo)
    num_casilla_carcel = 5
    @tablero = Tablero.new(num_casilla_carcel)
    
    @tablero.añade_casilla( CasillaCalle.new(TituloPropiedad.new("Calle1", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_casilla( CasillaCalle.new(TituloPropiedad.new("Calle2", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_casilla( CasillaSorpresa.new(mazo, "Sorpresa1"))
    @tablero.añade_casilla( CasillaCalle.new(TituloPropiedad.new("Calle3", 10, 1.1, 500, 600, 250)) )
    #Cárcel
    @tablero.añade_casilla( CasillaCalle.new(TituloPropiedad.new("Calle4", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_casilla( CasillaCalle.new(TituloPropiedad.new("Calle5", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_casilla( CasillaSorpresa.new(mazo, "Sorpresa2"))
    @tablero.añade_casilla( CasillaCalle.new(TituloPropiedad.new("Calle6", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_casilla( Casilla.new("PARKING") )
    @tablero.añade_casilla( CasillaCalle.new(TituloPropiedad.new("Calle7", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_casilla( CasillaCalle.new(TituloPropiedad.new("Calle8", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_casilla( CasillaSorpresa.new(mazo, "Sorpresa3"))
    @tablero.añade_casilla( CasillaCalle.new(TituloPropiedad.new("Calle9", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_juez
    @tablero.añade_casilla( CasillaCalle.new(TituloPropiedad.new("Calle10", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_casilla( CasillaCalle.new(TituloPropiedad.new("Calle11", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_casilla( CasillaImpuesto.new(10, "IMPUESTO") )
    @tablero.añade_casilla( CasillaCalle.new(TituloPropiedad.new("Calle12", 10, 1.1, 500, 600, 250)) )
  end
  
  def pasar_turno
    if @indiceJugadorActual < @jugadores.length-1
      @indiceJugadorActual += 1
    else
      @indiceJugadorActual = 0
    end
  end
  
  def ranking
    ranking = @jugadores[0 .. @jugadores.length-1]
    ranking.sort.reverse
    return ranking
  end
  
  def salir_carcel_pagando
    get_jugador_actual.salir_carcel_pagando
  end
  
  def salir_carcel_tirando
    get_jugador_actual.salir_carcel_tirando
  end
  
  def siguiente_paso
    jugador_actual = @jugadores[@indiceJugadorActual]
    operacion = @gestorEstados.operaciones_permitidas(jugador_actual, @estado)
    if operacion == Operaciones_juego::PASAR_TURNO
      pasar_turno
      siguiente_paso_completado(operacion)
    elsif operacion == Operaciones_juego::AVANZAR
      avanza_jugador
      siguiente_paso_completado(operacion)
    end
    
    operacion
  end
  
  def siguiente_paso_completado(operacion)
    @estado = @gestorEstados.siguiente_estado(get_jugador_actual, @estado, operacion)
  end
  
  def vender(ip)
    get_jugador_actual.vender(ip)
  end

  attr_reader :numCasillaActual
end
end
