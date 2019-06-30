package com.imooc.product.common;

import lombok.Data;

/**
 * @ClassName: DecreaseStockInput
 * @Description: TODO
 * @Author: ZhangChen
 * @Date: 2019/5/22 10:11
 **/
@Data
public class DecreaseStockInput {
    private String productId;

    private Integer productQuantity;

    public DecreaseStockInput(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public DecreaseStockInput() {
    }
}
