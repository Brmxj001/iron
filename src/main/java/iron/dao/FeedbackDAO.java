package iron.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import iron.bean.feedback;

public interface FeedbackDAO extends JpaRepository<feedback, Integer> {
}
