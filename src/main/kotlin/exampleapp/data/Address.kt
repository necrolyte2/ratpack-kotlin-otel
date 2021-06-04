package exampleapp.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Address(
    @JsonProperty("city")
    val city: String = "",
    @JsonProperty("geo")
    val geo: Geo = Geo(),
    @JsonProperty("street")
    val street: String = "",
    @JsonProperty("suite")
    val suite: String = "",
    @JsonProperty("zipcode")
    val zipcode: String = ""
)