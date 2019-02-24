/*
 *		Status: Listo
 */
 package puppeteer.TrapInform; 

import java.util.*;
import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.UserTarget;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.PrivDES;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.Null;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.UnsignedInteger32;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
 
//import org.snmp4j.*;
//import org.snmp4j.event.*;
//import org.snmp4j.mp.*;
//import org.snmp4j.security.*;
//import org.snmp4j.smi.*;
//import org.snmp4j.transport.*;

public class enviarTrapInform{
  
  private Snmp snmp = null;
  private static boolean respondio;
  private static String dato;  	  


  public String trapv1(String ip, String puerto, String comunidad, int intentos, int tiempoEspera, String requerimiento,String vbsOID,int tipoTrapv1,int especificTrap){
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
	    PDUv1 pdu = new PDUv1();
	    pdu.setType(PDU.V1TRAP);
		pdu.setGenericTrap(tipoTrapv1);
		if (tipoTrapv1==PDUv1.ENTERPRISE_SPECIFIC){
			pdu.setSpecificTrap(especificTrap);			
		}
		
		pdu.setTimestamp(20000);
		
		Vector vbs = new Vector();
	    vbs = getVariableBinding(requerimiento);
		pdu.setEnterprise(new OID(vbsOID));
		
		
		
		
    
	    // enviando el PDU
	    respondio=true;
	    dato="";
	    ResponseListener listener = new ResponseListener() {
	    public void onResponse(ResponseEvent event) {
	        PDU response = event.getResponse();
	        PDU request = event.getRequest();
	        if (response == null) {
	            //System.out.println("Request "+request+" timed out");
	            dato="Tiempo de espera excedido...";
	        }
	        else {
	            //System.out.println("Received response "+response+" on request "+request);
	            dato=String.valueOf(response);
	            //Tratamiento de la respuesta
	            //System.out.println("respuesta: "+dato);
	    	}
	    	respondio=false;
		}};
		snmp.send(pdu, target, null, listener);
		//snmp.trap(pdu,target);
		//System.out.println("envie el trapv1...");
	    snmp.close();
    
    }catch (Exception e){e.printStackTrace();}
        	
