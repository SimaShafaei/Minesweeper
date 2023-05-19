package minesweeper;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class MyFrame1 extends JFrame {
/**
	 * 
	 */
	private static final long serialVersionUID = -7832943936117247075L;
    //int array[][];
	public int width, height,counter=0;
    MyFrame2 frame2;
    public MyFrame1(int width, int height, int mine,MyFrame2 frame2) {
    	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    	EditedLb lb[][] = new EditedLb[height][width];
    	int array[][] = new int[height + 2][width + 2];
        ShowLabel iconstate =new ShowLabel(array,lb);
        
    	this.width = width;
        this.height = height;
        this.frame2=frame2;
        this.counter=mine;
        iconstate.mine=mine;
        iconstate.frame1=this;
        this.enableEvents(AWTEvent.MOUSE_EVENT_MASK +AWTEvent.MOUSE_MOTION_EVENT_MASK+AWTEvent.WINDOW_EVENT_MASK);
        this.setLocation((d.width - (width * 19)) / 2,(d.height - (height * 19)) / 2);
        this.setSize(width * 19 + 33, height * 19 + 90);
        
        this.gamemap( mine, array);
        
        iconstate.setLocation((width * 19 -10)/2,7);
        this.getContentPane().add(iconstate);
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                lb[i][j] = new EditedLb(this,array, lb);
                lb[i][j].setLocation(12 + j * 19, 44 + i * 19);
                this.getContentPane().add(lb[i][j]);
                lb[i][j].mine=mine;
                lb[i][j].xt=i;
                lb[i][j].yt=j;
                lb[i][j].iconstate=iconstate;

            }
        }

                
        this.setTitle("Minesweeper");
        this.setLayout(null);
        this.setResizable(false);
        this.setVisible(true);

    }


    
//  *********(Paint)**********//
    public void paint(Graphics g){
    	super.paint(g);
    	
    	 Font font = new Font("Cressida", 1, 17);
    	 g.setColor(Color.RED);
         g.setFont(font);
         g.drawString("MINE:"+ String.valueOf(counter),10 , 60);
    }
    
    
    //**********//(process event)//************//

    protected void processEvent(AWTEvent e) {
           if (e.getID() == 201) {
        	this.setVisible(false);
        	this.frame2.setVisible(true);
        	
        } 
        super.processEvent(e);
    }


    //*******************(GameMap)**********************//

    public void gamemap( int mine, int[][] array) {

        for (int k = 0; k < height + 2; k++) {
            for (int m = 0; m < width + 2; m++) {

                array[k][m] = 0;
            }
        }
        int xt = 0, yt = 0;
        for (int k = 0; k < mine; k++) {
            while (xt == 0 || yt == 0 || array[xt][yt] != 0) {
                xt = (int) ((height + 1) * Math.random());
                yt = (int) ((width + 1) * Math.random());
            }
            array[xt][yt] = 9;
        }

        int count = 0;
        for (int i = 1; i <= height; i++) {
            for (int j = 1; j <= width; j++) {
                count = 0;
                for (int k = -1; k <= 1; k++) {
                    for (int m = -1; m <= 1; m++) {
                        if (array[i + k][j + m] == 9) {
                            count++;
                        }
                    }
                }
                if (array[i][j] != 9) {
                    array[i][j] = count;
                }
            }
        }

    }


    //**************(GameRule)****************//

    public void gameRule(int x, int y, int[][] array, EditedLb[][] lb) {

        ImageIcon mousepress = new ImageIcon("backbtn.jpg");
        ImageIcon numimage[] = new ImageIcon[8];


        for (int i = 0; i < 8; i++) {
            numimage[i] = new ImageIcon("btn" + (i + 1) + ".JPG");
        }
        if (array[x + 1][y + 1] != 0) {
               lb[x][y].setIcon(numimage[array[x + 1][y + 1] - 1]);
               array[x + 1][y + 1] = -1;
               lb[x][y].state=-1;

        } else if(array[x + 1][y + 1] != -1){
            lb[x][y].setIcon(mousepress);
            array[x + 1][y + 1] = -1;
            lb[x][y].state=-1;

                if (array[x][y + 2] != -1 && 0 < x && x <= height && 0 < y+2 && y+2 <= width && lb[x-1][y+1].state!=-1) {
                    gameRule(x-1, y+1, array, lb);
                }
                if (array[x+2][y + 2] != -1 && 0 < x+2 && x+2 <= height && 0 < y+2 && y+2 <= width  && lb[x+1][y+1].state!=-1) {
                    gameRule(x+1, y + 1, array, lb);
                }
                if (array[x + 1][y + 2] != -1 && 0 < x+1 && x+1 <= height && 0 < y+2 && y+2 <= width && lb[x][y+1].state!=-1) {
                    gameRule(x , y + 1, array, lb);
                }
                if (array[x][y+1] != -1 && 0 < x && x <= height && 0 < y+1 && y+1 <= width  && lb[x-1][y].state!=-1) {
                    gameRule(x-1 , y , array, lb);
                }
                if (array[x +2][y+1 ] != -1 && 0 < x+2 && x+2 <= height && 0 < y+1 && y+1 <= width && lb[x+1][y].state!=-1) {
                    gameRule(x + 1, y , array, lb);
                }
                if (array[x][y] != -1 && 0 < x && x <= height && 0 < y && y <= width && lb[x-1][y-1].state!=-1) {
                    gameRule(x-1, y-1 , array, lb);
                }
                if (array[x+1 ][y] != -1 && 0 < x+1 && x+1 <= height && 0 < y && y <= width && lb[x][y-1].state!=-1) {
                    gameRule(x , y-1 , array, lb);
                }
                if (array[x + 2][y] != -1 && 0 < x+2 && x+2 <= height && 0 < y && y <= width && lb[x+1][y-1].state!=-1) {
                    gameRule(x + 1, y-1 , array, lb);
                }

        }
    }


    //***********(MineDetect)***************//
    public void mineDetect() {
    	this.counter++;
        
    }



    //***********(reduceC)***************//
        public void reduceC() {
            this.counter--;
        }


    //**********(MAIN)********************//
    public static void main(String[] args) {
         MyFrame2 myframe2 = new MyFrame2();

    }
}


