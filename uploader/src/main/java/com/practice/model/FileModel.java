package com.practice.model;

import java.util.Objects;
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
    private MultipartFile file;


	public FileModel() {}

    public FileModel(Long id, String city, String content) 
    {
        this.id = id;
        this.city = city;
        this.content = content;
    }
    
    public MultipartFile getFile() {return file;}

	public void setFile(MultipartFile file) {this.file = file;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getCity() {return city;}

    public void setCity(String city) {this.city = city;}

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}

    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.city);
        hash = 79 * hash + Objects.hashCode(this.content);
        return hash;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) 
            return true;
        if (obj == null) 
            return false;
        if (getClass() != obj.getClass()) 
            return false;
        final FileModel other = (FileModel) obj;
        if (!Objects.equals(this.city, other.city)) 
            return false;
        if (!Objects.equals(this.content, other.content)) 
            return false;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() 
    {
        final StringBuilder sb = new StringBuilder("File_Model{");
        sb.append("id=").append(id);
        sb.append(", city='").append(city).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
