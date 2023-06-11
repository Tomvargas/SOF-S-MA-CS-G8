/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vista;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controlador.Controlador_asistencia;
import controlador.Controlador_rol_arbitro;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import modelo.Arbitro;
import modelo.Asistencia;
import modelo.TipoBusquedaCombo;

/**
 *
 * @author kevin
 */
public class Inter_consulta_asistencia_arbitros extends javax.swing.JInternalFrame {

    DefaultTableModel modeloTablaArbitros;
    DefaultTableModel modeloTablaAsistencias;
    Integer id_arbitro = 0;

    /**
     * Creates new form Inter_consulta_asistencia_arbitros
     */
    public Inter_consulta_asistencia_arbitros() {
        initComponents();
        setearTabla();
        cargarComboFiltro();
        cargarArbitros();
    }

    /**
     *
     */
    private void setearTabla() {

        modeloTablaAsistencias = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                return false;
            }
        };

        modeloTablaArbitros = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                return false;
            }
        };

        modeloTablaArbitros.addColumn("#");
        modeloTablaArbitros.addColumn("id");
        modeloTablaArbitros.addColumn("Nombre");
        modeloTablaArbitros.addColumn("Apellido");
        modeloTablaArbitros.addColumn("Nacionalidad");
        modeloTablaArbitros.addColumn("Categoria");
        table_arbitros = new JTable(modeloTablaArbitros);
        jScrollPane1.setViewportView(table_arbitros);
        table_arbitros.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                table_arbitrosMousePressed(evt);
            }
        });
        table_arbitros.getColumnModel().getColumn(0).setMaxWidth(20);
        table_arbitros.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_arbitros.removeColumn(table_arbitros.getColumnModel().getColumn(1));

        modeloTablaAsistencias.addColumn("#");
        modeloTablaAsistencias.addColumn("id_asistencia");
        modeloTablaAsistencias.addColumn("Partido");
        modeloTablaAsistencias.addColumn("Lugar");
        modeloTablaAsistencias.addColumn("Fecha");
        modeloTablaAsistencias.addColumn("Asistencia");
        table_arbitro_asistencia = new JTable(modeloTablaAsistencias);
        jScrollPane2.setViewportView(table_arbitro_asistencia);
        table_arbitro_asistencia.getColumnModel().getColumn(0).setMaxWidth(20);
        table_arbitro_asistencia.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_arbitro_asistencia.removeColumn(table_arbitro_asistencia.getColumnModel().getColumn(1));
    }

    /**
     *
     */
    private void cargarArbitros() {
        int i = 0;
        /*
         * modeloTablaArbitros.getDataVector().removeAllElements();
         * table_arbitros.updateUI();
         */
        for (Arbitro e : Controlador_rol_arbitro.listarArbitros()) {
            i = i + 1;
            modeloTablaArbitros.addRow(new Object[]{
                i,
                e.getId_usuario(),
                e.getNombre(),
                e.getApellido(),
                e.getNacionalidad(),
                e.getCategoria()
            });
        }
    }

    /**
     *
     */
    private void cargarAsistencias() {

        if (id_arbitro == 0) {
            return;
        }

        int i = 0;
        modeloTablaAsistencias.getDataVector().removeAllElements();
        table_arbitro_asistencia.updateUI();
        for (Asistencia a : Controlador_asistencia.listarAsistencias(id_arbitro)) {
            i = i + 1;
            modeloTablaAsistencias.addRow(new Object[]{
                i,
                a.getId_asistencia(),
                a.getPartido(),
                a.getLugar(),
                a.getFecha(),
                a.getAsistio() ? "Asistio" : "No Asistio",});
        }
    }

    /**
     *
     * @return void
     */
    public void cargarComboFiltro() {
        combo_box_buscar.addItem(TipoBusquedaCombo.NOMBRE);
        combo_box_buscar.addItem(TipoBusquedaCombo.APELLIDO);
        combo_box_buscar.addItem(TipoBusquedaCombo.NACIONALIDAD);
    }

    /**
     * Filtra datos especificos de la tabla fecha de calendario en base al tipo
     * de busqueda que se hace.
     *
     * @param buscarPor
     * @param texto
     * @return void
     */
    public void flitrarTabla(String buscarPor, String texto) {
        List<Arbitro> listaFiltrada = new ArrayList<Arbitro>();

        /*
         * System.out.println("Filtro" + "\n");
         * System.out.println(buscarPor + "\n");
         * System.out.println(texto + "\n");
         */
        switch (buscarPor) {
            case TipoBusquedaCombo.NOMBRE:
                Controlador_rol_arbitro.listarArbitros().forEach(e -> {
                    if (e.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                        listaFiltrada.add(e);
                    }
                });
                break;
            case TipoBusquedaCombo.APELLIDO:
                Controlador_rol_arbitro.listarArbitros().forEach((e) -> {
                    if (e.getApellido().toLowerCase().contains(texto.toLowerCase())) {
                        listaFiltrada.add(e);
                    }
                });
                break;
            case TipoBusquedaCombo.NACIONALIDAD:
                Controlador_rol_arbitro.listarArbitros().forEach((e) -> {
                    if (e.getNacionalidad().toLowerCase().contains(texto.toLowerCase())) {
                        listaFiltrada.add(e);
                    }
                });
                break;
            default:
                break;
        }

        int i = 0;
        modeloTablaArbitros.getDataVector().removeAllElements();
        table_arbitros.updateUI();
        for (Arbitro e : listaFiltrada) {
            i = i + 1;
            modeloTablaArbitros.addRow(new Object[]{
                i,
                e.getId_usuario(),
                e.getNombre(),
                e.getApellido(),
                e.getNacionalidad(),
                e.getCategoria()
            });
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
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelArbitros = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_arbitros = new javax.swing.JTable();
        panelAsistencias = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_arbitro_asistencia = new javax.swing.JTable();
        combo_box_buscar = new javax.swing.JComboBox<>();
        lbl_advice = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        text_field_buscar_arbitro = new javax.swing.JTextField();
        boton_buscar_arbitro = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();

        setClosable(true);
        setResizable(true);
        setTitle("Asistencia arbitros");

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelArbitros.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Arbitros"));

        table_arbitros.setBackground(new java.awt.Color(255, 255, 255));
        table_arbitros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
            }
        ));
        table_arbitros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                table_arbitrosMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(table_arbitros);

        javax.swing.GroupLayout panelArbitrosLayout = new javax.swing.GroupLayout(panelArbitros);
        panelArbitros.setLayout(panelArbitrosLayout);
        panelArbitrosLayout.setHorizontalGroup(
            panelArbitrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelArbitrosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelArbitrosLayout.setVerticalGroup(
            panelArbitrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelArbitrosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel1.add(panelArbitros, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 770, 180));

        panelAsistencias.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabla de  Asistencia"));

        table_arbitro_asistencia.setModel(new javax.swing.table.DefaultTableModel(

        ));
        jScrollPane2.setViewportView(table_arbitro_asistencia);

        javax.swing.GroupLayout panelAsistenciasLayout = new javax.swing.GroupLayout(panelAsistencias);
        panelAsistencias.setLayout(panelAsistenciasLayout);
        panelAsistenciasLayout.setHorizontalGroup(
            panelAsistenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 760, Short.MAX_VALUE)
            .addGroup(panelAsistenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelAsistenciasLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panelAsistenciasLayout.setVerticalGroup(
            panelAsistenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 187, Short.MAX_VALUE)
            .addGroup(panelAsistenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelAsistenciasLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel1.add(panelAsistencias, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 770, 210));

        combo_box_buscar.setModel(new javax.swing.DefaultComboBoxModel<>());
        jPanel1.add(combo_box_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 170, -1));

        lbl_advice.setBackground(new java.awt.Color(0, 0, 0));
        lbl_advice.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_advice.setForeground(new java.awt.Color(255, 255, 102));
        lbl_advice.setText("Seleccione un arbitro para visualizar su historial de asistencias *");
        jPanel1.add(lbl_advice, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 460, -1));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Filtrar Por:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        text_field_buscar_arbitro.setBackground(new java.awt.Color(255, 255, 255));
        text_field_buscar_arbitro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_field_buscar_arbitroKeyPressed(evt);
            }
        });
        jPanel1.add(text_field_buscar_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 170, -1));

        boton_buscar_arbitro.setBackground(new java.awt.Color(0, 204, 204));
        boton_buscar_arbitro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        boton_buscar_arbitro.setForeground(new java.awt.Color(0, 0, 0));
        boton_buscar_arbitro.setText("Buscar");
        boton_buscar_arbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_buscar_arbitroActionPerformed(evt);
            }
        });
        jPanel1.add(boton_buscar_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 90, 30));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancha 2.0.jpg"))); // NOI18N
        jLabel15.setOpaque(true);
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 820, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 804, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void text_field_buscar_arbitroKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_text_field_buscar_arbitroKeyPressed
        if (evt.getKeyCode() == 10) {
            var buscarPor = combo_box_buscar.getSelectedItem().toString();
            var texto = text_field_buscar_arbitro.getText();
            flitrarTabla(buscarPor, texto);
        }
    }// GEN-LAST:event_text_field_buscar_arbitroKeyPressed

    private void table_arbitrosMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_table_arbitrosMousePressed
        if (table_arbitros.getRowCount() > -1) {
            int fila = table_arbitros.getSelectedRow();
            id_arbitro = Integer.parseInt(table_arbitros.getModel().getValueAt(fila, 1).toString());
            System.out.print("Id arbitro: " + id_arbitro);
            cargarAsistencias();
        }
    }// GEN-LAST:event_table_arbitrosMousePressed

    private void boton_buscar_arbitroActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_boton_buscar_arbitroActionPerformed
        if (!text_field_buscar_arbitro.getText().isEmpty()) {
            var buscarPor = combo_box_buscar.getSelectedItem().toString();
            var texto = text_field_buscar_arbitro.getText();
            flitrarTabla(buscarPor, texto);
        } else {
            modeloTablaArbitros.getDataVector().removeAllElements();
            table_arbitros.updateUI();
            cargarArbitros();
        }
    }// GEN-LAST:event_boton_buscar_arbitroActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_buscar_arbitro;
    private javax.swing.JComboBox<String> combo_box_buscar;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_advice;
    private javax.swing.JPanel panelArbitros;
    private javax.swing.JPanel panelAsistencias;
    private javax.swing.JTable table_arbitro_asistencia;
    private javax.swing.JTable table_arbitros;
    private javax.swing.JTextField text_field_buscar_arbitro;
    // End of variables declaration//GEN-END:variables
}
