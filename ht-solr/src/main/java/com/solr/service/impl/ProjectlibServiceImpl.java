package com.solr.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.entity.biz.solr.projectlib.Projectlib;
import com.solr.mapper.ProjectlibMapper;
import com.solr.service.ProjectlibService;
@Service("projectlibService")
public class ProjectlibServiceImpl extends ServiceImpl<ProjectlibMapper, Projectlib> implements ProjectlibService{

}
