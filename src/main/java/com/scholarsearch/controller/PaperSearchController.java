package com.scholarsearch.controller;

import com.scholarsearch.model.Paper;
import com.scholarsearch.model.PaperRelevanceResponse;
import com.scholarsearch.service.PaperSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.scholarsearch.service.PaperSearchService.RESULTS_LIMIT_PER_PAGE;
import static com.scholarsearch.service.PaperSearchService.RESULT_LIMIT_FOR_API;

@RequiredArgsConstructor
@Controller
@RequestMapping("/papers")
public class PaperSearchController {

    private final PaperSearchService paperSearchService;

    @GetMapping("/search")
    public String getPapersByRelevance(@RequestParam("query") String query, @RequestParam("page") int page, Model model) {
        if (query == null || query.isBlank()) {
            return "redirect:/";
        }

        PaperRelevanceResponse response = paperSearchService.getPapersByRelevance(query, page);

        model.addAttribute("currentPage", response.offset() / RESULTS_LIMIT_PER_PAGE + 1);
        model.addAttribute("totalPages", RESULT_LIMIT_FOR_API / RESULTS_LIMIT_PER_PAGE);
        model.addAttribute("papers", response.papers());

        return "papers";
    }

    @GetMapping("/{id}")
    public String getPaperDetails(@PathVariable("id") String id, Model model) {
        Paper paper = paperSearchService.getPaperDetails(id);
        model.addAttribute("paper", paper);
        return "paperDetails";
    }
}
