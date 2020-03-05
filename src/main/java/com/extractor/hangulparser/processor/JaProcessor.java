package com.extractor.hangulparser.processor;

import com.extractor.hangulparser.data.FileInfo;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Locale;

@Component
public class JaProcessor extends MultilingualProcessor {
    // 최상위 디렉토리에서 추출 대상 언어의 문장을 추출하여 리스트에 담는다.
    @Override
    public List<FileInfo> extractor(File file, String filePath) throws Exception {
        return null;
    }
    // 추출된 리스트를 번역한다.
    @Override
    public String translate(String statement, Locale locale) throws Exception {
        return null;
    }
    // 번역된 문장을 파일에 쓴다.
    @Override
    public void excelWrite(File file, String filePath) throws Exception {

    }
    // 추출, 번역, 파일 쓰기
    @Override
    public void executor() throws Exception {

    }
}
