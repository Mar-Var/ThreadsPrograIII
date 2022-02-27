package co.edu.uptc.presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
						System.out.println("Encontrado---");
						return modulo;
					}
				}
			System.out.println(modulo);
			}
		} catch (NullPointerException e) {
			// TODO: handle exception
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
		
		String verifi = encontrarObModulo().toString();
		System.out.println(verifi);
		String codigo= encontrarObModulo().getCodigo()+
				"-"+encontrarObTramite().getCodigo()+
				"-"+"0"+"-"+(ptu.TraerTodoslosTurnos()!=null?ptu.TraerTodoslosTurnos().size():0);
		System.out.println(codigo);
		Turno turno = new Turno(buscarAfiliado(), encontrarModulo(), encontrarObTramite().getCodigo(), servicio,new Date().toString(), 0, false);
		ptu.agregarUnNuevoTurno(turno);
		
		return null;
	}
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		switch (e.getActionCommand()) {
		case CONSULTAR:
			try {
				afiliados=pa.TraerTodoslosAfiliados();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
			
			try {
				int cont =0;
				if(afiliados!=null) {
					for (Afiliado afiliado : afiliados) {
						if(afiliado.getCedula()==Long.parseLong(mainWindow.getTxtCedula().getText())) {
							JOptionPane.showMessageDialog(null, "Bienvenido");
							mainWindow.getBtnConsultar().setEnabled(false);
							mainWindow.getBtnGenerarTurno().setEnabled(true);
							cont=0;
							break;
						}
						cont=1;
						
					}
					
					if(cont==1) {
						mainWindow.getBtnGenerarTurno().setEnabled(false);
						JOptionPane.showMessageDialog(null, "El usuario no esta dentro de los registros");
					}
				}
				
			} catch (NumberFormatException e2) {
				JOptionPane.showMessageDialog(null, "Debe ingresar por lo menos un dijito");
				mainWindow.getBtnGenerarTurno().disable();
				
				// TODO: handle exception
			} catch (Exception e2) {
				// TODO: handle exception
			}


			
			break;
		case GENERAR_TURNO:
			generarTurno();
			try {
				
				
			} catch (NullPointerException e2) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Archivos raiz no encontrados");
			}

			break;
		default:
			break;
		}
		
	}
	
	

}
