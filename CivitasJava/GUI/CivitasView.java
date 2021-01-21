package GUI;
import civitas.CivitasJuego;
import civitas.OperacionesJuego;
import civitas.Respuestas;
import civitas.SalidasCarcel;
import javax.swing.JOptionPane;

public class CivitasView extends javax.swing.JFrame {

    CivitasJuego juego;
    JugadorPanel jugadorPanel;
    GestionarDialog gestionarD;
   
    public CivitasView() {
        initComponents();
        jugadorPanel = new JugadorPanel();
        gestionarD = new GestionarDialog(this);
        contenedorVistaJugador.add(jugadorPanel);
        repaint();
        revalidate();
    }
    
    void setCivitasJuego(CivitasJuego j){
        juego = j;
        setVisible(true);
    }

    void actualizarVista(){
        label_ranking.setVisible(false);
        area_ranking.setVisible(false);
        jugadorPanel.setJugador(juego.getJugadorActual());
        field_casilla.setText(juego.getCasillaActual().toString());
        
        if(juego.finalDelJuego()){        
            area_ranking.setText(juego.ranking().toString());
            label_ranking.setVisible(true);
            area_ranking.setVisible(true);
            label_ranking.repaint();
            area_ranking.repaint();
            label_ranking.revalidate();
            area_ranking.revalidate();
        }
    }
    
    void mostrarEventos(){
        DiarioDialog diarioD = new DiarioDialog(this);
        diarioD.repaint();
        diarioD.revalidate();
    }
    
    void mostrarSiguienteOperacion(OperacionesJuego op){
        field_siguienteoperacion.setText(op.toString());
        actualizarVista();
    }
    
    Respuestas comprar(){
        int opcion = JOptionPane.showConfirmDialog(null, "¿Quieres comprar la calle actual?", "Compra", JOptionPane.YES_NO_OPTION);
        return Respuestas.values()[opcion];
    }
    
    int getGestionElegida(){
       return gestionarD.getGestion();
    }
    
    int getPropiedadElegida(){
        return gestionarD.getPropiedad();
    }
    
    void gestionar(){
        gestionarD.gestionar(juego.getJugadorActual());
        gestionarD.pack();
        gestionarD.repaint();
        gestionarD.revalidate();
        gestionarD.setVisible(true);
    }
    
    SalidasCarcel salirCarcel(){
        String[] opciones = {"Pagando", "Tirando"};
        int respuesta = JOptionPane.showOptionDialog(null, "¿Cómo quieres salir de la cárcel?", "Salir de la cárcel",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        
        return SalidasCarcel.values()[respuesta];
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        contenedorVistaJugador = new javax.swing.JPanel();
        field_casilla = new javax.swing.JTextField();
        label_casilla = new javax.swing.JLabel();
        label_siguienteoperacion = new javax.swing.JLabel();
        field_siguienteoperacion = new javax.swing.JTextField();
        label_ranking = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        area_ranking = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Civitas, El juego");
        jLabel1.setEnabled(false);

        field_casilla.setText("jTextField1");
        field_casilla.setEnabled(false);

        label_casilla.setText("Casilla:");
        label_casilla.setEnabled(false);

        label_siguienteoperacion.setText("Siguiente operación:");
        label_siguienteoperacion.setEnabled(false);

        field_siguienteoperacion.setText("jTextField1");
        field_siguienteoperacion.setEnabled(false);

        label_ranking.setText("Ranking:");
        label_ranking.setEnabled(false);

        area_ranking.setColumns(20);
        area_ranking.setRows(5);
        area_ranking.setEnabled(false);
        jScrollPane1.setViewportView(area_ranking);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(contenedorVistaJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(label_siguienteoperacion)
                                .addGap(18, 18, 18)
                                .addComponent(field_siguienteoperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(label_ranking)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label_casilla)
                                .addGap(18, 18, 18)
                                .addComponent(field_casilla, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contenedorVistaJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 322, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_casilla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_casilla))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_siguienteoperacion)
                    .addComponent(field_siguienteoperacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_ranking)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CivitasView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea area_ranking;
    private javax.swing.JPanel contenedorVistaJugador;
    private javax.swing.JTextField field_casilla;
    private javax.swing.JTextField field_siguienteoperacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_casilla;
    private javax.swing.JLabel label_ranking;
    private javax.swing.JLabel label_siguienteoperacion;
    // End of variables declaration//GEN-END:variables
}
