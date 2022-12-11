package hu.webuni.shippingservice.jms;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import hu.webuni.orderservice.dto.ParcelDto;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShippingServiceConsumer {
	
	private Random rand = new Random();
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	@JmsListener(destination = "shipmentService")
	public void shippingParcel(ParcelDto parcel) {
		log.info("New delivery request: " + parcel.orderId + " For address: " + parcel.getDeliveryAddress());
	
		try { 
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			
		}
		Integer processSucceed = rand.nextInt(0, 2);

		if(processSucceed > 0)  {
			
			parcel.setParcelno("PRC" + rand.nextInt(100000000, 999999999));
			log.info("Parcel delivered successful. Parcelno: " + parcel.getParcelno());
			log.info("Delivery address: " + parcel.getDeliveryAddress());
		} else {
			log.warn("Delivery unsuccessful. address " + parcel.getDeliveryAddress());
			parcel.setParcelno(null);
		}
		
		this.jmsTemplate.convertAndSend("orderService", parcel);
	}

}
