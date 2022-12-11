package hu.webuni.orderservice.service;

import hu.webuni.orderservice.dto.ParcelDto;
import hu.webuni.orderservice.model.OrderStatuses;
import hu.webuni.orderservice.model.Orders;
import hu.webuni.orderservice.repository.OrderRepository;
import hu.webuni.orderservice.wsclient.SystemXmlWs;
import hu.webuni.orderservice.wsclient.SystemXmlWsImpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final JmsTemplate jmsTemplate;
    private final OrderRepository orderRepository;

    public void sendParcelToDelivery(Orders orders) {
        ParcelDto parcel = new ParcelDto();
        parcel.setOrderId(orders.getId());
        parcel.setPickupAddress("Budapest 1149 Só utca 1");
        parcel.setDeliveryAddress(orders.getAddress().getZipCode()+" "+orders.getAddress().getCityName()
        		+" "+orders.getAddress().getStreet()+" "+orders.getAddress().getHouseNo());
        this.jmsTemplate.convertAndSend("shipmentService", parcel);
        log.info("Order sent to shipping service. Order id " + orders.getId());
    }
    
    @JmsListener(destination = "orderService")
    public void receiveParcelDetailsFromDelivery(ParcelDto parcel) throws Exception {
    	Orders order = orderRepository.findById(parcel.getOrderId()).orElseThrow(() -> new Exception("not found"));

        if(parcel.getParcelno() != null && order.getId().equals(parcel.getOrderId())) {
        	order.setOrderStatus(OrderStatuses.DELIVERED);
        	order.setParcelno(parcel.getParcelno());
        	order.setDeliveryDate(LocalDate.now());
        	log.info("Order " + order.getId() + " " + order.getOrderStatus() + " At " + order.getDeliveryDate());
        } else {
        	order.setOrderStatus(OrderStatuses.SHIPPING_FAILED);
        	log.info("Order " + order.getId() + " " + order.getOrderStatus() + " At " + LocalDate.now());
        }
	
    	orderRepository.save(order);
    	
    }

}
