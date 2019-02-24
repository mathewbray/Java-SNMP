/*
 *		SNMPv3 - Status: LISTO!!!
 */
package puppeteer.SNMPv3;
 
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

public class SNMPv3{

  private String erroresGenerales04 = "\n Tiempo de espera excedido...\n";
  private String erroresGenerales29 = " Error: El usuario no se encuentra en el Agente...\n";
  private String erroresGenerales30 = " Error: La clave y/o el algoritmo de autenticacin proporcionados son incorrectos...\n";
  private String erroresGenerales31 = " Error: La clave y/o el algoritmo de encriptacin proporcionados son incorrectos...\n";

  public void cambiarIdiomaAMensajes(String s04,String s29,String s30,String s31){
  	erroresGenerales04 = s04;
  	erroresGenerales29 = s29;
    erroresGenerales30 = s30;
    erroresGenerales31 = s31; 
  }
	
  private Snmp snmp = null;
  private static String dato;  
  
  public String getBulkv3(String ip, String puerto, String nombreUsuario, String claveAutenticacion, String claveEncriptacion, int SecurityLevel,int intentos, int tiempoEspera, Vector requerimiento,int NonRepeaters, int MaxRepetitions, OID metAut, OID metPriv){
    
    String respuesta = "";
    try{
	    Address targetAddress = GenericAddress.parse("udp:"+ip+"/"+puerto);
	    TransportMapping transport = new DefaultUdpTransportMapping();
	   	snmp = new Snmp(transport);
	   	USM usm = new USM(SecurityProtocols.getInstance(),new OctetString(MPv3.createLocalEngineID()), 0);
	   	SecurityModels.getInstance().addSecurityModel(usm);
	   	transport.listen();
	   		
	   	// aadir el usuario al USM
	   	//snmp.getUSM().addUser(new OctetString("default"),new UsmUser(new OctetString("default"),AuthMD5.ID,new OctetString("12345678"),PrivDES.ID,new OctetString("MD5DESUserPrivPassword")));
	   	//snmp.getUSM().addUser(new OctetString("seminario"),new UsmUser(new OctetString("seminario"),AuthMD5.ID,new OctetString("12345678"),PrivDES.ID,new OctetString("12345678")));
	   	//snmp.getUSM().addUser(new OctetString("seminario"),new UsmUser(new OctetString("seminario"),AuthMD5.ID,new OctetString("12345678"),null,null));
/*	   	if (claveEncriptacion==null){
	   	  snmp.getUSM().addUser(new OctetString(nombreUsuario),new UsmUser(new OctetString(nombreUsuario),AuthMD5.ID,new OctetString(claveAutenticacion),null,null));	
	   	}else{
	   	  snmp.getUSM().addUser(new OctetString(nombreUsuario),new UsmUser(new OctetString(nombreUsuario),AuthMD5.ID,new OctetString(claveAutenticacion),PrivDES.ID,new OctetString(claveEncriptacion)));
	   	}*/
	   	if (claveEncriptacion==null){
	   	  snmp.getUSM().addUser(new OctetString(nombreUsuario),new UsmUser(new OctetString(nombreUsuario),metAut,new OctetString(claveAutenticacion),null,null));	
	   	}else{
	   	  snmp.getUSM().addUser(new OctetString(nombreUsuario),new UsmUser(new OctetString(nombreUsuario),metAut,new OctetString(claveAutenticacion),metPriv,new OctetString(claveEncriptacion)));
	   	}	   	
	   	
	   	// establecer los parametros
		UserTarget target = new UserTarget();
		target.setAddress(targetAddress);
		target.setRetries(intentos);
		target.setTimeout(tiempoEspera);
		target.setVersion(SnmpConstants.version3);
		target.setSecurityLevel(SecurityLevel);
		//target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
		target.setSecurityName(new OctetString(nombreUsuario));
		
		// creando la PDU
		PDU pdu = new ScopedPDU();
		for (int i=0;i<(requerimiento.size());i++){
      	  //System.out.println("dato"+i+": "+String.valueOf(requerimiento.get(i)));	
      	  pdu.add(new VariableBinding(new OID(String.valueOf(requerimiento.get(i)))));
    	}
	    pdu.setType(PDU.GETBULK);
	    pdu.setNonRepeaters(NonRepeaters);//0  <0 se trae todo, si vale 1  >1 se trae solo 1 valor. - pedir por pantalla
		pdu.setMaxRepetitions(MaxRepetitions);// cantidad de variables que va a retornar - pedir por pantalla
		
		 // enviando la PDU
		 ResponseEvent response = snmp.send(pdu, target);
		 // extract the response PDU (could be null if timed out)
		 PDU responsePDU = response.getResponse();
		 dato="";  	
		 if (responsePDU == null) {
	       //System.out.println("Request "+responsePDU+" timed out");
	       dato=erroresGenerales04;
	     }else {
	       //System.out.println("Received response "+responsePDU);
	       dato=String.valueOf(responsePDU);
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
		   //System.out.println("dato: "+dato.indexOf("1.3.6.1.6.3.15.1.1.3.0"));
		   if (dato.indexOf("1.3.6.1.6.3.15.1.1.3.0")>=0){
		   	 dato = dato.concat(erroresGenerales29);
		   }
		   if (dato.indexOf("1.3.6.1.6.3.15.1.1.5.0")>=0){
		   	 dato = dato.concat(erroresGenerales30);
		   }
		   if (dato.indexOf("1.3.6.1.6.3.15.1.1.6.0")>=0){
		   	 dato = dato.concat(erroresGenerales31);
		   }
		   //Fin del tratamiento de la respuesta
	     }
    }catch (Exception e){e.printStackTrace();}
    
    respuesta = dato;
    	
  	return respuesta;
  
  }

