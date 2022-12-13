package hu.webuni.orderservice.mapper;

import hu.webuni.orderservice.api.model.OrderDto;
import hu.webuni.orderservice.model.Orders;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrdersMapper {

	Orders dtoToOrder(OrderDto orderDto);
	
	OrderDto orderToDto(Orders orders);

    List<OrderDto> ordersToDto(Iterable<Orders> orders);
    
    Iterable<Orders> iterableToDto(Iterable<Orders> iterable);
}
