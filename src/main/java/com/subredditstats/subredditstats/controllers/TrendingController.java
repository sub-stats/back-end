package com.subredditstats.subredditstats.controllers;

import com.subredditstats.subredditstats.dtos.TrendingStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class TrendingController {
    private static final Logger logger = LoggerFactory.getLogger(TrendingController.class);

    private static final Map<String, Integer> subredditToNumberOfFollowers;
    private static final Map<String, Integer> subredditToAgeInDays;

    // This initialization is inspired by StackOverflow post: https://stackoverflow.com/a/507658/3732147
    static {
        Map<String, Integer> temp = new HashMap<>();
        temp.put("askscience", 17886513);
        temp.put("AskEngineers", 132839);
        temp.put("AskHistorians", 996698);
        temp.put("AskReddit", 23821425);
        temp.put("AskComputerScience", 35079);
        temp.put("AskCulinary", 225505);
        temp.put("AskSocialScience", 84534);
        temp.put("TrueAskReddit", 189073);
        temp.put("askphilosophy", 107165);

        subredditToNumberOfFollowers = Collections.unmodifiableMap(temp);

        Map<String, Integer> temp2 = new HashMap<>();
        temp2.put("askscience", 3650);
        temp2.put("AskEngineers", 2920);
        temp2.put("AskHistorians", 2555);
        temp2.put("AskReddit", 4015);
        temp2.put("AskComputerScience", 2920);
        temp2.put("AskCulinary", 2555);
        temp2.put("AskSocialScience", 2920);
        temp2.put("TrueAskReddit", 2920);
        temp2.put("askphilosophy", 2920);

        subredditToAgeInDays = Collections.unmodifiableMap(temp2);
    }

    //- 'Trending' Score of (# of followers) / (age of sub)
    @CrossOrigin
    @GetMapping(value = "/trending", produces = {"application/json"})
    public ResponseEntity<?> getTrending(@RequestParam String subreddit) {
        logger.info("/trending accessed with subreddit: " + subreddit);

        if (subredditToNumberOfFollowers.containsKey(subreddit)) {
            TrendingStat actualTrendingStat = new TrendingStat(subreddit, subredditToNumberOfFollowers.get(subreddit), subredditToAgeInDays.get(subreddit));
            return new ResponseEntity<>(actualTrendingStat, HttpStatus.OK);
        }


        // If we don't know the number of followers or age in days, then return a random but realistic looking
        // number for the time being.
        Random generator = new Random(subreddit.hashCode());
        double randomDouble = generator.nextDouble();
        randomDouble = randomDouble * 5000 + 1;
        int randomInt = (int) randomDouble;

        randomDouble = generator.nextDouble();
        randomDouble = randomDouble * 5000 + 1;
        int randomInt2 = (int) randomDouble;


        TrendingStat trendingStat = new TrendingStat(subreddit, randomInt, randomInt2);

        return new ResponseEntity<>(trendingStat, HttpStatus.OK);
    }

}
