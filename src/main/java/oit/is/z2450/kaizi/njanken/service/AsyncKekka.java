package oit.is.z2450.kaizi.njanken.service;

import java.util.ArrayList;
//import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

//import oit.is.z2450.kaizi.njanken.model.User;
import oit.is.z2450.kaizi.njanken.model.Match;
//import oit.is.z2450.kaizi.njanken.model.MatchInfo;

//import oit.is.z2450.kaizi.njanken.model.UserMapper;
import oit.is.z2450.kaizi.njanken.model.MatchMapper;
//import oit.is.z2450.kaizi.njanken.model.MatchInfoMapper;

import java.io.IOException;

@Service
public class AsyncKekka {
  boolean Update = false;

  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  private MatchMapper matchMapper;

  /**
   *
   * @param emitter
   * @param role
   * @throws IOException
   */
  @Async
  public void matchfinish(SseEmitter emitter, Match match, int flag) throws IOException {
    logger.info("非同期処理を開始");
    try {
      while (true) {
        ArrayList<Integer> id = matchMapper.selectMId();
        logger.info(id.get(0) + "" + "\n");
        if (((id.size() != 0 && id != null) || Update == false) && flag == 0) {
          logger.info("aaa");
          int matchId = id.get(id.size() - 1);
          match = matchMapper.selectById(matchId);
          Update = true;
          logger.info("bbb");
          if (matchMapper.selectbool(match.getId())) {
            flag = 1;
            logger.info("ccc");
          }
        }

        match = matchMapper.selectById(flag);
        if (flag == 0) {
          logger.info("ddd");

        } else {
          logger.info("eee");
          match.setActive(false);
          matchMapper.updateBybool(match);
          emitter.send(match);
          Update = true;
        }
        Thread.sleep(1000); // 1秒ごとに結果を送信
      }

    } catch (

    Exception e) {
      logger.error("例外発生: " + e.getMessage());
    } finally {
      emitter.complete(); // 完了処理
    }
  }
}
