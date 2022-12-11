package hu.webuni.orderservice.wsclient;

import hu.webuni.orderservice.model.Orders;
import hu.webuni.orderservice.model.OrderStatuses;
import hu.webuni.orderservice.wsclient.SystemXmlWs;
import hu.webuni.orderservice.wsclient.SystemXmlWsImpService;

public class CallingShippingServiceMain {

    public static void main(String[] args) {
        SystemXmlWs systemXmlWsImpPort = new SystemXmlWsImpService().getSystemXmlWsImpPort();
        //String deliveryAddress, String deliveryItems, String pickupAddress);
        OrderStatuses status = null;
        String parcelno = systemXmlWsImpPort.deliveryParcel("address1", "items1", "pickupAddress");
        System.out.println(parcelno);
        if(parcelno.isEmpty()) {
            System.out.println(parcelno);
        } else {
            System.out.println(parcelno);
        }
    }
}
