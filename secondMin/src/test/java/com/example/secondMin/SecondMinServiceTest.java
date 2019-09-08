package com.example.secondMin;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecondMinServiceTest {

    @InjectMocks
    private SecondMinService secondMinService;

    @Mock
    private SecondMinRepository secondMinRepository;

    private SecondMin secondMin;
    private List<SecondMin> secondMinList;
    private String array;

    @Before
    public void initialise() {

        array = "1 3 5 2";

        secondMin = new SecondMin();
        secondMin.setInput(array);
        secondMin.setOutput("2.0");

        secondMinList = new ArrayList<>();
        secondMinList.add(secondMin);

        Mockito.when(secondMinRepository.save(secondMin)).thenReturn(secondMin);
        Mockito.when(secondMinRepository.findAll()).thenReturn(secondMinList);
    }

    @Test
    public void testFindSecondMin() {
        SecondMin secondMinFound = secondMinService.findSecondMin(array);
        assertEquals(array, secondMinFound.getInput());
        assertEquals("2.0", secondMinFound.getOutput());
    }

    @Test
    public void testGetHistory() {
        List<SecondMin> secondMinListFound = secondMinService.getHistory();
        assertEquals(secondMinList.get(0).getInput(),secondMinListFound.get(0).getInput());
        assertEquals(secondMinList.get(0).getOutput(),secondMinListFound.get(0).getOutput());
    }
}
