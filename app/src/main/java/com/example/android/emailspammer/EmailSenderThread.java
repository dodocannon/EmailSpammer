package com.example.android.emailspammer;

import android.content.Context;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSenderThread implements Runnable {
    private String text;
    private String from;
    private String to;
    private String[] arr;
    private static Context context;

    public EmailSenderThread(String text, String to, String from, Context context)
    {
        this.text = text;
        this.from = from;
        this.to = to;
        this.context = context;
        arr = text.split(" ");
        for (int i =0 ; i <arr.length/2; i++)
        {
            String curr = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i -1] = curr;
        }
    }
    @Override
    public void run() {
        try
        {
            for (int i = 0; i < arr.length; i++) {

                send(arr[i], to, from);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public static void send(String text, String to, String from) throws UnsupportedEncodingException {
        Properties props = new Properties();
        final String sender = ""; //enter google account username here
        final String password = ""; // enter google account password here
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });
        ArrayList<String> recipients = new ArrayList<String>();

        recipients.add(to); // or whoever the recipient is
        MimeMessage message = new MimeMessage(mailSession);
        try {
            InternetAddress me = new InternetAddress(sender);
            me.setPersonal(from);
            message.setFrom(me);
            for (String recipient : recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }
            message.setSubject(String.valueOf(Math.random()));
            message.setText(text);
            Transport.send(message);
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        } catch (Exception me) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            me.printStackTrace();
        }
    }

}
