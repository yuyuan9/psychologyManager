package com.ht.entity.biz.sys.keywork;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.commons.support.tree.entity.TreeEntity;
import com.ht.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;

@ApiModel(value="文库关键字",description="Keywork")
@TableName(value="t_keywork")
public class Keywork extends TreeEntity {
    private String keywordname; //名称

    private String describet ;//描述
    public String getKeywordname() {
        return keywordname; 
    }

    public void setKeywordname(String keywordname) {
        this.keywordname = keywordname;
    }

	public String getDescribet() {
		return describet;
	}

	public void setDescribet(String describet) {
		this.describet = describet;
	}


}
