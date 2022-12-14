package hu.webuni.orderservice.web;

import hu.webuni.orderservice.service.OrderService;
import hu.webuni.orderservice.api.OrderControllerApi;
import hu.webuni.orderservice.api.model.ItemDto;
import hu.webuni.orderservice.api.model.OrderDto;
import hu.webuni.orderservice.dto.ParcelDto;
import hu.webuni.orderservice.mapper.AddressMapper;
import hu.webuni.orderservice.mapper.OrderedItemMapper;
import hu.webuni.orderservice.mapper.OrdersMapper;
import hu.webuni.orderservice.model.Address;
import hu.webuni.orderservice.model.Catalog;
import hu.webuni.orderservice.model.OrderStatuses;
import hu.webuni.orderservice.model.OrderedItem;
import hu.webuni.orderservice.model.Orders;
import hu.webuni.orderservice.repository.AddressRepository;
import hu.webuni.orderservice.repository.CatalogRepository;
import hu.webuni.orderservice.repository.OrderedItemRepository;
import hu.webuni.orderservice.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@RestController
@Slf4j
public class OrderController implements OrderControllerApi {

    private final OrdersRepository ordersRepository;  
    private final OrdersMapper ordersMapper;
    private final OrderService orderService;
    private final OrderedItemMapper orderedItemMapper;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return OrderControllerApi.super.getRequest();
    }

    @Override
    public ResponseEntity<OrderDto> acceptOrder(Long id, Boolean accept) {
    	return ResponseEntity.ok(ordersMapper.orderToDto(orderService.acceptOrder(id, accept)));
    }

    @Override
    public ResponseEntity<OrderDto> addNewOrder(OrderDto orderDto) {     
        return ResponseEntity.ok(ordersMapper.orderToDto(orderService.addNewOrder(ordersMapper.dtoToOrder(orderDto))));
    }

    @Override
    public ResponseEntity<OrderDto> findOrder(Long id) {
        Orders orders = ordersRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(ordersMapper.orderToDto(orders));
    }

    @Override
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<Orders> order = ordersRepository.findAll();
        return ResponseEntity.ok(ordersMapper.ordersToDto(order));
    }

	@Override
	public ResponseEntity<OrderDto> addItemToOrder(@NotNull @Valid Long orderId, @NotNull @Valid Long productId,
			@Valid Integer piece) {
		return ResponseEntity.ok(ordersMapper.orderToDto(orderService.addItemToOrder(orderId, productId, piece)));
	}

	@Override
	public ResponseEntity<Void> deleteOrder(Long id) {
		orderService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<List<OrderDto>> findByUsername(@NotNull String username) {
		List<Orders> order = orderService.findOrderByUsername(username);
		return ResponseEntity.ok(ordersMapper.ordersToDto(order));
	}
}


