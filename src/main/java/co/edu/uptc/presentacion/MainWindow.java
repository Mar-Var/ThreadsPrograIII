package co.edu.uptc.presentacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.logica.modelo.Turno;
import co.edu.uptc.persistencia.PersistenciaTurnos;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import co.edu.uptc.presentacion.TablePanel;
import co.edu.uptc.presentacion.RoundedBorder;
import co.edu.uptc.presentacion.JPanelRound;
import co.edu.uptc.logica.modelo.Tramite;

import co.edu.uptc.persistencia.PersistenciaTramite;
import co.edu.uptc.threads.*;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	Font fuenteDigital;
	
	//Panel Validacion de usuario------------------------------------------------------------------------------------------------------------------------
	private JPanelRound pnlValidacion;
	
	private JLabel fondoValidacion;
	private JLabel lblIngUsuarioValidacion;
	private JTextField txtIdentificacionValidacion;

	private JLabel lblTramiteValidacion;
	private JComboBox<String> cmbTramiteValidacion;
	private JButton btnConsultar;
	private JButton btnGenerarTurno;
    private JPanelRound pnlTurnoValidacion;
    private JLabel lblDarTurnoValidacion;
    private JLabel lblTurnoValidacion;
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------
	//Panel modulos--------------------------------------------------------------------------------------------------------------------------------------
    
    private JPanel pnlModulos;
    private JLabel fondoModulos;
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------
    //modulo citas---------------------------------------------------------------------------------------------------------------------------------------
    
    private JPanelRound pnlCitas;
    private JLabel lblEstadoCitas;
    private JLabel lblImagenCitas;
	private TablePanel tablaTurnosCitas;
    
    //---------------------------------------------------------------------------------------------------------------------------------------------------
	//modulo medicamentos--------------------------------------------------------------------------------------------------------------------------------
	
	private JPanelRound pnlCajaMedicamentos;
	private JLabel lblEstadoMedicamentos;
	private JLabel lblImagenMedicamentos;
	private TablePanel tablaTurnosMedicamentos;
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	//modulo Administrativo------------------------------------------------------------------------------------------------------------------------------
	
	private JPanelRound pnlCajaAdministrativo;
	private JLabel lblEstadoAdministrativo;
	private JLabel lblImagenAdministrativo;
	private TablePanel tablaTurnosAdministrativo;
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------

    
	
	JPanel pnEstModulo;
	JPanel pnEstServicios;
	JPanel pnEstadisticas;
	
	
	
	JPanel pnCMTurno;
	JPanel pnCPTurno;
	
	JLabel lbCCCodigoTurno;
	JLabel lbCCEstadoCaja;
	JTable tCC;
	DefaultTableModel dtmCC;
	
	JLabel lbCMCodigoTurno;
	JLabel lbCMEstadoCaja;
	JTable tCM;
	DefaultTableModel dtmCM;
	
	JLabel lbCPCodigoTurno;
	JLabel lbCPEstadoCaja;
	JTable tCP;
	DefaultTableModel dtmCP;
	
	ChartPanel chtpModulos;
	ChartPanel chtpServicios;
	JFreeChart fcEstadisticaModulos;
	JFreeChart fcEstadisticaServicios;

	Thread threadCC;
	Thread threadCM;
	Thread threadCP;

	PersistenciaTurnos pt = new PersistenciaTurnos();

	ModuloEstadisticas rneModuloPagos;
	Thread thrEstModulo;
	PersistenciaTramite perTra;
	HandlingEvents he;


	public MainWindow() {
		
		super("Super EPS");
		setLayout(null);
		setSize(1350,900);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		this.getContentPane().setBackground(Color.WHITE);
		Image icono = Toolkit.getDefaultToolkit().getImage("Imagenes/Icono.png");
		this.setIconImage(icono);
		he = new HandlingEvents(this);
	}
	
	public void begin() {
		createComponents();
		addComponents();
		rneModuloPagos = new ModuloEstadisticas(pnEstadisticas,chtpModulos,chtpServicios);
		thrEstModulo = new Thread(rneModuloPagos);
		new Thread(()-> {


			while ( true ) {
				Queue<Turno> queueCC = new LinkedList<Turno>();
				ArrayList<Turno> trs = pt.TraerTodoslosTurnos();
				ArrayList<Turno> aux = new ArrayList<>();
				try {
					tablaTurnosCitas.cleanTable();
					for (Turno t : trs) {
						if ( t.getModulo().equals("Caja 1") && !t.isEstado() ) {
							queueCC.add(t);
							aux.add(t);
						}
					}
					
					llenarTabla(aux);
					String[][] tabla=new String[queueCC.size()][1];
					for(int i=0; i< aux.size(); i++) {
						tabla[i][0] = ""+aux.get(i).getAfiliado().getCedula();
						tabla[i][1] = aux.get(i).getCodigo();
					}
					tablaTurnosCitas.showTable(tabla);
					System.out.println(tabla.toString());
				} catch (Exception e) {
				};
				
				lblEstadoCitas.setText(queueCC.isEmpty() ? "Sin turnos...": "Atendiendo a: "+queueCC.element().getCodigo());
				try {
					Thread.sleep(7000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					int index = trs.indexOf(queueCC.remove());
					trs.get( index ).setEstado(true);
					pt.SobreEscribirArchivoProducto(trs);
					
				} catch (Exception e2) {
				}
				

	
			}
			
		}).start();
		new Thread(()-> {


			while ( true) {
				Queue<Turno> queueCM = new LinkedList<Turno>();
				ArrayList<Turno> trs = pt.TraerTodoslosTurnos();
				try {
					for (Turno t : trs) {
						if ( t.getModulo().equals("Caja 2") && !t.isEstado() ) {
							queueCM.add(t);
						}
					}
					
				} catch (Exception e) {
				}

				lblEstadoMedicamentos.setText(queueCM.isEmpty() ? "Sin turnos...":"Atendiendo a: "+queueCM.element().getCodigo());
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					int index = trs.indexOf(queueCM.remove());
					trs.get( index ).setEstado(true);
					pt.SobreEscribirArchivoProducto(trs);
					
				} catch (Exception e) {
				}
				
				
			
			}
		
		}).start();;
		new Thread(()-> {

			while ( true) {
				Queue<Turno> queueCP = new LinkedList<Turno>();
				ArrayList<Turno> trs = pt.TraerTodoslosTurnos();
				try {
					
					for (Turno t : trs ) {
						if ( t.getModulo().equals("Caja 3") && !t.isEstado()) {
							queueCP.add(t);
						}
					}
					
				}catch (Exception e) {
				}

				lblEstadoAdministrativo.setText( queueCP.isEmpty() ? "Sin turnos..." : "Atendiendo a: "+queueCP.element().getCodigo());
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					int index = trs.indexOf(queueCP.remove());
					trs.get( index ).setEstado(true);
					pt.SobreEscribirArchivoProducto(trs);
					pnlModulos.updateUI();
					
				} catch (Exception e) {
					
				}
			}
			
		}).start();;
		
		thrEstModulo.start();

	}

	
	private void llenarTabla(ArrayList<Turno> aux) {
		String[][] tabla=new String[aux.size()][1];
		for(int i=0; i< aux.size(); i++) {
			tabla[i][0] = ""+aux.get(i).getAfiliado().getCedula();
			tabla[i][1] = aux.get(i).getCodigo();
		}
		tablaTurnosCitas.showTable(tabla);
		
	}

	public void createComponents() {
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/co/edu/uptc/presentacion/digital-7.ttf")));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		fuenteDigital = new Font("Times New Roman",Font.PLAIN, 30); //--------------------------FUENTE
		
		//Inicio componentes panel validacion-----------------------------------------------------------------------------------------------------------
		
		pnlValidacion = new JPanelRound();
		pnlValidacion.setLayout(null);
		pnlValidacion.setBounds(9, 11, 292, 636);
		
		fondoValidacion = new JLabel();
		fondoValidacion.setBounds(0, 0, 292, 636);
		ImageIcon imgIcon = new ImageIcon("Imagenes/Registro.png");
	    Image imgEscalada = imgIcon.getImage().getScaledInstance(fondoValidacion.getWidth(),fondoValidacion.getHeight(), Image.SCALE_SMOOTH);
	    Icon iconoEscalado = new ImageIcon(imgEscalada);
        fondoValidacion.setIcon(iconoEscalado);
        
        lblIngUsuarioValidacion=new JLabel();
		lblIngUsuarioValidacion.setText("<html><center><p>Ingrese su numero de <br> identificacion </br></p></center></html>");
		lblIngUsuarioValidacion.setBounds(85, 81, 170, 37);
		
		txtIdentificacionValidacion = new JTextField(20);
		txtIdentificacionValidacion.setBounds(61, 129, 170, 27);
		txtIdentificacionValidacion.addKeyListener(new KeyAdapter()
		{
		   public void keyTyped(KeyEvent e)
		   {
		      char caracter = e.getKeyChar();

		      // Verificar si la tecla pulsada no es un digito
		      if(((caracter < '0') ||
		         (caracter > '9')) &&
		         (caracter != '\b' /*corresponde a BACK_SPACE*/))
		      {
		         e.consume();  // ignorar el evento de teclado
		      }
		   }
		});
		
		lblTramiteValidacion = new JLabel("Seleccione el tipo de tramite");
        lblTramiteValidacion.setBounds(62, 172, 173, 37);
        
        perTra= new PersistenciaTramite();
        cmbTramiteValidacion = new JComboBox<String>();
        cmbTramiteValidacion.setBounds(65, 216, 168, 27);
		ArrayList<Tramite> tramites;

		if(perTra.TraerTodoslosTramites()!=null) {
			tramites=perTra.TraerTodoslosTramites();
			for (int i = 0; i < tramites.size(); i++) {
				cmbTramiteValidacion.addItem(tramites.get(i).getNombre());
			}

		}
		
		btnConsultar = new JButton("Ingresar");
		btnConsultar.setActionCommand(HandlingEvents.CONSULTAR);
		btnConsultar.addActionListener(new HandlingEvents(this));
		btnConsultar.setBounds(85, 277, 127, 27);
		btnConsultar.setBorder(new RoundedBorder(10));
		
		btnGenerarTurno= new JButton("Generar Turno");
		btnGenerarTurno.setActionCommand(HandlingEvents.GENERAR_TURNO);
		btnGenerarTurno.addActionListener(new HandlingEvents(this));
		btnGenerarTurno.setBounds(85, 277, 127, 27);
		btnGenerarTurno.setBorder(new RoundedBorder(10));
		btnGenerarTurno.setVisible(false);
		
		pnlTurnoValidacion = new JPanelRound();
        pnlTurnoValidacion.setLayout(null);
        pnlTurnoValidacion.setBackground(Color.BLACK);
        pnlTurnoValidacion.setBounds(49, 432, 211, 100);
        pnlTurnoValidacion.setBorder(new RoundedBorder(40));

        
        lblTurnoValidacion = new JLabel("Su turno es: ");
        lblTurnoValidacion.setBounds(25,20,211,21);
        lblTurnoValidacion.setForeground(Color.WHITE);
        lblTurnoValidacion.setFont(fuenteDigital);
        

        lblDarTurnoValidacion = new JLabel();
        lblDarTurnoValidacion.setBounds(40,45,211,40);
        lblDarTurnoValidacion.setForeground(Color.GREEN);
        lblDarTurnoValidacion.setFont(fuenteDigital);
		
		
        //---------------------------------------------------------------------------------------------------------------------------------------------
        //Inicio componentes panel modulos-------------------------------------------------------------------------------------------------------------
        
        pnlModulos=new JPanel();
        pnlModulos.setLayout(null);
        pnlModulos.setBounds(311, 12, 1011, 538);
        
        fondoModulos=new JLabel();
		fondoModulos.setBounds(0, 0, 1011, 538);
		ImageIcon imgIconModulos = new ImageIcon("Imagenes/fondo.jpeg");
	    Image imgEscaladaModulos = imgIconModulos.getImage().getScaledInstance(fondoModulos.getWidth(), fondoModulos.getHeight(), Image.SCALE_SMOOTH);
	    Icon iconoEscaladoModulos = new ImageIcon(imgEscaladaModulos);
	    fondoModulos.setIcon(iconoEscaladoModulos);
        
        //---------------------------------------------------------------------------------------------------------------------------------------------
	    //modulo citas---------------------------------------------------------------------------------------------------------------------------------
	    
	    pnlCitas = new JPanelRound();
		pnlCitas.setLayout(null);
		pnlCitas.setBounds(18, 25, 301, 490);
		
		lblEstadoCitas=new JLabel();
		lblEstadoCitas.setForeground(Color.WHITE);
		lblEstadoCitas.setBounds(23, 8, 253, 21);
		
		lblImagenCitas=new JLabel();
		lblImagenCitas.setBounds(20, 29, 258, 240);
		
		ImageIcon imgIconCitas = new ImageIcon("Imagenes/4.png");
	    Image imgEscaladaCitas = imgIconCitas.getImage().getScaledInstance(lblImagenCitas.getWidth(), lblImagenCitas.getHeight(), Image.SCALE_SMOOTH);
	    Icon iconoEscaladoCitas = new ImageIcon(imgEscaladaCitas);
	    lblImagenCitas.setIcon(iconoEscaladoCitas);
		
		String[] headCitas= {"Identificacion","Turno"};
		tablaTurnosCitas = new TablePanel(headCitas, 253, 189);
		tablaTurnosCitas.setBounds(23,285,253,189);
	    
	    //----------------------------------------------------------------------------------------------------------------------------------------------
		//modulo medicamentos---------------------------------------------------------------------------------------------------------------------------
		
		pnlCajaMedicamentos = new JPanelRound();
		pnlCajaMedicamentos.setLayout(null);
		pnlCajaMedicamentos.setBounds(356, 28, 301, 490);
		
		lblEstadoMedicamentos=new JLabel();
		lblEstadoMedicamentos.setForeground(Color.WHITE);
		lblEstadoMedicamentos.setBounds(23, 8, 253, 21);
		
		lblImagenMedicamentos=new JLabel();
		lblImagenMedicamentos.setBounds(20, 29, 258, 240);
		
		ImageIcon imgIconMedicamentos = new ImageIcon("Imagenes/6.png");
	    Image imgEscaladaMedicamentos = imgIconMedicamentos.getImage().getScaledInstance(lblImagenMedicamentos.getWidth(), lblImagenMedicamentos.getHeight(), Image.SCALE_SMOOTH);
	    Icon iconoEscaladoMedicamentos = new ImageIcon(imgEscaladaMedicamentos);
	    lblImagenMedicamentos.setIcon(iconoEscaladoMedicamentos);
		
		String[] head= {"Identificacion","Turno"};
		tablaTurnosMedicamentos = new TablePanel(head, 253, 189);
		tablaTurnosMedicamentos.setBounds(23,285,253,189);
		
		//----------------------------------------------------------------------------------------------------------------------------------------------
		//modulo Administrativo---------------------------------------------------------------------------------------------------------------------------
		
		pnlCajaAdministrativo = new JPanelRound();
		pnlCajaAdministrativo.setLayout(null);
		pnlCajaAdministrativo.setBounds(686, 28, 301, 490);
		
		lblEstadoAdministrativo=new JLabel();
		lblEstadoAdministrativo.setForeground(Color.WHITE);
		lblEstadoAdministrativo.setBounds(23, 8, 253, 21);
		
		lblImagenAdministrativo=new JLabel();
		lblImagenAdministrativo.setBounds(20, 29, 258, 240);
		ImageIcon imgIconAdministrativo = new ImageIcon("Imagenes/5.png");
	    Image imgEscaladaAdministrativo = imgIconAdministrativo.getImage().getScaledInstance(lblImagenAdministrativo.getWidth(), lblImagenAdministrativo.getHeight(), Image.SCALE_SMOOTH);
	    Icon iconoEscaladoAdministrativo = new ImageIcon(imgEscaladaAdministrativo);
	    lblImagenAdministrativo.setIcon(iconoEscaladoAdministrativo);
		
		String[] headAdministrativo= {"Identificacion","Turno"};
		tablaTurnosAdministrativo = new TablePanel(headAdministrativo, 253, 189);
		tablaTurnosAdministrativo.setBounds(23,285,253,189);
		

		//---------------------------------------------------------------------------------------------------------------------------------------------
		
		pnEstadisticas = new JPanel();
		pnEstadisticas.setBorder(new TitledBorder("Estadisticas"));
		pnEstadisticas.setLayout( new GridBagLayout());
		pnEstadisticas.setBounds(311, 562, 1011, 292);
		
		pnEstModulo = new JPanel();
		pnEstModulo.setBorder(new TitledBorder("Estadisticas por Modulo"));
		pnEstModulo.setLayout( new GridBagLayout());
		
		pnEstServicios = new JPanel();
		pnEstServicios.setBorder(new TitledBorder("Estadisticas por Servicio"));
		pnEstServicios.setLayout( new GridBagLayout());

		pnCMTurno = new JPanel();
		pnCMTurno.setBorder(new TitledBorder("Turno Actual"));
		pnCMTurno.setBackground(Color.BLACK);
		pnCMTurno.setLayout( new GridBagLayout());
		
		pnCPTurno = new JPanel();
		pnCPTurno.setBorder(new TitledBorder("Turno Actual"));

		pnCPTurno.setBackground(Color.BLACK);
		pnCPTurno.setLayout( new GridBagLayout());
		
		
		lbCMCodigoTurno = new JLabel("XXXX");
		lbCMCodigoTurno.setForeground(new Color(57,255,20));;
		lbCMCodigoTurno.setFont(fuenteDigital);
		lbCMEstadoCaja = new JLabel("XXXXXXXX");
		lbCMEstadoCaja.setForeground(Color.WHITE);;
		lbCMEstadoCaja.setFont(fuenteDigital);
		tCM = new JTable(dtmCM);
		dtmCM = new DefaultTableModel();
		
		lbCPCodigoTurno = new JLabel("XXXX");
		lbCPCodigoTurno.setForeground(new Color(57,255,20));;
		lbCPCodigoTurno.setFont(fuenteDigital);
		lbCPEstadoCaja = new JLabel("XXXXXXXX");
		lbCPEstadoCaja.setForeground(Color.WHITE);;
		lbCPEstadoCaja.setFont(fuenteDigital);
		tCP = new JTable(dtmCP);
		dtmCP = new DefaultTableModel();
		
//		Dejemoslo pendiente
		DefaultPieDataset datos1= new DefaultPieDataset();
		datos1.setValue("Caja 1", 5);
		datos1.setValue("Caja 2", 5);
		datos1.setValue("Caja 3", 5);

		fcEstadisticaModulos = ChartFactory.createPieChart(
				"Estadisticas por Modulos",//Nombre del grafico
				datos1,// datos
				true, // nombre de las categorias f o v
				true, // herramientas f o t
				true); // generacion de URL false
			
		fcEstadisticaServicios = ChartFactory.createPieChart(
				"Estadisticas por Modulos",//Nombre del grafico
				datos1,// datos
				true, // nombre de las categorias f o v
				true, // herramientas f o t
				true); // generacion de URL false);


		chtpModulos=new ChartPanel(fcEstadisticaModulos);
		chtpModulos.setPreferredSize(new Dimension(200,200));

		chtpServicios = new ChartPanel(fcEstadisticaServicios);
		chtpServicios.setPreferredSize(new Dimension(200,200));



	}
	
	
	public void addComponents() {
		GridBagConstraints g = new GridBagConstraints();
		g.insets = new Insets(10, 10, 10, 10);
		g.gridx=0;
		g.gridy=0;
		g.gridheight=3;
		g.fill = GridBagConstraints.BOTH;
		g.weighty=1.0;

		addComponentspnValidacion();
		add(pnlValidacion,g);

		g.gridx=1;
		g.gridy=0;
		g.gridheight=2;
		addComponentspnZonaTurnos();

		add(pnlModulos,g);
		g.gridheight=1;
		g.gridy=2;
		g.fill = GridBagConstraints.NONE;
		g.fill=GridBagConstraints.HORIZONTAL;
		addComponentspnEstadisticas();
		add(pnEstadisticas,g);
		
	}
	
	//A�adir componentes al panel de usuarios------------------------------------------------------------------------------------------------------
	
	public void addComponentspnValidacion() {
		
		addComponentspnVerificarUsuario();
		pnlValidacion.add(lblIngUsuarioValidacion);
		pnlValidacion.add(txtIdentificacionValidacion);
		pnlValidacion.add(lblTramiteValidacion);
		pnlValidacion.add(cmbTramiteValidacion);
		pnlValidacion.add(btnConsultar);
		pnlValidacion.add(btnGenerarTurno);
		add(pnlTurnoValidacion);
        pnlTurnoValidacion.add(lblTurnoValidacion);
        pnlTurnoValidacion.add(lblDarTurnoValidacion);
		addComponentspnGenerarTurno();
		
		pnlValidacion.add(fondoValidacion);
		
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------------------
	
	//A�adir componentes al panel de modulos-------------------------------------------------------------------------------------------------------
	
	public void addComponentspnZonaTurnos() {
		addComponentspnCajaCitas();
		addComponentspnCajaMedicamentos();
		addComponentspnCajaPagos();
		pnlModulos.add(pnlCitas);
		pnlModulos.add(pnlCajaMedicamentos);
		pnlModulos.add(pnlCajaAdministrativo);
		
		pnlModulos.add(fondoModulos);
	}
	
	//modulo citas----------------------------------------------------------------------------------------------------------------------------------
    
	public void addComponentspnCajaCitas() {
		pnlCitas.add(lblEstadoCitas);
		pnlCitas.add(lblImagenCitas);
		pnlCitas.add(tablaTurnosCitas);
//			pnlCajaCitas.add(pnCCTurno,g);
//			pnlCajaCitas.add(tCC);
		
	}
		
	//---------------------------------------------------------------------------------------------------------------------------------------------
		
	//modulo medicamentos--------------------------------------------------------------------------------------------------------------------------
		
	public void addComponentspnCajaMedicamentos() {
		pnlCajaMedicamentos.add(lblEstadoMedicamentos);
		pnlCajaMedicamentos.add(lblImagenMedicamentos);
		pnlCajaMedicamentos.add(tablaTurnosMedicamentos);
		addComponentspnCMTurno();
//		pnlCajaMedicamentos.add(pnCMTurno,g);
//		pnlCajaMedicamentos.add(tCM);
		
	}	
		
	//---------------------------------------------------------------------------------------------------------------------------------------------
	//modulo administrativo--------------------------------------------------------------------------------------------------------------------------------
	
	public void addComponentspnCajaPagos() {
		pnlCajaAdministrativo.add(lblEstadoAdministrativo);
		pnlCajaAdministrativo.add(lblImagenAdministrativo);
		pnlCajaAdministrativo.add(tablaTurnosAdministrativo);
		addComponentspnCPTurno();
//		pnlCajaPagos.add(pnCPTurno,g);
//		pnlCajaPagos.add(tCP);
		
	}

	//---------------------------------------------------------------------------------------------------------------------------------------------

	//---------------------------------------------------------------------------------------------------------------------------------------------
	
	public void addComponentspnEstadisticas() {
		GridBagConstraints g = new GridBagConstraints();
		g.gridx=0;
		g.gridy=0;
		g.insets= new Insets(10, 10, 10, 10);
		pnEstadisticas.add(chtpModulos,g);
		g.gridx=1;
		pnEstadisticas.add(chtpServicios,g);
		
	}
	
	
	public void addComponentspnVerificarUsuario() {
		GridBagConstraints g = new GridBagConstraints();
		g.gridx=0;
		g.gridy=0;
		g.insets = new Insets(10, 10, 10, 10);
//		pnVerficaUsuario.add(lbCedula,g);
		g.gridy=1;
//		pnVerficaUsuario.add(txtCedula,g);
		g.gridy=2;
//		pnVerficaUsuario.add(btnConsultar,g);

		
		
	}
	public void addComponentspnGenerarTurno() {
		GridBagConstraints g = new GridBagConstraints();
		g.gridx=0;
		g.gridy=0;
		g.insets = new Insets(10, 10, 10, 10);

		g.gridy=1;
//		g.gridy=2;
//		pnGenerarTurno.add(lbCBTipoTramite,g);
//		g.gridy=3;
//		pnGenerarTurno.add(cbTipoTramite,g);
		g.gridy=2;
		
	}
	
	public void addComponentspnCMTurno() {
		GridBagConstraints g = new GridBagConstraints();
		g.gridx=0;
		g.gridy=0;
		pnCMTurno.add(lbCMCodigoTurno,g);
		g.gridy=1;
		pnCMTurno.add(lbCMEstadoCaja,g);
		
	}
	public void addComponentspnCPTurno() {
		GridBagConstraints g = new GridBagConstraints();
		g.gridx=0;
		g.gridy=0;
		pnCPTurno.add(lbCPCodigoTurno,g);
		g.gridy=1;
		pnCPTurno.add(lbCPEstadoCaja,g);
		
	}
	
	

	public JLabel getLblDarTurnoValidacion() {
		return lblDarTurnoValidacion;
	}

	public void setLblDarTurnoValidacion(JLabel lblDarTurnoValidacion) {
		this.lblDarTurnoValidacion = lblDarTurnoValidacion;
	}

	public JButton getBtnConsultar() {
		return btnConsultar;
	}

	public void setBtnConsultar(JButton btnConsultar) {
		this.btnConsultar = btnConsultar;
	}

	public JButton getBtnGenerarTurno() {
		return btnGenerarTurno;
	}

	public void setBtnGenerarTurno(JButton btnGenerarTurno) {
		this.btnGenerarTurno = btnGenerarTurno;
	}

	public JTextField getTxtCedula() {
		return txtIdentificacionValidacion;
	}

	public void setTxtCedula(JTextField txtCedula) {
		this.txtIdentificacionValidacion = txtCedula;
	}

	public JComboBox<String> getCbTipoTramite() {
		return cmbTramiteValidacion;
	}

	public void setCbTipoTramite(JComboBox<String> cbTipoTramite) {
		this.cmbTramiteValidacion = cbTipoTramite;
	}
	
	
//	public void addComponentspnEstModulo() {
//		GridBagConstraints g = new GridBagConstraints();
//		g.gridx=0;
//		g.gridy=0;
//
//	}
//	public void addComponentspnEstServicios() {
//		GridBagConstraints g = new GridBagConstraints();
//
//	}

}
