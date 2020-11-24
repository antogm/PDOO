require_relative 'civitas_juego'
require_relative 'vista_textual'
require_relative 'operaciones_juego'
require_relative 'gestiones_inmobiliarias'
require_relative 'operaciones_juego'
require_relative 'salidas_carcel'
require_relative 'respuestas'
require_relative 'operacion_inmobiliaria'

module Civitas
  
  class Controlador
    def initialize(juego, vista)
      @juego = juego
      @vista = vista
    end
    
    def juega
      @vista.setCivitasJuego(@juego)
      
      while not @juego.final_del_juego do
        @vista.actualizarVista
        @vista.pausa
        siguiente_operacion = @juego.siguiente_paso

        if siguiente_operacion != Operaciones_juego::PASAR_TURNO
          @vista.mostrarEventos
        end
        
        if not @juego.final_del_juego
          case siguiente_operacion
          when Operaciones_juego::COMPRAR
            if @vista.comprar == Respuestas::SI
              @juego.comprar
            end
            @juego.siguiente_paso_completado(siguiente_operacion)
        
          when Operaciones_juego::GESTIONAR
            @vista.gestionar
            i_gestion = @vista.getGestion
            ip = @vista.getPropiedad
            gestion = Lista_GestionesInmobiliarias[i_gestion]
            operacion = OperacionInmobiliaria.new(gestion, ip)
            
            case gestion
            when GestionesInmobiliarias::TERMINAR
              @juego.siguiente_paso_completado(siguiente_operacion)
            when GestionesInmobiliarias::VENDER
              @juego.vender(ip)
            when GestionesInmobiliarias::HIPOTECAR
              @juego.hipotecar(ip)
            when GestionesInmobiliarias::CANCELAR_HIPOTECA
              @juego.cancelar_hipoteca(ip)
            when GestionesInmobiliarias::CONSTRUIR_CASA
              @juego.construir_casa(ip)
            when GestionesInmobiliarias::CONSTRUIR_HOTEL
              @juego.construir_hotel(ip)
            end
              
          when Operaciones_juego::SALIR_CARCEL
            s = @vista.salir_carcel
            if s == SalidasCarcel::PAGANDO
              @juego.salir_carcel_pagando
            elsif s == SalidasCarcel::TIRANDO
              @juego.salir_carcel_tirando
            end
            
            @juego.siguiente_paso_completado(siguiente_operacion)
          else
          end
        end
      end
      
      # final del juego -> muestra ranking 
			ranking = @juego.ranking
      puts("El juego ha finalizado. La clasificacion ha sido:\n")
      for i in 0..ranking.length()-1
        cadena = i + ") " + ranking[i].to_s
        puts(cadena)
      end
    end
  end
end
