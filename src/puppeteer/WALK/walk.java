/*
 *		WALK - Status: LISTO!!!
 */
package puppeteer.WALK;

import java.io.*;
import java.util.*;
import javax.swing.JTextArea;
 
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

//////////////////////////////////////////
import net.percederberg.mibble.*;
import net.percederberg.mibble.value.*;
import mibblebrowser.MibNode;
import mibblebrowser.MibTreeBuilder;
//////////////////////////////////////////

public class walk{
  
  private String erroresGenerales07 = "Error: El agente consultado no contiene datos para el OID especificado...\n";
  private String erroresGenerales08 = "Error: El usuario no se encuentra en el Agente...\n";
  private String erroresGenerales09 = "Error: La clave y/o el algoritmo de autenticacin proporcionados son incorrectos...\n";
  private String erroresGenerales10 = "Error: La clave y/o el algoritmo de encriptacin proporcionados son incorrectos...\n";
  private String erroresGenerales11 = "Error: Tiempo de espera excedido...\n";
  private String erroresGenerales12 = "Error: El OID especificado es mayor que el mayor OID de la MIB consultada...\n";
  private String erroresGenerales13 = "La variable recibida no es sucesora de la requerida:\n";
  private String erroresGenerales17 = "Total de requerimientos enviados:   ";
  private String erroresGenerales18 = "Total de respuestas recibidas:      ";
  private String erroresGenerales19 = "Tiempo total del Walk:              ";
  private String erroresGenerales20 = " milisegundos";
  
  public void cambiarIdiomaAMensajes(String s07,String s08,String s09,String s10,String s11,String s12,String s13,String s17,String s18,String s19,String s20){
  	erroresGenerales07 = s07;
    erroresGenerales08 = s08;
    erroresGenerales09 = s09;
    erroresGenerales10 = s10;
    erroresGenerales11 = s11;
    erroresGenerales12 = s12;
    erroresGenerales13 = s13;
    erroresGenerales17 = s17;
    erroresGenerales18 = s18;
    erroresGenerales19 = s19;
    erroresGenerales20 = s20;
  }

  private String walkRealizado = "";
  
  public void limpiarWalkRealizado(){
  	walkRealizado = "";
  }
	
  public String getWalkRealizado(){
    return walkRealizado; 	
  }

