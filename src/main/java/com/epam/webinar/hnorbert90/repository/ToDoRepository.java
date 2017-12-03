package com.epam.webinar.hnorbert90.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.epam.webinar.hnorbert90.domain.ToDo;

public interface ToDoRepository extends CrudRepository<ToDo, Long> {
	Page<ToDo> findByUser_IdOrderByCreatedDesc(Pageable pageable, Long userId);

	Page<ToDo> findByUser_IdOrderByPriorityAsc(Pageable pageable, Long userId);

	Page<ToDo> findByUser_IdOrderByPriorityDesc(Pageable pageable, Long userId);

	Page<ToDo> findByUser_IdOrderByDoneAscPriorityDesc(Pageable pageable, Long userId);

	Page<ToDo> findByUser_Id(Pageable pageable, Long userId);

	List<ToDo> findAllByDone(boolean bool);

	Page<ToDo> findByUser_IdOrderByCreatedAsc(Pageable page, Long id);

	Page<ToDo> findByUser_IdOrderByDeadlineAsc(Pageable page, Long userId);

	Page<ToDo> findByUser_IdOrderByDeadlineDesc(Pageable page, Long userId);

}
