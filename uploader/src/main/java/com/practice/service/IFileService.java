package com.practice.service;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.practice.model.FileModel;

public interface IFileService 
{
    List<FileModel> findAll();
	boolean saveDataFromUploadFile(MultipartFile file, String city);
	FileModel getFile(Long fileId);
}
