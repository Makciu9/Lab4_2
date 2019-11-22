package test;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.japi.pf.ReceiveBuilder;
import test.Test;
import test.TestMsg;
import test.TestPackageMsg;

class TestPackageActor extends AbstractActor {
    private ActorSelection testPerformerRouter = getContext().actorSelection("/user/testPerformerRouter");
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestPackageMsg.class, m -> {
                    for(Test test: m.getTests()) {
                        testPerformerRouter.tell(new TestMsg(m.getPackageID(), m.getJsScript(), m.getFunctionName(), test),
                                self());
                    }
                })
                .build();

    }
}