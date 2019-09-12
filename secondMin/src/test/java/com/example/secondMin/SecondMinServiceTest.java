package com.example.secondMin;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.example.secondMin.Constants.*;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecondMinServiceTest {

    @InjectMocks
    @Spy
    private SecondMinService secondMinService;

    @Mock
    private SecondMinRepository secondMinRepository;

    private SecondMin secondMin, nullSecondMin, secondMinArrayOneValue, secondMinStringArray, secondMinEqualValueArray;
    private List<SecondMin> secondMinList;
    private String array;

    @Before
    public void initialise() {

        array = "1 3 5 2";

        secondMin = new SecondMin();
        secondMin.setInput(array);
        secondMin.setOutput("2.0");
        secondMin.setStatus(SUCCESS);

        nullSecondMin = new SecondMin();
        nullSecondMin.setOutput(EMPTY_INPUT_ERROR);
        nullSecondMin.setStatus(FAILED);

        secondMinArrayOneValue = new SecondMin();
        secondMinArrayOneValue.setOutput(ONE_INPUT_ERROR);
        secondMinArrayOneValue.setStatus(FAILED);

        secondMinStringArray = new SecondMin();
        secondMinStringArray.setOutput(NUMERIC_ERROR);
        secondMinStringArray.setStatus(FAILED);

        secondMinEqualValueArray = new SecondMin();
        secondMinEqualValueArray.setOutput(TWO_DIFF_NUMBER_ERROR);
        secondMinEqualValueArray.setStatus(FAILED);

        secondMinList = new ArrayList<>();
        secondMinList.add(secondMin);
    }
    @Test
    public void testFindSecondMinWithNullArray() {
        Mockito.when(secondMinRepository.save(any())).thenReturn(nullSecondMin);

        SecondMin secondMinFound = secondMinService.findSecondMin(null);
        assertEquals(nullSecondMin.getOutput(),secondMinFound.getOutput());
        assertEquals(nullSecondMin.getStatus(),secondMinFound.getStatus());
    }

    @Test
    public void testFindSecondMin() {
        doReturn(secondMin).when(secondMinService).validateArray(any(),any());
        doReturn(2.0).when(secondMinService).getSecondMin(any());

        SecondMin secondMinFound = secondMinService.findSecondMin(array);
        assertEquals(array, secondMinFound.getInput());
        assertEquals("2.0", secondMinFound.getOutput());
        assertEquals(SUCCESS, secondMinFound.getStatus());
    }

    @Test
    public void testGetHistory() {
        Mockito.when(secondMinRepository.findAll()).thenReturn(secondMinList);

        List<SecondMin> secondMinListFound = secondMinService.getHistory();
        assertEquals(secondMinList.get(0).getInput(),secondMinListFound.get(0).getInput());
        assertEquals(secondMinList.get(0).getOutput(),secondMinListFound.get(0).getOutput());
        assertEquals(secondMinList.get(0).getStatus(),secondMinListFound.get(0).getStatus());
    }

    @Test
    public void testValidateArrayWithOneValue() {
        Mockito.when(secondMinRepository.save(any())).thenReturn(secondMinArrayOneValue);

        String[] num = {"1"};
        SecondMin secondMinFound = secondMinService.validateArray(num, secondMinArrayOneValue);
        assertEquals(secondMinArrayOneValue.getOutput(),secondMinFound.getOutput());
        assertEquals(secondMinArrayOneValue.getStatus(),secondMinFound.getStatus());
    }

    @Test
    public void testValidateArrayWithStringValue() {
        Mockito.when(secondMinRepository.save(any())).thenReturn(secondMinStringArray);

        String[] num = {"a", "b", "1"};
        SecondMin secondMinFound = secondMinService.validateArray(num, secondMinStringArray);
        assertEquals(secondMinStringArray.getOutput(),secondMinFound.getOutput());
        assertEquals(secondMinStringArray.getStatus(),secondMinFound.getStatus());
    }
    @Test
    public void testValidateArrayWithAllEqualValues() {
        Mockito.when(secondMinRepository.save(any())).thenReturn(secondMinEqualValueArray);

        String[] num = {"1", "1", "1"};
        SecondMin secondMinFound = secondMinService.validateArray(num, secondMinEqualValueArray);
        assertEquals(secondMinEqualValueArray.getOutput(),secondMinFound.getOutput());
        assertEquals(secondMinEqualValueArray.getStatus(),secondMinFound.getStatus());
    }

    @Test
    public void testGetSecondMin() {
        String[] num = {"1","2","3"};
        Double sminFound = secondMinService.getSecondMin(num);
        assertEquals("2.0", sminFound.toString());
    }

    @Test
    public void testAreAllEqualWithEqualArrayValues() {
        String[] num = {"1", "1", "1"};
        assertTrue(secondMinService.areAllEqual(num));
    }

    @Test
    public void testAreAllEqualWithDifferentArrayValues() {
        String[] num = {"1", "2", "1"};
        assertFalse(secondMinService.areAllEqual(num));
    }

    @Test
    public void testAreAllDoubleWithDoubleArrayValues() {
        String[] num = {"1.0", "2"};
        assertTrue(secondMinService.areAllDouble(num));
    }

    @Test
    public void testAreAllDoubleWithNonDoubleArrayValues() {
        String[] num = {"1.0", "2", "a"};
        assertFalse(secondMinService.areAllDouble(num));
    }
}


