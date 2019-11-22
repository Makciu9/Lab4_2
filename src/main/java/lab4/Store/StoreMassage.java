package Store;
import test.Test;
import java.util.ArrayList;

public class StoreMassage {
    public Integer getPackageID;
    private int packageID;
    private ArrayList<Test> tests;

    private final String PACKAGE_ID = "packageID";
    private final String TESTS = "tests";

    public StoreMassage(int packageID, ArrayList<Test> tests) {
        this.packageID = packageID;
        this.tests = tests;
    }

    public int getPackageID() {
        return packageID;
    }

    public ArrayList<Test> getTests() {
        return tests;
    }
}