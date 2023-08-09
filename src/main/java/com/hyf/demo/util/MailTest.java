package com.hyf.demo.util;


import cn.hutool.http.HttpStatus;
import com.hyf.demo.exception.BizException;


import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MailTest {

    public static boolean sendMessage(String recipient, String subject, String text) {
        // map---properties
        Properties pro = new Properties();
        // 协议
        pro.put("mail.transport.protocol", "smtp");
        // 主机
        pro.put("mail.smtp.host", "smtp.qq.com");
        // 1、真正的邮箱建立一个连接
        Session session = Session.getInstance(pro);
        // 2、模拟一个邮件对象
        MimeMessage message = new MimeMessage(session);
        // 发件人、收件人、主题、时间、正文
        try {
            message.setFrom(new InternetAddress("2576587835@qq.com"));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(text);
            message.setSentDate(new Date());
            message.saveChanges();// 别忘了保存
            // 3、发送者(传送者--流)
            Transport transport = session.getTransport();
            transport.connect("2576587835@qq.com", "woggjkykjoirdija");
            transport.sendMessage(message, message.getAllRecipients());
            // 4、关闭
            transport.close();
            return true;
        } catch (MessagingException e) {
            throw new BizException(HttpStatus.HTTP_INTERNAL_ERROR, "邮箱发送失败");
        }
    }

}
