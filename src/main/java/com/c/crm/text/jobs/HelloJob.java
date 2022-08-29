package com.c.crm.text.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("自定义Job执行"+new Date());
        JobDataMap jobDataMap=jobExecutionContext.getJobDetail().getJobDataMap();
        final Set<Map.Entry<String,Object>> entries =jobDataMap.entrySet();
        entries.forEach((entry)->{
            String key= entry.getKey();
            Object value=entry.getValue();
            System.out.println(key+"="+value);
        });

    }
}
