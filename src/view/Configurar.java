package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;

public class Configurar extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtSmtp;
	private JTextField txtPorta;
	private JTextField txtEmai;
	private JTextField txtNome;
	private JPasswordField txtSenha;
	private JRadioButton rdbtnSim = new JRadioButton("SIM");
	private JRadioButton rdbtnNo = new JRadioButton("N\u00C3O");

	/**
	 * Create the frame.
	 */
	public Configurar() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Configurar.class.getResource("/img/iconfinder_mail_287559.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 476, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSmtp = new JLabel("SMTP");
		lblSmtp.setBounds(24, 42, 46, 14);
		contentPane.add(lblSmtp);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(24, 78, 56, 14);
		contentPane.add(lblEmail);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(24, 106, 46, 14);
		contentPane.add(lblSenha);

		txtSmtp = new JTextField();
		txtSmtp.setBounds(68, 39, 208, 20);
		contentPane.add(txtSmtp);
		txtSmtp.setColumns(10);

		JLabel lblPorta = new JLabel("Porta");
		lblPorta.setBounds(299, 42, 46, 14);
		contentPane.add(lblPorta);

		txtPorta = new JTextField();
		txtPorta.setBounds(336, 39, 105, 20);
		contentPane.add(txtPorta);
		txtPorta.setColumns(10);

		txtEmai = new JTextField();
		txtEmai.setBounds(68, 75, 185, 20);
		contentPane.add(txtEmai);
		txtEmai.setColumns(10);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(263, 78, 46, 14);
		contentPane.add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(309, 75, 125, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(66, 103, 187, 20);
		contentPane.add(txtSenha);

		JLabel lblNewLabel = new JLabel("SSL");
		lblNewLabel.setBounds(24, 134, 31, 14);
		contentPane.add(lblNewLabel);

		rdbtnSim.setBounds(68, 130, 90, 23);
		contentPane.add(rdbtnSim);

		rdbtnNo.setBounds(167, 130, 109, 23);
		contentPane.add(rdbtnNo);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnSim);
		buttonGroup.add(rdbtnNo);

		JButton btnNewButton = new JButton("Salvar");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				if (txtEmai.getText().isEmpty() || txtNome.getText().isEmpty() || txtPorta.getText().isEmpty()
						|| txtSenha.getText().isEmpty() || txtSmtp.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos");
				} else {
					Properties props = new Properties();

					try {
						props.load(new FileInputStream("config_email.properties"));
						System.out.println("Carregou o pro" + props);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Arquivo de email não foi carregado");
					}

					String host = txtSmtp.getText();
					String porta = txtPorta.getText();
					String email = txtEmai.getText();
					String nomeEmail = txtNome.getText();
					String senha = txtSenha.getText();
					String ssl = null;

					if (rdbtnSim.isSelected()) {
						ssl = "true";
					} else {
						ssl = "false";
					}

					host = Base64.getEncoder().encodeToString(host.getBytes());
					porta = Base64.getEncoder().encodeToString(porta.getBytes());
					email = Base64.getEncoder().encodeToString(email.getBytes());
					nomeEmail = Base64.getEncoder().encodeToString(nomeEmail.getBytes());
					senha = Base64.getEncoder().encodeToString(senha.getBytes());
					ssl = Base64.getEncoder().encodeToString(ssl.getBytes());

					props.setProperty("host", host);
					props.setProperty("port", porta);
					props.setProperty("email", email);
					props.setProperty("nameEmail", nomeEmail);
					props.setProperty("senha", senha);
					props.setProperty("ssl", ssl);

					try {
						// GRAVANDO PROP
						File file = new File("config_email.properties");
						FileOutputStream fos = new FileOutputStream(file);
						props.store(fos, "CONFIGURACÃO");
					} catch (Exception e) {
						// TODO: handle exception
					}
					JOptionPane.showMessageDialog(null, "Configurado");
					dispose();

				}

			}
		});
		btnNewButton.setIcon(new ImageIcon(Configurar.class.getResource("/img/iconfinder_floppy_285657 (1).png")));
		btnNewButton.setBounds(299, 130, 135, 23);
		contentPane.add(btnNewButton);

		getProperties();
	}

	public void getProperties() {
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream("config_email.properties"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Arquivo de email não foi carregado");
		}

//			String textoOriginal = "smtp.gmail.com";
//	        System.out.println("Texto original: " + textoOriginal);
//
//	        String textoSerializado = Base64.getEncoder().encodeToString(textoOriginal.getBytes());
//
//	        System.out.println("Texto em Base64: " + textoSerializado);
//
//	        String textoDeserializado = new String(Base64.getDecoder().decode(textoSerializado));
//
//	        System.out.println("Texto deserializado: "+ textoDeserializado);
//	        
		String host = new String(Base64.getDecoder().decode(prop.getProperty("host")));
		String port = new String(Base64.getDecoder().decode(prop.getProperty("port")));
		String nomeEmail = new String(Base64.getDecoder().decode(prop.getProperty("nameEmail")));
		String email = new String(Base64.getDecoder().decode(prop.getProperty("email")));
		String senha = new String(Base64.getDecoder().decode(prop.getProperty("senha")));
		String ssl = new String(Base64.getDecoder().decode(prop.getProperty("ssl")));
		txtSmtp.setText(host);
		txtPorta.setText(port);
		txtNome.setText(nomeEmail);
		txtEmai.setText(email);
		txtSenha.setText(senha);
		// boolean b = true;
		if (ssl.equals("true")) {
			rdbtnSim.setSelected(true);
		} else {
			rdbtnNo.setSelected(true);
		}

	}
}
