package jump.springboot.domain.answer;

import jakarta.persistence.*;
import jump.springboot.domain.question.Question;
import jump.springboot.domain.user.SiteUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "TEXT")
    private String content;
    @CreatedDate
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    @ManyToOne
    private Question question;
    @ManyToOne
    private SiteUser author;
    @ManyToMany
    Set<SiteUser> voter;
}
