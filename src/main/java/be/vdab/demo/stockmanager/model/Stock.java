package be.vdab.demo.stockmanager.model;

import io.swagger.annotations.ApiModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@ApiModel("Stock")
@Table(name = "stock")
public class Stock {

    @Id
    private String productId;
    private String sku;
    private int amountAvailable;

    public Stock() {
    }

    public Stock(String productId, String sku, int amountAvailable) {
        this.productId = productId;
        this.sku = sku;
        this.amountAvailable = amountAvailable;
    }

    public String getProductId() {
        return productId;
    }

    public String getSku() {
        return sku;
    }

    public int getAmountAvailable() {
        return amountAvailable;
    }
}
