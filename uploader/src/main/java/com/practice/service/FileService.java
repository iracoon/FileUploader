package com.practice.service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.practice.model.FileModel;
import com.practice.repository.FileRepository;

@Service
@Transactional
public class FileService implements IFileService 
{

    @Autowired
    private FileRepository repository;

    @Override
    public List<FileModel> findAll() 
    {
        List<FileModel> files = (List<FileModel>) repository.findAll();
        return files;
    }

	@Override
	public boolean saveDataFromUploadFile(MultipartFile file, String city) 
	{
		boolean isFlag = false;
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if(extension.equalsIgnoreCase("txt"))
			isFlag = readDataFromTxt(file, city);
		else if(extension.equalsIgnoreCase("pdf"))
			isFlag = readDataFromPdf(file, city);
		return isFlag;
	}

	private boolean readDataFromPdf(MultipartFile file,  String city) 
	{
		FileModel tmp = new FileModel();
		try
		{
			InputStream inputStream = file.getInputStream();
			PDDocument document = PDDocument.load(inputStream);
            document.getClass();
            if (!document.isEncrypted()) 
            {
			
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper tStripper = new PDFTextStripper();
                String content = tStripper.getText(document);
	   		    if(content.length() > 0)
	   		    {
			    	 tmp.setContent(content);
			    	 tmp.setCity(city);;
			    	 repository.save(tmp);
	   		    }
            }
		    return true;
		}
		catch(Exception e) {return false;}
	}

	private boolean readDataFromTxt(MultipartFile file,  String city) 
	{
		FileModel tmp = new FileModel();
		try
		{
			InputStream inputStream = file.getInputStream();
			String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
		     if(content.length() > 0)
		     {
		    	 tmp.setContent(content);
		    	 tmp.setCity(city);
		    	 repository.save(tmp);
		     }
		     return true;
		}
		catch(Exception e) {return false;}
	}
}