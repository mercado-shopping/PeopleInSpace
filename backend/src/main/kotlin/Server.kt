import io.grpc.examples.helloworld.HelloReply
import io.grpc.examples.helloworld.HelloRequest

import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.examples.helloworld.GreeterGrpcKt
import io.vertx.core.AbstractVerticle
import io.vertx.core.Context
import io.vertx.core.Vertx

class HelloWorldServer: AbstractVerticle() {
    private val port: Int = 12345
    val server: Server = ServerBuilder
        .forPort(port)
        .addService(GreeterService())
        .build()

    override fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down gRPC server since JVM is shutting down")
                this@HelloWorldServer.stop()
                println("*** server shut down")
            }
        )
    }

    override fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }

    class GreeterService : GreeterGrpcKt.GreeterCoroutineImplBase() {
        override suspend fun sayHello(request: HelloRequest) = HelloReply
            .newBuilder()
            .setMessage("Hello ${request.name}")
            .build()
    }
}


fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 50051
    val server = HelloWorldServer()
    server.start()
    server.blockUntilShutdown()
}