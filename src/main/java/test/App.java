package test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import co.edu.uptc.logica.control.TurnsManagement;
import co.edu.uptc.logica.modelo.Afiliado;
import co.edu.uptc.logica.modelo.Modulo;
import co.edu.uptc.logica.modelo.Tramite;
import co.edu.uptc.logica.modelo.Turno;
import co.edu.uptc.persistencia.PersistenciaAfiliados;
import co.edu.uptc.persistencia.PersistenciaModulos;
import co.edu.uptc.persistencia.PersistenciaTramite;
import co.edu.uptc.persistencia.PersistenciaTurnos;
import co.edu.uptc.presentacion.MainWindow;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//    	ArrayList<Tramite> tramites1= new ArrayList<>();
//    	ArrayList<Tramite> tramites2= new ArrayList<>();
//    	ArrayList<Tramite> tramites3= new ArrayList<>();
//
//		
//		tramites1.add(new Tramite("S11", "Cita Odontologia"));
//		tramites1.add(new Tramite("S12", "Cita Pediatria"));
//		tramites1.add(new Tramite("S13", "Cita Oftalmologia"));
//		tramites1.add(new Tramite("S14", "Cita Medicina General"));
//		tramites1.add(new Tramite("S15", "Cita Optometria"));
//		tramites1.add(new Tramite("S16", "Cita Laboratorio"));
//		tramites1.add(new Tramite("S17", "Solicitar Afiliacion"));
//		
//		tramites2.add(new Tramite("S21", "Reclamar medicamentos"));
//		tramites2.add(new Tramite("S22", "Aplicar inyeccion"));
//		
//		tramites3.add(new Tramite("S31", "Pagar cita"));
//		tramites3.add(new Tramite("S31", "Pagar medicamentos"));
//		tramites3.add(new Tramite("S31", "Pagar afiliacion"));
//		
//    	PersistenciaModulos modul = new PersistenciaModulos();
//    	modul.agregarUnNuevoModulo(new Modulo("C1", "Caja 1", tramites1));
//    	modul.agregarUnNuevoModulo(new Modulo("C2", "Caja 2", tramites2));
//    	modul.agregarUnNuevoModulo(new Modulo("C3", "Caja 3", tramites3));
    	
		MainWindow myWindow= new MainWindow();
		myWindow.begin();
		myWindow.setVisible(true);
		
		

		
		
		


		
		
//		PersistenciaTurnos pt = new PersistenciaTurnos();
//		ArrayList<Turno>turnos = pt.TraerTodoslosTurnos();
//		ArrayList<Turno> lFilter1= new ArrayList<>();
//		ArrayList<Turno> lFilter2= new ArrayList<>();
//		ArrayList<Turno> lFilter3= new ArrayList<>();
//		turnos.stream().filter(element -> element.getModulo().equals("Caja 1") ).map(element->lFilter1.add(element)).forEach(System.out::println);
//		turnos.stream().filter(element -> element.getModulo().equals("Caja 2") ).map(element->lFilter2.add(element)).forEach(System.out::println);
//		turnos.stream().filter(element -> element.getModulo().equals("Caja 3") ).map(element->lFilter3.add(element)).forEach(System.out::println);
		
    	    
    }
}
