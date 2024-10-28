package oit.is.z2450.kaizi.njanken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {
  @Select("SELECT * from MATCHES;")
  ArrayList<Match> selectMatch();

  @Select("SELECT * from MATCHES WHERE isActive=true")
  Match selectthis();

  @Select("SELECT id from MATCHES WHERE ((user1=#{user})OR(user2=#{user})) AND (isActive= true)")
  ArrayList<Integer> selectId(int user);

  @Select("SELECT id from MATCHES WHERE isActive= true")
  ArrayList<Integer> selectMId();

  @Select("SELECT isActive from MATCHES WHERE id=#{id}")
  boolean selectbool(int id);

  @Select("SELECT user2Hand from MATCHES WHERE id=#{id}")
  String selectuser2Hand(int id);

  @Select("SELECT * from MATCHES WHERE id=#{id}")
  Match selectById(int id);

  @Insert("INSERT INTO MATCHES (user1,user2,user1Hand,user2Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand},#{isActive});")
  void insertMatch(Match match);

  @Update("UPDATE MATCHES SET isActive = false WHERE ID = #{id}")
  void updateBybool(Match match);
}
