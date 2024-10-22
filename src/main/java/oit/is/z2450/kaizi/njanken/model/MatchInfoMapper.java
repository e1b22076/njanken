package oit.is.z2450.kaizi.njanken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface MatchInfoMapper {

  @Select("SELECT * FROM MatchInfo WHERE isActive = true ")
  ArrayList<MatchInfo> selectTrueUser();

  @Insert("INSERT INTO MatchInfo (user1,user2,user1Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{isActive});")
  void insertMatchInfo(MatchInfo matchInfo);
}
