package co.edu.uptc.presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import co.edu.uptc.logica.modelo.Afiliado;
import co.edu.uptc.logica.modelo.Modulo;
import co.edu.uptc.logica.modelo.Tramite;
import co.edu.uptc.logica.modelo.Turno;
import co.edu.uptc.persistencia.PersistenciaAfiliados;
import co.edu.uptc.persistencia.PersistenciaModulos;
import co.edu.uptc.persistencia.PersistenciaTramite;
import co.edu.uptc.persistencia.PersistenciaTurnos;

public class HandlingEvents implements ActionListener {
	
	private MainWindow mainWindow;
	private PersistenciaTramite pt = new PersistenciaTramite();
	private PersistenciaModulos pm = new PersistenciaModulos();
	private PersistenciaAfiliados pa = new PersistenciaAfiliados();
	private PersistenciaTurnos ptu = new PersistenciaTurnos();
	ArrayList<Modulo> modulos;
	ArrayList<Tramite> tramites;
	ArrayList<Afiliado> afiliados;
	
	static final String CONSULTAR = "Consultar";
	static final String GENERAR_TURNO = "Generar turno";
	
	
	public HandlingEvents(MainWindow mainWindow) {
		this.mainWindow = mainWindow;

	}
	
	public Afiliado buscarAfiliado() {

		return pa
				.TraerTodoslosAfiliados()
				.stream()
				.filter(element->element.getCedula()==Long.parseLong(mainWindow.getTxtCedula().getText()))
				.map(element->element)
				.findFirst()
				.orElse(null);
	}
	
	public String encontrarModulo() {
		for(Modulo modulo:pm.TraerTodoslosModulos()) {
			for(Tramite tramite : modulo.getTramites()) {
				if(tramite.getNombre().equals(mainWindow.getCbTipoTramite().getSelectedItem().toString())) {
					return modulo.getNombre();
				}
			}
		}
		return null;
	}
	public Modulo encontrarObModulo() {
		try {
			for(Modulo modulo:pm.TraerTodoslosModulos()) {
				for(Tramite tramite : modulo.getTramites()) {
					if(tramite.getNombre().equals(mainWindow.getCbTipoTramite().getSelectedItem().toString())) {
						return modulo;
					}
				}
			}
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "El archivo modulos.json no encontrado");
		}

		return null;
	}
	
	public Tramite encontrarObTramite() {
		try {
			for(Tramite tramite : pt.TraerTodoslosTramites()) {
				if(tramite.getNombre().equals(mainWindow.getCbTipoTramite().getSelectedItem().toString())) {
					return tramite;
				}
			}
			
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "El archivo tramites.json no encontrado");
			
		}
		return null;
		
		
	}
	
	
	
	public Turno generarTurno() {
		String servicio = mainWindow.getCbTipoTramite().getSelectedItem().toString();
		
		String verifi = encontrarObModulo().getCodigo().toString();
		String codigo= encontrarObModulo().getCodigo()+
				"-"+encontrarObTramite().getCodigo()+
				"-"+"0"+"-"+(ptu.TraerTodoslosTurnos()!=null?ptu.TraerTodoslosTurnos().size():0);
		Turno turno = new Turno(buscarAfiliado(), encontrarModulo(),servicio, codigo, new Date().toString(), 0, false);
		ptu.agregarUnNuevoTurno(turno);
		mainWindow.getLblDarTurnoValidacion().setText(codigo);
		
		return null;
	}
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
		case CONSULTAR:
			try {
				afiliados=pa.TraerTodoslosAfiliados();
			} catch (Exception e2) {
			}
			
			try {
				int cont =0;
				if(afiliados!=null) {
					for (Afiliado afiliado : afiliados) {
						if(afiliado.getCedula()==Long.parseLong(mainWindow.getTxtCedula().getText())) {
							JOptionPane.showMessageDialog(null, "Bienvenido");
							mainWindow.getBtnConsultar().setVisible(false);
							mainWindow.getBtnGenerarTurno().setVisible(true);
							mainWindow.getTxtCedula().setEditable(false);
							cont=0;
							break;
						}
						cont=1;
						
					}
					
					if(cont==1) {
						mainWindow.getBtnGenerarTurno().setVisible(false);
						JOptionPane.showMessageDialog(null, "El usuario no esta dentro de los registros");
					}
				}
				
			} catch (NumberFormatException e2) {
				JOptionPane.showMessageDialog(null, "Debe ingresar por lo menos un digito");
				mainWindow.getBtnGenerarTurno().disable();
				
			} catch (Exception e2) {
			}


			
			break;
		case GENERAR_TURNO:
			generarTurno();
			mainWindow.getBtnGenerarTurno().setVisible(false);
			mainWindow.getBtnConsultar().setVisible(true);
			mainWindow.getTxtCedula().setText("");
			mainWindow.getTxtCedula().setEditable(true);
			try {
				
				
			} catch (NullPointerException e2) {
				JOptionPane.showMessageDialog(null, "Archivos raiz no encontrados");
			}

			break;
		default:
			break;
		}
		
	}
	
	

}
