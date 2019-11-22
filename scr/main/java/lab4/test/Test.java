package test;


public class Test {

    private String testName;
    private String expectedResult;
    private ArrayList<Integer> params;
    private boolean result;

    public Test(String testName, String expectedResult, ArrayList<Integer> params){
        this.testName=testName;
        this.expectedResult=expectedResult;
        this.params=params;
        this.result=false;
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