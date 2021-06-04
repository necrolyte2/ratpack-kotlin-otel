package exampleapp.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Company(
    @JsonProperty("bs")
    val bs: String = "",
    @JsonProperty("catchPhrase")
    val catchPhrase: String = "",
    @JsonProperty("name")
    val name: String = ""
)