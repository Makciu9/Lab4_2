package test;

public class TestMsg {

    private int packageID;
    private String jsScript;
    private String functionName;
    private Test test;

    TestMsg(int packageID, String jsScript, String functionName, Test test){
        this.packageID=packageID;
        this.jsScript=jsScript;
        this.functionName=functionName;
        this.test=test;
    }
    public int getPackageID() {
        return packageID;
    }

    public String getJsScript() {
        return jsScript;
    }

    public String getFunctionName() {
        return functionName;
    }

    public Test getTest() {
        return test;
    }

}