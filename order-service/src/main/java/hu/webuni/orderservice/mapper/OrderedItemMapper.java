package hu.webuni.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.orderservice.api.model.OrderedItemDto;
import hu.webuni.orderservice.model.OrderedItem;

@Mapper(componentModel = "spring")
public interface OrderedItemMapper {

	//@Mapping(target = "orderId", source = "orders.id")
	public OrderedItemDto orderedItemToDto(OrderedItem orderedItem);
	
	public OrderedItem dtoToOrderedItem(OrderedItemDto orderedItemDto);
}
