package com.huidonline.coupon.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by raber on 22/04/15.
 */
public class Order {

    private final String orderId;
    private final String factuurId;
    private Customer customer;
    private Date date;
    private BigDecimal total;
    private List<Article> articles;


    public Order(final String orderId, final String factuurId) {
        this.orderId = orderId;
        this.factuurId = factuurId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!getOrderId().equals(order.getOrderId())) return false;
        return getFactuurId().equals(order.getFactuurId());

    }

    @Override
    public int hashCode() {
        int result = getOrderId().hashCode();
        result = 31 * result + getFactuurId().hashCode();
        return result;
    }


    public void addArticles(Article article) {

        if (articles == null) {
            articles = new ArrayList<Article>();
        }
         articles.add(article);
    }

    @Override
    public String toString() {
        return "com.huidonline.coupon.data.Order{" +
                "orderId='" + orderId + '\'' +
                ", factuurId='" + factuurId + '\'' +
                ", customer=" + customer +
                ", date=" + date +
                ", total=" + total +
                ", articles=" + articles +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }


    public String getFactuurId() {
        return factuurId;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getArticlesAsString() {
        StringBuilder stringBuffer = new StringBuilder();
        for (Article article : articles) {

            System.out.println(" article :"+ article.getName() ) ;
            stringBuffer.append(article.getName()+" ");

        }
        return stringBuffer.toString();
    }
}
