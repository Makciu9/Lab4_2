package Store;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import test.Test;
import java.util.ArrayList;

public class StoreMassage {
    public Integer getPackageID;
    private int packageID;
    private ArrayList<Test> tests;

    private final String PACKAGE_ID = "packageID";
    private final String TESTS = "tests";

    @JsonCreator
    public StoreMassage(@JsonProperty(PACKAGE_ID) int packageID, @JsonProperty(TESTS) ArrayList<Test> tests) {
        this.packageID = packageID;
        this.tests = tests;
    }
    @Override
    public String toString() {
        
    }
    public int getPackageID() {
        return packageID;
    }

    public ArrayList<Test> getTests() {
        return tests;
    }
}