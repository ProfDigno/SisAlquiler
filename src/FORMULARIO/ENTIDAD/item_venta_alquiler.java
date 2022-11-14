	package FORMULARIO.ENTIDAD;
public class item_venta_alquiler {

//---------------DECLARAR VARIABLES---------------
private int C1iditem_venta_alquiler;
private String C2descripcion;
private double C3precio_venta;
private double C4precio_compra;
private double C5cantidad_total;
private double C6cantidad_pagado;
private int C7fk_idventa_alquiler;
private int C8fk_idproducto;
private int C9fk_idproducto_combo;
private double C10precio_alquiler;
private double C11precio_reposicion;
private boolean C12es_combo;
private boolean C13es_producto;
private int C14orden;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public item_venta_alquiler() {
		setTb_item_venta_alquiler("item_venta_alquiler");
		setId_iditem_venta_alquiler("iditem_venta_alquiler");
	}
	public static String getTb_item_venta_alquiler(){
		return nom_tabla;
	}
	public static void setTb_item_venta_alquiler(String nom_tabla){
		item_venta_alquiler.nom_tabla = nom_tabla;
	}
	public static String getId_iditem_venta_alquiler(){
		return nom_idtabla;
	}
	public static void setId_iditem_venta_alquiler(String nom_idtabla){
		item_venta_alquiler.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------

    public int getC14orden() {
        return C14orden;
    }

    public void setC14orden(int C14orden) {
        this.C14orden = C14orden;
    }

    public int getC9fk_idproducto_combo() {
        return C9fk_idproducto_combo;
    }

    public void setC9fk_idproducto_combo(int C9fk_idproducto_combo) {
        this.C9fk_idproducto_combo = C9fk_idproducto_combo;
    }

    public double getC10precio_alquiler() {
        return C10precio_alquiler;
    }

    public void setC10precio_alquiler(double C10precio_alquiler) {
        this.C10precio_alquiler = C10precio_alquiler;
    }

    public double getC11precio_reposicion() {
        return C11precio_reposicion;
    }

    public void setC11precio_reposicion(double C11precio_reposicion) {
        this.C11precio_reposicion = C11precio_reposicion;
    }

    public boolean getC12es_combo() {
        return C12es_combo;
    }

    public void setC12es_combo(boolean C12es_combo) {
        this.C12es_combo = C12es_combo;
    }

    public boolean getC13es_producto() {
        return C13es_producto;
    }

    public void setC13es_producto(boolean C13es_producto) {
        this.C13es_producto = C13es_producto;
    }
        
	public int getC1iditem_venta_alquiler(){
		return C1iditem_venta_alquiler;
	}
	public void setC1iditem_venta_alquiler(int C1iditem_venta_alquiler){
		this.C1iditem_venta_alquiler = C1iditem_venta_alquiler;
	}
	public String getC2descripcion(){
		return C2descripcion;
	}
	public void setC2descripcion(String C2descripcion){
		this.C2descripcion = C2descripcion;
	}
	public double getC3precio_venta(){
		return C3precio_venta;
	}
	public void setC3precio_venta(double C3precio_venta){
		this.C3precio_venta = C3precio_venta;
	}
	public double getC4precio_compra(){
		return C4precio_compra;
	}
	public void setC4precio_compra(double C4precio_compra){
		this.C4precio_compra = C4precio_compra;
	}
	public double getC5cantidad_total(){
		return C5cantidad_total;
	}
	public void setC5cantidad_total(double C5cantidad_total){
		this.C5cantidad_total = C5cantidad_total;
	}
	public double getC6cantidad_pagado(){
		return C6cantidad_pagado;
	}
	public void setC6cantidad_pagado(double C6cantidad_pagado){
		this.C6cantidad_pagado = C6cantidad_pagado;
	}
	public int getC7fk_idventa_alquiler(){
		return C7fk_idventa_alquiler;
	}
	public void setC7fk_idventa_alquiler(int C7fk_idventa_alquiler){
		this.C7fk_idventa_alquiler = C7fk_idventa_alquiler;
	}
	public int getC8fk_idproducto(){
		return C8fk_idproducto;
	}
	public void setC8fk_idproducto(int C8fk_idproducto){
		this.C8fk_idproducto = C8fk_idproducto;
	}
	public String toString() {
		return "item_venta_alquiler(" + ",iditem_venta_alquiler=" + C1iditem_venta_alquiler + " ,descripcion=" + C2descripcion + " ,precio_venta=" + C3precio_venta + " ,precio_compra=" + C4precio_compra + " ,cantidad_total=" + C5cantidad_total + " ,cantidad_pagado=" + C6cantidad_pagado + " ,fk_idventa_alquiler=" + C7fk_idventa_alquiler + " ,fk_idproducto=" + C8fk_idproducto + " )";
	}
}
