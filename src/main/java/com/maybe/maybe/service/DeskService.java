package com.maybe.maybe.service;

import com.maybe.maybe.dto.DeskRequest;
import com.maybe.maybe.entity.Desk;
import com.maybe.maybe.entity.enums.DeskState;
import com.maybe.maybe.repository.DeskRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

@Service
public class DeskService {

    private DeskRepository deskRepository;

    public DeskService(DeskRepository deskRepository) {
        this.deskRepository = deskRepository;
    }

    public Desk getDeskById(Long id) {
        if (deskRepository.findDeskById(id) != null) {
            return deskRepository.findDeskById(id);
        } else {
            throw new EntityNotFoundException("Can not find desk by id = " + id);
        }
    }

    public List<Desk> getDeskList() {
        if (!deskRepository.findAll().isEmpty()) {
           return deskRepository.findAll();
        } else {
            throw new EntityNotFoundException("Can not find any desk");
        }
    }

    public Desk createDesk(DeskRequest deskRequest) {
        if (deskRepository.findDeskByName(deskRequest.getName()) == null) {
            Desk desk = new Desk();
            desk.setName(deskRequest.getName());
            return deskRepository.save(desk);
        } else {
            return null;
        }
    }

    public Desk updateDeskById(Long id, DeskRequest deskRequest){
        if(deskRepository.findDeskById(id) != null){
            Desk desk = new Desk();
            desk.setName(deskRequest.getName());
            desk.setId(id);
            return deskRepository.save(desk);
        }else {
            throw new EntityNotFoundException("Can not update desk by id = " + id);
        }
    }

    public List<Desk> getDesksByState(DeskState state){
        List<Desk> desks = deskRepository.findAllByDeskState(state);
        if(!desks.isEmpty()) {
            return desks;
        } else {
            throw new EntityNotFoundException("No desks found for this state");
        }
    }
}
