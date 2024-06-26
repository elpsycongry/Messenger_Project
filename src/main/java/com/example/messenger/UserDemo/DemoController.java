package com.example.messenger.UserDemo;

import com.example.messenger.mail.MailService;
import com.example.messenger.mail.MailStructure;
import com.example.messenger.user.IUserService;
import com.example.messenger.user.RoleRepository;
import com.example.messenger.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class DemoController {
    @Autowired
    private IUserService userService;
    @Autowired
    private RoleRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;
    @GetMapping()
    public ResponseEntity<?> getAllIntern(@RequestParam(value = "id", required = false) Long id) {
        MailStructure mailStructure = new MailStructure();
        mailStructure.setSubject("Subject");
        mailStructure.setText("Text");
        mailStructure.setReceiverName("Tùng Vũ");
        mailService.sendMailHtml("vuthanhtungtd2@gmail.com",mailStructure);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
