package co.edu.uptc.logica.modelo;

public class Afiliado {
	
	private String nombre ;
	private long cedula;
	public Afiliado() {
		
	}
	public Afiliado(String nombre, long cedula) {
		super();
		this.nombre = nombre;
		this.cedula = cedula;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public long getCedula() {
		return cedula;
	}
	public void setCedula(long cedula) {
		this.cedula = cedula;
	}
	@Override
	public String toString() {
		return "Afiliado [nombre=" + nombre + ", cedula=" + cedula + "]";
	}
}
