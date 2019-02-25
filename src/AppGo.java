
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import mibblebrowser.MibNode;
import mibblebrowser.MibTreeBuilder;
import net.percederberg.mibble.Mib;
import net.percederberg.mibble.MibLoaderException;
import net.percederberg.mibble.MibType;
import net.percederberg.mibble.MibTypeTag;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.AuthSHA;
import org.snmp4j.security.PrivAES128;
import org.snmp4j.security.PrivAES192;
import org.snmp4j.security.PrivAES256;
import org.snmp4j.security.PrivDES;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.smi.Counter32;
import org.snmp4j.smi.Counter64;
import org.snmp4j.smi.Gauge32;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Opaque;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.Variable;
import puppeteer.GetTable.getTable;
import puppeteer.SNMPv3.SNMPv3;
import puppeteer.TrapInform.enviarTrapInform;
import puppeteer.TrapInform.recibirTrapInform;
import puppeteer.WALK.walk;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mathew
 */
public class AppGo extends javax.swing.JFrame {
File pathDesktop = new File(System.getProperty("user.home"), "Desktop");
String strSessionListDefault = pathDesktop + "\\SNMP\\SessionList.csv";
DefaultListModel defaultListModelFilteredItems = new DefaultListModel();

    
private String nameOfTheProgram = "Super Neat Master Puppeteer";

  //////////////////////////////////	
  private JFrame jframe;
  //////////////////////////////////
  private JPanel jpanel;  	
  //////////////////////////////////	
  // GUI private JMenuBar jmenubar;
  private JMenu jm_archive;
  private JMenu jm_help;
  // GUI private JMenuItem jmi_outbound,jmi_about,jmi_helpHelp;
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
  // GUI private JPanel jp_mibtree;
  // GUI private JScrollPane jsp_mibtree;
  // GUI private JButton jb_mibtree;
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
    
    private String opcionSi = "Yes";
    private String opcionNo = "No";  
  	    private String configParamError01 = "Must enter the SNMP agent IP address.\n";
  		private String configParamError02 = "Must enter a valid IP address.\n";	
  		private String configParamError03 = "Must enter the port number. It has to be a numeric value.\n";	
	    private String configParamError04 = "Must enter the read community.\n";	
	    private String configParamError05 = "Must enter the write community.\n";
	    private String configParamError06 = "Must enter the retries. It has to be a numeric value.\n";
	    private String configParamError07 = "Must enter the retries. It has to be a numeric value greater than zero.\n";	 	
	    private String configParamError08 = "Must enter the timeout. It has to be a numeric value.\n";	 	
	    private String configParamError09 = "Must enter the timeout. It has to be a numeric value greater than zero.\n";	 	
	    private String configParamResult01 = "Changes successfully applied.";
    
      	    private String configParamError10 = "Must enter the username.\n";	
  		private String configParamError11 = "Must enter the authentication password.\n";
  		private String configParamError12 = "The authentication password must be at least 8 characters long.\n";
  		private String configParamError13 = "Must enter the privacy password.\n";
  		private String configParamError14 = "The privacy password must be at least 8 characters long.\n";
                  	    private String erroresGenerales01 = "Must select or write an OID.";
  	    private String erroresGenerales02 = "Must select or write a valid OID.";
  	    private String erroresGenerales03 = "There is no more objects to erase.";
  	    private String erroresGenerales04 = "\n Request timed out.\n";
            		private String erroresGenerales29 = " Error: The agent don't has the specified username.\n";
  	    private String erroresGenerales30 = " Error: Wrong password and/or authentication protocol.\n";
  	    private String erroresGenerales31 = " Error: Wrong password and/or privacy protocol.\n";
            
              	    private String erroresGenerales26 = "Must enter the NonRepeaters. It has to be a numeric value.";
  	    private String erroresGenerales27 = "Must enter the MaxRepetitions. It has to be a numeric value greater than zero.";
  	    private String erroresGenerales28 = "\nGetBulk: \n";
              	    private String erroresGenerales05 = "\nError: Request timed out.\n";
  	    private String erroresGenerales06 = "There is no data for the specified OID, the causes could be:\n- The OID selected isn't a table.\n- The agent don't has data for the selected table.\n- Entry selected instead of the table.";
  	    private String erroresGenerales07 = "Error: The agent don't has data for the specified OID.\n";
  	    private String erroresGenerales08 = "Error: The agent don't has the specified username.\n";
  	    private String erroresGenerales09 = "Error: Wrong password and/or authentication protocol.\n";
  	    private String erroresGenerales10 = "Error: Wrong password and/or privacy protocol.\n";
  	    private String erroresGenerales11 = "Error: Request timed out.\n";
  	    private String erroresGenerales12 = "Error: The specified OID is greater than the greater OID of the selected MIB.\n";
  	    private String erroresGenerales13 = "The variable given isn't the successor of the required:\n";
            	  	private String erroresGenerales21 = "No Limit";
	  	private String erroresGenerales14 = "Must enter the quantity of variables retrieved.";
	  	private String erroresGenerales15 = "Must enter the quantity of variables retrieved. It has to be a numeric value.";
	  	private String erroresGenerales16 = "\nRunning Walk, wait a moment...\n\n";
	  	private String erroresGenerales17 = "Total requests sent:    ";
	  	private String erroresGenerales18 = "Total objects received: ";
	  	private String erroresGenerales19 = "Total walk time:        ";
	  	private String erroresGenerales20 = " milliseconds";
                  	    private String erroresGenerales22 = "Must enter the new value.";
  	    private String erroresGenerales23 = "Must select the OID data type.";
  	    private String erroresGenerales24 = "Wrong data type. The operation has been cancelled.";
  	    private String erroresGenerales25 = "Unknown data type. The operation has been cancelled.";
            
            	  	private String erroresGenerales38 = "Must enter the Inform OID.\n";
	  	private String erroresGenerales39 = "Must enter the Inform OID. It has to be an OID.\n";
	  	private String erroresGenerales40 = "Must enter the Trap OID.\n";
	  	private String erroresGenerales41 = "Must enter the Trap OID. It has to be an OID.\n";
	  	private String erroresGenerales42 = "Must enter the description.\n";
	  	private String erroresGenerales43 = "Wrong description data type. The operation has been cancelled.";
	  	private String erroresGenerales44 = "Must select the description data type. The operation has been cancelled.";
	  	private String erroresGenerales45 = "Trap";
	  	private String erroresGenerales46 = "Other";
	  	private String erroresGenerales47 = "Inform";
	  	private String erroresGenerales48 = "INFORM successfully sent.";
	  	private String erroresGenerales49 = "Send INFORM";
	    private String erroresGenerales50 = "Inform OID";
	    private String erroresGenerales51 = "Press to send the INFORM.";
	    private String erroresGenerales52 = "Send TRAP";
	    private String erroresGenerales53 = "Trap OID";
	    private String erroresGenerales54 = "Press to send the TRAP.";
              	    private String erroresGenerales32 = "Must enter the specific trap. It has to be a numeric value.\n";
  	    private String erroresGenerales33 = "Must enter the Enterprise OID.\n";
  	    private String erroresGenerales34 = "Must enter the Enterprise OID. It has to be an OID.\n";
  	    private String erroresGenerales35 = "TRAP successfully sent.";
  	    private String erroresGenerales36 = "Must enter the destination IP address.\n";
  	    private String erroresGenerales37 = "Must enter the community.\n";
  	    
  	    private String mensajesDeTraps01 = "Waiting for TRAPs or INFORMs...\n";
  	    private String mensajesDeTraps02 = "\nPacket #";
  	    private String mensajesDeTraps03 = "SNMP Version 1 TRAP Arrived.\n";
  	    private String mensajesDeTraps04 = "Received: ";
  	    private String mensajesDeTraps05 = "IP / Source Port: ";
  	    private String mensajesDeTraps06 = "Community: ";
  	    private String mensajesDeTraps07 = "Enterprise: ";
  	    private String mensajesDeTraps08 = "TRAP Type: ";
  	    private String mensajesDeTraps09 = "Specific TRAP: ";
  	    private String mensajesDeTraps10 = "TimeStamp: ";
  	    private String mensajesDeTraps11 = "SNMP Version 2c INFORM Arrived.\n";
  	    private String mensajesDeTraps12 = "SNMP Version 3 INFORM Arrived.\n";
  	    private String mensajesDeTraps13 = "Username: ";
  	    private String mensajesDeTraps14 = "OID #";
  	    private String mensajesDeTraps15 = "Value #";
  	    private String mensajesDeTraps16 = "SNMP Version 2c TRAP Arrived.\n";
  	    private String mensajesDeTraps17 = "SNMP Version 3 TRAP Arrived.\n";
  	    private String mensajesDeTraps18 = "TRAP Type: Other\n";
              	    private String erroresGenerales55 = "Must enter the username.\n";	
  		private String erroresGenerales56 = "Must enter the authentication password. It has to be at least 8 characters long.\n";
  		private String erroresGenerales57 = "Must enter the privacy password. It has to be at least 8 characters long.\n";
                  private String erroresGenerales58 = "Help file not found.";
                  
