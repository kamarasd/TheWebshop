package hu.webuni.shippingservice.xml;

import javax.jws.WebService;

@WebService
public interface SystemXmlWs {
	public String deliveryParcel(String deliveryAddress, String deliveryItems, String pickupAddress);
}
