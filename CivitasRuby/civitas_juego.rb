# encoding:utf-8
module Civitas
  
require_relative 'gestor_estados'  
require_relative 'jugador'
require_relative 'mazo_sorpresas'
require_relative 'tipo_sorpresa'
require_relative 'sorpresa'
require_relative 'dado'
require_relative 'tablero'
require_relative 'titulo_propiedad'
require_relative 'operaciones_juego'
  
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
    @mazo.al_mazo( Sorpresa.new_IRCARCEL( TipoSorpresa::IRCARCEL, tablero) )
    @mazo.al_mazo( Sorpresa.new_SALIRCARCEL(TipoSorpresa::SALIRCARCEL, @mazo) )
    @mazo.al_mazo( Sorpresa.new_resto(TipoSorpresa::PORJUGADOR, -300, "PAGAR_PORJUGADOR_1") )
    @mazo.al_mazo( Sorpresa.new_resto( TipoSorpresa::PORJUGADOR, 300, "COBRAR_PORJUGADOR_1" ) )
    @mazo.al_mazo( Sorpresa.new_IRCASILLA(TipoSorpresa::IRCASILLA, tablero, 9, "IRCASILLA_9"))
    @mazo.al_mazo( Sorpresa.new_IRCASILLA(TipoSorpresa::IRCASILLA, tablero, 19, "IRCASILLA_19"))
    @mazo.al_mazo( Sorpresa.new_resto(TipoSorpresa::PORCASAHOTEL, -100, "PAGAR_PORCASAHOTEL_1") )
    @mazo.al_mazo( Sorpresa.new_resto(TipoSorpresa::PORCASAHOTEL, 100, "COBRAR_PORCASAHOTEL_1") )
    @mazo.al_mazo( Sorpresa.new_resto(TipoSorpresa::PAGARCOBRAR, 500, "COBRAR_1") )
    @mazo.al_mazo( Sorpresa.new_resto(TipoSorpresa::PAGARCOBRAR, -500, "PAGAR_1") )
  end
  
  def inicializar_tablero(mazo)
    num_casilla_carcel = 5
    @tablero = Tablero.new(num_casilla_carcel)
    
    @tablero.añade_casilla( Casilla.new_CALLE(TituloPropiedad.new("Calle1", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_casilla( Casilla.new_CALLE(TituloPropiedad.new("Calle2", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_casilla( Casilla.new_SORPRESA(mazo, "Sorpresa1"))
    @tablero.añade_casilla( Casilla.new_CALLE(TituloPropiedad.new("Calle3", 10, 1.1, 500, 600, 250)) )
    #Cárcel
    @tablero.añade_casilla( Casilla.new_CALLE(TituloPropiedad.new("Calle4", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_casilla( Casilla.new_CALLE(TituloPropiedad.new("Calle5", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_casilla( Casilla.new_SORPRESA(mazo, "Sorpresa2"))
    @tablero.añade_casilla( Casilla.new_CALLE(TituloPropiedad.new("Calle6", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_casilla( Casilla.new_DESCANSO("PARKING") )
    @tablero.añade_casilla( Casilla.new_CALLE(TituloPropiedad.new("Calle7", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_casilla( Casilla.new_CALLE(TituloPropiedad.new("Calle8", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_casilla( Casilla.new_SORPRESA(mazo, "Sorpresa3"))
    @tablero.añade_casilla( Casilla.new_CALLE(TituloPropiedad.new("Calle9", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_juez
    @tablero.añade_casilla( Casilla.new_CALLE(TituloPropiedad.new("Calle10", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_casilla( Casilla.new_CALLE(TituloPropiedad.new("Calle11", 10, 1.1, 500, 600, 250)) )
    @tablero.añade_casilla( Casilla.new_IMPUESTO(10, "IMPUESTO") )
    @tablero.añade_casilla( Casilla.new_CALLE(TituloPropiedad.new("Calle12", 10, 1.1, 500, 600, 250)) )
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