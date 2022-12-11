package hu.webuni.orderservice.web;

import hu.webuni.orderservice.OrderService;
import hu.webuni.orderservice.api.OrderControllerApi;
import hu.webuni.orderservice.api.model.OrderDto;
import hu.webuni.orderservice.dto.ParcelDto;
import hu.webuni.orderservice.mapper.AddressMapper;
import hu.webuni.orderservice.mapper.OrdersMapper;
import hu.webuni.orderservice.model.Address;
import hu.webuni.orderservice.model.OrderStatuses;
import hu.webuni.orderservice.model.Orders;
import hu.webuni.orderservice.repository.AddressRepository;
import hu.webuni.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class OrderController implements OrderControllerApi {

    private final OrderRepository orderRepository;

    private final OrdersMapper ordersMapper;
    private final OrderService orderService;


    @Override
    public Optional<NativeWebRequest> getRequest() {
        return OrderControllerApi.super.getRequest();
    }

    @Override
    public ResponseEntity<OrderDto> acceptOrder(Long id, Boolean accept) {
        Orders orders = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (accept.equals(Boolean.TRUE)) {
            orders.setOrderStatus(OrderStatuses.CONFIRMED);
            orderService.sendParcelToDelivery(orders);
        } else if (accept.equals(Boolean.FALSE)) {
            orders.setOrderStatus(OrderStatuses.DECLINED);
        } else {
            new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        orderRepository.save(orders);
        return ResponseEntity.ok(ordersMapper.orderToDto(orders));
    }

    @Override
    public ResponseEntity<OrderDto> addNewOrder(OrderDto orderDto) {
        Orders order = ordersMapper.dtoToOrder(orderDto);
        order.setOrderStatus(OrderStatuses.PENDING);
        return ResponseEntity.ok(ordersMapper.orderToDto(orderRepository.save(order)));
    }

    @Override
    public ResponseEntity<OrderDto> findOrder(Long id) {
        Orders orders = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(ordersMapper.orderToDto(orders));
    }

    @Override
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<Orders> order = orderRepository.findAll();
        return ResponseEntity.ok(ordersMapper.ordersToDto(order));
    }


}


