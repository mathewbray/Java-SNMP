/*
 *		SNMPv1 - Status: LISTO!!!
 */
 package puppeteer.SNMPv1;
 
import java.io.*;
import java.util.*;
 
import org.snmp4j.*;
import org.snmp4j.event.*;
import org.snmp4j.log.*;
import org.snmp4j.mp.*;
import org.snmp4j.security.*;
import org.snmp4j.smi.*;
import org.snmp4j.asn1.*;
import org.snmp4j.test.*;
import org.snmp4j.tools.console.*;
import org.snmp4j.transport.*;
import org.snmp4j.util.*;

public class SNMPv1{
	
  private Snmp snmp = null;
  private static boolean respondio;
  private static String dato;  

  public String getNextv1(String ip, String puerto, String comunidad, int intentos, int tiempoEspera, Vector requerimiento,String mensaje){
    
    String respuesta = "";
    try{
      	Address targetAddress = GenericAddress.parse("udp:"+ip+"/"+puerto);
	   	TransportMapping transport = new DefaultUdpTransportMapping();
		Snmp snmp = new Snmp(transport);
	
		CommandResponder trapPrinter = new CommandResponder() {
		  public synchronized void processPdu(CommandResponderEvent e) {
		    PDU command = e.getPDU();
		    if (command != null) {
		      System.out.println(command.toString());
		    }
		  }
		};
		snmp.addCommandResponder(trapPrinter);
		transport.listen();
	
	    CommunityTarget target = new CommunityTarget();
	    target.setCommunity(new OctetString(comunidad));
	    target.setAddress(targetAddress);
	    target.setRetries(intentos);
	    target.setTimeout(tiempoEspera);
	    target.setVersion(SnmpConstants.version1);
	    // creando el PDU
	    PDU pdu = new PDU();
	    for (int i=0;i<(requerimiento.size());i++){
      	  //System.out.println("dato"+i+": "+String.valueOf(requerimiento.get(i)));	
      	  pdu.add(new VariableBinding(new OID(String.valueOf(requerimiento.get(i)))));
    	}
	    pdu.setType(PDU.GETNEXT);
	        
	    // enviando el PDU
	    dato="";
	    ResponseEvent event = snmp.send(pdu, target);
	    PDU response = event.getResponse();
	    PDU request = event.getRequest();
	    if (response == null) {
	      //System.out.println("Request "+request+" timed out");
	      dato=mensaje;
	    }else{
	      //System.out.println("Received response "+response+" on request "+request);
	      dato=String.valueOf(response);
	      //Tratamiento de la respuesta
	      //System.out.println("respuesta: "+dato);
	      //System.out.println("index: "+respuesta.indexOf("VBS"));
		  int lugar =dato.indexOf("VBS");
		  int ini = dato.indexOf("[",lugar);
		  int fin = dato.indexOf("]",lugar);
		  ini++;
		  dato=dato.substring(ini,fin);
		  if (dato.indexOf(";")!=(-1)){
		    String tempPicador = "";
		    StringTokenizer Token = new StringTokenizer (dato,";");
			while(Token.hasMoreTokens()){
			  tempPicador=tempPicador.concat(String.valueOf(Token.nextToken())+"\n");
			}	
			dato=tempPicador;	
		  }else{
		    dato=dato.concat("\n");	
		  }
		  dato="\n ".concat(dato);
		  //Fin del tratamiento de la respuesta
	    }
  	}catch (Exception e){e.printStackTrace();}
    //System.out.println("El resultado fue: "+dato);
   	//System.out.println("Programa Terminado");
    
    respuesta = dato;
    
  	return respuesta;
  
  }

