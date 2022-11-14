	package FORMULARIO.ENTIDAD;
public class item_producto_combo {

//---------------DECLARAR VARIABLES---------------
private int C1iditem_producto_combo;
private String C2descripcion;
private double C3cantidad;
private double C4precio_alquiler;
private double C5precio_reposicion;
private int C6fk_idproducto;
private int C7fk_idproducto_combo;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public item_producto_combo() {
		setTb_item_producto_combo("item_producto_combo");
		setId_iditem_producto_combo("iditem_producto_combo");
	}
	public static String getTb_item_producto_combo(){
		return nom_tabla;
	}
	public static void setTb_item_producto_combo(String nom_tabla){
		item_producto_combo.nom_tabla = nom_tabla;
	}
	public static String getId_iditem_producto_combo(){
		return nom_idtabla;
	}
	public static void setId_iditem_producto_combo(String nom_idtabla){
		item_producto_combo.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1iditem_producto_combo(){
		return C1iditem_producto_combo;
	}
	public void setC1iditem_producto_combo(int C1iditem_producto_combo){
		this.C1iditem_producto_combo = C1iditem_producto_combo;
	}
	public String getC2descripcion(){
		return C2descripcion;
	}
	public void setC2descripcion(String C2descripcion){
		this.C2descripcion = C2descripcion;
	}
	public double getC3cantidad(){
		return C3cantidad;
	}
	public void setC3cantidad(double C3cantidad){
		this.C3cantidad = C3cantidad;
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
	public int getC6fk_idproducto(){
		return C6fk_idproducto;
	}
	public void setC6fk_idproducto(int C6fk_idproducto){
		this.C6fk_idproducto = C6fk_idproducto;
	}
	public int getC7fk_idproducto_combo(){
		return C7fk_idproducto_combo;
	}
	public void setC7fk_idproducto_combo(int C7fk_idproducto_combo){
		this.C7fk_idproducto_combo = C7fk_idproducto_combo;
	}
	public String toString() {
		return "item_producto_combo(" + ",iditem_producto_combo=" + C1iditem_producto_combo + " ,descripcion=" + C2descripcion + " ,cantidad=" + C3cantidad + " ,precio_alquiler=" + C4precio_alquiler + " ,precio_reposicion=" + C5precio_reposicion + " ,fk_idproducto=" + C6fk_idproducto + " ,fk_idproducto_combo=" + C7fk_idproducto_combo + " )";
	}
}
