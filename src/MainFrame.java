import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: markdev
 * Date: 10/21/13
 * Time: 7:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainFrame extends JFrame {
    private JTextField ipTextField;
    private JTextField portTextField;
    private JComboBox operationComboBox;
    private JTextField arg1TextField;
    private JTextField arg2TextField;
    private JButton calcButton;
    private JLabel resultLabel;
    private JFrame self;

    public MainFrame() throws HeadlessException {
        setTitle("https math");
        self = this;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ipTextField = new JTextField("localhost");
        portTextField = new JTextField("8080");
        operationComboBox = new JComboBox(new Object[]{Operations.PLUS,
                Operations.MINUS,
                Operations.MPL,
                Operations.DIV});
        arg1TextField = new JTextField("20");
        arg1TextField.setPreferredSize(new Dimension(120,30));
        arg2TextField = new JTextField("30");
        arg2TextField.setPreferredSize(arg1TextField.getPreferredSize());
        calcButton = new JButton("calc");
        resultLabel = new JLabel("");
        resultLabel.setPreferredSize(new Dimension(120,30));
        calcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String IP = ipTextField.getText();
                String PORT = portTextField.getText();
                try {
                    String res = new HttpsConnector(IP, PORT).calc(
                            (Operations) operationComboBox.
                                    getSelectedItem(), arg1TextField.getText(),
                            arg2TextField.getText());
                    if (res.startsWith("Exception")) {
                        JOptionPane.showMessageDialog(self, res);
                        repaint();
                    } else {
                        resultLabel.setText(res);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        setLayout(new FlowLayout());
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new GridLayout(2, 2));
        verticalPanel.add(new JLabel("ip:"));
        verticalPanel.add(ipTextField);
        verticalPanel.add(new JLabel("port"));
        verticalPanel.add(portTextField);
        add(verticalPanel);
        add(arg1TextField);
        add(operationComboBox);
        add(arg2TextField);
        add(calcButton);
        add(resultLabel);
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new Thread(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        }).start();
    }
}
