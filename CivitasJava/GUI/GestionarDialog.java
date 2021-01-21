package GUI;

import civitas.Jugador;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultListModel;

public class GestionarDialog extends javax.swing.JDialog {

    private int gestionElegida = -1;
    private int propiedadElegida = -1;
    
    public GestionarDialog(java.awt.Frame parent) {
        super(parent, true);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        realizar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaGestiones = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaPropiedades = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("GESTIONAR");
        jLabel1.setEnabled(false);

        jLabel2.setText("Gestiones");
        jLabel2.setEnabled(false);

        jLabel3.setText("Propiedades");
        jLabel3.setEnabled(false);

        realizar.setText("Realizar");
        realizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                realizarActionPerformed(evt);
            }
        });

        listaGestiones.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listaGestiones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaGestionesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listaGestiones);

        listaPropiedades.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listaPropiedades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaPropiedadesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listaPropiedades);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(realizar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(69, 69, 69))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(135, 135, 135))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(realizar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listaGestionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaGestionesMouseClicked
        gestionElegida = listaGestiones.getSelectedIndex();
    }//GEN-LAST:event_listaGestionesMouseClicked

    private void realizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_realizarActionPerformed
        propiedadElegida = listaPropiedades.getSelectedIndex();
        gestionElegida = listaGestiones.getSelectedIndex();
        this.dispose();
    }//GEN-LAST:event_realizarActionPerformed

    private void listaPropiedadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaPropiedadesMouseClicked
        propiedadElegida = listaPropiedades.getSelectedIndex();
    }//GEN-LAST:event_listaPropiedadesMouseClicked

    public int getGestion(){
        return gestionElegida;
    }
    
    public int getPropiedad(){
        return propiedadElegida;
    }
    
    public void gestionar(Jugador jugador){
        setGestiones();
        setPropiedades(jugador);
        repaint();
    }
    
    public void setGestiones(){
        DefaultListModel gestiones = new DefaultListModel<>();
        ArrayList<String> opciones = new ArrayList<>(Arrays.asList(
            "Vender","Hipotecar","Cancelar hipoteca","Construir casa","Construir hotel","Terminar"
        ));
        
        for(String s: opciones){
            gestiones.addElement(s);
        }
        
        listaGestiones.setModel(gestiones);
    }
    
    public void setPropiedades(Jugador jugador){
        DefaultListModel propiedades = new DefaultListModel<>();
        
        for (int i = 0; i < jugador.getPropiedades().size(); i++){
            String temp = jugador.getPropiedades().get(i).getNombre();
            propiedades.addElement(temp);
        }
        
        listaPropiedades.setModel(propiedades);
    }
    
    /*
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GestionarDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionarDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionarDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionarDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GestionarDialog dialog = new GestionarDialog(new javax.swing.JFrame());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> listaGestiones;
    private javax.swing.JList<String> listaPropiedades;
    private javax.swing.JButton realizar;
    // End of variables declaration//GEN-END:variables
}
