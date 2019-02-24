/*
 *		SNMPv2c - Status: LISTO!!!
 */
package puppeteer.SNMPv2c; 
 
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

public class SNMPv2c{
	
  private Snmp snmp = null;
  private static boolean respondio;
  private static String dato;  

  public String getBulkv2c(String ip, String puerto, String comunidad, int intentos, int tiempoEspera, Vector requerimiento,int NonRepeaters, int MaxRepetitions,String mensaje){
    
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
	    target.setVersion(SnmpConstants.version2c);
	    // creando el PDU
	    PDU pdu = new PDU();
	    //pdu.add(new VariableBinding(new OID(requerimiento)));
	    for (int i=0;i<(requerimiento.size());i++){
      	  //System.out.println("dato"+i+": "+String.valueOf(requerimiento.get(i)));	
      	  pdu.add(new VariableBinding(new OID(String.valueOf(requerimiento.get(i)))));
    	}
	    pdu.setType(PDU.GETBULK);
    	pdu.setNonRepeaters(NonRepeaters);//0  <0 se trae todo, si vale 1  >1 se trae solo 1 valor. - pedir por pantalla
		pdu.setMaxRepetitions(MaxRepetitions);// cantidad de variables que va a retornar - pedir por pantalla
	        
	    // enviando el PDU
	    
	    	dato="";
	    	ResponseEvent event = snmp.send(pdu, target);
	        PDU response = event.getResponse();
	        PDU request = event.getRequest();
	        if (response == null) {
	            //System.out.println("Request "+request+" timed out");
	            dato=mensaje;
	        }
	        else {
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

  public String getNextv2c(String ip, String puerto, String comunidad, int intentos, int tiempoEspera, Vector requerimiento,String mensaje){
    
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
	    target.setVersion(SnmpConstants.version2c);
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
	        }
	        else {
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

  public String getv2c(String ip, String puerto, String comunidad, int intentos, int tiempoEspera, Vector requerimiento,String mensaje){
    
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
	    target.setVersion(SnmpConstants.version2c);
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
	        }
	        else {
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

  public String setv2c(String ip, String puerto, String comunidad, int intentos, int tiempoEspera, Vector requerimiento, Variable[] valor,String mensaje){
    
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
	    target.setVersion(SnmpConstants.version2c);
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
	        if (response == null) {
	            //System.out.println("Request "+request+" timed out");
	            dato=mensaje;
	        }
	        else {
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
  	
  	//SNMPv2c manager = new SNMPv2c();
  	
  	//Vector pruebaCompuesto = new Vector();
  	//int tamao = 1;
  	//Variable valor[] = new Variable[tamao]; 
  	//pruebaCompuesto.add(".1.3.6.1.2.1.1.1");
  	//pruebaCompuesto.add(".1.3.6.1.2.1.1.4.0");
  	//valor[0]= new OctetString(String.valueOf("Contact 8"));
  	//pruebaCompuesto.add(".1.3.6.1.2.1.1.5.0");
  	//valor[1]= new OctetString(String.valueOf("Name 8"));
  	
  	//pruebaCompuesto.add(".1.3.6.1.2.1.1.1.0");
  	//valor[2]= new OctetString(String.valueOf("descriptor 1"));
  	//pruebaCompuesto.add(".1.3.6.1.2.1.1.3.0");
  	//pruebaCompuesto.add(".1.3.6.1.2.1.1.2.0");
  	
    //System.out.println("GetNextv2c: "+manager.getNextv2c("192.168.0.5", "161", "public", 3, 1500,pruebaCompuesto));
  	//System.out.println("Getv2c: "+manager.getv2c("192.168.0.5", "161", "public", 3, 1500,pruebaCompuesto));
  	//System.out.println("GetBulkv2c: "+manager.getBulkv2c("192.168.0.5", "161", "public", 3, 1500,pruebaCompuesto,1,10));
  	//NonRepeaters(0);//0  <0 se trae todo, si vale 1  >1 se trae solo 1 valor. - pedir por pantalla
	//MaxRepetitions(100);// cantidad de variables que va a retornar - pedir por pantalla
  	
  	//Variable valor;
    /***************************************************************************************/
    // AQUI DEBEN IR TODOS LOS TIPOS DE DATOS, NO SOLO OCTETSTRING.
   // valor = new OctetString(String.valueOf("Prueba desde 1 sola clase"));
       
    /***************************************************************************************/
  	//System.out.println("Setv2c: "+manager.setv2c("192.168.0.5", "161", "public", 2, 1500,pruebaCompuesto, valor));
    //System.out.println("GetNextv1: ");
    //System.out.println("--- FIN DEL PROGRAMA ---");
  }



}