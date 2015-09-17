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
 * 这个类代表贪吃蛇的活动场所
 *  @author 石明浩，李文斌
 * 
 */
@SuppressWarnings({ "serial", "unused" })
public class Yard extends Frame{
	//实例化一个运行组件
	PaintThread screen=new PaintThread();
	//判断游戏是否结束
	private boolean gameOver=false; 
	
	/*
	 * 行列格数
	 */
	public static final int ROWS=30;
	public static final int COLS=30;
	/*
	 * 格的大小
	 */
	public static final int BLOCK_SIZE=15;
	
	/*
	 * 设置显示字属性
	 * 
	 */
	private Font font=new Font("宋体",Font.BOLD,50);
	
	/*
	 * 分数
	 * 
	 */
	private int score=0;	
	/*
	 * 实例化一个蛇s和蛋e
	 * 
	 */
	Snake s=new Snake(this);
	Egg e=new Egg();	
	
	Image offScreenImage=null;
	
	/*
	 * 窗体大小及其运行时出现的位置
	 * 
	 */
	public void launch(){
		this.setLocation(400,120);
		this.setSize(COLS*BLOCK_SIZE,ROWS*BLOCK_SIZE);
		/*
		 * 设置窗体
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
	 * 主方法
	 */
	public static void main(String[] args) {
		new Yard().launch();
	}
	
	/*
	 * 蛇停止移动
	 * 
	 */
	public void stop() {
		gameOver=true;
	}
	
	/*
	 * 设置颜色
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g){
		Color c=g.getColor();
		//背景色
		g.setColor(Color.gray);
		//从0，0开始设置背景色
		g.fillRect(0,0,COLS*BLOCK_SIZE,ROWS*BLOCK_SIZE);
		//线条颜色
		g.setColor(Color.black);
		/*
		 * 画出行线
		 * 
		 */
		for(int i=1;i<ROWS;i++) {
			g.drawLine(0,BLOCK_SIZE*i,COLS*BLOCK_SIZE,BLOCK_SIZE*i);
		}
		/*
		 * 画出列线
		 * 
		 */
		for(int i=1; i<COLS; i++) {
			g.drawLine(BLOCK_SIZE*i,0,BLOCK_SIZE*i,BLOCK_SIZE*ROWS);
		}
		//设置字体颜色
		g.setColor(Color.green);
		//设置字体位置
		g.drawString("目前分数:"+score,10,60);
		g.drawString("提示：每吃一个加5分，加油！",10,80);
		/*
		 * 游戏停止
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
		 * 显示蛇和蛋
		 */
		e.draw(g);
		s.draw(g);
		
		
	}
	
	/*
	 * 更新
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
	 * 运行
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
				 * 设置蛇的移动速度
				 */
				try{
					Thread.sleep(200);
				}catch (InterruptedException e){
					e.printStackTrace();
				}
			}
		}
		
		/*
		 * 停止方法
		 * 
		 */
		public void pause(){
			this.pause=true;
		}
		
		/*
		 * 重新开始
		 * 
		 */
		public void reStart(){
			this.pause=false;
			s=new Snake(Yard.this);
			gameOver=false;
		}
		
		/*
		 *游戏停止
		 * 
		 */
		public void gameOver(){
			running=false;
		}
		
	}
	
	/*
	 * F2 游戏重新开始
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
	 * 拿到所得的分数
	 * 
	 * 
	 */
	
	public int getScore(){
		return score;
	}
	
	/*
	 * 设置所得的分数
	 * 
	 */
	public void setScore(int score){
		this.score=score;
	}

}
