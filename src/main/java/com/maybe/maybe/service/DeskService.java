package com.maybe.maybe.service;

import com.maybe.maybe.entity.Desk;
import com.maybe.maybe.repository.DeskRepository;
import org.springframework.stereotype.Service;

@Service
public class DeskService {

    private DeskRepository deskRepository;

    public DeskService(DeskRepository deskRepository) {
        this.deskRepository = deskRepository;
    }

    Desk getDesk(Long id){
        return deskRepository.getOne(id);
    }
}
