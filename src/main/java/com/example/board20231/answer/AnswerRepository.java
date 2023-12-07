package com.example.board20231.answer;

import com.example.board20231.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
     Page<Answer> findAllByQuestion(Question question, Pageable pageable);
}