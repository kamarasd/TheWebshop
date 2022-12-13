package hu.webuni.orderservice.service;

import hu.webuni.orderservice.dto.ParcelDto;
import hu.webuni.orderservice.model.Catalog;
import hu.webuni.orderservice.model.OrderStatuses;
import hu.webuni.orderservice.model.OrderedItem;
import hu.webuni.orderservice.model.Orders;
import hu.webuni.orderservice.repository.CatalogRepository;
import hu.webuni.orderservice.repository.OrderedItemRepository;
import hu.webuni.orderservice.repository.OrdersRepository;
import hu.webuni.orderservice.wsclient.SystemXmlWs;
import hu.webuni.orderservice.wsclient.SystemXmlWsImpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.criteria.Order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final JmsTemplate jmsTemplate;
    private final OrdersRepository ordersRepository;
    private final CatalogRepository catalogRepository;
    private final OrderedItemRepository orderedItemRepository;

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
    	Orders order = ordersRepository.findById(parcel.getOrderId()).orElseThrow(() -> new Exception("not found"));

        if(parcel.getParcelno() != null && order.getId().equals(parcel.getOrderId())) {
        	order.setOrderStatus(OrderStatuses.DELIVERED);
        	order.setParcelno(parcel.getParcelno());
        	order.setDeliveryDate(parcel.insertDate.toLocalDate());
        	log.info("Order " + order.getId() + " " + order.getOrderStatus() + " At " + order.getDeliveryDate());
        } else {
        	order.setOrderStatus(OrderStatuses.SHIPPING_FAILED);
        	log.info("Order " + order.getId() + " " + order.getOrderStatus() + " At " + LocalDate.now());
        }
	
    	ordersRepository.save(order);
    }
    
    public Orders acceptOrder(Long id, Boolean accept) {
    	Orders orders = ordersRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    	if(orders.getParcelno() == null) {
	        if (accept.equals(Boolean.TRUE)) {
	            orders.setOrderStatus(OrderStatuses.CONFIRMED);
	            sendParcelToDelivery(orders);
	        } else if (accept.equals(Boolean.FALSE)) {
	            orders.setOrderStatus(OrderStatuses.DECLINED);
	        } else {
	            new ResponseStatusException(HttpStatus.BAD_REQUEST);
	        }
        } else {
        	throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, "Already delivered");
        }

    	return ordersRepository.save(orders); 
    }
    
    public Orders addItemToOrder(Long orderId, Long productId, Integer piece) { 
    	Catalog catalog = catalogRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		Orders orders = ordersRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		piece = piece != null ? piece : 1;
		
		List<OrderedItem> alreadyOrderedItems = orders.getOrderedItemList();
		
		OrderedItem orderedItem = new OrderedItem();
		System.out.println(Objects.nonNull(orderedItem));
		if(!Objects.nonNull(orderedItem)) { 
			alreadyOrderedItems.forEach(item -> {				
				orders.setPiecesSum(item.getPiece() + orders.getPiecesSum());
				orders.setPriceSum(item.getProductPrice() + orders.getPriceSum());
			});
		} else {
			orders.setPiecesSum(orders.getPiecesSum() + piece);
			System.out.println(orders.getPiecesSum() + piece);
			orders.setPriceSum(orders.getPriceSum() + (catalog.getProductPrice() * piece));
			System.out.println(orders.getPriceSum() + (catalog.getProductPrice() * piece));
		}
		
		orderedItem.setPiece(piece);
		orderedItem.setProductName(catalog.getProductName());
		orderedItem.setProductPrice(catalog.getProductPrice());

		ordersRepository.save(orders);
		orders.addOrders(orderedItem);
		orderedItemRepository.save(orderedItem);
		
		return orders;
    }
    
    public Orders addNewOrder(Orders order) { 
    	order.setPiecesSum(0);
        order.setPriceSum(0);
        order.setOrderStatus(OrderStatuses.PENDING);
    	return ordersRepository.save(order);
    }

}
