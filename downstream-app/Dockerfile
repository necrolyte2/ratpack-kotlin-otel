FROM golang:1.16-alpine

RUN mkdir /src
COPY go.mod /src
COPY main.go /src
WORKDIR /src
RUN go build main.go

ENTRYPOINT ["./main"]
