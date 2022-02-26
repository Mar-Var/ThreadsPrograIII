package test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import co.edu.uptc.logica.modelo.Afiliado;
import co.edu.uptc.logica.modelo.Turno;
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
//		MainWindow myWindow= new MainWindow();
//		myWindow.begin();
//		myWindow.setVisible(true);
    	
		PersistenciaTurnos pt = new PersistenciaTurnos();
		try {
			System.out.println(pt.fileExist());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Turno t = new Turno(new Afiliado("Marcos", 1052416590), "Caja1", "nada", "A0111", new Date().toString(),1,true);
		ArrayList<Turno> turnos = new ArrayList();
		turnos.add(t);
		turnos.add(t);
		turnos.add(t);
		turnos.add(t);
		pt.SobreEscribirArchivoProducto(turnos);
		pt.TraerTodoslosTurnos().forEach(System.out::println);
		
		Turno a = new Turno(new Afiliado("Yasser", 1041416590), "Caja1", "nada", "A0111", new Date().toString(), 0,false);
		System.out.println("-----------------------------------------------");
		pt.agregarUnNuevoDomiciliario(a);
		pt.agregarUnNuevoDomiciliario(a);
		pt.agregarUnNuevoDomiciliario(a);
		
		pt.TraerTodoslosTurnos().forEach(System.out::println);
		
		
    
    
    }
}
