	package FORMULARIO.ENTIDAD;
public class producto_combo {

//---------------DECLARAR VARIABLES---------------
private int C1idproducto_combo;
private String C2nombre;
private String C3descripcion;
private double C4precio_alquiler;
private double C5precio_reposicion;
private double C6descuento;
private boolean C7activo;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public producto_combo() {
		setTb_producto_combo("producto_combo");
		setId_idproducto_combo("idproducto_combo");
	}
	public static String getTb_producto_combo(){
		return nom_tabla;
	}
	public static void setTb_producto_combo(String nom_tabla){
		producto_combo.nom_tabla = nom_tabla;
	}
	public static String getId_idproducto_combo(){
		return nom_idtabla;
	}
	public static void setId_idproducto_combo(String nom_idtabla){
		producto_combo.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idproducto_combo(){
		return C1idproducto_combo;
	}
	public void setC1idproducto_combo(int C1idproducto_combo){
		this.C1idproducto_combo = C1idproducto_combo;
	}
	public String getC2nombre(){
		return C2nombre;
	}
	public void setC2nombre(String C2nombre){
		this.C2nombre = C2nombre;
	}
	public String getC3descripcion(){
		return C3descripcion;
	}
	public void setC3descripcion(String C3descripcion){
		this.C3descripcion = C3descripcion;
	}
	public double getC4precio_alquiler(){
		return C4precio_alquiler;
	}
	public void setC4precio_alquiler(double C4precio_alquiler){
		this.C4precio_alquiler = C4precio_alquiler;
	}
	public double getC5precio_reposicion(){
		return C5precio_reposicion;
	}
	public void setC5precio_reposicion(double C5precio_reposicion){
		this.C5precio_reposicion = C5precio_reposicion;
	}
	public double getC6descuento(){
		return C6descuento;
	}
	public void setC6descuento(double C6descuento){
		this.C6descuento = C6descuento;
	}
	public boolean getC7activo(){
		return C7activo;
	}
	public void setC7activo(boolean C7activo){
		this.C7activo = C7activo;
	}
	public String toString() {
		return "producto_combo(" + ",idproducto_combo=" + C1idproducto_combo + " ,nombre=" + C2nombre + " ,descripcion=" + C3descripcion + " ,precio_alquiler=" + C4precio_alquiler + " ,precio_reposicion=" + C5precio_reposicion + " ,descuento=" + C6descuento + " ,activo=" + C7activo + " )";
	}
}
