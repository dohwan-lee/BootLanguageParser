package com.extractor.hangulparser.external;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Component
public class PapagoTranslator implements TranslaterInterface {

    private static final String CLIEND_ID = "sAtAb8M3UcWWmCqq77sK";
    private static final String CLIENT_SECRET = "Go0wtIT61X";

    @Override
    public String translate(String source, Object parser) {
        try {
            String text = URLEncoder.encode(source, "UTF-8");
            //"https://openapi.naver.com/v1/language/translate";
            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", CLIEND_ID);
            con.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);
            // post request
            String postParams = "source=ko&target=ja&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());

            String transTxt = "";

            if (response.length() > 0) {
                JsonElement element = ((JsonParser)parser).parse(response.toString());
                transTxt = element.getAsJsonObject().get("message")
                        .getAsJsonObject().get("result")
                        .getAsJsonObject().get("translatedText").getAsString();
            }
            return transTxt;
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }
}
