import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
/**
 * 
 * 定义鸡蛋精灵
 * @author 石明浩，李文斌
 */
public class Egg{
	//行列定义
	int row,col;
	//范围定义
	int w=Yard.BLOCK_SIZE;
	int h=Yard.BLOCK_SIZE;
	
	/*
	 * 初始位置产生随机数
	 */
	private static Random r=new Random();
	
	/*
	 * 
	 * 定义鸡蛋颜色
	 * 
	 */
	private Color color=Color.PINK;

	
	/*
	 * 
	 * 运动时的位置
	 * 
	 */
	public Egg(int row,int col) {
		this.row=row;
		this.col=col;
	}
	
	/*
	 * 
	 * 初始时在位置
	 * 
	 */
	public Egg(){
		this(r.nextInt(Yard.ROWS-2),r.nextInt(Yard.COLS));
	}
	
	/*
	 * 
	 * 重新出现的位置
	 * 
	 */
	public void reAppear() {
		this.row=r.nextInt(Yard.ROWS);
		this.col=r.nextInt(Yard.COLS);
	}
	
	/*
	 * 
	 * 
	 * 
	 */
	public Rectangle getRect(){
		return new Rectangle(Yard.BLOCK_SIZE*col,Yard.BLOCK_SIZE*row,w,h);
	}
	
	/*
	 * 
	 * 设置鸡蛋颜色
	 * 
	 */
	public void draw(Graphics g){
		Color c=g.getColor();
		g.setColor(color);
		g.fillOval(Yard.BLOCK_SIZE*col, Yard.BLOCK_SIZE*row,w,h);
		g.setColor(c);
		if(color==Color.GREEN)
			color=Color.RED;
		else color=Color.GREEN;
	}
	
	/*
	 * 
	 * 设置列
	 * 
	 */
	public int getCol(){
		return col;
	}

	public void setCol(int col){
		this.col=col;
	}
	
	/*
	 * 
	 * 设置行
	 * 
	 */
	public int getRow(){
		return row;
	}

	public void setRow(int row){
		this.row=row;
	}
	
}
