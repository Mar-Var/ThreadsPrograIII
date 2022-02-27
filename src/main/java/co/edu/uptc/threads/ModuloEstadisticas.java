package co.edu.uptc.threads;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Label;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import co.edu.uptc.logica.modelo.Turno;
import co.edu.uptc.persistencia.PersistenciaModulos;
import co.edu.uptc.persistencia.PersistenciaTramite;
import co.edu.uptc.persistencia.PersistenciaTurnos;

public class ModuloEstadisticas implements Runnable {
	private ArrayList<Turno> turnos;
	ChartPanel crtpM;
	ChartPanel crtpS;
	JPanel panelEstadisticas;
	DefaultPieDataset datos;
	JFreeChart grafico_circular;
	ChartPanel modulos,servicios;
	private PersistenciaTurnos pt;
	private PersistenciaModulos pm;
	private PersistenciaTramite ptr;
	public ModuloEstadisticas(JPanel panelEstadisticas,ChartPanel modulos,ChartPanel servicios) {
		// TODO Auto-generated constructor stub
		this.modulos=modulos;
		this.servicios=servicios;
		this.pt= new PersistenciaTurnos();
		this.panelEstadisticas= panelEstadisticas;

	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		LocalTime time = null;
		int i = 0;
		while(true) {
			this.pt= new PersistenciaTurnos();
			if(i!=0) {
				panelEstadisticas.remove(crtpM);
				panelEstadisticas.remove(crtpS);
			}
			removeComponents();
			crtpM = new ChartPanel(grafico_circularModulos());
			crtpM.setPreferredSize(new Dimension(400, 200));
			crtpS= new ChartPanel(grafico_circularModulos());
			crtpS.setPreferredSize(new Dimension(400, 200));
			añadirComponents();
			panelEstadisticas.updateUI();
			try {
				Thread.sleep(3000);
			}catch (Exception e) {
				// TODO: handle exception
			}
			i++;
			
		}
		
//		addComponentspnEstadisticas();
//		System.out.println(i++);

		

	}
	public void removeComponents () {
		panelEstadisticas.remove(modulos);
		panelEstadisticas.remove(servicios);
		
	}
	public void añadirComponents () {
		GridBagConstraints g = new GridBagConstraints();
		panelEstadisticas.add(crtpM);
		panelEstadisticas.add(crtpS);	
	}
	
//	public void addComponentspnEstadisticas() {
//		
//		g.gridx=0;
//		g.gridy=0;
//		g.insets= new Insets(10, 10, 10, 10);
//		panelEstadisticas.add(crtpM,g);
//		g.gridx=1;
//		panelEstadisticas.add(new Label("Esto es inservible"),g);
//		
//	}
	
	public ArrayList<Turno> filtradoModulos (String filtrado){
		ArrayList<Turno> lFilter1= new ArrayList<>();
		try {
			turnos.stream().filter(element -> element.getModulo().equals(filtrado) && element.isEstado()==true ).map(element->lFilter1.add(element)).forEach(element->System.out.println("1"));
			return lFilter1;
		}catch (NullPointerException e) {
			// TODO: handle exception
			return null;
		}
		
		
	}
	public JFreeChart grafico_circularModulos() {
		this.turnos = pt.TraerTodoslosTurnos();
		
		int m1=0,m2=0,m3=0;
		try {
			m1=filtradoModulos("Caja 1").size();
		} catch (Exception e) {
			System.out.println("Problemas Caja 1");
			m2=0;
			
		}
		try {
			m2=filtradoModulos("Caja 2").size();
		} catch (Exception e) {
			System.out.println("Problemas Caja 2");
			m2=0;
		}
		try {
			m3=filtradoModulos("Caja 3").size();
		} catch (Exception e) {
			System.out.println("Problemas Caja 3");
			m3=0;
		}

		datos = new DefaultPieDataset();
		datos.setValue("Caja 1", m1);
		datos.setValue("Caja 2", m2);
		datos.setValue("Caja 3", m3);
		grafico_circular = ChartFactory.createPieChart(
				"Estadistica por Modulo",
				datos,
				true,
				true,
				false);
		return grafico_circular;
		
	}
	
	
	
	public JFreeChart grafico_Circular_Servicios() {
		
		
		return null;
	}

}
