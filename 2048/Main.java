/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 *
 * @author j3rry
 */
public class Main extends JFrame implements MouseMotionListener, MouseListener {

    /**
     * @param args the command line arguments
     */
    int startX, startY, endX, endY;
    int pad[][];
    int scale=100;
    boolean flag=false;

    Main() {
        super("Demo");
        this.setBounds(100, 100, 500, 500);
        startX = 0;
        startY = 0;
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        pad = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                pad[i][j] = 0;
            }
        }
        insert();
        insert();
    }

    private void insert() {
        int x = (int) Math.floor(Math.random() * 4);
        int y = (int) Math.floor(Math.random() * 4);
        if (pad[x][y] != 0) {
            insert();
        } else {
            pad[x][y] = (Math.random() < 0.7) ? 2 : 4;
        }
    }

    public void left() {
        for (int i = 0; i < 4; i++) {
            int[] arr = new int[4];
            for (int j = 0; j < 4; j++) {
                arr[j] = pad[i][j];
            }
            arr = move(arr);
            for (int j = 0, k = 3; j < 4; j++, k--) {
                pad[i][j] = arr[j];
            }
        }
    }

    public void right() {
        for (int i = 0; i < 4; i++) {
            int[] arr = new int[4];
            for (int j = 0, k = 3; j < 4; j++, k--) {
                arr[j] = pad[i][k];
            }
            arr = move(arr);
            for (int j = 0, k = 3; j < 4; j++, k--) {
                pad[i][j] = arr[k];
            }
        }
    }
    
    public void up(){
        for (int i = 0; i < 4; i++) {
            int[] arr = new int[4];
            for (int j = 0; j < 4; j++) {
                arr[j] = pad[j][i];
            }
            arr = move(arr);
            for (int j = 0, k = 3; j < 4; j++, k--) {
                pad[j][i] = arr[j];
            }
        }
    }
    
    public void down() {
        for (int i = 0; i < 4; i++) {
            int[] arr = new int[4];
            for (int j = 0, k = 3; j < 4; j++, k--) {
                arr[j] = pad[k][i];
            }
            arr = move(arr);
            for (int j = 0, k = 3; j < 4; j++, k--) {
                pad[j][i] = arr[k];
            }
        }
    }

    public int[] move(int[] arr) {
        for (int k = 0; k < 2; k++) {
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 4; i++) {
                    if (arr[i] == 0 & i < 3&&arr[i+1]!=0) {
                        flag=true;
                        arr[i] = arr[i + 1];
                        arr[i + 1] = 0;
                    }
                }
            }
            if (k < 1) {
                for (int j = 0; j < 4; j++) {
                    if (j < 3 && arr[j] == arr[j + 1] && arr[j]!=0) {
                        flag=true;
                        arr[j] += arr[j + 1];
                        arr[j + 1] = 0;
                    }
                }
            }
        }
        return arr;
    }

    public void print() {
        for (int i = 0; i < 4; i++) {
            System.out.print("[ ");
            for (int j = 0; j < 4; j++) {
                System.out.print(pad[i][j] + ", ");
            }
            System.out.println("]");
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        if (startX == 0 || startY == 0) {
            startX = e.getX();
            startY = e.getY();
        }
        endX = e.getX();
        endY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = endX - startX;
        int y = endY - startY;
        if (x > y) {
            if (endX > startX&&(x+y)>0) {
                right();
            }
            if(endY<startY&&(x+y)<0){
                up();
            }
        }else{
            if (endX < startX&&(x+y)<0) {
                left();
            }
            if(endY>startY&&(x+y)>0){
                down();
            }
        }
        endX=startX=endY=startY=0;
        if(flag){
            insert();
            flag=false;
        }
        repaint();
    }
    
    public void paint(Graphics g){
        int c=0;
        g.clearRect(0, 0, 500, 500);
        g.setFont(new Font("Courier New",Font.BOLD,40));
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                c=pad[j][i]*255/11;
                g.setColor(new Color(c));
                g.fillRect(i*scale+55, j*scale+45, scale, scale);
                g.setColor(Color.red);
                g.drawString(pad[j][i]+"", i*scale+scale, j*scale+scale);
            }
        }
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Main m = new Main();
        int x = 0;
        while (x != 9) {
            int a = s.nextInt();
            if (a == 2) {
                m.left();
            }
            if (a == 3) {
                m.right();
            }
            if(a==4){
                m.up();
            }
            if(a==5){
                m.down();
            }
            m.insert();
            m.print();

        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {    }

    @Override
    public void mouseClicked(MouseEvent e) {    }

    @Override
    public void mousePressed(MouseEvent e) {    }

    @Override
    public void mouseEntered(MouseEvent e) {    }

    @Override
    public void mouseExited(MouseEvent e) {    }


}

