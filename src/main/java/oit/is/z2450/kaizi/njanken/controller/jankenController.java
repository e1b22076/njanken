package oit.is.z2450.kaizi.njanken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class jankenController {

  @PostMapping("/janken")
  public String enterRoom(@RequestParam String name, Model model) {

    model.addAttribute("name", "Hi " + name);
    return "janken";
  }

  @GetMapping("/jankengame")
  public String goResult(@RequestParam String hand, Model model) {
    // 'name'というキーで、フォームから送られた値をモデルに追加
    model.addAttribute("hand", "あなたの手" + hand);
    String opponentHand = "Gu"; // ここをランダムにすることも可能
    model.addAttribute("opponentHand", "相手の手" + opponentHand);

    String result;
    if (hand.equals(opponentHand)) {
      result = "Draw";
    } else if ((hand.equals("Pa") && opponentHand.equals("Gu"))) {
      result = "You Win!";
    } else {
      result = "You Lose";
    }
    model.addAttribute("result", "結果" + result);
    return "janken";
  }

}
