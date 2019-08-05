package com.practice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
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
    private String filename;
    private String filetype;

    @Type(type="org.hibernate.type.BinaryType") 
    private byte[] data;
    
    @Transient
    private MultipartFile[] files;
    @Transient
    private MultipartFile file;


	public FileModel() {}

    public FileModel(Long id, String city, String content)//, String fileName, String fileType, byte[] data) 
    {
        this.id = id;
        this.city = city;
        this.content = content;
        //this.fileName = fileName;
        //this.fileType = fileType;
        //this.data = data;
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
    
    public byte[] getData() {return data;}

    public void setData(byte[] data) {this.data = data;}
    
    public String getfileName() {return filename;}

    public void setfileName(String filename) {this.filename = filename;}
    
    public String getfileType() {return filetype;}

    public void setfileType(String fileType) {this.filetype = fileType;}
}
