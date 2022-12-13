package hu.webuni.shippingservice.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Parcel {
	@Id
	@GeneratedValue
	public Long id;
	public String deliveryAddress;
    public String deliveryItems;
    public String pickupAddress;
    public Long orderId;
    public String parcelno;
    public LocalDateTime insertDate;

}
