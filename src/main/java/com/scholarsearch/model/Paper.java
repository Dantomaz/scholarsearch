package com.scholarsearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Paper(
    String paperId,
    String title,
    List<String> fieldsOfStudy,
    List<String> publicationTypes,
    String publicationDate,
    Tldr tldr,
    String url,
    @JsonProperty("abstract") String abstractText,
    Integer referenceCount,
    Integer citationCount,
    OpenAccessPdf openAccessPdf,
    List<Author> authors
) {

}
