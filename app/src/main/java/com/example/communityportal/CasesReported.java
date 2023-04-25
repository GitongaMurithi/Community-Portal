package com.example.communityportal;

//Firebase model data class
public class CasesReported {
    String sender;
    String content;

    public CasesReported() {
    }

    public CasesReported(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
