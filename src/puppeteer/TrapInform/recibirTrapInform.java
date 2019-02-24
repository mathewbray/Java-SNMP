/*
 *		Status: Parsear la respuesta
 */
package puppeteer.TrapInform; 
 
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

public class recibirTrapInform implements CommandResponder {

  private String informacion = "";

  public String darInformacion(){
  	return informacion;
  }

  private String mensajesDeTraps01 = "Esperando TRAPs o INFORMs...\n";
  private String mensajesDeTraps02 = "\nPaquete #";
  private String mensajesDeTraps03 = "Lleg un TRAP SNMP Versin 1...\n";
  private String mensajesDeTraps04 = "Recibido: ";
  private String mensajesDeTraps05 = "IP / Puerto Origen: ";
  private String mensajesDeTraps06 = "Comunidad: ";
  private String mensajesDeTraps07 = "Enterprise: ";
  private String mensajesDeTraps08 = "Tipo de TRAP: ";
  private String mensajesDeTraps09 = "Trap Especfico: ";
  private String mensajesDeTraps10 = "TimeStamp: ";
  private String mensajesDeTraps11 = "Lleg un INFORM SNMP Versin 2c...\n";
  private String mensajesDeTraps12 = "Lleg un INFORM SNMP Versin 3...\n";
  private String mensajesDeTraps13 = "Usuario: ";
  private String mensajesDeTraps14 = "OID #";
  private String mensajesDeTraps15 = "valor #";
  private String mensajesDeTraps16 = "Lleg un TRAP SNMP Versin 2c...\n";
  private String mensajesDeTraps17 = "Lleg un TRAP SNMP Versin 3...\n";
  private String mensajesDeTraps18 = "Tipo de TRAP: Otro\n";
  
  public void cambiarIdiomaAMensajes(String s01,String s02,String s03,String s04,String s05,String s06,String s07,String s08,String s09,String s10,String s11,String s12,String s13,String s14,String s15,String s16,String s17,String s18){
  	  
  	  //System.out.println("mensaje: -"+mensajesDeTraps01+"- ");
  	  //System.out.println("informacion: -"+informacion+"- ");
  	  if (informacion.equals(mensajesDeTraps01)){
	  	informacion = s01;
	  }
  	  
  	  mensajesDeTraps01 = s01;
	  mensajesDeTraps02 = s02;
	  mensajesDeTraps03 = s03;
	  mensajesDeTraps04 = s04;
	  mensajesDeTraps05 = s05;
	  mensajesDeTraps06 = s06;
	  mensajesDeTraps07 = s07;
	  mensajesDeTraps08 = s08;
	  mensajesDeTraps09 = s09;
	  mensajesDeTraps10 = s10;
	  mensajesDeTraps11 = s11;
	  mensajesDeTraps12 = s12;
	  mensajesDeTraps13 = s13;
	  mensajesDeTraps14 = s14;
	  mensajesDeTraps15 = s15;
	  mensajesDeTraps16 = s16;
	  mensajesDeTraps17 = s17;
	  mensajesDeTraps18 = s18;
	  
  }
  
  

  private MultiThreadedMessageDispatcher dispatcher;
  private Snmp snmp = null;
  private TransportMapping transport;
  private Address listenAddress;
  private ThreadPool threadPool;
  private long cantidadPaquetes = 0;
  
  //private int n = 0;
  //private long start = -1;

  public recibirTrapInform() {
	//    BasicConfigurator.configure();
  }

