package hu.webuni.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.shippingservice.model.Parcel;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
	Parcel findOneByParcelno(String parcelno);
}
