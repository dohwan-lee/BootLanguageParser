package com.extractor.hangulparser.processor;

import com.extractor.hangulparser.external.TranslaterInterface;
import com.extractor.hangulparser.extractor.LanguageExtractorInterface;

/**
 * Created by joseph on 2020/05/10.
 */
public class JaProcessImpl extends JaProcessor {
	public JaProcessImpl(String rootPath, TranslaterInterface translater, LanguageExtractorInterface languageExtractor) {
		super(rootPath, translater, languageExtractor);
	}
}
