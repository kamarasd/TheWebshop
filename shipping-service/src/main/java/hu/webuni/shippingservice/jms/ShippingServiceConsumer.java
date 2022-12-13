package hu.webuni.shippingservice.jms;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import hu.webuni.orderservice.dto.ParcelDto;
import hu.webuni.shippingservice.mapper.ParcelMapper;
import hu.webuni.shippingservice.repository.ParcelRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShippingServiceConsumer {
	
	@Autowired
	private ParcelRepository parcelRepository;
	
	@Autowired
	private ParcelMapper parcelMapper;
	
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
			parcel.setParcelno("");
			while(parcelRepository.findOneByParcelno(parcel.getParcelno()) != null) {
				parcel.setParcelno(generateParcelno());
			}
			
			log.info("Parcel delivered successful. Parcelno: " + parcel.getParcelno());
			log.info("Delivery address: " + parcel.getDeliveryAddress());
			parcel.setInsertDate(LocalDateTime.now());
			parcelRepository.save(parcelMapper.dtoToParcel(parcel));
			reMessage(parcel);
		} else {
			log.warn("Delivery unsuccessful. address " + parcel.getDeliveryAddress());
			parcel.setParcelno(null); 
			reMessage(parcel);
		}		
	}
	
	private String generateParcelno() {
		return "PRC" + rand.nextInt(100000000, 999999999);
	}
	
	private void reMessage(ParcelDto parcelDto) {
		this.jmsTemplate.convertAndSend("orderService", parcelDto);
	}

}
