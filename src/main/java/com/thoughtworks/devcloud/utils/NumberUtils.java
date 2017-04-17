package com.thoughtworks.devcloud.utils;


import java.math.BigDecimal;

public class NumberUtils {

    private static final String PERCENT_FLAG = "%";
    private static final int SCALE = 2;
    private static final String PATTERN = "#0.00";

    public static String getPercentFromBigDecimal(BigDecimal data){
        data = data.setScale(SCALE, BigDecimal.ROUND_HALF_EVEN) ;
        java.text.DecimalFormat df = new java.text.DecimalFormat(PATTERN);
        StringBuffer buffer = new StringBuffer();
        buffer.append(df.format(data)).append(PERCENT_FLAG);
        return buffer.toString();
    }
}
