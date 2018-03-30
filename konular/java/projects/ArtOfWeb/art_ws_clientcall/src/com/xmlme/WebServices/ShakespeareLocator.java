/**
 * ShakespeareLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.xmlme.WebServices;

public class ShakespeareLocator extends org.apache.axis.client.Service implements com.xmlme.WebServices.Shakespeare {

    // <h3>This Web Service takes a phrase from the plays of William Shakespeare
    // and returns the associated speech, speaker, and play. The Shakespeare
    // texts used in this Web Service are the <a href=http://www.oasis-open.org/cover/bosakShakespeare200.html>
    // XML versions</a> developed by Jon Bosak. Please visit <a href=http://www.xmlme.com>
    // XML Me</a> or <a href=mailto:kevinc@xmlme> contact us</a> for more
    // information.</h3>

    // Use to get a proxy class for ShakespeareSoap
    private final java.lang.String ShakespeareSoap_address = "http://www.xmlme.com/WSShakespeare.asmx";

    public java.lang.String getShakespeareSoapAddress() {
        return ShakespeareSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ShakespeareSoapWSDDServiceName = "ShakespeareSoap";

    public java.lang.String getShakespeareSoapWSDDServiceName() {
        return ShakespeareSoapWSDDServiceName;
    }

    public void setShakespeareSoapWSDDServiceName(java.lang.String name) {
        ShakespeareSoapWSDDServiceName = name;
    }

    public com.xmlme.WebServices.ShakespeareSoap getShakespeareSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ShakespeareSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getShakespeareSoap(endpoint);
    }

    public com.xmlme.WebServices.ShakespeareSoap getShakespeareSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.xmlme.WebServices.ShakespeareSoapStub _stub = new com.xmlme.WebServices.ShakespeareSoapStub(portAddress, this);
            _stub.setPortName(getShakespeareSoapWSDDServiceName());
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
            if (com.xmlme.WebServices.ShakespeareSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.xmlme.WebServices.ShakespeareSoapStub _stub = new com.xmlme.WebServices.ShakespeareSoapStub(new java.net.URL(ShakespeareSoap_address), this);
                _stub.setPortName(getShakespeareSoapWSDDServiceName());
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
        if ("ShakespeareSoap".equals(inputPortName)) {
            return getShakespeareSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://xmlme.com/WebServices", "Shakespeare");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("ShakespeareSoap"));
        }
        return ports.iterator();
    }

}