  public String getNextv3(String ip, String puerto, String nombreUsuario, String claveAutenticacion, String claveEncriptacion, int SecurityLevel, int intentos, int tiempoEspera, Vector requerimiento, OID metAut, OID metPriv){
    
    String respuesta = "";
    try{
	    Address targetAddress = GenericAddress.parse("udp:"+ip+"/"+puerto);
	    TransportMapping transport = new DefaultUdpTransportMapping();
	   	snmp = new Snmp(transport);
	   	USM usm = new USM(SecurityProtocols.getInstance(),new OctetString(MPv3.createLocalEngineID()), 0);
	   	SecurityModels.getInstance().addSecurityModel(usm);
	   	transport.listen();
	   		
	   	// aadir el usuario al USM
	   	//snmp.getUSM().addUser(new OctetString("default"),new UsmUser(new OctetString("default"),AuthMD5.ID,new OctetString("12345678"),PrivDES.ID,new OctetString("MD5DESUserPrivPassword")));
	   	//snmp.getUSM().addUser(new OctetString("seminario"),new UsmUser(new OctetString("seminario"),AuthMD5.ID,new OctetString("12345678"),PrivDES.ID,new OctetString("12345678")));
	   	//snmp.getUSM().addUser(new OctetString("seminario"),new UsmUser(new OctetString("seminario"),AuthMD5.ID,new OctetString("12345678"),null,null));
/*	   	if (claveEncriptacion==null){
	   	  snmp.getUSM().addUser(new OctetString(nombreUsuario),new UsmUser(new OctetString(nombreUsuario),AuthMD5.ID,new OctetString(claveAutenticacion),null,null));	
	   	}else{
	   	  snmp.getUSM().addUser(new OctetString(nombreUsuario),new UsmUser(new OctetString(nombreUsuario),AuthMD5.ID,new OctetString(claveAutenticacion),PrivDES.ID,new OctetString(claveEncriptacion)));
	   	}*/
	   	if (claveEncriptacion==null){
	   	  snmp.getUSM().addUser(new OctetString(nombreUsuario),new UsmUser(new OctetString(nombreUsuario),metAut,new OctetString(claveAutenticacion),null,null));	
	   	}else{
	   	  snmp.getUSM().addUser(new OctetString(nombreUsuario),new UsmUser(new OctetString(nombreUsuario),metAut,new OctetString(claveAutenticacion),metPriv,new OctetString(claveEncriptacion)));
	   	}	   	
	   	
	   	// establecer los parametros
		UserTarget target = new UserTarget();
		target.setAddress(targetAddress);
		target.setRetries(intentos);
		target.setTimeout(tiempoEspera);
		target.setVersion(SnmpConstants.version3);
		target.setSecurityLevel(SecurityLevel);
		//target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
		target.setSecurityName(new OctetString(nombreUsuario));
		
		// creando la PDU
		PDU pdu = new ScopedPDU();
		for (int i=0;i<(requerimiento.size());i++){
      	  //System.out.println("dato"+i+": "+String.valueOf(requerimiento.get(i)));	
      	  pdu.add(new VariableBinding(new OID(String.valueOf(requerimiento.get(i)))));
    	}
	    pdu.setType(PDU.GETNEXT);
	    		
		 // enviando la PDU
		 ResponseEvent response = snmp.send(pdu, target);
		 // extract the response PDU (could be null if timed out)
		 PDU responsePDU = response.getResponse();
		 dato="";  	
		 if (responsePDU == null) {
	       //System.out.println("Request "+responsePDU+" timed out");
	       dato=erroresGenerales04;
	     }else {
	       //System.out.println("Received response "+responsePDU);
	       dato=String.valueOf(responsePDU);
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
		   //System.out.println("dato: "+dato.indexOf("1.3.6.1.6.3.15.1.1.3.0"));
		   if (dato.indexOf("1.3.6.1.6.3.15.1.1.3.0")>=0){
		   	 dato = dato.concat(erroresGenerales29);
		   }
		   if (dato.indexOf("1.3.6.1.6.3.15.1.1.5.0")>=0){
		   	 dato = dato.concat(erroresGenerales30);
		   }
		   if (dato.indexOf("1.3.6.1.6.3.15.1.1.6.0")>=0){
		   	 dato = dato.concat(erroresGenerales31);
		   }
		   //Fin del tratamiento de la respuesta
	     }
    }catch (Exception e){e.printStackTrace();}
    
    respuesta = dato;
    	
  	return respuesta;
  
  }

