package com.extractor.hangulparser.data;

import lombok.Data;

import java.io.File;

@Data
public class FileInfo {
	private String filePullName;
	private File filePath;
	private String fileName;
	private Integer lineNumber;
	private String content;
	private String help;
	private Integer startPos;
	private Integer endPos;
}
