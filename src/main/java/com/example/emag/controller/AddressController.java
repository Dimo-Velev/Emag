package com.example.emag.controller;

import com.example.emag.model.DTOs.AddressDTO;
import com.example.emag.service.AddressService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressController extends AbstractController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/addresses")
    public AddressDTO add(@RequestBody AddressDTO dto, HttpSession session) {
        return addressService.addAddress(dto, getLoggedId(session));
    }

    @PutMapping("/addresses/{id}")
    public AddressDTO edit(@RequestBody AddressDTO dto, @PathVariable int id, HttpSession session) {
        isLogged(session);
        return addressService.editAddress(dto, id);
    }

    @GetMapping("/addresses")
    public List<AddressDTO> getAll(HttpSession session) {
        return addressService.getAllAddresses(getLoggedId(session));
    }

    @GetMapping("/addresses/{id}")
    public AddressDTO getById(@PathVariable int id, HttpSession session) {
        isLogged(session);
        return addressService.getAddress(id);
    }

    @DeleteMapping("/addresses/{id}")
    public String deleteById(@PathVariable int id, HttpSession session) {
        isLogged(session);
        return addressService.deleteAddress(id);
    }
}