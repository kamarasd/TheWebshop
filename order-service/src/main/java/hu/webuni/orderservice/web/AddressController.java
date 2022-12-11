package hu.webuni.orderservice.web;

import hu.webuni.orderservice.api.AddressControllerApi;
import hu.webuni.orderservice.api.model.AddressDto;
import hu.webuni.orderservice.mapper.AddressMapper;
import hu.webuni.orderservice.model.Address;
import hu.webuni.orderservice.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AddressController implements AddressControllerApi {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;


    @Override
    public Optional<NativeWebRequest> getRequest() {
        return AddressControllerApi.super.getRequest();
    }

    @Override
    public ResponseEntity<AddressDto> addNewAddress(AddressDto addressDto) {
        Address address = addressRepository.save(addressMapper.dtoToAddress(addressDto));
        return ResponseEntity.ok(addressMapper.addressToDto(address));
    }

    @Override
    public ResponseEntity<Void> deleteAddress(Long id) {
        addressRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        addressRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<AddressDto>> getAllAddress() {
        List<Address> address = addressRepository.findAll();
        return ResponseEntity.ok(addressMapper.addressesToDtos(address));

    }
}
