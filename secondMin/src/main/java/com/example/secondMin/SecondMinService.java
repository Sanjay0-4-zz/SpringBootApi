package com.example.secondMin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecondMinService {

    @Autowired
    private SecondMinRepository secondMinRepository;

    // array = [1 2 5 3]
    public SecondMin findSecondMin(String array) {

        SecondMin secondMin = new SecondMin();
        secondMin.setInput(array);

        //check empty input given
        if (array==null || array.trim().length() == 0) {
            secondMin.setOutput("Please enter some numeric value");
            return saveSecondMinEntry(secondMin);
        }

        String[] num = array.split(" ");

        //check only one value in array
        if(num.length == 1) {
            secondMin.setOutput("Please enter more than one numeric value");
            return saveSecondMinEntry(secondMin);
        }

        // check if all array elements are double
        if (!areAllDouble(num)) {
            secondMin.setOutput("Please enter only numeric elements");
            return saveSecondMinEntry(secondMin);
        }

        //check all array element are same or not
        if(areAllEqual(num)){
            secondMin.setOutput("Please enter atleast two different number");
            return saveSecondMinEntry(secondMin);
        }

        //find second minimum number
        Double min, smin;
        min = Double.MAX_VALUE;
        smin = Double.MAX_VALUE;

        for (int i = 0; i < num.length; ++i) {
            Double a = Double.parseDouble(num[i]);
            if (a < min) {
                smin = min;
                min = a;
            } else if (smin > a) {
                smin = a;
            }
        }

        secondMin.setOutput(smin.toString());

        saveSecondMinEntry(secondMin);

        return secondMin;
    }

    public List<SecondMin> getHistory() {
        return secondMinRepository.findAll();
    }


    public boolean areAllEqual(String[] s)
    {
        String first=s[0];
        for(int i=1;i<s.length;++i)
        {
            if(!s[i].equals(first))
                return false;
        }
        return true;
    }

    public boolean areAllDouble(String[] s) {
        for(int i=0;i<s.length;++i)
        {
           try {
               Double.parseDouble(s[i]);
           } catch (NumberFormatException e) {
               return false;
           }
        }
        return true;
    }

    public SecondMin saveSecondMinEntry(SecondMin secondMin) {
        return secondMinRepository.save(secondMin);
    }
}
