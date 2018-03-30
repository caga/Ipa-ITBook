/**
 * Shakespeare.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.xmlme.WebServices;

public interface Shakespeare extends javax.xml.rpc.Service {

    // <h3>This Web Service takes a phrase from the plays of William Shakespeare
    // and returns the associated speech, speaker, and play. The Shakespeare
    // texts used in this Web Service are the <a href=http://www.oasis-open.org/cover/bosakShakespeare200.html>
    // XML versions</a> developed by Jon Bosak. Please visit <a href=http://www.xmlme.com>
    // XML Me</a> or <a href=mailto:kevinc@xmlme> contact us</a> for more
    // information.</h3>
    public java.lang.String getShakespeareSoapAddress();

    public com.xmlme.WebServices.ShakespeareSoap getShakespeareSoap() throws javax.xml.rpc.ServiceException;

    public com.xmlme.WebServices.ShakespeareSoap getShakespeareSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