  private void init() throws Exception {
    
    listenAddress = GenericAddress.parse(System.getProperty("snmp4j.listenAddress", "udp:0.0.0.0/162"));
    transport = new DefaultUdpTransportMapping((UdpAddress)listenAddress);
    threadPool = ThreadPool.create("Trap", 2);
    dispatcher = new MultiThreadedMessageDispatcher(threadPool,new MessageDispatcherImpl());
    //Para escuchar para SNMPv1 y SNMPv2c
    dispatcher.addMessageProcessingModel(new MPv1());
    dispatcher.addMessageProcessingModel(new MPv2c());
    SecurityProtocols.getInstance().addDefaultProtocols();
    //Inicializamos
    snmp = new Snmp(dispatcher, transport);
    //Para escuchar para SNMPv3
    dispatcher.addMessageProcessingModel(new MPv3());
	MPv3 mpv3 = (MPv3)snmp.getMessageProcessingModel(MessageProcessingModel.MPv3);
	USM usm = new USM(SecurityProtocols.getInstance(),new OctetString(mpv3.createLocalEngineID()), 0);
	SecurityModels.getInstance().addSecurityModel(usm);
	
    
    //snmp.addCommandResponder(this);
	
    //Para SNPv3
    snmp.setLocalEngine((new OctetString("12345678")).getValue(), 0, 0);
	snmp.getUSM().addUser(new OctetString("usuario"), new UsmUser(new OctetString("usuario"),AuthMD5.ID,new OctetString("12345678"),PrivDES.ID,new OctetString("12345678")));
    snmp.listen();
    informacion = mensajesDeTraps01;
        
    //------AQUI VA LO DEL LIBRO--------------------------------------------------------------------
    /*
    Address address = new UdpAddress("0.0.0.0/" + 162);
    DefaultUdpTransportMapping _transport = null;
    OctetString _authoritativeEngineID = new OctetString("12345678");
    int _version = 0;
		try {
			_transport = new DefaultUdpTransportMapping((UdpAddress) address);
		} catch(IOException ioex) {
			System.out.println("Unable to bind to local IP and port: " + ioex);
			System.exit(-1);
		}

		ThreadPool _threadPool = ThreadPool.create(this.getClass().getName(), 3);

		MessageDispatcher mtDispatcher = new MultiThreadedMessageDispatcher(_threadPool, new MessageDispatcherImpl());

		// add message processing models
		mtDispatcher.addMessageProcessingModel(new MPv1());
		mtDispatcher.addMessageProcessingModel(new MPv2c());

		// add all security protocols
		SecurityProtocols.getInstance().addDefaultProtocols();

		snmp = new Snmp(mtDispatcher, _transport);
		if(snmp != null){
			snmp.addCommandResponder(this);
		} else {
			System.out.println("Unable to create Target object");
			System.exit(-1);
		}
		
		OctetString _securityName = new OctetString("seminario");
		OID _authProtocol = AuthMD5.ID;
		OctetString _authPassphrase = new OctetString("12345678");
		OID _privProtocol = PrivDES.ID;
		OctetString _privPassphrase = new OctetString("12345678");
		
		//if(_version == SnmpConstants.version3) {
			mtDispatcher.addMessageProcessingModel(new MPv3());

			MPv3 mpv3 = (MPv3)snmp.getMessageProcessingModel(MessageProcessingModel.MPv3);

			USM usm = new USM(SecurityProtocols.getInstance(),new OctetString(mpv3.createLocalEngineID()), 0);

			SecurityModels.getInstance().addSecurityModel(usm);

			if (_authoritativeEngineID != null) {
				snmp.setLocalEngine(_authoritativeEngineID.getValue(), 0, 0);
			}

			//this.addUsmUser(_snmp);
			snmp.getUSM().addUser(_securityName, new UsmUser(_securityName,_authProtocol,_authPassphrase,_privProtocol,_privPassphrase));
	//	}
      
      
      snmp.listen();
    
    */
    
    //------FIN DE LO DEL LIBRO--------------------------------------------------------------------
    
  }

  public void run() throws Exception {
    //try {
      init();
      snmp.addCommandResponder(this);
    //}
    //catch (Exception ex) {ex.printStackTrace();}
  }

  public static void main(String[] args) throws Exception {
    recibirTrapInform t = new recibirTrapInform();
    t.run();
  }

