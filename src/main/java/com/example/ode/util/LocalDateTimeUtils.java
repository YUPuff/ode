package com.example.ode.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * LocalDateTime工具类
 *
 */
public class LocalDateTimeUtils {

    /**
     * 当前时间
     *
     * @return
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * Date 转 LocalDateTime
     *
     * @return
     */
    public static LocalDateTime convertToLDT(Date date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime 转 Date
     *
     * @return
     */
    public static Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 今天开始时间
     *
     * @return
     */
    public static LocalDateTime todayStartTime() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

    /**
     * 今天结束时间
     *
     * @return
     */
    public static LocalDateTime todayEndTime() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    }


    /**
     * 本周开始时间
     *
     * @return
     */
    public static LocalDateTime weekStartTime() {
        LocalDate now = LocalDate.now();
        return LocalDateTime.of(now.minusDays(now.getDayOfWeek().getValue() - 1), LocalTime.MIN);
    }

    /**
     * 本周结束时间
     *
     * @return
     */
    public static LocalDateTime weekEndTime() {
        LocalDate now = LocalDate.now();
        return LocalDateTime.of(now.plusDays(7 - now.getDayOfWeek().getValue()), LocalTime.MAX);
    }

    /**
     * 本月开始时间
     *
     * @return
     */
    public static LocalDateTime monthStartTime() {
        return LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
    }

    /**
     * 本月结束时间
     *
     * @return
     */
    public static LocalDateTime monthEndTime() {
        return LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
    }



    /**
     * 本年开始时间
     *
     * @return
     */
    public static LocalDateTime yearStartTime() {
        return LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN);
    }

    /**
     * 本年结束时间
     *
     * @return
     */
    public static LocalDateTime yearEndTime() {
        return LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.lastDayOfYear()), LocalTime.MAX);
    }

    /**
     * 上周开始时间
     *
     * @return
     */
    public static LocalDateTime lastWeekStartTime() {
        LocalDate lastWeek = LocalDate.now().minus(1L, ChronoUnit.WEEKS);
        return LocalDateTime.of(lastWeek.minusDays(lastWeek.getDayOfWeek().getValue() - 1), LocalTime.MIN);
    }

    /**
     * 上周结束时间
     *
     * @return
     */
    public static LocalDateTime lastWeekEndTime() {
        LocalDate lastWeek = LocalDate.now().minus(1L, ChronoUnit.WEEKS);
        return LocalDateTime.of(lastWeek.plusDays(7 - lastWeek.getDayOfWeek().getValue()), LocalTime.MAX);
    }

    public static LocalDateTime nMonthStartTime(LocalDateTime start, Long n) {
        return LocalDateTime.of(LocalDate.from(start).plus(n, ChronoUnit.MONTHS).with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
    }

    /**
     * 上月结束时间
     *
     * @return
     */
    public static LocalDateTime nMonthEndTime(LocalDateTime start, Long n) {
        return LocalDateTime.of(LocalDate.from(start).plus(n, ChronoUnit.MONTHS).with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
    }

    /**
     * LocalDate.toEpochDay()将日期转换成Epoch 天，Epoch就是从1970-01-01(ISO)。开始的天数，和时间戳是一个道理，时间戳是秒数。
     * @param start
     * @param end
     * @return
     */
    private static long getBetweenDay(LocalDate start, LocalDate end) {
        return end.toEpochDay() - start.toEpochDay();
    }

    public static long getBetweenDay(Date addTime) {

        LocalDate start = LocalDate.from(convertToLDT(addTime));
        LocalDate now = LocalDate.now();

        return getBetweenDay(start, now);
    }

    public static void main(String[] args) {
//        System.out.println("当前时间：" + now());
//        System.out.println("Date 转 LocalDateTime：" + convertToLDT(new Date()));
//        System.out.println("LocalDateTime 转 Date：" + convertToDate(LocalDateTime.now()));
//        System.out.println("今天开始时间：" + todayStartTime());
//        System.out.println("今天结束时间：" + todayEndTime());
//        System.out.println("本周开始时间：" + weekStartTime());
//        System.out.println("本周结束时间：" + weekEndTime());
//        System.out.println("本月开始时间：" + monthStartTime());
//        System.out.println("本月结束时间：" + monthEndTime());
//        System.out.println("本年开始时间：" + yearStartTime());
//        System.out.println("本年结束时间：" + yearEndTime());
//        System.out.println("上周开始时间：" + lastWeekStartTime());
//        System.out.println("上周结束时间：" + lastWeekEndTime());
//        //转换格式化
//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
//        System.out.println("当前年的开始时间:" + todayStartTime().format(fmt));
        LocalDateTime start = yearStartTime();
        for(long i=0;i<12;i++){
            System.out.println(nMonthStartTime(start,i));
            System.out.println(nMonthEndTime(start,i));
            System.out.println("-------");
        }
    }

}

