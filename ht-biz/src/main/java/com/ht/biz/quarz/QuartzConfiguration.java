package com.ht.biz.quarz;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class QuartzConfiguration {
	
	// 配置定时任务1（立项库上传重复记录）
	@Bean(name = "projectlibDetail")
    public MethodInvokingJobDetailFactoryBean firstJobDetail(ProjectlibQuarz projectlibQuarz) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(projectlibQuarz);
        // 需要执行的方法
        jobDetail.setTargetMethod("task");
        return jobDetail;
    }


	// 配置触发器1
    @Bean(name = "projectlibTrigger")
    public CronTriggerFactoryBean firstTrigger(JobDetail projectlibDetail) {
    	CronTriggerFactoryBean  trigger = new CronTriggerFactoryBean ();
        trigger.setJobDetail(projectlibDetail);
        // 设置任务启动延迟
        trigger.setStartDelay(0);
        // cron表达式 
        //<value>0 30 17 ? * *</value>  <!-- 每天17:30触发 -->
	    trigger.setCronExpression("0 10 19 ? * *");
        return trigger;
    }
    
 // 配置定时任务2（政策库上传重复记录）
 	@Bean(name = "policylibDetail")
     public MethodInvokingJobDetailFactoryBean firstJobDetail2(PolicylibQuarz policylibQuarz) {
         MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
         // 是否并发执行
         jobDetail.setConcurrent(false);
         // 为需要执行的实体类对应的对象
         jobDetail.setTargetObject(policylibQuarz);
         // 需要执行的方法
         jobDetail.setTargetMethod("task");
         return jobDetail;
     }


 	// 配置触发器2
     @Bean(name = "policylibTrigger")
     public CronTriggerFactoryBean firstTrigger2(JobDetail policylibDetail) {
     	CronTriggerFactoryBean  trigger = new CronTriggerFactoryBean ();
         trigger.setJobDetail(policylibDetail);
         // 设置任务启动延迟
         trigger.setStartDelay(0);
         // cron表达式 
         //<value>0 30 17 ? * *</value>  <!-- 每天17:30触发 -->
 	    trigger.setCronExpression("0 30 19 ? * *");
         return trigger;
     }
     // 配置定时任务3（政策库速递回收站清理）
  	@Bean(name = "policyDigDetail")
      public MethodInvokingJobDetailFactoryBean firstJobDetail3(PolicyDigQuarz policyDigQuarz) {
          MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
          // 是否并发执行
          jobDetail.setConcurrent(false);
          // 为需要执行的实体类对应的对象
          jobDetail.setTargetObject(policyDigQuarz);
          // 需要执行的方法
          jobDetail.setTargetMethod("task");
          return jobDetail;
      }


  	// 配置触发器3
      @Bean(name = "policyDigTrigger")
      public CronTriggerFactoryBean firstTrigger3(JobDetail policyDigDetail) {
      	CronTriggerFactoryBean  trigger = new CronTriggerFactoryBean ();
          trigger.setJobDetail(policyDigDetail);
          // 设置任务启动延迟
          trigger.setStartDelay(0);
          // cron表达式 
          //<value>0 30 17 ? * *</value>  <!-- 每天17:30触发 -->
  	    trigger.setCronExpression("0 50 19 ? * *");
          return trigger;
      }	
      // 配置定时任务4（保障金过期检测）
    	@Bean(name = "guaranteeMoneyDetail")
        public MethodInvokingJobDetailFactoryBean firstJobDetail4(GuaranteeMoneyQuarz guaranteeMoneyQuarz) {
            MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
            // 是否并发执行
            jobDetail.setConcurrent(false);
            // 为需要执行的实体类对应的对象
            jobDetail.setTargetObject(guaranteeMoneyQuarz);
            // 需要执行的方法
            jobDetail.setTargetMethod("task");
            return jobDetail;
        }


    	// 配置触发器4
        @Bean(name = "guaranteeMoneyTrigger")
        public CronTriggerFactoryBean firstTrigger4(JobDetail guaranteeMoneyDetail) {
        	CronTriggerFactoryBean  trigger = new CronTriggerFactoryBean ();
            trigger.setJobDetail(guaranteeMoneyDetail);
            // 设置任务启动延迟
            trigger.setStartDelay(0);
            // cron表达式 
            //<value>0 30 17 ? * *</value>  <!-- 每天17:30触发 -->
    	    trigger.setCronExpression("0 0 0 ? * *");
            return trigger;
        }	
    
 // 配置Scheduler
    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger projectlibTrigger,Trigger policylibTrigger,Trigger policyDigTrigger,Trigger guaranteeMoneyTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();  
        // 延时启动，应用启动1秒后
        bean.setStartupDelay(1);  
        // 注册触发器
        bean.setTriggers(projectlibTrigger,policylibTrigger,policyDigTrigger,guaranteeMoneyTrigger);
        
        return bean;
    }  
}
