import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.io.*;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;

public class MailGonder {

    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final String SMTP_AUTH_USER = "canoyunlar";
    private static final String SMTP_AUTH_PWD = "Can_1111544111";


    public static void main(String args[]) throws Exception {
        String[] a = new String[1];
        a[0] = "cankirimca@gmail.com";

        postMail(a, "deneme", "bu bir denemedir", "canoyunlar@gmail.com");
    }

    public static void postMail(String recipients[], String subject, String message, String from) throws MessagingException {
        class SMTPAuthenticator extends javax.mail.Authenticator {
            public PasswordAuthentication getPasswordAuthentication() {
                String username = SMTP_AUTH_USER;
                String password = SMTP_AUTH_PWD;
                return new PasswordAuthentication(username, password);
            }
        }

        try {
            boolean debug = false;

            Properties props = new Properties();
            props.put("mail.smtp.host", SMTP_HOST_NAME);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");

            Authenticator auth = new SMTPAuthenticator();

            Session session = Session.getInstance(props, auth);
            session.setDebug(debug);


            Message msg = new MimeMessage(session);

            InternetAddress addressFrom = new InternetAddress(from);
            msg.setFrom(addressFrom);
            InternetAddress[] addressTo = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                addressTo[i] = new InternetAddress(recipients[i]);
            }
            msg.setRecipients(Message.RecipientType.TO, addressTo);

            msg.setSubject(subject);

            msg.setContent(message, "text/plain");

            Transport.send(msg);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * SimpleAuthenticator is used to do simple authentication
     * when the SMTP server requires it.
     */

}