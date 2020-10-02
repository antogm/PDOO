module Civitas
  class Enumerados
    module TipoCasilla
      CALLE =     :calle
      SORPRESA =  :sorpresa
      JUEZ =      :juez
      IMPUESTO =  :impuesto
      DESCANSO =  :descanso
    end
    
    module TipoSorpresa
      IRCARCEL =      :ircarcel
      IRCASILLA =     :ircasilla
      PAGARCOBRAR =   :pagarcobrar
      PORCASAHOTEL =  :porcasahotel
      PORJUGADOR =    :porjugador
      SALIRCARCEL =   :salircarcel
    end
    
    module EstadosJuego
      INICIO_TURNO =      :inicio_turno  
      DESPUES_CARCEL =    :despues_carcel
  		DESPUES_AVANZAR =   :despues_avanzar
  		DESPUES_COMPRAR =   :despues_comprar
  		DESPUES_GESTIONAR = :despues_gestionar
    end
  end
end
