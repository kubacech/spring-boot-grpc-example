package com.github.kubacech.springgrpc.api;

import com.github.kubacech.springgrpc.SpringGrpcExampleApp;
import com.github.kubacech.springgrpc.api.grpc.CreateOrderRequest;
import com.github.kubacech.springgrpc.api.grpc.OrderApiGrpc;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class GrpcServerTest {

    private int port = 4044;

    private OrderApiGrpc.OrderApiBlockingStub stub;

    @Before
    public void init() throws Exception {
        SpringGrpcExampleApp.main(new String[0]);

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port)
                .usePlaintext(true)
                .build();
        stub = OrderApiGrpc.newBlockingStub(channel);
    }

    @Test
    public void tryCallGrpc() {
       Empty e = stub.create(CreateOrderRequest.newBuilder()
               .setAmount("1.001")
               .setPrice("1000.1")
               .setProductId("ABCDEF")
               .setTradeType("BUY")
               .setTransactionId("abcdef")
               .build());
       assertNotNull(e);
    }
}