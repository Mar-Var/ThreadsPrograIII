package co.edu.uptc.persistencia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.uptc.logica.modelo.Afiliado;

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
	
	@SuppressWarnings("unchecked")
	public synchronized boolean SobreEscribirArchivoProducto(ArrayList<Afiliado> contenido){
			
			try {
				fileExist();
			} catch (IOException e1) {
				System.out.println("hay un problema en PersistenciaTurnos FileExist()");
			}
			JSONArray content = new JSONArray();
			
			for (int i = 0; i < contenido.size(); i++) {
				JSONObject ob= new JSONObject();
				ob.put("nombre", contenido.get(i).getNombre());
				ob.put("cedula", contenido.get(i).getCedula());

				content.add(ob);
			}
			try {
				 Files.write(file.toPath(), content.toJSONString().getBytes());
			    return true;
			} catch (Exception e) {
				return false;
			}
	
		}
		
	
	@SuppressWarnings("unchecked")
	public synchronized boolean agregarUnNuevoAfiliado(Afiliado afiliadoAgregar) {
		try {
			fileExist();
		} catch (IOException e1) {
		
		}
		try {
			JSONArray content = new JSONArray();
			afiliados =TraerTodoslosAfiliados();

			if(afiliados!=null) {
				for (int i = 0; i < afiliados.size(); i++) {
					JSONObject ob= new JSONObject();
					ob.put("nombre", afiliados.get(i).getNombre());
					ob.put("cedula", afiliados.get(i).getCedula());

					content.add(ob);
				}
			}
			JSONObject nuevoTurno= new JSONObject();
			nuevoTurno.put("nombre", afiliadoAgregar.getNombre());
			nuevoTurno.put("cedula", afiliadoAgregar.getCedula());

			content.add(nuevoTurno);
			Files.write(Paths.get(ruta), content.toJSONString().getBytes());
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public synchronized ArrayList<Afiliado> TraerTodoslosAfiliados(){
		ObjectMapper mapper = new ObjectMapper();
		afiliados= new ArrayList();
		try {
			afiliados= mapper.readValue(new File(ruta),
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, Afiliado.class));

			return afiliados;
		} 
		catch (FileNotFoundException e) {
			try {
				fileExist();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return afiliados;
		}
		
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
}
