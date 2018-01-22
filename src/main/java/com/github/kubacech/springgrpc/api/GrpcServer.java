package com.github.kubacech.springgrpc.api;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Service
public class GrpcServer {

    private Logger LOG = LoggerFactory.getLogger(GrpcServer.class);

    @Value("${server.port}")
    private Integer port;

    @Autowired
    private OrderApiImpl orderApi;

    private Server grpc;

    @PostConstruct
    protected void init() {
        ServerBuilder builder = ServerBuilder.forPort(port);
        builder.addService(orderApi);
        this.grpc = builder.build();

        try {
            grpc.start();
        } catch (IOException e) {
            LOG.error("Exception while starting GRPC", e);
            throw new RuntimeException(e);
        }
        LOG.info("GRPC server started.");
        await();
    }

    private void await() {
        Thread awaitThread = new Thread(
                "grpcContainer") {

            @Override
            public void run() {
                try {
                    GrpcServer.this.grpc.awaitTermination();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

        };
        awaitThread.setDaemon(false);
        awaitThread.start();
    }

    @PreDestroy
    protected void closeServer() {
        if (this.grpc != null) {
            this.grpc.shutdown();
            this.grpc = null;
            LOG.info("GRPC server stopped");
        }
    }
}
