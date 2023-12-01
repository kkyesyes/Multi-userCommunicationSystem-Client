package com.kk.qqclient.service;

import com.kk.qqcommon.Message;
import com.kk.qqcommon.MessageType;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件传输服务
 *
 * @author KK
 * @version 1.0
 */
public class FileClientService {
    public void sendFileToOne(String src, String senderId, String getterId) {
        // 读取src文件 -> message
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setSender(senderId);
        message.setGetter(getterId);

        String regex = "\\/([^\\/]+)$";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);
        // 创建Matcher对象
        Matcher matcher = pattern.matcher(src);
        // 查找匹配
        if (matcher.find()) {
            // 获取捕获组中的文件名
            String fileName = matcher.group(1);
            message.setFileName(fileName);
        } else {
            message.setFileName("unknown.txt");
        }

        FileInputStream fileInputStream = null;

        byte[] buff = new byte[(int)new File(src).length()];
        try {
            fileInputStream = new FileInputStream(src);
            fileInputStream.read(buff);
            message.setFileData(buff);
//            while (!((readLen = fileInputStream.read(buff)) != -1)) {
//                fileData.append(new String(buff, 0, readLen));
//            }
//            String content = new String(fileData);
//            message.setContent(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(message.getSender()).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("文件发送成功");
    }
}
