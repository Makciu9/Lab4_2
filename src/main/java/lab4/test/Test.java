package test;


import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
@JsonAutoDetect
public class Test {

    private String testName;
    private String expectedResult;
    private ArrayList<Integer> params;
    private boolean result;

    public Test(String testName, String expectedResult, ArrayList<Integer> params, boolean result){
        this.testName=testName;
        this.expectedResult=expectedResult;
        this.params=params;
        this.result=result;
    }
   public String getTestName() {
        return this.testName;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public ArrayList<Integer> getParams() {
        return params;
    }

}