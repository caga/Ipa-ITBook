/**
 * OrderInfoService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package localhost.emotherearth.services.OrderStatus;

public interface OrderInfoService extends javax.xml.rpc.Service {
    public java.lang.String getOrderStatusAddress();

    public localhost.emotherearth.services.OrderStatus.OrderInfo getOrderStatus() throws javax.xml.rpc.ServiceException;

    public localhost.emotherearth.services.OrderStatus.OrderInfo getOrderStatus(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
