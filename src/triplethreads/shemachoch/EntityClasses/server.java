package src.triplethreads.shemachoch.EntityClasses;

import java.awt.image.BufferedImage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class server {
    ServerSocket ss;
    Socket socket;
    DataInputStream din;
    DataOutputStream dos;

    public void StartServer(){
        try {
            ss = new ServerSocket(9002);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    void getId() {
        StartServer();
        String UserId;
        try {
            while(true) {
                socket = ss.accept();
                din = new DataInputStream(socket.getInputStream());
                UserId = din.readLine();
                Customer v = new Customer();
                System.out.println(UserId+"\n");
                System.out.println(v.getCustomer(UserId));

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void recieveImage() {
        StartServer();
        try {
            while(true) {
                socket = ss.accept();
                BufferedImage bi = ImageIO.read(ImageIO.createImageInputStream(socket.getInputStream()));
                File f = new File("pic3.jpg");
                ImageIO.write(bi, "jpg", f);
                JFrame frame = new JFrame();
                frame.getContentPane().add(new JLabel(new ImageIcon(bi)));
                frame.pack();
                frame.setVisible(true);
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void StopServer() {
        try {
            ss.close();
            socket.close();
            din.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


}
