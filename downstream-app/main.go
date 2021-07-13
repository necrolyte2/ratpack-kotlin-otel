package main

import (
	"log"
	"net/http"
	"time"
)

func timeoutHandler(w http.ResponseWriter, r *http.Request) {
	var timeout time.Duration
	var err error

	keys, ok := r.URL.Query()["duration"]

	if !ok || len(keys[0]) < 1 {
		timeout = 60 * time.Second
	} else {
		timeout, err = time.ParseDuration(keys[0])
		if err != nil {
			timeout = 60 * time.Second
		}
	}
	log.Printf("requested to timeout after %v", timeout)
	time.Sleep(timeout)
	w.WriteHeader(200)
}

func addRoutes() {
	http.HandleFunc("/api/v1/timeout", timeoutHandler)
}

func main() {
	addRoutes()
	log.Printf("starting server on :9999")
	log.Fatal(http.ListenAndServe(":9999", nil))
}
