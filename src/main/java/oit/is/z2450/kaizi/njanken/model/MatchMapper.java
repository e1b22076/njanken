package oit.is.z2450.kaizi.njanken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatchMapper {
  @Select("SELECT * from MATCHES;")
  ArrayList<Match> selectMatch();



  @Insert("INSERT INTO MATCHES (user1,user2,user1Hand,user2Hand) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand});")
  void insertMatch(Match match);
}
