package com.extractor.hangulparser.extractor;

import com.extractor.hangulparser.data.FileInfo;

import java.io.File;
import java.util.List;

public interface LanguageExtractorInterface {
    List<FileInfo> findLanguageExtractor(File file) throws Exception;
}
