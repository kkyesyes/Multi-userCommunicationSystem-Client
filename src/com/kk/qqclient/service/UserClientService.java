package com.kk.qqclient.service;

import com.kk.qqcommon.Message;
import com.kk.qqcommon.MessageType;
import com.kk.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 用户注册和登录验证
 *
 * @author KK
 * @version 1.0
 */
public class UserClientService {
    private User u = new User();
    private Socket socket;

    /**
     * 验证用户是否合法
     * @param userId 用户ID
     * @param pwd 用户密码
     * @return 是否合法
     */
    public boolean checkUser(String userId, String pwd) {
        boolean b = false;

        u.setUserId(userId);
        u.setPasswd(pwd);

        try {
            socket = new Socket(InetAddress.getLocalHost(), 9999);
            // 将用户信息发送至服务端验证登录
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);

            // 读取从服务器回复的Message对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message)ois.readObject();

            // 服务端返回登录是否成功
            if (ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
                // 成功
                // 开启线程ClientConnectServerThread
                ClientConnectServerThread clientConnectServerThread =
                        new ClientConnectServerThread(socket);
                clientConnectServerThread.start();

                // 加入线程管理
                ManageClientConnectServerThread.addClientConnectServerThread(userId, clientConnectServerThread);
                b = true;
            } else {
                // 失败
                socket.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return b;
    }

    /**
     * 拉取在线用户
     */
    public void getOnlineFriends() {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(u.getUserId());

        // 发送给服务器
        // 得到想要拉取列表的用户在客户端的线程的socket进行传输数据
        ClientConnectServerThread clientConnectServerThread =
                ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId());
        Socket socket = clientConnectServerThread.getSocket();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logout() {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserId());

        ClientConnectServerThread clientConnectServerThread =
                ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId());
        Socket socket = clientConnectServerThread.getSocket();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
