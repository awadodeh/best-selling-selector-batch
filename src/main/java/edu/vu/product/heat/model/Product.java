package edu.vu.product.heat.model;

import java.util.UUID;

public class Product {

    private String id;
    private String name;
    private long price;
    private long rank;

    public Product() {
    }

    public Product(String id, String name, long price, long rank) {
        this.id = UUID.randomUUID().toString();
        ;
        this.name = name;
        this.price = price;
        this.rank = rank;
    }

    public Product(String name, long price, long rank) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.rank = rank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", rank=" + rank +
                '}';
    }
}
