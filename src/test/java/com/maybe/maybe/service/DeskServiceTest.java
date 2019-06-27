package com.maybe.maybe.service;

import com.maybe.maybe.dto.DeskDTO;
import com.maybe.maybe.entity.Desk;
import com.maybe.maybe.entity.enums.DeskState;
import com.maybe.maybe.repository.DeskRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

    @Before
    public void setUp() {
        deskService = new DeskService(deskRepository);
        expectedDesk = new Desk();
        expectedDesk.setId(1L);
        expectedDesk.setName("name1");
        deskDTO = new DeskDTO("name1");
    }

    @Test
    public void getDeskByIdTest() {
        Long id = 1L;
        when(deskRepository.findDeskById(id)).thenReturn(expectedDesk);

        Desk actualDesk = deskService.findById(id);

        assertEquals(expectedDesk.getName(), actualDesk.getName());
    }

    @Test
    public void getDeskListTest() {
        List<Desk> expectedDeskList = new ArrayList<>();
        expectedDeskList.add(expectedDesk);
        when(deskRepository.findAll()).thenReturn(expectedDeskList);

        List<Desk> actualDeskList = deskService.findAll();

        assertArrayEquals(expectedDeskList.toArray(), actualDeskList.toArray());
    }

    @Test
    public void createFromDTOTest() {
        when(deskRepository.save(new Desk())).thenReturn(expectedDesk);

        Desk actualDesk = deskService.createFromDTO(deskDTO);

        assertEquals(expectedDesk.getName(), actualDesk.getName());
    }

    @Test
    public void updateByIdTest() {
        Long id = 1L;
        when(deskRepository.findDeskById(id)).thenReturn(expectedDesk);
        when(deskRepository.save(expectedDesk)).thenReturn(expectedDesk);

        Desk actualDesk = deskService.updateById(id, deskDTO);

        assertEquals(expectedDesk.getName(), actualDesk.getName());
    }

    @Test
    public void getDesksByStateTest() {
        List<Desk> expectedDesks = new ArrayList<>();
        for(DeskState state : DeskState.values()) {
            expectedDesk.setDeskState(state);
            expectedDesks.add(expectedDesk);
            when(deskRepository.findAllByDeskState(state)).thenReturn(expectedDesks);

            List<Desk> actualDesks = deskService.getDesksByState(state);

            assertEquals(expectedDesks.get(0), actualDesks.get(0));
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void getDesksByStateTest_NoDesksReserved() throws EntityNotFoundException {
        DeskState state = DeskState.RESERVED;
        expectedDesk.setDeskState(DeskState.AVAILABLE);
        deskService.getDesksByState(state);
    }
}