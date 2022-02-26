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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

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
	
	
	JFreeChart fcEstadisticaModulos;
	JFreeChart fcEstadisticaServicios;
	
	
	public MainWindow() {
		super("Super EPS");
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);// Decir que pasa cuando se cierre la ventana
		setMinimumSize(new Dimension(1000,700));
		setLayout(new GridBagLayout());
		setResizable(true);
	}
	
	public void begin() {
		createComponents();
		addComponents();
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
		
		lbCBModulo = new JLabel("Seleccione a que modulo desea acceder");
		cbModulos= new JComboBox<String>();
		lbCBTipoTramite= new JLabel("Seleccione el tipo de tramite");
		cbTipoTramite = new JComboBox<String>();
		btnGenerarTurno= new JButton("Generar Turno");
		
		lbCCCodigoTurno = new JLabel("1XXX");
		lbCCCodigoTurno.setForeground(new Color(57,255,20));;
		lbCCCodigoTurno.setFont(fuenteDigital);
		lbCCEstadoCaja = new JLabel("xXXXXXXX");
		tCC = new JTable(dtmCC) ;
		dtmCC = new DefaultTableModel();
		
		lbCMCodigoTurno = new JLabel("XXXX");
		lbCMCodigoTurno.setFont(fuenteDigital);
		lbCMEstadoCaja = new JLabel("XXXXXXXX");
		tCM = new JTable(dtmCM);
		dtmCM = new DefaultTableModel();
		
		lbCPCodigoTurno = new JLabel("XXXX");
		lbCPCodigoTurno.setFont(fuenteDigital);
		lbCPEstadoCaja = new JLabel("XXXXXXXX");
		tCP = new JTable(dtmCP);
		dtmCP = new DefaultTableModel();
		
//		Dejemoslo pendiente
		
		fcEstadisticaModulos = ChartFactory.createPieChart(
				"Estadisticas por Modulos",//Nombre del grafico
				new DefaultPieDataset(),// datos
				true, // nombre de las categorias f o v
				true, // herramientas f o t
				true); // generacion de URL false
			
		fcEstadisticaServicios = ChartFactory.createPieChart(
				"Estadisticas por Modulos",//Nombre del grafico
				new DefaultPieDataset(),// datos
				true, // nombre de las categorias f o v
				true, // herramientas f o t
				true); // generacion de URL false);
		
	}
	
	
	public void addComponents() {
		GridBagConstraints g = new GridBagConstraints();
		g.insets = new Insets(10, 10, 10, 10);
		g.gridx=0;
		g.gridy=0;
		g.gridheight=3;
		addComponentspnValidacion();
		add(pnValidacion,g);
		g.gridx=1;
		g.gridy=0;
		g.gridheight=2;
		addComponentspnZonaTurnos();
		add(pnZonaTurnos,g);
		g.gridheight=1;
		g.gridy=2;
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
		pnValidacion.add(pnGenerarTurno,g);
		
	}
	public void addComponentspnZonaTurnos() {
		GridBagConstraints g = new GridBagConstraints();
		g.gridx=0;
		g.gridy=0;
		addComponentspnCajaCitas();
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
		pnEstadisticas.add(pnEstModulo,g);
		g.gridx=1;
		pnEstadisticas.add(pnEstServicios,g);
		
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
		pnGenerarTurno.add(lbCBModulo,g);
		g.gridy=1;
		pnGenerarTurno.add(cbModulos,g);
		g.gridy=2;
		pnGenerarTurno.add(lbCBTipoTramite,g);
		g.gridy=3;
		pnGenerarTurno.add(cbTipoTramite,g);
		
	}
	public void addComponentspnCajaCitas() {
		GridBagConstraints g = new GridBagConstraints();
		g.gridx=0;
		g.gridy=0;
		addComponentspnCCTurno();
		pnCajaCitas.add(pnCCTurno,g);
		g.gridy=1;
		pnCajaCitas.add(tCC);
		
	}
	public void addComponentspnCajaMedicamentos() {
		GridBagConstraints g = new GridBagConstraints();
		g.gridx=0;
		g.gridy=0;
		addComponentspnCMTurno();
		pnCajaMedicamentos.add(pnCMTurno,g);
		g.gridy=1;
		pnCajaMedicamentos.add(tCM);
		
	}
	public void addComponentspnCajaPagos() {
		GridBagConstraints g = new GridBagConstraints();
		g.gridx=0;
		g.gridy=0;
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
	
	
	public void addComponentspnEstModulo() {
		GridBagConstraints g = new GridBagConstraints();
		g.gridx=0;
		g.gridy=0;
		
	}
	public void addComponentspnEstServicios() {
		GridBagConstraints g = new GridBagConstraints();
		
	}

}
