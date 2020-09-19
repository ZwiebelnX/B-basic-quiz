package com.thoughtworks.gtb.csc.basicquiz.repository;

import com.thoughtworks.gtb.csc.basicquiz.model.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
