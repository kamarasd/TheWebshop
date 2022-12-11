package hu.webuni.orderservice.xmlws;

import javax.jws.WebService;

@WebService
public interface ShippingXmlWs {

    public String deliveryParcel(String address, String items, String shippingAddress);


}
