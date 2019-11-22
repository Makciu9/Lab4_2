package test;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import test.Test;
import test.TestMsg;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;

public class TestActor extends AbstractActor {

    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestMsg.class, m -> storeActor.tell(new StoreMessage(m.getPackageID(),
                        ruTest(m.getJsScript(), m.getTest().getTestName(), m.getTest().getParams(),
                                m.getFunctionName(), m.msg.getTest().getExpectedResult())),self()))
                .build();
    }


    private ArrayList<Test>   runTest(String proga, String testName, ArrayList<Integer> params, String functionName, String expectedResult ) {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(proga);
        Invocable invocable = (Invocable) engine;
        String resTmp = invocable.invokeFunction(functionName, params.toArray).toString();
        ArrayList<Test> res = new ArrayList<>();
        Test test = new Test(testName, expectedResult, params, resTmp.equals(expectedResult));
        res.add(test);
        return res;
    }

}