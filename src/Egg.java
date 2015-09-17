import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
/**
 * 
 * ���弦������
 * @author ʯ���ƣ����ı�
 */
public class Egg{
	//���ж���
	int row,col;
	//��Χ����
	int w=Yard.BLOCK_SIZE;
	int h=Yard.BLOCK_SIZE;
	
	/*
	 * ��ʼλ�ò��������
	 */
	private static Random r=new Random();
	
	/*
	 * 
	 * ���弦����ɫ
	 * 
	 */
	private Color color=Color.PINK;

	
	/*
	 * 
	 * �˶�ʱ��λ��
	 * 
	 */
	public Egg(int row,int col) {
		this.row=row;
		this.col=col;
	}
	
	/*
	 * 
	 * ��ʼʱ��λ��
	 * 
	 */
	public Egg(){
		this(r.nextInt(Yard.ROWS-2),r.nextInt(Yard.COLS));
	}
	
	/*
	 * 
	 * ���³��ֵ�λ��
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
	 * ���ü�����ɫ
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
	 * ������
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
	 * ������
	 * 
	 */
	public int getRow(){
		return row;
	}

	public void setRow(int row){
		this.row=row;
	}
	
}
