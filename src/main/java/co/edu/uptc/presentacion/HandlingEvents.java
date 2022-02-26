package co.edu.uptc.presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import co.edu.uptc.logica.modelo.Modulo;
import co.edu.uptc.logica.modelo.Tramite;
import co.edu.uptc.persistencia.PersistenciaModulos;
import co.edu.uptc.persistencia.PersistenciaTramite;

public class HandlingEvents implements ActionListener {
	
	private MainWindow mainWindow;
	private PersistenciaTramite pt;
	private PersistenciaModulos pm;
	ArrayList<Modulo> modulos=new ArrayList<Modulo>();
	ArrayList<Tramite> tramites=new ArrayList<Tramite>();
	
	
	public HandlingEvents(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
