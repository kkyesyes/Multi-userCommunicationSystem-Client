package com.kk.qqclient.view;

import com.kk.qqclient.service.UserClientService;
import com.kk.qqclient.utils.Utility;

/**
 * 客户端菜单界面
 *
 * @author KK
 * @version 1.0
 */
public class QQView {
    // 循环控制
    private boolean loop = true;

    // 接收用户键盘输入
    private String key = "";

    // 用户服务
    private UserClientService userClientService = new UserClientService();

    public static void main(String[] args) {
        new QQView().mainMenu();

    }

    // 显示主菜单
    private void mainMenu() {
        while (loop) {
            System.out.println("==========欢迎登录网络通信系统==========");
            System.out.println("\t\t1 登录系统");
            System.out.println("\t\t9 退出系统");
            System.out.print("请输入你的选择：");

            key = Utility.readString(1);

            switch (key) {
                case "1":
                    System.out.print("请输入用户号：");
                    String userId = Utility.readString(50);
                    System.out.print("请输入密  码：");
                    String pwd = Utility.readString(50);

                    // 验证用户是否合法
                    if (userClientService.checkUser(userId, pwd)) {
                        System.out.println("==========欢迎用户（" + userId + "）登录成功==========");

                        // 二级菜单
                        while (loop) {
                            System.out.println("\n==========网络通信系统二级菜单（用户" + userId + "）==========");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择：");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
//                                    System.out.println("显示在线用户列表");
                                    userClientService.getOnlineFriends();
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                    break;
                                case "2":
                                    System.out.println("群发消息");
                                    break;
                                case "3":
                                    System.out.println("私聊消息");
                                    break;
                                case "4":
                                    System.out.println("发送文件");
                                    break;
                                case "9":
                                    loop = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("登录失败");
                    }

                    break;
                case "9":
                    loop = false;
                    System.out.println("系统退出中");
                    break;
            }

        }
    }
}
