/**
 * OrderInfoServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package localhost.emotherearth.services.OrderStatus;

public class OrderInfoServiceLocator extends org.apache.axis.client.Service implements localhost.emotherearth.services.OrderStatus.OrderInfoService {

    // Use to get a proxy class for OrderStatus
    private final java.lang.String OrderStatus_address = "http://localhost:8080/emotherearth/services/OrderStatus";

    public java.lang.String getOrderStatusAddress() {
        return OrderStatus_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String OrderStatusWSDDServiceName = "OrderStatus";

    public java.lang.String getOrderStatusWSDDServiceName() {
        return OrderStatusWSDDServiceName;
    }

    public void setOrderStatusWSDDServiceName(java.lang.String name) {
        OrderStatusWSDDServiceName = name;
    }

    public localhost.emotherearth.services.OrderStatus.OrderInfo getOrderStatus() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(OrderStatus_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getOrderStatus(endpoint);
    }

    public localhost.emotherearth.services.OrderStatus.OrderInfo getOrderStatus(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            localhost.emotherearth.services.OrderStatus.OrderStatusSoapBindingStub _stub = new localhost.emotherearth.services.OrderStatus.OrderStatusSoapBindingStub(portAddress, this);
            _stub.setPortName(getOrderStatusWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (localhost.emotherearth.services.OrderStatus.OrderInfo.class.isAssignableFrom(serviceEndpointInterface)) {
                localhost.emotherearth.services.OrderStatus.OrderStatusSoapBindingStub _stub = new localhost.emotherearth.services.OrderStatus.OrderStatusSoapBindingStub(new java.net.URL(OrderStatus_address), this);
                _stub.setPortName(getOrderStatusWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("OrderStatus".equals(inputPortName)) {
            return getOrderStatus();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://localhost:8080/emotherearth/services/OrderStatus", "OrderInfoService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("OrderStatus"));
        }
        return ports.iterator();
    }

}
