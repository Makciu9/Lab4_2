
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;


public class TestPackageMsg {
    private final String PACKAGE_ID = "packageID";
    private final String JSSCRIPT = "jsScript";
    private final String FUNCTION_NAME = "functionName";
    private final String TESTS = "tests";

    @JsonProperty(PACKAGE_ID)
    private int packageID;
    @JsonProperty(JSSCRIPT)
    private String jsScript;
    @JsonProperty(FUNCTION_NAME)
    private String functionName;
    private ArrayList<Test> tests;

    public class TestPackageMsg(int packageID, String jsScript,
    String functionName, ArrayList<Test> tests) {
        this.packageID = packageID;
        @JsonProperty
        this.jsScript = jsScript;
        @JsonProperty
        this.functionName = functionName;
        @JsonProperty
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