                  		private recibirTrapInform traps = null;
            
    /**
     * Creates new form AppGo
     */
    public AppGo() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, FileNotFoundException, URISyntaxException {
        initComponents();
        getSessionList();
               try {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        // turn off bold fonts
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        // re-install the Metal Look and Feel
        UIManager.setLookAndFeel(new MetalLookAndFeel());

        // Update the ComponentUIs for all Components. This
        // needs to be invoked for all windows.
        SwingUtilities.updateComponentTreeUI(this);
                            String laf = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(laf);
                
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(AppGo.class.getName()).log(Level.SEVERE, null, ex);
        }
               
        addWindowListener(new WindowAdapter() {
            @Override
            //--- Listen for window close
            public void windowClosing(WindowEvent we)
            { 
                String ObjButtons[] = {"Yes","No"};
                int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","You sure??",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
                if(PromptResult==JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }
            }
        });
                 	//----------DEFINICION DEL TAMAO Y UBICACION DE LA VENTANA EN LA PANTALLA----------------
  	//Dimension pantalla = getToolkit().getScreenSize();//obtiene el tamao usado de la pantalla
  	//int ancho  = 800;//ancho de la ventana a hacer
  	//int alto   = 600;//alto de la ventana a hacer
  	//int anchop = ((int)(((pantalla.getWidth ())-ancho)/2));//para calcular el centro del ancho
  	//int altop  = ((int)(((pantalla.getHeight())- alto)/2));//para calcular el centro del alto
    //------FIN DE LA DEFINICION DEL TAMAO Y UBICACION DE LA VENTANA EN LA PANTALLA----------
    
  	
  	//----------------------------CONSTRUCCION DE LA VENTANA----------------------------------
//  	jframe = new JFrame(nameOfTheProgram);//titulo de la ventana
//  	jframe.setBounds(anchop,altop,ancho,alto);//en que posicion y de que tamao se va a situar la ventana en la pantalla
//  	jframe.setIconImage(new ImageIcon("images/pred.gif").getImage());//se establece el icono de la ventana
//  	jframe.setResizable(false);//para que la ventana no se pueda maximizar
  	//------------------------FIN DE LA CONSTRUCCION DE LA VENTANA----------------------------
  	
  	
  	//--------------------------CREACION DEL JPANEL PRINCIPAL---------------------------------
//  	jpanel = new JPanel(true);//se crea el panel sobre el que se van a poner todos los componentes
//    //jpanel.setBackground(Color.lightGray);//establece el color del panel
//    jpanel.setLayout(null);//establece layout nulo, de esta forma puedes poner los componentes en un xy especifico
////  	jframe.getContentPane().add(jpanel,"Center");//adiciona el panel a la ventana
    //----------------------FIN DE LA CREACION DEL JPANEL PRINCIPAL---------------------------
  	
  	//-------------------------------PANEL DEL MIBTREE----------------------------------------
//	  	jp_mibtree = new JPanel();
//		jp_mibtree.setBackground(Color.black);
//		jp_mibtree.setBounds(new Rectangle(2,2,300,541));
//		jp_mibtree.setLayout(null);
//		//snmpv1.setVisible(false);
//		jpanel.add(jp_mibtree,null);
		
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
                jsp_mibtree.setViewportView(mibTree);
//		jsp_mibtree = new JScrollPane(mibTree);
//	    jsp_mibtree.setBounds(new Rectangle(0,0,300,511));
//	    jsp_mibtree.setWheelScrollingEnabled(true);
//	    jp_mibtree.add(jsp_mibtree,null);
	    
	    //Para cargar la mib
   		try {loadMib();} catch (Exception e){e.printStackTrace();}
	    //Fin del cargar mib
	        
//	    jb_mibtree = new JButton("Importar MIBs");
//	    jb_mibtree.setBounds(new Rectangle(0,511,300,30));
//	    jb_mibtree.setToolTipText("Presione para importar MIBs.");
//	    jp_mibtree.add(jb_mibtree,null);
	    
    	jb_mibtree.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e) {
            try {loadNewMib();} catch (Exception e1){e1.printStackTrace();}
          }
       	});
  	//-------------------------------FIN DEL PANEL DEL MIBTREE--------------------------------
  	  	
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
//        jp_snmpv3_Con.setVisible(false);
//        jp_snmpv3_Get.setVisible(false);
//        jp_snmpv3_GetNext.setVisible(false);
//        jp_snmpv3_GetBulk.setVisible(false);
//        jp_snmpv3_Set.setVisible(false);
//        jp_snmpv3_Traps.setVisible(false);
//        jp_snmpv3_TrapsSend.setVisible(false);    
//        jp_snmpv3_walk.setVisible(false);
//        jp_snmpv3_getTable.setVisible(false); 
//        if ((jcb_snmpv3_sel.getSelectedItem())==ConfigureParameters){jp_snmpv3_Con.setVisible(true);}
//        if ((jcb_snmpv3_sel.getSelectedItem())==ComandoGet){jp_snmpv3_Get.setVisible(true);}
//        if ((jcb_snmpv3_sel.getSelectedItem())==ComandoGetNext){jp_snmpv3_GetNext.setVisible(true);}
//        if ((jcb_snmpv3_sel.getSelectedItem())==ComandoGetBulk){jp_snmpv3_GetBulk.setVisible(true);}
//        if ((jcb_snmpv3_sel.getSelectedItem())==ComandoGetTable){jp_snmpv3_getTable.setVisible(true);}
//        if ((jcb_snmpv3_sel.getSelectedItem())==ComandoWalk){jp_snmpv3_walk.setVisible(true);}
//        if ((jcb_snmpv3_sel.getSelectedItem())==ComandoSet){jp_snmpv3_Set.setVisible(true);}
//        if ((jcb_snmpv3_sel.getSelectedItem())==EnviarVerTraps){jp_snmpv3_Traps.setVisible(true);}
//        if ((jcb_snmpv3_sel.getSelectedItem())==EnviarVerTraps){jp_snmpv3_TrapsSend.setVisible(true);}   
      }
    });


		//----------------------------------Pantalla de Conexin-----------------------------------
//	    jp_snmpv3_Con = new JPanel();
//	    //jp_snmpv2c_Con.setBackground(Color.white);
//	    jp_snmpv3_Con.setBounds(new Rectangle(0,30,483,483));
//		jp_snmpv3_Con.setBorder(BorderFactory.createTitledBorder("Configure Parameters"));
//		jp_snmpv3_Con.setLayout(null);
//		jp_snmpv3_Con.setVisible(true);
//		snmpv3.add(jp_snmpv3_Con,null);

//		jl_snmpv3_IP = new JLabel("Direccin IP del Agente");//USO DEL JLABEL
//    	jl_snmpv3_IP.setBounds(new Rectangle(76,26,160,20));//establece el xy del componente
//    	jp_snmpv3_Con.add(jl_snmpv3_IP,null);
//    	
//    	jtf_snmpv3_IP = new JTextField();//USO DEL JTEXTFIELD
//    	jtf_snmpv3_IP.setBounds(new Rectangle(246,26,160,20));//establece el xy del componente
    	jtf_snmpv3_IP.setText(String.valueOf(IP));
//    	jp_snmpv3_Con.add(jtf_snmpv3_IP,null);
    
//    	jl_snmpv3_pto = new JLabel("Puerto de Comunicaciones");//USO DEL JLABEL
//    	jl_snmpv3_pto.setBounds(new Rectangle(76,66,160,20));//establece el xy del componente
//    	jp_snmpv3_Con.add(jl_snmpv3_pto,null);
    
//    	jtf_snmpv3_pto = new JTextField();//USO DEL JTEXTFIELD
//    	jtf_snmpv3_pto.setBounds(new Rectangle(246,66,160,20));//establece el xy del componente
//    	jtf_snmpv3_pto.setText(String.valueOf(pto));
//    	jp_snmpv3_Con.add(jtf_snmpv3_pto,null);
//     	
//    	jl_snmpv3_User = new JLabel("Nombre de Usuario");//USO DEL JLABEL
//    	jl_snmpv3_User.setBounds(new Rectangle(76,146,160,20));//establece el xy del componente
//    	jp_snmpv3_Con.add(jl_snmpv3_User,null);
 
