package iron.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import iron.bean.contact;

public interface ContactDAO extends JpaRepository<contact, Integer> {

}
