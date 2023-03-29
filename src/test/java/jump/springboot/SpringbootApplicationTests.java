package jump.springboot;

import jump.springboot.domain.answer.Answer;
import jump.springboot.domain.answer.AnswerService;
import jump.springboot.domain.question.Question;
import jump.springboot.domain.answer.AnswerRepository;
import jump.springboot.domain.question.QuestionRepository;
import jump.springboot.domain.question.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class SpringbootApplicationTests {


	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private AnswerService answerService;
	@Autowired
	private QuestionService questionService;

//	@BeforeEach
//	void beforeEach() {
//		answerRepository.deleteAll();
//		answerRepository.clearAutoIncrement();
//
//		questionRepository.deleteAll();
//		questionRepository.clearAutoIncrement();
//
//		Question q1 = new Question();
//		q1.setSubject("sbb가 무엇인가요?");
//		q1.setContent("sbb에 대해서 알고 싶습니다.");
//		q1.setCreateDate(LocalDateTime.now());
//		questionRepository.save(q1);
//
//		Question q2 = new Question();
//		q2.setSubject("스프링부트 모델 질문입니다.");
//		q2.setContent("id는 자동으로 생성되나요?");
//		q2.setCreateDate(LocalDateTime.now());
//		questionRepository.save(q2);
//
//		Answer a1 = new Answer();
//		a1.setContent("네 자동으로 생성됩니다.");
//		q2.addAnswer(a1);
//		a1.setCreateDate(LocalDateTime.now());
//		answerRepository.save(a1);
//	}

	@Test
	@DisplayName("질문 저장 테스트")
	void t1() {

		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);
	}

	@Test
	@DisplayName("질문 리스트 불러오기")
	void t2() {
		List<Question> questionList = questionRepository.findAll();
		assertThat(questionList.size())
				.isEqualTo(2);
		Question q1 = questionList.get(0);
		assertThat(q1.getSubject())
				.isEqualTo("sbb가 무엇인가요?");
	}

	@Test
	@DisplayName("질문 번호로 불러오기")
	void t3() {
		Optional<Question> q2 = questionRepository.findById(2);
		if (q2.isPresent()) {
			Question question = q2.get();
			assertThat(question.getSubject())
					.isEqualTo("스프링부트 모델 질문입니다.");
		}
	}
	@Test
	@DisplayName("질문 주제로 불러오기")
	void t4() {
		Optional<Question> q2 = questionRepository.findBySubject("스프링부트 모델");
		if (q2.isPresent()) {
			Question question = q2.get();
			assertThat(question.getId())
					.isEqualTo(4);
		}
	}

	@Test
	@DisplayName("질문 주제, 본문으로 불러오기")
	void t5() {
		Optional<Question> q2 = questionRepository.findBySubjectAndContent("스프링부트 모델", "id는 자동으로 생성되나요?");
		if (q2.isPresent()) {
			Question question = q2.get();
			assertThat(question.getId())
					.isEqualTo(4);
		}
	}
	@Test
	@DisplayName("질문 주제, 본문으로 불러오기")
	void t6() {
		List<Question> q2 = questionRepository.findBySubjectLike("스프링부트 모델");
		if (!q2.isEmpty()) {
			assertThat(q2.size())
					.isEqualTo(1);
		}
	}
	@Test
	@DisplayName("가져온 엔티티를 수정하고 저장")
	void t7() {
		Optional<Question> q2 = questionRepository.findById(1);
		if (q2.isPresent()) {
			Question question = q2.get();
			question.setSubject("123");
			questionRepository.save(question);
		}
	}
	@Test
	@DisplayName("가져온 엔티티를 수정하고 저장")
	void t8() {
		assertThat(questionRepository.count())
				.isEqualTo(2);
		Optional<Question> q2 = questionRepository.findById(1);
		if (q2.isPresent()) {
			Question question = q2.get();
			questionRepository.delete(question);
			assertThat(questionRepository.count())
					.isEqualTo(1);
		}
	}
	@Test
	@DisplayName("답변 데이터 생성 후 저장하기")
	void t9() {
		Optional<Question> q2 = questionRepository.findById(2);
		if (q2.isPresent()) {
			Question question = q2.get();
			Answer answer = new Answer();
			answer.setContent("네 잘됩니다");
			answer.setQuestion(question);
			answer.setCreateDate(LocalDateTime.now());
			answerRepository.save(answer);
		}
	}
	@Test
	@DisplayName("답변 데이터 생성 후 저장하기")
	void t10() {
		Optional<Answer> q2 = answerRepository.findById(2);
		if (q2.isPresent()) {
			Answer question = q2.get();
			assertThat(question.getId())
					.isEqualTo(2);
		}
	}

	@Test
	@Transactional
	@DisplayName("질문에 달린 답변 찾기")
	void t11() {
		Optional<Question> q2 = questionRepository.findById(2);
		if (q2.isPresent()) {
			Question question = q2.get();
			List<Answer> answerList = question.getAnswerList();
			assertThat(answerList.size())
					.isEqualTo(1);
		}
	}

	@Test
	void testQuestion() {
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			questionService.create(subject, content, null);
		}
	}
	@Test
	void testAnswer() {
		Optional<Question> byId = questionRepository.findById(303);
		if (byId.isPresent()) {
			Question question = byId.get();
			for (int i = 1; i <= 200; i++) {
				String content = String.format("테스트 데이터입니다:[%03d]", i);
				answerService.create(question, content, null);
			}
		}
	}
}
