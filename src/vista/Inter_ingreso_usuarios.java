/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vista;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.x.protobuf.MysqlxCrud.Find.RowLock;

import controlador.Controlador_usuario;
import excepciones.UsuarioException;
import java.util.ArrayList;
import java.util.List;

import modelo.Rol;
import modelo.TipoBusquedaCombo;
import modelo.Usuario;

/**
 *
 * @author kevin
 */
public class Inter_ingreso_usuarios extends javax.swing.JInternalFrame {

    Controlador_usuario controlador_usuario = new Controlador_usuario();
    DefaultTableModel modelo;
    Integer id = 0;
    Boolean mostrar = true;
    char caracter;

    /**
     * Creates new form Inter_ingreso_secretarias
     */
    public Inter_ingreso_usuarios() {
        initComponents();
        setearTabla();
        cargarComboRol();
        cargarComboFiltro();
        cargarListadoUsuarios();
        caracter = text_contrasenia.getEchoChar();
    }

    private void setearTabla() {
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
        modelo.addColumn("Email");
        modelo.addColumn("Contraseña");
        modelo.addColumn("id_rol");
        modelo.addColumn("Rol");
        table_usuarios = new JTable(modelo);
        // table_usuarios.setModel(modelo);
        jScrollPane1.setViewportView(table_usuarios);
        table_usuarios.getColumnModel().getColumn(0).setMaxWidth(20);
        table_usuarios.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_usuarios.removeColumn(table_usuarios.getColumnModel().getColumn(1));
        table_usuarios.removeColumn(table_usuarios.getColumnModel().getColumn(5));
        table_usuarios.removeColumn(table_usuarios.getColumnModel().getColumn(5));
    }

    private void cargarComboFiltro() {
        combo_box_buscar.addItem(TipoBusquedaCombo.NOMBRE);
        combo_box_buscar.addItem(TipoBusquedaCombo.APELLIDO);
        combo_box_buscar.addItem(TipoBusquedaCombo.USUARIO);
    }

    private void cargarComboRol() {
        combo_box_rol.addItem("<SELECCIONE>");
        combo_box_rol.addItem(Rol.ARBITRO_STRING);
        combo_box_rol.addItem(Rol.SECRETARIO_STRING);
        combo_box_rol.addItem(Rol.PRESIDENTE_STRING);
        combo_box_rol.addItem(Rol.ADMINISTRADOR_STRING);
    }

