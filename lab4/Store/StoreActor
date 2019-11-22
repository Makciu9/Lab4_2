import java.util.ArrayList
import java.util.HashMap
import akka.actor.AbstractActor
import akka.japi.pf.ReceiveBuilder


class StoreActor : AbstractActor() {
    private val store = HashMap()
    //id res
    @Override
    fun createReceive(): Receive {
        return ReceiveBuilder.create()
                .match(StoreMessage::class.java, { m ->
                    if (!store.containsKey(m.getPackageID));
                    store.put(m.getPackageID(), m.getTests())
                    run {
                        val res = store.get(m.getPackageID)
                        res.addAll(m.getTests())
                        store.replace(m.getPackageID(), res)
                    }
                    System.out.println("receive message! " + m.toString())
                })
                .match(GetMessage::class.java, { req ->
                    sender().tell(
                            StoreMessage(req.getPackageID(), store.get(req.getPackageID())), self())
                }
                ).build()
    }
}

