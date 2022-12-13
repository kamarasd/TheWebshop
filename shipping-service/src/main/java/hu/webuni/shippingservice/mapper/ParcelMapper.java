package hu.webuni.shippingservice.mapper;

import org.mapstruct.Mapper;

import hu.webuni.orderservice.dto.ParcelDto;
import hu.webuni.shippingservice.model.Parcel;

@Mapper(componentModel = "spring")
public interface ParcelMapper {
	
	Parcel dtoToParcel(ParcelDto parcelDto);
}
