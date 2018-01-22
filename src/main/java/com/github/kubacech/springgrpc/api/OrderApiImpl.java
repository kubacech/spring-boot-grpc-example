package com.github.kubacech.springgrpc.api;

import com.github.kubacech.springgrpc.api.grpc.CreateOrderRequest;
import com.github.kubacech.springgrpc.api.grpc.OrderApiGrpc;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class OrderApiImpl extends OrderApiGrpc.OrderApiImplBase {

    @Override
    public void create(CreateOrderRequest request, StreamObserver<Empty> responseObserver) {
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

}
