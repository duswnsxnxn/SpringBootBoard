package jump.springboot.domain.question;

import jakarta.persistence.*;
import jump.springboot.domain.answer.Answer;
import jump.springboot.domain.user.SiteUser;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    @LazyCollection(LazyCollectionOption.EXTRA)
    private List<Answer> answerList = new ArrayList<>();
    @ManyToOne
    private SiteUser author;
    @ManyToMany
    Set<SiteUser> voter;

    public void addAnswer(Answer answer) {
        answer.setQuestion(this);
        answerList.add(answer);
    }
}
