package co.edu.uptc.logica.modelo;

public class Turno {
	
	private Afiliado afiliado;
	private String modulo;
	private String servicio;
	private String codigo;
	private String fecha;
	private int nivel;
	private boolean estado;
	
	public Turno() {
		
	}
	public Turno(Afiliado afiliado, String modulo, String servicio, String codigo, String fecha, int nivel,boolean estado) {
		super();
		this.afiliado = afiliado;
		this.modulo = modulo;
		this.servicio = servicio;
		this.codigo = codigo;
		this.fecha = fecha;
		this.nivel = nivel;
		this.estado = estado;
	}
	public Afiliado getAfiliado() {
		return afiliado;
	}
	public void setAfiliado(Afiliado afiliado) {
		this.afiliado = afiliado;
	}
	public String getModulo() {
		return modulo;
	}
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	public String getServicio() {
		return servicio;
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "Turno [afiliado=" + afiliado + ", modulo=" + modulo + ", servicio=" + servicio + ", codigo=" + codigo
				+ ", fecha=" + fecha + ", nivel=" + nivel + ", estado=" + estado + "]";
	}

}
