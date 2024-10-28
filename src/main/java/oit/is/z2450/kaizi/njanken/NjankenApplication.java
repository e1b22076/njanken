package oit.is.z2450.kaizi.njanken;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class NjankenApplication {

  public static void main(String[] args) {
    SpringApplication.run(NjankenApplication.class, args);
  }

}
