package uz.ulugbek.aws.lambda.orderapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class OrderDto implements Serializable {
    @JsonProperty("id")
    private int id;
    @JsonProperty("itemName")
    private String itemName;
    @JsonProperty("quantity")
    private int quantity;

    public OrderDto() {
    }

    public OrderDto(int id, String itemName, int quantity) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
