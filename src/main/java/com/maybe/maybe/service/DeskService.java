package com.maybe.maybe.service;

import com.maybe.maybe.dto.DeskRequest;
import com.maybe.maybe.entity.Desk;
import com.maybe.maybe.repository.DeskRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeskService {

    private DeskRepository deskRepository;

    public DeskService(DeskRepository deskRepository) {
        this.deskRepository = deskRepository;
    }

    public Desk getCustomerById(Long id) {
        if (deskRepository.findDeskById(id) != null) {
            return deskRepository.findDeskById(id);
        } else {
            throw new EntityNotFoundException("Can not find customer by id = " + id);
        }
    }

    public List<Desk> getCustomerList() {
        if (deskRepository.findDeskById(1L) != null) {
           return deskRepository.findAll();
        }
        return null;
    }

    public Desk createCustomer(DeskRequest deskRequest) {
        if (deskRequest != null) {
            Desk desk = new Desk();
            desk.setName(deskRequest.getName());
            return deskRepository.save(desk);
        }
        return null;
    }

    public List<Desk> createCustomerList(List<DeskRequest> deskRequests) {
        if (deskRequests != null) {
            List<Desk> deskList = new ArrayList<>();
            for (DeskRequest deskRequest : deskRequests){
                Desk desk = new Desk();
                desk.setName(deskRequest.getName());
                deskList.add(desk);
            }
            return deskRepository.saveAll(deskList);
        }
        return null;
    }

    public Desk updateCustomerById(Long id, DeskRequest deskRequest){
        if(deskRepository.findDeskById(id) != null){
            Desk desk = new Desk();
            desk.setName(deskRequest.getName());
            desk.setId(id);
            return deskRepository.save(desk);
        }
        return null;

    }
}
