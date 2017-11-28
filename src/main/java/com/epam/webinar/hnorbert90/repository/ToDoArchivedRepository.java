package com.epam.webinar.hnorbert90.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.epam.webinar.hnorbert90.domain.ToDoArchived;

public interface ToDoArchivedRepository extends CrudRepository<ToDoArchived, Long> {
	Page<ToDoArchived> findAllByOrderByCreated(Pageable pageable);
	Page<ToDoArchived> findAllByOrderByCreatedDesc(Pageable pageable);
	Page<ToDoArchived> findAllByOrderByPriority(Pageable pageable);
	Page<ToDoArchived> findAllByOrderByPriorityDesc(Pageable pageable);
	Page<ToDoArchived> findAllByOrderByDoneAscPriorityDesc(Pageable pageable);
}
