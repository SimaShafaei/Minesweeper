package minesweeper;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;


public class MyFrame2 extends JDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3344463112853198209L;
	Txtbutton tbtn1 = null;
    Txtbutton tbtn2 = null;
    Txtbutton tbtn3 = null;
    MyButton btn1 = null;
    MyButton btn2 = null;
   
    public MyFrame2() {
    	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    	tbtn1 = new Txtbutton("12", 70, 35);
        tbtn2 = new Txtbutton("10", 70, 65);
        tbtn3 = new Txtbutton("6", 70, 95);
        btn1 = new MyButton("OK", 115, 40);
        btn2 = new MyButton("Cancel", 115, 80);
        btn1.txt1 = tbtn1;
        btn1.txt2 = tbtn2;
        btn1.txt3 = tbtn3;
        btn1.frame2 = this;
        tbtn1.txt1 = tbtn1;
        tbtn1.txt2 = tbtn2;
        tbtn1.txt3 = tbtn3;
        tbtn1.frame2 = this;
        tbtn2.txt1 = tbtn1;
        tbtn2.txt2 = tbtn2;
        tbtn2.txt3 = tbtn3;
        tbtn2.frame2 = this;
        tbtn3.txt1 = tbtn1;
        tbtn3.txt2 = tbtn2;
        tbtn3.txt3 = tbtn3;
        tbtn3.frame2 = this;
        
  
        this.setModal(true);
        this.enableEvents(AWTEvent.MOUSE_EVENT_MASK +AWTEvent.MOUSE_MOTION_EVENT_MASK +AWTEvent.FOCUS_EVENT_MASK + AWTEvent.KEY_EVENT_MASK);
        this.setLayout(null);
        this.setTitle("Custom Feild");
        this.setLocation((d.width - 200) / 2, (d.height - 200) / 2);
        this.setSize(200, 200);
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(tbtn1);
        this.getContentPane().add(tbtn2);
        this.getContentPane().add(tbtn3);
        this.getContentPane().add(btn1);
        this.getContentPane().add(btn2);
        this.btn1.setName("OK");
        this.btn2.setName("cancel");
        this.setResizable(false);
        this.setFocusTraversalPolicy(new Myfocus());
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//chera nemitunam exit begzaram?

    }

   
	public void paint(Graphics g) {
        super.paint(g);
//        Font font = new Font("Cressida", 0, 17);
//        g.setFont(font);
        g.drawString("Height", 30, 80);
        g.drawString("Width", 30, 110);
        g.drawString("Mines", 30, 140);
    }

    

    class Myfocus extends FocusTraversalPolicy {
    public Myfocus() {

    }
        public Component getDefaultComponent(Container cont) {
            return btn1;
        }
        public Component getFirstComponent(Container focusCycleRoot) {
            return btn1;
        } //end getFirstComponent

        public Component getLastComponent(Container focusCycleRoot) {
            return btn2;
        } //end getLastComponent

        public Component getComponentAfter(Container focusCycleRoot,
                                           Component aComponent) {
            if (aComponent == tbtn1) {
                return tbtn2;
            } else if (aComponent == tbtn2) {
                return tbtn3;
            } else if (aComponent == tbtn3) {
                return btn1;
            } else if (aComponent == btn1) {
                return btn2;
            } //end else
            return tbtn1; //make compiler happy
        } //end getComponentAfter

        public Component getComponentBefore(Container focusCycleRoot,
                                            Component aComponent) {
            if (aComponent == tbtn2) {
                return tbtn1;
            } else if (aComponent == tbtn3) {
                return tbtn2;
            } else if (aComponent == btn1) {
                return tbtn3;
            } else if (aComponent == btn2) {
                return btn1;
            }else if(aComponent == tbtn1){
                return btn2;
            } //end else
            return tbtn1; //make compiler happy
        } //end getComponentBefore

    }


}


//****************************************************
class Txtbutton extends JTextField {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int count = 0;
	public Txtbutton txt1;
    public Txtbutton txt2;
    public Txtbutton txt3;
    public MyFrame2 frame2;
    private int width = 10, height = 12, mine = 9;
	public Txtbutton(String num, int x, int y) {
        this.setLocation(x, y);
        this.setSize(40, 22);
        this.setText(num);

    }

