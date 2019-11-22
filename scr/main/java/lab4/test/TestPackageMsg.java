
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;



public class TestPackageMsg {
    
    private int packageID;
    private String jsScript;
    private String functionName;
    private ArrayList<Test> tests;

    public class TestPackageMsg(int packageID, String jsScript,
    String functionName, ArrayList<Test> tests) {
        this.packageID = packageID;
        this.jsScript = jsScript;
        this.functionName = functionName;
        this.tests = tests;

    }

    int getPackageID() {
        return packageID;
    }

    String getJsScript() {
        return jsScript;
    }

    String getFunctionName() {
        return functionName;
    }

    ArrayList<Test> getTests() {
        return tests;
    }


}