//    	jpf_snmpv3_User = new JPasswordField();//USO DEL JPASSWORDFIELD
//    	jpf_snmpv3_User.setBounds(new Rectangle(246,146,160,20));//establece el xy del componente
    	jpf_snmpv3_User.setEchoChar('*');
    	jpf_snmpv3_User.setText(String.valueOf(user));
//    	//jpf_snmpv3_User.setEchoChar((char)0);
//    	jp_snmpv3_Con.add(jpf_snmpv3_User,null);
   	
//    	jl_snmpv3_Aut = new JLabel("Clave de Autenticacin");//USO DEL JLABEL
//    	jl_snmpv3_Aut.setBounds(new Rectangle(76,186,160,20));//establece el xy del componente
//    	jp_snmpv3_Con.add(jl_snmpv3_Aut,null);

//    	jpf_snmpv3_Aut = new JPasswordField();//USO DEL JPASSWORDFIELD
//    	jpf_snmpv3_Aut.setBounds(new Rectangle(246,186,160,20));//establece el xy del componente
    	jpf_snmpv3_Aut.setEchoChar('*');
    	jpf_snmpv3_Aut.setText(String.valueOf(claveAut));
    	//jpf_snmpv3_User.setEchoChar((char)0);
//    	jp_snmpv3_Con.add(jpf_snmpv3_Aut,null);

//    	jl_snmpv3_Priv = new JLabel("Clave de Encriptacin");//USO DEL JLABEL
//    	jl_snmpv3_Priv.setBounds(new Rectangle(76,226,160,20));//establece el xy del componente
//    	jp_snmpv3_Con.add(jl_snmpv3_Priv,null);

//    	jpf_snmpv3_Priv = new JPasswordField();//USO DEL JPASSWORDFIELD
//    	jpf_snmpv3_Priv.setBounds(new Rectangle(246,226,160,20));//establece el xy del componente
    	jpf_snmpv3_Priv.setEchoChar('*');
    	jpf_snmpv3_Priv.setText(String.valueOf(clavePriv));
    	//jpf_snmpv3_Priv.setEchoChar((char)0);
//    	jp_snmpv3_Con.add(jpf_snmpv3_Priv,null);
   	
   	
//    	jl_snmpv3_VerUsr = new JLabel("Visualizar Datos Privados");//USO DEL JLABEL
//    	jl_snmpv3_VerUsr.setBounds(new Rectangle(76,106,160,20));//establece el xy del componente
//    	jp_snmpv3_Con.add(jl_snmpv3_VerUsr,null);

//	    jcb_snmpv3_VerUsr = new JComboBox();//USO DEL JCOMBOBOX
//	  	jcb_snmpv3_VerUsr.setBounds(new Rectangle(246,106,160,20));  
//		jcb_snmpv3_VerUsr.addItem("Si");
//	  	jcb_snmpv3_VerUsr.addItem("No");
//	  	jcb_snmpv3_VerUsr.setSelectedIndex(1);	  	 
//	  	jp_snmpv3_Con.add(jcb_snmpv3_VerUsr,null);
 
	  	jcb_snmpv3_VerUsr.addActionListener(new ActionListener(){    
	      public void actionPerformed(ActionEvent e) {	          
	        if ((jcb_snmpv3_VerUsr.getSelectedItem())==opcionSi){jpf_snmpv3_User.setEchoChar((char)0);jpf_snmpv3_Aut.setEchoChar((char)0);jpf_snmpv3_Priv.setEchoChar((char)0);}; 
	        if ((jcb_snmpv3_VerUsr.getSelectedItem())==opcionNo){jpf_snmpv3_User.setEchoChar('*');jpf_snmpv3_Aut.setEchoChar('*');jpf_snmpv3_Priv.setEchoChar('*');}; 	
	      }
	    });

//    	jl_snmpv3_metAut = new JLabel("Algoritmo de Autenticacin");//USO DEL JLABEL
//    	jl_snmpv3_metAut.setBounds(new Rectangle(76,266,160,20));//establece el xy del componente
//    	jp_snmpv3_Con.add(jl_snmpv3_metAut,null);

//	    jcb_snmpv3_metAut = new JComboBox();//USO DEL JCOMBOBOX
//	  	jcb_snmpv3_metAut.setBounds(new Rectangle(246,266,160,20));  
//		jcb_snmpv3_metAut.addItem("MD5");
//	  	jcb_snmpv3_metAut.addItem("SHA");
//	  	jcb_snmpv3_metAut.setSelectedIndex(1);	  	 
//	  	jp_snmpv3_Con.add(jcb_snmpv3_metAut,null);
//    
//    	jl_snmpv3_metPriv = new JLabel("Algoritmo de Encriptacin");//USO DEL JLABEL
//    	jl_snmpv3_metPriv.setBounds(new Rectangle(76,306,160,20));//establece el xy del componente
//    	jp_snmpv3_Con.add(jl_snmpv3_metPriv,null);

//	    jcb_snmpv3_metPriv = new JComboBox();//USO DEL JCOMBOBOX
//	  	jcb_snmpv3_metPriv.setBounds(new Rectangle(246,306,160,20));  
//		jcb_snmpv3_metPriv.addItem("DES");
//	  	jcb_snmpv3_metPriv.addItem("AES128");
//	  	jcb_snmpv3_metPriv.addItem("AES192");	  	
//	  	jcb_snmpv3_metPriv.addItem("AES256");	  	
//	  	jcb_snmpv3_metPriv.setSelectedIndex(1);	  	 
//	  	jp_snmpv3_Con.add(jcb_snmpv3_metPriv,null);     
//  
//    	jl_snmpv3_inten = new JLabel("Nro. de Intentos");//USO DEL JLABEL
//    	jl_snmpv3_inten.setBounds(new Rectangle(76,346,160,20));//establece el xy del componente
//    	jp_snmpv3_Con.add(jl_snmpv3_inten,null);
    
//    	jtf_snmv3_inten = new JTextField();//USO DEL JTEXTFIELD
//    	jtf_snmv3_inten.setBounds(new Rectangle(246,346,160,20));//establece el xy del componente
//    	jtf_snmv3_inten.setText(String.valueOf(inten));
//    	jp_snmpv3_Con.add(jtf_snmv3_inten,null);

//    	jl_snmpv3_timeOut = new JLabel("Tiempo de Espera (ms)");//USO DEL JLABEL
//    	jl_snmpv3_timeOut.setBounds(new Rectangle(76,386,160,20));//establece el xy del componente
//    	jp_snmpv3_Con.add(jl_snmpv3_timeOut,null);
     
//    	jtt_snmpv3_timeOut = new JTextField();//USO DEL JTEXTFIELD
//    	jtt_snmpv3_timeOut.setBounds(new Rectangle(246,386,160,20));//establece el xy del componente
//    	jtt_snmpv3_timeOut.setText(String.valueOf(timeOut));
//    	jp_snmpv3_Con.add(jtt_snmpv3_timeOut,null);
 
