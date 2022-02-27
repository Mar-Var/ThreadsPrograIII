package co.edu.uptc.presentacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.logica.modelo.Turno;
import co.edu.uptc.persistencia.PersistenciaTurnos;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import co.edu.uptc.threads.ModuloPagos;
import co.edu.uptc.logica.modelo.Modulo;
import co.edu.uptc.logica.modelo.Tramite;
import co.edu.uptc.persistencia.PersistenciaModulos;
import co.edu.uptc.persistencia.PersistenciaTramite;
import co.edu.uptc.threads.*;

public class MainWindow extends JFrame {
	Font fuenteDigital;
	
	
	JPanel pnValidacion;
	JPanel pnZonaTurnos;
	JPanel pnEstModulo;
	JPanel pnEstServicios;
	JPanel pnEstadisticas;
	
	
	JPanel pnVerficaUsuario;
	JPanel pnGenerarTurno;
	
	JPanel pnCajaCitas;
	JPanel pnCajaMedicamentos;
	JPanel pnCajaPagos;
	
	JPanel pnCCTurno;
	JPanel pnCMTurno;
	JPanel pnCPTurno;
	
	JLabel lbCedula;
	JTextField txtCedula;
	JButton btnConsultar;
	
	JLabel lbCBModulo;
	JComboBox<String> cbModulos;
	JLabel lbCBTipoTramite;
	JComboBox<String> cbTipoTramite;
	JButton btnGenerarTurno;
	
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
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);// Decir que pasa cuando se cierre la ventana
		setMinimumSize(new Dimension(1000,700));
		setLayout(new GridBagLayout());
		setResizable(true);
		he = new HandlingEvents(this);
	}
	
	public void begin() {
		createComponents();
		addComponents();
		rneModuloPagos = new ModuloEstadisticas(pnEstadisticas,chtpModulos,chtpServicios);
		thrEstModulo = new Thread(rneModuloPagos);
		thrEstModulo.start();
		threads();
	}

	private void threads() {
//		MOD_CITAS----------------------------------------------------------------------
		threadCC = new Thread(()-> {
			Queue<Turno> queueCC = new LinkedList<Turno>();
			ArrayList<Turno> trs = pt.TraerTodoslosTurnos();
			for (Turno t : trs) {
				if ( t.getModulo().equals("Caja 1") && !t.isEstado() ) {
					queueCC.add(t);
				}
			}

			while ( !queueCC.isEmpty() ) {
				lbCCCodigoTurno.setText(queueCC.element().getCodigo());
				try {
					Thread.sleep(1200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int index = trs.indexOf(queueCC.remove());
				trs.get( index ).setEstado(true);
				lbCCEstadoCaja.setText( queueCC.isEmpty() ? "Disponible" : "Atendiendo...");
			}
			pt.SobreEscribirArchivoProducto(trs);
		});
		threadCC.start();

//		MOD_MEDICAMENTOS--------------------------------------------------------
		threadCM = new Thread(()-> {
			Queue<Turno> queueCM = new LinkedList<Turno>();
			ArrayList<Turno> trs = pt.TraerTodoslosTurnos();
			for (Turno t : trs) {
				if ( t.getModulo().equals("Caja 2") && !t.isEstado() ) {
					queueCM.add(t);
				}
			}

			while ( !queueCM.isEmpty() ) {
				lbCMCodigoTurno.setText(queueCM.element().getCodigo());
				queueCM.element().setEstado(true);
				//System.out.println(queueCM.element().toString());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int index = trs.indexOf(queueCM.remove());
				trs.get( index ).setEstado(true);
				lbCMEstadoCaja.setText( queueCM.isEmpty() ? "Disponible" : "Atendiendo...");
			}
			pt.SobreEscribirArchivoProducto(trs);
		});
		threadCM.start();

//		MOD_PAGOS----------------------------------------------------------
		threadCP = new Thread(()-> {
			Queue<Turno> queueCP = new LinkedList<Turno>();
			ArrayList<Turno> trs = pt.TraerTodoslosTurnos();
			for (Turno t : trs ) {
				if ( t.getModulo().equals("Caja 3") ) {
					queueCP.add(t);
				}
			}

			while ( !queueCP.isEmpty() ) {
				lbCPCodigoTurno.setText(queueCP.element().getCodigo());
				queueCP.element().setEstado(true);
				//System.out.println(queueCP.element().toString());
				try {
					Thread.sleep(950);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int index = trs.indexOf(queueCP.remove());
				trs.get( index ).setEstado(true);
				lbCPEstadoCaja.setText( queueCP.isEmpty() ? "Disponible" : "Atendiendo...");
			}
			pt.SobreEscribirArchivoProducto(trs);
		});
		threadCP.start();

	}

	public void createComponents() {
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/co/edu/uptc/presentacion/digital-7.ttf")));
			String fuentes[] = ge.getAvailableFontFamilyNames();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fuenteDigital = new Font("Times New Roman",Font.PLAIN, 30); //--------------------------FUENTE
		
		pnValidacion = new JPanel();
		pnValidacion.setBorder(new TitledBorder("Validacion de usuario"));
		pnValidacion.setSize(new Dimension(50, 50));
		pnValidacion.setLayout( new GridBagLayout());
		
		pnZonaTurnos = new JPanel();
		pnZonaTurnos.setBorder(new TitledBorder("Generar Turnos"));
		pnZonaTurnos.setLayout( new GridBagLayout());
		
		pnEstadisticas = new JPanel();
		pnEstadisticas.setBorder(new TitledBorder("Estadisticas"));
		pnEstadisticas.setLayout( new GridBagLayout());
		
		pnEstModulo = new JPanel();
		pnEstModulo.setBorder(new TitledBorder("Estadisticas por Modulo"));
		pnEstModulo.setLayout( new GridBagLayout());
		
		pnEstServicios = new JPanel();
		pnEstServicios.setBorder(new TitledBorder("Estadisticas por Servicio"));
		pnEstServicios.setLayout( new GridBagLayout());
		

		
		
		pnVerficaUsuario = new JPanel();
		pnVerficaUsuario.setBorder(new TitledBorder("Verificar usuario"));
		pnVerficaUsuario.setLayout( new GridBagLayout());
		
		pnGenerarTurno = new JPanel();
		pnGenerarTurno.setBorder(new TitledBorder("Generar turno"));
		pnGenerarTurno.setLayout( new GridBagLayout());
		
		pnCajaCitas = new JPanel();
		pnCajaCitas.setBorder(new TitledBorder("Caja 1: Ordenar citas"));
		pnCajaCitas.setLayout( new GridBagLayout());
		
		pnCajaMedicamentos = new JPanel();
		pnCajaMedicamentos.setBorder(new TitledBorder("Caja 2: Medicamentos"));
		pnCajaMedicamentos.setLayout( new GridBagLayout());
		
		pnCajaPagos = new JPanel();
		pnCajaPagos.setBorder(new TitledBorder("Caja 3 : Pagos"));
		pnCajaPagos.setLayout( new GridBagLayout());
		
		
		pnCCTurno = new JPanel();
		pnCCTurno.setBorder(new TitledBorder("Turno Actual"));
		pnCCTurno.setBackground(Color.BLACK);
		pnCCTurno.setLayout( new GridBagLayout());
		
		pnCMTurno = new JPanel();
		pnCMTurno.setBorder(new TitledBorder("Turno Actual"));
		pnCMTurno.setBackground(Color.BLACK);
		pnCMTurno.setLayout( new GridBagLayout());
		
		pnCPTurno = new JPanel();
		pnCPTurno.setBorder(new TitledBorder("Turno Actual"));

		pnCPTurno.setBackground(Color.BLACK);
		pnCPTurno.setLayout( new GridBagLayout());
		
		
		lbCedula = new JLabel("Ingrese su numero de identificacion");;
		txtCedula = new JTextField(20);

		btnConsultar = new JButton("Ingresar");
		btnConsultar.setActionCommand(HandlingEvents.CONSULTAR);
		btnConsultar.addActionListener(new HandlingEvents(this));
		
		lbCBModulo = new JLabel("Seleccione a que modulo desea acceder");
		cbModulos= new JComboBox<String>();
		lbCBTipoTramite= new JLabel("Seleccione el tipo de tramite");////-----------AQUIIIIIIIIIII
		perTra= new PersistenciaTramite();
		cbTipoTramite = new JComboBox<String>();
		ArrayList<Tramite> tramites;

		if(perTra.TraerTodoslosTramites()!=null) {
			tramites=perTra.TraerTodoslosTramites();
			for (int i = 0; i < tramites.size(); i++) {
				cbTipoTramite.addItem(tramites.get(i).getNombre());
			}

		}
		
		btnGenerarTurno= new JButton("Generar Turno");
		btnGenerarTurno.setActionCommand(HandlingEvents.GENERAR_TURNO);
		btnGenerarTurno.addActionListener(new HandlingEvents(this));
		
		
		lbCCCodigoTurno = new JLabel("1XXX");
		lbCCCodigoTurno.setForeground(new Color(57,255,20));;
		lbCCCodigoTurno.setFont(fuenteDigital);
		lbCCEstadoCaja = new JLabel("xXXXXXXX");
		lbCCEstadoCaja.setForeground(Color.WHITE);;
		lbCCEstadoCaja.setFont(fuenteDigital);
		tCC = new JTable(dtmCC) ;
		dtmCC = new DefaultTableModel();
		
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
		chtpModulos.setPreferredSize(new Dimension(200,200));;

		chtpServicios = new ChartPanel(fcEstadisticaServicios);
		chtpServicios.setPreferredSize(new Dimension(200,200));;



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
		add(pnValidacion,g);

		g.gridx=1;
		g.gridy=0;
		g.gridheight=2;
		addComponentspnZonaTurnos();

		add(pnZonaTurnos,g);
		g.gridheight=1;
		g.gridy=2;
		g.fill = GridBagConstraints.NONE;
		g.fill=GridBagConstraints.HORIZONTAL;
		addComponentspnEstadisticas();
		add(pnEstadisticas,g);
		
	}
	
	
	
	public void addComponentspnValidacion() {
		GridBagConstraints g = new GridBagConstraints();
		g.insets = new Insets(10, 10, 10, 10);
		g.gridx=0;
		g.gridy=0;
		addComponentspnVerificarUsuario();
		pnValidacion.add(pnVerficaUsuario,g);
		g.gridy=1;
		addComponentspnGenerarTurno();
		g.fill = GridBagConstraints.HORIZONTAL;
		pnValidacion.add(pnGenerarTurno,g);
		
	}
	public void addComponentspnZonaTurnos() {
		GridBagConstraints g = new GridBagConstraints();
		g.gridx=0;
		g.gridy=0;
		g.weightx=1.0;
		addComponentspnCajaCitas();
		g.fill=GridBagConstraints.HORIZONTAL;
		pnZonaTurnos.add(pnCajaCitas,g);
		g.gridx=1;
		addComponentspnCajaMedicamentos();
		pnZonaTurnos.add(pnCajaMedicamentos,g);
		g.gridx=2;
		addComponentspnCajaPagos();
		pnZonaTurnos.add(pnCajaPagos,g);
		
	}
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
		pnVerficaUsuario.add(lbCedula,g);
		g.gridy=1;
		pnVerficaUsuario.add(txtCedula,g);
		g.gridy=2;
		pnVerficaUsuario.add(btnConsultar,g);

		
		
	}
	public void addComponentspnGenerarTurno() {
		GridBagConstraints g = new GridBagConstraints();
		g.gridx=0;
		g.gridy=0;
		g.insets = new Insets(10, 10, 10, 10);

		pnGenerarTurno.add(lbCBTipoTramite,g);
		g.gridy=1;
		pnGenerarTurno.add(cbTipoTramite,g);
//		g.gridy=2;
//		pnGenerarTurno.add(lbCBTipoTramite,g);
//		g.gridy=3;
//		pnGenerarTurno.add(cbTipoTramite,g);
		g.gridy=2;
		pnGenerarTurno.add(btnGenerarTurno,g);
		
	}
	public void addComponentspnCajaCitas() {
		GridBagConstraints g = new GridBagConstraints();
		g.gridx=0;
		g.gridy=0;
		g.weightx=1.0;
		g.fill=GridBagConstraints.HORIZONTAL;
		addComponentspnCCTurno();
		pnCajaCitas.add(pnCCTurno,g);
		g.gridy=1;
		pnCajaCitas.add(tCC);
		
	}
	public void addComponentspnCajaMedicamentos() {
		GridBagConstraints g = new GridBagConstraints();
		g.gridx=0;
		g.gridy=0;
		g.weightx=1.0;
		g.fill=GridBagConstraints.HORIZONTAL;
		addComponentspnCMTurno();
		pnCajaMedicamentos.add(pnCMTurno,g);
		g.gridy=1;
		pnCajaMedicamentos.add(tCM);
		
	}
	public void addComponentspnCajaPagos() {
		GridBagConstraints g = new GridBagConstraints();
		g.gridx=0;
		g.gridy=0;
		g.weightx=1.0;
		g.fill=GridBagConstraints.HORIZONTAL;
		addComponentspnCPTurno();
		pnCajaPagos.add(pnCPTurno,g);
		g.gridy=1;
		pnCajaPagos.add(tCP);
		
	}
	
	public void addComponentspnCCTurno() {
		GridBagConstraints g = new GridBagConstraints();
		g.gridx=0;
		g.gridy=0;
		pnCCTurno.add(lbCCCodigoTurno,g);
		g.gridy=1;
		pnCCTurno.add(lbCCEstadoCaja,g);
		
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
		return txtCedula;
	}

	public void setTxtCedula(JTextField txtCedula) {
		this.txtCedula = txtCedula;
	}

	public JComboBox<String> getCbTipoTramite() {
		return cbTipoTramite;
	}

	public void setCbTipoTramite(JComboBox<String> cbTipoTramite) {
		this.cbTipoTramite = cbTipoTramite;
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
