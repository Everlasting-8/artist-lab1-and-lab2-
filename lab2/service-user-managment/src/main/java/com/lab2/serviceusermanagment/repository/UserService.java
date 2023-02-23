package com.lab2.serviceusermanagment.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface UserService extends JpaRepository<User, Long> {
}
