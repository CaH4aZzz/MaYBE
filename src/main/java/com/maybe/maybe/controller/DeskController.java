package com.maybe.maybe.controller;

import com.maybe.maybe.dto.DeskRequest;
import com.maybe.maybe.entity.Desk;
import com.maybe.maybe.service.DeskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DeskController {

    private DeskService deskService;

    public DeskController(DeskService deskService) {
        this.deskService = deskService;
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Desk> getCustomerById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(deskService.getCustomerById(id));
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Desk>> getCustomerList() {
        return  ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(deskService.getCustomerList());
    }

    @PostMapping("/customer")
    public ResponseEntity<Desk> createCustomer(@Valid @RequestBody DeskRequest deskRequest){
         return ResponseEntity.status(HttpStatus.OK)
                 .contentType(MediaType.APPLICATION_JSON)
                 .body(deskService.createCustomer(deskRequest));
    }

    @PostMapping("/customers")
    public ResponseEntity<List<Desk>> createCustomerList(@Valid @RequestBody List<DeskRequest> customerList){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(deskService.createCustomerList(customerList));
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<Desk> updateCustomerById(@PathVariable Long id,
                                                   @Valid @RequestBody DeskRequest deskRequest){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(deskService.updateCustomerById(id, deskRequest));
    }
}
