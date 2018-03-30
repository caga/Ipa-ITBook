/**
 * OrderInfoServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package localhost.emotherearth.services.OrderStatus;

public class OrderInfoServiceTestCase extends junit.framework.TestCase {
    public OrderInfoServiceTestCase(java.lang.String name) {
        super(name);
    }
    public void test1OrderStatusGetWsDescription() throws Exception {
        localhost.emotherearth.services.OrderStatus.OrderStatusSoapBindingStub binding;
        try {
            binding = (localhost.emotherearth.services.OrderStatus.OrderStatusSoapBindingStub)
                          new localhost.emotherearth.services.OrderStatus.OrderInfoServiceLocator().getOrderStatus();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        java.lang.String value = null;
        value = binding.getWsDescription();
        // TBD - validate results
    }

    public void test2OrderStatusGetOrderStatus() throws Exception {
        localhost.emotherearth.services.OrderStatus.OrderStatusSoapBindingStub binding;
        try {
            binding = (localhost.emotherearth.services.OrderStatus.OrderStatusSoapBindingStub)
                          new localhost.emotherearth.services.OrderStatus.OrderInfoServiceLocator().getOrderStatus();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        java.lang.String value = null;
        value = binding.getOrderStatus(0);
        // TBD - validate results
    }

    public void test3OrderStatusGetShippingStatus() throws Exception {
        localhost.emotherearth.services.OrderStatus.OrderStatusSoapBindingStub binding;
        try {
            binding = (localhost.emotherearth.services.OrderStatus.OrderStatusSoapBindingStub)
                          new localhost.emotherearth.services.OrderStatus.OrderInfoServiceLocator().getOrderStatus();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        java.lang.String value = null;
        value = binding.getShippingStatus(0);
        // TBD - validate results
    }

}
