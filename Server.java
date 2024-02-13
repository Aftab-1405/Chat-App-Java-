import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// Here class Server extending itself with the help of 'JFrame' class present in swing package.
// Mostly used for creating frames.
public class Server implements ActionListener {
    JTextField textField1; // I have to declare object of JTextField globally to use it outside ths constructor as well.
    JPanel P2;
    static Box vertical = Box.createVerticalBox();
    static JFrame frame = new JFrame();
    static DataOutputStream dout; // Here I am declaring dataoutputstream's object as global to make use of it anywhere in the code.
    public Server()
    {
    frame.setLayout(null);
        // This is an Panel part of the frame.
        JPanel P1 = new JPanel();// If we want to add custom panel above the frame then we can do it with the help of 'JPanel' class.
        P1.setBackground(new Color(7,94,84)); // To use our custom color we have to create an object of 'Color' class,
                                                       // otherwise we can do it in traditional way i.e., Color.green.
        P1.setBounds(0,0,450,70);   // Setting the co-ordinates and width,height of panel.
        P1.setLayout(null);
        frame.add(P1);

        // Setting 'backButton' and its corresponding action.
        ImageIcon I1 = new ImageIcon(ClassLoader.getSystemResource("Icons/3.png")); // In order to set any image in frame will user class called 'ImageIcon'.
                                                                                          // And to load the images from our directory will use another class called 'ClassLoader'.
        Image I2 = I1.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT); // Here we are scaling an image as per our need. Object 'I2' refers to scaled image.
        ImageIcon I3 = new ImageIcon(I2); // Used above scaled object we created another image object that will be passed to the 'JLabel' class.
        JLabel backButton = new JLabel(I3); // This class is used to set images over the frame. Here we are passing object 'I1' of 'ImageIcon' class to 'JLabel' class.
        backButton.setBounds(5,20,25,25); // Here we are setting co-ordinates of an image.
        P1.add(backButton); // To add image over the panel then we have to call 'add()' method with 'JPanel' class's object.
        // Here we are using abstract method 'addMouseListener' which is belongs to 'ActionListener' class.
        // Then we are overriding 'mouseClicked' method to perform exit operation or back operation if user clicks on backButton present on panel.
        backButton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent ae) {
            System.exit(0);
        }
        }
        );

        //Setting 'profilePicture'.
        ImageIcon I4 = new ImageIcon(ClassLoader.getSystemResource("Icons/1.png"));
        Image I5 = I4.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
        ImageIcon I6 = new ImageIcon(I5);
        JLabel profilePicture = new JLabel(I6);
        profilePicture.setBounds(40,10,50,50);
        P1.add(profilePicture);

        //Setting 'videoImage'.
        ImageIcon I7 = new ImageIcon(ClassLoader.getSystemResource("Icons/video.png"));
        Image I8 = I7.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT);
        ImageIcon I9 = new ImageIcon(I8);
        JLabel videoImage = new JLabel(I9);
        videoImage.setBounds(300,20,30,30);
        P1.add(videoImage);

        //Setting 'phoneImage'.
        ImageIcon I10 = new ImageIcon(ClassLoader.getSystemResource("Icons/phone.png"));
        Image I11 = I10.getImage().getScaledInstance(35,30, Image.SCALE_DEFAULT);
        ImageIcon I12 = new ImageIcon(I11);
        JLabel phoneImage = new JLabel(I12);
        phoneImage.setBounds(360,20,35,30);
        P1.add(phoneImage);

        //Setting 'moreVert'.
        ImageIcon I13 = new ImageIcon(ClassLoader.getSystemResource("Icons/3icon.png"));
        Image I14 = I13.getImage().getScaledInstance(10,25, Image.SCALE_DEFAULT);
        ImageIcon I15 = new ImageIcon(I14);
        JLabel moreVert = new JLabel(I15);
        moreVert.setBounds(410,20,10,25);
        P1.add(moreVert);

        // Setting name of user.
        JLabel userName = new JLabel("Active Now");
        userName.setBounds(110, 35, 100, 12);
        userName.setForeground(Color.WHITE); // 'setForeground()' method used to set the color of text.
        userName.setFont(new Font("calibre",Font.BOLD,12));
        P1.add((userName));

        // Setting name of user.
        JLabel status = new JLabel("Salman Khan");
        status.setBounds(110, 15, 100, 15);
        status.setForeground(Color.WHITE); // 'setForeground()' method used to set the color of text.
        status.setFont(new Font("calibre",Font.BOLD,15));
        P1.add((status));

        // Here I am created second panel for textbox and send button to send the text.
        P2 = new JPanel();
        P2.setBounds(5,75,440, 570);
        frame.add(P2);

        textField1 = new JTextField();
        textField1.setBounds(5, 645, 310, 30);
        textField1.setFont(new Font("calibre", Font.BOLD, 14));
        frame.add(textField1);

        // Here I am using 'JButton' class to create a 'send' button.
        JButton Send = new JButton("SEND");
        Send.setFont(new Font("calibre", Font.BOLD, 14));
        Send.setBounds(320,645,123,30);
        Send.setBackground(new Color(7,94,84));
        Send.setForeground(Color.WHITE);
        Send.addActionListener(this);
        frame.add(Send);

    frame.setSize(450,700);
    frame.setLocation(0,0); // Using this method we can set co-ordinates of frame.
    frame.setUndecorated(true); // This function is used to add/remove default header of frame.
    frame.getContentPane().setBackground(Color.WHITE); // These two methods can use the class 'Color' to change the color of frame.
                                                 // This class is present in AWT(Abstract Window Tool-Kit) package.
    frame.setVisible(true); // This method is used to display the frame.
    }

    public void actionPerformed(ActionEvent ae){

        try{
            String out = textField1.getText();
            JPanel P3 = formatLabel(out);

            P2.setLayout(new BorderLayout());
            JPanel rightSide = new JPanel(new BorderLayout());
            rightSide.add(P3, BorderLayout.LINE_END);
            vertical.add(rightSide);
            vertical.add(Box.createVerticalStrut(15));
            P2.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);
            textField1.setText(""); // This line of code is responsible for replacing text with empty string after user send text.

            // Thsese are the three main built-in function of swing which allows frame to reload, if an event happens.
            frame.repaint();
            frame.invalidate();
            frame.validate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style = \"width:150px\">"+out+"</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);

        // Adding padding here for text box.
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
    }
    public static void main(String[] args) {

        new Server(); // This is an anonymous object of class 'Server'.
        // Here I am created a server.
        try {
            ServerSocket serverSocket = new ServerSocket(6001);
            while (true)
            {
                Socket S = serverSocket.accept(); // Here server can accept the client's queries.
                DataInputStream din = new DataInputStream(S.getInputStream());
                dout = new DataOutputStream(S.getOutputStream());
                while(true)
                {
                    String sms = din.readUTF();
                    JPanel panel = formatLabel(sms);

                    JPanel leftSide = new JPanel(new BorderLayout());
                    leftSide.add(panel,BorderLayout.LINE_START);
                    vertical.add(leftSide);
                    frame.validate();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
