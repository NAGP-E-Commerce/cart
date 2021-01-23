package com.nagp.cart.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagp.cart.entity.Entry;

public interface EntryRepository extends JpaRepository<Entry, Long> {

}
