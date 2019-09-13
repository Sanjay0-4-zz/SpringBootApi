package com.example.secondMin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.secondMin.Constants.*;

@Service
public class SecondMinService {

    @Autowired
    private SecondMinRepository secondMinRepository;

    // array = [1 2 5 3]
    public SecondMin findSecondMin(String array) {
        SecondMin secondMin = new SecondMin();
        secondMin.setInput(array);

//check empty input given
        if (array == null || array.trim().length() == 0) {
            secondMin.setOutput(EMPTY_INPUT_ERROR);
            secondMin.setStatus(FAILED);
            return saveSecondMinEntry(secondMin);
        }

        String[] num = array.split(" ");

        secondMin = validateArray(num, secondMin);
        if (secondMin.getStatus() != null)
            return secondMin;

        Double smin = getSecondMin(num);
        secondMin.setOutput(smin.toString());
        secondMin.setStatus(SUCCESS);
        return saveSecondMinEntry(secondMin);
    }

    public SecondMin validateArray(String[] num, SecondMin secondMin) {
//check only one value in array
        if (num.length == 1) {
            secondMin.setOutput(ONE_INPUT_ERROR);
            secondMin.setStatus(FAILED);
            return saveSecondMinEntry(secondMin);
        }
// check if all array elements are double
        if (!areAllDouble(num)) {
            secondMin.setOutput(NUMERIC_ERROR);
            secondMin.setStatus(FAILED);
            return saveSecondMinEntry(secondMin);
        }
//check all array element are same or not
        if (areAllEqual(num)) {
            secondMin.setOutput(TWO_DIFF_NUMBER_ERROR);
            secondMin.setStatus(FAILED);
            return saveSecondMinEntry(secondMin);
        }
        return secondMin;
    }

    public Double getSecondMin(String[] num) {
//find second minimum number
        Double min, smin;
        min = Double.MAX_VALUE;
        smin = Double.MAX_VALUE;
        for (int i = 0; i < num.length; ++i) {
            Double a = Double.parseDouble(num[i]);
            if (Double.compare(a, min) < 0) {
                smin = min;
                min = a;
            } else if (Double.compare(smin, a) > 0 && Double.compare(a, min) != 0) {
                smin = a;
            }
        }
        return smin;
    }
//1 1 1 1
    public boolean areAllEqual(String[] s) {
        Double first = Double.parseDouble(s[0]);
        for (int i = 1; i < s.length; ++i) {
            Double val = Double.parseDouble(s[i]);
            if (!val.equals(first))
                return false;
        }
        return true;
    }

    public boolean areAllDouble(String[] s) {
        for (int i = 0; i < s.length; ++i) {
            try {
                Double.parseDouble(s[i]);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public List<SecondMin> getHistory() {
        return secondMinRepository.findAll();
    }

    public SecondMin saveSecondMinEntry(SecondMin secondMin) {
        return secondMinRepository.save(secondMin);
    }
}