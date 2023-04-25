package com.example.communityportal;

public class Message {
    String sender, content;

    public Message() {
    }

    public Message(String sender, String content) {
        this.sender = sender;

        this.content = content;


    }

    public String getSender() {
        return sender;
    }

  //  public void setSender(String sender) {
    //    this.sender = sender;
  //  }


    public String getContent() {
        return content;
    }

  //  public void setContent(String content) {
     //   this.content = content;
  //  }

}

