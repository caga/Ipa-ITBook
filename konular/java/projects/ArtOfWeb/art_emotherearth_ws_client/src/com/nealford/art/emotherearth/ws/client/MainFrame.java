package com.nealford.art.emotherearth.ws.client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

public class MainFrame extends JFrame {
    private JPanel contentPane;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JToolBar jToolBar1 = new JToolBar();
    private JPanel jPanel1 = new JPanel();
    private JTextField jTextField1 = new JTextField();
    private JButton btnInvokeWs = new JButton();
    private JLabel jLabel1 = new JLabel();
    private JPanel jPanel2 = new JPanel();
    private JLabel lblOrderStatus = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel lblShippingStatus = new JLabel();
    private BoxLayout2 boxLayout21 = new BoxLayout2();
    private Component component1;
    private JButton jButton3 = new JButton();

    //Construct the frame
    public MainFrame() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Component initialization
    private void jbInit() throws Exception {
        contentPane = (JPanel)this.getContentPane();
        component1 = Box.createGlue();
        contentPane.setLayout(borderLayout1);
        this.setSize(new Dimension(400, 108));
        this.setTitle("Order Info");
        jTextField1.setPreferredSize(new Dimension(111, 20));
        jTextField1.setText("1");
        btnInvokeWs.setText("Get Status");
        btnInvokeWs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnInvokeWs_actionPerformed(e);
            }
        });
        jLabel1.setText("Order Number:");
        jLabel3.setText("Order Status : ");
        jLabel4.setText("Shipping Status : ");
        jPanel2.setLayout(boxLayout21);
        jButton3.setMnemonic('X');
        jButton3.setText("Exit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton3_actionPerformed(e);
            }
        });
        lblOrderStatus.setPreferredSize(new Dimension(60, 16));
        lblOrderStatus.setText("  ");
        lblShippingStatus.setPreferredSize(new Dimension(60, 16));
        lblShippingStatus.setText("  ");
        contentPane.add(jToolBar1, BorderLayout.NORTH);
        contentPane.add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jLabel1, null);
        jPanel1.add(jTextField1, null);
        jPanel1.add(btnInvokeWs, null);
        contentPane.add(jPanel2, BorderLayout.SOUTH);
        jPanel2.add(jLabel3, null);
        jPanel2.add(lblOrderStatus, null);
        jPanel2.add(component1, null);
        jPanel2.add(jLabel4, null);
        jPanel2.add(lblShippingStatus, null);
        jToolBar1.add(jButton3, null);
    }

    //Overridden so we can exit when window is closed
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }

    void btnInvokeWs_actionPerformed(ActionEvent e) {
        localhost.emotherearth.services.OrderStatus.OrderInfo ws;
        try {
            ws = new localhost.emotherearth.services.OrderStatus.OrderInfoServiceLocator().
                      getOrderStatus();
        } catch (javax.xml.rpc.ServiceException jre) {
            if (jre.getLinkedCause() != null) {
                jre.getLinkedCause().printStackTrace();
            }
            throw new RuntimeException(
                    "JAX-RPC ServiceException caught: " + jre);
        }

        try {
            int orderNo =
                    Integer.parseInt(jTextField1.getText());
            ws.getShippingStatus(orderNo);
            lblOrderStatus.setText(ws.getOrderStatus(orderNo));
            lblShippingStatus.setText(
                    ws.getShippingStatus(orderNo));
        } catch (java.rmi.RemoteException re) {
            throw new RuntimeException("Web service call failed");
        }
    }

    void jButton3_actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}