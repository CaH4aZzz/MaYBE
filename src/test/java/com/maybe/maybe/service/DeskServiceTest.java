package com.maybe.maybe.service;

import com.maybe.maybe.dto.DeskDTO;
import com.maybe.maybe.entity.Desk;
import com.maybe.maybe.entity.enums.DeskState;
import com.maybe.maybe.repository.DeskRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DeskServiceTest {

    @Mock
    private DeskRepository deskRepository;

    private DeskService deskService;

    private Desk expectedDesk;

    private DeskDTO deskDTO;

    private Long id;

    private List expectedDeskList;

    @Before
    public void setUp() {
        deskService = new DeskService(deskRepository);
        id = 1L;
        expectedDesk = new Desk();
        expectedDesk.setId(id);
        expectedDesk.setName("name1");
        deskDTO = new DeskDTO("name1");
        expectedDeskList = new ArrayList<>();
        expectedDeskList.add(expectedDesk);
    }

    @Test
    public void findByIdTest() {
        when(deskRepository.findDeskById(id)).thenReturn(expectedDesk);

        Desk actualDesk = deskService.findById(id);

        assertEquals(expectedDesk.getName(), actualDesk.getName());
    }

    @Test(expected = EntityNotFoundException.class)
    public void findById_thenReturnException() {
        when(deskRepository.findDeskById(id)).thenReturn(null);

        deskService.findById(id);
    }

    @Test
    public void findAllTest() {
        when(deskRepository.findDeskById(id)).thenReturn(expectedDesk);
        when(deskRepository.findAll()).thenReturn(expectedDeskList);

        List<Desk> actualDeskList = deskService.findAll();

        assertArrayEquals(expectedDeskList.toArray(), actualDeskList.toArray());
    }

    @Test(expected = EntityNotFoundException.class)
    public void findAll_thenReturnException() {
        when(deskRepository.findDeskById(id)).thenReturn(null);

        deskService.findAll();
    }

    @Test
    public void createFromDTOTest() {
        when(deskRepository.save(new Desk())).thenReturn(expectedDesk);

        Desk actualDesk = deskService.createFromDTO(deskDTO);

        assertEquals(expectedDesk.getName(), actualDesk.getName());
    }

    @Test(expected = BeanCreationException.class)
    public void createFromDTO_thenReturnException() {
        when(deskRepository.findDeskByName(expectedDesk.getName())).thenReturn(expectedDesk);

        deskService.createFromDTO(deskDTO);
    }

    @Test
    public void updateByIdTest() {
        when(deskRepository.findDeskById(id)).thenReturn(expectedDesk);
        when(deskRepository.save(expectedDesk)).thenReturn(expectedDesk);

        Desk actualDesk = deskService.updateById(id, deskDTO);

        assertEquals(expectedDesk.getName(), actualDesk.getName());
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateById_thenReturnException() {
        when(deskRepository.findDeskById(id)).thenReturn(null);

        deskService.updateById(id, deskDTO);
    }

    @Test
    public void findAllByStateTest() {
        when(deskRepository.findAllByDeskState(DeskState.AVAILABLE)).thenReturn(expectedDeskList);

        List<Desk> actualDeskList = deskService.findAllByState(DeskState.AVAILABLE);

        assertArrayEquals(expectedDeskList.toArray(), actualDeskList.toArray());
    }

    @Test(expected = EntityNotFoundException.class)
    public void findAllByState_thenReturnException() {
        when(deskRepository.findAllByDeskState(DeskState.AVAILABLE)).thenReturn(new ArrayList<>());

        deskService.findAllByState(DeskState.AVAILABLE);
    }

    @Test
    public void deleteByIdTest() {
        when(deskRepository.findDeskById(id)).thenReturn(expectedDesk);

        Desk actualDesk = deskService.deleteById(id);

        assertEquals(expectedDesk.getName(), actualDesk.getName());
    }
}