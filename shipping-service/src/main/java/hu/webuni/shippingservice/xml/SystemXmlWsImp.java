package hu.webuni.shippingservice.xml;

import java.util.Random;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SystemXmlWsImp implements SystemXmlWs {
	
	private Random rand = new Random();
	
	@Override
	public String deliveryParcel(String deliveryAddress, String deliveryItems, String pickupAddress) {
		Integer processSucceed = rand.nextInt(0, 1);

		if(processSucceed == 1)  {
			String parcelno = "PRC" + rand.nextInt(100000000, 999999999);
			log.info("Parcel delivered successful. Parcelno: " + parcelno);
			log.info("Delivery address: " + deliveryAddress);
			return parcelno;
		} else {
			log.warn("Delivery unsuccesfull. address " + deliveryAddress);
			return "";
		}
	}

	
}
 