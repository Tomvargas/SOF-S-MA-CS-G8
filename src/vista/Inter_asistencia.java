/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vista;

import java.awt.HeadlessException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.Color;
import java.awt.Component;

import controlador.Controlador_asistencia;
import controlador.Controlador_sorteo;
import excepciones.UsuarioException;
import modelo.Asistencia;
import modelo.Sorteo;
import modelo.UsuarioCache;

/**
 *
 * @author Hidalgo
 */
public class Inter_asistencia extends javax.swing.JInternalFrame {

    private class FilaColoreada extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
                int row, int column) {

            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            setBackground(Color.DARK_GRAY);
            return this;
        }
    };

    FilaColoreada filaColoreada = new FilaColoreada();
    DefaultTableModel modeloAsistencia;
    DefaultTableModel modeloAsistida;

    /*
     * Controlador_sorteo controladorSorteo;
     * Controlador_asistencia controladorAsistencia;
     */
    List<Asistencia> listadoAsistenciaConfirmar = new ArrayList<Asistencia>();

    /**
     * Creates new form Inter_asistencia
     */
    public Inter_asistencia() {
        initComponents();
        this.setTitle("ÁRBITRO: ASISTENCIA");
        setearTablas();
        cargarListadoAsistenciaPartidos();
        cargarListadoAsistenciaGuardas();
    }

    private void setearTablas() {

        modeloAsistencia = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                if (columnas == 7) {
                    return true;
                }
                return false;
            }
        };

        modeloAsistida = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                return false;
            }
        };

        modeloAsistencia.addColumn("#");
        modeloAsistencia.addColumn("id");
        modeloAsistencia.addColumn("Partido");
        modeloAsistencia.addColumn("Lugar");
        modeloAsistencia.addColumn("Fecha");
        modeloAsistencia.addColumn("Hora");
        modeloAsistencia.addColumn("id_arbitro");
        modeloAsistencia.addColumn("Asistir");
        table_asistencia = new JTable(modeloAsistencia);
        jScrollPane1.setViewportView(table_asistencia);

        table_asistencia.getColumnModel().getColumn(0).setMaxWidth(20);
        table_asistencia.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_asistencia.removeColumn(table_asistencia.getColumnModel().getColumn(1));
        table_asistencia.removeColumn(table_asistencia.getColumnModel().getColumn(5));
        TableColumn col = table_asistencia.getColumnModel().getColumn(5);
        col.setCellEditor(table_asistencia.getDefaultEditor(Boolean.class));
        col.setCellRenderer(table_asistencia.getDefaultRenderer(Boolean.class));

        modeloAsistida.addColumn("#");
        modeloAsistida.addColumn("id");
        modeloAsistida.addColumn("Partido");
        modeloAsistida.addColumn("Lugar");
        modeloAsistida.addColumn("Fecha");
        modeloAsistida.addColumn("Confirmacion");

        table_asistencia_guardas = new JTable(modeloAsistida);
        jScrollPane2.setViewportView(table_asistencia_guardas);
        table_asistencia_guardas.setDefaultRenderer(table_asistencia_guardas.getColumnClass(5), filaColoreada);
        table_asistencia_guardas.getColumnModel().getColumn(0).setMaxWidth(20);
        table_asistencia_guardas.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_asistencia_guardas.removeColumn(table_asistencia_guardas.getColumnModel().getColumn(1));

    }

    private void guardarAsistencia() {
        if (table_asistencia.getRowCount() > 0) {

            int confirmacion = JOptionPane.showConfirmDialog(null, "Esta seguro de guardar sus asistencias?",
                    "Advertencia",
                    JOptionPane.YES_NO_OPTION);
            if (confirmacion == 0) {
                for (int i = 0; i < table_asistencia.getRowCount(); i++) {
                    Asistencia tmp = new Asistencia();
                    tmp.setPartido(table_asistencia.getModel().getValueAt(i, 2).toString());
                    tmp.setLugar(table_asistencia.getModel().getValueAt(i, 3).toString());
                    tmp.setFecha(Date.valueOf(table_asistencia.getModel().getValueAt(i, 4).toString()));
                    tmp.setId_arbitro(Integer.parseInt(table_asistencia.getModel().getValueAt(i, 6).toString()));
                    if (table_asistencia.getModel().getValueAt(i, 7) != null) {
                        tmp.setAsistio(true);
                    }
                    System.out.printf("Asistencia datos: %s, %s, %b, %d", tmp.getPartido(), tmp.getLugar(),
                            tmp.getAsistio(), tmp.getId_arbitro());
                    // listadoAsistenciaConfirmar.add(tmp);
                    // cargarListadoAsistenciaGuardas();
                }
            }

        }

    }

    private void cargarListadoAsistenciaPartidos() {
        int i = 0;
        modeloAsistencia.getDataVector().removeAllElements();
        table_asistencia.updateUI();
        try {
            if (Controlador_sorteo.listarAsistenciasPartidos(UsuarioCache.getUsuarioCache().getId_usuario())
                    .size() > 0) {
                for (Sorteo s : Controlador_sorteo
                        .listarAsistenciasPartidos(UsuarioCache.getUsuarioCache().getId_usuario())) {
                    i = i + 1;
                    modeloAsistencia.addRow(new Object[] {
                            i,
                            s.getId_sorteo(),
                            s.getAgenda().getPartido_id().getPartido_descripcion(),
                            s.getAgenda().getLugar_partido(),
                            s.getAgenda().getFecha_partido(),
                            s.getAgenda().getHora_partido(),
                            s.getArbitro_id()
                    });
                }
            } else {
                JOptionPane.showMessageDialog(null, "No hay fechas asignadas!", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (HeadlessException | UsuarioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarListadoAsistenciaGuardas() {
        int i = 0;
        modeloAsistida.getDataVector().removeAllElements();
        table_asistencia_guardas.updateUI();
        try {
            if (Controlador_asistencia.listarAsistencias(UsuarioCache.getUsuarioCache().getId_usuario()).size() > 0) {
                for (Asistencia a : Controlador_asistencia
                        .listarAsistencias(UsuarioCache.getUsuarioCache().getId_usuario())) {
                    i = i + 1;
                    modeloAsistida.addRow(new Object[] {
                            i,
                            a.getId_asistencia(),
                            a.getPartido(),
                            a.getLugar(),
                            a.getFecha(),
                            a.getAsistio() ? "Confirmado" : "No Asistire"
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_asistencia = new javax.swing.JTable();
        btnGuadarAsistencia = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_asistencia_guardas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setResizable(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Módulo de asistencias");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        table_asistencia.setModel(new javax.swing.table.DefaultTableModel(

        ) {
            Class[] types = new Class[] {
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        jScrollPane1.setViewportView(table_asistencia);

        btnGuadarAsistencia.setBackground(new java.awt.Color(0, 204, 204));
        btnGuadarAsistencia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuadarAsistencia.setText("Guardar");
        btnGuadarAsistencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuadarAsistenciaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(257, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 211,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(130, 130, 130)
                                .addComponent(btnGuadarAsistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 98,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addContainerGap()));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnGuadarAsistencia))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(19, Short.MAX_VALUE)));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 710, 220));

        table_asistencia_guardas.setModel(new javax.swing.table.DefaultTableModel(

        ));
        jScrollPane2.setViewportView(table_asistencia_guardas);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 710, 175));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancha 4.0.jpg"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 440));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuadarAsistenciaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnGuadarAsistenciaActionPerformed
        guardarAsistencia();
    }// GEN-LAST:event_btnGuadarAsistenciaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuadarAsistencia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table_asistencia;
    private javax.swing.JTable table_asistencia_guardas;
    // End of variables declaration//GEN-END:variables
}
