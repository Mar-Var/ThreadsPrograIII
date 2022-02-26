package test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import co.edu.uptc.logica.modelo.Afiliado;
import co.edu.uptc.logica.modelo.Tramite;
import co.edu.uptc.logica.modelo.Turno;
import co.edu.uptc.persistencia.PersistenciaAfiliados;
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
    	
    	PersistenciaTramite ptramite = new PersistenciaTramite();
		
		ptramite.agregarUnNuevoTramite(new Tramite("S11", "Cita Odontologia"));
		ptramite.agregarUnNuevoTramite(new Tramite("S12", "Cita Pediatria"));
		ptramite.agregarUnNuevoTramite(new Tramite("S13", "Cita Oftalmologia"));
		ptramite.agregarUnNuevoTramite(new Tramite("S14", "Cita Medicina General"));
		ptramite.agregarUnNuevoTramite(new Tramite("S15", "Cita Optometria"));
		ptramite.agregarUnNuevoTramite(new Tramite("S16", "Cita Laboratorio"));
		ptramite.agregarUnNuevoTramite(new Tramite("S17", "Solicitar Afiliacion"));
		
		ptramite.agregarUnNuevoTramite(new Tramite("S21", "Reclamar medicamentos"));
		ptramite.agregarUnNuevoTramite(new Tramite("S22", "Aplicar inyeccion"));
		
		ptramite.agregarUnNuevoTramite(new Tramite("S31", "Pagar cita"));
		ptramite.agregarUnNuevoTramite(new Tramite("S31", "Pagar medicamentos"));
		ptramite.agregarUnNuevoTramite(new Tramite("S31", "Pagar afiliacion"));
		
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
