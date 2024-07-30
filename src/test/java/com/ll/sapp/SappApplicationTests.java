package com.ll.sapp;

import com.ll.sapp.answer.Answer;
import com.ll.sapp.answer.AnswerRepository;
import com.ll.sapp.question.Question;
import com.ll.sapp.question.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SappApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Test
	void testJpa()
	{
		Question q1 = new Question();
		q1.setSubject("sbb?");
		q1.setContent("sbb!");
		q1.setCreateDate(LocalDateTime.now());

		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스ㅍㄹ");
		q2.setContent("id?");
		q2.setCreateDate(LocalDateTime.now());

		this.questionRepository.save(q2);
	}

	@Test
	void testJpa2() {
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("sbb?", q.getSubject());
	}

	@Test
	void testJpa3() {
		Optional<Question> oq = this.questionRepository.findById(1);

		if(oq.isPresent())
		{
			Question q = oq.get();
			assertEquals("sbb?", q.getSubject());
		}
	}

	@Test
	void testJpa4() {
		Question q = this.questionRepository.findBySubject("sbb?");

		assertEquals(1, q.getId());
	}

	@Test
	void testJpa5() {
		Question q = this.questionRepository.findBySubjectAndContent(
				"sbb?", "sbb!"
		);

		assertEquals(1, q.getId());
	}

	@Test
	void testJpa6() {
		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");

		Question q = qList.get(0);

		assertEquals("sbb?", q.getSubject());
	}

	@Test
	void testJpa7() {
		Optional<Question> oq = this.questionRepository.findById(1);

		assertTrue(oq.isPresent());
		Question q = oq.get();

		q.setSubject("수정된 제목");
		this.questionRepository.save(q);
	}

	@Test
	void testJpa8() {
		assertEquals(2, this.questionRepository.count());

		Optional<Question> oq = this.questionRepository.findById(1);

		assertTrue(oq.isPresent());

		Question q = oq.get();

		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
	}

	@Test
	void testJpa9() {
		Optional<Question> oq = this.questionRepository.findById(2);

		assertTrue(oq.isPresent());
		Question q = oq.get();

		Answer a = new Answer();
		a.setContent("자동 생성");
		a.setQuestion(q);
		a.setCreateDate(LocalDateTime.now());

		this.answerRepository.save(a);
	}

	@Test
	void testJpa10() {
		Optional<Answer> oa = this.answerRepository.findById(1);

		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId());
	}

	@Test
	@Transactional
	void testJpa11() {
		Optional<Question> oq = this.questionRepository.findById(2);

		assertTrue(oq.isPresent());

		Question q =  oq.get();

		List<Answer> answerList = q.getAnswerList();

		assertEquals(1, answerList.size());

		assertEquals("자동 생성", answerList.get(0).getContent());
	}
}