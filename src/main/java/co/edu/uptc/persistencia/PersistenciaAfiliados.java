package co.edu.uptc.persistencia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	
	
	public ArrayList<Afiliado> TraerTodoslosTurnos(){
		ObjectMapper mapper = new ObjectMapper();
		afiliados= new ArrayList();
		try {
			afiliados= mapper.readValue(new File(ruta),
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, Afiliado.class));

			return afiliados;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
}
