package client;

import javax.imageio.ImageIO;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by Михаил on 04.11.2016.
 */
public class ViewForChat {
    private JFrame frame;
    private JTextArea message;
    private  JTextArea send;
    private  JTextField fieldIP;
    private JButton buttonIP;
    private String prev="connect";
    private String name;
    private String id="test";
    private String forSend=null;
    private int count=0;
    public String getId() {
        while (true) {
            try {
               Thread.sleep(10);
            if (!id.equals("test") && id.length()>10){
                fieldIP.setText("CONNECTED TO TEST VIEW");
                buttonIP.setText(" ");

               // Image im=new ImageIcon("d:/qwe.jpg").getImage();
             /*   try {
                   Image im = ImageIO.read(new File("d:/qwe.jpg"));
                    im.getScaledInstance(10,10,10);
                    buttonIP.setIcon(new ImageIcon(im));
                } catch (IOException e) {}*/

                return id;}

           } catch (InterruptedException e) {}

        }
    }


    public  synchronized void outMessage(){


        frame= new JFrame("Test view");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,500);
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon("qwe.jpg").getImage());

        frame.setContentPane(new BgPanel());
        fieldIP=new JTextField();
        fieldIP.setForeground(Color.cyan);

        buttonIP=new JButton("Connect");


            ImageIcon i=new ImageIcon("qwe.jpg");
            Image newimg = i.getImage().getScaledInstance(15, 19, Image.SCALE_SMOOTH);
            buttonIP.setIcon(new ImageIcon(newimg));


        buttonIP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id=fieldIP.getText();

                count++;
                if(count>1){
                    fieldIP.setText("STOP! Don`t click.");
                    }
                else  if(count>5){
                    fieldIP.setText("MEW-MEW");
                }

            }
        });
        Label l=new Label("IP-server");
        l.setForeground(Color.RED);
       // frame.getContentPane().add(buttonIP);
        Box box=new Box(BoxLayout.Y_AXIS);
        Box boxIn=new Box(BoxLayout.X_AXIS);
      //  boxIn.add(new Label("IP-server"));
        boxIn.add(l);
        boxIn.add(fieldIP);
        boxIn.add(buttonIP);
        box.add(boxIn);
        box.add(Box.createVerticalStrut(15));
        message = new JTextArea(20,27);
        message.setForeground(Color.BLACK);
        message.setSelectedTextColor(Color.red);
        message.setLineWrap(true);
        DefaultCaret caret = (DefaultCaret)message.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        message.setEditable(false);
        box.add(new JScrollPane(message));


        box.add(Box.createVerticalStrut(15));
        box.add(new Label("Enter your message:"));


        send=new JTextArea(3,5);
        send.setLineWrap(true);

        send.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    forSend=name+": "+send.getText();

                }
            }
        });
box.add(new JScrollPane(send));

       frame.add(box);



    //   p.add(send);
        //p2.add(send);
        while(true) {
            name = JOptionPane.showInputDialog("Enter your name:");
            if(name==null || name.equals("") || name.length()<2) continue;
            break;
        }


       // id = JOptionPane.showInputDialog("Enter server ip:");



        frame.setVisible(true);
    }
    public synchronized void refresh(String s){
     /*   s=s.trim();
        prev=prev+s+"\n";*/

       if(s.endsWith("\n")){
        prev=prev+s;}
        else prev=prev+s+"\n";
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            MidiChannel[] channels = synth.getChannels();
            channels[0].programChange(41);
            channels[0].noteOn(101, 80);
            Thread.sleep(100); // in milliseconds
            channels[0].noteOff(101);
            synth.close();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        message.setText(prev);

    }
    public synchronized String sendM(){

        return forSend;

    }
    public synchronized void sendClear(){
        forSend=null;
        send.setText("");
    }
    static class BgPanel extends JPanel{
        public void paintComponent(Graphics g){
            Image im = null;
            try {
                im = ImageIO.read(new File("23380.jpg"));
            } catch (IOException e) {}
            g.drawImage(im, 0, 0, null);
        }
    }

}
