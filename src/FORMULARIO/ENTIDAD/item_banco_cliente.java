	package FORMULARIO.ENTIDAD;
public class item_banco_cliente {

//---------------DECLARAR VARIABLES---------------
private int C1iditem_banco_cliente;
private int C2fk_iddato_banco_cliente;
private int C3fk_idcliente;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public item_banco_cliente() {
		setTb_item_banco_cliente("item_banco_cliente");
		setId_iditem_banco_cliente("iditem_banco_cliente");
	}
	public static String getTb_item_banco_cliente(){
		return nom_tabla;
	}
	public static void setTb_item_banco_cliente(String nom_tabla){
		item_banco_cliente.nom_tabla = nom_tabla;
	}
	public static String getId_iditem_banco_cliente(){
		return nom_idtabla;
	}
	public static void setId_iditem_banco_cliente(String nom_idtabla){
		item_banco_cliente.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1iditem_banco_cliente(){
		return C1iditem_banco_cliente;
	}
	public void setC1iditem_banco_cliente(int C1iditem_banco_cliente){
		this.C1iditem_banco_cliente = C1iditem_banco_cliente;
	}
	public int getC2fk_iddato_banco_cliente(){
		return C2fk_iddato_banco_cliente;
	}
	public void setC2fk_iddato_banco_cliente(int C2fk_iddato_banco_cliente){
		this.C2fk_iddato_banco_cliente = C2fk_iddato_banco_cliente;
	}
	public int getC3fk_idcliente(){
		return C3fk_idcliente;
	}
	public void setC3fk_idcliente(int C3fk_idcliente){
		this.C3fk_idcliente = C3fk_idcliente;
	}
	public String toString() {
		return "item_banco_cliente(" + ",iditem_banco_cliente=" + C1iditem_banco_cliente + " ,fk_iddato_banco_cliente=" + C2fk_iddato_banco_cliente + " ,fk_idcliente=" + C3fk_idcliente + " )";
	}
}
