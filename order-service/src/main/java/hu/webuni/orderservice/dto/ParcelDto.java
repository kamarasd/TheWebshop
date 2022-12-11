package hu.webuni.orderservice.dto;

import lombok.Data;

@Data
public class ParcelDto {

    public String deliveryAddress;
    public String deliveryItems;
    public String pickupAddress;
}
