package window_builder;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MyFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFrame frame = new MyFrame();
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
	public MyFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		
		JMenuBar menuBar = new JMenuBar();
		panel.add(menuBar);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		menuBar.add(mntmNewMenuItem);
		
		JButton btnNewButton = new JButton("press when you are ready");
		panel.add(btnNewButton);
		
		JTextPane txtpnTypeSomethingIn = new JTextPane();
		txtpnTypeSomethingIn.setText("Type something in there");
		panel.add(txtpnTypeSomethingIn);
	}

}
