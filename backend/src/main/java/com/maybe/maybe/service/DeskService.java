package com.maybe.maybe.service;

import com.maybe.maybe.dto.DeskDTO;
import com.maybe.maybe.entity.Desk;
import com.maybe.maybe.entity.enums.DeskState;
import com.maybe.maybe.repository.DeskRepository;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DeskService {

    private DeskRepository deskRepository;

    public DeskService(DeskRepository deskRepository) {
        this.deskRepository = deskRepository;
    }


    public Desk findById(Long id) {
        if (deskRepository.findDeskById(id) != null) {
            return deskRepository.findDeskById(id);
        } else {
            throw new EntityNotFoundException("Can not find desk by id = " + id);
        }
    }

    public List<Desk> findAll() {
        if (deskRepository.findDeskById(1L) != null) {
            return deskRepository.findAll();
        } else {
            throw new EntityNotFoundException("Can not find any desk");
        }
    }

    public Desk createFromDTO(DeskDTO deskDTO) {
        if (deskRepository.findDeskByName(deskDTO.getName()) == null) {
            Desk desk = new Desk();
            desk.setName(deskDTO.getName());
            return deskRepository.save(desk);
        } else {
            throw new BeanCreationException("Can not create desk entity by dto " + deskDTO.toString());
        }
    }

    public Desk updateById(Long id, DeskDTO deskDTO) {
        if (deskRepository.findDeskById(id) != null) {
            Desk desk = deskRepository.findDeskById(id);
            desk.setName(deskDTO.getName());
            desk.setId(id);
            return deskRepository.save(desk);
        } else {
            throw new EntityNotFoundException("Can not update desk by id = " + id);
        }
    }

    public List<Desk> findAllByState(DeskState state) {
        List<Desk> desks = deskRepository.findAllByDeskState(state);
        if (!desks.isEmpty()) {
            return desks;
        } else {
            throw new EntityNotFoundException("No desks found for this state");
        }
    }

    public Desk deleteById(Long id) {
        if (deskRepository.findDeskById(id) != null) {
            Desk desk = deskRepository.findDeskById(id);
            deskRepository.deleteById(id);
            return desk;
        } else {
            throw new EntityNotFoundException("Can not delete desk by id = " + id);
        }
    }

    public Desk updateDeskStateToNotAvailable(Desk desk) {

        Desk deskNotAvailableState = deskRepository.findDeskById(desk.getId());
        deskNotAvailableState.setDeskState(DeskState.NOT_AVAILABLE);
        desk = deskNotAvailableState;
        return deskRepository.save(desk);
    }

    public Desk updateDeskStateToAvailable(Desk desk) {

        Desk deskNotAvailableState = deskRepository.findDeskById(desk.getId());
        deskNotAvailableState.setDeskState(DeskState.AVAILABLE);

        return deskRepository.save(desk);
    }
}
