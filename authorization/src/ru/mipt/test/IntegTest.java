package ru.mipt.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.mipt.message.Message;
import ru.mipt.message.ReturnCode;
import ru.mipt.session.Session;
import ru.mipt.threads.MessageListener;
import ru.mipt.threads.ThreadedClient;
import ru.mipt.threads.ThreadedServer;

/**
 * Created by artem on 01.12.15.
 */
public class IntegTest implements MessageListener{

    private ThreadedClient client;
    private ThreadedServer server;
    private short resultCode;
    private int chatId;


    @Before
    public void setup() throws Exception {
        Thread.sleep(1000);

        new Thread(() -> {
            try {
                server = new ThreadedServer();
                server.startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(1000);

        client = new ThreadedClient();
        client.getHandler().addListener(this);


    }


//    @Test
//    public void test1() throws Exception {
//        Thread.sleep(1000);
//        boolean mustBeTrue =gotResult(ReturnCode.SUCCESS,/*on request*/"message");
//        server.stopServer();
//        assert/*that*/(!mustBeTrue);
//    }
//
//    @Test
//    public void test2() throws Exception {
//        Thread.sleep(1000);
//        boolean mustBeTrue = gotResult(ReturnCode.SUCCESS,/*on request*/"\\login 1 2");
//        server.stopServer();
//        assert/*that*/(!mustBeTrue);
//        Thread.sleep(1000);
//        mustBeTrue = gotResult(ReturnCode.SUCCESS,/*on request*/"\\login 1 1");
//        server.stopServer();
//        assert/*that*/(mustBeTrue);
//    }
//
//    @Test
//    public void test3() throws Exception {
//        Thread.sleep(1000);
//        boolean mustBeTrue = gotResult(ReturnCode.SUCCESS,/*on request*/"\\login 1 1");
//        server.stopServer();
//        assert/*that*/(mustBeTrue);
//    }
//
//
//    @Test
//    public void test4() throws Exception {
//
//        client.processInput("\\login 1 1");
//        Thread.sleep(1000);
//        boolean mustBeTrue = gotResult(ReturnCode.SUCCESS,"\\chat send 1 afasf");
//
//        server.stopServer();
//        assert/*that*/(mustBeTrue);
//    }

    @Test
    public void test5() throws Exception {
        Thread.sleep(1000);
        client.processInput("\\login 4 4");
        new Thread(() -> {
            try {
                ThreadedClient client2 = new ThreadedClient();
                client2.processInput("\\login 5 5");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(2000);
        boolean mustBeTrue = gotResult(ReturnCode.SUCCESS,"\\chat create 3");
        assert/*that*/(mustBeTrue);
        Thread.sleep(2000);
        mustBeTrue = gotResult(ReturnCode.SUCCESS,"\\chat send 1 afasf");
        assert/*that*/(mustBeTrue);
        Thread.sleep(2000);
        mustBeTrue = gotResult(ReturnCode.SUCCESS,"\\chat history");
        assert/*that*/(mustBeTrue);
        server.stopServer();
//        assert/*that*/(mustBeTrue);
    }

    private boolean gotResult(Short returnCode, String on) throws Exception {
        client.processInput(on);
        Thread.sleep(2000);
        return resultCode == returnCode;
    }

    @After
    public void close() {
//        server.stopServer();
    }

    @Override
    public void onMessage(Message message) {
            resultCode = message.getReturnCode();
    }
}

