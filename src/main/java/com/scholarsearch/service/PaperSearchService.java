package com.scholarsearch.service;

import com.scholarsearch.model.Paper;
import com.scholarsearch.model.PaperRelevanceResponse;
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

    public static final int RESULTS_LIMIT_PER_PAGE = 25;
    public static final int RESULT_LIMIT_FOR_API = 1000;
    private final RestClient restClient;

    private String relevanceSearchUrl;
    private String detailsSearchUrl;

    public PaperRelevanceResponse getPapersByRelevance(String query, int page) {
        int offset = (page - 1) * RESULTS_LIMIT_PER_PAGE;

        String[] RELEVANCE_FIELDS = new String[]{
            "title",
            "fieldsOfStudy",
            "publicationTypes",
            "publicationDate",
            "tldr"
        };

        URI uri = UriComponentsBuilder
            .fromUriString(relevanceSearchUrl.concat("?query={query}&fields={fields}&offset={offset}&limit={limit}"))
            .build(query, RELEVANCE_FIELDS, offset, RESULTS_LIMIT_PER_PAGE);

        return restClient.get().uri(uri).accept(MediaType.APPLICATION_JSON).retrieve().body(PaperRelevanceResponse.class);
    }

    public Paper getPaperDetails(String id) {
        String[] DETAILS_FIELDS = new String[]{
            "url",
            "abstract",
            "referenceCount",
            "citationCount",
            "openAccessPdf",
            "authors"
        };

        URI uri = UriComponentsBuilder.fromUriString(detailsSearchUrl.concat("?fields={fields}")).build(id, DETAILS_FIELDS);

        return restClient.get().uri(uri).retrieve().body(Paper.class);
    }
}
