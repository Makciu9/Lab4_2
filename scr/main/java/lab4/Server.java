
package test;
package Store;
import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;
import static akka.http.javadsl.server.Directives.*;
import java.util.concurrent.CompletionStage;
import akka.pattern.PatternsCS;
import akka.routing.RoundRobinPool;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import Store.GetMessage;
import Store.StoreActor;
import test.TestActor;
import test.TestPackageActor;
import test.TestPackageMessage;





import java.io.IOException;

public class Server {
    private ActorRef storeActor;
    private final String STORE_ACTOR = "storeActor";

    private ActorRef testPerformerRouter;
    private final String TEST_PERFORMER_ROUTER = "testPerformerRouter";

    private ActorRef testPackageActor;
    private final String TEST_PACKAGE_ACTOR = "testPackageActor";

    private static final String SERVER = "localhost";
    private static final int PORT = 8080;






    public static void main(String[] args) throws IOException {
        //ActorSystem system = ActorSystem.create("test")
        ActorSystem system = ActorSystem.create("routes");
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        Server instance = new Server(system);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =
                instance.createRoute(system).flow(system, materializer);

        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost(SERVER , PORT),
                materializer
        );
        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());

    }

    private Route createRoute(ActorSystem system) {
        return route(
            get(() ->
                    parameter("packageID", (packageID) ->
                    {
                        CompletionStage<Object> result = PatternsCs.ask(storeActor,
                new GetMessage(Integer.parseInt(packageID)), 5000);
                        return completeOKWithFuture(result, Jackson.marshaller());
    })),
                post(() -> entity(Jackson.unmarshaller(TestPackageMsg.class), msg -> {
        testPackageActor.tell(msg, ActorRef.noSender());
        return complete("Test started!");
    })));
    }

    private Server(final ActorSystem system) {
        storeActor = system.actorOf(Props.create(StoreActor.class), STORE_ACTOR);
        testPackageActor = system.actorOf(Props.create(TestPackageActor.class), TEST_PACKAGE_ACTOR);
        testPerformerRouter =  system.actorOf(new RoundRobinPool(5).props(Props.create(TestActor.class)), TEST_PERFORMER_ROUTER);
    }

}