//******************************************************
class EditedLb extends JLabel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5071261652690459837L;
	ImageIcon state1=new ImageIcon("1up.JPG");
	ImageIcon state2=new ImageIcon("2up.JPG");
	ImageIcon state3=new ImageIcon("3up.JPG");
	ImageIcon state4=new ImageIcon("4up.JPG");
	ImageIcon flag  = new ImageIcon("flag.jpg");
    ImageIcon quest = new ImageIcon("quesbtn.jpg");
    ImageIcon endgame = new ImageIcon("bombend.JPG");
    ImageIcon mousepress = new ImageIcon("backbtn.jpg");
    ImageIcon nomousepress = new ImageIcon("button.jpg");
    ImageIcon pressquest = new ImageIcon("pressquest.jpg");
    ImageIcon temp;
    MyFrame1 frame1;
    int state = 0;
    boolean isEnter=false;
    boolean isPress=false;
    ShowLabel iconstate;
    int x, y, mine,xt,yt;
    int[][] array;
    EditedLb lb[][];
    public EditedLb(MyFrame1 frame1, int[][] array,EditedLb lb[][]) {
      	this.frame1=frame1;
        this.array = array;
        this.lb = lb;
        this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        this.setSize(19, 19);
        this.setOpaque(true);
        this.setLayout(null);

    }


    //*********(Paint)**********//
    public void paint(Graphics g) {

        super.paint(g);
        ImageIcon temp = (ImageIcon)this.getIcon();
        
        if(this.isPress==true && this.isEnter==true){
        	if(state==0)	state=1;
        	if(state==3)	state=2;
        }
        else if(this.isPress==true && this.isEnter==false){
        	if(array[xt+1][yt+1]!=-1){
        		if(state==1)	state=0;
        		if(state==2)	state=3;
        	}
        }
        
        
//        for (int k = 0; k <array.length-2; k++) {
//     	   for (int m = 0; m <array[0].length-2; m++) {
//     		   System.out.print(lb[k][m].state + "  ");
//     	   }
//     	   System.out.println("");
//        }
        
        
        
        switch (state) {
        case 0: 
        	
            this.setIcon(nomousepress);
            break;
        case 1: 
            this.setIcon(mousepress);
            break;
        case 2:
        	this.setIcon(pressquest);
        	break;
        case 3:
        	this.setIcon(quest);
        	break;
        case 4:{
            if(array[xt+1][yt+1]==9){
                lb[xt][yt].setIcon(endgame);
                gameOver();
              }
            else{
               frame1.gameRule(xt, yt, array, lb);
               if (noNonpress()==true) {
                  wingame();
                }
               }
               


  
//            }//firstif
        }//case4
        break;
        case 5:{
            if (temp == nomousepress) {
                this.setIcon(flag);
                frame1.reduceC();
                frame1.repaint();
            	state=-1;
            } else if (temp == flag) {
                this.setIcon(quest);
                frame1.mineDetect();
                frame1.repaint();
                state=3;
            } else if (temp == quest) {
                this.setIcon(nomousepress);
                state=0;
            }
        }break;
     } //switch
    
   }


    //**************(ProcessEvent)****************//
    public void processMouseEvent(MouseEvent ev) {
        if (noNonpress()==false && state!=-2  ) {
        
        if(ev.getID()== MouseEvent.MOUSE_ENTERED) {	
        
        
        	this.isEnter=true;
        	
        }
        else if(ev.getID()== MouseEvent.MOUSE_EXITED){
      
        	this.isEnter=false;
       
        }
        
        
        
        	
        	
        if (ev.getButton() == MouseEvent.BUTTON1) {
          	
            	
                switch (ev.getID()) {
                case MouseEvent.MOUSE_PRESSED:{
                	for (int i = 0; i < array.length-2; i++) {
                        for (int j = 0; j < array[0].length-2; j++) {
                        	lb[i][j].isPress=true;
                        }
                   }
                   iconstate.setIcon(state1);
                }break;
                case MouseEvent.MOUSE_RELEASED:{
                	for (int i = 0; i < array.length-2; i++) {
                        for (int j = 0; j < array[0].length-2; j++) {
                        	if(lb[i][j].isEnter==true){
                        		this.x=i;
                        		this.y=j;
                        	}	
                        }
                    }
                	System.out.println(x+"  "+y);
                	 for (int k = 0; k <array.length; k++) {
      		     	   for (int m = 0; m <array[0].length; m++) {
      		     		   System.out.print(array[k][m] + "  ");
      		     	   }
      			  	   
      		     	   System.out.println("");
      			  }
                	if(array[x+1][y+1]!=-1){
                		if(lb[x][y].state==1 || lb[x][y].state==2){
                			lb[x][y].state=4;
                			lb[x][y].repaint();
                		}
                	}
                	for (int i = 0; i < array.length-2; i++) {
                        for (int j = 0; j < array[0].length-2; j++) {
                        	lb[i][j].isPress=false;
                        }
                	}    
                    iconstate.setIcon(state2);
                }break;

              }//switch
        }//if-botton1
            if (ev.getButton() == MouseEvent.BUTTON3) {
                if (ev.getID() == MouseEvent.MOUSE_CLICKED) {
                    state = 5;
                }
            }
        }
        super.processMouseEvent(ev);
        this.repaint();
    }




    //***********(GameOver)***************//
   public void gameOver() {

       ImageIcon bomb = new ImageIcon("bomb.JPG");
       ImageIcon bombnot = new ImageIcon("bombnot.JPG");
       for (int i = 0; i < array.length-2; i++) {
           for (int j = 0; j < array[0].length-2; j++) {
               lb[i][j].state=-2;
               if (array[i+1][j+1] == 9 && !(i==x && j==y)) {
                   if(lb[i][j].getIcon()!=flag){//dastur ejra nemishe chera???
                       lb[i][j].setIcon(bomb);
                       lb[i][j].repaint();
                   }
                   else if(array[i+1][j+1] != 9 && lb[i][j].getIcon()==flag){
                	   lb[i][j].setIcon(bombnot);
                       lb[i][j].repaint();
                   }

               }

           }
       }
       iconstate.setIcon(state4);

   }



   //*******(NoNonpress)*******//
   public boolean noNonpress(){
       boolean state1=true;
       for (int i = 1; i <= array.length-2; i++) {
           for (int j = 1; j <= array[0].length-2; j++) {
               if(array[i][j]!=9 && array[i][j]!=-1)
                   state1=false;

           }
        }
        return state1;

   }


   //******(WinGame)*******//
    public void wingame() {
    	iconstate.setIcon(state3);
    	frame1.counter=0;
    	frame1.repaint();
    	for (int i = 1; i <= array.length-2; i++) {
            for (int j = 1; j <= array[0].length-2; j++) {
                if(array[i][j]==9 ){
                	lb[i-1][j-1].setIcon(flag);
                	lb[i-1][j-1].state=-1;
                	lb[i-1][j-1].repaint();
                	
                }	
            }    	
    	}             
    }
}





