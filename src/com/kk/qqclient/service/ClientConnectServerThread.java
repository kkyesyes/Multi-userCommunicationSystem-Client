package com.kk.qqclient.service;

import com.kk.qqclient.utils.Utility;
import com.kk.qqcommon.Message;
import com.kk.qqcommon.MessageType;
import com.kk.qqcommon.Settings;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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

                switch (ms.getMesType()) {
                    // 返回在线用户列表
                    case MessageType.MESSAGE_RET_ONLINE_FRIEND:
                        String[] onlineUsers = ms.getContent().split(" ");
                        System.out.println("\n\t==========当前用户在线列表==========");
                        for (int i = 0; i < onlineUsers.length; ++i) {
                            System.out.println(onlineUsers[i]);
                        }
                        break;

                    // 普通消息
                    case MessageType.MESSAGE_COMM_MES:
                        if (ms.getGetter().equals("@all")) {
                            System.out.println("\n--------------------");
                            System.out.println(ms.getSender() + " 群发说：" + ms.getContent());
                            System.out.println("--------------------");
                            break;
                        }
                        System.out.println("\n--------------------");
                        System.out.println(ms.getSender() + " 说：" + ms.getContent());
                        System.out.println("--------------------");
                        break;

                    // 接收文件
                    case MessageType.MESSAGE_FILE_MES:
//                        System.out.println(ms.getSender() + " 向你发送了一份文件");
//                        // 获取文件存储位置
//                        String rootDir = Settings.getDownloadPath();
//                        StringBuilder path = new StringBuilder();
//                        path.append(rootDir);
//                        path.append(ms.getFileName());
//
//                        // 保存
//                        FileOutputStream fileOutputStream = null;
//                        try {
//                            fileOutputStream = new FileOutputStream(new String(path));
//                            fileOutputStream.write(ms.getFileData());
//                        } catch (Exception e) {
////                            System.out.println("文件保存异常");
//                            throw new RuntimeException(e);
//                        } finally {
//                            try {
//                                if (fileOutputStream != null) {
//                                    fileOutputStream.close();
//                                }
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//                        }
//                        System.out.println("保存文件成功");
                        break;
                    // 系统消息
                    case MessageType.MESSAGE_SYS:
                        System.out.println("\n--------------------");
                        System.out.println("SERVER_INFO -> " + ms.getContent());
                        System.out.println("--------------------");
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
