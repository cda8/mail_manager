package fr.afpa.cda.metier;

import fr.afpa.cda.exception.InvalidMDPException;
import fr.afpa.cda.jdbc.MailDAOJdbcImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.mail.*;
import java.util.*;

public class ConnexionManager {

    private static final String READ = "R";
    private static final String UNREAD = "U";
    private static final String DELETE = "D";
    private final MailDAOJdbcImpl mailDAOJdbc = new MailDAOJdbcImpl();

    Properties properties = new Properties();
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnexionManager.class);
    protected static Store store;
    protected static Folder inbox;
    private List<LocalMail> listMails = new ArrayList<>();


    public ConnexionManager() {
    }

    public void connexionMail(int userChoise) throws InvalidMDPException {
        String decodedMdp;

        try {
//            properties.load(getClass().getClassLoader().getResourceAsStream("/mailConfig.properties"));
//            String user = properties.getProperty("user");
//            String mdp = properties.getProperty("userMDP");
//            String server = properties.getProperty("serveur");
            String user = "formationcda@laposte.net";
            String mdp = "Rm9ybWF0aW9uQWZwYTIwMjEh";
            String server = "imap.laposte.net";
            decodedMdp = decodeBase64(mdp);
            if (!validationMDP(decodedMdp)) {
                throw new InvalidMDPException("Le mot de passe est invalide");
            }
            //init session serveur mail:
            connectSession(decodedMdp, user, server, Folder.READ_ONLY);
            logMailsTypeAndCount();
            System.err.println(inbox.getMessages().length);
            mapResult();
           // displayMailsInConsol();
            forInsert(listMails);
            listMails = returnUserChoise(userChoise);
            displayMailsInConsol(listMails);




//        }catch(FileNotFoundException ignored) {
//
//        }catch(IOException e)    {
//            e.printStackTrace();


        } catch (MessagingException | InvalidMDPException e) {
            e.printStackTrace();
        }

    }

    private List<LocalMail> returnUserChoise(int userChoise) {
        List<LocalMail>listMail = new ArrayList<>();
        switch (userChoise){
            case 1 :
                listMail=  mailDAOJdbc.selectByFlag(READ);
                break;
            case 2 :
                listMail=  mailDAOJdbc.selectByFlag(UNREAD);
                break;
            case 3 :
                listMail=  mailDAOJdbc.selectByFlag(DELETE);
                break;
            case 4 :
                 listMail = mailDAOJdbc.selectAll();

        }
        return listMail;
    }

    private void logMailsTypeAndCount() throws MessagingException {
        LOGGER.info("Total mails : " + inbox.getMessageCount());
        LOGGER.info("Total new mails : " + inbox.getNewMessageCount());
        LOGGER.info("Total mails non lus: " + inbox.getUnreadMessageCount());
        LOGGER.info("Total mails effacés: " + inbox.getDeletedMessageCount());

    }

    private void mapResult() throws MessagingException {
        for (Message message : inbox.getMessages()) {
            LocalMail mail = new LocalMail();
            char status = checkFlags(message);

            checkFlagsInConsol(message);

            mail.setIdMail(message.getMessageNumber());
            mail.setDateReception(message.getReceivedDate());
            mail.setFlag(status);
            mail.setSujet(message.getSubject());
            mail.setExpediteur(Arrays.stream(message.getFrom()).iterator().next().toString());
            listMails.add(mail);
        }
    }

    private void checkFlagsInConsol(Message message) throws MessagingException {
        System.err.println("------" + message.getSubject() + "---------");
        System.err.println("USER " + message.getFlags().contains(Flags.Flag.USER));
        System.err.println("SEEN " + message.getFlags().contains(Flags.Flag.SEEN));
        System.err.println("RECENT " + message.getFlags().contains(Flags.Flag.RECENT));
        System.err.println("DELETED " + message.getFlags().contains(Flags.Flag.DELETED));
        System.err.println("FLAGGED " + message.getFlags().contains(Flags.Flag.FLAGGED));
        System.err.println("ANSWERED " + message.getFlags().contains(Flags.Flag.ANSWERED));
        for (String s : message.getFlags().getUserFlags()){

            System.err.println("User flags =>> " + s);
        }


        System.err.println("--------------------------------------------------------------");
    }

    private void displayMailsInConsol(List<LocalMail> mailTemp) {
        StringBuilder sb = new StringBuilder();
        sb.append("Boite de reception : \n")
                .append("nombre de mail : ")
                .append(mailTemp.size())
                .append("\n");
        System.out.println(sb);
        for (LocalMail m : mailTemp) {

            System.out.println(m);

        }
        sb.append("=========================================\n")
                .append("==================FIN====================");
        System.out.println(sb);
    }

    private void connectSession(String decodedMdp, String user, String server, int folderTypeNumber) throws MessagingException {
        Session session = Session.getDefaultInstance(properties, null);
        store = session.getStore("imap");
        store.connect(server, user, decodedMdp);
        inbox = store.getFolder("inbox");
        inbox.open(folderTypeNumber);
    }

    private String decodeBase64(String mdp) {
        String decodedMdp;
        byte[] encodedMdp = Base64.getDecoder().decode(mdp);
        decodedMdp = new String(encodedMdp);
        System.out.println(decodedMdp);
        return decodedMdp;
    }

    private void selectedBy(String status) {
        listMails=  mailDAOJdbc.selectByFlag(status);
    }

    private void forInsert(List<LocalMail> localMailList) {
        for (LocalMail m : localMailList) {
            mailDAOJdbc.insert(m);
        }
        
    }

    private char checkFlags(Message message) throws MessagingException {

        if (message.getFlags().contains(Flags.Flag.SEEN)) {
            return 'R';
        } else if (message.getFlags().contains(Flags.Flag.DELETED)) {
            return 'D';
        } else if (!message.getFlags().contains(Flags.Flag.SEEN)) {
            return 'U';
        }
        return 'Z';
    }


    public boolean validationMDP(String mdp) {

        return mdp.length() >= 8;
    }

    //TODO a voir pour le body du message :
/**
 *
 *
 *
 * Properties properties = System.getProperties();
 * properties.setProperty("mail.store.protocol", "imap");
 * try {
 *     Session session = Session.getDefaultInstance(properties, null);
 *     Store store = session.getStore("pop3");//create store instance
 *     store.connect("pop3.domain.it", "mail.it", "*****");
 *     Folder inbox = store.getFolder("inbox");
 *     FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
 *     inbox.open(Folder.READ_ONLY);//set access type of Inbox
 *     Message messages[] = inbox.search(ft);
 *     String mail,sub,bodyText="";
 *     Object body;
 *     for(Message message:messages) {
 *         mail = message.getFrom()[0].toString();
 *         sub = message.getSubject();
 *         body = message.getContent();
 *         //bodyText = body.....
 *     }
 * } catch (Exception e) {
 *     System.out.println(e);
 * }
 * -----------------------------------------------------------------------
    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // with out break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }
    */

}
