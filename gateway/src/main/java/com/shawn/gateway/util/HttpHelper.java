package com.shawn.gateway.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


public class HttpHelper {

    private static Logger logger = LoggerFactory.getLogger(HttpHelper.class);

    private static final String CHAR_ENCODE_FORMAT = "UTF-8";

    /**
     * 获取请求Body
     *
     * @param request
     * @return
     */
    public static String getBodyString(ServletRequest request) {

        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;

        try {

            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(CHAR_ENCODE_FORMAT)));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            logger.error("解析请求体异常");
        } finally {

            if (inputStream != null) {

                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }

            }

            if (reader != null) {

                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }

            }
        }

        return sb.toString();
    }


    public static void setRequestParams(Map<String, List<String>> params,String queryString){

        if(StringUtils.isBlank(queryString)) {
            return;
        }

        StringTokenizer paramSplis = new StringTokenizer(queryString, "&");

        while (paramSplis.hasMoreTokens()) {

            String paramSpli = paramSplis.nextToken();

            String[] paramStrs = paramSpli.split("=");

            String name = paramStrs[0];
            String value = null;

            if(paramStrs.length > 1){
                value = paramStrs[1];
            }

            if(!StringUtils.isBlank(name)){
                try {
                    name = URLDecoder.decode(name, CHAR_ENCODE_FORMAT);
                    if(!StringUtils.isBlank(value)) {
                        value = URLDecoder.decode(value, CHAR_ENCODE_FORMAT);
                    }
                } catch (Exception e) {
                    break;
                }
            }

            List<String> valueList = params.get(name);
            if (valueList == null) {
                valueList = new LinkedList<String>();
                params.put(name, valueList);
            }

            if(!StringUtils.isBlank(value)){
                if(!valueList.isEmpty()) {
                    boolean tf = false;
                    for (String s:valueList) {
                        if(s.equals(value)){
                           tf = true;
                            break;
                        }
                    }
                    if(!tf){
                        valueList.add(value);
                    }
                }else{
                    valueList.add(value);
                }
            }
        }
    }

}
