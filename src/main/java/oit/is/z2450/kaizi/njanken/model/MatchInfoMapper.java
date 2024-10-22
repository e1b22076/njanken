package oit.is.z2450.kaizi.njanken.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface MatchInfoMapper {

  @Insert("INSERT INTO MatchInfo (user1,user2,user1Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{isActive});")
  void insertMatchInfo(MatchInfo matchInfo);
}
