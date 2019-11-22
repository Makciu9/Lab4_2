


import java.util.ArrayList;
import java.util.HashMap;
import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import java.util.HashMap;
import java.util.Map;


public class StoreActor extends AbstractActor {
    private HashMap  <Integer, ArrayList<Test>> store = new HashMap<>();
    //id res
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(StoreMessage.class, m -> {
                    if (!store.containsKey(m.getPackageID)))
                       store.put(m.getPackageID(), m.getTests());
                    else{
                        ArrayList<Test> res = store.get(m.getPackageID)
                        res.addAll(m.getTests());
                        store.replace(m.getPackageID(), res)
                    }
                    System.out.println("receive message! " + m.toString());
                })
                .match(GetMessage.class, req -> sender().tell(
                        new StoreMessage(req.getPackageID(), store.get(req.getPackageID())), self())
                ).build();
    }
}