  public void walkSNMPv1(String ip, String puerto, String comunidad, int intentos, int tiempoEspera, int[] requerimiento,long limite){
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
	    pdu.add(new VariableBinding(new OID(requerimiento)));
	    pdu.setType(PDU.GETNEXT);
	    
	    // Para enviar y procesar el pdu para hacer el walk
	    OID rootOID = pdu.get(0).getOid();
		PDU response = null;
		int objects = 0;
		int requests = 0;
		long startTime = System.currentTimeMillis();

		if (limite==0){
			do {
				requests++;
				ResponseEvent responseEvent = snmp.send(pdu, target);
				response = responseEvent.getResponse();
				//System.out.println("response "+response);
				if (response != null) {
					objects += response.size();
				}
				//Aqui se haria el setText(walkRealizado);
				//System.out.println("Walking MIB - "+requests);
				//jta.setText("Walking MIB - "+requests);
			}
			while (!procesarWalk(response, pdu, rootOID));
		}else{
			do {
				requests++;
				ResponseEvent responseEvent = snmp.send(pdu, target);
				response = responseEvent.getResponse();
				//System.out.println("response "+response);
				if (response != null) {
					objects += response.size();
				}
				//Aqui se haria el setText(walkRealizado);
				//System.out.println("Walking MIB - "+requests);
				//jta.setText("Walking MIB - "+requests);
			}
			while ((!procesarWalk(response, pdu, rootOID))&&(requests<limite));
		}
		
		if(walkRealizado.equals("")){
		  //System.out.println("respuesta vacia");	
		  walkRealizado = walkRealizado.concat(erroresGenerales07);
		}
		
		//System.out.println();
		walkRealizado = walkRealizado.concat("\n");
		//System.out.println("Total requests sent:    "+requests);
		walkRealizado = walkRealizado.concat(erroresGenerales17+requests+"\n");
		//System.out.println("Total objects received: "+objects);
		walkRealizado = walkRealizado.concat(erroresGenerales18+objects+"\n");
		//System.out.println("Total walk time:        "+(System.currentTimeMillis()-startTime)+" milliseconds");	    
		walkRealizado = walkRealizado.concat(erroresGenerales19+(System.currentTimeMillis()-startTime)+erroresGenerales20+"\n");
	    
	  }catch (Exception e){e.printStackTrace();}
		
  }
	
  public void walkSNMPv2c(String ip, String puerto, String comunidad, int intentos, int tiempoEspera, int[] requerimiento,long limite){
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
	    pdu.add(new VariableBinding(new OID(requerimiento)));
	    pdu.setType(PDU.GETNEXT);
	    
	    // Para enviar y procesar el pdu para hacer el walk
	    OID rootOID = pdu.get(0).getOid();
		PDU response = null;
		int objects = 0;
		int requests = 0;
		long startTime = System.currentTimeMillis();

		if (limite==0){
			do {
				requests++;
				ResponseEvent responseEvent = snmp.send(pdu, target);
				response = responseEvent.getResponse();
				if (response != null) {
					objects += response.size();
				}
				//Aqui se haria el setText(walkRealizado);
				//System.out.println("Walking MIB Tree - "+requests);
			}
			while (!procesarWalk(response, pdu, rootOID));
		}else{
			do {
				requests++;
				ResponseEvent responseEvent = snmp.send(pdu, target);
				response = responseEvent.getResponse();
				if (response != null) {
					objects += response.size();
				}
				//Aqui se haria el setText(walkRealizado);
				//System.out.println("Walking MIB Tree - "+requests);
			}
			while ((!procesarWalk(response, pdu, rootOID))&&(requests<limite));
		}
		if(walkRealizado.equals("")){
		  //System.out.println("respuesta vacia");	
		  walkRealizado = walkRealizado.concat(erroresGenerales07);
		}
		//System.out.println();
		walkRealizado = walkRealizado.concat("\n");
		//System.out.println("Total requests sent:    "+requests);
		walkRealizado = walkRealizado.concat(erroresGenerales17+requests+"\n");
		//System.out.println("Total objects received: "+objects);
		walkRealizado = walkRealizado.concat(erroresGenerales18+objects+"\n");
		//System.out.println("Total walk time:        "+(System.currentTimeMillis()-startTime)+" milliseconds");	    
		walkRealizado = walkRealizado.concat(erroresGenerales19+(System.currentTimeMillis()-startTime)+erroresGenerales20+"\n");
	    
	  }catch (Exception e){e.printStackTrace();}
		
  }

  public void walkSNMPv3(String ip, String puerto, String nombreUsuario, String claveAutenticacion, String claveEncriptacion, int SecurityLevel, int intentos, int tiempoEspera, int[] requerimiento,long limite, OID metAut, OID metPriv){
    try{
	    Address targetAddress = GenericAddress.parse("udp:"+ip+"/"+puerto);
	    TransportMapping transport = new DefaultUdpTransportMapping();
	   	Snmp snmp = new Snmp(transport);
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
		pdu.add(new VariableBinding(new OID(requerimiento)));
	    pdu.setType(PDU.GETNEXT);
	    		
		 // Para enviar y procesar el pdu para hacer el walk
	    OID rootOID = pdu.get(0).getOid();
		PDU response = null;
		int objects = 0;
		int requests = 0;
		long startTime = System.currentTimeMillis();

		if (limite==0){
			do {
				requests++;
				ResponseEvent responseEvent = snmp.send(pdu, target);
				response = responseEvent.getResponse();
				if (response != null) {
					objects += response.size();
				}
				//Aqui se haria el setText(walkRealizado);
				//System.out.println("Walking MIB Tree - "+requests);
			}
			while (!procesarWalk(response, pdu, rootOID));
		}else{
			do {
				requests++;
				ResponseEvent responseEvent = snmp.send(pdu, target);
				response = responseEvent.getResponse();
				if (response != null) {
					objects += response.size();
				}
				//Aqui se haria el setText(walkRealizado);
				//System.out.println("Walking MIB Tree - "+requests);
			}
			while ((!procesarWalk(response, pdu, rootOID))&&(requests<limite));
		}
		if(walkRealizado.equals("")){
		  //System.out.println("respuesta vacia");	
		  walkRealizado = walkRealizado.concat(erroresGenerales07);
		}
		//System.out.println();
		walkRealizado = walkRealizado.concat("\n");
		//System.out.println("Total requests sent:    "+requests);
		walkRealizado = walkRealizado.concat(erroresGenerales17+requests+"\n");
		//System.out.println("Total objects received: "+objects);
		walkRealizado = walkRealizado.concat(erroresGenerales18+objects+"\n");
		//System.out.println("Total walk time:        "+(System.currentTimeMillis()-startTime)+" milliseconds");	    
		walkRealizado = walkRealizado.concat(erroresGenerales19+(System.currentTimeMillis()-startTime)+erroresGenerales20+"\n");
	    
	  }catch (Exception e){e.printStackTrace();}
   }

  private boolean procesarWalk(PDU response, PDU request, OID rootOID) {
  		//System.out.println("Error 0 - "+response);
		if ((response == null) || (response.getErrorStatus() != 0)|| (response.getType() == PDU.REPORT)){
			
			//System.out.println("dato: "+dato.indexOf("1.3.6.1.6.3.15.1.1.3.0"));
		    if (String.valueOf(response).indexOf("1.3.6.1.6.3.15.1.1.3.0")>=0){
		   	  //walkRealizado = walkRealizado.concat("Error: El usuario no se encuentra en el Agente...\n");
		   	  walkRealizado = walkRealizado.concat(erroresGenerales08);
		    }else{
		      if (String.valueOf(response).indexOf("1.3.6.1.6.3.15.1.1.5.0")>=0){
			   	 //walkRealizado = walkRealizado.concat("Error: La clave y/o el algoritmo de autenticacin proporcionados son incorrectos...\n");
			   	 walkRealizado = walkRealizado.concat(erroresGenerales09);
			  }else{
			    if (String.valueOf(response).indexOf("1.3.6.1.6.3.15.1.1.6.0")>=0){
			   	  //walkRealizado = walkRealizado.concat("Error: La clave y/o el algoritmo de encriptacin proporcionados son incorrectos...\n");
			   	  walkRealizado = walkRealizado.concat(erroresGenerales10);
			    }else{
		      	  //System.out.println("Error: Tiempo de espera excedido...\nError 1 - "+response);
				  //walkRealizado = walkRealizado.concat("Error: Tiempo de espera excedido...\n");
				  walkRealizado = walkRealizado.concat(erroresGenerales11);
			    }
		      }	
		    }
		    
			return true;
		}
		boolean finished = false;
		OID lastOID = request.get(0).getOid();
		for (int i=0; (!finished) && (i<response.size()); i++) {
			VariableBinding vb = response.get(i);
			if ((vb.getOid() == null) || (vb.getOid().size() < rootOID.size())|| (rootOID.leftMostCompare(rootOID.size(), vb.getOid()) != 0)) {
				//System.out.println("Error 2 - "+vb.getOid());
				//System.out.println("Error 2 - "+vb.getOid().size());
				//System.out.println("Error 2 - "+rootOID);
				//System.out.println("Error 2 - "+rootOID.size());
				//System.out.println("Error 2 - "+rootOID.leftMostCompare(rootOID.size(), vb.getOid()));
				//System.out.println("Error 2 - "+response);
				if ((rootOID.leftMostCompare(rootOID.size(), vb.getOid()))>0){
				  //System.out.println("Error: El OID especificado es mayor que el mayor OID de la MIB consultada...\nError 2 - "+response);
				  walkRealizado = walkRealizado.concat(erroresGenerales12);
				}
				finished = true;
			} else {
			  if (Null.isExceptionSyntax(vb.getVariable().getSyntax())) {
				walkRealizado = walkRealizado.concat(vb.toString()+"\n");
				//System.out.println(vb.toString());
				//System.out.println("Error 3 - "+response);
				finished = true;
			  }else{
				if (vb.getOid().compareTo(lastOID) <= 0) {
				  //System.out.println("La variable recibida no es sucesora de la requerida:");
				  //System.out.println(vb.toString() + " <= "+lastOID);
				  walkRealizado = walkRealizado.concat(erroresGenerales13);
				  walkRealizado = walkRealizado.concat(vb.toString() + " <= "+lastOID+"\n");
				  //System.out.println("Error 4 - "+response);
				  finished = true;
				  
			    }else{
				  //Aqui se imprime el mensaje ppal
				  //System.out.println("request: "+request);
				  //System.out.println("response: "+response);
				  //System.out.println(vb.toString());
				  walkRealizado = walkRealizado.concat(vb.toString()+"\n");
				  lastOID = vb.getOid();
				  
				  //MibValueSymbol  symbolTemp = mib.getSymbolByOid("1.3.6.1.2.1.3.1.1.3");
	      		  //System.out.println("datos de ahora: "+symbolTemp);
	      		  //System.out.println("valor de getName: "+(MibTreeBuilder.getInstance().getNode(symbolTemp)).getName());
				  
			    }
			  }
			}
		}
		if (response.size() == 0) {
			//System.out.println("Error 5 - "+response);
			finished = true;
		}
		if (!finished) {
			//System.out.println("Error 6 - "+response);
			VariableBinding next = response.get(response.size()-1);
			next.setVariable(new Null());
			request.set(0, next);
			request.setRequestID(new Integer32(0));
		}
		return finished;
  }
	
  public int[] pasarOID(String OID){
	  	int digitos=0;
		StringTokenizer Token = new StringTokenizer (OID,".");
		while(Token.hasMoreTokens()){
		  Token.nextToken();
		  digitos++;	
		}
	  	int tamao=digitos;
	  	int requerimiento[] = new int[tamao];
		int i=0;
		StringTokenizer Token1 = new StringTokenizer (OID,".");
		while(Token1.hasMoreTokens()){
		  requerimiento[i]=Integer.parseInt(Token1.nextToken());
		  i++;
		}
		return requerimiento;
  }
	
  public static void main(String[] args){
  	  	
		//walk p = new walk();
		//int requerimiento[] = p.pasarOID(".1.3.6.1.2.1");
		//String ip = "192.168.0.5";
		//String puerto = "161";
		//String comunidad = "public";
		//int intentos = 3;
		//int tiempoEspera = 1500;
		//long limite = 0;
		
		//requerimiento = p.pasarOID(".1.3.6.1.2.1.1");
		//requerimiento = p.pasarOID(".1.3.6.1.2.1.4.22");
		//requerimiento = p.pasarOID("111");
		//requerimiento = p.pasarOID(".1.3.6.1.2.1.4.22");
		
		
		//p.walkSNMPv1(ip,puerto,comunidad,intentos,tiempoEspera,requerimiento,100);
		//p.walkSNMPv1(ip,puerto,comunidad,intentos,tiempoEspera,requerimiento,limite);
		//System.out.println("\n\n\nIMPRESION DE LA VARIABLE WALK REALIZADO: -SNMPv1-\n\n\n"+p.getWalkRealizado());
		//p.limpiarWalkRealizado();
		
		//p.walkSNMPv2c(ip, puerto, comunidad, intentos, tiempoEspera,requerimiento,10);
		//System.out.println("\n\n\nIMPRESION DE LA VARIABLE WALK REALIZADO: -SNMPv2c-\n\n\n"+p.getWalkRealizado());
		//p.walkSNMPv2c(ip, puerto, comunidad, intentos, tiempoEspera,requerimiento,limite);
		//p.limpiarWalkRealizado();
		
		//String nombreUsuario = "seminario";
		//String claveAutenticacion = "12345678";
		//String claveEncriptacion = "12345678";
				
		//p.walkSNMPv3(ip,puerto,nombreUsuario,claveAutenticacion,claveEncriptacion,SecurityLevel.AUTH_PRIV,intentos,tiempoEspera,requerimiento,limite,AuthMD5.ID,PrivDES.ID);
		//System.out.println("\n\n\nIMPRESION DE LA VARIABLE WALK REALIZADO: -SNMPv3-\n\n\n"+p.getWalkRealizado());
		//p.limpiarWalkRealizado();
		//p.walkSNMPv3(ip,puerto,nombreUsuario,claveAutenticacion,claveEncriptacion,SecurityLevel.AUTH_NOPRIV,intentos,tiempoEspera,requerimiento,limite);
		//System.out.println("\n\n\nIMPRESION DE LA VARIABLE WALK REALIZADO\n\n\n"+p.getWalkRealizado());
		//p.walkSNMPv3(ip,puerto,nombreUsuario,claveAutenticacion,claveEncriptacion,SecurityLevel.AUTH_PRIV,intentos,tiempoEspera,requerimiento,10);
		//System.out.println("\n\n\nIMPRESION DE LA VARIABLE WALK REALIZADO: -SNMPv3 / encriptado-\n\n\n"+p.getWalkRealizado());
		//p.walkSNMPv3(ip,puerto,nombreUsuario,claveAutenticacion,claveEncriptacion,SecurityLevel.AUTH_PRIV,intentos,tiempoEspera,requerimiento,limite);
		//p.limpiarWalkRealizado();

		//System.out.println("\n\n\nIMPRESION DE LA VARIABLE WALK REALIZADO\n\n\n"+p.getWalkRealizado());
  }
}