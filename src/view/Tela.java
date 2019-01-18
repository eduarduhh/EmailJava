package view;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;
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
import javax.swing.JFileChooser;

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
	private JTextField txtArquivo;
	private File[] f;

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
		txtMensagem.setBounds(91, 168, 332, 82);
		contentPane.add(txtMensagem);
		
		JLabel lblAssunto = new JLabel("Assunto");
		lblAssunto.setBounds(25, 92, 56, 14);
		contentPane.add(lblAssunto);
		
		txtAssunto = new JTextField();
		txtAssunto.setBounds(91, 89, 298, 20);
		contentPane.add(txtAssunto);
		txtAssunto.setColumns(10);
		
		JLabel lblMensagem = new JLabel("Mensagem");
		lblMensagem.setBounds(25, 162, 64, 14);
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
//					String caminhoArquivo = txtArquivo.getText();
					Mail mail = new Mail();
					mail.EnviarEmail(assunto, mensagem, emailDestinado, f);
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
		lblNewLabel.setBounds(10, 181, 64, 80);
		contentPane.add(lblNewLabel);
		
		JLabel lblArquivo = new JLabel("Arquivo");
		lblArquivo.setBounds(25, 123, 56, 14);
		contentPane.add(lblArquivo);
		
		JButton btnNewButton = new JButton("...");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 	JFileChooser file = new JFileChooser(new File("C:/Users/Documents"));
				 	
				    file.setMultiSelectionEnabled(true);
				    file.setFileSelectionMode(JFileChooser.FILES_ONLY);
				    int i = file.showOpenDialog(null);
				    if (i == 1) {
				        txtArquivo.setText("");
				        f = null;
				    } else {
				        f = file.getSelectedFiles();
				        
				        String SomaAnexo1 = "";
				        String SomaAnexo2 = "";
				        for (File enderec : f) {
				        	txtArquivo.setText(enderec.getPath());
				            SomaAnexo1 = txtArquivo.getText();
				            SomaAnexo2 = SomaAnexo2 + SomaAnexo1 + ";";
				            txtArquivo.setText(SomaAnexo2);

				        }
				    }
				
			}
		});
		btnNewButton.setBounds(350, 120, 39, 20);
		contentPane.add(btnNewButton);
		
		txtArquivo = new JTextField();
		//txtArquivo.setText("C:\\Users\\Eduardo\\Downloads\\EDUARDO COSTA CARVALHO.pdf");
		txtArquivo.setBounds(91, 120, 249, 20);
		contentPane.add(txtArquivo);
		txtArquivo.setColumns(10);
		
		JButton btnConfigurar = new JButton("Configurar");
		btnConfigurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Configurar configurar = new Configurar();
				configurar.setLocationRelativeTo(null);
				configurar.setVisible(true);
			}
		});
		btnConfigurar.setIcon(new ImageIcon(Tela.class.getResource("/img/iconfinder_setting-gear-process-engineering_2075815.png")));
		btnConfigurar.setBounds(91, 261, 122, 23);
		contentPane.add(btnConfigurar);
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
			String email = new String(Base64.getDecoder().decode(prop.getProperty("email")));
			txtDe.setText(email);
		}
}