//    	jb_snmpv3_aplicarPara = new JButton("Aplicar Cambios");
//	    jb_snmpv3_aplicarPara.setBounds(new Rectangle(141,431,200,20));
//	    jb_snmpv3_aplicarPara.setToolTipText("Presione para establecer los nuevos parmetros.");
//	    jp_snmpv3_Con.add(jb_snmpv3_aplicarPara,null);

	
//		jb_snmpv3_aplicarPara.addActionListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//          	String men_err = "";
//          	boolean men_err_l = false;
//
//          		if (esVacio(jtf_snmpv3_IP.getText())){          			
//          		  men_err = men_err.concat(configParamError01);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          	  	if (!(ipValida(jtf_snmpv3_IP.getText()))){
//          	  	  men_err = men_err.concat(configParamError02);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          	  	
//          		if (!(esNumero(jtf_snmpv3_pto.getText()))){          			
//          		  men_err = men_err.concat(configParamError03);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          	  	if (esVacio(jpf_snmpv3_User.getText())){          			
//          		  men_err = men_err.concat(configParamError10);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          	  	//jpf_snmpv3_Aut
//          	  	if (esVacio(jpf_snmpv3_Aut.getText())){          			
//          		  men_err = men_err.concat(configParamError11);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          	  	if ((jpf_snmpv3_Aut.getText()).length()<8){          			
//          		  men_err = men_err.concat(configParamError12);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          	  	//jpf_snmpv3_Priv
//          	  	if (esVacio(jpf_snmpv3_Priv.getText())){          			
//          		  men_err = men_err.concat(configParamError13);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	
//          	  	if ((jpf_snmpv3_Priv.getText()).length()<8){          			
//          		  men_err = men_err.concat(configParamError14);	 	
//          	  	  men_err_l =true;
//          	  	}
//          	  	          	  	
//          		if (!(esNumero(jtf_snmv3_inten.getText()))){          			
//          		  men_err = men_err.concat(configParamError06);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          		if ((esNumero(jtf_snmv3_inten.getText()))&&(Integer.parseInt(jtf_snmv3_inten.getText())<=0)){          			
//          		  men_err = men_err.concat(configParamError07);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          		if (!(esNumero(jtt_snmpv3_timeOut.getText()))){          			
//          		  men_err = men_err.concat(configParamError08);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          		if ((esNumero(jtt_snmpv3_timeOut.getText()))&&(Integer.parseInt(jtt_snmpv3_timeOut.getText())<=0)){          			
//          		  men_err = men_err.concat(configParamError09);	 	
//          	  	  men_err_l =true;
//          	  	}
//
//          		if (men_err_l){          			
//				JOptionPane.showMessageDialog(AppGo.this,men_err,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	          	  	          	  
//		    	if (esVacio(jtf_snmpv3_IP.getText())){jtf_snmpv3_IP.setText(String.valueOf(IP));}
//		    	if (!(ipValida(jtf_snmpv3_IP.getText()))){jtf_snmpv3_IP.setText(String.valueOf(IP));}
//		    	if (!(esNumero(jtt_snmpv3_timeOut.getText()))){jtt_snmpv3_timeOut.setText(String.valueOf(timeOut));}
//		    	if ((esNumero(jtt_snmpv3_timeOut.getText()))&&(Integer.parseInt(jtt_snmpv3_timeOut.getText())<=0)){jtt_snmpv3_timeOut.setText(String.valueOf(timeOut));}
//		    	if (!(esNumero(jtf_snmv3_inten.getText()))){jtf_snmv3_inten.setText(String.valueOf(inten));}
//		    	if ((esNumero(jtf_snmv3_inten.getText()))&&(Integer.parseInt(jtf_snmv3_inten.getText())<=0)){jtf_snmv3_inten.setText(String.valueOf(inten));}
//				if (!(esNumero(jtf_snmpv3_pto.getText()))){jtf_snmpv3_pto.setText(String.valueOf(pto));}    
//				if (esVacio(jpf_snmpv3_User.getText())){jpf_snmpv3_User.setText(String.valueOf(user));}   
//				if (esVacio(jpf_snmpv3_Aut.getText())){jpf_snmpv3_Aut.setText(String.valueOf(claveAut));}
//				if ((jpf_snmpv3_Aut.getText()).length()<8){jpf_snmpv3_Aut.setText(String.valueOf(claveAut));}
//				if (esVacio(jpf_snmpv3_Priv.getText())){jpf_snmpv3_Priv.setText(String.valueOf(clavePriv));}
//				if ((jpf_snmpv3_Priv.getText()).length()<8){jpf_snmpv3_Priv.setText(String.valueOf(clavePriv));}
//
//				} else {       
//					   	  	          	  	
//          		IP =		 jtf_snmpv3_IP.getText();
//          		pto =	 Long.parseLong(jtf_snmpv3_pto.getText());
//          		user =	 jpf_snmpv3_User.getText();
//          		claveAut =	 jpf_snmpv3_Aut.getText();
//          		clavePriv =	 jpf_snmpv3_Priv.getText();
//          		if ((jcb_snmpv3_metAut.getSelectedItem())=="MD5"){metAut = AuthMD5.ID;}; 
//          		if ((jcb_snmpv3_metAut.getSelectedItem())=="SHA"){metAut = AuthSHA.ID;}; 
//          		if ((jcb_snmpv3_metPriv.getSelectedItem())=="DES"){metPriv = PrivDES.ID;}; 
//          		if ((jcb_snmpv3_metPriv.getSelectedItem())=="AES128"){metPriv = PrivAES128.ID;};           			
//          		if ((jcb_snmpv3_metPriv.getSelectedItem())=="AES192"){metPriv = PrivAES192.ID;}; 
//          		if ((jcb_snmpv3_metPriv.getSelectedItem())=="AES256"){metPriv = PrivAES256.ID;};        
//          		inten =	 Integer.parseInt(jtf_snmv3_inten.getText());
//          		timeOut = Integer.parseInt(jtt_snmpv3_timeOut.getText());
//				
//				JOptionPane.showMessageDialog(AppGo.this, configParamResult01,nameOfTheProgram,JOptionPane.INFORMATION_MESSAGE,(new ImageIcon("images/help.gif")));  	          	  	          	  
//					
////				//PARA LLENAR LOS PARAMETROS DE SNMPv1					
////				jtf_snmpv1_IP.setText(IP);
////          		jtf_snmpv1_pto.setText(String.valueOf(pto));          		
////          		jtf_snmv1_inten.setText(String.valueOf(inten));
////          		jtt_snmpv1_timeOut.setText(String.valueOf(timeOut));
////          		
//// 
////          		//PARA LLENAR LOS PARAMETROS DE SNMPv2c	
////				jtf_snmpv2c_IP.setText(IP);
////          		jtf_snmpv2c_pto.setText(String.valueOf(pto));
////          		jtf_snmv2c_inten.setText(String.valueOf(inten));
////          		jtt_snmpv2c_timeOut.setText(String.valueOf(timeOut));	
////				  		
//					
//				} 
//          		
//         	}
//       	});
	    //----------------------------------Fin de Pantalla de Conexin----------------------------


	    //----------------------------------Pantalla de Get----------------------------------------  	
