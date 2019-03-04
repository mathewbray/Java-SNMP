
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
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
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
import net.percederberg.mibble.MibLoader;
import net.percederberg.mibble.MibLoaderException;
import net.percederberg.mibble.MibType;
import net.percederberg.mibble.MibTypeTag;
import net.percederberg.mibble.MibbleBrowser;
import net.percederberg.mibble.browser.MibTree;
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
String pathUserProfile = System.getenv("USERPROFILE");
File pathDesktop = new File(System.getProperty("user.home"), "Desktop");
String pathApplicationFolder = pathDesktop + "\\SNMP\\";
String strPersonalFolder = pathUserProfile + "\\.SNMP\\";
String strSessionListDefault = pathDesktop + "\\SNMP\\SessionList.csv";
DefaultListModel defaultListModelFilteredItems = new DefaultListModel();

    
private String nameOfTheProgram = "Super Neat Master Puppeteer";

private String ConfigureParameters = "Configure Parameters";
private String CommandGet = "Command Get";
private String CommandGetNext = "Command GetNext";
private String CommandGetBulk = "Command GetBulk";
private String CommandGetTable = "Command GetTable";
private String CommandWalk = "Command Walk";
private String CommandSet = "Command Set";

private String IP = "192.168.0.24";
private long pto = 161;

private int inten = 3;
private int timeOut = 1500;
private String OID = "";
private int NonRepeaters = 0;
private int MaxRepetitions = 10;
private String user = "snmpuser";
private String claveAut = "snmppassword";
private String clavePriv = "snmppassword"; 

private OID metAut=AuthMD5.ID;
private OID metPriv=PrivDES.ID;

private String optionYES = "Yes";
private String optionNO = "No";  
private String configParamError01 = "Must enter the SNMP agent IP address.\n";
private String configParamError02 = "Must enter a valid IP address.\n";	
private String configParamError03 = "Must enter the port number. It has to be a numeric value.\n";	

private String configParamError06 = "Must enter the retries. It has to be a numeric value.\n";
private String configParamError07 = "Must enter the retries. It has to be a numeric value greater than zero.\n";	 	
private String configParamError08 = "Must enter the timeout. It has to be a numeric value.\n";	 	
private String configParamError09 = "Must enter the timeout. It has to be a numeric value greater than zero.\n";	 	


private String configParamError10 = "Must enter the username.\n";	
private String configParamError11 = "Must enter the authentication password.\n";
private String configParamError12 = "The authentication password must be at least 8 characters long.\n";
private String configParamError13 = "Must enter the privacy password.\n";
private String configParamError14 = "The privacy password must be at least 8 characters long.\n";
private String errorGeneral01 = "Must select or write an OID.";
private String errorGeneral02 = "Must select or write a valid OID.";
private String errorGeneral03 = "There is no more objects to erase.";
private String errorGeneral04 = "\n Request timed out.\n";
private String errorGeneral29 = " Error: The agent don't has the specified username.\n";
private String errorGeneral30 = " Error: Wrong password and/or authentication protocol.\n";
private String errorGeneral31 = " Error: Wrong password and/or privacy protocol.\n";

