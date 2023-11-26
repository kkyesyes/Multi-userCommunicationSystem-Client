package com.kk.qqclient.service;

import com.kk.qqcommon.Message;
import com.kk.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 客户端连接服务端线程
 *
 * @author KK
 * @version 1.0
 */
public class ClientConnectServerThread extends Thread {
    private Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
//            System.out.println("客户端线程等待从服务端发送的消息");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message ms = (Message)ois.readObject();

                if (ms.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {
                    String[] onlineUsers = ms.getContent().split(" ");
                    System.out.println("\t==========当前用户在线列表==========");
                    for (int i = 0; i < onlineUsers.length; ++i) {
                        System.out.println(onlineUsers[i]);
                    }
                } else {
                    System.out.println("其它消息暂不处理...");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
