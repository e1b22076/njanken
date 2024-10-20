package oit.is.z2450.kaizi.njanken.controller;

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
import oit.is.z2450.kaizi.njanken.model.UserMapper;
import oit.is.z2450.kaizi.njanken.model.MatchMapper;

@Controller
public class jankenController {

  @Autowired
  private Entry entry;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private MatchMapper matchMapper;

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
  public String enterMatch(Principal prin, Model model) {
    String loginUser = prin.getName();

    // ユーザーをデータベースから取得
    ArrayList<User> userList = userMapper.selectUser();
    model.addAttribute("userList", userList);
    ArrayList<Match> result = matchMapper.selectMatch();
    model.addAttribute("result", result);
    // エントリーにユーザーを追加
    this.entry.addUser(loginUser);
    model.addAttribute("loginUser", loginUser);
    model.addAttribute("entry", this.entry);

    return "match";
  }
}
