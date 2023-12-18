package com.example.board20231;

import com.example.board20231.answer.AnswerService;
import com.example.board20231.question.Question;
import com.example.board20231.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Board20231ApplicationTests {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Test
    void testJpa(){
        Question question = questionService.getQuestion(304);
        for (int i=1; i<=100; i++){
            String content = String.format("테스트 테스터입니다:[:%03d]",i);
            this.answerService.create(question,content,null);
        }
    }
}
