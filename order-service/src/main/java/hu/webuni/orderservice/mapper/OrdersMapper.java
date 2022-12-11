package hu.webuni.orderservice.mapper;

import hu.webuni.orderservice.api.model.OrderDto;
import hu.webuni.orderservice.model.Orders;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrdersMapper {

    OrderDto orderToDto(Orders order);

    Orders dtoToOrder(OrderDto orderDto);

    List<OrderDto> ordersToDto(Iterable<Orders> orders);
}
