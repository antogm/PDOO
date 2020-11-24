require_relative 'gestiones_inmobiliarias'

module Civitas
  class OperacionInmobiliaria
    def initialize(gest, ip)
      @gestion = gest
      @numPropiedad = ip
    end
    
    attr_reader :gestion, :numPropiedad
  end
end
