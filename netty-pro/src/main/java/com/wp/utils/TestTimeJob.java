package com.wp.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class Temp extends Thread
{
    public void run()
    {
        System.out.println("run");
    }
}

public class TestTimeJob
{
    public static void main(String args[]) throws Exception
    {
        Temp command = new Temp();
        
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> scheduleTask = scheduler.scheduleWithFixedDelay(command, 2, 3, TimeUnit.SECONDS);
    }
}