package co.edu.uptc.persistencia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.uptc.logica.modelo.Afiliado;
import co.edu.uptc.logica.modelo.Turno;

public class PersistenciaTurnos {
	private String ruta="archivosJson/turnos.json";
	File file = new File(ruta);
	ArrayList<Turno> turnos;
	
	public boolean fileExist() throws IOException {
		if(!file.exists()) {
			file.createNewFile();
			return false;
		}
		
		return true;
	}
	
	public boolean SobreEscribirArchivoProducto(ArrayList<Turno> contenido){
		
		try {
			fileExist();
		} catch (IOException e1) {
			System.out.println("hay un problema en PersistenciaTurnos FileExist()");
		}
		JSONArray content = new JSONArray();
		
		for (int i = 0; i < contenido.size(); i++) {
			JSONObject ob= new JSONObject();
			
			ob.put("nivel", contenido.get(i).getNivel());
			ob.put("fecha", contenido.get(i).getFecha());
			ob.put("codigo", contenido.get(i).getCodigo());
			ob.put("servicio", contenido.get(i).getServicio());
			ob.put("modulo", contenido.get(i).getModulo());
			
			JSONObject afiliado = new JSONObject();
			afiliado.put("nombre", contenido.get(i).getAfiliado().getNombre());
			afiliado.put("cedula", contenido.get(i).getAfiliado().getCedula());
			ob.put("afiliado",afiliado);
			
			content.add(ob);
		}
		try {
			 Files.write(file.toPath(), content.toJSONString().getBytes());
		    return true;
		} catch (Exception e) {
			return false;
		}

	}
	
	public boolean agregarUnNuevoDomiciliario(Turno TurnoAgregar) {
		try {
			fileExist();
		} catch (IOException e1) {
		
		}
		try {
			JSONArray content = new JSONArray();
			ObjectMapper mapper = new ObjectMapper();
			turnos =TraerTodoslosTurnos();

			if(turnos!=null) {
				for (int i = 0; i < turnos.size(); i++) {
					JSONObject ob= new JSONObject();
					
					ob.put("nivel", turnos.get(i).getNivel());
					ob.put("fecha", turnos.get(i).getFecha());
					ob.put("codigo", turnos.get(i).getCodigo());
					ob.put("servicio", turnos.get(i).getServicio());
					ob.put("modulo", turnos.get(i).getModulo());
					
					JSONObject afiliado = new JSONObject();
					afiliado.put("nombre", turnos.get(i).getAfiliado().getNombre());
					afiliado.put("cedula", turnos.get(i).getAfiliado().getCedula());
					ob.put("afiliado",afiliado);

					content.add(ob);
				}
			}
			JSONObject nuevoTurno= new JSONObject();
			nuevoTurno.put("nivel", TurnoAgregar.getNivel());
			nuevoTurno.put("fecha", TurnoAgregar.getFecha());
			nuevoTurno.put("codigo", TurnoAgregar.getCodigo());
			nuevoTurno.put("servicio", TurnoAgregar.getServicio());
			nuevoTurno.put("modulo", TurnoAgregar.getModulo());
			
			JSONObject afiliado = new JSONObject();
			afiliado.put("nombre", TurnoAgregar.getAfiliado().getNombre());
			afiliado.put("cedula", TurnoAgregar.getAfiliado().getCedula());
			nuevoTurno.put("afiliado",afiliado);

			content.add(nuevoTurno);
			Files.write(Paths.get(ruta), content.toJSONString().getBytes());
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public ArrayList<Turno> TraerTodoslosTurnos(){
		ObjectMapper mapper = new ObjectMapper();
		turnos= new ArrayList();
		try {
			turnos= mapper.readValue(new File(ruta),
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, Turno.class));

			return turnos;
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
	}
	

	
	

}
