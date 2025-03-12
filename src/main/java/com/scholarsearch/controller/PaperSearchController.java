package com.scholarsearch.controller;

import com.scholarsearch.service.PaperSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/paper")
public class PaperSearchController {

    private final PaperSearchService paperSearchService;

    @GetMapping("/search")
    public ResponseEntity<String> getPapersByRelevance(
        @RequestParam("query") String query,
        @RequestParam(value = "offset", defaultValue = "0") int offset
    ) {
        String response = paperSearchService.getPapersByRelevance(query, offset);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getPaperDetails(@PathVariable("id") String id) {
        String response = paperSearchService.getPaperDetails(id);
        return ResponseEntity.ok(response);
    }
}
