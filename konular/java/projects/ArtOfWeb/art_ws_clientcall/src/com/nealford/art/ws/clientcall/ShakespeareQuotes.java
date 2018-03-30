package com.nealford.art.ws.clientcall;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;
import com.xmlme.WebServices.ShakespeareLocator;
import com.xmlme.WebServices.ShakespeareSoap;

public class ShakespeareQuotes {
    public ShakespeareQuotes() {
        ShakespeareSoap bard = null;
        try {
             bard = new ShakespeareLocator().
                                      getShakespeareSoap();
        } catch (ServiceException ex) {
            ex.printStackTrace();
        }
        String speech = "To be, or not to be";
        System.out.println("The quote:'" + speech + "'");
        try {
            System.out.println(bard.getSpeech(speech));
        } catch (RemoteException ex1) {
        }
    }

    public static void main(String[] args) {
        new ShakespeareQuotes();
    }

}