package com.example.board20231;

import com.example.board20231.question.QuestionService;
import com.example.board20231.user.SiteUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SbbApplicationTests {
    @Autowired
    private QuestionService questionService;

    @Test
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("데스트 데이터입니다:[%03d", i);
            String content = "내용무";
//            SiteUser user = ;
            this.questionService.create(subject, content);
        }
    }
}
