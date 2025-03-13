package com.scholarsearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PaperRelevanceResponse(String total, Integer offset, Integer next, @JsonProperty("data") List<Paper> papers) {

}
