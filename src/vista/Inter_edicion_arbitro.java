/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vista;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controlador.Controlador_rol_arbitro;
import modelo.Arbitro;
import modelo.TipoBusquedaCombo;

/**
 *
 * @author kevin
 */
public class Inter_edicion_arbitro extends javax.swing.JInternalFrame {

    Controlador_rol_arbitro controlador_arbitro = new Controlador_rol_arbitro();
    DefaultTableModel modelo;
    Integer id = 0;

    public Inter_edicion_arbitro() {

        initComponents();

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                return false;
            }

        };

        modelo.addColumn("#");
        modelo.addColumn("id");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Usuario");
        modelo.addColumn("Correo/Email");
        modelo.addColumn("Contraseña");
        modelo.addColumn("Edad");
        modelo.addColumn("Total partidos");
        modelo.addColumn("Categoria");
        table_arbitro = new JTable(modelo);
        // table_arbitro.setModel(modelo);
        jScrollPane1.setViewportView(table_arbitro);

        table_arbitro.getColumnModel().getColumn(0).setMaxWidth(20);
        table_arbitro.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_arbitro.removeColumn(table_arbitro.getColumnModel().getColumn(1));
        cargarComboCategoria();
        cargarComboBusqueda();
        cargarListadoArbitros();
    }

    /**
     * Ingreso de un nuevo arbitro a la base de datos
     *
     * @param void
     * @return void
     */
    public void guardarArbitro() {

        String mensaje = "";
        if (text_field_nombre_arbitro.getText().isEmpty() || text_field_contrasena_arbitro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor ingrese llene los datos", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {

            Arbitro nuevoArbitro = new Arbitro();
            nuevoArbitro.setNombre(text_field_nombre_arbitro.getText().trim());
            nuevoArbitro.setNombre_usuario(text_field_usuario_arbitro.getText().trim());
            nuevoArbitro.setApellido(text_field_apellido_arbitro.getText().trim());
            nuevoArbitro.setEmail(text_field_email_arbitro.getText().trim());
            nuevoArbitro.setContrasenia(text_field_contrasena_arbitro.getText().trim());
            nuevoArbitro.setEdad(text_field_edad_arbitro.getText().trim());
            nuevoArbitro.setCantidad_partidos(text_field_partidos_arbitro.getText().trim());
            nuevoArbitro.setCategoria(combo_box_categoria_arbitro.getSelectedItem().toString());
            if (id == 0) {
                mensaje = controlador_arbitro.guardarArbitro(nuevoArbitro);
                limpiarTexts();
            } else {
                nuevoArbitro.setId_usuario(id);
                mensaje = controlador_arbitro.actualizarArbitro(nuevoArbitro);
                id = 0;
                limpiarTexts();
            }

            JOptionPane.showMessageDialog(null, mensaje, "Info", JOptionPane.INFORMATION_MESSAGE);
            cargarListadoArbitros();
        }

    }

    /**
     * Limpia las cajas de texto una vez ingresado un nuevo arbitro.
     *
     * @param void
     * @return void
     */
    public void limpiarTexts() {
        id = 0;
        text_field_nombre_arbitro.setText("");
        text_field_apellido_arbitro.setText("");
        text_field_email_arbitro.setText("");
        text_field_contrasena_arbitro.setText("");
        text_field_edad_arbitro.setText("");
        text_field_usuario_arbitro.setText("");
        // text_field_dorsal_arbitro.setText("");
        text_field_partidos_arbitro.setText("");
        combo_box_categoria_arbitro.setSelectedIndex(0);
    }

    /**
     * Edicion de algun registro en especifico de un arbitro que ya ha sido
     * ingresado en base de datos.
     *
     * @param void
     * @return void
     */
    public void editarArbitro() {

        if (table_arbitro.getSelectedRow() > -1) {
            int fila = table_arbitro.getSelectedRow();
            id = (int) (table_arbitro.getModel().getValueAt(fila, 1));
            System.out.println(id);
            text_field_nombre_arbitro.setText(table_arbitro.getModel().getValueAt(fila, 2).toString());
            text_field_apellido_arbitro.setText(table_arbitro.getModel().getValueAt(fila, 3).toString());
            text_field_usuario_arbitro.setText(table_arbitro.getModel().getValueAt(fila, 4).toString());
            text_field_email_arbitro.setText(table_arbitro.getModel().getValueAt(fila, 5).toString());
            text_field_contrasena_arbitro.setText(table_arbitro.getModel().getValueAt(fila, 6).toString());
            text_field_edad_arbitro.setText(table_arbitro.getModel().getValueAt(fila, 7).toString());
            text_field_partidos_arbitro.setText(table_arbitro.getModel().getValueAt(fila, 8).toString());
            // text_field_dorsal_arbitro.setText(table_arbitro.getModel().getValueAt(fila,
            // 7).toString());

            for (int i = 0; i <= combo_box_categoria_arbitro.getItemCount(); i++) {
                String item = combo_box_categoria_arbitro.getItemAt(i);
                if (table_arbitro.getModel().getValueAt(fila, 9).toString().equals(item)) {
                    combo_box_categoria_arbitro.setSelectedIndex(i);
                    break;
                }
            }
        }

    }

    /**
     * Borrado logico de un arbitro ingresado en la base de datos Lo pasa de
     * activo a inactivo.
     *
     * @param void
     * @return void
     */
    public void eliminarArbitro() {

        if (table_arbitro.getSelectedRow() >= 0) {

            int row = table_arbitro.getSelectedRow();
            int id = Integer.parseInt(table_arbitro.getModel().getValueAt(row, 1).toString());

            int confirmacion = JOptionPane.showConfirmDialog(null, "Desea eliminar este registro?", "Advertencia",
                    JOptionPane.YES_NO_OPTION);
            if (confirmacion == 0) {
                try {
                    String mensaje = controlador_arbitro.eliminarArbitro(id);
                    JOptionPane.showMessageDialog(null, mensaje, "Info", JOptionPane.INFORMATION_MESSAGE);
                    cargarListadoArbitros();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {

            }
        }
    }

    /**
     * Carga todo el listado de arbitros de la base de datos y lo muestra en la
     * ventana actual.
     *
     * @param void
     * @return void
     */
    public void cargarListadoArbitros() {

        int i = 0;
        modelo.getDataVector().removeAllElements();
        table_arbitro.updateUI();
        for (Arbitro e : controlador_arbitro.listarArbitros()) {
            i = i + 1;
            modelo.addRow(new Object[]{i,
                e.getId_usuario(), e.getNombre(), e.getApellido(),
                e.getNombre_usuario(), e.getEmail(), e.getContrasenia(),
                e.getEdad(), e.getCantidad_partidos(), e.getCategoria()});
        }
    }

    /**
     * Carga 3 titulos de columna de la tabla arbitros especificada en este
     * metodo dentro de un comboBox.
     *
     * @param void
     * @return void
     */
    public void cargarComboBusqueda() {
        combo_box_buscar_arbitro.addItem(TipoBusquedaCombo.NOMBRE);
        combo_box_buscar_arbitro.addItem(TipoBusquedaCombo.USUARIO);
        combo_box_buscar_arbitro.addItem(TipoBusquedaCombo.CATEGORIA);
    }

    /**
     * Carga 4 titulos de columna de la tabla arbitros especificada en este
     * metodo dentro de un comboBox.
     *
     * @param void
     * @return void
     */
    public void cargarComboCategoria() {
        combo_box_categoria_arbitro.addItem("<Selecione>");
        combo_box_categoria_arbitro.addItem("Profesional");
        combo_box_categoria_arbitro.addItem("Junior");
        combo_box_categoria_arbitro.addItem("Infantil");
    }

    /**
     * Filtra datos especificos de la tabla arbitros en base al tipo de busqueda
     * que se hace.
     *
     * @param buscarPor, texto
     * @return void
     */
    public void filtrarTabla(String buscarPor, String texto) {
        List<Arbitro> listaFiltrada = new ArrayList<Arbitro>();

//        System.out.println("Filtro" + "\n");
//        System.out.println(buscarPor + "\n");
//        System.out.println(texto + "\n");
        switch (buscarPor) {
            case TipoBusquedaCombo.NOMBRE:
                for (Arbitro e : controlador_arbitro.listarArbitros()) {
                    if (e.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                        System.out.println(e.getNombre() + "\n");
                        listaFiltrada.add(e);
                    }
                }
                break;
            case TipoBusquedaCombo.APELLIDO:
                controlador_arbitro.listarArbitros().forEach((e) -> {
                    if (e.getApellido().toLowerCase().contains(texto.toLowerCase())) {
                        listaFiltrada.add(e);
                    }
                });
                break;
            case TipoBusquedaCombo.USUARIO:
                controlador_arbitro.listarArbitros().forEach((e) -> {
                    if (e.getNombre_usuario().toLowerCase().contains(texto.toLowerCase())) {
                        listaFiltrada.add(e);
                    }
                });
                break;
            case TipoBusquedaCombo.CATEGORIA:
                controlador_arbitro.listarArbitros().forEach((e) -> {
                    if (e.getCategoria().toLowerCase().contains(texto.toLowerCase())) {
                        listaFiltrada.add(e);
                    }
                });
                break;

            default:
                throw new AssertionError();
        }

        int i = 0;
        modelo.getDataVector().removeAllElements();
        table_arbitro.updateUI();
        for (Arbitro e : listaFiltrada) {
            i = i + 1;
            modelo.addRow(new Object[]{i,
                e.getId_usuario(), e.getNombre(), e.getApellido(),
                e.getNombre_usuario(), e.getEmail(), e.getContrasenia(),
                e.getEdad(), e.getCantidad_partidos(), e.getCategoria()});
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        boton_limpiar_arbitro = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        text_field_usuario_arbitro = new javax.swing.JTextField();
        text_field_apellido_arbitro = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        text_field_edad_arbitro = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        text_field_nombre_arbitro = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        text_field_email_arbitro = new javax.swing.JTextField();
        combo_box_categoria_arbitro = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        text_field_contrasena_arbitro = new javax.swing.JTextField();
        text_field_partidos_arbitro = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        boton_guardar_arbitro = new javax.swing.JButton();
        text_field_dorsal_arbitro = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        text_field_buscar_arbitro = new javax.swing.JTextField();
        boton_buscar_arbitro = new javax.swing.JButton();
        boton_editar_arbitro = new javax.swing.JButton();
        boton_borrar_arbitro = new javax.swing.JButton();
        combo_box_buscar_arbitro = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_arbitro = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Ingreso arbitro");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        boton_limpiar_arbitro.setBackground(new java.awt.Color(0, 204, 0));
        boton_limpiar_arbitro.setForeground(new java.awt.Color(0, 0, 0));
        boton_limpiar_arbitro.setText("Limpiar");
        boton_limpiar_arbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_limpiar_arbitroActionPerformed(evt);
            }
        });
        jPanel1.add(boton_limpiar_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 440, 97, -1));

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Partidos:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, -1, -1));

        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Apellidos:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        text_field_usuario_arbitro.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(text_field_usuario_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 194, -1));

        text_field_apellido_arbitro.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(text_field_apellido_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 192, -1));

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Dorsal:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

        text_field_edad_arbitro.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(text_field_edad_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 194, -1));

        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Usuario:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Correo electronico:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        text_field_nombre_arbitro.setBackground(new java.awt.Color(255, 255, 255));
        text_field_nombre_arbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_field_nombre_arbitroActionPerformed(evt);
            }
        });
        jPanel1.add(text_field_nombre_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 192, -1));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Contraseña:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        text_field_email_arbitro.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(text_field_email_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 194, -1));

        combo_box_categoria_arbitro.setBackground(new java.awt.Color(255, 255, 255));
        combo_box_categoria_arbitro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {  }));
        jPanel1.add(combo_box_categoria_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 390, 194, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Ingreso");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, -1, -1));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Edad:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));

        text_field_contrasena_arbitro.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(text_field_contrasena_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 194, -1));

        text_field_partidos_arbitro.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(text_field_partidos_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 194, -1));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nombre completo:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Categoria:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

        boton_guardar_arbitro.setBackground(new java.awt.Color(0, 204, 204));
        boton_guardar_arbitro.setForeground(new java.awt.Color(0, 0, 0));
        boton_guardar_arbitro.setText("Guardar");
        boton_guardar_arbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_guardar_arbitroActionPerformed(evt);
            }
        });
        jPanel1.add(boton_guardar_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 440, 92, -1));

        text_field_dorsal_arbitro.setBackground(new java.awt.Color(255, 255, 255));
        text_field_dorsal_arbitro.setEnabled(false);
        jPanel1.add(text_field_dorsal_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 194, -1));

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("*Seleccione un registro para efectuar alguna accion de edicion o borrado* ");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, -1, -1));

        jLabel11.setText("Buscar por:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 80, -1, -1));

        text_field_buscar_arbitro.setBackground(new java.awt.Color(255, 255, 255));
        text_field_buscar_arbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_field_buscar_arbitroActionPerformed(evt);
            }
        });
        text_field_buscar_arbitro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_field_buscar_arbitroKeyPressed(evt);
            }
        });
        jPanel1.add(text_field_buscar_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 80, 157, -1));

        boton_buscar_arbitro.setBackground(new java.awt.Color(0, 204, 204));
        boton_buscar_arbitro.setForeground(new java.awt.Color(0, 0, 0));
        boton_buscar_arbitro.setText("Buscar");
        boton_buscar_arbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_buscar_arbitroActionPerformed(evt);
            }
        });
        jPanel1.add(boton_buscar_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 80, -1, -1));

        boton_editar_arbitro.setBackground(new java.awt.Color(255, 255, 0));
        boton_editar_arbitro.setForeground(new java.awt.Color(0, 0, 0));
        boton_editar_arbitro.setText("Editar");
        boton_editar_arbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_editar_arbitroActionPerformed(evt);
            }
        });
        jPanel1.add(boton_editar_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 450, 94, -1));

        boton_borrar_arbitro.setBackground(new java.awt.Color(204, 0, 0));
        boton_borrar_arbitro.setForeground(new java.awt.Color(0, 0, 0));
        boton_borrar_arbitro.setText("Borrar");
        boton_borrar_arbitro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_borrar_arbitroActionPerformed(evt);
            }
        });
        jPanel1.add(boton_borrar_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 450, 92, -1));

        combo_box_buscar_arbitro.setBackground(new java.awt.Color(255, 255, 255));
        combo_box_buscar_arbitro.setForeground(new java.awt.Color(0, 0, 0));
        combo_box_buscar_arbitro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {  }));
        jPanel1.add(combo_box_buscar_arbitro, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 80, 145, -1));

        table_arbitro.setBackground(new java.awt.Color(255, 255, 255));
        table_arbitro.setForeground(new java.awt.Color(51, 51, 51));
        table_arbitro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(table_arbitro);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 742, 320));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo blanco.jpg"))); // NOI18N
        jLabel13.setText("jLabel5");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 350, 460));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo blanco.jpg"))); // NOI18N
        jLabel14.setText("jLabel5");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 760, 460));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancha 2.0.jpg"))); // NOI18N
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1130, 510));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void boton_editar_arbitroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_editar_arbitroActionPerformed
        editarArbitro();
    }//GEN-LAST:event_boton_editar_arbitroActionPerformed

    private void boton_borrar_arbitroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_borrar_arbitroActionPerformed
        eliminarArbitro();
    }//GEN-LAST:event_boton_borrar_arbitroActionPerformed

    private void boton_guardar_arbitroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_guardar_arbitroActionPerformed
        guardarArbitro();
    }//GEN-LAST:event_boton_guardar_arbitroActionPerformed

    private void boton_limpiar_arbitroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_limpiar_arbitroActionPerformed
        limpiarTexts();
    }//GEN-LAST:event_boton_limpiar_arbitroActionPerformed

    private void boton_buscar_arbitroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_buscar_arbitroActionPerformed
        if (text_field_buscar_arbitro.getText().isEmpty()) {
            cargarListadoArbitros();
        } else {
            String buscarPor = combo_box_buscar_arbitro.getSelectedItem().toString();
            String texto = text_field_buscar_arbitro.getText().trim();
            filtrarTabla(buscarPor, texto);
        }
    }//GEN-LAST:event_boton_buscar_arbitroActionPerformed

    private void text_field_buscar_arbitroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_field_buscar_arbitroKeyPressed
        if (evt.getKeyCode() == 10) {
            String buscarPor = combo_box_buscar_arbitro.getSelectedItem().toString();
            String texto = text_field_buscar_arbitro.getText().trim();
            filtrarTabla(buscarPor, texto);
        }
    }//GEN-LAST:event_text_field_buscar_arbitroKeyPressed

    private void text_field_nombre_arbitroActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_text_field_nombre_arbitroActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_text_field_nombre_arbitroActionPerformed

    private void text_field_buscar_arbitroActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_text_field_buscar_arbitroActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_text_field_buscar_arbitroActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_borrar_arbitro;
    private javax.swing.JButton boton_buscar_arbitro;
    private javax.swing.JButton boton_editar_arbitro;
    private javax.swing.JButton boton_guardar_arbitro;
    private javax.swing.JButton boton_limpiar_arbitro;
    private javax.swing.JComboBox<String> combo_box_buscar_arbitro;
    private javax.swing.JComboBox<String> combo_box_categoria_arbitro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_arbitro;
    private javax.swing.JTextField text_field_apellido_arbitro;
    private javax.swing.JTextField text_field_buscar_arbitro;
    private javax.swing.JTextField text_field_contrasena_arbitro;
    private javax.swing.JTextField text_field_dorsal_arbitro;
    private javax.swing.JTextField text_field_edad_arbitro;
    private javax.swing.JTextField text_field_email_arbitro;
    private javax.swing.JTextField text_field_nombre_arbitro;
    private javax.swing.JTextField text_field_partidos_arbitro;
    private javax.swing.JTextField text_field_usuario_arbitro;
    // End of variables declaration//GEN-END:variables

}
