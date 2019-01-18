package function;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

public class Mail {

	public void EnviarEmail(String assunto, String mensagem, String emailDestinado, File[] Anexo) {
		
		
		//carregando arquivo properties para connfiguração java
		Properties prop  = new Properties();
		Properties props = new Properties();
		try {
			//carregando arquivo .properties
			prop.load(new FileInputStream("config_email.properties"));
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null , "Arquivo de configuração de email com problemas erro:"+e);
		}
		String propHost	    =  new String(Base64.getDecoder().decode(prop.getProperty("host")));
		String propPorta    =  new String(Base64.getDecoder().decode(prop.getProperty("port")));
		String propSsl      =  new String(Base64.getDecoder().decode(prop.getProperty("ssl")));
		String propEmail    =  new String(Base64.getDecoder().decode(prop.getProperty("email")));
		String propSenha    =  new String(Base64.getDecoder().decode(prop.getProperty("senha")));
		String NomeEmail    =  new String(Base64.getDecoder().decode(prop.getProperty("nameEmail")));

		//configuração do  servidor de email
		props.put("mail.smtp.host", propHost);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", propPorta);
		props.put("mail.smtp.ssl.enable",propSsl);
		//autenticacao do email
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(propEmail, propSenha);
			}
		});
		session.setDebug(true);
		//remetende do email
		try {

			Message message = new MimeMessage(session);

			message.setSentDate(new Date());

			try {
				message.setFrom(new InternetAddress(propEmail, NomeEmail));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//destinatatios
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(emailDestinado));

            //cabecalho
			message.setSubject(assunto);
			
			//mensagem que vai no corpo do email
            MimeBodyPart mbpMensagem = new MimeBodyPart();
            mbpMensagem.setText(mensagem);
            
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbpMensagem);
			
            String Endereco_Anexo = "";
            if (Anexo != null) { // se tiver alguma coisa anexada ela inicializar o comando abaixo
                for (File element : Anexo) {

                    Endereco_Anexo = element.getPath();
                    String imagem = Endereco_Anexo;
                    File Arquivo = new File(imagem);
                    //setando o anexo
                    MimeBodyPart mbpAnexo = new MimeBodyPart();
                    mbpAnexo.setDataHandler(new DataHandler(new FileDataSource(Arquivo)));
                    mbpAnexo.setFileName(Arquivo.getName());
                    mp.addBodyPart(mbpAnexo);
                }
//            	
//            	MimeBodyPart mbpAnexo = new MimeBodyPart();
//                mbpAnexo.setDataHandler(new DataHandler(new FileDataSource(Anexo)));
//                mbpAnexo.setFileName(Anexo.getName());
//                mp.addBodyPart(mbpAnexo);
            }
           
            //send
            message.setContent(mp);
            
			Transport.send(message);
			//System.exit(0);

			JOptionPane.showMessageDialog(null , "Email enviado com Sucesso");

		} catch (MessagingException e) {
			JOptionPane.showMessageDialog(null , "Email não enviado erro:"+ e );
		}
	}
}
