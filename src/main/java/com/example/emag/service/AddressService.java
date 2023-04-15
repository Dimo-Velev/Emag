package com.example.emag.service;

import com.example.emag.model.DTOs.address.AddressDTO;
import com.example.emag.model.entities.Address;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService extends AbstractService {

    @Autowired
    private AddressRepository addressRepository;

    public AddressDTO addAddress(AddressDTO dto, int id) {
        Address address = mapper.map(dto, Address.class);
        address.setUser(getUserById(id));
        addressRepository.save(address);
        return dto;
    }

    public AddressDTO editAddress(AddressDTO dto, int id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new NotFoundException("Address not found"));
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
        if (dto.getFloor() != address.getFloor()) {
            address.setFloor(dto.getFloor());
        }
        addressRepository.save(address);
        return dto;

    }

    public List<AddressDTO> getAllAddresses(int id) {
        ifUserExists(id);
        return addressRepository.findAllByUserId(id)
                .stream()
                .map(address -> mapper.map(address, AddressDTO.class))
                .collect(Collectors.toList());
    }

    public AddressDTO getAddress(int id) {
        Address address = addressRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Address not found."));
        return mapper.map(address, AddressDTO.class);
    }

    public String deleteAddress(int id) {
        if (addressRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Address doesn't exist.");
        }
        addressRepository.deleteById(id);
        return "Address was deleted.";
    }
}