  	return respuesta;
  
  }

  private Vector getVariableBinding(String varbind) {
		Vector v = new Vector(varbind.length());
		String oid = null;
		char type = 'i';
		String value = null;
		int equal = varbind.indexOf("={");
		if (equal > 0) {
			oid = varbind.substring(0, equal);
			type = varbind.charAt(equal+2);
			value = varbind.substring(varbind.indexOf('}')+1);
		}else{
			v.add(new VariableBinding(new OID(varbind)));
			return v;
		}

		VariableBinding vb = new VariableBinding(new OID(oid));
		if (value != null) {
			Variable variable;
			switch (type) {
				case 'i':
					variable = new Integer32(Integer.parseInt(value));
					break;
				case 'u':
					variable = new UnsignedInteger32(Long.parseLong(value));
					break;
				case 's':
					variable = new OctetString(value);
					break;
				case 'x':
					variable = OctetString.fromString(value, ':', 16);
					break;
				case 'd':
					variable = OctetString.fromString(value, '.', 10);
					break;
				case 'b':
					variable = OctetString.fromString(value, ' ', 2);
					break;
				case 'n':
					variable = new Null();
					break;
				case 'o':
					variable = new OID(value);
					break;
				case 't':
					variable = new TimeTicks(Long.parseLong(value));
					break;
				case 'a':
					variable = new IpAddress(value);
					break;
				default:
					throw new IllegalArgumentException("Variable type "+type+" not supported");
			}
			vb.setVariable(variable);
		}
		v.add(vb);
		return v;
	  }
	  
  public String trapv2c(String ip, String puerto, String comunidad, int intentos, int tiempoEspera, String requerimiento,String vbsOID){
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
	    Vector vbs = new Vector();
	    if (
	    	(vbsOID.equals(String.valueOf(SnmpConstants.coldStart)))||
  			(vbsOID.equals(String.valueOf(SnmpConstants.warmStart)))||
  			(vbsOID.equals(String.valueOf(SnmpConstants.linkDown)))||
  			(vbsOID.equals(String.valueOf(SnmpConstants.linkUp)))||
  			(vbsOID.equals(String.valueOf(SnmpConstants.authenticationFailure)))
  		   ){
	    	if ((vbs.size() == 0) ||((vbs.size() > 1) &&(!((VariableBinding) vbs.get(0)).getOid().equals(SnmpConstants.sysUpTime)))) {
					vbs.add(0, new VariableBinding(SnmpConstants.sysUpTime, new TimeTicks(0)));
				}
			if ((vbs.size() == 1) || ((vbs.size() > 2) &&(!((VariableBinding) vbs.get(1)).getOid().equals(SnmpConstants.snmpTrapOID)))) {
					vbs.add(1, new VariableBinding(SnmpConstants.snmpTrapOID, new OID(vbsOID)));
				}
	    }else{
		    vbs = getVariableBinding(requerimiento);
		    if ((vbs.size() == 0) ||((vbs.size() > 1) &&(!((VariableBinding) vbs.get(0)).getOid().equals(SnmpConstants.sysUpTime)))) {
					vbs.add(0, new VariableBinding(SnmpConstants.sysUpTime, new TimeTicks(0)));
				}
			if ((vbs.size() == 1) || ((vbs.size() > 2) &&(!((VariableBinding) vbs.get(1)).getOid().equals(SnmpConstants.snmpTrapOID)))) {
					vbs.add(1, new VariableBinding(SnmpConstants.snmpTrapOID, new OID(vbsOID)));
				}
		}
		
		for (int i=0; i<vbs.size(); i++) {
			pdu.add((VariableBinding)vbs.get(i));
		}
	    pdu.setType(PDU.TRAP);
    
	    // enviando el PDU
	    respondio=true;
	    dato="";
	    ResponseListener listener = new ResponseListener() {
	    public void onResponse(ResponseEvent event) {
	        PDU response = event.getResponse();
	        PDU request = event.getRequest();
	        if (response == null) {
	            //System.out.println("Request "+request+" timed out");
	            dato="Tiempo de espera excedido...";
	        }
	        else {
	            System.out.println("Received response "+response+" on request "+request);
	            dato=String.valueOf(response);
	            //Tratamiento de la respuesta
	            System.out.println("respuesta: "+dato);
	    	}
	    	respondio=false;
		}};
		snmp.send(pdu, target, null, listener);
		//System.out.println("envie el trapv2c...");
	    snmp.close();
    
    }catch (Exception e){e.printStackTrace();}
        	
  	return respuesta;
  
  }
  
  public String informv2c(String ip, String puerto, String comunidad, int intentos, int tiempoEspera, String requerimiento,String vbsOID){
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
	    Vector vbs = new Vector();
	    vbs = getVariableBinding(requerimiento);
	    if ((vbs.size() == 0) ||((vbs.size() > 1) &&(!((VariableBinding) vbs.get(0)).getOid().equals(SnmpConstants.sysUpTime)))) {
				vbs.add(0, new VariableBinding(SnmpConstants.sysUpTime, new TimeTicks(0)));
			}
		if ((vbs.size() == 1) || ((vbs.size() > 2) &&(!((VariableBinding) vbs.get(1)).getOid().equals(SnmpConstants.snmpTrapOID)))) {
				vbs.add(1, new VariableBinding(SnmpConstants.snmpTrapOID, new OID(vbsOID)));
			}
		for (int i=0; i<vbs.size(); i++) {
			pdu.add((VariableBinding)vbs.get(i));
		}
	    pdu.setType(PDU.INFORM);
    
	    // enviando el PDU
	    respondio=true;
	    dato="";
	    ResponseListener listener = new ResponseListener() {
	    public void onResponse(ResponseEvent event) {
	        PDU response = event.getResponse();
	        PDU request = event.getRequest();
	        if (response == null) {
	            //System.out.println("Request "+request+" timed out");
	            dato="Tiempo de espera excedido...";
	        }
	        else {
	            System.out.println("Received response "+response+" on request "+request);
	            dato=String.valueOf(response);
	            //Tratamiento de la respuesta
	            System.out.println("respuesta: "+dato);
	    	}
	    	respondio=false;
		}};
		snmp.send(pdu, target, null, listener);
		//System.out.println("envie el informv2c...");
	    snmp.close();
    
    }catch (Exception e){e.printStackTrace();}
        	
  	return respuesta;
  
  }
  
  
  public String trapv3(String ip, String puerto, String nombreUsuario, String claveAutenticacion, String claveEncriptacion, int SecurityLevel, int intentos, int tiempoEspera, String requerimiento,String vbsOID){
    String respuesta = "";
    
    try{
      	Address targetAddress = GenericAddress.parse("udp:"+ip+"/"+puerto);
	    TransportMapping transport = new DefaultUdpTransportMapping();
	   	snmp = new Snmp(transport);
	   	USM usm = new USM(SecurityProtocols.getInstance(),new OctetString(MPv3.createLocalEngineID()), 0);
	   	SecurityModels.getInstance().addSecurityModel(usm);
	   	transport.listen();
	   		
	   	// a�adir el usuario al USM
	   	//snmp.getUSM().addUser(new OctetString("default"),new UsmUser(new OctetString("default"),AuthMD5.ID,new OctetString("12345678"),PrivDES.ID,new OctetString("MD5DESUserPrivPassword")));
	   	//snmp.getUSM().addUser(new OctetString("seminario"),new UsmUser(new OctetString("seminario"),AuthMD5.ID,new OctetString("12345678"),PrivDES.ID,new OctetString("12345678")));
	   	//snmp.getUSM().addUser(new OctetString("seminario"),new UsmUser(new OctetString("seminario"),AuthMD5.ID,new OctetString("12345678"),null,null));
	   	if (claveEncriptacion==null){
	   	  snmp.getUSM().addUser(new OctetString(nombreUsuario),new UsmUser(new OctetString(nombreUsuario),AuthMD5.ID,new OctetString(claveAutenticacion),null,null));	
	   	}else{
	   	  snmp.getUSM().addUser(new OctetString(nombreUsuario),new UsmUser(new OctetString(nombreUsuario),AuthMD5.ID,new OctetString(claveAutenticacion),PrivDES.ID,new OctetString(claveEncriptacion)));
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
	    // creando el PDU
	    PDU pdu = new ScopedPDU();
	    Vector vbs = new Vector();
	    if (
	    	(vbsOID.equals(String.valueOf(SnmpConstants.coldStart)))||
  			(vbsOID.equals(String.valueOf(SnmpConstants.warmStart)))||
  			(vbsOID.equals(String.valueOf(SnmpConstants.linkDown)))||
  			(vbsOID.equals(String.valueOf(SnmpConstants.linkUp)))||
  			(vbsOID.equals(String.valueOf(SnmpConstants.authenticationFailure)))
  		   ){
	    	if ((vbs.size() == 0) ||((vbs.size() > 1) &&(!((VariableBinding) vbs.get(0)).getOid().equals(SnmpConstants.sysUpTime)))) {
					vbs.add(0, new VariableBinding(SnmpConstants.sysUpTime, new TimeTicks(0)));
				}
			if ((vbs.size() == 1) || ((vbs.size() > 2) &&(!((VariableBinding) vbs.get(1)).getOid().equals(SnmpConstants.snmpTrapOID)))) {
					vbs.add(1, new VariableBinding(SnmpConstants.snmpTrapOID, new OID(vbsOID)));
				}
	    }else{
		    vbs = getVariableBinding(requerimiento);
		    if ((vbs.size() == 0) ||((vbs.size() > 1) &&(!((VariableBinding) vbs.get(0)).getOid().equals(SnmpConstants.sysUpTime)))) {
					vbs.add(0, new VariableBinding(SnmpConstants.sysUpTime, new TimeTicks(0)));
				}
			if ((vbs.size() == 1) || ((vbs.size() > 2) &&(!((VariableBinding) vbs.get(1)).getOid().equals(SnmpConstants.snmpTrapOID)))) {
					vbs.add(1, new VariableBinding(SnmpConstants.snmpTrapOID, new OID(vbsOID)));
				}
		}
		
		for (int i=0; i<vbs.size(); i++) {
			pdu.add((VariableBinding)vbs.get(i));
		}
	    pdu.setType(PDU.TRAP);
    
	    // enviando el PDU
	    respondio=true;
	    dato="";
	    ResponseListener listener = new ResponseListener() {
	    public void onResponse(ResponseEvent event) {
	        PDU response = event.getResponse();
	        PDU request = event.getRequest();
	        if (response == null) {
	            //System.out.println("Request "+request+" timed out");
	            dato="Tiempo de espera excedido...";
	        }
	        else {
	            System.out.println("Received response "+response+" on request "+request);
	            dato=String.valueOf(response);
	            //Tratamiento de la respuesta
	            System.out.println("respuesta: "+dato);
	    	}
	    	respondio=false;
		}};
		snmp.send(pdu, target, null, listener);
		//System.out.println("envie el trapv3...");
	    snmp.close();
    
    }catch (Exception e){e.printStackTrace();}
        	
  	return respuesta;
  
  }
  
  public String informv3(String ip, String puerto, String nombreUsuario, String claveAutenticacion, String claveEncriptacion, int SecurityLevel, int intentos, int tiempoEspera, String requerimiento,String vbsOID){
  	/*
  	System.out.println("ip "+ip); 
  	System.out.println("puerto "+puerto);
  	System.out.println("nombreUsuario "+nombreUsuario);
  	System.out.println("claveAutenticacion "+claveAutenticacion); 
  	System.out.println("claveEncriptacion "+claveEncriptacion);
  	System.out.println("SecurityLevel "+SecurityLevel); 
  	System.out.println("intentos "+intentos); 
  	System.out.println("tiempoEspera "+tiempoEspera);
  	System.out.println("requerimiento "+requerimiento);
  	System.out.println("vbsOID "+vbsOID);
  	*/
    String respuesta = "";
    
    try{
      	Address targetAddress = GenericAddress.parse("udp:"+ip+"/"+puerto);
	    TransportMapping transport = new DefaultUdpTransportMapping();
	   	snmp = new Snmp(transport);
	   	USM usm = new USM(SecurityProtocols.getInstance(),new OctetString(MPv3.createLocalEngineID()), 0);
	   	SecurityModels.getInstance().addSecurityModel(usm);
	   	transport.listen();
	   		
	   	// a�adir el usuario al USM
	   	//snmp.getUSM().addUser(new OctetString("default"),new UsmUser(new OctetString("default"),AuthMD5.ID,new OctetString("12345678"),PrivDES.ID,new OctetString("MD5DESUserPrivPassword")));
	   	//snmp.getUSM().addUser(new OctetString("seminario"),new UsmUser(new OctetString("seminario"),AuthMD5.ID,new OctetString("12345678"),PrivDES.ID,new OctetString("12345678")));
	   	//snmp.getUSM().addUser(new OctetString("seminario"),new UsmUser(new OctetString("seminario"),AuthMD5.ID,new OctetString("12345678"),null,null));
	   	if (claveEncriptacion==null){
	   	  snmp.getUSM().addUser(new OctetString(nombreUsuario),new UsmUser(new OctetString(nombreUsuario),AuthMD5.ID,new OctetString(claveAutenticacion),null,null));	
	   	}else{
	   	  snmp.getUSM().addUser(new OctetString(nombreUsuario),new UsmUser(new OctetString(nombreUsuario),AuthMD5.ID,new OctetString(claveAutenticacion),PrivDES.ID,new OctetString(claveEncriptacion)));
	   	}
	   	
	   	// establecer los parametros
		UserTarget target = new UserTarget();
		target.setAddress(targetAddress);
		target.setRetries(intentos);
		//truco, quitarlo luego
		target.setRetries(0);
		target.setTimeout(tiempoEspera);
		target.setVersion(SnmpConstants.version3);
		target.setSecurityLevel(SecurityLevel);
		//target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
		target.setSecurityName(new OctetString(nombreUsuario));
	    // creando el PDU
	    PDU pdu = new ScopedPDU();
	    Vector vbs = new Vector();
	    vbs = getVariableBinding(requerimiento);
	    if ((vbs.size() == 0) ||((vbs.size() > 1) &&(!((VariableBinding) vbs.get(0)).getOid().equals(SnmpConstants.sysUpTime)))) {
				vbs.add(0, new VariableBinding(SnmpConstants.sysUpTime, new TimeTicks(0)));
			}
		if ((vbs.size() == 1) || ((vbs.size() > 2) &&(!((VariableBinding) vbs.get(1)).getOid().equals(SnmpConstants.snmpTrapOID)))) {
				vbs.add(1, new VariableBinding(SnmpConstants.snmpTrapOID, new OID(vbsOID)));
			}
		for (int i=0; i<vbs.size(); i++) {
			pdu.add((VariableBinding)vbs.get(i));
		}
	    pdu.setType(PDU.INFORM);
    
	    // enviando el PDU
	    respondio=true;
	    dato="";
	    /*
	    ResponseListener listener = new ResponseListener() {
	    public void onResponse(ResponseEvent event) {
	        PDU response = event.getResponse();
	        PDU request = event.getRequest();
	        if (response == null) {
	            //System.out.println("Request "+request+" timed out");
	            dato="Tiempo de espera excedido...";
	        }
	        else {
	            System.out.println("Received response "+response+" on request "+request);
	            dato=String.valueOf(response);
	            //Tratamiento de la respuesta
	            System.out.println("respuesta: "+dato);
	    	}
	    	respondio=false;
		}};
		*/
		//snmp.sendPDU(pdu, target, null, listener);
		
		PDU response = null;
		ResponseEvent responseEvent=null;
		long startTime = System.currentTimeMillis();
		responseEvent = snmp.send(pdu, target);
		if (responseEvent != null) {
		  response = responseEvent.getResponse();
		  //System.out.println("Received response after "+(System.currentTimeMillis()-startTime)+" millis");
		  //System.out.println("La respuesta fue: "+responseEvent);
		  //System.out.println("respuesta: "+response);
		}
		
		
		
		
		//System.out.println("envie el informv3...");
	    snmp.close();
    
    }catch (Exception e){e.printStackTrace();}
        	
  	return respuesta;
  
  }	 
	  
  public static void main(String args[]) throws Exception {
  	
  	enviarTrapInform manager = new enviarTrapInform();
  	//TRAPSv1
  	/*
  	System.out.println(manager.trapv1("192.168.0.5", "162", "public", 3, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2854",PDUv1.COLDSTART,0));
  	System.out.println(manager.trapv1("192.168.0.5", "162", "public", 3, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2854",PDUv1.WARMSTART,0));
  	System.out.println(manager.trapv1("192.168.0.5", "162", "public", 3, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2854",PDUv1.LINKDOWN,0));
  	System.out.println(manager.trapv1("192.168.0.5", "162", "public", 3, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2854",PDUv1.LINKUP,0));
  	System.out.println(manager.trapv1("192.168.0.5", "162", "public", 3, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2854",PDUv1.AUTHENTICATIONFAILURE,0));
  	System.out.println(manager.trapv1("192.168.0.5", "162", "public", 3, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2854",PDUv1.ENTERPRISE_SPECIFIC,1));
  	*/
  	//TRAPS E INFORM SNMPv2
  	/*
  	System.out.println(manager.informv2c("192.168.0.5", "162", "public", 3, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2789.2005"));
  	System.out.println(manager.trapv2c("192.168.0.5", "162", "public", 3, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2789.2005"));
  	
  	System.out.println(manager.trapv2c("192.168.0.5", "162", "public", 3, 1500, "",String.valueOf(SnmpConstants.coldStart)));
  	System.out.println(manager.trapv2c("192.168.0.5", "162", "public", 3, 1500, "",String.valueOf(SnmpConstants.warmStart)));
  	System.out.println(manager.trapv2c("192.168.0.5", "162", "public", 3, 1500, "",String.valueOf(SnmpConstants.linkDown)));
  	System.out.println(manager.trapv2c("192.168.0.5", "162", "public", 3, 1500, "",String.valueOf(SnmpConstants.linkUp)));
  	System.out.println(manager.trapv2c("192.168.0.5", "162", "public", 3, 1500, "",String.valueOf(SnmpConstants.authenticationFailure)));
  	*/
  	/*
  	System.out.println("COLDSTART: "+String.valueOf(SnmpConstants.coldStart));
  	System.out.println("WARMSTART: "+String.valueOf(SnmpConstants.warmStart));
  	System.out.println("LINKDOWN: "+String.valueOf(SnmpConstants.linkDown));
  	System.out.println("LINKUP: "+String.valueOf(SnmpConstants.linkUp));
  	System.out.println("AUTHENTICATIONFAILURE: "+String.valueOf(SnmpConstants.authenticationFailure));
  	System.out.println("Otro: "+"1.3.6.1.4.1.2789.2005");
  	*/
  	
  	//TRAPS E INFORM SNMPv3
  	
  	System.out.println(manager.informv3("192.168.0.20", "162", "seminario", "12345678", "12345678", SecurityLevel.NOAUTH_NOPRIV,3, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2789.2005"));
/*  
  	System.out.println(manager.trapv3("192.168.0.20", "162", "seminario", "12345678", "12345678", SecurityLevel.NOAUTH_NOPRIV,2, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2789.2005"));
  	System.out.println(manager.informv3("192.168.0.20", "162", "seminario", "12345678", "12345678", SecurityLevel.NOAUTH_NOPRIV,3, 1500, "1.3.6.1.4.1.2789.2005.2={s}WWW Server Has Been ","1.3.6.1.4.1.2789.2005"));
  	System.out.println(manager.trapv3("192.168.0.20", "162", "seminario", "12345678", "12345678", SecurityLevel.NOAUTH_NOPRIV,2, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2789.2005"));
  	System.out.println(manager.informv3("192.168.0.20", "162", "seminario", "12345678", "12345678", SecurityLevel.NOAUTH_NOPRIV,3, 1500, "1.3.6.1.4.1.2789.2005.3={s}WWW Server Has ","1.3.6.1.4.1.2789.2005"));
  	System.out.println(manager.trapv3("192.168.0.20", "162", "seminario", "12345678", "12345678", SecurityLevel.NOAUTH_NOPRIV,2, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2789.2005"));
  	System.out.println(manager.informv3("192.168.0.20", "162", "seminario", "12345678", "12345678", SecurityLevel.NOAUTH_NOPRIV,3, 1500, "1.3.6.1.4.1.2789.2005.4={s}WWW Server ","1.3.6.1.4.1.2789.2005"));
  	System.out.println(manager.trapv3("192.168.0.20", "162", "seminario", "12345678", "12345678", SecurityLevel.NOAUTH_NOPRIV,2, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2789.2005"));
  	System.out.println(manager.informv3("192.168.0.20", "162", "seminario", "12345678", "12345678", SecurityLevel.NOAUTH_NOPRIV,3, 1500, "1.3.6.1.4.1.2789.2005.5={s}WWW ","1.3.6.1.4.1.2789.2005"));
  	System.out.println(manager.trapv3("192.168.0.20", "162", "seminario", "12345678", "12345678", SecurityLevel.NOAUTH_NOPRIV,2, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2789.2005"));
  	System.out.println(manager.informv3("192.168.0.20", "162", "seminario", "12345678", "12345678", SecurityLevel.NOAUTH_NOPRIV,3, 1500, "1.3.6.1.4.1.2789.2005.6={s}WWW otro","1.3.6.1.4.1.2789.2005"));
  	System.out.println(manager.trapv3("192.168.0.20", "162", "seminario", "12345678", "12345678", SecurityLevel.NOAUTH_NOPRIV,2, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2789.2005"));
  */	
  //	System.out.println(manager.trapv3("192.168.0.20", "162", "seminario", "12345678", "12345678", SecurityLevel.NOAUTH_NOPRIV,2, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2789.2005"));
  	/*
  	System.out.println(manager.informv3("192.168.0.5", "162", "seminario", "12345678", "12345678", SecurityLevel.AUTH_NOPRIV,2, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2789.2005"));
  	System.out.println(manager.trapv3("192.168.0.5", "162", "seminario", "12345678", "12345678", SecurityLevel.AUTH_NOPRIV,2, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2789.2005"));
  	System.out.println(manager.informv3("192.168.0.5", "162", "seminario", "12345678", "12345678", SecurityLevel.AUTH_PRIV,2, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2789.2005"));
  	System.out.println(manager.trapv3("192.168.0.5", "162", "seminario", "12345678", "12345678", SecurityLevel.AUTH_PRIV,2, 1500, "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted","1.3.6.1.4.1.2789.2005"));
  	*/
  	
  }	  
	  
}