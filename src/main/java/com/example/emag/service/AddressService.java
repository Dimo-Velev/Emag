package com.example.emag.service;

import com.example.emag.model.DTOs.address.AddressDTO;
import com.example.emag.model.DTOs.address.AddressIdDTO;
import com.example.emag.model.entities.Address;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.exceptions.UnauthorizedException;
import com.example.emag.model.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService extends AbstractService {

    @Autowired
    private AddressRepository addressRepository;

    public AddressIdDTO addAddress(AddressDTO dto, int id) {
        Address address = mapper.map(dto, Address.class);
        address.setUser(getUserById(id));
        addressRepository.save(address);
        return mapper.map(address,AddressIdDTO.class);
    }

    public AddressIdDTO editAddress(AddressDTO dto, int id, int userId) {
        Address address = addressRepository.findByIdAndUserId(id,userId).orElseThrow(() -> new NotFoundException("Address not found"));
        if (!dto.getName().equals(address.getName())) {
            address.setAddress(dto.getAddress());
        }
        if (!dto.getPhoneNumber().equals(address.getPhoneNumber())) {
            address.setPhoneNumber(dto.getPhoneNumber());
        }
        if (!dto.getAddress().equals(address.getAddress())) {
            address.setAddress(dto.getAddress());
        }
        if (!dto.getResidentialArea().equals(address.getResidentialArea())) {
            address.setResidentialArea(dto.getResidentialArea());
        }
        if (Integer.parseInt(dto.getFloor()) !=(address.getFloor())) {
            address.setFloor(Integer.parseInt(dto.getFloor()));
        }
        addressRepository.save(address);
        return mapper.map(address,AddressIdDTO.class);

    }

    public List<AddressIdDTO> getAllAddresses(int id) {
        List<Address> addressList = addressRepository.findAllByUserId(id);
        if (addressList.isEmpty()){
            throw new NotFoundException("No addresses found.");
        }
        return addressRepository.findAllByUserId(id)
                .stream()
                .map(address -> mapper.map(address, AddressIdDTO.class))
                .collect(Collectors.toList());
    }

    public AddressIdDTO  getAddress(int id, int userId) {
        Address address = getAddressById(id);
        checkIfItsUserAddress(address, userId);
        return mapper.map(address, AddressIdDTO.class);
    }

    public void deleteAddress(int id, int userId) {
        checkIfItsUserAddress(getAddressById(id), userId);
        addressRepository.deleteById(id);
    }

    private Address getAddressById(int id) {
        return addressRepository.findById(id).orElseThrow(() -> new NotFoundException("Address not found."));
    }

    private void checkIfItsUserAddress(Address address, int userId) {
        if (userId != address.getUser().getId()) {
            throw new UnauthorizedException("You have no access to this resource.");
        }
    }
}