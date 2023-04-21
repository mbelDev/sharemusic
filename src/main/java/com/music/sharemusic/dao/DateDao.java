package com.music.sharemusic.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DateDao {
    public Map<String, String> getMonthRankDate(int moveMonth);
}
