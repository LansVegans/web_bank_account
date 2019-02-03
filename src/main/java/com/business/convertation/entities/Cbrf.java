package com.business.convertation.entities;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "metadata",
        "columns",
        "data"
})
public class Cbrf implements Serializable
{
    @JsonIgnore
    @JsonProperty("metadata")
    public Metadata metadata;
    @JsonProperty("columns")
    public List<String> columns = new ArrayList<String>();
    @JsonProperty("data")
    public List<List<String>> data = new ArrayList<List<String>>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 7616369118525689519L;

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
    @JsonAnyGetter
    public List<String> getColumns() {
        return columns;
    }
    @JsonAnySetter
    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    @JsonAnyGetter
    public List<List<String>> getData() {
        return data;
    }
    @JsonAnySetter
    public void setData(List<List<String>> data) {
        this.data = data;
    }
}