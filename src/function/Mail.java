package function;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class Mail {

	public void EnviarEmail(String assunto, String mensagem, String emailDestinado) {
		//carregando arquivo properties para connfiguração java
		Properties prop  = new Properties();
		Properties props = new Properties();
		try {
			//carregando arquivo .properties
			prop.load(new FileInputStream("config_email.properties"));
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null , "Arquivo de configuração de email com problemas erro:"+e);
		}
		props.put("mail.smtp.host", prop.getProperty("host"));
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", prop.getProperty("port"));
		props.put("mail.smtp.ssl.enable", prop.getProperty("ssl"));
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(prop.getProperty("email"), prop.getProperty("senha"));
			}
		});
		session.setDebug(true);
		try {

			Message message = new MimeMessage(session);

			message.setSentDate(new Date());

			try {
				message.setFrom(new InternetAddress(prop.getProperty("email"), prop.getProperty("nameEmail")));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emailDestinado));

			message.setSubject(assunto);
			message.setText(mensagem);

			Transport.send(message);

			JOptionPane.showMessageDialog(null , "Email enviado com Sucesso");

		} catch (MessagingException e) {
			JOptionPane.showMessageDialog(null , "Email não enviado erro:"+ e );
		}
	}
}
