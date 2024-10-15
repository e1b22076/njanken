package oit.is.z2450.kaizi.njanken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

/**
 * Sample21Controller
 *
 * クラスの前に@Controllerをつけていると，HTTPリクエスト（GET/POSTなど）があったときに，このクラスが呼び出される
 */
@Controller
public class jankenController {
  @PostMapping("/janken")
  public String enterRoom(@RequestParam String name, Model model) {
    // 'name'というキーで、フォームから送られた値をモデルに追加
    model.addAttribute("name", name);
    return "janken";
  }

  @GetMapping("/janken")
  public String janken() {
    return "janken.html";
  }
}
