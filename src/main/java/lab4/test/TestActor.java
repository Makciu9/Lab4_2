package test;

import Store.StoreMassage;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import test.Test;
import test.TestMsg;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;

public class TestActor extends AbstractActor {

    public Receive createReceive() {
        ActorRef storeActor = null;
        return ReceiveBuilder.create()
                .match(TestMsg.class, m -> storeActor.tell(new StoreMassage(m.getPackageID(),
                        runTest(m.getJsScript(), m.getTest().getTestName(), m.getTest().getParams(),
                                m.getFunctionName(), m.getTest().getExpectedResult())),self()))
                .build();
    }


    private ArrayList<Test>   runTest(String proga, String testName, ArrayList<Integer> params, String functionName, String expectedResult ) throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(proga);
        Invocable invocable = (Invocable) engine;
        String resTmp = invocable.invokeFunction(functionName, params.toArray()).toString();
        ArrayList<Test> res = new ArrayList<>();
        Test test = new Test(testName, expectedResult, params, resTmp.equals(expectedResult));
        res.add(test);
        return res;
    }

}