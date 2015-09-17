import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * ��������̰���ߵĻ����
 *  @author ʯ���ƣ����ı�
 * 
 */
@SuppressWarnings({ "serial", "unused" })
public class Yard extends Frame{
	//ʵ����һ���������
	PaintThread screen=new PaintThread();
	//�ж���Ϸ�Ƿ����
	private boolean gameOver=false; 
	
	/*
	 * ���и���
	 */
	public static final int ROWS=30;
	public static final int COLS=30;
	/*
	 * ��Ĵ�С
	 */
	public static final int BLOCK_SIZE=15;
	
	/*
	 * ������ʾ������
	 * 
	 */
	private Font font=new Font("����",Font.BOLD,50);
	
	/*
	 * ����
	 * 
	 */
	private int score=0;	
	/*
	 * ʵ����һ����s�͵�e
	 * 
	 */
	Snake s=new Snake(this);
	Egg e=new Egg();	
	
	Image offScreenImage=null;
	
	/*
	 * �����С��������ʱ���ֵ�λ��
	 * 
	 */
	public void launch(){
		this.setLocation(400,120);
		this.setSize(COLS*BLOCK_SIZE,ROWS*BLOCK_SIZE);
		/*
		 * ���ô���
		 */
		this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		this.setVisible(true);
		this.addKeyListener(new KeyMonitor());
		new Thread(screen).start();
	}
	/*
	 * ������
	 */
	public static void main(String[] args) {
		new Yard().launch();
	}
	
	/*
	 * ��ֹͣ�ƶ�
	 * 
	 */
	public void stop() {
		gameOver=true;
	}
	
	/*
	 * ������ɫ
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g){
		Color c=g.getColor();
		//����ɫ
		g.setColor(Color.gray);
		//��0��0��ʼ���ñ���ɫ
		g.fillRect(0,0,COLS*BLOCK_SIZE,ROWS*BLOCK_SIZE);
		//������ɫ
		g.setColor(Color.black);
		/*
		 * ��������
		 * 
		 */
		for(int i=1;i<ROWS;i++) {
			g.drawLine(0,BLOCK_SIZE*i,COLS*BLOCK_SIZE,BLOCK_SIZE*i);
		}
		/*
		 * ��������
		 * 
		 */
		for(int i=1; i<COLS; i++) {
			g.drawLine(BLOCK_SIZE*i,0,BLOCK_SIZE*i,BLOCK_SIZE*ROWS);
		}
		//����������ɫ
		g.setColor(Color.green);
		//��������λ��
		g.drawString("Ŀǰ����:"+score,10,60);
		g.drawString("��ʾ��ÿ��һ����5�֣����ͣ�",10,80);
		/*
		 * ��Ϸֹͣ
		 * 
		 */
		if(gameOver) {
			g.setFont(font);
			g.drawString("game over!",90,170);
//			screen.pause();
		}
		
		g.setColor(c);
		s.eat(e);
		/*
		 * ��ʾ�ߺ͵�
		 */
		e.draw(g);
		s.draw(g);
		
		
	}
	
	/*
	 * ����
	 * @see java.awt.Container#update(java.awt.Graphics)
	 */
	@Override
	public void update(Graphics g){
		if(offScreenImage==null) {
			offScreenImage=this.createImage(COLS*BLOCK_SIZE,ROWS*BLOCK_SIZE);
		}
		Graphics gOff=offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage,0,0,null);
	}
	
	/*
	 * ����
	 */
	private class PaintThread implements Runnable{
		private boolean running=true;
		private boolean pause=false;
		public void run(){
			while(running){
				if(pause)
					continue; 
				else
					repaint();
				/*
				 * �����ߵ��ƶ��ٶ�
				 */
				try{
					Thread.sleep(200);
				}catch (InterruptedException e){
					e.printStackTrace();
				}
			}
		}
		
		/*
		 * ֹͣ����
		 * 
		 */
		public void pause(){
			this.pause=true;
		}
		
		/*
		 * ���¿�ʼ
		 * 
		 */
		public void reStart(){
			this.pause=false;
			s=new Snake(Yard.this);
			gameOver=false;
		}
		
		/*
		 *��Ϸֹͣ
		 * 
		 */
		public void gameOver(){
			running=false;
		}
		
	}
	
	/*
	 * F2 ��Ϸ���¿�ʼ
	 * 
	 */
	private class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e){
			int key=e.getKeyCode();
			if(key==KeyEvent.VK_F2){
				screen.reStart();
			}
			s.keyPressed(e);
		}
		
	}
	
	/*
	 * �õ����õķ���
	 * 
	 * 
	 */
	
	public int getScore(){
		return score;
	}
	
	/*
	 * �������õķ���
	 * 
	 */
	public void setScore(int score){
		this.score=score;
	}

}
