package com.chiapin.calculator;

import java.math.BigDecimal;
/**
 * Created by chiapin_peng on 2015/8/27.
 */
public class Arith {

    private static final int DEF_DIV_SCALE = 16;
    /**
     * addition two numbers (v1 + v2)
     * @param v1
     * @param v2
     * @return v1+v2
     */
    public static double add(double v1,double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * subtracting two numbers (v1 - v2)
     * @param v1
     * @param v2
     * @return  v1-v2
     */
    public static double sub(double v1,double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

   /**
     * multiply two numbers (v1 * v2)
     * @param v1
     * @param v2
     * @return v1 * v2
     */
    public static double mul(double v1,double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * division the number (v1 / v2)
     * @param v1
     * @param v2
     * @return v1/v2
     */
    public static double div(double v1,double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * division the number (v1 / v2)
     * @param v1
     * @param v2
     * @param scale
     * @return v1/v2
     */
    public static double div(double v1,double v2,int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}