private String errorGeneral26 = "Must enter the NonRepeaters. It has to be a numeric value.";
private String errorGeneral27 = "Must enter the MaxRepetitions. It has to be a numeric value greater than zero.";
private String errorGeneral28 = "\nGetBulk: \n";
private String errorGeneral05 = "\nError: Request timed out.\n";
private String errorGeneral06 = "There is no data for the specified OID, the causes could be:\n- The OID selected isn't a table.\n- The agent don't has data for the selected table.\n- Entry selected instead of the table.";
private String errorGeneral07 = "Error: The agent don't has data for the specified OID.\n";
private String errorGeneral08 = "Error: The agent don't has the specified username.\n";
private String errorGeneral09 = "Error: Wrong password and/or authentication protocol.\n";
private String errorGeneral10 = "Error: Wrong password and/or privacy protocol.\n";
private String errorGeneral11 = "Error: Request timed out.\n";
private String errorGeneral12 = "Error: The specified OID is greater than the greater OID of the selected MIB.\n";
private String errorGeneral13 = "The variable given isn't the successor of the required:\n";
private String errorGeneral21 = "No Limit";
private String errorGeneral14 = "Must enter the quantity of variables retrieved.";
private String errorGeneral15 = "Must enter the quantity of variables retrieved. It has to be a numeric value.";
private String errorGeneral16 = "\nRunning Walk, wait a moment...\n\n";
private String errorGeneral17 = "Total requests sent:    ";
private String errorGeneral18 = "Total objects received: ";
private String errorGeneral19 = "Total walk time:        ";
private String errorGeneral20 = " milliseconds";
private String errorGeneral22 = "Must enter the new value.";
private String errorGeneral23 = "Must select the OID data type.";
private String errorGeneral24 = "Wrong data type. The operation has been cancelled.";
private String errorGeneral25 = "Unknown data type. The operation has been cancelled.";
               
            
    /**
     * Creates new form AppGo
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.net.URISyntaxException
     * @throws java.io.FileNotFoundException
     */
    public AppGo() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, FileNotFoundException, URISyntaxException {
        new File(pathApplicationFolder).mkdirs();
        new File(strPersonalFolder).mkdirs();
        initComponents();
        unzipfiles();
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
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

  	
  	//-------------------------------MIB TREE----------------------------------------

		
        //To initialize the fix of mibs
        loadedMibs = new ArrayList();
        loadedMibsToSearchForNames = new ArrayList();
        //To paint the mib browser
        mibTree = MibTreeBuilder.getInstance().getTree();
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        //renderer.setClosedIcon(createImageIcon("images/gray-small.png"));
        //renderer.setOpenIcon(createImageIcon("images/green-small.png"));
        mibTree.setCellRenderer(renderer);

        mibTree.addTreeSelectionListener(new TreeSelectionListener() {
          @Override
          public void valueChanged(TreeSelectionEvent e) {
            updateTreeSelection();
          }
        });
        jsp_mibtree.setViewportView(mibTree);

	    
        //To load the mib
            try {loadMib();} catch (Exception e){}
        //End of loading mib

	     
	//Listen for Import button     
//    	jb_mibtree.addActionListener(new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {loadNewMib();} catch (Exception e1){}
//            }
//       	});
  	//-------------------------------FIN DEL PANEL DEL MIBTREE--------------------------------
  	  	
        //--------------------------------Manager SNMPv3------------------------------------------
	snmpv3 = new JPanel();
	snmpv3.setLayout(null);

	jl_snmpv3_sel = new JLabel("Accin a realizar");//USO DEL JLABEL
    jl_snmpv3_sel.setBounds(new Rectangle(5,5,100,20));//establece el xy del componente   
    snmpv3.add(jl_snmpv3_sel,null);
    
    jcb_snmpv3_sel = new JComboBox();//USO DEL JCOMBOBOX
  	jcb_snmpv3_sel.setBounds(new Rectangle(110,5,373,20));
	jcb_snmpv3_sel.addItem("Configure Parameters");
  	jcb_snmpv3_sel.addItem("Command Get");
  	jcb_snmpv3_sel.addItem("Command GetNext");
  	jcb_snmpv3_sel.addItem("Command GetBulk");
  	jcb_snmpv3_sel.addItem("Command GetTable");
  	jcb_snmpv3_sel.addItem("Command Walk");
  	jcb_snmpv3_sel.addItem("Command Set");
  	jcb_snmpv3_sel.addItem("Enviar/Ver Traps");  	//jcb_snmpv1_sel.setMaximumRowCount(2);
  	snmpv3.add(jcb_snmpv3_sel,null);
  	
  	jcb_snmpv3_sel.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
      }
    });


        //----------------------------------Connection Properties-----------------------------------
    	jtf_snmpv3_IP.setText(String.valueOf(IP));
    	jpf_snmpv3_User.setEchoChar('*');
    	jpf_snmpv3_User.setText(String.valueOf(user));
    	jpf_snmpv3_Aut.setEchoChar('*');
    	jpf_snmpv3_Aut.setText(String.valueOf(claveAut));
    	jpf_snmpv3_Priv.setEchoChar('*');
    	jpf_snmpv3_Priv.setText(String.valueOf(clavePriv));
 
        jcb_snmpv3_VerUsr.addActionListener(new ActionListener(){    
            public void actionPerformed(ActionEvent e) {	          
                if ((jcb_snmpv3_VerUsr.getSelectedItem())==optionYES){jpf_snmpv3_User.setEchoChar((char)0);jpf_snmpv3_Aut.setEchoChar((char)0);jpf_snmpv3_Priv.setEchoChar((char)0);}; 
                if ((jcb_snmpv3_VerUsr.getSelectedItem())==optionNO){jpf_snmpv3_User.setEchoChar('*');jpf_snmpv3_Aut.setEchoChar('*');jpf_snmpv3_Priv.setEchoChar('*');}; 	
            }
        });

        //----------------------------------End Connection Properties----------------------------


	    //----------------------------------Pantalla de Get----------------------------------------  	
 	    compuestoGetSNMPv3Temp = new Vector();
 	    
 	    //Para el aadir
 	    jb_snmpv3_getAdd.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        
        	  //aqui lo que hace
          	  if ((jtf_snmpv3_getGet.getText()).equals("")){
          	    JOptionPane.showMessageDialog(AppGo.this,errorGeneral01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_getGet.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	}else{        	  	
          	  	          	  	
                            //transform the string OID to the array to pass it to the get
	          	  	try{
		          	  	int digits=0;
		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
						while(Token.hasMoreTokens()){
						  Token.nextToken();
						  digits++;	
						}
		          	  	//System.out.println("the oid has "+ digits +" digits");
		          	  	int size=digits;
		          	  	//System.out.println("the request will have "+ size +" digits");
		          	  	int request[] = new int[size];
		          	  	int i=0;
		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
						while(Token1.hasMoreTokens()){
						  request[i]=Integer.parseInt(Token1.nextToken());
						  i++;
						  //System.out.println("value of i: "+i);	
						}

	           	  		
	           	  		compuestoGetSNMPv3Temp.add(new OID(request));
	          	  	    
	          	  	    String contenido = "";
					    for (int pp=0;pp<compuestoGetSNMPv3Temp.size();pp++){
					      contenido=contenido.concat(compuestoGetSNMPv3Temp.get(pp)+"; ");	
					    }
					    jtf_snmpv3_getObjs.setText(contenido);
		          	  	
		          	  }catch(NumberFormatException nfe){
	          	  	    JOptionPane.showMessageDialog(AppGo.this,errorGeneral02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
        	  	JOptionPane.showMessageDialog(AppGo.this,errorGeneral03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
          	    JOptionPane.showMessageDialog(AppGo.this,errorGeneral01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_getGet.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	}else{        	  	
          	  	          	  	
					//Para transformar el OID de string al arreglo para pasarselo al get
	          	  	//System.out.println("Tratando el oid");
	          	  	try{
		          	  	int digits=0;
		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
						while(Token.hasMoreTokens()){
						  Token.nextToken();
						  digits++;	
						}
		          	  	//System.out.println("el oid tiene "+digits+"digits");
		          	  	int size=digits;
		          	  	//System.out.println("el request va a tener "+size+" digits");
		          	  	int request[] = new int[size];
		          	  	int i=0;
		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
						while(Token1.hasMoreTokens()){
						  request[i]=Integer.parseInt(Token1.nextToken());
						  i++;
						  //System.out.println("value of i: "+i);	
						}
		          	  	//request[i]=0;
		          	  	/*
		          	  	System.out.println("cambiado a arreglo");
		          	  	i++;
		          	  	System.out.println("size del arreglo: "+i);
		          	  	for (int j=0;j<i;j++){
		          	  	  System.out.println("d"+j+" valor: "+request[j]);	
		          	  	}
		          	  	*/
	           	  		

                                        // HERE COMES THE CODE TO REALLY SEND THE REQUIREMENT	           	  		
	           	  		boolean esCompuesto=false;
		          	  	compuestoGetSNMPv3 = new Vector();
		          	  	if((compuestoGetSNMPv3Temp.size())>=1){
		          	  	   compuestoGetSNMPv3=compuestoGetSNMPv3Temp;
		          	  	   esCompuesto=true;	
		          	  	}else{
		          	  	   compuestoGetSNMPv3.add(new OID(request));
		          	  	   esCompuesto=false;
		          	  	}
	           	  		
	         	  		SNMPv3 manager = new SNMPv3();
	         	  		
	         	  		int nivelSeguridad=0;
	         	  		if ((jcb_snmpv3_getModSeg.getSelectedItem())=="AUTH_NOPRIV"){nivelSeguridad=SecurityLevel.AUTH_NOPRIV;}
	         	  		if ((jcb_snmpv3_getModSeg.getSelectedItem())=="AUTH_PRIV"){nivelSeguridad=SecurityLevel.AUTH_PRIV;}
	         	  		if ((jcb_snmpv3_getModSeg.getSelectedItem())=="NOAUTH_NOPRIV"){nivelSeguridad=SecurityLevel.NOAUTH_NOPRIV;}
	         	  		manager.cambiarIdiomaAMensajes(errorGeneral04,errorGeneral29,errorGeneral30,errorGeneral31);
		          	  	String answer = manager.getv3(IP, String.valueOf(pto), user, claveAut, clavePriv, nivelSeguridad,inten, timeOut, compuestoGetSNMPv3,metAut,metPriv);
		          	  	compuestoGetSNMPv3.removeAllElements();
		          	  	compuestoGetSNMPv3Temp.removeAllElements();
				        jtf_snmpv3_getObjs.setText("");
		          	  	
		          	  	//System.out.println("Getv2c: "+answer);
		          	  	if (answer.equals(errorGeneral04)){
		          	  	  answer=("Get SNMPv3: ").concat(answer);
		          	  	}else{
		          	  	  answer=("Get SNMPv3: ").concat(answer);	
		          	  	}
		          	  	answer=answer.concat("\n");
		          	  	jta_snmpv3_getResp.setText((jta_snmpv3_getResp.getText()).concat(answer));
		          	  	
		          	  }catch(NumberFormatException nfe){
	          	  	    JOptionPane.showMessageDialog(AppGo.this,errorGeneral02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
	          	  	  }
          	  	}
          	  }
         	}
       	});
       	
   	
   		
				
		//----------------------------------End de Pantalla de Get---------------------------------	
	 
 		//----------------------------------Pantalla de GetNext------------------------------------	      	
//		jp_snmpv3_GetNext = new JPanel();
//	    //jp_snmpv3_GetNext.setBackground(Color.blue);
//		//jp_snmpv3_GetNext.setBorder(BorderFactory.createTitledBorder("Command GetNext de SNMPv1"));
//		jp_snmpv3_GetNext.setBounds(new Rectangle(0,30,483,483));  
//		jp_snmpv3_GetNext.setBorder(BorderFactory.createTitledBorder("Command GetNext"));
//		jp_snmpv3_GetNext.setLayout(null);
//		jp_snmpv3_GetNext.setVisible(false);
//		snmpv3.add(jp_snmpv3_GetNext,null);
	    	
//    	jsp_snmpv3_GetNextDescrip = new JScrollPane();
//    	jsp_snmpv3_GetNextDescrip.setBounds(new Rectangle(10,20,465,270));  //320,10,435,300
    	jsp_snmpv3_GetNextDescrip.setWheelScrollingEnabled(true);
//    	jp_snmpv3_GetNext.add(jsp_snmpv3_GetNextDescrip,null);
    	
//    	jta_snmpv3_GetNextDescrip = new JTextArea();           
    	jta_snmpv3_GetNextDescrip.setText("");
    	jta_snmpv3_GetNextDescrip.setEditable(false);
//    	jsp_snmpv3_GetNextDescrip.getViewport().add(jta_snmpv3_GetNextDescrip,null);
     	
//    	jl_snmpv3_GetNextEtiGetNext = new JLabel("OID");
//	    jl_snmpv3_GetNextEtiGetNext.setBounds(new Rectangle(10,300,20,20));   
//	    jp_snmpv3_GetNext.add(jl_snmpv3_GetNextEtiGetNext,null);
	    
//	    jtf_snmpv3_GetNextGetNext = new JTextField();
//	    jtf_snmpv3_GetNextGetNext.setBounds(new Rectangle(40,300,245,20));  
	    jtf_snmpv3_GetNextGetNext.setEditable(true);
//	    jp_snmpv3_GetNext.add(jtf_snmpv3_GetNextGetNext,null);

//        jb_snmpv3_getNextAdd = new JButton("Aadir");
//	    jb_snmpv3_getNextAdd.setBounds(new Rectangle(295,300,79,20));  
//	    jb_snmpv3_getNextAdd.setToolTipText("Presione para agregar el OID.");
//	    jp_snmpv3_GetNext.add(jb_snmpv3_getNextAdd,null);
	    
//    	jb_snmpv3_getNextUndo = new JButton("Deshacer");
//	    jb_snmpv3_getNextUndo.setBounds(new Rectangle(384,300,90,20));  //9999
//	    jb_snmpv3_getNextUndo.setToolTipText("Presione para eliminar el ltimo OID de los objetos.");
//	    jp_snmpv3_GetNext.add(jb_snmpv3_getNextUndo,null);
	    
//    	jl_snmpv3_getNextObjs = new JLabel("Objetos");
//	    jl_snmpv3_getNextObjs.setBounds(new Rectangle(10,330,60,20));
//	    jp_snmpv3_GetNext.add(jl_snmpv3_getNextObjs,null);

//	    jtf_snmpv3_getNextObjs = new JTextField();
//	    jtf_snmpv3_getNextObjs.setBounds(new Rectangle(60,330,120,20));   
	    jtf_snmpv3_getNextObjs.setEditable(false);	    	    
//	    jp_snmpv3_GetNext.add(jtf_snmpv3_getNextObjs,null);

//    	jl_snmpv3_getNextModSeg = new JLabel("Seguridad");
//	    jl_snmpv3_getNextModSeg.setBounds(new Rectangle(190,330,60,20));
//	    jp_snmpv3_GetNext.add(jl_snmpv3_getNextModSeg,null);
	    
//	    jcb_snmpv3_getNextModSeg = new JComboBox();//USO DEL JCOMBOBOX
//	  	jcb_snmpv3_getNextModSeg.setBounds(new Rectangle(260,330,125,20));  
//		jcb_snmpv3_getNextModSeg.addItem("AUTH_NOPRIV");
//		jcb_snmpv3_getNextModSeg.addItem("AUTH_PRIV");
//		jcb_snmpv3_getNextModSeg.addItem("NOAUTH_NOPRIV");
//		//jcb_snmpv3_getNextModSeg.addItem("NOAUTH_PRIV");	  	
//	  	//jcb_snmpv3_getNextModSeg.setSelectedIndex(1);	  	 
//	  	jp_snmpv3_GetNext.add(jcb_snmpv3_getNextModSeg,null);

//    	jb_snmpv3_GetNextGetNext = new JButton("GetNext");
//	    jb_snmpv3_GetNextGetNext.setBounds(new Rectangle(395,330,79,20));   
//	    jb_snmpv3_GetNextGetNext.setToolTipText("Presione para obtener el valor.");
//	    jp_snmpv3_GetNext.add(jb_snmpv3_GetNextGetNext,null);
 
//	    jsp_snmpv3_GetNextResp = new JScrollPane();
//    	jsp_snmpv3_GetNextResp.setBounds(new Rectangle(10,360,465,111));   
    	jsp_snmpv3_GetNextResp.setWheelScrollingEnabled(true);
//    	jp_snmpv3_GetNext.add(jsp_snmpv3_GetNextResp,null);
    	
//    	jta_snmpv3_GetNextResp = new JTextArea();
    	jta_snmpv3_GetNextResp.setText("");
    	jta_snmpv3_GetNextResp.setEditable(false);
//    	jsp_snmpv3_GetNextResp.getViewport().add(jta_snmpv3_GetNextResp,null);
	    
	    compuestoGetNextSNMPv3Temp = new Vector();
	    
	    //Para aadir
	    jb_snmpv3_getNextAdd.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        
        	  //aqui lo que hace
          	  if ((jtf_snmpv3_GetNextGetNext.getText()).equals("")){
          	    JOptionPane.showMessageDialog(AppGo.this,errorGeneral01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_GetNextGetNext.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	}else{        	  	
          	  	          	  	
					//Para transformar el OID de string al arreglo para pasarselo al GetNext
	          	  	//System.out.println("Tratando el oid");
	          	  	try{
		          	  	int digits=0;
		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
						while(Token.hasMoreTokens()){
						  Token.nextToken();
						  digits++;	
						}
		          	  	//System.out.println("el oid tiene "+digits+"digits");
		          	  	int size=digits;
		          	  	//System.out.println("el request va a tener "+size+" digits");
		          	  	int request[] = new int[size];
		          	  	int i=0;
		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
						while(Token1.hasMoreTokens()){
						  request[i]=Integer.parseInt(Token1.nextToken());
						  i++;
						  //System.out.println("value of i: "+i);	
						}
		          	  	//request[i]=0;
		          	  	/*
		          	  	System.out.println("cambiado a arreglo");
		          	  	i++;
		          	  	System.out.println("size del arreglo: "+i);
		          	  	for (int j=0;j<i;j++){
		          	  	  System.out.println("d"+j+" valor: "+request[j]);	
		          	  	}
		          	  	*/
		          	  	
		          	  	compuestoGetNextSNMPv3Temp.add(new OID(request));
		          	  	String contenido = "";
		          	  	for (int pp=0;pp<compuestoGetNextSNMPv3Temp.size();pp++){
		          	  	  contenido=contenido.concat(compuestoGetNextSNMPv3Temp.get(pp)+"; ");	
		          	  	}
		          	  	jtf_snmpv3_getNextObjs.setText(contenido);   
		          	  	
		          	  }catch(NumberFormatException nfe){
	          	  	    JOptionPane.showMessageDialog(AppGo.this,errorGeneral02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
        	  	JOptionPane.showMessageDialog(AppGo.this,errorGeneral03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
          	    JOptionPane.showMessageDialog(AppGo.this,errorGeneral01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_GetNextGetNext.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	}else{        	  	
          	  	          	  	
					//Para transformar el OID de string al arreglo para pasarselo al GetNext
	          	  	//System.out.println("Tratando el oid");
	          	  	try{
		          	  	int digits=0;
		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
						while(Token.hasMoreTokens()){
						  Token.nextToken();
						  digits++;	
						}
		          	  	//System.out.println("el oid tiene "+digits+"digits");
		          	  	int size=digits;
		          	  	//System.out.println("el request va a tener "+size+" digits");
		          	  	int request[] = new int[size];
		          	  	int i=0;
		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
						while(Token1.hasMoreTokens()){
						  request[i]=Integer.parseInt(Token1.nextToken());
						  i++;
						  //System.out.println("value of i: "+i);	
						}
		          	  	//request[i]=0;
		          	  	/*
		          	  	System.out.println("cambiado a arreglo");
		          	  	i++;
		          	  	System.out.println("size del arreglo: "+i);
		          	  	for (int j=0;j<i;j++){
		          	  	  System.out.println("d"+j+" valor: "+request[j]);	
		          	  	}
		          	  	*/
		          	  	
		          	  	boolean esCompuesto=false;
		          	  	compuestoGetNextSNMPv3 = new Vector();
		          	  	if((compuestoGetNextSNMPv3Temp.size())>=1){
		          	  	   compuestoGetNextSNMPv3=compuestoGetNextSNMPv3Temp;
		          	  	   esCompuesto=true;	
		          	  	}else{
		          	  	   compuestoGetNextSNMPv3.add(new OID(request));
		          	  	   esCompuesto=false;
		          	  	}
		          	  	
		          	  	SNMPv3 manager = new SNMPv3();
	         	  		
	         	  		int nivelSeguridad=0;
	         	  		if ((jcb_snmpv3_getNextModSeg.getSelectedItem())=="AUTH_NOPRIV"){nivelSeguridad=SecurityLevel.AUTH_NOPRIV;}
	         	  		if ((jcb_snmpv3_getNextModSeg.getSelectedItem())=="AUTH_PRIV"){nivelSeguridad=SecurityLevel.AUTH_PRIV;}
	         	  		if ((jcb_snmpv3_getNextModSeg.getSelectedItem())=="NOAUTH_NOPRIV"){nivelSeguridad=SecurityLevel.NOAUTH_NOPRIV;}
	         	  		manager.cambiarIdiomaAMensajes(errorGeneral04,errorGeneral29,errorGeneral30,errorGeneral31);
		          	  	String answer = manager.getNextv3(IP, String.valueOf(pto), user, claveAut, clavePriv, nivelSeguridad,inten, timeOut, compuestoGetNextSNMPv3,metAut,metPriv);
		          	  	compuestoGetNextSNMPv3.removeAllElements();
		          	  	compuestoGetNextSNMPv3Temp.removeAllElements();
					    jtf_snmpv3_getNextObjs.setText("");
		          	  	
		          	  	//System.out.println("GetNextv3: "+answer);
		          	  	if (answer.equals(errorGeneral04)){
		          	  	  answer=("GetNext SNMPv3: ").concat(answer);
		          	  	}else{
		          	  	  //Para el GetNext acumulativo	
		          	  	  if (!esCompuesto){
		          	  	  	int ini = answer.indexOf(" ");
			    		  	ini=ini+1;
			    		  	int fin = answer.indexOf(" ",ini);
		          	  	  	//System.out.println("actual --"+answer.substring(ini,fin)+"--");
		          	  	  	jtf_snmpv3_GetNextGetNext.setText("."+answer.substring(ini,fin));
			    		  }
		          	  	  answer=("GetNext SNMPv3: ").concat(answer);
		          	  	}
		          	  	answer=answer.concat("\n");
		          	  	jta_snmpv3_GetNextResp.setText((jta_snmpv3_GetNextResp.getText()).concat(answer));   
		          	  	
		          	  }catch(NumberFormatException nfe){
	          	  	    JOptionPane.showMessageDialog(AppGo.this,errorGeneral02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
	          	  	  }
          	  	}
          	  }   
         	}
        	});
       	
		
		//----------------------------------End de Pantalla de GetNext-----------------------------

		//---------------------------------------Pantalla de GetBulk----------------------------------
		
//		jp_snmpv3_GetBulk = new JPanel();
//	    //jp_snmpv3_GetBulk.setBackground(Color.white);
//		//jp_snmpv3_GetBulk.setBorder(BorderFactory.createTitledBorder("Set"));
//		jp_snmpv3_GetBulk.setBounds(new Rectangle(0,30,483,483));
//		jp_snmpv3_GetBulk.setBorder(BorderFactory.createTitledBorder("Command GetBulk"));
//		jp_snmpv3_GetBulk.setLayout(null);
//		jp_snmpv3_GetBulk.setVisible(false);
//		snmpv3.add(jp_snmpv3_GetBulk,null);
		
//	    jsp_snmpv3_GetBulkResp = new JScrollPane();
//    	jsp_snmpv3_GetBulkResp.setBounds(new Rectangle(10,110,465,361));   
    	jsp_snmpv3_GetBulkResp.setWheelScrollingEnabled(true);
//    	jp_snmpv3_GetBulk.add(jsp_snmpv3_GetBulkResp,null);

//    	jta_snmpv3_GetBulkResp = new JTextArea();
    	jta_snmpv3_GetBulkResp.setText("");
    	jta_snmpv3_GetBulkResp.setEditable(false);
//    	jsp_snmpv3_GetBulkResp.getViewport().add(jta_snmpv3_GetBulkResp,null);
  	
    	//0  <0 se trae todo, si vale 1  >1 se trae solo 1 valor. - pedir por pantalla
//    	jl_snmpv3_nonRepe = new JLabel("NonRepeaters");//USO DEL JLABEL
//    	jl_snmpv3_nonRepe.setBounds(new Rectangle(10,50,90,20));//establece el xy del componente
//    	jp_snmpv3_GetBulk.add(jl_snmpv3_nonRepe,null);
    
//    	jtf_snmpv3_nonRepe = new JTextField();//USO DEL JTEXTFIELD    
//    	jtf_snmpv3_nonRepe.setBounds(new Rectangle(105,50,30,20));//establece el xy del componente
    	jtf_snmpv3_nonRepe.setText(String.valueOf(NonRepeaters));
//    	jp_snmpv3_GetBulk.add(jtf_snmpv3_nonRepe,null);
   	
    	// cantidad de variables que va a retornar - pedir por pantalla
//    	jl_snmpv3_maxRep = new JLabel("MaxRepetitions");//USO DEL JLABEL
//    	jl_snmpv3_maxRep.setBounds(new Rectangle(150,50,90,20));//establece el xy del componente
//    	jp_snmpv3_GetBulk.add(jl_snmpv3_maxRep,null);
   
//    	jtf_snmpv3_maxRep = new JTextField();//USO DEL JTEXTFIELD    
//    	jtf_snmpv3_maxRep.setBounds(new Rectangle(250,50,30,20));//establece el xy del componente
    	jtf_snmpv3_maxRep.setText(String.valueOf(MaxRepetitions));
//    	jp_snmpv3_GetBulk.add(jtf_snmpv3_maxRep,null);

//    	jb_snmpv3_GetBulk_add = new JButton("Aadir");
//	    jb_snmpv3_GetBulk_add.setBounds(new Rectangle(295,50,79,20));  //9999
//	    jb_snmpv3_GetBulk_add.setToolTipText("Presione para agregar el OID.");
//	    jp_snmpv3_GetBulk.add(jb_snmpv3_GetBulk_add,null);
	    		
//    	jb_snmpv3_GetBulkUndo = new JButton("Deshacer");
//	    jb_snmpv3_GetBulkUndo.setBounds(new Rectangle(385,50,89,20));  //9999 
//	    jb_snmpv3_GetBulkUndo.setToolTipText("Presione para eliminar el ltimo OID de los objetos.");
//	    jp_snmpv3_GetBulk.add(jb_snmpv3_GetBulkUndo,null);
	      	
//    	jl_snmpv3_GetBulkEtiSet = new JLabel("OID");
//	    jl_snmpv3_GetBulkEtiSet.setBounds(new Rectangle(10,20,20,20));   
//	    jp_snmpv3_GetBulk.add(jl_snmpv3_GetBulkEtiSet,null);

//	    jtf_snmpv3_GetBulkGetBulk = new JTextField();
//	    jtf_snmpv3_GetBulkGetBulk.setBounds(new Rectangle(40,20,225,20));    
	    jtf_snmpv3_GetBulkGetBulk.setEditable(true);
//	    jp_snmpv3_GetBulk.add(jtf_snmpv3_GetBulkGetBulk,null);

//    	jl_snmpv3_getBulkModSeg = new JLabel("Seguridad");
//	    jl_snmpv3_getBulkModSeg.setBounds(new Rectangle(280,20,60,20));
//	    jp_snmpv3_GetBulk.add(jl_snmpv3_getBulkModSeg,null);
	    
//	    jcb_snmpv3_getBulkModSeg = new JComboBox();//USO DEL JCOMBOBOX
//	  	jcb_snmpv3_getBulkModSeg.setBounds(new Rectangle(349,20,125,20));  
//		jcb_snmpv3_getBulkModSeg.addItem("AUTH_NOPRIV");
//		jcb_snmpv3_getBulkModSeg.addItem("AUTH_PRIV");
//		jcb_snmpv3_getBulkModSeg.addItem("NOAUTH_NOPRIV");
//		//jcb_snmpv3_getBulkModSeg.addItem("NOAUTH_PRIV");	  	
//	  	//jcb_snmpv3_getBulkModSeg.setSelectedIndex(1);	  	 
//	  	jp_snmpv3_GetBulk.add(jcb_snmpv3_getBulkModSeg,null);

//    	jl_snmpv3_getBulkObjs = new JLabel("Objetos");
//	    jl_snmpv3_getBulkObjs.setBounds(new Rectangle(10,80,60,20));
//	    jp_snmpv3_GetBulk.add(jl_snmpv3_getBulkObjs,null);
 
//	    jtf_snmpv3_getBulkObjs = new JTextField();
//	    jtf_snmpv3_getBulkObjs.setBounds(new Rectangle(70,80,310,20));   
	    jtf_snmpv3_getBulkObjs.setEditable(false);	    	    
//	    jp_snmpv3_GetBulk.add(jtf_snmpv3_getBulkObjs,null);
	  	
//    	jb_snmpv3_GetBulkGetBulk = new JButton("GetBulk");
//	    jb_snmpv3_GetBulkGetBulk.setBounds(new Rectangle(395,80,79,20));   
//        jb_snmpv3_GetBulkGetBulk.setToolTipText("Presione para obtener los valores.");
//	    jp_snmpv3_GetBulk.add(jb_snmpv3_GetBulkGetBulk,null);
		
		compuestoGetBulkSNMPv3Temp = new Vector();
		
		//Para aadir
		jb_snmpv3_GetBulk_add.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        
        	  //aqui lo que hace
          	  if ((jtf_snmpv3_GetBulkGetBulk.getText()).equals("")){
          	    JOptionPane.showMessageDialog(AppGo.this,errorGeneral01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	    jta_snmpv3_GetBulkResp.setText("");
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_GetBulkGetBulk.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	  jta_snmpv3_GetBulkResp.setText("");
          	  	}else{ 
          	  		if (!(esNumero(jtf_snmpv3_nonRepe.getText()))){          			
          		  	  JOptionPane.showMessageDialog(AppGo.this,errorGeneral26,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
          	  		  jta_snmpv3_GetBulkResp.setText("");
          	  		}else{
          	  			if ((!(esNumero(jtf_snmpv3_maxRep.getText())))||(Integer.parseInt(jtf_snmpv3_maxRep.getText())<=0)){          			
          		          JOptionPane.showMessageDialog(AppGo.this,errorGeneral27,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
          	  			  jta_snmpv3_GetBulkResp.setText("");
          	  			}else{
							//Para transformar el OID de string al arreglo para pasarselo al GetBulk
			          	  	//System.out.println("Tratando el oid");
			          	  	try{   
				          	  	int digits=0;
				          	  	StringTokenizer Token = new StringTokenizer (OID,".");
								while(Token.hasMoreTokens()){
								  Token.nextToken();
								  digits++;	
								}
				          	  	//System.out.println("el oid tiene "+digits+"digits");
				          	  	int size=digits;
				          	  	//System.out.println("el request va a tener "+size+" digits");
				          	  	int request[] = new int[size];
				          	  	int i=0;
				          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
								while(Token1.hasMoreTokens()){
								  request[i]=Integer.parseInt(Token1.nextToken());
								  i++;
								  //System.out.println("value of i: "+i);	
								}
				          	  	//request[i]=0;
				          	  	
				          	  	compuestoGetBulkSNMPv3Temp.add(new OID(request));
				          	  	String contenido = "";
				          	  	for (int pp=0;pp<compuestoGetBulkSNMPv3Temp.size();pp++){
				          	  	  contenido=contenido.concat(compuestoGetBulkSNMPv3Temp.get(pp)+"; ");	
				          	  	}
				          	  	jtf_snmpv3_getBulkObjs.setText(contenido);
				          	  	
			          	  	  }catch(NumberFormatException nfe){
		          	  	    	JOptionPane.showMessageDialog(AppGo.this,errorGeneral02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
        	  	JOptionPane.showMessageDialog(AppGo.this,errorGeneral03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
          	    JOptionPane.showMessageDialog(AppGo.this,errorGeneral01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	    jta_snmpv3_GetBulkResp.setText("");
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_GetBulkGetBulk.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	  jta_snmpv3_GetBulkResp.setText("");
          	  	}else{ 
          	  		if (!(esNumero(jtf_snmpv3_nonRepe.getText()))){          			
          		  	  JOptionPane.showMessageDialog(AppGo.this,errorGeneral26,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
          	  		  jta_snmpv3_GetBulkResp.setText("");
          	  		}else{
          	  			if ((!(esNumero(jtf_snmpv3_maxRep.getText())))||(Integer.parseInt(jtf_snmpv3_maxRep.getText())<=0)){          			
          		          JOptionPane.showMessageDialog(AppGo.this,errorGeneral27,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
          	  			  jta_snmpv3_GetBulkResp.setText("");
          	  			}else{
							//Para transformar el OID de string al arreglo para pasarselo al GetBulk
			          	  	//System.out.println("Tratando el oid");
			          	  	try{   
				          	  	int digits=0;
				          	  	StringTokenizer Token = new StringTokenizer (OID,".");
								while(Token.hasMoreTokens()){
								  Token.nextToken();
								  digits++;	
								}
				          	  	//System.out.println("el oid tiene "+digits+"digits");
				          	  	int size=digits;
				          	  	//System.out.println("el request va a tener "+size+" digits");
				          	  	int request[] = new int[size];
				          	  	int i=0;
				          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
								while(Token1.hasMoreTokens()){
								  request[i]=Integer.parseInt(Token1.nextToken());
								  i++;
								  //System.out.println("value of i: "+i);	
								}
				          	  	//request[i]=0;
				          	  	
				          	  	jta_snmpv3_GetBulkResp.setText(errorGeneral28);
				          	  	
				          	  	boolean esCompuesto=false;
				          	  	compuestoGetBulkSNMPv3 = new Vector();
				          	  	if((compuestoGetBulkSNMPv3Temp.size())>=1){
				          	  	   compuestoGetBulkSNMPv3=compuestoGetBulkSNMPv3Temp;
				          	  	   esCompuesto=true;	
				          	  	}else{
				          	  	   compuestoGetBulkSNMPv3.add(new OID(request));
				          	  	   esCompuesto=false;
				          	  	}
				          	  	
				          	  	SNMPv3 manager = new SNMPv3();
			         	  		int nivelSeguridad=0;
			         	  		if ((jcb_snmpv3_getBulkModSeg.getSelectedItem())=="AUTH_NOPRIV"){nivelSeguridad=SecurityLevel.AUTH_NOPRIV;}
			         	  		if ((jcb_snmpv3_getBulkModSeg.getSelectedItem())=="AUTH_PRIV"){nivelSeguridad=SecurityLevel.AUTH_PRIV;}
			         	  		if ((jcb_snmpv3_getBulkModSeg.getSelectedItem())=="NOAUTH_NOPRIV"){nivelSeguridad=SecurityLevel.NOAUTH_NOPRIV;}
			         	  		manager.cambiarIdiomaAMensajes(errorGeneral04,errorGeneral29,errorGeneral30,errorGeneral31);
		          	  			String answer = manager.getBulkv3(IP, String.valueOf(pto), user, claveAut, clavePriv, nivelSeguridad,inten, timeOut, compuestoGetBulkSNMPv3,Integer.parseInt(jtf_snmpv3_nonRepe.getText()), Integer.parseInt(jtf_snmpv3_maxRep.getText()), metAut, metPriv);
		          	  			compuestoGetBulkSNMPv3.removeAllElements();
				          	  	compuestoGetBulkSNMPv3Temp.removeAllElements();
							    jtf_snmpv3_getBulkObjs.setText("");
		          	  			
		          	  			if (answer.equals(errorGeneral04)){
		          	  	  		  answer=errorGeneral05;
		          	  			}else{
		          	  			  String temp = " ";
								  StringTokenizer Token2 = new StringTokenizer (answer,";");
								  while(Token2.hasMoreTokens()){
								    temp=temp.concat(String.valueOf(Token2.nextToken())+("\n"));
								  }
								  answer=temp;	
		          	  			}
		          	  			
								jta_snmpv3_GetBulkResp.setText(jta_snmpv3_GetBulkResp.getText().concat(answer));
				          	  	
			          	  	  }catch(NumberFormatException nfe){
		          	  	    	JOptionPane.showMessageDialog(AppGo.this,errorGeneral02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
		          	  	    	jta_snmpv3_GetBulkResp.setText("");
		          	  	  	  }
          	  		  }
          	  		}
          	  	}
          	  }
          	  
         	}   
       	});    

		//----------------------------------End de Pantalla de GetBulk--------------------------------
		
		
		//****************************************************************************************************************************
		//----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE--------

//		jp_snmpv3_getTable = new JPanel();
	    //jp_snmpv3_getTable.setBackground(Color.blue);
//		//jp_snmpv3_getTable.setBorder(BorderFactory.createTitledBorder("Command GetNext de SNMPv1"));
//		jp_snmpv3_getTable.setBounds(new Rectangle(0,30,483,483));  
//		jp_snmpv3_getTable.setBorder(BorderFactory.createTitledBorder("Command GetTable"));
//		jp_snmpv3_getTable.setLayout(null);
//		jp_snmpv3_getTable.setVisible(false);
//		snmpv3.add(jp_snmpv3_getTable,null);
		/*
		JLabel jl_snmpv3_getTableEtigetTable;
		JTextField jtf_snmpv3_getTablegetTable;
		JButton jb_snmpv3_getTablegetTable;
		JScrollPane jsp_snmpv3_getTablegetTable;
		*/

//		jl_snmpv3_getTableEtigetTable = new JLabel("OID");
//	    jl_snmpv3_getTableEtigetTable.setBounds(new Rectangle(10,20,20,20));    
//	    jp_snmpv3_getTable.add(jl_snmpv3_getTableEtigetTable,null);
	    
//	    jtf_snmpv3_getTablegetTable = new JTextField();
//	    jtf_snmpv3_getTablegetTable.setBounds(new Rectangle(40,20,135,20));  
	    jtf_snmpv3_getTablegetTable.setEditable(true);
//	    jp_snmpv3_getTable.add(jtf_snmpv3_getTablegetTable,null);
	    
//	    jl_snmpv3_getTableModSeg = new JLabel("Seguridad");
//	    jl_snmpv3_getTableModSeg.setBounds(new Rectangle(185,20,60,20));
//	    jp_snmpv3_getTable.add(jl_snmpv3_getTableModSeg,null);
	    
//	    jcb_snmpv3_getTableModSeg = new JComboBox();//USO DEL JCOMBOBOX
//	  	jcb_snmpv3_getTableModSeg.setBounds(new Rectangle(250,20,125,20));  
//		jcb_snmpv3_getTableModSeg.addItem("AUTH_NOPRIV");
//		jcb_snmpv3_getTableModSeg.addItem("AUTH_PRIV");
//		jcb_snmpv3_getTableModSeg.addItem("NOAUTH_NOPRIV");
//		//jcb_snmpv3_getTableModSeg.addItem("NOAUTH_PRIV");	  	
//	  	//jcb_snmpv3_getTableModSeg.setSelectedIndex(1);	  	 
//	  	jp_snmpv3_getTable.add(jcb_snmpv3_getTableModSeg,null);
	    
//	    jb_snmpv3_getTablegetTable = new JButton("GetTable");
//	    jb_snmpv3_getTablegetTable.setBounds(new Rectangle(384,20,90,20));  //9999
//	    jb_snmpv3_getTablegetTable.setToolTipText("Presione para consultar la tabla.");
//	    jp_snmpv3_getTable.add(jb_snmpv3_getTablegetTable,null);
	    
//	    jsp_snmpv3_getTablegetTable = new JScrollPane();
//    	jsp_snmpv3_getTablegetTable.setBounds(new Rectangle(10,50,465,420));  //320,10,435,300
    	jsp_snmpv3_getTablegetTable.setWheelScrollingEnabled(true);
//    	jp_snmpv3_getTable.add(jsp_snmpv3_getTablegetTable,null);
    	
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
          	    JOptionPane.showMessageDialog(AppGo.this,errorGeneral01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_getTablegetTable.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	}else{        	  	
          	  	        	  	
					//Para transformar el OID de string al arreglo para pasarselo al GetNext
	          	  	//System.out.println("Tratando el oid");
	          	  	try{
		          	  	int digits=0;
		          	  	StringTokenizer Token = new StringTokenizer (OID,".");
						while(Token.hasMoreTokens()){
						  Token.nextToken();
						  digits++;	
						}
		          	  	//System.out.println("el oid tiene "+digits+"digits");
		          	  	int size=digits;
		          	  	//System.out.println("el request va a tener "+size+" digits");
		          	  	int request[] = new int[size];
		          	  	int i=0;
		          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
						while(Token1.hasMoreTokens()){
						  request[i]=Integer.parseInt(Token1.nextToken());
						  i++;
						  //System.out.println("value of i: "+i);	
						}
		          	  	//request[i]=0;
		          	  	//Para la parte de la tabla
		          	  	getTable traerTabla = new getTable();
		          	  	
		          	  	int nivelSeguridad=0;
			         	if ((jcb_snmpv3_getTableModSeg.getSelectedItem())=="AUTH_NOPRIV"){nivelSeguridad=SecurityLevel.AUTH_NOPRIV;}
			         	if ((jcb_snmpv3_getTableModSeg.getSelectedItem())=="AUTH_PRIV"){nivelSeguridad=SecurityLevel.AUTH_PRIV;}
			         	if ((jcb_snmpv3_getTableModSeg.getSelectedItem())=="NOAUTH_NOPRIV"){nivelSeguridad=SecurityLevel.NOAUTH_NOPRIV;}
		          	  	
		          	  	//recorrer.walkSNMPv3(IP,String.valueOf(pto),user,claveAut,clavePriv,nivelSeguridad,inten,timeOut,request,limite, metAut, metPriv);
		          	  	traerTabla.cambiarIdiomaAMensajes(errorGeneral07,errorGeneral08,errorGeneral09,errorGeneral10,errorGeneral11,errorGeneral12,errorGeneral13);
		          	  	Vector columnas = traerTabla.getTablev3(IP,String.valueOf(pto),user,claveAut,clavePriv,nivelSeguridad,inten,timeOut,request,0, metAut, metPriv);
		          	  	
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
		          	  	    Collection listaMibs = (Collection)loadedMibsToSearchForNames;	
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
					    	if(mensajeError.equals("\n".concat(errorGeneral07))){
							  mensajeError=mensajeError.concat("\n");
					    	  mensajeError=mensajeError.concat(errorGeneral06);					    		
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
	          	  	    JOptionPane.showMessageDialog(AppGo.this,errorGeneral02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
	          	  	  }
          	  	}
          	  }
         	}
       	});
		//GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE----GETTABLE--------
		//****************************************************************************************************************************		
		

		//----------------------------------Pantalla del Walk--------------------------------------
//		jp_snmpv3_walk= new JPanel();
//	    //jp_snmpv3_walk.setBackground(Color.white);
//	    jp_snmpv3_walk.setBounds(new Rectangle(0,30,483,483));
//		jp_snmpv3_walk.setBorder(BorderFactory.createTitledBorder("Command Walk"));
//		jp_snmpv3_walk.setLayout(null);
//		jp_snmpv3_walk.setVisible(false);
//		snmpv3.add(jp_snmpv3_walk,null);
	
//		jl_snmpv3_WalkEtiLimitePregunta = new JLabel("Limitar Cantidad de Variables a Consultar");
//	    jl_snmpv3_WalkEtiLimitePregunta.setBounds(new Rectangle(10,20,236,20));   
//	    jp_snmpv3_walk.add(jl_snmpv3_WalkEtiLimitePregunta,null);

//	    jcb_snmpv3_WalkEtiLimitePregunta = new JComboBox();//USO DEL JCOMBOBOX
//	  	jcb_snmpv3_WalkEtiLimitePregunta.setBounds(new Rectangle(256,20,50,20));  
//		jcb_snmpv3_WalkEtiLimitePregunta.addItem(optionYES);
//	  	jcb_snmpv3_WalkEtiLimitePregunta.addItem(optionNO);
//	  	jcb_snmpv3_WalkEtiLimitePregunta.setSelectedIndex(1);	  	 
//	  	jp_snmpv3_walk.add(jcb_snmpv3_WalkEtiLimitePregunta,null);
		
//		jl_snmpv3_WalkEtiLimite = new JLabel("Cantidad");
//	    jl_snmpv3_WalkEtiLimite.setBounds(new Rectangle(326,20,50,20));
	    jl_snmpv3_WalkEtiLimite.setEnabled(false);
//	    jp_snmpv3_walk.add(jl_snmpv3_WalkEtiLimite,null);

//		jtf_snmpv3_WalkEtiLimite = new JTextField();
//	    jtf_snmpv3_WalkEtiLimite.setBounds(new Rectangle(386,20,89,20));  
	    jtf_snmpv3_WalkEtiLimite.setEditable(false);
//	    jtf_snmpv3_WalkEtiLimite.setText("Sin Lmite");
//	    jp_snmpv3_walk.add(jtf_snmpv3_WalkEtiLimite,null);

	  	jcb_snmpv3_WalkEtiLimitePregunta.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e) {	          
	        if ((jcb_snmpv3_WalkEtiLimitePregunta.getSelectedItem())==optionYES){jtf_snmpv3_WalkEtiLimite.setEditable(true);jtf_snmpv3_WalkEtiLimite.setText("");}
	        if ((jcb_snmpv3_WalkEtiLimitePregunta.getSelectedItem())==optionNO){jtf_snmpv3_WalkEtiLimite.setEditable(false);jtf_snmpv3_WalkEtiLimite.setText(errorGeneral21);}
	      }
	    });
				
//		jl_snmpv3_WalkEti = new JLabel("OID");
//	    jl_snmpv3_WalkEti.setBounds(new Rectangle(10,50,20,20));   
//	    jp_snmpv3_walk.add(jl_snmpv3_WalkEti,null);

//	    jtf_snmpv3_WalkOID = new JTextField();
//	    jtf_snmpv3_WalkOID.setBounds(new Rectangle(40,50,145,20));  
	    jtf_snmpv3_WalkOID.setEditable(true);
//	    jp_snmpv3_walk.add(jtf_snmpv3_WalkOID,null);

//    	jl_snmpv3_getWalkModSeg = new JLabel("Seguridad");
//	    jl_snmpv3_getWalkModSeg.setBounds(new Rectangle(195,50,60,20));
//	    jp_snmpv3_walk.add(jl_snmpv3_getWalkModSeg,null);
	    
//	    jcb_snmpv3_WalkModSeg = new JComboBox();//USO DEL JCOMBOBOX
//	  	jcb_snmpv3_WalkModSeg.setBounds(new Rectangle(260,50,125,20));  
//		jcb_snmpv3_WalkModSeg.addItem("AUTH_NOPRIV");
//		jcb_snmpv3_WalkModSeg.addItem("AUTH_PRIV");
//		jcb_snmpv3_WalkModSeg.addItem("NOAUTH_NOPRIV");
//		//jcb_snmpv3_WalkModSeg.addItem("NOAUTH_PRIV");	  	
//	  	//jcb_snmpv3_WalkModSeg.setSelectedIndex(1);	  	 
//	  	jp_snmpv3_walk.add(jcb_snmpv3_WalkModSeg,null);

//	    jb_snmpv3_Walk = new JButton("Walk");
//	    jb_snmpv3_Walk.setBounds(new Rectangle(395,50,80,20));   
//	    jb_snmpv3_Walk.setToolTipText("Presione para iniciar el Walk.");
//	    jp_snmpv3_walk.add(jb_snmpv3_Walk,null);
   
//	    jsp_snmpv3_WalkResp = new JScrollPane();
//    	jsp_snmpv3_WalkResp.setBounds(new Rectangle(10,80,466,391));   
    	jsp_snmpv3_WalkResp.setWheelScrollingEnabled(true);
//    	jp_snmpv3_walk.add(jsp_snmpv3_WalkResp,null);

//    	jta_snmpv3_WalkResp = new JTextArea();           
    	jta_snmpv3_WalkResp.setText("");
    	jta_snmpv3_WalkResp.setEditable(false);
//    	jsp_snmpv3_WalkResp.getViewport().add(jta_snmpv3_WalkResp,null);
	    
	    jb_snmpv3_Walk.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
          	  //aqui lo que hace
          	  jta_snmpv3_WalkResp.setText(errorGeneral16);
          	  if ((jtf_snmpv3_WalkOID.getText()).equals("")){
          	    JOptionPane.showMessageDialog(AppGo.this,errorGeneral01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	    jta_snmpv3_WalkResp.setText("");
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_WalkOID.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	  jta_snmpv3_WalkResp.setText("");
          	  	}else{ 
          	  		if (((jcb_snmpv3_WalkEtiLimitePregunta.getSelectedItem())==optionYES)&&(jtf_snmpv3_WalkEtiLimite.getText().equals(""))){
          	  		  JOptionPane.showMessageDialog(AppGo.this,errorGeneral14,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
          	  		  jta_snmpv3_WalkResp.setText("");
          	  		}else{
          	  			if (((jcb_snmpv3_WalkEtiLimitePregunta.getSelectedItem())==optionYES)&&(!(esNumero(jtf_snmpv3_WalkEtiLimite.getText())))){
          	  			  JOptionPane.showMessageDialog(AppGo.this,errorGeneral15,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
          	  			  jta_snmpv3_WalkResp.setText("");
          	  			}else{
							//Para transformar el OID de string al arreglo para pasarselo al GetNext
			          	  	//System.out.println("Tratando el oid");
			          	  	try{   
				          	  	int digits=0;
				          	  	StringTokenizer Token = new StringTokenizer (OID,".");
								while(Token.hasMoreTokens()){
								  Token.nextToken();
								  digits++;	
								}
				          	  	//System.out.println("el oid tiene "+digits+"digits");
				          	  	int size=digits;
				          	  	//System.out.println("el request va a tener "+size+" digits");
				          	  	int request[] = new int[size];
				          	  	int i=0;
				          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
								while(Token1.hasMoreTokens()){
								  request[i]=Integer.parseInt(Token1.nextToken());
								  i++;
								  //System.out.println("value of i: "+i);	
								}
				          	  	//request[i]=0;
				          	  	
				          	  	jta_snmpv3_WalkResp.setText(errorGeneral16);
				          	  	
			         	  		int nivelSeguridad=0;
			         	  		if ((jcb_snmpv3_WalkModSeg.getSelectedItem())=="AUTH_NOPRIV"){nivelSeguridad=SecurityLevel.AUTH_NOPRIV;}
			         	  		if ((jcb_snmpv3_WalkModSeg.getSelectedItem())=="AUTH_PRIV"){nivelSeguridad=SecurityLevel.AUTH_PRIV;}
			         	  		if ((jcb_snmpv3_WalkModSeg.getSelectedItem())=="NOAUTH_NOPRIV"){nivelSeguridad=SecurityLevel.NOAUTH_NOPRIV;}
			         	  		
		          	  			walk recorrer = new walk();
				          	  	int limite = 0;
		          	  			
				          	  	if((jcb_snmpv3_WalkEtiLimitePregunta.getSelectedItem())==optionYES){
				          	  	  limite = Integer.parseInt(jtf_snmpv3_WalkEtiLimite.getText());	
				          	  	}else{
 				          	  	  limite = 0;	
				          	  	}
				          	  	recorrer.cambiarIdiomaAMensajes(errorGeneral07,errorGeneral08,errorGeneral09,errorGeneral10,errorGeneral11,errorGeneral12,errorGeneral13,errorGeneral17,errorGeneral18,errorGeneral19,errorGeneral20);
				          	  	recorrer.walkSNMPv3(IP,String.valueOf(pto),user,claveAut,clavePriv,nivelSeguridad,inten,timeOut,request,limite, metAut, metPriv);
				          	  	
								jta_snmpv3_WalkResp.setText(jta_snmpv3_WalkResp.getText().concat(recorrer.getWalkRealizado()));
								recorrer.limpiarWalkRealizado();
				          	  	
			          	  	  }catch(NumberFormatException nfe){
		          	  	    	JOptionPane.showMessageDialog(AppGo.this,errorGeneral02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
		          	  	    	jta_snmpv3_WalkResp.setText("");
		          	  	  	  }
          	  		  }
          	  		}
          	  	}
          	  }
         	}
       	});
		
		//----------------------------------End Walk-------------------------------



		//----------------------------------Set----------------------------------------	      	

    	jsp_snmpv3_SetDescrip.setWheelScrollingEnabled(true);
    	jta_snmpv3_SetDescrip.setText("");
    	jta_snmpv3_SetDescrip.setEditable(false);
    	jsp_snmpv3_SetResp.setWheelScrollingEnabled(true);
    	jta_snmpv3_SetResp.setText("");
    	jta_snmpv3_SetResp.setEditable(false);
	    jtf_snmpv3_SetSet.setEditable(true);
	  	jcb_snmpv3_SetTipo.setEnabled(false);
	  	jcb_snmpv3_SetTipo.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e) {
	      }
	    });

	    jb_snmpv3_setAdd.setEnabled(false);    //   ***

	    jb_snmpv3_setUndo.setEnabled(false);    //   ***
 
	    jtf_snmpv3_setObjs.setEditable(false);	    	    

	    jtf_snmpv3_SetSetValor.setEditable(true);

	  	jcb_snmpv3_setModSeg.setEnabled(false);	  	 
  
        jb_snmpv3_SetSet.setEnabled(false);
        jtf_snmpv3_SetSetValor.setEnabled(false); 	 
        jtf_snmpv3_SetSetValor.setText("");


		//AQUI DEBE DE IR EL EVENTO DE SETEAR UNA VARIABLE VIA SNMP
		


		compuestoSetSNMPv3TempOID = new Vector();
		compuestoSetSNMPv3TempTipoDatos = new Vector();
		compuestoSetSNMPv3TempDatos = new Vector();		

		//Para aadir
		jb_snmpv3_setAdd.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        
        	  //aqui lo que hace
          	  if ((jtf_snmpv3_SetSet.getText()).equals("")){
          	    JOptionPane.showMessageDialog(AppGo.this,errorGeneral01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_SetSet.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	}else{ 
          	  		
          	  		if ((jtf_snmpv3_SetSetValor.getText()).equals("")){
          	  		  JOptionPane.showMessageDialog(AppGo.this,errorGeneral22,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
          	  		}else{
          	  			if ((jcb_snmpv3_SetTipo.getSelectedItem()).equals("---------------------------")){
          	  			  JOptionPane.showMessageDialog(AppGo.this,errorGeneral23,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
          	  			}else{
							//Para transformar el OID de string al arreglo para pasarselo al GetNext
			          	  	//System.out.println("Tratando el oid");
			          	  	try{   
				          	  	int digits=0;
				          	  	StringTokenizer Token = new StringTokenizer (OID,".");
								while(Token.hasMoreTokens()){
								  Token.nextToken();
								  digits++;	
								}
				          	  	//System.out.println("el oid tiene "+digits+"digits");
				          	  	int size=digits;
				          	  	//System.out.println("el request va a tener "+size+" digits");
				          	  	int request[] = new int[size];
				          	  	int i=0;
				          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
								while(Token1.hasMoreTokens()){
								  request[i]=Integer.parseInt(Token1.nextToken());
								  i++;
								  //System.out.println("value of i: "+i);	
								}
				          	  	//request[i]=0;
				          	  					          	  	
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
				          	  			
				          	  			compuestoSetSNMPv3TempOID.add(new OID(request));
				          	  			compuestoSetSNMPv3TempTipoDatos.add(tipoDatoReconocido);
				          	  			compuestoSetSNMPv3TempDatos.add(String.valueOf(valor));
				          	  			
				          	  			String contenido = "";
								        for (int pp=0;pp<compuestoSetSNMPv3TempOID.size();pp++){
								          contenido=contenido.concat(compuestoSetSNMPv3TempOID.get(pp)+"; ");	
								        }
								        jtf_snmpv3_setObjs.setText(contenido);
								        
				          	  		}else{
				          	  		  JOptionPane.showMessageDialog(AppGo.this,errorGeneral24,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));				
				          	  		}
		          	  			}else{
		          	  				JOptionPane.showMessageDialog(AppGo.this,errorGeneral25,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));			
		          	  			}
			          	  	  }catch(NumberFormatException nfe){
		          	  	    	JOptionPane.showMessageDialog(AppGo.this,errorGeneral02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
        	  	JOptionPane.showMessageDialog(AppGo.this,errorGeneral03,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
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
//              jl_snmpv3_SetEtiTipo.setEnabled(true);
//              jl_snmpv3_SetEtiSetValor.setEnabled(true);   
              jcb_snmpv3_setModSeg.setEnabled(true);   
//              jl_snmpv3_setModSeg.setEnabled(true);
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
          	    JOptionPane.showMessageDialog(AppGo.this,errorGeneral01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
          	  }else{
          	  	//Tratamiento
          	  	OID = jtf_snmpv3_SetSet.getText();
          	  	
          	  	if (esVacio(IP)){
          	  	  JOptionPane.showMessageDialog(AppGo.this,configParamError01,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));  	
          	  	}else{ 
          	  		
          	  		if ((jtf_snmpv3_SetSetValor.getText()).equals("")){
          	  		  JOptionPane.showMessageDialog(AppGo.this,errorGeneral22,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		
          	  		}else{
          	  			if ((jcb_snmpv3_SetTipo.getSelectedItem()).equals("---------------------------")){
          	  			  JOptionPane.showMessageDialog(AppGo.this,errorGeneral23,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));		  
          	  			}else{
							//Para transformar el OID de string al arreglo para pasarselo al GetNext
			          	  	//System.out.println("Tratando el oid");
			          	  	try{   
				          	  	int digits=0;
				          	  	StringTokenizer Token = new StringTokenizer (OID,".");
								while(Token.hasMoreTokens()){
								  Token.nextToken();
								  digits++;	
								}
				          	  	//System.out.println("el oid tiene "+digits+"digits");
				          	  	int size=digits;
				          	  	//System.out.println("el request va a tener "+size+" digits");
				          	  	int request[] = new int[size];
				          	  	int i=0;
				          	  	StringTokenizer Token1 = new StringTokenizer (OID,".");
								while(Token1.hasMoreTokens()){
								  request[i]=Integer.parseInt(Token1.nextToken());
								  i++;
								  //System.out.println("value of i: "+i);	
								}
				          	  	//request[i]=0;
				          	  	
				          	  	boolean esCompuesto=false;
				          	  	compuestoSetSNMPv3 = new Vector();
				          	  	if((compuestoSetSNMPv3TempOID.size())>=1){
				          	  	   compuestoSetSNMPv3=compuestoSetSNMPv3TempOID;
				          	  	   esCompuesto=true;	
				          	  	   reconocido=true;
				          	  	}else{
				          	  	   compuestoSetSNMPv3.add(new OID(request));
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
					         	  		
					         	  		manager.cambiarIdiomaAMensajes(errorGeneral04,errorGeneral29,errorGeneral30,errorGeneral31);
									    String answer = manager.setv3(IP, String.valueOf(pto), user, claveAut, clavePriv, nivelSeguridad,inten, timeOut, compuestoSetSNMPv3, compuestoSetSNMPv3Valores, metAut, metPriv);
									    
									    compuestoSetSNMPv3.removeAllElements();
		    							compuestoSetSNMPv3TempOID.removeAllElements();
		        	    				compuestoSetSNMPv3TempTipoDatos.removeAllElements();
		        	    				compuestoSetSNMPv3TempDatos.removeAllElements();
								        compuestoSetSNMPv3Valores=null;
								        jtf_snmpv3_setObjs.setText("");
									    
						          	  	//System.out.println("Setv3: "+answer);
						          	  	if (answer.equals(errorGeneral04)){
						          	  	  answer=("Set SNMPv3: ").concat(answer);
						          	  	}else{
						          	  	  answer=("Set SNMPv3: ").concat(answer);	
						          	  	}
						          	  	answer=answer.concat("\n");
						          	  	jta_snmpv3_SetResp.setText((jta_snmpv3_SetResp.getText()).concat(answer));
						          	  	
				          	  		}else{
				          	  		  JOptionPane.showMessageDialog(AppGo.this,errorGeneral24,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));				
				          	  		}
		          	  			}else{
		          	  				JOptionPane.showMessageDialog(AppGo.this,errorGeneral25,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));			
		          	  			}
			          	  	  }catch(NumberFormatException nfe){
		          	  	    	JOptionPane.showMessageDialog(AppGo.this,errorGeneral02,nameOfTheProgram,JOptionPane.ERROR_MESSAGE,(new ImageIcon("images/no.gif")));
		          	  	  	  }
          	  		  }
          	  		}
          	  	}
          	  }
         	}
       	});
		
	    //----------------------------------End de Pantalla de Set---------------------------------


  	//----------------------------End del Manager SNMPv3--------------------------------------
  	
 
//  	  jb_mibtree.setToolTipText("Press to import MIBs.");


      String etiquetaIdioma="";
      for (int yy=0;yy<165; yy++){
		  etiquetaIdioma=etiquetaIdioma.concat(" ");
		}
		etiquetaIdioma=etiquetaIdioma.concat(" Idioma / Language   ");

            //FOR THE COMBO BOX OF ACTION TO BE CARRIED OUT
            // COMMANDS ADMITTED
  	    ConfigureParameters = "Connection Options";
  	    CommandGet = "Get Command";
  	    CommandGetNext = "GetNext Command";
  	    CommandGetBulk = "GetBulk Command";
  	    CommandGetTable = "GetTable";
  	    CommandWalk = "Walk";
  	    CommandSet = "Set Command";

  	    //COMMANDS IN SNMPv3
  	    jl_snmpv3_sel.setText("Action Selected");
  	    jcb_snmpv3_sel.removeAllItems();
  	    jcb_snmpv3_sel.addItem(ConfigureParameters);
  		jcb_snmpv3_sel.addItem(CommandGet);
  		jcb_snmpv3_sel.addItem(CommandGetNext);
  		jcb_snmpv3_sel.addItem(CommandGetBulk);
  		jcb_snmpv3_sel.addItem(CommandGetTable);
  		jcb_snmpv3_sel.addItem(CommandWalk);
  		jcb_snmpv3_sel.addItem(CommandSet);
  		



  	    optionYES = "Yes";
  		optionNO = "No";
  	    jcb_snmpv3_VerUsr.removeAllItems();
  	    jcb_snmpv3_VerUsr.addItem(optionYES);
  	    jcb_snmpv3_VerUsr.addItem(optionNO);
  	    jcb_snmpv3_VerUsr.setSelectedIndex(1);	  	 
  	    //--PARAMETER SCREEN--------------------------------------------------------------------------------------------
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //-- GETNEXT ----
//  	    jp_snmpv3_GetNext.setBorder(BorderFactory.createTitledBorder("GetNext Command"));
//  	    jl_snmpv3_GetNextEtiGetNext.setText("OID");
//  	    jb_snmpv3_getNextAdd.setText("Add");
  	    jb_snmpv3_getNextAdd.setToolTipText("Press to add the OID.");
//  	    jl_snmpv3_getNextObjs.setText("Objects");
//  	    jb_snmpv3_getNextUndo.setText("Undo");
  	    jb_snmpv3_getNextUndo.setToolTipText("Press to erase the last OID added.");
//  	    jb_snmpv3_GetNextGetNext.setText("GetNext");
  	    jb_snmpv3_GetNextGetNext.setToolTipText("Press to get the next value(s).");
//  	    jl_snmpv3_getNextModSeg.setText("Security");
  	    //-- GETNEXT -----
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //-- GETBULK----
//  	    jp_snmpv3_GetBulk.setBorder(BorderFactory.createTitledBorder("GetBulk Command"));
//  	    jl_snmpv3_nonRepe.setText("NonRepeaters");
//  	    jl_snmpv3_maxRep.setText("MaxRepetitions");
  	    jb_snmpv3_GetBulk_add.setText("Add");
  	    jb_snmpv3_GetBulk_add.setToolTipText("Press to add the OID.");
  	    jb_snmpv3_GetBulkUndo.setText("Undo");
  	    jb_snmpv3_GetBulkUndo.setToolTipText("Press to erase the last OID added.");
//  	    jl_snmpv3_getBulkObjs.setText("Objects");
//  	    jl_snmpv3_GetBulkEtiSet.setText("OID");
  	    jb_snmpv3_GetBulkGetBulk.setText("GetBulk");
  	    jb_snmpv3_GetBulkGetBulk.setToolTipText("Press to get the value(s).");
//  	    jl_snmpv3_getBulkModSeg.setText("Security");
  	    //--GETBULK-----
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--GETTABLE--
//  	    jp_snmpv3_getTable.setBorder(BorderFactory.createTitledBorder("GetTable"));
//  	    jl_snmpv3_getTableEtigetTable.setText("OID");
//  	    jb_snmpv3_getTablegetTable.setText("GetTable");
  	    jb_snmpv3_getTablegetTable.setToolTipText("Press to get the table.");
//  	    jl_snmpv3_getTableModSeg.setText("Security");
  	    //--GETTABLE--
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--WALK--
//  	    jp_snmpv3_walk.setBorder(BorderFactory.createTitledBorder("Walk"));
//  	    jl_snmpv3_WalkEtiLimitePregunta.setText("Limit the quantity of variables retrieved");
  	    jcb_snmpv3_WalkEtiLimitePregunta.removeAllItems();
  	    jcb_snmpv3_WalkEtiLimitePregunta.addItem(optionYES);
  	    jcb_snmpv3_WalkEtiLimitePregunta.addItem(optionNO);
  	    jcb_snmpv3_WalkEtiLimitePregunta.setSelectedIndex(1);
//	  	jl_snmpv3_WalkEtiLimite.setText("Quantity");
	  	jtf_snmpv3_WalkEtiLimite.setText(errorGeneral21);
//	  	jl_snmpv3_WalkEti.setText("OID");
	  	jb_snmpv3_Walk.setText("Walk");
	  	jb_snmpv3_Walk.setToolTipText("Press to start the MIB Walk.");
//	  	jl_snmpv3_getWalkModSeg.setText("Security");
  	    //--WALK--
  	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 	    
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	    //--SET--
  	    jb_snmpv3_setAdd.setText("Add");
  	    jb_snmpv3_setAdd.setToolTipText("Press to add the OID.");
  	    jb_snmpv3_setUndo.setText("Undo");
  	    jb_snmpv3_setUndo.setToolTipText("Press to erase the last OID added.");
  	    jb_snmpv3_SetSet.setText("Set");
  	    jb_snmpv3_SetSet.setToolTipText("Press to set the new value(s).");
  	    //--SET--
  	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		

  	  //End de SNMPv3-----------------------------------------------------------------------------------------------------
  	  
        

    }
    
    
    
      //----------------Inicio SNMPv3------------------------------------------------------------------------------------------
		private JLabel jl_snmpv3_sel;
		private JComboBox jcb_snmpv3_sel;
		private JPanel jp_snmpv3_GetNext, jp_snmpv3_GetBulk, jp_snmpv3_walk,jp_snmpv3_getTable;
		///////////////////Pantalla parametros v3\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
		////////////////////////GET
	
		private Vector compuestoGetSNMPv3; //To pass the multiple parameters
    	private Vector compuestoGetSNMPv3Temp; 
		////////////////////////GETNEXT
//		private JScrollPane jsp_snmpv3_GetNextDescrip,jsp_snmpv3_GetNextResp;
//		private JTextArea jta_snmpv3_GetNextDescrip,jta_snmpv3_GetNextResp;
//		private JLabel jl_snmpv3_GetNextEtiGetNext,jl_snmpv3_getNextModSeg,jl_snmpv3_getNextObjs;
//		private JTextField jtf_snmpv3_GetNextGetNext,jtf_snmpv3_getNextObjs;
//		private JButton jb_snmpv3_GetNextGetNext,jb_snmpv3_getNextAdd,jb_snmpv3_getNextUndo;
//	  	private JComboBox jcb_snmpv3_getNextModSeg;	
	  	private Vector compuestoGetNextSNMPv3; //To pass the multiple parameters
        private Vector compuestoGetNextSNMPv3Temp;		
		////////////////////////GETBULK
//		private JScrollPane jsp_snmpv3_GetBulkResp;
//		private JTextArea jta_snmpv3_GetBulkResp;
//		private	JLabel jl_snmpv3_nonRepe, jl_snmpv3_maxRep, jl_snmpv3_GetBulkEtiSet,jl_snmpv3_getBulkModSeg,jl_snmpv3_getBulkObjs; 
//		private JTextField jtf_snmpv3_nonRepe, jtf_snmpv3_maxRep, jtf_snmpv3_GetBulkGetBulk,jtf_snmpv3_getBulkObjs;
//		private JButton jb_snmpv3_GetBulkGetBulk,jb_snmpv3_GetBulk_add,jb_snmpv3_GetBulkUndo;
//		private JComboBox jcb_snmpv3_getBulkModSeg;
		private Vector compuestoGetBulkSNMPv3; //To pass the multiple parameters
    	private Vector compuestoGetBulkSNMPv3Temp;
		//////////////////////////WALK
//		private JLabel jl_snmpv3_WalkEtiLimitePregunta, jl_snmpv3_WalkEtiLimite, jl_snmpv3_WalkEti,jl_snmpv3_getWalkModSeg;
//		private JComboBox jcb_snmpv3_WalkEtiLimitePregunta,jcb_snmpv3_WalkModSeg;
//		private JTextField jtf_snmpv3_WalkEtiLimite, jtf_snmpv3_WalkOID;
//		private JButton jb_snmpv3_Walk;
//		private JScrollPane jsp_snmpv3_WalkResp;
//		private JTextArea jta_snmpv3_WalkResp;
		////////////////////////////SET

		private int jtf_snmpv3_SetSetDigitos;		
		private Vector compuestoSetSNMPv3; //To pass the multiple parameters
		private Vector compuestoSetSNMPv3TempOID;
    	private Vector compuestoSetSNMPv3TempDatos;
    	private Vector compuestoSetSNMPv3TempTipoDatos;
    	private Variable[] compuestoSetSNMPv3Valores; //To pass the multiple parameters

		//////////////PANTALLA DEL GETTABLE
//	    private JLabel jl_snmpv3_getTableEtigetTable;
//		private JTextField jtf_snmpv3_getTablegetTable;
//		private JButton jb_snmpv3_getTablegetTable;
//		private JScrollPane jsp_snmpv3_getTablegetTable;
//		private JLabel jl_snmpv3_getTableModSeg;
//	    private JComboBox jcb_snmpv3_getTableModSeg;
            
            
                	private String tipoDatoReconocido=null;
    	private boolean reconocido=false;
        
        
  //----------------End SNMPv3--------------------------------------------------------------------------------------------- 
      ///////////----FOR THE MIBBLEBROWSER---------------------------------------------------
  
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
    private ArrayList loadedMibsToSearchForNames;
    
    MibLoader loaderMibs = new MibLoader();

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
        loadedMibsToSearchForNames.add(mib);
        
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
        loadedMibsToSearchForNames.add(mib);
        
    }    
    
    
    public void loadMibFolder (String src) throws IOException, MibLoaderException {
            
        File file = new File("mibs/");

        // The MIB file may import other MIBs (often in same dir)
    loaderMibs.addDir(file.getParentFile());

    // Once initialized, MIB loading is straight-forward
    loaderMibs.load(file);
    
        }
    
            public void unloadAllMib() throws IOException, MibLoaderException {

        mb = MibTreeBuilder.getInstance();

		
        // Check for already loaded MIB
        for (int i = 0; i < loadedMibs.size(); i++) {
            //System.out.println(mib.getName());
            mb.unloadMib((String) loadedMibs.get(i));
        }
 
        
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
        System.out.println(currentDir.toString());
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
    	

loadMib("mibs/RFC1213-MIB");
loadMib("mibs/ALL/GDC4S-ASSIGNMENTS-MIB.txt");
loadMib("mibs/ALL/GDC4S-COMMON-NOTIFICATIONS-MIB.txt");
loadMib("mibs/ALL/GDC4S-ENCRYPTION-PRODUCTS-COMMON-MIB.txt");
loadMib("mibs/ALL/GDC4S-ENCRYPTION-PRODUCTS-MIB.txt");
loadMib("mibs/ALL/GDC4S-EXTENSION-MIB.txt");
loadMib("mibs/ALL/GDC4S-FEATURE-MIB.txt");
loadMib("mibs/ALL/GDC4S-VLAN-MIB.txt");
loadMib("mibs/ALL/HAIPE-ASSIGNMENTS-MIB.txt");
loadMib("mibs/ALL/HAIPE-COMPLIANCE-MIB.txt");
loadMib("mibs/ALL/HAIPE-FEATURE-HIERARCHY-MIB.txt");
loadMib("mibs/ALL/HAIPE-KEY-TRANSFER-MIB.txt");
loadMib("mibs/ALL/HAIPE-MANAGEMENT-MIB.txt");
loadMib("mibs/ALL/HAIPE-NETWORKING-DISCOVERY-MIB.txt");
loadMib("mibs/ALL/HAIPE-NETWORKING-MIB.txt");
loadMib("mibs/ALL/HAIPE-TC-MIB.txt");
loadMib("mibs/ALL/HAIPE-TRAFFIC-PROTECTION-MIB.txt");
loadMib("mibs/ALL/HAIPE-V040100-MIB.txt");
loadMib("mibs/ALL/HAIPE-V4-BANDWIDTH-MANAGEMENT-MIB.txt");
loadMib("mibs/ALL/HAIPE-V4-COMPLIANCE-MIB.txt");
loadMib("mibs/ALL/HAIPE-V4-DISCOVERY-MIB.txt");
loadMib("mibs/ALL/HAIPE-V4-DMDK-MIB.txt");
loadMib("mibs/ALL/HAIPE-V4-FEATURE-HIERARCHY-MIB.txt");
loadMib("mibs/ALL/HAIPE-V4-KEY-INFORMATION-MIB.txt");
loadMib("mibs/ALL/HAIPE-V4-KEY-TRANSFER-MIB.txt");
loadMib("mibs/ALL/HAIPE-V4-KMI-MIB.txt");
loadMib("mibs/ALL/HAIPE-V4-MANAGEMENT-MIB.txt");
loadMib("mibs/ALL/HAIPE-V4-NETWORKING-MIB.txt");
loadMib("mibs/ALL/HAIPE-V4-ROHC-MIB.txt");
loadMib("mibs/ALL/HAIPE-V4-TEXTUAL-CONVENTIONS-MIB.txt");
loadMib("mibs/ALL/HAIPE-V4-TRAFFIC-PROTECTION-MIB.txt");
//loadMib("mibs/ALL/HOST-RESOURCES-MIB.txt");
//loadMib("mibs/ALL/IANA-RTPROTO-MIB.txt");
//loadMib("mibs/ALL/IANA-TUNNELTYPE-MIB.txt");
//loadMib("mibs/ALL/IANAifType-MIB.txt");
//loadMib("mibs/ALL/IF-MIB.txt");
//loadMib("mibs/ALL/INET-ADDRESS-MIB.txt");
//loadMib("mibs/ALL/IP-FORWARD-MIB.txt");
//loadMib("mibs/ALL/IP-MIB.txt");
//loadMib("mibs/ALL/IPV6-FLOW-LABEL-MIB.txt");
//loadMib("mibs/ALL/MAU-MIB.txt");
//loadMib("mibs/ALL/MGMD-STD-MIB.txt");
//loadMib("mibs/ALL/NETWORKENCRYPTOR-ENTERPRISE-MIB.txt");
//loadMib("mibs/ALL/NOTIFICATION-LOG-MIB.txt");
//loadMib("mibs/ALL/SNMP-FRAMEWORK-MIB.txt");
//loadMib("mibs/ALL/SNMP-MPD-MIB.txt");
//loadMib("mibs/ALL/SNMP-NOTIFICATION-MIB.txt");
//loadMib("mibs/ALL/SNMP-TARGET-MIB.txt");
//loadMib("mibs/ALL/SNMP-USER-BASED-SM-MIB.txt");
//loadMib("mibs/ALL/SNMP-USM-AES-MIB.txt");
//loadMib("mibs/ALL/SNMP-VIEW-BASED-ACM-MIB.txt");
//loadMib("mibs/ALL/SNMPv2-CONF.txt");
loadMib("mibs/ALL/SNMPv2-MIB.txt");
//loadMib("mibs/ALL/SNMPv2-SMI.txt");
//loadMib("mibs/ALL/SNMPv2-TC.txt");
//loadMib("mibs/ALL/TACLANE-MICRO-COMMON-MIB.txt");
//loadMib("mibs/ALL/TACLANE-PRODUCTS-REGISTRATION-MIB.txt");
//loadMib("mibs/ALL/TCP-MIB.txt");
//loadMib("mibs/ALL/TRANSPORT-ADDRESS-MIB.txt");
//loadMib("mibs/ALL/UDP-MIB.txt");

        refreshTree();

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

            //Para SNMPv3            ---------------------------------------------------------------  666
            jta_snmpv3_getDescrip.setText("");
            jtf_snmpv3_getGet.setText("");
            jtf_snmpv3_GetNextGetNext.setText("");
            jtf_snmpv3_GetBulkGetBulk.setText("");
            jtf_snmpv3_SetSet.setText("");
            jtf_snmpv3_WalkOID.setText("");
            //                       ---------------------------------------------------------------  666            
                        
            
        } else {

        	if (node.getSnmpObjectType()!=null){
        	  //System.out.println("invento: "+node.getSnmpObjectType().getSyntax().getName());	
                // To recognize the data type of the variable --------------------------------------- ------------------------
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
        	// End of the recognition of the data type of the variable -------------------------------------- -----------------
        	// To recognize the type of access of the variable, if it is read-only or read-write -----------------
        	int lugar =node.getDescription().indexOf("Access:");
			if ((!(node.getDescription().equals("")))&&(lugar>0)){
			  //System.out.println("RESPUESTA DE CANWRITE: "+node.getSnmpObjectType().getAccess().canWrite());
        	  if (node.getSnmpObjectType().getAccess().canWrite()){
        	    //Para SNMPv3  -------------------------------------------------------------------------------  666
        	    jb_snmpv3_SetSet.setEnabled(true);
        	    jtf_snmpv3_SetSetValor.setEnabled(true);
        	    jcb_snmpv3_SetTipo.setEnabled(true);     
                    jcb_snmpv3_setModSeg.setEnabled(true);
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
        	  
        	  	//Para SNMPv3--------------------------------------------------------  666
				jb_snmpv3_SetSet.setEnabled(false);
		        jtf_snmpv3_SetSetValor.setEnabled(false); 	 
		        jtf_snmpv3_SetSetValor.setText("");
		        jcb_snmpv3_setModSeg.setEnabled(false);
        	    jcb_snmpv3_SetTipo.setEnabled(false);    
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
   	   
        	  	
        	  //Para SNMPv3                     ------------------------------------- 666
	    	  	jb_snmpv3_SetSet.setEnabled(false);
		        jtf_snmpv3_SetSetValor.setEnabled(false); 	 
		        jtf_snmpv3_SetSetValor.setText("");
		        jcb_snmpv3_setModSeg.setEnabled(false);
//		        jl_snmpv3_setModSeg.setEnabled(false);
	    	    jcb_snmpv3_SetTipo.setEnabled(false);    
//	    	    jl_snmpv3_SetEtiTipo.setEnabled(false); 
//	    	    jl_snmpv3_SetEtiSetValor.setEnabled(false);   	
        	  	jcb_snmpv3_SetTipo.setSelectedIndex(0);
				jb_snmpv3_setAdd.setEnabled(false);    //   ***
				jb_snmpv3_setUndo.setEnabled(false);    //   ***        	       	
        	  	 	    	    	
                //                                ------------------------------------- 666
  
			}
            // End of the method to find the access type of the variable ------------------------------------ ------------

//
            //Para SNMPv3   -------------------------------------------------------------------- 666
            jta_snmpv3_getDescrip.setText(("").concat(node.getDescription()));
            jta_snmpv3_GetNextDescrip.setText(("").concat(node.getDescription()));
            jta_snmpv3_SetDescrip.setText(("").concat(node.getDescription()));
            //              -------------------------------------------------------------------- 666
                        
            if ((node.getOid()).equals("")){//Aqui es necesario colocar todos los setText
              //Para SNMPv3    ----------------------------------------------666
              jtf_snmpv3_getGet.setText("");
              jtf_snmpv3_GetNextGetNext.setText("");
              jtf_snmpv3_SetSet.setText("");
              jtf_snmpv3_GetBulkGetBulk.setText("");
              jtf_snmpv3_WalkOID.setText("");
              jtf_snmpv3_getTablegetTable.setText("");
              //                ---------------------------------------------666

            }else{
              //Para SNMPv3    -------------------------------------------------------------------- 666  
              jtf_snmpv3_getGet.setText(((".").concat(node.getOid())).concat(".0"));
              jtf_snmpv3_GetNextGetNext.setText((".").concat(node.getOid()));
              jtf_snmpv3_GetBulkGetBulk.setText((".").concat(node.getOid()));
              jtf_snmpv3_SetSet.setText(((".").concat(node.getOid())).concat(".0"));
              jtf_snmpv3_WalkOID.setText((".").concat(node.getOid()));
              jtf_snmpv3_getTablegetTable.setText((".").concat(node.getOid()));
              //               -------------------------------------------------------------------- 666                

            }
        }
    }



        // to see if the parameter is a number, if it is it returns true but false
	public boolean esNumero(String parametro){
	  boolean answer = true;
	  if(parametro.equals("")){
	    answer=false;
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
    	  if (j == TAMabc){ answer=false;}
		}
	  }
	  return answer;
	}
	
	//para validar que el parametro no sea vacio, si lo es retorna verdad sino falso
	public boolean esVacio(String parametro){
	  boolean answer = false;	
	  if(parametro.equals("")){
	  	answer=true;	
	  }
	  return answer;	
	}
	
	//Para validar que un string es una direccion ip valida
	public boolean ipValida(String IP){
      boolean answer = true;
      try{
        InetAddress destino = InetAddress.getByName(IP);
      }catch(Exception pp){answer=false;}
      return answer;
    }
  
    //para validar que el parametro sea un oid
	public boolean esOID(String parametro){
	  boolean answer = true;
	  OID pruebaOID;
	  try{
	  	pruebaOID = new OID(parametro);
	  }catch(Exception e3){answer = false;}
	  return answer;	
	}
  
  
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
				
                            }	
  }
    private void unzipfiles() {
          //Open the file
          
  
          
        try {
            InputStream input = getClass().getResourceAsStream("mibs.zip"); 
                File tempfile = File.createTempFile("tempfile", ".zip");
                        OutputStream out = new FileOutputStream(tempfile);
            int read;
            byte[] bytes = new byte[1024];

            while ((read = input.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            tempfile.deleteOnExit(); 
            
            ZipFile zipFile = new ZipFile(tempfile);
            Enumeration<?> enu = zipFile.entries();
            while (enu.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) enu.nextElement();

                String name = zipEntry.getName();
                long size = zipEntry.getSize();
                long compressedSize = zipEntry.getCompressedSize();
                System.out.printf("name: %-20s | size: %6d | compressed size: %6d\n", 
                        name, size, compressedSize);

                // Do we need to create a directory ?
                File file = new File(name);
                if (name.endsWith("/")) {
                    file.mkdirs();
                    continue;
                }

                File parent = file.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }

                // Extract the file
                InputStream is = zipFile.getInputStream(zipEntry);
                FileOutputStream fos = new FileOutputStream(file);
                bytes = new byte[1024];
                int length;
                while ((length = is.read(bytes)) >= 0) {
                    fos.write(bytes, 0, length);
                }
                is.close();
                fos.close();

            }
            zipFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
       private ArrayList getSessionList() throws FileNotFoundException, IOException, URISyntaxException
    {
        String strSessionList;
        DefaultListModel listModel = new DefaultListModel();
        ArrayList arrSessionList = new ArrayList();

            strSessionList = strSessionListDefault;

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
        jSplitPane1 = new javax.swing.JSplitPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jp_mibtree = new javax.swing.JPanel();
        jsp_mibtree = new javax.swing.JScrollPane();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jTextField4 = new javax.swing.JTextField();
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
        jb_snmpv3_getNextUndo = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jb_snmpv3_GetNextGetNext = new javax.swing.JButton();
        jtf_snmpv3_getNextObjs = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jcb_snmpv3_getNextModSeg = new javax.swing.JComboBox<>();
        jsp_snmpv3_GetNextDescrip = new javax.swing.JScrollPane();
        jta_snmpv3_GetNextDescrip = new javax.swing.JTextArea();
        jsp_snmpv3_GetNextResp = new javax.swing.JScrollPane();
        jta_snmpv3_GetNextResp = new javax.swing.JTextArea();
        jLabel21 = new javax.swing.JLabel();
        jtf_snmpv3_GetNextGetNext = new javax.swing.JTextField();
        jb_snmpv3_getNextAdd = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jtf_snmpv3_GetBulkGetBulk = new javax.swing.JTextField();
        jb_snmpv3_GetBulk_add = new javax.swing.JButton();
        jb_snmpv3_GetBulkUndo = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jcb_snmpv3_getBulkModSeg = new javax.swing.JComboBox<>();
        jb_snmpv3_GetBulkGetBulk = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jtf_snmpv3_nonRepe = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jtf_snmpv3_maxRep = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jtf_snmpv3_getBulkObjs = new javax.swing.JTextField();
        jsp_snmpv3_GetBulkResp = new javax.swing.JScrollPane();
        jta_snmpv3_GetBulkResp = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jtf_snmpv3_getTablegetTable = new javax.swing.JTextField();
        jcb_snmpv3_getTableModSeg = new javax.swing.JComboBox<>();
        jb_snmpv3_getTablegetTable = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jsp_snmpv3_getTablegetTable = new javax.swing.JScrollPane();
        jta_snmpv3_getTablegetTable = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jl_snmpv3_WalkEtiLimitePregunta = new javax.swing.JLabel();
        jcb_snmpv3_WalkEtiLimitePregunta = new javax.swing.JComboBox<>();
        jl_snmpv3_WalkEtiLimite = new javax.swing.JLabel();
        jtf_snmpv3_WalkEtiLimite = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jcb_snmpv3_WalkModSeg = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        jtf_snmpv3_WalkOID = new javax.swing.JTextField();
        jsp_snmpv3_WalkResp = new javax.swing.JScrollPane();
        jta_snmpv3_WalkResp = new javax.swing.JTextArea();
        jb_snmpv3_Walk = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jtf_snmpv3_SetSet = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jcb_snmpv3_SetTipo = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jtf_snmpv3_SetSetValor = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jcb_snmpv3_setModSeg = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jtf_snmpv3_setObjs = new javax.swing.JTextField();
        jb_snmpv3_setAdd = new javax.swing.JButton();
        jb_snmpv3_setUndo = new javax.swing.JButton();
        jb_snmpv3_SetSet = new javax.swing.JButton();
        jsp_snmpv3_SetDescrip = new javax.swing.JScrollPane();
        jta_snmpv3_SetDescrip = new javax.swing.JTextArea();
        jsp_snmpv3_SetResp = new javax.swing.JScrollPane();
        jta_snmpv3_SetResp = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaLog = new javax.swing.JTextArea();
        jmenubar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        jmi_outbound = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        MenuItemImportMIB = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        jmi_helpHelp = new javax.swing.JMenuItem();
        jmi_about = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Puppeteer");

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

        jtf_snmpv3_IP.setText("192.168.0.24");
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
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
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

        jSplitPane1.setDividerLocation(300);

        javax.swing.GroupLayout jp_mibtreeLayout = new javax.swing.GroupLayout(jp_mibtree);
        jp_mibtree.setLayout(jp_mibtreeLayout);
        jp_mibtreeLayout.setHorizontalGroup(
            jp_mibtreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jsp_mibtree, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
        );
        jp_mibtreeLayout.setVerticalGroup(
            jp_mibtreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jsp_mibtree, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
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
                    .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("MIB List", jPanel10);

        jSplitPane1.setLeftComponent(jTabbedPane2);

        jta_snmpv3_getDescrip.setColumns(20);
        jta_snmpv3_getDescrip.setRows(5);
        jsp_snmpv3_getDescrip.setViewportView(jta_snmpv3_getDescrip);

        jLabel1.setText("OID:");

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
                    .addComponent(jsp_snmpv3_getDescrip)
                    .addComponent(jsp_snmpv3_getResp)
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
                        .addComponent(jcb_snmpv3_getModSeg, 0, 125, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtf_snmpv3_getObjs, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jb_snmpv3_getGet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsp_snmpv3_getDescrip, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
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
                .addComponent(jsp_snmpv3_getResp, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Get", jPanel1);

        jb_snmpv3_getNextUndo.setText("Undo");
        jb_snmpv3_getNextUndo.setToolTipText("Remove the last OID added.");

        jLabel19.setText("Objects:");

        jb_snmpv3_GetNextGetNext.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jb_snmpv3_GetNextGetNext.setText("GetNext!");
        jb_snmpv3_GetNextGetNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jb_snmpv3_GetNextGetNextMousePressed(evt);
            }
        });
        jb_snmpv3_GetNextGetNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_snmpv3_GetNextGetNextActionPerformed(evt);
            }
        });

        jtf_snmpv3_getNextObjs.setEditable(false);

        jLabel20.setText("Security:");

        jcb_snmpv3_getNextModSeg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AUTH_PRIV", "AUTH_NOPRIV", "NOAUTH_NOPRIV" }));

        jta_snmpv3_GetNextDescrip.setColumns(20);
        jta_snmpv3_GetNextDescrip.setRows(5);
        jsp_snmpv3_GetNextDescrip.setViewportView(jta_snmpv3_GetNextDescrip);

        jta_snmpv3_GetNextResp.setColumns(20);
        jta_snmpv3_GetNextResp.setRows(5);
        jsp_snmpv3_GetNextResp.setViewportView(jta_snmpv3_GetNextResp);

        jLabel21.setText("OID:");

        jb_snmpv3_getNextAdd.setText("Add");
        jb_snmpv3_getNextAdd.setToolTipText("Add the OID to the Objects list.");
        jb_snmpv3_getNextAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_snmpv3_getNextAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsp_snmpv3_GetNextDescrip)
                    .addComponent(jsp_snmpv3_GetNextResp)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtf_snmpv3_getNextObjs, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jb_snmpv3_GetNextGetNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtf_snmpv3_GetNextGetNext, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jb_snmpv3_getNextAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jb_snmpv3_getNextUndo)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcb_snmpv3_getNextModSeg, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsp_snmpv3_GetNextDescrip, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jb_snmpv3_getNextAdd)
                        .addComponent(jb_snmpv3_getNextUndo)
                        .addComponent(jLabel20)
                        .addComponent(jcb_snmpv3_getNextModSeg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jtf_snmpv3_GetNextGetNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jb_snmpv3_GetNextGetNext)
                    .addComponent(jtf_snmpv3_getNextObjs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsp_snmpv3_GetNextResp, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("GetNext", jPanel2);

        jLabel22.setText("OID:");

        jb_snmpv3_GetBulk_add.setText("Add");
        jb_snmpv3_GetBulk_add.setToolTipText("Add the OID to the Objects list.");
        jb_snmpv3_GetBulk_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_snmpv3_GetBulk_addActionPerformed(evt);
            }
        });

        jb_snmpv3_GetBulkUndo.setText("Undo");
        jb_snmpv3_GetBulkUndo.setToolTipText("Remove the last OID added.");

        jLabel23.setText("Security:");

        jcb_snmpv3_getBulkModSeg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AUTH_PRIV", "AUTH_NOPRIV", "NOAUTH_NOPRIV" }));

        jb_snmpv3_GetBulkGetBulk.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jb_snmpv3_GetBulkGetBulk.setText("GetBulk!");
        jb_snmpv3_GetBulkGetBulk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jb_snmpv3_GetBulkGetBulkMousePressed(evt);
            }
        });
        jb_snmpv3_GetBulkGetBulk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_snmpv3_GetBulkGetBulkActionPerformed(evt);
            }
        });

        jLabel24.setText("Non-Repeaters:");

        jLabel25.setText("Max-Repetitions:");

        jLabel26.setText("Objects:");

        jta_snmpv3_GetBulkResp.setColumns(20);
        jta_snmpv3_GetBulkResp.setRows(5);
        jsp_snmpv3_GetBulkResp.setViewportView(jta_snmpv3_GetBulkResp);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsp_snmpv3_GetBulkResp)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtf_snmpv3_GetBulkGetBulk)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcb_snmpv3_getBulkModSeg, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtf_snmpv3_nonRepe, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtf_snmpv3_maxRep, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jb_snmpv3_GetBulk_add)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jb_snmpv3_GetBulkUndo)
                        .addGap(18, 18, 18)
                        .addComponent(jb_snmpv3_GetBulkGetBulk, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtf_snmpv3_getBulkObjs)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jtf_snmpv3_GetBulkGetBulk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_snmpv3_getBulkModSeg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jb_snmpv3_GetBulkUndo)
                    .addComponent(jb_snmpv3_GetBulk_add)
                    .addComponent(jLabel24)
                    .addComponent(jtf_snmpv3_nonRepe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(jtf_snmpv3_maxRep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jb_snmpv3_GetBulkGetBulk))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jtf_snmpv3_getBulkObjs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jsp_snmpv3_GetBulkResp, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("GetBulk", jPanel3);

        jLabel27.setText("OID:");

        jcb_snmpv3_getTableModSeg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AUTH_PRIV", "AUTH_NOPRIV", "NOAUTH_NOPRIV" }));

        jb_snmpv3_getTablegetTable.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jb_snmpv3_getTablegetTable.setText("GetTable!");
        jb_snmpv3_getTablegetTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jb_snmpv3_getTablegetTableMousePressed(evt);
            }
        });
        jb_snmpv3_getTablegetTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_snmpv3_getTablegetTableActionPerformed(evt);
            }
        });

        jLabel28.setText("Security:");

        jta_snmpv3_getTablegetTable.setColumns(20);
        jta_snmpv3_getTablegetTable.setRows(5);
        jsp_snmpv3_getTablegetTable.setViewportView(jta_snmpv3_getTablegetTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsp_snmpv3_getTablegetTable)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtf_snmpv3_getTablegetTable, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcb_snmpv3_getTableModSeg, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jb_snmpv3_getTablegetTable)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jtf_snmpv3_getTablegetTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(jcb_snmpv3_getTableModSeg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jb_snmpv3_getTablegetTable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jsp_snmpv3_getTablegetTable, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("GetTable", jPanel4);

        jl_snmpv3_WalkEtiLimitePregunta.setText("Limit the quantity of variables received:");

        jcb_snmpv3_WalkEtiLimitePregunta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No", "Yes" }));

        jl_snmpv3_WalkEtiLimite.setText("Quantity:");

        jLabel31.setText("Security:");

        jcb_snmpv3_WalkModSeg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AUTH_PRIV", "AUTH_NOPRIV", "NOAUTH_NOPRIV" }));

        jLabel29.setText("OID:");

        jta_snmpv3_WalkResp.setColumns(20);
        jta_snmpv3_WalkResp.setRows(5);
        jsp_snmpv3_WalkResp.setViewportView(jta_snmpv3_WalkResp);

        jb_snmpv3_Walk.setText("jButton1");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsp_snmpv3_WalkResp)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jl_snmpv3_WalkEtiLimitePregunta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcb_snmpv3_WalkEtiLimitePregunta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jl_snmpv3_WalkEtiLimite)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtf_snmpv3_WalkEtiLimite, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcb_snmpv3_WalkModSeg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtf_snmpv3_WalkOID, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jb_snmpv3_Walk, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_snmpv3_WalkEtiLimitePregunta)
                    .addComponent(jcb_snmpv3_WalkEtiLimitePregunta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_snmpv3_WalkEtiLimite)
                    .addComponent(jtf_snmpv3_WalkEtiLimite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(jcb_snmpv3_WalkModSeg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jtf_snmpv3_WalkOID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jb_snmpv3_Walk))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jsp_snmpv3_WalkResp, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Walk", jPanel5);

        jLabel14.setText("OID:");

        jLabel15.setText("Data Type:");

        jcb_snmpv3_SetTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--------------------------------", "INTEGER", "OCTET STRING", "OBJECT IDENTIFIER", "IpAddress", "Counter", "Counter64", "Gauge", "TimeTicks", "Opaque" }));

        jLabel16.setText("Value:");

        jLabel17.setText("Security:");

        jcb_snmpv3_setModSeg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AUTH_PRIV", "AUTH_NOPRIV", "NOAUTH_NOPRIV" }));

        jLabel18.setText("Objects:");

        jb_snmpv3_setAdd.setText("Add");

        jb_snmpv3_setUndo.setText("Undo");

        jb_snmpv3_SetSet.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jb_snmpv3_SetSet.setText("SET!");
        jb_snmpv3_SetSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_snmpv3_SetSetActionPerformed(evt);
            }
        });

        jta_snmpv3_SetDescrip.setColumns(20);
        jta_snmpv3_SetDescrip.setRows(5);
        jsp_snmpv3_SetDescrip.setViewportView(jta_snmpv3_SetDescrip);

        jta_snmpv3_SetResp.setColumns(20);
        jta_snmpv3_SetResp.setRows(5);
        jsp_snmpv3_SetResp.setViewportView(jta_snmpv3_SetResp);

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
                        .addComponent(jtf_snmpv3_SetSet, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcb_snmpv3_SetTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtf_snmpv3_SetSetValor, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcb_snmpv3_setModSeg, 0, 173, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtf_snmpv3_setObjs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jb_snmpv3_setAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jb_snmpv3_setUndo)
                        .addGap(18, 18, 18)
                        .addComponent(jb_snmpv3_SetSet, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsp_snmpv3_SetDescrip, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jtf_snmpv3_SetSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jcb_snmpv3_SetTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jtf_snmpv3_SetSetValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jcb_snmpv3_setModSeg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jtf_snmpv3_setObjs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jb_snmpv3_setAdd)
                    .addComponent(jb_snmpv3_setUndo)
                    .addComponent(jb_snmpv3_SetSet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jsp_snmpv3_SetResp, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Set", jPanel6);

        jTextAreaLog.setColumns(20);
        jTextAreaLog.setRows(5);
        jScrollPane2.setViewportView(jTextAreaLog);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Log", jPanel7);

        jSplitPane1.setRightComponent(jTabbedPane1);

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

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
        editMenu.setText("MIBs");

        MenuItemImportMIB.setMnemonic('t');
        MenuItemImportMIB.setText("Import MIB");
        editMenu.add(MenuItemImportMIB);

        copyMenuItem.setMnemonic('y');
        copyMenuItem.setText("Unload All MIBs");
        copyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(copyMenuItem);

        jMenu1.setText("Presets");

        jMenuItem1.setText("3.5");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("4.1");
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("4.1v2");
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("4.1v5");
        jMenu1.add(jMenuItem4);

        jMenuItem5.setText("4.1v6");
        jMenu1.add(jMenuItem5);

        jMenuItem6.setText("4.1v7");
        jMenu1.add(jMenuItem6);

        jMenuItem7.setText("4.2v1");
        jMenu1.add(jMenuItem7);

        editMenu.add(jMenu1);

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
            .addComponent(jSplitPane1)
            .addComponent(jPanelTopBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelTopBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSplitPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmi_outboundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_outboundActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jmi_outboundActionPerformed

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

    private void jb_snmpv3_SetSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_snmpv3_SetSetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_snmpv3_SetSetActionPerformed

    private void jb_snmpv3_GetNextGetNextMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jb_snmpv3_GetNextGetNextMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_snmpv3_GetNextGetNextMousePressed

    private void jb_snmpv3_getNextAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_snmpv3_getNextAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_snmpv3_getNextAddActionPerformed

    private void jb_snmpv3_GetNextGetNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_snmpv3_GetNextGetNextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_snmpv3_GetNextGetNextActionPerformed

    private void jb_snmpv3_GetBulk_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_snmpv3_GetBulk_addActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_snmpv3_GetBulk_addActionPerformed

    private void jb_snmpv3_GetBulkGetBulkMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jb_snmpv3_GetBulkGetBulkMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_snmpv3_GetBulkGetBulkMousePressed

    private void jb_snmpv3_GetBulkGetBulkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_snmpv3_GetBulkGetBulkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_snmpv3_GetBulkGetBulkActionPerformed

    private void jb_snmpv3_getTablegetTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jb_snmpv3_getTablegetTableMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_snmpv3_getTablegetTableMousePressed

    private void jb_snmpv3_getTablegetTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_snmpv3_getTablegetTableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jb_snmpv3_getTablegetTableActionPerformed

    private void copyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyMenuItemActionPerformed
    try {
        unloadAllMib();
    } catch (IOException | MibLoaderException ex) {
        Logger.getLogger(AppGo.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_copyMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {


        
        
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
    private javax.swing.JMenuItem MenuItemImportMIB;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jListSessions;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
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
    private javax.swing.JPanel jPanelTopBar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextArea jTextAreaLog;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextFieldFilter;
    private javax.swing.JButton jb_snmpv3_GetBulkGetBulk;
    private javax.swing.JButton jb_snmpv3_GetBulkUndo;
    private javax.swing.JButton jb_snmpv3_GetBulk_add;
    private javax.swing.JButton jb_snmpv3_GetNextGetNext;
    private javax.swing.JButton jb_snmpv3_SetSet;
    private javax.swing.JButton jb_snmpv3_Walk;
    private javax.swing.JButton jb_snmpv3_getAdd;
    private javax.swing.JButton jb_snmpv3_getGet;
    private javax.swing.JButton jb_snmpv3_getNextAdd;
    private javax.swing.JButton jb_snmpv3_getNextUndo;
    private javax.swing.JButton jb_snmpv3_getTablegetTable;
    private javax.swing.JButton jb_snmpv3_getUndo;
    private javax.swing.JButton jb_snmpv3_setAdd;
    private javax.swing.JButton jb_snmpv3_setUndo;
    private javax.swing.JComboBox<String> jcb_snmpv3_SetTipo;
    private javax.swing.JComboBox<String> jcb_snmpv3_VerUsr;
    private javax.swing.JComboBox<String> jcb_snmpv3_WalkEtiLimitePregunta;
    private javax.swing.JComboBox<String> jcb_snmpv3_WalkModSeg;
    private javax.swing.JComboBox<String> jcb_snmpv3_getBulkModSeg;
    private javax.swing.JComboBox<String> jcb_snmpv3_getModSeg;
    private javax.swing.JComboBox<String> jcb_snmpv3_getNextModSeg;
    private javax.swing.JComboBox<String> jcb_snmpv3_getTableModSeg;
    private javax.swing.JComboBox<String> jcb_snmpv3_metAut;
    private javax.swing.JComboBox<String> jcb_snmpv3_metPriv;
    private javax.swing.JComboBox<String> jcb_snmpv3_setModSeg;
    private javax.swing.JLabel jl_snmpv3_WalkEtiLimite;
    private javax.swing.JLabel jl_snmpv3_WalkEtiLimitePregunta;
    private javax.swing.JMenuBar jmenubar;
    private javax.swing.JMenuItem jmi_about;
    private javax.swing.JMenuItem jmi_helpHelp;
    private javax.swing.JMenuItem jmi_outbound;
    private javax.swing.JPanel jp_mibtree;
    private javax.swing.JPasswordField jpf_snmpv3_Aut;
    private javax.swing.JPasswordField jpf_snmpv3_Priv;
    private javax.swing.JPasswordField jpf_snmpv3_User;
    private javax.swing.JScrollPane jsp_mibtree;
    private javax.swing.JScrollPane jsp_snmpv3_GetBulkResp;
    private javax.swing.JScrollPane jsp_snmpv3_GetNextDescrip;
    private javax.swing.JScrollPane jsp_snmpv3_GetNextResp;
    private javax.swing.JScrollPane jsp_snmpv3_SetDescrip;
    private javax.swing.JScrollPane jsp_snmpv3_SetResp;
    private javax.swing.JScrollPane jsp_snmpv3_WalkResp;
    private javax.swing.JScrollPane jsp_snmpv3_getDescrip;
    private javax.swing.JScrollPane jsp_snmpv3_getResp;
    private javax.swing.JScrollPane jsp_snmpv3_getTablegetTable;
    private javax.swing.JTextArea jta_snmpv3_GetBulkResp;
    private javax.swing.JTextArea jta_snmpv3_GetNextDescrip;
    private javax.swing.JTextArea jta_snmpv3_GetNextResp;
    private javax.swing.JTextArea jta_snmpv3_SetDescrip;
    private javax.swing.JTextArea jta_snmpv3_SetResp;
    private javax.swing.JTextArea jta_snmpv3_WalkResp;
    private javax.swing.JTextArea jta_snmpv3_getDescrip;
    private javax.swing.JTextArea jta_snmpv3_getResp;
    private javax.swing.JTextArea jta_snmpv3_getTablegetTable;
    private javax.swing.JTextField jtf_snmpv3_GetBulkGetBulk;
    private javax.swing.JTextField jtf_snmpv3_GetNextGetNext;
    private javax.swing.JTextField jtf_snmpv3_IP;
    private javax.swing.JTextField jtf_snmpv3_SetSet;
    private javax.swing.JTextField jtf_snmpv3_SetSetValor;
    private javax.swing.JTextField jtf_snmpv3_WalkEtiLimite;
    private javax.swing.JTextField jtf_snmpv3_WalkOID;
    private javax.swing.JTextField jtf_snmpv3_getBulkObjs;
    private javax.swing.JTextField jtf_snmpv3_getGet;
    private javax.swing.JTextField jtf_snmpv3_getNextObjs;
    private javax.swing.JTextField jtf_snmpv3_getObjs;
    private javax.swing.JTextField jtf_snmpv3_getTablegetTable;
    private javax.swing.JTextField jtf_snmpv3_maxRep;
    private javax.swing.JTextField jtf_snmpv3_nonRepe;
    private javax.swing.JTextField jtf_snmpv3_pto;
    private javax.swing.JTextField jtf_snmpv3_setObjs;
    private javax.swing.JTextField jtf_snmv3_inten;
    private javax.swing.JTextField jtt_snmpv3_timeOut;
    // End of variables declaration//GEN-END:variables

}
