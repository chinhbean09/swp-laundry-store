package com.project.SWP391.security.utils;

import java.util.Arrays;
import java.util.List;

public  class Utils {
    public static String getPubId (String url){
        List<String> items = Arrays.asList(url.split("/"));
        String item = (items.get(items.size() - 1));
        String[] res = item.split("[.]", 0);
        return res[0];

    }
}
