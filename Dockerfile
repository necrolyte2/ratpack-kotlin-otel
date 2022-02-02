FROM openjdk:11-slim as builder

ENV OTEL_RESOURCE_ATTRIBUTES=service.name=ratpack-kotlin-otel
ENV OTEL_INSTRUMENTATION_KAFKA_ENABLED=false
ENV OTEL_EXPORTER_ZIPKIN_ENDPOINT=http://jaegerallinone:9411/api/v2/spans
ENV OTEL_PROPAGATORS=b3,tracecontext
ENV OTEL_METRICS_EXPORTER=none
ENV OTEL_INSTRUMENTATION_CASSANDRA_ENABLED=false
ENV OTEL_TRACES_EXPORTER=zipkin
ENV OTEL_INSTRUMENTATION_NETTY_ALWAYS_CREATE_SPAN=false
ENV RATPACK_SERVER__ADDRESS=0.0.0.0

COPY run.sh /
COPY build/distributions/*.tar /
COPY otel /otel
RUN cd /tmp/ \
    && tar xvf /*.tar \
    && mv ratpack-kotlin-otel* /src
WORKDIR /src

ENTRYPOINT ["/run.sh"]
