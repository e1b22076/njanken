package oit.is.z2450.kaizi.njanken.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2450.kaizi.njanken.model.Entry;
import oit.is.z2450.kaizi.njanken.model.User;
import oit.is.z2450.kaizi.njanken.model.Match;
import oit.is.z2450.kaizi.njanken.model.MatchInfo;

import oit.is.z2450.kaizi.njanken.model.UserMapper;
import oit.is.z2450.kaizi.njanken.model.MatchMapper;
import oit.is.z2450.kaizi.njanken.model.MatchInfoMapper;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import oit.is.z2450.kaizi.njanken.service.AsyncKekka;

@Controller
public class jankenController {

  private final Logger logger = LoggerFactory.getLogger(jankenController.class);

  @Autowired
  private Entry entry;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private MatchMapper matchMapper;

  @Autowired
  private MatchInfoMapper matchInfoMapper;

  @Autowired
  AsyncKekka Kekka;

  private static final String[] hands = { "Gu", "Choki", "Pa" };

  @PostMapping("/janken")
  public String enterRoom(@RequestParam String name, Model model, Principal prin) {

    model.addAttribute("name", "Hi " + name);
    return "janken";
  }

  @GetMapping("/janken")
  public String enterRoom(Principal prin, Model model) {
    String loginUser = prin.getName();

    // ユーザーをデータベースから取得
    ArrayList<User> userList = userMapper.selectUser();
    model.addAttribute("userList", userList);
    ArrayList<Match> result = matchMapper.selectMatch();
    model.addAttribute("result", result);
    ArrayList<MatchInfo> information = matchInfoMapper.selectTrueUser();
    model.addAttribute("information", information);

    // エントリーにユーザーを追加
    this.entry.addUser(loginUser);
    model.addAttribute("loginUser", loginUser);
    model.addAttribute("entry", this.entry);

    return "janken";
  }

  @GetMapping("/jankengame")
  public String goResult(@RequestParam String hand, Model model) {
    // 'hand'というキーで、フォームから送られた値をモデルに追加
    model.addAttribute("hand", "あなたの手: " + hand);
    Random random = new Random();

    String opponentHand = hands[random.nextInt(hands.length)];
    model.addAttribute("opponentHand", "相手の手: " + opponentHand);

    // 結果判定
    String result;
    if (hand.equals(opponentHand)) {
      result = "Draw";
    } else if ((hand.equals("Gu") && opponentHand.equals("Choki")) ||
        (hand.equals("Choki") && opponentHand.equals("Pa")) ||
        (hand.equals("Pa") && opponentHand.equals("Gu"))) {
      result = "You Win!";
    } else {
      result = "You Lose";
    }
    model.addAttribute("result", "結果: " + result);
    return "janken";
  }

  @GetMapping("/match")
  public String enterMatch(Principal prin, Model model, @RequestParam int id) {
    String loginUser = prin.getName();

    // ユーザーをデータベースから取得
    ArrayList<User> userList = userMapper.selectUser();
    model.addAttribute("userList", userList);
    ArrayList<Match> result = matchMapper.selectMatch();
    model.addAttribute("result", result);
    model.addAttribute("CPUID", id);

    // エントリーにユーザーを追加
    this.entry.addUser(loginUser);
    model.addAttribute("loginUser", loginUser);
    model.addAttribute("entry", this.entry);
    model.addAttribute("hand", "");
    model.addAttribute("opponentHand", "");
    model.addAttribute("result", "");
    model.addAttribute("enemy", userMapper.selectNameUser(id));

    return "match";
  }

  @GetMapping("/fight")
  public String goFight(Principal prin, @RequestParam int id, @RequestParam String hand, Model model) {
    // 'hand'というキーで、フォームから送られた値をモデルに追加
    String loginUser = prin.getName();
    ArrayList<MatchInfo> AMI = matchInfoMapper.selectMe();
    int flag = 0; // 少なくとも1つの一致があればtrueになるフラグ
    int mid = 0;
    int matchId = -1;
    for (MatchInfo matchInfo : AMI) {
      if (matchInfo.getUser2() == userMapper.selectIdUser(loginUser)) {
        mid = matchInfo.getId();
        flag = 1;
        break;
      }
    }

    if (flag == 1) {// trueが後
      Match match = new Match();
      match.setUser1(id);
      match.setUser2(userMapper.selectIdUser(loginUser));
      match.setUser1Hand(matchInfoMapper.selectEnemy(id));
      match.setUser2Hand(hand);
      match.setActive(true);
      matchMapper.insertMatch(match);
      match = matchMapper.selectthis();
      MatchInfo info = new MatchInfo(mid, id, userMapper.selectIdUser(loginUser), matchInfoMapper.selectEnemy(id),
          false);
      matchInfoMapper.updateBybool(info);
      matchId = match.getId();

    } else {// falseが先
      MatchInfo information = new MatchInfo();
      information.setUser1(userMapper.selectIdUser(loginUser));
      information.setUser2(id);
      information.setUser1Hand(hand);
      information.setActive(true);
      matchInfoMapper.insertMatchInfo(information);

    }
    model.addAttribute("loginUser", loginUser);

    // Match match = new Match();
    // model.addAttribute("hand", "あなたの手: " + hand);
    // match.setUser2Hand(hand);
    // Random random = new Random();

    // String opponentHand = hands[random.nextInt(hands.length)];
    // model.addAttribute("opponentHand", "相手の手: " + opponentHand);
    // match.setUser1Hand(opponentHand);
    // int ID = userMapper.selectIdUser(loginUser);
    // match.setUser1(1);
    // match.setUser2(ID);
    // // 結果判定
    // String result;
    // if (hand.equals(opponentHand)) {
    // result = "Draw";
    // } else if ((hand.equals("Gu") && opponentHand.equals("Choki")) ||
    // (hand.equals("Choki") && opponentHand.equals("Pa")) ||
    // (hand.equals("Pa") && opponentHand.equals("Gu"))) {
    // result = "You Win!";
    // } else {
    // result = "You Lose";
    // }
    // model.addAttribute("result", "結果: " + result);
    // matchMapper.insertMatch(match);
    model.addAttribute("ID", matchId);
    model.addAttribute("MID", mid);
    model.addAttribute("Flag", flag);
    return "wait";
  }

  @GetMapping("/kekka")
  public SseEmitter seeResult(Principal prin, Model model, @RequestParam int id, @RequestParam int flag,
      @RequestParam int mid) {
    final SseEmitter sseEmitter = new SseEmitter();
    Match match = new Match();

    match = matchMapper.selectById(id);

    try {
      this.Kekka.matchfinish(sseEmitter, match, flag);
    } catch (IOException e) {
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
      sseEmitter.complete();
    }
    model.addAttribute("match", match);
    return sseEmitter;
  }

}
