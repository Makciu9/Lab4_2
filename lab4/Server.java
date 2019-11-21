

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
        testPackageActor = system.actorOf(Props.create(TestPackageActor.class),
    }

}