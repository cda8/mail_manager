package fr.afpa.cda.metier;

import java.util.Properties;
import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.afpa.utils.DecodeBase64;
import fr.afpa.utils.LoadProperties;

public class CreateMail {
  static final Logger LOGGER = LoggerFactory.getLogger(CreateMail.class);

  public static void sendMail() {
    LoadProperties loadProp = new LoadProperties();
    Properties propsConfig;
    try {
      propsConfig = loadProp.load("/mailConfig.properties");
      String hostname = propsConfig.getProperty("hostname");
      String server = propsConfig.getProperty("server");
      final String username = propsConfig.getProperty("mail");
      String userMdpEncode = propsConfig.getProperty("password");
      final String password = DecodeBase64.decodeString(userMdpEncode);

      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", hostname);
      props.put("mail.smtp.port", "587");
      props.put("mail.host", server);
      props.put("mail.debug", "true");
      javax.mail.Authenticator auth = new javax.mail.Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(username, password);
        }
      };
      Session session = Session.getInstance(props, auth);
      MimeMessage msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress(username));
      InternetAddress[] address = { new InternetAddress("fabienpuech1@gmail.com") };
      msg.setRecipients(Message.RecipientType.TO, address);
      msg.setSubject("Jakarta Mail APIs Test");
      msg.addHeader("x-cloudmta-class", "standard");
      msg.addHeader("x-cloudmta-tags", "demo, example");
      msg.setText("Test Message Content");

      Transport.send(msg);

      LOGGER.info("Message Sent.");
    } catch (MessagingException | IOException e) {
      LOGGER.error(e.getMessage());
      LOGGER.error(Arrays.toString(e.getStackTrace()));
    }

  }
}
