package oit.is.z2450.kaizi.njanken.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import oit.is.z2450.kaizi.njanken.model.Entry;

@Controller
public class jankenController {

  @Autowired
  private Entry entry;

  private static final String[] hands = { "Gu", "Choki", "Pa" };

  @PostMapping("/janken")
  public String enterRoom(@RequestParam String name, Model model) {

    model.addAttribute("name", "Hi " + name);
    return "janken";
  }

  @GetMapping("/janken")
  public String enterRoom(Principal prin, Model model) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("loginUser", loginUser);
    model.addAttribute("entry", this.entry);
    return "janken.html";
  }

  @GetMapping("/jankengame")
  public String goResult(@RequestParam String hand, Model model) {
    // 'name'というキーで、フォームから送られた値をモデルに追加
    model.addAttribute("hand", "あなたの手" + hand);
    Random random = new Random();

    String opponentHand = hands[random.nextInt(hands.length)];
    model.addAttribute("opponentHand", "相手の手" + opponentHand);

    String result;
    if (hand.equals(opponentHand)) {
      result = "Draw";
    } else if ((hand.equals("Gu") && opponentHand.equals("Choki") ||
        (hand.equals("Choki") && opponentHand.equals("Pa")) ||
        (hand.equals("Pa") && opponentHand.equals("Gu")))) {
      result = "You Win!";
    } else {
      result = "You Lose";
    }
    model.addAttribute("result", "結果" + result);
    return "janken";
  }

}
