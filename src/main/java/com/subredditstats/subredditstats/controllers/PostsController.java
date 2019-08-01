package com.subredditstats.subredditstats.controllers;

import com.subredditstats.subredditstats.dtos.DateStats;
import com.subredditstats.subredditstats.models.DateStat;
import com.subredditstats.subredditstats.models.FieldSpec;
import com.subredditstats.subredditstats.services.DataGenerator;
import com.subredditstats.subredditstats.services.PostsService;
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
public class PostsController {
    private static final Logger logger = LoggerFactory.getLogger(PostsController.class);

    @Autowired
    private PostsService postsService;

    @Autowired
    private DataGenerator dataGenerator;

    // - Posts per Window (of time)
    // - Time-series plots of post counts
    // - Time-series plots of post counts
    @CrossOrigin
    @GetMapping(value = "/posts", produces = {"application/json"})
    public ResponseEntity<?> getPosts(@RequestParam String start,
                                      @RequestParam String end,
                                      @RequestParam String subreddit) throws ParseException {
        logger.info("/posts accessed with start: " + start + " end: " + end + " subreddit: " + subreddit);

        Map<String, Integer> dateToPostsCount = postsService.getPostsCount(subreddit);

        FieldSpec fieldSpec = new FieldSpec("postsCount", 5000, true);
        DateStats dateStats = new DateStats(dataGenerator.getDateStats(start, end, subreddit, List.of(fieldSpec)));

        for (DateStat ds : dateStats.getResults()) {
            if (dateToPostsCount.containsKey(ds.getDate())) {
                ds.getData().put("postsCount", Double.valueOf(dateToPostsCount.get(ds.getDate())));
            }
        }
        return new ResponseEntity<>(dateStats, HttpStatus.OK);
    }
}
