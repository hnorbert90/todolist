package com.epam.webinar.hnorbert90.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.epam.webinar.hnorbert90.domain.ToDo;

public interface ToDoRepository extends CrudRepository<ToDo, Long> {
	Page<ToDo> findAllByOrderByCreated(Pageable pageable);
	Page<ToDo> findAllByOrderByCreatedDesc(Pageable pageable);
	Page<ToDo> findAllByOrderByPriority(Pageable pageable);
	Page<ToDo> findAllByOrderByPriorityDesc(Pageable pageable);
	Page<ToDo> findAllByOrderByDoneAscPriorityDesc(Pageable pageable);
	List<ToDo> findAllByDone(boolean bool);
}
