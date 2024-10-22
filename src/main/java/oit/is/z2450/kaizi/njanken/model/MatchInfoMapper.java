package oit.is.z2450.kaizi.njanken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface MatchInfoMapper {

  // idを取ってくる。その際、isActive,user1,user2の情報を送れば出てくるんじゃない？
  @Select("SELECT * FROM MatchInfo WHERE isActive = true ")
  ArrayList<MatchInfo> selectTrueUser();

  @Select("SELECT * FROM MatchInfo WHERE isActive = true")
  ArrayList<MatchInfo> selectMe();

  @Select("SELECT user1Hand FROM MatchInfo WHERE (isActive = true)AND(user1=#{id})")
  String selectEnemy(int id);

  // @Select("SELECT Id FROM MatchInfo WHERE (isActive=true)AND(user1 =
  // CPU)AND(user2=Player)")
  // int selectMid()
  @Insert("INSERT INTO MatchInfo (user1,user2,user1Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{isActive});")
  void insertMatchInfo(MatchInfo matchInfo);

  @Update("UPDATE MatchInfo SET isActive = false WHERE ID = #{id}")
  void updateBybool(MatchInfo matchInfo);
}
