	package FORMULARIO.ENTIDAD;
public class app_item_pedido {

//---------------DECLARAR VARIABLES---------------
private String C1iditem_venta_alquiler;
private int C2cantidad_total;
private String C3descripcion;
private double C4precio_alquiler;
private double C5precio_descuento;
private boolean C6es_combo;
private boolean C7es_producto;
private String C8fk_idventa_alquiler;
private int C9fk_idproducto;
private int C10fk_idproducto_combo;
private int C11orden;
private boolean C12es_vencido;
private boolean C13es_eliminado;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public app_item_pedido() {
		setTb_app_item_pedido("app_item_pedido");
		setId_iditem_venta_alquiler("iditem_venta_alquiler");
	}
	public static String getTb_app_item_pedido(){
		return nom_tabla;
	}
	public static void setTb_app_item_pedido(String nom_tabla){
		app_item_pedido.nom_tabla = nom_tabla;
	}
	public static String getId_iditem_venta_alquiler(){
		return nom_idtabla;
	}
	public static void setId_iditem_venta_alquiler(String nom_idtabla){
		app_item_pedido.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------

    public boolean getC12es_vencido() {
        return C12es_vencido;
    }

    public void setC12es_vencido(boolean C12es_vencido) {
        this.C12es_vencido = C12es_vencido;
    }

    public boolean getC13es_eliminado() {
        return C13es_eliminado;
    }

    public void setC13es_eliminado(boolean C13es_eliminado) {
        this.C13es_eliminado = C13es_eliminado;
    }
        
	public String getC1iditem_venta_alquiler(){
		return C1iditem_venta_alquiler;
	}
	public void setC1iditem_venta_alquiler(String C1iditem_venta_alquiler){
		this.C1iditem_venta_alquiler = C1iditem_venta_alquiler;
	}
	public int getC2cantidad_total(){
		return C2cantidad_total;
	}
	public void setC2cantidad_total(int C2cantidad_total){
		this.C2cantidad_total = C2cantidad_total;
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
	public double getC5precio_descuento(){
		return C5precio_descuento;
	}
	public void setC5precio_descuento(double C5precio_descuento){
		this.C5precio_descuento = C5precio_descuento;
	}
	public boolean getC6es_combo(){
		return C6es_combo;
	}
	public void setC6es_combo(boolean C6es_combo){
		this.C6es_combo = C6es_combo;
	}
	public boolean getC7es_producto(){
		return C7es_producto;
	}
	public void setC7es_producto(boolean C7es_producto){
		this.C7es_producto = C7es_producto;
	}
	public String getC8fk_idventa_alquiler(){
		return C8fk_idventa_alquiler;
	}
	public void setC8fk_idventa_alquiler(String C8fk_idventa_alquiler){
		this.C8fk_idventa_alquiler = C8fk_idventa_alquiler;
	}
	public int getC9fk_idproducto(){
		return C9fk_idproducto;
	}
	public void setC9fk_idproducto(int C9fk_idproducto){
		this.C9fk_idproducto = C9fk_idproducto;
	}
	public int getC10fk_idproducto_combo(){
		return C10fk_idproducto_combo;
	}
	public void setC10fk_idproducto_combo(int C10fk_idproducto_combo){
		this.C10fk_idproducto_combo = C10fk_idproducto_combo;
	}

    public int getC11orden() {
        return C11orden;
    }

    public void setC11orden(int C11orden) {
        this.C11orden = C11orden;
    }
        
	public String toString() {
		return "app_item_pedido(" + ",iditem_venta_alquiler=" + C1iditem_venta_alquiler + " ,cantidad_total=" + C2cantidad_total + " ,descripcion=" + C3descripcion + " ,precio_alquiler=" + C4precio_alquiler + " ,precio_descuento=" + C5precio_descuento + " ,es_combo=" + C6es_combo + " ,es_producto=" + C7es_producto + " ,fk_idventa_alquiler=" + C8fk_idventa_alquiler + " ,fk_idproducto=" + C9fk_idproducto + " ,fk_idproducto_combo=" + C10fk_idproducto_combo + " )";
	}
}
