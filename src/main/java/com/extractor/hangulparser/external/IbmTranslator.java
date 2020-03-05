package com.extractor.hangulparser.external;

import com.fasterxml.jackson.core.JsonParser;
import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.Translation;
import com.ibm.watson.language_translator.v3.model.TranslationResult;
import com.ibm.watson.language_translator.v3.util.Language;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IbmTranslator implements TranslaterInterface{

    @Override
    public String translate(String source, Object parser) {
        Authenticator authenticator = new IamAuthenticator("mIAzKC4j3Z5nlpg9XUCr8-dhog4VzCkyIT2B6f8SHfUP");
        LanguageTranslator service = new LanguageTranslator("2018-05-01", authenticator);

        TranslateOptions translateOptions = new TranslateOptions.Builder()
                .addText(StringUtils.isBlank(source) ? "" : source)
                .source(Language.KOREAN)
                .target(Language.JAPANESE)
                .build();

        TranslationResult translationResult = service.translate(translateOptions).execute().getResult();

        System.out.println(translationResult);

        List<Translation> list = translationResult.getTranslations();
        StringBuffer sb = new StringBuffer();
        list.stream()
                .forEach(model -> sb.append(model.getTranslation()));

        return sb.toString();
    }
}
