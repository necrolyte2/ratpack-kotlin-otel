package exampleapp.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Geo(
    @JsonProperty("lat")
    val lat: String = "",
    @JsonProperty("lng")
    val lng: String = ""
)