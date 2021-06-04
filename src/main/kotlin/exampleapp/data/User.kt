package exampleapp.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
    @JsonProperty("address")
    val address: Address = Address(),
    @JsonProperty("company")
    val company: Company = Company(),
    @JsonProperty("email")
    val email: String = "",
    @JsonProperty("id")
    val id: Int = 0,
    @JsonProperty("name")
    val name: String = "",
    @JsonProperty("phone")
    val phone: String = "",
    @JsonProperty("username")
    val username: String = "",
    @JsonProperty("website")
    val website: String = ""
)