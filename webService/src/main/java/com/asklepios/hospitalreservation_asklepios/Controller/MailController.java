package com.asklepios.hospitalreservation_asklepios.Controller;

import com.asklepios.hospitalreservation_asklepios.Service.IF_MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {

  private final IF_MailService mailService;
  private int number; // 이메일 인증 숫자를 저장하는 변수

  // 인증 이메일 전송
  @ResponseBody
  @PostMapping("/mailSend")
  public String mailSend(@RequestParam("user_email") String user_email) {
    try {
      number = mailService.sendMail(user_email);
    } catch (Exception e) {
      return "error"; // 예외 발생 시 "error" 반환
    }
    return String.valueOf(number);
  }

  // 인증번호 일치여부 확인
  @GetMapping("/mailCheck")
  public ResponseEntity<?> mailCheck(@RequestParam String userNumber) {
    boolean isMatch = userNumber.equals(String.valueOf(number));
    return ResponseEntity.ok(isMatch);
  }
}
