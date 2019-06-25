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

    @GetMapping("/desks/{id}")
    public ResponseEntity<Desk> getDeskById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(deskService.getDeskById(id));
    }

    @GetMapping("/desks")
    public ResponseEntity<List<Desk>> getDeskList() {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(deskService.getDeskList());
    }

    @PostMapping("/desks")
    public ResponseEntity<Desk> createDesk(@Valid @RequestBody DeskRequest deskRequest){
         return ResponseEntity.status(HttpStatus.OK)
                 .body(deskService.createDesk(deskRequest));
    }

    @PutMapping("/desks/{id}")
    public ResponseEntity<Desk> updateDeskById(@PathVariable Long id,
                                                   @Valid @RequestBody DeskRequest deskRequest){
        return ResponseEntity.status(HttpStatus.OK)
                .body(deskService.updateDeskById(id, deskRequest));
    }
}
