package hu.webuni.orderservice.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParcelDto {
	public Long id;
	public String deliveryAddress;
    public String deliveryItems;
    public String pickupAddress;
    public Long orderId;
    public String parcelno;
    public LocalDateTime insertDate;
}
