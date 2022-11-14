	package FORMULARIO.ENTIDAD;
public class dato_banco_cliente {

//---------------DECLARAR VARIABLES---------------
private int C1iddato_banco_cliente;
private String C2titular;
private String C3documento;
private String C4nro_cuenta;
private boolean C5activo;
private int C6fk_idbanco;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public dato_banco_cliente() {
		setTb_dato_banco_cliente("dato_banco_cliente");
		setId_iddato_banco_cliente("iddato_banco_cliente");
	}
	public static String getTb_dato_banco_cliente(){
		return nom_tabla;
	}
	public static void setTb_dato_banco_cliente(String nom_tabla){
		dato_banco_cliente.nom_tabla = nom_tabla;
	}
	public static String getId_iddato_banco_cliente(){
		return nom_idtabla;
	}
	public static void setId_iddato_banco_cliente(String nom_idtabla){
		dato_banco_cliente.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1iddato_banco_cliente(){
		return C1iddato_banco_cliente;
	}
	public void setC1iddato_banco_cliente(int C1iddato_banco_cliente){
		this.C1iddato_banco_cliente = C1iddato_banco_cliente;
	}
	public String getC2titular(){
		return C2titular;
	}
	public void setC2titular(String C2titular){
		this.C2titular = C2titular;
	}
	public String getC3documento(){
		return C3documento;
	}
	public void setC3documento(String C3documento){
		this.C3documento = C3documento;
	}
	public String getC4nro_cuenta(){
		return C4nro_cuenta;
	}
	public void setC4nro_cuenta(String C4nro_cuenta){
		this.C4nro_cuenta = C4nro_cuenta;
	}
	public boolean getC5activo(){
		return C5activo;
	}
	public void setC5activo(boolean C5activo){
		this.C5activo = C5activo;
	}
	public int getC6fk_idbanco(){
		return C6fk_idbanco;
	}
	public void setC6fk_idbanco(int C6fk_idbanco){
		this.C6fk_idbanco = C6fk_idbanco;
	}
	public String toString() {
		return "dato_banco_cliente(" + ",iddato_banco_cliente=" + C1iddato_banco_cliente + " ,titular=" + C2titular + " ,documento=" + C3documento + " ,nro_cuenta=" + C4nro_cuenta + " ,activo=" + C5activo + " ,fk_idbanco=" + C6fk_idbanco + " )";
	}
}
