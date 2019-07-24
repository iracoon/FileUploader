package com.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practice.service.IFileService;


@Controller
public class MyController 
{

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
    
}
