package com.scholarsearch.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@ConfigurationProperties(prefix = "custom.semantic-scholar.paper")
@Data
@RequiredArgsConstructor
@Service
public class PaperSearchService {

    private final RestClient restClient;
    private String relevanceSearchUrl;
    private String detailsSearchUrl;

    public String getPapersByRelevance(String query, int offset) {
        int RESULTS_LIMIT = 25;

        String[] RELEVANCE_FIELDS = new String[]{
            "title",
            "fieldsOfStudy",
            "publicationTypes",
            "publicationDate",
            "tldr"
        };

        URI uri = UriComponentsBuilder
            .fromUriString(relevanceSearchUrl.concat("?query={query}&fields={fields}&offset={offset}&limit={limit}"))
            .build(query, RELEVANCE_FIELDS, offset, RESULTS_LIMIT);

        return restClient.get().uri(uri).accept(MediaType.APPLICATION_JSON).retrieve().body(String.class);
    }

    public String getPaperDetails(String id) {
        String[] DETAILS_FIELDS = new String[]{
            "url",
            "abstract",
            "referenceCount",
            "citationCount",
            "openAccessPdf",
            "authors"
        };

        URI uri = UriComponentsBuilder
            .fromUriString(detailsSearchUrl.concat("?fields={fields}"))
            .build(id, DETAILS_FIELDS);

        return restClient.get().uri(uri).accept(MediaType.APPLICATION_JSON).retrieve().body(String.class);
    }
}
