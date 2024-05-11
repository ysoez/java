package grpc.interceptor;

import com.google.common.annotations.VisibleForTesting;
import io.grpc.*;
import io.grpc.ForwardingClientCall.SimpleForwardingClientCall;
import io.grpc.ForwardingClientCallListener.SimpleForwardingClientCallListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaderClientInterceptor implements ClientInterceptor {

    @VisibleForTesting
    static final Metadata.Key<String> CUSTOM_HEADER_KEY = Metadata.Key.of("custom_client_header_key", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
                                                               CallOptions callOptions, Channel next) {
        return new SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.put(CUSTOM_HEADER_KEY, "customRequestValue");
                super.start(new SimpleForwardingClientCallListener<>(responseListener) {
                    @Override
                    public void onHeaders(Metadata headers) {
                        /**
                         * if you don't need receive header from server,
                         * you can use {@link io.grpc.stub.MetadataUtils#attachHeaders}
                         * directly to send header
                         */
                        log.info("Received header from server: {}", headers);
                        super.onHeaders(headers);
                    }
                }, headers);
            }
        };
    }
}