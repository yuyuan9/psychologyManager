package com.ht.biz.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.ProjectlibCopyMapper;
import com.ht.biz.service.ProjectlibCopyService;
import com.ht.entity.biz.solr.projectlib.ProjectlibCopy;
@Service("projectlibCopyService")
public class ProjectlibCopyServiceImpl extends ServiceImpl<ProjectlibCopyMapper, ProjectlibCopy> implements ProjectlibCopyService{

}
