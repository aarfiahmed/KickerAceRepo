package com.app.model;

public class FileInfo {
String fileName;
String contentType;
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}
public String getContentType() {
	return contentType;
}
public void setContentType(String contentType) {
	this.contentType = contentType;
}

public FileInfo(String fileName, String contentType) {
	this.fileName=fileName;
	this.contentType=contentType;
}

@Override
public String toString() {
	return "FileInfo [fileName=" + fileName + ", contentType=" + contentType + "]";
}


}
