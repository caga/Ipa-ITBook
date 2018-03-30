/**
 * OrderInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package localhost.emotherearth.services.OrderStatus;

public interface OrderInfo extends java.rmi.Remote {
    public java.lang.String getWsDescription() throws java.rmi.RemoteException;
    public java.lang.String getOrderStatus(int orderKey) throws java.rmi.RemoteException;
    public java.lang.String getShippingStatus(int orderKey) throws java.rmi.RemoteException;
}
