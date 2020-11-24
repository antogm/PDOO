module Civitas
  module GestionesInmobiliarias
    VENDER=:vender
    HIPOTECAR=:hipotecar
    CANCELAR_HIPOTECA=:cancelar_hipoteca
    CONSTRUIR_CASA=:construir_casa
    CONSTRUIR_HOTEL=:construir_hotel
    TERMINAR=:terminar
  end
  
  Lista_GestionesInmobiliarias = [
    GestionesInmobiliarias::VENDER,
    GestionesInmobiliarias::HIPOTECAR,
    GestionesInmobiliarias::CANCELAR_HIPOTECA,
    GestionesInmobiliarias::CONSTRUIR_CASA,
    GestionesInmobiliarias::CONSTRUIR_HOTEL,
    GestionesInmobiliarias::TERMINAR      
  ]
end