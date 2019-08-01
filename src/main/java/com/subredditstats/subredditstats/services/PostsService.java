package com.subredditstats.subredditstats.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@Service
public class PostsService {
    private static final Logger logger = LoggerFactory.getLogger(PostsService.class);

    @Autowired
    private DatabaseService databaseService;

    public Map<String, Integer> getPostsCount(String subredditName) {
        Map<String, Integer> dateToPostsCount = new HashMap<>();

        // TODO fix SQL injection risk by sanitizing user input
        String SQL = "select " +
                "date(to_timestamp(created_utc)) as date, " +
                "count(1) as posts_count " +
                "from submission " +
                "where subreddit = '" + subredditName +"' " +
                "group by 1";

        try  {
            Connection conn = databaseService.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                String date = rs.getString("date");
                int postsCount= rs.getInt("posts_count");

                dateToPostsCount.put(date, postsCount);

                logger.info("date: " + date);
                logger.info("commentsPerPost: " + postsCount);

            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
        logger.info("size: " + dateToPostsCount.size());

        return dateToPostsCount;
    }
}
