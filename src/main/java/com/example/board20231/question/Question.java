package com.example.board20231.question;

import com.example.board20231.answer.Answer;
import com.example.board20231.user.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "Text")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Answer> answerList;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyData;


    @Enumerated(EnumType.STRING)
    private Category category;

    public enum Category{
        QUESTION,LECTURE,BOARD,NOTICE
    }

    @ManyToMany(fetch = FetchType.EAGER)
    Set<SiteUser> voter;  // list가 아닌 set ? 추천인은 중복되면 안되기때문에..

}
