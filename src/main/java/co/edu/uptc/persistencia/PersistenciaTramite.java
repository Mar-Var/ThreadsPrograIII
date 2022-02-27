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
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import co.edu.uptc.logica.modelo.Tramite;

public class PersistenciaTramite {
	private String ruta="archivosJson/tramites.json";
	File file = new File(ruta);
	ArrayList<Tramite> tramites;
	
	
	public boolean fileExist() throws IOException {
		if(!file.exists()) {
			file.createNewFile();
			return false;
		}
		return true;
	}
	
	public boolean SobreEscribirArchivoTramite(ArrayList<Tramite> contenido){
			
			try {
				fileExist();
			} catch (IOException e1) {
				System.out.println("hay un problema en PersistenciaTurnos FileExist()");
			}
			JSONArray content = new JSONArray();
			
			for (int i = 0; i < contenido.size(); i++) {
				JSONObject ob= new JSONObject();
				ob.put("codigo", contenido.get(i).getCodigo());
				ob.put("nombre", contenido.get(i).getNombre());

				content.add(ob);
			}
			try {
				 Files.write(file.toPath(), content.toJSONString().getBytes());
			    return true;
			} catch (Exception e) {
				return false;
			}
	
		}
		
	
	public boolean agregarUnNuevoTramite(Tramite tramiteAgregar) {
		try {
			fileExist();
		} catch (IOException e1) {
		
		}
		try {
			JSONArray content = new JSONArray();
			ObjectMapper mapper = new ObjectMapper();
			tramites =TraerTodoslosTramites();

			if(tramites!=null) {
				for (int i = 0; i < tramites.size(); i++) {
					JSONObject ob= new JSONObject();
					ob.put("codigo", tramites.get(i).getCodigo());
					ob.put("nombre", tramites.get(i).getNombre());

					content.add(ob);
				}
			}
			JSONObject nuevoTramite= new JSONObject();
			nuevoTramite.put("codigo", tramiteAgregar.getCodigo());
			nuevoTramite.put("nombre", tramiteAgregar.getNombre());

			content.add(nuevoTramite);
			Files.write(Paths.get(ruta), content.toJSONString().getBytes());
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}	
	
	public ArrayList<Tramite> TraerTodoslosTramites(){
		ObjectMapper mapper = new ObjectMapper();
		tramites= new ArrayList();
		try {
			tramites= mapper.readValue(new File(ruta),
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, Tramite.class));

			return tramites;
		} 
		catch (FileNotFoundException e) {
			try {
				fileExist();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return tramites;
		}
		catch (MismatchedInputException e) {
			e.printStackTrace();
			System.out.println("Estaba vacio: Ahora no");
			return null;
		}
		
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Presistencia Tramite");
			return null;
		}
	}
	

}
