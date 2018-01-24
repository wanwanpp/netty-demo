package com.wp.bytebuf;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;

/**
 * @author 王萍
 * @date 2018/1/5 0005
 */

public class ApiDemo {

    public static void main(String[] args) {

//        堆缓冲区
        Unpooled.buffer(1024);
//        直接缓冲区
        Unpooled.directBuffer(1024);

        //直接内存中创建
        UnpooledByteBufAllocator.DEFAULT.directBuffer(1024);
        //堆内存中创建
        UnpooledByteBufAllocator.DEFAULT.heapBuffer(1024);
        //可堆内存中可直接内存中
        UnpooledByteBufAllocator.DEFAULT.buffer(1024);
        //可堆内存中可直接内存中
        UnpooledByteBufAllocator.DEFAULT.ioBuffer(1024);

//        池化
        PooledByteBufAllocator.DEFAULT.directBuffer(1024);

    }
}
