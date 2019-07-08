package com.maybe.maybe.controller;

import com.maybe.maybe.annotation.Statistic;
import com.maybe.maybe.dto.DeskDTO;
import com.maybe.maybe.entity.Desk;
import com.maybe.maybe.entity.enums.DeskState;
import com.maybe.maybe.service.DeskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DeskController {

    private DeskService deskService;

    public DeskController(DeskService deskService) {
        this.deskService = deskService;
    }

    @Statistic
    @GetMapping("/desks/{id}")
    public ResponseEntity<Desk> getDeskById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(deskService.findById(id));
    }

    @Statistic
    @PostMapping("/desks/{name}")
    public ResponseEntity<Desk> createDesk(@PathVariable @Size(min = 4, max = 50) String name) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(deskService.createFromDTO(new DeskDTO(name)));
    }
    @Statistic
    @GetMapping("/desks")
    public ResponseEntity<List<Desk>> getDeskList(@RequestParam(required = false) DeskState state) {
        if (state == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(deskService.findAll());
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(deskService.findAllByState(state));
        }

    }

    @Statistic
    @PutMapping("/desks/{id}/{name}")
    public ResponseEntity<Desk> updateDeskById(@PathVariable @Min(1) Long id,
                                               @PathVariable @Size(min = 4, max = 50) String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(deskService.updateById(id, new DeskDTO(name)));
    }

    @Statistic
    @DeleteMapping("/desks/{id}")
    public ResponseEntity<Desk> deleteDeskById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(deskService.deleteById(id));
    }
}
