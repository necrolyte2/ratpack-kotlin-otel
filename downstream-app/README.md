# Simple api to do timeouts easily

```
docker build -t testing . && docker run --rm -it -p 9999:9999 testing
```

```
curl http://localhost:9999/api/v1/timeout?duration=10s
```
