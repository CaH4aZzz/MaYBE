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
    public Desk getDeskById(@PathVariable @Min(1) Long id) {
        return deskService.findById(id);
    }

    @Statistic
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/desks/{name}")
    public Desk createDesk(@PathVariable @Size(min = 4, max = 50) String name) {
        return deskService.createFromDTO(new DeskDTO(name));
    }
    @Statistic
    @GetMapping("/desks")
    public List<Desk> getDeskList(@RequestParam(required = false) DeskState state) {
        if (state == null) {
            return deskService.findAll();
        } else {
            return deskService.findAllByState(state);
        }

    }

    @Statistic
    @PutMapping("/desks/{id}/{name}")
    public Desk updateDeskById(@PathVariable @Min(1) Long id,
                                               @PathVariable @Size(min = 4, max = 50) String name) {
        return deskService.updateById(id, new DeskDTO(name));
    }

    @Statistic
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/desks/{id}")
    public void deleteDeskById(@PathVariable @Min(1) Long id) {
        deskService.deleteById(id);
    }
}
