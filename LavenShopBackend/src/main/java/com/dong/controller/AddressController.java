package com.dong.controller;


import com.dong.dto.model.AddressDto;
import com.dong.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@AllArgsConstructor
public class AddressController {
    AddressService addressService;

    @GetMapping("/create")
    public ResponseEntity<AddressDto> createAddress(@AuthenticationPrincipal Jwt principal,
                                                    @RequestBody AddressDto addressDto){
        long userId = principal.getClaim("id");

        return new ResponseEntity<>(addressService.createAddress(userId, addressDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAllAddressByUserId(@AuthenticationPrincipal Jwt principal){
        long userId = principal.getClaim("id");

        return new ResponseEntity<>(addressService.getAddressByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(addressService.getAddressById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> updateAddress(@AuthenticationPrincipal Jwt principal,
                                                    @PathVariable(name = "id") long id,
                                                    @RequestBody AddressDto addressDto) {
        long userId = principal.getClaim("id");

        return new ResponseEntity<>(addressService.updateAddress(userId, addressDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable(name = "id") long id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<>("Address deleted successfully", HttpStatus.OK);
    }

}
