package com.extractor.hangulparser.processor;

import com.extractor.hangulparser.data.FileInfo;
import com.extractor.hangulparser.external.TranslaterInterface;
import com.extractor.hangulparser.extractor.LanguageExtractorInterface;
import com.google.common.io.Files;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.subtlelib.poi.api.sheet.SheetContext;
import org.subtlelib.poi.api.workbook.WorkbookContext;
import org.subtlelib.poi.impl.workbook.WorkbookContextFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class JaProcessor {

    private String rootPath;

    private TranslaterInterface translater;

    private LanguageExtractorInterface languageExtractor;

    public static <T> T wrap(ExceptionSupplier<T> z) {
        try {
            return z.get();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public JaProcessor(String rootPath, TranslaterInterface translater, LanguageExtractorInterface languageExtractor) {
        this.rootPath = rootPath;
        this.translater = translater;
        this.languageExtractor = languageExtractor;
    }

    private void nullCheck() {
        if(StringUtils.isEmpty(rootPath)) throw new NullPointerException("rootPath is null!");
        if(translater == null) throw new NullPointerException("translater is null!");
        if(languageExtractor == null) throw new NullPointerException("languageExtractor is null!");
    }


    // 최상위 디렉토리에서 추출 대상 언어의 문장을 추출하여 리스트에 담는다.
    protected List<FileInfo> extractor() throws Exception {
        if(StringUtils.isEmpty(rootPath)) {
            return new ArrayList<>();
        }

        List<File> files = FileUtils.listFiles(new File(rootPath), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)
                .stream()
                .filter(file -> ignorePaths(file.getAbsolutePath()))
                .filter(file -> ignoreExtentions(file.getName()))
                .map(file->file.getAbsoluteFile())
                .collect(Collectors.toList());

        List<FileInfo> fileInfos = new ArrayList<>();


        for(File korExtractFile : files) {
            fileInfos.addAll(wrap(() -> languageExtractor.findLanguageExtractor(korExtractFile)));
        }

        return fileInfos;
    }

    // 추출된 문장을 번역한다.
    protected String translate(String statement, Locale locale) throws Exception {
        return translater.translate(statement, new JsonParser());
    }

    // 번역된 문장을 파일에 쓴다.
    protected void excelWrite(List<FileInfo> fileInfos) throws Exception {

        WorkbookContextFactory ctx = WorkbookContextFactory.useXlsx();
        WorkbookContext workbookCtx = ctx.createWorkbook();

        SheetContext sheetCtx = workbookCtx.createSheet("HangulTranslate");

        sheetCtx.nextRow()
                .skipCell()
                .header("파일 경로").setColumnWidth(80).setRowHeight(28)
                .header("파일명").setColumnWidth(50)
                .header("위치(Line)").setColumnWidth(10)
                .header("시작 위치").setColumnWidth(5)
                .header("종료 위치").setColumnWidth(5)
                .header("추출 문자열").setColumnWidth(70)
                .header("번역 문자열").setColumnWidth(70)
                .header("프로퍼티(kor)").setColumnWidth(50)
                .header("프로퍼티(jpn)").setColumnWidth(50);

        String beStr = "";
        AtomicReference<Integer> fileSameCnt = new AtomicReference<>(0);

        fileInfos.stream()
                .forEach(data -> {

                    String fileName = data.getFileName();

                    if (!fileName.contains(beStr)) {
                        fileSameCnt.set(0);
                    } else {
                        fileSameCnt.getAndSet(fileSameCnt.get() + 1);
                    }

                    sheetCtx.nextRow()
                            .skipCell()
                            .text(String.valueOf(data.getFilePath())).setRowHeight(28)
                            .text(data.getFileName())
                            .number(data.getLineNumber())
                            .number(data.getStartPos())
                            .number(data.getEndPos())
                            .text(data.getContent())
                            //.text(papagoTranslate(data.getContent(), parser));
                            //.text(ibmTranslate(data.getContent()));
                            //.text(kakaoTranslate(data.getContent(), parser));
                            //.text(naverTranslate(data.getContent(), parser));
                            .text("")
                            .text(fileName.toUpperCase() + "." + fileSameCnt.get() + "=" + data.getContent())
                            .text(fileName.toUpperCase() + "." + fileSameCnt.get() + "=");

                });

        Files.write(workbookCtx.toNativeBytes(), new File(rootPath + File.separator + "RequestTranslateFile_"
                + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                +".xlsx"));


    }

    // 추출, 번역, 파일 쓰기
    public void executor() throws Exception {

        nullCheck();

        excelWrite(extractor());

    }

    private boolean ignorePaths(String path) {

        List<String> ignores = Arrays.asList("target","IBChart","test","IBSheet");

        return !(ignores.stream().filter(ignStr -> path.contains(ignStr)).count() > 0); // 1 있으면 실패

    }

    private boolean ignoreExtentions(String fileName) {
        List<String> ignoresExts = Arrays.asList("jsp", "properties" ,"java", "xml", "html");
        String[] fileNms = fileName.split("\\.");

        if(fileNms.length >= 2)
            return (ignoresExts.stream().filter(ignStr -> fileName.contains(ignStr)).count() > 0);

        return false;
    }
}
