package oit.is.z2450.kaizi.njanken.model;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatchMapper {
  @Select("SELECT * from MATCHES;")
  ArrayList<Match> selectMatch();
}
