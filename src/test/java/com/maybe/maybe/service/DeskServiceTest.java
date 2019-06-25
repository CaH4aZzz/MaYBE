package com.maybe.maybe.service;

import com.maybe.maybe.dto.DeskRequest;
import com.maybe.maybe.entity.Desk;
import com.maybe.maybe.repository.DeskRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Before
    public void setUp() {
        deskService = new DeskService(deskRepository);
        expectedDesk = new Desk();
        expectedDesk.setId(1L);
        expectedDesk.setName("name1");
    }

    @Test
    public void getDeskById() {
        Long id = 1L;
        when(deskRepository.findDeskById(id)).thenReturn(expectedDesk);

        Desk actualDesk = deskService.getDeskById(1L);

        assertEquals(expectedDesk.getName(), actualDesk.getName());
    }

    @Test
    public void getDeskList() {
        List<Desk> expectedDeskList = new ArrayList<>();
        expectedDeskList.add(expectedDesk);
        when(deskRepository.saveAll(expectedDeskList)).thenReturn(expectedDeskList);

        List<Desk> actualDeskList = deskService.getDeskList();

        assertArrayEquals(expectedDeskList.toArray(), actualDeskList.toArray());
    }

    @Test
    public void createDesk() {
        DeskRequest deskRequest = new DeskRequest("name1");

        Desk actualDesk = deskService.createDesk(deskRequest);

        assertEquals(expectedDesk.getId(), actualDesk.getId());
    }

    @Test
    public void updateDeskById() {
        Long id = 1L;
        DeskRequest deskRequest = new DeskRequest("name1Update");
        Desk expectedDesk = new Desk();
        expectedDesk.setId(1L);
        expectedDesk.setName("name1Update");
        deskRepository.save(expectedDesk);

        Desk actualDesk = deskService.updateDeskById(id, deskRequest);

        assertEquals(expectedDesk.getName(), actualDesk.getName());
    }
}