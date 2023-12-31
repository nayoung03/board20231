package com.example.board20231.question;

import com.example.board20231.DataNotFoundException;
import com.example.board20231.answer.Answer;
import com.example.board20231.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    private Specification<Question> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) { //q : 질문제목,내용 검색하기위해
                query.distinct(true);  // 중복을 제거
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT); //Question,SiteUser은 author로 연결되어있기 때문에 조인 -질문작성자 검색
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }
    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }
    public void create(String subject, String content, Question.Category category, SiteUser user){
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setCategory(category);
        q.setAuthor(user);
        this.questionRepository.save(q);
    }
    /*
    Pageable 객체를 생성할때 사용한 pageRequest.of(page,10) 에서 page : 조회할 페이지의 번호, 10 : 한페이지에 보여줄 게시물의 갯수
     */
    public Page<Question> getList(int page,String kw){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page,10,Sort.by(sorts));
        Specification<Question> spec = search(kw);
        return this.questionRepository.findAll(spec, pageable);
}

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyData(LocalDateTime.now());
        this.questionRepository.save(question);
    }
    public void delete(Question question){
        this.questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }



}
