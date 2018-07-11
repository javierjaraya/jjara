package cl.forestcenter.sistema.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Javier-PC
 */
public class CorreoElectronico {
    private String desde;
    private String para;
    private String asunto;
    private String texto;
    private String password;

    public CorreoElectronico(String desde, String para, String asunto, String texto, String password) {
        this.desde = desde;
        this.para = para;
        this.asunto = asunto;
        this.texto = texto;
        this.password = password;
    }
    
    public boolean enviarCorreo(){
        
        try{
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", desde);
            props.setProperty("mail.smtp.auth", "true");

            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(desde));//("yo@yo.com"));
            message.addRecipient(
                Message.RecipientType.TO,
                new InternetAddress(para));
            message.setSubject(asunto);
            message.setText(texto);

            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            t.connect(desde, password);
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
