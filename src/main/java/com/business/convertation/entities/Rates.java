package com.business.convertation.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
        "cbrf"
})
public class Rates {

    @JsonProperty("cbrf")
    public Cbrf cbrf;

    public Cbrf getCbrf() {
        return cbrf;
    }
}