//*************************************************
class ShowLabel extends JLabel{

	ImageIcon state2=new ImageIcon("2up.JPG");
	ImageIcon bstate2=new ImageIcon("2.JPG");
	ImageIcon nomousepress = new ImageIcon("button.jpg");
	private static final long serialVersionUID = -4767373471561664070L;
	int mine;
	int state=0;
	int[][]array;
	EditedLb lb[][];
	MyFrame1 frame1;
	public ShowLabel(int[][]array,EditedLb lb[][]){
		this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		this.array=array;
		this.lb=lb;
        this.setSize(32, 32);
        this.setOpaque(true);
        this.setLayout(null);
        this.setIcon(state2);
	}
	
	
	
   //*********(Paint)**********//
	public void paint(Graphics g) {
		super.paint(g);
		switch (state){
		case 1:{
			this.setIcon(state2);
			  frame1.gamemap(mine,array);
			  frame1.counter=mine;
			  frame1.repaint();
			  for(int i=0;i<array.length-2 ; i++){
				  for(int j=0; j<array[0].length-2 ; j++){
					  lb[i][j].state=0;
					  lb[i][j].isEnter=false;
					  lb[i][j].isPress=false;
					 // lb[i][j].win=false;
					  lb[i][j].setIcon(nomousepress);
					  
				  }
			  }
			 
			  state=0;
		    }break;
		case 2:
			this.setIcon(bstate2);
			break;
		}//switch
		
	}
	
	
	
	//*************(processMouseEvent)****************//
	 public void processMouseEvent(MouseEvent ev) {
		  if (ev.getButton() == MouseEvent.BUTTON1) {
			  if(ev.getID()==MouseEvent.MOUSE_RELEASED){
				  state=1;
			  }
			  if(ev.getID()==MouseEvent.MOUSE_PRESSED)
				  state=2;
				  
		  }
		  super.processMouseEvent(ev);
	        this.repaint();
	 }
		
}