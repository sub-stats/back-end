package com.subredditstats.subredditstats.services;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {
    private static final Logger logger = LoggerFactory.getLogger(CommentsService.class);

    @Autowired
    private DatabaseService databaseService;

    public Map<String, Integer> getNumCommentsPerDay(String subredditName) {

        Map<String, Integer> dateToCommentsCount = new HashMap<>();

        // TODO fix SQL injection risk by sanitizing user input
        String SQL = "select " +
        "date(to_timestamp(created_utc)) as date, " +
        "sum(num_comments) as comments_per_post " +
        "from submission " +
        "where subreddit = '" + subredditName +"' " +
        "group by 1";

        try  {
            Connection conn = databaseService.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {

                String date = rs.getString("date");
                int commentsPerPost = rs.getInt("comments_per_post");

                dateToCommentsCount.put(date, commentsPerPost);

                logger.info("date: " + date);
                logger.info("commentsPerPost: " + commentsPerPost);

            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }

        return dateToCommentsCount;
    }


    public Map<String, Double> getAverageCommentsPerPost(String subredditName) {
        Map<String, Double> dateToAverageCommentsPerPostCount = new HashMap<>();

        // TODO fix SQL injection risk by sanitizing user input
        String SQL = "select " +
                "date(to_timestamp(created_utc)) as date, " +
                "avg(num_comments) as comments_per_post " +
                "from submission " +
                "where subreddit = '" + subredditName + "' " +
                "group by 1";

        try  {
            Connection conn = databaseService.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {

                String date = rs.getString("date");
                double averageCommentsPerPost = rs.getDouble("comments_per_post");

                dateToAverageCommentsPerPostCount.put(date, averageCommentsPerPost);

                logger.info("date: " + date);
                logger.info("commentsPerPost: " + averageCommentsPerPost);

            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }

        return dateToAverageCommentsPerPostCount;

    }

}
