package com.maybe.maybe.service;

import com.maybe.maybe.dto.DeskRequest;
import com.maybe.maybe.entity.Desk;
import com.maybe.maybe.repository.DeskRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeskServiceTest {

    @Autowired
    private DeskRepository deskRepository;

    private DeskService deskService;

    @Before
    public void setUp(){
        deskService = new DeskService(deskRepository);
    }

    @Test
    public void getCustomerById() {
        Desk expectedDesk = new Desk();
        expectedDesk.setId(1L);
        expectedDesk.setName("name1");
        deskRepository.save(expectedDesk);

        Desk actualDesk = deskService.getCustomerById(1L);

        assertEquals(expectedDesk, actualDesk);
    }

    @Test
    public void getCustomerList() {
        List<Desk> expectedDeskList = new ArrayList<>();
        Desk deskOne = new Desk();
        Desk deskTwo = new Desk();
        deskOne.setId(1L);
        deskOne.setName("name1");
        deskTwo.setId(2L);
        deskTwo.setName("name2");
        expectedDeskList.add(deskOne);
        expectedDeskList.add(deskTwo);
        deskRepository.saveAll(expectedDeskList);

        List<Desk> actualDeskList = deskService.getCustomerList();

        assertArrayEquals(expectedDeskList.toArray(), actualDeskList.toArray());
    }

    @Test
    public void createCustomer() {
        DeskRequest deskRequest = new DeskRequest("name1");
        Desk expectedDesk = new Desk();
        expectedDesk.setId(5L);
        expectedDesk.setName("name5");

        Desk actualDesk = deskService.createCustomer(deskRequest);

        assertEquals(expectedDesk.getId(), actualDesk.getId());
    }

    @Test
    public void createCustomerList() {
        List<DeskRequest> deskRequestsList = new ArrayList<>();
        DeskRequest deskRequestOne = new DeskRequest("name1");
        DeskRequest deskRequestTwo = new DeskRequest("name2");
        deskRequestsList.add(deskRequestOne);
        deskRequestsList.add(deskRequestTwo);
        List<Desk> expectedDeskList = new ArrayList<>();
        Desk deskOne = new Desk();
        Desk deskTwo = new Desk();
        deskOne.setId(3L);
        deskOne.setName("name3");
        deskTwo.setId(4L);
        deskTwo.setName("name4");
        expectedDeskList.add(deskOne);
        expectedDeskList.add(deskTwo);

        List<Desk> actualDeskList = deskService.createCustomerList(deskRequestsList);

        assertEquals(expectedDeskList.get(0).getId(), actualDeskList.get(0).getId());
    }

    @Test
    public void updateCustomerById() {
        Long id = 1L;
        DeskRequest deskRequest = new DeskRequest("name1Update");
        Desk expectedDesk = new Desk();
        expectedDesk.setId(1L);
        expectedDesk.setName("name1Update");
        deskRepository.save(expectedDesk);

        Desk actualDesk = deskService.updateCustomerById(id, deskRequest);

        assertEquals(expectedDesk.getName(), actualDesk.getName());
    }
}