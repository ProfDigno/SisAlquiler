	package FORMULARIO.ENTIDAD;
public class tipo_evento {

//---------------DECLARAR VARIABLES---------------
private int C1idtipo_evento;
private String C2nombre;
private boolean C3activar;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public tipo_evento() {
		setTb_tipo_evento("tipo_evento");
		setId_idtipo_evento("idtipo_evento");
	}
	public static String getTb_tipo_evento(){
		return nom_tabla;
	}
	public static void setTb_tipo_evento(String nom_tabla){
		tipo_evento.nom_tabla = nom_tabla;
	}
	public static String getId_idtipo_evento(){
		return nom_idtabla;
	}
	public static void setId_idtipo_evento(String nom_idtabla){
		tipo_evento.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idtipo_evento(){
		return C1idtipo_evento;
	}
	public void setC1idtipo_evento(int C1idtipo_evento){
		this.C1idtipo_evento = C1idtipo_evento;
	}
	public String getC2nombre(){
		return C2nombre;
	}
	public void setC2nombre(String C2nombre){
		this.C2nombre = C2nombre;
	}
	public boolean getC3activar(){
		return C3activar;
	}
	public void setC3activar(boolean C3activar){
		this.C3activar = C3activar;
	}
	public String toString() {
		return "tipo_evento(" + ",idtipo_evento=" + C1idtipo_evento + " ,nombre=" + C2nombre + " ,activar=" + C3activar + " )";
	}
}
