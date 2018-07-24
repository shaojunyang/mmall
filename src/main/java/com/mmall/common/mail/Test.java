package com.mmall.common.mail;

public class Test {
    @org.junit.Test
    public void test() {
        AttMailsendServiceImpl attMailsendService = new AttMailsendServiceImpl();
        attMailsendService.sendMessage();
    }
}