    private void guardarUsuario() {

        String mensaje = "";

        if (camposNoValidos()) {
            JOptionPane.showMessageDialog(null, "Por favor ingrese los datos", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(text_nombre.getText().trim());
        nuevoUsuario.setApellido(text_apellido.getText().trim());
        nuevoUsuario.setNombre_usuario(text_nombre_user.getText().trim());
        nuevoUsuario.setEmail(text_email.getText().trim());
        nuevoUsuario.setContrasenia(text_contrasenia.getPassword().toString());
        nuevoUsuario.setId_rol(combo_box_rol.getSelectedIndex());
        if (id == 0) {
            mensaje = controlador_usuario.guardarUsuario(nuevoUsuario);

        } else {
            try {
                nuevoUsuario.setId_usuario(id);
                mensaje = controlador_usuario.actualizarUsuario(nuevoUsuario);
                id = 0;
            } catch (UsuarioException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage() + mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        JOptionPane.showMessageDialog(null, mensaje, "Info", JOptionPane.INFORMATION_MESSAGE);
        cargarListadoUsuarios();
        limpiarTexts();

    }

    private void eliminarUsuario() {
        if (!filaSeleccionada()) {
            JOptionPane.showMessageDialog(null, "Por favor seleccione una fila para realizar esta acción", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int row = table_usuarios.getSelectedRow();
        int id = Integer.parseInt(table_usuarios.getModel().getValueAt(row, 1).toString());

        int confirmacion = JOptionPane.showConfirmDialog(null, "Desea eliminar este registro?", "Advertencia",
                JOptionPane.YES_NO_OPTION);
        if (confirmacion == 0) {
            try {
                String mensaje = controlador_usuario.eliminarUsuario(id);
                JOptionPane.showMessageDialog(null, mensaje, "Info", JOptionPane.INFORMATION_MESSAGE);
                cargarListadoUsuarios();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {

        }

    }

    private void editarUsuario() {
        if (!filaSeleccionada()) {
            JOptionPane.showMessageDialog(null, "Por favor seleccione una fila para realizar esta acción", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int fila = table_usuarios.getSelectedRow();
        id = (int) (table_usuarios.getModel().getValueAt(fila, 1));
        System.out.println(id);
        text_nombre.setText(table_usuarios.getModel().getValueAt(fila, 2).toString());
        text_apellido.setText(table_usuarios.getModel().getValueAt(fila, 3).toString());
        text_nombre_user.setText(table_usuarios.getModel().getValueAt(fila, 4).toString());
        text_email.setText(table_usuarios.getModel().getValueAt(fila, 5).toString());
        text_contrasenia.setText(table_usuarios.getModel().getValueAt(fila, 6).toString());

    }

    private void limpiarTexts() {
        id = 0;
        text_nombre.setText("");
        text_apellido.setText("");
        text_nombre_user.setText("");
        text_email.setText("");
        text_contrasenia.setText("");
        combo_box_rol.setSelectedIndex(0);
    }

    private void filtrarTabla(String buscarPor, String texto) {
        List<Usuario> listaFiltrada = new ArrayList<Usuario>();

        /*
         * System.out.println("Filtro" + "\n");
         * System.out.println(buscarPor + "\n");
         * System.out.println(texto + "\n");
         */
        switch (buscarPor) {
            case TipoBusquedaCombo.NOMBRE:
                for (Usuario e : controlador_usuario.listarUsuarios()) {
                    if (e.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                        listaFiltrada.add(e);
                    }
                }
                break;
            case TipoBusquedaCombo.APELLIDO:
                controlador_usuario.listarUsuarios().forEach((e) -> {
                    if (e.getApellido().toLowerCase().contains(texto.toLowerCase())) {
                        listaFiltrada.add(e);
                    }
                });
                break;
            case TipoBusquedaCombo.USUARIO:
                controlador_usuario.listarUsuarios().forEach((e) -> {
                    if (e.getApellido().toLowerCase().contains(texto.toLowerCase())) {
                        listaFiltrada.add(e);
                    }
                });
                break;
            default:
                break;
        }

        int i = 0;
        modelo.getDataVector().removeAllElements();
        table_usuarios.updateUI();
        for (Usuario e : listaFiltrada) {
            i = i + 1;
            modelo.addRow(new Object[]{i,
                e.getId_usuario(),
                e.getNombre(),
                e.getApellido(),
                e.getNombre_usuario(),
                e.getEmail(),
                e.getContrasenia(),
                (e.getId_rol()),
                e.getNombre_rol()
            });
        }
    }

    /*
     * private String getRol(int rol) {
     * switch (rol) {
     * case Rol.ARBITRO:
     * return Rol.ARBITRO_STRING;
     * case Rol.SECRETARIO:
     * return Rol.SECRETARIO_STRING;
     * case Rol.PRESIDENTE:
     * return Rol.PRESIDENTE_STRING;
     * case Rol.ADMINISTRADOR:
     * return Rol.ADMINISTRADOR_STRING;
     * default:
     * return "";
     * }
     * }
     */
    private void cargarListadoUsuarios() {
        int i = 0;
        modelo.getDataVector().removeAllElements();
        table_usuarios.updateUI();
        for (Usuario e : controlador_usuario.listarUsuarios()) {
            i = i + 1;
            modelo.addRow(new Object[]{i,
                e.getId_usuario(),
                e.getNombre(),
                e.getApellido(),
                e.getNombre_usuario(),
                e.getEmail(),
                e.getContrasenia(),
                (e.getId_rol()),
                e.getNombre_rol()
            });
        }
    }

    private boolean filaSeleccionada() {
        return (table_usuarios.getSelectedRow() > -1);
    }

    private boolean camposNoValidos() {
        return (text_nombre.getText().isEmpty() || text_apellido.getText().isEmpty()
                || text_contrasenia.getPassword().toString().isEmpty() || text_email.getText().isEmpty()
                || text_nombre_user.getText().isEmpty() || combo_box_rol.getSelectedIndex() == 0);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        text_email = new javax.swing.JTextField();
        lbl_ver = new javax.swing.JLabel();
        text_contrasenia = new javax.swing.JPasswordField();
        jLabel27 = new javax.swing.JLabel();
        text_nombre = new javax.swing.JTextField();
        text_apellido = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        text_nombre_user = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        boton_guardar = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        boton_limpiar = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        boton_borrar = new javax.swing.JButton();
        combo_box_buscar = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_usuarios = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        text_field_buscar = new javax.swing.JTextField();
        boton_buscar_secretaria = new javax.swing.JButton();
        boton_editar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        combo_box_rol = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setTitle("Ingreso secretaria");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Contraseña:");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        text_email.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(text_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 290, 190, -1));

        lbl_ver.setForeground(new java.awt.Color(0, 0, 0));
        lbl_ver.setText("Ver");
        lbl_ver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_ver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_verMouseClicked(evt);
            }
        });
        jPanel1.add(lbl_ver, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, 20, 20));
        jPanel1.add(text_contrasenia, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 170, -1));

        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Email:");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, 30));

        text_nombre.setBackground(new java.awt.Color(255, 255, 255));
        text_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_nombreActionPerformed(evt);
            }
        });
        jPanel1.add(text_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 190, -1));

        text_apellido.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(text_apellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 190, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Ingreso");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, -1, -1));

        text_nombre_user.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(text_nombre_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 190, -1));

        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Nombre :");
        jPanel1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        boton_guardar.setBackground(new java.awt.Color(0, 204, 204));
        boton_guardar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        boton_guardar.setForeground(new java.awt.Color(0, 0, 0));
        boton_guardar.setText("Guardar");
        boton_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_guardarActionPerformed(evt);
            }
        });
        jPanel1.add(boton_guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 92, -1));

        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Apellido:");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        boton_limpiar.setBackground(new java.awt.Color(0, 204, 0));
        boton_limpiar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        boton_limpiar.setForeground(new java.awt.Color(0, 0, 0));
        boton_limpiar.setText("Limpiar");
        boton_limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_limpiarActionPerformed(evt);
            }
        });
        jPanel1.add(boton_limpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 380, 92, -1));

        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Nombre usuario:");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        boton_borrar.setBackground(new java.awt.Color(204, 0, 0));
        boton_borrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        boton_borrar.setForeground(new java.awt.Color(0, 0, 0));
        boton_borrar.setText("Borrar");
        boton_borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_borrarActionPerformed(evt);
            }
        });
        jPanel1.add(boton_borrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 410, 80, -1));

        combo_box_buscar.setBackground(new java.awt.Color(255, 255, 255));
        combo_box_buscar.setForeground(new java.awt.Color(0, 0, 0));
        combo_box_buscar.setModel(new javax.swing.DefaultComboBoxModel<>());
        jPanel1.add(combo_box_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 145, -1));

        table_usuarios.setBackground(new java.awt.Color(255, 255, 255));
        table_usuarios.setModel(new javax.swing.table.DefaultTableModel(

        ));
        jScrollPane1.setViewportView(table_usuarios);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 110, 730, 280));

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("*Seleccione un registro para efectuar alguna accion de edicion o borrado* ");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, -1, -1));

        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Buscar por:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, -1, -1));

        text_field_buscar.setBackground(new java.awt.Color(255, 255, 255));
        text_field_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_field_buscarActionPerformed(evt);
            }
        });
        text_field_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_field_buscarKeyPressed(evt);
            }
        });
        jPanel1.add(text_field_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 70, 157, -1));

        boton_buscar_secretaria.setBackground(new java.awt.Color(0, 204, 204));
        boton_buscar_secretaria.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        boton_buscar_secretaria.setForeground(new java.awt.Color(0, 0, 0));
        boton_buscar_secretaria.setText("Buscar");
        boton_buscar_secretaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_buscar_secretariaActionPerformed(evt);
            }
        });
        jPanel1.add(boton_buscar_secretaria, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 60, -1, -1));

        boton_editar.setBackground(new java.awt.Color(255, 255, 0));
        boton_editar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        boton_editar.setForeground(new java.awt.Color(0, 0, 0));
        boton_editar.setText("Editar");
        boton_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_editarActionPerformed(evt);
            }
        });
        jPanel1.add(boton_editar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 410, 85, -1));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Rol:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, -1, 30));

        combo_box_rol.setBackground(new java.awt.Color(255, 255, 255));
        combo_box_rol.setModel(new javax.swing.DefaultComboBoxModel<>());
        jPanel1.add(combo_box_rol, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, 190, -1));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo blanco.jpg"))); // NOI18N
        jLabel6.setText("jLabel5");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 330, 340));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo blanco.jpg"))); // NOI18N
        jLabel5.setText("jLabel5");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 760, 450));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancha 2.0.jpg"))); // NOI18N
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1130, 510));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleName("Ingreso usuarios");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_verMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_verMouseClicked

        if (mostrar) { // a es una variable boolean en true
            text_contrasenia.setEchoChar((char) 0); // este método es el que hace visible el texto del jPasswordField
            mostrar = false;
        } else {
            text_contrasenia.setEchoChar(caracter); // i es el char
            mostrar = true;
        }
    }//GEN-LAST:event_lbl_verMouseClicked

    private void boton_guardarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_boton_guardarActionPerformed
        guardarUsuario();
    }// GEN-LAST:event_boton_guardarActionPerformed

    private void boton_limpiarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_boton_limpiarActionPerformed
        limpiarTexts();
    }// GEN-LAST:event_boton_limpiarActionPerformed

    private void boton_editarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_boton_editarActionPerformed
        editarUsuario();
    }// GEN-LAST:event_boton_editarActionPerformed

    private void boton_borrarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_boton_borrarActionPerformed
        eliminarUsuario();
    }// GEN-LAST:event_boton_borrarActionPerformed

    private void text_field_buscarKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_text_field_buscarKeyPressed
        if (evt.getKeyCode() == 10) {
            String filtro = combo_box_buscar.getSelectedItem().toString();
            String textoAbuscar = text_field_buscar.getText().trim();
            filtrarTabla(filtro, textoAbuscar);
        }
    }// GEN-LAST:event_text_field_buscarKeyPressed

    private void boton_buscar_secretariaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_boton_buscar_secretariaActionPerformed
        if (!text_field_buscar.getText().isEmpty()) {
            String filtro = combo_box_buscar.getSelectedItem().toString();
            String textoAbuscar = text_field_buscar.getText().trim();
            filtrarTabla(filtro, textoAbuscar);
        }
    }// GEN-LAST:event_boton_buscar_secretariaActionPerformed

    private void text_nombreActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_text_nombreActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_text_nombreActionPerformed

    private void text_field_buscarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_text_field_buscarActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_text_field_buscarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_borrar;
    private javax.swing.JButton boton_buscar_secretaria;
    private javax.swing.JButton boton_editar;
    private javax.swing.JButton boton_guardar;
    private javax.swing.JButton boton_limpiar;
    private javax.swing.JComboBox<String> combo_box_buscar;
    private javax.swing.JComboBox<String> combo_box_rol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_ver;
    private javax.swing.JTable table_usuarios;
    private javax.swing.JTextField text_apellido;
    private javax.swing.JPasswordField text_contrasenia;
    private javax.swing.JTextField text_email;
    private javax.swing.JTextField text_field_buscar;
    private javax.swing.JTextField text_nombre;
    private javax.swing.JTextField text_nombre_user;
    // End of variables declaration//GEN-END:variables
}
