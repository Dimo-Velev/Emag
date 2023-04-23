package com.example.emag.controller;

import com.example.emag.model.DTOs.address.AddressDTO;
import com.example.emag.model.DTOs.address.AddressIdDTO;
import com.example.emag.service.AddressService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressController extends AbstractController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/addresses")
    public AddressIdDTO add(@Valid @RequestBody AddressDTO dto, HttpSession session) {
        return addressService.addAddress(dto, getLoggedId(session));
    }

    @PutMapping("/addresses/{id:\\d+}")
    public AddressIdDTO edit(@Valid @RequestBody AddressDTO dto,@Valid @PathVariable int id, HttpSession session) {
        return addressService.editAddress(dto, id,getLoggedId(session));
    }

    @GetMapping("/addresses")
    public List<AddressIdDTO> getAll(HttpSession session) {
        return addressService.getAllAddresses(getLoggedId(session));
    }

    @GetMapping("/addresses/{id:\\d+}")
    public AddressIdDTO getById(@PathVariable int id, HttpSession session) {
        return addressService.getAddress(id, getLoggedId(session));
    }

    @DeleteMapping("/addresses/{id:\\d+}")
    public ResponseEntity<String> deleteById(@PathVariable int id, HttpSession session) {
        addressService.deleteAddress(id, getLoggedId(session));
        return ResponseEntity.ok("Address was deleted.");
    }
}
