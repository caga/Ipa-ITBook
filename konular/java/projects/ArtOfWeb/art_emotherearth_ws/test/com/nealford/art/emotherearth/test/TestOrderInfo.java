package com.nealford.art.emotherearth.test;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import junit.framework.TestCase;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;


public class TestOrderInfo extends TestCase {
    private static final String ENDPOINT_URL =
        "http://localhost:8080/emotherearth/services/OrderStatus";
    private Call call;

    public TestOrderInfo(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        setUpWSCall();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }


    private void setUpWSCall() throws ServiceException,
            MalformedURLException {
        Service service = new Service();
        call = (Call) service.createCall();
        call.setTargetEndpointAddress(new java.net.URL(ENDPOINT_URL));
    }

    public void testGetDexcription() {
        String expectedReturn =
                "eMotherEarth order information";
        call.setOperationName(new QName("OrderStatus",
                                        "getWsDescription"));
        call.setReturnType(org.apache.axis.encoding.XMLType.
                           XSD_STRING);
        String actualReturn = null;
        try {
            actualReturn = (String) call.invoke(new Object[] {});
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        assertEquals("getInfo() test", expectedReturn, actualReturn);
    }

    public void testGetOrderStatus() {
        int orderKey = 1;
        String expectedReturn = "P";
        call.setOperationName(new QName("OrderStatus", "getOrderStatus"));
        call.addParameter("orderKey", XMLType.XSD_INT, ParameterMode.IN);
        call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
        String actualReturn = null;
        try {
          actualReturn = (String) call.invoke((new Object[] {new Integer(orderKey)}));
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        assertEquals("order status", expectedReturn, actualReturn);
    }

    public void testGetShippingStatus() {
        int orderKey = 1;
        String expectedReturn = "P";
        call.setOperationName(new QName("OrderStatus", "getShippingStatus"));
        call.addParameter("orderKey", XMLType.XSD_INT, ParameterMode.IN);
        call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
        String actualReturn = null;
        try {
          actualReturn = (String) call.invoke((new Object[] {new Integer(orderKey)}));
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        assertEquals("Shipping status", expectedReturn, actualReturn);
    }


    private String executeWsCall(String start, int duration,
                                 String text, String eventType) {
        String result = null;
        try {
            result = (String) call.invoke(new Object[] {start,
                    new Integer(duration),
                    text, eventType});
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private void createWsCall() {
        call.setOperationName(new QName("PublicSchedule",
                                        "addScheduleItem"));
        call.addParameter("start", XMLType.XSD_STRING,
                          ParameterMode.IN);
        call.addParameter("duration", XMLType.XSD_INT,
                          ParameterMode.IN);
        call.addParameter("text", XMLType.XSD_STRING,
                          ParameterMode.IN);
        call.addParameter("eventType", XMLType.XSD_STRING,
                          ParameterMode.IN);
        call.setReturnType(org.apache.axis.encoding.XMLType.
                           XSD_STRING);
    }
}
