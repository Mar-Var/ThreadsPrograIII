package co.edu.uptc.persistencia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import co.edu.uptc.logica.modelo.Afiliado;
import co.edu.uptc.logica.modelo.Turno;

public class PersistenciaAfiliados {
	
	private String ruta="archivosJson/afiliados.json";
	File file = new File(ruta);
	ArrayList<Afiliado> afiliados;
	
	
	public boolean fileExist() throws IOException {
		if(!file.exists()) {
			file.createNewFile();
			return false;
		}
		return true;
	}
	
	
}
