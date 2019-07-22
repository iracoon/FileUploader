package com.practice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.practice.model.FileModel;

@Repository
public interface FileRepository extends CrudRepository<FileModel, Long> 
{

}