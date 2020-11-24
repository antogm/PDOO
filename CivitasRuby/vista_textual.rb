#encoding:utf-8
require_relative 'operaciones_juego'
require_relative 'civitas_juego'
require_relative 'jugador'
require_relative 'diario'
require 'io/console'

module Civitas

  class Vista_textual

    def mostrar_estado(estado)
      puts estado
    end
    
    def pausa
      print "Pulsa una tecla"
      STDIN.getch
      print "\n"
    end

    def lee_entero(max,msg1,msg2)
      ok = false
      begin
        print msg1
        cadena = gets.chomp
        begin
          if (cadena =~ /\A\d+\Z/)
            numero = cadena.to_i
            ok = true
          else
            raise IOError
          end
        rescue IOError
          puts msg2
        end
        if (ok)
          if (numero >= max)
            ok = false
          end
        end
      end while (!ok)

      return numero
    end

    def menu(titulo,lista)
      tab = "  "
      puts titulo
      index = 0
      lista.each { |l|
        puts tab+index.to_s+"-"+l
        index += 1
      }

      opcion = lee_entero(lista.length,
                          "\n"+tab+"Elige una opcion: ",
                          tab+"Valor erroneo")
      return opcion
    end

    def salir_carcel
      opcion = menu("Elige la forma para intentar salir de la carcel", ["Pagando", "Tirando el dado"])
      Lista_SalidasCarcel[opcion]
    end
    
    def comprar
      respuestas = []
      for i in 0..Lista_Respuestas.size-1
        respuestas.push(Lista_Respuestas[i].to_s)
      end
      
      opcion = menu("¿Desea comprar la propiedad?", respuestas)
      Lista_Respuestas[opcion]
    end

    def gestionar
      gestiones = []
      for i in 0..Lista_GestionesInmobiliarias.size-1
        gestiones.push(Lista_GestionesInmobiliarias[i].to_s)
      end
      @iGestion = menu("Introduzca que gestion desea realizar", gestiones)
		
      if @iGestion != 5
        str_propiedades = []
        propiedades = @juegoModel.get_jugador_actual.propiedades
        
        for i in 0..propiedades.size-1
          cadena = propiedades[i].nombre + ". Precio: " + propiedades[i].precioCompra.to_s
          str_propiedades.push(cadena)
        end

        @iPropiedad = menu("Seleccione que propiedad gestionar", str_propiedades)
      end
    end

    def getGestion
      @iGestion
    end

    def getPropiedad
      @iPropiedad
    end

    def mostrarSiguienteOperacion(operacion)
      puts("La siguiente operacion a realizar es: " + operacion.to_s)
    end

    def mostrarEventos
      while Diario.instance.eventos_pendientes
        puts(Diario.instance.leer_evento)
      end
    end

    def setCivitasJuego(civitas)
         @juegoModel=civitas
         self.actualizarVista
    end

    def actualizarVista
      jugador = @juegoModel.get_jugador_actual
      num_propiedades = jugador.propiedades.size
      casilla_actual = @juegoModel.get_casilla_actual
		
      info = "Jugador actual: " + jugador.nombre.to_s
			info += "\nCasilla: " + casilla_actual.nombre
			info += "\nNum. propiedades: " + num_propiedades.to_s + "\n"
		
      for i in 0..num_propiedades-1
        propiedades = jugador.propiedades
        info += i.to_s + ") " + propiedades[i].to_s + "\n"
      end
      info += "\n"
    end
  end
end
