/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vista;

import controlador.Controlador_partido;
import controlador.Controlador_rol_arbitro;
import controlador.Controlador_sorteo;
import excepciones.SorteoException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import modelo.Arbitro;
import modelo.Partido;
import modelo.Sorteo;
import modelo.TipoBusqueda;
import modelo.Usuario;

/**
 *
 * @author kevin
 */
public class Inter_sorteo_arbitros extends javax.swing.JInternalFrame {

    Sorteo sorteo;
    Date fechaSorteo =  Date.valueOf(LocalDate.now());
    Integer id_partido = 0;
    Integer arbitroTitular = 0;
    Integer arbitroSustituto = 0;
    DefaultTableModel modelo;
    Controlador_sorteo controladorSorteo = new Controlador_sorteo();
    List<Usuario> arbitrosSorteo = new ArrayList<Usuario>();

    /**
     * Creates new form Inter_sorteo_arbitros
     */
    public Inter_sorteo_arbitros() {
        initComponents();
        setearTabla();
        cargarTablaSorteo();
        cargarComboPartido();
    }

    /**
     * 
     * Carga los partidos que no cuentan con sorteo 
     */
    private void cargarComboPartido() {
        partido_combo.removeAllItems();
        partido_combo.updateUI();
        partido_combo.addItem("<Seleccione>");
        for (Partido e : Controlador_partido.cargarComboPartido(TipoBusqueda.SORTEO)) {
            partido_combo.addItem(e.getClub_local().getNombre_equipo() + " VS " + e.getClub_rival().getNombre_equipo());
        }
    }

    private void limpiarText(){
        text_arbitro_principal.setText("");
        text_arbitro_sustituto.setText("");
        partido_combo.setSelectedIndex(0);
    }
    
