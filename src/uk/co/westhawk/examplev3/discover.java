// NAME
//      $RCSfile: discover.java,v $
// DESCRIPTION
//      [given below in javadoc format]
// DELTA
//      $Revision: 1.4 $
// CREATED
//      $Date: 2008/12/12 14:55:51 $
// COPYRIGHT
//      Westhawk Ltd
// TO DO
//

/*
 * Copyright (C) 1996 - 2006 by Westhawk Ltd
 *
 * Permission to use, copy, modify, and distribute this software
 * for any purpose and without fee is hereby granted, provided
 * that the above copyright notices appear in all copies and that
 * both the copyright notice and this permission notice appear in
 * supporting documentation.
 * This software is provided "as is" without express or implied
 * warranty.
 * author <a href="mailto:snmp@westhawk.co.uk">Tim Panton</a>
 */
 
package uk.co.westhawk.examplev3;

import java.util.*;

import uk.co.westhawk.snmp.stack.*;    
import uk.co.westhawk.snmp.pdu.*;    

/**
 * <p>
 * The host, port, oid and community name can be configured in the 
 * properties file. 
 * </p>
 *
 * @see uk.co.westhawk.snmp.stack.MultiResponsePdu
 * @see Util
 *
 * @since 4_14
 * @author <a href="mailto:snmp@westhawk.co.uk">Birgit Arkesteijn</a>
 * @version $Revision: 1.4 $ $Date: 2008/12/12 14:55:51 $
 */
public class discover implements Observer
{
    private static final String     version_id =
        "@(#)$Id: discover.java,v 1.4 2008/12/12 14:55:51 tpanton Exp $ Copyright Westhawk Ltd";

    public final static String sysUpTime = "1.3.6.1.2.1.1.3.0";

    private SnmpContextv3 context;
    private Util util;
    private String oid;

/**
 * Constructor.
 *
 * @param propertiesFilename The name of the properties file. Can be
 * null.
 */
public discover(String propertiesFilename)
{
    util = new Util(propertiesFilename, this.getClass().getName());
}

public void init () 
{
    //AsnObject.setDebug(7);
    String host = util.getHost();
    String bindAddr = util.getBindAddress();
    int port = util.getPort(SnmpContextBasisFace.DEFAULT_PORT);
    String socketType = util.getSocketType();
    oid = util.getOid(sysUpTime);
    byte[] engineId = util.getContextEngineId();
    String contextName = util.getContextName();
    String userName = util.getUserName();
    int auth = util.getUseAuth();
    String authPassw = util.getUserAuthPassword();
    int aproto = util.getAuthProcotol();
    int priv = util.getUsePriv();
    String privPassw = util.getUserPrivPassword();
    int pproto = util.getPrivProcotol();

    try 
    {
        context = new SnmpContextv3(host, port, bindAddr, socketType);
        context.setUserName(userName);
        context.setUseAuthentication((auth==1));
        context.setUserAuthenticationPassword(authPassw);
        context.setAuthenticationProtocol(aproto);
        context.setContextEngineId(engineId);
        context.setContextName(contextName);
        context.setUsePrivacy((priv == 1));
        context.setUserPrivacyPassword(privPassw);
        context.setPrivacyProtocol(pproto);
        System.out.println("init(): " + context.toString());

        MultiResponsePdu pdu = new MultiResponsePdu(context);
        pdu.addObserver(this);
        pdu.addOid(oid);
        pdu.send();
    }
    catch (java.io.IOException exc)
    {
        System.out.println("init(): IOException " + exc.getMessage());
        context.destroy();
        exc.printStackTrace(System.out);
        System.exit(1);
    }
    catch (uk.co.westhawk.snmp.stack.PduException exc)
    {
        System.out.println("init(): PduException " + exc.getMessage());
        context.destroy();
        exc.printStackTrace(System.out);
        System.exit(1);
    }
    catch (Exception exc)
    {
        System.out.println("init(): Exception " + exc.getMessage());
        context.destroy();
        exc.printStackTrace(System.out);
        System.exit(1);
    }
}


/**
 * Implementing the Observer interface. Receiving the response from 
 * the Pdu.
 *
 * @param obs the MultiResponsePdu variable
 * @param ov the agent that replied
 *
 * @see uk.co.westhawk.snmp.stack.MultiResponsePdu
 * @see uk.co.westhawk.snmp.stack.varbind
 */
public void update(Observable obs, Object ov)
{
    MultiResponsePdu pdu = (MultiResponsePdu) obs;
    if (ov instanceof AgentException)
    {
        if (pdu.isTimedOut())
        {
            // this PDU always times out
            System.out.println();
            //System.out.println(pdu.toString());
            System.out.println(TimeWindow.getCurrent().toString());
            System.out.println();


            // Try it again. The broadcast might produce so many responses,
            // that some will be dropped. Next time the result might be
            // different.
            try 
            {
                // sleep 7sec
                Thread.sleep(7000);

                pdu = new MultiResponsePdu(context);
                pdu.addObserver(this);
                pdu.addOid(oid);
                pdu.send();
            }
            catch (java.io.IOException exc) { }
            catch (uk.co.westhawk.snmp.stack.PduException exc) { }
            catch (java.lang.InterruptedException exc) { }
        }
        else
        {
            AgentException exc = (AgentException) ov;
            System.out.println("init(): AgentException " + exc.getMessage());
            context.destroy();
            exc.printStackTrace(System.out);
            System.exit(1);
        }
    }
    else
    {
        String agent = (String) ov;
        System.out.print("SNMPv3 Agent: " + agent);
        try
        {
            java.net.InetAddress addr = java.net.InetAddress.getByName(agent);
            System.out.print(" - " + addr.getHostName());
        }
        catch (java.net.UnknownHostException exc) {}
        System.out.println();
    }
}


/**
 * Main. To use a properties file different from 
 * <code>discover.properties</code>, pass the name as first argument.
 */
public static void main(String[] args)
{
    String propFileName = null;
    if (args.length > 0)
    {
        propFileName = args[0];
    }
    discover application = new discover(propFileName);
    application.init();
}

}
