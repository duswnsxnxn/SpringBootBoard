package jump.springboot.domain.answer;

import jump.springboot.domain.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE answer AUTO_INCREMENT = 1", nativeQuery = true)
    void clearAutoIncrement();

    Page<Answer> findAllByQuestion(Question question, Pageable pageable);
}
