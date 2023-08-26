package com.sm.demo.springbatch.repository;

import com.sm.demo.springbatch.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
}
