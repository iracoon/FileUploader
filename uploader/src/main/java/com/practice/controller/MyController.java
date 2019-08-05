package com.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import com.practice.model.FileModel;
import com.practice.service.IFileService;


@Controller
public class MyController 
{
//
    @Autowired
    private IFileService fileService;
    
    @GetMapping("/")
    public String home(Model model) 
    {
        return "fileUploader";
    }

    @PostMapping("/fileupload")
    public String uploadFile(@RequestParam("files") MultipartFile[] files, @RequestParam("city") String city, RedirectAttributes redirectAttributes)
    {
    	boolean isFlag = false;
    	for(MultipartFile file: files)
        	isFlag = fileService.saveDataFromUploadFile(file, city);
    	if(isFlag)
    		redirectAttributes.addFlashAttribute("successmessage", "Uploaded Successfully!");
    	else
    		redirectAttributes.addFlashAttribute("errormessage", "Upload Failed");
    	return "redirect:/";
    }
    

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) 
    {
        FileModel dbFile = fileService.getFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getfileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileId + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
    
}