    public void processEvent(AWTEvent e) {
    	
        if (e.getID() == 400 || e.getID() == 401 || e.getID() == 402) {
            KeyEvent ev = (KeyEvent) e;
            System.out.println(ev.getKeyCode());
            if (!(((ev.getKeyChar() > 47 && ev.getKeyChar() < 58) && (getText().length() < 5)) || ev.getKeyChar() == 8 || ev.getKeyCode() > 34 && ev.getKeyCode() < 41 || ev.getKeyChar() == 127)) {
                ev.consume();
            }
            
            if(ev.getKeyCode()==10){
    			
                    try {
                        height = Integer.parseInt(txt1.getText());
                        if (height > 24)    height = 24;
                        if(height<9)        height=9;

                    } catch (Exception ev1) {
                        height = 12;
                    }
                    try {
                        width = Integer.parseInt(txt2.getText());
                        if (width > 32)        width = 32;
                        if(width<9)            width=9;
                    } catch (Exception ev1) {
                        width = 10;
                    }
                    try {
                        mine = Integer.parseInt(txt3.getText());
                        while (mine >= width * height) {
                            mine -= 5;
                        }
                        if(mine<=0)
                            mine=6;
                    } catch (Exception ev1) {
                        mine = 6;
                    }
                    txt1.setText(String.valueOf(height));
                    txt2.setText(String.valueOf(width));
                    txt3.setText(String.valueOf(mine));
                    frame2.setVisible(false);
                    MyFrame1 PlayFrame = new MyFrame1(width, height, mine,frame2);
                

    		}
          
        }

        super.processEvent(e);

    }
}


//****************************************************
class MyButton extends JButton {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Txtbutton txt1;
    public Txtbutton txt2;
    public Txtbutton txt3;
    public MyFrame2 frame2;
   // public MyFrame1 frame1;
    private int width = 10, height = 12, mine = 9;
    public MyButton(String s, int x, int y) {
        Font font = new Font("Times New Roman", 1, 13);
        this.setLocation(x, y);
        this.setSize(73, 27);
        this.setFont(font);
        this.setText(s);

    }

    public void processEvent(AWTEvent e) {
    //	System.out.println(e.getID());
    	if(e.getID() == 400 || e.getID() == 401 || e.getID() == 402 ){
    		KeyEvent ev1 = (KeyEvent) e;
    		if(ev1.getKeyCode()==10){
    			if (((MyButton) (e.getSource())).getName().equals("OK")) {
                    try {
                        height = Integer.parseInt(txt1.getText());
                        if (height > 24)    height = 24;
                        if(height<9)        height=9;

                    } catch (Exception ev) {
                        height = 12;
                    }
                    try {
                        width = Integer.parseInt(txt2.getText());
                        if (width > 32)        width = 32;
                        if(width<9)            width=9;
                    } catch (Exception ev) {
                        width = 10;
                    }
                    try {
                        mine = Integer.parseInt(txt3.getText());
                        while (mine >= width * height) {
                            mine -= 5;
                        }
                        if(mine<=0)
                            mine=6;
                    } catch (Exception ev) {
                        mine = 6;
                    }
                    txt1.setText(String.valueOf(height));
                    txt2.setText(String.valueOf(width));
                    txt3.setText(String.valueOf(mine));
                    frame2.setVisible(false);
                    MyFrame1 PlayFrame = new MyFrame1(width, height, mine,frame2);
                } //okButton
                if (((MyButton) (e.getSource())).getName().equals("cancel")) {
                    System.exit(0);
               }

    		}
    	}
    	
    	if (e.getID() == 502 ) {
    			
            if (((MyButton) (e.getSource())).getName().equals("OK")) {
                try {
                    height = Integer.parseInt(txt1.getText());
                    if (height > 24)    height = 24;
                    if(height<9)        height=9;

                } catch (Exception ev) {
                    height = 12;
                }
                try {
                    width = Integer.parseInt(txt2.getText());
                    if (width > 32)        width = 32;
                    if(width<9)            width=9;
                } catch (Exception ev) {
                    width = 10;
                }
                try {
                    mine = Integer.parseInt(txt3.getText());
                    while (mine >= width * height) {
                        mine -= 5;
                    }
                    if(mine<=0)
                        mine=6;
                } catch (Exception ev) {
                    mine = 6;
                }
                txt1.setText(String.valueOf(height));
                txt2.setText(String.valueOf(width));
                txt3.setText(String.valueOf(mine));
                frame2.setVisible(false);
                MyFrame1 PlayFrame = new MyFrame1(width, height, mine,frame2);
            } //okButton
            if (((MyButton) (e.getSource())).getName().equals("cancel")) {
                System.exit(0);
           }
    	 
        } //pressButton
    	
        super.processEvent(e);
    }


}


//************************************************

