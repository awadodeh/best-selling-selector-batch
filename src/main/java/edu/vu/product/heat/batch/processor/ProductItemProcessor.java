package edu.vu.product.heat.batch.processor;

import edu.vu.product.heat.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ProductItemProcessor implements ItemProcessor<Product, Product> {

    private static final Logger log = LoggerFactory.getLogger(ProductItemProcessor.class);
    //initial decision making
    private final static long priceRankThreshould = 14;

    @Override
    public Product process(Product product) throws Exception {

        //this method basically processes the products data.
        long price = product.getPrice();
        long rank = product.getRank();
        if (isBestSelling(product)) {
            product = toUpperCaseName(product);
            log.info("Normalizing (" + price + ") and (" + rank + ")");
        }

        return product;
    }

    private boolean isBestSelling(Product product) {
        return true;
    }

    private Product toUpperCaseName(Product product) {
        product.setName(product.getName().toUpperCase());
        return product;
    }
}
