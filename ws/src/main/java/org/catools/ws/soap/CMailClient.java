package org.catools.ws.soap;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.common.concurrent.CTimeBoxRunner;

import javax.mail.*;
import java.io.ByteArrayOutputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CMailClient {
  private final String username, password, host;
  private Session session = null;
  private Store store = null;

  public CMailClient(String host, String username, String password) {
    this.host = host;
    this.username = username;
    this.password = password;
  }

  public void deleteAll(int timeoutInSeconds) {
    CTimeBoxRunner.get(
        () -> {
          try {
            connect();
            for (Folder folder : store.getDefaultFolder().list("*")) {
              try {
                folder.open(Folder.READ_WRITE);
                Message[] msgs = folder.getMessages();
                for (Message msg : msgs) {
                  msg.setFlag(Flags.Flag.DELETED, true);
                }
              } finally {
                folder.close(true);
              }
            }
          } catch (Exception e) {
            throw new RuntimeException(e);
          } finally {
            try {
              store.close();
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          }
          return true;
        },
        timeoutInSeconds,
        true);
  }

  protected String getMessageAndDeleteIt(
      String subjectPattern, String contentPattern, int timeoutInSeconds) {
    return CTimeBoxRunner.get(
        () -> {
          try {
            connect();
            for (Folder folder : store.getDefaultFolder().list("*")) {
              try {
                folder.open(Folder.READ_WRITE);
                Message[] msgs = folder.getMessages();
                for (int i = 0; i < msgs.length; i++) {
                  Message m = msgs[i];
                  String content = getMessage(subjectPattern, contentPattern, i, m);
                  if (StringUtils.isNotBlank(content)) {
                    m.setFlag(Flags.Flag.DELETED, true);
                    return content;
                  }
                  m.setFlag(Flags.Flag.SEEN, false);
                }
              } finally {
                folder.close(true);
              }
              Thread.sleep(1000);
            }
          } catch (Exception e) {
            throw new RuntimeException(e);
          } finally {
            try {
              store.close();
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          }
          return null;
        },
        timeoutInSeconds,
        true);
  }

  private String getMessage(String subjectPattern, String contentPattern, int i, Message m) {
    try {
      if (m.getSubject().matches(subjectPattern)) {
        ByteArrayOutputStream w = new ByteArrayOutputStream();
        m.writeTo(w);
        if (i == 0) {
          log.trace("Message Read. Subject: " + m.getSubject());
        }
        Pattern regex = Pattern.compile(contentPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(w.toString());
        if (matcher.find()) {
          log.trace(
              "Message Found. Subject: " + m.getSubject() + " ContentPattern: " + contentPattern);
          return w.toString();
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  private void connect() {
    try {
      Properties pop3Props = new Properties();
      pop3Props.setProperty("mail.pop3.starttls.enable", "true");
      pop3Props.setProperty("mail.smtp.starttls.enable", "true");
      session = Session.getDefaultInstance(pop3Props);
      store = session.getStore("pop3");
      store.connect(host, username, password);
    } catch (Exception exception) {
      log.trace("Not able to Connect the mail server!!\n" + exception);
    }
  }
}
