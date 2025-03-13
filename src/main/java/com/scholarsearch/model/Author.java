package com.scholarsearch.model;

import java.util.List;

public record Author(
    String authorId,
    String url,
    String name,
    List<String> affiliations,
    String homepage,
    String paperCount,
    String citationCount,
    String hIndex
) {

}
