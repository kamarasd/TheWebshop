package hu.webuni.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParcelDto {

	public String deliveryAddress;
    public String deliveryItems;
    public String pickupAddress;
    public Long orderId;
    public String parcelno;
}
