package com.Bikkadit.blog.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Bikkadit.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer> {

}
