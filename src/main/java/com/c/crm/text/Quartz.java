package com.c.crm.text;


import com.c.crm.text.jobs.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class Quartz {
    public static void main(String[] args) throws Exception {
        Scheduler scheduler=StdSchedulerFactory.getDefaultScheduler();
        JobDetail jobDetail=JobBuilder.newJob(HelloJob.class).withIdentity("aaJob").build();

        jobDetail.getJobDataMap().put("key1","value1");
        jobDetail.getJobDataMap().put("key2","value2");

        SimpleScheduleBuilder simpleScheduleBuilder=SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).repeatForever();
        CronScheduleBuilder cronScheduleBuilder=CronScheduleBuilder.cronSchedule("1,5,11,20,27,35 * * * * ?");

        Trigger aaJob = TriggerBuilder.newTrigger().withIdentity("aaJob").withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,aaJob);
        scheduler.start();

    }
}
