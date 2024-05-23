/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

/**
 *
 * @author Sebastian
 */
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class JavaMail {
    
    /**
     * Envia un correo
     * @param receptor String
     * @param sismo Sismo
     * @throws MessagingException 
     */
    public static void enviarCorreo(String receptor, Sismo sismo) throws MessagingException {
        
        String mensajeListo = sismo.toString();
        
        System.out.println("Sending");
        Properties propiedades = new Properties();

        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587");
        propiedades.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        String myAccountMail = "dayronpc24@gmail.com";    // Aqui se coloca el correo
        String myPassword = "24191308";       // Aqui se coloca la contraseña

        Session session = Session.getInstance(propiedades, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountMail, myPassword);
            }
        });
        session.getProperties().put("mail.smtp.ssl.trust", "smtp.gmail.com");
        session.getProperties().put("mail.smtp.starttls.enable", "true");


        Message mensaje = prepareMessage(session, receptor, myAccountMail, mensajeListo);

        Transport.send(mensaje);
        System.out.println("Sent succesfully");
    }
    /**
     * Crea un mensaje para ser enviado por correo
     * @param session Session
     * @param receptor String
     * @param myAccountMail String
     * @param mensajeListo String
     * @return Message
     */
    private static Message prepareMessage(Session session, String receptor, String myAccountMail, String mensajeListo) {
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountMail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
            message.setSubject("Prueba 2");
            message.setText(mensajeListo);
            return message;
        } catch (Exception ex){
            Logger.getLogger(JavaMail.class.getName()).log(Level.SEVERE,null,ex);
        }
        return null;
    }

}
