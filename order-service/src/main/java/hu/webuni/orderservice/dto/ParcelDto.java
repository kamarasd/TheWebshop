package hu.webuni.orderservice.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ParcelDto {

    public String deliveryAddress;
    public String deliveryItems;
    public String pickupAddress;
    public Long orderId;
    public String parcelno;
    public LocalDateTime insertDate;
}
