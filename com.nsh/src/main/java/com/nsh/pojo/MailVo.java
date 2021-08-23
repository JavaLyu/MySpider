package com.nsh.pojo;

/**
 * 邮件发送信息类
 * @author NSH
 * @create 2021-08-15 22:10
 */
public class MailVo {
    //接收方法邮箱
    private String to;
    //邮件标题
    private String title;
    //邮件正文
    private String content;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MailVo{" +
                "to='" + to + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
