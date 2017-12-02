package com.epam.webinar.hnorbert90.repository;

import org.springframework.data.repository.CrudRepository;

import com.epam.webinar.hnorbert90.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
