package com.extractor.hangulparser.template;

import com.extractor.hangulparser.data.FileInfo;

import java.util.List;
import java.util.Locale;

public abstract class TransExtractTemplate {
    // 최상위 디렉토리에서 추출 대상 언어의 문장을 추출하여 리스트에 담는다.
    protected abstract List<FileInfo> extractor() throws Exception;
    // 추출된 리스트를 번역한다.
    protected abstract String translate(String statement, Locale locale) throws Exception;
    // 번역된 문장을 파일에 쓴다.
    protected abstract void excelWrite(List<FileInfo> fileInfos) throws Exception;
    // 추출, 번역, 파일 쓰기
    public abstract void executor() throws Exception;

}
