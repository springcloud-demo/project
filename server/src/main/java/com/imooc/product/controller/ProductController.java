package com.imooc.product.controller;

import com.imooc.product.VO.ProductInfoVO;
import com.imooc.product.VO.ProductVO;
import com.imooc.product.VO.ResultVO;
import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutput;
import com.imooc.product.dataobject.ProductCategory;
import com.imooc.product.dataobject.ProductInfo;
import com.imooc.product.service.CategoryService;
import com.imooc.product.service.ProductService;
import com.imooc.product.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    public ResultVO<ProductVO> list(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //从商品服务取得所有已上架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        /*拉姆达表达式*/
        //取出上架商品中的所有类别id
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());
        //根据取出的类别id去数据库中查询具体的类别集合
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
        //新建我们要向前端返回的数据
        List<ProductVO> productVOList = new ArrayList<>();
        //将取出的商品与取出的类别进行组合,以满足前端的需求
        for (ProductCategory productCategory:categoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryname(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for(ProductInfo productInfo:productInfoList){
                //当商品id与类别id相等时
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO  = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        //将组合完的数据返回前端
        return ResultVOUtil.success(productVOList);
    }
    /*获取商品列表(给订单服务用的)*/
    @PostMapping("/listForOrder")
    public List<ProductInfoOutput> listForOrder(@RequestBody List<String> productList){
        log.info("商品查询服务开始");
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e){
//            e.printStackTrace();
//        }
        return productService.findList(productList);
    }

    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList){
        log.info("商品减少服务开始");
        productService.decreaseStock(decreaseStockInputList);
    }
    //怎么跨电脑?
}
