package com.huidonline.coupon.data;

import java.math.BigDecimal;

/**
 * Created by raber on 22/04/15.
 */
public class Article {

    private final String code;
    private final String name;
    private final BigDecimal price;

    public Article(final String code, final String name, final BigDecimal price) {
        System.out.println(" code :"+ code);
        System.out.println(" name :" + name);
        this.code = code;
        this.name = name;
        this.price = price;
    }


    public String getCode() {
        return code;
    }


    public String getName() {
        System.out.println(" getname :"+ name);
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
