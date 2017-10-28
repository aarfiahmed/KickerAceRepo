package com.app;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.amazon.aws.S3Service;
import com.app.model.FileInfo;

@RestController
public class UploadMediaController {

	@Autowired
	S3Service service;

	
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST,headers=("content-type=multipart/*"))
	public ResponseEntity<FileInfo> uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
		System.out.println("UploadMediaController.uploadFile()");
	System.out.println("name" +file.getName());
	System.out.println("ori"+file.getOriginalFilename());
	
	service.uploadFile(file.getOriginalFilename(), file.getInputStream());
		 
		return  new ResponseEntity<FileInfo>(new FileInfo(file.getOriginalFilename(), file.getContentType()), HttpStatus.OK);
	}

	
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String uploadResource() throws IOException {
		System.out.println("UploadMediaController.uploadResource()");
	
		InputStream in = this.getClass().getResourceAsStream("test.txt");
	
		System.out.println(in);
		service.uploadFile("e:\test.txt",in);
		return "done";
	}

	@RequestMapping(value = "bucketLocation", method = RequestMethod.GET)
	public String getBucketLocation() {
		System.out.println("UploadMediaController.getBucketLocation()");
		return service.gets3Buckets();
	}

	@RequestMapping(value = "createDir/{dirName}")

	public String createDir(@PathVariable("dirName") String dirName) {
		System.out.println("UploadMediaController.createDir()");

		service.createDirInS3(dirName);
		return "done";

	}

	@RequestMapping("getMedia/{fileName}")
	public String getMedia(@PathVariable String fileName) throws IOException {
		System.out.println("UploadMediaController.getMedia()");
		return service.getMedia(fileName);
	}

	@RequestMapping("getAllMediaNames")
	public String[] getAllMediaName() throws IOException {
		System.out.println("UploadMediaController.getAllMediaName()");
		return service.getAllFilesInBucket();
	}
	
	@RequestMapping(value="download/{key}",method=RequestMethod.GET)
	public ResponseEntity<Resource>  download(@PathVariable String key){
		System.out.println("UploadMediaController.download()");
		InputStreamResource in=new InputStreamResource(service.getFile(key));
		
		return new ResponseEntity<Resource>(in, HttpStatus.OK);
	}
	
}
