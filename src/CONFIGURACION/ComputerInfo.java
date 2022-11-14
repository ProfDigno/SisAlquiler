/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONFIGURACION;

//package demo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 **
 * *****************************************************************************
 * <Obtenga la dirección física de la tarjeta de red--
 * 1. Disponible en sistemas Windows y Linux;
 * 2. Obtenga información de la computadora a través de ipconifg, ifconfig;
 * 3. Utilice la coincidencia de patrones para encontrar la dirección MAC, que no tiene nada que ver con el idioma del sistema operativo>
 *
 * // * Descripción:
 * <tomar el nombre de la computadora - tomar de la variable de entorno>
 * límite abstracto herencia / crear instancia
 */
/**
 * *****************************************************************************
 */
public class ComputerInfo {

    private static String macAddressStr = null;
    private static String computerName = System.getenv().get("COMPUTERNAME");

    private static final String[] windowsCommand = {"ipconfig", "/all"};
    private static final String[] linuxCommand = {"/sbin/ifconfig", "-a"};
    private static final Pattern macPattern = Pattern.compile(".*((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*",
            Pattern.CASE_INSENSITIVE);

    /**
     * Obtenga varias direcciones de tarjetas de red
     *
     * @return
     * @throws IOException
     */
    private final static List<String> getMacAddressList() throws IOException {
        final ArrayList<String> macAddressList = new ArrayList<String>();
        final String os = System.getProperty("os.name");
        final String command[];

        if (os.startsWith("Windows")) {
            command = windowsCommand;
        } else if (os.startsWith("Linux")) {
            command = linuxCommand;
        } else {
            throw new IOException("Unknow operating system:" + os);
        }
        // Ejecutando una orden
        final Process process = Runtime.getRuntime().exec(command);

        BufferedReader bufReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        for (String line = null; (line = bufReader.readLine()) != null;) {
            Matcher matcher = macPattern.matcher(line);
            if (matcher.matches()) {
                macAddressList.add(matcher.group(1));
                // macAddressList.add(matcher.group(1).replaceAll("[-:]",
                // "")); // Elimina el "-" en MAC
            }
        }

        process.destroy();
        bufReader.close();
        return macAddressList;
    }

    /**
     * Obtenga una dirección de tarjeta de red (obtenga una de varias tarjetas
     * de red)
     *
     * @return
     */
    public static String getMacAddress() {
        if (macAddressStr == null || macAddressStr.equals("")) {
            StringBuffer sb = new StringBuffer(); // Se usa para almacenar múltiples direcciones de tarjetas de red, actualmente solo se toma un valor diferente al túnel 0000000000E0
            try {
                List<String> macList = getMacAddressList();
                for (Iterator<String> iter = macList.iterator(); iter.hasNext();) {
                    String amac = iter.next();
                    if (!amac.equals("0000000000E0")) {
                        sb.append(amac);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            macAddressStr = sb.toString();

        }

        return macAddressStr;
    }

    /**
     * Obtener el nombre de la computadora
     *
     * @return
     */
    public static String getComputerName() {
        if (computerName == null || computerName.equals("")) {
            computerName = System.getenv().get("COMPUTERNAME");
        }
        return computerName;
    }

    /**
     * Obtenga la dirección IP del cliente
     *
     * @return
     */
    public static String getIpAddrAndName() throws IOException {
        return InetAddress.getLocalHost().toString();
    }

    /**
     * Obtenga la dirección IP del cliente
     *
     * @return
     */
    public static String getIpAddr() throws IOException {
        return InetAddress.getLocalHost().getHostAddress().toString();
    }

    /**
     * Obtenga una identificación de computadora única
     *
     * @return
     */
    public static String getComputerID() {
        String id = getMacAddress();
        if (id == null || id.equals("")) {
            try {
                id = getIpAddrAndName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return computerName;
    }

//    /**
//           * Restringir la creación de instancias
//     */
//    private ComputerInfo() {
// 
// 
//    }
    public static String getNombrePC() {
        String hostname = "Unknown";
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (UnknownHostException ex) {
            System.out.println("Hostname can not be resolved:"+ex);
        }
        return hostname;
    }

//    public static void main(String[] args) throws IOException {
////        System.out.println(ComputerInfo.getMacAddress());
////        System.out.println(ComputerInfo.getComputerName());
////        System.out.println(ComputerInfo.getIpAddr());
////        System.out.println(ComputerInfo.getIpAddrAndName());
////        System.out.println(ComputerInfo.getNombrePC());
//    }
//    static private final Logger LOGGER = Logger.getLogger("mx.com.hash.checkip.CheckIP");

    String obtenerIP() throws UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        return ip.getHostAddress();
    }
    public String getStringMiIP(){
        String miIP="0.0.0.0";
        try {
            System.out.println("La IP de su compuradora es " + obtenerIP());
            miIP=obtenerIP();
        } catch (Exception e) {
            System.out.println("getStringMiIP:"+e);
        }
        return miIP;
    }
}
