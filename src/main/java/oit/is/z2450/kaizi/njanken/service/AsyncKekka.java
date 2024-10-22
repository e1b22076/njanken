package oit.is.z2450.kaizi.njanken.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class AsyncKekka {

  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  /**
   *
   * @param emitter
   * @param role
   * @throws IOException
   */
  @Async
  public void matchfinish(SseEmitter emitter, String hand, int id) throws IOException {
    logger.info("AsyncKekka.count");
    try {
      while (true) {
        emitter.send(SseEmitter.event());
        TimeUnit.SECONDS.sleep(1);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