  public String getv1(String ip, String puerto, String comunidad, int intentos, int tiempoEspera, Vector requerimiento,String mensaje){
    
    String respuesta = "";
    try{
      	Address targetAddress = GenericAddress.parse("udp:"+ip+"/"+puerto);
	   	TransportMapping transport = new DefaultUdpTransportMapping();
		Snmp snmp = new Snmp(transport);
	
		CommandResponder trapPrinter = new CommandResponder() {
		  public synchronized void processPdu(CommandResponderEvent e) {
		    PDU command = e.getPDU();
		    if (command != null) {
		      System.out.println(command.toString());
		    }
		  }
		};
		snmp.addCommandResponder(trapPrinter);
		transport.listen();
	
	    CommunityTarget target = new CommunityTarget();
	    target.setCommunity(new OctetString(comunidad));
	    target.setAddress(targetAddress);
	    target.setRetries(intentos);
	    target.setTimeout(tiempoEspera);
	    target.setVersion(SnmpConstants.version1);
	    // creando el PDU
	    PDU pdu = new PDU();
	    for (int i=0;i<(requerimiento.size());i++){
      	  //System.out.println("dato"+i+": "+String.valueOf(requerimiento.get(i)));	
      	  pdu.add(new VariableBinding(new OID(String.valueOf(requerimiento.get(i)))));
    	}
	    pdu.setType(PDU.GET);
	        
	    // enviando el PDU
	    dato="";
	    ResponseEvent event = snmp.send(pdu, target);
	    PDU response = event.getResponse();
	    PDU request = event.getRequest();
	    if (response == null) {
	      //System.out.println("Request "+request+" timed out");
	      dato=mensaje;
	    }else {
	      //System.out.println("Received response "+response+" on request "+request);
	      dato=String.valueOf(response);
	      //Tratamiento de la respuesta
	      //System.out.println("respuesta: "+dato);
		  //System.out.println("index: "+respuesta.indexOf("VBS"));
		  int lugar =dato.indexOf("VBS");
		  int ini = dato.indexOf("[",lugar);
		  int fin = dato.indexOf("]",lugar);
		  ini++;
		  dato=dato.substring(ini,fin);
		  if (dato.indexOf(";")!=(-1)){
		    String tempPicador = "";
		    StringTokenizer Token = new StringTokenizer (dato,";");
			while(Token.hasMoreTokens()){
			  tempPicador=tempPicador.concat(String.valueOf(Token.nextToken())+"\n");
			}	
			dato=tempPicador;	
		  }else{
			dato=dato.concat("\n");	
		  }
		  dato="\n ".concat(dato);
		  //Fin del tratamiento de la respuesta
	    }
  	}catch (Exception e){e.printStackTrace();}
    
    //System.out.println("El resultado fue: "+dato);
   	//System.out.println("Programa Terminado");
    
    respuesta = dato;
        	
  	return respuesta;
  
  }

