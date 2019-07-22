package com.practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practice.model.FileModel;
import com.practice.service.IFileService;


@Controller
public class MyController 
{

    @Autowired
    private IFileService fileService;
    
    @GetMapping("/")
    public String home(Model model) 
    {
    	model.addAttribute("newFile", new FileModel());
        List<FileModel> allFiles = (List<FileModel>) fileService.findAll();
        model.addAttribute("allFiles", allFiles);
        return "fileUploader";
    }

    @PostMapping("/fileupload")
    public String uploadFile(@ModelAttribute FileModel newFile, RedirectAttributes redirectAttributes)
    {
    	boolean isFlag = fileService.saveDataFromUploadFile(newFile.getFile(), newFile.getCity());
    	if(isFlag)
    		redirectAttributes.addFlashAttribute("successmessage", "File Uploaded Successfully!");
    	else
    		redirectAttributes.addFlashAttribute("errormessage", "File Upload Failed");
    	return "redirect:/";
    }

    
}
