package com.music.sharemusic.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DateDao {
    public Map<String, String> getMonthRankDate(int moveMonth);
    
    // 월간 랭킹 날짜

    public Map<String, String> getWeeklyRankDate(int moveWeekly);

    // 주간 랭킹 날짜
}
