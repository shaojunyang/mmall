package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 前台商品 的controller
 *
 * @author
 * @create 2017-12-06 下午8:50
 **/
@Controller
@RequestMapping("/product")
public class ProductController {

    // 注入service
    @Autowired
    private IProductService iProductService;

    /**
     * 前台 商品 详情
     * @param productId
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId) {
        return iProductService.getProductDetail(productId);
    }

 /**
     * 前台 商品 详情
     * @param productId
     * @return
     */
    @RequestMapping(value = "/{productId}")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detailRESTful(@PathVariable Integer productId) {
        return iProductService.getProductDetail(productId);
    }



    /**
     * 前台商品 搜索
     * @param keyword
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword",required = false) String keyword,
                                           @RequestParam(value = "categoryId",required = false) Integer categoryId,
                                           @RequestParam(value ="pageNum" ,defaultValue = "1") Integer pageNum,
                                           @RequestParam(value ="pageSize" ,defaultValue = "10") Integer pageSize,
                                           @RequestParam(value = "orderBy",defaultValue = "") String orderBy) {

        //     调用 业务
        return  iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);

    }



    /**
     * 前台商品 搜索
     * @param keyword
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    // http://localhost:8080/product/%E6%89%8B%E6%9C%BA/100012/1/10/price_asc
    @RequestMapping(value = "/{keyword}/{categoryId}/{pageNum}/{pageSize}/{orderBy}")
    @ResponseBody
    public ServerResponse<PageInfo> listRESTful(@PathVariable String keyword,
                                           @PathVariable Integer categoryId,
                                           @PathVariable Integer pageNum,
                                           @PathVariable Integer pageSize,
                                           @PathVariable String orderBy) {
       pageNum = pageNum == null ? 1 : pageNum;
        pageSize  = pageSize == null ? 1 : pageSize;
        orderBy = orderBy == null ? "price_asc" : orderBy;

        //     调用 业务
        return  iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);

    }

    public static void main(String[] args) {

    }




}
