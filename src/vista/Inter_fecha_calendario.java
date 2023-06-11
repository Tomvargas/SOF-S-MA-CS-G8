/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vista;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controlador.Controlador_agenda;
import controlador.Controlador_equipo;
import controlador.Controlador_partido;
import excepciones.AgendaException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import modelo.Agenda;
import modelo.Equipo_futbol;
import modelo.Partido;
import modelo.TipoBusquedaCombo;

/**
 *
 * @author kevin
 */
public class Inter_fecha_calendario extends javax.swing.JInternalFrame {

    SimpleDateFormat fechaFormato = new SimpleDateFormat("yyyy-MM-dd");
    Controlador_agenda controlador_agenda = new Controlador_agenda();
    DefaultTableModel modelo;
    Integer id = 0;

    /**
     * Creates new form Inter_fecha_calendario
     */
    public Inter_fecha_calendario() {
        initComponents();
        formatearText();

        setearTabla();
        cargarComboFiltro();
        cargarListadoAgendas();
        combo_box_equipo_local.addItem("<Seleccione>");
        combo_box_equipo_rival.addItem("<Seleccione>");
        cargarComboEquipos();
    }

    /**
     *
     */
    private void setearTabla() {
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                return false;
            }

        };

        modelo.addColumn("#");
        modelo.addColumn("id");
        modelo.addColumn("Hora");
        modelo.addColumn("Fecha");
        modelo.addColumn("Lugar");
        modelo.addColumn("Equipo local");
        modelo.addColumn("Equipo rival");
        table_calendario = new JTable(modelo);
        jScrollPane1.setViewportView(table_calendario);

        table_calendario.getColumnModel().getColumn(0).setMaxWidth(20);
        table_calendario.getColumnModel().getColumn(0).setPreferredWidth(20);
        table_calendario.removeColumn(table_calendario.getColumnModel().getColumn(1));
    }

    private boolean camposNoValidos() {
        return (text_box_lugar_partido.getText().isEmpty() || text_box_hora_partido.getText().isEmpty());
    }

    /**
     *
     */
    public void formatearText() {
        try {
            MaskFormatter maskHora = new MaskFormatter("##:##");
            text_box_hora_partido.setFormatterFactory(new DefaultFormatterFactory(maskHora));
        } catch (ParseException e) {
            System.out.println("error en parseo data" + e.getMessage());
        }
    }

    /**
     * Captura los datos dentro de los textbox y los guarda en la tabla fecha
     * calendario de la base de datos.
     *
     * @param void
     * @return void
     */
    public void guardarCalendario() {

        String mensaje = "";
        int id_partido = 0;

        if (camposNoValidos()) {
            JOptionPane.showMessageDialog(null, "Por favor ingrese los datos", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Agenda nuevaAgenda = new Agenda();
        nuevaAgenda.setHora_partido(Time.valueOf(text_box_hora_partido.getValue().toString() + ":00"));
        nuevaAgenda.setFecha_partido(new Date(fecha_partido.getDate().getTime()));
        nuevaAgenda.setLugar_partido(text_box_lugar_partido.getText().trim());

        try {
            String msg = guardarPartido();
            id_partido = Integer.parseInt(msg);
        } catch (Exception e) {
            System.out.print("Error en parseo " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error en parseo " + e.getMessage(), "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        nuevaAgenda.setPartido_id(new Partido(id_partido, new Equipo_futbol(), new Equipo_futbol()));

        if (id == 0) {
            mensaje = controlador_agenda.guardarAgenda(nuevaAgenda);
        } else {
            try {
                nuevaAgenda.setId_agenda(id);
                mensaje = controlador_agenda.actualizarAgenda(nuevaAgenda);
                id = 0;
            } catch (AgendaException ex) {
                Logger.getLogger(Inter_fecha_calendario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        JOptionPane.showMessageDialog(null, mensaje, "Info", JOptionPane.INFORMATION_MESSAGE);

        cargarListadoAgendas();
        limpiarTexts();

    }

    /**
     * Guarda un nuevo partido con los datos de los textbox equipo rival y local
     *
     * @param void
     * @return mensaje
     */
    public String guardarPartido() {
        Integer id_local = 0;
        Integer id_rival = 0;
        Partido nuevoPartido = new Partido();

        Controlador_equipo controlador_equipo = new Controlador_equipo();
        for (Equipo_futbol e : controlador_equipo.listarEquipos()) {
            if (e.getNombre_equipo().toLowerCase()
                    .equals(combo_box_equipo_local.getSelectedItem().toString().toLowerCase())) {
                id_local = e.getId_equipo();
                break;
            }
        }
        for (Equipo_futbol e : controlador_equipo.listarEquipos()) {
            if (e.getNombre_equipo().toLowerCase()
                    .equals(combo_box_equipo_rival.getSelectedItem().toString().toLowerCase())) {
                id_rival = e.getId_equipo();
                break;
            }
        }
        Controlador_partido controlador_partido = new Controlador_partido();
        System.out.print(" lc" + id_local + " re " + id_rival);
        nuevoPartido.setClub_local(new Equipo_futbol(id_local));
        nuevoPartido.setClub_rival(new Equipo_futbol(id_rival));
        String mensaje = controlador_partido.guardarPartido(nuevoPartido);

        return mensaje;
    }

    /**
     * Limpia las cajas de texto una vez ingresado un nuevo calendario de
     * fechas.
     *
     * @param void
     * @return void
     */
    public void limpiarTexts() {
        id = 0;
        text_box_hora_partido.setText("");
        text_box_lugar_partido.setText("");
        combo_box_equipo_local.setSelectedIndex(0);
        combo_box_equipo_rival.setSelectedIndex(0);
        fecha_partido.setCalendar(null);
    }

    /**
     * Edicion de algun registro en especifico de una fecha de calendario que ya
     * ha sido ingresada en base de datos.
     *
     * @param void
     * @return void
     */
    public void editarCalendario() {
        if (table_calendario.getSelectedRow() > -1) {
            try {
                int fila = table_calendario.getSelectedRow();
                id = (int) (table_calendario.getModel().getValueAt(fila, 1));
                System.out.println(id);
                text_box_hora_partido.setValue(table_calendario.getModel().getValueAt(fila, 2).toString());
                fecha_partido.setDate(fechaFormato.parse(table_calendario.getModel().getValueAt(fila, 3).toString()));
                text_box_lugar_partido.setText(table_calendario.getModel().getValueAt(fila, 4).toString());
                // Obtener id local
                for (int i = 0; i <= combo_box_equipo_local.getItemCount(); i++) {
                    String item = combo_box_equipo_local.getItemAt(i);
                    if (table_calendario.getModel().getValueAt(fila, 5).toString().equals(item)) {
                        combo_box_equipo_local.setSelectedIndex(i);
                        break;
                    }
                }
                // Obtener id rival
                for (int i = 0; i <= combo_box_equipo_rival.getItemCount(); i++) {
                    String item = combo_box_equipo_rival.getItemAt(i);
                    if (table_calendario.getModel().getValueAt(fila, 6).toString().equals(item)) {
                        combo_box_equipo_rival.setSelectedIndex(i);
                        break;
                    }
                }
            } catch (ParseException ex) {
                Logger.getLogger(Inter_fecha_calendario.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error en parseo date");
            }
        }
    }

    /**
     * Borrado logico de una fecha de calendario ingresado en la base de datos
     * Lo pasa de activo a inactivo.
     *
     * @param void
     * @return void
     */
    public void eliminarCalendario() {
        if (table_calendario.getSelectedRow() >= 0) {

            int row = table_calendario.getSelectedRow();
            int id = Integer.parseInt(table_calendario.getModel().getValueAt(row, 1).toString());

            int confirmacion = JOptionPane.showConfirmDialog(null, "Desea eliminar este registro?", "Advertencia",
                    JOptionPane.YES_NO_OPTION);
            if (confirmacion == 0) {
                try {
                    String mensaje = controlador_agenda.eliminarAgenda(id);
                    JOptionPane.showMessageDialog(null, mensaje, "Info", JOptionPane.INFORMATION_MESSAGE);
                    cargarListadoAgendas();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {

            }
        }
    }

    /**
     * Carga todo el listado de las fechas de calendario de la base de datos y
     * lo muestra en la ventana actual.
     *
     * @param void
     * @return void
     */
    public void cargarListadoAgendas() {
        int i = 0;
        modelo.getDataVector().removeAllElements();
        table_calendario.updateUI();
        for (Agenda e : controlador_agenda.listarAgendas()) {
            i = i + 1;
            modelo.addRow(new Object[]{i, e.getId_agenda(), e.getHora_partido(), e.getFecha_partido(),
                e.getLugar_partido(), e.getPartido_id().getClub_local().getNombre_equipo(),
                e.getPartido_id().getClub_rival().getNombre_equipo()
            });
        }
    }

    /**
     * Carga 2 titulos de columna de la tabla fechas calendario especificada en
     * este metodo dentro de un comboBox.
     *
     * @param void
     * @return void
     */
    public void cargarComboFiltro() {
        combo_box_buscar.addItem(TipoBusquedaCombo.LUGAR_PARTIDO);
        combo_box_buscar.addItem(TipoBusquedaCombo.EQUIPO_LOCAL);
        combo_box_buscar.addItem(TipoBusquedaCombo.EQUIPO_RIVAL);
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
        List<Agenda> listaFiltrada = new ArrayList<Agenda>();
        /*
         * System.out.println("Filtro" + "\n");
         * System.out.println(buscarPor + "\n");
         * System.out.println(texto + "\n");
         */

        switch (buscarPor) {
            case TipoBusquedaCombo.LUGAR_PARTIDO:
                controlador_agenda.listarAgendas().forEach(e -> {
                    if (e.getLugar_partido().toLowerCase().contains(texto.toLowerCase())) {
                        listaFiltrada.add(e);
                    }
                });
                break;
            case TipoBusquedaCombo.EQUIPO_LOCAL:
                controlador_agenda.listarAgendas().forEach((e) -> {
                    if (e.getPartido_id().getClub_local().getNombre_equipo().toLowerCase()
                            .contains(texto.toLowerCase())) {
                        listaFiltrada.add(e);
                    }
                });
                break;
            case TipoBusquedaCombo.EQUIPO_RIVAL:
                controlador_agenda.listarAgendas().forEach((e) -> {
                    if (e.getPartido_id().getClub_rival().getNombre_equipo().toLowerCase()
                            .contains(texto.toLowerCase())) {
                        listaFiltrada.add(e);
                    }
                });
                break;
            default:
                break;
        }

        int i = 0;
        modelo.getDataVector().removeAllElements();
        table_calendario.updateUI();
        for (Agenda e : listaFiltrada) {
            i = i + 1;
            modelo.addRow(new Object[]{i, e.getId_agenda(), e.getHora_partido(), e.getFecha_partido(),
                e.getLugar_partido(), e.getPartido_id().getClub_local().getNombre_equipo(),
                e.getPartido_id().getClub_rival().getNombre_equipo()
            });
        }
    }

    /**
     * Carga los equipos que existen dentro de un comboBox.
     *
     * @return void
     */
    public void cargarComboEquipos() {
        Controlador_equipo controlador_equipo = new Controlador_equipo();
        for (Equipo_futbol e : controlador_equipo.listarEquipos()) {
            combo_box_equipo_local.addItem(e.getNombre_equipo());
            combo_box_equipo_rival.addItem(e.getNombre_equipo());
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        fecha_partido = new com.toedter.calendar.JDateChooser();
        text_box_hora_partido = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        combo_box_equipo_local = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        combo_box_equipo_rival = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        boton_guardar_calendario = new javax.swing.JButton();
        text_box_lugar_partido = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        boton_limpiar_calendario = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        text_field_buscar = new javax.swing.JTextField();
        boton_buscar_calendario = new javax.swing.JButton();
        boton_editar = new javax.swing.JButton();
        boton_borrar_calendario = new javax.swing.JButton();
        combo_box_buscar = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_calendario = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Ingreso fecha calendario");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Hora del partido:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));
        jPanel1.add(fecha_partido, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 160, -1));

        text_box_hora_partido.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(text_box_hora_partido, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 160, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Ingreso");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, -1, -1));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Fecha del partido:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        combo_box_equipo_local.setBackground(new java.awt.Color(255, 255, 255));
        combo_box_equipo_local.setForeground(new java.awt.Color(0, 0, 0));
        combo_box_equipo_local.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
        }));
        jPanel1.add(combo_box_equipo_local, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 280, 161, -1));

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Equipo Rival:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, 20));

        combo_box_equipo_rival.setBackground(new java.awt.Color(255, 255, 255));
        combo_box_equipo_rival.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { }));
        jPanel1.add(combo_box_equipo_rival, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 320, 160, -1));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Lugar del partido:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        boton_guardar_calendario.setBackground(new java.awt.Color(0, 204, 204));
        boton_guardar_calendario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        boton_guardar_calendario.setForeground(new java.awt.Color(0, 0, 0));
        boton_guardar_calendario.setText("Guardar");
        boton_guardar_calendario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_guardar_calendarioActionPerformed(evt);
            }
        });
        jPanel1.add(boton_guardar_calendario, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, 91, -1));

        text_box_lugar_partido.setBackground(new java.awt.Color(255, 255, 255));
        text_box_lugar_partido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_box_lugar_partidoActionPerformed(evt);
            }
        });
        jPanel1.add(text_box_lugar_partido, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, 159, -1));

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Equipo Local:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        boton_limpiar_calendario.setBackground(new java.awt.Color(0, 204, 0));
        boton_limpiar_calendario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        boton_limpiar_calendario.setForeground(new java.awt.Color(0, 0, 0));
        boton_limpiar_calendario.setText("Limpiar");
        boton_limpiar_calendario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_limpiar_calendarioActionPerformed(evt);
            }
        });
        jPanel1.add(boton_limpiar_calendario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 370, 88, -1));

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("*Seleccione un registro para efectuar alguna accion de edicion o borrado* ");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, -1, -1));

        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Buscar por:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, -1, -1));

        text_field_buscar.setBackground(new java.awt.Color(255, 255, 255));
        text_field_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_field_buscarActionPerformed(evt);
            }
        });
        jPanel1.add(text_field_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 80, 157, -1));

        boton_buscar_calendario.setBackground(new java.awt.Color(0, 204, 204));
        boton_buscar_calendario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        boton_buscar_calendario.setForeground(new java.awt.Color(0, 0, 0));
        boton_buscar_calendario.setText("Buscar");
        boton_buscar_calendario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_buscar_calendarioActionPerformed(evt);
            }
        });
        jPanel1.add(boton_buscar_calendario, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 80, -1, -1));

        boton_editar.setBackground(new java.awt.Color(255, 255, 0));
        boton_editar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        boton_editar.setForeground(new java.awt.Color(0, 0, 0));
        boton_editar.setText("Editar");
        boton_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_editarActionPerformed(evt);
            }
        });
        jPanel1.add(boton_editar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 420, 90, -1));

        boton_borrar_calendario.setBackground(new java.awt.Color(204, 0, 0));
        boton_borrar_calendario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        boton_borrar_calendario.setForeground(new java.awt.Color(0, 0, 0));
        boton_borrar_calendario.setText("Borrar");
        boton_borrar_calendario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_borrar_calendarioActionPerformed(evt);
            }
        });
        jPanel1.add(boton_borrar_calendario, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 420, 95, -1));

        combo_box_buscar.setBackground(new java.awt.Color(255, 255, 255));
        combo_box_buscar.setForeground(new java.awt.Color(0, 0, 0));
        combo_box_buscar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {  }));
        jPanel1.add(combo_box_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 145, -1));

        table_calendario.setBackground(new java.awt.Color(255, 255, 255));
        table_calendario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
            }
        ));
        jScrollPane1.setViewportView(table_calendario);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 130, 757, 281));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo blanco.jpg"))); // NOI18N
        jLabel13.setText("jLabel5");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 300, 320));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo blanco.jpg"))); // NOI18N
        jLabel14.setText("jLabel5");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 770, 440));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancha 2.0.jpg"))); // NOI18N
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1140, 520));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1105, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boton_editarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_boton_editarActionPerformed
        editarCalendario();
    }// GEN-LAST:event_boton_editarActionPerformed

    private void boton_limpiar_calendarioActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_boton_limpiar_calendarioActionPerformed
        limpiarTexts();
    }// GEN-LAST:event_boton_limpiar_calendarioActionPerformed

    private void boton_borrar_calendarioActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_boton_borrar_calendarioActionPerformed
        eliminarCalendario();
    }// GEN-LAST:event_boton_borrar_calendarioActionPerformed

    private void boton_buscar_calendarioActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_boton_buscar_calendarioActionPerformed
        if (text_field_buscar.getText().isEmpty()) {
            cargarListadoAgendas();
        } else {
            String filtro = combo_box_buscar.getSelectedItem().toString();
            String textoAbuscar = text_field_buscar.getText().trim();
            flitrarTabla(filtro, textoAbuscar);
        }
    }// GEN-LAST:event_boton_buscar_calendarioActionPerformed

    private void boton_guardar_calendarioActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_boton_guardar_calendarioActionPerformed
        guardarCalendario();
    }// GEN-LAST:event_boton_guardar_calendarioActionPerformed

    private void text_field_id_calendarioActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_text_field_id_calendarioActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_text_field_id_calendarioActionPerformed

    private void text_field_buscarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_text_field_buscarActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_text_field_buscarActionPerformed

    private void text_box_lugar_partidoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_text_box_lugar_partidoActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_text_box_lugar_partidoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_borrar_calendario;
    private javax.swing.JButton boton_buscar_calendario;
    private javax.swing.JButton boton_editar;
    private javax.swing.JButton boton_guardar_calendario;
    private javax.swing.JButton boton_limpiar_calendario;
    private javax.swing.JComboBox<String> combo_box_buscar;
    private javax.swing.JComboBox<String> combo_box_equipo_local;
    private javax.swing.JComboBox<String> combo_box_equipo_rival;
    private com.toedter.calendar.JDateChooser fecha_partido;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_calendario;
    private javax.swing.JFormattedTextField text_box_hora_partido;
    private javax.swing.JTextField text_box_lugar_partido;
    private javax.swing.JTextField text_field_buscar;
    // End of variables declaration//GEN-END:variables
}
