module Civitas
class Sorpresa
  
  # Constructores
  def self.new_IRCARCEL(tipo, tablero)
    new(tipo, tablero, nil, nil, nil)
  end
  
  def self.new_IRCASILLA(tipo, tablero, valor, texto)
    new(tipo, tablero, valor, texto, nil)
  end
  
  def self.new_SALIRCARCEL(tipo, mazo)
    new(tipo, nil, nil, nil, mazo)
  end
  
  def self.new_resto(tipo, valor, texto)
    new(tipo, nil, valor, texto, nil)
  end
  
  def initialize(tipo, tablero, valor, texto, mazo)
    init
    @texto = texto
    @tipo = tipo
    @tablero = tablero
    @mazo = mazo
    
    if valor != nil
      @valor = valor
    end
  end
  
  private :initialize
  
  # Métodos
  def aplicar_a_jugador(actual, todos)
    case @tipo
    when TipoSorpresa::IRCARCEL
      aplicar_a_jugador_ir_carcel(actual, todos)
      
    when TipoSorpresa::IRCASILLA
      aplicar_a_jugador_ir_a_casilla(actual, todos)
      
    when TipoSorpresa::PAGARCOBRAR
      aplicar_a_jugador_pagar_cobrar(actual,todos)
      
    when TipoSorpresa::PORCASAHOTEL
      aplicar_a_jugador_por_casa_hotel(actual, todos)
      
    when TipoSorpresa::PORJUGADOR
      aplicar_a_jugador_por_jugador(actual, todos)
      
    when TipoSorpresa::SALIRCARCEL
      aplicar_a_jugador_salir_carcel(actual, todos)
      
    end
  end
  
  def aplicar_a_jugador_ir_a_casilla(actual, todos)
    if jugador_correcto(actual, todos)
      informe(actual, todos)
      
      casilla_actual = todos[actual].numCasillaActual
      tirada = @tablero.calcular_tirada(casilla_actual, @valor)
      nueva_pos = @tablero.nueva_posicion(casilla_actual, tirada)
      todos[actual].mover_a_casilla(nueva_pos)
      
      casilla = @tablero.get_casilla(nueva_pos)
      casilla.recibe_jugador(actual, todos)
    end
  end
  
  def aplicar_a_jugador_ir_carcel(actual, todos)
    if jugador_correcto(actual, todos)
      informe(actual, todos)
      todos[actual].encarcelar(@tablero.numCasillaCarcel)
    end
  end
  
  def aplicar_a_jugador_pagar_cobrar
    if jugador_correcto(actual, todos)
      informe(actual, todos)
      todos[actual].modificar_saldo(@valor)
    end
  end
  
  def aplicar_a_jugador_por_casa_hotel
    if jugador_correcto(actual, todos)
      informe(actual, todos)
      todos[actual].modificar_saldo( @valor * todos[actual].cantidad_casas_hoteles)
    end
  end
  
  def aplicar_a_jugador_por_jugador
    if jugador_correcto(actual, todos)
      informe(actual, todos)
      str = "Pagas " + @valor + " al jugador " + todos[actual].nombre
      sorpresa_pagar = Sorpresa.new_resto(TipoSorpresa::PAGARCOBRAR, @valor * -1, str)
      
      for i in 0..4 do
        if i != actual
          sorpresa_pagar.aplicar_a_jugador(i, todos)
        end
      end
      
      str = "Recibes " + @valor + " de cada jugador (" + @valor*(todos.size()-1) + " en total)"
      sorpresa_cobrar = Sorpresa.new_resto(TipoSorpresa::PAGARCOBRAR, @valor, str)
      sorpresa_cobrar.aaplicar_a_jugador(actual, todos)
    end
  end
  
  def aplicar_a_jugador_salir_carcel
    if jugador_correcto(actual, todos)
      informe(actual, todos)
      
      tiene_salvoconducto = false
      for i in 0..4 do
        if (todos[i].tiene_salvoconducto)
          tiene_salvoconducto = true
        end
      end
      
      if (!tiene_salvoconducto)
        todos[actual].obtener_salvoconducto
        salir_del_mazo
      end
    end
  end
  
  def informe(actual, todos)
    evento = "Se ha aplicado una sorpresa " + to_string + " al jugador " + todos[actual].get_nombre + "\n";
    Diario.instance.ocurre_evento(evento)
  end
  
  def init
    @valor = -1
  end
  
  def jugador_correcto(actual, todos)
    actual < todos.length
  end
  
  def  salir_del_mazo
    if @tipo == TipoSorpresa::SALIRCARCEL
      @mazo.inhabilitar_carta_especial(self)
    end
  end
  
  def to_string
    nombre = " "
    
    case @tipo
    when TipoSorpresa::IRCARCEL
      nombre = "IRCARCEL"
      
    when TipoSorpresa::IRCASILLA
      nombre = "IRCASILLA"
      
    when TipoSorpresa::PAGARCOBRAR
      nombre = "PAGARCOBRAR"
      
    when TipoSorpresa::PORCASAHOTEL
      nombre = "PORCASAHOTEL"
      
    when TipoSorpresa::PORJUGADOR
      nombre = "PORJUGADOR"
      
    when TipoSorpresa::SALIRCARCEL
      nombre = "SALIRCARCEL"
    end
    
    nombre
  end
  
  def usada
    if @tipo == TipoSorpresa::SALIRCARCEL
      @mazo.habilitar_carta_especial(self)
    end
  end
  
end
end