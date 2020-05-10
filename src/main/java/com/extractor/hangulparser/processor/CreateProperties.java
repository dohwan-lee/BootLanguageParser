package com.extractor.hangulparser.processor;

import com.extractor.hangulparser.data.FileInfo;

import java.io.File;
import java.util.List;

/**
 * Created by joseph on 2020/05/10.
 */
public interface CreateProperties {
	List<FileInfo> readExcel() throws Exception;            // 엑셀에서 정형화된 파일 정보를 추출하여 리스트로 리턴
	boolean createProperties(File file) throws Exception;   // properties 파일 생성
}
