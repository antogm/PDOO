package civitas;

public class TestP1 {
    public static void main(String[] argv) {
        
        // Test quienEmpieza()
        System.out.println("Test del método quienEmpieza():");
                
        int[] resultados = {0,0,0,0};
        
        for (int i = 0; i < 100; i++)
            resultados[ Dado.getInstance().quienEmpieza(4) ]++;
                        
        for (int i = 0; i < 4; i++)
            System.out.println("Jugador " + (i+1) + " empieza " + resultados[i] + " veces");
        
        
        // Test modo debug del dado
        System.out.println("\nTest del modo debug del dado:");
        Dado.getInstance().setDebug(true);
        for (int i = 0; i < 3; i++){
            int tirada = Dado.getInstance().tirar();
            System.out.println("Tirada con modo debug: " + tirada);
        };
        
        Dado.getInstance().setDebug (false);
        for (int i = 0; i < 3; i++){
            int tirada = Dado.getInstance().tirar();
            System.out.println("Tirada sin modo debug: " + tirada);
        };
        
        
        // Test métodos getUltimoResultado() y salgoDeLaCarcel()
        System.out.println("\nTest de los métodos getUltimoResultado() y salgoDeLaCarcel():");
        System.out.println("Último resultado: " + Dado.getInstance().getUltimoResultado());
        System.out.println("Sale de cárcel: " + Dado.getInstance().salgoDeLaCarcel());
        
        
        // Test tipos enumerados
        System.out.println("\nTest de los tipos enumerados:");
        TipoCasilla enum1 = TipoCasilla.CALLE;
        TipoSorpresa enum2 = TipoSorpresa.IRCARCEL;
        EstadosJuego enum3 = EstadosJuego.INICIO_TURNO;
        System.out.println(
                "Enumerado TipoCasilla: " + enum1 + "\n" +
                "Enumerado TipoSorpresa: " + enum2 + "\n" +
                "Enumerado EstadosJuego: " + enum3
        );
        
        
        // Test MazoSorpresas
        MazoSorpresas mazo = new MazoSorpresas();
        Sorpresa sorpresa1 = new Sorpresa();
        Sorpresa sorpresa2 = new Sorpresa();
        mazo.alMazo(sorpresa1);
        mazo.alMazo(sorpresa2);
        mazo.siguiente();
        mazo.inhabilitarCartaEspecial(sorpresa2);
        mazo.habilitarCartaEspecial(sorpresa2);
        
        // Test Diario
        Diario.getInstance().ocurreEvento("Test evento diario");
        Diario.getInstance().leerEvento();
        
        
        // Test tablero
        System.out.println("\nTest Tablero:");
        Tablero miTablero = new Tablero(5);
        miTablero.añadeJuez();
        for (int i = 0; i < 10; i++)
            miTablero.añadeCasilla(new Casilla("casilla_test"));
            
        System.out.println("Estoy en la casilla: " + miTablero.nuevaPosicion(0, Dado.getInstance().tirar() ) );
    }
}
