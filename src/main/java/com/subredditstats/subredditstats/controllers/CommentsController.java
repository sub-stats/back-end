package com.subredditstats.subredditstats.controllers;


import com.subredditstats.subredditstats.dtos.DateStats;
import com.subredditstats.subredditstats.models.DateStat;
import com.subredditstats.subredditstats.models.FieldSpec;
import com.subredditstats.subredditstats.services.CommentsService;
import com.subredditstats.subredditstats.services.DataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
public class CommentsController {
    private static final Logger logger = LoggerFactory.getLogger(CommentsController.class);

    @Autowired
    private DataGenerator dataGenerator;

    @Autowired
    private CommentsService commentsService;

    // - Comments per Post
    // - Time-series plots of comment counts
    // - Time-series plot of comments per post
    @CrossOrigin
    @GetMapping(value = "/comments", produces = {"application/json"})
    public ResponseEntity<?> getComments(@RequestParam String start,
                                      @RequestParam String end,
                                      @RequestParam String subreddit) throws ParseException {
        logger.info("/comments accessed with start: " + start + " end: " + end + " subreddit: " + subreddit);

        Map<String, Integer> dateToCommentsCount = commentsService.getNumCommentsPerDay(subreddit);
        Map<String, Double> dateToAverageCommentsPerPost =commentsService.getAverageCommentsPerPost(subreddit);

        FieldSpec field1 = new FieldSpec("averageCommentsPerPost", 30, false);
        FieldSpec field2 = new FieldSpec("commentsCount", 20000, true);
        DateStats dateStats = new DateStats(dataGenerator.getDateStats(start, end, subreddit,
                List.of(field1, field2)));

        for (DateStat ds : dateStats.getResults()) {
            if (dateToCommentsCount.containsKey(ds.getDate())) {
                ds.getData().put("commentsCount", Double.valueOf(dateToCommentsCount.get(ds.getDate())));
            }

            if (dateToAverageCommentsPerPost.containsKey(ds.getDate())) {
                ds.getData().put("averageCommentsPerPost", dateToAverageCommentsPerPost.get(ds.getDate()));
            }
        }
        return new ResponseEntity<>(dateStats, HttpStatus.OK);
    }

}
