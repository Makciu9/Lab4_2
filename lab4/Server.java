

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
    route(
            path("semaphore", () ->
    route(
            get( () -> {
        Future<Object> result = Patterns.ask(testPackageActor,
                SemaphoreActor.makeRequest(), 5000);
        return completeOKWithFuture(result, Jackson.marshaller());
    }))),
    path("test", () ->
    route(
            post(() ->
    entity(Jackson.unmarshaller(TestPackageMsg.class), msg -> {
        testPackageActor.tell(msg, ActorRef.noSender());
        return complete("Test started!");
    })))),
    path("put", () ->
    get(() ->
    parameter("key", (key) ->
    parameter("value", (value) ->
    {
        storeActor.tell(new StoreActor.StoreMessage(key, value), ActorRef.noSender());
        return complete("value saved to store ! key=" + key + " value=" + value);
    })))),
}