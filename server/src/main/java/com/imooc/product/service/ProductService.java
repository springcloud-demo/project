package com.imooc.product.service;

import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutput;
import com.imooc.product.dataobject.ProductInfo;

import java.util.List;

public interface ProductService {
    /*
    * 查询所有在架商品列表
    * */
    List<ProductInfo> findUpAll();
    List<ProductInfoOutput> findList(List<String> productIdList);
    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);
}