    private void setearTabla() {

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                if (columnas == 6) {
                    return true;
                }
                return false;
            }
        };

        modelo.addColumn("#");
        modelo.addColumn("id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Nacionalidad");
        modelo.addColumn("Categoria");
        modelo.addColumn("Seleccione");
        table_sorteo = new JTable(modelo);
        jScrollPane1.setViewportView(table_sorteo);

        table_sorteo.getColumnModel().getColumn(0).setMaxWidth(20);
        table_sorteo.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_sorteo.removeColumn(table_sorteo.getColumnModel().getColumn(1));
    }

    private void cargarTablaSorteo() {
        int i = 0;
        modelo.getDataVector().removeAllElements();
        TableColumn col = table_sorteo.getColumnModel().getColumn(5);
        col.setCellEditor(table_sorteo.getDefaultEditor(Boolean.class));
        col.setCellRenderer(table_sorteo.getDefaultRenderer(Boolean.class));

        table_sorteo.updateUI();
        for (Arbitro e : Controlador_rol_arbitro.listarArbitros()) {
            i = i + 1;
            modelo.addRow(new Object[] { 
                i,
                e.getId_usuario(),
                e.getNombre(), 
                e.getApellido(),
                e.getNacionalidad(),
                e.getCategoria() });
        }
    }

    private boolean esFilaSeleccionada(int fila) {
        return table_sorteo.getValueAt(fila, 5) != null;
    }

    private void agregarArbitrosAlSorteo() {
        
        arbitrosSorteo.clear();
        Integer id = 0;
        String nombre = "";
        String apellido = "";

        for (int i = 0; i < table_sorteo.getRowCount(); i++) {
            if (esFilaSeleccionada(i)) {
                id = Integer.parseInt(table_sorteo.getModel().getValueAt(i, 1).toString());
                System.out.println("Id arbitro " + id);
                nombre = table_sorteo.getModel().getValueAt(i, 2).toString();
                apellido = table_sorteo.getModel().getValueAt(i, 3).toString();
                arbitrosSorteo.add(new Usuario(id, nombre, apellido));
            }
        }
    }

    private void sortearArbitros() {

        /* System.out.println("Tamaño lista: " + arbitrosSorteo.size()); */
        if (!(arbitrosSorteo.size() > 2)) {
            // JOptionPane.showMessageDialog(, closable);
            JOptionPane.showMessageDialog(null, "Seleccione 2 o mas Arbitros a sortear!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Seleccione 2 o mas Arbitros a sortear");
        } else {
            arbitroTitular = (int) (Math.random() * arbitrosSorteo.size());
            arbitroSustituto = (int) (Math.random() * arbitrosSorteo.size());
            System.out.println("arbitroTitular" + arbitroTitular + " arbitroSustituto "+ arbitroSustituto);
            if (arbitroSustituto == arbitroTitular) {
                sortearArbitros();
            }
            setearArbitrosText();
        }

    }

    private void setearArbitrosText() {
        String textArbitroTitular = arbitrosSorteo.get(arbitroTitular).getNombre() + " "
                + arbitrosSorteo.get(arbitroTitular).getApellido();
        String textArbitroSustituto = arbitrosSorteo.get(arbitroSustituto).getNombre() + " "
                + arbitrosSorteo.get(arbitroSustituto).getApellido();
        text_arbitro_principal.setText(textArbitroTitular);
        text_arbitro_sustituto.setText(textArbitroSustituto);
    }

    private void guardarSorteo() {
        String mensaje = "";
        if (!camposNoValidos()) {
            int confirmacion = JOptionPane.showConfirmDialog(null, "Desea guardar este sorteo?", "Advertencia",
                    JOptionPane.YES_NO_OPTION);
            if (confirmacion == 0) {
                try {

                    /* System.out.println("Try entrando " + arbitrosSorteo.size());
                    System.out.println("Indexes" + arbitroSustituto + " " + arbitroTitular);
                    System.out.println("Id arbitro" +
                            arbitrosSorteo.get(arbitroTitular).getId_usuario());
                    System.out.println("Id arbitro sustituto"
                            + arbitrosSorteo.get(arbitroSustituto).getId_usuario()); */

                    System.out.println((new Date(fechaSorteo.getTime())));
                    sorteo = new Sorteo();
                    sorteo.setFecha_sorteo(new Date(fechaSorteo.getTime()));
                    for (Partido p : Controlador_partido.cargarComboPartido(TipoBusqueda.SORTEO)) {
                        String partido = p.getClub_local().getNombre_equipo() + " VS "
                                + p.getClub_rival().getNombre_equipo();
                            System.out.println("partido" + partido);
                            System.out.println("partido combo" + (partido_combo.getSelectedItem().toString()));

                        if (partido_combo.getSelectedItem().toString().equalsIgnoreCase(partido)) {
                            id_partido = p.getId_partido();
                            System.out.println("Id partido" + id_partido);
                            break;
                        }
                    }
                    sorteo.setPartido_id(id_partido);
                    sorteo.setArbitro_id(arbitrosSorteo.get(arbitroTitular).getId_usuario());
                    sorteo.setArbitro_id_sustituto(arbitrosSorteo.get(arbitroSustituto).getId_usuario());
                    mensaje = controladorSorteo.guardarSorteo(sorteo);
                    limpiarText();
                    cargarComboPartido();
                    JOptionPane.showMessageDialog(null, mensaje, "Info", JOptionPane.INFORMATION_MESSAGE);
                } catch (SorteoException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, mensaje + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor seleccione un partido y genere un sorteo!", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean camposNoValidos() {
        return (text_arbitro_principal.getText().isEmpty()
                || text_arbitro_sustituto.getText().isEmpty() && partido_combo.getSelectedIndex() > 0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        text_arbitro_principal = new javax.swing.JTextField();
        text_arbitro_sustituto = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblPartido = new javax.swing.JLabel();
        partido_combo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        boton_sorteo_arbitro = new javax.swing.JButton();
        btnGuardarSorteo = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_sorteo = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Sorteo arbitros");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Arbitro principal:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        text_arbitro_principal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        text_arbitro_principal.setEnabled(false);
        getContentPane().add(text_arbitro_principal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, 170, -1));

        text_arbitro_sustituto.setBackground(new java.awt.Color(255, 255, 255));
        text_arbitro_sustituto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        text_arbitro_sustituto.setEnabled(false);
        text_arbitro_sustituto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_arbitro_sustitutoActionPerformed(evt);
            }
        });
        getContentPane().add(text_arbitro_sustituto, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 174, 170, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Sorteo");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        lblPartido.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblPartido.setForeground(new java.awt.Color(0, 0, 0));
        lblPartido.setText("Partido");
        getContentPane().add(lblPartido, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        partido_combo.setModel(new javax.swing.DefaultComboBoxModel<>());
        getContentPane().add(partido_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 170, -1));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Arbitro sustituto:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        boton_sorteo_arbitro.setBackground(new java.awt.Color(0, 204, 204));
        boton_sorteo_arbitro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        boton_sorteo_arbitro.setForeground(new java.awt.Color(0, 0, 0));
        boton_sorteo_arbitro.setText("Generar sorteo");
        boton_sorteo_arbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_sorteo_arbitroActionPerformed(evt);
            }
        });
        getContentPane().add(boton_sorteo_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 30, 150, -1));

        btnGuardarSorteo.setText("Guardar Sorteo");
        btnGuardarSorteo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarSorteoActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardarSorteo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, 120, 40));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Listado de arbitros a sortear");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, -1, -1));

        table_sorteo.setBackground(new java.awt.Color(255, 255, 255));
        table_sorteo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
            }
        ));
        jScrollPane1.setViewportView(table_sorteo);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, 640, 340));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo blanco.jpg"))); // NOI18N
        jLabel13.setText("jLabel5");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 300, 250));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo blanco.jpg"))); // NOI18N
        jLabel14.setText("jLabel5");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 660, 410));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancha 2.0.jpg"))); // NOI18N
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarSorteoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnGuardarSorteoActionPerformed
        guardarSorteo();
    }// GEN-LAST:event_btnGuardarSorteoActionPerformed

    private void boton_sorteo_arbitroActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_boton_sorteo_arbitroActionPerformed
        agregarArbitrosAlSorteo();
        sortearArbitros();
    }// GEN-LAST:event_boton_sorteo_arbitroActionPerformed

    private void text_arbitro_sustitutoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_text_arbitro_sustitutoActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_text_arbitro_sustitutoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_sorteo_arbitro;
    private javax.swing.JButton btnGuardarSorteo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPartido;
    private javax.swing.JComboBox<String> partido_combo;
    private javax.swing.JTable table_sorteo;
    private javax.swing.JTextField text_arbitro_principal;
    private javax.swing.JTextField text_arbitro_sustituto;
    // End of variables declaration//GEN-END:variables
}
