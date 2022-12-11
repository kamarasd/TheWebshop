package hu.webuni.orderservice;

import hu.webuni.orderservice.dto.ParcelDto;
import hu.webuni.orderservice.model.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final JmsTemplate jmsTemplate;

    public void sendParcelToDelivery(Orders orders) {
        ParcelDto parcel = new ParcelDto();
        parcel.setDeliveryItems(orders.getItems());
        parcel.setPickupAddress("pickupAddress");
        parcel.setDeliveryAddress(orders.getAddress().getZipCode()+" "+orders.getAddress().getCityName());
        this.jmsTemplate.convertAndSend("/shippingService", parcel);
    }

}
