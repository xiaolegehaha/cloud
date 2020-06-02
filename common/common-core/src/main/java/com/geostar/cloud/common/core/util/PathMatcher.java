package com.geostar.cloud.common.core.util;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

@Component
public class PathMatcher {

    public Boolean matchUri(String uri, String requestPath, boolean fullCheck) {
        AntPathMatcher matcher = new AntPathMatcher();
        if (!fullCheck) {
            requestPath = parseRequestPath(requestPath);
        }
        if ((uri != null) &&
                (matcher.match(uri, requestPath))) {
            return Boolean.valueOf(true);
        }

        return Boolean.valueOf(false);
    }

    public String parseRequestPath(String requestPath) {
        if (requestPath != null) {
            if (requestPath.startsWith("/")) {
                String[] array = requestPath.split("/");
                if ((array.length > 1) && (array[1].contains("-"))) {
                    String tmp = "";
                    for (int i = 2; i < array.length; i++) {
                        tmp = tmp + "/" + array[i];
                    }
                    requestPath = tmp;
                }
            } else {
                String[] array = requestPath.split("/");
                if ((array.length > 0) && (array[0].contains("-"))) {
                    String tmp = "";
                    for (int i = 1; i < array.length; i++) {
                        tmp = tmp + "/" + array[i];
                    }
                    requestPath = tmp;
                }
            }
            return requestPath;
        }
        return null;
    }
}