  public String getv3(String ip, String puerto, String nombreUsuario, String claveAutenticacion, String claveEncriptacion, int SecurityLevel, int intentos, int tiempoEspera, Vector requerimiento, OID metAut, OID metPriv){
    
    String respuesta = "";
    try{
	    Address targetAddress = GenericAddress.parse("udp:"+ip+"/"+puerto);
	    TransportMapping transport = new DefaultUdpTransportMapping();
	   	snmp = new Snmp(transport);
	   	USM usm = new USM(SecurityProtocols.getInstance(),new OctetString(MPv3.createLocalEngineID()), 0);
	   	SecurityModels.getInstance().addSecurityModel(usm);
	   	transport.listen();
	   		
	   	// aadir el usuario al USM
	   	//snmp.getUSM().addUser(new OctetString("default"),new UsmUser(new OctetString("default"),AuthMD5.ID,new OctetString("12345678"),PrivDES.ID,new OctetString("MD5DESUserPrivPassword")));
	   	//snmp.getUSM().addUser(new OctetString("seminario"),new UsmUser(new OctetString("seminario"),AuthMD5.ID,new OctetString("12345678"),PrivDES.ID,new OctetString("12345678")));
	   	//snmp.getUSM().addUser(new OctetString("seminario"),new UsmUser(new OctetString("seminario"),AuthMD5.ID,new OctetString("12345678"),null,null));
/*	   	if (claveEncriptacion==null){
	   	  snmp.getUSM().addUser(new OctetString(nombreUsuario),new UsmUser(new OctetString(nombreUsuario),AuthMD5.ID,new OctetString(claveAutenticacion),null,null));	
	   	}else{
	   	  snmp.getUSM().addUser(new OctetString(nombreUsuario),new UsmUser(new OctetString(nombreUsuario),AuthMD5.ID,new OctetString(claveAutenticacion),PrivDES.ID,new OctetString(claveEncriptacion)));
	   	}*/

	   	if (claveEncriptacion==null){
	   	  snmp.getUSM().addUser(new OctetString(nombreUsuario),new UsmUser(new OctetString(nombreUsuario),metAut,new OctetString(claveAutenticacion),null,null));	
	   	}else{
	   	  snmp.getUSM().addUser(new OctetString(nombreUsuario),new UsmUser(new OctetString(nombreUsuario),metAut,new OctetString(claveAutenticacion),metPriv,new OctetString(claveEncriptacion)));
	   	}

	   	
	   	// establecer los parametros
		UserTarget target = new UserTarget();
		target.setAddress(targetAddress);
		target.setRetries(intentos);
		target.setTimeout(tiempoEspera);
		target.setVersion(SnmpConstants.version3);
		target.setSecurityLevel(SecurityLevel);
		//target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
		target.setSecurityName(new OctetString(nombreUsuario));
		
		// creando la PDU
		PDU pdu = new ScopedPDU();
		for (int i=0;i<(requerimiento.size());i++){
      	  //System.out.println("dato"+i+": "+String.valueOf(requerimiento.get(i)));	
      	  pdu.add(new VariableBinding(new OID(String.valueOf(requerimiento.get(i)))));
    	}
	    pdu.setType(PDU.GET);
	    		
		 // enviando la PDU
		 ResponseEvent response = snmp.send(pdu, target);
		 // extract the response PDU (could be null if timed out)
		 PDU responsePDU = response.getResponse();
		 dato="";  	
		 if (responsePDU == null) {
	       //System.out.println("Request "+responsePDU+" timed out");
	       dato=erroresGenerales04;
	     }else {
	       //System.out.println("Received response "+responsePDU);
	       dato=String.valueOf(responsePDU);
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
		   //System.out.println("dato: "+dato.indexOf("1.3.6.1.6.3.15.1.1.3.0"));
		   if (dato.indexOf("1.3.6.1.6.3.15.1.1.3.0")>=0){
		   	 dato = dato.concat(erroresGenerales29);
		   }
		   if (dato.indexOf("1.3.6.1.6.3.15.1.1.5.0")>=0){
		   	 dato = dato.concat(erroresGenerales30);
		   }
		   if (dato.indexOf("1.3.6.1.6.3.15.1.1.6.0")>=0){
		   	 dato = dato.concat(erroresGenerales31);
		   }
		   //Fin del tratamiento de la respuesta
	       
	     }
    }catch (Exception e){e.printStackTrace();}
    
    respuesta = dato;
    	
  	return respuesta;
  
  }

