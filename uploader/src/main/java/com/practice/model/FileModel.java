package com.practice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "files_table")
public class FileModel 
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String city;
    private String content;
    
    @Transient
    private MultipartFile[] files;
    @Transient
    private MultipartFile file;


	public FileModel() {}

    public FileModel(Long id, String city, String content) 
    {
        this.id = id;
        this.city = city;
        this.content = content;
    }
    
    public MultipartFile[] getFiles() {return files;}

	public void setFiles(MultipartFile[] files) {this.files = files;}
    
    public MultipartFile getFile() {return file;}

	public void setFile(MultipartFile file) {this.file = file;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getCity() {return city;}

    public void setCity(String city) {this.city = city;}

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}
}
