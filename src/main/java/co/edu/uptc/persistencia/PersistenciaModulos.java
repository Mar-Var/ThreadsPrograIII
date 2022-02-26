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

import co.edu.uptc.logica.modelo.Modulo;
import co.edu.uptc.logica.modelo.Tramite;

public class PersistenciaModulos {
	
	private String ruta="archivosJson/modulos.json";
	File file = new File(ruta);
	ArrayList<Modulo> modulos;
	
	
	public boolean fileExist() throws IOException {
		if(!file.exists()) {
			file.createNewFile();
			return false;
		}
		return true;
	}
	
	public boolean SobreEscribirArchivoModulo(ArrayList<Modulo> contenido){
			
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
				
				JSONArray contentTramites = new JSONArray();
				
				for (int j = 0; j < contenido.get(i).getTramites().size(); j++) {
					JSONObject tr= new JSONObject();
					tr.put("codigo", contenido.get(i).getTramites().get(j).getCodigo());
					tr.put("nombre", contenido.get(i).getTramites().get(j).getNombre());
					contentTramites.add(tr);
					
				}
				ob.put("tramites", contentTramites);
				

				content.add(ob);
			}
			try {
				 Files.write(file.toPath(), content.toJSONString().getBytes());
			    return true;
			} catch (Exception e) {
				return false;
			}
	
		}
		
	
	public boolean agregarUnNuevoModulo(Modulo moduloAgregar) {
		try {
			fileExist();
		} catch (IOException e1) {
		
		}
		try {
			JSONArray content = new JSONArray();
			ObjectMapper mapper = new ObjectMapper();
			modulos =TraerTodoslosModulos();

			if(modulos!=null) {
				for (int i = 0; i < modulos.size(); i++) {
					JSONObject ob= new JSONObject();
					ob.put("codigo", modulos.get(i).getCodigo());
					ob.put("nombre", modulos.get(i).getNombre());
					
					
					JSONArray contentTramites = new JSONArray();
					
					for (int j = 0; j < modulos.get(i).getTramites().size(); j++) {
						JSONObject tr= new JSONObject();
						tr.put("codigo", modulos.get(i).getTramites().get(j).getCodigo());
						tr.put("nombre", modulos.get(i).getTramites().get(j).getNombre());
						contentTramites.add(tr);
						
					}
					ob.put("tramites", contentTramites);
					

					content.add(ob);
				}
			}
			JSONObject nuevoModulo= new JSONObject();
			nuevoModulo.put("codigo", moduloAgregar.getCodigo());
			nuevoModulo.put("nombre", moduloAgregar.getNombre());
			
			
			JSONArray nuevoContentTramites = new JSONArray();
			
			for (int j = 0; j < moduloAgregar.getTramites().size(); j++) {
				JSONObject nuevotr= new JSONObject();
				nuevotr.put("codigo", moduloAgregar.getTramites().get(j).getCodigo());
				nuevotr.put("nombre", moduloAgregar.getTramites().get(j).getNombre());
				nuevoContentTramites.add(nuevotr);
				
			}
			
			nuevoModulo.put("tramites", nuevoContentTramites);
			
			

			content.add(nuevoModulo);
			Files.write(Paths.get(ruta), content.toJSONString().getBytes());
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}	
	
	public ArrayList<Modulo> TraerTodoslosModulos(){
		ObjectMapper mapper = new ObjectMapper();
		modulos= new ArrayList();
		try {
			modulos= mapper.readValue(new File(ruta),
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, Modulo.class));

			return modulos;
		} 
		catch (FileNotFoundException e) {
			try {
				fileExist();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return modulos;
		}
		
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