// 	    jp_snmpv3_Get = new JPanel();
//	    //jp_snmpv3_Get.setBackground(Color.blue);
//		//jp_snmpv3_Get.setBorder(BorderFactory.createTitledBorder("Comando Get de SNMPv1"));
//		jp_snmpv3_Get.setBounds(new Rectangle(0,30,483,483));
//		jp_snmpv3_Get.setBorder(BorderFactory.createTitledBorder("Comando Get"));
//		jp_snmpv3_Get.setLayout(null);
//		jp_snmpv3_Get.setVisible(false);
//		snmpv3.add(jp_snmpv3_Get,null);    	    	
//
//    	jsp_snmpv3_getDescrip = new JScrollPane();
//    	jsp_snmpv3_getDescrip.setBounds(new Rectangle(10,20,465,270));
//    	jsp_snmpv3_getDescrip.setWheelScrollingEnabled(true);
//    	jp_snmpv3_Get.add(jsp_snmpv3_getDescrip,null);
//	
//    	jta_snmpv3_getDescrip = new JTextArea();
//    	jta_snmpv3_getDescrip.setText("");
//    	jta_snmpv3_getDescrip.setEditable(false);
//    	jsp_snmpv3_getDescrip.getViewport().add(jta_snmpv3_getDescrip,null);
//     	
//    	jl_snmpv3_getEtiGet = new JLabel("OID");
//	    jl_snmpv3_getEtiGet.setBounds(new Rectangle(10,300,20,20));
//	    jp_snmpv3_Get.add(jl_snmpv3_getEtiGet,null);
//	    
//	    jtf_snmpv3_getGet = new JTextField();
//	    jtf_snmpv3_getGet.setBounds(new Rectangle(40,300,244,20));   
//	    jtf_snmpv3_getGet.setEditable(true);
//	    jp_snmpv3_Get.add(jtf_snmpv3_getGet,null);
//
//       	jb_snmpv3_getAdd = new JButton("Aadir");
//	    jb_snmpv3_getAdd.setBounds(new Rectangle(295,300,79,20));  	    
//	    jb_snmpv3_getAdd.setToolTipText("Presione para agregar el OID.");
//	    jp_snmpv3_Get.add(jb_snmpv3_getAdd,null);
//	    
//    	jb_snmpv3_getUndo = new JButton("Deshacer");
//	    jb_snmpv3_getUndo.setBounds(new Rectangle(384,300,90,20));  //9999
//	    jb_snmpv3_getUndo.setToolTipText("Presione para eliminar el ltimo OID de los objetos.");
//	    jp_snmpv3_Get.add(jb_snmpv3_getUndo,null);
//	    
//    	jl_snmpv3_getObjs = new JLabel("Objetos");
//	    jl_snmpv3_getObjs.setBounds(new Rectangle(10,330,60,20));
//	    jp_snmpv3_Get.add(jl_snmpv3_getObjs,null);
//	    
//	    jtf_snmpv3_getObjs = new JTextField();
//	    jtf_snmpv3_getObjs.setBounds(new Rectangle(60,330,140,20));   
//	    jtf_snmpv3_getObjs.setEditable(false);	    	    
//	    jp_snmpv3_Get.add(jtf_snmpv3_getObjs,null);
//
//    	jl_snmpv3_getModSeg = new JLabel("Seguridad");
//	    jl_snmpv3_getModSeg.setBounds(new Rectangle(210,330,60,20));
//	    jp_snmpv3_Get.add(jl_snmpv3_getModSeg,null);
//	    
//	    jcb_snmpv3_getModSeg = new JComboBox();//USO DEL JCOMBOBOX
//	  	jcb_snmpv3_getModSeg.setBounds(new Rectangle(280,330,125,20));  
//		jcb_snmpv3_getModSeg.addItem("AUTH_NOPRIV");
//		jcb_snmpv3_getModSeg.addItem("AUTH_PRIV");
//		jcb_snmpv3_getModSeg.addItem("NOAUTH_NOPRIV");
//		//jcb_snmpv3_getModSeg.addItem("NOAUTH_PRIV");	  	
//	  	jcb_snmpv3_getModSeg.setSelectedIndex(1);	  	 
//	  	jp_snmpv3_Get.add(jcb_snmpv3_getModSeg,null);
//
//    	jb_snmpv3_getGet = new JButton("Get");
//	    jb_snmpv3_getGet.setBounds(new Rectangle(415,330,59,20));  
//	    jb_snmpv3_getGet.setToolTipText("Presione para obtener el valor.");
//	    jp_snmpv3_Get.add(jb_snmpv3_getGet,null);
//	    
//	    jsp_snmpv3_getResp = new JScrollPane();
//    	jsp_snmpv3_getResp.setBounds(new Rectangle(10,360,465,111));  
//    	jsp_snmpv3_getResp.setWheelScrollingEnabled(true);
//    	jp_snmpv3_Get.add(jsp_snmpv3_getResp,null);
//
//    	jta_snmpv3_getResp = new JTextArea();
//    	jta_snmpv3_getResp.setText("");
//    	jta_snmpv3_getResp.setEditable(false);
//    	jsp_snmpv3_getResp.getViewport().add(jta_snmpv3_getResp,null);
// 	    
 	    compuestoGetSNMPv3Temp = new Vector();
 	    
 	    //Para el aadir
 	    jb_snmpv3_getAdd.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        
        	  //aqui lo que hace
          	  if ((jtf_snmpv3_getGet.getText()).equals("")){
          	    JOptionPane.showMessageDialog(AppGo.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_getGet.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
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
	          	  	    JOptionPane.showMessageDialog(AppGo.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
        	  	JOptionPane.showMessageDialog(AppGo.this,erroresGenerales03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
                  checkAndApplyV3Creds();

                    
          	  if ((jtf_snmpv3_getGet.getText()).equals("")){
          	    JOptionPane.showMessageDialog(AppGo.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_getGet.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
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
	          	  	    JOptionPane.showMessageDialog(AppGo.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
          	    JOptionPane.showMessageDialog(AppGo.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_GetNextGetNext.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
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
	          	  	    JOptionPane.showMessageDialog(AppGo.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
        	  	JOptionPane.showMessageDialog(AppGo.this,erroresGenerales03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
          	    JOptionPane.showMessageDialog(AppGo.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_GetNextGetNext.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
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
	          	  	    JOptionPane.showMessageDialog(AppGo.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
          	    JOptionPane.showMessageDialog(AppGo.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	    jta_snmpv3_GetBulkResp.setText("");
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_GetBulkGetBulk.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	  jta_snmpv3_GetBulkResp.setText("");
          	  	}else{ 
          	  		if (!(esNumero(jtf_snmpv3_nonRepe.getText()))){          			
          		  	  JOptionPane.showMessageDialog(AppGo.this,erroresGenerales26,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
          	  		  jta_snmpv3_GetBulkResp.setText("");
          	  		}else{
          	  			if ((!(esNumero(jtf_snmpv3_maxRep.getText())))||(Integer.parseInt(jtf_snmpv3_maxRep.getText())<=0)){          			
          		          JOptionPane.showMessageDialog(AppGo.this,erroresGenerales27,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
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
		          	  	    	JOptionPane.showMessageDialog(AppGo.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
        	  	JOptionPane.showMessageDialog(AppGo.this,erroresGenerales03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
          	    JOptionPane.showMessageDialog(AppGo.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	    jta_snmpv3_GetBulkResp.setText("");
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_GetBulkGetBulk.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	  jta_snmpv3_GetBulkResp.setText("");
          	  	}else{ 
          	  		if (!(esNumero(jtf_snmpv3_nonRepe.getText()))){          			
          		  	  JOptionPane.showMessageDialog(AppGo.this,erroresGenerales26,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
          	  		  jta_snmpv3_GetBulkResp.setText("");
          	  		}else{
          	  			if ((!(esNumero(jtf_snmpv3_maxRep.getText())))||(Integer.parseInt(jtf_snmpv3_maxRep.getText())<=0)){          			
          		          JOptionPane.showMessageDialog(AppGo.this,erroresGenerales27,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
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
		          	  	    	JOptionPane.showMessageDialog(AppGo.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
          	    JOptionPane.showMessageDialog(AppGo.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_getTablegetTable.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
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
		          	  	  	//JOptionPane.showMessageDialog(AppGo.this,"No se encontraron datos, las causas pueden ser:\n- El OID seleccionado no es una tabla.\n- El agente no tiene datos para la tabla seleccionada.\n- Seleccion el Entry en vez de la tabla como tal.",nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));	
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
	          	  	    JOptionPane.showMessageDialog(AppGo.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
          	    JOptionPane.showMessageDialog(AppGo.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	    jta_snmpv3_WalkResp.setText("");
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_WalkOID.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	  jta_snmpv3_WalkResp.setText("");
          	  	}else{ 
          	  		if (((jcb_snmpv3_WalkEtiLimitePregunta.getSelectedItem())==opcionSi)&&(jtf_snmpv3_WalkEtiLimite.getText().equals(""))){
          	  		  JOptionPane.showMessageDialog(AppGo.this,erroresGenerales14,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
          	  		  jta_snmpv3_WalkResp.setText("");
          	  		}else{
          	  			if (((jcb_snmpv3_WalkEtiLimitePregunta.getSelectedItem())==opcionSi)&&(!(esNumero(jtf_snmpv3_WalkEtiLimite.getText())))){
          	  			  JOptionPane.showMessageDialog(AppGo.this,erroresGenerales15,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
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
		          	  	    	JOptionPane.showMessageDialog(AppGo.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
          	    JOptionPane.showMessageDialog(AppGo.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_SetSet.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	}else{ 
          	  		
          	  		if ((jtf_snmpv3_SetSetValor.getText()).equals("")){
          	  		  JOptionPane.showMessageDialog(AppGo.this,erroresGenerales22,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
          	  		}else{
          	  			if ((jcb_snmpv3_SetTipo.getSelectedItem()).equals("---------------------------")){
          	  			  JOptionPane.showMessageDialog(AppGo.this,erroresGenerales23,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
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
				          	  		  JOptionPane.showMessageDialog(AppGo.this,erroresGenerales24,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));				
				          	  		}
		          	  			}else{
		          	  				JOptionPane.showMessageDialog(AppGo.this,erroresGenerales25,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));			
		          	  			}
			          	  	  }catch(NumberFormatException nfe){
		          	  	    	JOptionPane.showMessageDialog(AppGo.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
        	  	JOptionPane.showMessageDialog(AppGo.this,erroresGenerales03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
          	    JOptionPane.showMessageDialog(AppGo.this,erroresGenerales01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_SetSet.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	}else{ 
          	  		
          	  		if ((jtf_snmpv3_SetSetValor.getText()).equals("")){
          	  		  JOptionPane.showMessageDialog(AppGo.this,erroresGenerales22,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
          	  		}else{
          	  			if ((jcb_snmpv3_SetTipo.getSelectedItem()).equals("---------------------------")){
          	  			  JOptionPane.showMessageDialog(AppGo.this,erroresGenerales23,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
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
				          	  		  JOptionPane.showMessageDialog(AppGo.this,erroresGenerales24,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));				
				          	  		}
		          	  			}else{
		          	  				JOptionPane.showMessageDialog(AppGo.this,erroresGenerales25,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));			
		          	  			}
			          	  	  }catch(NumberFormatException nfe){
		          	  	    	JOptionPane.showMessageDialog(AppGo.this,erroresGenerales02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
					JOptionPane.showMessageDialog(AppGo.this,men_err,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	          	  	          	  
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
					        JOptionPane.showMessageDialog(AppGo.this,erroresGenerales43,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));				
					      }
			          	}else{
			          	   snmpv3_tipoDatoTrapSeleccionado = false;
			          	   JOptionPane.showMessageDialog(AppGo.this,erroresGenerales44,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));			
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
	          	  	    JOptionPane.showMessageDialog(AppGo.this, erroresGenerales48 ,nameOfTheProgram,JOptionPane.INFORMATION_MESSAGE,(new ImageIcon("images/help.gif")));	 	          	  	          	  	
	          	  	  }else{
	          	  	    sndtrap.trapv3(jtf_snmpv3_TrapSndHostIP.getText(), jtf_snmpv3_PtoComTxt.getText(), jpf_snmpv3_TrpUsr.getText(),jpf_snmpv3_TrpAut.getText(), jpf_snmpv3_TrpPriv.getText(), nivelSeguridad,Integer.parseInt(jtf_snmpv3_IntTxt.getText()), Integer.parseInt(jtf_snmpv3_TmoutTxt.getText()), tipoTrapsndSpc,tipoTrapsnd);
	          	  	    JOptionPane.showMessageDialog(AppGo.this, erroresGenerales35 ,nameOfTheProgram,JOptionPane.INFORMATION_MESSAGE,(new ImageIcon("images/help.gif")));	 	          	  	          	  	
	          	  	  }
	          	  	  
					  //System.out.println("Trap enviado.");
					}
				}
         	}
       	});      
		//----------------------------------Fin de Pantalla de Traps-------------------------------










  	//----------------------------Fin del Manager SNMPv3--------------------------------------
  	
        
        
          	  //System.out.println("Cambiando idioma a ingles");
  	  //Para la interfaz general
  	  //jm_archive.setText("File");//aade el menu
  	  //jmi_outbound.setText("Exit");//crea un jmenuitem
  	  //jm_help.setText("Help");
  	  //jmi_helpHelp.setText("User's Guide");
  	  //jmi_about.setText("About");
  	  //jb_mibtree.setText("Import MIBs");
  	  jb_mibtree.setToolTipText("Press to import MIBs.");
  	  aboutDeTitulo = nameOfTheProgram+" - About";
  	  String encabezadoAcercaDe = "Super Neat Master Puppeteer\n"+
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

      String etiquetaIdioma="";
      for (int yy=0;yy<165; yy++){
		  etiquetaIdioma=etiquetaIdioma.concat(" ");
		}
		etiquetaIdioma=etiquetaIdioma.concat(" Idioma / Language   ");
//      jl_idioma.setText("           ".concat(etiquetaIdioma));
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

//  	    //--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
//  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//		  		  		
  	  //Fin de SNMPv2c-----------------------------------------------------------------------------------------------------
  	  
  	  //Para SNMPv3-------------------------------------------------------------------------------------------------------
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS--PANTALLA DE PARAMETROS----------------
//  	    jp_snmpv3_Con.setBorder(BorderFactory.createTitledBorder("Connection Options"));
//  	    jl_snmpv3_IP.setText("Agent IP Address");
//  	    jl_snmpv3_pto.setText("Port Number");
//  	    jl_snmpv3_User.setText("Username");
//  	    jl_snmpv3_Aut.setText("Authentication Password");
//  	    jl_snmpv3_Priv.setText("Privacy Password");
//  	    jl_snmpv3_VerUsr.setText("Show Private Data");
//  	    jl_snmpv3_metAut.setText("Authentication Protocol");
//  	    jl_snmpv3_metPriv.setText("Privacy Protocol");
//  	    jl_snmpv3_inten.setText("Retries");
//  	    jl_snmpv3_timeOut.setText("TimeOut (ms)");
//  	    jb_snmpv3_aplicarPara.setText("Apply Changes");
//  	    jb_snmpv3_aplicarPara.setToolTipText("Press to set the new values.");

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
//  	    jp_snmpv3_Get.setBorder(BorderFactory.createTitledBorder("Get Command"));
//  	    jl_snmpv3_getEtiGet.setText("OID");
//  	    jb_snmpv3_getAdd.setText("Add");
//  	    jb_snmpv3_getAdd.setToolTipText("Press to add the OID.");
//  	    jl_snmpv3_getObjs.setText("Objects");
//  	    jb_snmpv3_getUndo.setText("Undo");
//  	    jb_snmpv3_getUndo.setToolTipText("Press to erase the last OID added.");
//  	    jb_snmpv3_getGet.setText("Get");
//  	    jb_snmpv3_getGet.setToolTipText("Press to get the value(s).");
//  	    jl_snmpv3_getModSeg.setText("Security");

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
  	    

  	    //--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS--PANTALLA DE LOS TRAPS
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
          	     	    
  	  //Fin de SNMPv3-----------------------------------------------------------------------------------------------------
  	  
        

    }
    
    
    
      //----------------Inicio SNMPv3------------------------------------------------------------------------------------------
		private JLabel jl_snmpv3_sel;
		private JComboBox jcb_snmpv3_sel;
		private JPanel jp_snmpv3_Con, jp_snmpv3_Get, jp_snmpv3_GetNext, jp_snmpv3_GetBulk, jp_snmpv3_Set, jp_snmpv3_Traps, jp_snmpv3_TrapsSend, jp_snmpv3_walk,jp_snmpv3_getTable;
		///////////////////Pantalla parametros v3\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		// GUI private JComboBox jcb_snmpv3_VerUsr,jcb_snmpv3_metAut,jcb_snmpv3_metPriv;
		// REMOVED private JLabel jl_snmpv3_IP,jl_snmpv3_pto,jl_snmpv3_User,jl_snmpv3_VerUsr,jl_snmpv3_inten,jl_snmpv3_timeOut,jl_snmpv3_Aut,jl_snmpv3_Priv,jl_snmpv3_metAut,jl_snmpv3_metPriv;    
		// GUI private JTextField jtf_snmpv3_IP,jtf_snmpv3_pto,jtf_snmv3_inten,jtt_snmpv3_timeOut;
		// GUI private JPasswordField jpf_snmpv3_User,jpf_snmpv3_Aut,jpf_snmpv3_Priv;  
		// REMOVED private JButton jb_snmpv3_aplicarPara;    
		////////////////////////GET
		//GUI private JScrollPane jsp_snmpv3_getDescrip;
		// GUI private JTextArea jta_snmpv3_getDescrip,jta_snmpv3_getResp;
		private JLabel jl_snmpv3_getEtiGet,jl_snmpv3_getModSeg,jl_snmpv3_getObjs;
		// GUI private JScrollPane jsp_snmpv3_getResp;
		// GUI private JTextField jtf_snmpv3_getGet,jtf_snmpv3_getObjs; 
		// GUI private JButton jb_snmpv3_getGet,jb_snmpv3_getAdd,jb_snmpv3_getUndo;
		// GUI private JComboBox jcb_snmpv3_getModSeg;		
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
		// GUI private JScrollPane jsp_snmpv3_SetDescrip, jsp_snmpv3_SetResp;
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
            
            
                	private String tipoDatoReconocido=null;
    	private boolean reconocido=false;
        
        
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
            //Para SNMPv1
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
//
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
        java.net.URL imgURL = AppGo.class.getResource(path);
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
  
  private void checkAndApplyV3Creds() {
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
				JOptionPane.showMessageDialog(AppGo.this,men_err,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	          	  	          	  
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
				
				//JOptionPane.showMessageDialog(AppGo.this, configParamResult01,nameOfTheProgram,JOptionPane.INFORMATION_MESSAGE,(new ImageIcon("images/help.gif")));  	          	  	          	  
                            }	
  }
  
       private ArrayList getSessionList() throws FileNotFoundException, IOException, URISyntaxException
    {
        String strSessionList;
        DefaultListModel listModel = new DefaultListModel();
        ArrayList arrSessionList = new ArrayList();
        //File pathWorkingDirectory = new File(System.getProperty("user.dir"));
        //--- Favorites?
//        if(jCheckBox1.isSelected()){
//            strSessionList = strSessionListFavorites;
//        }
//        else {
            strSessionList = strSessionListDefault;
//        }
        System.out.println("SessionList.csv directory: " + strSessionList);
        File archivo = new File(strSessionList);
        if(!archivo.exists())
        {  
            archivo.createNewFile();
            List<String> lines = Arrays.asList(
" ~~~~~~~~ ROUTERS ~~~~~~~~~",
"C1000-a01-asdf-090-Rm100,10.0.0.14",
"C1000-a01-asdf-A51-0710-Rm100,10.0.0.15",
"",
" ~~~~~~~ CORE NODES ~~~~~~~~",
"C6500-a01-asdf-0700-Rm100,10.0.0.12",
"C6500-a01-asdf-0700-Rm100,10.0.0.13",
"",
" ~~~~ DISTRIBUTION NODES ~~~~",
"C4500-a01-asdf-0020-1FL-Rm100,10.0.0.8",
"C4500-a01-asdf-0020-2FL-Rm200_C4500,10.0.0.9",
"",
" ~~~~~~ ACCESS NODES ~~~~~~ ",
"C9300-a01-asdf-0001-1FL-Rm100,10.0.0.5",
"C3850-a01-asdf-0001-2FL-Rm200,10.0.0.6",
"C3750-a01-asdf-0051-BSMT-Rm2,10.0.0.7",
"",
" ~~~~~~~~ SERVERS ~~~~~~~~~",
"Cisco ISE,10.0.1.2",
"Cisco ACS,10.0.1.3",
"InfoBlox,10.0.1.4");
            Path file = Paths.get(strSessionList);
            Files.write(file, lines, Charset.forName("UTF-8"));
        }
        try (FileReader fr = new FileReader(archivo)) {
            BufferedReader buffIn;
            buffIn = new BufferedReader(fr);
               
            String line;
            while ((line = buffIn.readLine()) != null) {
                arrSessionList.add(line);
                listModel.addElement(line);
                //System.out.println(line);
            }
        jListSessions.setModel(listModel);
        }
        catch (IOException e) {
            System.out.println("SessionList.csv no good"); 
            JOptionPane.showMessageDialog(null, "SessionList.csv Error!"); 
        }
        return arrSessionList;
    }
      private void searchFilter(String searchTerm) throws IOException, FileNotFoundException, URISyntaxException
    {
        DefaultListModel filteredItems=new DefaultListModel();
        ArrayList sessions=getSessionList();

        sessions.stream().forEach((session) -> {
            String sessionName=session.toString().toLowerCase();
            if (sessionName.contains(searchTerm.toLowerCase())) {
                filteredItems.addElement(session);
            }
        });
        defaultListModelFilteredItems=filteredItems;
        jListSessions.setModel(defaultListModelFilteredItems);

    }
  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTopBar = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListSessions = new javax.swing.JList<>();
        jTextFieldFilter = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jtf_snmpv3_IP = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtf_snmv3_inten = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jcb_snmpv3_metAut = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jcb_snmpv3_metPriv = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jtf_snmpv3_pto = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jtt_snmpv3_timeOut = new javax.swing.JTextField();
        jpf_snmpv3_Aut = new javax.swing.JPasswordField();
        jpf_snmpv3_Priv = new javax.swing.JPasswordField();
        jLabel13 = new javax.swing.JLabel();
        jcb_snmpv3_VerUsr = new javax.swing.JComboBox<>();
        jpf_snmpv3_User = new javax.swing.JPasswordField();
        jPanelRight = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jsp_snmpv3_getDescrip = new javax.swing.JScrollPane();
        jta_snmpv3_getDescrip = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jtf_snmpv3_getGet = new javax.swing.JTextField();
        jb_snmpv3_getAdd = new javax.swing.JButton();
        jb_snmpv3_getUndo = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jb_snmpv3_getGet = new javax.swing.JButton();
        jtf_snmpv3_getObjs = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jcb_snmpv3_getModSeg = new javax.swing.JComboBox<>();
        jsp_snmpv3_getResp = new javax.swing.JScrollPane();
        jta_snmpv3_getResp = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jsp_snmpv3_SetDescrip = new javax.swing.JScrollPane();
        jsp_snmpv3_SetResp = new javax.swing.JScrollPane();
        jPanel7 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jp_mibtree = new javax.swing.JPanel();
        jsp_mibtree = new javax.swing.JScrollPane();
        jb_mibtree = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jTextField4 = new javax.swing.JTextField();
        jmenubar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        jmi_outbound = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        jmi_helpHelp = new javax.swing.JMenuItem();
        jmi_about = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("S.N.M.P.");

        jListSessions.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListSessions.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListSessionsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListSessions);

        jTextFieldFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldFilterKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldFilter)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jTextFieldFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1))
        );

        jtf_snmpv3_IP.setText("192.168.0.23");
        jtf_snmpv3_IP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtf_snmpv3_IPKeyReleased(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Target:");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Retries:");

        jtf_snmv3_inten.setText("3");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Username:");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Auth Password:");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Priv Password:");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Auth:");

        jcb_snmpv3_metAut.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MD5", "SHA" }));
        jcb_snmpv3_metAut.setSelectedIndex(1);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Priv:");

        jcb_snmpv3_metPriv.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DES", "AES128", "AES192", "AES256" }));
        jcb_snmpv3_metPriv.setSelectedIndex(1);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Port:");

        jtf_snmpv3_pto.setText("161");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Timeout:");

        jtt_snmpv3_timeOut.setText("1500");

        jpf_snmpv3_Aut.setText("snmppassword");

        jpf_snmpv3_Priv.setText("snmppassword");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Show:");
        jLabel13.setToolTipText("Show Auth/Priv in plain text.");

        jcb_snmpv3_VerUsr.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No", "Yes" }));
        jcb_snmpv3_VerUsr.setToolTipText("Show Auth/Priv in plain text.");

        jpf_snmpv3_User.setText("snmpuser");
        jpf_snmpv3_User.setToolTipText("");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jtf_snmv3_inten, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtt_snmpv3_timeOut, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
                    .addComponent(jtf_snmpv3_IP)
                    .addComponent(jpf_snmpv3_Aut)
                    .addComponent(jpf_snmpv3_Priv)
                    .addComponent(jpf_snmpv3_User))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jcb_snmpv3_metAut, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jcb_snmpv3_metPriv, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtf_snmpv3_pto)
                    .addComponent(jcb_snmpv3_VerUsr, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtf_snmpv3_IP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jtf_snmpv3_pto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jpf_snmpv3_User, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jcb_snmpv3_metAut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jpf_snmpv3_Aut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jcb_snmpv3_metPriv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpf_snmpv3_Priv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtf_snmv3_inten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jtt_snmpv3_timeOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jcb_snmpv3_VerUsr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelTopBarLayout = new javax.swing.GroupLayout(jPanelTopBar);
        jPanelTopBar.setLayout(jPanelTopBarLayout);
        jPanelTopBarLayout.setHorizontalGroup(
            jPanelTopBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopBarLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelTopBarLayout.setVerticalGroup(
            jPanelTopBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jta_snmpv3_getDescrip.setColumns(20);
        jta_snmpv3_getDescrip.setRows(5);
        jsp_snmpv3_getDescrip.setViewportView(jta_snmpv3_getDescrip);

        jLabel1.setText("OID:");

        jtf_snmpv3_getGet.setText("jTextField1");

        jb_snmpv3_getAdd.setText("Add");
        jb_snmpv3_getAdd.setToolTipText("Add the OID to the Objects list.");
        jb_snmpv3_getAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_snmpv3_getAddActionPerformed(evt);
            }
        });

        jb_snmpv3_getUndo.setText("Undo");
        jb_snmpv3_getUndo.setToolTipText("Remove the last OID added.");

        jLabel11.setText("Objects:");

        jb_snmpv3_getGet.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jb_snmpv3_getGet.setText("GET!");
        jb_snmpv3_getGet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jb_snmpv3_getGetMousePressed(evt);
            }
        });

        jtf_snmpv3_getObjs.setEditable(false);

        jLabel12.setText("Security:");

        jcb_snmpv3_getModSeg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AUTH_PRIV", "AUTH_NOPRIV", "NOAUTH_NOPRIV" }));

        jta_snmpv3_getResp.setColumns(20);
        jta_snmpv3_getResp.setRows(5);
        jsp_snmpv3_getResp.setViewportView(jta_snmpv3_getResp);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsp_snmpv3_getResp)
                    .addComponent(jsp_snmpv3_getDescrip)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtf_snmpv3_getGet, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jb_snmpv3_getAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jb_snmpv3_getUndo)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcb_snmpv3_getModSeg, 0, 138, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtf_snmpv3_getObjs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jb_snmpv3_getGet)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsp_snmpv3_getDescrip, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jb_snmpv3_getAdd)
                        .addComponent(jb_snmpv3_getUndo)
                        .addComponent(jLabel12)
                        .addComponent(jcb_snmpv3_getModSeg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jtf_snmpv3_getGet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jb_snmpv3_getGet)
                    .addComponent(jtf_snmpv3_getObjs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsp_snmpv3_getResp, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Get", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 648, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("GetNext", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 648, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("GetBulk", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 648, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("GetTable", jPanel4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 648, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Walk", jPanel5);

        jLabel14.setText("OID:");

        jLabel15.setText("Data Type:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--------------------------------", "INTEGER", "OCTET STRING", "OBJECT IDENTIFIER", "IpAddress", "Counter", "Counter64", "Gauge", "TimeTicks", "Opaque" }));

        jLabel16.setText("Value:");

        jLabel17.setText("Security:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AUTH_PRIV", "AUTH_NOPRIV", "NOAUTH_NOPRIV" }));

        jLabel18.setText("Objects:");

        jButton1.setText("Add");

        jButton2.setText("Undo");

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3.setText("SET!");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsp_snmpv3_SetResp)
                    .addComponent(jsp_snmpv3_SetDescrip)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, 0, 186, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsp_snmpv3_SetDescrip, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jsp_snmpv3_SetResp, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Set", jPanel6);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 648, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Traps", jPanel7);

        javax.swing.GroupLayout jPanelRightLayout = new javax.swing.GroupLayout(jPanelRight);
        jPanelRight.setLayout(jPanelRightLayout);
        jPanelRightLayout.setHorizontalGroup(
            jPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRightLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanelRightLayout.setVerticalGroup(
            jPanelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRightLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        jb_mibtree.setText("Import MIB");
        jb_mibtree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_mibtreeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jp_mibtreeLayout = new javax.swing.GroupLayout(jp_mibtree);
        jp_mibtree.setLayout(jp_mibtreeLayout);
        jp_mibtreeLayout.setHorizontalGroup(
            jp_mibtreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_mibtreeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jp_mibtreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsp_mibtree)
                    .addComponent(jb_mibtree, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
                .addContainerGap())
        );
        jp_mibtreeLayout.setVerticalGroup(
            jp_mibtreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_mibtreeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsp_mibtree, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jb_mibtree)
                .addContainerGap())
        );

        jTabbedPane2.addTab("MIB Tree", jp_mibtree);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(jList1);

        jTextField4.setText("not setup yet");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("MIB List", jPanel10);

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        openMenuItem.setEnabled(false);
        fileMenu.add(openMenuItem);

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        saveMenuItem.setEnabled(false);
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Save As ...");
        saveAsMenuItem.setDisplayedMnemonicIndex(5);
        saveAsMenuItem.setEnabled(false);
        fileMenu.add(saveAsMenuItem);

        jmi_outbound.setMnemonic('x');
        jmi_outbound.setText("Exit");
        jmi_outbound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_outboundActionPerformed(evt);
            }
        });
        fileMenu.add(jmi_outbound);

        jmenubar.add(fileMenu);

        editMenu.setMnemonic('e');
        editMenu.setText("Edit");

        cutMenuItem.setMnemonic('t');
        cutMenuItem.setText("Cut");
        cutMenuItem.setEnabled(false);
        editMenu.add(cutMenuItem);

        copyMenuItem.setMnemonic('y');
        copyMenuItem.setText("Copy");
        copyMenuItem.setEnabled(false);
        editMenu.add(copyMenuItem);

        pasteMenuItem.setMnemonic('p');
        pasteMenuItem.setText("Paste");
        pasteMenuItem.setEnabled(false);
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setMnemonic('d');
        deleteMenuItem.setText("Delete");
        deleteMenuItem.setEnabled(false);
        editMenu.add(deleteMenuItem);

        jmenubar.add(editMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        jmi_helpHelp.setMnemonic('h');
        jmi_helpHelp.setText("Help");
        jmi_helpHelp.setEnabled(false);
        helpMenu.add(jmi_helpHelp);

        jmi_about.setMnemonic('a');
        jmi_about.setText("About");
        jmi_about.setEnabled(false);
        helpMenu.add(jmi_about);

        jmenubar.add(helpMenu);

        setJMenuBar(jmenubar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelTopBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelRight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelTopBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelRight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmi_outboundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_outboundActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jmi_outboundActionPerformed

    private void jb_mibtreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_mibtreeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_mibtreeActionPerformed

    private void jb_snmpv3_getAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_snmpv3_getAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_snmpv3_getAddActionPerformed

    private void jtf_snmpv3_IPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtf_snmpv3_IPKeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jtf_snmpv3_IPKeyReleased

    private void jTextFieldFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFilterKeyReleased
        // TODO add your handling code here:
        try {
            searchFilter(jTextFieldFilter.getText());
        } catch (IOException ex) {
            Logger.getLogger(AppGo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(AppGo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextFieldFilterKeyReleased

    private void jListSessionsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListSessionsValueChanged
        // TODO add your handling code here:
        String strSelectedValue = jListSessions.getSelectedValue();
        if(strSelectedValue.contains(",")) {
            String[] arrSelectedValue = strSelectedValue.split(",");
            jtf_snmpv3_IP.setText(arrSelectedValue[1]);
        }
    }//GEN-LAST:event_jListSessionsValueChanged

    private void jb_snmpv3_getGetMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jb_snmpv3_getGetMousePressed
        // TODO add your handling code here:
                //--- Update output box
        jta_snmpv3_getResp.setText((jta_snmpv3_getResp.getText()).concat("Running: SNMPv3 Get...\n"));
    }//GEN-LAST:event_jb_snmpv3_getGetMousePressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        String pathUserProfile = System.getenv("USERPROFILE");
        File pathDesktop = new File(System.getProperty("user.home"), "Desktop");
        String pathApplicationFolder = pathDesktop + "\\SNMP\\";
        String strSessionListFavoritesFolder = pathUserProfile + "\\.SNMP\\";
        new File(pathApplicationFolder).mkdirs();
        new File(strSessionListFavoritesFolder).mkdirs();
        
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(AppGo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(AppGo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(AppGo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(AppGo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }


        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AppGo().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AppGo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(AppGo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(AppGo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(AppGo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(AppGo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jListSessions;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelRight;
    private javax.swing.JPanel jPanelTopBar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextFieldFilter;
    private javax.swing.JButton jb_mibtree;
    private javax.swing.JButton jb_snmpv3_getAdd;
    private javax.swing.JButton jb_snmpv3_getGet;
    private javax.swing.JButton jb_snmpv3_getUndo;
    private javax.swing.JComboBox<String> jcb_snmpv3_VerUsr;
    private javax.swing.JComboBox<String> jcb_snmpv3_getModSeg;
    private javax.swing.JComboBox<String> jcb_snmpv3_metAut;
    private javax.swing.JComboBox<String> jcb_snmpv3_metPriv;
    private javax.swing.JMenuBar jmenubar;
    private javax.swing.JMenuItem jmi_about;
    private javax.swing.JMenuItem jmi_helpHelp;
    private javax.swing.JMenuItem jmi_outbound;
    private javax.swing.JPanel jp_mibtree;
    private javax.swing.JPasswordField jpf_snmpv3_Aut;
    private javax.swing.JPasswordField jpf_snmpv3_Priv;
    private javax.swing.JPasswordField jpf_snmpv3_User;
    private javax.swing.JScrollPane jsp_mibtree;
    private javax.swing.JScrollPane jsp_snmpv3_SetDescrip;
    private javax.swing.JScrollPane jsp_snmpv3_SetResp;
    private javax.swing.JScrollPane jsp_snmpv3_getDescrip;
    private javax.swing.JScrollPane jsp_snmpv3_getResp;
    private javax.swing.JTextArea jta_snmpv3_getDescrip;
    private javax.swing.JTextArea jta_snmpv3_getResp;
    private javax.swing.JTextField jtf_snmpv3_IP;
    private javax.swing.JTextField jtf_snmpv3_getGet;
    private javax.swing.JTextField jtf_snmpv3_getObjs;
    private javax.swing.JTextField jtf_snmpv3_pto;
    private javax.swing.JTextField jtf_snmv3_inten;
    private javax.swing.JTextField jtt_snmpv3_timeOut;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    // End of variables declaration//GEN-END:variables

}
