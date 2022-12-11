package hu.webuni.orderservice.mapper;

import hu.webuni.orderservice.api.model.AddressDto;
import hu.webuni.orderservice.model.Address;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDto addressToDto(Address address);

    Address dtoToAddress(AddressDto addressDto);

    List<AddressDto> addressesToDtos(Iterable<Address> addresses);
}
