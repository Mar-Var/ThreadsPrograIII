package co.edu.uptc.logica.modelo;

import java.util.ArrayList;

public class Modulo {
	private String codigo;
	private String nombre;
	private ArrayList<Tramite> tramites;
	
	public Modulo() {

	}
	public Modulo(String codigo, String nombre, ArrayList<Tramite> tramites) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.tramites = tramites;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<Tramite> getTramites() {
		return tramites;
	}
	public void setTramites(ArrayList<Tramite> tramites) {
		this.tramites = tramites;
	}
	@Override
	public String toString() {
		return "Modulo [codigo=" + codigo + ", nombre=" + nombre + ", tramites=" + tramites + "]";
	}
	
	

}
