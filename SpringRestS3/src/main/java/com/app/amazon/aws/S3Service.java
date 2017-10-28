package com.app.amazon.aws;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Component
public class S3Service {

	AWSCredentials credentials = new BasicAWSCredentials("AKIAIUYFXNHVRK3HTH6A",
			"VsNTWVAC90wLYXNcNJLJ2bS0JsOAQfSycH7HcMNN");

	private final String BUCKET = "rest-api-data";

	public boolean uploadFile(String fileName, InputStream in) throws IOException {
		AmazonS3Client client = new AmazonS3Client(credentials);
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentLength((long) in.available());

		client.putObject(new PutObjectRequest(BUCKET, fileName, in, meta));

		return true;
	}

	public String gets3Buckets() {
		AmazonS3Client client = new AmazonS3Client(credentials);
		return client.getBucketLocation("rest-api-data");
	}

	public boolean createDirInS3(final String dirName) {
		AmazonS3Client client = new AmazonS3Client(credentials);
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentLength(0);

		InputStream input = new ByteArrayInputStream(new byte[0]);
		PutObjectRequest req = new PutObjectRequest(BUCKET, dirName, input, meta);
		client.putObject(req);

		return true;
	}

	public String getMedia(String fileName) throws IOException {
		AmazonS3Client client = new AmazonS3Client(credentials);

		S3Object object = client.getObject(new GetObjectRequest(BUCKET, fileName));
		System.out.println("file name " + object.getKey());
		S3ObjectInputStream objectContent = object.getObjectContent();
		int temp = 0;

		while ((temp = objectContent.read()) != -1) {
			System.out.print((char) temp);
		}
		return object.getObjectMetadata().getContentType();
	}

	public String[] getAllFilesInBucket() {
		AmazonS3Client client = new AmazonS3Client(credentials);
		List<String> fileNames = new ArrayList<>();
		for (S3ObjectSummary obj : client.listObjects(BUCKET).getObjectSummaries()) {
			fileNames.add(obj.getKey());

		}
		System.out.println(fileNames);

		return null;
	}

	public InputStream getFile(String fileName) {

		AmazonS3Client client = new AmazonS3Client(credentials);

		S3Object object = client.getObject(new GetObjectRequest(BUCKET, fileName));
		System.out.println("file name " + object.getKey());
		S3ObjectInputStream objectContent = object.getObjectContent();

		return object.getObjectContent();

	}
}
