package com.extractor.hangulparser;

import com.extractor.hangulparser.external.IbmTranslator;
import com.extractor.hangulparser.extractor.KoreanFinder;
import com.extractor.hangulparser.processor.JaProcessImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HangulparserApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HangulparserApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//String targetDir = "/Users/joseph/project_jp_all/ohprint-backoffice";
		//String targetDir = "/Users/joseph/project_jp_all/ohprint-analytics";
		//String targetDir = "/Users/joseph/project_jp_all/ohprint-ground-api";   // 아무 것도 없음
		//String targetDir = "/Users/joseph/project_jp_all/ohprint-framework";       // 아무 것도 없음
		//String targetDir = "/Users/joseph/project_jp_all/ohprint-batch";
		String targetDir = "/Users/joseph/project_jp_all/ohprint-service-api";


		JaProcessImpl jp = new JaProcessImpl(targetDir, new IbmTranslator(), new KoreanFinder());

		jp.executor();


	}
}