  public String setv1(String ip, String puerto, String comunidad, int intentos, int tiempoEspera, Vector requerimiento, Variable[] valor, String mensaje){
    
    String respuesta = "";
    try{
		Address targetAddress = GenericAddress.parse("udp:"+ip+"/"+puerto);
		TransportMapping transport = new DefaultUdpTransportMapping();
		Snmp snmp = new Snmp(transport);
		
		CommandResponder trapPrinter = new CommandResponder() {
		  public synchronized void processPdu(CommandResponderEvent e) {
		    PDU command = e.getPDU();
		    if (command != null) {
		      System.out.println(command.toString());
		    }
		  }
		};
		snmp.addCommandResponder(trapPrinter);
		   
		transport.listen();
	
	    CommunityTarget target = new CommunityTarget();
	    target.setCommunity(new OctetString(comunidad));
		target.setAddress(targetAddress);
		target.setRetries(intentos);
		target.setTimeout(tiempoEspera);
	    target.setVersion(SnmpConstants.version1);
	    // creando PDU
	    PDU pdu = new PDU();
	    for (int i=0;i<(requerimiento.size());i++){
      	  VariableBinding vb = new VariableBinding(new OID(String.valueOf(requerimiento.get(i))));
      	  //System.out.println(String.valueOf(requerimiento.get(i))+" "+valor[i]);	
	      vb.setVariable(valor[i]);
	      pdu.add(vb);
    	}
	    pdu.setType(PDU.SET);

	    // enviando el PDU
	    dato="";
	    ResponseEvent event = snmp.send(pdu, target);
	    PDU response = event.getResponse();
	    PDU request = event.getRequest();
	        
	    //System.out.println("Received response "+response+" on request "+request);
	        
	    if (response == null) {
	      //System.out.println("Request "+request+" timed out");
	      dato=mensaje;
	    }else {
	      //System.out.println("Received response "+response+" on request "+request);
	            
	            /*
	             *public static final int SNMP_ERROR_AUTHORIZATION_ERROR 16 
				 public static final int SNMP_ERROR_BAD_VALUE 3 
				 public static final int SNMP_ERROR_COMMIT_FAILED 14 
				 public static final int SNMP_ERROR_GENERAL_ERROR 5 
				 public static final int SNMP_ERROR_INCONSISTENT_NAME 18 
				 public static final int SNMP_ERROR_INCONSISTENT_VALUE 12 
				 public static final int SNMP_ERROR_NO_ACCESS 6 
				 public static final int SNMP_ERROR_NO_CREATION 11 
				 public static final int SNMP_ERROR_NO_SUCH_NAME 2 
				 public static final int SNMP_ERROR_NOT_WRITEABLE 17 
				 public static final int SNMP_ERROR_READ_ONLY 4 
				 public static final int SNMP_ERROR_RESOURCE_UNAVAILABLE 13 
				 public static final int SNMP_ERROR_SUCCESS 0 
				 public static final int SNMP_ERROR_TOO_BIG 1 
				 public static final int SNMP_ERROR_UNDO_FAILED 15 
				 public static final int SNMP_ERROR_WRONG_ENCODING 9 
				 public static final int SNMP_ERROR_WRONG_LENGTH 8 
				 public static final int SNMP_ERROR_WRONG_TYPE 7 
				 public static final int SNMP_ERROR_WRONG_VALUE 10 
             **/
             
             	dato=String.valueOf(response);
	            //Tratamiento de la respuesta
	            //System.out.println("respuesta: "+dato);
			    //System.out.println("index: "+respuesta.indexOf("VBS"));
			    int lugar =dato.indexOf("VBS");
			    int ini = dato.indexOf("[",lugar);
			    int fin = dato.indexOf("]",lugar);
			    ini++;
			    dato=dato.substring(ini,fin);
			    if (dato.indexOf(";")!=(-1)){
		          String tempPicador = "";
		          StringTokenizer Token = new StringTokenizer (dato,";");
				  while(Token.hasMoreTokens()){
				    tempPicador=tempPicador.concat(String.valueOf(Token.nextToken())+"\n");
				  }	
				  dato=tempPicador;	
			    }else{
			       dato=dato.concat("\n");	
			    }
			    dato="\n ".concat(dato);
			    //Fin del tratamiento de la respuesta
             
                if(response.getErrorStatus()!=SnmpConstants.SNMP_ERROR_SUCCESS){
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_AUTHORIZATION_ERROR){dato=dato.concat(" Error: AUTHORIZATION ERROR...\n");}
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_BAD_VALUE){dato=dato.concat(" Error: BAD VALUE...\n");}
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_COMMIT_FAILED){dato=dato.concat(" Error: COMMIT FAILED...\n");}
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_GENERAL_ERROR){dato=dato.concat(" Error: GENERAL ERROR...\n");}
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_INCONSISTENT_NAME){dato=dato.concat(" Error: INCONSISTENT NAME...\n");}
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_INCONSISTENT_VALUE){dato=dato.concat(" Error: INCONSISTENT VALUE...\n");}
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_NO_ACCESS){dato=dato.concat(" Error: NO ACCESS...\n");}
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_NO_CREATION){dato=dato.concat(" Error: NO CREATION...\n");}
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_NO_SUCH_NAME){dato=dato.concat(" Error: NO SUCH NAME...\n");}
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_NOT_WRITEABLE){dato=dato.concat(" Error: NOT WRITEABLE...\n");}
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_READ_ONLY){dato=dato.concat(" Error: READ ONLY...\n");}
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_RESOURCE_UNAVAILABLE){dato=dato.concat(" Error: RESOURCE UNAVAILABLE...\n");}
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_SUCCESS){dato=dato.concat(" Error: SUCCESS...\n");}
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_TOO_BIG){dato=dato.concat(" Error: TOO BIG...\n");}
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_UNDO_FAILED){dato=dato.concat(" Error: UNDO FAILED...\n");}
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_WRONG_ENCODING){dato=dato.concat(" Error: WRONG ENCODING...\n");}
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_WRONG_LENGTH){dato=dato.concat(" Error: WRONG ENGTH...\n");}
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_WRONG_TYPE){dato=dato.concat(" Error: WRONG TYPE...\n");}
	             	if(response.getErrorStatus()==SnmpConstants.SNMP_ERROR_WRONG_VALUE){dato=dato.concat(" Error: WRONG VALUE...\n");}
                }
             	
             	//System.out.println("DATO: "+dato);
	            
	            
	            //dato=String.valueOf(response);
	            
	    	}
	      
    }catch (Exception e){e.printStackTrace();}
        
    respuesta = dato;
    	
  	return respuesta;
    
  }

  public static void main(String args[]) throws Exception {
  	
  	//SNMPv1 manager = new SNMPv1();
  	
  	//Vector pruebaCompuesto = new Vector();
  	//int tamao = 1;
  	//Variable valor[] = new Variable[tamao]; 
  	//pruebaCompuesto.add(".1.3.6.1.2.1.1.4.0");
  	//valor[0]= new OctetString(String.valueOf("Contact 7"));
  	//pruebaCompuesto.add(".1.3.6.1.2.1.1.5.0");
  	//valor[1]= new OctetString(String.valueOf("Name 7"));
  	
  	//pruebaCompuesto.add(".1.3.6.1.2.1.1.1");
  	//valor[2]= new OctetString(String.valueOf("descriptor 1"));
  	//pruebaCompuesto.add(".1.3.6.1.2.1.1.3");
  	//pruebaCompuesto.add(".1.3.6.1.2.1.1.2");
  	/*
  	for (int i=0;i<(pruebaCompuesto.size());i++){
      System.out.println("dato"+i+": "+String.valueOf(pruebaCompuesto.get(i)));	
    }
  	*/	
  	//System.out.println("GetNextv1: "+manager.getNextv1("192.168.0.5", "161", "public", 3, 1500,pruebaCompuesto));
  	//System.out.println("GetNextv1: ");
  	//System.out.println("Getv1: "+manager.getv1("192.168.0.5", "161", "public", 3, 1500,pruebaCompuesto));
  	
  	//Variable valor;
    /***************************************************************************************/
    // AQUI DEBEN IR TODOS LOS TIPOS DE DATOS, NO SOLO OCTETSTRING.
    	//tipoDatoReconocido = "INTEGER";--------------
        //tipoDatoReconocido = "OCTET STRING";---------
        //tipoDatoReconocido = "OBJECT IDENTIFIER";----
        //tipoDatoReconocido = "IPADDRESS";------------
        //tipoDatoReconocido = "COUNTER";--------------
        //tipoDatoReconocido = "GAUGE";----------------
        //tipoDatoReconocido = "TIMETICKS";------------
        //tipoDatoReconocido = "OPAQUE";---------------
        //tipoDatoReconocido = "COUNTER64";------------
        //tipoDatoReconocido="NO RECONOCIDO";
    /*valor = new Integer32(Integer.parseInt(String.valueOf("12345")));
    valor = new Gauge32(Long.parseLong(String.valueOf("1234567890")));
    valor = new IpAddress(String.valueOf("192.168.0.1"));
    valor = new OID(String.valueOf(".1.3.6.1.2.1.1.5.0"));
    valor = new TimeTicks(Long.parseLong(String.valueOf("Prueba desde 1 sola clase")));
    valor = new Counter32(Long.parseLong(String.valueOf("Prueba desde 1 sola clase")));
    valor = new Counter64(Long.parseLong(String.valueOf("Prueba desde 1 sola clase")));
    valor = new Opaque(String.valueOf("Prueba desde 1 sola clase").getBytes());*/
    //valor = new OctetString(String.valueOf("Prueba desde 1 sola clase"));
       
    /***************************************************************************************/
  	//System.out.println("Setv1: "+manager.setv1("192.168.0.5", "161", "public", 3, 1500,pruebaCompuesto, valor));
  	//System.out.println("--- FIN DEL PROGRAMA ---");
  }



}