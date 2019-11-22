
package lab4;
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
import lab4.Store.GetMessage;
import lab4.Store.StoreActor;
import lab4.Test.TestActor;
import lab4.Test.TestPackageActor;
import lab4.





import java.io.IOException;

public class Server {


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
                ConnectHttp.toHost("localhost", 8080),
                materializer
        );
        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());

    }
    private Route createRoute() {
        return route(
            get( () ->
            parameter("packageID", (packageID) ->
            {
        Future<Object> result = Patterns.ask(storeActor,
                new GetMessage(Integer.parseInt(packageID)), 5000);
        return completeOKWithFuture(result, Jackson.marshaller());
    }))),
    path("test", () ->
    route(
            post(() ->
    entity(Jackson.unmarshaller(TestPackageMsg.class), msg -> {
        testPackageActor.tell(msg, ActorRef.noSender());
        return complete("Test started!");
    })))),
    }

    private Server(final ActorSystem system) {
        storeActor = system.actorOf(Props.create(StoreActor.class), "storeActor");
        testPackageActor = system.actorOf(Props.create(TestPackageActor.class), "testPackageActor");
        testPerformerRouter =  system.actorOf(new RoundRobinPool(5).props(Props.create(TestActor.class)), "testPerfomerRouter");
    }

}