  public void processPdu(CommandResponderEvent event) {
    cantidadPaquetes++;
    /*
    System.out.println("LLego algo...");
    System.out.println("event: "+event);
    System.out.println("pdu: "+event.getPDU());
    System.out.println("tipo PDU: "+event.getPDU().getType()); 
    System.out.println("inform: "+PDU.INFORM);    
    System.out.println("trap: "+PDU.TRAP);  
    System.out.println("trapv1: "+PDU.V1TRAP);  
    */
    if(event.getPDU().getType()==PDU.V1TRAP){
      informacion = informacion.concat(mensajesDeTraps02+cantidadPaquetes+" - "+mensajesDeTraps03);
      //System.out.println("Paquete #"+cantidadPaquetes+" - "+"Lleg un TRAPv1...");	 
      //System.out.println(event);  
      //System.out.println(event.getPDU());   	
      //System.out.println("RESPONSE: "+event.getResponse());
      //System.out.println("REQUEST: "+event.getRequest());
      /*
      PDUv1 trama = event.getPDU();
      trama.getAgentAddress() ;
 	  trama.getEnterprise() ;
      trama.getGenericTrap() ;
      trama.getSpecificTrap() ;
      trama.getTimestamp();
      */    
      	/*
      	int lugar =dato.indexOf("VBS");
			  	int ini = dato.indexOf("[",lugar);
			    int fin = dato.indexOf("]",lugar);
			    ini++;
			    dato=dato.substring(ini,fin);
      	
      	int lugar =;
		int ini = dato.indexOf("=",dato.indexOf("timestamp"));
		int fin = dato.indexOf(",",dato.indexOf("timestamp"));
		ini++;**/
		//System.out.println((new java.util.Date()));
		informacion = informacion.concat(mensajesDeTraps04+(new java.util.Date())+"\n");
		//System.out.println("IP origen: "+event.getPeerAddress());
		informacion = informacion.concat(mensajesDeTraps05+event.getPeerAddress()+"\n");
		//System.out.println("");
		
		String dato = event.toString();
		dato=dato.substring((dato.indexOf("=",dato.indexOf("securityName"))+1),dato.indexOf(",",dato.indexOf("securityName")));
      	//System.out.println("Comunidad="+dato);
      	informacion = informacion.concat(mensajesDeTraps06+dato+"\n");
		
      	dato = event.getPDU().toString();
      	dato=dato.substring((dato.indexOf("=",dato.indexOf("enterprise"))+1),dato.indexOf(",",dato.indexOf("enterprise")));
      	//System.out.println("enterprise="+dato);
      	informacion = informacion.concat(mensajesDeTraps07+dato+"\n");
      	
      	dato = event.getPDU().toString();
      	dato=dato.substring((dato.indexOf("=",dato.indexOf("genericTrap"))+1),dato.indexOf(",",dato.indexOf("genericTrap")));
      	if(PDUv1.COLDSTART==(Integer.parseInt(dato))){
      	  //System.out.println("genericTrap=COLDSTART");
      	  informacion = informacion.concat(mensajesDeTraps08+"coldStart"+"\n");
      	}
	  	if(PDUv1.WARMSTART==(Integer.parseInt(dato))){
	  	  //System.out.println("genericTrap=WARMSTART");
	  	  informacion = informacion.concat(mensajesDeTraps08+"warmStart"+"\n");
	  	}
	  	if(PDUv1.LINKDOWN==(Integer.parseInt(dato))){
	  	  //System.out.println("genericTrap=LINKDOWN");
	  	  informacion = informacion.concat(mensajesDeTraps08+"linkDown"+"\n");
	  	}
	  	if(PDUv1.LINKUP==(Integer.parseInt(dato))){
	  	  //System.out.println("genericTrap=LINKUP");
	  	  informacion = informacion.concat(mensajesDeTraps08+"linkUp"+"\n");
	  	}
	  	if(PDUv1.AUTHENTICATIONFAILURE==(Integer.parseInt(dato))){
	  	  //System.out.println("genericTrap=AUTHENTICATIONFAILURE");
	  	  informacion = informacion.concat(mensajesDeTraps08+"authenticationFailure"+"\n");
	  	}
	  	if(PDUv1.ENTERPRISE_SPECIFIC==(Integer.parseInt(dato))){
	  	  dato = event.getPDU().toString();
      	  dato=dato.substring((dato.indexOf("=",dato.indexOf("specificTrap"))+1),dato.indexOf(",",dato.indexOf("specificTrap")));
	  	  //System.out.println("genericTrap=ENTERPRISE_SPECIFIC");
	  	  informacion = informacion.concat(mensajesDeTraps08+"enterpriseSpecific"+"\n");
	  	  //System.out.println("specificTrap="+dato);
	  	  informacion = informacion.concat(mensajesDeTraps09+dato+"\n");
	  	}
	  	
	  	dato = event.getPDU().toString();
		dato=dato.substring((dato.indexOf("=",dato.indexOf("timestamp"))+1),dato.indexOf(",",dato.indexOf("timestamp")));
      	//System.out.println("Timestamp="+dato);
      	informacion = informacion.concat(mensajesDeTraps10+dato+"\n");
	  	
	  	//informacion = informacion.concat("");
	  	//System.out.println(informacion);
	  	//System.out.println("-----------------------------------------------------------------------------------------------");
	  	
      	dato = event.getPDU().toString();
      	//V1TRAP[reqestID=0,timestamp=0:03:20.00,enterprise=1.3.6.1.4.1.2854,genericTrap=2,specificTrap=0, VBS[]]
		//CommandResponderEvent[transportMapping=org.snmp4j.transport.DefaultUdpTransportMapping@15eb0a9peerAddress=192.168.0.5/31753, processed=false, pdu=[V1TRAP[reqestID=0,timestamp=0:03:20.00,enterprise=1.3.6.1.4.1.2854,genericTrap=0,specificTrap=0, VBS[]]], securityName=public, securityModel=1, securityLevel=1]
      
    }
    	
    if (event.getPDU().getType() == PDU.INFORM){
      //System.out.println("Paquete #"+cantidadPaquetes+" - "+"Lleg un INFORM...");	
      //System.out.println(event);
      String dato = event.toString();
      dato=dato.substring((dato.indexOf("=",dato.indexOf("securityModel"))+1),dato.indexOf(",",dato.indexOf("securityModel")));
      int verInformSnmp=0;
      if (Integer.parseInt(dato)==2){
      	informacion = informacion.concat(mensajesDeTraps02+cantidadPaquetes+" - "+mensajesDeTraps11);
      	verInformSnmp=2;
      }else{
      	informacion = informacion.concat(mensajesDeTraps02+cantidadPaquetes+" - "+mensajesDeTraps12);
      	verInformSnmp=3;
      }
	  informacion = informacion.concat(mensajesDeTraps04+(new java.util.Date())+"\n");
	  informacion = informacion.concat(mensajesDeTraps05+event.getPeerAddress()+"\n");
	  dato = event.toString();
	  dato=dato.substring((dato.indexOf("=",dato.indexOf("securityName"))+1),dato.indexOf(",",dato.indexOf("securityName")));
      if (verInformSnmp == 2){
        informacion = informacion.concat(mensajesDeTraps06+dato+"\n");
      }else{
      	informacion = informacion.concat(mensajesDeTraps13+dato+"\n");
      }
	  dato = event.getPDU().toString();
      Vector vbs = event.getPDU().getVariableBindings();
      int tamVBS = vbs.size();
      for (int i=0;i<tamVBS; i++){
        //System.out.println("VBS: "+vbs.get(i));	
        String variable = String.valueOf(vbs.get(i));
        String valor="";
        valor = (variable.substring((variable.indexOf("=")+1),variable.length())).trim();
        variable = (variable.substring(0, variable.indexOf("="))).trim();
        informacion = informacion.concat(mensajesDeTraps14+(i+1)+": "+variable+"\n");
        //System.out.println("OID #"+(i+1)+": "+variable);
        informacion = informacion.concat(mensajesDeTraps15+(i+1)+": "+valor+"\n");
        //System.out.println("valor #"+(i+1)+": "+valor);
      }
      //informacion = informacion.concat("\nPaquete #"+cantidadPaquetes+" - "+"Lleg un INFORM...");
      //informacion = informacion.concat(String.valueOf(event));
      //Para realizar pruebas con net-snmp de linux que se encuentra en /usr/bin
      //snmpinform -v 2c -c public 192.168.0.5:162 42 UCD-SNMP-MIB::ucdStart
    } 
    	    	
    if (event.getPDU().getType() == PDU.TRAP){
      String dato = event.toString();
      dato=dato.substring((dato.indexOf("=",dato.indexOf("securityModel"))+1),dato.indexOf(",",dato.indexOf("securityModel")));
      int verTrapSnmp = 0;
      if (Integer.parseInt(dato)==2){
      	informacion = informacion.concat(mensajesDeTraps02+cantidadPaquetes+" - "+mensajesDeTraps16);
      	verTrapSnmp = 2;
      }else{
      	informacion = informacion.concat(mensajesDeTraps02+cantidadPaquetes+" - "+mensajesDeTraps17);
      	verTrapSnmp = 3;
      }
	  informacion = informacion.concat(mensajesDeTraps04+(new java.util.Date())+"\n");
	  informacion = informacion.concat(mensajesDeTraps05+event.getPeerAddress()+"\n");
	  dato = event.toString();
	  dato=dato.substring((dato.indexOf("=",dato.indexOf("securityName"))+1),dato.indexOf(",",dato.indexOf("securityName")));
      if (verTrapSnmp == 2){
        informacion = informacion.concat(mensajesDeTraps06+dato+"\n");
      }else{
      	informacion = informacion.concat(mensajesDeTraps13+dato+"\n");
      }
	  dato = event.getPDU().toString();
      //dato=dato.substring((dato.indexOf("=",dato.indexOf("enterprise"))+1),dato.indexOf(",",dato.indexOf("enterprise")));
      //informacion = informacion.concat("Enterprise: "+dato+"\n");
      Vector vbs = event.getPDU().getVariableBindings();
      int tamVBS = vbs.size();
      for (int i=0;i<tamVBS; i++){
        //System.out.println("VBS: "+vbs.get(i));	
        String variable = String.valueOf(vbs.get(i));
        String valor="";
        valor = (variable.substring((variable.indexOf("=")+1),variable.length())).trim();
        variable = (variable.substring(0, variable.indexOf("="))).trim();
        informacion = informacion.concat(mensajesDeTraps14+(i+1)+": "+variable+"\n");
        //System.out.println("OID #"+(i+1)+": "+variable);
        informacion = informacion.concat(mensajesDeTraps15+(i+1)+": "+valor+"\n");
        //System.out.println("valor #"+(i+1)+": "+valor);
        OID oidVBSRecibido=null;
        OID variableOID=null;
        boolean noOID = false;
        try{oidVBSRecibido= new OID(valor);}catch(Exception yy){noOID=true;}
        if (!noOID){
          variableOID = new OID(variable);
          if(variableOID.equals(SnmpConstants.snmpTrapOID)){
	        boolean reconocido = false;
	        if (oidVBSRecibido.equals(SnmpConstants.coldStart)){
	        	//System.out.println("Tipo de TRAP: ColdStart");
	        	informacion = informacion.concat(mensajesDeTraps08+"coldStart\n");reconocido = true;
	        }
	        if (oidVBSRecibido.equals(SnmpConstants.warmStart)){
	        	//System.out.println("Tipo de TRAP: WarmStart");
	        	informacion = informacion.concat(mensajesDeTraps08+"warmStart\n");reconocido = true;
	        }
	        if (oidVBSRecibido.equals(SnmpConstants.linkDown)){
	        	//System.out.println("Tipo de TRAP: LinkDown");
	        	informacion = informacion.concat(mensajesDeTraps08+"linkDown\n");reconocido = true;
	        }
	        if (oidVBSRecibido.equals(SnmpConstants.linkUp)){
	        	//System.out.println("Tipo de TRAP: LinkUp");
	        	informacion = informacion.concat(mensajesDeTraps08+"linkUp\n");reconocido = true;
	        }
	        if (oidVBSRecibido.equals(SnmpConstants.authenticationFailure)){
	        	//System.out.println("Tipo de TRAP: Authentication Failure");
	        	informacion = informacion.concat(mensajesDeTraps08+"authenticationFailure\n");reconocido = true;
	        }
	        if (!reconocido){
	        	//System.out.println("Tipo de TRAP: Otro");
	        	informacion = informacion.concat(mensajesDeTraps18);reconocido = true;
	        }
          }  
        }
      }	
      //informacion = informacion.concat("\nPaquete #"+cantidadPaquetes+" - "+"Lleg un TRAP...");
      //informacion = informacion.concat(String.valueOf(event)+"\n\n");
      //System.out.println("Paquete #"+cantidadPaquetes+" - "+"Lleg un TRAP...");		
      //System.out.println(event);
      //Para realizar pruebas con net-snmp de linux que se encuentra en /usr/bin
      //snmptrap -v 2c -c public 192.168.0.5:162 42 UCD-SNMP-MIB::ucdStart
    }	
    	
    /*
    if (start < 0) {
      start = System.currentTimeMillis()-1;
    }
    System.out.println(event.toString());
    n++;
    if ((n % 100 == 1)) {
      System.out.println("Processed " + (n / (double)(System.currentTimeMillis() - start)) * 1000 + "/s, total="+n);
    }
    */
    /*
    PDU command = event.getPdu();
    System.out.println("Direccion del agente: "+event.getAgentAddress());
    System.out.println("OID del trap: "+event.getEnterprise());
    System.out.println("Generic Trap: "+event.getGenericTrap());
    System.out.println("Specific Trap: "+event.getSpecificTrap());
    System.out.println("sysUptime: "+event.getTimestamp());
    */
  }
}