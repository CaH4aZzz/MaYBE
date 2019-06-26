package com.maybe.maybe.service;

import com.maybe.maybe.dto.DeskRequest;
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

    private DeskRequest deskRequest;

    @Before
    public void setUp() {
        deskService = new DeskService(deskRepository);
        expectedDesk = new Desk();
        expectedDesk.setId(1L);
        expectedDesk.setName("name1");
        deskRequest = new DeskRequest("name1");
    }

    @Test
    public void getDeskByIdTest() {
        Long id = 1L;
        when(deskRepository.findDeskById(id)).thenReturn(expectedDesk);

        Desk actualDesk = deskService.getDeskById(id);

        assertEquals(expectedDesk.getName(), actualDesk.getName());
    }

    @Test
    public void getDeskListTest() {
        List<Desk> expectedDeskList = new ArrayList<>();
        expectedDeskList.add(expectedDesk);
        when(deskRepository.findAll()).thenReturn(expectedDeskList);

        List<Desk> actualDeskList = deskService.getDeskList();

        assertArrayEquals(expectedDeskList.toArray(), actualDeskList.toArray());
    }

    @Test
    public void createDeskTest() {
        when(deskRepository.save(new Desk())).thenReturn(expectedDesk);

        Desk actualDesk = deskService.createDesk(deskRequest);

        assertEquals(expectedDesk.getName(), actualDesk.getName());
    }

    @Test
    public void updateDeskByIdTest() {
        Long id = 1L;
        when(deskRepository.findDeskById(id)).thenReturn(expectedDesk);
        when(deskRepository.save(expectedDesk)).thenReturn(expectedDesk);

        Desk actualDesk = deskService.updateDeskById(id, deskRequest);

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