/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config_JSON;

import java.io.FileReader;
import java.net.InetAddress;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Digno
 */
public class json_appsheet {

   

    private static String dato_pedido_xlsx;
    
    public void cargar_json_appsheet() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("JSON\\json_appsheet.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String dato_pedido_xlsx = (String) jsonObject.get("dato_pedido_xlsx");
            setDato_pedido_xlsx(dato_pedido_xlsx);
            System.out.println("json json_appsheet:"+jsonObject);
        } catch (Exception ex) {
            System.err.println("Error: " + ex.toString());
            JOptionPane.showMessageDialog(null, "Error: " + ex.toString());
        } finally {

        }
    }

    public static String getDato_pedido_xlsx() {
        return dato_pedido_xlsx;
    }

    public static void setDato_pedido_xlsx(String dato_pedido_xlsx) {
        json_appsheet.dato_pedido_xlsx = dato_pedido_xlsx;
    }

    

}