  public String setv3(String ip, String puerto, String nombreUsuario, String claveAutenticacion, String claveEncriptacion, int SecurityLevel, int intentos, int tiempoEspera, Vector requerimiento, Variable[] valor, OID metAut, OID metPriv){
    
    String respuesta = "";
    try{
	    Address targetAddress = GenericAddress.parse("udp:"+ip+"/"+puerto);
	    TransportMapping transport = new DefaultUdpTransportMapping();
	   	snmp = new Snmp(transport);
	   	USM usm = new USM(SecurityProtocols.getInstance(),new OctetString(MPv3.createLocalEngineID()), 0);
	   	SecurityModels.getInstance().addSecurityModel(usm);
	   	transport.listen();
	   		
	   	// aadir el usuario al USM
	   	//snmp.getUSM().addUser(new OctetString("default"),new UsmUser(new OctetString("default"),AuthMD5.ID,new OctetString("12345678"),PrivDES.ID,new OctetString("MD5DESUserPrivPassword")));
	   	//snmp.getUSM().addUser(new OctetString("seminario"),new UsmUser(new OctetString("seminario"),AuthMD5.ID,new OctetString("12345678"),PrivDES.ID,new OctetString("12345678")));
	   	//snmp.getUSM().addUser(new OctetString("seminario"),new UsmUser(new OctetString("seminario"),AuthMD5.ID,new OctetString("12345678"),null,null));
	   	if (claveEncriptacion==null){
	   	  snmp.getUSM().addUser(new OctetString(nombreUsuario),new UsmUser(new OctetString(nombreUsuario),metAut,new OctetString(claveAutenticacion),null,null));	
	   	}else{
	   	  snmp.getUSM().addUser(new OctetString(nombreUsuario),new UsmUser(new OctetString(nombreUsuario),metAut,new OctetString(claveAutenticacion),metPriv,new OctetString(claveEncriptacion)));
	   	}
	   	
	   	// establecer los parametros
		UserTarget target = new UserTarget();
		target.setAddress(targetAddress);
		target.setRetries(intentos);
		target.setTimeout(tiempoEspera);
		target.setVersion(SnmpConstants.version3);
		target.setSecurityLevel(SecurityLevel);
		//target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
		target.setSecurityName(new OctetString(nombreUsuario));
		
		// creando la PDU
		PDU pdu = new ScopedPDU();
		for (int i=0;i<(requerimiento.size());i++){
      	  VariableBinding vb = new VariableBinding(new OID(String.valueOf(requerimiento.get(i))));
      	  //System.out.println(String.valueOf(requerimiento.get(i))+" "+valor[i]);	
	      vb.setVariable(valor[i]);
	      pdu.add(vb);
    	}
    	pdu.setType(PDU.SET);
	    
	    		
		 // enviando la PDU
		 ResponseEvent response = snmp.send(pdu, target);
		 // extract the response PDU (could be null if timed out)
		 PDU responsePDU = response.getResponse();
		 dato="";  	
		 if (responsePDU == null) {
	       //System.out.println("Request "+responsePDU+" timed out");
	       dato=erroresGenerales04;
	     }else {
		        //System.out.println("Received response "+responsePDU);
		        dato=String.valueOf(responsePDU);
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
             
                if(responsePDU.getErrorStatus()!=SnmpConstants.SNMP_ERROR_SUCCESS){
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_AUTHORIZATION_ERROR){dato=dato.concat(" Error: AUTHORIZATION ERROR...\n");}
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_BAD_VALUE){dato=dato.concat(" Error: BAD VALUE...\n");}
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_COMMIT_FAILED){dato=dato.concat(" Error: COMMIT FAILED...\n");}
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_GENERAL_ERROR){dato=dato.concat(" Error: GENERAL ERROR...\n");}
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_INCONSISTENT_NAME){dato=dato.concat(" Error: INCONSISTENT NAME...\n");}
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_INCONSISTENT_VALUE){dato=dato.concat(" Error: INCONSISTENT VALUE...\n");}
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_NO_ACCESS){dato=dato.concat(" Error: NO ACCESS...\n");}
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_NO_CREATION){dato=dato.concat(" Error: NO CREATION...\n");}
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_NO_SUCH_NAME){dato=dato.concat(" Error: NO SUCH NAME...\n");}
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_NOT_WRITEABLE){dato=dato.concat(" Error: NOT WRITEABLE...\n");}
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_READ_ONLY){dato=dato.concat(" Error: READ ONLY...\n");}
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_RESOURCE_UNAVAILABLE){dato=dato.concat(" Error: RESOURCE UNAVAILABLE...\n");}
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_SUCCESS){dato=dato.concat(" Error: SUCCESS...\n");}
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_TOO_BIG){dato=dato.concat(" Error: TOO BIG...\n");}
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_UNDO_FAILED){dato=dato.concat(" Error: UNDO FAILED...\n");}
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_WRONG_ENCODING){dato=dato.concat(" Error: WRONG ENCODING...\n");}
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_WRONG_LENGTH){dato=dato.concat(" Error: WRONG ENGTH...\n");}
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_WRONG_TYPE){dato=dato.concat(" Error: WRONG TYPE...\n");}
	             	if(responsePDU.getErrorStatus()==SnmpConstants.SNMP_ERROR_WRONG_VALUE){dato=dato.concat(" Error: WRONG VALUE...\n");}
                }
                   //System.out.println("dato: "+dato.indexOf("1.3.6.1.6.3.15.1.1.3.0"));
				   if (dato.indexOf("1.3.6.1.6.3.15.1.1.3.0")>=0){
				   	 dato = dato.concat(erroresGenerales29);
				   }
				   if (dato.indexOf("1.3.6.1.6.3.15.1.1.5.0")>=0){
				   	 dato = dato.concat(erroresGenerales30);
				   }
				   if (dato.indexOf("1.3.6.1.6.3.15.1.1.6.0")>=0){
				   	 dato = dato.concat(erroresGenerales31);
				   }
             	
             	//System.out.println("DATO: "+dato);
	            	            
	            //dato=String.valueOf(response);
	     }
    }catch (Exception e){e.printStackTrace();}
    
    respuesta = dato;
    	
  	return respuesta;
    
  }

  public static void main(String args[]) throws Exception {
  	
  	//SNMPv3 manager = new SNMPv3();
  	
  	//Vector pruebaCompuesto = new Vector();
  	//pruebaCompuesto.add(".1.3.6.1.2.1.1.1");
  	//pruebaCompuesto.add(".1.3.6.1.2.1.1.4.0");
  	//pruebaCompuesto.add(".1.3.6.1.2.1.1.5.0");
  	//pruebaCompuesto.add(".1.3.6.1.2.1.1.3");
  	//pruebaCompuesto.add(".1.3.6.1.2.1.1.2");
  	
  	//int tamao = 1;
  	//Variable valor[] = new Variable[tamao]; 
  	//valor[0]= new OctetString(String.valueOf("Contact 10"));
  	//valor[1]= new OctetString(String.valueOf("Name 9"));
  	//valor[2]= new OctetString(String.valueOf("descriptor 6"));
  	
  	//System.out.println("GetNextv3: "+manager.getNextv3("192.168.0.5", "161", "seminario", "12345678", "12345678", SecurityLevel.AUTH_PRIV,3, 1500, pruebaCompuesto,AuthMD5.ID,PrivDES.ID));
  	//System.out.println("GetNextv3: "+manager.getNextv3("192.168.0.5", "161", "seminario", "12345678", "12345678", SecurityLevel.AUTH_PRIV,3, 1500, pruebaCompuesto,AuthMD5.ID,PrivDES.ID));
  	//System.out.println("GetNextv3: "+manager.getNextv3("192.168.0.5", "161", "seminario", "12345678", "12345678", SecurityLevel.AUTH_NOPRIV,2, 1500, new int[] {1,3,6,1,2,1,1,5},AuthMD5.ID,PrivDES.ID));
  	//System.out.println("GetNextv3: "+manager.getNextv3("192.168.0.5", "161", "seminario", "12345678", "12345678", SecurityLevel.NOAUTH_NOPRIV,2, 1500, new int[] {1,3,6,1,2,1,1,5},AuthMD5.ID,PrivDES.ID));
  	//System.out.println("GetNextv3: "+manager.getNextv3("192.168.0.25", "161", "seminario", "12345678", "12345678", SecurityLevel.NOAUTH_PRIV,2, 1500, new int[] {1,3,6,1,2,1,1,5}));
  	
  	//System.out.println("Getv3: "+manager.getv3("192.168.0.5", "161", "seminario", "12345678", "12345678", SecurityLevel.AUTH_PRIV,3, 1500, pruebaCompuesto,AuthMD5.ID,PrivDES.ID));
  	//System.out.println("GetBulkv3: "+manager.getBulkv3("192.168.0.20", "161", "seminario", "12345678", "12345678", SecurityLevel.AUTH_PRIV,2, 1500, new int[] {1,3,6,1,2,1,1,5},0, 100));
  	//System.out.println("GetBulkv3: "+manager.getBulkv3("192.168.0.5", "161", "seminario", "12345678", "12345678", SecurityLevel.AUTH_PRIV,2, 1500, pruebaCompuesto,2, 5,AuthMD5.ID,PrivDES.ID));
  	//System.out.println("GetBulkv3: "+manager.getBulkv3("192.168.0.5", "161", "seminario", "12345678", "12345678", SecurityLevel.AUTH_PRIV,2, 1500, pruebaCompuesto,0, 5,AuthMD5.ID,PrivDES.ID));
  	
  	//NonRepeaters(0);//0  <0 se trae todo, si vale 1  >1 se trae solo 1 valor. - pedir por pantalla
	//MaxRepetitions(100);// cantidad de variables que va a retornar - pedir por pantalla
  	
  	//Variable valor;
    /***************************************************************************************/
    // AQUI DEBEN IR TODOS LOS TIPOS DE DATOS, NO SOLO OCTETSTRING.
    //valor = new OctetString(String.valueOf("Prueba desde 1 sola clase de SNMPv3"));
       
    /***************************************************************************************/
  	//System.out.println("Setv3: "+manager.setv3("192.168.0.20", "161", "public", 2, 1500,new int[] {1,3,6,1,2,1,1,5,0}, valor));
    //System.out.println("Setv3: "+manager.setv3("192.168.0.5", "161", "seminario", "12345678", "12345678", SecurityLevel.AUTH_PRIV,2, 1500, pruebaCompuesto, valor,AuthMD5.ID,PrivDES.ID));
    //System.out.println("Getv3: "+manager.getv3("192.168.0.5", "161", "seminario", "12345678", "12345678", SecurityLevel.AUTH_PRIV,3, 1500, pruebaCompuesto,AuthMD5.ID,PrivDES.ID));
    //System.out.println("--- FIN DEL PROGRAMA ---");
  }



}