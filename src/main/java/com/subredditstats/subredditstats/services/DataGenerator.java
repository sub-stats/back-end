package com.subredditstats.subredditstats.services;

import com.subredditstats.subredditstats.dtos.DateStats;
import com.subredditstats.subredditstats.models.DateStat;
import com.subredditstats.subredditstats.models.FieldSpec;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DataGenerator {


    public List<DateStat> getDateStats(String start, String end, String subreddit, List<FieldSpec> dataFields) throws ParseException {
        List<DateStat> dateStats = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = format.parse(start);
        Date endDate = format.parse(end);
        List<Date> dateRange = getDaysBetweenDates(startDate, endDate);

        String requestFingerprint = start + end + subreddit;
        int requestNumericalFingerprint = requestFingerprint.hashCode();
        Random generator = new Random(requestNumericalFingerprint);

        for (Date date : dateRange) {
            String dateString = format.format(date);

            Map<String, Double> stats = new HashMap<>();
            for (FieldSpec field : dataFields) {
                double randomNumber = generator.nextDouble();
                randomNumber = randomNumber * field.getMaxValue() + 1;
                if (field.isShouldBeInt()) {
                    randomNumber = (int) randomNumber;
                }
                stats.put(field.getName(), randomNumber);
            }

            DateStat ds = new DateStat(dateString, stats);
            dateStats.add(ds);
        }

        return dateStats;
    }


    // Example code found on StackOverflow: https://stackoverflow.com/a/2893790/3732147
    public List<Date> getDaysBetweenDates(Date startDate, Date endDate) {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }

        return dates;
    }
}
