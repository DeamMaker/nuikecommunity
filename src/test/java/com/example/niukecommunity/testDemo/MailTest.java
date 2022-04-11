package com.example.niukecommunity.testDemo;

import com.example.niukecommunity.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@SpringBootTest
public class MailTest {
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail(){
        mailClient.sendMail("djj12605064142021@163.com","TEST","Welcome");
    }
    @Test
    public void testHtmlMail(){
        Context context=new Context();
        context.setVariable("username","dabao");
        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);
        mailClient.sendMail("djj12605064142021@163.com","HTML",content);
    }
}
