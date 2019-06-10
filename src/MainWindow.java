import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class MainWindow {

	private JFrame frame;
	private JTextField topologySize;
	private JTextField learnRate;
	private JTextField momentumRate;
	private JTextField maxQ;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 500);
		frame.setMinimumSize(new Dimension(200, 200));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		frame.getContentPane().add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblIloWarstw = new JLabel("Liczby neuronow oddzielone przecinkami:");
		lblIloWarstw.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblIloWarstw);
		
		topologySize = new JTextField();
		topologySize.setColumns(30);
		panel.add(topologySize);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1);
		
		JLabel lblWspczynnikUczenia = new JLabel("Wsp\u00F3\u0142czynnik uczenia:");
		panel_1.add(lblWspczynnikUczenia);
		
		learnRate = new JTextField();
		panel_1.add(learnRate);
		learnRate.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2);
		
		JLabel lblWspczynnikMomentum = new JLabel("Wsp\u00F3\u0142czynnik momentum:");
		panel_2.add(lblWspczynnikMomentum);
		
		momentumRate = new JTextField();
		panel_2.add(momentumRate);
		momentumRate.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		frame.getContentPane().add(panel_3);
		
		JLabel lblMaksymalnaWarto = new JLabel("Maksymalna warto\u015B\u0107 b\u0142\u0119du uczenia:");
		panel_3.add(lblMaksymalnaWarto);
		
		maxQ = new JTextField();
		panel_3.add(maxQ);
		maxQ.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		frame.getContentPane().add(panel_4);
		
		JLabel lblciekaDoZapisu = new JLabel("\u015Acie\u017Cka do zapisu wag: ");
		panel_4.add(lblciekaDoZapisu);
		
		textField = new JTextField();
		panel_4.add(textField);
		textField.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		frame.getContentPane().add(panel_5);
		
		JLabel lblciekaDoOdczytu = new JLabel("\u015Acie\u017Cka do odczytu danych ucz\u0105cych:");
		panel_5.add(lblciekaDoOdczytu);
		
		textField_1 = new JTextField();
		panel_5.add(textField_1);
		textField_1.setColumns(10);
		
		JPanel panel_6 = new JPanel();
		frame.getContentPane().add(panel_6);
		
		JButton startLearning = new JButton("Rozpocznij nauke");
		panel_6.add(startLearning);
		
		JButton resetWeights = new JButton("Ustaw losowe wagi");
		resetWeights.setEnabled(false);
		panel_6.add(resetWeights);
	}
}
