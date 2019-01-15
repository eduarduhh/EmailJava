package view;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import function.Mail;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Toolkit;


public class Tela extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtAssunto;
	private JTextField txtDe;
	private JComboBox<String> comboBox ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela frame = new Tela();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Tela() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Tela.class.getResource("/img/iconfinder_mail_287559.png")));
		setTitle("ENVIAR EMAIL PELO JAVA");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEnviar = new JLabel("Para");
		lblEnviar.setBounds(25, 55, 46, 14);
		contentPane.add(lblEnviar);
		
		JTextPane txtMensagem = new JTextPane();
		txtMensagem.setBounds(91, 129, 332, 82);
		contentPane.add(txtMensagem);
		
		JLabel lblAssunto = new JLabel("Assunto");
		lblAssunto.setBounds(25, 92, 56, 14);
		contentPane.add(lblAssunto);
		
		txtAssunto = new JTextField();
		txtAssunto.setBounds(91, 89, 298, 20);
		contentPane.add(txtAssunto);
		txtAssunto.setColumns(10);
		
		JLabel lblMensagem = new JLabel("Mensagem");
		lblMensagem.setBounds(25, 129, 64, 14);
		contentPane.add(lblMensagem);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.setIcon(new ImageIcon(Tela.class.getResource("/img/iconfinder_mail_287559.png")));
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtMensagem.getText().equals("") && txtAssunto.getText().equals("")) {
					JOptionPane.showMessageDialog(rootPane, "Voce deve preencher os campos");
				}else {
					String assunto  = txtAssunto.getText();
					String mensagem = txtMensagem.getText();
					String emailDestinado = comboBox.getSelectedItem().toString();
					Mail mail = new Mail();
					mail.EnviarEmail(assunto, mensagem, emailDestinado);
				}
				
			}
		});
		btnEnviar.setBounds(286, 261, 137, 23);
		contentPane.add(btnEnviar);
		
		JLabel lblDe = new JLabel("De:");
		lblDe.setBounds(25, 24, 46, 14);
		contentPane.add(lblDe);
		
		txtDe = new JTextField();
		txtDe.setEditable(false);
		txtDe.setBounds(91, 21, 298, 20);
		contentPane.add(txtDe);
		txtDe.setColumns(10);
		
		comboBox = new JComboBox<>();
		comboBox.setBounds(91, 52, 298, 20);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Tela.class.getResource("/img/java.png")));
		lblNewLabel.setBounds(25, 216, 64, 80);
		contentPane.add(lblNewLabel);
		//carregando funcões na inicialização da tela
		carregarEmailCombobox() ;
		carregarEmail();
	
	}	
		// funcao para pegar arquivo do email txt e carregar em combobox
		public void carregarEmailCombobox() {
			File arquivo = new File("email.txt");
			
			 try {
		            FileReader fr = new FileReader(arquivo);
		            BufferedReader br = new BufferedReader(fr);
		            for (String linha = br.readLine(); linha != null; linha = br.readLine()) {  
		            	comboBox.addItem(linha);    
		            } 
		            br.close();
		        } catch (IOException e) {
		            System.err.printf("Erro na leitura do Arquivo: %s.\n", 
		                    e.getMessage());
		        }
		}
		// funcao para pegar email do config_email.pro
		public void carregarEmail() {
			Properties prop  = new Properties();
			try {
				prop.load(new FileInputStream("config_email.properties"));
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null , "Arquivo de configuração de email com problemas erro:"+e);
			}
			txtDe.setText(prop.getProperty("email"));
		}
}
