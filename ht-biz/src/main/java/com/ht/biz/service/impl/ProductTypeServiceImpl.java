package com.ht.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.ProductTypeMapper;
import com.ht.biz.service.ProductTypeService;
import com.ht.commons.utils.CopyUtils;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.Tools;
import com.ht.entity.biz.product.Product;
import com.ht.entity.biz.product.ProductType;
import com.ht.entity.biz.product.ProductVo;
import com.ht.entity.biz.product.productenum.ApplyType;
import com.ht.entity.biz.product.productenum.Productplevel;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements ProductTypeService {


    @Override
    public ProductVo getProductvo(Product product) {
        ProductVo productVo = new ProductVo();
        CopyUtils.copyProperties(product,productVo);
        if(null!=product.getPlevel()){
            productVo.setPlevelname( Productplevel.getValue( product.getPlevel() ) );
        }
        if(StringUtils.isNotBlank( product.getProducttypeone() )){
            ProductType productType = baseMapper.selectById( product.getProducttypeone()  );
            if(null!=productType){
                productVo.setProducttypeonename( productType.getName() );
                productVo.setIsscience(productType.getIsscience()  );
            }
        }
        if(StringUtils.isNotBlank( product.getProducttypetwo() )){
            ProductType productType = baseMapper.selectById( product.getProducttypetwo()  );
            if(null!=productType){
                productVo.setProducttypetwoname( productType.getName() );
            }
        }
        if(StringUtils.isNotBlank( product.getProducttypethree() )){
            ProductType productType = baseMapper.selectById( product.getProducttypethree()  );
            if(null!= productType){
                productVo.setProducttypethreename(  productType.getName() );
            }
        }
        if(productVo.isIsscience()){
            if(null!=productVo.getApplytimetype()){
                if(1==productVo.getApplytimetype()){
                    if(null != product.getEndtime()){
                        Date date1 = new Date(  );
                        Date date2 = product.getEndtime();
                        int date = Tools.differentDays( date1, date2);
                        if(date>0){
                            productVo.setIsapply( true );
                            productVo.setStype( ApplyType.SECTION_APPLY.getCode());
                            productVo.setRemainingdate(date >0 ? date :0);
                        }else{
                            productVo.setStype(ApplyType.END_APPLU.getCode());
                        }
                    }
                }
                if(2==productVo.getApplytimetype()){
                    productVo.setIsapply( true );
                    productVo.setStype( ApplyType.ALL_APPLY.getCode());
                }
            }

        }else{
            productVo.setStype( ApplyType.No_SCIENCE.getCode() );
            productVo.setIsapply( true );
        }
        return productVo ;
    }


    @Override
    public void getPageDatevo(PageData pageData) {
        if(StringUtils.isNotBlank(pageData.getString( "producttypeone" ))){
            ProductType productType = baseMapper.selectById( pageData.getString( "producttypeone" )  );
            if(null!=productType){
                if(!productType.getIsscience()){
                    pageData.put( "stype" ,ApplyType.No_SCIENCE.getCode());
                }else{
                    if(null!=pageData.get( "applytimetype" )){
                        int timetype = (int) pageData.get( "applytimetype" );
                        if(2==timetype){
                            pageData.put( "stype" ,ApplyType.ALL_APPLY.getCode());
                        }
                        if(1==timetype){
                            if(null != pageData.get( "endtime" )){
                                Date date1 = new Date(  );
                                Date date2 = (Date) pageData.get( "endtime" );
                                int date = Tools.differentDays( date1, date2);
                                if(date>0){
                                    pageData.put( "stype" ,ApplyType.SECTION_APPLY.getCode());
                                    pageData.put( "remainingdate" ,date >0 ? date :0);
                                }

                            }
                        }
                    }
                }
            }
        }

    }

}
