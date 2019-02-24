/*
 *
 *
 *  To add a new language, you have to:
 *  - Go to line 81 and follow the instruccions.
 *	
 */
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.net.*;

//Para importar las mibs 
import net.percederberg.mibble.*;
import net.percederberg.mibble.value.*;
import mibblebrowser.MibNode;
import mibblebrowser.MibTreeBuilder;

//Para dibujar el mib browser
import javax.swing.tree.*;

//Para usar las versiones y comandos de snmp
import puppeteer.SNMPv1.SNMPv1;
import puppeteer.SNMPv2c.SNMPv2c;
import puppeteer.SNMPv3.SNMPv3;
import puppeteer.TrapInform.enviarTrapInform;
import puppeteer.TrapInform.recibirTrapInform;
import puppeteer.WALK.walk;
import puppeteer.GetTable.getTable;

//Para usar los comandos de la biblioteca snmp4j
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



public class managerSNMP extends JFrame {

  private String nameOfTheProgram = "SNMP JManager 1.0";

  //////////////////////////////////	
  private JFrame jframe;
  //////////////////////////////////
  private JPanel jpanel;  	
  //////////////////////////////////	
  private JMenuBar jmenubar;
  private JMenu jm_archive,jm_help;
  private JMenuItem jmi_outbound,jmi_about,jmi_helpHelp;
  private JOptionPane jOptionPaneMessage;
  private JPanel jp_versiones,jp_toolbar;
  private JTabbedPane jtp_versiones;
//  private JLabel jl_snmpv1_sel;
//  private JComboBox jcb_snmpv1_sel;
  private JLabel jl_idioma;
  private JButton jb_idiomaEs,jb_idiomaEn;
  //private JButton jb_newLanguage; // Here define the name of the button for the language. Then go to line 7118
  private String aboutDe="",aboutDeTitulo="";
  private String ConfigureParameters = "Configure Parameters";
  private String ComandoGet = "Comando Get";
  private String ComandoGetNext = "Comando GetNext";
  private String ComandoGetBulk = "Comando GetBulk";
  private String ComandoGetTable = "Comando GetTable";
  private String ComandoWalk = "Comando Walk";
  private String ComandoSet = "Comando Set";
  private String EnviarVerTraps = "Enviar/Ver Traps";
  ///////////////PANEL DEL MIBTREE
  private JPanel jp_mibtree;
  private JScrollPane jsp_mibtree;
  private JButton jb_mibtree;
  ///////////////////////////////////
//  private JPanel snmpv1,jp_comSNMPv1,jp_snmpv1_Con,jp_snmpv1_Get,jp_snmpv1_GetNext,jp_snmpv1_Set,jp_snmpv1_Traps,jp_snmpv1_getTable;
//  private JPanel jp_snmpv1_TrapsSend;     	 
//  private JComboBox jcb_snmpv1_TrapSel;		 
//  private JLabel jl_snmpv1_TrapSndTipo,jl_snmpv1_TrapSndHost,jl_snmpv1_TrapSndVarBin;		 
//  private JTextField jtf_snmpv1_TrapSndHostIP,jtf_snmpv1_TrapSndVarBin;    
//  private JTabbedPane jtp_snmpv1;
  
  private String IP = "192.168.0.23";
  private long pto = 161;
  private String comEsc = "private";
  private String comLec = "public";
  private int inten = 3;
  private int timeOut = 1500;
  private String OID = "";
  private int NonRepeaters = 0;
  private int MaxRepetitions = 10;
  private String user = "snmpuser";
  private String claveAut = "snmppassword";
  private String clavePriv = "snmppassword"; 

  //////////////////////////////////////
  private OID metAut=AuthMD5.ID;
  private OID metPriv=PrivDES.ID;
  	
  //----------------Inicio SNMPv1-----------------------------------------------------------------------------------------
  
//  		///////////////////Pantalla parametros\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//  		private JLabel jl_snmpv1_IP,jl_snmpv1_pto,jtf_snmpv1_comEsc,jl_snmpv1_comLec,jl_snmpv1_inten,jl_snmpv1_timeOut, jl_snmpv1_VerCom; 
//		private JTextField jtf_snmpv1_IP,jtf_snmpv1_pto,jtf_snmv1_inten,jtt_snmpv1_timeOut;
//		private JPasswordField jpf_snmpv1_comEsc, jtf_snmpv1_comLec;   
//		private JButton jb_snmpv1_aplicarPara;
//		private JComboBox jcb_snmpv1_VerCom;   
//		//////////////////Pantalla de los Traps\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//  		private JTextArea jta_snmpv1_traps;
//		private JScrollPane jsp_snmpv1_traps;
		private recibirTrapInform traps = null;
//		private JTextField jtf_snmpv1_PtoComTxt, jtf_snmpv1_ComTxt, jtf_snmpv1_IntTxt, jtf_snmpv1_TmoutTxt, jtf_snmpv1_EntTxt, jtf_snmpv1_TrapSpcTxt;
//		private JLabel jl_snmpv1_PtoCom, jl_snmpv1_Com, jl_snmpv1_Int, jl_snmpv1_Timeout, jl_snmpv1_Enter, jl_snmpv1_TrapSpc;
//		private JButton jb_SndTrap;  
//  		//////////////////Pantalla del Get
//  		private JScrollPane jsp_snmpv1_get,jsp_snmpv1_getDescrip,jsp_snmpv1_getResp;
//  		private JButton jb_snmpv1_getImpMib,jb_snmpv1_getGet,jb_snmpv1_add,jb_snmpv1_undo;
//    	private JTextArea jta_snmpv1_getDescrip,jta_snmpv1_getResp;
//    	private JTextField jtf_snmpv1_getGet,jtf_snmpv1_getObjs;
//    	private JLabel jl_snmpv1_getEtiGet,jl_snmpv1_getObjs;
//    	private Vector compuestoGetSNMPv1; //Para pasar los multiples parametros
//    	private Vector compuestoGetSNMPv1Temp;
//    	//////////////////Pantalla del GetNext
//    	private JScrollPane jsp_snmpv1_GetNext,jsp_snmpv1_GetNextDescrip,jsp_snmpv1_GetNextResp;
//  		private JButton jb_snmpv1_GetNextImpMib,jb_snmpv1_GetNextGetNext,jb_snmpv1_GetNext_add,jb_snmpv1_GetNextUndo;
//    	private JTextArea jta_snmpv1_GetNextDescrip,jta_snmpv1_GetNextResp;
//    	private JTextField jtf_snmpv1_GetNextGetNext,jtf_snmpv1_getNextObjs;   
//    	private JLabel jl_snmpv1_GetNextEtiGetNext,jl_snmpv1_getNextObjs;
//    	private Vector compuestoGetNextSNMPv1; //Para pasar los multiples parametros
//    	private Vector compuestoGetNextSNMPv1Temp;
//  		//////////////////Pantalla del Set
//		private JScrollPane jsp_snmpv1_SetDescrip,jsp_snmpv1_SetResp;
//		private JTextArea jta_snmpv1_SetDescrip,jta_snmpv1_SetResp;
//		private JTextField jtf_snmpv1_SetSet,jtf_snmpv1_SetSetValor,jtf_snmpv1_SetSetTipo,jtf_snmpv1_setObjs;
//		private JLabel jl_snmpv1_SetEtiSet,jl_snmpv1_SetEtiSetValor,jl_snmpv1_SetEtiSetTipo, jl_snmpv1_SetEtiTipo,jl_snmpv1_setObjs;
//		private JButton jb_snmpv1_SetSet,jb_snmpv1_setUndo,jb_snmpv1_setAdd;
    	private String tipoDatoReconocido=null;
    	private boolean reconocido=false;
//    	private JComboBox jcb_snmpv1_SetTipo;       
//    	private int jtf_snmpv1_SetSetDigitos;
//    	private Vector compuestoSetSNMPv1; //Para pasar los multiples parametros
//		private Vector compuestoSetSNMPv1TempOID;
//    	private Vector compuestoSetSNMPv1TempDatos;
//    	private Vector compuestoSetSNMPv1TempTipoDatos;
//    	private Variable[] compuestoSetSNMPv1Valores; //Para pasar los multiples parametros
//    	////////////////PANTALLA DEL WALK
//    	private JPanel jp_snmpv1_walk;
//    	private JLabel jl_snmpv1_WalkEti,jl_snmpv1_WalkEtiLimitePregunta,jl_snmpv1_WalkEtiLimite;
//		private JTextField jtf_snmpv1_WalkOID,jtf_snmpv1_WalkEtiLimite;
//		private JButton jb_snmpv1_Walk;
//		private JComboBox jcb_snmpv1_WalkEtiLimitePregunta;
//		private JScrollPane jsp_snmpv1_WalkResp;
//	    private JTextArea jta_snmpv1_WalkResp;
//	    //////////////PANTALLA DEL GETTABLE
//	    private JLabel jl_snmpv1_getTableEtigetTable;
//		private JTextField jtf_snmpv1_getTablegetTable;
//		private JButton jb_snmpv1_getTablegetTable;
//		private JScrollPane jsp_snmpv1_getTablegetTable;
//  //----------------Fin SNMPv1---------------------------------------------------------------------------------------------	    
//
//  //----------------Inicio SNMPv2c-----------------------------------------------------------------------------------------	    
//		private JLabel jl_snmpv2c_sel;
//		private JComboBox jcb_snmpv2c_sel;
//		private JPanel jp_snmpv2c_Con,jp_snmpv2c_Get,jp_snmpv2c_GetNext,jp_snmpv2c_GetBulk,jp_snmpv2c_Set,jp_snmpv2c_Traps,jp_snmpv2c_TrapsSend,jp_snmpv2c_walk,jp_snmpv2c_getTable;  
//	    ///////////////////Pantalla parametros v2c\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//	    //private JPanel jp_snmpv2c_Con;	
//	    private JLabel 	jl_snmpv2c_IP,jl_snmpv2c_pto,jl_snmpv2c_comLec,jl_snmpv2c_VerCom,jl_snmpv2c_inten,jl_snmpv2c_timeOut,jl_snmpv2c_comEsc,jl_snmpv2c_nonRepe,jl_snmpv2c_maxRep; 
//	    private JTextField jtf_snmpv2c_IP,jtf_snmpv2c_pto,jtf_snmv2c_inten,jtt_snmpv2c_timeOut,jtf_snmpv2c_maxRep,jtf_snmpv2c_nonRepe; 
//	    private JPasswordField jpf_snmpv2c_comEsc,jtf_snmpv2c_comLec,jtf_snmpv2c_comEsc; 
//	    private JComboBox jcb_snmpv2c_VerCom; 
//	    private JButton jb_snmpv2c_aplicarPara;   	    	
//        ////////////////////Pantalla del Get\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
//		private JScrollPane jsp_snmpv2c_getDescrip, jsp_snmpv2c_getResp; 
//		private JTextArea jta_snmpv2c_getDescrip,jta_snmpv2c_getResp; 
//		private JLabel jl_snmpv2c_getEtiGet,jl_snmpv2c_getObjs;	
//		private JTextField jtf_snmpv2c_getGet,jtf_snmpv2c_getObjs;                          
//		private JButton jb_snmpv2c_getGet,jb_snmpv2c_getAdd,jb_snmpv2c_getUndo;  
//		private Vector compuestoGetSNMPv2c; //Para pasar los multiples parametros
//    	private Vector compuestoGetSNMPv2cTemp; 		    
//		/////////////////////////Pantalla del GetNext\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
//		private JScrollPane jsp_snmpv2c_GetNextDescrip, jsp_snmpv2c_GetNextResp;
//		private JTextArea jta_snmpv2c_GetNextDescrip,jta_snmpv2c_GetNextResp; 
//		private JLabel jl_snmpv2c_GetNextEtiGetNext,jl_snmpv2c_getNextObjs; 
//		private JTextField jtf_snmpv2c_GetNextGetNext,jtf_snmpv2c_getNextObjs;     
//		private JButton jb_snmpv2c_GetNextGetNext,jb_snmpv2c_GetNextUndo,jb_snmpv2c_GetNext_add;   
//		private Vector compuestoGetNextSNMPv2c; //Para pasar los multiples parametros
//    	private Vector compuestoGetNextSNMPv2cTemp;	
//		////////////////////////Pantalla del Walk\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
//		private JLabel jl_snmpv2c_WalkEtiLimitePregunta,jl_snmpv2c_WalkEtiLimite,jl_snmpv2c_WalkEti; 
//		private JComboBox jcb_snmpv2c_WalkEtiLimitePregunta; 
//		private JTextField jtf_snmpv2c_WalkEtiLimite,jtf_snmpv2c_WalkOID; 
//		private JButton jb_snmpv2c_Walk; 
//		private JScrollPane jsp_snmpv2c_WalkResp; 
//		private JTextArea jta_snmpv2c_WalkResp;		    	    
//		//////////////////////////////////Pantalla del Set\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
//		private JScrollPane jsp_snmpv2c_SetDescrip, jsp_snmpv2c_SetResp; 
//		private JTextArea jta_snmpv2c_SetDescrip,jta_snmpv2c_SetResp; 
//		private JLabel jl_snmpv2c_SetEtiSet,jl_snmpv2c_SetEtiTipo,jl_snmpv2c_SetEtiSetValor,jl_snmpv2c_setObjs;   
//		private JTextField jtf_snmpv2c_SetSet,jtf_snmpv2c_SetSetValor,jtf_snmpv2c_setObjs; 
//		private JComboBox jcb_snmpv2c_SetTipo; 
//		private JButton jb_snmpv2c_SetSet,jb_snmpv2c_setUndo,jb_snmpv2c_setAdd;	    
//		private int jtf_snmpv2c_SetSetDigitos;	
//		private Vector compuestoSetSNMPv2c; //Para pasar los multiples parametros
//		private Vector compuestoSetSNMPv2cTempOID;
//    	private Vector compuestoSetSNMPv2cTempDatos;
//    	private Vector compuestoSetSNMPv2cTempTipoDatos;
//    	private Variable[] compuestoSetSNMPv2cValores; //Para pasar los multiples parametros
//		/////////////////////////Pantalla de los TRAPS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//		private JScrollPane jsp_snmpv2c_traps; 
//		private JTextArea jta_snmpv2c_traps;  
//		private JLabel jl_snmpv2c_TrapSndHost,jl_snmpv2c_Enter,jl_snmpv2c_Timeout,jl_snmpv2c_Int,jl_snmpv2c_Com,jl_snmpv2c_PtoCom,jl_snmpv2c_TrapSndTipo,jl_snmpv2c_TrpInfSel,jl_snmpv2c_OtroTrp,jl_snmpv2c_Descr,jl_snmpv2c_TpoDtoTrp; 
//		private JTextField jtf_snmpv2c_TrapSndHostIP,jtf_snmpv2c_PtoComTxt,jtf_snmpv2c_ComTxt,jtf_snmpv2c_IntTxt,jtf_snmpv2c_TmoutTxt,jtf_snmpv2c_EntTxt,jtf_snmpv2c_OtroTrp,jtf_snmpv2c_Descr; 
//		private JButton jb_SndTrapv2c;
//		private JComboBox jcb_snmpv2c_TrapSel,jcb_snmpv2c_TrpInfSel,jcb_snmpv2c_TpoDtoTrp;
//		////////////////////////Pantalla de GetBulk\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//		private JButton jb_snmpv2c_GetBulkGetBulk,jb_snmpv2c_GetBulk_add,jb_snmpv2c_GetBulkUndo;
//		private JTextField jtf_snmpv2c_GetBulkGetBulk,jtf_snmpv2c_getBulkObjs;
//		private JScrollPane jsp_snmpv2c_GetBulkResp;
//		private JTextArea jta_snmpv2c_GetBulkResp;
//		private JLabel jl_snmpv2c_GetBulkEtiSet,jl_snmpv2c_getBulkObjs;	
//		private Vector compuestoGetBulkSNMPv2c; //Para pasar los multiples parametros
//    	private Vector compuestoGetBulkSNMPv2cTemp;	
//		//////////////PANTALLA DEL GETTABLE
//	    private JLabel jl_snmpv2c_getTableEtigetTable;
//		private JTextField jtf_snmpv2c_getTablegetTable;
//		private JButton jb_snmpv2c_getTablegetTable;
//		private JScrollPane jsp_snmpv2c_getTablegetTable;	
  //----------------Fin SNMPv2c--------------------------------------------------------------------------------------------

  //----------------Inicio SNMPv3------------------------------------------------------------------------------------------
		private JLabel jl_snmpv3_sel;
		private JComboBox jcb_snmpv3_sel;
		private JPanel jp_snmpv3_Con, jp_snmpv3_Get, jp_snmpv3_GetNext, jp_snmpv3_GetBulk, jp_snmpv3_Set, jp_snmpv3_Traps, jp_snmpv3_TrapsSend, jp_snmpv3_walk,jp_snmpv3_getTable;
		///////////////////Pantalla parametros v3\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		private JComboBox jcb_snmpv3_VerUsr,jcb_snmpv3_metAut,jcb_snmpv3_metPriv;
		private JLabel jl_snmpv3_IP,jl_snmpv3_pto,jl_snmpv3_User,jl_snmpv3_VerUsr,jl_snmpv3_inten,jl_snmpv3_timeOut,jl_snmpv3_Aut,jl_snmpv3_Priv,jl_snmpv3_metAut,jl_snmpv3_metPriv;    
		private JTextField jtf_snmpv3_IP,jtf_snmpv3_pto,jtf_snmv3_inten,jtt_snmpv3_timeOut;
		private JPasswordField jpf_snmpv3_User,jpf_snmpv3_Aut,jpf_snmpv3_Priv;  
		private JButton jb_snmpv3_aplicarPara;    
		////////////////////////GET
		private JScrollPane jsp_snmpv3_getDescrip;
		private JTextArea jta_snmpv3_getDescrip,jta_snmpv3_getResp;
		private JLabel jl_snmpv3_getEtiGet,jl_snmpv3_getModSeg,jl_snmpv3_getObjs;
		private JScrollPane jsp_snmpv3_getResp;
		private JTextField jtf_snmpv3_getGet,jtf_snmpv3_getObjs; 
		private JButton jb_snmpv3_getGet,jb_snmpv3_getAdd,jb_snmpv3_getUndo;
		private JComboBox jcb_snmpv3_getModSeg;		
		private Vector compuestoGetSNMPv3; //Para pasar los multiples parametros
    	private Vector compuestoGetSNMPv3Temp; 
		////////////////////////GETNEXT
		private JScrollPane jsp_snmpv3_GetNextDescrip,jsp_snmpv3_GetNextResp;
		private JTextArea jta_snmpv3_GetNextDescrip,jta_snmpv3_GetNextResp;
		private JLabel jl_snmpv3_GetNextEtiGetNext,jl_snmpv3_getNextModSeg,jl_snmpv3_getNextObjs;
		private JTextField jtf_snmpv3_GetNextGetNext,jtf_snmpv3_getNextObjs;
		private JButton jb_snmpv3_GetNextGetNext,jb_snmpv3_getNextAdd,jb_snmpv3_getNextUndo;
	  	private JComboBox jcb_snmpv3_getNextModSeg;	
	  	private Vector compuestoGetNextSNMPv3; //Para pasar los multiples parametros
    	private Vector compuestoGetNextSNMPv3Temp;		
		////////////////////////GETBULK
		private JScrollPane jsp_snmpv3_GetBulkResp;
		private JTextArea jta_snmpv3_GetBulkResp;
		private	JLabel jl_snmpv3_nonRepe, jl_snmpv3_maxRep, jl_snmpv3_GetBulkEtiSet,jl_snmpv3_getBulkModSeg,jl_snmpv3_getBulkObjs; 
		private JTextField jtf_snmpv3_nonRepe, jtf_snmpv3_maxRep, jtf_snmpv3_GetBulkGetBulk,jtf_snmpv3_getBulkObjs;
		private JButton jb_snmpv3_GetBulkGetBulk,jb_snmpv3_GetBulk_add,jb_snmpv3_GetBulkUndo;
		private JComboBox jcb_snmpv3_getBulkModSeg;
		private Vector compuestoGetBulkSNMPv3; //Para pasar los multiples parametros
    	private Vector compuestoGetBulkSNMPv3Temp;
		//////////////////////////WALK
		private JLabel jl_snmpv3_WalkEtiLimitePregunta, jl_snmpv3_WalkEtiLimite, jl_snmpv3_WalkEti,jl_snmpv3_getWalkModSeg;
		private JComboBox jcb_snmpv3_WalkEtiLimitePregunta,jcb_snmpv3_WalkModSeg;
		private JTextField jtf_snmpv3_WalkEtiLimite, jtf_snmpv3_WalkOID;
		private JButton jb_snmpv3_Walk;
		private JScrollPane jsp_snmpv3_WalkResp;
		private JTextArea jta_snmpv3_WalkResp;
		////////////////////////////SET
		private JScrollPane jsp_snmpv3_SetDescrip, jsp_snmpv3_SetResp;
		private JTextArea jta_snmpv3_SetDescrip, jta_snmpv3_SetResp;
		private JLabel jl_snmpv3_SetEtiSet, jl_snmpv3_SetEtiTipo, jl_snmpv3_SetEtiSetValor, jl_snmpv3_setModSeg,jl_snmpv3_setObjs;
		private JTextField jtf_snmpv3_SetSet, jtf_snmpv3_SetSetValor,jtf_snmpv3_setObjs;
		private JComboBox jcb_snmpv3_SetTipo, jcb_snmpv3_setModSeg;
		private JButton jb_snmpv3_SetSet,jb_snmpv3_setAdd,jb_snmpv3_setUndo;
		private int jtf_snmpv3_SetSetDigitos;		
		private Vector compuestoSetSNMPv3; //Para pasar los multiples parametros
		private Vector compuestoSetSNMPv3TempOID;
    	private Vector compuestoSetSNMPv3TempDatos;
    	private Vector compuestoSetSNMPv3TempTipoDatos;
    	private Variable[] compuestoSetSNMPv3Valores; //Para pasar los multiples parametros
		////////////////////////////TRAPS
		private JScrollPane	jsp_snmpv3_traps;
		private JTextArea jta_snmpv3_traps;		
		private JLabel jl_snmpv3_TrapSndHost, jl_snmpv3_TrapSndTipo, jl_snmpv3_PtoCom, jl_snmpv3_TrpUsr, jl_snmpv3_Int, jl_snmpv3_Enter, jl_snmpv3_TrpInfSel, jl_snmpv3_OtroTrp, jl_snmpv3_Descr, jl_snmpv3_Timeout,jl_snmpv3_TpDto,jl_snmpv3_TrpPriv, jl_snmpv3_TrpAut,jl_snmpv3_TrpVer,jl_snmpv3_trpModSeg;
		private JTextField jtf_snmpv3_TrapSndHostIP, jtf_snmpv3_PtoComTxt, jtf_snmpv3_ComTxt, jtf_snmpv3_IntTxt, jtf_snmpv3_TmoutTxt, jtf_snmpv3_EntTxt, jtf_snmpv3_OtroTrp, jtf_snmpv3_Descr;
		private JComboBox jcb_snmpv3_TrapSel, jcb_snmpv3_TrpInfSel,jcb_snmpv3_TpoDtoTrp,jcb_snmpv3_TrpVer, jcb_snmpv3_trpModSeg;	
		private JButton	jb_SndTrapv3;
		private JPasswordField jpf_snmpv3_TrpPriv, jpf_snmpv3_TrpAut, jpf_snmpv3_TrpUsr;
		//////////////PANTALLA DEL GETTABLE
	    private JLabel jl_snmpv3_getTableEtigetTable;
		private JTextField jtf_snmpv3_getTablegetTable;
		private JButton jb_snmpv3_getTablegetTable;
		private JScrollPane jsp_snmpv3_getTablegetTable;
		private JLabel jl_snmpv3_getTableModSeg;
	    private JComboBox jcb_snmpv3_getTableModSeg;
  //----------------Fin SNMPv3--------------------------------------------------------------------------------------------- 
   
  ///////////----PARA EL MIBBLEBROWSER---------------------------------------------------
  
	/**
     * The MIB tree component.
     */
    private JTree mibTree = null;
            
    /**
     * The list of loaded MIB names.
     */
    // TODO: ultimately remove this list of MIB names, use a
    //       MibLoader instead
    private ArrayList loadedMibs;
    private ArrayList loadedMibsParaBuscarNombres;
        
	/**
     * Loads MIB file or URL.
     *
     * @param src            the MIB file or URL
     *
     * @throws IOException if the MIB file couldn't be found in the
     *             MIB search path
     * @throws MibLoaderException if the MIB file couldn't be loaded
     *             correctly
     */
    private MibTreeBuilder  mb;
    private Mib             mib; 
    public void loadMib(String src) throws IOException, MibLoaderException {

        mb = MibTreeBuilder.getInstance();
        mib = null;

        // TODO: handle URLs

        // Loading the specified file
        mib = mb.loadMib(new File(src));
		
		
        // Check for already loaded MIB
        for (int i = 0; i < loadedMibs.size(); i++) {
        	//System.out.println(mib.getName());
            if (mib.getName().equals(loadedMibs.get(i))) {
                return;
            }
        }
        // Add MIB to tree model
        mb.addMib(mib);
        loadedMibs.add(mib.getName());
        loadedMibsParaBuscarNombres.add(mib);
        
    }    

    public void loadMibInternal(String src) throws IOException, MibLoaderException {

        mb = MibTreeBuilder.getInstance();
        mib = null;
        File file = null;
        // TODO: handle URLs
        try {
                       InputStream input = getClass().getResourceAsStream(src); 
            file = File.createTempFile("tempfile", ".tmp");
                        OutputStream out = new FileOutputStream(file);
            int read;
            byte[] bytes = new byte[1024];

            while ((read = input.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            file.deleteOnExit(); 
        } catch (IOException ex) {
            
        }

            //OutputStream out = new FileOutputStream(file);
//BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        // Loading the specified file
        mib = mb.loadMib(file);
		
		
        // Check for already loaded MIB
        for (int i = 0; i < loadedMibs.size(); i++) {
        	//System.out.println(mib.getName());
            if (mib.getName().equals(loadedMibs.get(i))) {
                return;
            }
        }
        // Add MIB to tree model
        mb.addMib(mib);
        loadedMibs.add(mib.getName());
        loadedMibsParaBuscarNombres.add(mib);
        
    }    
        
    /**
     * The current MIB file directory.
     */
    private File currentDir = new File(".");
    
    /**
     * Opens the load MIB dialog.
     */
    protected void loadNewMib() throws Exception{
        JFileChooser  dialog = new JFileChooser();
        File[]        files;
        int           result;

        dialog.setCurrentDirectory(currentDir);
        dialog.setMultiSelectionEnabled(true);
        result = dialog.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            files = dialog.getSelectedFiles();
            if (files.length > 0) {
                currentDir = files[0].getParentFile();
                //descriptionArea.setText("");
                for (int i = 0; i < files.length; i++) {
                	loadMib(files[i].toString());
            	}
                //loadMib(files);
                refreshTree();
            }
        }
    }
    
    protected void loadMib() throws Exception{
    	
            //loadMibInternal("RFC1213-MIB");
            loadMibInternal("HAIPE-MIB");
            refreshTree();
            //loadMib("RFC1271-MIB");
            //	refreshTree();
   }
        
    /**
     * Refreshes the MIB tree.
     */
    public void refreshTree() {
        ((DefaultTreeModel) mibTree.getModel()).reload();
        mibTree.repaint();
    }

	/**
     * Returns the currently selected MIB node.
     *
     * @return the currently selected MIB node, or
     *         null for none
     */
    public MibNode getSelectedNode() {
        return (MibNode) mibTree.getLastSelectedPathComponent();
    }
    
    /**
     * Updates the tree selection.
     */
    protected void updateTreeSelection() {// Aqui es necesario colocar todos los 
        MibNode  node = getSelectedNode();

        if (node == null) {
//            //Para SNMPv1
//            jta_snmpv1_getDescrip.setText("");
//            jtf_snmpv1_getGet.setText("");
//            jtf_snmpv1_GetNextGetNext.setText("");
//            jtf_snmpv1_SetSet.setText("");
//            //jtf_snmpv1_SetSetTipo.setText("");
//            jtf_snmpv1_WalkOID.setText("");
//            //Para SNMPv2c
//            jta_snmpv2c_getDescrip.setText("");
//            jtf_snmpv2c_getGet.setText("");
//            jtf_snmpv2c_GetNextGetNext.setText("");
//            jtf_snmpv2c_GetBulkGetBulk.setText("");
//            jtf_snmpv2c_SetSet.setText("");
//            jtf_snmpv2c_WalkOID.setText("");
            
            //Para SNMPv3            ---------------------------------------------------------------  666
            jta_snmpv3_getDescrip.setText("");
            jtf_snmpv3_getGet.setText("");
            jtf_snmpv3_GetNextGetNext.setText("");
            jtf_snmpv3_GetBulkGetBulk.setText("");
            jtf_snmpv3_SetSet.setText("");
            jtf_snmpv3_WalkOID.setText("");
            //                       ---------------------------------------------------------------  666            
                        
            
        } else {
        	/*
        	System.out.println("-------------------------------------------------------------------------------");
        	System.out.println("valor de getName: "+node.getName());
			System.out.println("valor de getValue: "+node.getValue());
        	System.out.println("valor de getOid: "+node.getOid());
        	System.out.println("valor de getSymbol: "+node.getSymbol());
        	System.out.println("valor de getSnmpObjectType: "+node.getSnmpObjectType());
        	System.out.println("valor de getDescription: "+node.getDescription());
        	System.out.println("valor de getToolTipText: "+node.getToolTipText());
        	*/
        	if (node.getSnmpObjectType()!=null){
        	  //System.out.println("invento: "+node.getSnmpObjectType().getSyntax().getName());	
			  //Para reconocer el tipo de dato de la variable---------------------------------------------------------------
        	  reconocido = false;
        	  MibType  type = node.getSnmpObjectType().getSyntax();
        	  if (type.hasTag(MibTypeTag.UNIVERSAL_CATEGORY, 2)) {
            	// INTEGER & Integer32
            	//System.out.println("invento2: INTEGER");
            	tipoDatoReconocido = "INTEGER";
            	reconocido = true;	
        	  } 
        	  if (type.hasTag(MibTypeTag.UNIVERSAL_CATEGORY, 4)) {
            	// OCTET STRING
            	//System.out.println("invento2: OCTET STRING");
            	tipoDatoReconocido = "OCTET STRING";	
            	reconocido = true;	
			  }
			  if (type.hasTag(MibTypeTag.UNIVERSAL_CATEGORY, 6)) {
            	// OBJECT IDENTIFIER
            	//System.out.println("invento2: OBJECT IDENTIFIER");
            	tipoDatoReconocido = "OBJECT IDENTIFIER";
            	reconocido = true;		
              }
              if (type.hasTag(MibTypeTag.APPLICATION_CATEGORY, 0)) {
            	// IPAddress
            	//System.out.println("invento2: IPAddress");
            	tipoDatoReconocido = "IPADDRESS";
            	reconocido = true;		
              }
              if (type.hasTag(MibTypeTag.APPLICATION_CATEGORY, 1)) {
            	// Counter
            	//System.out.println("invento2: COUNTER");
            	tipoDatoReconocido = "COUNTER";
            	reconocido = true;		
              }
              if (type.hasTag(MibTypeTag.APPLICATION_CATEGORY, 2)) {
            	// Gauge
            	//System.out.println("invento2: GAUGE");	
            	tipoDatoReconocido = "GAUGE";
            	reconocido = true;	
              }
              if (type.hasTag(MibTypeTag.APPLICATION_CATEGORY, 3)) {
            	// TimeTicks
            	//System.out.println("invento2: TIMETICKS");
            	tipoDatoReconocido = "TIMETICKS";
            	reconocido = true;		
              }
              if (type.hasTag(MibTypeTag.APPLICATION_CATEGORY, 4)) {
            	// Opaque
            	//System.out.println("invento2: OPAQUE");
            	tipoDatoReconocido = "OPAQUE";
            	reconocido = true;		
              }
              if (type.hasTag(MibTypeTag.APPLICATION_CATEGORY, 6)) {
            	// Counter64
            	//System.out.println("invento2: COUNTER64");
            	tipoDatoReconocido = "COUNTER64";
            	reconocido = true;		
              }
              if (!(reconocido)){
              	//System.out.println("NO ENCONTRE MATCH, VALOR ES: "+MibTypeTag.APPLICATION_CATEGORY);
              	if (MibTypeTag.APPLICATION_CATEGORY==1){
              	  int lugar =node.getDescription().indexOf("APPLICATION");
        	  	  //System.out.println("posicion: "+node.getDescription().substring((lugar+12),(lugar+13)));	
              	  if((node.getDescription().substring((lugar+12),(lugar+13))).equals("0")){
              	  	// IPAddress
            		//System.out.println("invento2: IPADDRESS");
            		tipoDatoReconocido = "IPADDRESS";
            		reconocido = true;
              	  }else{
              	  	//tipo de dato no reconocido
              	  	//System.out.println("invento2: TIPO DE DATOS NO RECONOCIDO");
              	  	tipoDatoReconocido="NO RECONOCIDO";
              	  }	
              	}else{
              	  //tipo de dato no reconocido
 				  //System.out.println("invento2: TIPO DE DATOS NO RECONOCIDO");              	    	
 				  tipoDatoReconocido="NO RECONOCIDO";
              	}
              }
             // System.out.println("Tipo de Dato reconocido: "+tipoDatoReconocido);
              //jtf_snmpv1_SetSetTipo.setText(tipoDatoReconocido);
        	}
        	//Fin del reconocimiento del tipo de datos de la variable-------------------------------------------------------
        	//Para reconocer el tipo de acceso de la variable, si es de solo lectura o de lectura-escritura-----------------
        	int lugar =node.getDescription().indexOf("Access:");
			if ((!(node.getDescription().equals("")))&&(lugar>0)){
			  //System.out.println("RESPUESTA DE CANWRITE: "+node.getSnmpObjectType().getAccess().canWrite());
        	  if (node.getSnmpObjectType().getAccess().canWrite()){
//        	    //Para SNMPv1-------------------------------------------------------------------------------------
//        	    jb_snmpv1_SetSet.setEnabled(true);
//        	    jtf_snmpv1_SetSetValor.setEnabled(true);
//        	    jcb_snmpv1_SetTipo.setEnabled(true);      
//        	    jl_snmpv1_SetEtiTipo.setEnabled(true);  	
//        	    jl_snmpv1_SetEtiSetValor.setEnabled(true);
//        	    jb_snmpv1_setAdd.setEnabled(true);
//              	jb_snmpv1_setUndo.setEnabled(true);
//        	    if (tipoDatoReconocido.equals("INTEGER")) {jcb_snmpv1_SetTipo.setSelectedIndex(1);}    
//        	    if (tipoDatoReconocido.equals("OCTET STRING")) {jcb_snmpv1_SetTipo.setSelectedIndex(2);}  
//        	    if (tipoDatoReconocido.equals("OBJECT IDENTIFIER")) {jcb_snmpv1_SetTipo.setSelectedIndex(3);} 
//        	    if (tipoDatoReconocido.equals("IPADDRESS")) {jcb_snmpv1_SetTipo.setSelectedIndex(4);}      
//        	    if (tipoDatoReconocido.equals("COUNTER")) {jcb_snmpv1_SetTipo.setSelectedIndex(5);}        
//        	    if (tipoDatoReconocido.equals("GAUGE")) {jcb_snmpv1_SetTipo.setSelectedIndex(6);}           
//        	    if (tipoDatoReconocido.equals("TIMETICKS")) {jcb_snmpv1_SetTipo.setSelectedIndex(7);}       
//        	    if (tipoDatoReconocido.equals("OPAQUE")) {jcb_snmpv1_SetTipo.setSelectedIndex(8);}           
//        	    //if (tipoDatoReconocido.equals("COUNTER64")) {jcb_snmpv1_SetTipo.setSelectedIndex(9);}        /*ESTE TIPO DE DATO NO ES SOPORTADO EN V1*/
//        	    //Para SNMPv2c------------------------------------------------------------------------------------
//        	    jb_snmpv2c_SetSet.setEnabled(true);
//        	    jtf_snmpv2c_SetSetValor.setEnabled(true);
//        	    jcb_snmpv2c_SetTipo.setEnabled(true);     
//        	    jl_snmpv2c_SetEtiTipo.setEnabled(true);	 
//        	    jl_snmpv2c_SetEtiSetValor.setEnabled(true);  
//	        	jb_snmpv2c_setAdd.setEnabled(true);     //  ***
//	        	jb_snmpv2c_setUndo.setEnabled(true);     //  ***        	    	 
//        	    if (tipoDatoReconocido.equals("INTEGER")) {jcb_snmpv2c_SetTipo.setSelectedIndex(1);}    
//        	    if (tipoDatoReconocido.equals("OCTET STRING")) {jcb_snmpv2c_SetTipo.setSelectedIndex(2);}  
//        	    if (tipoDatoReconocido.equals("OBJECT IDENTIFIER")) {jcb_snmpv2c_SetTipo.setSelectedIndex(3);} 
//        	    if (tipoDatoReconocido.equals("IPADDRESS")) {jcb_snmpv2c_SetTipo.setSelectedIndex(4);}      
//        	    if (tipoDatoReconocido.equals("COUNTER")) {jcb_snmpv2c_SetTipo.setSelectedIndex(5);}        
//        	    if (tipoDatoReconocido.equals("GAUGE")) {jcb_snmpv2c_SetTipo.setSelectedIndex(6);}           
//        	    if (tipoDatoReconocido.equals("TIMETICKS")) {jcb_snmpv2c_SetTipo.setSelectedIndex(7);}       
//        	    if (tipoDatoReconocido.equals("OPAQUE")) {jcb_snmpv2c_SetTipo.setSelectedIndex(8);}           
//        	    if (tipoDatoReconocido.equals("COUNTER64")) {jcb_snmpv2c_SetTipo.setSelectedIndex(9);}        

        	    //Para SNMPv3  -------------------------------------------------------------------------------  666
        	    jb_snmpv3_SetSet.setEnabled(true);
        	    jtf_snmpv3_SetSetValor.setEnabled(true);
        	    jcb_snmpv3_SetTipo.setEnabled(true);     
        	    jl_snmpv3_SetEtiTipo.setEnabled(true);	 
        	    jl_snmpv3_SetEtiSetValor.setEnabled(true);   
				jcb_snmpv3_setModSeg.setEnabled(true);
		        jl_snmpv3_setModSeg.setEnabled(true);
				jb_snmpv3_setAdd.setEnabled(true);    //   ***
				jb_snmpv3_setUndo.setEnabled(true);    //   ***        	       			                	    	
        	    if (tipoDatoReconocido.equals("INTEGER")) {jcb_snmpv3_SetTipo.setSelectedIndex(1);}    
        	    if (tipoDatoReconocido.equals("OCTET STRING")) {jcb_snmpv3_SetTipo.setSelectedIndex(2);}  
        	    if (tipoDatoReconocido.equals("OBJECT IDENTIFIER")) {jcb_snmpv3_SetTipo.setSelectedIndex(3);} 
        	    if (tipoDatoReconocido.equals("IPADDRESS")) {jcb_snmpv3_SetTipo.setSelectedIndex(4);}      
        	    if (tipoDatoReconocido.equals("COUNTER")) {jcb_snmpv3_SetTipo.setSelectedIndex(5);}        
        	    if (tipoDatoReconocido.equals("GAUGE")) {jcb_snmpv3_SetTipo.setSelectedIndex(6);}           
        	    if (tipoDatoReconocido.equals("TIMETICKS")) {jcb_snmpv3_SetTipo.setSelectedIndex(7);}       
        	    if (tipoDatoReconocido.equals("OPAQUE")) {jcb_snmpv3_SetTipo.setSelectedIndex(8);}           
        	    if (tipoDatoReconocido.equals("COUNTER64")) {jcb_snmpv3_SetTipo.setSelectedIndex(9);}        
				//             -------------------------------------------------------------------------------  666

        	  }else{
//        	    //Para SNMPv1------------------------------------------------------------------------------------
//        	    jb_snmpv1_SetSet.setEnabled(false);
//        	    jtf_snmpv1_SetSetValor.setEnabled(false);  
//        	    jtf_snmpv1_SetSetValor.setText("");
//        	    jcb_snmpv1_SetTipo.setEnabled(false);      
//        	    jl_snmpv1_SetEtiTipo.setEnabled(false);   
//        	    jl_snmpv1_SetEtiSetValor.setEnabled(false);
//        	    jb_snmpv1_setAdd.setEnabled(false);
//              	jb_snmpv1_setUndo.setEnabled(false);
//        	    if (tipoDatoReconocido.equals("INTEGER")) {jcb_snmpv1_SetTipo.setSelectedIndex(1);}    
//        	    if (tipoDatoReconocido.equals("OCTET STRING")) {jcb_snmpv1_SetTipo.setSelectedIndex(2);}  
//        	    if (tipoDatoReconocido.equals("OBJECT IDENTIFIER")) {jcb_snmpv1_SetTipo.setSelectedIndex(3);}     
//        	    if (tipoDatoReconocido.equals("IPADDRESS")) {jcb_snmpv1_SetTipo.setSelectedIndex(4);}    
//        	    if (tipoDatoReconocido.equals("COUNTER")) {jcb_snmpv1_SetTipo.setSelectedIndex(5);}       
//        	    if (tipoDatoReconocido.equals("GAUGE")) {jcb_snmpv1_SetTipo.setSelectedIndex(6);}        
//        	    if (tipoDatoReconocido.equals("TIMETICKS")) {jcb_snmpv1_SetTipo.setSelectedIndex(7);}     
//        	    if (tipoDatoReconocido.equals("OPAQUE")) {jcb_snmpv1_SetTipo.setSelectedIndex(8);}         
//        	    if (tipoDatoReconocido.equals("COUNTER64")) {jcb_snmpv1_SetTipo.setSelectedIndex(9);}       
//        	    //Para SNMPv2c----------------------------------------------------------------------------------
//        	    jb_snmpv2c_SetSet.setEnabled(false);
//        	    jtf_snmpv2c_SetSetValor.setEnabled(false);  
//        	    jtf_snmpv2c_SetSetValor.setText("");
//        	    jcb_snmpv2c_SetTipo.setEnabled(false);    
//        	    jl_snmpv2c_SetEtiTipo.setEnabled(false); 
//        	    jl_snmpv2c_SetEtiSetValor.setEnabled(false);
//        	    jb_snmpv2c_setAdd.setEnabled(false);     //  ***
//        	  	jb_snmpv2c_setUndo.setEnabled(false);     //  ***   	
//        	    if (tipoDatoReconocido.equals("INTEGER")) {jcb_snmpv2c_SetTipo.setSelectedIndex(1);}    
//        	    if (tipoDatoReconocido.equals("OCTET STRING")) {jcb_snmpv2c_SetTipo.setSelectedIndex(2);}  
//        	    if (tipoDatoReconocido.equals("OBJECT IDENTIFIER")) {jcb_snmpv2c_SetTipo.setSelectedIndex(3);}     
//        	    if (tipoDatoReconocido.equals("IPADDRESS")) {jcb_snmpv2c_SetTipo.setSelectedIndex(4);}    
//        	    if (tipoDatoReconocido.equals("COUNTER")) {jcb_snmpv2c_SetTipo.setSelectedIndex(5);}       
//        	    if (tipoDatoReconocido.equals("GAUGE")) {jcb_snmpv2c_SetTipo.setSelectedIndex(6);}        
//        	    if (tipoDatoReconocido.equals("TIMETICKS")) {jcb_snmpv2c_SetTipo.setSelectedIndex(7);}     
//        	    if (tipoDatoReconocido.equals("OPAQUE")) {jcb_snmpv2c_SetTipo.setSelectedIndex(8);}         
//        	    if (tipoDatoReconocido.equals("COUNTER64")) {jcb_snmpv2c_SetTipo.setSelectedIndex(9);}       
//        	  
        	  	//Para SNMPv3--------------------------------------------------------  666
				jb_snmpv3_SetSet.setEnabled(false);
		        jtf_snmpv3_SetSetValor.setEnabled(false); 	 
		        jtf_snmpv3_SetSetValor.setText("");
		        jcb_snmpv3_setModSeg.setEnabled(false);
		        jl_snmpv3_setModSeg.setEnabled(false);
        	    jcb_snmpv3_SetTipo.setEnabled(false);    
        	    jl_snmpv3_SetEtiTipo.setEnabled(false); 
        	    jl_snmpv3_SetEtiSetValor.setEnabled(false);
				jb_snmpv3_setAdd.setEnabled(false);    //   ***
				jb_snmpv3_setUndo.setEnabled(false);    //   ***        	       	
        	    if (tipoDatoReconocido.equals("INTEGER")) {jcb_snmpv3_SetTipo.setSelectedIndex(1);}    
        	    if (tipoDatoReconocido.equals("OCTET STRING")) {jcb_snmpv3_SetTipo.setSelectedIndex(2);}  
        	    if (tipoDatoReconocido.equals("OBJECT IDENTIFIER")) {jcb_snmpv3_SetTipo.setSelectedIndex(3);}     
        	    if (tipoDatoReconocido.equals("IPADDRESS")) {jcb_snmpv3_SetTipo.setSelectedIndex(4);}    
        	    if (tipoDatoReconocido.equals("COUNTER")) {jcb_snmpv3_SetTipo.setSelectedIndex(5);}       
        	    if (tipoDatoReconocido.equals("GAUGE")) {jcb_snmpv3_SetTipo.setSelectedIndex(6);}        
        	    if (tipoDatoReconocido.equals("TIMETICKS")) {jcb_snmpv3_SetTipo.setSelectedIndex(7);}     
        	    if (tipoDatoReconocido.equals("OPAQUE")) {jcb_snmpv3_SetTipo.setSelectedIndex(8);}         
        	    if (tipoDatoReconocido.equals("COUNTER64")) {jcb_snmpv3_SetTipo.setSelectedIndex(9);}       


        	  	//-------------------------------------------------------------------- 666
        	  
        	  }
			}else{
//			  //Para SNMPv1	
//			  jb_snmpv1_SetSet.setEnabled(false);
//			  jtf_snmpv1_SetSetValor.setEnabled(false);  
//			  jtf_snmpv1_SetSetValor.setText("");
//			  jcb_snmpv1_SetTipo.setEnabled(false); 
//			  jl_snmpv1_SetEtiTipo.setEnabled(false);  	   
//			  jl_snmpv1_SetEtiSetValor.setEnabled(false);
//        	  jcb_snmpv1_SetTipo.setSelectedIndex(0);
//        	  jb_snmpv1_setAdd.setEnabled(false);     //  ***
//        	  jb_snmpv1_setUndo.setEnabled(false);     //  ***
//        	  //Para SNMPv2c
//        	  jb_snmpv2c_SetSet.setEnabled(false);
//			  jtf_snmpv2c_SetSetValor.setEnabled(false);  
//			  jtf_snmpv2c_SetSetValor.setText("");
//			  jcb_snmpv2c_SetTipo.setEnabled(false);
//			  jl_snmpv2c_SetEtiTipo.setEnabled(false); 
//			  jl_snmpv2c_SetEtiSetValor.setEnabled(false);   	   
//        	  jcb_snmpv2c_SetTipo.setSelectedIndex(0);
//        	  jb_snmpv2c_setAdd.setEnabled(false);     //  ***
//        	  jb_snmpv2c_setUndo.setEnabled(false);     //  ***        	   
        	  	
        	  //Para SNMPv3                     ------------------------------------- 666
	    	  	jb_snmpv3_SetSet.setEnabled(false);
		        jtf_snmpv3_SetSetValor.setEnabled(false); 	 
		        jtf_snmpv3_SetSetValor.setText("");
		        jcb_snmpv3_setModSeg.setEnabled(false);
		        jl_snmpv3_setModSeg.setEnabled(false);
	    	    jcb_snmpv3_SetTipo.setEnabled(false);    
	    	    jl_snmpv3_SetEtiTipo.setEnabled(false); 
	    	    jl_snmpv3_SetEtiSetValor.setEnabled(false);   	
        	  	jcb_snmpv3_SetTipo.setSelectedIndex(0);
				jb_snmpv3_setAdd.setEnabled(false);    //   ***
				jb_snmpv3_setUndo.setEnabled(false);    //   ***        	       	
        	  	 	    	    	
			  //                                ------------------------------------- 666
  
			}
			//Fin del metodo para encontrar el tipo de acceso de la variable------------------------------------------------
//            //Para SNMPv1
//            jta_snmpv1_getDescrip.setText(("").concat(node.getDescription()));
//            jta_snmpv1_GetNextDescrip.setText(("").concat(node.getDescription()));
//            jta_snmpv1_SetDescrip.setText(("").concat(node.getDescription()));
//            //System.out.println("Descripcion:\n"+node.getDescription());
//            //System.out.println("Nombre:\n"+node.getName());
//            //Para SNMPv2c
//            jta_snmpv2c_getDescrip.setText(("").concat(node.getDescription()));
//            jta_snmpv2c_GetNextDescrip.setText(("").concat(node.getDescription()));
//            jta_snmpv2c_SetDescrip.setText(("").concat(node.getDescription()));

            //Para SNMPv3   -------------------------------------------------------------------- 666
            jta_snmpv3_getDescrip.setText(("").concat(node.getDescription()));
            jta_snmpv3_GetNextDescrip.setText(("").concat(node.getDescription()));
            jta_snmpv3_SetDescrip.setText(("").concat(node.getDescription()));
            //              -------------------------------------------------------------------- 666
                        
            if ((node.getOid()).equals("")){//Aqui es necesario colocar todos los setText
//              //Para SNMPv1
//              jtf_snmpv1_getGet.setText("");
//              jtf_snmpv1_GetNextGetNext.setText("");
//              jtf_snmpv1_SetSet.setText("");
//              //jtf_snmpv1_SetSetTipo.setText("");
//              jtf_snmpv1_WalkOID.setText("");
//              jtf_snmpv1_getTablegetTable.setText("");
//              //Para SNMPv2c
//              jtf_snmpv2c_getGet.setText("");
//              jtf_snmpv2c_GetNextGetNext.setText("");
//              jtf_snmpv2c_SetSet.setText("");
//              jtf_snmpv2c_GetBulkGetBulk.setText("");
//              jtf_snmpv2c_WalkOID.setText("");
//			  jtf_snmpv2c_getTablegetTable.setText("");
              //Para SNMPv3    ----------------------------------------------666
              jtf_snmpv3_getGet.setText("");
              jtf_snmpv3_GetNextGetNext.setText("");
              jtf_snmpv3_SetSet.setText("");
              jtf_snmpv3_GetBulkGetBulk.setText("");
              jtf_snmpv3_WalkOID.setText("");
              jtf_snmpv3_getTablegetTable.setText("");
              //                ---------------------------------------------666

            }else{
//              //Para SNMPv1
//              jtf_snmpv1_getGet.setText(((".").concat(node.getOid())).concat(".0"));
//              jtf_snmpv1_GetNextGetNext.setText((".").concat(node.getOid()));
//              jtf_snmpv1_SetSet.setText(((".").concat(node.getOid())).concat(".0"));
//              jtf_snmpv1_WalkOID.setText((".").concat(node.getOid()));
//              jtf_snmpv1_getTablegetTable.setText((".").concat(node.getOid()));
//              //Para SNMPv2c
//              jtf_snmpv2c_getGet.setText(((".").concat(node.getOid())).concat(".0"));
//              jtf_snmpv2c_GetNextGetNext.setText((".").concat(node.getOid()));
//              jtf_snmpv2c_GetBulkGetBulk.setText((".").concat(node.getOid()));
//              jtf_snmpv2c_SetSet.setText(((".").concat(node.getOid())).concat(".0"));
//              jtf_snmpv2c_WalkOID.setText((".").concat(node.getOid()));
//			  jtf_snmpv2c_getTablegetTable.setText((".").concat(node.getOid()));
              //Para SNMPv3    -------------------------------------------------------------------- 666  
              jtf_snmpv3_getGet.setText(((".").concat(node.getOid())).concat(".0"));
              jtf_snmpv3_GetNextGetNext.setText((".").concat(node.getOid()));
              jtf_snmpv3_GetBulkGetBulk.setText((".").concat(node.getOid()));
              jtf_snmpv3_SetSet.setText(((".").concat(node.getOid())).concat(".0"));
              jtf_snmpv3_WalkOID.setText((".").concat(node.getOid()));
              jtf_snmpv3_getTablegetTable.setText((".").concat(node.getOid()));
              //               -------------------------------------------------------------------- 666                

            }
            
            //System.out.println("OID:\n"+node.getOid());
            //descriptionArea.setCaretPosition(0);
            //System.out.println("OID:\n"+node.getDescription());
            
        }
        //snmpPanel.updateOid();
    }

	/** Returns an ImageIcon, or null if the path was invalid. */
    protected ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = managerSNMP.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

	//para ver si el parametro es un nmero, si lo es retorna verdad sino falso
	public boolean esNumero(String parametro){
	  boolean respuesta = true;
	  if(parametro.equals("")){
	    respuesta=false;
	  }else{
		String abc = "1234567890";
	    int tam = parametro.length();
		int TAMabc = abc.length();
		int i=0,j=0;
	   	for (i = 0;  i < tam;  i++){
	   	  char ch = parametro.charAt(i);
		  for (j = 0;  j < TAMabc;  j++){
			char s = abc.charAt(j);
	       	if (ch==s)  break;
		  }
    	  if (j == TAMabc){ respuesta=false;}
		}
	  }
	  return respuesta;
	}
	
	//para validar que el parametro no sea vacio, si lo es retorna verdad sino falso
	public boolean esVacio(String parametro){
	  boolean respuesta = false;	
	  if(parametro.equals("")){
	  	respuesta=true;	
	  }
	  return respuesta;	
	}
	
	//Para validar que un string es una direccion ip valida
	public boolean ipValida(String IP){
      boolean respuesta = true;
      try{
        InetAddress destino = InetAddress.getByName(IP);
      }catch(Exception pp){respuesta=false;}
      return respuesta;
    }
  
    //para validar que el parametro sea un oid
	public boolean esOID(String parametro){
	  boolean respuesta = true;
	  OID pruebaOID;
	  try{
	  	pruebaOID = new OID(parametro);
	  }catch(Exception e3){respuesta = false;}
	  return respuesta;	
	}
  
  
  //////////////////////////////////
  private JPanel snmpv2c;
  //////////////////////////////////
  private JPanel snmpv3;

  public managerSNMP(){
  	
  	//----------DEFINICION DEL TAMAO Y UBICACION DE LA VENTANA EN LA PANTALLA----------------
  	Dimension pantalla = getToolkit().getScreenSize();//obtiene el tamao usado de la pantalla
  	int ancho  = 800;//ancho de la ventana a hacer
  	int alto   = 600;//alto de la ventana a hacer
  	int anchop = ((int)(((pantalla.getWidth ())-ancho)/2));//para calcular el centro del ancho
  	int altop  = ((int)(((pantalla.getHeight())- alto)/2));//para calcular el centro del alto
    //------FIN DE LA DEFINICION DEL TAMAO Y UBICACION DE LA VENTANA EN LA PANTALLA----------
    
  	
  	//----------------------------CONSTRUCCION DE LA VENTANA----------------------------------
  	jframe = new JFrame(nameOfTheProgram);//titulo de la ventana
  	jframe.setBounds(anchop,altop,ancho,alto);//en que posicion y de que tamao se va a situar la ventana en la pantalla
  	jframe.setIconImage(new ImageIcon("images/pred.gif").getImage());//se establece el icono de la ventana
  	jframe.setResizable(false);//para que la ventana no se pueda maximizar
  	//------------------------FIN DE LA CONSTRUCCION DE LA VENTANA----------------------------
  	
  	
  	//--------------------------CREACION DEL JPANEL PRINCIPAL---------------------------------
  	jpanel = new JPanel(true);//se crea el panel sobre el que se van a poner todos los componentes
    //jpanel.setBackground(Color.lightGray);//establece el color del panel
    jpanel.setLayout(null);//establece layout nulo, de esta forma puedes poner los componentes en un xy especifico
  	jframe.getContentPane().add(jpanel,"Center");//adiciona el panel a la ventana
    //----------------------FIN DE LA CREACION DEL JPANEL PRINCIPAL---------------------------
  	
  	//-------------------------------PANEL DEL MIBTREE----------------------------------------
	  	jp_mibtree = new JPanel();
		jp_mibtree.setBackground(Color.black);
		jp_mibtree.setBounds(new Rectangle(2,2,300,541));
		jp_mibtree.setLayout(null);
		//snmpv1.setVisible(false);
		jpanel.add(jp_mibtree,null);
		
		//Para inicializar el arreglo de mibs
		loadedMibs = new ArrayList();
		loadedMibsParaBuscarNombres = new ArrayList();
		//Para pintar el mib browser
		mibTree = MibTreeBuilder.getInstance().getTree();
		//ImageIcon leafIcon = createImageIcon("images/hojaMini.gif");
		ImageIcon otroIcon = null;
		//if (leafIcon != null) {
		  DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		  //renderer.setLeafIcon(leafIcon);
		  renderer.setClosedIcon(createImageIcon("images/gray-small.png"));
		  renderer.setOpenIcon(createImageIcon("images/green-small.png"));
		  mibTree.setCellRenderer(renderer);
		//}
		mibTree.addTreeSelectionListener(new TreeSelectionListener() {
		  public void valueChanged(TreeSelectionEvent e) {
		    updateTreeSelection();
		  }
		});
		jsp_mibtree = new JScrollPane(mibTree);
	    jsp_mibtree.setBounds(new Rectangle(0,0,300,511));
	    jsp_mibtree.setWheelScrollingEnabled(true);
	    jp_mibtree.add(jsp_mibtree,null);
	    
	    //Para cargar la mib
   		try {loadMib();} catch (Exception e){e.printStackTrace();}
	    //Fin del cargar mib
	        
	    jb_mibtree = new JButton("Importar MIBs");
	    jb_mibtree.setBounds(new Rectangle(0,511,300,30));
	    jb_mibtree.setToolTipText("Presione para importar MIBs.");
	    jp_mibtree.add(jb_mibtree,null);
	    
    	jb_mibtree.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e) {
            try {loadNewMib();} catch (Exception e1){e1.printStackTrace();}
          }
       	});
  	//-------------------------------FIN DEL PANEL DEL MIBTREE--------------------------------
  	  	
  	//--------------------------------Manager SNMPv1------------------------------------------
//  	snmpv1 = new JPanel();
//	//snmpv1.setBackground(Color.black);
//	//snmpv1.setBounds(new Rectangle(0,0,100,100));
//	snmpv1.setLayout(null);
//	//snmpv1.setVisible(false);
//	//jpanel.add(snmpv1,null);	
//	
//	jl_snmpv1_sel = new JLabel("Accin a realizar");//USO DEL JLABEL
//    jl_snmpv1_sel.setBounds(new Rectangle(5,5,100,20));//establece el xy del componente   
//    snmpv1.add(jl_snmpv1_sel,null);
//    
//    jcb_snmpv1_sel = new JComboBox();//USO DEL JCOMBOBOX
//  	jcb_snmpv1_sel.setBounds(new Rectangle(110,5,373,20));
//	jcb_snmpv1_sel.addItem("Configure Parameters");
//  	jcb_snmpv1_sel.addItem("Comando Get");
//  	jcb_snmpv1_sel.addItem("Comando GetNext");
//  	jcb_snmpv1_sel.addItem("Comando GetTable");
//  	jcb_snmpv1_sel.addItem("Comando Walk");
//  	jcb_snmpv1_sel.addItem("Comando Set");
//  	jcb_snmpv1_sel.addItem("Enviar/Ver Traps");
//  	//jcb_snmpv1_sel.setMaximumRowCount(2);
//  	snmpv1.add(jcb_snmpv1_sel,null);
//  	
//  	jcb_snmpv1_sel.addActionListener(new ActionListener(){
//      public void actionPerformed(ActionEvent e) {
//        jp_snmpv1_Con.setVisible(false);
//        jp_snmpv1_Get.setVisible(false);
//        jp_snmpv1_GetNext.setVisible(false);
//        jp_snmpv1_Set.setVisible(false);
//        jp_snmpv1_Traps.setVisible(false);
//        jp_snmpv1_TrapsSend.setVisible(false);    
//        jp_snmpv1_walk.setVisible(false);  
//        jp_snmpv1_getTable.setVisible(false);
//        if ((jcb_snmpv1_sel.getSelectedItem())==ConfigureParameters){jp_snmpv1_Con.setVisible(true);}
//        if ((jcb_snmpv1_sel.getSelectedItem())==ComandoGet){jp_snmpv1_Get.setVisible(true);}
//        if ((jcb_snmpv1_sel.getSelectedItem())==ComandoGetNext){jp_snmpv1_GetNext.setVisible(true);}
//        if ((jcb_snmpv1_sel.getSelectedItem())==ComandoGetTable){jp_snmpv1_getTable.setVisible(true);}
//        if ((jcb_snmpv1_sel.getSelectedItem())==ComandoWalk){jp_snmpv1_walk.setVisible(true);}
//        if ((jcb_snmpv1_sel.getSelectedItem())==ComandoSet){jp_snmpv1_Set.setVisible(true);}
//        if ((jcb_snmpv1_sel.getSelectedItem())==EnviarVerTraps){jp_snmpv1_Traps.setVisible(true);}
//        if ((jcb_snmpv1_sel.getSelectedItem())==EnviarVerTraps){jp_snmpv1_TrapsSend.setVisible(true);}   
//      }
//    });
//	
		//----------------------------------Pantalla de Conexin-----------------------------------
//	    jp_snmpv1_Con = new JPanel();
//	    //jp_snmpv1_Con.setBackground(Color.white);
//	    jp_snmpv1_Con.setBounds(new Rectangle(0,30,483,483));
//		jp_snmpv1_Con.setBorder(BorderFactory.createTitledBorder("Configure Parameters"));
//		jp_snmpv1_Con.setLayout(null);
//		jp_snmpv1_Con.setVisible(true);
//		snmpv1.add(jp_snmpv1_Con,null);
//		
//		jl_snmpv1_IP = new JLabel("Direccin IP del Agente");//USO DEL JLABEL
//    	jl_snmpv1_IP.setBounds(new Rectangle(76,86,160,20));//establece el xy del componente
//    	jp_snmpv1_Con.add(jl_snmpv1_IP,null);
//    
//    	jtf_snmpv1_IP = new JTextField();//USO DEL JTEXTFIELD
//    	jtf_snmpv1_IP.setBounds(new Rectangle(246,86,160,20));//establece el xy del componente
//    	jtf_snmpv1_IP.setText(String.valueOf(IP));
//    	jp_snmpv1_Con.add(jtf_snmpv1_IP,null);
//    	
//    	jl_snmpv1_pto = new JLabel("Puerto de Comunicaciones");//USO DEL JLABEL
//    	jl_snmpv1_pto.setBounds(new Rectangle(76,126,160,20));//establece el xy del componente
//    	jp_snmpv1_Con.add(jl_snmpv1_pto,null);
//    
//    	jtf_snmpv1_pto = new JTextField();//USO DEL JTEXTFIELD
//    	jtf_snmpv1_pto.setBounds(new Rectangle(246,126,160,20));//establece el xy del componente
//    	jtf_snmpv1_pto.setText(String.valueOf(pto));
//    	jp_snmpv1_Con.add(jtf_snmpv1_pto,null);
//    	
//    	jl_snmpv1_comLec = new JLabel("Comunidad de Lectura");//USO DEL JLABEL
//    	jl_snmpv1_comLec.setBounds(new Rectangle(76,206,160,20));//establece el xy del componente
//    	jp_snmpv1_Con.add(jl_snmpv1_comLec,null);
//    	
//     	jtf_snmpv1_comLec = new JPasswordField();//USO DEL JPASSWORDFIELD
//    	jtf_snmpv1_comLec.setBounds(new Rectangle(246,206,160,20));//establece el xy del componente
//    	jtf_snmpv1_comLec.setEchoChar('*');
//    	jtf_snmpv1_comLec.setText(String.valueOf(comLec));
//    	//jpf_snmpv1_comEsc.setEchoChar((char)0);
//    	jp_snmpv1_Con.add(jtf_snmpv1_comLec,null);
//    	
//    	jtf_snmpv1_comEsc = new JLabel("Comunidad de Escritura");//USO DEL JLABEL
//    	jtf_snmpv1_comEsc.setBounds(new Rectangle(76,246,160,20));//establece el xy del componente
//    	jp_snmpv1_Con.add(jtf_snmpv1_comEsc,null);
//    
//    	jpf_snmpv1_comEsc = new JPasswordField();//USO DEL JPASSWORDFIELD
//    	jpf_snmpv1_comEsc.setBounds(new Rectangle(246,246,160,20));//establece el xy del componente
//    	jpf_snmpv1_comEsc.setEchoChar('*');
//    	jpf_snmpv1_comEsc.setText(String.valueOf(comEsc));
//    	//jpf_snmpv1_comEsc.setEchoChar((char)0);
//    	jp_snmpv1_Con.add(jpf_snmpv1_comEsc,null);
//
//    	jl_snmpv1_VerCom = new JLabel("Visualizar Comunidades");//USO DEL JLABEL
//    	jl_snmpv1_VerCom.setBounds(new Rectangle(76,166,160,20));//establece el xy del componente
//    	jp_snmpv1_Con.add(jl_snmpv1_VerCom,null);
//
//	    jcb_snmpv1_VerCom = new JComboBox();//USO DEL JCOMBOBOX
//	  	jcb_snmpv1_VerCom.setBounds(new Rectangle(246,166,160,20));  
//		jcb_snmpv1_VerCom.addItem("Si");
//	  	jcb_snmpv1_VerCom.addItem("No");
//	  	jcb_snmpv1_VerCom.setSelectedIndex(1);	  	 
//	  	jp_snmpv1_Con.add(jcb_snmpv1_VerCom,null);
//
//	  	jcb_snmpv1_VerCom.addActionListener(new ActionListener(){
//	      public void actionPerformed(ActionEvent e) {	          
//	        if ((jcb_snmpv1_VerCom.getSelectedItem())==opcionSi){jpf_snmpv1_comEsc.setEchoChar((char)0);jtf_snmpv1_comLec.setEchoChar((char)0);}; 
//	        if ((jcb_snmpv1_VerCom.getSelectedItem())==opcionNo){jpf_snmpv1_comEsc.setEchoChar('*');jtf_snmpv1_comLec.setEchoChar('*');}; 	
//	      }
//	    });
//	    
//    	jl_snmpv1_inten = new JLabel("Nro. de Intentos");//USO DEL JLABEL
//    	jl_snmpv1_inten.setBounds(new Rectangle(76,286,160,20));//establece el xy del componente
//    	jp_snmpv1_Con.add(jl_snmpv1_inten,null);
//    
//    	jtf_snmv1_inten = new JTextField();//USO DEL JTEXTFIELD
//    	jtf_snmv1_inten.setBounds(new Rectangle(246,286,160,20));//establece el xy del componente
//    	jtf_snmv1_inten.setText(String.valueOf(inten));
//    	jp_snmpv1_Con.add(jtf_snmv1_inten,null);
//    	
//    	jl_snmpv1_timeOut = new JLabel("Tiempo de Espera (ms)");//USO DEL JLABEL
//    	jl_snmpv1_timeOut.setBounds(new Rectangle(76,326,160,20));//establece el xy del componente
//    	jp_snmpv1_Con.add(jl_snmpv1_timeOut,null);
//    
//    	jtt_snmpv1_timeOut = new JTextField();//USO DEL JTEXTFIELD
//    	jtt_snmpv1_timeOut.setBounds(new Rectangle(246,326,160,20));//establece el xy del componente
//    	jtt_snmpv1_timeOut.setText(String.valueOf(timeOut));
//    	jp_snmpv1_Con.add(jtt_snmpv1_timeOut,null);
//    	
//    	jb_snmpv1_aplicarPara = new JButton("Aplicar Cambios");
//	    jb_snmpv1_aplicarPara.setBounds(new Rectangle(141,376,200,20));
//	    jb_snmpv1_aplicarPara.setToolTipText("Presione para establecer los nuevos parmetros.");
//	    jp_snmpv1_Con.add(jb_snmpv1_aplicarPara,null);
//	
//		jb_snmpv1_aplicarPara.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//          	String men_err = "";
//          	boolean men_err_l = false;
//
//          		if (esVacio(jtf_snmpv1_IP.getText())){          			
//          		  men_err = men_err.concat(configParamError01);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          	  	if (!(ipValida(jtf_snmpv1_IP.getText()))){
//          	  	  men_err = men_err.concat(configParamError02);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          	  	
//          		if (!(esNumero(jtf_snmpv1_pto.getText()))){          			
//          		  men_err = men_err.concat(configParamError03);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          	  	if (esVacio(jpf_snmpv1_comEsc.getText())){          			
//          		  men_err = men_err.concat(configParamError04);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          	  	if (esVacio(jtf_snmpv1_comLec.getText())){          			
//          		  men_err = men_err.concat(configParamError05);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          		if (!(esNumero(jtf_snmv1_inten.getText()))){          			
//          		  men_err = men_err.concat(configParamError06);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          		if ((esNumero(jtf_snmv1_inten.getText()))&&(Integer.parseInt(jtf_snmv1_inten.getText())<=0)){          			
//          		  men_err = men_err.concat(configParamError07);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          		if (!(esNumero(jtt_snmpv1_timeOut.getText()))){          			
//          		  men_err = men_err.concat(configParamError08);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          		if ((esNumero(jtt_snmpv1_timeOut.getText()))&&(Integer.parseInt(jtt_snmpv1_timeOut.getText())<=0)){          			
//          		  men_err = men_err.concat(configParamError09);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//
//          		if (men_err_l){          			
//				JOptionPane.showMessageDialog(managerSNMP.this,men_err,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	          	  	          	  
//		    	if (esVacio(jtf_snmpv1_IP.getText())){jtf_snmpv1_IP.setText(String.valueOf(IP));}
//		    	if (!(ipValida(jtf_snmpv1_IP.getText()))){jtf_snmpv1_IP.setText(String.valueOf(IP));}
//		    	if (!(esNumero(jtt_snmpv1_timeOut.getText()))){jtt_snmpv1_timeOut.setText(String.valueOf(timeOut));}
//		    	if ((esNumero(jtt_snmpv1_timeOut.getText()))&&(Integer.parseInt(jtt_snmpv1_timeOut.getText())<=0)){jtt_snmpv1_timeOut.setText(String.valueOf(timeOut));}
//		    	if (!(esNumero(jtf_snmv1_inten.getText()))){jtf_snmv1_inten.setText(String.valueOf(inten));}
//		    	 if ((esNumero(jtf_snmv1_inten.getText()))&&(Integer.parseInt(jtf_snmv1_inten.getText())<=0)){jtf_snmv1_inten.setText(String.valueOf(inten));}
//				if (!(esNumero(jtf_snmpv1_pto.getText()))){jtf_snmpv1_pto.setText(String.valueOf(pto));}    
//				if (esVacio(jpf_snmpv1_comEsc.getText())){jpf_snmpv1_comEsc.setText(String.valueOf(comEsc));}
//				if (esVacio(jtf_snmpv1_comLec.getText())){jtf_snmpv1_comLec.setText(String.valueOf(comLec));}
//
//				} else {          	  	          	  	
//          		IP =		 jtf_snmpv1_IP.getText();
//          		pto =	 Long.parseLong(jtf_snmpv1_pto.getText());
//          		comLec =	 jtf_snmpv1_comLec.getText();
//          		comEsc =	 jpf_snmpv1_comEsc.getText();
//          		inten =	 Integer.parseInt(jtf_snmv1_inten.getText());
//          		timeOut = Integer.parseInt(jtt_snmpv1_timeOut.getText());
//				
//				JOptionPane.showMessageDialog(managerSNMP.this, configParamResult01 ,nameOfTheProgram,JOptionPane.INFORMATION_MESSAGE,(new ImageIcon("images/help.gif")));
//
//				//PARA LLENAR LOS PARAMETROS DE SNMPv2c					
//				jtf_snmpv2c_IP.setText(IP);
//          		jtf_snmpv2c_pto.setText(String.valueOf(pto));
//          		jtf_snmpv2c_comLec.setText(comLec);
//          		jpf_snmpv2c_comEsc.setText(comEsc);
//          		jtf_snmv2c_inten.setText(String.valueOf(inten));
//          		jtt_snmpv2c_timeOut.setText(String.valueOf(timeOut));
//          		
//          		//PARA LLENAR LOS PARAMETROS DE SNMPv3	
//				jtf_snmpv3_IP.setText(IP);
//          		jtf_snmpv3_pto.setText(String.valueOf(pto));
//          		jtf_snmv3_inten.setText(String.valueOf(inten));
//          		jtt_snmpv3_timeOut.setText(String.valueOf(timeOut));	
//				}
//         	}
//       	});
//	    //----------------------------------Fin de Pantalla de Conexin----------------------------
//	    
//	    
//	    //----------------------------------Pantalla de Get----------------------------------------  	
//	    jp_snmpv1_Get = new JPanel();
//	    //jp_snmpv1_Get.setBackground(Color.blue);
//		//jp_snmpv1_Get.setBorder(BorderFactory.createTitledBorder("Comando Get de SNMPv1"));
//		jp_snmpv1_Get.setBounds(new Rectangle(0,30,483,483));
//		jp_snmpv1_Get.setBorder(BorderFactory.createTitledBorder("Comando Get"));
//		jp_snmpv1_Get.setLayout(null);
//		jp_snmpv1_Get.setVisible(false);
//		snmpv1.add(jp_snmpv1_Get,null);    	    	
//    	
//    	jsp_snmpv1_getDescrip = new JScrollPane();
//    	jsp_snmpv1_getDescrip.setBounds(new Rectangle(10,20,465,270));
//    	jsp_snmpv1_getDescrip.setWheelScrollingEnabled(true);
//    	jp_snmpv1_Get.add(jsp_snmpv1_getDescrip,null);
//    	
//    	jta_snmpv1_getDescrip = new JTextArea();
//    	jta_snmpv1_getDescrip.setText("");
//    	jta_snmpv1_getDescrip.setEditable(false);
//    	jsp_snmpv1_getDescrip.getViewport().add(jta_snmpv1_getDescrip,null);
//    	
//    	jl_snmpv1_getEtiGet = new JLabel("OID");
//	    jl_snmpv1_getEtiGet.setBounds(new Rectangle(10,300,20,20));
//	    jp_snmpv1_Get.add(jl_snmpv1_getEtiGet,null);
//	    
//	    jtf_snmpv1_getGet = new JTextField();
//	    jtf_snmpv1_getGet.setBounds(new Rectangle(40,300,245,20));   
//	    jtf_snmpv1_getGet.setEditable(true);
//	    jp_snmpv1_Get.add(jtf_snmpv1_getGet,null);
//
//    	jb_snmpv1_add = new JButton("Aadir");
//	    jb_snmpv1_add.setBounds(new Rectangle(295,300,79,20));  //9999
//	    jb_snmpv1_add.setToolTipText("Presione para agregar el OID.");
//	    jp_snmpv1_Get.add(jb_snmpv1_add,null);
//	    
//    	jl_snmpv1_getObjs = new JLabel("Objetos");
//	    jl_snmpv1_getObjs.setBounds(new Rectangle(10,330,60,20));
//	    jp_snmpv1_Get.add(jl_snmpv1_getObjs,null);
//	    
//	    jtf_snmpv1_getObjs = new JTextField();
//	    jtf_snmpv1_getObjs.setBounds(new Rectangle(60,330,344,20));   
//	    jtf_snmpv1_getObjs.setEditable(false);	    	    
//	    jp_snmpv1_Get.add(jtf_snmpv1_getObjs,null);
//
//    	jb_snmpv1_undo = new JButton("Deshacer");
//	    jb_snmpv1_undo.setBounds(new Rectangle(384,300,90,20));  //9999
//	    jb_snmpv1_undo.setToolTipText("Presione para eliminar el ltimo OID de los objetos.");
//	    jp_snmpv1_Get.add(jb_snmpv1_undo,null);
//
//    	jb_snmpv1_getGet = new JButton("Get");
//	    jb_snmpv1_getGet.setBounds(new Rectangle(415,330,59,20));  
//	    jb_snmpv1_getGet.setToolTipText("Presione para obtener el valor.");
//	    jp_snmpv1_Get.add(jb_snmpv1_getGet,null);
//	    
//	    jsp_snmpv1_getResp = new JScrollPane();
//    	jsp_snmpv1_getResp.setBounds(new Rectangle(10,360,465,111));  
//    	jsp_snmpv1_getResp.setWheelScrollingEnabled(true);
//    	jp_snmpv1_Get.add(jsp_snmpv1_getResp,null);
//    	
//    	jta_snmpv1_getResp = new JTextArea();
//    	jta_snmpv1_getResp.setText("");
//    	jta_snmpv1_getResp.setEditable(false);
//    	jsp_snmpv1_getResp.getViewport().add(jta_snmpv1_getResp,null);
//
//		compuestoGetSNMPv1Temp = new Vector();
//	    	    
//	    //Para el boton aadir
//	    jb_snmpv1_add.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//        	  
//        	  //aqui lo que hace
//          	  if ((jtf_snmpv1_getGet.getText()).equals("")){
//          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//          	  }else{
//          	  	//Tratamiento
//          	  	OID = jtf_snmpv1_getGet.getText();
//          	  	
//          	  	if (esVacio(IP)){
//          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
//          	  	}else{        	  	
//          	  	          	  	
//					//Para transformar el OID de string al arreglo para pasarselo al get
//	          	  	//System.out.println("Tratando el oid");
//	          	  	try{
//	          	  		//Se usa solo para comprobar que relamente se esta pasando un oid
//		          	  	int digitos=0;
//		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
//						while(Token.hasMoreTokens()){
//						  Token.nextToken();
//						  digitos++;	
//						}
//		          	  	//System.out.println("el oid tiene "+digitos+"digitos");
//		          	  	int tamao=digitos;
//		          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
//		          	  	int requerimiento[] = new int[tamao];
//		          	  	int i=0;
//		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
//						while(Token1.hasMoreTokens()){
//						  requerimiento[i]=Integer.parseInt(Token1.nextToken());
//						  i++;
//						  //System.out.println("valor de i: "+i);	
//						}
//		          	  	//requerimiento[i]=0;
//		          	  	/*
//		          	  	System.out.println("cambiado a arreglo");
//		          	  	i++;
//		          	  	System.out.println("tamao del arreglo: "+i);
//		          	  	for (int j=0;j<i;j++){
//		          	  	  System.out.println("d"+j+" valor: "+requerimiento[j]);	
//		          	  	}
//		          	  	*/
//		          	  	
//		          	  	//private Vector compuestoGetSNMPv1; //Para pasar los multiples parametros
//    					//private Vector compuestoGetSNMPv1Temp;
//		          	  	
//		          	  	
//		          	  	compuestoGetSNMPv1Temp.add(new OID(requerimiento));
//	          	  	    
//	          	  	    String contenido = "";
//					    for (int pp=0;pp<compuestoGetSNMPv1Temp.size();pp++){
//					      contenido=contenido.concat(compuestoGetSNMPv1Temp.get(pp)+"; ");	
//					    }
//					    jtf_snmpv1_getObjs.setText(contenido);
//		          	  	
//		          	  }catch(NumberFormatException nfe){
//	          	  	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//	          	  	  }
//          	  	}
//          	  }
//        	  
//        	}
//        }); 
//        	
//        //Para el boton deshacer
//        jb_snmpv1_undo.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//        
//        	  int ultimo = compuestoGetSNMPv1Temp.size();
//        	  ultimo=ultimo-1;
//        	  if (ultimo>=0){
//        	    compuestoGetSNMPv1Temp.removeElementAt(ultimo);
//        	  }else{
//        	  	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//        	  }
//    	  	
//		      String contenido = "";
//		      for (int pp=0;pp<compuestoGetSNMPv1Temp.size();pp++){
//		        contenido=contenido.concat(compuestoGetSNMPv1Temp.get(pp)+"; ");	
//		      }
//		      jtf_snmpv1_getObjs.setText(contenido);
//        
//        	}
//        }); 
//	    
//    	//esto va para el get no para aca
//	      jb_snmpv1_getGet.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//          	  //aqui lo que hace
//          	  if ((jtf_snmpv1_getGet.getText()).equals("")){
//          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//          	  }else{
//          	  	//Tratamiento
//          	  	OID = jtf_snmpv1_getGet.getText();
//          	  	
//          	  	if (esVacio(IP)){
//          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
//          	  	}else{        	  	
//          	  	          	  	
//					//Para transformar el OID de string al arreglo para pasarselo al get
//	          	  	//System.out.println("Tratando el oid");
//	          	  	try{
//	          	  		//Se usa solo para comprobar que relamente se esta pasando un oid
//		          	  	int digitos=0;
//		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
//						while(Token.hasMoreTokens()){
//						  Token.nextToken();
//						  digitos++;	
//						}
//		          	  	//System.out.println("el oid tiene "+digitos+"digitos");
//		          	  	int tamao=digitos;
//		          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
//		          	  	int requerimiento[] = new int[tamao];
//		          	  	int i=0;
//		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
//						while(Token1.hasMoreTokens()){
//						  requerimiento[i]=Integer.parseInt(Token1.nextToken());
//						  i++;
//						  //System.out.println("valor de i: "+i);	
//						}
//		          	  	//requerimiento[i]=0;
//		          	  	/*
//		          	  	System.out.println("cambiado a arreglo");
//		          	  	i++;
//		          	  	System.out.println("tamao del arreglo: "+i);
//		          	  	for (int j=0;j<i;j++){
//		          	  	  System.out.println("d"+j+" valor: "+requerimiento[j]);	
//		          	  	}
//		          	  	*/
//		          	  	
//		          	  	//private Vector compuestoGetSNMPv1; //Para pasar los multiples parametros
//    					//private Vector compuestoGetSNMPv1Temp;
//		          	  	
//		          	  	boolean esCompuesto=false;
//		          	  	compuestoGetSNMPv1 = new Vector();
//		          	  	if((compuestoGetSNMPv1Temp.size())>=1){
//		          	  	   compuestoGetSNMPv1=compuestoGetSNMPv1Temp;
//		          	  	   esCompuesto=true;	
//		          	  	}else{
//		          	  	   compuestoGetSNMPv1.add(new OID(requerimiento));
//		          	  	   esCompuesto=false;
//		          	  	}
//		          	  	
//		          	  	//compuestoGetSNMPv1.add(new OID(requerimiento));
//	          	  	    
//		          	  	SNMPv1 manager = new SNMPv1();
//		          	  	
//		          	  	String respuesta = manager.getv1(IP, String.valueOf(pto), comLec, inten,timeOut,compuestoGetSNMPv1,erroresGenerales04);
//		          	  	compuestoGetSNMPv1.removeAllElements();
//		          	  	compuestoGetSNMPv1Temp.removeAllElements();
//				        jtf_snmpv1_getObjs.setText("");
//		          	  		
//		          	  	//System.out.println("Getv1: "+respuesta);
//		          	  	if (respuesta.equals(erroresGenerales04)){
//		          	  	  respuesta=("Get SNMPv1: ").concat(respuesta);
//		          	  	}else{
//		          	  	  respuesta=("Get SNMPv1: ").concat(respuesta);	
//		          	  	}
//		          	  	respuesta=respuesta.concat("\n");
//		          	  	jta_snmpv1_getResp.setText((jta_snmpv1_getResp.getText()).concat(respuesta));
//		          	  }catch(NumberFormatException nfe){
//	          	  	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//	          	  	  }
//          	  	}
//          	  }
//         	}
//       	});
//       	
//   		
//	    //jp_snmpv1_Get.setVisible(true);
//				
//		//----------------------------------Fin de Pantalla de Get---------------------------------
//		
//		
//		//----------------------------------Pantalla de GetNext------------------------------------	      	
//		jp_snmpv1_GetNext = new JPanel();
//	    //jp_snmpv1_GetNext.setBackground(Color.blue);
//		//jp_snmpv1_GetNext.setBorder(BorderFactory.createTitledBorder("Comando GetNext de SNMPv1"));
//		jp_snmpv1_GetNext.setBounds(new Rectangle(0,30,483,483));  
//		jp_snmpv1_GetNext.setBorder(BorderFactory.createTitledBorder("Comando GetNext"));
//		jp_snmpv1_GetNext.setLayout(null);
//		jp_snmpv1_GetNext.setVisible(false);
//		snmpv1.add(jp_snmpv1_GetNext,null);
//		    	
//    	jsp_snmpv1_GetNextDescrip = new JScrollPane();
//    	jsp_snmpv1_GetNextDescrip.setBounds(new Rectangle(10,20,465,270));  //320,10,435,300
//    	jsp_snmpv1_GetNextDescrip.setWheelScrollingEnabled(true);
//    	jp_snmpv1_GetNext.add(jsp_snmpv1_GetNextDescrip,null);
//    	
//    	jta_snmpv1_GetNextDescrip = new JTextArea();           
//    	jta_snmpv1_GetNextDescrip.setText("");
//    	jta_snmpv1_GetNextDescrip.setEditable(false);
//    	jsp_snmpv1_GetNextDescrip.getViewport().add(jta_snmpv1_GetNextDescrip,null);
//    	
//    	jl_snmpv1_GetNextEtiGetNext = new JLabel("OID");
//	    jl_snmpv1_GetNextEtiGetNext.setBounds(new Rectangle(10,300,20,20));    
//	    jp_snmpv1_GetNext.add(jl_snmpv1_GetNextEtiGetNext,null);
//	    
//	    jtf_snmpv1_GetNextGetNext = new JTextField();
//	    jtf_snmpv1_GetNextGetNext.setBounds(new Rectangle(40,300,245,20));  
//	    jtf_snmpv1_GetNextGetNext.setEditable(true);
//	    jp_snmpv1_GetNext.add(jtf_snmpv1_GetNextGetNext,null);
//
//    	jb_snmpv1_GetNext_add = new JButton("Aadir");
//	    jb_snmpv1_GetNext_add.setBounds(new Rectangle(295,300,79,20));  //9999
//	    jb_snmpv1_GetNext_add.setToolTipText("Presione para agregar el OID.");
//	    jp_snmpv1_GetNext.add(jb_snmpv1_GetNext_add,null);
//	    
//    	jl_snmpv1_getNextObjs = new JLabel("Objetos");
//	    jl_snmpv1_getNextObjs.setBounds(new Rectangle(10,330,60,20));
//	    jp_snmpv1_GetNext.add(jl_snmpv1_getNextObjs,null);
//	   
//	    jtf_snmpv1_getNextObjs = new JTextField();
//	    jtf_snmpv1_getNextObjs.setBounds(new Rectangle(60,330,325,20));   
//	    jtf_snmpv1_getNextObjs.setEditable(false);	    	    
//	    jp_snmpv1_GetNext.add(jtf_snmpv1_getNextObjs,null);
//
//    	jb_snmpv1_GetNextUndo = new JButton("Deshacer");
//	    jb_snmpv1_GetNextUndo.setBounds(new Rectangle(384,300,90,20));  //9999
//	    jb_snmpv1_GetNextUndo.setToolTipText("Presione para eliminar el ltimo OID de los objetos.");
//	    jp_snmpv1_GetNext.add(jb_snmpv1_GetNextUndo,null);
//	    
//    	jb_snmpv1_GetNextGetNext = new JButton("GetNext");
//	    jb_snmpv1_GetNextGetNext.setBounds(new Rectangle(395,330,79,20));   
//	    jb_snmpv1_GetNextGetNext.setToolTipText("Presione para obtener el valor.");
//	    jp_snmpv1_GetNext.add(jb_snmpv1_GetNextGetNext,null);
//	    
//	    jsp_snmpv1_GetNextResp = new JScrollPane();
//    	jsp_snmpv1_GetNextResp.setBounds(new Rectangle(10,360,465,111));   
//    	jsp_snmpv1_GetNextResp.setWheelScrollingEnabled(true);
//    	jp_snmpv1_GetNext.add(jsp_snmpv1_GetNextResp,null);
//    	
//    	jta_snmpv1_GetNextResp = new JTextArea();
//    	jta_snmpv1_GetNextResp.setText("");
//    	jta_snmpv1_GetNextResp.setEditable(false);
//    	jsp_snmpv1_GetNextResp.getViewport().add(jta_snmpv1_GetNextResp,null);
//	    
//	    compuestoGetNextSNMPv1Temp = new Vector();
//	    
//	    //Para el boton aadir
//	    jb_snmpv1_GetNext_add.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e){
//        	  //aqui lo que hace
//          	  if ((jtf_snmpv1_GetNextGetNext.getText()).equals("")){
//          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//          	  }else{
//          	  	//Tratamiento
//          	  	OID = jtf_snmpv1_GetNextGetNext.getText();
//          	  	if (esVacio(IP)){
//          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
//          	  	}else{        	  	
//          	  	          	  	
//					//Para transformar el OID de string al arreglo para pasarselo al GetNext
//	          	  	//System.out.println("Tratando el oid");
//	          	  	try{
//	          	  		//Se usa solo para comprobar que relamente se esta pasando un oid
//		          	  	int digitos=0;
//		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
//						while(Token.hasMoreTokens()){
//						  Token.nextToken();
//						  digitos++;	
//						}
//		          	  	//System.out.println("el oid tiene "+digitos+"digitos");
//		          	  	int tamao=digitos;
//		          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
//		          	  	int requerimiento[] = new int[tamao];
//		          	  	int i=0;
//		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
//						while(Token1.hasMoreTokens()){
//						  requerimiento[i]=Integer.parseInt(Token1.nextToken());
//						  i++;
//						  //System.out.println("valor de i: "+i);	
//						}
//		          	  	//requerimiento[i]=0;
//		          	  	/*
//		          	  	System.out.println("cambiado a arreglo");
//		          	  	i++;
//		          	  	System.out.println("tamao del arreglo: "+i);
//		          	  	for (int j=0;j<i;j++){
//		          	  	  System.out.println("d"+j+" valor: "+requerimiento[j]);	
//		          	  	}
//		          	  	*/
//		          	  	
//		          	  	compuestoGetNextSNMPv1Temp.add(new OID(requerimiento));
//		          	  	String contenido = "";
//		          	  	for (int pp=0;pp<compuestoGetNextSNMPv1Temp.size();pp++){
//		          	  	  contenido=contenido.concat(compuestoGetNextSNMPv1Temp.get(pp)+"; ");	
//		          	  	}
//		          	  	jtf_snmpv1_getNextObjs.setText(contenido);
//		          	  	
//		          	  }catch(NumberFormatException nfe){
//	          	  	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//	          	  	  }
//          	  	}
//          	  }
//        	}
//        }); 
//	    
//	    //Para el boton deshacer
//	    jb_snmpv1_GetNextUndo.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//        	  
//        	  int ultimo = compuestoGetNextSNMPv1Temp.size();
//        	  ultimo=ultimo-1;
//        	  if (ultimo>=0){
//        	    compuestoGetNextSNMPv1Temp.removeElementAt(ultimo);
//        	  }else{
//        	  	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//        	  }
//    	  	
//		      String contenido = "";
//		      for (int pp=0;pp<compuestoGetNextSNMPv1Temp.size();pp++){
//		        contenido=contenido.concat(compuestoGetNextSNMPv1Temp.get(pp)+"; ");	
//		      }
//		      jtf_snmpv1_getNextObjs.setText(contenido);
//		      
//        	}
//        }); 
//	    
//    	jb_snmpv1_GetNextGetNext.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//          	  //aqui lo que hace
//          	  if ((jtf_snmpv1_GetNextGetNext.getText()).equals("")){
//          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//          	  }else{
//          	  	//Tratamiento
//          	  	OID = jtf_snmpv1_GetNextGetNext.getText();
//          	  	
//          	  	if (esVacio(IP)){
//          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
//          	  	}else{        	  	
//          	  	          	  	
//					//Para transformar el OID de string al arreglo para pasarselo al GetNext
//	          	  	//System.out.println("Tratando el oid");
//	          	  	try{
//	          	  		//Se usa solo para comprobar que relamente se esta pasando un oid
//		          	  	int digitos=0;
//		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
//						while(Token.hasMoreTokens()){
//						  Token.nextToken();
//						  digitos++;	
//						}
//		          	  	//System.out.println("el oid tiene "+digitos+"digitos");
//		          	  	int tamao=digitos;
//		          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
//		          	  	int requerimiento[] = new int[tamao];
//		          	  	int i=0;
//		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
//						while(Token1.hasMoreTokens()){
//						  requerimiento[i]=Integer.parseInt(Token1.nextToken());
//						  i++;
//						  //System.out.println("valor de i: "+i);	
//						}
//		          	  	//requerimiento[i]=0;
//		          	  	/*
//		          	  	System.out.println("cambiado a arreglo");
//		          	  	i++;
//		          	  	System.out.println("tamao del arreglo: "+i);
//		          	  	for (int j=0;j<i;j++){
//		          	  	  System.out.println("d"+j+" valor: "+requerimiento[j]);	
//		          	  	}
//		          	  	*/
//		          	  	//System.out.println("parseado: "+new OID(requerimiento));
//		          	  	//System.out.println("directo: "+OID);
//		          	  	boolean esCompuesto=false;
//		          	  	compuestoGetNextSNMPv1 = new Vector();
//		          	  	if((compuestoGetNextSNMPv1Temp.size())>=1){
//		          	  	   compuestoGetNextSNMPv1=compuestoGetNextSNMPv1Temp;
//		          	  	   esCompuesto=true;	
//		          	  	}else{
//		          	  	   compuestoGetNextSNMPv1.add(new OID(requerimiento));
//		          	  	   esCompuesto=false;
//		          	  	}
//		          	  	
//		          	  	
//		          	  	SNMPv1 manager = new SNMPv1();
//		          	  	String respuesta = manager.getNextv1(IP, String.valueOf(pto), comLec, inten,timeOut,compuestoGetNextSNMPv1,erroresGenerales04);
//		          	  	compuestoGetNextSNMPv1.removeAllElements();
//		          	  	compuestoGetNextSNMPv1Temp.removeAllElements();
//					    jtf_snmpv1_getNextObjs.setText("");
//		          	  	
//		          	  	//System.out.println("GetNextv1: "+respuesta);
//		          	  	if (respuesta.equals(erroresGenerales04)){
//		          	  	  respuesta=("GetNext SNMPv1: ").concat(respuesta);
//		          	  	}else{
//		          	  	  //Para el GetNext acumulativo
//		          	  	  //jtf_snmpv1_GetNextGetNext.setText("."+respuesta.substring(0,respuesta.indexOf(" ")));	          	  	  
//		          	  	  //System.out.println("--"+respuesta.substring(0,respuesta.indexOf(" "))+"--");
//			    		  if (!esCompuesto){
//			    		  	int ini = respuesta.indexOf(" ");
//			    		  	ini=ini+1;
//			    		  	int fin = respuesta.indexOf(" ",ini);
//		          	  	  	//System.out.println("actual --"+respuesta.substring(ini,fin)+"--");
//		          	  	  	jtf_snmpv1_GetNextGetNext.setText("."+respuesta.substring(ini,fin));
//			    		  }
//		          	  	  respuesta=("GetNext SNMPv1: ").concat(respuesta);
//		          	  	}
//		          	  	respuesta=respuesta.concat("\n");
//		          	  	jta_snmpv1_GetNextResp.setText((jta_snmpv1_GetNextResp.getText()).concat(respuesta));
//		          	  }catch(NumberFormatException nfe){
//	          	  	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//	          	  	  }
//          	  	}
//          	  }
//         	}
//       	});
//       	
//		
//		//----------------------------------Fin de Pantalla de GetNext-----------------------------
//
//		//****************************************************************************************************************************
//		//GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE--------
//
//		jp_snmpv1_getTable = new JPanel();
//	    //jp_snmpv1_getTable.setBackground(Color.blue);
//		//jp_snmpv1_getTable.setBorder(BorderFactory.createTitledBorder("Comando GetNext de SNMPv1"));
//		jp_snmpv1_getTable.setBounds(new Rectangle(0,30,483,483));  
//		jp_snmpv1_getTable.setBorder(BorderFactory.createTitledBorder("Comando GetTable"));
//		jp_snmpv1_getTable.setLayout(null);
//		jp_snmpv1_getTable.setVisible(false);
//		snmpv1.add(jp_snmpv1_getTable,null);
//		/*
//		JLabel jl_snmpv1_getTableEtigetTable;
//		JTextField jtf_snmpv1_getTablegetTable;
//		JButton jb_snmpv1_getTablegetTable;
//		JScrollPane jsp_snmpv1_getTablegetTable;
//		*/
//
//		jl_snmpv1_getTableEtigetTable = new JLabel("OID");
//	    jl_snmpv1_getTableEtigetTable.setBounds(new Rectangle(10,20,20,20));    
//	    jp_snmpv1_getTable.add(jl_snmpv1_getTableEtigetTable,null);
//	    
//	    jtf_snmpv1_getTablegetTable = new JTextField();
//	    jtf_snmpv1_getTablegetTable.setBounds(new Rectangle(40,20,334,20));  
//	    jtf_snmpv1_getTablegetTable.setEditable(true);
//	    jp_snmpv1_getTable.add(jtf_snmpv1_getTablegetTable,null);
//	    
//	    jb_snmpv1_getTablegetTable = new JButton("GetTable");
//	    jb_snmpv1_getTablegetTable.setBounds(new Rectangle(384,20,90,20));  //9999
//	    jb_snmpv1_getTablegetTable.setToolTipText("Presione para consultar la tabla.");
//	    jp_snmpv1_getTable.add(jb_snmpv1_getTablegetTable,null);
//	    
//	    jsp_snmpv1_getTablegetTable = new JScrollPane();
//    	jsp_snmpv1_getTablegetTable.setBounds(new Rectangle(10,50,465,420));  //320,10,435,300
//    	jsp_snmpv1_getTablegetTable.setWheelScrollingEnabled(true);
//    	jp_snmpv1_getTable.add(jsp_snmpv1_getTablegetTable,null);
//    	
//    	/*
//    	JTextArea jta_snmpv1_getTablegetTable;
//    	jta_snmpv1_getTablegetTable = new JTextArea();           
//    	jta_snmpv1_getTablegetTable.setText("");
//    	jta_snmpv1_getTablegetTable.setEditable(false);
//    	jsp_snmpv1_getTablegetTable.getViewport().add(jta_snmpv1_getTablegetTable,null);
//		*/
//
//		jb_snmpv1_getTablegetTable.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//          	  //aqui lo que hace
//          	  if ((jtf_snmpv1_getTablegetTable.getText()).equals("")){
//          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//          	  }else{
//          	  	//Tratamiento
//          	  	OID = jtf_snmpv1_getTablegetTable.getText();
//          	  	
//          	  	if (esVacio(IP)){
//          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
//          	  	}else{        	  	
//          	  	        	  	
//					//Para transformar el OID de string al arreglo para pasarselo al GetNext
//	          	  	//System.out.println("Tratando el oid");
//	          	  	try{
//		          	  	int digitos=0;
//		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
//						while(Token.hasMoreTokens()){
//						  Token.nextToken();
//						  digitos++;	
//						}
//		          	  	//System.out.println("el oid tiene "+digitos+"digitos");
//		          	  	int tamao=digitos;
//		          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
//		          	  	int requerimiento[] = new int[tamao];
//		          	  	int i=0;
//		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
//						while(Token1.hasMoreTokens()){
//						  requerimiento[i]=Integer.parseInt(Token1.nextToken());
//						  i++;
//						  //System.out.println("valor de i: "+i);	
//						}
//		          	  	//requerimiento[i]=0;
//		          	  	//Para la parte de la tabla
//		          	  	getTable traerTabla = new getTable();
//		          	  	traerTabla.cambiarIdiomaAMensajes(erroresGenerales07,erroresGenerales08,erroresGenerales09,erroresGenerales10,erroresGenerales11,erroresGenerales12,erroresGenerales13);
//		          	  	Vector columnas = traerTabla.getTablev1(IP,String.valueOf(pto),comLec,inten,timeOut,requerimiento,0);
//		          	  	Vector names = traerTabla.getNombreColumnas();
//		          	  	traerTabla.limpiarNombreColumnas();
//		          	  	//System.out.println("Resultado del walk: "+traerTabla.getWalkRealizado());
//		          	  	//traerTabla.limpiarWalkRealizado();
//		          	  	//Para la cantidad de columnas
//		          	  	Object[] columnNames = new Object[names.size()];
//		          	  	for (int k=0;k<(names.size());k++){
//      						//System.out.println("Nombre "+(k+1)+": "+String.valueOf(names.get(k)));	
//      						columnNames[k]=String.valueOf(names.get(k));
//     					}
//     					//Para la cantidad de filas
//		          	  	int cantidadFilas = 0;
//		          	  	for (int k1=0;k1<(columnas.size());k1++){
//      						Vector temporalisimo = (Vector)columnas.get(k1);
//      						//System.out.println("Cantidad de filas: "+temporalisimo.size());
//      						cantidadFilas=temporalisimo.size();
//		          	  	}		          	  	
//		          	  	Object[][] rowData = new Object[cantidadFilas][names.size()];
//		          	  	for (int k1=0;k1<(columnas.size());k1++){
//      						Vector temporalisimo = (Vector)columnas.get(k1);
//      						//System.out.println("Cantidad de filas: "+temporalisimo.size());
//     						for (int k3=0;k3<temporalisimo.size();k3++){
//      						  	rowData[k3][k1]=temporalisimo.get(k3);
//      						}
//      						//System.out.println("columnas "+(k1+1)+": "+String.valueOf(columnas.get(k1)));	
//     					}
//     					//rowData[2][1]="aqui es 2,1";
//		          	  	//System.out.println("Cantidad de columnas: "+names.size());
//		          	  	//System.out.println("Cantidad de filas: "+cantidadFilas);
//		          	  	//Para cambiar los names de las columnas por el name del nodo
//		          	  	
//		          	  	for (int k4=0;k4<names.size();k4++){
//		          	  	  OID oidTemporalisimo = new OID(String.valueOf(columnNames[k4]));
//		          	  	  
//		          	  	  try{
//		          	  	    Collection listaMibs = (Collection)loadedMibsParaBuscarNombres;	
//		          	  	  	Iterator iteradorListaMibs = listaMibs.iterator();
//              				while (iteradorListaMibs.hasNext()){
//                			  Mib cargada = (Mib)iteradorListaMibs.next();
//                			  //System.out.println("OID es: "+oidTemporalisimo);	
//				        	  //System.out.println("symbol: "+cargada.getSymbolByOid(String.valueOf(oidTemporalisimo)));
//				        	  String symbolEncontrado = String.valueOf(cargada.getSymbolByOid(String.valueOf(oidTemporalisimo)));
//			          	  	  //System.out.println("SYMBOL: ---"+cargada.getSymbolByOid(String.valueOf(oidTemporalisimo))+"---");
//			          	  	  int ini = symbolEncontrado.indexOf(" ");
//				    		  int fin = symbolEncontrado.indexOf(" ",(ini+1));
//				    	      symbolEncontrado=(symbolEncontrado.substring(ini,fin)).trim();
//				              //System.out.println("encontrado: -"+symbolEncontrado+"-");
//				              columnNames[k4]=symbolEncontrado;
//              				}
//		          	  	  }catch(Exception h){
//		          	  	  	//System.out.println("es nulo----------------------------------------------------------------------");
//		          	  	  }
//		          	  	  
//		          	  	}
//		          	  
//		          	  	//Para pintar la tabla
//		          	  	if((cantidadFilas==0)&&((names.size())==0)){
//		          	  	  	//JOptionPane.showMessageDialog(managerSNMP.this,"No se encontraron datos, las causas pueden ser:\n- El OID seleccionado no es una tabla.\n- El agente no tiene datos para la tabla seleccionada.\n- Seleccion el Entry en vez de la tabla como tal.",nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));	
//		          	  	  	JTextArea jta_snmpv1_getTablegetTable;
//					    	jta_snmpv1_getTablegetTable = new JTextArea(); 
//					    	String mensajeError="\n".concat(traerTabla.getErrores());          
//					    	traerTabla.limpiarErrores();
//					    	if(!(mensajeError.equals(erroresGenerales05))){
//							  mensajeError=mensajeError.concat("\n");
//					    	  mensajeError=mensajeError.concat(erroresGenerales06);					    		
//					    	}
//					    	jta_snmpv1_getTablegetTable.setText(mensajeError);
//					    	jta_snmpv1_getTablegetTable.setEditable(false);
//					    	jsp_snmpv1_getTablegetTable.getViewport().add(jta_snmpv1_getTablegetTable,null);
//		          	  	}else{
//			          	  	JTable tabla = new JTable(rowData,columnNames);
//			          	  	tabla.getTableHeader().setReorderingAllowed(false);
//			          	  	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//			          	  	for (int k5=0;k5<names.size();k5++){
//			          	  	  tabla.getColumn(String.valueOf(columnNames[k5])).setPreferredWidth(130);
//			          	  	}
//			          	  	tabla.setEnabled(false);
//	    					jsp_snmpv1_getTablegetTable.getViewport().add(tabla,null);
//		          	  	}
//		          	  }catch(NumberFormatException nfe){
//	          	  	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//	          	  	  }
//          	  	}
//          	  }
//         	}
//       	});
//		//GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE--------
//		//****************************************************************************************************************************		
//
//		//----------------------------------Pantalla del Walk--------------------------------------
//		jp_snmpv1_walk= new JPanel();
//	    //jp_snmpv1_walk.setBackground(Color.white);
//	    jp_snmpv1_walk.setBounds(new Rectangle(0,30,483,483));
//		jp_snmpv1_walk.setBorder(BorderFactory.createTitledBorder("Comando Walk"));
//		jp_snmpv1_walk.setLayout(null);
//		jp_snmpv1_walk.setVisible(false);
//		snmpv1.add(jp_snmpv1_walk,null);
//		
//		jl_snmpv1_WalkEtiLimitePregunta = new JLabel("Limitar Cantidad de Variables a Consultar");
//	    jl_snmpv1_WalkEtiLimitePregunta.setBounds(new Rectangle(10,20,236,20));   
//	    jp_snmpv1_walk.add(jl_snmpv1_WalkEtiLimitePregunta,null);
//		
//	    jcb_snmpv1_WalkEtiLimitePregunta = new JComboBox();//USO DEL JCOMBOBOX
//	  	jcb_snmpv1_WalkEtiLimitePregunta.setBounds(new Rectangle(256,20,50,20));  
//		jcb_snmpv1_WalkEtiLimitePregunta.addItem("Si");
//	  	jcb_snmpv1_WalkEtiLimitePregunta.addItem("No");
//	  	jcb_snmpv1_WalkEtiLimitePregunta.setSelectedIndex(1);	  	 
//	  	jp_snmpv1_walk.add(jcb_snmpv1_WalkEtiLimitePregunta,null);
//
//		jl_snmpv1_WalkEtiLimite = new JLabel("Cantidad");
//	    jl_snmpv1_WalkEtiLimite.setBounds(new Rectangle(326,20,50,20));   
//	    jl_snmpv1_WalkEtiLimite.setEnabled(false);
//	    jp_snmpv1_walk.add(jl_snmpv1_WalkEtiLimite,null);
//
//		jtf_snmpv1_WalkEtiLimite = new JTextField();
//	    jtf_snmpv1_WalkEtiLimite.setBounds(new Rectangle(386,20,89,20));  
//	    jtf_snmpv1_WalkEtiLimite.setEditable(false);
//	    jtf_snmpv1_WalkEtiLimite.setText(erroresGenerales21);
//	    jp_snmpv1_walk.add(jtf_snmpv1_WalkEtiLimite,null);
//	
//	  	jcb_snmpv1_WalkEtiLimitePregunta.addActionListener(new ActionListener(){
//	      public void actionPerformed(ActionEvent e) {	          
//	        if ((jcb_snmpv1_WalkEtiLimitePregunta.getSelectedItem())==opcionSi){jtf_snmpv1_WalkEtiLimite.setEditable(true);jtf_snmpv1_WalkEtiLimite.setText("");jl_snmpv1_WalkEtiLimite.setEnabled(true);}
//	        if ((jcb_snmpv1_WalkEtiLimitePregunta.getSelectedItem())==opcionNo){jtf_snmpv1_WalkEtiLimite.setEditable(false);jtf_snmpv1_WalkEtiLimite.setText(erroresGenerales21);jl_snmpv1_WalkEtiLimite.setEnabled(false);}
//	      }
//	    });
//				
//		jl_snmpv1_WalkEti = new JLabel("OID");
//	    jl_snmpv1_WalkEti.setBounds(new Rectangle(10,50,20,20));   
//	    jp_snmpv1_walk.add(jl_snmpv1_WalkEti,null);
//	    
//	    jtf_snmpv1_WalkOID = new JTextField();
//	    jtf_snmpv1_WalkOID.setBounds(new Rectangle(40,50,345,20));  
//	    jtf_snmpv1_WalkOID.setEditable(true);
//	    jp_snmpv1_walk.add(jtf_snmpv1_WalkOID,null);
//	    
//	    jb_snmpv1_Walk = new JButton("Walk");
//	    jb_snmpv1_Walk.setBounds(new Rectangle(395,50,80,20));   
//	    jb_snmpv1_Walk.setToolTipText("Presione para iniciar el Walk.");
//	    jp_snmpv1_walk.add(jb_snmpv1_Walk,null);
//	    
//	    jsp_snmpv1_WalkResp = new JScrollPane();
//    	jsp_snmpv1_WalkResp.setBounds(new Rectangle(10,80,466,391));   
//    	jsp_snmpv1_WalkResp.setWheelScrollingEnabled(true);
//    	jp_snmpv1_walk.add(jsp_snmpv1_WalkResp,null);
//
//    	jta_snmpv1_WalkResp = new JTextArea();           
//    	jta_snmpv1_WalkResp.setText("");
//    	jta_snmpv1_WalkResp.setEditable(false);
//    	jsp_snmpv1_WalkResp.getViewport().add(jta_snmpv1_WalkResp,null);
//	    
//	    jb_snmpv1_Walk.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//          	  //aqui lo que hace
//          	  jta_snmpv1_WalkResp.setText(erroresGenerales16);
//          	  if ((jtf_snmpv1_WalkOID.getText()).equals("")){
//          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//          	    jta_snmpv1_WalkResp.setText("");
//          	  }else{
//          	  	//Tratamiento
//          	  	OID = jtf_snmpv1_WalkOID.getText();
//          	  	
//          	  	if (esVacio(IP)){
//          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
//          	  	  jta_snmpv1_WalkResp.setText("");
//          	  	}else{ 
//          	  		if (((jcb_snmpv1_WalkEtiLimitePregunta.getSelectedItem())==opcionSi)&&(jtf_snmpv1_WalkEtiLimite.getText().equals(""))){
//          	  		  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales14,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
//          	  		  jta_snmpv1_WalkResp.setText("");
//          	  		}else{
//          	  			if (((jcb_snmpv1_WalkEtiLimitePregunta.getSelectedItem())==opcionSi)&&(!(esNumero(jtf_snmpv1_WalkEtiLimite.getText())))){
//          	  			  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales15,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
//          	  			  jta_snmpv1_WalkResp.setText("");
//          	  			}else{
//							//Para transformar el OID de string al arreglo para pasarselo al GetNext
//			          	  	//System.out.println("Tratando el oid");
//			          	  	try{
//				          	  	int digitos=0;
//				          	  	StringTokenizer Token = new StringTokenizer (OID,".");
//								while(Token.hasMoreTokens()){
//								  Token.nextToken();
//								  digitos++;	
//								}
//				          	  	//System.out.println("el oid tiene "+digitos+"digitos");
//				          	  	int tamao=digitos;
//				          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
//				          	  	int requerimiento[] = new int[tamao];
//				          	  	int i=0;
//				          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
//								while(Token1.hasMoreTokens()){
//								  requerimiento[i]=Integer.parseInt(Token1.nextToken());
//								  i++;
//								  //System.out.println("valor de i: "+i);	
//								}
//				          	  	//requerimiento[i]=0;
//				          	  	jta_snmpv1_WalkResp.setText(erroresGenerales16);
//				          	  	walk recorrer = new walk();
//				          	  	int limite = 0;
//				          	  	if((jcb_snmpv1_WalkEtiLimitePregunta.getSelectedItem())==opcionSi){
//				          	  	  limite = Integer.parseInt(jtf_snmpv1_WalkEtiLimite.getText());	
//				          	  	}else{
// 				          	  	  limite = 0;	
//				          	  	}
//				          	  	recorrer.cambiarIdiomaAMensajes(erroresGenerales07,erroresGenerales08,erroresGenerales09,erroresGenerales10,erroresGenerales11,erroresGenerales12,erroresGenerales13,erroresGenerales17,erroresGenerales18,erroresGenerales19,erroresGenerales20);
//				          	  	recorrer.walkSNMPv1(IP,String.valueOf(pto),comLec,inten,timeOut,requerimiento,limite);
//								jta_snmpv1_WalkResp.setText(jta_snmpv1_WalkResp.getText().concat(recorrer.getWalkRealizado()));
//								recorrer.limpiarWalkRealizado();
//				          	  	
//			          	  	  }catch(NumberFormatException nfe){
//		          	  	    	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//		          	  	    	jta_snmpv1_WalkResp.setText("");
//		          	  	  	  }
//          	  		  }
//          	  		}
//          	  	}
//          	  }
//         	}
//       	});
//		
//		//----------------------------------Fin de Pantalla del Walk-------------------------------
//
//		//----------------------------------Pantalla de Set----------------------------------------	      	
//	    jp_snmpv1_Set = new JPanel();
//	    //jp_snmpv1_Set.setBackground(Color.white);
//		//jp_snmpv1_Set.setBorder(BorderFactory.createTitledBorder("Set"));
//		jp_snmpv1_Set.setBounds(new Rectangle(0,30,483,483));
//		jp_snmpv1_Set.setBorder(BorderFactory.createTitledBorder("Comando Set"));
//		jp_snmpv1_Set.setLayout(null);
//		jp_snmpv1_Set.setVisible(false);
//		snmpv1.add(jp_snmpv1_Set,null);
//
//    	jsp_snmpv1_SetDescrip = new JScrollPane();
//    	jsp_snmpv1_SetDescrip.setBounds(new Rectangle(10,20,465,240));   
//    	jsp_snmpv1_SetDescrip.setWheelScrollingEnabled(true);
//    	jp_snmpv1_Set.add(jsp_snmpv1_SetDescrip,null);
//
//    	jta_snmpv1_SetDescrip = new JTextArea();           
//    	jta_snmpv1_SetDescrip.setText("");
//    	jta_snmpv1_SetDescrip.setEditable(false);
//    	jsp_snmpv1_SetDescrip.getViewport().add(jta_snmpv1_SetDescrip,null);
//
//	    jsp_snmpv1_SetResp = new JScrollPane();
//    	jsp_snmpv1_SetResp.setBounds(new Rectangle(10,360,465,111));   
//    	jsp_snmpv1_SetResp.setWheelScrollingEnabled(true);
//    	jp_snmpv1_Set.add(jsp_snmpv1_SetResp,null);
//    	
//    	jta_snmpv1_SetResp = new JTextArea();
//    	jta_snmpv1_SetResp.setText("");
//    	jta_snmpv1_SetResp.setEditable(false);
//    	jsp_snmpv1_SetResp.getViewport().add(jta_snmpv1_SetResp,null);
//
//    	jl_snmpv1_SetEtiSet = new JLabel("OID");
//	    jl_snmpv1_SetEtiSet.setBounds(new Rectangle(10,270,20,20));   
//	    jp_snmpv1_Set.add(jl_snmpv1_SetEtiSet,null);
//
//	    jtf_snmpv1_SetSet = new JTextField();
//	    jtf_snmpv1_SetSet.setBounds(new Rectangle(50,270,200,20));    
//	    jtf_snmpv1_SetSet.setEditable(true);
//	    jp_snmpv1_Set.add(jtf_snmpv1_SetSet,null);
// 
//    	jl_snmpv1_SetEtiTipo = new JLabel("Tipo de Dato");
//	    jl_snmpv1_SetEtiTipo.setBounds(new Rectangle(260,270,80,20));
//	    jl_snmpv1_SetEtiTipo.setEnabled(false);   
//	    jp_snmpv1_Set.add(jl_snmpv1_SetEtiTipo,null);
// 
//	    jcb_snmpv1_SetTipo = new JComboBox();//USO DEL JCOMBOBOX
//	  	jcb_snmpv1_SetTipo.setBounds(new Rectangle(339,270,135,20));  // 350,226,70,20
//		jcb_snmpv1_SetTipo.addItem("---------------------------");
//	  	jcb_snmpv1_SetTipo.addItem("INTEGER");
//	  	jcb_snmpv1_SetTipo.addItem("OCTET STRING");
//	  	jcb_snmpv1_SetTipo.addItem("OBJECT IDENTIFIER");	  	
//	  	jcb_snmpv1_SetTipo.addItem("IpAddress");
//	  	jcb_snmpv1_SetTipo.addItem("Counter");
//	  	jcb_snmpv1_SetTipo.addItem("Gauge");
//	  	jcb_snmpv1_SetTipo.addItem("TimeTicks");
//	  	jcb_snmpv1_SetTipo.addItem("Opaque");
//	  	//jcb_snmpv1_SetTipo.addItem("Counter64");
//	  	jcb_snmpv1_SetTipo.setSelectedIndex(0);	  	 
//	  	jcb_snmpv1_SetTipo.setEnabled(false);
//	  	jp_snmpv1_Set.add(jcb_snmpv1_SetTipo,null);
//
//	  	jcb_snmpv1_SetTipo.addActionListener(new ActionListener(){
//	      public void actionPerformed(ActionEvent e) {
//	      }
//	    });
//	  		  	
//	    jl_snmpv1_SetEtiSetValor = new JLabel("Valor");
//	    jl_snmpv1_SetEtiSetValor.setBounds(new Rectangle(10,300,35,20));   
//	    jl_snmpv1_SetEtiSetValor.setEnabled(false);	
//	    jp_snmpv1_Set.add(jl_snmpv1_SetEtiSetValor,null);
//		
//	    jtf_snmpv1_SetSetValor = new JTextField();
//	    jtf_snmpv1_SetSetValor.setBounds(new Rectangle(50,300,235,20));    
//	    jtf_snmpv1_SetSetValor.setEditable(true);
//	    jp_snmpv1_Set.add(jtf_snmpv1_SetSetValor,null);	 
//
//    	jb_snmpv1_setAdd = new JButton("Aadir");
//	    jb_snmpv1_setAdd.setBounds(new Rectangle(295,300,79,20));  //9999
//	    jb_snmpv1_setAdd.setToolTipText("Presione para agregar el OID.");
//	    jb_snmpv1_setAdd.setEnabled(false);
//	    jp_snmpv1_Set.add(jb_snmpv1_setAdd,null);
//	    
//    	jl_snmpv1_setObjs = new JLabel("Objetos");
//	    jl_snmpv1_setObjs.setBounds(new Rectangle(10,330,60,20));
//	    jp_snmpv1_Set.add(jl_snmpv1_setObjs,null);
//	    
//	    jtf_snmpv1_setObjs = new JTextField();
//	    jtf_snmpv1_setObjs.setBounds(new Rectangle(60,330,344,20));   
//	    jtf_snmpv1_setObjs.setEditable(false);	    	    
//	    jp_snmpv1_Set.add(jtf_snmpv1_setObjs,null);
//
//    	jb_snmpv1_setUndo = new JButton("Deshacer");
//	    jb_snmpv1_setUndo.setBounds(new Rectangle(384,300,90,20));  //9999
//	    jb_snmpv1_setUndo.setToolTipText("Presione para eliminar el ltimo OID de los objetos.");
//	    jb_snmpv1_setUndo.setEnabled(false);
//	    jp_snmpv1_Set.add(jb_snmpv1_setUndo,null);
//	    
//    	jb_snmpv1_SetSet = new JButton("Set");
//	    jb_snmpv1_SetSet.setBounds(new Rectangle(415,330,59,20));   
//        jb_snmpv1_SetSet.setEnabled(false);
//        jtf_snmpv1_SetSetValor.setEnabled(false); 	 
//        jtf_snmpv1_SetSetValor.setText(""); 		 
//	    jb_snmpv1_SetSet.setToolTipText("Presione para especificar el valor.");
//	    jp_snmpv1_Set.add(jb_snmpv1_SetSet,null);
//
//		//AQUI DEBE DE IR EL EVENTO DE SETEAR UNA VARIABLE VIA SNMP
//		
//		//TIPOS DE DATOS DEL SET
//       	//tipoDatoReconocido = "INTEGER";
//        //tipoDatoReconocido = "OCTET STRING";	
//        //tipoDatoReconocido = "OBJECT IDENTIFIER";
//        //tipoDatoReconocido = "IPADDRESS";
//        //tipoDatoReconocido = "COUNTER";
//        //tipoDatoReconocido = "GAUGE";
//        //tipoDatoReconocido = "TIMETICKS";
//        //tipoDatoReconocido = "OPAQUE";
//        //tipoDatoReconocido = "COUNTER64";
//        //tipoDatoReconocido="NO RECONOCIDO";
//        //reconocido
//		
//		compuestoSetSNMPv1TempOID = new Vector();
//		compuestoSetSNMPv1TempTipoDatos = new Vector();
//		compuestoSetSNMPv1TempDatos = new Vector();
//		
//		
//		//Para el boton aadir
//		jb_snmpv1_setAdd.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//        	  
//        	  //aqui lo que hace
//          	  if ((jtf_snmpv1_SetSet.getText()).equals("")){
//          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//          	  }else{
//          	  	//Tratamiento
//          	  	OID = jtf_snmpv1_SetSet.getText();
//          	  	
//          	  	if (esVacio(IP)){
//          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
//          	  	}else{ 
//          	  		
//          	  		if ((jtf_snmpv1_SetSetValor.getText()).equals("")){
//          	  		  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales22,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
//          	  		}else{
//          	  			if ((jcb_snmpv1_SetTipo.getSelectedItem()).equals("---------------------------")){
//          	  			  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales23,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
//          	  			}else{
//							//Para transformar el OID de string al arreglo para pasarselo al GetNext
//			          	  	//System.out.println("Tratando el oid");
//			          	  	try{
//			          	  		//Se usa solo para comprobar que relamente se esta pasando un oid
//				          	  	int digitos=0;
//				          	  	StringTokenizer Token = new StringTokenizer (OID,".");
//								while(Token.hasMoreTokens()){
//								  Token.nextToken();
//								  digitos++;	
//								}
//				          	  	//System.out.println("el oid tiene "+digitos+"digitos");
//				          	  	int tamao=digitos;
//				          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
//				          	  	int requerimiento[] = new int[tamao];
//				          	  	int i=0;
//				          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
//								while(Token1.hasMoreTokens()){
//								  requerimiento[i]=Integer.parseInt(Token1.nextToken());
//								  i++;
//								  //System.out.println("valor de i: "+i);	
//								}
//				          	  	//requerimiento[i]=0;
//				          	  	
//				          	  	//private Vector compuestoSetSNMPv1; //Para pasar los multiples parametros
//								//private Vector compuestoSetSNMPv1TempOID;
//    							//private Vector compuestoSetSNMPv1TempDatos;
//    							//private Vector compuestoSetSNMPv1TempTipoDatos;
//    							//private Variable[] compuestoSetSNMPv1Valores; //Para pasar los multiples parametros
//    							
//				          	  	Variable valor = null;
//				          	  	
//				          	  	if(reconocido){
//				          	  		boolean datoInvalido=false;
//				          	  		tipoDatoReconocido=jcb_snmpv1_SetTipo.getSelectedItem().toString();
//				          	  		try{
//				          	  			if(tipoDatoReconocido.equals("INTEGER")){valor = new Integer32(Integer.parseInt(String.valueOf(jtf_snmpv1_SetSetValor.getText())));}
//				    					if(tipoDatoReconocido.equals("Gauge")){valor = new Gauge32(Long.parseLong(String.valueOf(jtf_snmpv1_SetSetValor.getText())));}
//									    if(tipoDatoReconocido.equals("IpAddress")){valor = new IpAddress(String.valueOf(jtf_snmpv1_SetSetValor.getText()));}
//									    if(tipoDatoReconocido.equals("OBJECT IDENTIFIER")){valor = new OID(String.valueOf(jtf_snmpv1_SetSetValor.getText()));}
//									    if(tipoDatoReconocido.equals("TimeTicks")){valor = new TimeTicks(Long.parseLong(String.valueOf(jtf_snmpv1_SetSetValor.getText())));}
//									    if(tipoDatoReconocido.equals("Counter")){valor = new Counter32(Long.parseLong(String.valueOf(jtf_snmpv1_SetSetValor.getText())));}
//									    //if(tipoDatoReconocido.equals("Counter64")){valor = new Counter64(Long.parseLong(String.valueOf(jtf_snmpv1_SetSetValor.getText())));}
//									    if(tipoDatoReconocido.equals("Opaque")){valor = new Opaque(String.valueOf(jtf_snmpv1_SetSetValor.getText()).getBytes());}
//									    if(tipoDatoReconocido.equals("OCTET STRING")){valor = new OctetString(String.valueOf(jtf_snmpv1_SetSetValor.getText()));}
//				          	  		}catch(Exception e4){datoInvalido=true;}
//				          	  		if(!(datoInvalido)){
//				          	  			
//				          	  			compuestoSetSNMPv1TempOID.add(new OID(requerimiento));
//				          	  			compuestoSetSNMPv1TempTipoDatos.add(tipoDatoReconocido);
//				          	  			compuestoSetSNMPv1TempDatos.add(String.valueOf(valor));
//				          	  			
//				          	  			String contenido = "";
//								        for (int pp=0;pp<compuestoSetSNMPv1TempOID.size();pp++){
//								          contenido=contenido.concat(compuestoSetSNMPv1TempOID.get(pp)+"; ");	
//								        }
//								        jtf_snmpv1_setObjs.setText(contenido);
//				          	  			
//				          	  			
//				          	  		}else{
//				          	  		  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales24,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));				
//				          	  		}
//		          	  			}else{
//		          	  				JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales25,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));			
//		          	  			}
//			          	  	  }catch(NumberFormatException nfe){
//		          	  	    	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//		          	  	  	  }
//          	  		  }
//          	  		}
//          	  	}
//          	  }
//         	
//        	  
//        	}
//        });  
//		
//		//Para el boton deshacer
//		jb_snmpv1_setUndo.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//        	  
//        	  int ultimo = compuestoSetSNMPv1TempOID.size();
//        	  ultimo=ultimo-1;
//        	  if (ultimo>=0){
//        	    compuestoSetSNMPv1TempOID.removeElementAt(ultimo);
//        	    compuestoSetSNMPv1TempTipoDatos.removeElementAt(ultimo);
//        	    compuestoSetSNMPv1TempDatos.removeElementAt(ultimo);
//        	  }else{
//        	  	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//        	  }
//    	  	
//		      String contenido = "";
//		      for (int pp=0;pp<compuestoSetSNMPv1TempOID.size();pp++){
//		        contenido=contenido.concat(compuestoSetSNMPv1TempOID.get(pp)+"; ");	
//		      }
//		      jtf_snmpv1_setObjs.setText(contenido);
//        	  
//        	}
//        });    
//				
//		//EVENTO PARA HACER QUE AL INTRODUCIR EL OID DEL SET SE ACTIVE EL TIPO DE DATOS, VALOR Y BOTON SET
//		jtf_snmpv1_SetSetDigitos = 0;
//		jtf_snmpv1_SetSet.getDocument().addDocumentListener(new DocumentListener(){
//		  public void insertUpdate(DocumentEvent e) {
//            //System.out.println("escribi, valor: "+jtf_snmpv1_SetSetDigitos);
//            jtf_snmpv1_SetSetDigitos++;
//            if (jtf_snmpv1_SetSetDigitos==2){
//              //System.out.println("active"); 
//              reconocido=true;	
//              jcb_snmpv1_SetTipo.setEnabled(true);//combobox
//              jb_snmpv1_SetSet.setEnabled(true); //boton
//              jtf_snmpv1_SetSetValor.setEnabled(true); //valor	
//              jl_snmpv1_SetEtiTipo.setEnabled(true); 
//              jl_snmpv1_SetEtiSetValor.setEnabled(true);
//              jb_snmpv1_setAdd.setEnabled(true);
//              jb_snmpv1_setUndo.setEnabled(true);
//            }
//          }
//          public void removeUpdate(DocumentEvent e) {
//            //System.out.println("borre, valor: "+jtf_snmpv1_SetSetDigitos);
//            jtf_snmpv1_SetSetDigitos=0;
//          }
//          public void changedUpdate(DocumentEvent e) {
//            //Plain text components don't fire these events.
//          }
//		});
//		
//		jb_snmpv1_SetSet.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//          	  //aqui lo que hace
//          	  if ((jtf_snmpv1_SetSet.getText()).equals("")){
//          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//          	  }else{
//          	  	//Tratamiento
//          	  	OID = jtf_snmpv1_SetSet.getText();
//          	  	
//          	  	if (esVacio(IP)){
//          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
//          	  	}else{ 
//          	  		
//          	  		if ((jtf_snmpv1_SetSetValor.getText()).equals("")){
//          	  		  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales22,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
//          	  		}else{
//          	  			if ((jcb_snmpv1_SetTipo.getSelectedItem()).equals("---------------------------")){
//          	  			  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales23,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
//          	  			}else{
//							//Para transformar el OID de string al arreglo para pasarselo al GetNext
//			          	  	//System.out.println("Tratando el oid");
//			          	  	try{
//			          	  		//Se usa solo para comprobar que relamente se esta pasando un oid
//				          	  	int digitos=0;
//				          	  	StringTokenizer Token = new StringTokenizer (OID,".");
//								while(Token.hasMoreTokens()){
//								  Token.nextToken();
//								  digitos++;	
//								}
//				          	  	//System.out.println("el oid tiene "+digitos+"digitos");
//				          	  	int tamao=digitos;
//				          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
//				          	  	int requerimiento[] = new int[tamao];
//				          	  	int i=0;
//				          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
//								while(Token1.hasMoreTokens()){
//								  requerimiento[i]=Integer.parseInt(Token1.nextToken());
//								  i++;
//								  //System.out.println("valor de i: "+i);	
//								}
//				          	  	//requerimiento[i]=0;
//				          	  	
//				          	  	//private Vector compuestoSetSNMPv1; //Para pasar los multiples parametros
//								//private Vector compuestoSetSNMPv1TempOID;
//    							//private Vector compuestoSetSNMPv1TempDatos;
//    							//private Vector compuestoSetSNMPv1TempTipoDatos;
//    							//private Variable[] compuestoSetSNMPv1Valores; //Para pasar los multiples parametros
//    							
//    							boolean esCompuesto=false;
//				          	  	compuestoSetSNMPv1 = new Vector();
//				          	  	if((compuestoSetSNMPv1TempOID.size())>=1){
//				          	  	   compuestoSetSNMPv1=compuestoSetSNMPv1TempOID;
//				          	  	   esCompuesto=true;	
//				          	  	   reconocido=true;
//				          	  	}else{
//				          	  	   compuestoSetSNMPv1.add(new OID(requerimiento));
//				          	  	   esCompuesto=false;
//				          	  	}
//				          	  					          	  	
//				          	  	SNMPv1 manager = new SNMPv1();
//				          	  	
//				          	  	Variable valor = null;
//				          	  	
//				          	  	if(reconocido){
//				          	  		boolean datoInvalido=false;
//				          	  		if (!esCompuesto){
//					          	  		tipoDatoReconocido=jcb_snmpv1_SetTipo.getSelectedItem().toString();
//					          	  		try{
//					          	  			if(tipoDatoReconocido.equals("INTEGER")){valor = new Integer32(Integer.parseInt(String.valueOf(jtf_snmpv1_SetSetValor.getText())));}
//					    					if(tipoDatoReconocido.equals("Gauge")){valor = new Gauge32(Long.parseLong(String.valueOf(jtf_snmpv1_SetSetValor.getText())));}
//										    if(tipoDatoReconocido.equals("IpAddress")){valor = new IpAddress(String.valueOf(jtf_snmpv1_SetSetValor.getText()));}
//										    if(tipoDatoReconocido.equals("OBJECT IDENTIFIER")){valor = new OID(String.valueOf(jtf_snmpv1_SetSetValor.getText()));}
//										    if(tipoDatoReconocido.equals("TimeTicks")){valor = new TimeTicks(Long.parseLong(String.valueOf(jtf_snmpv1_SetSetValor.getText())));}
//										    if(tipoDatoReconocido.equals("Counter")){valor = new Counter32(Long.parseLong(String.valueOf(jtf_snmpv1_SetSetValor.getText())));}
//										    //if(tipoDatoReconocido.equals("Counter64")){valor = new Counter64(Long.parseLong(String.valueOf(jtf_snmpv1_SetSetValor.getText())));}
//										    if(tipoDatoReconocido.equals("Opaque")){valor = new Opaque(String.valueOf(jtf_snmpv1_SetSetValor.getText()).getBytes());}
//										    if(tipoDatoReconocido.equals("OCTET STRING")){valor = new OctetString(String.valueOf(jtf_snmpv1_SetSetValor.getText()));}
//					          	  		}catch(Exception e4){datoInvalido=true;}
//				          	  		}
//				          	  		if(!(datoInvalido)){
//				          	  			if (!esCompuesto){				          	  			
//				          	  			  compuestoSetSNMPv1TempTipoDatos.add(tipoDatoReconocido);
//				          	  			  compuestoSetSNMPv1TempDatos.add(String.valueOf(valor));
//				          	  			}
//				          	  			
//				          	  			compuestoSetSNMPv1Valores = new Variable[compuestoSetSNMPv1.size()];
//				          	  			
//				          	  			for (int pp=0;pp<compuestoSetSNMPv1.size();pp++){
//					          	  			if(((compuestoSetSNMPv1TempTipoDatos.get(pp)).toString()).equals("INTEGER")){compuestoSetSNMPv1Valores[pp] = new Integer32(Integer.parseInt(String.valueOf(compuestoSetSNMPv1TempDatos.get(pp))));}
//					    					if(((compuestoSetSNMPv1TempTipoDatos.get(pp)).toString()).equals("Gauge")){compuestoSetSNMPv1Valores[pp] = new Gauge32(Long.parseLong(String.valueOf(compuestoSetSNMPv1TempDatos.get(pp))));}
//										    if(((compuestoSetSNMPv1TempTipoDatos.get(pp)).toString()).equals("IpAddress")){compuestoSetSNMPv1Valores[pp] = new IpAddress(String.valueOf(compuestoSetSNMPv1TempDatos.get(pp)));}
//										    if(((compuestoSetSNMPv1TempTipoDatos.get(pp)).toString()).equals("OBJECT IDENTIFIER")){compuestoSetSNMPv1Valores[pp] = new OID(String.valueOf(compuestoSetSNMPv1TempDatos.get(pp)));}
//										    if(((compuestoSetSNMPv1TempTipoDatos.get(pp)).toString()).equals("TimeTicks")){compuestoSetSNMPv1Valores[pp] = new TimeTicks(Long.parseLong(String.valueOf(compuestoSetSNMPv1TempDatos.get(pp))));}
//										    //ojo falta el de counter64
//										    if(((compuestoSetSNMPv1TempTipoDatos.get(pp)).toString()).equals("Counter")){compuestoSetSNMPv1Valores[pp] = new Counter32(Long.parseLong(String.valueOf(compuestoSetSNMPv1TempDatos.get(pp))));}
//										    if(((compuestoSetSNMPv1TempTipoDatos.get(pp)).toString()).equals("Opaque")){compuestoSetSNMPv1Valores[pp] = new Opaque(String.valueOf(compuestoSetSNMPv1TempDatos.get(pp)).getBytes());}
//										    if(((compuestoSetSNMPv1TempTipoDatos.get(pp)).toString()).equals("OCTET STRING")){compuestoSetSNMPv1Valores[pp] = new OctetString(String.valueOf(compuestoSetSNMPv1TempDatos.get(pp)));}
//				          	  				
//				          	  			}
//				          	  												    
//									    String respuesta = manager.setv1(IP,  String.valueOf(pto), comEsc, inten, timeOut,compuestoSetSNMPv1, compuestoSetSNMPv1Valores,erroresGenerales04);
//						          	  	compuestoSetSNMPv1.removeAllElements();
//		    							compuestoSetSNMPv1TempOID.removeAllElements();
//		        	    				compuestoSetSNMPv1TempTipoDatos.removeAllElements();
//		        	    				compuestoSetSNMPv1TempDatos.removeAllElements();
//								        compuestoSetSNMPv1Valores=null;
//								        String contenido = "";
//								        for (int pp=0;pp<compuestoSetSNMPv1TempOID.size();pp++){
//								          contenido=contenido.concat(compuestoSetSNMPv1TempOID.get(pp)+"; ");	
//								        }
//								        jtf_snmpv1_setObjs.setText(contenido);
//						          	  							          	  	
//						          	  	//System.out.println("Setv1: "+respuesta);
//						          	  	if (respuesta.equals(erroresGenerales04)){
//						          	  	  respuesta=("Set SNMPv1: ").concat(respuesta);
//						          	  	}else{
//						          	  	  respuesta=("Set SNMPv1: ").concat(respuesta);	
//						          	  	}
//						          	  	respuesta=respuesta.concat("\n");
//						          	  	jta_snmpv1_SetResp.setText((jta_snmpv1_SetResp.getText()).concat(respuesta));
//						          	  	
//				          	  		}else{
//				          	  		  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales24,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));				
//				          	  		}
//		          	  			}else{
//		          	  				JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales25,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));			
//		          	  			}
//			          	  	  }catch(NumberFormatException nfe){
//		          	  	    	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//		          	  	  	  }
//          	  		  }
//          	  		}
//          	  	}
//          	  }
//         	}
//       	});
//		
//
//	    //----------------------------------Fin de Pantalla de Set---------------------------------
//
//	    
//	    //----------------------------------Pantalla de Traps--------------------------------------  	
//	    jp_snmpv1_Traps = new JPanel();
//	    //jp_snmpv1_Traps.setBackground(Color.yellow);
//		//jp_snmpv1_Traps.setBorder(BorderFactory.createTitledBorder("Traps"));
//		jp_snmpv1_Traps.setBounds(new Rectangle(0,30,483,283));     
//		jp_snmpv1_Traps.setBorder(BorderFactory.createTitledBorder("Recepcin de TRAPs SNMP v1/2c/3 e INFORMs SNMP v2c/3"));   //  "Enviar/Ver Traps"   
//		jp_snmpv1_Traps.setLayout(null);
//		jp_snmpv1_Traps.setVisible(false);
//		snmpv1.add(jp_snmpv1_Traps,null);
//		
//	    jsp_snmpv1_traps = new JScrollPane();//USO DEL JSCROLLPANE
//	    jsp_snmpv1_traps.setBounds(new Rectangle(10,20,465,250)); 
//	    jsp_snmpv1_traps.setWheelScrollingEnabled(true);
//	    jp_snmpv1_Traps.add(jsp_snmpv1_traps,null);
//	    
//	    jta_snmpv1_traps = new JTextArea();//USO DEL JTEXTAREA
//	    jta_snmpv1_traps.setEditable(false);
//	    jsp_snmpv1_traps.getViewport().add(jta_snmpv1_traps,null);
//	     
//	    jp_snmpv1_TrapsSend = new JPanel();
//	    //jp_snmpv1_Set.setBackground(Color.white);
//		//jp_snmpv1_Set.setBorder(BorderFactory.createTitledBorder("TrapsSend"));
//		jp_snmpv1_TrapsSend.setBounds(new Rectangle(0,313,483,193));
//		jp_snmpv1_TrapsSend.setBorder(BorderFactory.createTitledBorder("Envo de TRAPs SNMP v1"));
//		jp_snmpv1_TrapsSend.setLayout(null);
//		jp_snmpv1_TrapsSend.setVisible(false);
//		snmpv1.add(jp_snmpv1_TrapsSend,null);
//
//	    jl_snmpv1_TrapSndHost = new JLabel("Direccion IP del destino");
//	    jl_snmpv1_TrapSndHost.setBounds(new Rectangle(10,30,150,20));    
//	    jp_snmpv1_TrapsSend.add(jl_snmpv1_TrapSndHost,null);
//
//	    jtf_snmpv1_TrapSndHostIP = new JTextField();
//	    jtf_snmpv1_TrapSndHostIP.setBounds(new Rectangle(150,30,150,20));    
//	    //jtf_snmpv1_TrapSndHostIP.setEditable(true);
//	    jp_snmpv1_TrapsSend.add(jtf_snmpv1_TrapSndHostIP,null);	 
//
//	    jl_snmpv1_TrapSndTipo = new JLabel("Tipo de Trap");
//	    jl_snmpv1_TrapSndTipo.setBounds(new Rectangle(10,60,100,20));   //0,313,483,193
//	    jp_snmpv1_TrapsSend.add(jl_snmpv1_TrapSndTipo,null);
//		
//	    jcb_snmpv1_TrapSel = new JComboBox();//USO DEL JCOMBOBOX
//	  	jcb_snmpv1_TrapSel.setBounds(new Rectangle(150,60,150,20));  //95,70,150,20
//		jcb_snmpv1_TrapSel.addItem("coldStart");
//	  	jcb_snmpv1_TrapSel.addItem("warmStart");
//	  	jcb_snmpv1_TrapSel.addItem("linkDown");
//	  	jcb_snmpv1_TrapSel.addItem("linkUp");
//	  	jcb_snmpv1_TrapSel.addItem("authenticationFailure");	  	
//	  	jcb_snmpv1_TrapSel.addItem("enterpriseSpecific");	  	
//	  	//jcb_snmpv1_sel.setMaximumRowCount(2);
//	  	jp_snmpv1_TrapsSend.add(jcb_snmpv1_TrapSel,null);
//
//	  	jcb_snmpv1_TrapSel.addActionListener(new ActionListener(){
//	      public void actionPerformed(ActionEvent e) {
//	        jtf_snmpv1_TrapSpcTxt.setEditable(false);
//	        jl_snmpv1_TrapSpc.setEnabled(false);
//	        jtf_snmpv1_TrapSpcTxt.setText("");
//	        if ((jcb_snmpv1_TrapSel.getSelectedItem())=="enterpriseSpecific"){
//	        	jtf_snmpv1_TrapSpcTxt.setEditable(true);
//	        	jl_snmpv1_TrapSpc.setEnabled(true);
//	        	jtf_snmpv1_TrapSpcTxt.setText("1");
//	        } 
//	      }
//	    });
//		
//		jl_snmpv1_PtoCom = new JLabel("Puerto");
//	    jl_snmpv1_PtoCom.setBounds(new Rectangle(310,30,50,20));    
//	    jp_snmpv1_TrapsSend.add(jl_snmpv1_PtoCom,null);
//
//	    jtf_snmpv1_PtoComTxt = new JTextField();
//	    jtf_snmpv1_PtoComTxt.setBounds(new Rectangle(390,30,85,20));    
//	    //jtf_snmpv1_PtoComTxt.setEditable(true);
//	    jtf_snmpv1_PtoComTxt.setText("162");
//	    jp_snmpv1_TrapsSend.add(jtf_snmpv1_PtoComTxt,null);	 
//	    	
//		jl_snmpv1_Com = new JLabel("Comunidad");
//	    jl_snmpv1_Com.setBounds(new Rectangle(310,60,150,20));    
//	    jp_snmpv1_TrapsSend.add(jl_snmpv1_Com,null);
//
//	    jtf_snmpv1_ComTxt = new JTextField();
//	    jtf_snmpv1_ComTxt.setBounds(new Rectangle(390,60,85,20));    
//	    //jtf_snmpv1_ComTxt.setEditable(true);
//	    jtf_snmpv1_ComTxt.setText("public");
//	    jp_snmpv1_TrapsSend.add(jtf_snmpv1_ComTxt,null);	 
//	    	
//		jl_snmpv1_Int = new JLabel("Intentos");
//	    jl_snmpv1_Int.setBounds(new Rectangle(310,90,200,20));    
//	    jp_snmpv1_TrapsSend.add(jl_snmpv1_Int,null);
//
//	    jtf_snmpv1_IntTxt = new JTextField();
//	    jtf_snmpv1_IntTxt.setBounds(new Rectangle(390,90,85,20));    
//	    //jtf_snmpv1_IntTxt.setEditable(true);
//	    jtf_snmpv1_IntTxt.setText("3");
//	    jp_snmpv1_TrapsSend.add(jtf_snmpv1_IntTxt,null);	 
//	    
//		jl_snmpv1_Timeout = new JLabel("TimeOut (ms)");
//	    jl_snmpv1_Timeout.setBounds(new Rectangle(310,120,150,20));    
//	    jp_snmpv1_TrapsSend.add(jl_snmpv1_Timeout,null);
//
//	    jtf_snmpv1_TmoutTxt = new JTextField();
//	    jtf_snmpv1_TmoutTxt.setBounds(new Rectangle(390,120,85,20));
//	    jtf_snmpv1_TmoutTxt.setText("1500");    
//	    //jtf_snmpv1_TmoutTxt.setEditable(true);
//	    jp_snmpv1_TrapsSend.add(jtf_snmpv1_TmoutTxt,null);	 
// 	    	
//		jl_snmpv1_Enter = new JLabel("Enterprise OID");
//	    jl_snmpv1_Enter.setBounds(new Rectangle(10,120,150,20));    
//	    jp_snmpv1_TrapsSend.add(jl_snmpv1_Enter,null);
//
//	    jtf_snmpv1_EntTxt = new JTextField();
//	    jtf_snmpv1_EntTxt.setBounds(new Rectangle(150,120,150,20));    
//	    //jtf_snmpv1_EntTxt.setEditable(true);
//	    jtf_snmpv1_EntTxt.setText("1.3.6.1.4.1.2854");	
//	    jp_snmpv1_TrapsSend.add(jtf_snmpv1_EntTxt,null);	 
//			
//		jl_snmpv1_TrapSpc = new JLabel("Trap Especfico");
//	    jl_snmpv1_TrapSpc.setBounds(new Rectangle(10,90,150,20));    
//	    jl_snmpv1_TrapSpc.setEnabled(false);
//	    jp_snmpv1_TrapsSend.add(jl_snmpv1_TrapSpc,null);
//
//	    jtf_snmpv1_TrapSpcTxt = new JTextField();
//	    jtf_snmpv1_TrapSpcTxt.setBounds(new Rectangle(150,90,150,20));    
//	    jtf_snmpv1_TrapSpcTxt.setEditable(false);
//	    jp_snmpv1_TrapsSend.add(jtf_snmpv1_TrapSpcTxt,null);	 
//
//	    jb_SndTrap = new JButton("Enviar TRAP");
//	    jb_SndTrap.setBounds(new Rectangle(155,150,150,20));
//	    jb_SndTrap.setToolTipText("Presione para enviar el TRAP.");
//	    jp_snmpv1_TrapsSend.add(jb_SndTrap,null);
//	    
//    	jb_SndTrap.addActionListener(new ActionListener(){ 
//        	public void actionPerformed(ActionEvent e) {
//	          	String men_err = "";
//	          	boolean men_err_l = false;
// 
//          		if (esVacio(jtf_snmpv1_TrapSndHostIP.getText())){          			
//          		  men_err = men_err.concat(erroresGenerales36);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          	  	if (!(ipValida(jtf_snmpv1_TrapSndHostIP.getText()))){
//          	  	  men_err = men_err.concat(configParamError02);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          		if (!(esNumero(jtf_snmpv1_PtoComTxt.getText()))){          			
//          		  men_err = men_err.concat(configParamError03);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          	  	if (esVacio(jtf_snmpv1_ComTxt.getText())){          			
//          		  men_err = men_err.concat(erroresGenerales37);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          		if (!(esNumero(jtf_snmpv1_IntTxt.getText()))){          			
//          		  men_err = men_err.concat(configParamError06);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          		if (!(esNumero(jtf_snmpv1_TmoutTxt.getText()))){          			
//          		  men_err = men_err.concat(configParamError08);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//        		if ((jcb_snmpv1_TrapSel.getSelectedItem())=="enterpriseSpecific"){
//		        	if (!(esNumero(jtf_snmpv1_TrapSpcTxt.getText()))){          			
//		          		  men_err = men_err.concat(erroresGenerales32);	 	
//		          	  	  men_err_l =true;
//		          	  	}
//		        }
//         
//          	  	if (esVacio(jtf_snmpv1_EntTxt.getText())){          			
//          		  men_err = men_err.concat(erroresGenerales33);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          	  	if (!(esOID(jtf_snmpv1_EntTxt.getText()))){          			
//          		  men_err = men_err.concat(erroresGenerales34);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          		if ((esNumero(jtf_snmpv1_IntTxt.getText()))&&(Integer.parseInt(jtf_snmpv1_IntTxt.getText())<=0)){          			
//          		  men_err = men_err.concat(configParamError07);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          		if ((esNumero(jtf_snmpv1_TmoutTxt.getText()))&&(Integer.parseInt(jtf_snmpv1_TmoutTxt.getText())<=0)){          			
//          		  men_err = men_err.concat(configParamError09);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//
//          		if (men_err_l){          			
//					JOptionPane.showMessageDialog(managerSNMP.this,men_err,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	          	  	          	  
//			    	if (esVacio(jtf_snmpv1_TrapSndHostIP.getText())){jtf_snmpv1_TrapSndHostIP.setText("");}
//			    	if (!(esNumero(jtf_snmpv1_PtoComTxt.getText()))){jtf_snmpv1_PtoComTxt.setText("162");  }
//			    	if (esVacio(jtf_snmpv1_ComTxt.getText())){jtf_snmpv1_ComTxt.setText("public");}
//			    	if (!(esNumero(jtf_snmpv1_IntTxt.getText()))){jtf_snmpv1_IntTxt.setText("3");}  
//			    	if ((esNumero(jtf_snmpv1_IntTxt.getText()))&&(Integer.parseInt(jtf_snmpv1_IntTxt.getText())<=0)){jtf_snmpv1_IntTxt.setText("3");}
//					if (!(esNumero(jtf_snmpv1_TmoutTxt.getText()))){jtf_snmpv1_TmoutTxt.setText("1500");}
//			    	if ((esNumero(jtf_snmpv1_TmoutTxt.getText()))&&(Integer.parseInt(jtf_snmpv1_TmoutTxt.getText())<=0)){jtf_snmpv1_TmoutTxt.setText("1500");}			
//			    	if ((jcb_snmpv1_TrapSel.getSelectedItem())=="enterpriseSpecific"){
//			        	if (!(esNumero(jtf_snmpv1_TrapSpcTxt.getText()))){ 
//			        	  jtf_snmpv1_TrapSpcTxt.setText("1");
//			        	}
//			        }
//			        if (!(esOID(jtf_snmpv1_EntTxt.getText()))){jtf_snmpv1_EntTxt.setText("1.3.6.1.4.1.2854");}
//			        if (esVacio(jtf_snmpv1_EntTxt.getText())){jtf_snmpv1_EntTxt.setText("1.3.6.1.4.1.2854");}
//				}else{
//					enviarTrapInform sndtrap = new enviarTrapInform();
//					traps.cambiarIdiomaAMensajes(mensajesDeTraps01,mensajesDeTraps02,mensajesDeTraps03,mensajesDeTraps04,mensajesDeTraps05,mensajesDeTraps06,mensajesDeTraps07,mensajesDeTraps08,mensajesDeTraps09,mensajesDeTraps10,mensajesDeTraps11,mensajesDeTraps12,mensajesDeTraps13,mensajesDeTraps14,mensajesDeTraps15,mensajesDeTraps16,mensajesDeTraps17,mensajesDeTraps18);
//					
//					int tipoTrapsnd = 0;
//					int tipoTrapsndSpc = 0;
//					if ((jcb_snmpv1_TrapSel.getSelectedItem())=="coldStart"){tipoTrapsnd=PDUv1.COLDSTART;}
//					if ((jcb_snmpv1_TrapSel.getSelectedItem())=="warmStart"){tipoTrapsnd=PDUv1.WARMSTART;}
//					if ((jcb_snmpv1_TrapSel.getSelectedItem())=="linkDown"){tipoTrapsnd=PDUv1.LINKDOWN;}
//					if ((jcb_snmpv1_TrapSel.getSelectedItem())=="linkUp"){tipoTrapsnd=PDUv1.LINKUP;}
//					if ((jcb_snmpv1_TrapSel.getSelectedItem())=="authenticationFailure"){tipoTrapsnd=PDUv1.AUTHENTICATIONFAILURE;}
//					if ((jcb_snmpv1_TrapSel.getSelectedItem())=="enterpriseSpecific"){tipoTrapsnd=PDUv1.ENTERPRISE_SPECIFIC;tipoTrapsndSpc=Integer.parseInt(jtf_snmpv1_TrapSpcTxt.getText());}
//							
//					sndtrap.trapv1(jtf_snmpv1_TrapSndHostIP.getText(), jtf_snmpv1_PtoComTxt.getText(), jtf_snmpv1_ComTxt.getText(), Integer.parseInt(jtf_snmpv1_IntTxt.getText()), Integer.parseInt(jtf_snmpv1_TmoutTxt.getText()), "1.3.6.1.4.1.2789.2005.1={s}WWW Server Has Been Restarted",jtf_snmpv1_EntTxt.getText(),tipoTrapsnd,tipoTrapsndSpc);
//	          	  	          	  	 
//					JOptionPane.showMessageDialog(managerSNMP.this, erroresGenerales35 ,nameOfTheProgram,JOptionPane.INFORMATION_MESSAGE,(new ImageIcon("images/help.gif")));
//				}
//         	}
//       	});
//		//----------------------------------Fin de Pantalla de Traps-------------------------------
//	  
//  	//----------------------------Fin del Manager SNMPv1--------------------------------------
//  	
//  	  	
//  	//--------------------------------Manager SNMPv2c-----------------------------------------
//  	snmpv2c = new JPanel();
//	//snmpv2c.setBackground(Color.white);
//	//snmpv2c.setBounds(new Rectangle(0,0,100,100));
//	snmpv2c.setLayout(null);
//	//snmpv2c.setVisible(false);
//	//jpanel.add(snmpv2c,null);	
//	
//	jl_snmpv2c_sel = new JLabel("Accin a realizar");//USO DEL JLABEL
//    jl_snmpv2c_sel.setBounds(new Rectangle(5,5,100,20));//establece el xy del componente   
//    snmpv2c.add(jl_snmpv2c_sel,null);
//    
//    jcb_snmpv2c_sel = new JComboBox();//USO DEL JCOMBOBOX
//  	jcb_snmpv2c_sel.setBounds(new Rectangle(110,5,373,20));
//	jcb_snmpv2c_sel.addItem("Configure Parameters");
//  	jcb_snmpv2c_sel.addItem("Comando Get");
//  	jcb_snmpv2c_sel.addItem("Comando GetNext");
//  	jcb_snmpv2c_sel.addItem("Comando GetBulk");
//  	jcb_snmpv2c_sel.addItem("Comando GetTable");
//  	jcb_snmpv2c_sel.addItem("Comando Walk");
//  	jcb_snmpv2c_sel.addItem("Comando Set");
//  	jcb_snmpv2c_sel.addItem("Enviar/Ver Traps");
//  	//jcb_snmpv1_sel.setMaximumRowCount(2);
//  	snmpv2c.add(jcb_snmpv2c_sel,null);
//  	
//  	jcb_snmpv2c_sel.addActionListener(new ActionListener(){
//      public void actionPerformed(ActionEvent e) {
//        jp_snmpv2c_Con.setVisible(false);
//        jp_snmpv2c_Get.setVisible(false);
//        jp_snmpv2c_GetNext.setVisible(false);
//        jp_snmpv2c_GetBulk.setVisible(false);
//        jp_snmpv2c_Set.setVisible(false);
//        jp_snmpv2c_Traps.setVisible(false);
//        jp_snmpv2c_TrapsSend.setVisible(false);    
//        jp_snmpv2c_walk.setVisible(false); 
//        jp_snmpv2c_getTable.setVisible(false); 
//        if ((jcb_snmpv2c_sel.getSelectedItem())==ConfigureParameters){jp_snmpv2c_Con.setVisible(true);}
//        if ((jcb_snmpv2c_sel.getSelectedItem())==ComandoGet){jp_snmpv2c_Get.setVisible(true);}
//        if ((jcb_snmpv2c_sel.getSelectedItem())==ComandoGetNext){jp_snmpv2c_GetNext.setVisible(true);}
//        if ((jcb_snmpv2c_sel.getSelectedItem())==ComandoGetBulk){jp_snmpv2c_GetBulk.setVisible(true);}
//        if ((jcb_snmpv2c_sel.getSelectedItem())==ComandoGetTable){jp_snmpv2c_getTable.setVisible(true);}
//        if ((jcb_snmpv2c_sel.getSelectedItem())==ComandoWalk){jp_snmpv2c_walk.setVisible(true);}
//        if ((jcb_snmpv2c_sel.getSelectedItem())==ComandoSet){jp_snmpv2c_Set.setVisible(true);}
//        if ((jcb_snmpv2c_sel.getSelectedItem())==EnviarVerTraps){jp_snmpv2c_Traps.setVisible(true);}
//        if ((jcb_snmpv2c_sel.getSelectedItem())==EnviarVerTraps){jp_snmpv2c_TrapsSend.setVisible(true);}   
//      }
//    });
//	
//	
//		//----------------------------------Pantalla de Conexin-----------------------------------
//	    jp_snmpv2c_Con = new JPanel();
//	    //jp_snmpv2c_Con.setBackground(Color.white);
//	    jp_snmpv2c_Con.setBounds(new Rectangle(0,30,483,483));
//		jp_snmpv2c_Con.setBorder(BorderFactory.createTitledBorder("Configure Parameters"));
//		jp_snmpv2c_Con.setLayout(null);
//		jp_snmpv2c_Con.setVisible(true);
//		snmpv2c.add(jp_snmpv2c_Con,null);
//
//		jl_snmpv2c_IP = new JLabel("Direccin IP del Agente");//USO DEL JLABEL
//    	jl_snmpv2c_IP.setBounds(new Rectangle(76,86,160,20));//establece el xy del componente
//    	jp_snmpv2c_Con.add(jl_snmpv2c_IP,null);
//    
//    	jtf_snmpv2c_IP = new JTextField();//USO DEL JTEXTFIELD
//    	jtf_snmpv2c_IP.setBounds(new Rectangle(246,86,160,20));//establece el xy del componente
//    	jtf_snmpv2c_IP.setText(String.valueOf(IP));
//    	jp_snmpv2c_Con.add(jtf_snmpv2c_IP,null);
//    
//    	jl_snmpv2c_pto = new JLabel("Puerto de Comunicaciones");//USO DEL JLABEL
//    	jl_snmpv2c_pto.setBounds(new Rectangle(76,126,160,20));//establece el xy del componente
//    	jp_snmpv2c_Con.add(jl_snmpv2c_pto,null);
//    
//    	jtf_snmpv2c_pto = new JTextField();//USO DEL JTEXTFIELD
//    	jtf_snmpv2c_pto.setBounds(new Rectangle(246,126,160,20));//establece el xy del componente
//    	jtf_snmpv2c_pto.setText(String.valueOf(pto));
//    	jp_snmpv2c_Con.add(jtf_snmpv2c_pto,null);
//    	
//    	jl_snmpv2c_comLec = new JLabel("Comunidad de Lectura");//USO DEL JLABEL
//    	jl_snmpv2c_comLec.setBounds(new Rectangle(76,206,160,20));//establece el xy del componente
//    	jp_snmpv2c_Con.add(jl_snmpv2c_comLec,null);
//
//    	jtf_snmpv2c_comLec = new JPasswordField();//USO DEL JPASSWORDFIELD
//    	jtf_snmpv2c_comLec.setBounds(new Rectangle(246,206,160,20));//establece el xy del componente
//    	jtf_snmpv2c_comLec.setEchoChar('*');
//    	jtf_snmpv2c_comLec.setText(String.valueOf(comLec));
//    	//jtf_snmpv2c_comLec.setEchoChar((char)0);
//    	jp_snmpv2c_Con.add(jtf_snmpv2c_comLec,null);
//    	
//    	jl_snmpv2c_comEsc = new JLabel("Comunidad de Escritura");//USO DEL JLABEL
//    	jl_snmpv2c_comEsc.setBounds(new Rectangle(76,246,160,20));//establece el xy del componente
//    	jp_snmpv2c_Con.add(jl_snmpv2c_comEsc,null);
//	    
//    	jpf_snmpv2c_comEsc = new JPasswordField();//USO DEL JPASSWORDFIELD
//    	jpf_snmpv2c_comEsc.setBounds(new Rectangle(246,246,160,20));//establece el xy del componente
//    	jpf_snmpv2c_comEsc.setEchoChar('*');
//    	jpf_snmpv2c_comEsc.setText(String.valueOf(comEsc));
//    	//jpf_snmpv2c_comEsc.setEchoChar((char)0);
//    	jp_snmpv2c_Con.add(jpf_snmpv2c_comEsc,null);
//
//    	jl_snmpv2c_VerCom = new JLabel("Visualizar Comunidades");//USO DEL JLABEL
//    	jl_snmpv2c_VerCom.setBounds(new Rectangle(76,166,160,20));//establece el xy del componente
//    	jp_snmpv2c_Con.add(jl_snmpv2c_VerCom,null);
//
//	    jcb_snmpv2c_VerCom = new JComboBox();//USO DEL JCOMBOBOX
//	  	jcb_snmpv2c_VerCom.setBounds(new Rectangle(246,166,160,20));  
//		jcb_snmpv2c_VerCom.addItem("Si");
//	  	jcb_snmpv2c_VerCom.addItem("No");
//	  	jcb_snmpv2c_VerCom.setSelectedIndex(1);	  	 
//	  	jp_snmpv2c_Con.add(jcb_snmpv2c_VerCom,null);
//
//	  	jcb_snmpv2c_VerCom.addActionListener(new ActionListener(){
//	      public void actionPerformed(ActionEvent e) {	          
//	        if ((jcb_snmpv2c_VerCom.getSelectedItem())==opcionSi){jpf_snmpv2c_comEsc.setEchoChar((char)0);jtf_snmpv2c_comLec.setEchoChar((char)0);}; 
//	        if ((jcb_snmpv2c_VerCom.getSelectedItem())==opcionNo){jpf_snmpv2c_comEsc.setEchoChar('*');jtf_snmpv2c_comLec.setEchoChar('*');}; 	
//	      }
//	    });
//
//    	jl_snmpv2c_inten = new JLabel("Nro. de Intentos");//USO DEL JLABEL
//    	jl_snmpv2c_inten.setBounds(new Rectangle(76,286,160,20));//establece el xy del componente
//    	jp_snmpv2c_Con.add(jl_snmpv2c_inten,null);
//    
//    	jtf_snmv2c_inten = new JTextField();//USO DEL JTEXTFIELD
//    	jtf_snmv2c_inten.setBounds(new Rectangle(246,286,160,20));//establece el xy del componente
//    	jtf_snmv2c_inten.setText(String.valueOf(inten));
//    	jp_snmpv2c_Con.add(jtf_snmv2c_inten,null);
//
//    	jl_snmpv2c_timeOut = new JLabel("Tiempo de Espera (ms)");//USO DEL JLABEL
//    	jl_snmpv2c_timeOut.setBounds(new Rectangle(76,326,160,20));//establece el xy del componente
//    	jp_snmpv2c_Con.add(jl_snmpv2c_timeOut,null);
//    
//    	jtt_snmpv2c_timeOut = new JTextField();//USO DEL JTEXTFIELD
//    	jtt_snmpv2c_timeOut.setBounds(new Rectangle(246,326,160,20));//establece el xy del componente
//    	jtt_snmpv2c_timeOut.setText(String.valueOf(timeOut));
//    	jp_snmpv2c_Con.add(jtt_snmpv2c_timeOut,null);
// 
//    	jb_snmpv2c_aplicarPara = new JButton("Aplicar Cambios");
//	    jb_snmpv2c_aplicarPara.setBounds(new Rectangle(141,376,200,20));
//	    jb_snmpv2c_aplicarPara.setToolTipText("Presione para establecer los nuevos parmetros.");
//	    jp_snmpv2c_Con.add(jb_snmpv2c_aplicarPara,null);
//	
//		jb_snmpv2c_aplicarPara.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//          	String men_err = "";
//          	boolean men_err_l = false;
//
//          		if (esVacio(jtf_snmpv2c_IP.getText())){          			
//          		  men_err = men_err.concat(configParamError01);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          	  	if (!(ipValida(jtf_snmpv2c_IP.getText()))){
//          	  	  men_err = men_err.concat(configParamError02);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          	  	
//          		if (!(esNumero(jtf_snmpv2c_pto.getText()))){          			
//          		  men_err = men_err.concat(configParamError03);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          	  	if (esVacio(jpf_snmpv2c_comEsc.getText())){          			
//          		  men_err = men_err.concat(configParamError04);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          	  	if (esVacio(jtf_snmpv2c_comLec.getText())){          			
//          		  men_err = men_err.concat(configParamError05);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          		if (!(esNumero(jtf_snmv2c_inten.getText()))){          			
//          		  men_err = men_err.concat(configParamError06);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          		if ((esNumero(jtf_snmv2c_inten.getText()))&&(Integer.parseInt(jtf_snmv2c_inten.getText())<=0)){          			
//          		  men_err = men_err.concat(configParamError07);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          		if (!(esNumero(jtt_snmpv2c_timeOut.getText()))){          			
//          		  men_err = men_err.concat(configParamError08);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          		if ((esNumero(jtt_snmpv2c_timeOut.getText()))&&(Integer.parseInt(jtt_snmpv2c_timeOut.getText())<=0)){          			
//          		  men_err = men_err.concat(configParamError09);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          		if (men_err_l){          			
//				JOptionPane.showMessageDialog(managerSNMP.this,men_err,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	          	  	          	  
//		    	if (esVacio(jtf_snmpv2c_IP.getText())){jtf_snmpv2c_IP.setText(String.valueOf(IP));}
//		    	if (!(ipValida(jtf_snmpv2c_IP.getText()))){jtf_snmpv2c_IP.setText(String.valueOf(IP));}
//		    	if (!(esNumero(jtt_snmpv2c_timeOut.getText()))){jtt_snmpv2c_timeOut.setText(String.valueOf(timeOut));}
//		    	if ((esNumero(jtt_snmpv2c_timeOut.getText()))&&(Integer.parseInt(jtt_snmpv2c_timeOut.getText())<=0)){jtt_snmpv2c_timeOut.setText(String.valueOf(timeOut));}
//		    	if (!(esNumero(jtf_snmv2c_inten.getText()))){jtf_snmv2c_inten.setText(String.valueOf(inten));}
//		    	 if ((esNumero(jtf_snmv2c_inten.getText()))&&(Integer.parseInt(jtf_snmv2c_inten.getText())<=0)){jtf_snmv2c_inten.setText(String.valueOf(inten));}
//				if (!(esNumero(jtf_snmpv2c_pto.getText()))){jtf_snmpv2c_pto.setText(String.valueOf(pto));}    
//				if (esVacio(jpf_snmpv2c_comEsc.getText())){jpf_snmpv2c_comEsc.setText(String.valueOf(comEsc));}
//				if (esVacio(jtf_snmpv2c_comLec.getText())){jtf_snmpv2c_comLec.setText(String.valueOf(comLec));}
//
//				} else {          	  	          	  	
//          		IP =		 jtf_snmpv2c_IP.getText();
//          		pto =	 Long.parseLong(jtf_snmpv2c_pto.getText());
//          		comLec =	 jtf_snmpv2c_comLec.getText();
//          		comEsc =	 jpf_snmpv2c_comEsc.getText();
//          		inten =	 Integer.parseInt(jtf_snmv2c_inten.getText());
//          		timeOut = Integer.parseInt(jtt_snmpv2c_timeOut.getText());
//				
//				JOptionPane.showMessageDialog(managerSNMP.this, configParamResult01 ,nameOfTheProgram,JOptionPane.INFORMATION_MESSAGE,(new ImageIcon("images/help.gif")));  	          	  	          	  
//					
//				//PARA LLENAR LOS PARAMETROS DE SNMPv1					
//				jtf_snmpv1_IP.setText(IP);
//          		jtf_snmpv1_pto.setText(String.valueOf(pto));
//          		jtf_snmpv1_comLec.setText(comLec);
//          		jpf_snmpv1_comEsc.setText(comEsc);
//          		jtf_snmv1_inten.setText(String.valueOf(inten));
//          		jtt_snmpv1_timeOut.setText(String.valueOf(timeOut));
//          		
//          		//PARA LLENAR LOS PARAMETROS DE SNMPv3	
//				jtf_snmpv3_IP.setText(IP);
//          		jtf_snmpv3_pto.setText(String.valueOf(pto));
//          		jtf_snmv3_inten.setText(String.valueOf(inten));
//          		jtt_snmpv3_timeOut.setText(String.valueOf(timeOut));	
//				}
//         	}
//       	});
//	    //----------------------------------Fin de Pantalla de Conexin----------------------------
//	
//	    //----------------------------------Pantalla de Get----------------------------------------  	
//	    jp_snmpv2c_Get = new JPanel();
//	    //jp_snmpv2c_Get.setBackground(Color.blue);
//		//jp_snmpv2c_Get.setBorder(BorderFactory.createTitledBorder("Comando Get de SNMPv1"));
//		jp_snmpv2c_Get.setBounds(new Rectangle(0,30,483,483));
//		jp_snmpv2c_Get.setBorder(BorderFactory.createTitledBorder("Comando Get"));
//		jp_snmpv2c_Get.setLayout(null);
//		jp_snmpv2c_Get.setVisible(false);
//		snmpv2c.add(jp_snmpv2c_Get,null);    	    	
//
//    	jsp_snmpv2c_getDescrip = new JScrollPane();
//    	jsp_snmpv2c_getDescrip.setBounds(new Rectangle(10,20,465,270));
//    	jsp_snmpv2c_getDescrip.setWheelScrollingEnabled(true);
//    	jp_snmpv2c_Get.add(jsp_snmpv2c_getDescrip,null);
//    	
//    	jta_snmpv2c_getDescrip = new JTextArea();
//    	jta_snmpv2c_getDescrip.setText("");
//    	jta_snmpv2c_getDescrip.setEditable(false);
//    	jsp_snmpv2c_getDescrip.getViewport().add(jta_snmpv2c_getDescrip,null);
//    	
//    	jl_snmpv2c_getEtiGet = new JLabel("OID");
//	    jl_snmpv2c_getEtiGet.setBounds(new Rectangle(10,300,20,20));
//	    jp_snmpv2c_Get.add(jl_snmpv2c_getEtiGet,null);
//	    
//	    jtf_snmpv2c_getGet = new JTextField();
//	    jtf_snmpv2c_getGet.setBounds(new Rectangle(40,300,245,20));   
//	    jtf_snmpv2c_getGet.setEditable(true);
//	    jp_snmpv2c_Get.add(jtf_snmpv2c_getGet,null);
//
//    	jb_snmpv2c_getAdd = new JButton("Aadir");
//	    jb_snmpv2c_getAdd.setBounds(new Rectangle(295,300,79,20));  
//	    jb_snmpv2c_getAdd.setToolTipText("Presione para agregar el OID.");
//	    jp_snmpv2c_Get.add(jb_snmpv2c_getAdd,null);
//	    
//    	jb_snmpv2c_getUndo = new JButton("Deshacer");
//	    jb_snmpv2c_getUndo.setBounds(new Rectangle(384,300,90,20));  //9999
//	    jb_snmpv2c_getUndo.setToolTipText("Presione para eliminar el ltimo OID de los objetos.");
//	    jp_snmpv2c_Get.add(jb_snmpv2c_getUndo,null);
//	    
//    	jl_snmpv2c_getObjs = new JLabel("Objetos");
//	    jl_snmpv2c_getObjs.setBounds(new Rectangle(10,330,60,20));
//	    jp_snmpv2c_Get.add(jl_snmpv2c_getObjs,null);
//	    
//	    jtf_snmpv2c_getObjs = new JTextField();
//	    jtf_snmpv2c_getObjs.setBounds(new Rectangle(60,330,344,20));   
//	    jtf_snmpv2c_getObjs.setEditable(false);	    	    
//	    jp_snmpv2c_Get.add(jtf_snmpv2c_getObjs,null);
//
//
//    	jb_snmpv2c_getGet = new JButton("Get");
//	    jb_snmpv2c_getGet.setBounds(new Rectangle(415,330,59,20));  
//	    jb_snmpv2c_getGet.setToolTipText("Presione para obtener el valor.");
//	    jp_snmpv2c_Get.add(jb_snmpv2c_getGet,null);
//	    
//	    jsp_snmpv2c_getResp = new JScrollPane();
//    	jsp_snmpv2c_getResp.setBounds(new Rectangle(10,360,465,111));  
//    	jsp_snmpv2c_getResp.setWheelScrollingEnabled(true);
//    	jp_snmpv2c_Get.add(jsp_snmpv2c_getResp,null);
//    	
//    	jta_snmpv2c_getResp = new JTextArea();
//    	jta_snmpv2c_getResp.setText("");
//    	jta_snmpv2c_getResp.setEditable(false);
//    	jsp_snmpv2c_getResp.getViewport().add(jta_snmpv2c_getResp,null);
//		
//		compuestoGetSNMPv2cTemp = new Vector();
//			    
//		//para aadir
//		jb_snmpv2c_getAdd.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//        	  
//        	  //aqui lo que hace
//          	  if ((jtf_snmpv2c_getGet.getText()).equals("")){
//          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//          	  }else{
//          	  	//Tratamiento
//          	  	OID = jtf_snmpv2c_getGet.getText();
//          	  	
//          	  	if (esVacio(IP)){
//          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
//          	  	}else{        	  	
//          	  	          	  	
//					//Para transformar el OID de string al arreglo para pasarselo al get
//	          	  	//System.out.println("Tratando el oid");
//	          	  	try{
//		          	  	int digitos=0;
//		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
//						while(Token.hasMoreTokens()){
//						  Token.nextToken();
//						  digitos++;	
//						}
//		          	  	//System.out.println("el oid tiene "+digitos+"digitos");
//		          	  	int tamao=digitos;
//		          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
//		          	  	int requerimiento[] = new int[tamao];
//		          	  	int i=0;
//		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
//						while(Token1.hasMoreTokens()){
//						  requerimiento[i]=Integer.parseInt(Token1.nextToken());
//						  i++;
//						  //System.out.println("valor de i: "+i);	
//						}
//		          	  	//requerimiento[i]=0;
//		          	  	/*
//		          	  	System.out.println("cambiado a arreglo");
//		          	  	i++;
//		          	  	System.out.println("tamao del arreglo: "+i);
//		          	  	for (int j=0;j<i;j++){
//		          	  	  System.out.println("d"+j+" valor: "+requerimiento[j]);	
//		          	  	}
//		          	  	*/
//		          	  	
//		          	  	compuestoGetSNMPv2cTemp.add(new OID(requerimiento));
//	          	  	    
//	          	  	    String contenido = "";
//					    for (int pp=0;pp<compuestoGetSNMPv2cTemp.size();pp++){
//					      contenido=contenido.concat(compuestoGetSNMPv2cTemp.get(pp)+"; ");	
//					    }
//					    jtf_snmpv2c_getObjs.setText(contenido);
//		         	  	
//		          	  }catch(NumberFormatException nfe){
//	          	  	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//	          	  	  }
//          	  	}
//          	  }
//        	  
//        	}
//        });
//        
//        //para eliminar
//        jb_snmpv2c_getUndo.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//        
//              int ultimo = compuestoGetSNMPv2cTemp.size();
//        	  ultimo=ultimo-1;
//        	  if (ultimo>=0){
//        	    compuestoGetSNMPv2cTemp.removeElementAt(ultimo);
//        	  }else{
//        	  	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//        	  }
//    	  	
//		      String contenido = "";
//		      for (int pp=0;pp<compuestoGetSNMPv2cTemp.size();pp++){
//		        contenido=contenido.concat(compuestoGetSNMPv2cTemp.get(pp)+"; ");	
//		      }
//		      jtf_snmpv2c_getObjs.setText(contenido);
//        	
//        	}
//        });    
//
//
//    	//esto va para el get no para aca
//	      jb_snmpv2c_getGet.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//          	  //aqui lo que hace
//          	  if ((jtf_snmpv2c_getGet.getText()).equals("")){
//          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//          	  }else{
//          	  	//Tratamiento
//          	  	OID = jtf_snmpv2c_getGet.getText();
//          	  	
//          	  	if (esVacio(IP)){
//          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
//          	  	}else{        	  	
//          	  	          	  	
//					//Para transformar el OID de string al arreglo para pasarselo al get
//	          	  	//System.out.println("Tratando el oid");
//	          	  	try{
//		          	  	int digitos=0;
//		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
//						while(Token.hasMoreTokens()){
//						  Token.nextToken();
//						  digitos++;	
//						}
//		          	  	//System.out.println("el oid tiene "+digitos+"digitos");
//		          	  	int tamao=digitos;
//		          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
//		          	  	int requerimiento[] = new int[tamao];
//		          	  	int i=0;
//		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
//						while(Token1.hasMoreTokens()){
//						  requerimiento[i]=Integer.parseInt(Token1.nextToken());
//						  i++;
//						  //System.out.println("valor de i: "+i);	
//						}
//		          	  	//requerimiento[i]=0;
//		          	  	/*
//		          	  	System.out.println("cambiado a arreglo");
//		          	  	i++;
//		          	  	System.out.println("tamao del arreglo: "+i);
//		          	  	for (int j=0;j<i;j++){
//		          	  	  System.out.println("d"+j+" valor: "+requerimiento[j]);	
//		          	  	}
//		          	  	*/
//		          	  	
//		          	  	boolean esCompuesto=false;
//		          	  	compuestoGetSNMPv2c = new Vector();
//		          	  	if((compuestoGetSNMPv2cTemp.size())>=1){
//		          	  	   compuestoGetSNMPv2c=compuestoGetSNMPv2cTemp;
//		          	  	   esCompuesto=true;	
//		          	  	}else{
//		          	  	   compuestoGetSNMPv2c.add(new OID(requerimiento));
//		          	  	   esCompuesto=false;
//		          	  	}
//		          	  	
//	          	  	
//		         	  	SNMPv2c manager = new SNMPv2c();
//		          	  	String respuesta = manager.getv2c(IP, String.valueOf(pto), comLec, inten,timeOut,compuestoGetSNMPv2c,erroresGenerales04);
//		          	  	compuestoGetSNMPv2c.removeAllElements();
//		          	  	compuestoGetSNMPv2cTemp.removeAllElements();
//				        jtf_snmpv2c_getObjs.setText("");
//				        
//		          	  	//System.out.println("Getv2c: "+respuesta);
//		          	  	if (respuesta.equals(erroresGenerales04)){
//		          	  	  respuesta=("Get SNMPv2c: ").concat(respuesta);
//		          	  	}else{
//		          	  	  respuesta=("Get SNMPv2c: ").concat(respuesta);	
//		          	  	}
//		          	  	respuesta=respuesta.concat("\n");
//		          	  	jta_snmpv2c_getResp.setText((jta_snmpv2c_getResp.getText()).concat(respuesta));
//		          	  }catch(NumberFormatException nfe){
//	          	  	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//	          	  	  }
//          	  	}
//          	  }
//         	}
//       	});
//       	
//   		
//	    //jp_snmpv1_Get.setVisible(true);
//				
//		//----------------------------------Fin de Pantalla de Get---------------------------------	
//	
//		//----------------------------------Pantalla de GetNext------------------------------------	      	
//		jp_snmpv2c_GetNext = new JPanel();
//	    //jp_snmpv2c_GetNext.setBackground(Color.blue);
//		//jp_snmpv2c_GetNext.setBorder(BorderFactory.createTitledBorder("Comando GetNext de SNMPv1"));
//		jp_snmpv2c_GetNext.setBounds(new Rectangle(0,30,483,483));  
//		jp_snmpv2c_GetNext.setBorder(BorderFactory.createTitledBorder("Comando GetNext"));
//		jp_snmpv2c_GetNext.setLayout(null);
//		jp_snmpv2c_GetNext.setVisible(false);
//		snmpv2c.add(jp_snmpv2c_GetNext,null);
//	    	
//    	jsp_snmpv2c_GetNextDescrip = new JScrollPane();
//    	jsp_snmpv2c_GetNextDescrip.setBounds(new Rectangle(10,20,465,270));  //320,10,435,300
//    	jsp_snmpv2c_GetNextDescrip.setWheelScrollingEnabled(true);
//    	jp_snmpv2c_GetNext.add(jsp_snmpv2c_GetNextDescrip,null);
//    	
//    	jta_snmpv2c_GetNextDescrip = new JTextArea();           
//    	jta_snmpv2c_GetNextDescrip.setText("");
//    	jta_snmpv2c_GetNextDescrip.setEditable(false);
//    	jsp_snmpv2c_GetNextDescrip.getViewport().add(jta_snmpv2c_GetNextDescrip,null);
//    	
//    	jl_snmpv2c_GetNextEtiGetNext = new JLabel("OID");
//	    jl_snmpv2c_GetNextEtiGetNext.setBounds(new Rectangle(10,300,20,20));   
//	    jp_snmpv2c_GetNext.add(jl_snmpv2c_GetNextEtiGetNext,null);
//	    
//	    jtf_snmpv2c_GetNextGetNext = new JTextField();
//	    jtf_snmpv2c_GetNextGetNext.setBounds(new Rectangle(40,300,245,20));  
//	    jtf_snmpv2c_GetNextGetNext.setEditable(true);
//	    jp_snmpv2c_GetNext.add(jtf_snmpv2c_GetNextGetNext,null);
//
//    	jb_snmpv2c_GetNext_add = new JButton("Aadir");
//	    jb_snmpv2c_GetNext_add.setBounds(new Rectangle(295,300,79,20));  //9999
//	    jb_snmpv2c_GetNext_add.setToolTipText("Presione para agregar el OID.");
//	    jp_snmpv2c_GetNext.add(jb_snmpv2c_GetNext_add,null);
//	    
//    	jl_snmpv2c_getNextObjs = new JLabel("Objetos");
//	    jl_snmpv2c_getNextObjs.setBounds(new Rectangle(10,330,60,20));
//	    jp_snmpv2c_GetNext.add(jl_snmpv2c_getNextObjs,null);
//	   
//	    jtf_snmpv2c_getNextObjs = new JTextField();
//	    jtf_snmpv2c_getNextObjs.setBounds(new Rectangle(60,330,325,20));   
//	    jtf_snmpv2c_getNextObjs.setEditable(false);	    	    
//	    jp_snmpv2c_GetNext.add(jtf_snmpv2c_getNextObjs,null);
//
//    	jb_snmpv2c_GetNextUndo = new JButton("Deshacer");
//	    jb_snmpv2c_GetNextUndo.setBounds(new Rectangle(384,300,90,20));  //9999
//	    jb_snmpv2c_GetNextUndo.setToolTipText("Presione para eliminar el ltimo OID de los objetos.");
//	    jp_snmpv2c_GetNext.add(jb_snmpv2c_GetNextUndo,null);
//	    
//    	jb_snmpv2c_GetNextGetNext = new JButton("GetNext");
//	    jb_snmpv2c_GetNextGetNext.setBounds(new Rectangle(395,330,79,20));   
//	    jb_snmpv2c_GetNextGetNext.setToolTipText("Presione para obtener el valor.");
//	    jp_snmpv2c_GetNext.add(jb_snmpv2c_GetNextGetNext,null);
//	    
//	    jsp_snmpv2c_GetNextResp = new JScrollPane();
//    	jsp_snmpv2c_GetNextResp.setBounds(new Rectangle(10,360,465,111));   
//    	jsp_snmpv2c_GetNextResp.setWheelScrollingEnabled(true);
//    	jp_snmpv2c_GetNext.add(jsp_snmpv2c_GetNextResp,null);
//    	
//    	jta_snmpv2c_GetNextResp = new JTextArea();
//    	jta_snmpv2c_GetNextResp.setText("");
//    	jta_snmpv2c_GetNextResp.setEditable(false);
//    	jsp_snmpv2c_GetNextResp.getViewport().add(jta_snmpv2c_GetNextResp,null);
//	    
//	    compuestoGetNextSNMPv2cTemp = new Vector();
//	    
//	    //Para aadir
//	    jb_snmpv2c_GetNext_add.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//        
//        	  //aqui lo que hace
//          	  if ((jtf_snmpv2c_GetNextGetNext.getText()).equals("")){
//          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//          	  }else{
//          	  	//Tratamiento
//          	  	OID = jtf_snmpv2c_GetNextGetNext.getText();
//          	  	
//          	  	if (esVacio(IP)){
//          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
//          	  	}else{        	  	
//          	  	          	  	
//					//Para transformar el OID de string al arreglo para pasarselo al GetNext
//	          	  	//System.out.println("Tratando el oid");
//	          	  	try{
//		          	  	int digitos=0;
//		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
//						while(Token.hasMoreTokens()){
//						  Token.nextToken();
//						  digitos++;	
//						}
//		          	  	//System.out.println("el oid tiene "+digitos+"digitos");
//		          	  	int tamao=digitos;
//		          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
//		          	  	int requerimiento[] = new int[tamao];
//		          	  	int i=0;
//		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
//						while(Token1.hasMoreTokens()){
//						  requerimiento[i]=Integer.parseInt(Token1.nextToken());
//						  i++;
//						  //System.out.println("valor de i: "+i);	
//						}
//		          	  	//requerimiento[i]=0;
//		          	  	/*
//		          	  	System.out.println("cambiado a arreglo");
//		          	  	i++;
//		          	  	System.out.println("tamao del arreglo: "+i);
//		          	  	for (int j=0;j<i;j++){
//		          	  	  System.out.println("d"+j+" valor: "+requerimiento[j]);	
//		          	  	}
//		          	  	*/
//		         	  	
//		         	  	compuestoGetNextSNMPv2cTemp.add(new OID(requerimiento));
//		          	  	String contenido = "";
//		          	  	for (int pp=0;pp<compuestoGetNextSNMPv2cTemp.size();pp++){
//		          	  	  contenido=contenido.concat(compuestoGetNextSNMPv2cTemp.get(pp)+"; ");	
//		          	  	}
//		          	  	jtf_snmpv2c_getNextObjs.setText(contenido);
//		         	  	
//		          	  	   
//		          	  	
//		          	  }catch(NumberFormatException nfe){
//	          	  	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//	          	  	  }
//          	  	}
//          	  }
//         	
//        	
//        	}
//        });  
//        		
//        //Para eliminar
//        jb_snmpv2c_GetNextUndo.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//        	
//        	  int ultimo = compuestoGetNextSNMPv2cTemp.size();
//        	  ultimo=ultimo-1;
//        	  if (ultimo>=0){
//        	    compuestoGetNextSNMPv2cTemp.removeElementAt(ultimo);
//        	  }else{
//        	  	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//        	  }
//    	  	
//		      String contenido = "";
//		      for (int pp=0;pp<compuestoGetNextSNMPv2cTemp.size();pp++){
//		        contenido=contenido.concat(compuestoGetNextSNMPv2cTemp.get(pp)+"; ");	
//		      }
//		      jtf_snmpv2c_getNextObjs.setText(contenido);
//        	
//        	}
//        }); 
//        
//	    
//    	jb_snmpv2c_GetNextGetNext.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//          	  //aqui lo que hace
//          	  if ((jtf_snmpv2c_GetNextGetNext.getText()).equals("")){
//          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//          	  }else{
//          	  	//Tratamiento
//          	  	OID = jtf_snmpv2c_GetNextGetNext.getText();
//          	  	
//          	  	if (esVacio(IP)){
//          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
//          	  	}else{        	  	
//          	  	          	  	
//					//Para transformar el OID de string al arreglo para pasarselo al GetNext
//	          	  	//System.out.println("Tratando el oid");
//	          	  	try{
//		          	  	int digitos=0;
//		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
//						while(Token.hasMoreTokens()){
//						  Token.nextToken();
//						  digitos++;	
//						}
//		          	  	//System.out.println("el oid tiene "+digitos+"digitos");
//		          	  	int tamao=digitos;
//		          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
//		          	  	int requerimiento[] = new int[tamao];
//		          	  	int i=0;
//		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
//						while(Token1.hasMoreTokens()){
//						  requerimiento[i]=Integer.parseInt(Token1.nextToken());
//						  i++;
//						  //System.out.println("valor de i: "+i);	
//						}
//		          	  	//requerimiento[i]=0;
//		          	  	/*
//		          	  	System.out.println("cambiado a arreglo");
//		          	  	i++;
//		          	  	System.out.println("tamao del arreglo: "+i);
//		          	  	for (int j=0;j<i;j++){
//		          	  	  System.out.println("d"+j+" valor: "+requerimiento[j]);	
//		          	  	}
//		          	  	*/
//		         	  	
//		         	  	boolean esCompuesto=false;
//		          	  	compuestoGetNextSNMPv2c = new Vector();
//		          	  	if((compuestoGetNextSNMPv2cTemp.size())>=1){
//		          	  	   compuestoGetNextSNMPv2c=compuestoGetNextSNMPv2cTemp;
//		          	  	   esCompuesto=true;	
//		          	  	}else{
//		          	  	   compuestoGetNextSNMPv2c.add(new OID(requerimiento));
//		          	  	   esCompuesto=false;
//		          	  	}
//		         	  	
//		         	  	
//		          	  	SNMPv2c manager = new SNMPv2c();
//		          	  			          	  	
//		          	  	String respuesta = manager.getNextv2c(IP, String.valueOf(pto), comLec, inten,timeOut,compuestoGetNextSNMPv2c,erroresGenerales04);
//		          	  	compuestoGetNextSNMPv2c.removeAllElements();
//		          	  	compuestoGetNextSNMPv2cTemp.removeAllElements();
//					    jtf_snmpv2c_getNextObjs.setText("");
//		          	  	
//		          	  	//System.out.println("GetNextv2c: "+respuesta);
//		          	  	if (respuesta.equals(erroresGenerales04)){
//		          	  	  respuesta=("GetNext SNMPv2c: ").concat(respuesta);
//		          	  	}else{
//		          	  	
//		          	  	  if (!esCompuesto){
//			    		  	int ini = respuesta.indexOf(" ");
//			    		  	ini=ini+1;
//			    		  	int fin = respuesta.indexOf(" ",ini);
//		          	  	  	//System.out.println("actual --"+respuesta.substring(ini,fin)+"--");
//		          	  	  	jtf_snmpv2c_GetNextGetNext.setText("."+respuesta.substring(ini,fin));
//			    		  }
//		          	  	  		          	  	  
//		          	  	  //Para el GetNext acumulativo
//		          	  	  respuesta=("GetNext SNMPv2c: ").concat(respuesta);
//		          	  	}
//		          	  	respuesta=respuesta.concat("\n");
//		          	  	jta_snmpv2c_GetNextResp.setText((jta_snmpv2c_GetNextResp.getText()).concat(respuesta));   
//		          	  	
//		          	  }catch(NumberFormatException nfe){
//	          	  	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//	          	  	  }
//          	  	}
//          	  }
//         	}
//       	});
//       	
//		
//		//----------------------------------Fin de Pantalla de GetNext-----------------------------
//
//
//		//---------------------------------------Pantalla de GetBulk----------------------------------
//		
//		jp_snmpv2c_GetBulk = new JPanel();
//	    //jp_snmpv2c_GetBulk.setBackground(Color.white);
//		//jp_snmpv2c_GetBulk.setBorder(BorderFactory.createTitledBorder("Set"));
//		jp_snmpv2c_GetBulk.setBounds(new Rectangle(0,30,483,483));
//		jp_snmpv2c_GetBulk.setBorder(BorderFactory.createTitledBorder("Comando GetBulk"));
//		jp_snmpv2c_GetBulk.setLayout(null);
//		jp_snmpv2c_GetBulk.setVisible(false);
//		snmpv2c.add(jp_snmpv2c_GetBulk,null);
//		
//	    jsp_snmpv2c_GetBulkResp = new JScrollPane();
//    	jsp_snmpv2c_GetBulkResp.setBounds(new Rectangle(10,110,465,361));   
//    	jsp_snmpv2c_GetBulkResp.setWheelScrollingEnabled(true);
//    	jp_snmpv2c_GetBulk.add(jsp_snmpv2c_GetBulkResp,null);
//
//    	jta_snmpv2c_GetBulkResp = new JTextArea();
//    	jta_snmpv2c_GetBulkResp.setText("");
//    	jta_snmpv2c_GetBulkResp.setEditable(false);
//    	jsp_snmpv2c_GetBulkResp.getViewport().add(jta_snmpv2c_GetBulkResp,null);
//    	
//    	//0  <0 se trae todo, si vale 1  >1 se trae solo 1 valor. - pedir por pantalla
//    	jl_snmpv2c_nonRepe = new JLabel("NonRepeaters");//USO DEL JLABEL
//    	jl_snmpv2c_nonRepe.setBounds(new Rectangle(10,50,90,20));//establece el xy del componente
//    	jp_snmpv2c_GetBulk.add(jl_snmpv2c_nonRepe,null);
//    
//    	jtf_snmpv2c_nonRepe = new JTextField();//USO DEL JTEXTFIELD
//    	jtf_snmpv2c_nonRepe.setBounds(new Rectangle(105,50,30,20));//establece el xy del componente
//    	jtf_snmpv2c_nonRepe.setText(String.valueOf(NonRepeaters));
//    	jp_snmpv2c_GetBulk.add(jtf_snmpv2c_nonRepe,null);
//    	
//    	// cantidad de variables que va a retornar - pedir por pantalla
//    	jl_snmpv2c_maxRep = new JLabel("MaxRepetitions");//USO DEL JLABEL
//    	jl_snmpv2c_maxRep.setBounds(new Rectangle(150,50,90,20));//establece el xy del componente
//    	jp_snmpv2c_GetBulk.add(jl_snmpv2c_maxRep,null);
//
//    	jtf_snmpv2c_maxRep = new JTextField();//USO DEL JTEXTFIELD
//    	jtf_snmpv2c_maxRep.setBounds(new Rectangle(250,50,30,20));//establece el xy del componente
//    	jtf_snmpv2c_maxRep.setText(String.valueOf(MaxRepetitions));
//    	jp_snmpv2c_GetBulk.add(jtf_snmpv2c_maxRep,null);
//
//    	jb_snmpv2c_GetBulk_add = new JButton("Aadir");
//	    jb_snmpv2c_GetBulk_add.setBounds(new Rectangle(290,50,79,20));  //9999
//	    jb_snmpv2c_GetBulk_add.setToolTipText("Presione para agregar el OID.");
//	    jp_snmpv2c_GetBulk.add(jb_snmpv2c_GetBulk_add,null);
//	    		
//    	jb_snmpv2c_GetBulkUndo = new JButton("Deshacer");
//	    jb_snmpv2c_GetBulkUndo.setBounds(new Rectangle(380,50,94,20));  //9999
//	    jb_snmpv2c_GetBulkUndo.setToolTipText("Presione para eliminar el ltimo OID de los objetos.");
//	    jp_snmpv2c_GetBulk.add(jb_snmpv2c_GetBulkUndo,null);
//	    
//    	jl_snmpv2c_getBulkObjs = new JLabel("Objetos");
//	    jl_snmpv2c_getBulkObjs.setBounds(new Rectangle(10,80,60,20));
//	    jp_snmpv2c_GetBulk.add(jl_snmpv2c_getBulkObjs,null);
// 
//	    jtf_snmpv2c_getBulkObjs = new JTextField();
//	    jtf_snmpv2c_getBulkObjs.setBounds(new Rectangle(70,80,310,20));   
//	    jtf_snmpv2c_getBulkObjs.setEditable(false);	    	    
//	    jp_snmpv2c_GetBulk.add(jtf_snmpv2c_getBulkObjs,null);
//
//    	jl_snmpv2c_GetBulkEtiSet = new JLabel("OID");
//	    jl_snmpv2c_GetBulkEtiSet.setBounds(new Rectangle(10,20,20,20));   
//	    jp_snmpv2c_GetBulk.add(jl_snmpv2c_GetBulkEtiSet,null);
//
//	    jtf_snmpv2c_GetBulkGetBulk = new JTextField();
//	    jtf_snmpv2c_GetBulkGetBulk.setBounds(new Rectangle(40,20,435,20));    
//	    jtf_snmpv2c_GetBulkGetBulk.setEditable(true);
//	    jp_snmpv2c_GetBulk.add(jtf_snmpv2c_GetBulkGetBulk,null);
// 
//    	jb_snmpv2c_GetBulkGetBulk = new JButton("GetBulk");
//	    jb_snmpv2c_GetBulkGetBulk.setBounds(new Rectangle(390,80,84,20));   
//        jb_snmpv2c_GetBulkGetBulk.setToolTipText("Presione para obtener los valores.");
//	    jp_snmpv2c_GetBulk.add(jb_snmpv2c_GetBulkGetBulk,null);
//		
//		
//		compuestoGetBulkSNMPv2cTemp = new Vector();
//		
//		//Para aadir
//		jb_snmpv2c_GetBulk_add.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//        
//              //aqui lo que hace
//          	  if ((jtf_snmpv2c_GetBulkGetBulk.getText()).equals("")){
//          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//          	    jta_snmpv2c_GetBulkResp.setText("");
//          	  }else{
//          	  	//Tratamiento
//          	  	OID = jtf_snmpv2c_GetBulkGetBulk.getText();
//          	  	
//          	  	if (esVacio(IP)){
//          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
//          	  	  jta_snmpv2c_GetBulkResp.setText("");
//          	  	}else{ 
//          	  		if (!(esNumero(jtf_snmpv2c_nonRepe.getText()))){          			
//          		  	  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales26,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
//          	  		  jta_snmpv2c_GetBulkResp.setText("");
//          	  		}else{
//          	  			if ((!(esNumero(jtf_snmpv2c_maxRep.getText())))||(Integer.parseInt(jtf_snmpv2c_maxRep.getText())<=0)){          			
//          		          JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales27,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
//          	  			  jta_snmpv2c_GetBulkResp.setText("");
//          	  			}else{
//							//Para transformar el OID de string al arreglo para pasarselo al GetBulk
//			          	  	//System.out.println("Tratando el oid");
//			          	  	try{   
//				          	  	int digitos=0;
//				          	  	StringTokenizer Token = new StringTokenizer (OID,".");
//								while(Token.hasMoreTokens()){
//								  Token.nextToken();
//								  digitos++;	
//								}
//				          	  	//System.out.println("el oid tiene "+digitos+"digitos");
//				          	  	int tamao=digitos;
//				          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
//				          	  	int requerimiento[] = new int[tamao];
//				          	  	int i=0;
//				          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
//								while(Token1.hasMoreTokens()){
//								  requerimiento[i]=Integer.parseInt(Token1.nextToken());
//								  i++;
//								  //System.out.println("valor de i: "+i);	
//								}
//				          	  	//requerimiento[i]=0;
//				          	  	
//				          	  	compuestoGetBulkSNMPv2cTemp.add(new OID(requerimiento));
//				          	  	String contenido = "";
//				          	  	for (int pp=0;pp<compuestoGetBulkSNMPv2cTemp.size();pp++){
//				          	  	  contenido=contenido.concat(compuestoGetBulkSNMPv2cTemp.get(pp)+"; ");	
//				          	  	}
//				          	  	jtf_snmpv2c_getBulkObjs.setText(contenido);
//				          	  	
//			          	  	  }catch(NumberFormatException nfe){
//		          	  	    	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//		          	  	    	jta_snmpv2c_GetBulkResp.setText("");
//		          	  	  	  }
//          	  		  }
//          	  		}
//          	  	}
//          	  }
//          	  
//         	
//        	
//        	}
//        }); 
//        	
//        //Para eliminar
//        jb_snmpv2c_GetBulkUndo.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//        
//        	  int ultimo = compuestoGetBulkSNMPv2cTemp.size();
//        	  ultimo=ultimo-1;
//        	  if (ultimo>=0){
//        	    compuestoGetBulkSNMPv2cTemp.removeElementAt(ultimo);
//        	  }else{
//        	  	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//        	  }
//    	  	
//		      String contenido = "";
//		      for (int pp=0;pp<compuestoGetBulkSNMPv2cTemp.size();pp++){
//		        contenido=contenido.concat(compuestoGetBulkSNMPv2cTemp.get(pp)+"; ");	
//		      }
//		      jtf_snmpv2c_getBulkObjs.setText(contenido);
//        		
//        	}
//        });   
//		
//		jb_snmpv2c_GetBulkGetBulk.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//          	  //aqui lo que hace
//          	  if ((jtf_snmpv2c_GetBulkGetBulk.getText()).equals("")){
//          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//          	    jta_snmpv2c_GetBulkResp.setText("");
//          	  }else{
//          	  	//Tratamiento
//          	  	OID = jtf_snmpv2c_GetBulkGetBulk.getText();
//          	  	
//          	  	if (esVacio(IP)){
//          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
//          	  	  jta_snmpv2c_GetBulkResp.setText("");
//          	  	}else{ 
//          	  		if (!(esNumero(jtf_snmpv2c_nonRepe.getText()))){          			
//          		  	  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales26,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
//          	  		  jta_snmpv2c_GetBulkResp.setText("");
//          	  		}else{
//          	  			if ((!(esNumero(jtf_snmpv2c_maxRep.getText())))||(Integer.parseInt(jtf_snmpv2c_maxRep.getText())<=0)){          			
//          		          JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales27,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
//          	  			  jta_snmpv2c_GetBulkResp.setText("");
//          	  			}else{
//							//Para transformar el OID de string al arreglo para pasarselo al GetBulk
//			          	  	//System.out.println("Tratando el oid");
//			          	  	try{   
//				          	  	int digitos=0;
//				          	  	StringTokenizer Token = new StringTokenizer (OID,".");
//								while(Token.hasMoreTokens()){
//								  Token.nextToken();
//								  digitos++;	
//								}
//				          	  	//System.out.println("el oid tiene "+digitos+"digitos");
//				          	  	int tamao=digitos;
//				          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
//				          	  	int requerimiento[] = new int[tamao];
//				          	  	int i=0;
//				          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
//								while(Token1.hasMoreTokens()){
//								  requerimiento[i]=Integer.parseInt(Token1.nextToken());
//								  i++;
//								  //System.out.println("valor de i: "+i);	
//								}
//				          	  	//requerimiento[i]=0;
//				          	  	
//				          	  	boolean esCompuesto=false;
//				          	  	compuestoGetBulkSNMPv2c = new Vector();
//				          	  	if((compuestoGetBulkSNMPv2cTemp.size())>=1){
//				          	  	   compuestoGetBulkSNMPv2c=compuestoGetBulkSNMPv2cTemp;
//				          	  	   esCompuesto=true;	
//				          	  	}else{
//				          	  	   compuestoGetBulkSNMPv2c.add(new OID(requerimiento));
//				          	  	   esCompuesto=false;
//				          	  	}
//				          	  	
//				          	  	
//				          	  	jta_snmpv2c_GetBulkResp.setText(erroresGenerales28);
//				          	  	SNMPv2c recorrer = new SNMPv2c();
//				          	  	
//				          	  	String respuesta = recorrer.getBulkv2c(IP,String.valueOf(pto),comLec,inten,timeOut,compuestoGetBulkSNMPv2c,Integer.parseInt(jtf_snmpv2c_nonRepe.getText()),Integer.parseInt(jtf_snmpv2c_maxRep.getText()),erroresGenerales04);
//				          	  	compuestoGetBulkSNMPv2c.removeAllElements();
//				          	  	compuestoGetBulkSNMPv2cTemp.removeAllElements();
//							    jtf_snmpv2c_getBulkObjs.setText("");
//				          	  	
//				          	  	if (respuesta.equals(erroresGenerales04)){
//		          	  	  		  respuesta=erroresGenerales05;
//		          	  			}else{
//		          	  			  String temp = " ";
//								  StringTokenizer Token2 = new StringTokenizer (respuesta,";");
//								  while(Token2.hasMoreTokens()){
//								    temp=temp.concat(String.valueOf(Token2.nextToken())+("\n"));
//								  }
//								  respuesta=temp;	
//		          	  			}
//				          	  	
//								jta_snmpv2c_GetBulkResp.setText(jta_snmpv2c_GetBulkResp.getText().concat(respuesta));
//				          	  	
//			          	  	  }catch(NumberFormatException nfe){
//		          	  	    	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//		          	  	    	jta_snmpv2c_GetBulkResp.setText("");
//		          	  	  	  }
//          	  		  }
//          	  		}
//          	  	}
//          	  }
//          	  
//         	}
//       	});
//
//		//----------------------------------Fin de Pantalla de GetBulk--------------------------------
//
//		
//		//****************************************************************************************************************************
//		//GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE--------
//
//		jp_snmpv2c_getTable = new JPanel();
//	    //jp_snmpv2c_getTable.setBackground(Color.blue);
//		//jp_snmpv2c_getTable.setBorder(BorderFactory.createTitledBorder("Comando GetNext de SNMPv1"));
//		jp_snmpv2c_getTable.setBounds(new Rectangle(0,30,483,483));  
//		jp_snmpv2c_getTable.setBorder(BorderFactory.createTitledBorder("Comando GetTable"));
//		jp_snmpv2c_getTable.setLayout(null);
//		jp_snmpv2c_getTable.setVisible(false);
//		snmpv2c.add(jp_snmpv2c_getTable,null);
//		/*
//		JLabel jl_snmpv2c_getTableEtigetTable;
//		JTextField jtf_snmpv2c_getTablegetTable;
//		JButton jb_snmpv2c_getTablegetTable;
//		JScrollPane jsp_snmpv2c_getTablegetTable;
//		*/
//
//		jl_snmpv2c_getTableEtigetTable = new JLabel("OID");
//	    jl_snmpv2c_getTableEtigetTable.setBounds(new Rectangle(10,20,20,20));    
//	    jp_snmpv2c_getTable.add(jl_snmpv2c_getTableEtigetTable,null);
//	    
//	    jtf_snmpv2c_getTablegetTable = new JTextField();
//	    jtf_snmpv2c_getTablegetTable.setBounds(new Rectangle(40,20,334,20));  
//	    jtf_snmpv2c_getTablegetTable.setEditable(true);
//	    jp_snmpv2c_getTable.add(jtf_snmpv2c_getTablegetTable,null);
//	    
//	    jb_snmpv2c_getTablegetTable = new JButton("GetTable");
//	    jb_snmpv2c_getTablegetTable.setBounds(new Rectangle(384,20,90,20));  //9999
//	    jb_snmpv2c_getTablegetTable.setToolTipText("Presione para consultar la tabla.");
//	    jp_snmpv2c_getTable.add(jb_snmpv2c_getTablegetTable,null);
//	    
//	    jsp_snmpv2c_getTablegetTable = new JScrollPane();
//    	jsp_snmpv2c_getTablegetTable.setBounds(new Rectangle(10,50,465,420));  //320,10,435,300
//    	jsp_snmpv2c_getTablegetTable.setWheelScrollingEnabled(true);
//    	jp_snmpv2c_getTable.add(jsp_snmpv2c_getTablegetTable,null);
//    	
//    	/*
//    	JTextArea jta_snmpv2c_getTablegetTable;
//    	jta_snmpv2c_getTablegetTable = new JTextArea();           
//    	jta_snmpv2c_getTablegetTable.setText("");
//    	jta_snmpv2c_getTablegetTable.setEditable(false);
//    	jsp_snmpv2c_getTablegetTable.getViewport().add(jta_snmpv2c_getTablegetTable,null);
//		*/
//
//		jb_snmpv2c_getTablegetTable.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//          	  //aqui lo que hace
//          	  if ((jtf_snmpv2c_getTablegetTable.getText()).equals("")){
//          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//          	  }else{
//          	  	//Tratamiento
//          	  	OID = jtf_snmpv2c_getTablegetTable.getText();
//          	  	
//          	  	if (esVacio(IP)){
//          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
//          	  	}else{        	  	
//          	  	        	  	
//					//Para transformar el OID de string al arreglo para pasarselo al GetNext
//	          	  	//System.out.println("Tratando el oid");
//	          	  	try{
//		          	  	int digitos=0;
//		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
//						while(Token.hasMoreTokens()){
//						  Token.nextToken();
//						  digitos++;	
//						}
//		          	  	//System.out.println("el oid tiene "+digitos+"digitos");
//		          	  	int tamao=digitos;
//		          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
//		          	  	int requerimiento[] = new int[tamao];
//		          	  	int i=0;
//		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
//						while(Token1.hasMoreTokens()){
//						  requerimiento[i]=Integer.parseInt(Token1.nextToken());
//						  i++;
//						  //System.out.println("valor de i: "+i);	
//						}
//		          	  	//requerimiento[i]=0;
//		          	  	//Para la parte de la tabla
//		          	  	getTable traerTabla = new getTable();
//		          	  	traerTabla.cambiarIdiomaAMensajes(erroresGenerales07,erroresGenerales08,erroresGenerales09,erroresGenerales10,erroresGenerales11,erroresGenerales12,erroresGenerales13);
//		          	  	//recorrer.walkSNMPv2c(IP,String.valueOf(pto),comLec,inten,timeOut,requerimiento,limite);
//		          	  	
//		          	  	Vector columnas = traerTabla.getTablev2c(IP,String.valueOf(pto),comLec,inten,timeOut,requerimiento,0);
//		          	  	
//		          	  	Vector names = traerTabla.getNombreColumnas();
//		          	  	traerTabla.limpiarNombreColumnas();
//		          	  	//System.out.println("Resultado del walk: "+traerTabla.getWalkRealizado());
//		          	  	//traerTabla.limpiarWalkRealizado();
//		          	  	//Para la cantidad de columnas
//		          	  	Object[] columnNames = new Object[names.size()];
//		          	  	for (int k=0;k<(names.size());k++){
//      						//System.out.println("Nombre "+(k+1)+": "+String.valueOf(names.get(k)));	
//      						columnNames[k]=String.valueOf(names.get(k));
//     					}
//     					//Para la cantidad de filas
//		          	  	int cantidadFilas = 0;
//		          	  	for (int k1=0;k1<(columnas.size());k1++){
//      						Vector temporalisimo = (Vector)columnas.get(k1);
//      						//System.out.println("Cantidad de filas: "+temporalisimo.size());
//      						cantidadFilas=temporalisimo.size();
//		          	  	}		          	  	
//		          	  	Object[][] rowData = new Object[cantidadFilas][names.size()];
//		          	  	for (int k1=0;k1<(columnas.size());k1++){
//      						Vector temporalisimo = (Vector)columnas.get(k1);
//      						//System.out.println("Cantidad de filas: "+temporalisimo.size());
//     						for (int k3=0;k3<temporalisimo.size();k3++){
//      						  	rowData[k3][k1]=temporalisimo.get(k3);
//      						}
//      						//System.out.println("columnas "+(k1+1)+": "+String.valueOf(columnas.get(k1)));	
//     					}
//     					//rowData[2][1]="aqui es 2,1";
//		          	  	//System.out.println("Cantidad de columnas: "+names.size());
//		          	  	//System.out.println("Cantidad de filas: "+cantidadFilas);
//		          	  	//Para cambiar los names de las columnas por el name del nodo
//		          	  	for (int k4=0;k4<names.size();k4++){
//		          	  	  OID oidTemporalisimo = new OID(String.valueOf(columnNames[k4]));
//		          	  	  
//		          	  	  try{
//		          	  	    Collection listaMibs = (Collection)loadedMibsParaBuscarNombres;	
//		          	  	  	Iterator iteradorListaMibs = listaMibs.iterator();
//              				while (iteradorListaMibs.hasNext()){
//                			  Mib cargada = (Mib)iteradorListaMibs.next();
//                			  //System.out.println("OID es: "+oidTemporalisimo);	
//				        	  //System.out.println("symbol: "+cargada.getSymbolByOid(String.valueOf(oidTemporalisimo)));
//				        	  String symbolEncontrado = String.valueOf(cargada.getSymbolByOid(String.valueOf(oidTemporalisimo)));
//			          	  	  //System.out.println("SYMBOL: ---"+cargada.getSymbolByOid(String.valueOf(oidTemporalisimo))+"---");
//			          	  	  int ini = symbolEncontrado.indexOf(" ");
//				    		  int fin = symbolEncontrado.indexOf(" ",(ini+1));
//				    	      symbolEncontrado=(symbolEncontrado.substring(ini,fin)).trim();
//				              //System.out.println("encontrado: -"+symbolEncontrado+"-");
//				              columnNames[k4]=symbolEncontrado;
//              				}
//		          	  	  }catch(Exception h){
//		          	  	  	//System.out.println("es nulo----------------------------------------------------------------------");
//		          	  	  }
//		          	  	  
//		          	  	}
//		          	  	//Para pintar la tabla
//		          	  	if((cantidadFilas==0)&&((names.size())==0)){
//		          	  	  	//JOptionPane.showMessageDialog(managerSNMP.this,"No se encontraron datos, las causas pueden ser:\n- El OID seleccionado no es una tabla.\n- El agente no tiene datos para la tabla seleccionada.\n- Seleccion el Entry en vez de la tabla como tal.",nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));	
//		          	  	  	JTextArea jta_snmpv2c_getTablegetTable;
//					    	jta_snmpv2c_getTablegetTable = new JTextArea(); 
//					    	String mensajeError="\n".concat(traerTabla.getErrores());          
//					    	traerTabla.limpiarErrores();
//					    	if(!(mensajeError.equals(erroresGenerales05))){
//							  mensajeError=mensajeError.concat("\n");
//					    	  mensajeError=mensajeError.concat(erroresGenerales06);					    		
//					    	}
//					    	jta_snmpv2c_getTablegetTable.setText(mensajeError);
//					    	jta_snmpv2c_getTablegetTable.setEditable(false);
//					    	jsp_snmpv2c_getTablegetTable.getViewport().add(jta_snmpv2c_getTablegetTable,null);
//		          	  	}else{
//			          	  	JTable tablav2c = new JTable(rowData,columnNames);
//			          	  	tablav2c.getTableHeader().setReorderingAllowed(false);
//			          	  	tablav2c.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//			          	  	for (int k5=0;k5<names.size();k5++){
//			          	  	  tablav2c.getColumn(String.valueOf(columnNames[k5])).setPreferredWidth(130);
//			          	  	}
//			          	  	tablav2c.setEnabled(false);
//	    					jsp_snmpv2c_getTablegetTable.getViewport().add(tablav2c,null);
//		          	  	}
//		          	  }catch(NumberFormatException nfe){
//	          	  	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//	          	  	  }
//          	  	}
//          	  }
//         	}
//       	});
//		//GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE--------
//		//****************************************************************************************************************************		
//
//
//		//----------------------------------Pantalla del Walk--------------------------------------
//		jp_snmpv2c_walk= new JPanel();
//	    //jp_snmpv2c_walk.setBackground(Color.white);
//	    jp_snmpv2c_walk.setBounds(new Rectangle(0,30,483,483));
//		jp_snmpv2c_walk.setBorder(BorderFactory.createTitledBorder("Comando Walk"));
//		jp_snmpv2c_walk.setLayout(null);
//		jp_snmpv2c_walk.setVisible(false);
//		snmpv2c.add(jp_snmpv2c_walk,null);
//	
//		jl_snmpv2c_WalkEtiLimitePregunta = new JLabel("Limitar Cantidad de Variables a Consultar");
//	    jl_snmpv2c_WalkEtiLimitePregunta.setBounds(new Rectangle(10,20,236,20));   
//	    jp_snmpv2c_walk.add(jl_snmpv2c_WalkEtiLimitePregunta,null);
//		
//	    jcb_snmpv2c_WalkEtiLimitePregunta = new JComboBox();//USO DEL JCOMBOBOX
//	  	jcb_snmpv2c_WalkEtiLimitePregunta.setBounds(new Rectangle(256,20,50,20));  
//		jcb_snmpv2c_WalkEtiLimitePregunta.addItem(opcionSi);
//	  	jcb_snmpv2c_WalkEtiLimitePregunta.addItem(opcionNo);
//	  	jcb_snmpv2c_WalkEtiLimitePregunta.setSelectedIndex(1);	  	 
//	  	jp_snmpv2c_walk.add(jcb_snmpv2c_WalkEtiLimitePregunta,null);
//
//		jl_snmpv2c_WalkEtiLimite = new JLabel("Cantidad");
//	    jl_snmpv2c_WalkEtiLimite.setBounds(new Rectangle(326,20,50,20));
//	    jl_snmpv2c_WalkEtiLimite.setEnabled(false);
//	    jp_snmpv2c_walk.add(jl_snmpv2c_WalkEtiLimite,null);
//
//		jtf_snmpv2c_WalkEtiLimite = new JTextField();
//	    jtf_snmpv2c_WalkEtiLimite.setBounds(new Rectangle(386,20,89,20));  
//	    jtf_snmpv2c_WalkEtiLimite.setEditable(false);
//	    jtf_snmpv2c_WalkEtiLimite.setText(erroresGenerales21);
//	    jp_snmpv2c_walk.add(jtf_snmpv2c_WalkEtiLimite,null);
//
//	  	jcb_snmpv2c_WalkEtiLimitePregunta.addActionListener(new ActionListener(){
//	      public void actionPerformed(ActionEvent e) {	          
//	        if ((jcb_snmpv2c_WalkEtiLimitePregunta.getSelectedItem())==opcionSi){jtf_snmpv2c_WalkEtiLimite.setEditable(true);jtf_snmpv2c_WalkEtiLimite.setText("");jl_snmpv2c_WalkEtiLimite.setEnabled(true);}
//	        if ((jcb_snmpv2c_WalkEtiLimitePregunta.getSelectedItem())==opcionNo){jtf_snmpv2c_WalkEtiLimite.setEditable(false);jtf_snmpv2c_WalkEtiLimite.setText(erroresGenerales21);jl_snmpv2c_WalkEtiLimite.setEnabled(false);}
//	      }
//	    });
//				
//		jl_snmpv2c_WalkEti = new JLabel("OID");
//	    jl_snmpv2c_WalkEti.setBounds(new Rectangle(10,50,20,20));   
//	    jp_snmpv2c_walk.add(jl_snmpv2c_WalkEti,null);
//
//	    jtf_snmpv2c_WalkOID = new JTextField();
//	    jtf_snmpv2c_WalkOID.setBounds(new Rectangle(40,50,345,20));  
//	    jtf_snmpv2c_WalkOID.setEditable(true);
//	    jp_snmpv2c_walk.add(jtf_snmpv2c_WalkOID,null);
//
//	    jb_snmpv2c_Walk = new JButton("Walk");
//	    jb_snmpv2c_Walk.setBounds(new Rectangle(395,50,80,20));   
//	    jb_snmpv2c_Walk.setToolTipText("Presione para iniciar el Walk.");
//	    jp_snmpv2c_walk.add(jb_snmpv2c_Walk,null);
//	    
//	    jsp_snmpv2c_WalkResp = new JScrollPane();
//    	jsp_snmpv2c_WalkResp.setBounds(new Rectangle(10,80,466,391));   
//    	jsp_snmpv2c_WalkResp.setWheelScrollingEnabled(true);
//    	jp_snmpv2c_walk.add(jsp_snmpv2c_WalkResp,null);
//
//    	jta_snmpv2c_WalkResp = new JTextArea();           
//    	jta_snmpv2c_WalkResp.setText("");
//    	jta_snmpv2c_WalkResp.setEditable(false);
//    	jsp_snmpv2c_WalkResp.getViewport().add(jta_snmpv2c_WalkResp,null);
//	    
//	    jb_snmpv2c_Walk.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//          	  //aqui lo que hace
//          	  jta_snmpv2c_WalkResp.setText(erroresGenerales16);
//          	  if ((jtf_snmpv2c_WalkOID.getText()).equals("")){
//          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//          	    jta_snmpv2c_WalkResp.setText("");
//          	  }else{
//          	  	//Tratamiento
//          	  	OID = jtf_snmpv2c_WalkOID.getText();
//          	  	
//          	  	if (esVacio(IP)){
//          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
//          	  	  jta_snmpv2c_WalkResp.setText("");
//          	  	}else{ 
//          	  		if (((jcb_snmpv2c_WalkEtiLimitePregunta.getSelectedItem())==opcionSi)&&(jtf_snmpv2c_WalkEtiLimite.getText().equals(""))){
//          	  		  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales14,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
//          	  		  jta_snmpv2c_WalkResp.setText("");
//          	  		}else{
//          	  			if (((jcb_snmpv2c_WalkEtiLimitePregunta.getSelectedItem())==opcionSi)&&(!(esNumero(jtf_snmpv2c_WalkEtiLimite.getText())))){
//          	  			  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales15,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
//          	  			  jta_snmpv2c_WalkResp.setText("");
//          	  			}else{
//							//Para transformar el OID de string al arreglo para pasarselo al GetNext
//			          	  	//System.out.println("Tratando el oid");
//			          	  	try{   
//				          	  	int digitos=0;
//				          	  	StringTokenizer Token = new StringTokenizer (OID,".");
//								while(Token.hasMoreTokens()){
//								  Token.nextToken();
//								  digitos++;	
//								}
//				          	  	//System.out.println("el oid tiene "+digitos+"digitos");
//				          	  	int tamao=digitos;
//				          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
//				          	  	int requerimiento[] = new int[tamao];
//				          	  	int i=0;
//				          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
//								while(Token1.hasMoreTokens()){
//								  requerimiento[i]=Integer.parseInt(Token1.nextToken());
//								  i++;
//								  //System.out.println("valor de i: "+i);	
//								}
//				          	  	//requerimiento[i]=0;
//				          	  	jta_snmpv2c_WalkResp.setText(erroresGenerales16);
//				          	  	walk recorrer = new walk();
//				          	  	int limite = 0;
//				          	  	if((jcb_snmpv2c_WalkEtiLimitePregunta.getSelectedItem())==opcionSi){
//				          	  	  limite = Integer.parseInt(jtf_snmpv2c_WalkEtiLimite.getText());	
//				          	  	}else{
// 				          	  	  limite = 0;	
//				          	  	}
//				          	  	recorrer.cambiarIdiomaAMensajes(erroresGenerales07,erroresGenerales08,erroresGenerales09,erroresGenerales10,erroresGenerales11,erroresGenerales12,erroresGenerales13,erroresGenerales17,erroresGenerales18,erroresGenerales19,erroresGenerales20);
//				          	  	recorrer.walkSNMPv2c(IP,String.valueOf(pto),comLec,inten,timeOut,requerimiento,limite);
//								jta_snmpv2c_WalkResp.setText(jta_snmpv2c_WalkResp.getText().concat(recorrer.getWalkRealizado()));
//								recorrer.limpiarWalkRealizado();
//				          	  	
//			          	  	  }catch(NumberFormatException nfe){
//		          	  	    	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//		          	  	    	jta_snmpv2c_WalkResp.setText("");
//		          	  	  	  }
//          	  		  }
//          	  		}
//          	  	}
//          	  }
//         	}
//       	});
//		
//		//----------------------------------Fin de Pantalla del Walk-------------------------------
//			
//
//		//----------------------------------Pantalla de Set----------------------------------------	      	
//	    jp_snmpv2c_Set = new JPanel();
//	    //jp_snmpv2c_Set.setBackground(Color.white);
//		//jp_snmpv2c_Set.setBorder(BorderFactory.createTitledBorder("Set"));
//		jp_snmpv2c_Set.setBounds(new Rectangle(0,30,483,483));
//		jp_snmpv2c_Set.setBorder(BorderFactory.createTitledBorder("Comando Set"));
//		jp_snmpv2c_Set.setLayout(null);
//		jp_snmpv2c_Set.setVisible(false);
//		snmpv2c.add(jp_snmpv2c_Set,null);
//
//    	jsp_snmpv2c_SetDescrip = new JScrollPane();
//    	jsp_snmpv2c_SetDescrip.setBounds(new Rectangle(10,20,465,240));   
//    	jsp_snmpv2c_SetDescrip.setWheelScrollingEnabled(true);
//    	jp_snmpv2c_Set.add(jsp_snmpv2c_SetDescrip,null);
//
//    	jta_snmpv2c_SetDescrip = new JTextArea();           
//    	jta_snmpv2c_SetDescrip.setText("");
//    	jta_snmpv2c_SetDescrip.setEditable(false);
//    	jsp_snmpv2c_SetDescrip.getViewport().add(jta_snmpv2c_SetDescrip,null);
//
//	    jsp_snmpv2c_SetResp = new JScrollPane();
//    	jsp_snmpv2c_SetResp.setBounds(new Rectangle(10,360,465,111));   
//    	jsp_snmpv2c_SetResp.setWheelScrollingEnabled(true);
//    	jp_snmpv2c_Set.add(jsp_snmpv2c_SetResp,null);
//
//    	jta_snmpv2c_SetResp = new JTextArea();
//    	jta_snmpv2c_SetResp.setText("");
//    	jta_snmpv2c_SetResp.setEditable(false);
//    	jsp_snmpv2c_SetResp.getViewport().add(jta_snmpv2c_SetResp,null);
//    	
//    	jl_snmpv2c_SetEtiSet = new JLabel("OID");
//	    jl_snmpv2c_SetEtiSet.setBounds(new Rectangle(10,270,20,20));   
//	    jp_snmpv2c_Set.add(jl_snmpv2c_SetEtiSet,null);
//
//	    jtf_snmpv2c_SetSet = new JTextField();
//	    jtf_snmpv2c_SetSet.setBounds(new Rectangle(50,270,200,20));    
//	    jtf_snmpv2c_SetSet.setEditable(true);
//	    jp_snmpv2c_Set.add(jtf_snmpv2c_SetSet,null);
// 
//    	jl_snmpv2c_SetEtiTipo = new JLabel("Tipo de Dato");
//	    jl_snmpv2c_SetEtiTipo.setBounds(new Rectangle(260,270,80,20));
//	    jl_snmpv2c_SetEtiTipo.setEnabled(false);   
//	    jp_snmpv2c_Set.add(jl_snmpv2c_SetEtiTipo,null);
// 
//	    jcb_snmpv2c_SetTipo = new JComboBox();//USO DEL JCOMBOBOX
//	  	jcb_snmpv2c_SetTipo.setBounds(new Rectangle(339,270,135,20));  // 350,226,70,20
//		jcb_snmpv2c_SetTipo.addItem("---------------------------");
//	  	jcb_snmpv2c_SetTipo.addItem("INTEGER");
//	  	jcb_snmpv2c_SetTipo.addItem("OCTET STRING");
//	  	jcb_snmpv2c_SetTipo.addItem("OBJECT IDENTIFIER");	  	
//	  	jcb_snmpv2c_SetTipo.addItem("IpAddress");
//	  	jcb_snmpv2c_SetTipo.addItem("Counter");
//	  	jcb_snmpv2c_SetTipo.addItem("Counter64");
//	  	jcb_snmpv2c_SetTipo.addItem("Gauge");
//	  	jcb_snmpv2c_SetTipo.addItem("TimeTicks");
//	  	jcb_snmpv2c_SetTipo.addItem("Opaque");
//	  	jcb_snmpv2c_SetTipo.setSelectedIndex(0);	  	 
//	  	jcb_snmpv2c_SetTipo.setEnabled(false);
//	  	jp_snmpv2c_Set.add(jcb_snmpv2c_SetTipo,null);
//
//	  	jcb_snmpv2c_SetTipo.addActionListener(new ActionListener(){
//	      public void actionPerformed(ActionEvent e) {
//	      }
//	    });
//	  		  	
//	    jl_snmpv2c_SetEtiSetValor = new JLabel("Valor");
//	    jl_snmpv2c_SetEtiSetValor.setBounds(new Rectangle(10,300,35,20));
//	    jl_snmpv2c_SetEtiSetValor.setEnabled(false);   
//	    jp_snmpv2c_Set.add(jl_snmpv2c_SetEtiSetValor,null);
//		
//	    jtf_snmpv2c_SetSetValor = new JTextField();
//	    jtf_snmpv2c_SetSetValor.setBounds(new Rectangle(50,300,235,20));    
//	    jtf_snmpv2c_SetSetValor.setEditable(true);
//	    jp_snmpv2c_Set.add(jtf_snmpv2c_SetSetValor,null);	 
//
//      	jb_snmpv2c_setAdd = new JButton("Aadir");
//	    jb_snmpv2c_setAdd.setBounds(new Rectangle(295,300,79,20));  //9999
//	    jb_snmpv2c_setAdd.setToolTipText("Presione para agregar el OID.");
//        jb_snmpv2c_setAdd.setEnabled(false);     //  ***        
//	    jp_snmpv2c_Set.add(jb_snmpv2c_setAdd,null);
//	    
//    	jb_snmpv2c_setUndo = new JButton("Deshacer");
//	    jb_snmpv2c_setUndo.setBounds(new Rectangle(384,300,90,20));  //9999
//	    jb_snmpv2c_setUndo.setToolTipText("Presione para eliminar el ltimo OID de los objetos.");        
//        jb_snmpv2c_setUndo.setEnabled(false);     //  ***
//	    jp_snmpv2c_Set.add(jb_snmpv2c_setUndo,null);
//	    
//   		jl_snmpv2c_setObjs = new JLabel("Objetos");
//	    jl_snmpv2c_setObjs.setBounds(new Rectangle(10,330,60,20));
//	    jp_snmpv2c_Set.add(jl_snmpv2c_setObjs,null);
//	    
//	    jtf_snmpv2c_setObjs = new JTextField();
//	    jtf_snmpv2c_setObjs.setBounds(new Rectangle(60,330,344,20));   
//	    jtf_snmpv2c_setObjs.setEditable(false);	    	    
//	    jp_snmpv2c_Set.add(jtf_snmpv2c_setObjs,null);
//
//    	jb_snmpv2c_SetSet = new JButton("Set");
//	    jb_snmpv2c_SetSet.setBounds(new Rectangle(415,330,59,20));   
//        jb_snmpv2c_SetSet.setEnabled(false);
//        jtf_snmpv2c_SetSetValor.setEnabled(false); 	 
//        jtf_snmpv2c_SetSetValor.setText(""); 		 
//	    jb_snmpv2c_SetSet.setToolTipText("Presione para especificar el valor.");
//	    jp_snmpv2c_Set.add(jb_snmpv2c_SetSet,null);
//
//		//AQUI DEBE DE IR EL EVENTO DE SETEAR UNA VARIABLE VIA SNMP
//		
//		//TIPOS DE DATOS DEL SET
//       	//tipoDatoReconocido = "INTEGER";
//        //tipoDatoReconocido = "OCTET STRING";	
//        //tipoDatoReconocido = "OBJECT IDENTIFIER";
//        //tipoDatoReconocido = "IPADDRESS";
//        //tipoDatoReconocido = "COUNTER";
//        //tipoDatoReconocido = "GAUGE";
//        //tipoDatoReconocido = "TIMETICKS";
//        //tipoDatoReconocido = "OPAQUE";
//        //tipoDatoReconocido = "COUNTER64";
//        //tipoDatoReconocido="NO RECONOCIDO";
//        //reconocido
//
//		compuestoSetSNMPv2cTempOID = new Vector();
//		compuestoSetSNMPv2cTempTipoDatos = new Vector();
//		compuestoSetSNMPv2cTempDatos = new Vector();
//
//		//Para aadir
//		jb_snmpv2c_setAdd.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//        
//        	  //aqui lo que hace
//          	  if ((jtf_snmpv2c_SetSet.getText()).equals("")){
//          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//          	  }else{
//          	  	//Tratamiento
//          	  	OID = jtf_snmpv2c_SetSet.getText();
//          	  	
//          	  	if (esVacio(IP)){
//          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
//          	  	}else{ 
//          	  		
//          	  		if ((jtf_snmpv2c_SetSetValor.getText()).equals("")){
//          	  		  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales22,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
//          	  		}else{
//          	  			if ((jcb_snmpv2c_SetTipo.getSelectedItem()).equals("---------------------------")){
//          	  			  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales23,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
//          	  			}else{
//							//Para transformar el OID de string al arreglo para pasarselo al GetNext
//			          	  	//System.out.println("Tratando el oid");
//			          	  	try{   
//				          	  	int digitos=0;
//				          	  	StringTokenizer Token = new StringTokenizer (OID,".");
//								while(Token.hasMoreTokens()){
//								  Token.nextToken();
//								  digitos++;	
//								}
//				          	  	//System.out.println("el oid tiene "+digitos+"digitos");
//				          	  	int tamao=digitos;
//				          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
//				          	  	int requerimiento[] = new int[tamao];
//				          	  	int i=0;
//				          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
//								while(Token1.hasMoreTokens()){
//								  requerimiento[i]=Integer.parseInt(Token1.nextToken());
//								  i++;
//								  //System.out.println("valor de i: "+i);	
//								}
//				          	  	//requerimiento[i]=0;
//				          	  	
//				          	  	Variable valor = null;
//				          	  	
//				          	  	if(reconocido){
//				          	  		boolean datoInvalido=false;
//				          	  		
//					          	  		tipoDatoReconocido=jcb_snmpv2c_SetTipo.getSelectedItem().toString();
//					          	  		try{
//					          	  			if(tipoDatoReconocido.equals("INTEGER")){valor = new Integer32(Integer.parseInt(String.valueOf(jtf_snmpv2c_SetSetValor.getText())));}
//					    					if(tipoDatoReconocido.equals("Gauge")){valor = new Gauge32(Long.parseLong(String.valueOf(jtf_snmpv2c_SetSetValor.getText())));}
//										    if(tipoDatoReconocido.equals("IpAddress")){valor = new IpAddress(String.valueOf(jtf_snmpv2c_SetSetValor.getText()));}
//										    if(tipoDatoReconocido.equals("OBJECT IDENTIFIER")){valor = new OID(String.valueOf(jtf_snmpv2c_SetSetValor.getText()));}
//										    if(tipoDatoReconocido.equals("TimeTicks")){valor = new TimeTicks(Long.parseLong(String.valueOf(jtf_snmpv2c_SetSetValor.getText())));}
//										    if(tipoDatoReconocido.equals("Counter")){valor = new Counter32(Long.parseLong(String.valueOf(jtf_snmpv2c_SetSetValor.getText())));}
//										    if(tipoDatoReconocido.equals("Counter64")){valor = new Counter64(Long.parseLong(String.valueOf(jtf_snmpv2c_SetSetValor.getText())));}
//										    if(tipoDatoReconocido.equals("Opaque")){valor = new Opaque(String.valueOf(jtf_snmpv2c_SetSetValor.getText()).getBytes());}
//										    if(tipoDatoReconocido.equals("OCTET STRING")){valor = new OctetString(String.valueOf(jtf_snmpv2c_SetSetValor.getText()));}
//					          	  		}catch(Exception e4){datoInvalido=true;}
//				          	  		
//				          	  		if(!(datoInvalido)){
//				          	  			
//				          	  			compuestoSetSNMPv2cTempOID.add(new OID(requerimiento));
//				          	  			compuestoSetSNMPv2cTempTipoDatos.add(tipoDatoReconocido);
//				          	  			compuestoSetSNMPv2cTempDatos.add(String.valueOf(valor));
//				          	  			
//				          	  			String contenido = "";
//								        for (int pp=0;pp<compuestoSetSNMPv2cTempOID.size();pp++){
//								          contenido=contenido.concat(compuestoSetSNMPv2cTempOID.get(pp)+"; ");	
//								        }
//								        jtf_snmpv2c_setObjs.setText(contenido);
//						          	  	
//				          	  		}else{
//				          	  		  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales24,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));				
//				          	  		}
//		          	  			}else{
//		          	  				JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales25,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));			
//		          	  			}
//			          	  	  }catch(NumberFormatException nfe){
//		          	  	    	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//		          	  	  	  }
//          	  		  }
//          	  		}
//          	  	}
//          	  }
//         	
//        		
//        	}
//        }); 
//		
//		//Para eliminar
//		jb_snmpv2c_setUndo.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//        
//        	  int ultimo = compuestoSetSNMPv2cTempOID.size();
//        	  ultimo=ultimo-1;
//        	  if (ultimo>=0){
//        	    compuestoSetSNMPv2cTempOID.removeElementAt(ultimo);
//        	    compuestoSetSNMPv2cTempTipoDatos.removeElementAt(ultimo);
//        	    compuestoSetSNMPv2cTempDatos.removeElementAt(ultimo);
//        	  }else{
//        	  	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//        	  }
//    	  	
//		      String contenido = "";
//		      for (int pp=0;pp<compuestoSetSNMPv2cTempOID.size();pp++){
//		        contenido=contenido.concat(compuestoSetSNMPv2cTempOID.get(pp)+"; ");	
//		      }
//		      jtf_snmpv2c_setObjs.setText(contenido);
//        		
//        	}
//        });
//
//
//				
//		//EVENTO PARA HACER QUE AL INTRODUCIR EL OID DEL SET SE ACTIVE EL TIPO DE DATOS, VALOR Y BOTON SET
//		jtf_snmpv2c_SetSetDigitos = 0;
//		jtf_snmpv2c_SetSet.getDocument().addDocumentListener(new DocumentListener(){
//		  public void insertUpdate(DocumentEvent e) {
//            //System.out.println("escribi, valor: "+jtf_snmpv1_SetSetDigitos);
//            jtf_snmpv2c_SetSetDigitos++;
//            if (jtf_snmpv2c_SetSetDigitos==2){
//              //System.out.println("active"); 
//              reconocido=true;	
//              jcb_snmpv2c_SetTipo.setEnabled(true);//combobox
//              jb_snmpv2c_SetSet.setEnabled(true); //boton
//              jtf_snmpv2c_SetSetValor.setEnabled(true); //valor	
//              jl_snmpv2c_SetEtiTipo.setEnabled(true);
//              jl_snmpv2c_SetEtiSetValor.setEnabled(true);
//        	  jb_snmpv2c_setAdd.setEnabled(true);     //  ***
//        	  jb_snmpv2c_setUndo.setEnabled(true);     //  ***                 
//            }
//          }
//          public void removeUpdate(DocumentEvent e) {
//            //System.out.println("borre, valor: "+jtf_snmpv1_SetSetDigitos);
//            jtf_snmpv2c_SetSetDigitos=0;
//          }
//          public void changedUpdate(DocumentEvent e) {
//            //Plain text components don't fire these events.
//          }
//		});
//		
//		jb_snmpv2c_SetSet.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//          	  //aqui lo que hace
//          	  if ((jtf_snmpv2c_SetSet.getText()).equals("")){
//          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//          	  }else{
//          	  	//Tratamiento
//          	  	OID = jtf_snmpv2c_SetSet.getText();
//          	  	
//          	  	if (esVacio(IP)){
//          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
//          	  	}else{ 
//          	  		
//          	  		if ((jtf_snmpv2c_SetSetValor.getText()).equals("")){
//          	  		  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales22,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
//          	  		}else{
//          	  			if ((jcb_snmpv2c_SetTipo.getSelectedItem()).equals("---------------------------")){
//          	  			  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales23,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
//          	  			}else{
//							//Para transformar el OID de string al arreglo para pasarselo al GetNext
//			          	  	//System.out.println("Tratando el oid");
//			          	  	try{   
//				          	  	int digitos=0;
//				          	  	StringTokenizer Token = new StringTokenizer (OID,".");
//								while(Token.hasMoreTokens()){
//								  Token.nextToken();
//								  digitos++;	
//								}
//				          	  	//System.out.println("el oid tiene "+digitos+"digitos");
//				          	  	int tamao=digitos;
//				          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
//				          	  	int requerimiento[] = new int[tamao];
//				          	  	int i=0;
//				          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
//								while(Token1.hasMoreTokens()){
//								  requerimiento[i]=Integer.parseInt(Token1.nextToken());
//								  i++;
//								  //System.out.println("valor de i: "+i);	
//								}
//				          	  	//requerimiento[i]=0;
//				          	  	
//				          	  	boolean esCompuesto=false;
//				          	  	compuestoSetSNMPv2c = new Vector();
//				          	  	if((compuestoSetSNMPv2cTempOID.size())>=1){
//				          	  	   compuestoSetSNMPv2c=compuestoSetSNMPv2cTempOID;
//				          	  	   esCompuesto=true;	
//				          	  	   reconocido=true;
//				          	  	}else{
//				          	  	   compuestoSetSNMPv2c.add(new OID(requerimiento));
//				          	  	   esCompuesto=false;
//				          	  	}
//				          	  	
//				          	  	SNMPv2c manager = new SNMPv2c();
//				          	  	
//				          	  	Variable valor = null;
//				          	  	
//				          	  	if(reconocido){
//				          	  		boolean datoInvalido=false;
//				          	  		if (!esCompuesto){
//					          	  		tipoDatoReconocido=jcb_snmpv2c_SetTipo.getSelectedItem().toString();
//					          	  		try{
//					          	  			if(tipoDatoReconocido.equals("INTEGER")){valor = new Integer32(Integer.parseInt(String.valueOf(jtf_snmpv2c_SetSetValor.getText())));}
//					    					if(tipoDatoReconocido.equals("Gauge")){valor = new Gauge32(Long.parseLong(String.valueOf(jtf_snmpv2c_SetSetValor.getText())));}
//										    if(tipoDatoReconocido.equals("IpAddress")){valor = new IpAddress(String.valueOf(jtf_snmpv2c_SetSetValor.getText()));}
//										    if(tipoDatoReconocido.equals("OBJECT IDENTIFIER")){valor = new OID(String.valueOf(jtf_snmpv2c_SetSetValor.getText()));}
//										    if(tipoDatoReconocido.equals("TimeTicks")){valor = new TimeTicks(Long.parseLong(String.valueOf(jtf_snmpv2c_SetSetValor.getText())));}
//										    if(tipoDatoReconocido.equals("Counter")){valor = new Counter32(Long.parseLong(String.valueOf(jtf_snmpv2c_SetSetValor.getText())));}
//										    if(tipoDatoReconocido.equals("Counter64")){valor = new Counter64(Long.parseLong(String.valueOf(jtf_snmpv2c_SetSetValor.getText())));}
//										    if(tipoDatoReconocido.equals("Opaque")){valor = new Opaque(String.valueOf(jtf_snmpv2c_SetSetValor.getText()).getBytes());}
//										    if(tipoDatoReconocido.equals("OCTET STRING")){valor = new OctetString(String.valueOf(jtf_snmpv2c_SetSetValor.getText()));}
//					          	  		}catch(Exception e4){datoInvalido=true;}
//				          	  		}
//				          	  		if(!(datoInvalido)){
//				          	  			
//				          	  			if (!esCompuesto){				          	  			
//				          	  			  compuestoSetSNMPv2cTempTipoDatos.add(tipoDatoReconocido);
//				          	  			  compuestoSetSNMPv2cTempDatos.add(String.valueOf(valor));
//				          	  			}
//				          	  			
//				          	  			compuestoSetSNMPv2cValores = new Variable[compuestoSetSNMPv2c.size()];
//				          	  			
//				          	  			for (int pp=0;pp<compuestoSetSNMPv2c.size();pp++){
//					          	  			if(((compuestoSetSNMPv2cTempTipoDatos.get(pp)).toString()).equals("INTEGER")){compuestoSetSNMPv2cValores[pp] = new Integer32(Integer.parseInt(String.valueOf(compuestoSetSNMPv2cTempDatos.get(pp))));}
//					    					if(((compuestoSetSNMPv2cTempTipoDatos.get(pp)).toString()).equals("Gauge")){compuestoSetSNMPv2cValores[pp] = new Gauge32(Long.parseLong(String.valueOf(compuestoSetSNMPv2cTempDatos.get(pp))));}
//										    if(((compuestoSetSNMPv2cTempTipoDatos.get(pp)).toString()).equals("IpAddress")){compuestoSetSNMPv2cValores[pp] = new IpAddress(String.valueOf(compuestoSetSNMPv2cTempDatos.get(pp)));}
//										    if(((compuestoSetSNMPv2cTempTipoDatos.get(pp)).toString()).equals("OBJECT IDENTIFIER")){compuestoSetSNMPv2cValores[pp] = new OID(String.valueOf(compuestoSetSNMPv2cTempDatos.get(pp)));}
//										    if(((compuestoSetSNMPv2cTempTipoDatos.get(pp)).toString()).equals("TimeTicks")){compuestoSetSNMPv2cValores[pp] = new TimeTicks(Long.parseLong(String.valueOf(compuestoSetSNMPv2cTempDatos.get(pp))));}
//										    if(((compuestoSetSNMPv2cTempTipoDatos.get(pp)).toString()).equals("Counter64")){compuestoSetSNMPv2cValores[pp] = new Counter64(Long.parseLong(String.valueOf(compuestoSetSNMPv2cTempDatos.get(pp))));}
//										    if(((compuestoSetSNMPv2cTempTipoDatos.get(pp)).toString()).equals("Counter")){compuestoSetSNMPv2cValores[pp] = new Counter32(Long.parseLong(String.valueOf(compuestoSetSNMPv2cTempDatos.get(pp))));}
//										    if(((compuestoSetSNMPv2cTempTipoDatos.get(pp)).toString()).equals("Opaque")){compuestoSetSNMPv2cValores[pp] = new Opaque(String.valueOf(compuestoSetSNMPv2cTempDatos.get(pp)).getBytes());}
//										    if(((compuestoSetSNMPv2cTempTipoDatos.get(pp)).toString()).equals("OCTET STRING")){compuestoSetSNMPv2cValores[pp] = new OctetString(String.valueOf(compuestoSetSNMPv2cTempDatos.get(pp)));}
//				          	  				
//				          	  			}
//				          	  			
//				          	  			String respuesta = manager.setv2c(IP,  String.valueOf(pto), comEsc, inten, timeOut,compuestoSetSNMPv2c, compuestoSetSNMPv2cValores,erroresGenerales04);
//				          	  			
//				          	  			compuestoSetSNMPv2c.removeAllElements();
//		    							compuestoSetSNMPv2cTempOID.removeAllElements();
//		        	    				compuestoSetSNMPv2cTempTipoDatos.removeAllElements();
//		        	    				compuestoSetSNMPv2cTempDatos.removeAllElements();
//								        compuestoSetSNMPv2cValores=null;
//								        jtf_snmpv2c_setObjs.setText("");
//				          	  			
//						          	  	//System.out.println("Setv2c: "+respuesta);
//						          	  	if (respuesta.equals(erroresGenerales04)){
//						          	  	  respuesta=("Set SNMPv2c: ").concat(respuesta);
//						          	  	}else{
//						          	  	  respuesta=("Set SNMPv2c: ").concat(respuesta);	
//						          	  	}
//						          	  	respuesta=respuesta.concat("\n");
//						          	  	jta_snmpv2c_SetResp.setText((jta_snmpv2c_SetResp.getText()).concat(respuesta));
//						          	  	
//				          	  		}else{
//				          	  		  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales24,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));				
//				          	  		}
//		          	  			}else{
//		          	  				JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales25,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));			
//		          	  			}
//			          	  	  }catch(NumberFormatException nfe){
//		          	  	    	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
//		          	  	  	  }
//          	  		  }
//          	  		}
//          	  	}
//          	  }
//         	}
//       	});
//		

	    //----------------------------------Fin de Pantalla de Set---------------------------------
	    
	    
	    	
	    //----------------------------------Pantalla de Traps--------------------------------------  	
	    //boolean errorPtoTrap = false;
	    /*try{
	      traps = new recibirTrapInform();
	      traps.run();
	    }catch(Exception tp){errorPtoTrap = true;}*/
	    
//	    jp_snmpv2c_Traps = new JPanel();
//	    //jp_snmpv2c_Traps.setBackground(Color.yellow);
//		//jp_snmpv2c_Traps.setBorder(BorderFactory.createTitledBorder("Traps"));
//		jp_snmpv2c_Traps.setBounds(new Rectangle(0,30,483,253));      //0,30,483,283
//		jp_snmpv2c_Traps.setBorder(BorderFactory.createTitledBorder("Recepcin de TRAPs SNMP v1/2c/3 e INFORMs SNMP v2c/3"));   //  "Enviar/Ver Traps"   
//		jp_snmpv2c_Traps.setLayout(null);
//		jp_snmpv2c_Traps.setVisible(false);
//		snmpv2c.add(jp_snmpv2c_Traps,null);
//		
//	    jsp_snmpv2c_traps = new JScrollPane();//USO DEL JSCROLLPANE
//	    jsp_snmpv2c_traps.setBounds(new Rectangle(10,20,465,220)); //10,20,465,250
//	    jsp_snmpv2c_traps.setWheelScrollingEnabled(true);
//	    jp_snmpv2c_Traps.add(jsp_snmpv2c_traps,null);
//	    
//	    jta_snmpv2c_traps = new JTextArea();//USO DEL JTEXTAREA
//	    jta_snmpv2c_traps.setEditable(false);
//	    jsp_snmpv2c_traps.getViewport().add(jta_snmpv2c_traps,null);
//	    
//	    jp_snmpv2c_TrapsSend = new JPanel();
//	    //jp_snmpv2c_TrapsSend.setBackground(Color.white);
//		//jp_snmpv2c_TrapsSend.setBorder(BorderFactory.createTitledBorder("TrapsSend"));
//		jp_snmpv2c_TrapsSend.setBounds(new Rectangle(0,283,483,223));  //0,313,483,193
//		jp_snmpv2c_TrapsSend.setBorder(BorderFactory.createTitledBorder("Envo de TRAPs / INFORMs SNMP v2c"));
//		jp_snmpv2c_TrapsSend.setLayout(null);
//		jp_snmpv2c_TrapsSend.setVisible(false);
//		snmpv2c.add(jp_snmpv2c_TrapsSend,null);
//
//	    jl_snmpv2c_TrapSndHost = new JLabel("Direccin IP del destino");
//	    jl_snmpv2c_TrapSndHost.setBounds(new Rectangle(10,30,150,20));    
//	    jp_snmpv2c_TrapsSend.add(jl_snmpv2c_TrapSndHost,null);
//
//	    jtf_snmpv2c_TrapSndHostIP = new JTextField();
//	    jtf_snmpv2c_TrapSndHostIP.setBounds(new Rectangle(150,30,150,20));    
//	    //jtf_snmpv2c_TrapSndHostIP.setEditable(true);
//	    jp_snmpv2c_TrapsSend.add(jtf_snmpv2c_TrapSndHostIP,null);	 
//
//	    jl_snmpv2c_TrapSndTipo = new JLabel("Tipo de Trap");
//	    jl_snmpv2c_TrapSndTipo.setBounds(new Rectangle(10,80,150,20));    
//	    jp_snmpv2c_TrapsSend.add(jl_snmpv2c_TrapSndTipo,null);
//		
//	    jcb_snmpv2c_TrapSel = new JComboBox();//USO DEL JCOMBOBOX
//	  	jcb_snmpv2c_TrapSel.setBounds(new Rectangle(150,80,150,20));  
//		jcb_snmpv2c_TrapSel.addItem("coldStart");
//	  	jcb_snmpv2c_TrapSel.addItem("warmStart");
//	  	jcb_snmpv2c_TrapSel.addItem("linkDown");
//	  	jcb_snmpv2c_TrapSel.addItem("linkUp");
//	  	jcb_snmpv2c_TrapSel.addItem("authenticationFailure");	  	
//	  	jcb_snmpv2c_TrapSel.addItem("Otro");	  	
//	  	//jcb_snmpv1_sel.setMaximumRowCount(2);
//	  	jp_snmpv2c_TrapsSend.add(jcb_snmpv2c_TrapSel,null);
//
//	  	jcb_snmpv2c_TrapSel.addActionListener(new ActionListener(){
//	      public void actionPerformed(ActionEvent e) {	        
//	        //jtf_snmpv2c_EntTxt.setText("");
//	        if ((jcb_snmpv2c_TrapSel.getSelectedItem())==erroresGenerales46){
//	          jl_snmpv2c_Enter.setEnabled(true);
//	          jtf_snmpv2c_EntTxt.setEditable(true);
//	          jl_snmpv2c_OtroTrp.setEnabled(true);
//	          jtf_snmpv2c_OtroTrp.setEditable(true);
//	          jl_snmpv2c_Descr.setEnabled(true);
//	          jtf_snmpv2c_Descr.setEditable(true);
//	          jtf_snmpv2c_EntTxt.setText("1.3.6.1.4.1.2789.2005");
//	          jtf_snmpv2c_OtroTrp.setText("1.3.6.1.4.1.2789.2005.1");
//	          jtf_snmpv2c_Descr.setText("WWW Server Has Been Restarted");
//	  		  jl_snmpv2c_TpoDtoTrp.setEnabled(true); 
//	  		  jcb_snmpv2c_TpoDtoTrp.setEnabled(true);
//	  		  jcb_snmpv2c_TpoDtoTrp.setSelectedIndex(1);
//	          
//	        }else{
//	          jl_snmpv2c_Enter.setEnabled(false);
//	          jtf_snmpv2c_EntTxt.setEditable(false);
//	          jl_snmpv2c_OtroTrp.setEnabled(false);
//	          jtf_snmpv2c_OtroTrp.setEditable(false);
//	          jl_snmpv2c_Descr.setEnabled(false);
//	          jtf_snmpv2c_Descr.setEditable(false);
//	          jtf_snmpv2c_EntTxt.setText("");
//	          jtf_snmpv2c_OtroTrp.setText("");
//	          jtf_snmpv2c_Descr.setText("");
//	  		  jl_snmpv2c_TpoDtoTrp.setEnabled(false); 
//	  		  jcb_snmpv2c_TpoDtoTrp.setEnabled(false);
//	  		  jcb_snmpv2c_TpoDtoTrp.setSelectedIndex(0);}
//	        }
//	    });
//		
//		jl_snmpv2c_PtoCom = new JLabel("Puerto");
//	    jl_snmpv2c_PtoCom.setBounds(new Rectangle(310,30,50,20));    
//	    jp_snmpv2c_TrapsSend.add(jl_snmpv2c_PtoCom,null);
//
//	    jtf_snmpv2c_PtoComTxt = new JTextField();
//	    jtf_snmpv2c_PtoComTxt.setBounds(new Rectangle(390,30,85,20));    
//	    //jtf_snmpv2c_PtoComTxt.setEditable(true);
//	    jtf_snmpv2c_PtoComTxt.setText("162");
//	    jp_snmpv2c_TrapsSend.add(jtf_snmpv2c_PtoComTxt,null);	 
//	    	
//		jl_snmpv2c_Com = new JLabel("Comunidad");
//	    jl_snmpv2c_Com.setBounds(new Rectangle(310,55,150,20));    
//	    jp_snmpv2c_TrapsSend.add(jl_snmpv2c_Com,null);
//
//	    jtf_snmpv2c_ComTxt = new JTextField();
//	    jtf_snmpv2c_ComTxt.setBounds(new Rectangle(390,55,85,20));    
//	    //jtf_snmpv2c_ComTxt.setEditable(true);
//	    jtf_snmpv2c_ComTxt.setText("public");
//	    jp_snmpv2c_TrapsSend.add(jtf_snmpv2c_ComTxt,null);	 
//	    	
//		jl_snmpv2c_Int = new JLabel("Intentos");
//	    jl_snmpv2c_Int.setBounds(new Rectangle(310,80,200,20));    
//	    jp_snmpv2c_TrapsSend.add(jl_snmpv2c_Int,null);
//
//	    jtf_snmpv2c_IntTxt = new JTextField();
//	    jtf_snmpv2c_IntTxt.setBounds(new Rectangle(390,80,85,20));    
//	    //jtf_snmpv2c_IntTxt.setEditable(true);
//	    jtf_snmpv2c_IntTxt.setText("3");
//	    jp_snmpv2c_TrapsSend.add(jtf_snmpv2c_IntTxt,null);	 
//	    
//		jl_snmpv2c_Timeout = new JLabel("TimeOut (ms)");
//	    jl_snmpv2c_Timeout.setBounds(new Rectangle(310,105,150,20));    
//	    jp_snmpv2c_TrapsSend.add(jl_snmpv2c_Timeout,null);
//
//	    jtf_snmpv2c_TmoutTxt = new JTextField();
//	    jtf_snmpv2c_TmoutTxt.setBounds(new Rectangle(390,105,85,20));
//	    jtf_snmpv2c_TmoutTxt.setText("1500");    
//	    //jtf_snmpv2c_TmoutTxt.setEditable(true);
//	    jp_snmpv2c_TrapsSend.add(jtf_snmpv2c_TmoutTxt,null);	 
// 	    	
//		jl_snmpv2c_Enter = new JLabel("Enterprise OID");
//	    jl_snmpv2c_Enter.setBounds(new Rectangle(10,105,150,20));  
//	    jl_snmpv2c_Enter.setEnabled(false);
//	    jp_snmpv2c_TrapsSend.add(jl_snmpv2c_Enter,null);
//
//	    jtf_snmpv2c_EntTxt = new JTextField();
//	    jtf_snmpv2c_EntTxt.setBounds(new Rectangle(150,105,150,20));	 
//	    jtf_snmpv2c_EntTxt.setEditable(false);
//	    jp_snmpv2c_TrapsSend.add(jtf_snmpv2c_EntTxt,null);	 
			
/*		jl_snmpv2c_TrapSpc = new JLabel("Trap Especifico");
	    jl_snmpv2c_TrapSpc.setBounds(new Rectangle(10,85,150,20));    
	    jp_snmpv2c_TrapsSend.add(jl_snmpv2c_TrapSpc,null);

	    jtf_snmpv2c_TrapSpcTxt = new JTextField();
	    jtf_snmpv2c_TrapSpcTxt.setBounds(new Rectangle(150,90,150,20));    
	    jtf_snmpv2c_TrapSpcTxt.setEditable(false);
	    jp_snmpv2c_TrapsSend.add(jtf_snmpv2c_TrapSpcTxt,null);	    */
//
//		jl_snmpv2c_TrpInfSel = new JLabel("Seleccione una opcin");
//	    jl_snmpv2c_TrpInfSel.setBounds(new Rectangle(10,55,150,20)); 
//	    jp_snmpv2c_TrapsSend.add(jl_snmpv2c_TrpInfSel,null);
//	    
//	    jcb_snmpv2c_TrpInfSel = new JComboBox();//USO DEL JCOMBOBOX		 
//	  	jcb_snmpv2c_TrpInfSel.setBounds(new Rectangle(150,55,150,20));  
//		jcb_snmpv2c_TrpInfSel.addItem("Trap");
//	  	jcb_snmpv2c_TrpInfSel.addItem("Inform");
//	  	//jcb_snmpv1_sel.setMaximumRowCount(2);
//	  	jp_snmpv2c_TrapsSend.add(jcb_snmpv2c_TrpInfSel,null);
//
//	  	jcb_snmpv2c_TrpInfSel.addActionListener(new ActionListener(){
//	      public void actionPerformed(ActionEvent e) {	        
//	        if ((jcb_snmpv2c_TrpInfSel.getSelectedItem())==erroresGenerales47){
//	        	jb_SndTrapv2c.setText(erroresGenerales49);
//	        	jb_SndTrapv2c.setToolTipText(erroresGenerales51);
//	        	jl_snmpv2c_OtroTrp.setText(erroresGenerales50);
//	        	
//	  			jcb_snmpv2c_TrapSel.setSelectedItem(erroresGenerales46);
//	  			jl_snmpv2c_TrapSndTipo.setEnabled(false);
//	  			jcb_snmpv2c_TrapSel.setEnabled(false);
//	        	
//	        } 
//	        if ((jcb_snmpv2c_TrpInfSel.getSelectedItem())==erroresGenerales45){
//	        	jb_SndTrapv2c.setText(erroresGenerales52);
//	        	jb_SndTrapv2c.setToolTipText(erroresGenerales54);
//	        	jl_snmpv2c_OtroTrp.setText(erroresGenerales53);
//	        	
//	        	jcb_snmpv2c_TrapSel.setSelectedItem("coldStart");
//	        	jl_snmpv2c_TrapSndTipo.setEnabled(true);
//	        	jcb_snmpv2c_TrapSel.setEnabled(true);	        	
//	        	
//	        } 	
//	      }
//	    });
//
//		jl_snmpv2c_OtroTrp = new JLabel("Trap OID");
//	    jl_snmpv2c_OtroTrp.setBounds(new Rectangle(10,130,150,20));
//	    jl_snmpv2c_OtroTrp.setEnabled(false);  	           
//	    jp_snmpv2c_TrapsSend.add(jl_snmpv2c_OtroTrp,null);
//
//	    jtf_snmpv2c_OtroTrp = new JTextField();
//	    jtf_snmpv2c_OtroTrp.setBounds(new Rectangle(150,130,150,20));	 
//	    //jtf_snmpv2c_OtroTrp.setText("1.3.6.1.4.1.2854");
//	    jtf_snmpv2c_OtroTrp.setEditable(false);		    
//	    jp_snmpv2c_TrapsSend.add(jtf_snmpv2c_OtroTrp,null);	 
//
//		jl_snmpv2c_Descr = new JLabel("Descripcin");
//	    jl_snmpv2c_Descr.setBounds(new Rectangle(10,155,150,20));
//	    jl_snmpv2c_Descr.setEnabled(false);  	           
//	    jp_snmpv2c_TrapsSend.add(jl_snmpv2c_Descr,null);
//
//	    jtf_snmpv2c_Descr = new JTextField();
//	    jtf_snmpv2c_Descr.setBounds(new Rectangle(150,155,150,20));	 
//	    //jtf_snmpv2c_OtroTrp.setText("1.3.6.1.4.1.2854");
//	    jtf_snmpv2c_Descr.setEditable(false);		    
//	    jp_snmpv2c_TrapsSend.add(jtf_snmpv2c_Descr,null);	 
//
//		jl_snmpv2c_TpoDtoTrp = new JLabel("Tipo de Dato");
//	    jl_snmpv2c_TpoDtoTrp.setBounds(new Rectangle(10,180,150,20));
//	    jl_snmpv2c_TpoDtoTrp.setEnabled(false);  	           
//	    jp_snmpv2c_TrapsSend.add(jl_snmpv2c_TpoDtoTrp,null);
//
//	    jcb_snmpv2c_TpoDtoTrp = new JComboBox();//USO DEL JCOMBOBOX		 
//	  	jcb_snmpv2c_TpoDtoTrp.setBounds(new Rectangle(150,180,150,20));  
//		jcb_snmpv2c_TpoDtoTrp.addItem("-------------------------------");
//	  	jcb_snmpv2c_TpoDtoTrp.addItem("OCTET STRING");	  	
//	  	jcb_snmpv2c_TpoDtoTrp.addItem("INTEGER");
//	  	jcb_snmpv2c_TpoDtoTrp.addItem("OBJECT IDENTIFIER");	  	
//	  	jcb_snmpv2c_TpoDtoTrp.addItem("IpAddress");
//	  	//jcb_snmpv2c_TpoDtoTrp.addItem("Counter");
//	  	//jcb_snmpv2c_TpoDtoTrp.addItem("Gauge");
//	  	jcb_snmpv2c_TpoDtoTrp.addItem("TimeTicks");
//	  	//jcb_snmpv2c_TpoDtoTrp.addItem("Opaque");
//	  	//jcb_snmpv2c_TpoDtoTrp.addItem("Counter64");
//	  	//jcb_snmpv2c_TpoDtoTrp.setMaximumRowCount(2);
//	  	jcb_snmpv2c_TpoDtoTrp.setEnabled(false);
//	  	jp_snmpv2c_TrapsSend.add(jcb_snmpv2c_TpoDtoTrp,null);
//
//	    jb_SndTrapv2c = new JButton("Enviar TRAP");
//	    jb_SndTrapv2c.setBounds(new Rectangle(310,155,165,20));   //155,150,150,30
//	    jb_SndTrapv2c.setToolTipText("Presione para enviar el TRAP.");
//	    jp_snmpv2c_TrapsSend.add(jb_SndTrapv2c,null);
//	    
//    	jb_SndTrapv2c.addActionListener(new ActionListener(){ 
//        	public void actionPerformed(ActionEvent e) {  
//	          	String men_err = "";
//	          	boolean men_err_l = false;
// 
//          		if (esVacio(jtf_snmpv2c_TrapSndHostIP.getText())){          			
//          		  men_err = men_err.concat(erroresGenerales36);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          	  	if (!(ipValida(jtf_snmpv2c_TrapSndHostIP.getText()))){
//          	  	  men_err = men_err.concat(configParamError02);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          		if (!(esNumero(jtf_snmpv2c_PtoComTxt.getText()))){          			
//          		  men_err = men_err.concat(configParamError03);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          	  	if (esVacio(jtf_snmpv2c_ComTxt.getText())){          			
//          		  men_err = men_err.concat(erroresGenerales37);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          		if (!(esNumero(jtf_snmpv2c_IntTxt.getText()))){          			
//          		  men_err = men_err.concat(configParamError06);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          		if (!(esNumero(jtf_snmpv2c_TmoutTxt.getText()))){          			
//          		  men_err = men_err.concat(configParamError08);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          	  	if ((jcb_snmpv2c_TrapSel.getSelectedItem())==erroresGenerales46){
//          	  		//jtf_snmpv2c_EntTxt
//          	  		if (esVacio(jtf_snmpv2c_EntTxt.getText())){          			
//          		  	  men_err = men_err.concat(erroresGenerales33);	 	
//          	  	  	  men_err_l =true;
//          	  		}
//          	  		if (!(esOID(jtf_snmpv2c_EntTxt.getText()))){          			
//	          		  men_err = men_err.concat(erroresGenerales34);	 	
//	          	  	  men_err_l =true;
//	          	  	}	
//          	  		if ((jcb_snmpv2c_TrpInfSel.getSelectedItem())==erroresGenerales47){
//          	  			//jtf_snmpv2c_OtroTrp
//	          	  		if (esVacio(jtf_snmpv2c_OtroTrp.getText())){          			
//	          		  	  men_err = men_err.concat(erroresGenerales38);	 	
//	          	  	  	  men_err_l =true;
//	          	  		}
//	          	  		if (!(esOID(jtf_snmpv2c_OtroTrp.getText()))){          			
//		          		  men_err = men_err.concat(erroresGenerales39);	 	
//		          	  	  men_err_l =true;
//		          	  	}
//          	  		}else{
//	          	  		//jtf_snmpv2c_OtroTrp
//	          	  		if (esVacio(jtf_snmpv2c_OtroTrp.getText())){          			
//	          		  	  men_err = men_err.concat(erroresGenerales40);	 	
//	          	  	  	  men_err_l =true;
//	          	  		}
//	          	  		if (!(esOID(jtf_snmpv2c_OtroTrp.getText()))){          			
//		          		  men_err = men_err.concat(erroresGenerales41);	 	
//		          	  	  men_err_l =true;
//		          	  	}
//          	  		}
//          	  		//jtf_snmpv2c_Descr
//          	  		if (esVacio(jtf_snmpv2c_Descr.getText())){          			
//          		  	  men_err = men_err.concat(erroresGenerales42);	 	
//          	  	  	  men_err_l =true;
//          	  		}
//          	  	}
//          	  	
//          	  	/*
//        		if ((jcb_snmpv2c_TrapSel.getSelectedItem())=="Enterprise Specific"){
//		        	if (!(esNumero(jtf_snmpv1_TrapSpcTxt.getText()))){          			
//		          		  men_err = men_err.concat("Debe introducir el valor del tipo de TRAP especfico y este debe ser un valor numrico\n");	 	
//		          	  	  men_err_l =true;
//		          	  	}
//		        }
//         		
//          	  	if (esVacio(jtf_snmpv1_EntTxt.getText())){          			
//          		  men_err = men_err.concat("Debe introducir el Enterprise\n");	 	
//          	  	  men_err_l =true;
//          	  	}
//				*/
//          		if ((esNumero(jtf_snmpv2c_IntTxt.getText()))&&(Integer.parseInt(jtf_snmpv2c_IntTxt.getText())<=0)){          			
//          		  men_err = men_err.concat(configParamError07);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          		if ((esNumero(jtf_snmpv2c_TmoutTxt.getText()))&&(Integer.parseInt(jtf_snmpv2c_TmoutTxt.getText())<=0)){          			
//          		  men_err = men_err.concat(configParamError09);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//
//          		if (men_err_l){          			
//					JOptionPane.showMessageDialog(managerSNMP.this,men_err,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	          	  	          	  
//			    	if (esVacio(jtf_snmpv2c_TrapSndHostIP.getText())){jtf_snmpv2c_TrapSndHostIP.setText("");}
//			    	if (!(esNumero(jtf_snmpv2c_PtoComTxt.getText()))){jtf_snmpv2c_PtoComTxt.setText("162");  }
//			    	if (esVacio(jtf_snmpv2c_ComTxt.getText())){jtf_snmpv2c_ComTxt.setText("public");}
//			    	if (!(esNumero(jtf_snmpv2c_IntTxt.getText()))){jtf_snmpv2c_IntTxt.setText("3");}  
//			    	if ((esNumero(jtf_snmpv2c_IntTxt.getText()))&&(Integer.parseInt(jtf_snmpv2c_IntTxt.getText())<=0)){jtf_snmpv2c_IntTxt.setText("3");}
//					if (!(esNumero(jtf_snmpv2c_TmoutTxt.getText()))){jtf_snmpv2c_TmoutTxt.setText("1500");}
//			    	if ((esNumero(jtf_snmpv2c_TmoutTxt.getText()))&&(Integer.parseInt(jtf_snmpv2c_TmoutTxt.getText())<=0)){jtf_snmpv2c_TmoutTxt.setText("1500");}			
//			    	if ((jcb_snmpv2c_TrapSel.getSelectedItem())==erroresGenerales46){
//			    		if (esVacio(jtf_snmpv2c_EntTxt.getText())){jtf_snmpv2c_EntTxt.setText("1.3.6.1.4.1.2789.2005");}
//          	  			if (!(esOID(jtf_snmpv2c_EntTxt.getText()))){jtf_snmpv2c_EntTxt.setText("1.3.6.1.4.1.2789.2005");}	
//	          	  		if (esVacio(jtf_snmpv2c_OtroTrp.getText())){jtf_snmpv2c_OtroTrp.setText("1.3.6.1.4.1.2789.2005.1");}
//	          	    	if (!(esOID(jtf_snmpv2c_OtroTrp.getText()))){jtf_snmpv2c_OtroTrp.setText("1.3.6.1.4.1.2789.2005.1");}
//          	  			if (esVacio(jtf_snmpv2c_Descr.getText())){jtf_snmpv2c_Descr.setText("WWW Server Has Been Restarted");}
//			        }
//				}else{
//					//Para encontrar y validar el tipo de dato de la descripcion cuando sea "Otro" trap
//					boolean snmpv2c_tipoDatoTrapSeleccionado = true;
//					String snmpv2c_tipoDatoTrapValor = "";
//					if ((jcb_snmpv2c_TrapSel.getSelectedItem())==erroresGenerales46){
//						if(!((jcb_snmpv2c_TpoDtoTrp.getSelectedItem().toString()).equals("-------------------------------"))){
//						  Variable valorTemp;
//					      String snmpv2c_tipoDatoTrapValorTemp=jcb_snmpv2c_TpoDtoTrp.getSelectedItem().toString();
//					      try{
//					        if(snmpv2c_tipoDatoTrapValorTemp.equals("OCTET STRING")){valorTemp = new OctetString(String.valueOf(jtf_snmpv2c_Descr.getText()));}
//					        if(snmpv2c_tipoDatoTrapValorTemp.equals("INTEGER")){valorTemp = new Integer32(Integer.parseInt(String.valueOf(jtf_snmpv2c_Descr.getText())));}
//							if(snmpv2c_tipoDatoTrapValorTemp.equals("OBJECT IDENTIFIER")){valorTemp = new OID(String.valueOf(jtf_snmpv2c_Descr.getText()));}				        
//					        if(snmpv2c_tipoDatoTrapValorTemp.equals("IpAddress")){valorTemp = new IpAddress(String.valueOf(jtf_snmpv2c_Descr.getText()));}
//					        if(snmpv2c_tipoDatoTrapValorTemp.equals("TimeTicks")){valorTemp = new TimeTicks(Long.parseLong(String.valueOf(jtf_snmpv2c_Descr.getText())));}
//					      }catch(Exception e4){
//					      	snmpv2c_tipoDatoTrapSeleccionado=false;
//					        JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales43,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));				
//					      }
//			          	}else{
//			          	   snmpv2c_tipoDatoTrapSeleccionado = false;
//			          	   JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales44,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));			
//			          	   	jcb_snmpv2c_TpoDtoTrp.setSelectedIndex(1);
//			          	}
//					}
//					//Una vez validado el tipo de dato de "Otro" trap, ahora se arma el trap ha ser enviado
//					if (snmpv2c_tipoDatoTrapSeleccionado){
//					  //System.out.println("se envio el trap.");
//					  enviarTrapInform sndtrap = new enviarTrapInform();
//					  traps.cambiarIdiomaAMensajes(mensajesDeTraps01,mensajesDeTraps02,mensajesDeTraps03,mensajesDeTraps04,mensajesDeTraps05,mensajesDeTraps06,mensajesDeTraps07,mensajesDeTraps08,mensajesDeTraps09,mensajesDeTraps10,mensajesDeTraps11,mensajesDeTraps12,mensajesDeTraps13,mensajesDeTraps14,mensajesDeTraps15,mensajesDeTraps16,mensajesDeTraps17,mensajesDeTraps18);
//					  String tipoTrapsnd = "";
//					  String tipoTrapsndSpc = "";
//					  if ((jcb_snmpv2c_TrapSel.getSelectedItem())=="coldStart"){tipoTrapsnd=String.valueOf(SnmpConstants.coldStart);}
//					  if ((jcb_snmpv2c_TrapSel.getSelectedItem())=="warmStart"){tipoTrapsnd=String.valueOf(SnmpConstants.warmStart);}
//					  if ((jcb_snmpv2c_TrapSel.getSelectedItem())=="linkDown"){tipoTrapsnd=String.valueOf(SnmpConstants.linkDown);}
//					  if ((jcb_snmpv2c_TrapSel.getSelectedItem())=="linkUp"){tipoTrapsnd=String.valueOf(SnmpConstants.linkUp);}
//					  if ((jcb_snmpv2c_TrapSel.getSelectedItem())=="authenticationFailure"){tipoTrapsnd=String.valueOf(SnmpConstants.authenticationFailure);}
//					  if ((jcb_snmpv2c_TrapSel.getSelectedItem())==erroresGenerales46){
//						tipoTrapsnd=jtf_snmpv2c_EntTxt.getText();
//						tipoTrapsndSpc=jtf_snmpv2c_OtroTrp.getText();
//						tipoTrapsndSpc=tipoTrapsndSpc.concat("=");
//						String snmpv2c_tipoDatoTrapValorTemp=jcb_snmpv2c_TpoDtoTrp.getSelectedItem().toString();
//						if(snmpv2c_tipoDatoTrapValorTemp.equals("OCTET STRING")){tipoTrapsndSpc=tipoTrapsndSpc.concat("{s}");}
//					    if(snmpv2c_tipoDatoTrapValorTemp.equals("INTEGER")){tipoTrapsndSpc=tipoTrapsndSpc.concat("{i}");}
//						if(snmpv2c_tipoDatoTrapValorTemp.equals("OBJECT IDENTIFIER")){tipoTrapsndSpc=tipoTrapsndSpc.concat("{o}");}				        
//					    if(snmpv2c_tipoDatoTrapValorTemp.equals("IpAddress")){tipoTrapsndSpc=tipoTrapsndSpc.concat("{a}");}
//					    if(snmpv2c_tipoDatoTrapValorTemp.equals("TimeTicks")){tipoTrapsndSpc=tipoTrapsndSpc.concat("{t}");}
//						tipoTrapsndSpc=tipoTrapsndSpc.concat(jtf_snmpv2c_Descr.getText());
//					  }
//	          	  	  if ((jcb_snmpv2c_TrpInfSel.getSelectedItem())==erroresGenerales47){
//	          	  	    sndtrap.informv2c(jtf_snmpv2c_TrapSndHostIP.getText(), jtf_snmpv2c_PtoComTxt.getText(), jtf_snmpv2c_ComTxt.getText(), Integer.parseInt(jtf_snmpv2c_IntTxt.getText()), Integer.parseInt(jtf_snmpv2c_TmoutTxt.getText()), tipoTrapsndSpc,tipoTrapsnd);
//	          	  	    JOptionPane.showMessageDialog(managerSNMP.this, erroresGenerales48 ,nameOfTheProgram,JOptionPane.INFORMATION_MESSAGE,(new ImageIcon("images/help.gif")));			
//	          	  	  }else{
//	          	  	    sndtrap.trapv2c(jtf_snmpv2c_TrapSndHostIP.getText(), jtf_snmpv2c_PtoComTxt.getText(), jtf_snmpv2c_ComTxt.getText(), Integer.parseInt(jtf_snmpv2c_IntTxt.getText()), Integer.parseInt(jtf_snmpv2c_TmoutTxt.getText()), tipoTrapsndSpc,tipoTrapsnd);
//	          	  	    JOptionPane.showMessageDialog(managerSNMP.this, erroresGenerales35 ,nameOfTheProgram,JOptionPane.INFORMATION_MESSAGE,(new ImageIcon("images/help.gif")));			
//	          	  	  }
//					}
//				}
//         	}
//       	});
//		//----------------------------------Fin de Pantalla de Traps-------------------------------
		
  	//----------------------------Fin del Manager SNMPv2c-------------------------------------
  	


  	//--------------------------------Manager SNMPv3------------------------------------------
	snmpv3 = new JPanel();
	//snmpv3.setBackground(Color.black);
	//snmpv3.setBounds(new Rectangle(0,0,100,100));
	snmpv3.setLayout(null);
	//snmpv3.setVisible(false);
	//jpanel.add(snmpv3,null);	

	jl_snmpv3_sel = new JLabel("Accin a realizar");//USO DEL JLABEL
    jl_snmpv3_sel.setBounds(new Rectangle(5,5,100,20));//establece el xy del componente   
    snmpv3.add(jl_snmpv3_sel,null);
    
    jcb_snmpv3_sel = new JComboBox();//USO DEL JCOMBOBOX
  	jcb_snmpv3_sel.setBounds(new Rectangle(110,5,373,20));
	jcb_snmpv3_sel.addItem("Configure Parameters");
  	jcb_snmpv3_sel.addItem("Comando Get");
  	jcb_snmpv3_sel.addItem("Comando GetNext");
  	jcb_snmpv3_sel.addItem("Comando GetBulk");
  	jcb_snmpv3_sel.addItem("Comando GetTable");
  	jcb_snmpv3_sel.addItem("Comando Walk");
  	jcb_snmpv3_sel.addItem("Comando Set");
  	jcb_snmpv3_sel.addItem("Enviar/Ver Traps");
  	//jcb_snmpv1_sel.setMaximumRowCount(2);
  	snmpv3.add(jcb_snmpv3_sel,null);
  	
  	jcb_snmpv3_sel.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        jp_snmpv3_Con.setVisible(false);
        jp_snmpv3_Get.setVisible(false);
        jp_snmpv3_GetNext.setVisible(false);
        jp_snmpv3_GetBulk.setVisible(false);
        jp_snmpv3_Set.setVisible(false);
        jp_snmpv3_Traps.setVisible(false);
        jp_snmpv3_TrapsSend.setVisible(false);    
        jp_snmpv3_walk.setVisible(false);
        jp_snmpv3_getTable.setVisible(false); 
        if ((jcb_snmpv3_sel.getSelectedItem())==ConfigureParameters){jp_snmpv3_Con.setVisible(true);}
        if ((jcb_snmpv3_sel.getSelectedItem())==ComandoGet){jp_snmpv3_Get.setVisible(true);}
        if ((jcb_snmpv3_sel.getSelectedItem())==ComandoGetNext){jp_snmpv3_GetNext.setVisible(true);}
        if ((jcb_snmpv3_sel.getSelectedItem())==ComandoGetBulk){jp_snmpv3_GetBulk.setVisible(true);}
        if ((jcb_snmpv3_sel.getSelectedItem())==ComandoGetTable){jp_snmpv3_getTable.setVisible(true);}
        if ((jcb_snmpv3_sel.getSelectedItem())==ComandoWalk){jp_snmpv3_walk.setVisible(true);}
        if ((jcb_snmpv3_sel.getSelectedItem())==ComandoSet){jp_snmpv3_Set.setVisible(true);}
        if ((jcb_snmpv3_sel.getSelectedItem())==EnviarVerTraps){jp_snmpv3_Traps.setVisible(true);}
        if ((jcb_snmpv3_sel.getSelectedItem())==EnviarVerTraps){jp_snmpv3_TrapsSend.setVisible(true);}   
      }
    });


		//----------------------------------Pantalla de Conexin-----------------------------------
	    jp_snmpv3_Con = new JPanel();
	    //jp_snmpv2c_Con.setBackground(Color.white);
	    jp_snmpv3_Con.setBounds(new Rectangle(0,30,483,483));
		jp_snmpv3_Con.setBorder(BorderFactory.createTitledBorder("Configure Parameters"));
		jp_snmpv3_Con.setLayout(null);
		jp_snmpv3_Con.setVisible(true);
		snmpv3.add(jp_snmpv3_Con,null);

		jl_snmpv3_IP = new JLabel("Direccin IP del Agente");//USO DEL JLABEL
    	jl_snmpv3_IP.setBounds(new Rectangle(76,26,160,20));//establece el xy del componente
    	jp_snmpv3_Con.add(jl_snmpv3_IP,null);
    	
    	jtf_snmpv3_IP = new JTextField();//USO DEL JTEXTFIELD
    	jtf_snmpv3_IP.setBounds(new Rectangle(246,26,160,20));//establece el xy del componente
    	jtf_snmpv3_IP.setText(String.valueOf(IP));
    	jp_snmpv3_Con.add(jtf_snmpv3_IP,null);
    
    	jl_snmpv3_pto = new JLabel("Puerto de Comunicaciones");//USO DEL JLABEL
    	jl_snmpv3_pto.setBounds(new Rectangle(76,66,160,20));//establece el xy del componente
    	jp_snmpv3_Con.add(jl_snmpv3_pto,null);
    
    	jtf_snmpv3_pto = new JTextField();//USO DEL JTEXTFIELD
    	jtf_snmpv3_pto.setBounds(new Rectangle(246,66,160,20));//establece el xy del componente
    	jtf_snmpv3_pto.setText(String.valueOf(pto));
    	jp_snmpv3_Con.add(jtf_snmpv3_pto,null);
     	
    	jl_snmpv3_User = new JLabel("Nombre de Usuario");//USO DEL JLABEL
    	jl_snmpv3_User.setBounds(new Rectangle(76,146,160,20));//establece el xy del componente
    	jp_snmpv3_Con.add(jl_snmpv3_User,null);
 
    	jpf_snmpv3_User = new JPasswordField();//USO DEL JPASSWORDFIELD
    	jpf_snmpv3_User.setBounds(new Rectangle(246,146,160,20));//establece el xy del componente
    	jpf_snmpv3_User.setEchoChar('*');
    	jpf_snmpv3_User.setText(String.valueOf(user));
    	//jpf_snmpv3_User.setEchoChar((char)0);
    	jp_snmpv3_Con.add(jpf_snmpv3_User,null);
   	
    	jl_snmpv3_Aut = new JLabel("Clave de Autenticacin");//USO DEL JLABEL
    	jl_snmpv3_Aut.setBounds(new Rectangle(76,186,160,20));//establece el xy del componente
    	jp_snmpv3_Con.add(jl_snmpv3_Aut,null);

    	jpf_snmpv3_Aut = new JPasswordField();//USO DEL JPASSWORDFIELD
    	jpf_snmpv3_Aut.setBounds(new Rectangle(246,186,160,20));//establece el xy del componente
    	jpf_snmpv3_Aut.setEchoChar('*');
    	jpf_snmpv3_Aut.setText(String.valueOf(claveAut));
    	//jpf_snmpv3_User.setEchoChar((char)0);
    	jp_snmpv3_Con.add(jpf_snmpv3_Aut,null);

    	jl_snmpv3_Priv = new JLabel("Clave de Encriptacin");//USO DEL JLABEL
    	jl_snmpv3_Priv.setBounds(new Rectangle(76,226,160,20));//establece el xy del componente
    	jp_snmpv3_Con.add(jl_snmpv3_Priv,null);

    	jpf_snmpv3_Priv = new JPasswordField();//USO DEL JPASSWORDFIELD
    	jpf_snmpv3_Priv.setBounds(new Rectangle(246,226,160,20));//establece el xy del componente
    	jpf_snmpv3_Priv.setEchoChar('*');
    	jpf_snmpv3_Priv.setText(String.valueOf(clavePriv));
    	//jpf_snmpv3_Priv.setEchoChar((char)0);
    	jp_snmpv3_Con.add(jpf_snmpv3_Priv,null);
   	
   	
    	jl_snmpv3_VerUsr = new JLabel("Visualizar Datos Privados");//USO DEL JLABEL
    	jl_snmpv3_VerUsr.setBounds(new Rectangle(76,106,160,20));//establece el xy del componente
    	jp_snmpv3_Con.add(jl_snmpv3_VerUsr,null);

	    jcb_snmpv3_VerUsr = new JComboBox();//USO DEL JCOMBOBOX
	  	jcb_snmpv3_VerUsr.setBounds(new Rectangle(246,106,160,20));  
		jcb_snmpv3_VerUsr.addItem("Si");
	  	jcb_snmpv3_VerUsr.addItem("No");
	  	jcb_snmpv3_VerUsr.setSelectedIndex(1);	  	 
	  	jp_snmpv3_Con.add(jcb_snmpv3_VerUsr,null);
 
	  	jcb_snmpv3_VerUsr.addActionListener(new ActionListener(){    
	      public void actionPerformed(ActionEvent e) {	          
	        if ((jcb_snmpv3_VerUsr.getSelectedItem())==opcionSi){jpf_snmpv3_User.setEchoChar((char)0);jpf_snmpv3_Aut.setEchoChar((char)0);jpf_snmpv3_Priv.setEchoChar((char)0);}; 
	        if ((jcb_snmpv3_VerUsr.getSelectedItem())==opcionNo){jpf_snmpv3_User.setEchoChar('*');jpf_snmpv3_Aut.setEchoChar('*');jpf_snmpv3_Priv.setEchoChar('*');}; 	
	      }
	    });

    	jl_snmpv3_metAut = new JLabel("Algoritmo de Autenticacin");//USO DEL JLABEL
    	jl_snmpv3_metAut.setBounds(new Rectangle(76,266,160,20));//establece el xy del componente
    	jp_snmpv3_Con.add(jl_snmpv3_metAut,null);

	    jcb_snmpv3_metAut = new JComboBox();//USO DEL JCOMBOBOX
	  	jcb_snmpv3_metAut.setBounds(new Rectangle(246,266,160,20));  
		jcb_snmpv3_metAut.addItem("MD5");
	  	jcb_snmpv3_metAut.addItem("SHA");
	  	jcb_snmpv3_metAut.setSelectedIndex(1);	  	 
	  	jp_snmpv3_Con.add(jcb_snmpv3_metAut,null);
    
    	jl_snmpv3_metPriv = new JLabel("Algoritmo de Encriptacin");//USO DEL JLABEL
    	jl_snmpv3_metPriv.setBounds(new Rectangle(76,306,160,20));//establece el xy del componente
    	jp_snmpv3_Con.add(jl_snmpv3_metPriv,null);

	    jcb_snmpv3_metPriv = new JComboBox();//USO DEL JCOMBOBOX
	  	jcb_snmpv3_metPriv.setBounds(new Rectangle(246,306,160,20));  
		jcb_snmpv3_metPriv.addItem("DES");
	  	jcb_snmpv3_metPriv.addItem("AES128");
	  	jcb_snmpv3_metPriv.addItem("AES192");	  	
	  	jcb_snmpv3_metPriv.addItem("AES256");	  	
	  	jcb_snmpv3_metPriv.setSelectedIndex(1);	  	 
	  	jp_snmpv3_Con.add(jcb_snmpv3_metPriv,null);     
  
    	jl_snmpv3_inten = new JLabel("Nro. de Intentos");//USO DEL JLABEL
    	jl_snmpv3_inten.setBounds(new Rectangle(76,346,160,20));//establece el xy del componente
    	jp_snmpv3_Con.add(jl_snmpv3_inten,null);
    
    	jtf_snmv3_inten = new JTextField();//USO DEL JTEXTFIELD
    	jtf_snmv3_inten.setBounds(new Rectangle(246,346,160,20));//establece el xy del componente
    	jtf_snmv3_inten.setText(String.valueOf(inten));
    	jp_snmpv3_Con.add(jtf_snmv3_inten,null);

    	jl_snmpv3_timeOut = new JLabel("Tiempo de Espera (ms)");//USO DEL JLABEL
    	jl_snmpv3_timeOut.setBounds(new Rectangle(76,386,160,20));//establece el xy del componente
    	jp_snmpv3_Con.add(jl_snmpv3_timeOut,null);
     
    	jtt_snmpv3_timeOut = new JTextField();//USO DEL JTEXTFIELD
    	jtt_snmpv3_timeOut.setBounds(new Rectangle(246,386,160,20));//establece el xy del componente
    	jtt_snmpv3_timeOut.setText(String.valueOf(timeOut));
    	jp_snmpv3_Con.add(jtt_snmpv3_timeOut,null);
 
    	jb_snmpv3_aplicarPara = new JButton("Aplicar Cambios");
	    jb_snmpv3_aplicarPara.setBounds(new Rectangle(141,431,200,20));
	    jb_snmpv3_aplicarPara.setToolTipText("Presione para establecer los nuevos parmetros.");
	    jp_snmpv3_Con.add(jb_snmpv3_aplicarPara,null);

	
		jb_snmpv3_aplicarPara.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
          	String men_err = "";
          	boolean men_err_l = false;

          		if (esVacio(jtf_snmpv3_IP.getText())){          			
          		  men_err = men_err.concat(configParamError01);	 	
          	  	  men_err_l =true;
          	  	}
          	  	
          	  	if (!(ipValida(jtf_snmpv3_IP.getText()))){
          	  	  men_err = men_err.concat(configParamError02);	 	
          	  	  men_err_l =true;
          	  	}
          	  	
          	  	
          		if (!(esNumero(jtf_snmpv3_pto.getText()))){          			
          		  men_err = men_err.concat(configParamError03);	 	
          	  	  men_err_l =true;
          	  	}

          	  	if (esVacio(jpf_snmpv3_User.getText())){          			
          		  men_err = men_err.concat(configParamError10);	 	
          	  	  men_err_l =true;
          	  	}
          	  	
          	  	//jpf_snmpv3_Aut
          	  	if (esVacio(jpf_snmpv3_Aut.getText())){          			
          		  men_err = men_err.concat(configParamError11);	 	
          	  	  men_err_l =true;
          	  	}
          	  	
          	  	if ((jpf_snmpv3_Aut.getText()).length()<8){          			
          		  men_err = men_err.concat(configParamError12);	 	
          	  	  men_err_l =true;
          	  	}
          	  	
          	  	//jpf_snmpv3_Priv
          	  	if (esVacio(jpf_snmpv3_Priv.getText())){          			
          		  men_err = men_err.concat(configParamError13);	 	
          	  	  men_err_l =true;
          	  	}
          	  	
          	  	if ((jpf_snmpv3_Priv.getText()).length()<8){          			
          		  men_err = men_err.concat(configParamError14);	 	
          	  	  men_err_l =true;
          	  	}
          	  	          	  	
          		if (!(esNumero(jtf_snmv3_inten.getText()))){          			
          		  men_err = men_err.concat(configParamError06);	 	
          	  	  men_err_l =true;
          	  	}

          		if ((esNumero(jtf_snmv3_inten.getText()))&&(Integer.parseInt(jtf_snmv3_inten.getText())<=0)){          			
          		  men_err = men_err.concat(configParamError07);	 	
          	  	  men_err_l =true;
          	  	}

          		if (!(esNumero(jtt_snmpv3_timeOut.getText()))){          			
          		  men_err = men_err.concat(configParamError08);	 	
          	  	  men_err_l =true;
          	  	}

          		if ((esNumero(jtt_snmpv3_timeOut.getText()))&&(Integer.parseInt(jtt_snmpv3_timeOut.getText())<=0)){          			
          		  men_err = men_err.concat(configParamError09);	 	
          	  	  men_err_l =true;
          	  	}

          		if (men_err_l){          			
				JOptionPane.showMessageDialog(managerSNMP.this,men_err,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	          	  	          	  
		    	if (esVacio(jtf_snmpv3_IP.getText())){jtf_snmpv3_IP.setText(String.valueOf(IP));}
		    	if (!(ipValida(jtf_snmpv3_IP.getText()))){jtf_snmpv3_IP.setText(String.valueOf(IP));}
		    	if (!(esNumero(jtt_snmpv3_timeOut.getText()))){jtt_snmpv3_timeOut.setText(String.valueOf(timeOut));}
		    	if ((esNumero(jtt_snmpv3_timeOut.getText()))&&(Integer.parseInt(jtt_snmpv3_timeOut.getText())<=0)){jtt_snmpv3_timeOut.setText(String.valueOf(timeOut));}
		    	if (!(esNumero(jtf_snmv3_inten.getText()))){jtf_snmv3_inten.setText(String.valueOf(inten));}
		    	if ((esNumero(jtf_snmv3_inten.getText()))&&(Integer.parseInt(jtf_snmv3_inten.getText())<=0)){jtf_snmv3_inten.setText(String.valueOf(inten));}
				if (!(esNumero(jtf_snmpv3_pto.getText()))){jtf_snmpv3_pto.setText(String.valueOf(pto));}    
				if (esVacio(jpf_snmpv3_User.getText())){jpf_snmpv3_User.setText(String.valueOf(user));}   
				if (esVacio(jpf_snmpv3_Aut.getText())){jpf_snmpv3_Aut.setText(String.valueOf(claveAut));}
				if ((jpf_snmpv3_Aut.getText()).length()<8){jpf_snmpv3_Aut.setText(String.valueOf(claveAut));}
				if (esVacio(jpf_snmpv3_Priv.getText())){jpf_snmpv3_Priv.setText(String.valueOf(clavePriv));}
				if ((jpf_snmpv3_Priv.getText()).length()<8){jpf_snmpv3_Priv.setText(String.valueOf(clavePriv));}

				} else {       
					   	  	          	  	
          		IP =		 jtf_snmpv3_IP.getText();
          		pto =	 Long.parseLong(jtf_snmpv3_pto.getText());
          		user =	 jpf_snmpv3_User.getText();
          		claveAut =	 jpf_snmpv3_Aut.getText();
          		clavePriv =	 jpf_snmpv3_Priv.getText();
          		if ((jcb_snmpv3_metAut.getSelectedItem())=="MD5"){metAut = AuthMD5.ID;}; 
          		if ((jcb_snmpv3_metAut.getSelectedItem())=="SHA"){metAut = AuthSHA.ID;}; 
          		if ((jcb_snmpv3_metPriv.getSelectedItem())=="DES"){metPriv = PrivDES.ID;}; 
          		if ((jcb_snmpv3_metPriv.getSelectedItem())=="AES128"){metPriv = PrivAES128.ID;};           			
          		if ((jcb_snmpv3_metPriv.getSelectedItem())=="AES192"){metPriv = PrivAES192.ID;}; 
          		if ((jcb_snmpv3_metPriv.getSelectedItem())=="AES256"){metPriv = PrivAES256.ID;};        
          		inten =	 Integer.parseInt(jtf_snmv3_inten.getText());
          		timeOut = Integer.parseInt(jtt_snmpv3_timeOut.getText());
				
				JOptionPane.showMessageDialog(managerSNMP.this, configParamResult01,nameOfTheProgram,JOptionPane.INFORMATION_MESSAGE,(new ImageIcon("images/help.gif")));  	          	  	          	  
					
//				//PARA LLENAR LOS PARAMETROS DE SNMPv1					
//				jtf_snmpv1_IP.setText(IP);
//          		jtf_snmpv1_pto.setText(String.valueOf(pto));          		
//          		jtf_snmv1_inten.setText(String.valueOf(inten));
//          		jtt_snmpv1_timeOut.setText(String.valueOf(timeOut));
//          		
// 
//          		//PARA LLENAR LOS PARAMETROS DE SNMPv2c	
//				jtf_snmpv2c_IP.setText(IP);
//          		jtf_snmpv2c_pto.setText(String.valueOf(pto));
//          		jtf_snmv2c_inten.setText(String.valueOf(inten));
//          		jtt_snmpv2c_timeOut.setText(String.valueOf(timeOut));	
//				  		
					
				} 
          		
         	}
       	});
	    //----------------------------------Fin de Pantalla de Conexin----------------------------


	    //----------------------------------Pantalla de Get----------------------------------------  	
 	    jp_snmpv3_Get = new JPanel();
	    //jp_snmpv3_Get.setBackground(Color.blue);
		//jp_snmpv3_Get.setBorder(BorderFactory.createTitledBorder("Comando Get de SNMPv1"));
		jp_snmpv3_Get.setBounds(new Rectangle(0,30,483,483));
		jp_snmpv3_Get.setBorder(BorderFactory.createTitledBorder("Comando Get"));
		jp_snmpv3_Get.setLayout(null);
		jp_snmpv3_Get.setVisible(false);
		snmpv3.add(jp_snmpv3_Get,null);    	    	

    	jsp_snmpv3_getDescrip = new JScrollPane();
    	jsp_snmpv3_getDescrip.setBounds(new Rectangle(10,20,465,270));
    	jsp_snmpv3_getDescrip.setWheelScrollingEnabled(true);
    	jp_snmpv3_Get.add(jsp_snmpv3_getDescrip,null);
	
    	jta_snmpv3_getDescrip = new JTextArea();
    	jta_snmpv3_getDescrip.setText("");
    	jta_snmpv3_getDescrip.setEditable(false);
    	jsp_snmpv3_getDescrip.getViewport().add(jta_snmpv3_getDescrip,null);
     	
    	jl_snmpv3_getEtiGet = new JLabel("OID");
	    jl_snmpv3_getEtiGet.setBounds(new Rectangle(10,300,20,20));
	    jp_snmpv3_Get.add(jl_snmpv3_getEtiGet,null);
	    
	    jtf_snmpv3_getGet = new JTextField();
	    jtf_snmpv3_getGet.setBounds(new Rectangle(40,300,244,20));   
	    jtf_snmpv3_getGet.setEditable(true);
	    jp_snmpv3_Get.add(jtf_snmpv3_getGet,null);

       	jb_snmpv3_getAdd = new JButton("Aadir");
	    jb_snmpv3_getAdd.setBounds(new Rectangle(295,300,79,20));  	    
	    jb_snmpv3_getAdd.setToolTipText("Presione para agregar el OID.");
	    jp_snmpv3_Get.add(jb_snmpv3_getAdd,null);
	    
    	jb_snmpv3_getUndo = new JButton("Deshacer");
	    jb_snmpv3_getUndo.setBounds(new Rectangle(384,300,90,20));  //9999
	    jb_snmpv3_getUndo.setToolTipText("Presione para eliminar el ltimo OID de los objetos.");
	    jp_snmpv3_Get.add(jb_snmpv3_getUndo,null);
	    
    	jl_snmpv3_getObjs = new JLabel("Objetos");
	    jl_snmpv3_getObjs.setBounds(new Rectangle(10,330,60,20));
	    jp_snmpv3_Get.add(jl_snmpv3_getObjs,null);
	    
	    jtf_snmpv3_getObjs = new JTextField();
	    jtf_snmpv3_getObjs.setBounds(new Rectangle(60,330,140,20));   
	    jtf_snmpv3_getObjs.setEditable(false);	    	    
	    jp_snmpv3_Get.add(jtf_snmpv3_getObjs,null);

    	jl_snmpv3_getModSeg = new JLabel("Seguridad");
	    jl_snmpv3_getModSeg.setBounds(new Rectangle(210,330,60,20));
	    jp_snmpv3_Get.add(jl_snmpv3_getModSeg,null);
	    
	    jcb_snmpv3_getModSeg = new JComboBox();//USO DEL JCOMBOBOX
	  	jcb_snmpv3_getModSeg.setBounds(new Rectangle(280,330,125,20));  
		jcb_snmpv3_getModSeg.addItem("AUTH_NOPRIV");
		jcb_snmpv3_getModSeg.addItem("AUTH_PRIV");
		jcb_snmpv3_getModSeg.addItem("NOAUTH_NOPRIV");
		//jcb_snmpv3_getModSeg.addItem("NOAUTH_PRIV");	  	
	  	jcb_snmpv3_getModSeg.setSelectedIndex(1);	  	 
	  	jp_snmpv3_Get.add(jcb_snmpv3_getModSeg,null);

    	jb_snmpv3_getGet = new JButton("Get");
	    jb_snmpv3_getGet.setBounds(new Rectangle(415,330,59,20));  
	    jb_snmpv3_getGet.setToolTipText("Presione para obtener el valor.");
	    jp_snmpv3_Get.add(jb_snmpv3_getGet,null);
	    
	    jsp_snmpv3_getResp = new JScrollPane();
    	jsp_snmpv3_getResp.setBounds(new Rectangle(10,360,465,111));  
    	jsp_snmpv3_getResp.setWheelScrollingEnabled(true);
    	jp_snmpv3_Get.add(jsp_snmpv3_getResp,null);

    	jta_snmpv3_getResp = new JTextArea();
    	jta_snmpv3_getResp.setText("");
    	jta_snmpv3_getResp.setEditable(false);
    	jsp_snmpv3_getResp.getViewport().add(jta_snmpv3_getResp,null);
 	    
 	    compuestoGetSNMPv3Temp = new Vector();
 	    
 	    //Para el aadir
 	    jb_snmpv3_getAdd.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        
        	  //aqui lo que hace
          	  if ((jtf_snmpv3_getGet.getText()).equals("")){
          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_getGet.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	}else{        	  	
          	  	          	  	
					//Para transformar el OID de string al arreglo para pasarselo al get
	          	  	//System.out.println("Tratando el oid");
	          	  	try{
		          	  	int digitos=0;
		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
						while(Token.hasMoreTokens()){
						  Token.nextToken();
						  digitos++;	
						}
		          	  	//System.out.println("el oid tiene "+digitos+"digitos");
		          	  	int tamao=digitos;
		          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
		          	  	int requerimiento[] = new int[tamao];
		          	  	int i=0;
		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
						while(Token1.hasMoreTokens()){
						  requerimiento[i]=Integer.parseInt(Token1.nextToken());
						  i++;
						  //System.out.println("valor de i: "+i);	
						}
		          	  	//requerimiento[i]=0;
		          	  	/*
		          	  	System.out.println("cambiado a arreglo");
		          	  	i++;
		          	  	System.out.println("tamao del arreglo: "+i);
		          	  	for (int j=0;j<i;j++){
		          	  	  System.out.println("d"+j+" valor: "+requerimiento[j]);	
		          	  	}
		          	  	*/
	           	  		
	           	  		compuestoGetSNMPv3Temp.add(new OID(requerimiento));
	          	  	    
	          	  	    String contenido = "";
					    for (int pp=0;pp<compuestoGetSNMPv3Temp.size();pp++){
					      contenido=contenido.concat(compuestoGetSNMPv3Temp.get(pp)+"; ");	
					    }
					    jtf_snmpv3_getObjs.setText(contenido);
		          	  	
		          	  }catch(NumberFormatException nfe){
	          	  	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
	          	  	  }
          	  	}
          	  }
         	
        	
        	}
        });
        
        //Para eliminar
        jb_snmpv3_getUndo.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        
        	  int ultimo = compuestoGetSNMPv3Temp.size();
        	  ultimo=ultimo-1;
        	  if (ultimo>=0){
        	    compuestoGetSNMPv3Temp.removeElementAt(ultimo);
        	  }else{
        	  	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
        	  }
    	  	
		      String contenido = "";
		      for (int pp=0;pp<compuestoGetSNMPv3Temp.size();pp++){
		        contenido=contenido.concat(compuestoGetSNMPv3Temp.get(pp)+"; ");	
		      }
		      jtf_snmpv3_getObjs.setText(contenido);
        	
        	}
        }); 

    	//esto va para el get no para aca
	      jb_snmpv3_getGet.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
          	  //aqui lo que hace
          	  if ((jtf_snmpv3_getGet.getText()).equals("")){
          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_getGet.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	}else{        	  	
          	  	          	  	
					//Para transformar el OID de string al arreglo para pasarselo al get
	          	  	//System.out.println("Tratando el oid");
	          	  	try{
		          	  	int digitos=0;
		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
						while(Token.hasMoreTokens()){
						  Token.nextToken();
						  digitos++;	
						}
		          	  	//System.out.println("el oid tiene "+digitos+"digitos");
		          	  	int tamao=digitos;
		          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
		          	  	int requerimiento[] = new int[tamao];
		          	  	int i=0;
		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
						while(Token1.hasMoreTokens()){
						  requerimiento[i]=Integer.parseInt(Token1.nextToken());
						  i++;
						  //System.out.println("valor de i: "+i);	
						}
		          	  	//requerimiento[i]=0;
		          	  	/*
		          	  	System.out.println("cambiado a arreglo");
		          	  	i++;
		          	  	System.out.println("tamao del arreglo: "+i);
		          	  	for (int j=0;j<i;j++){
		          	  	  System.out.println("d"+j+" valor: "+requerimiento[j]);	
		          	  	}
		          	  	*/
	           	  		
	           	  		//AQUI VIENE EL CODIGO PARA REALMENTE ENVIAR EL REQUERIMIENTO
	           	  		
	           	  		boolean esCompuesto=false;
		          	  	compuestoGetSNMPv3 = new Vector();
		          	  	if((compuestoGetSNMPv3Temp.size())>=1){
		          	  	   compuestoGetSNMPv3=compuestoGetSNMPv3Temp;
		          	  	   esCompuesto=true;	
		          	  	}else{
		          	  	   compuestoGetSNMPv3.add(new OID(requerimiento));
		          	  	   esCompuesto=false;
		          	  	}
	           	  		
	         	  		SNMPv3 manager = new SNMPv3();
	         	  		
	         	  		int nivelSeguridad=0;
	         	  		if ((jcb_snmpv3_getModSeg.getSelectedItem())=="AUTH_NOPRIV"){nivelSeguridad=SecurityLevel.AUTH_NOPRIV;}
	         	  		if ((jcb_snmpv3_getModSeg.getSelectedItem())=="AUTH_PRIV"){nivelSeguridad=SecurityLevel.AUTH_PRIV;}
	         	  		if ((jcb_snmpv3_getModSeg.getSelectedItem())=="NOAUTH_NOPRIV"){nivelSeguridad=SecurityLevel.NOAUTH_NOPRIV;}
	         	  		manager.cambiarIdiomaAMensajes(erroresGenerales04,erroresGenerales29,erroresGenerales30,erroresGenerales31);
		          	  	String respuesta = manager.getv3(IP, String.valueOf(pto), user, claveAut, clavePriv, nivelSeguridad,inten, timeOut, compuestoGetSNMPv3,metAut,metPriv);
		          	  	compuestoGetSNMPv3.removeAllElements();
		          	  	compuestoGetSNMPv3Temp.removeAllElements();
				        jtf_snmpv3_getObjs.setText("");
		          	  	
		          	  	//System.out.println("Getv2c: "+respuesta);
		          	  	if (respuesta.equals(erroresGenerales04)){
		          	  	  respuesta=("Get SNMPv3: ").concat(respuesta);
		          	  	}else{
		          	  	  respuesta=("Get SNMPv3: ").concat(respuesta);	
		          	  	}
		          	  	respuesta=respuesta.concat("\n");
		          	  	jta_snmpv3_getResp.setText((jta_snmpv3_getResp.getText()).concat(respuesta));
		          	  	
		          	  }catch(NumberFormatException nfe){
	          	  	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
	          	  	  }
          	  	}
          	  }
         	}
       	});
       	
   	
   		
	    //jp_snmpv1_Get.setVisible(true);
				
		//----------------------------------Fin de Pantalla de Get---------------------------------	
	 
 		//----------------------------------Pantalla de GetNext------------------------------------	      	
		jp_snmpv3_GetNext = new JPanel();
	    //jp_snmpv3_GetNext.setBackground(Color.blue);
		//jp_snmpv3_GetNext.setBorder(BorderFactory.createTitledBorder("Comando GetNext de SNMPv1"));
		jp_snmpv3_GetNext.setBounds(new Rectangle(0,30,483,483));  
		jp_snmpv3_GetNext.setBorder(BorderFactory.createTitledBorder("Comando GetNext"));
		jp_snmpv3_GetNext.setLayout(null);
		jp_snmpv3_GetNext.setVisible(false);
		snmpv3.add(jp_snmpv3_GetNext,null);
	    	
    	jsp_snmpv3_GetNextDescrip = new JScrollPane();
    	jsp_snmpv3_GetNextDescrip.setBounds(new Rectangle(10,20,465,270));  //320,10,435,300
    	jsp_snmpv3_GetNextDescrip.setWheelScrollingEnabled(true);
    	jp_snmpv3_GetNext.add(jsp_snmpv3_GetNextDescrip,null);
    	
    	jta_snmpv3_GetNextDescrip = new JTextArea();           
    	jta_snmpv3_GetNextDescrip.setText("");
    	jta_snmpv3_GetNextDescrip.setEditable(false);
    	jsp_snmpv3_GetNextDescrip.getViewport().add(jta_snmpv3_GetNextDescrip,null);
     	
    	jl_snmpv3_GetNextEtiGetNext = new JLabel("OID");
	    jl_snmpv3_GetNextEtiGetNext.setBounds(new Rectangle(10,300,20,20));   
	    jp_snmpv3_GetNext.add(jl_snmpv3_GetNextEtiGetNext,null);
	    
	    jtf_snmpv3_GetNextGetNext = new JTextField();
	    jtf_snmpv3_GetNextGetNext.setBounds(new Rectangle(40,300,245,20));  
	    jtf_snmpv3_GetNextGetNext.setEditable(true);
	    jp_snmpv3_GetNext.add(jtf_snmpv3_GetNextGetNext,null);

        jb_snmpv3_getNextAdd = new JButton("Aadir");
	    jb_snmpv3_getNextAdd.setBounds(new Rectangle(295,300,79,20));  
	    jb_snmpv3_getNextAdd.setToolTipText("Presione para agregar el OID.");
	    jp_snmpv3_GetNext.add(jb_snmpv3_getNextAdd,null);
	    
    	jb_snmpv3_getNextUndo = new JButton("Deshacer");
	    jb_snmpv3_getNextUndo.setBounds(new Rectangle(384,300,90,20));  //9999
	    jb_snmpv3_getNextUndo.setToolTipText("Presione para eliminar el ltimo OID de los objetos.");
	    jp_snmpv3_GetNext.add(jb_snmpv3_getNextUndo,null);
	    
    	jl_snmpv3_getNextObjs = new JLabel("Objetos");
	    jl_snmpv3_getNextObjs.setBounds(new Rectangle(10,330,60,20));
	    jp_snmpv3_GetNext.add(jl_snmpv3_getNextObjs,null);

	    jtf_snmpv3_getNextObjs = new JTextField();
	    jtf_snmpv3_getNextObjs.setBounds(new Rectangle(60,330,120,20));   
	    jtf_snmpv3_getNextObjs.setEditable(false);	    	    
	    jp_snmpv3_GetNext.add(jtf_snmpv3_getNextObjs,null);

    	jl_snmpv3_getNextModSeg = new JLabel("Seguridad");
	    jl_snmpv3_getNextModSeg.setBounds(new Rectangle(190,330,60,20));
	    jp_snmpv3_GetNext.add(jl_snmpv3_getNextModSeg,null);
	    
	    jcb_snmpv3_getNextModSeg = new JComboBox();//USO DEL JCOMBOBOX
	  	jcb_snmpv3_getNextModSeg.setBounds(new Rectangle(260,330,125,20));  
		jcb_snmpv3_getNextModSeg.addItem("AUTH_NOPRIV");
		jcb_snmpv3_getNextModSeg.addItem("AUTH_PRIV");
		jcb_snmpv3_getNextModSeg.addItem("NOAUTH_NOPRIV");
		//jcb_snmpv3_getNextModSeg.addItem("NOAUTH_PRIV");	  	
	  	//jcb_snmpv3_getNextModSeg.setSelectedIndex(1);	  	 
	  	jp_snmpv3_GetNext.add(jcb_snmpv3_getNextModSeg,null);

    	jb_snmpv3_GetNextGetNext = new JButton("GetNext");
	    jb_snmpv3_GetNextGetNext.setBounds(new Rectangle(395,330,79,20));   
	    jb_snmpv3_GetNextGetNext.setToolTipText("Presione para obtener el valor.");
	    jp_snmpv3_GetNext.add(jb_snmpv3_GetNextGetNext,null);
 
	    jsp_snmpv3_GetNextResp = new JScrollPane();
    	jsp_snmpv3_GetNextResp.setBounds(new Rectangle(10,360,465,111));   
    	jsp_snmpv3_GetNextResp.setWheelScrollingEnabled(true);
    	jp_snmpv3_GetNext.add(jsp_snmpv3_GetNextResp,null);
    	
    	jta_snmpv3_GetNextResp = new JTextArea();
    	jta_snmpv3_GetNextResp.setText("");
    	jta_snmpv3_GetNextResp.setEditable(false);
    	jsp_snmpv3_GetNextResp.getViewport().add(jta_snmpv3_GetNextResp,null);
	    
	    compuestoGetNextSNMPv3Temp = new Vector();
	    
	    //Para aadir
	    jb_snmpv3_getNextAdd.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        
        	  //aqui lo que hace
          	  if ((jtf_snmpv3_GetNextGetNext.getText()).equals("")){
          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_GetNextGetNext.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	}else{        	  	
          	  	          	  	
					//Para transformar el OID de string al arreglo para pasarselo al GetNext
	          	  	//System.out.println("Tratando el oid");
	          	  	try{
		          	  	int digitos=0;
		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
						while(Token.hasMoreTokens()){
						  Token.nextToken();
						  digitos++;	
						}
		          	  	//System.out.println("el oid tiene "+digitos+"digitos");
		          	  	int tamao=digitos;
		          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
		          	  	int requerimiento[] = new int[tamao];
		          	  	int i=0;
		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
						while(Token1.hasMoreTokens()){
						  requerimiento[i]=Integer.parseInt(Token1.nextToken());
						  i++;
						  //System.out.println("valor de i: "+i);	
						}
		          	  	//requerimiento[i]=0;
		          	  	/*
		          	  	System.out.println("cambiado a arreglo");
		          	  	i++;
		          	  	System.out.println("tamao del arreglo: "+i);
		          	  	for (int j=0;j<i;j++){
		          	  	  System.out.println("d"+j+" valor: "+requerimiento[j]);	
		          	  	}
		          	  	*/
		          	  	
		          	  	compuestoGetNextSNMPv3Temp.add(new OID(requerimiento));
		          	  	String contenido = "";
		          	  	for (int pp=0;pp<compuestoGetNextSNMPv3Temp.size();pp++){
		          	  	  contenido=contenido.concat(compuestoGetNextSNMPv3Temp.get(pp)+"; ");	
		          	  	}
		          	  	jtf_snmpv3_getNextObjs.setText(contenido);   
		          	  	
		          	  }catch(NumberFormatException nfe){
	          	  	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
	          	  	  }
          	  	}
          	  }   
         	
        		
        	}
        });
        
        //Para eliminar
        jb_snmpv3_getNextUndo.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		
        	  int ultimo = compuestoGetNextSNMPv3Temp.size();
        	  ultimo=ultimo-1;
        	  if (ultimo>=0){
        	    compuestoGetNextSNMPv3Temp.removeElementAt(ultimo);
        	  }else{
        	  	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
        	  }
    	  	
		      String contenido = "";
		      for (int pp=0;pp<compuestoGetNextSNMPv3Temp.size();pp++){
		        contenido=contenido.concat(compuestoGetNextSNMPv3Temp.get(pp)+"; ");	
		      }
		      jtf_snmpv3_getNextObjs.setText(contenido);
        		
        	}
        }); 
 
    	jb_snmpv3_GetNextGetNext.addActionListener(new ActionListener(){   
        	public void actionPerformed(ActionEvent e) {   
          	  //aqui lo que hace
          	  if ((jtf_snmpv3_GetNextGetNext.getText()).equals("")){
          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_GetNextGetNext.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	}else{        	  	
          	  	          	  	
					//Para transformar el OID de string al arreglo para pasarselo al GetNext
	          	  	//System.out.println("Tratando el oid");
	          	  	try{
		          	  	int digitos=0;
		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
						while(Token.hasMoreTokens()){
						  Token.nextToken();
						  digitos++;	
						}
		          	  	//System.out.println("el oid tiene "+digitos+"digitos");
		          	  	int tamao=digitos;
		          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
		          	  	int requerimiento[] = new int[tamao];
		          	  	int i=0;
		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
						while(Token1.hasMoreTokens()){
						  requerimiento[i]=Integer.parseInt(Token1.nextToken());
						  i++;
						  //System.out.println("valor de i: "+i);	
						}
		          	  	//requerimiento[i]=0;
		          	  	/*
		          	  	System.out.println("cambiado a arreglo");
		          	  	i++;
		          	  	System.out.println("tamao del arreglo: "+i);
		          	  	for (int j=0;j<i;j++){
		          	  	  System.out.println("d"+j+" valor: "+requerimiento[j]);	
		          	  	}
		          	  	*/
		          	  	
		          	  	boolean esCompuesto=false;
		          	  	compuestoGetNextSNMPv3 = new Vector();
		          	  	if((compuestoGetNextSNMPv3Temp.size())>=1){
		          	  	   compuestoGetNextSNMPv3=compuestoGetNextSNMPv3Temp;
		          	  	   esCompuesto=true;	
		          	  	}else{
		          	  	   compuestoGetNextSNMPv3.add(new OID(requerimiento));
		          	  	   esCompuesto=false;
		          	  	}
		          	  	
		          	  	SNMPv3 manager = new SNMPv3();
	         	  		
	         	  		int nivelSeguridad=0;
	         	  		if ((jcb_snmpv3_getNextModSeg.getSelectedItem())=="AUTH_NOPRIV"){nivelSeguridad=SecurityLevel.AUTH_NOPRIV;}
	         	  		if ((jcb_snmpv3_getNextModSeg.getSelectedItem())=="AUTH_PRIV"){nivelSeguridad=SecurityLevel.AUTH_PRIV;}
	         	  		if ((jcb_snmpv3_getNextModSeg.getSelectedItem())=="NOAUTH_NOPRIV"){nivelSeguridad=SecurityLevel.NOAUTH_NOPRIV;}
	         	  		manager.cambiarIdiomaAMensajes(erroresGenerales04,erroresGenerales29,erroresGenerales30,erroresGenerales31);
		          	  	String respuesta = manager.getNextv3(IP, String.valueOf(pto), user, claveAut, clavePriv, nivelSeguridad,inten, timeOut, compuestoGetNextSNMPv3,metAut,metPriv);
		          	  	compuestoGetNextSNMPv3.removeAllElements();
		          	  	compuestoGetNextSNMPv3Temp.removeAllElements();
					    jtf_snmpv3_getNextObjs.setText("");
		          	  	
		          	  	//System.out.println("GetNextv3: "+respuesta);
		          	  	if (respuesta.equals(erroresGenerales04)){
		          	  	  respuesta=("GetNext SNMPv3: ").concat(respuesta);
		          	  	}else{
		          	  	  //Para el GetNext acumulativo	
		          	  	  if (!esCompuesto){
		          	  	  	int ini = respuesta.indexOf(" ");
			    		  	ini=ini+1;
			    		  	int fin = respuesta.indexOf(" ",ini);
		          	  	  	//System.out.println("actual --"+respuesta.substring(ini,fin)+"--");
		          	  	  	jtf_snmpv3_GetNextGetNext.setText("."+respuesta.substring(ini,fin));
			    		  }
		          	  	  respuesta=("GetNext SNMPv3: ").concat(respuesta);
		          	  	}
		          	  	respuesta=respuesta.concat("\n");
		          	  	jta_snmpv3_GetNextResp.setText((jta_snmpv3_GetNextResp.getText()).concat(respuesta));   
		          	  	
		          	  }catch(NumberFormatException nfe){
	          	  	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
	          	  	  }
          	  	}
          	  }   
         	}
        	});
       	
		
		//----------------------------------Fin de Pantalla de GetNext-----------------------------

		//---------------------------------------Pantalla de GetBulk----------------------------------
		
		jp_snmpv3_GetBulk = new JPanel();
	    //jp_snmpv3_GetBulk.setBackground(Color.white);
		//jp_snmpv3_GetBulk.setBorder(BorderFactory.createTitledBorder("Set"));
		jp_snmpv3_GetBulk.setBounds(new Rectangle(0,30,483,483));
		jp_snmpv3_GetBulk.setBorder(BorderFactory.createTitledBorder("Comando GetBulk"));
		jp_snmpv3_GetBulk.setLayout(null);
		jp_snmpv3_GetBulk.setVisible(false);
		snmpv3.add(jp_snmpv3_GetBulk,null);
		
	    jsp_snmpv3_GetBulkResp = new JScrollPane();
    	jsp_snmpv3_GetBulkResp.setBounds(new Rectangle(10,110,465,361));   
    	jsp_snmpv3_GetBulkResp.setWheelScrollingEnabled(true);
    	jp_snmpv3_GetBulk.add(jsp_snmpv3_GetBulkResp,null);

    	jta_snmpv3_GetBulkResp = new JTextArea();
    	jta_snmpv3_GetBulkResp.setText("");
    	jta_snmpv3_GetBulkResp.setEditable(false);
    	jsp_snmpv3_GetBulkResp.getViewport().add(jta_snmpv3_GetBulkResp,null);
  	
    	//0  <0 se trae todo, si vale 1  >1 se trae solo 1 valor. - pedir por pantalla
    	jl_snmpv3_nonRepe = new JLabel("NonRepeaters");//USO DEL JLABEL
    	jl_snmpv3_nonRepe.setBounds(new Rectangle(10,50,90,20));//establece el xy del componente
    	jp_snmpv3_GetBulk.add(jl_snmpv3_nonRepe,null);
    
    	jtf_snmpv3_nonRepe = new JTextField();//USO DEL JTEXTFIELD    
    	jtf_snmpv3_nonRepe.setBounds(new Rectangle(105,50,30,20));//establece el xy del componente
    	jtf_snmpv3_nonRepe.setText(String.valueOf(NonRepeaters));
    	jp_snmpv3_GetBulk.add(jtf_snmpv3_nonRepe,null);
   	
    	// cantidad de variables que va a retornar - pedir por pantalla
    	jl_snmpv3_maxRep = new JLabel("MaxRepetitions");//USO DEL JLABEL
    	jl_snmpv3_maxRep.setBounds(new Rectangle(150,50,90,20));//establece el xy del componente
    	jp_snmpv3_GetBulk.add(jl_snmpv3_maxRep,null);
   
    	jtf_snmpv3_maxRep = new JTextField();//USO DEL JTEXTFIELD    
    	jtf_snmpv3_maxRep.setBounds(new Rectangle(250,50,30,20));//establece el xy del componente
    	jtf_snmpv3_maxRep.setText(String.valueOf(MaxRepetitions));
    	jp_snmpv3_GetBulk.add(jtf_snmpv3_maxRep,null);

    	jb_snmpv3_GetBulk_add = new JButton("Aadir");
	    jb_snmpv3_GetBulk_add.setBounds(new Rectangle(295,50,79,20));  //9999
	    jb_snmpv3_GetBulk_add.setToolTipText("Presione para agregar el OID.");
	    jp_snmpv3_GetBulk.add(jb_snmpv3_GetBulk_add,null);
	    		
    	jb_snmpv3_GetBulkUndo = new JButton("Deshacer");
	    jb_snmpv3_GetBulkUndo.setBounds(new Rectangle(385,50,89,20));  //9999 
	    jb_snmpv3_GetBulkUndo.setToolTipText("Presione para eliminar el ltimo OID de los objetos.");
	    jp_snmpv3_GetBulk.add(jb_snmpv3_GetBulkUndo,null);
	      	
    	jl_snmpv3_GetBulkEtiSet = new JLabel("OID");
	    jl_snmpv3_GetBulkEtiSet.setBounds(new Rectangle(10,20,20,20));   
	    jp_snmpv3_GetBulk.add(jl_snmpv3_GetBulkEtiSet,null);

	    jtf_snmpv3_GetBulkGetBulk = new JTextField();
	    jtf_snmpv3_GetBulkGetBulk.setBounds(new Rectangle(40,20,225,20));    
	    jtf_snmpv3_GetBulkGetBulk.setEditable(true);
	    jp_snmpv3_GetBulk.add(jtf_snmpv3_GetBulkGetBulk,null);

    	jl_snmpv3_getBulkModSeg = new JLabel("Seguridad");
	    jl_snmpv3_getBulkModSeg.setBounds(new Rectangle(280,20,60,20));
	    jp_snmpv3_GetBulk.add(jl_snmpv3_getBulkModSeg,null);
	    
	    jcb_snmpv3_getBulkModSeg = new JComboBox();//USO DEL JCOMBOBOX
	  	jcb_snmpv3_getBulkModSeg.setBounds(new Rectangle(349,20,125,20));  
		jcb_snmpv3_getBulkModSeg.addItem("AUTH_NOPRIV");
		jcb_snmpv3_getBulkModSeg.addItem("AUTH_PRIV");
		jcb_snmpv3_getBulkModSeg.addItem("NOAUTH_NOPRIV");
		//jcb_snmpv3_getBulkModSeg.addItem("NOAUTH_PRIV");	  	
	  	//jcb_snmpv3_getBulkModSeg.setSelectedIndex(1);	  	 
	  	jp_snmpv3_GetBulk.add(jcb_snmpv3_getBulkModSeg,null);

    	jl_snmpv3_getBulkObjs = new JLabel("Objetos");
	    jl_snmpv3_getBulkObjs.setBounds(new Rectangle(10,80,60,20));
	    jp_snmpv3_GetBulk.add(jl_snmpv3_getBulkObjs,null);
 
	    jtf_snmpv3_getBulkObjs = new JTextField();
	    jtf_snmpv3_getBulkObjs.setBounds(new Rectangle(70,80,310,20));   
	    jtf_snmpv3_getBulkObjs.setEditable(false);	    	    
	    jp_snmpv3_GetBulk.add(jtf_snmpv3_getBulkObjs,null);
	  	
    	jb_snmpv3_GetBulkGetBulk = new JButton("GetBulk");
	    jb_snmpv3_GetBulkGetBulk.setBounds(new Rectangle(395,80,79,20));   
        jb_snmpv3_GetBulkGetBulk.setToolTipText("Presione para obtener los valores.");
	    jp_snmpv3_GetBulk.add(jb_snmpv3_GetBulkGetBulk,null);
		
		compuestoGetBulkSNMPv3Temp = new Vector();
		
		//Para aadir
		jb_snmpv3_GetBulk_add.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        
        	  //aqui lo que hace
          	  if ((jtf_snmpv3_GetBulkGetBulk.getText()).equals("")){
          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	    jta_snmpv3_GetBulkResp.setText("");
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_GetBulkGetBulk.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	  jta_snmpv3_GetBulkResp.setText("");
          	  	}else{ 
          	  		if (!(esNumero(jtf_snmpv3_nonRepe.getText()))){          			
          		  	  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales26,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
          	  		  jta_snmpv3_GetBulkResp.setText("");
          	  		}else{
          	  			if ((!(esNumero(jtf_snmpv3_maxRep.getText())))||(Integer.parseInt(jtf_snmpv3_maxRep.getText())<=0)){          			
          		          JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales27,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
          	  			  jta_snmpv3_GetBulkResp.setText("");
          	  			}else{
							//Para transformar el OID de string al arreglo para pasarselo al GetBulk
			          	  	//System.out.println("Tratando el oid");
			          	  	try{   
				          	  	int digitos=0;
				          	  	StringTokenizer Token = new StringTokenizer (OID,".");
								while(Token.hasMoreTokens()){
								  Token.nextToken();
								  digitos++;	
								}
				          	  	//System.out.println("el oid tiene "+digitos+"digitos");
				          	  	int tamao=digitos;
				          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
				          	  	int requerimiento[] = new int[tamao];
				          	  	int i=0;
				          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
								while(Token1.hasMoreTokens()){
								  requerimiento[i]=Integer.parseInt(Token1.nextToken());
								  i++;
								  //System.out.println("valor de i: "+i);	
								}
				          	  	//requerimiento[i]=0;
				          	  	
				          	  	compuestoGetBulkSNMPv3Temp.add(new OID(requerimiento));
				          	  	String contenido = "";
				          	  	for (int pp=0;pp<compuestoGetBulkSNMPv3Temp.size();pp++){
				          	  	  contenido=contenido.concat(compuestoGetBulkSNMPv3Temp.get(pp)+"; ");	
				          	  	}
				          	  	jtf_snmpv3_getBulkObjs.setText(contenido);
				          	  	
			          	  	  }catch(NumberFormatException nfe){
		          	  	    	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
		          	  	    	jta_snmpv3_GetBulkResp.setText("");
		          	  	  	  }
          	  		  }
          	  		}
          	  	}
          	  }
          	  
         	
        	
        	}
        }); 
        	
        //Para eliminar
        jb_snmpv3_GetBulkUndo.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		
        	  int ultimo = compuestoGetBulkSNMPv3Temp.size();
        	  ultimo=ultimo-1;
        	  if (ultimo>=0){
        	    compuestoGetBulkSNMPv3Temp.removeElementAt(ultimo);
        	  }else{
        	  	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
        	  }
    	  	
		      String contenido = "";
		      for (int pp=0;pp<compuestoGetBulkSNMPv3Temp.size();pp++){
		        contenido=contenido.concat(compuestoGetBulkSNMPv3Temp.get(pp)+"; ");	
		      }
		      jtf_snmpv3_getBulkObjs.setText(contenido);
        		
        	}
        });  
		
		jb_snmpv3_GetBulkGetBulk.addActionListener(new ActionListener(){ 
        	public void actionPerformed(ActionEvent e) {   
          	  //aqui lo que hace
          	  if ((jtf_snmpv3_GetBulkGetBulk.getText()).equals("")){
          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	    jta_snmpv3_GetBulkResp.setText("");
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_GetBulkGetBulk.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	  jta_snmpv3_GetBulkResp.setText("");
          	  	}else{ 
          	  		if (!(esNumero(jtf_snmpv3_nonRepe.getText()))){          			
          		  	  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales26,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
          	  		  jta_snmpv3_GetBulkResp.setText("");
          	  		}else{
          	  			if ((!(esNumero(jtf_snmpv3_maxRep.getText())))||(Integer.parseInt(jtf_snmpv3_maxRep.getText())<=0)){          			
          		          JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales27,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
          	  			  jta_snmpv3_GetBulkResp.setText("");
          	  			}else{
							//Para transformar el OID de string al arreglo para pasarselo al GetBulk
			          	  	//System.out.println("Tratando el oid");
			          	  	try{   
				          	  	int digitos=0;
				          	  	StringTokenizer Token = new StringTokenizer (OID,".");
								while(Token.hasMoreTokens()){
								  Token.nextToken();
								  digitos++;	
								}
				          	  	//System.out.println("el oid tiene "+digitos+"digitos");
				          	  	int tamao=digitos;
				          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
				          	  	int requerimiento[] = new int[tamao];
				          	  	int i=0;
				          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
								while(Token1.hasMoreTokens()){
								  requerimiento[i]=Integer.parseInt(Token1.nextToken());
								  i++;
								  //System.out.println("valor de i: "+i);	
								}
				          	  	//requerimiento[i]=0;
				          	  	
				          	  	jta_snmpv3_GetBulkResp.setText(erroresGenerales28);
				          	  	
				          	  	boolean esCompuesto=false;
				          	  	compuestoGetBulkSNMPv3 = new Vector();
				          	  	if((compuestoGetBulkSNMPv3Temp.size())>=1){
				          	  	   compuestoGetBulkSNMPv3=compuestoGetBulkSNMPv3Temp;
				          	  	   esCompuesto=true;	
				          	  	}else{
				          	  	   compuestoGetBulkSNMPv3.add(new OID(requerimiento));
				          	  	   esCompuesto=false;
				          	  	}
				          	  	
				          	  	SNMPv3 manager = new SNMPv3();
			         	  		int nivelSeguridad=0;
			         	  		if ((jcb_snmpv3_getBulkModSeg.getSelectedItem())=="AUTH_NOPRIV"){nivelSeguridad=SecurityLevel.AUTH_NOPRIV;}
			         	  		if ((jcb_snmpv3_getBulkModSeg.getSelectedItem())=="AUTH_PRIV"){nivelSeguridad=SecurityLevel.AUTH_PRIV;}
			         	  		if ((jcb_snmpv3_getBulkModSeg.getSelectedItem())=="NOAUTH_NOPRIV"){nivelSeguridad=SecurityLevel.NOAUTH_NOPRIV;}
			         	  		manager.cambiarIdiomaAMensajes(erroresGenerales04,erroresGenerales29,erroresGenerales30,erroresGenerales31);
		          	  			String respuesta = manager.getBulkv3(IP, String.valueOf(pto), user, claveAut, clavePriv, nivelSeguridad,inten, timeOut, compuestoGetBulkSNMPv3,Integer.parseInt(jtf_snmpv3_nonRepe.getText()), Integer.parseInt(jtf_snmpv3_maxRep.getText()), metAut, metPriv);
		          	  			compuestoGetBulkSNMPv3.removeAllElements();
				          	  	compuestoGetBulkSNMPv3Temp.removeAllElements();
							    jtf_snmpv3_getBulkObjs.setText("");
		          	  			
		          	  			if (respuesta.equals(erroresGenerales04)){
		          	  	  		  respuesta=erroresGenerales05;
		          	  			}else{
		          	  			  String temp = " ";
								  StringTokenizer Token2 = new StringTokenizer (respuesta,";");
								  while(Token2.hasMoreTokens()){
								    temp=temp.concat(String.valueOf(Token2.nextToken())+("\n"));
								  }
								  respuesta=temp;	
		          	  			}
		          	  			
								jta_snmpv3_GetBulkResp.setText(jta_snmpv3_GetBulkResp.getText().concat(respuesta));
				          	  	
			          	  	  }catch(NumberFormatException nfe){
		          	  	    	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
		          	  	    	jta_snmpv3_GetBulkResp.setText("");
		          	  	  	  }
          	  		  }
          	  		}
          	  	}
          	  }
          	  
         	}   
       	});    

		//----------------------------------Fin de Pantalla de GetBulk--------------------------------
		
		
		//****************************************************************************************************************************
		//GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE--------

		jp_snmpv3_getTable = new JPanel();
	    //jp_snmpv3_getTable.setBackground(Color.blue);
		//jp_snmpv3_getTable.setBorder(BorderFactory.createTitledBorder("Comando GetNext de SNMPv1"));
		jp_snmpv3_getTable.setBounds(new Rectangle(0,30,483,483));  
		jp_snmpv3_getTable.setBorder(BorderFactory.createTitledBorder("Comando GetTable"));
		jp_snmpv3_getTable.setLayout(null);
		jp_snmpv3_getTable.setVisible(false);
		snmpv3.add(jp_snmpv3_getTable,null);
		/*
		JLabel jl_snmpv3_getTableEtigetTable;
		JTextField jtf_snmpv3_getTablegetTable;
		JButton jb_snmpv3_getTablegetTable;
		JScrollPane jsp_snmpv3_getTablegetTable;
		*/

		jl_snmpv3_getTableEtigetTable = new JLabel("OID");
	    jl_snmpv3_getTableEtigetTable.setBounds(new Rectangle(10,20,20,20));    
	    jp_snmpv3_getTable.add(jl_snmpv3_getTableEtigetTable,null);
	    
	    jtf_snmpv3_getTablegetTable = new JTextField();
	    jtf_snmpv3_getTablegetTable.setBounds(new Rectangle(40,20,135,20));  
	    jtf_snmpv3_getTablegetTable.setEditable(true);
	    jp_snmpv3_getTable.add(jtf_snmpv3_getTablegetTable,null);
	    
	    jl_snmpv3_getTableModSeg = new JLabel("Seguridad");
	    jl_snmpv3_getTableModSeg.setBounds(new Rectangle(185,20,60,20));
	    jp_snmpv3_getTable.add(jl_snmpv3_getTableModSeg,null);
	    
	    jcb_snmpv3_getTableModSeg = new JComboBox();//USO DEL JCOMBOBOX
	  	jcb_snmpv3_getTableModSeg.setBounds(new Rectangle(250,20,125,20));  
		jcb_snmpv3_getTableModSeg.addItem("AUTH_NOPRIV");
		jcb_snmpv3_getTableModSeg.addItem("AUTH_PRIV");
		jcb_snmpv3_getTableModSeg.addItem("NOAUTH_NOPRIV");
		//jcb_snmpv3_getTableModSeg.addItem("NOAUTH_PRIV");	  	
	  	//jcb_snmpv3_getTableModSeg.setSelectedIndex(1);	  	 
	  	jp_snmpv3_getTable.add(jcb_snmpv3_getTableModSeg,null);
	    
	    jb_snmpv3_getTablegetTable = new JButton("GetTable");
	    jb_snmpv3_getTablegetTable.setBounds(new Rectangle(384,20,90,20));  //9999
	    jb_snmpv3_getTablegetTable.setToolTipText("Presione para consultar la tabla.");
	    jp_snmpv3_getTable.add(jb_snmpv3_getTablegetTable,null);
	    
	    jsp_snmpv3_getTablegetTable = new JScrollPane();
    	jsp_snmpv3_getTablegetTable.setBounds(new Rectangle(10,50,465,420));  //320,10,435,300
    	jsp_snmpv3_getTablegetTable.setWheelScrollingEnabled(true);
    	jp_snmpv3_getTable.add(jsp_snmpv3_getTablegetTable,null);
    	
    	/*
    	JTextArea jta_snmpv3_getTablegetTable;
    	jta_snmpv3_getTablegetTable = new JTextArea();           
    	jta_snmpv3_getTablegetTable.setText("");
    	jta_snmpv3_getTablegetTable.setEditable(false);
    	jsp_snmpv3_getTablegetTable.getViewport().add(jta_snmpv3_getTablegetTable,null);
		*/

		jb_snmpv3_getTablegetTable.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
          	  //aqui lo que hace
          	  if ((jtf_snmpv3_getTablegetTable.getText()).equals("")){
          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_getTablegetTable.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	}else{        	  	
          	  	        	  	
					//Para transformar el OID de string al arreglo para pasarselo al GetNext
	          	  	//System.out.println("Tratando el oid");
	          	  	try{
		          	  	int digitos=0;
		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
						while(Token.hasMoreTokens()){
						  Token.nextToken();
						  digitos++;	
						}
		          	  	//System.out.println("el oid tiene "+digitos+"digitos");
		          	  	int tamao=digitos;
		          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
		          	  	int requerimiento[] = new int[tamao];
		          	  	int i=0;
		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
						while(Token1.hasMoreTokens()){
						  requerimiento[i]=Integer.parseInt(Token1.nextToken());
						  i++;
						  //System.out.println("valor de i: "+i);	
						}
		          	  	//requerimiento[i]=0;
		          	  	//Para la parte de la tabla
		          	  	getTable traerTabla = new getTable();
		          	  	
		          	  	int nivelSeguridad=0;
			         	if ((jcb_snmpv3_getTableModSeg.getSelectedItem())=="AUTH_NOPRIV"){nivelSeguridad=SecurityLevel.AUTH_NOPRIV;}
			         	if ((jcb_snmpv3_getTableModSeg.getSelectedItem())=="AUTH_PRIV"){nivelSeguridad=SecurityLevel.AUTH_PRIV;}
			         	if ((jcb_snmpv3_getTableModSeg.getSelectedItem())=="NOAUTH_NOPRIV"){nivelSeguridad=SecurityLevel.NOAUTH_NOPRIV;}
		          	  	
		          	  	//recorrer.walkSNMPv3(IP,String.valueOf(pto),user,claveAut,clavePriv,nivelSeguridad,inten,timeOut,requerimiento,limite, metAut, metPriv);
		          	  	traerTabla.cambiarIdiomaAMensajes(erroresGenerales07,erroresGenerales08,erroresGenerales09,erroresGenerales10,erroresGenerales11,erroresGenerales12,erroresGenerales13);
		          	  	Vector columnas = traerTabla.getTablev3(IP,String.valueOf(pto),user,claveAut,clavePriv,nivelSeguridad,inten,timeOut,requerimiento,0, metAut, metPriv);
		          	  	
		          	  	Vector names = traerTabla.getNombreColumnas();
		          	  	traerTabla.limpiarNombreColumnas();
		          	  	//System.out.println("Resultado del walk: "+traerTabla.getWalkRealizado());
		          	  	//traerTabla.limpiarWalkRealizado();
		          	  	//Para la cantidad de columnas
		          	  	Object[] columnNames = new Object[names.size()];
		          	  	for (int k=0;k<(names.size());k++){
      						//System.out.println("Nombre "+(k+1)+": "+String.valueOf(names.get(k)));	
      						columnNames[k]=String.valueOf(names.get(k));
     					}
     					//Para la cantidad de filas
		          	  	int cantidadFilas = 0;
		          	  	for (int k1=0;k1<(columnas.size());k1++){
      						Vector temporalisimo = (Vector)columnas.get(k1);
      						//System.out.println("Cantidad de filas: "+temporalisimo.size());
      						cantidadFilas=temporalisimo.size();
		          	  	}		          	  	
		          	  	Object[][] rowData = new Object[cantidadFilas][names.size()];
		          	  	for (int k1=0;k1<(columnas.size());k1++){
      						Vector temporalisimo = (Vector)columnas.get(k1);
      						//System.out.println("Cantidad de filas: "+temporalisimo.size());
     						for (int k3=0;k3<temporalisimo.size();k3++){
      						  	rowData[k3][k1]=temporalisimo.get(k3);
      						}
      						//System.out.println("columnas "+(k1+1)+": "+String.valueOf(columnas.get(k1)));	
     					}
     					//rowData[2][1]="aqui es 2,1";
		          	  	//System.out.println("Cantidad de columnas: "+names.size());
		          	  	//System.out.println("Cantidad de filas: "+cantidadFilas);
		          	  	//Para cambiar los names de las columnas por el name del nodo
		          	  	for (int k4=0;k4<names.size();k4++){
		          	  	  OID oidTemporalisimo = new OID(String.valueOf(columnNames[k4]));
		          	  	  
		          	  	  try{
		          	  	    Collection listaMibs = (Collection)loadedMibsParaBuscarNombres;	
		          	  	  	Iterator iteradorListaMibs = listaMibs.iterator();
              				while (iteradorListaMibs.hasNext()){
                			  Mib cargada = (Mib)iteradorListaMibs.next();
                			  //System.out.println("OID es: "+oidTemporalisimo);	
				        	  //System.out.println("symbol: "+cargada.getSymbolByOid(String.valueOf(oidTemporalisimo)));
				        	  String symbolEncontrado = String.valueOf(cargada.getSymbolByOid(String.valueOf(oidTemporalisimo)));
			          	  	  //System.out.println("SYMBOL: ---"+cargada.getSymbolByOid(String.valueOf(oidTemporalisimo))+"---");
			          	  	  int ini = symbolEncontrado.indexOf(" ");
				    		  int fin = symbolEncontrado.indexOf(" ",(ini+1));
				    	      symbolEncontrado=(symbolEncontrado.substring(ini,fin)).trim();
				              //System.out.println("encontrado: -"+symbolEncontrado+"-");
				              columnNames[k4]=symbolEncontrado;
              				}
		          	  	  }catch(Exception h){
		          	  	  	//System.out.println("es nulo----------------------------------------------------------------------");
		          	  	  }
		          	  	  
		          	  	}
		          	  	//Para pintar la tabla
		          	  	if((cantidadFilas==0)&&((names.size())==0)){
		          	  	  	//JOptionPane.showMessageDialog(managerSNMP.this,"No se encontraron datos, las causas pueden ser:\n- El OID seleccionado no es una tabla.\n- El agente no tiene datos para la tabla seleccionada.\n- Seleccion el Entry en vez de la tabla como tal.",nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));	
		          	  	  	JTextArea jta_snmpv3_getTablegetTable;
					    	jta_snmpv3_getTablegetTable = new JTextArea(); 
					    	String mensajeError="\n".concat(traerTabla.getErrores());          
					    	traerTabla.limpiarErrores();
					    	if(mensajeError.equals("\n".concat(erroresGenerales07))){
							  mensajeError=mensajeError.concat("\n");
					    	  mensajeError=mensajeError.concat(erroresGenerales06);					    		
					    	}
					    	jta_snmpv3_getTablegetTable.setText(mensajeError);
					    	jta_snmpv3_getTablegetTable.setEditable(false);
					    	jsp_snmpv3_getTablegetTable.getViewport().add(jta_snmpv3_getTablegetTable,null);
		          	  	}else{
			          	  	JTable tablav3 = new JTable(rowData,columnNames);
			          	  	tablav3.getTableHeader().setReorderingAllowed(false);
			          	  	tablav3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			          	  	for (int k5=0;k5<names.size();k5++){
			          	  	  tablav3.getColumn(String.valueOf(columnNames[k5])).setPreferredWidth(130);
			          	  	}
			          	  	tablav3.setEnabled(false);
	    					jsp_snmpv3_getTablegetTable.getViewport().add(tablav3,null);
		          	  	}
		          	  }catch(NumberFormatException nfe){
	          	  	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
	          	  	  }
          	  	}
          	  }
         	}
       	});
		//GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE--------
		//****************************************************************************************************************************		
		

		//----------------------------------Pantalla del Walk--------------------------------------
		jp_snmpv3_walk= new JPanel();
	    //jp_snmpv3_walk.setBackground(Color.white);
	    jp_snmpv3_walk.setBounds(new Rectangle(0,30,483,483));
		jp_snmpv3_walk.setBorder(BorderFactory.createTitledBorder("Comando Walk"));
		jp_snmpv3_walk.setLayout(null);
		jp_snmpv3_walk.setVisible(false);
		snmpv3.add(jp_snmpv3_walk,null);
	
		jl_snmpv3_WalkEtiLimitePregunta = new JLabel("Limitar Cantidad de Variables a Consultar");
	    jl_snmpv3_WalkEtiLimitePregunta.setBounds(new Rectangle(10,20,236,20));   
	    jp_snmpv3_walk.add(jl_snmpv3_WalkEtiLimitePregunta,null);

	    jcb_snmpv3_WalkEtiLimitePregunta = new JComboBox();//USO DEL JCOMBOBOX
	  	jcb_snmpv3_WalkEtiLimitePregunta.setBounds(new Rectangle(256,20,50,20));  
		jcb_snmpv3_WalkEtiLimitePregunta.addItem(opcionSi);
	  	jcb_snmpv3_WalkEtiLimitePregunta.addItem(opcionNo);
	  	jcb_snmpv3_WalkEtiLimitePregunta.setSelectedIndex(1);	  	 
	  	jp_snmpv3_walk.add(jcb_snmpv3_WalkEtiLimitePregunta,null);
		
		jl_snmpv3_WalkEtiLimite = new JLabel("Cantidad");
	    jl_snmpv3_WalkEtiLimite.setBounds(new Rectangle(326,20,50,20));
	    jl_snmpv3_WalkEtiLimite.setEnabled(false);
	    jp_snmpv3_walk.add(jl_snmpv3_WalkEtiLimite,null);

		jtf_snmpv3_WalkEtiLimite = new JTextField();
	    jtf_snmpv3_WalkEtiLimite.setBounds(new Rectangle(386,20,89,20));  
	    jtf_snmpv3_WalkEtiLimite.setEditable(false);
	    jtf_snmpv3_WalkEtiLimite.setText("Sin Lmite");
	    jp_snmpv3_walk.add(jtf_snmpv3_WalkEtiLimite,null);

	  	jcb_snmpv3_WalkEtiLimitePregunta.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e) {	          
	        if ((jcb_snmpv3_WalkEtiLimitePregunta.getSelectedItem())==opcionSi){jtf_snmpv3_WalkEtiLimite.setEditable(true);jtf_snmpv3_WalkEtiLimite.setText("");jl_snmpv3_WalkEtiLimite.setEnabled(true);}
	        if ((jcb_snmpv3_WalkEtiLimitePregunta.getSelectedItem())==opcionNo){jtf_snmpv3_WalkEtiLimite.setEditable(false);jtf_snmpv3_WalkEtiLimite.setText(erroresGenerales21);jl_snmpv3_WalkEtiLimite.setEnabled(false);}
	      }
	    });
				
		jl_snmpv3_WalkEti = new JLabel("OID");
	    jl_snmpv3_WalkEti.setBounds(new Rectangle(10,50,20,20));   
	    jp_snmpv3_walk.add(jl_snmpv3_WalkEti,null);

	    jtf_snmpv3_WalkOID = new JTextField();
	    jtf_snmpv3_WalkOID.setBounds(new Rectangle(40,50,145,20));  
	    jtf_snmpv3_WalkOID.setEditable(true);
	    jp_snmpv3_walk.add(jtf_snmpv3_WalkOID,null);

    	jl_snmpv3_getWalkModSeg = new JLabel("Seguridad");
	    jl_snmpv3_getWalkModSeg.setBounds(new Rectangle(195,50,60,20));
	    jp_snmpv3_walk.add(jl_snmpv3_getWalkModSeg,null);
	    
	    jcb_snmpv3_WalkModSeg = new JComboBox();//USO DEL JCOMBOBOX
	  	jcb_snmpv3_WalkModSeg.setBounds(new Rectangle(260,50,125,20));  
		jcb_snmpv3_WalkModSeg.addItem("AUTH_NOPRIV");
		jcb_snmpv3_WalkModSeg.addItem("AUTH_PRIV");
		jcb_snmpv3_WalkModSeg.addItem("NOAUTH_NOPRIV");
		//jcb_snmpv3_WalkModSeg.addItem("NOAUTH_PRIV");	  	
	  	//jcb_snmpv3_WalkModSeg.setSelectedIndex(1);	  	 
	  	jp_snmpv3_walk.add(jcb_snmpv3_WalkModSeg,null);

	    jb_snmpv3_Walk = new JButton("Walk");
	    jb_snmpv3_Walk.setBounds(new Rectangle(395,50,80,20));   
	    jb_snmpv3_Walk.setToolTipText("Presione para iniciar el Walk.");
	    jp_snmpv3_walk.add(jb_snmpv3_Walk,null);
   
	    jsp_snmpv3_WalkResp = new JScrollPane();
    	jsp_snmpv3_WalkResp.setBounds(new Rectangle(10,80,466,391));   
    	jsp_snmpv3_WalkResp.setWheelScrollingEnabled(true);
    	jp_snmpv3_walk.add(jsp_snmpv3_WalkResp,null);

    	jta_snmpv3_WalkResp = new JTextArea();           
    	jta_snmpv3_WalkResp.setText("");
    	jta_snmpv3_WalkResp.setEditable(false);
    	jsp_snmpv3_WalkResp.getViewport().add(jta_snmpv3_WalkResp,null);
	    
	    jb_snmpv3_Walk.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
          	  //aqui lo que hace
          	  jta_snmpv3_WalkResp.setText(erroresGenerales16);
          	  if ((jtf_snmpv3_WalkOID.getText()).equals("")){
          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	    jta_snmpv3_WalkResp.setText("");
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_WalkOID.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	  jta_snmpv3_WalkResp.setText("");
          	  	}else{ 
          	  		if (((jcb_snmpv3_WalkEtiLimitePregunta.getSelectedItem())==opcionSi)&&(jtf_snmpv3_WalkEtiLimite.getText().equals(""))){
          	  		  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales14,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
          	  		  jta_snmpv3_WalkResp.setText("");
          	  		}else{
          	  			if (((jcb_snmpv3_WalkEtiLimitePregunta.getSelectedItem())==opcionSi)&&(!(esNumero(jtf_snmpv3_WalkEtiLimite.getText())))){
          	  			  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales15,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
          	  			  jta_snmpv3_WalkResp.setText("");
          	  			}else{
							//Para transformar el OID de string al arreglo para pasarselo al GetNext
			          	  	//System.out.println("Tratando el oid");
			          	  	try{   
				          	  	int digitos=0;
				          	  	StringTokenizer Token = new StringTokenizer (OID,".");
								while(Token.hasMoreTokens()){
								  Token.nextToken();
								  digitos++;	
								}
				          	  	//System.out.println("el oid tiene "+digitos+"digitos");
				          	  	int tamao=digitos;
				          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
				          	  	int requerimiento[] = new int[tamao];
				          	  	int i=0;
				          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
								while(Token1.hasMoreTokens()){
								  requerimiento[i]=Integer.parseInt(Token1.nextToken());
								  i++;
								  //System.out.println("valor de i: "+i);	
								}
				          	  	//requerimiento[i]=0;
				          	  	
				          	  	jta_snmpv3_WalkResp.setText(erroresGenerales16);
				          	  	
			         	  		int nivelSeguridad=0;
			         	  		if ((jcb_snmpv3_WalkModSeg.getSelectedItem())=="AUTH_NOPRIV"){nivelSeguridad=SecurityLevel.AUTH_NOPRIV;}
			         	  		if ((jcb_snmpv3_WalkModSeg.getSelectedItem())=="AUTH_PRIV"){nivelSeguridad=SecurityLevel.AUTH_PRIV;}
			         	  		if ((jcb_snmpv3_WalkModSeg.getSelectedItem())=="NOAUTH_NOPRIV"){nivelSeguridad=SecurityLevel.NOAUTH_NOPRIV;}
			         	  		
		          	  			walk recorrer = new walk();
				          	  	int limite = 0;
		          	  			
				          	  	if((jcb_snmpv3_WalkEtiLimitePregunta.getSelectedItem())==opcionSi){
				          	  	  limite = Integer.parseInt(jtf_snmpv3_WalkEtiLimite.getText());	
				          	  	}else{
 				          	  	  limite = 0;	
				          	  	}
				          	  	recorrer.cambiarIdiomaAMensajes(erroresGenerales07,erroresGenerales08,erroresGenerales09,erroresGenerales10,erroresGenerales11,erroresGenerales12,erroresGenerales13,erroresGenerales17,erroresGenerales18,erroresGenerales19,erroresGenerales20);
				          	  	recorrer.walkSNMPv3(IP,String.valueOf(pto),user,claveAut,clavePriv,nivelSeguridad,inten,timeOut,requerimiento,limite, metAut, metPriv);
				          	  	
								jta_snmpv3_WalkResp.setText(jta_snmpv3_WalkResp.getText().concat(recorrer.getWalkRealizado()));
								recorrer.limpiarWalkRealizado();
				          	  	
			          	  	  }catch(NumberFormatException nfe){
		          	  	    	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
		          	  	    	jta_snmpv3_WalkResp.setText("");
		          	  	  	  }
          	  		  }
          	  		}
          	  	}
          	  }
         	}
       	});
		
		//----------------------------------Fin de Pantalla del Walk-------------------------------



		//----------------------------------Pantalla de Set----------------------------------------	      	
	    jp_snmpv3_Set = new JPanel();
	    //jp_snmpv3_Set.setBackground(Color.white);
		//jp_snmpv3_Set.setBorder(BorderFactory.createTitledBorder("Set"));
		jp_snmpv3_Set.setBounds(new Rectangle(0,30,483,483));
		jp_snmpv3_Set.setBorder(BorderFactory.createTitledBorder("Comando Set"));
		jp_snmpv3_Set.setLayout(null);
		jp_snmpv3_Set.setVisible(false);
		snmpv3.add(jp_snmpv3_Set,null);

    	jsp_snmpv3_SetDescrip = new JScrollPane();
    	jsp_snmpv3_SetDescrip.setBounds(new Rectangle(10,20,465,240));  
    	jsp_snmpv3_SetDescrip.setWheelScrollingEnabled(true);
    	jp_snmpv3_Set.add(jsp_snmpv3_SetDescrip,null);

    	jta_snmpv3_SetDescrip = new JTextArea();           
    	jta_snmpv3_SetDescrip.setText("");
    	jta_snmpv3_SetDescrip.setEditable(false);
    	jsp_snmpv3_SetDescrip.getViewport().add(jta_snmpv3_SetDescrip,null);

	    jsp_snmpv3_SetResp = new JScrollPane();
    	jsp_snmpv3_SetResp.setBounds(new Rectangle(10,360,465,111));    
    	jsp_snmpv3_SetResp.setWheelScrollingEnabled(true);
    	jp_snmpv3_Set.add(jsp_snmpv3_SetResp,null);

    	jta_snmpv3_SetResp = new JTextArea();
    	jta_snmpv3_SetResp.setText("");
    	jta_snmpv3_SetResp.setEditable(false);
    	jsp_snmpv3_SetResp.getViewport().add(jta_snmpv3_SetResp,null);
    	
    	jl_snmpv3_SetEtiSet = new JLabel("OID");
	    jl_snmpv3_SetEtiSet.setBounds(new Rectangle(10,270,20,20));   
	    jp_snmpv3_Set.add(jl_snmpv3_SetEtiSet,null);

	    jtf_snmpv3_SetSet = new JTextField();
	    jtf_snmpv3_SetSet.setBounds(new Rectangle(50,270,200,20));    
	    jtf_snmpv3_SetSet.setEditable(true);
	    jp_snmpv3_Set.add(jtf_snmpv3_SetSet,null);

    	jl_snmpv3_SetEtiTipo = new JLabel("Tipo de Dato");
	    jl_snmpv3_SetEtiTipo.setBounds(new Rectangle(260,270,80,20));
	    jl_snmpv3_SetEtiTipo.setEnabled(false);   
	    jp_snmpv3_Set.add(jl_snmpv3_SetEtiTipo,null);
 
	    jcb_snmpv3_SetTipo = new JComboBox();//USO DEL JCOMBOBOX
	  	jcb_snmpv3_SetTipo.setBounds(new Rectangle(339,270,135,20));  // 350,226,70,20
		jcb_snmpv3_SetTipo.addItem("---------------------------");
	  	jcb_snmpv3_SetTipo.addItem("INTEGER");
	  	jcb_snmpv3_SetTipo.addItem("OCTET STRING");
	  	jcb_snmpv3_SetTipo.addItem("OBJECT IDENTIFIER");	  	
	  	jcb_snmpv3_SetTipo.addItem("IpAddress");
	  	jcb_snmpv3_SetTipo.addItem("Counter");
	  	jcb_snmpv3_SetTipo.addItem("Counter64");
	  	jcb_snmpv3_SetTipo.addItem("Gauge");
	  	jcb_snmpv3_SetTipo.addItem("TimeTicks");
	  	jcb_snmpv3_SetTipo.addItem("Opaque");
	  	jcb_snmpv3_SetTipo.setSelectedIndex(0);	  	 
	  	jcb_snmpv3_SetTipo.setEnabled(false);
	  	jp_snmpv3_Set.add(jcb_snmpv3_SetTipo,null);

	  	jcb_snmpv3_SetTipo.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e) {
	      }
	    });

     	jb_snmpv3_setAdd = new JButton("Aadir");
	    jb_snmpv3_setAdd.setBounds(new Rectangle(226,330,79,20));  //9999
	    jb_snmpv3_setAdd.setToolTipText("Presione para agregar el OID.");
	    jb_snmpv3_setAdd.setEnabled(false);    //   ***
	    jp_snmpv3_Set.add(jb_snmpv3_setAdd,null);
	    
    	jb_snmpv3_setUndo = new JButton("Deshacer");
	    jb_snmpv3_setUndo.setBounds(new Rectangle(315,330,89,20));  //9999
	    jb_snmpv3_setUndo.setToolTipText("Presione para eliminar el ltimo OID de los objetos.");
	    jb_snmpv3_setUndo.setEnabled(false);    //   ***
	    jp_snmpv3_Set.add(jb_snmpv3_setUndo,null);
	    
   		jl_snmpv3_setObjs = new JLabel("Objetos");
	    jl_snmpv3_setObjs.setBounds(new Rectangle(10,330,60,20));
	    jp_snmpv3_Set.add(jl_snmpv3_setObjs,null);
	    
	    jtf_snmpv3_setObjs = new JTextField();
	    jtf_snmpv3_setObjs.setBounds(new Rectangle(60,330,150,20));   
	    jtf_snmpv3_setObjs.setEditable(false);	    	    
	    jp_snmpv3_Set.add(jtf_snmpv3_setObjs,null);



/*
 *
 *


    	jb_snmpv2c_SetSet = new JButton("Set");
	    jb_snmpv2c_SetSet.setBounds(new Rectangle(415,330,59,20));   
        jb_snmpv2c_SetSet.setEnabled(false);
        jtf_snmpv2c_SetSetValor.setEnabled(false); 	 
        jtf_snmpv2c_SetSetValor.setText(""); 		 
	    jb_snmpv2c_SetSet.setToolTipText("Presione para especificar el valor.");
	    jp_snmpv2c_Set.add(jb_snmpv2c_SetSet,null);

 *
 *
 *
 **/


	  		  	
	    jl_snmpv3_SetEtiSetValor = new JLabel("Valor");
	    jl_snmpv3_SetEtiSetValor.setBounds(new Rectangle(10,300,35,20));
	    jl_snmpv3_SetEtiSetValor.setEnabled(false);   
	    jp_snmpv3_Set.add(jl_snmpv3_SetEtiSetValor,null);

	    jtf_snmpv3_SetSetValor = new JTextField();
	    jtf_snmpv3_SetSetValor.setBounds(new Rectangle(50,300,214,20));    
	    jtf_snmpv3_SetSetValor.setEditable(true);
	    jp_snmpv3_Set.add(jtf_snmpv3_SetSetValor,null);	 

    	jl_snmpv3_setModSeg = new JLabel("Seguridad");
	    jl_snmpv3_setModSeg.setBounds(new Rectangle(279,300,60,20));
	    jl_snmpv3_setModSeg.setEnabled(false); 
	    jp_snmpv3_Set.add(jl_snmpv3_setModSeg,null);

	    jcb_snmpv3_setModSeg = new JComboBox();//USO DEL JCOMBOBOX
	  	jcb_snmpv3_setModSeg.setBounds(new Rectangle(349,300,125,20));  
		jcb_snmpv3_setModSeg.addItem("AUTH_NOPRIV");
		jcb_snmpv3_setModSeg.addItem("AUTH_PRIV");
		jcb_snmpv3_setModSeg.addItem("NOAUTH_NOPRIV");
		//jcb_snmpv3_setModSeg.addItem("NOAUTH_PRIV");	  	
	  	//jcb_snmpv3_setModSeg.setSelectedIndex(1);
	  	jcb_snmpv3_setModSeg.setEnabled(false);	  	 
	  	jp_snmpv3_Set.add(jcb_snmpv3_setModSeg,null);
	  	
    	jb_snmpv3_SetSet = new JButton("Set");
	    jb_snmpv3_SetSet.setBounds(new Rectangle(415,330,59,20));   
        jb_snmpv3_SetSet.setEnabled(false);
        jtf_snmpv3_SetSetValor.setEnabled(false); 	 
        jtf_snmpv3_SetSetValor.setText("");
	    jb_snmpv3_SetSet.setToolTipText("Presione para especificar el valor.");
	    jp_snmpv3_Set.add(jb_snmpv3_SetSet,null);

		//AQUI DEBE DE IR EL EVENTO DE SETEAR UNA VARIABLE VIA SNMP
		
		//TIPOS DE DATOS DEL SET
       	//tipoDatoReconocido = "INTEGER";
        //tipoDatoReconocido = "OCTET STRING";	
        //tipoDatoReconocido = "OBJECT IDENTIFIER";
        //tipoDatoReconocido = "IPADDRESS";
        //tipoDatoReconocido = "COUNTER";
        //tipoDatoReconocido = "GAUGE";
        //tipoDatoReconocido = "TIMETICKS";
        //tipoDatoReconocido = "OPAQUE";
        //tipoDatoReconocido = "COUNTER64";
        //tipoDatoReconocido="NO RECONOCIDO";
        //reconocido

		compuestoSetSNMPv3TempOID = new Vector();
		compuestoSetSNMPv3TempTipoDatos = new Vector();
		compuestoSetSNMPv3TempDatos = new Vector();		

		//Para aadir
		jb_snmpv3_setAdd.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        
        	  //aqui lo que hace
          	  if ((jtf_snmpv3_SetSet.getText()).equals("")){
          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_SetSet.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	}else{ 
          	  		
          	  		if ((jtf_snmpv3_SetSetValor.getText()).equals("")){
          	  		  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales22,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
          	  		}else{
          	  			if ((jcb_snmpv3_SetTipo.getSelectedItem()).equals("---------------------------")){
          	  			  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales23,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
          	  			}else{
							//Para transformar el OID de string al arreglo para pasarselo al GetNext
			          	  	//System.out.println("Tratando el oid");
			          	  	try{   
				          	  	int digitos=0;
				          	  	StringTokenizer Token = new StringTokenizer (OID,".");
								while(Token.hasMoreTokens()){
								  Token.nextToken();
								  digitos++;	
								}
				          	  	//System.out.println("el oid tiene "+digitos+"digitos");
				          	  	int tamao=digitos;
				          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
				          	  	int requerimiento[] = new int[tamao];
				          	  	int i=0;
				          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
								while(Token1.hasMoreTokens()){
								  requerimiento[i]=Integer.parseInt(Token1.nextToken());
								  i++;
								  //System.out.println("valor de i: "+i);	
								}
				          	  	//requerimiento[i]=0;
				          	  					          	  	
				          	  	Variable valor = null;
				          	  	
				          	  	if(reconocido){
				          	  		boolean datoInvalido=false;
				          	  		
					          	  		tipoDatoReconocido=jcb_snmpv3_SetTipo.getSelectedItem().toString();
					          	  		try{
					          	  			if(tipoDatoReconocido.equals("INTEGER")){valor = new Integer32(Integer.parseInt(String.valueOf(jtf_snmpv3_SetSetValor.getText())));}
					    					if(tipoDatoReconocido.equals("Gauge")){valor = new Gauge32(Long.parseLong(String.valueOf(jtf_snmpv3_SetSetValor.getText())));}
										    if(tipoDatoReconocido.equals("IpAddress")){valor = new IpAddress(String.valueOf(jtf_snmpv3_SetSetValor.getText()));}
										    if(tipoDatoReconocido.equals("OBJECT IDENTIFIER")){valor = new OID(String.valueOf(jtf_snmpv3_SetSetValor.getText()));}
										    if(tipoDatoReconocido.equals("TimeTicks")){valor = new TimeTicks(Long.parseLong(String.valueOf(jtf_snmpv3_SetSetValor.getText())));}
										    if(tipoDatoReconocido.equals("Counter")){valor = new Counter32(Long.parseLong(String.valueOf(jtf_snmpv3_SetSetValor.getText())));}
										    if(tipoDatoReconocido.equals("Counter64")){valor = new Counter64(Long.parseLong(String.valueOf(jtf_snmpv3_SetSetValor.getText())));}
										    if(tipoDatoReconocido.equals("Opaque")){valor = new Opaque(String.valueOf(jtf_snmpv3_SetSetValor.getText()).getBytes());}
										    if(tipoDatoReconocido.equals("OCTET STRING")){valor = new OctetString(String.valueOf(jtf_snmpv3_SetSetValor.getText()));}
					          	  		}catch(Exception e4){datoInvalido=true;}
				          	  		
				          	  		if(!(datoInvalido)){
				          	  			
				          	  			compuestoSetSNMPv3TempOID.add(new OID(requerimiento));
				          	  			compuestoSetSNMPv3TempTipoDatos.add(tipoDatoReconocido);
				          	  			compuestoSetSNMPv3TempDatos.add(String.valueOf(valor));
				          	  			
				          	  			String contenido = "";
								        for (int pp=0;pp<compuestoSetSNMPv3TempOID.size();pp++){
								          contenido=contenido.concat(compuestoSetSNMPv3TempOID.get(pp)+"; ");	
								        }
								        jtf_snmpv3_setObjs.setText(contenido);
								        
				          	  		}else{
				          	  		  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales24,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));				
				          	  		}
		          	  			}else{
		          	  				JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales25,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));			
		          	  			}
			          	  	  }catch(NumberFormatException nfe){
		          	  	    	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
		          	  	  	  }
          	  		  }
          	  		}
          	  	}
          	  }
         	
        	
        	}
        });   
        	
        jb_snmpv3_setUndo.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        	
        	  int ultimo = compuestoSetSNMPv3TempOID.size();
        	  ultimo=ultimo-1;
        	  if (ultimo>=0){
        	    compuestoSetSNMPv3TempOID.removeElementAt(ultimo);
        	    compuestoSetSNMPv3TempTipoDatos.removeElementAt(ultimo);
        	    compuestoSetSNMPv3TempDatos.removeElementAt(ultimo);
        	  }else{
        	  	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
        	  }
    	  	
		      String contenido = "";
		      for (int pp=0;pp<compuestoSetSNMPv3TempOID.size();pp++){
		        contenido=contenido.concat(compuestoSetSNMPv3TempOID.get(pp)+"; ");	
		      }
		      jtf_snmpv3_setObjs.setText(contenido);
        			
        	}
        });


		//EVENTO PARA HACER QUE AL INTRODUCIR EL OID DEL SET SE ACTIVE EL TIPO DE DATOS, VALOR Y BOTON SET
		jtf_snmpv3_SetSetDigitos = 0;
		jtf_snmpv3_SetSet.getDocument().addDocumentListener(new DocumentListener(){
		  public void insertUpdate(DocumentEvent e) {
            //System.out.println("escribi, valor: "+jtf_snmpv1_SetSetDigitos);
            jtf_snmpv3_SetSetDigitos++;
            if (jtf_snmpv3_SetSetDigitos==2){
              //System.out.println("active"); 
              reconocido=true;	
              jcb_snmpv3_SetTipo.setEnabled(true);//combobox
              jb_snmpv3_SetSet.setEnabled(true); //boton
              jtf_snmpv3_SetSetValor.setEnabled(true); //valor	
              jl_snmpv3_SetEtiTipo.setEnabled(true);
              jl_snmpv3_SetEtiSetValor.setEnabled(true);   
              jcb_snmpv3_setModSeg.setEnabled(true);   
              jl_snmpv3_setModSeg.setEnabled(true);
			  jb_snmpv3_setAdd.setEnabled(true);    //   ***
			  jb_snmpv3_setUndo.setEnabled(true);    //   ***        	       	                 
            }
          }
          public void removeUpdate(DocumentEvent e) {
            //System.out.println("borre, valor: "+jtf_snmpv1_SetSetDigitos);
            jtf_snmpv3_SetSetDigitos=0;
          }
          public void changedUpdate(DocumentEvent e) {
            //Plain text components don't fire these events.
          }
		});
		
		jb_snmpv3_SetSet.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
          	  //aqui lo que hace
          	  if ((jtf_snmpv3_SetSet.getText()).equals("")){
          	    JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_SetSet.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(managerSNMP.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	}else{ 
          	  		
          	  		if ((jtf_snmpv3_SetSetValor.getText()).equals("")){
          	  		  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales22,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
          	  		}else{
          	  			if ((jcb_snmpv3_SetTipo.getSelectedItem()).equals("---------------------------")){
          	  			  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales23,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
          	  			}else{
							//Para transformar el OID de string al arreglo para pasarselo al GetNext
			          	  	//System.out.println("Tratando el oid");
			          	  	try{   
				          	  	int digitos=0;
				          	  	StringTokenizer Token = new StringTokenizer (OID,".");
								while(Token.hasMoreTokens()){
								  Token.nextToken();
								  digitos++;	
								}
				          	  	//System.out.println("el oid tiene "+digitos+"digitos");
				          	  	int tamao=digitos;
				          	  	//System.out.println("el requerimiento va a tener "+tamao+" digitos");
				          	  	int requerimiento[] = new int[tamao];
				          	  	int i=0;
				          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
								while(Token1.hasMoreTokens()){
								  requerimiento[i]=Integer.parseInt(Token1.nextToken());
								  i++;
								  //System.out.println("valor de i: "+i);	
								}
				          	  	//requerimiento[i]=0;
				          	  	
				          	  	boolean esCompuesto=false;
				          	  	compuestoSetSNMPv3 = new Vector();
				          	  	if((compuestoSetSNMPv3TempOID.size())>=1){
				          	  	   compuestoSetSNMPv3=compuestoSetSNMPv3TempOID;
				          	  	   esCompuesto=true;	
				          	  	   reconocido=true;
				          	  	}else{
				          	  	   compuestoSetSNMPv3.add(new OID(requerimiento));
				          	  	   esCompuesto=false;
				          	  	}
				          	  	
				          	  	Variable valor = null;
				          	  	
				          	  	if(reconocido){
				          	  		boolean datoInvalido=false;
				          	  		if (!esCompuesto){
					          	  		tipoDatoReconocido=jcb_snmpv3_SetTipo.getSelectedItem().toString();
					          	  		try{
					          	  			if(tipoDatoReconocido.equals("INTEGER")){valor = new Integer32(Integer.parseInt(String.valueOf(jtf_snmpv3_SetSetValor.getText())));}
					    					if(tipoDatoReconocido.equals("Gauge")){valor = new Gauge32(Long.parseLong(String.valueOf(jtf_snmpv3_SetSetValor.getText())));}
										    if(tipoDatoReconocido.equals("IpAddress")){valor = new IpAddress(String.valueOf(jtf_snmpv3_SetSetValor.getText()));}
										    if(tipoDatoReconocido.equals("OBJECT IDENTIFIER")){valor = new OID(String.valueOf(jtf_snmpv3_SetSetValor.getText()));}
										    if(tipoDatoReconocido.equals("TimeTicks")){valor = new TimeTicks(Long.parseLong(String.valueOf(jtf_snmpv3_SetSetValor.getText())));}
										    if(tipoDatoReconocido.equals("Counter")){valor = new Counter32(Long.parseLong(String.valueOf(jtf_snmpv3_SetSetValor.getText())));}
										    if(tipoDatoReconocido.equals("Counter64")){valor = new Counter64(Long.parseLong(String.valueOf(jtf_snmpv3_SetSetValor.getText())));}
										    if(tipoDatoReconocido.equals("Opaque")){valor = new Opaque(String.valueOf(jtf_snmpv3_SetSetValor.getText()).getBytes());}
										    if(tipoDatoReconocido.equals("OCTET STRING")){valor = new OctetString(String.valueOf(jtf_snmpv3_SetSetValor.getText()));}
					          	  		}catch(Exception e4){datoInvalido=true;}
				          	  		}
				          	  		if(!(datoInvalido)){
				          	  			
				          	  			if (!esCompuesto){				          	  			
				          	  			  compuestoSetSNMPv3TempTipoDatos.add(tipoDatoReconocido);
				          	  			  compuestoSetSNMPv3TempDatos.add(String.valueOf(valor));
				          	  			}
				          	  			
				          	  			compuestoSetSNMPv3Valores = new Variable[compuestoSetSNMPv3.size()];
				          	  			
				          	  			for (int pp=0;pp<compuestoSetSNMPv3.size();pp++){
					          	  			if(((compuestoSetSNMPv3TempTipoDatos.get(pp)).toString()).equals("INTEGER")){compuestoSetSNMPv3Valores[pp] = new Integer32(Integer.parseInt(String.valueOf(compuestoSetSNMPv3TempDatos.get(pp))));}
					    					if(((compuestoSetSNMPv3TempTipoDatos.get(pp)).toString()).equals("Gauge")){compuestoSetSNMPv3Valores[pp] = new Gauge32(Long.parseLong(String.valueOf(compuestoSetSNMPv3TempDatos.get(pp))));}
										    if(((compuestoSetSNMPv3TempTipoDatos.get(pp)).toString()).equals("IpAddress")){compuestoSetSNMPv3Valores[pp] = new IpAddress(String.valueOf(compuestoSetSNMPv3TempDatos.get(pp)));}
										    if(((compuestoSetSNMPv3TempTipoDatos.get(pp)).toString()).equals("OBJECT IDENTIFIER")){compuestoSetSNMPv3Valores[pp] = new OID(String.valueOf(compuestoSetSNMPv3TempDatos.get(pp)));}
										    if(((compuestoSetSNMPv3TempTipoDatos.get(pp)).toString()).equals("TimeTicks")){compuestoSetSNMPv3Valores[pp] = new TimeTicks(Long.parseLong(String.valueOf(compuestoSetSNMPv3TempDatos.get(pp))));}
										    if(((compuestoSetSNMPv3TempTipoDatos.get(pp)).toString()).equals("Counter64")){compuestoSetSNMPv3Valores[pp] = new Counter64(Long.parseLong(String.valueOf(compuestoSetSNMPv3TempDatos.get(pp))));}
										    if(((compuestoSetSNMPv3TempTipoDatos.get(pp)).toString()).equals("Counter")){compuestoSetSNMPv3Valores[pp] = new Counter32(Long.parseLong(String.valueOf(compuestoSetSNMPv3TempDatos.get(pp))));}
										    if(((compuestoSetSNMPv3TempTipoDatos.get(pp)).toString()).equals("Opaque")){compuestoSetSNMPv3Valores[pp] = new Opaque(String.valueOf(compuestoSetSNMPv3TempDatos.get(pp)).getBytes());}
										    if(((compuestoSetSNMPv3TempTipoDatos.get(pp)).toString()).equals("OCTET STRING")){compuestoSetSNMPv3Valores[pp] = new OctetString(String.valueOf(compuestoSetSNMPv3TempDatos.get(pp)));}
				          	  			}
				          	  			
				          	  			SNMPv3 manager = new SNMPv3();
					         	  		int nivelSeguridad=0;
					         	  		if ((jcb_snmpv3_setModSeg.getSelectedItem())=="AUTH_NOPRIV"){nivelSeguridad=SecurityLevel.AUTH_NOPRIV;}
					         	  		if ((jcb_snmpv3_setModSeg.getSelectedItem())=="AUTH_PRIV"){nivelSeguridad=SecurityLevel.AUTH_PRIV;}
					         	  		if ((jcb_snmpv3_setModSeg.getSelectedItem())=="NOAUTH_NOPRIV"){nivelSeguridad=SecurityLevel.NOAUTH_NOPRIV;}
					         	  		
					         	  		manager.cambiarIdiomaAMensajes(erroresGenerales04,erroresGenerales29,erroresGenerales30,erroresGenerales31);
									    String respuesta = manager.setv3(IP, String.valueOf(pto), user, claveAut, clavePriv, nivelSeguridad,inten, timeOut, compuestoSetSNMPv3, compuestoSetSNMPv3Valores, metAut, metPriv);
									    
									    compuestoSetSNMPv3.removeAllElements();
		    							compuestoSetSNMPv3TempOID.removeAllElements();
		        	    				compuestoSetSNMPv3TempTipoDatos.removeAllElements();
		        	    				compuestoSetSNMPv3TempDatos.removeAllElements();
								        compuestoSetSNMPv3Valores=null;
								        jtf_snmpv3_setObjs.setText("");
									    
						          	  	//System.out.println("Setv3: "+respuesta);
						          	  	if (respuesta.equals(erroresGenerales04)){
						          	  	  respuesta=("Set SNMPv3: ").concat(respuesta);
						          	  	}else{
						          	  	  respuesta=("Set SNMPv3: ").concat(respuesta);	
						          	  	}
						          	  	respuesta=respuesta.concat("\n");
						          	  	jta_snmpv3_SetResp.setText((jta_snmpv3_SetResp.getText()).concat(respuesta));
						          	  	
				          	  		}else{
				          	  		  JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales24,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));				
				          	  		}
		          	  			}else{
		          	  				JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales25,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));			
		          	  			}
			          	  	  }catch(NumberFormatException nfe){
		          	  	    	JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
		          	  	  	  }
          	  		  }
          	  		}
          	  	}
          	  }
         	}
       	});
		
	    //----------------------------------Fin de Pantalla de Set---------------------------------


	    //----------------------------------Pantalla de Traps--------------------------------------  	
	    //boolean errorPtoTrap = false;
	    /*try{
	      traps = new recibirTrapInform();
	      traps.run();
	    }catch(Exception tp){errorPtoTrap = true;}*/
	    
	    jp_snmpv3_Traps = new JPanel();
	    //jp_snmpv3_Traps.setBackground(Color.yellow);
		//jp_snmpv3_Traps.setBorder(BorderFactory.createTitledBorder("Traps"));
		jp_snmpv3_Traps.setBounds(new Rectangle(0,30,483,223));      //0,30,483,283
		jp_snmpv3_Traps.setBorder(BorderFactory.createTitledBorder("Recepcin de TRAPs SNMP v1/2c/3 e INFORMs SNMP v2c/3"));   //  "Enviar/Ver Traps"   
		jp_snmpv3_Traps.setLayout(null);
		jp_snmpv3_Traps.setVisible(false);
		snmpv3.add(jp_snmpv3_Traps,null);
		
	    jsp_snmpv3_traps = new JScrollPane();//USO DEL JSCROLLPANE
	    jsp_snmpv3_traps.setBounds(new Rectangle(10,20,465,190)); //10,20,465,250      
	    jsp_snmpv3_traps.setWheelScrollingEnabled(true);
	    jp_snmpv3_Traps.add(jsp_snmpv3_traps,null);
	    
	    jta_snmpv3_traps = new JTextArea();//USO DEL JTEXTAREA
	    jta_snmpv3_traps.setEditable(false);
	    jsp_snmpv3_traps.getViewport().add(jta_snmpv3_traps,null);
         
	    jp_snmpv3_TrapsSend = new JPanel();
	    //jp_snmpv3_TrapsSend.setBackground(Color.white);
		//jp_snmpv3_TrapsSend.setBorder(BorderFactory.createTitledBorder("TrapsSend"));
		jp_snmpv3_TrapsSend.setBounds(new Rectangle(0,253,483,253));  //0,313,483,193
		jp_snmpv3_TrapsSend.setBorder(BorderFactory.createTitledBorder("Envo de TRAPs / INFORMs SNMP v3"));
		jp_snmpv3_TrapsSend.setLayout(null);
		jp_snmpv3_TrapsSend.setVisible(false);
		snmpv3.add(jp_snmpv3_TrapsSend,null);

	    jl_snmpv3_TrapSndHost = new JLabel("Direccin IP del destino");     
	    jl_snmpv3_TrapSndHost.setBounds(new Rectangle(10,30,150,20));    
	    jp_snmpv3_TrapsSend.add(jl_snmpv3_TrapSndHost,null);

	    jtf_snmpv3_TrapSndHostIP = new JTextField();
	    jtf_snmpv3_TrapSndHostIP.setBounds(new Rectangle(150,30,150,20));    
	    //jtf_snmpv3_TrapSndHostIP.setEditable(true);
	    jp_snmpv3_TrapsSend.add(jtf_snmpv3_TrapSndHostIP,null);	 

	    jl_snmpv3_TrapSndTipo = new JLabel("Tipo de Trap");			
	    jl_snmpv3_TrapSndTipo.setBounds(new Rectangle(10,80,150,20));    
	    jp_snmpv3_TrapsSend.add(jl_snmpv3_TrapSndTipo,null);

	    jcb_snmpv3_TrapSel = new JComboBox();//USO DEL JCOMBOBOX
	  	jcb_snmpv3_TrapSel.setBounds(new Rectangle(150,80,150,20));  
		jcb_snmpv3_TrapSel.addItem("coldStart");
	  	jcb_snmpv3_TrapSel.addItem("warmStart");
	  	jcb_snmpv3_TrapSel.addItem("linkDown");
	  	jcb_snmpv3_TrapSel.addItem("linkUp");
	  	jcb_snmpv3_TrapSel.addItem("authenticationFailure");	  	
	  	jcb_snmpv3_TrapSel.addItem("Otro");	  	
	  	//jcb_snmpv3_TrapSel.setMaximumRowCount(2);
	  	jp_snmpv3_TrapsSend.add(jcb_snmpv3_TrapSel,null);

	  	jcb_snmpv3_TrapSel.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e) {	        
	        //jtf_snmpv3_EntTxt.setText("");
	        if ((jcb_snmpv3_TrapSel.getSelectedItem())==erroresGenerales46){
	          jl_snmpv3_Enter.setEnabled(true);
	          jtf_snmpv3_EntTxt.setEditable(true);
	          jl_snmpv3_OtroTrp.setEnabled(true);
	          jtf_snmpv3_OtroTrp.setEditable(true);
	          jl_snmpv3_Descr.setEnabled(true);
	          jtf_snmpv3_Descr.setEditable(true);
	          jtf_snmpv3_EntTxt.setText("1.3.6.1.4.1.2789.2005");
	          jtf_snmpv3_OtroTrp.setText("1.3.6.1.4.1.2789.2005.1");
	          jtf_snmpv3_Descr.setText("WWW Server Has Been Restarted");
			  jl_snmpv3_TpDto.setEnabled(true); 
			  jcb_snmpv3_TpoDtoTrp.setEnabled(true);	          
			  jcb_snmpv3_TpoDtoTrp.setSelectedIndex(1);	          
	        }else{
	          jl_snmpv3_Enter.setEnabled(false);
	          jtf_snmpv3_EntTxt.setEditable(false);
	          jl_snmpv3_OtroTrp.setEnabled(false);
	          jtf_snmpv3_OtroTrp.setEditable(false);
	          jl_snmpv3_Descr.setEnabled(false);
	          jtf_snmpv3_Descr.setEditable(false);
	          jtf_snmpv3_EntTxt.setText("");
	          jtf_snmpv3_OtroTrp.setText("");
	          jtf_snmpv3_Descr.setText("");
			  jl_snmpv3_TpDto.setEnabled(false); 
			  jcb_snmpv3_TpoDtoTrp.setEnabled(false);	          
			  jcb_snmpv3_TpoDtoTrp.setSelectedIndex(0);	
	          }
	        }
	    });

		jl_snmpv3_PtoCom = new JLabel("Puerto");
	    jl_snmpv3_PtoCom.setBounds(new Rectangle(310,30,50,20));    
	    jp_snmpv3_TrapsSend.add(jl_snmpv3_PtoCom,null);

	    jtf_snmpv3_PtoComTxt = new JTextField();
	    jtf_snmpv3_PtoComTxt.setBounds(new Rectangle(390,30,85,20));    
	    //jtf_snmpv3_PtoComTxt.setEditable(true);
	    jtf_snmpv3_PtoComTxt.setText("162");
	    jp_snmpv3_TrapsSend.add(jtf_snmpv3_PtoComTxt,null);	 

		jl_snmpv3_TrpVer = new JLabel("Visualizar");//USO DEL JLABEL
    	jl_snmpv3_TrpVer.setBounds(new Rectangle(310,105,160,20));//establece el xy del componente
    	jp_snmpv3_TrapsSend.add(jl_snmpv3_TrpVer,null);

	    jcb_snmpv3_TrpVer = new JComboBox();//USO DEL JCOMBOBOX
	  	jcb_snmpv3_TrpVer.setBounds(new Rectangle(390,105,85,20));  
		jcb_snmpv3_TrpVer.addItem("Si");
	  	jcb_snmpv3_TrpVer.addItem("No");
	  	jcb_snmpv3_TrpVer.setSelectedIndex(1);	  	 
	  	jp_snmpv3_TrapsSend.add(jcb_snmpv3_TrpVer,null);
 
	  	jcb_snmpv3_TrpVer.addActionListener(new ActionListener(){    
	      public void actionPerformed(ActionEvent e) {	          
	        if ((jcb_snmpv3_TrpVer.getSelectedItem())==opcionSi){jpf_snmpv3_TrpUsr.setEchoChar((char)0);jpf_snmpv3_TrpAut.setEchoChar((char)0);jpf_snmpv3_TrpPriv.setEchoChar((char)0);}; 
	        if ((jcb_snmpv3_TrpVer.getSelectedItem())==opcionNo){jpf_snmpv3_TrpUsr.setEchoChar('*');jpf_snmpv3_TrpAut.setEchoChar('*');jpf_snmpv3_TrpPriv.setEchoChar('*');}; 	
	      }
	    });
		
		jl_snmpv3_TrpUsr = new JLabel("Usuario");
	    jl_snmpv3_TrpUsr.setBounds(new Rectangle(310,130,150,20));    
	    jp_snmpv3_TrapsSend.add(jl_snmpv3_TrpUsr,null);    

    	jpf_snmpv3_TrpUsr = new JPasswordField();//USO DEL JPASSWORDFIELD
    	jpf_snmpv3_TrpUsr.setBounds(new Rectangle(390,130,85,20));//establece el xy del componente
    	jpf_snmpv3_TrpUsr.setEchoChar('*');
    	jpf_snmpv3_TrpUsr.setText(String.valueOf(user));
    	//jpf_snmpv3_TrpUsr.setEchoChar((char)0);
    	jp_snmpv3_TrapsSend.add(jpf_snmpv3_TrpUsr,null);
	    	 
    	jl_snmpv3_TrpAut = new JLabel("Clave Auten.");//USO DEL JLABEL
    	jl_snmpv3_TrpAut.setBounds(new Rectangle(310,155,150,20));//establece el xy del componente
    	jp_snmpv3_TrapsSend.add(jl_snmpv3_TrpAut,null);

    	jpf_snmpv3_TrpAut = new JPasswordField();//USO DEL JPASSWORDFIELD
    	jpf_snmpv3_TrpAut.setBounds(new Rectangle(390,155,85,20));//establece el xy del componente
    	jpf_snmpv3_TrpAut.setEchoChar('*');
    	jpf_snmpv3_TrpAut.setText(String.valueOf(claveAut));
    	//jpf_snmpv3_TrpAut.setEchoChar((char)0);
    	jp_snmpv3_TrapsSend.add(jpf_snmpv3_TrpAut,null);

    	jl_snmpv3_TrpPriv = new JLabel("Clave Encrip.");//USO DEL JLABEL
    	jl_snmpv3_TrpPriv.setBounds(new Rectangle(310,180,150,20));//establece el xy del componente
    	jp_snmpv3_TrapsSend.add(jl_snmpv3_TrpPriv,null);

    	jpf_snmpv3_TrpPriv = new JPasswordField();//USO DEL JPASSWORDFIELD
    	jpf_snmpv3_TrpPriv.setBounds(new Rectangle(390,180,85,20));//establece el xy del componente
    	jpf_snmpv3_TrpPriv.setEchoChar('*');
    	jpf_snmpv3_TrpPriv.setText(String.valueOf(clavePriv));
    	//jpf_snmpv3_TrpPriv.setEchoChar((char)0);
    	jp_snmpv3_TrapsSend.add(jpf_snmpv3_TrpPriv,null);

		jl_snmpv3_Int = new JLabel("Intentos");
	    jl_snmpv3_Int.setBounds(new Rectangle(310,55,200,20));    
	    jp_snmpv3_TrapsSend.add(jl_snmpv3_Int,null);

	    jtf_snmpv3_IntTxt = new JTextField();
	    jtf_snmpv3_IntTxt.setBounds(new Rectangle(390,55,85,20));    
	    //jtf_snmpv3_IntTxt.setEditable(true);
	    jtf_snmpv3_IntTxt.setText("3");
	    jp_snmpv3_TrapsSend.add(jtf_snmpv3_IntTxt,null);	 
	    
		jl_snmpv3_Timeout = new JLabel("TimeOut (ms)");
	    jl_snmpv3_Timeout.setBounds(new Rectangle(310,80,150,20));    
	    jp_snmpv3_TrapsSend.add(jl_snmpv3_Timeout,null);

	    jtf_snmpv3_TmoutTxt = new JTextField();
	    jtf_snmpv3_TmoutTxt.setBounds(new Rectangle(390,80,85,20));
	    jtf_snmpv3_TmoutTxt.setText("1500");    
	    //jtf_snmpv3_TmoutTxt.setEditable(true);
	    jp_snmpv3_TrapsSend.add(jtf_snmpv3_TmoutTxt,null);	 
 	    	
		jl_snmpv3_Enter = new JLabel("Enterprise OID");
	    jl_snmpv3_Enter.setBounds(new Rectangle(10,105,150,20));  
	    jl_snmpv3_Enter.setEnabled(false);
	    jp_snmpv3_TrapsSend.add(jl_snmpv3_Enter,null);

	    jtf_snmpv3_EntTxt = new JTextField();
	    jtf_snmpv3_EntTxt.setBounds(new Rectangle(150,105,150,20));	 
	    jtf_snmpv3_EntTxt.setEditable(false);
	    jp_snmpv3_TrapsSend.add(jtf_snmpv3_EntTxt,null);	 

		jl_snmpv3_TrpInfSel = new JLabel("Seleccione una opcin");             
	    jl_snmpv3_TrpInfSel.setBounds(new Rectangle(10,55,150,20)); 
	    jp_snmpv3_TrapsSend.add(jl_snmpv3_TrpInfSel,null);

	    jcb_snmpv3_TrpInfSel = new JComboBox();//USO DEL JCOMBOBOX		 
	  	jcb_snmpv3_TrpInfSel.setBounds(new Rectangle(150,55,150,20));  
		jcb_snmpv3_TrpInfSel.addItem("Trap");
	  	jcb_snmpv3_TrpInfSel.addItem("Inform");
	  	//jcb_snmpv3_TrpInfSel.setMaximumRowCount(2);
	  	jp_snmpv3_TrapsSend.add(jcb_snmpv3_TrpInfSel,null);

	  	jcb_snmpv3_TrpInfSel.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e) {	        
	        if ((jcb_snmpv3_TrpInfSel.getSelectedItem())==erroresGenerales47){
	        	jb_SndTrapv3.setText(erroresGenerales49);
	        	jb_SndTrapv3.setToolTipText(erroresGenerales51);
	        	jl_snmpv3_OtroTrp.setText(erroresGenerales50);
	        	
	  			jcb_snmpv3_TrapSel.setSelectedItem(erroresGenerales46);
	  			jl_snmpv3_TrapSndTipo.setEnabled(false);
	  			jcb_snmpv3_TrapSel.setEnabled(false);
	        	
	        } 
	        if ((jcb_snmpv3_TrpInfSel.getSelectedItem())==erroresGenerales45){
	        	jb_SndTrapv3.setText(erroresGenerales52);
	        	jb_SndTrapv3.setToolTipText(erroresGenerales54);
	        	jl_snmpv3_OtroTrp.setText(erroresGenerales53);
	        	
	        	jcb_snmpv3_TrapSel.setSelectedItem("coldStart");
	        	jl_snmpv3_TrapSndTipo.setEnabled(true);
	        	jcb_snmpv3_TrapSel.setEnabled(true);
	        	
	        } 	
	      }
	    });

		jl_snmpv3_OtroTrp = new JLabel("Trap OID");
	    jl_snmpv3_OtroTrp.setBounds(new Rectangle(10,130,150,20));
	    jl_snmpv3_OtroTrp.setEnabled(false);  	           
	    jp_snmpv3_TrapsSend.add(jl_snmpv3_OtroTrp,null);

	    jtf_snmpv3_OtroTrp = new JTextField();
	    jtf_snmpv3_OtroTrp.setBounds(new Rectangle(150,130,150,20));	//  150
	    //jtf_snmpv3_OtroTrp.setText("1.3.6.1.4.1.2854");
	    jtf_snmpv3_OtroTrp.setEditable(false);		    
	    jp_snmpv3_TrapsSend.add(jtf_snmpv3_OtroTrp,null);	 

		jl_snmpv3_Descr = new JLabel("Descripcin");
	    jl_snmpv3_Descr.setBounds(new Rectangle(10,155,150,20));
	    jl_snmpv3_Descr.setEnabled(false);  	           
	    jp_snmpv3_TrapsSend.add(jl_snmpv3_Descr,null);

	    jtf_snmpv3_Descr = new JTextField();
	    jtf_snmpv3_Descr.setBounds(new Rectangle(150,155,150,20));	 
	    //jtf_snmpv3_Descr.setText("1.3.6.1.4.1.2854");
	    jtf_snmpv3_Descr.setEditable(false);		    
	    jp_snmpv3_TrapsSend.add(jtf_snmpv3_Descr,null);	 

		jl_snmpv3_TpDto = new JLabel("Tipo de Dato");
	    jl_snmpv3_TpDto.setBounds(new Rectangle(10,180,150,20));
	    jl_snmpv3_TpDto.setEnabled(false);  	           
	    jp_snmpv3_TrapsSend.add(jl_snmpv3_TpDto,null);

	    jcb_snmpv3_TpoDtoTrp = new JComboBox();//USO DEL JCOMBOBOX		 
	  	jcb_snmpv3_TpoDtoTrp.setBounds(new Rectangle(150,180,150,20));  
		jcb_snmpv3_TpoDtoTrp.addItem("-------------------------------");
	  	jcb_snmpv3_TpoDtoTrp.addItem("OCTET STRING");	  	
	  	jcb_snmpv3_TpoDtoTrp.addItem("INTEGER");
	  	jcb_snmpv3_TpoDtoTrp.addItem("OBJECT IDENTIFIER");	  	
	  	jcb_snmpv3_TpoDtoTrp.addItem("IpAddress");
	  	//jcb_snmpv3_TpoDtoTrp.addItem("Counter");
	  	//jcb_snmpv3_TpoDtoTrp.addItem("Gauge");
	  	jcb_snmpv3_TpoDtoTrp.addItem("TimeTicks");
	  	//jcb_snmpv3_TpoDtoTrp.addItem("Opaque");
	  	//jcb_snmpv3_TpoDtoTrp.addItem("Counter64");
	  	//jcb_snmpv3_TpoDtoTrp.setMaximumRowCount(2);
	  	jcb_snmpv3_TpoDtoTrp.setEnabled(false);
	  	jp_snmpv3_TrapsSend.add(jcb_snmpv3_TpoDtoTrp,null);

    	jl_snmpv3_trpModSeg = new JLabel("Seguridad");
	    jl_snmpv3_trpModSeg.setBounds(new Rectangle(10,205,150,20));
	    //jl_snmpv3_trpModSeg.setEnabled(false); 
	    jp_snmpv3_TrapsSend.add(jl_snmpv3_trpModSeg,null);

	    jcb_snmpv3_trpModSeg = new JComboBox();//USO DEL JCOMBOBOX
	  	jcb_snmpv3_trpModSeg.setBounds(new Rectangle(150,205,150,20));  
		jcb_snmpv3_trpModSeg.addItem("AUTH_NOPRIV");
		jcb_snmpv3_trpModSeg.addItem("AUTH_PRIV");
		jcb_snmpv3_trpModSeg.addItem("NOAUTH_NOPRIV");
		//jcb_snmpv3_trpModSeg.addItem("NOAUTH_PRIV");	  	
	  	//jcb_snmpv3_trpModSeg.setSelectedIndex(1);
	  	jcb_snmpv3_trpModSeg.setEnabled(true);	  	 
	  	jp_snmpv3_TrapsSend.add(jcb_snmpv3_trpModSeg,null);

	    jb_SndTrapv3 = new JButton("Enviar TRAP");
	    jb_SndTrapv3.setBounds(new Rectangle(310,205,165,20));    
	    jb_SndTrapv3.setToolTipText("Presione para enviar el TRAP.");
	    jp_snmpv3_TrapsSend.add(jb_SndTrapv3,null);
	    
    	jb_SndTrapv3.addActionListener(new ActionListener(){ 
        	public void actionPerformed(ActionEvent e) {  
	          	String men_err = "";
	          	boolean men_err_l = false;
 
          		if (esVacio(jtf_snmpv3_TrapSndHostIP.getText())){          			
          		  men_err = men_err.concat(erroresGenerales36);	 	
          	  	  men_err_l =true;
          	  	}
          	  	
          	  	if (!(ipValida(jtf_snmpv3_TrapSndHostIP.getText()))){
          	  	  men_err = men_err.concat(configParamError02);	 	
          	  	  men_err_l =true;
          	  	}
          	  	
          		if (!(esNumero(jtf_snmpv3_PtoComTxt.getText()))){          			
          		  men_err = men_err.concat(configParamError03);	 	
          	  	  men_err_l =true;
          	  	}

          	  	if (esVacio(jpf_snmpv3_TrpUsr.getText())){          			
          		  men_err = men_err.concat(erroresGenerales55);	 	
          	  	  men_err_l =true;
          	  	}
          	  	          	  	
          	  	//jpf_snmpv3_TrpAut
          	  	if ((esVacio(jpf_snmpv3_TrpAut.getText()))||((String.valueOf(jpf_snmpv3_TrpAut.getText())).length()<8)){          			
          		  men_err = men_err.concat(erroresGenerales56);	 	
          	  	  men_err_l =true;
          	  	}
          	  	
          	  	//jpf_snmpv3_TrpPriv
          	  	if ((esVacio(jpf_snmpv3_TrpPriv.getText()))||((String.valueOf(jpf_snmpv3_TrpPriv.getText())).length()<8)){          			
          		  men_err = men_err.concat(erroresGenerales57);	 	
          	  	  men_err_l =true;
          	  	}
          	  	
          		if (!(esNumero(jtf_snmpv3_IntTxt.getText()))){          			
          		  men_err = men_err.concat(configParamError06);	 	
          	  	  men_err_l =true;
          	  	}

          		if (!(esNumero(jtf_snmpv3_TmoutTxt.getText()))){          			
          		  men_err = men_err.concat(configParamError08);	 	
          	  	  men_err_l =true;
          	  	}
          	  	
          	  	if ((jcb_snmpv3_TrapSel.getSelectedItem())==erroresGenerales46){
          	  		//jtf_snmpv3_EntTxt
          	  		if (esVacio(jtf_snmpv3_EntTxt.getText())){          			
          		  	  men_err = men_err.concat(erroresGenerales33);	 	
          	  	  	  men_err_l =true;
          	  		}
          	  		if (!(esOID(jtf_snmpv3_EntTxt.getText()))){          			
	          		  men_err = men_err.concat(erroresGenerales34);	 	
	          	  	  men_err_l =true;
	          	  	}	
          	  		if ((jcb_snmpv3_TrpInfSel.getSelectedItem())==erroresGenerales47){
          	  			//jtf_snmpv3_OtroTrp
	          	  		if (esVacio(jtf_snmpv3_OtroTrp.getText())){          			
	          		  	  men_err = men_err.concat(erroresGenerales38);	 	
	          	  	  	  men_err_l =true;
	          	  		}
	          	  		if (!(esOID(jtf_snmpv3_OtroTrp.getText()))){          			
		          		  men_err = men_err.concat(erroresGenerales39);	 	
		          	  	  men_err_l =true;
		          	  	}
          	  		}else{
	          	  		//jtf_snmpv3_OtroTrp
	          	  		if (esVacio(jtf_snmpv3_OtroTrp.getText())){          			
	          		  	  men_err = men_err.concat(erroresGenerales40);	 	
	          	  	  	  men_err_l =true;
	          	  		}
	          	  		if (!(esOID(jtf_snmpv3_OtroTrp.getText()))){          			
		          		  men_err = men_err.concat(erroresGenerales41);	 	
		          	  	  men_err_l =true;
		          	  	}
          	  		}
          	  		//jtf_snmpv3_Descr
          	  		if (esVacio(jtf_snmpv3_Descr.getText())){          			
          		  	  men_err = men_err.concat(erroresGenerales42);	 	
          	  	  	  men_err_l =true;
          	  		}
          	  	}
          	  	          	  	
          		if ((esNumero(jtf_snmpv3_IntTxt.getText()))&&(Integer.parseInt(jtf_snmpv3_IntTxt.getText())<=0)){          			
          		  men_err = men_err.concat(configParamError07);	 	
          	  	  men_err_l =true;
          	  	}

          		if ((esNumero(jtf_snmpv3_TmoutTxt.getText()))&&(Integer.parseInt(jtf_snmpv3_TmoutTxt.getText())<=0)){          			
          		  men_err = men_err.concat(configParamError09);	 	
          	  	  men_err_l =true;
          	  	}


          		if (men_err_l){          			
					JOptionPane.showMessageDialog(managerSNMP.this,men_err,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	          	  	          	  
			    	if (esVacio(jtf_snmpv3_TrapSndHostIP.getText())){jtf_snmpv3_TrapSndHostIP.setText("");}
			    	if (!(esNumero(jtf_snmpv3_PtoComTxt.getText()))){jtf_snmpv3_PtoComTxt.setText("162");  }
			    	//new
			    	if (esVacio(jpf_snmpv3_TrpUsr.getText())){jpf_snmpv3_TrpUsr.setText("snmpuser");}
			    	if ((esVacio(jpf_snmpv3_TrpAut.getText()))||((String.valueOf(jpf_snmpv3_TrpAut.getText())).length()<8)){jpf_snmpv3_TrpAut.setText("snmppassword");}
			    	if ((esVacio(jpf_snmpv3_TrpPriv.getText()))||((String.valueOf(jpf_snmpv3_TrpPriv.getText())).length()<8)){jpf_snmpv3_TrpPriv.setText("snmppassword");}
			    	//fin del new
			    	if (!(esNumero(jtf_snmpv3_IntTxt.getText()))){jtf_snmpv3_IntTxt.setText("3");}  
			    	if ((esNumero(jtf_snmpv3_IntTxt.getText()))&&(Integer.parseInt(jtf_snmpv3_IntTxt.getText())<=0)){jtf_snmpv3_IntTxt.setText("3");}
					if (!(esNumero(jtf_snmpv3_TmoutTxt.getText()))){jtf_snmpv3_TmoutTxt.setText("1500");}
			    	if ((esNumero(jtf_snmpv3_TmoutTxt.getText()))&&(Integer.parseInt(jtf_snmpv3_TmoutTxt.getText())<=0)){jtf_snmpv3_TmoutTxt.setText("1500");}			
			    	if ((jcb_snmpv3_TrapSel.getSelectedItem())==erroresGenerales46){
			    		if (esVacio(jtf_snmpv3_EntTxt.getText())){jtf_snmpv3_EntTxt.setText("1.3.6.1.4.1.2789.2005");}
          	  			if (!(esOID(jtf_snmpv3_EntTxt.getText()))){jtf_snmpv3_EntTxt.setText("1.3.6.1.4.1.2789.2005");}	
	          	  		if (esVacio(jtf_snmpv3_OtroTrp.getText())){jtf_snmpv3_OtroTrp.setText("1.3.6.1.4.1.2789.2005.1");}
	          	    	if (!(esOID(jtf_snmpv3_OtroTrp.getText()))){jtf_snmpv3_OtroTrp.setText("1.3.6.1.4.1.2789.2005.1");}
          	  			if (esVacio(jtf_snmpv3_Descr.getText())){jtf_snmpv3_Descr.setText("WWW Server Has Been Restarted");}
			        }
				}else{
					//Para encontrar y validar el tipo de dato de la descripcion cuando sea "Otro" trap
					boolean snmpv3_tipoDatoTrapSeleccionado = true;
					String snmpv3_tipoDatoTrapValor = "";
					if ((jcb_snmpv3_TrapSel.getSelectedItem())==erroresGenerales46){
						if(!((jcb_snmpv3_TpoDtoTrp.getSelectedItem().toString()).equals("-------------------------------"))){
						  Variable valorTemp;
					      String snmpv3_tipoDatoTrapValorTemp=jcb_snmpv3_TpoDtoTrp.getSelectedItem().toString();
					      try{
					        if(snmpv3_tipoDatoTrapValorTemp.equals("OCTET STRING")){valorTemp = new OctetString(String.valueOf(jtf_snmpv3_Descr.getText()));}
					        if(snmpv3_tipoDatoTrapValorTemp.equals("INTEGER")){valorTemp = new Integer32(Integer.parseInt(String.valueOf(jtf_snmpv3_Descr.getText())));}
							if(snmpv3_tipoDatoTrapValorTemp.equals("OBJECT IDENTIFIER")){valorTemp = new OID(String.valueOf(jtf_snmpv3_Descr.getText()));}				        
					        if(snmpv3_tipoDatoTrapValorTemp.equals("IpAddress")){valorTemp = new IpAddress(String.valueOf(jtf_snmpv3_Descr.getText()));}
					        if(snmpv3_tipoDatoTrapValorTemp.equals("TimeTicks")){valorTemp = new TimeTicks(Long.parseLong(String.valueOf(jtf_snmpv3_Descr.getText())));}
					      }catch(Exception e4){
					      	snmpv3_tipoDatoTrapSeleccionado=false;
					        JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales43,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));				
					      }
			          	}else{
			          	   snmpv3_tipoDatoTrapSeleccionado = false;
			          	   JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales44,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));			
			          	   jcb_snmpv3_TpoDtoTrp.setSelectedIndex(1);
			          	}
					}
					//Una vez validado el tipo de dato de "Otro" trap, ahora se arma el trap ha ser enviado
					if (snmpv3_tipoDatoTrapSeleccionado){
					  //System.out.println("se envio el trap.");
					  String tipoTrapsnd = "";
					  String tipoTrapsndSpc = "";
					  if ((jcb_snmpv3_TrapSel.getSelectedItem())=="coldStart"){tipoTrapsnd=String.valueOf(SnmpConstants.coldStart);}
					  if ((jcb_snmpv3_TrapSel.getSelectedItem())=="warmStart"){tipoTrapsnd=String.valueOf(SnmpConstants.warmStart);}
					  if ((jcb_snmpv3_TrapSel.getSelectedItem())=="linkDown"){tipoTrapsnd=String.valueOf(SnmpConstants.linkDown);}
					  if ((jcb_snmpv3_TrapSel.getSelectedItem())=="linkUp"){tipoTrapsnd=String.valueOf(SnmpConstants.linkUp);}
					  if ((jcb_snmpv3_TrapSel.getSelectedItem())=="authenticationFailure"){tipoTrapsnd=String.valueOf(SnmpConstants.authenticationFailure);}
					  if ((jcb_snmpv3_TrapSel.getSelectedItem())==erroresGenerales46){
						tipoTrapsnd=jtf_snmpv3_EntTxt.getText();
						tipoTrapsndSpc=jtf_snmpv3_OtroTrp.getText();
						tipoTrapsndSpc=tipoTrapsndSpc.concat("=");
						String snmpv3_tipoDatoTrapValorTemp=jcb_snmpv3_TpoDtoTrp.getSelectedItem().toString();
						if(snmpv3_tipoDatoTrapValorTemp.equals("OCTET STRING")){tipoTrapsndSpc=tipoTrapsndSpc.concat("{s}");}
					    if(snmpv3_tipoDatoTrapValorTemp.equals("INTEGER")){tipoTrapsndSpc=tipoTrapsndSpc.concat("{i}");}
						if(snmpv3_tipoDatoTrapValorTemp.equals("OBJECT IDENTIFIER")){tipoTrapsndSpc=tipoTrapsndSpc.concat("{o}");}				        
					    if(snmpv3_tipoDatoTrapValorTemp.equals("IpAddress")){tipoTrapsndSpc=tipoTrapsndSpc.concat("{a}");}
					    if(snmpv3_tipoDatoTrapValorTemp.equals("TimeTicks")){tipoTrapsndSpc=tipoTrapsndSpc.concat("{t}");}
						tipoTrapsndSpc=tipoTrapsndSpc.concat(jtf_snmpv3_Descr.getText());
					  }
					  
					  //aqui va el nivel de seguridad
					  int nivelSeguridad=0;
	         	  	  if ((jcb_snmpv3_trpModSeg.getSelectedItem())=="AUTH_NOPRIV"){nivelSeguridad=SecurityLevel.AUTH_NOPRIV;}
	         	  	  if ((jcb_snmpv3_trpModSeg.getSelectedItem())=="AUTH_PRIV"){nivelSeguridad=SecurityLevel.AUTH_PRIV;}
	         	  	  if ((jcb_snmpv3_trpModSeg.getSelectedItem())=="NOAUTH_NOPRIV"){nivelSeguridad=SecurityLevel.NOAUTH_NOPRIV;}
					  
					  enviarTrapInform sndtrap = new enviarTrapInform();
					  traps.cambiarIdiomaAMensajes(mensajesDeTraps01,mensajesDeTraps02,mensajesDeTraps03,mensajesDeTraps04,mensajesDeTraps05,mensajesDeTraps06,mensajesDeTraps07,mensajesDeTraps08,mensajesDeTraps09,mensajesDeTraps10,mensajesDeTraps11,mensajesDeTraps12,mensajesDeTraps13,mensajesDeTraps14,mensajesDeTraps15,mensajesDeTraps16,mensajesDeTraps17,mensajesDeTraps18);
	          	  	  if ((jcb_snmpv3_TrpInfSel.getSelectedItem())==erroresGenerales47){
	          	  	    /*
	          	  	    System.out.println(sndtrap.informv3(jtf_snmpv3_TrapSndHostIP.getText(), jtf_snmpv3_PtoComTxt.getText(), jpf_snmpv3_TrpUsr.getText(), jpf_snmpv3_TrpAut.getText(), jpf_snmpv3_TrpPriv.getText(), nivelSeguridad,Integer.parseInt(jtf_snmpv3_IntTxt.getText()), Integer.parseInt(jtf_snmpv3_TmoutTxt.getText()), tipoTrapsndSpc,tipoTrapsnd));
	          	  	    System.out.println(jtf_snmpv3_TrapSndHostIP.getText());
	          	  	    System.out.println(jtf_snmpv3_PtoComTxt.getText());
	          	  	    System.out.println(jpf_snmpv3_TrpUsr.getText());
	          	  	    System.out.println(jpf_snmpv3_TrpAut.getText());
	          	  	    System.out.println(jpf_snmpv3_TrpPriv.getText());
	          	  	    System.out.println(nivelSeguridad);
	          	  	    System.out.println(Integer.parseInt(jtf_snmpv3_IntTxt.getText()));
	          	  	    System.out.println(Integer.parseInt(jtf_snmpv3_TmoutTxt.getText()));
	          	  	    System.out.println(tipoTrapsndSpc);
	          	  	    System.out.println(tipoTrapsnd);
	          	  	    */
	          	  	    sndtrap.informv3(jtf_snmpv3_TrapSndHostIP.getText(), jtf_snmpv3_PtoComTxt.getText(), jpf_snmpv3_TrpUsr.getText(), jpf_snmpv3_TrpAut.getText(), jpf_snmpv3_TrpPriv.getText(), nivelSeguridad,Integer.parseInt(jtf_snmpv3_IntTxt.getText()), Integer.parseInt(jtf_snmpv3_TmoutTxt.getText()), tipoTrapsndSpc,tipoTrapsnd);
	          	  	    JOptionPane.showMessageDialog(managerSNMP.this, erroresGenerales48 ,nameOfTheProgram,JOptionPane.INFORMATION_MESSAGE,(new ImageIcon("images/help.gif")));	 	          	  	          	  	
	          	  	  }else{
	          	  	    sndtrap.trapv3(jtf_snmpv3_TrapSndHostIP.getText(), jtf_snmpv3_PtoComTxt.getText(), jpf_snmpv3_TrpUsr.getText(),jpf_snmpv3_TrpAut.getText(), jpf_snmpv3_TrpPriv.getText(), nivelSeguridad,Integer.parseInt(jtf_snmpv3_IntTxt.getText()), Integer.parseInt(jtf_snmpv3_TmoutTxt.getText()), tipoTrapsndSpc,tipoTrapsnd);
	          	  	    JOptionPane.showMessageDialog(managerSNMP.this, erroresGenerales35 ,nameOfTheProgram,JOptionPane.INFORMATION_MESSAGE,(new ImageIcon("images/help.gif")));	 	          	  	          	  	
	          	  	  }
	          	  	  
					  //System.out.println("Trap enviado.");
					}
				}
         	}
       	});      
		//----------------------------------Fin de Pantalla de Traps-------------------------------










  	//----------------------------Fin del Manager SNMPv3--------------------------------------
  	
  	
  	
  	
  	//----------------------------VERSIONES DE LOS PROTOCOLOS DE SNMP-------------------------
	  	jp_versiones = new JPanel();
		//jp_versiones.setBackground(Color.black);
		jp_versiones.setBounds(new Rectangle(304,2,488,541));
		jp_versiones.setLayout(null);
		//jp_versiones.setBorder(BorderFactory.createTitledBorder("Versiones de SNMP"));
		//jp_versiones.setVisible(false);
		jpanel.add(jp_versiones,null);
  	
 		jtp_versiones = new JTabbedPane(JTabbedPane.TOP);
	    jtp_versiones.setBounds(new Rectangle(0,0,488,541));
	    jtp_versiones.setForeground(Color.black);
	    jp_versiones.add(jtp_versiones,null);
	    
	    //jtp_versiones.add("SNMPv1",snmpv1);
	    //jtp_versiones.add("SNMPv2c",snmpv2c);
	    jtp_versiones.add("Main Panel",snmpv3);
  	//----------------------------FIN DE VERSIONES DE LOS PROTOCOLOS DE SNMP------------------
  	
  	
  	//--------------------------------USO DEL MENU--------------------------------------------
      //USO DE JMENUBAR
      jmenubar = new JMenuBar();
      jmenubar.setBackground(Color.white);
      jmenubar.setBorderPainted(true);//para que salga el borde pintado
        //USO DEL JMENU
        jm_archive = new JMenu("Archivo");//aade el menu
        jm_archive.setMnemonic('r');//establece el caracter mnemonico del menu
          jmi_outbound = new JMenuItem("Salir");//crea un jmenuitem
          jmi_outbound.setIcon(new ImageIcon("images/exit.gif"));//le asigna un icono
          jmi_outbound.setMnemonic('s');//crea el nemonico del jmenuitem
          jmi_outbound.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));//crea el acceso directo
          //jm_archive.add(jmi_outbound);//adiciona el jmeuitem al menu archive
        jmenubar.add(jm_archive);//adiciona el jmenu a la barra de menu
      
        jm_help = new JMenu("Ayuda");
        jm_help.setMnemonic('u');
          jmi_helpHelp= new JMenuItem("Ayuda del programa");
          jmi_helpHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
          jm_help.add(jmi_helpHelp);
          jm_help.addSeparator();
          jmi_about = new JMenuItem("Acerca de");
          jmi_about.setIcon(new ImageIcon("images/help.gif"));
          jmi_about.setMnemonic('d');
          jmi_about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,ActionEvent.CTRL_MASK));
          //jm_help.add(jmi_about);
        jmenubar.add(jm_help);
        
      //jframe.getContentPane().add(jmenubar,"North");//adiciona el jmenubar al panel
      
      jp_toolbar = new JPanel(true);//se crea el panel sobre el que se van a poner todos los componentes
      jp_toolbar.setBackground(Color.black);//establece el color del panel
      jp_toolbar.setLayout(new BorderLayout());//establece layout nulo, de esta forma puedes poner los componentes en un xy especifico
  	  jframe.getContentPane().add(jp_toolbar,"North");//adiciona el panel a la ventana
      
		String etiquetaIdioma = " ";
		for (int yy=0;yy<165; yy++){
		  etiquetaIdioma=etiquetaIdioma.concat(" ");
		}
		etiquetaIdioma=etiquetaIdioma.concat(" Idioma / Language   ");
  	  	jl_idioma = new JLabel(etiquetaIdioma);
        //jmenubar.add(jl_idioma);
        
        jb_idiomaEs = new JButton();
	    jb_idiomaEs.setToolTipText("Presione para cambiar el idioma a Espaol.");
	    jb_idiomaEs.setIcon(new ImageIcon("images/es.gif"));
	    jb_idiomaEs.setBackground(Color.white);
	    jb_idiomaEs.setBorderPainted(false);
	    jb_idiomaEs.setMargin(new Insets(0,0, 0,0));
	    jmenubar.add(jb_idiomaEs);
	    
	    jb_idiomaEn = new JButton();
	    jb_idiomaEn.setToolTipText("Press to change the language to English.");
	    jb_idiomaEn.setIcon(new ImageIcon("images/en.gif"));
	    jb_idiomaEn.setBackground(Color.white);
	    jb_idiomaEn.setBorderPainted(false);
	    jb_idiomaEn.setMargin(new Insets(0,0, 0,0));
	    jmenubar.add(jb_idiomaEn);
	    
	    /*
	     * Change the comented text to define a new button for the new language.
	     * The flag of the new language has to be 22x14 pixels long, and has to be in the images folder.
	     * Then go to line 7152 to setup the event for this button.
	     *
	    jb_idiomaEn = new JButton();
	    jb_idiomaEn.setToolTipText("Press to change the language to English.");
	    jb_idiomaEn.setIcon(new ImageIcon("images/en.gif"));
	    jb_idiomaEn.setBackground(Color.white);
	    jb_idiomaEn.setBorderPainted(false);
	    jb_idiomaEn.setMargin(new Insets(0,0, 0,0));
	    jmenubar.add(jb_idiomaEn);
	    */
  	  
  	  jp_toolbar.add(jmenubar,BorderLayout.CENTER);//adiciona el jmenubar al panel

	  jmi_helpHelp.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          //System.out.println("se debe abrir el archive de help...");
          
          /*
          try{
		    File path = new File ("helps/helpES.pdf");
			java.awt.Desktop.getDesktop().open(path);
		  }catch(IOException ex){ex.printStackTrace();}
		  */
          
          try{
            //Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler helps/helpES.pdf");
            Process p = Runtime.getRuntime().exec("cmd /c start helps/helpES.pdf");
          }catch(Exception yy){
            //yy.printStackTrace();
            JOptionPane.showMessageDialog(managerSNMP.this,erroresGenerales58,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          }
          
          //helps/helpES.pdf
         }
       });
  	      
      jmi_outbound.addActionListener(new ActionListener(){//este maneja el evento del menu item outbound
        public void actionPerformed(ActionEvent e) {//esta linea tambien es necesaria
          System.exit(0);//sale del sistema
          //lo que se hace si se produce el evento
         }
       });
       
      jb_idiomaEs.addActionListener(new ActionListener(){//este maneja el evento del menu item outbound
        public void actionPerformed(ActionEvent e) {//esta linea tambien es necesaria
          cambiarIdioma("Espaol");
         }
       });
       
      jb_idiomaEn.addActionListener(new ActionListener(){//este maneja el evento del menu item outbound
        public void actionPerformed(ActionEvent e) {//esta linea tambien es necesaria
          cambiarIdioma("English");
         }
       });
       
      /*
       * Change the comented text.
       * Change the name of the button for the one that you defined, and change the parameter of the
       * method cambiarIdioma and write the name of your language.
       * Then go to line 8504 to write the translation.
       *
      jb_idiomaEn.addActionListener(new ActionListener(){//este maneja el evento del menu item outbound
        public void actionPerformed(ActionEvent e) {//esta linea tambien es necesaria
          cambiarIdioma("English");
         }
       });
      */
      
      //todos los componentes items, botones, textfield y otros tienen la misma forma de manejar
      //los eventos producidos por ellos
      
      jmi_about.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
          if(aboutDe.equals("")){
          	  String encabezadoAcercaDe = "Program realizado como Trabajo Especial de Grado\n"+
		      	  						  "presentado ante la ilustre Universidad Central de\n"+
		      	  						  "Venezuela por:\n";
			  String ac3 = 				  "\nPablo Poskal\npabloposkal@gmail.com\n";
		      String ac2 = 				  "\nGustavo Ayala\ngustavoucv@yahoo.com.mx\n";
			  String tutoriado = 	      "\nPara optar al ttulo de Licenciado en Computacin.\n"+
			  							  "\nCon la Tutoria de: "+
			  							  "\n\nProf. Eric Gamess\negamess@kuaimare.ciens.ucv.ve\n\n";
			  String ac4 = 				  "Este programa utiliza las bibliotecas SNMP4J y Mibble.\n"+
		    				   			  "Visite http://www.snmp4j.org y http://www.mibble.org \n"+  	  	
		    				   			  "para mayor informacin.\n\n";
		      String ac = (encabezadoAcercaDe+ac2+ac3+tutoriado+ac4);
      	    aboutDe = ac;
      	    aboutDeTitulo = nameOfTheProgram+" - Acerca de";
          }
          jOptionPaneMessage = new JOptionPane();
          jOptionPaneMessage.showMessageDialog(managerSNMP.this,aboutDe,aboutDeTitulo,JOptionPane.INFORMATION_MESSAGE,(new ImageIcon("images/aboutde.png")));
         }
       });
    //-------------------------------FIN DEL USO DEL MENU-------------------------------------
  	
  	
  	
  	jframe.addWindowListener(new WindowAdapter(){
  	  public void windowClosing(WindowEvent e){
  	    System.exit(0);
  	   }
  	 });
  	
  	//Para iniciar el demonio receptor de traps
  	    boolean errorPtoTrap = false;
	    try{
	      traps = new recibirTrapInform();
	      traps.run();
	    }catch(Exception tp){errorPtoTrap = true;}
	    
	    //Para colocar a escribir mientras siempre en el text area los traps recibidos
//	    new java.util.Timer(true).schedule(new TimerTask(){
//    	  public void run(){	
////			jta_snmpv1_traps.setText(traps.darInformacion());
//			//System.out.println("pedido: "+traps.darInformacion());
//    	  }	
//   		},0,1);
//	    
//	    //Para colocar a escribir mientras siempre en el text area los traps recibidos
//	    new java.util.Timer(true).schedule(new TimerTask(){
//    	  public void run(){	
////			jta_snmpv2c_traps.setText(traps.darInformacion());
//    	  }	
//   		},0,1);
    
	    //Para colocar a escribir mientras siempre en el text area los traps recibidos
	    new java.util.Timer(true).schedule(new TimerTask(){
    	  public void run(){	
			jta_snmpv3_traps.setText(traps.darInformacion());
    	  }	
   		},0,1);
  	//Fin del inicio del demonio receptor de traps
  	
  	//Para establecer / set el idioma / language predeterminado / default
  	cambiarIdioma("English");
  	
  	jframe.setVisible(true); 
  	
  	if (errorPtoTrap){
  	  String errorPto = "Port 162 is busy, only the reception function of TRAPs is not available";
	  JOptionPane.showMessageDialog(managerSNMP.this,errorPto,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
  	}
  }	

  private String idiomaSeleccionado = "Espaol";
  private String opcionSi = "Si";
  private String opcionNo = "No";
  private String configParamError01 = "Debe introducir la direccin IP del agente SNMP.\n";
  private String configParamError02 = "Debe introducir una direccin IP vlida.\n";	
  private String configParamError03 = "Debe introducir el nmero del puerto y este debe ser un valor numrico.\n";	
  private String configParamError04 = "Debe introducir el name de la comunidad de escritura.\n";	
  private String configParamError05 = "Debe introducir el name de la comunidad de lectura.\n";
  private String configParamError06 = "Debe introducir el nmero de intentos y este debe ser un valor numrico.\n";
  private String configParamError07 = "Debe introducir el nmero de intentos y este debe ser un valor numrico mayor a cero.\n";	 	
  private String configParamError08 = "Debe introducir el valor del tiempo de espera y este debe ser un valor numrico.\n";	 	
  private String configParamError09 = "Debe introducir el valor del tiempo de espera y este debe ser un valor numrico mayor a cero.\n";	 	
  private String configParamError10 = "Debe introducir el name de usuario.\n";	
  private String configParamError11 = "Debe introducir la clave de autenticacin.\n";
  private String configParamError12 = "La clave de autenticacin debe tener al menos 8 digitos.\n";
  private String configParamError13 = "Debe introducir la clave de encriptacin.\n";
  private String configParamError14 = "La clave de encriptacin debe tener al menos 8 digitos.\n";
  private String configParamResult01 = "Cambios aplicados con exito.";
  private String erroresGenerales01 = "Debe seleccionar o escribir un OID.";
  private String erroresGenerales02 = "Debe seleccionar o escribir un OID vlido.";
  private String erroresGenerales03 = "No hay ms objetos para eliminar.";
  private String erroresGenerales04 = "\n Tiempo de espera excedido.\n";
  private String erroresGenerales05 = "\nError: Tiempo de espera excedido.\n";
  private String erroresGenerales06 = "No se encontraron datos para el OID especificado, las causas pueden ser:\n- El OID seleccionado no es una tabla.\n- El agente no tiene datos para la tabla seleccionada.\n- Seleccion el Entry en vez de la tabla como tal.";
  private String erroresGenerales07 = "Error: El agente consultado no contiene datos para el OID especificado.\n";
  private String erroresGenerales08 = "Error: El usuario no se encuentra en el Agente.\n";
  private String erroresGenerales09 = "Error: La clave y/o el algoritmo de autenticacin proporcionados son incorrectos.\n";
  private String erroresGenerales10 = "Error: La clave y/o el algoritmo de encriptacin proporcionados son incorrectos.\n";
  private String erroresGenerales11 = "Error: Tiempo de espera excedido.\n";
  private String erroresGenerales12 = "Error: El OID especificado es mayor que el mayor OID de la MIB consultada.\n";
  private String erroresGenerales13 = "La variable recibida no es sucesora de la requerida:\n";
  private String erroresGenerales14 = "Debe escribir la cantidad de variables a consultar.";
  private String erroresGenerales15 = "Debe escribir la cantidad de variables a consultar y debe ser un valor numrico.";
  private String erroresGenerales16 = "\nEjecutando el Walk, espere un momento...\n\n";
  private String erroresGenerales17 = "Total de requerimientos enviados:   ";
  private String erroresGenerales18 = "Total de respuestas recibidas:      ";
  private String erroresGenerales19 = "Tiempo total del Walk:              ";
  private String erroresGenerales20 = " milisegundos";
  private String erroresGenerales21 = "Sin Lmite";
  private String erroresGenerales22 = "Debe escribir el nuevo valor.";
  private String erroresGenerales23 = "Debe seleccionar el tipo de dato del OID.";
  private String erroresGenerales24 = "El nuevo valor no es del tipo de dato de la variable, operacin cancelada.";
  private String erroresGenerales25 = "Tipo de dato desconocido, operacin cancelada.";
  private String erroresGenerales26 = "Debe introducir el valor del NonRepeaters y este debe ser un valor numrico.";
  private String erroresGenerales27 = "Debe introducir el valor de MaxRepetitions y este debe ser un valor numrico mayor a cero.";
  private String erroresGenerales28 = "\nResultado del GetBulk: \n";
  private String erroresGenerales29 = " Error: El usuario no se encuentra en el Agente.\n";
  private String erroresGenerales30 = " Error: La clave y/o el algoritmo de autenticacin proporcionados son incorrectos.\n";
  private String erroresGenerales31 = " Error: La clave y/o el algoritmo de encriptacin proporcionados son incorrectos.\n";
  private String erroresGenerales32 = "Debe introducir el valor del tipo de TRAP especfico y este debe ser un valor numrico.\n";
  private String erroresGenerales33 = "Debe introducir el Enterprise OID.\n";
  private String erroresGenerales34 = "Debe introducir el Enterprise OID y este debe ser OID.\n";
  private String erroresGenerales35 = "TRAP enviado exitosamente.";
  private String erroresGenerales36 = "Debe introducir la direccin IP del destino.\n";
  private String erroresGenerales37 = "Debe introducir el name de la comunidad.\n";
  private String erroresGenerales38 = "Debe introducir el Inform OID.\n";
  private String erroresGenerales39 = "Debe introducir el Inform OID y este debe ser OID.\n";
  private String erroresGenerales40 = "Debe introducir el Trap OID.\n";
  private String erroresGenerales41 = "Debe introducir el Trap OID y este debe ser OID.\n";
  private String erroresGenerales42 = "Debe introducir la Descripcin.\n";
  private String erroresGenerales43 = "La descripcin no es del tipo de dato especificado, operacin cancelada.";
  private String erroresGenerales44 = "Debe seleccionar el tipo de dato de la descripcin, operacin cancelada.";
  private String erroresGenerales45 = "Trap";
  private String erroresGenerales46 = "Otro";
  private String erroresGenerales47 = "Inform";
  private String erroresGenerales48 = "INFORM enviado exitosamente.";
  private String erroresGenerales49 = "Enviar INFORM";
  private String erroresGenerales50 = "Inform OID";
  private String erroresGenerales51 = "Presione para enviar el INFORM.";
  private String erroresGenerales52 = "Enviar TRAP";
  private String erroresGenerales53 = "Trap OID";
  private String erroresGenerales54 = "Presione para enviar el TRAP.";
  private String erroresGenerales55 = "Debe introducir el name de usuario.\n";
  private String erroresGenerales56 = "Debe introducir la clave de autenticacin y esta debe tener al menos 8 digitos.\n";
  private String erroresGenerales57 = "Debe introducir la clave de encriptacin y esta debe tener al menos 8 digitos.\n";
  private String erroresGenerales58 = "Archivo de help no encontrado.";
  
  private String mensajesDeTraps01 = "Esperando TRAPs o INFORMs...\n";
  private String mensajesDeTraps02 = "\nPaquete #";
  private String mensajesDeTraps03 = "Lleg un TRAP SNMP Versin 1.\n";
  private String mensajesDeTraps04 = "Recibido: ";
  private String mensajesDeTraps05 = "IP / Puerto Origen: ";
  private String mensajesDeTraps06 = "Comunidad: ";
  private String mensajesDeTraps07 = "Enterprise: ";
  private String mensajesDeTraps08 = "Tipo de TRAP: ";
  private String mensajesDeTraps09 = "TRAP Especfico: ";
  private String mensajesDeTraps10 = "TimeStamp: ";
  private String mensajesDeTraps11 = "Lleg un INFORM SNMP Versin 2c.\n";
  private String mensajesDeTraps12 = "Lleg un INFORM SNMP Versin 3.\n";
  private String mensajesDeTraps13 = "Usuario: ";
  private String mensajesDeTraps14 = "OID #";
  private String mensajesDeTraps15 = "Valor #";
  private String mensajesDeTraps16 = "Lleg un TRAP SNMP Versin 2c.\n";
  private String mensajesDeTraps17 = "Lleg un TRAP SNMP Versin 3.\n";
  private String mensajesDeTraps18 = "Tipo de TRAP: Otro\n";

  //funcion que se encarga de cambiar el idioma de la interfaz
  public void cambiarIdioma(String idioma){
  	if (idioma.equals("Espaol")){
  	  //System.out.println("Cambiando idioma a espaol");	
  	  //Para la interfaz general------------------------------------------------------------------------------------------
  	  jm_archive.setText("Archivo");//aade el menu
  	  jmi_outbound.setText("Salir");//crea un jmenuitem
  	  jm_help.setText("Ayuda");
  	  jmi_helpHelp.setText("Ayuda del programa");
  	  jmi_about.setText("Acerca de");
  	  jb_mibtree.setText("Importar MIBs");
  	  jb_mibtree.setToolTipText("Presione para importar MIBs.");
  	  aboutDeTitulo = nameOfTheProgram+" - Acerca de";
      String encabezadoAcercaDe = "Program realizado como Trabajo Especial de Grado\n"+
      	  						  "presentado ante la ilustre Universidad Central de\n"+
      	  						  "Venezuela por:\n";
	  String ac3 = 				  "\nPablo Poskal\npabloposkal@gmail.com\n";
      String ac2 = 				  "\nGustavo Ayala\ngustavoucv@yahoo.com.mx\n";
	  String tutoriado = 	      "\nPara optar al ttulo de Licenciado en Computacin.\n"+
	  							  "\nCon la Tutoria de: "+
	  							  "\n\nProf. Eric Gamess\negamess@kuaimare.ciens.ucv.ve\n\n";
	  String ac4 = 				  "Este programa utiliza las bibliotecas SNMP4J y Mibble.\n"+
    				   			  "Visite http://www.snmp4j.org y http://www.mibble.org \n"+  	  	
    				   			  "para mayor informacin.\n\n";
      String ac = (encabezadoAcercaDe+ac2+ac3+tutoriado+ac4);
      aboutDe = ac;
      erroresGenerales58 = "Archivo de help no encontrado.";
      String etiquetaIdioma = " ";
		for (int yy=0;yy<165; yy++){
		  etiquetaIdioma=etiquetaIdioma.concat(" ");
		}
		etiquetaIdioma=etiquetaIdioma.concat(" Idioma / Language   ");
      jl_idioma.setText(etiquetaIdioma);
      //PARA EL COMBO BOX DE ACCION A REALIZAR
        //COMANDOS ADMITIDOS
  	    ConfigureParameters = "Configure Parameters";
  	    ComandoGet = "Comando Get";
  	    ComandoGetNext = "Comando GetNext";
  	    ComandoGetBulk = "Comando GetBulk";
  	    ComandoGetTable = "GetTable";
  	    ComandoWalk = "Walk";
  	    ComandoSet = "Comando Set";
  	    EnviarVerTraps = "Enviar/Recibir Traps";
  	    //COMANDOS EN SNMPv1
//  	    jl_snmpv1_sel.setText("Accin a realizar");
//  	    jcb_snmpv1_sel.removeAllItems();
//  	    jcb_snmpv1_sel.addItem(ConfigureParameters);
//	  	jcb_snmpv1_sel.addItem(ComandoGet);
//	  	jcb_snmpv1_sel.addItem(ComandoGetNext);
//	  	jcb_snmpv1_sel.addItem(ComandoGetTable);
//	  	jcb_snmpv1_sel.addItem(ComandoWalk);
//	  	jcb_snmpv1_sel.addItem(ComandoSet);
//	  	jcb_snmpv1_sel.addItem(EnviarVerTraps);
//  	    //COMANDOS EN SNMPv2C
//  	    jl_snmpv2c_sel.setText("Accin a realizar");
//	  	jcb_snmpv2c_sel.removeAllItems();
//		jcb_snmpv2c_sel.addItem(ConfigureParameters);
//	  	jcb_snmpv2c_sel.addItem(ComandoGet);
//	  	jcb_snmpv2c_sel.addItem(ComandoGetNext);
//	  	jcb_snmpv2c_sel.addItem(ComandoGetBulk);
//	  	jcb_snmpv2c_sel.addItem(ComandoGetTable);
//	  	jcb_snmpv2c_sel.addItem(ComandoWalk);
//	  	jcb_snmpv2c_sel.addItem(ComandoSet);
//	  	jcb_snmpv2c_sel.addItem(EnviarVerTraps);
  	    //COMANDOS EN SNMPv3
  	    jl_snmpv3_sel.setText("Accin a realizar");
  	    jcb_snmpv3_sel.removeAllItems();
  	    jcb_snmpv3_sel.addItem(ConfigureParameters);
  		jcb_snmpv3_sel.addItem(ComandoGet);
  		jcb_snmpv3_sel.addItem(ComandoGetNext);
  		jcb_snmpv3_sel.addItem(ComandoGetBulk);
  		jcb_snmpv3_sel.addItem(ComandoGetTable);
  		jcb_snmpv3_sel.addItem(ComandoWalk);
  		jcb_snmpv3_sel.addItem(ComandoSet);
  		jcb_snmpv3_sel.addItem(EnviarVerTraps);
  		
      //Para SNMPv1-------------------------------------------------------------------------------------------------------
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS----------------
//  	    jp_snmpv1_Con.setBorder(BorderFactory.createTitledBorder("Configure Parameters"));
//  	    jl_snmpv1_IP.setText("Direccin IP del Agente");
//  	    jl_snmpv1_pto.setText("Nro. de Puerto");
//  	    jl_snmpv1_comLec.setText("Comunidad de Lectura");
//  	    jtf_snmpv1_comEsc.setText("Comunidad de Escritura");
//  	    jl_snmpv1_VerCom.setText("Visualizar Comunidades");
//  	    jl_snmpv1_inten.setText("Nro. de Intentos");
//  	    jl_snmpv1_timeOut.setText("Tiempo de Espera (ms)");
//  	    jb_snmpv1_aplicarPara.setText("Aplicar Cambios");
//  	    jb_snmpv1_aplicarPara.setToolTipText("Presione para establecer los nuevos parmetros.");
  	    configParamError01 = "Debe introducir la direccin IP del agente SNMP.\n";
  		configParamError02 = "Debe introducir una direccin IP vlida.\n";	
  		configParamError03 = "Debe introducir el nmero del puerto y este debe ser un valor numrico.\n";	
	    configParamError04 = "Debe introducir el name de la comunidad de escritura.\n";	
	    configParamError05 = "Debe introducir el name de la comunidad de lectura.\n";
	    configParamError06 = "Debe introducir el nmero de intentos y este debe ser un valor numrico.\n";
	    configParamError07 = "Debe introducir el nmero de intentos y este debe ser un valor numrico mayor a cero.\n";	 	
	    configParamError08 = "Debe introducir el valor del tiempo de espera y este debe ser un valor numrico.\n";	 	
	    configParamError09 = "Debe introducir el valor del tiempo de espera y este debe ser un valor numrico mayor a cero.\n";	 	
	    configParamResult01 = "Cambios aplicados con exito.";
  	    opcionSi = "Si";
  		opcionNo = "No";
//  	    jcb_snmpv1_VerCom.removeAllItems();
//  	    jcb_snmpv1_VerCom.addItem(opcionSi);
//  	    jcb_snmpv1_VerCom.addItem(opcionNo);
//  	    jcb_snmpv1_VerCom.setSelectedIndex(1);	  	 
//  	    //--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS----------------
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET----
//  	    jp_snmpv1_Get.setBorder(BorderFactory.createTitledBorder("Comando Get"));
//  	    jl_snmpv1_getEtiGet.setText("OID");
//  	    jb_snmpv1_add.setText("Aadir");
//  	    jb_snmpv1_add.setToolTipText("Presione para agregar el OID.");
//  	    jl_snmpv1_getObjs.setText("Objetos");
//  	    jb_snmpv1_undo.setText("Deshacer");
//  	    jb_snmpv1_undo.setToolTipText("Presione para eliminar el ltimo OID aadido.");
//  	    jb_snmpv1_getGet.setText("Get");
//  	    jb_snmpv1_getGet.setToolTipText("Presione para obtener el(los) valor(es).");
  	    erroresGenerales01 = "Debe seleccionar o escribir un OID.";
  	    erroresGenerales02 = "Debe seleccionar o escribir un OID vlido.";
  	    erroresGenerales03 = "No hay ms objetos para eliminar.";
  	    erroresGenerales04 = "\n Tiempo de espera excedido.\n";
  	    //--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET----
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--
//  	    jp_snmpv1_GetNext.setBorder(BorderFactory.createTitledBorder("Comando GetNext"));
//  	    jl_snmpv1_GetNextEtiGetNext.setText("OID");
//  	    jb_snmpv1_GetNext_add.setText("Aadir");
//  	    jb_snmpv1_GetNext_add.setToolTipText("Presione para agregar el OID.");
//  	    jl_snmpv1_getNextObjs.setText("Objetos");
//  	    jb_snmpv1_GetNextUndo.setText("Deshacer");
//  	    jb_snmpv1_GetNextUndo.setToolTipText("Presione para eliminar el ltimo OID aadido.");
//  	    jb_snmpv1_GetNextGetNext.setText("GetNext");
//  	    jb_snmpv1_GetNextGetNext.setToolTipText("Presione para obtener el(los) valor(es).");
//  	    //--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE
//  	    jp_snmpv1_getTable.setBorder(BorderFactory.createTitledBorder("GetTable"));
//  	    jl_snmpv1_getTableEtigetTable.setText("OID");
//  	    jb_snmpv1_getTablegetTable.setText("GetTable");
//  	    jb_snmpv1_getTablegetTable.setToolTipText("Presione para consultar la tabla.");
  	    erroresGenerales05 = "\nError: Tiempo de espera excedido.\n";
  	    erroresGenerales06 = "No se encontraron datos para el OID especificado, las causas pueden ser:\n- El OID seleccionado no es una tabla.\n- El agente no tiene datos para la tabla seleccionada.\n- Seleccion el Entry en vez de la tabla como tal.";
  	    erroresGenerales07 = "Error: El agente consultado no contiene datos para el OID especificado.\n";
  	    erroresGenerales08 = "Error: El usuario no se encuentra en el Agente.\n";
  	    erroresGenerales09 = "Error: La clave y/o el algoritmo de autenticacin proporcionados son incorrectos.\n";
  	    erroresGenerales10 = "Error: La clave y/o el algoritmo de encriptacin proporcionados son incorrectos.\n";
  	    erroresGenerales11 = "Error: Tiempo de espera excedido.\n";
  	    erroresGenerales12 = "Error: El OID especificado es mayor que el mayor OID de la MIB consultada.\n";
  	    erroresGenerales13 = "La variable recibida no es sucesora de la requerida:\n";
  	    //--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK
//  	    jp_snmpv1_walk.setBorder(BorderFactory.createTitledBorder("Walk"));
//  	    jl_snmpv1_WalkEtiLimitePregunta.setText("Limitar Cantidad de Variables a Consultar");
//  	    jcb_snmpv1_WalkEtiLimitePregunta.removeAllItems();
//  	    jcb_snmpv1_WalkEtiLimitePregunta.addItem(opcionSi);
//  	    jcb_snmpv1_WalkEtiLimitePregunta.addItem(opcionNo);
//  	    jcb_snmpv1_WalkEtiLimitePregunta.setSelectedIndex(1);
//	  	jl_snmpv1_WalkEtiLimite.setText("Cantidad");
//	  	erroresGenerales21 = "Sin Lmite";
//	  	jtf_snmpv1_WalkEtiLimite.setText(erroresGenerales21);
//	  	jl_snmpv1_WalkEti.setText("OID");
//	  	jb_snmpv1_Walk.setText("Walk");
//	  	jb_snmpv1_Walk.setToolTipText("Presione para iniciar el Walk.");
//	  	erroresGenerales14 = "Debe escribir la cantidad de variables a consultar.";
//	  	erroresGenerales15 = "Debe escribir la cantidad de variables a consultar y debe ser un valor numrico.";
//	  	erroresGenerales16 = "\nEjecutando el Walk, espere un momento...\n\n";
//	  	erroresGenerales17 = "Total de requerimientos enviados:   ";
//	  	erroresGenerales18 = "Total de respuestas recibidas:      ";
//	  	erroresGenerales19 = "Tiempo total del Walk:              ";
//	  	erroresGenerales20 = " milisegundos";
  	    //--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	     	    
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET
//  	    jp_snmpv1_Set.setBorder(BorderFactory.createTitledBorder("Comando Set"));
//  	    jl_snmpv1_SetEtiSet.setText("OID");
//  	    jl_snmpv1_SetEtiTipo.setText("Tipo de Dato");
//  	    jl_snmpv1_SetEtiSetValor.setText("Valor");
//  	    jb_snmpv1_setAdd.setText("Aadir");
//  	    jb_snmpv1_setAdd.setToolTipText("Presione para agregar el OID.");
//  	    jl_snmpv1_setObjs.setText("Objetos");
//  	    jb_snmpv1_setUndo.setText("Deshacer");
//  	    jb_snmpv1_setUndo.setToolTipText("Presione para eliminar el ltimo OID aadido.");
//  	    jb_snmpv1_SetSet.setText("Set");
//  	    jb_snmpv1_SetSet.setToolTipText("Presione para especificar el(los) valor(es).");
//  	    erroresGenerales22 = "Debe escribir el nuevo valor.";
//  	    erroresGenerales23 = "Debe seleccionar el tipo de dato del OID.";
//  	    erroresGenerales24 = "El nuevo valor no es del tipo de dato de la variable, operacin cancelada.";
//  	    erroresGenerales25 = "Tipo de dato desconocido, operacin cancelada.";
//  	    //--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET
//  	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 	    
//	
//	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////	  		
//  	    //--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
//  	    jp_snmpv1_Traps.setBorder(BorderFactory.createTitledBorder("Recepcin de TRAPs SNMP v1/2c/3 e INFORMs SNMP v2c/3"));  
//  	    jp_snmpv1_TrapsSend.setBorder(BorderFactory.createTitledBorder("Envo de TRAPs SNMP v1"));
//  	    jl_snmpv1_TrapSndHost.setText("Direccion IP del destino");
//  	    jl_snmpv1_TrapSndTipo.setText("Tipo de Trap");
//  	    jl_snmpv1_PtoCom.setText("Puerto");
//  	    jl_snmpv1_Com.setText("Comunidad");
//  	    jl_snmpv1_Int.setText("Intentos");
//  	    //jl_snmpv1_Timeout.setText("TimeOut (ms)");
//  	    jl_snmpv1_Timeout.setText("Tiempo de Es.");
//  	    jl_snmpv1_Enter.setText("Enterprise OID");
//  	    jl_snmpv1_TrapSpc.setText("Trap Especfico");
//  	    jb_SndTrap.setText("Enviar TRAP");
//  	    jb_SndTrap.setToolTipText("Presione para enviar el TRAP.");
//  	    erroresGenerales32 = "Debe introducir el valor del tipo de TRAP especfico y este debe ser un valor numrico.\n";
//  	    erroresGenerales33 = "Debe introducir el Enterprise OID.\n";
//  	    erroresGenerales34 = "Debe introducir el Enterprise OID y este debe ser OID.\n";
//  	    erroresGenerales35 = "TRAP enviado exitosamente.";
//  	    erroresGenerales36 = "Debe introducir la direccin IP del destino.\n";
//  	    erroresGenerales37 = "Debe introducir el name de la comunidad.\n";
//  	      	      	    
//  	    mensajesDeTraps01 = "Esperando TRAPs o INFORMs...\n";
//  	    mensajesDeTraps02 = "\nPaquete #";
//  	    mensajesDeTraps03 = "Lleg un TRAP SNMP Versin 1.\n";
//  	    mensajesDeTraps04 = "Recibido: ";
//  	    mensajesDeTraps05 = "IP / Puerto Origen: ";
//  	    mensajesDeTraps06 = "Comunidad: ";
//  	    mensajesDeTraps07 = "Enterprise: ";
//  	    mensajesDeTraps08 = "Tipo de TRAP: ";
//  	    mensajesDeTraps09 = "TRAP Especfico: ";
//  	    mensajesDeTraps10 = "TimeStamp: ";
//  	    mensajesDeTraps11 = "Lleg un INFORM SNMP Versin 2c.\n";
//  	    mensajesDeTraps12 = "Lleg un INFORM SNMP Versin 3.\n";
//  	    mensajesDeTraps13 = "Usuario: ";
//  	    mensajesDeTraps14 = "OID #";
//  	    mensajesDeTraps15 = "Valor #";
//  	    mensajesDeTraps16 = "Lleg un TRAP SNMP Versin 2c.\n";
//  	    mensajesDeTraps17 = "Lleg un TRAP SNMP Versin 3.\n";
//  	    mensajesDeTraps18 = "Tipo de TRAP: Otro\n";
//  	    //--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	      	     	    
//  	  //Fin de SNMPv1-----------------------------------------------------------------------------------------------------  
//  	  
//  	  //Para SNMPv2c-------------------------------------------------------------------------------------------------------
//  	    
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS----------------
//  	    jp_snmpv2c_Con.setBorder(BorderFactory.createTitledBorder("Configure Parameters"));
//  	    jl_snmpv2c_IP.setText("Direccin IP del Agente");
//  	    jl_snmpv2c_pto.setText("Nro. de Puerto");
//  	    jl_snmpv2c_comLec.setText("Comunidad de Lectura");
//  	    jl_snmpv2c_comEsc.setText("Comunidad de Escritura");
//  	    jl_snmpv2c_VerCom.setText("Visualizar Comunidades");
//  	    jl_snmpv2c_inten.setText("Nro. de Intentos");
//  	    jl_snmpv2c_timeOut.setText("Tiempo de Espera (ms)");
//  	    jb_snmpv2c_aplicarPara.setText("Aplicar Cambios");
//  	    jb_snmpv2c_aplicarPara.setToolTipText("Presione para establecer los nuevos parmetros.");
//  	    opcionSi = "Si";
//  		opcionNo = "No";
//  	    jcb_snmpv2c_VerCom.removeAllItems();
//  	    jcb_snmpv2c_VerCom.addItem(opcionSi);
//  	    jcb_snmpv2c_VerCom.addItem(opcionNo);
//  	    jcb_snmpv2c_VerCom.setSelectedIndex(1);	  	 
//  	    //--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS----------------
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET----
//  	    jp_snmpv2c_Get.setBorder(BorderFactory.createTitledBorder("Comando Get"));
//  	    jl_snmpv2c_getEtiGet.setText("OID");
//  	    jb_snmpv2c_getAdd.setText("Aadir");
//  	    jb_snmpv2c_getAdd.setToolTipText("Presione para agregar el OID.");
//  	    jl_snmpv2c_getObjs.setText("Objetos");
//  	    jb_snmpv2c_getUndo.setText("Deshacer");
//  	    jb_snmpv2c_getUndo.setToolTipText("Presione para eliminar el ltimo OID aadido.");
//  	    jb_snmpv2c_getGet.setText("Get");
//  	    jb_snmpv2c_getGet.setToolTipText("Presione para obtener el(los) valor(es).");
//  	    //--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET----
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    
//		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--
//  	    jp_snmpv2c_GetNext.setBorder(BorderFactory.createTitledBorder("Comando GetNext"));
//  	    jl_snmpv2c_GetNextEtiGetNext.setText("OID");
//  	    jb_snmpv2c_GetNext_add.setText("Aadir");
//  	    jb_snmpv2c_GetNext_add.setToolTipText("Presione para agregar el OID.");
//  	    jl_snmpv2c_getNextObjs.setText("Objetos");
//  	    jb_snmpv2c_GetNextUndo.setText("Deshacer");
//  	    jb_snmpv2c_GetNextUndo.setToolTipText("Presione para eliminar el ltimo OID aadido.");
//  	    jb_snmpv2c_GetNextGetNext.setText("GetNext");
//  	    jb_snmpv2c_GetNextGetNext.setToolTipText("Presione para obtener el(los) valor(es).");
//  	    //--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--
//  	    jp_snmpv2c_GetBulk.setBorder(BorderFactory.createTitledBorder("Comando GetBulk"));
//  	    jl_snmpv2c_nonRepe.setText("NonRepeaters");
//  	    jl_snmpv2c_maxRep.setText("MaxRepetitions");
//  	    jb_snmpv2c_GetBulk_add.setText("Aadir");
//  	    jb_snmpv2c_GetBulk_add.setToolTipText("Presione para agregar el OID.");
//  	    jb_snmpv2c_GetBulkUndo.setText("Deshacer");
//  	    jb_snmpv2c_GetBulkUndo.setToolTipText("Presione para eliminar el ltimo OID aadido.");
//  	    jl_snmpv2c_getBulkObjs.setText("Objetos");
//  	    jl_snmpv2c_GetBulkEtiSet.setText("OID");
//  	    jb_snmpv2c_GetBulkGetBulk.setText("GetBulk");
//  	    jb_snmpv2c_GetBulkGetBulk.setToolTipText("Presione para obtener el(los) valor(es).");
//  	    erroresGenerales26 = "Debe introducir el valor del NonRepeaters y este debe ser un valor numrico.";
//  	    erroresGenerales27 = "Debe introducir el valor de MaxRepetitions y este debe ser un valor numrico mayor a cero.";
//  	    erroresGenerales28 = "\nResultado del GetBulk: \n";
//  	    //--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE
//  	    jp_snmpv2c_getTable.setBorder(BorderFactory.createTitledBorder("GetTable"));
//  	    jl_snmpv2c_getTableEtigetTable.setText("OID");
//  	    jb_snmpv2c_getTablegetTable.setText("GetTable");
//  	    jb_snmpv2c_getTablegetTable.setToolTipText("Presione para consultar la tabla.");
//  	    //--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	     	    
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK
//  	    jp_snmpv2c_walk.setBorder(BorderFactory.createTitledBorder("Walk"));
//  	    jl_snmpv2c_WalkEtiLimitePregunta.setText("Limitar Cantidad de Variables a Consultar");
//  	    jcb_snmpv2c_WalkEtiLimitePregunta.removeAllItems();
//  	    jcb_snmpv2c_WalkEtiLimitePregunta.addItem(opcionSi);
//  	    jcb_snmpv2c_WalkEtiLimitePregunta.addItem(opcionNo);
//  	    jcb_snmpv2c_WalkEtiLimitePregunta.setSelectedIndex(1);
//	  	jl_snmpv2c_WalkEtiLimite.setText("Cantidad");
//	  	jtf_snmpv1_WalkEtiLimite.setText(erroresGenerales21);
//	  	jl_snmpv2c_WalkEti.setText("OID");
//	  	jb_snmpv2c_Walk.setText("Walk");
//	  	jb_snmpv2c_Walk.setToolTipText("Presione para iniciar el Walk.");
//  	    //--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK
//  	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 	    
//  	     	    
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET
//  	    jp_snmpv2c_Set.setBorder(BorderFactory.createTitledBorder("Comando Set"));
//  	    jl_snmpv2c_SetEtiSet.setText("OID");
//  	    jl_snmpv2c_SetEtiTipo.setText("Tipo de Dato");
//  	    jl_snmpv2c_SetEtiSetValor.setText("Valor");
//  	    jb_snmpv2c_setAdd.setText("Aadir");
//  	    jb_snmpv2c_setAdd.setToolTipText("Presione para agregar el OID.");
//  	    jl_snmpv2c_setObjs.setText("Objetos");
//  	    jb_snmpv2c_setUndo.setText("Deshacer");
//  	    jb_snmpv2c_setUndo.setToolTipText("Presione para eliminar el ltimo OID aadido.");
//  	    jb_snmpv2c_SetSet.setText("Set");
//  	    jb_snmpv2c_SetSet.setToolTipText("Presione para especificar el(los) valor(es).");
//  	    //--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET
//  	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 	    
// 	    
// 	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////	  		
//  	    //--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
//  	    jp_snmpv2c_Traps.setBorder(BorderFactory.createTitledBorder("Recepcin de TRAPs SNMP v1/2c/3 e INFORMs SNMP v2c/3"));
//  	    jp_snmpv2c_TrapsSend.setBorder(BorderFactory.createTitledBorder("Envo de TRAPs e INFORMs SNMP v2c"));
//  	    jl_snmpv2c_TrapSndHost.setText("Direccin IP del destino");
//  	    jl_snmpv2c_TrapSndTipo.setText("Tipo de Trap");
//
//  	    jcb_snmpv2c_TrapSel.removeItemAt(5);
//  	    //jcb_snmpv2c_TrapSel.addItem("coldStart");
//	  	//jcb_snmpv2c_TrapSel.addItem("warmStart");
//	  	//jcb_snmpv2c_TrapSel.addItem("linkDown");
//	  	//jcb_snmpv2c_TrapSel.addItem("linkUp");
//	  	//jcb_snmpv2c_TrapSel.addItem("authenticationFailure");	  	
//	  	jcb_snmpv2c_TrapSel.addItem("Otro");
//	  	jcb_snmpv2c_TrapSel.setSelectedIndex(0);
//  	    jcb_snmpv2c_TrpInfSel.setSelectedIndex(0);
//	  	
//	  	jl_snmpv2c_PtoCom.setText("Puerto");
//	  	jl_snmpv2c_Com.setText("Comunidad");
//	  	jl_snmpv2c_Int.setText("Intentos");
//	  	//jl_snmpv2c_Timeout.setText("TimeOut (ms)");
//	  	jl_snmpv2c_Timeout.setText("Tiempo de Es.");
//	  	jl_snmpv2c_Enter.setText("Enterprise OID");
//	  	jl_snmpv2c_TrpInfSel.setText("Seleccione una opcin");
//	  	jl_snmpv2c_OtroTrp.setText("Trap OID");
//	  	jl_snmpv2c_Descr.setText("Descripcin");
//	  	jl_snmpv2c_TpoDtoTrp.setText("Tipo de Dato");
//	  	jb_SndTrapv2c.setText("Enviar TRAP");
//	  	jb_SndTrapv2c.setToolTipText("Presione para enviar el TRAP.");
//	  	
//	  	erroresGenerales38 = "Debe introducir el Inform OID.\n";
//	  	erroresGenerales39 = "Debe introducir el Inform OID y este debe ser OID.\n";
//	  	erroresGenerales40 = "Debe introducir el Trap OID.\n";
//	  	erroresGenerales41 = "Debe introducir el Trap OID y este debe ser OID.\n";
//	  	erroresGenerales42 = "Debe introducir la Descripcin.\n";
//	  	erroresGenerales43 = "La descripcin no es del tipo de dato especificado, operacin cancelada.";
//	  	erroresGenerales44 = "Debe seleccionar el tipo de dato de la descripcin, operacin cancelada.";
//	  	erroresGenerales45 = "Trap";
//	  	erroresGenerales46 = "Otro";
//	  	erroresGenerales47 = "Inform";
//	  	erroresGenerales48 = "INFORM enviado exitosamente.";
//	  	erroresGenerales49 = "Enviar INFORM";
//	    erroresGenerales50 = "Inform OID";
//	    erroresGenerales51 = "Presione para enviar el INFORM.";
//	    erroresGenerales52 = "Enviar TRAP";
//	    erroresGenerales53 = "Trap OID";
//	    erroresGenerales54 = "Presione para enviar el TRAP.";
//  	    //--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// 	    
  	  //Fin de SNMPv2c-----------------------------------------------------------------------------------------------------  
  	  
  	  //Para SNMPv3-------------------------------------------------------------------------------------------------------
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS----------------
  	    jp_snmpv3_Con.setBorder(BorderFactory.createTitledBorder("Configure Parameters"));
  	    jl_snmpv3_IP.setText("Direccin IP del Agente");
  	    jl_snmpv3_pto.setText("Nro. de Puerto");
  	    jl_snmpv3_User.setText("Nombre de Usuario");
  	    jl_snmpv3_Aut.setText("Clave de Autenticacin");
  	    jl_snmpv3_Priv.setText("Clave de Encriptacin");
  	    jl_snmpv3_VerUsr.setText("Visualizar Datos Privados");
  	    jl_snmpv3_metAut.setText("Algoritmo de Autenticacin");
  	    jl_snmpv3_metPriv.setText("Algoritmo de Encriptacin");
  	    jl_snmpv3_inten.setText("Nro. de Intentos");
  	    jl_snmpv3_timeOut.setText("Tiempo de Espera (ms)");
  	    jb_snmpv3_aplicarPara.setText("Aplicar Cambios");
  	    jb_snmpv3_aplicarPara.setToolTipText("Presione para establecer los nuevos parmetros.");
  	    configParamError10 = "Debe introducir el name de usuario.\n";	
  		configParamError11 = "Debe introducir la clave de autenticacin.\n";
  		configParamError12 = "La clave de autenticacin debe tener al menos 8 digitos.\n";
  		configParamError13 = "Debe introducir la clave de encriptacin.\n";
  		configParamError14 = "La clave de encriptacin debe tener al menos 8 digitos.\n";
  	    opcionSi = "Si";
  		opcionNo = "No";
  	    jcb_snmpv3_VerUsr.removeAllItems();
  	    jcb_snmpv3_VerUsr.addItem(opcionSi);
  	    jcb_snmpv3_VerUsr.addItem(opcionNo);
  	    jcb_snmpv3_VerUsr.setSelectedIndex(1);	  	 
  	    //--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS----------------
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET----
  	    jp_snmpv3_Get.setBorder(BorderFactory.createTitledBorder("Comando Get"));
  	    jl_snmpv3_getEtiGet.setText("OID");
  	    jb_snmpv3_getAdd.setText("Aadir");
  	    jb_snmpv3_getAdd.setToolTipText("Presione para agregar el OID.");
  	    jl_snmpv3_getObjs.setText("Objetos");
  	    jb_snmpv3_getUndo.setText("Deshacer");
  	    jb_snmpv3_getUndo.setToolTipText("Presione para eliminar el ltimo OID aadido.");
  	    jl_snmpv3_getModSeg.setText("Seguridad");
  	    jb_snmpv3_getGet.setText("Get");
  	    jb_snmpv3_getGet.setToolTipText("Presione para obtener el(los) valor(es).");
  	    erroresGenerales29 = " Error: El usuario no se encuentra en el Agente.\n";
  	    erroresGenerales30 = " Error: La clave y/o el algoritmo de autenticacin proporcionados son incorrectos.\n";
  	    erroresGenerales31 = " Error: La clave y/o el algoritmo de encriptacin proporcionados son incorrectos.\n";
  	    //--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET----
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--
  	    jp_snmpv3_GetNext.setBorder(BorderFactory.createTitledBorder("Comando GetNext"));
  	    jl_snmpv3_GetNextEtiGetNext.setText("OID");
  	    jb_snmpv3_getNextAdd.setText("Aadir");
  	    jb_snmpv3_getNextAdd.setToolTipText("Presione para agregar el OID.");
  	    jl_snmpv3_getNextObjs.setText("Objetos");
  	    jb_snmpv3_getNextUndo.setText("Deshacer");
  	    jb_snmpv3_getNextUndo.setToolTipText("Presione para eliminar el ltimo OID aadido.");
  	    jb_snmpv3_GetNextGetNext.setText("GetNext");
  	    jb_snmpv3_GetNextGetNext.setToolTipText("Presione para obtener el(los) valor(es).");
  	    jl_snmpv3_getNextModSeg.setText("Seguridad");
  	    //--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--
  	    jp_snmpv3_GetBulk.setBorder(BorderFactory.createTitledBorder("Comando GetBulk"));
  	    jl_snmpv3_nonRepe.setText("NonRepeaters");
  	    jl_snmpv3_maxRep.setText("MaxRepetitions");
  	    jb_snmpv3_GetBulk_add.setText("Aadir");
  	    jb_snmpv3_GetBulk_add.setToolTipText("Presione para agregar el OID.");
  	    jb_snmpv3_GetBulkUndo.setText("Deshacer");
  	    jb_snmpv3_GetBulkUndo.setToolTipText("Presione para eliminar el ltimo OID aadido.");
  	    jl_snmpv3_getBulkObjs.setText("Objetos");
  	    jl_snmpv3_GetBulkEtiSet.setText("OID");
  	    jb_snmpv3_GetBulkGetBulk.setText("GetBulk");
  	    jb_snmpv3_GetBulkGetBulk.setToolTipText("Presione para obtener el(los) valor(es).");
  	    jl_snmpv3_getBulkModSeg.setText("Seguridad");
  	    //--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE
  	    jp_snmpv3_getTable.setBorder(BorderFactory.createTitledBorder("GetTable"));
  	    jl_snmpv3_getTableEtigetTable.setText("OID");
  	    jb_snmpv3_getTablegetTable.setText("GetTable");
  	    jb_snmpv3_getTablegetTable.setToolTipText("Presione para consultar la tabla.");
  	    jl_snmpv3_getTableModSeg.setText("Seguridad");
  	    //--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK
  	    jp_snmpv3_walk.setBorder(BorderFactory.createTitledBorder("Walk"));
  	    jl_snmpv3_WalkEtiLimitePregunta.setText("Limitar Cantidad de Variables a Consultar");
  	    jcb_snmpv3_WalkEtiLimitePregunta.removeAllItems();
  	    jcb_snmpv3_WalkEtiLimitePregunta.addItem(opcionSi);
  	    jcb_snmpv3_WalkEtiLimitePregunta.addItem(opcionNo);
  	    jcb_snmpv3_WalkEtiLimitePregunta.setSelectedIndex(1);
	  	jl_snmpv3_WalkEtiLimite.setText("Cantidad");
	  	jtf_snmpv3_WalkEtiLimite.setText(erroresGenerales21);
	  	jl_snmpv3_WalkEti.setText("OID");
	  	jb_snmpv3_Walk.setText("Walk");
	  	jb_snmpv3_Walk.setToolTipText("Presione para iniciar el Walk.");
	  	jl_snmpv3_getWalkModSeg.setText("Seguridad");
  	    //--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK
  	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 	    
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET
  	    jp_snmpv3_Set.setBorder(BorderFactory.createTitledBorder("Comando Set"));
  	    jl_snmpv3_SetEtiSet.setText("OID");
  	    jl_snmpv3_SetEtiTipo.setText("Tipo de Dato");
  	    jl_snmpv3_SetEtiSetValor.setText("Valor");
  	    jb_snmpv3_setAdd.setText("Aadir");
  	    jb_snmpv3_setAdd.setToolTipText("Presione para agregar el OID.");
  	    jl_snmpv3_setObjs.setText("Objetos");
  	    jb_snmpv3_setUndo.setText("Deshacer");
  	    jb_snmpv3_setUndo.setToolTipText("Presione para eliminar el ltimo OID aadido.");
  	    jb_snmpv3_SetSet.setText("Set");
  	    jb_snmpv3_SetSet.setToolTipText("Presione para especificar el(los) valor(es).");
  	    jl_snmpv3_setModSeg.setText("Seguridad");
  	    //--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////	  		
  	    //--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
  	    jp_snmpv3_Traps.setBorder(BorderFactory.createTitledBorder("Recepcin de TRAPs SNMP v1/2c/3 e INFORMs SNMP v2c/3"));
  	    jp_snmpv3_TrapsSend.setBorder(BorderFactory.createTitledBorder("Envo de TRAPs e INFORMs SNMP v3"));
  	    jl_snmpv3_TrapSndHost.setText("Direccin IP del destino");     
  	    jl_snmpv3_TrapSndTipo.setText("Tipo de Trap");			
  	    	
  	    jcb_snmpv3_TrapSel.removeItemAt(5);
  	    jcb_snmpv3_TrapSel.addItem("Otro");
  	    jcb_snmpv3_TrapSel.setSelectedIndex(0);
  	    
  	    jcb_snmpv3_TrpInfSel.setSelectedIndex(0);
  	    
  	    jl_snmpv3_PtoCom.setText("Puerto");
  	    jl_snmpv3_TrpVer.setText("Visualizar");//USO DEL JLABEL
  	    
  	    jcb_snmpv3_TrpVer.removeAllItems();
  	    jcb_snmpv3_TrpVer.addItem(opcionSi);
  	    jcb_snmpv3_TrpVer.addItem(opcionNo);
  	    jcb_snmpv3_TrpVer.setSelectedIndex(1);
  	    
  	    jl_snmpv3_TrpUsr.setText("Usuario");
  	    jl_snmpv3_TrpAut.setText("Clave Auten.");//USO DEL JLABEL
  	    jl_snmpv3_TrpPriv.setText("Clave Encrip.");//USO DEL JLABEL
  	    jl_snmpv3_Int.setText("Intentos");
  	    //jl_snmpv3_Timeout.setText("TimeOut (ms)");
  	    jl_snmpv3_Timeout.setText("Tiempo de Es.");
  	    jl_snmpv3_Enter.setText("Enterprise OID");
  	    jl_snmpv3_TrpInfSel.setText("Seleccione una opcin");             
  	    jl_snmpv3_OtroTrp.setText("Trap OID");
  	    jl_snmpv3_Descr.setText("Descripcin");
  	    jl_snmpv3_TpDto.setText("Tipo de Dato");
  	    jl_snmpv3_trpModSeg.setText("Seguridad");
  	    jb_SndTrapv3.setText("Enviar TRAP");
  	    jb_SndTrapv3.setToolTipText("Presione para enviar el TRAP.");
  	    
  	    erroresGenerales55 = "Debe introducir el name de usuario.\n";
  		erroresGenerales56 = "Debe introducir la clave de autenticacin y esta debe tener al menos 8 digitos.\n";
  		erroresGenerales57 = "Debe introducir la clave de encriptacin y esta debe tener al menos 8 digitos.\n";
  	    //--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
  	  //Fin de SNMPv3-----------------------------------------------------------------------------------------------------  
  	  
  	}
  	if (idioma.equals("English")){
  	  //System.out.println("Cambiando idioma a ingles");
  	  //Para la interfaz general
  	  jm_archive.setText("File");//aade el menu
  	  jmi_outbound.setText("Exit");//crea un jmenuitem
  	  jm_help.setText("Help");
  	  jmi_helpHelp.setText("User's Guide");
  	  jmi_about.setText("About");
  	  jb_mibtree.setText("Import MIBs");
  	  jb_mibtree.setToolTipText("Press to import MIBs.");
  	  aboutDeTitulo = nameOfTheProgram+" - About";
  	  String encabezadoAcercaDe = "The software was developed in UCV (Universidad Central\n"+
								  "de Venezuela) as the final project to obtain the Bachelor\n"+
								  "of Science (Licenciatura) in Computer Science, by:\n";
  	  							  /*
  	  							  "This Software is a Thesis presented to the illustrious\n"+
      	  						  "Universidad Central de Venezuela by:\n";
      	  						  */
	  String ac3 = 				  "\nPablo Poskal\npabloposkal@gmail.com\n";
      String ac2 = 				  "\nGustavo Ayala\ngustavoucv@yahoo.com.mx\n";
      String tutoriado = 	      /*"\nTo obtain the Licentiate degree on Computer Science.\n"+*/
	  							  "\nAdvisor: "+
	  							  "\n\nProf. Eric Gamess\negamess@kuaimare.ciens.ucv.ve\n\n";
      String ac4 = 				  "This software uses SNMP4J and Mibble libraries.\n"+
    			   				  "See http://www.snmp4j.org and http://www.mibble.org \n"+  	  	
    			   				  "for information.\n";
      String ac = (encabezadoAcercaDe+ac2+ac3+tutoriado+ac4);
      aboutDe = ac;
      erroresGenerales58 = "Help file not found.";
      String etiquetaIdioma="";
      for (int yy=0;yy<165; yy++){
		  etiquetaIdioma=etiquetaIdioma.concat(" ");
		}
		etiquetaIdioma=etiquetaIdioma.concat(" Idioma / Language   ");
      jl_idioma.setText("           ".concat(etiquetaIdioma));
      //PARA EL COMBO BOX DE ACCION A REALIZAR
        //COMANDOS ADMITIDOS
  	    ConfigureParameters = "Connection Options";
  	    ComandoGet = "Get Command";
  	    ComandoGetNext = "GetNext Command";
  	    ComandoGetBulk = "GetBulk Command";
  	    ComandoGetTable = "GetTable";
  	    ComandoWalk = "Walk";
  	    ComandoSet = "Set Command";
  	    EnviarVerTraps = "Send/Receive Traps";
  	    //COMANDOS EN SNMPv1
//  	    jl_snmpv1_sel.setText("Action Selected");
//  	    jcb_snmpv1_sel.removeAllItems();
//  	    jcb_snmpv1_sel.addItem(ConfigureParameters);
//	  	jcb_snmpv1_sel.addItem(ComandoGet);
//	  	jcb_snmpv1_sel.addItem(ComandoGetNext);
//	  	jcb_snmpv1_sel.addItem(ComandoGetTable);
//	  	jcb_snmpv1_sel.addItem(ComandoWalk);
//	  	jcb_snmpv1_sel.addItem(ComandoSet);
//	  	jcb_snmpv1_sel.addItem(EnviarVerTraps);
//  	    //COMANDOS EN SNMPv2C
//  	    jl_snmpv2c_sel.setText("Action Selected");
//	  	jcb_snmpv2c_sel.removeAllItems();
//		jcb_snmpv2c_sel.addItem(ConfigureParameters);
//	  	jcb_snmpv2c_sel.addItem(ComandoGet);
//	  	jcb_snmpv2c_sel.addItem(ComandoGetNext);
//	  	jcb_snmpv2c_sel.addItem(ComandoGetBulk);
//	  	jcb_snmpv2c_sel.addItem(ComandoGetTable);
//	  	jcb_snmpv2c_sel.addItem(ComandoWalk);
//	  	jcb_snmpv2c_sel.addItem(ComandoSet);
//	  	jcb_snmpv2c_sel.addItem(EnviarVerTraps);
  	    //COMANDOS EN SNMPv3
  	    jl_snmpv3_sel.setText("Action Selected");
  	    jcb_snmpv3_sel.removeAllItems();
  	    jcb_snmpv3_sel.addItem(ConfigureParameters);
  		jcb_snmpv3_sel.addItem(ComandoGet);
  		jcb_snmpv3_sel.addItem(ComandoGetNext);
  		jcb_snmpv3_sel.addItem(ComandoGetBulk);
  		jcb_snmpv3_sel.addItem(ComandoGetTable);
  		jcb_snmpv3_sel.addItem(ComandoWalk);
  		jcb_snmpv3_sel.addItem(ComandoSet);
  		jcb_snmpv3_sel.addItem(EnviarVerTraps);
  		
      //Para SNMPv1-------------------------------------------------------------------------------------------------------
  		
  		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PARAMETER SCREEN
//  	    jp_snmpv1_Con.setBorder(BorderFactory.createTitledBorder("Connection Options"));
//  	    jl_snmpv1_IP.setText("Agent IP Address");
//  	    jl_snmpv1_pto.setText("Port Number");
//  	    jl_snmpv1_comLec.setText("Read Community");
//  	    jtf_snmpv1_comEsc.setText("Write Community");
//  	    jl_snmpv1_VerCom.setText("Show Communities");
//  	    jl_snmpv1_inten.setText("Retries");
//  	    jl_snmpv1_timeOut.setText("TimeOut (ms)");
//  	    jb_snmpv1_aplicarPara.setText("Apply Changes");
//  	    jb_snmpv1_aplicarPara.setToolTipText("Press to set the new values.");
  	    configParamError01 = "Must enter the SNMP agent IP address.\n";
  		configParamError02 = "Must enter a valid IP address.\n";	
  		configParamError03 = "Must enter the port number. It has to be a numeric value.\n";	
	    configParamError04 = "Must enter the read community.\n";	
	    configParamError05 = "Must enter the write community.\n";
	    configParamError06 = "Must enter the retries. It has to be a numeric value.\n";
	    configParamError07 = "Must enter the retries. It has to be a numeric value greater than zero.\n";	 	
	    configParamError08 = "Must enter the timeout. It has to be a numeric value.\n";	 	
	    configParamError09 = "Must enter the timeout. It has to be a numeric value greater than zero.\n";	 	
	    configParamResult01 = "Changes successfully applied.";
  	    opcionSi = "Yes";
  		opcionNo = "No";  
//  	    jcb_snmpv1_VerCom.removeAllItems();
//  	    jcb_snmpv1_VerCom.addItem(opcionSi);
//  	    jcb_snmpv1_VerCom.addItem(opcionNo);
//  	    jcb_snmpv1_VerCom.setSelectedIndex(1);	  	 
//  	    //--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS----------------
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  		
//  		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET----
//  	    jp_snmpv1_Get.setBorder(BorderFactory.createTitledBorder("Get Command"));
//  	    jl_snmpv1_getEtiGet.setText("OID");
//  	    jb_snmpv1_add.setText("Add");
//  	    jb_snmpv1_add.setToolTipText("Press to add the OID.");
//  	    jl_snmpv1_getObjs.setText("Objects");
//  	    jb_snmpv1_undo.setText("Undo");
//  	    jb_snmpv1_undo.setToolTipText("Press to erase the last OID added.");
//  	    jb_snmpv1_getGet.setText("Get");
//  	    jb_snmpv1_getGet.setToolTipText("Press to get the value(s).");
//  	    erroresGenerales01 = "Must select or write an OID.";
//  	    erroresGenerales02 = "Must select or write a valid OID.";
//  	    erroresGenerales03 = "There is no more objects to erase.";
//  	    erroresGenerales04 = "\n Request timed out.\n";
//  	    //--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET----
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  		
//  		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--
//  	    jp_snmpv1_GetNext.setBorder(BorderFactory.createTitledBorder("GetNext Command"));
//  	    jl_snmpv1_GetNextEtiGetNext.setText("OID");
//  	    jb_snmpv1_GetNext_add.setText("Add");
//  	    jb_snmpv1_GetNext_add.setToolTipText("Press to add the OID.");
//  	    jl_snmpv1_getNextObjs.setText("Objects");
//  	    jb_snmpv1_GetNextUndo.setText("Undo");
//  	    jb_snmpv1_GetNextUndo.setToolTipText("Press to erase the last OID added.");
//  	    jb_snmpv1_GetNextGetNext.setText("GetNext");
//  	    jb_snmpv1_GetNextGetNext.setToolTipText("Press to get the next value(s).");
//  	    //--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  		
//  		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE
//  	    jp_snmpv1_getTable.setBorder(BorderFactory.createTitledBorder("GetTable"));
//  	    jl_snmpv1_getTableEtigetTable.setText("OID");
//  	    jb_snmpv1_getTablegetTable.setText("GetTable");
//  	    jb_snmpv1_getTablegetTable.setToolTipText("Press to get the table.");
//  	    erroresGenerales05 = "\nError: Request timed out.\n";
//  	    erroresGenerales06 = "There is no data for the specified OID, the causes could be:\n- The OID selected isn't a table.\n- The agent don't has data for the selected table.\n- Entry selected instead of the table.";
//  	    erroresGenerales07 = "Error: The agent don't has data for the specified OID.\n";
//  	    erroresGenerales08 = "Error: The agent don't has the specified username.\n";
//  	    erroresGenerales09 = "Error: Wrong password and/or authentication protocol.\n";
//  	    erroresGenerales10 = "Error: Wrong password and/or privacy protocol.\n";
//  	    erroresGenerales11 = "Error: Request timed out.\n";
//  	    erroresGenerales12 = "Error: The specified OID is greater than the greater OID of the selected MIB.\n";
//  	    erroresGenerales13 = "The variable given isn't the successor of the required:\n";
//  	    //--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK
//  	    jp_snmpv1_walk.setBorder(BorderFactory.createTitledBorder("Walk"));
//  	    jl_snmpv1_WalkEtiLimitePregunta.setText("Limit the quantity of variables retrieved");
//  	    jcb_snmpv1_WalkEtiLimitePregunta.removeAllItems();
//  	    jcb_snmpv1_WalkEtiLimitePregunta.addItem(opcionSi);
//  	    jcb_snmpv1_WalkEtiLimitePregunta.addItem(opcionNo);
//  	    jcb_snmpv1_WalkEtiLimitePregunta.setSelectedIndex(1);
//	  	jl_snmpv1_WalkEtiLimite.setText("Quantity");
//	  	erroresGenerales21 = "No Limit";
//	  	jtf_snmpv1_WalkEtiLimite.setText(erroresGenerales21);
//	  	jl_snmpv1_WalkEti.setText("OID");
//	  	jb_snmpv1_Walk.setText("Walk");
//	  	jb_snmpv1_Walk.setToolTipText("Press to start the MIB Walk.");
//	  	erroresGenerales14 = "Must enter the quantity of variables retrieved.";
//	  	erroresGenerales15 = "Must enter the quantity of variables retrieved. It has to be a numeric value.";
//	  	erroresGenerales16 = "\nRunning Walk, wait a moment...\n\n";
//	  	erroresGenerales17 = "Total requests sent:    ";
//	  	erroresGenerales18 = "Total objects received: ";
//	  	erroresGenerales19 = "Total walk time:        ";
//	  	erroresGenerales20 = " milliseconds";
//  	    //--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    
//		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET
//  	    jp_snmpv1_Set.setBorder(BorderFactory.createTitledBorder("Set Command"));
//  	    jl_snmpv1_SetEtiSet.setText("OID");
//  	    jl_snmpv1_SetEtiTipo.setText("Data Type");
//  	    jl_snmpv1_SetEtiSetValor.setText("Value");
//  	    jb_snmpv1_setAdd.setText("Add");
//  	    jb_snmpv1_setAdd.setToolTipText("Press to add the OID.");
//  	    jl_snmpv1_setObjs.setText("Objects");
//  	    jb_snmpv1_setUndo.setText("Undo");
//  	    jb_snmpv1_setUndo.setToolTipText("Press to erase the last OID added.");
//  	    jb_snmpv1_SetSet.setText("Set");
//  	    jb_snmpv1_SetSet.setToolTipText("Press to set the new value(s).");
//  	    erroresGenerales22 = "Must enter the new value.";
//  	    erroresGenerales23 = "Must select the OID data type.";
//  	    erroresGenerales24 = "Wrong data type. The operation has been cancelled.";
//  	    erroresGenerales25 = "Unknown data type. The operation has been cancelled.";
//  	    //--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET
//  	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 	      	    
//  		  		
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////	  		
//  	    //--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
//  	    jp_snmpv1_Traps.setBorder(BorderFactory.createTitledBorder("Receive TRAPs (SNMP v1/2c/3) and INFORMs (SNMP v2c/3)"));  
//  	    jp_snmpv1_TrapsSend.setBorder(BorderFactory.createTitledBorder("Send TRAPs SNMP v1"));
//  	    jl_snmpv1_TrapSndHost.setText("Destination IP Address");
//  	    jl_snmpv1_TrapSndTipo.setText("Trap Type");
//  	    jl_snmpv1_PtoCom.setText("Port");
//  	    jl_snmpv1_Com.setText("Community");
//  	    jl_snmpv1_Int.setText("Retries");
//  	    jl_snmpv1_Timeout.setText("TimeOut (ms)");
//  	    jl_snmpv1_Enter.setText("Enterprise OID");
//  	    jl_snmpv1_TrapSpc.setText("Specific Trap");
//  	    jb_SndTrap.setText("Send TRAP");
//  	    jb_SndTrap.setToolTipText("Press to send the TRAP.");
//  	    erroresGenerales32 = "Must enter the specific trap. It has to be a numeric value.\n";
//  	    erroresGenerales33 = "Must enter the Enterprise OID.\n";
//  	    erroresGenerales34 = "Must enter the Enterprise OID. It has to be an OID.\n";
//  	    erroresGenerales35 = "TRAP successfully sent.";
//  	    erroresGenerales36 = "Must enter the destination IP address.\n";
//  	    erroresGenerales37 = "Must enter the community.\n";
//  	    
//  	    mensajesDeTraps01 = "Waiting for TRAPs or INFORMs...\n";
//  	    mensajesDeTraps02 = "\nPacket #";
//  	    mensajesDeTraps03 = "SNMP Version 1 TRAP Arrived.\n";
//  	    mensajesDeTraps04 = "Received: ";
//  	    mensajesDeTraps05 = "IP / Source Port: ";
//  	    mensajesDeTraps06 = "Community: ";
//  	    mensajesDeTraps07 = "Enterprise: ";
//  	    mensajesDeTraps08 = "TRAP Type: ";
//  	    mensajesDeTraps09 = "Specific TRAP: ";
//  	    mensajesDeTraps10 = "TimeStamp: ";
//  	    mensajesDeTraps11 = "SNMP Version 2c INFORM Arrived.\n";
//  	    mensajesDeTraps12 = "SNMP Version 3 INFORM Arrived.\n";
//  	    mensajesDeTraps13 = "Username: ";
//  	    mensajesDeTraps14 = "OID #";
//  	    mensajesDeTraps15 = "Value #";
//  	    mensajesDeTraps16 = "SNMP Version 2c TRAP Arrived.\n";
//  	    mensajesDeTraps17 = "SNMP Version 3 TRAP Arrived.\n";
//  	    mensajesDeTraps18 = "TRAP Type: Other\n";
//  		//--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
//  		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	  		
//  	      		
//  	  //Fin de SNMPv1-----------------------------------------------------------------------------------------------------  	
//  	  
//  	  //Para SNMPv2c-------------------------------------------------------------------------------------------------------
//  		
//  		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS----------------
//  	    jp_snmpv2c_Con.setBorder(BorderFactory.createTitledBorder("Connection Options"));
//  	    jl_snmpv2c_IP.setText("Agent IP Address");
//  	    jl_snmpv2c_pto.setText("Port Number");
//  	    jl_snmpv2c_comLec.setText("Read Community");
//  	    jl_snmpv2c_comEsc.setText("Write Community");
//  	    jl_snmpv2c_VerCom.setText("Show Communities");
//  	    jl_snmpv2c_inten.setText("Retries");
//  	    jl_snmpv2c_timeOut.setText("TimeOut (ms)");
//  	    jb_snmpv2c_aplicarPara.setText("Apply Changes");
//  	    jb_snmpv2c_aplicarPara.setToolTipText("Press to set the new values.");
//  	    opcionSi = "Yes";
//  		opcionNo = "No";  
//  	    jcb_snmpv2c_VerCom.removeAllItems();
//  	    jcb_snmpv2c_VerCom.addItem(opcionSi);
//  	    jcb_snmpv2c_VerCom.addItem(opcionNo);
//  	    jcb_snmpv2c_VerCom.setSelectedIndex(1);	  	 
//  	    //--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS----------------
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		
//		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET----
//  	    jp_snmpv2c_Get.setBorder(BorderFactory.createTitledBorder("Get Command"));
//  	    jl_snmpv2c_getEtiGet.setText("OID");
//  	    jb_snmpv2c_getAdd.setText("Add");
//  	    jb_snmpv2c_getAdd.setToolTipText("Press to add the OID.");
//  	    jl_snmpv2c_getObjs.setText("Objects");
//  	    jb_snmpv2c_getUndo.setText("Undo");
//  	    jb_snmpv2c_getUndo.setToolTipText("Press to erase the last OID added.");
//  	    jb_snmpv2c_getGet.setText("Get");
//  	    jb_snmpv2c_getGet.setToolTipText("Press to get the value(s).");
//  	    //--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET----
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--
//  	    jp_snmpv2c_GetNext.setBorder(BorderFactory.createTitledBorder("GetNext Command"));
//  	    jl_snmpv2c_GetNextEtiGetNext.setText("OID");
//  	    jb_snmpv2c_GetNext_add.setText("Add");
//  	    jb_snmpv2c_GetNext_add.setToolTipText("Press to add the OID.");
//  	    jl_snmpv2c_getNextObjs.setText("Objects");
//  	    jb_snmpv2c_GetNextUndo.setText("Undo");
//  	    jb_snmpv2c_GetNextUndo.setToolTipText("Press to erase the last OID added.");
//  	    jb_snmpv2c_GetNextGetNext.setText("GetNext");
//  	    jb_snmpv2c_GetNextGetNext.setToolTipText("Press to get the next value(s).");
//  	    //--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		
//		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--
//  	    jp_snmpv2c_GetBulk.setBorder(BorderFactory.createTitledBorder("GetBulk Command"));
//  	    jl_snmpv2c_nonRepe.setText("NonRepeaters");
//  	    jl_snmpv2c_maxRep.setText("MaxRepetitions");
//  	    jb_snmpv2c_GetBulk_add.setText("Add");
//  	    jb_snmpv2c_GetBulk_add.setToolTipText("Press to add the OID.");
//  	    jb_snmpv2c_GetBulkUndo.setText("Undo");
//  	    jb_snmpv2c_GetBulkUndo.setToolTipText("Press to erase the last OID added.");
//  	    jl_snmpv2c_getBulkObjs.setText("Objects");
//  	    jl_snmpv2c_GetBulkEtiSet.setText("OID");
//  	    jb_snmpv2c_GetBulkGetBulk.setText("GetBulk");
//  	    jb_snmpv2c_GetBulkGetBulk.setToolTipText("Press to get the value(s).");
//  	    erroresGenerales26 = "Must enter the NonRepeaters. It has to be a numeric value.";
//  	    erroresGenerales27 = "Must enter the MaxRepetitions. It has to be a numeric value greater than zero.";
//  	    erroresGenerales28 = "\nGetBulk: \n";
//  	    //--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE
//  	    jp_snmpv2c_getTable.setBorder(BorderFactory.createTitledBorder("GetTable"));
//  	    jl_snmpv2c_getTableEtigetTable.setText("OID");
//  	    jb_snmpv2c_getTablegetTable.setText("GetTable");
//  	    jb_snmpv2c_getTablegetTable.setToolTipText("Press to get the table.");
//  	    //--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    
//		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK
//	  	jp_snmpv2c_walk.setBorder(BorderFactory.createTitledBorder("Walk"));
//  	    jl_snmpv2c_WalkEtiLimitePregunta.setText("Limit the quantity of variables retrieved");
//  	    jcb_snmpv2c_WalkEtiLimitePregunta.removeAllItems();
//  	    jcb_snmpv2c_WalkEtiLimitePregunta.addItem(opcionSi);
//  	    jcb_snmpv2c_WalkEtiLimitePregunta.addItem(opcionNo);
//  	    jcb_snmpv2c_WalkEtiLimitePregunta.setSelectedIndex(1);
//	  	jl_snmpv2c_WalkEtiLimite.setText("Quantity");
//	  	jtf_snmpv2c_WalkEtiLimite.setText(erroresGenerales21);
//	  	jl_snmpv2c_WalkEti.setText("OID");
//	  	jb_snmpv2c_Walk.setText("Walk");
//	  	jb_snmpv2c_Walk.setToolTipText("Press to start the MIB Walk.");
//  	    //--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  	    //--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET
//  	    jp_snmpv2c_Set.setBorder(BorderFactory.createTitledBorder("Set Command"));
//  	    jl_snmpv2c_SetEtiSet.setText("OID");
//  	    jl_snmpv2c_SetEtiTipo.setText("Data Type");
//  	    jl_snmpv2c_SetEtiSetValor.setText("Value");
//  	    jb_snmpv2c_setAdd.setText("Add");
//  	    jb_snmpv2c_setAdd.setToolTipText("Press to add the OID.");
//  	    jl_snmpv2c_setObjs.setText("Objects");
//  	    jb_snmpv2c_setUndo.setText("Undo");
//  	    jb_snmpv2c_setUndo.setToolTipText("Press to erase the last OID added.");
//  	    jb_snmpv2c_SetSet.setText("Set");
//  	    jb_snmpv2c_SetSet.setToolTipText("Press to set the new value(s).");
//  	    //--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET
//  	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 	    
//  	    
//		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	  		
//  	    //--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
//  	    jp_snmpv2c_Traps.setBorder(BorderFactory.createTitledBorder("Receive TRAPs (SNMP v1/2c/3) and INFORMs (SNMP v2c/3)"));  
//  	    jp_snmpv2c_TrapsSend.setBorder(BorderFactory.createTitledBorder("Send TRAPs and INFORMs SNMP v2c"));
//  	    jl_snmpv2c_TrapSndHost.setText("Destination IP Address");
//  	    jl_snmpv2c_TrapSndTipo.setText("Trap Type");
//  	    jl_snmpv2c_PtoCom.setText("Port");
//  	    jl_snmpv2c_Com.setText("Community");
//  	    jl_snmpv2c_Int.setText("Retries");
//  	    jl_snmpv2c_Timeout.setText("TimeOut (ms)");
//  	    jl_snmpv2c_Enter.setText("Enterprise OID");
//  	    
//  	    jcb_snmpv2c_TrapSel.removeItemAt(5);
//  	    //jcb_snmpv2c_TrapSel.addItem("coldStart");
//	  	//jcb_snmpv2c_TrapSel.addItem("warmStart");
//	  	//jcb_snmpv2c_TrapSel.addItem("linkDown");
//	  	//jcb_snmpv2c_TrapSel.addItem("linkUp");
//	  	//jcb_snmpv2c_TrapSel.addItem("authenticationFailure");	  	
//	  	jcb_snmpv2c_TrapSel.addItem("Other");
//	  	jcb_snmpv2c_TrapSel.setSelectedIndex(0);
//  	    jcb_snmpv2c_TrpInfSel.setSelectedIndex(0);
//	  	
//	  	jl_snmpv2c_TrpInfSel.setText("Select an Option");
//	  	jl_snmpv2c_OtroTrp.setText("Trap OID");
//	  	jl_snmpv2c_Descr.setText("Description");
//	  	jl_snmpv2c_TpoDtoTrp.setText("Data Type");
//	  	jb_SndTrapv2c.setText("Send TRAP");
//	  	jb_SndTrapv2c.setToolTipText("Press to send the TRAP.");
//	  	erroresGenerales38 = "Must enter the Inform OID.\n";
//	  	erroresGenerales39 = "Must enter the Inform OID. It has to be an OID.\n";
//	  	erroresGenerales40 = "Must enter the Trap OID.\n";
//	  	erroresGenerales41 = "Must enter the Trap OID. It has to be an OID.\n";
//	  	erroresGenerales42 = "Must enter the description.\n";
//	  	erroresGenerales43 = "Wrong description data type. The operation has been cancelled.";
//	  	erroresGenerales44 = "Must select the description data type. The operation has been cancelled.";
//	  	erroresGenerales45 = "Trap";
//	  	erroresGenerales46 = "Other";
//	  	erroresGenerales47 = "Inform";
//	  	erroresGenerales48 = "INFORM successfully sent.";
//	  	erroresGenerales49 = "Send INFORM";
//	    erroresGenerales50 = "Inform OID";
//	    erroresGenerales51 = "Press to send the INFORM.";
//	    erroresGenerales52 = "Send TRAP";
//	    erroresGenerales53 = "Trap OID";
//	    erroresGenerales54 = "Press to send the TRAP.";
//  	    //--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//		  		  		
  	  //Fin de SNMPv2c-----------------------------------------------------------------------------------------------------
  	  
  	  //Para SNMPv3-------------------------------------------------------------------------------------------------------
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS----------------
  	    jp_snmpv3_Con.setBorder(BorderFactory.createTitledBorder("Connection Options"));
  	    jl_snmpv3_IP.setText("Agent IP Address");
  	    jl_snmpv3_pto.setText("Port Number");
  	    jl_snmpv3_User.setText("Username");
  	    jl_snmpv3_Aut.setText("Authentication Password");
  	    jl_snmpv3_Priv.setText("Privacy Password");
  	    jl_snmpv3_VerUsr.setText("Show Private Data");
  	    jl_snmpv3_metAut.setText("Authentication Protocol");
  	    jl_snmpv3_metPriv.setText("Privacy Protocol");
  	    jl_snmpv3_inten.setText("Retries");
  	    jl_snmpv3_timeOut.setText("TimeOut (ms)");
  	    jb_snmpv3_aplicarPara.setText("Apply Changes");
  	    jb_snmpv3_aplicarPara.setToolTipText("Press to set the new values.");
  	    configParamError10 = "Must enter the username.\n";	
  		configParamError11 = "Must enter the authentication password.\n";
  		configParamError12 = "The authentication password must be at least 8 characters long.\n";
  		configParamError13 = "Must enter the privacy password.\n";
  		configParamError14 = "The privacy password must be at least 8 characters long.\n";
  	    opcionSi = "Yes";
  		opcionNo = "No";
  	    jcb_snmpv3_VerUsr.removeAllItems();
  	    jcb_snmpv3_VerUsr.addItem(opcionSi);
  	    jcb_snmpv3_VerUsr.addItem(opcionNo);
  	    jcb_snmpv3_VerUsr.setSelectedIndex(1);	  	 
  	    //--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS----------------
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET----
  	    jp_snmpv3_Get.setBorder(BorderFactory.createTitledBorder("Get Command"));
  	    jl_snmpv3_getEtiGet.setText("OID");
  	    jb_snmpv3_getAdd.setText("Add");
  	    jb_snmpv3_getAdd.setToolTipText("Press to add the OID.");
  	    jl_snmpv3_getObjs.setText("Objects");
  	    jb_snmpv3_getUndo.setText("Undo");
  	    jb_snmpv3_getUndo.setToolTipText("Press to erase the last OID added.");
  	    jb_snmpv3_getGet.setText("Get");
  	    jb_snmpv3_getGet.setToolTipText("Press to get the value(s).");
  	    jl_snmpv3_getModSeg.setText("Security");
		erroresGenerales29 = " Error: The agent don't has the specified username.\n";
  	    erroresGenerales30 = " Error: Wrong password and/or authentication protocol.\n";
  	    erroresGenerales31 = " Error: Wrong password and/or privacy protocol.\n";
  	    //--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET----
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--
  	    jp_snmpv3_GetNext.setBorder(BorderFactory.createTitledBorder("GetNext Command"));
  	    jl_snmpv3_GetNextEtiGetNext.setText("OID");
  	    jb_snmpv3_getNextAdd.setText("Add");
  	    jb_snmpv3_getNextAdd.setToolTipText("Press to add the OID.");
  	    jl_snmpv3_getNextObjs.setText("Objects");
  	    jb_snmpv3_getNextUndo.setText("Undo");
  	    jb_snmpv3_getNextUndo.setToolTipText("Press to erase the last OID added.");
  	    jb_snmpv3_GetNextGetNext.setText("GetNext");
  	    jb_snmpv3_GetNextGetNext.setToolTipText("Press to get the next value(s).");
  	    jl_snmpv3_getNextModSeg.setText("Security");
  	    //--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--
  	    jp_snmpv3_GetBulk.setBorder(BorderFactory.createTitledBorder("GetBulk Command"));
  	    jl_snmpv3_nonRepe.setText("NonRepeaters");
  	    jl_snmpv3_maxRep.setText("MaxRepetitions");
  	    jb_snmpv3_GetBulk_add.setText("Add");
  	    jb_snmpv3_GetBulk_add.setToolTipText("Press to add the OID.");
  	    jb_snmpv3_GetBulkUndo.setText("Undo");
  	    jb_snmpv3_GetBulkUndo.setToolTipText("Press to erase the last OID added.");
  	    jl_snmpv3_getBulkObjs.setText("Objects");
  	    jl_snmpv3_GetBulkEtiSet.setText("OID");
  	    jb_snmpv3_GetBulkGetBulk.setText("GetBulk");
  	    jb_snmpv3_GetBulkGetBulk.setToolTipText("Press to get the value(s).");
  	    jl_snmpv3_getBulkModSeg.setText("Security");
  	    //--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE
  	    jp_snmpv3_getTable.setBorder(BorderFactory.createTitledBorder("GetTable"));
  	    jl_snmpv3_getTableEtigetTable.setText("OID");
  	    jb_snmpv3_getTablegetTable.setText("GetTable");
  	    jb_snmpv3_getTablegetTable.setToolTipText("Press to get the table.");
  	    jl_snmpv3_getTableModSeg.setText("Security");
  	    //--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK
  	    jp_snmpv3_walk.setBorder(BorderFactory.createTitledBorder("Walk"));
  	    jl_snmpv3_WalkEtiLimitePregunta.setText("Limit the quantity of variables retrieved");
  	    jcb_snmpv3_WalkEtiLimitePregunta.removeAllItems();
  	    jcb_snmpv3_WalkEtiLimitePregunta.addItem(opcionSi);
  	    jcb_snmpv3_WalkEtiLimitePregunta.addItem(opcionNo);
  	    jcb_snmpv3_WalkEtiLimitePregunta.setSelectedIndex(1);
	  	jl_snmpv3_WalkEtiLimite.setText("Quantity");
	  	jtf_snmpv3_WalkEtiLimite.setText(erroresGenerales21);
	  	jl_snmpv3_WalkEti.setText("OID");
	  	jb_snmpv3_Walk.setText("Walk");
	  	jb_snmpv3_Walk.setToolTipText("Press to start the MIB Walk.");
	  	jl_snmpv3_getWalkModSeg.setText("Security");
  	    //--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK
  	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 	    
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET
  	    jp_snmpv3_Set.setBorder(BorderFactory.createTitledBorder("Set Command"));
  	    jl_snmpv3_SetEtiSet.setText("OID");
  	    jl_snmpv3_SetEtiTipo.setText("Data Type");
  	    jl_snmpv3_SetEtiSetValor.setText("Value");
  	    jb_snmpv3_setAdd.setText("Add");
  	    jb_snmpv3_setAdd.setToolTipText("Press to add the OID.");
  	    jl_snmpv3_setObjs.setText("Objects");
  	    jb_snmpv3_setUndo.setText("Undo");
  	    jb_snmpv3_setUndo.setToolTipText("Press to erase the last OID added.");
  	    jb_snmpv3_SetSet.setText("Set");
  	    jb_snmpv3_SetSet.setToolTipText("Press to set the new value(s).");
  	    jl_snmpv3_setModSeg.setText("Security");
  	    //--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	  		
  	    //--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
  	    jp_snmpv3_Traps.setBorder(BorderFactory.createTitledBorder("Receive TRAPs (SNMP v1/2c/3) and INFORMs (SNMP v2c/3)"));  
  	    jp_snmpv3_TrapsSend.setBorder(BorderFactory.createTitledBorder("Send TRAPs and INFORMs SNMP v3"));
  	    jl_snmpv3_TrapSndHost.setText("Destination IP Address");
  	    jl_snmpv3_TrapSndTipo.setText("Trap Type");
  	    jl_snmpv3_PtoCom.setText("Port");
  	    jl_snmpv3_Int.setText("Retries");
  	    jl_snmpv3_Timeout.setText("TimeOut (ms)");
  	    jl_snmpv3_Enter.setText("Enterprise OID");
  	    
  	    jcb_snmpv3_TrapSel.removeItemAt(5);
  	    //jcb_snmpv2c_TrapSel.addItem("coldStart");
	  	//jcb_snmpv2c_TrapSel.addItem("warmStart");
	  	//jcb_snmpv2c_TrapSel.addItem("linkDown");
	  	//jcb_snmpv2c_TrapSel.addItem("linkUp");
	  	//jcb_snmpv2c_TrapSel.addItem("authenticationFailure");	  	
	  	jcb_snmpv3_TrapSel.addItem("Other");
	  	jcb_snmpv3_TrapSel.setSelectedIndex(0);
  	    
  	    jcb_snmpv3_TrpInfSel.setSelectedIndex(0);
	  	
	  	jl_snmpv3_TrpInfSel.setText("Select an Option");
	  	jl_snmpv3_OtroTrp.setText("Trap OID");
	  	jl_snmpv3_Descr.setText("Description");
	  	jl_snmpv3_TpDto.setText("Data Type");
	  	jb_SndTrapv3.setText("Send TRAP");
	  	jb_SndTrapv3.setToolTipText("Press to send the TRAP.");
	  	                          
  	    jl_snmpv3_TrpVer.setText("Show Data");
  	    
  	    jcb_snmpv3_TrpVer.removeAllItems();
  	    jcb_snmpv3_TrpVer.addItem(opcionSi);
  	    jcb_snmpv3_TrpVer.addItem(opcionNo);
  	    jcb_snmpv3_TrpVer.setSelectedIndex(1);
  	    
  	    jl_snmpv3_TrpUsr.setText("Username");
  	    jl_snmpv3_TrpAut.setText("Auth. Pass.");
  	    jl_snmpv3_TrpPriv.setText("Priv. Pass.");
  	    jl_snmpv3_trpModSeg.setText("Security");
  	    
  	    erroresGenerales55 = "Must enter the username.\n";	
  		erroresGenerales56 = "Must enter the authentication password. It has to be at least 8 characters long.\n";
  		erroresGenerales57 = "Must enter the privacy password. It has to be at least 8 characters long.\n";
  	    //--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
          	     	    
  	  //Fin de SNMPv3-----------------------------------------------------------------------------------------------------
  	  
  	}
  	
  	/*
  	 * Change the comented text.
  	 * In the line 8510 write the name of your language. Hast to be the same that the one that you write in line 7160.
  	 * The text that you need translate is the one that you can find between the lines 8515 and 9079.
  	 *
  	 *
  	 *  	 
  	 if (idioma.equals("English")){
  	  //System.out.println("Cambiando idioma a ingles");
  	  //Para la interfaz general
  	  jm_archive.setText("File");//aade el menu
  	  jmi_outbound.setText("Exit");//crea un jmenuitem
  	  jm_help.setText("Help");
  	  jmi_helpHelp.setText("User's Guide");
  	  jmi_about.setText("About");
  	  jb_mibtree.setText("Import MIBs");
  	  jb_mibtree.setToolTipText("Press to import MIBs.");
  	  aboutDeTitulo = nameOfTheProgram+" - About";
  	  String encabezadoAcercaDe = "The software was developed in UCV (Universidad Central\n"+
								  "de Venezuela) as the final project to obtain the Bachelor\n"+
								  "of Science (Licenciatura) in Computer Science, by:\n";
	  String ac3 = 				  "\nPablo Poskal\npabloposkal@gmail.com\n";
      String ac2 = 				  "\nGustavo Ayala\ngustavoucv@yahoo.com.mx\n";
      String tutoriado = 	      "\nAdvisor: "+
	  							  "\n\nProf. Eric Gamess\negamess@kuaimare.ciens.ucv.ve\n\n";
      String ac4 = 				  "This software uses SNMP4J and Mibble libraries.\n"+
    			   				  "See http://www.snmp4j.org and http://www.mibble.org \n"+  	  	
    			   				  "for information.\n";
      String ac = (encabezadoAcercaDe+ac2+ac3+tutoriado+ac4);
      aboutDe = ac;
      
      String etiquetaIdioma="";
      for (int yy=0;yy<165; yy++){
		  etiquetaIdioma=etiquetaIdioma.concat(" ");
		}
		etiquetaIdioma=etiquetaIdioma.concat(" Idioma / Language   ");
      jl_idioma.setText("           ".concat(etiquetaIdioma));
      //PARA EL COMBO BOX DE ACCION A REALIZAR
        //COMANDOS ADMITIDOS
  	    ConfigureParameters = "Connection Options";
  	    ComandoGet = "Get Command";
  	    ComandoGetNext = "GetNext Command";
  	    ComandoGetBulk = "GetBulk Command";
  	    ComandoGetTable = "GetTable";
  	    ComandoWalk = "Walk";
  	    ComandoSet = "Set Command";
  	    EnviarVerTraps = "Send/Receive Traps";
  	    //COMANDOS EN SNMPv1
  	    jl_snmpv1_sel.setText("Action Selected");
  	    jcb_snmpv1_sel.removeAllItems();
  	    jcb_snmpv1_sel.addItem(ConfigureParameters);
	  	jcb_snmpv1_sel.addItem(ComandoGet);
	  	jcb_snmpv1_sel.addItem(ComandoGetNext);
	  	jcb_snmpv1_sel.addItem(ComandoGetTable);
	  	jcb_snmpv1_sel.addItem(ComandoWalk);
	  	jcb_snmpv1_sel.addItem(ComandoSet);
	  	jcb_snmpv1_sel.addItem(EnviarVerTraps);
  	    //COMANDOS EN SNMPv2C
  	    jl_snmpv2c_sel.setText("Action Selected");
	  	jcb_snmpv2c_sel.removeAllItems();
		jcb_snmpv2c_sel.addItem(ConfigureParameters);
	  	jcb_snmpv2c_sel.addItem(ComandoGet);
	  	jcb_snmpv2c_sel.addItem(ComandoGetNext);
	  	jcb_snmpv2c_sel.addItem(ComandoGetBulk);
	  	jcb_snmpv2c_sel.addItem(ComandoGetTable);
	  	jcb_snmpv2c_sel.addItem(ComandoWalk);
	  	jcb_snmpv2c_sel.addItem(ComandoSet);
	  	jcb_snmpv2c_sel.addItem(EnviarVerTraps);
  	    //COMANDOS EN SNMPv3
  	    jl_snmpv3_sel.setText("Action Selected");
  	    jcb_snmpv3_sel.removeAllItems();
  	    jcb_snmpv3_sel.addItem(ConfigureParameters);
  		jcb_snmpv3_sel.addItem(ComandoGet);
  		jcb_snmpv3_sel.addItem(ComandoGetNext);
  		jcb_snmpv3_sel.addItem(ComandoGetBulk);
  		jcb_snmpv3_sel.addItem(ComandoGetTable);
  		jcb_snmpv3_sel.addItem(ComandoWalk);
  		jcb_snmpv3_sel.addItem(ComandoSet);
  		jcb_snmpv3_sel.addItem(EnviarVerTraps);
  		
      //Para SNMPv1-------------------------------------------------------------------------------------------------------
  		
  		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS----------------
  	    jp_snmpv1_Con.setBorder(BorderFactory.createTitledBorder("Connection Options"));
  	    jl_snmpv1_IP.setText("Agent IP Address");
  	    jl_snmpv1_pto.setText("Port Number");
  	    jl_snmpv1_comLec.setText("Read Community");
  	    jtf_snmpv1_comEsc.setText("Write Community");
  	    jl_snmpv1_VerCom.setText("Show Communities");
  	    jl_snmpv1_inten.setText("Retries");
  	    jl_snmpv1_timeOut.setText("TimeOut (ms)");
  	    jb_snmpv1_aplicarPara.setText("Apply Changes");
  	    jb_snmpv1_aplicarPara.setToolTipText("Press to set the new values.");
  	    configParamError01 = "Must enter the SNMP agent IP address.\n";
  		configParamError02 = "Must enter a valid IP address.\n";	
  		configParamError03 = "Must enter the port number. It has to be a numeric value.\n";	
	    configParamError04 = "Must enter the read community.\n";	
	    configParamError05 = "Must enter the write community.\n";
	    configParamError06 = "Must enter the retries. It has to be a numeric value.\n";
	    configParamError07 = "Must enter the retries. It has to be a numeric value greater than zero.\n";	 	
	    configParamError08 = "Must enter the timeout. It has to be a numeric value.\n";	 	
	    configParamError09 = "Must enter the timeout. It has to be a numeric value greater than zero.\n";	 	
	    configParamResult01 = "Changes successfully applied.";
  	    opcionSi = "Yes";
  		opcionNo = "No";  
  	    jcb_snmpv1_VerCom.removeAllItems();
  	    jcb_snmpv1_VerCom.addItem(opcionSi);
  	    jcb_snmpv1_VerCom.addItem(opcionNo);
  	    jcb_snmpv1_VerCom.setSelectedIndex(1);	  	 
  	    //--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS----------------
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  		
  		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET----
  	    jp_snmpv1_Get.setBorder(BorderFactory.createTitledBorder("Get Command"));
  	    jl_snmpv1_getEtiGet.setText("OID");
  	    jb_snmpv1_add.setText("Add");
  	    jb_snmpv1_add.setToolTipText("Press to add the OID.");
  	    jl_snmpv1_getObjs.setText("Objects");
  	    jb_snmpv1_undo.setText("Undo");
  	    jb_snmpv1_undo.setToolTipText("Press to erase the last OID added.");
  	    jb_snmpv1_getGet.setText("Get");
  	    jb_snmpv1_getGet.setToolTipText("Press to get the value(s).");
  	    erroresGenerales01 = "Must select or write an OID.";
  	    erroresGenerales02 = "Must select or write a valid OID.";
  	    erroresGenerales03 = "There is no more objects to erase.";
  	    erroresGenerales04 = "\n Request timed out.\n";
  	    //--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET----
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  		
  		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--
  	    jp_snmpv1_GetNext.setBorder(BorderFactory.createTitledBorder("GetNext Command"));
  	    jl_snmpv1_GetNextEtiGetNext.setText("OID");
  	    jb_snmpv1_GetNext_add.setText("Add");
  	    jb_snmpv1_GetNext_add.setToolTipText("Press to add the OID.");
  	    jl_snmpv1_getNextObjs.setText("Objects");
  	    jb_snmpv1_GetNextUndo.setText("Undo");
  	    jb_snmpv1_GetNextUndo.setToolTipText("Press to erase the last OID added.");
  	    jb_snmpv1_GetNextGetNext.setText("GetNext");
  	    jb_snmpv1_GetNextGetNext.setToolTipText("Press to get the next value(s).");
  	    //--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  		
  		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE
  	    jp_snmpv1_getTable.setBorder(BorderFactory.createTitledBorder("GetTable"));
  	    jl_snmpv1_getTableEtigetTable.setText("OID");
  	    jb_snmpv1_getTablegetTable.setText("GetTable");
  	    jb_snmpv1_getTablegetTable.setToolTipText("Press to get the table.");
  	    erroresGenerales05 = "\nError: Request timed out.\n";
  	    erroresGenerales06 = "There is no data for the specified OID, the causes could be:\n- The OID selected isn't a table.\n- The agent don't has data for the selected table.\n- Entry selected instead of the table.";
  	    erroresGenerales07 = "Error: The agent don't has data for the specified OID.\n";
  	    erroresGenerales08 = "Error: The agent don't has the specified username.\n";
  	    erroresGenerales09 = "Error: Wrong password and/or authentication protocol.\n";
  	    erroresGenerales10 = "Error: Wrong password and/or privacy protocol.\n";
  	    erroresGenerales11 = "Error: Request timed out.\n";
  	    erroresGenerales12 = "Error: The specified OID is greater than the greater OID of the selected MIB.\n";
  	    erroresGenerales13 = "The variable given isn't the successor of the required:\n";
  	    //--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK
  	    jp_snmpv1_walk.setBorder(BorderFactory.createTitledBorder("Walk"));
  	    jl_snmpv1_WalkEtiLimitePregunta.setText("Limit the quantity of variables retrieved");
  	    jcb_snmpv1_WalkEtiLimitePregunta.removeAllItems();
  	    jcb_snmpv1_WalkEtiLimitePregunta.addItem(opcionSi);
  	    jcb_snmpv1_WalkEtiLimitePregunta.addItem(opcionNo);
  	    jcb_snmpv1_WalkEtiLimitePregunta.setSelectedIndex(1);
	  	jl_snmpv1_WalkEtiLimite.setText("Quantity");
	  	erroresGenerales21 = "No Limit";
	  	jtf_snmpv1_WalkEtiLimite.setText(erroresGenerales21);
	  	jl_snmpv1_WalkEti.setText("OID");
	  	jb_snmpv1_Walk.setText("Walk");
	  	jb_snmpv1_Walk.setToolTipText("Press to start the MIB Walk.");
	  	erroresGenerales14 = "Must enter the quantity of variables retrieved.";
	  	erroresGenerales15 = "Must enter the quantity of variables retrieved. It has to be a numeric value.";
	  	erroresGenerales16 = "\nRunning Walk, wait a moment...\n\n";
	  	erroresGenerales17 = "Total requests sent:    ";
	  	erroresGenerales18 = "Total objects received: ";
	  	erroresGenerales19 = "Total walk time:        ";
	  	erroresGenerales20 = " milliseconds";
  	    //--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET
  	    jp_snmpv1_Set.setBorder(BorderFactory.createTitledBorder("Set Command"));
  	    jl_snmpv1_SetEtiSet.setText("OID");
  	    jl_snmpv1_SetEtiTipo.setText("Data Type");
  	    jl_snmpv1_SetEtiSetValor.setText("Value");
  	    jb_snmpv1_setAdd.setText("Add");
  	    jb_snmpv1_setAdd.setToolTipText("Press to add the OID.");
  	    jl_snmpv1_setObjs.setText("Objects");
  	    jb_snmpv1_setUndo.setText("Undo");
  	    jb_snmpv1_setUndo.setToolTipText("Press to erase the last OID added.");
  	    jb_snmpv1_SetSet.setText("Set");
  	    jb_snmpv1_SetSet.setToolTipText("Press to set the new value(s).");
  	    erroresGenerales22 = "Must enter the new value.";
  	    erroresGenerales23 = "Must select the OID data type.";
  	    erroresGenerales24 = "Wrong data type. The operation has been cancelled.";
  	    erroresGenerales25 = "Unknown data type. The operation has been cancelled.";
  	    //--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET
  	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 	      	    
  		  		
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////	  		
  	    //--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
  	    jp_snmpv1_Traps.setBorder(BorderFactory.createTitledBorder("Receive TRAPs (SNMP v1/2c/3) and INFORMs (SNMP v2c/3)"));  
  	    jp_snmpv1_TrapsSend.setBorder(BorderFactory.createTitledBorder("Send TRAPs SNMP v1"));
  	    jl_snmpv1_TrapSndHost.setText("Destination IP Address");
  	    jl_snmpv1_TrapSndTipo.setText("Trap Type");
  	    jl_snmpv1_PtoCom.setText("Port");
  	    jl_snmpv1_Com.setText("Community");
  	    jl_snmpv1_Int.setText("Retries");
  	    jl_snmpv1_Timeout.setText("TimeOut (ms)");
  	    jl_snmpv1_Enter.setText("Enterprise OID");
  	    jl_snmpv1_TrapSpc.setText("Specific Trap");
  	    jb_SndTrap.setText("Send TRAP");
  	    jb_SndTrap.setToolTipText("Press to send the TRAP.");
  	    erroresGenerales32 = "Must enter the specific trap. It has to be a numeric value.\n";
  	    erroresGenerales33 = "Must enter the Enterprise OID.\n";
  	    erroresGenerales34 = "Must enter the Enterprise OID. It has to be an OID.\n";
  	    erroresGenerales35 = "TRAP successfully sent.";
  	    erroresGenerales36 = "Must enter the destination IP address.\n";
  	    erroresGenerales37 = "Must enter the community.\n";
  	    
  	    mensajesDeTraps01 = "Waiting for TRAPs or INFORMs...\n";
  	    mensajesDeTraps02 = "\nPacket #";
  	    mensajesDeTraps03 = "SNMP Version 1 TRAP Arrived.\n";
  	    mensajesDeTraps04 = "Received: ";
  	    mensajesDeTraps05 = "IP / Source Port: ";
  	    mensajesDeTraps06 = "Community: ";
  	    mensajesDeTraps07 = "Enterprise: ";
  	    mensajesDeTraps08 = "TRAP Type: ";
  	    mensajesDeTraps09 = "Specific TRAP: ";
  	    mensajesDeTraps10 = "TimeStamp: ";
  	    mensajesDeTraps11 = "SNMP Version 2c INFORM Arrived.\n";
  	    mensajesDeTraps12 = "SNMP Version 3 INFORM Arrived.\n";
  	    mensajesDeTraps13 = "Username: ";
  	    mensajesDeTraps14 = "OID #";
  	    mensajesDeTraps15 = "Value #";
  	    mensajesDeTraps16 = "SNMP Version 2c TRAP Arrived.\n";
  	    mensajesDeTraps17 = "SNMP Version 3 TRAP Arrived.\n";
  	    mensajesDeTraps18 = "TRAP Type: Other\n";
  		//--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
  		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	  		
  	      		
  	  //Fin de SNMPv1-----------------------------------------------------------------------------------------------------  	
  	  
  	  //Para SNMPv2c-------------------------------------------------------------------------------------------------------
  		
  		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS----------------
  	    jp_snmpv2c_Con.setBorder(BorderFactory.createTitledBorder("Connection Options"));
  	    jl_snmpv2c_IP.setText("Agent IP Address");
  	    jl_snmpv2c_pto.setText("Port Number");
  	    jl_snmpv2c_comLec.setText("Read Community");
  	    jl_snmpv2c_comEsc.setText("Write Community");
  	    jl_snmpv2c_VerCom.setText("Show Communities");
  	    jl_snmpv2c_inten.setText("Retries");
  	    jl_snmpv2c_timeOut.setText("TimeOut (ms)");
  	    jb_snmpv2c_aplicarPara.setText("Apply Changes");
  	    jb_snmpv2c_aplicarPara.setToolTipText("Press to set the new values.");
  	    opcionSi = "Yes";
  		opcionNo = "No";  
  	    jcb_snmpv2c_VerCom.removeAllItems();
  	    jcb_snmpv2c_VerCom.addItem(opcionSi);
  	    jcb_snmpv2c_VerCom.addItem(opcionNo);
  	    jcb_snmpv2c_VerCom.setSelectedIndex(1);	  	 
  	    //--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS----------------
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET----
  	    jp_snmpv2c_Get.setBorder(BorderFactory.createTitledBorder("Get Command"));
  	    jl_snmpv2c_getEtiGet.setText("OID");
  	    jb_snmpv2c_getAdd.setText("Add");
  	    jb_snmpv2c_getAdd.setToolTipText("Press to add the OID.");
  	    jl_snmpv2c_getObjs.setText("Objects");
  	    jb_snmpv2c_getUndo.setText("Undo");
  	    jb_snmpv2c_getUndo.setToolTipText("Press to erase the last OID added.");
  	    jb_snmpv2c_getGet.setText("Get");
  	    jb_snmpv2c_getGet.setToolTipText("Press to get the value(s).");
  	    //--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET----
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--
  	    jp_snmpv2c_GetNext.setBorder(BorderFactory.createTitledBorder("GetNext Command"));
  	    jl_snmpv2c_GetNextEtiGetNext.setText("OID");
  	    jb_snmpv2c_GetNext_add.setText("Add");
  	    jb_snmpv2c_GetNext_add.setToolTipText("Press to add the OID.");
  	    jl_snmpv2c_getNextObjs.setText("Objects");
  	    jb_snmpv2c_GetNextUndo.setText("Undo");
  	    jb_snmpv2c_GetNextUndo.setToolTipText("Press to erase the last OID added.");
  	    jb_snmpv2c_GetNextGetNext.setText("GetNext");
  	    jb_snmpv2c_GetNextGetNext.setToolTipText("Press to get the next value(s).");
  	    //--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--
  	    jp_snmpv2c_GetBulk.setBorder(BorderFactory.createTitledBorder("GetBulk Command"));
  	    jl_snmpv2c_nonRepe.setText("NonRepeaters");
  	    jl_snmpv2c_maxRep.setText("MaxRepetitions");
  	    jb_snmpv2c_GetBulk_add.setText("Add");
  	    jb_snmpv2c_GetBulk_add.setToolTipText("Press to add the OID.");
  	    jb_snmpv2c_GetBulkUndo.setText("Undo");
  	    jb_snmpv2c_GetBulkUndo.setToolTipText("Press to erase the last OID added.");
  	    jl_snmpv2c_getBulkObjs.setText("Objects");
  	    jl_snmpv2c_GetBulkEtiSet.setText("OID");
  	    jb_snmpv2c_GetBulkGetBulk.setText("GetBulk");
  	    jb_snmpv2c_GetBulkGetBulk.setToolTipText("Press to get the value(s).");
  	    erroresGenerales26 = "Must enter the NonRepeaters. It has to be a numeric value.";
  	    erroresGenerales27 = "Must enter the MaxRepetitions. It has to be a numeric value greater than zero.";
  	    erroresGenerales28 = "\nGetBulk: \n";
  	    //--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE
  	    jp_snmpv2c_getTable.setBorder(BorderFactory.createTitledBorder("GetTable"));
  	    jl_snmpv2c_getTableEtigetTable.setText("OID");
  	    jb_snmpv2c_getTablegetTable.setText("GetTable");
  	    jb_snmpv2c_getTablegetTable.setToolTipText("Press to get the table.");
  	    //--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK
	  	jp_snmpv2c_walk.setBorder(BorderFactory.createTitledBorder("Walk"));
  	    jl_snmpv2c_WalkEtiLimitePregunta.setText("Limit the quantity of variables retrieved");
  	    jcb_snmpv2c_WalkEtiLimitePregunta.removeAllItems();
  	    jcb_snmpv2c_WalkEtiLimitePregunta.addItem(opcionSi);
  	    jcb_snmpv2c_WalkEtiLimitePregunta.addItem(opcionNo);
  	    jcb_snmpv2c_WalkEtiLimitePregunta.setSelectedIndex(1);
	  	jl_snmpv2c_WalkEtiLimite.setText("Quantity");
	  	jtf_snmpv2c_WalkEtiLimite.setText(erroresGenerales21);
	  	jl_snmpv2c_WalkEti.setText("OID");
	  	jb_snmpv2c_Walk.setText("Walk");
	  	jb_snmpv2c_Walk.setToolTipText("Press to start the MIB Walk.");
  	    //--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET
  	    jp_snmpv2c_Set.setBorder(BorderFactory.createTitledBorder("Set Command"));
  	    jl_snmpv2c_SetEtiSet.setText("OID");
  	    jl_snmpv2c_SetEtiTipo.setText("Data Type");
  	    jl_snmpv2c_SetEtiSetValor.setText("Value");
  	    jb_snmpv2c_setAdd.setText("Add");
  	    jb_snmpv2c_setAdd.setToolTipText("Press to add the OID.");
  	    jl_snmpv2c_setObjs.setText("Objects");
  	    jb_snmpv2c_setUndo.setText("Undo");
  	    jb_snmpv2c_setUndo.setToolTipText("Press to erase the last OID added.");
  	    jb_snmpv2c_SetSet.setText("Set");
  	    jb_snmpv2c_SetSet.setToolTipText("Press to set the new value(s).");
  	    //--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET
  	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 	    
  	    
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	  		
  	    //--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
  	    jp_snmpv2c_Traps.setBorder(BorderFactory.createTitledBorder("Receive TRAPs (SNMP v1/2c/3) and INFORMs (SNMP v2c/3)"));  
  	    jp_snmpv2c_TrapsSend.setBorder(BorderFactory.createTitledBorder("Send TRAPs and INFORMs SNMP v2c"));
  	    jl_snmpv2c_TrapSndHost.setText("Destination IP Address");
  	    jl_snmpv2c_TrapSndTipo.setText("Trap Type");
  	    jl_snmpv2c_PtoCom.setText("Port");
  	    jl_snmpv2c_Com.setText("Community");
  	    jl_snmpv2c_Int.setText("Retries");
  	    jl_snmpv2c_Timeout.setText("TimeOut (ms)");
  	    jl_snmpv2c_Enter.setText("Enterprise OID");
  	    
  	    jcb_snmpv2c_TrapSel.removeItemAt(5);
  	    //jcb_snmpv2c_TrapSel.addItem("coldStart");
	  	//jcb_snmpv2c_TrapSel.addItem("warmStart");
	  	//jcb_snmpv2c_TrapSel.addItem("linkDown");
	  	//jcb_snmpv2c_TrapSel.addItem("linkUp");
	  	//jcb_snmpv2c_TrapSel.addItem("authenticationFailure");	  	
	  	jcb_snmpv2c_TrapSel.addItem("Other");
	  	jcb_snmpv2c_TrapSel.setSelectedIndex(0);
  	    jcb_snmpv2c_TrpInfSel.setSelectedIndex(0);
	  	
	  	jl_snmpv2c_TrpInfSel.setText("Select an Option");
	  	jl_snmpv2c_OtroTrp.setText("Trap OID");
	  	jl_snmpv2c_Descr.setText("Description");
	  	jl_snmpv2c_TpoDtoTrp.setText("Data Type");
	  	jb_SndTrapv2c.setText("Send TRAP");
	  	jb_SndTrapv2c.setToolTipText("Press to send the TRAP.");
	  	erroresGenerales38 = "Must enter the Inform OID.\n";
	  	erroresGenerales39 = "Must enter the Inform OID. It has to be an OID.\n";
	  	erroresGenerales40 = "Must enter the Trap OID.\n";
	  	erroresGenerales41 = "Must enter the Trap OID. It has to be an OID.\n";
	  	erroresGenerales42 = "Must enter the description.\n";
	  	erroresGenerales43 = "Wrong description data type. The operation has been cancelled.";
	  	erroresGenerales44 = "Must select the description data type. The operation has been cancelled.";
	  	erroresGenerales45 = "Trap";
	  	erroresGenerales46 = "Other";
	  	erroresGenerales47 = "Inform";
	  	erroresGenerales48 = "INFORM successfully sent.";
	  	erroresGenerales49 = "Send INFORM";
	    erroresGenerales50 = "Inform OID";
	    erroresGenerales51 = "Press to send the INFORM.";
	    erroresGenerales52 = "Send TRAP";
	    erroresGenerales53 = "Trap OID";
	    erroresGenerales54 = "Press to send the TRAP.";
  	    //--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		  		  		
  	  //Fin de SNMPv2c-----------------------------------------------------------------------------------------------------
  	  
  	  //Para SNMPv3-------------------------------------------------------------------------------------------------------
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS----------------
  	    jp_snmpv3_Con.setBorder(BorderFactory.createTitledBorder("Connection Options"));
  	    jl_snmpv3_IP.setText("Agent IP Address");
  	    jl_snmpv3_pto.setText("Port Number");
  	    jl_snmpv3_User.setText("Username");
  	    jl_snmpv3_Aut.setText("Authentication Password");
  	    jl_snmpv3_Priv.setText("Privacy Password");
  	    jl_snmpv3_VerUsr.setText("Show Private Data");
  	    jl_snmpv3_metAut.setText("Authentication Protocol");
  	    jl_snmpv3_metPriv.setText("Privacy Protocol");
  	    jl_snmpv3_inten.setText("Retries");
  	    jl_snmpv3_timeOut.setText("TimeOut (ms)");
  	    jb_snmpv3_aplicarPara.setText("Apply Changes");
  	    jb_snmpv3_aplicarPara.setToolTipText("Press to set the new values.");
  	    configParamError10 = "Must enter the username.\n";	
  		configParamError11 = "Must enter the authentication password.\n";
  		configParamError12 = "The authentication password must be at least 8 characters long.\n";
  		configParamError13 = "Must enter the privacy password.\n";
  		configParamError14 = "The privacy password must be at least 8 characters long.\n";
  	    opcionSi = "Yes";
  		opcionNo = "No";
  	    jcb_snmpv3_VerUsr.removeAllItems();
  	    jcb_snmpv3_VerUsr.addItem(opcionSi);
  	    jcb_snmpv3_VerUsr.addItem(opcionNo);
  	    jcb_snmpv3_VerUsr.setSelectedIndex(1);	  	 
  	    //--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS----------------
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET----
  	    jp_snmpv3_Get.setBorder(BorderFactory.createTitledBorder("Get Command"));
  	    jl_snmpv3_getEtiGet.setText("OID");
  	    jb_snmpv3_getAdd.setText("Add");
  	    jb_snmpv3_getAdd.setToolTipText("Press to add the OID.");
  	    jl_snmpv3_getObjs.setText("Objects");
  	    jb_snmpv3_getUndo.setText("Undo");
  	    jb_snmpv3_getUndo.setToolTipText("Press to erase the last OID added.");
  	    jb_snmpv3_getGet.setText("Get");
  	    jb_snmpv3_getGet.setToolTipText("Press to get the value(s).");
  	    jl_snmpv3_getModSeg.setText("Security");
		erroresGenerales29 = " Error: The agent don't has the specified username.\n";
  	    erroresGenerales30 = " Error: Wrong password and/or authentication protocol.\n";
  	    erroresGenerales31 = " Error: Wrong password and/or privacy protocol.\n";
  	    //--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET--PANTALLA DEL GET----
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--
  	    jp_snmpv3_GetNext.setBorder(BorderFactory.createTitledBorder("GetNext Command"));
  	    jl_snmpv3_GetNextEtiGetNext.setText("OID");
  	    jb_snmpv3_getNextAdd.setText("Add");
  	    jb_snmpv3_getNextAdd.setToolTipText("Press to add the OID.");
  	    jl_snmpv3_getNextObjs.setText("Objects");
  	    jb_snmpv3_getNextUndo.setText("Undo");
  	    jb_snmpv3_getNextUndo.setToolTipText("Press to erase the last OID added.");
  	    jb_snmpv3_GetNextGetNext.setText("GetNext");
  	    jb_snmpv3_GetNextGetNext.setToolTipText("Press to get the next value(s).");
  	    jl_snmpv3_getNextModSeg.setText("Security");
  	    //--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--PANTALLA DEL GETNEXT--
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--
  	    jp_snmpv3_GetBulk.setBorder(BorderFactory.createTitledBorder("GetBulk Command"));
  	    jl_snmpv3_nonRepe.setText("NonRepeaters");
  	    jl_snmpv3_maxRep.setText("MaxRepetitions");
  	    jb_snmpv3_GetBulk_add.setText("Add");
  	    jb_snmpv3_GetBulk_add.setToolTipText("Press to add the OID.");
  	    jb_snmpv3_GetBulkUndo.setText("Undo");
  	    jb_snmpv3_GetBulkUndo.setToolTipText("Press to erase the last OID added.");
  	    jl_snmpv3_getBulkObjs.setText("Objects");
  	    jl_snmpv3_GetBulkEtiSet.setText("OID");
  	    jb_snmpv3_GetBulkGetBulk.setText("GetBulk");
  	    jb_snmpv3_GetBulkGetBulk.setToolTipText("Press to get the value(s).");
  	    jl_snmpv3_getBulkModSeg.setText("Security");
  	    //--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--PANTALLA DEL GETBULK--
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE
  	    jp_snmpv3_getTable.setBorder(BorderFactory.createTitledBorder("GetTable"));
  	    jl_snmpv3_getTableEtigetTable.setText("OID");
  	    jb_snmpv3_getTablegetTable.setText("GetTable");
  	    jb_snmpv3_getTablegetTable.setToolTipText("Press to get the table.");
  	    jl_snmpv3_getTableModSeg.setText("Security");
  	    //--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE--PANTALLA DEL GETTABLE
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK
  	    jp_snmpv3_walk.setBorder(BorderFactory.createTitledBorder("Walk"));
  	    jl_snmpv3_WalkEtiLimitePregunta.setText("Limit the quantity of variables retrieved");
  	    jcb_snmpv3_WalkEtiLimitePregunta.removeAllItems();
  	    jcb_snmpv3_WalkEtiLimitePregunta.addItem(opcionSi);
  	    jcb_snmpv3_WalkEtiLimitePregunta.addItem(opcionNo);
  	    jcb_snmpv3_WalkEtiLimitePregunta.setSelectedIndex(1);
	  	jl_snmpv3_WalkEtiLimite.setText("Quantity");
	  	jtf_snmpv3_WalkEtiLimite.setText(erroresGenerales21);
	  	jl_snmpv3_WalkEti.setText("OID");
	  	jb_snmpv3_Walk.setText("Walk");
	  	jb_snmpv3_Walk.setToolTipText("Press to start the MIB Walk.");
	  	jl_snmpv3_getWalkModSeg.setText("Security");
  	    //--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK--PANTALLA DEL WALK
  	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 	    
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET
  	    jp_snmpv3_Set.setBorder(BorderFactory.createTitledBorder("Set Command"));
  	    jl_snmpv3_SetEtiSet.setText("OID");
  	    jl_snmpv3_SetEtiTipo.setText("Data Type");
  	    jl_snmpv3_SetEtiSetValor.setText("Value");
  	    jb_snmpv3_setAdd.setText("Add");
  	    jb_snmpv3_setAdd.setToolTipText("Press to add the OID.");
  	    jl_snmpv3_setObjs.setText("Objects");
  	    jb_snmpv3_setUndo.setText("Undo");
  	    jb_snmpv3_setUndo.setToolTipText("Press to erase the last OID added.");
  	    jb_snmpv3_SetSet.setText("Set");
  	    jb_snmpv3_SetSet.setToolTipText("Press to set the new value(s).");
  	    jl_snmpv3_setModSeg.setText("Security");
  	    //--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET--PANTALLA DEL SET
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////	  		
  	    //--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
  	    jp_snmpv3_Traps.setBorder(BorderFactory.createTitledBorder("Receive TRAPs (SNMP v1/2c/3) and INFORMs (SNMP v2c/3)"));  
  	    jp_snmpv3_TrapsSend.setBorder(BorderFactory.createTitledBorder("Send TRAPs and INFORMs SNMP v3"));
  	    jl_snmpv3_TrapSndHost.setText("Destination IP Address");
  	    jl_snmpv3_TrapSndTipo.setText("Trap Type");
  	    jl_snmpv3_PtoCom.setText("Port");
  	    jl_snmpv3_Int.setText("Retries");
  	    jl_snmpv3_Timeout.setText("TimeOut (ms)");
  	    jl_snmpv3_Enter.setText("Enterprise OID");
  	    
  	    jcb_snmpv3_TrapSel.removeItemAt(5);
  	    //jcb_snmpv2c_TrapSel.addItem("coldStart");
	  	//jcb_snmpv2c_TrapSel.addItem("warmStart");
	  	//jcb_snmpv2c_TrapSel.addItem("linkDown");
	  	//jcb_snmpv2c_TrapSel.addItem("linkUp");
	  	//jcb_snmpv2c_TrapSel.addItem("authenticationFailure");	  	
	  	jcb_snmpv3_TrapSel.addItem("Other");
	  	jcb_snmpv3_TrapSel.setSelectedIndex(0);
  	    
  	    jcb_snmpv3_TrpInfSel.setSelectedIndex(0);
	  	
	  	jl_snmpv3_TrpInfSel.setText("Select an Option");
	  	jl_snmpv3_OtroTrp.setText("Trap OID");
	  	jl_snmpv3_Descr.setText("Description");
	  	jl_snmpv3_TpDto.setText("Data Type");
	  	jb_SndTrapv3.setText("Send TRAP");
	  	jb_SndTrapv3.setToolTipText("Press to send the TRAP.");
	  	                          
  	    jl_snmpv3_TrpVer.setText("Show Data");
  	    
  	    jcb_snmpv3_TrpVer.removeAllItems();
  	    jcb_snmpv3_TrpVer.addItem(opcionSi);
  	    jcb_snmpv3_TrpVer.addItem(opcionNo);
  	    jcb_snmpv3_TrpVer.setSelectedIndex(1);
  	    
  	    jl_snmpv3_TrpUsr.setText("Username");
  	    jl_snmpv3_TrpAut.setText("Auth. Pass.");
  	    jl_snmpv3_TrpPriv.setText("Priv. Pass.");
  	    jl_snmpv3_trpModSeg.setText("Security");
  	    
  	    erroresGenerales55 = "Must enter the username.\n";	
  		erroresGenerales56 = "Must enter the authentication password. It has to be at least 8 characters long.\n";
  		erroresGenerales57 = "Must enter the privacy password. It has to be at least 8 characters long.\n";
  	    //--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
          	     	    
  	  //Fin de SNMPv3-----------------------------------------------------------------------------------------------------
  	  
  	}
  	 
  	 */
  	
  	traps.cambiarIdiomaAMensajes(mensajesDeTraps01,mensajesDeTraps02,mensajesDeTraps03,mensajesDeTraps04,mensajesDeTraps05,mensajesDeTraps06,mensajesDeTraps07,mensajesDeTraps08,mensajesDeTraps09,mensajesDeTraps10,mensajesDeTraps11,mensajesDeTraps12,mensajesDeTraps13,mensajesDeTraps14,mensajesDeTraps15,mensajesDeTraps16,mensajesDeTraps17,mensajesDeTraps18);
  	idiomaSeleccionado=idioma;
  }

  public static void main(String args[]){
    managerSNMP a = new managerSNMP();
    
  }

}