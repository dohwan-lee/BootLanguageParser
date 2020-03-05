package com.extractor.hangulparser.processor;

import com.extractor.hangulparser.data.FileInfo;

import java.io.File;
import java.util.List;
import java.util.Locale;

public abstract class MultilingualProcessor {
    // 최상위 디렉토리에서 추출 대상 언어의 문장을 추출하여 리스트에 담는다.
    public abstract List<FileInfo> extractor(File file, String filePath) throws Exception;
    // 추출된 리스트를 번역한다.
    public abstract String translate(String statement, Locale locale) throws Exception;
    // 번역된 문장을 파일에 쓴다.
    public abstract void excelWrite(File file, String filePath) throws Exception;
    // 추출, 번역, 파일 쓰기
    public abstract void executor() throws Exception;
}
