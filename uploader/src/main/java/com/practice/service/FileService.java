package com.practice.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.mozilla.universalchardet.UniversalDetector;
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
			    	 tmp.setCity(city);
			    	 tmp.setfileName(file.getOriginalFilename());
			    	 tmp.setfileType(file.getContentType());
			    	 tmp.setData(file.getBytes());
			    	 repository.save(tmp);
	   		    }
            }
		    return true;
		}
		catch(Exception e) {return false;}
	}
	
	public static File convert(MultipartFile file) throws IOException {
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile();
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(file.getBytes());
	    fos.close();
	    return convFile;
	}

	private boolean readDataFromTxt(MultipartFile file,  String city)  
	{
		FileModel tmp = new FileModel();
		try
		{

			java.io.FileInputStream fis = new java.io.FileInputStream(convert(file));
			UniversalDetector detector = new UniversalDetector(null);
			byte[] buf = new byte[1000000];
			int nread;
			String content = "";
			
			while ((nread = fis.read(buf)) > 0 && !detector.isDone()) 
			  detector.handleData(buf, 0, nread);
			detector.dataEnd();
			String encoding = detector.getDetectedCharset();
			InputStream inputStream = file.getInputStream();
			
			if (encoding == "UTF-16BE")
				  content = IOUtils.toString(inputStream, StandardCharsets.UTF_16BE);
			else if (encoding == "UTF-16LE")
				  content = IOUtils.toString(inputStream, StandardCharsets.UTF_16LE);
			else 
				  content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
			fis.close();
			detector.reset();
			
		    if(content.length() > 0)
		    {
		    	tmp.setContent(content);
		    	tmp.setCity(city);
		    	tmp.setfileName(file.getOriginalFilename());
		    	tmp.setfileType(file.getContentType());
		    	tmp.setData(file.getBytes());
		    	repository.save(tmp);
		    }
		     return true;
		}
		catch(Exception e) {return false;}
	}
}