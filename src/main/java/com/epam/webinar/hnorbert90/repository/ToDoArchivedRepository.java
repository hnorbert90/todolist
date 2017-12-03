package com.epam.webinar.hnorbert90.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.epam.webinar.hnorbert90.domain.ToDoArchived;

public interface ToDoArchivedRepository extends CrudRepository<ToDoArchived, Long> {
	Page<ToDoArchived> findByUser_IdOrderByCreatedAsc(Pageable pageable,Long userId);
	Page<ToDoArchived> findByUser_IdOrderByCreatedDesc(Pageable pageable,Long userId);
	Page<ToDoArchived> findByUser_IdOrderByPriorityAsc(Pageable pageable,Long userId);
	Page<ToDoArchived> findByUser_IdOrderByPriorityDesc(Pageable pageable,Long userId);
	Page<ToDoArchived> findByUser_IdOrderByDoneAscPriorityDesc(Pageable pageable,Long userId);
	Page<ToDoArchived> findByUser_Id(Pageable pageable,Long userId);
	Page<ToDoArchived> findByUser_IdOrderByDeadlineAsc(Pageable page, Long userId);
	Page<ToDoArchived> findByUser_IdOrderByDeadlineDesc(Pageable page, Long userId);

}
