import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 * ������
 *  @author ʯ���ƣ����ı�
 *
 */

public class Snake{
	/*
	 * ����������
	 * 
	 */
	private Body head=null;
	private Body tail=null;
	private int size=0;
	//�ߵĳ�ʼλ��
	private Body n=new Body(0,0,Dir.D);
	private Yard y;
	/*
	 * �����߷���
	 */
	public Snake(Yard y){
		head=n;
		tail=n;
		size=1;
		this.y=y;
	}
	
	
	/*
	 * ��ͷ��1
	 * 
	 */
	public void addToHead(){
		Body body=null; 
		switch(head.dir){
		case L:
			body=new Body(head.row,head.col-1,head.dir);
			break;
		case U:
			body=new Body(head.row-1,head.col,head.dir);
			break;
		case R:
			body = new Body(head.row,head.col+1,head.dir);
			break;
		case D:
			body = new Body(head.row+1,head.col,head.dir);
			break;
		}
		body.next=head;
		head.prev=body;
		head=body;
		size ++;
	}
	
	/*
	 * ��ǰ�ƶ�
	 */
	public void draw(Graphics g){
		if(size<=0) 
			return;
		move();
		for(Body n=head;n!=null;n=n.next) {
			n.draw(g);
		}
	}
	
	/*
	 * �ƶ�����
	 */
	private void move(){
		addToHead();
		deleteFromTail();
		checkDead();
	}

	/*
	 * �ж����Ƿ�����
	 * 
	 */
	private void checkDead(){
		if(head.row<0||head.col<0||head.row>Yard.ROWS||head.col>Yard.COLS){
			y.stop();
		}
		
		for(Body n=head.next;n!=null;n=n.next) {
			if(head.row==n.row&&head.col==n.col){
				y.stop();
			}
		}
	}
	
	/*
	 * ��ǰ�ƶ���β����һ
	 * 
	 */
	private void deleteFromTail(){
		if(size==0) 
			return;
		tail=tail.prev;
		tail.next=null;
		
	}

	/*
	 * ������
	 * 
	 */
	private class Body{
		int w=Yard.BLOCK_SIZE;
		int h=Yard.BLOCK_SIZE;
		int row,col;
		Dir dir=Dir.L;
		Body next=null;
		Body prev=null;
		
		Body(int row,int col,Dir dir){
			this.row=row;
			this.col=col;
			this.dir=dir;
		}
		
		/*
		 * �����������ɫ
		 * 
		 */
		void draw(Graphics g){
			Color c=g.getColor();
			g.setColor(Color.black);
//			if(c==Color.black)
//				c=Color.RED;
//			else c=Color.black;
			g.fillRect(Yard.BLOCK_SIZE*col,Yard.BLOCK_SIZE*row,w,h);
			g.setColor(c);

		}
	}
	
	/*
	 * �߳Ե�egg��5��
	 * 
	 */
	public void eat(Egg e){
		if(this.getRect().intersects(e.getRect())){
			e.reAppear();
			this.addToHead();
			y.setScore(y.getScore()+5);
		}
	}
	
	/*
	 * 
	 */
	private Rectangle getRect(){
		return new Rectangle(Yard.BLOCK_SIZE*head.col,Yard.BLOCK_SIZE*head.row,head.w,head.h);
	}
	
	/*
	 * ���ü����������ҿ��Ƽ��������ߵ��ƶ�����
	 * 
	 */
	public void keyPressed(KeyEvent e){
		int key=e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT:
			if(head.dir!=Dir.R)
				head.dir=Dir.L;
			break;
		case KeyEvent.VK_UP:
			if(head.dir!=Dir.D)
				head.dir=Dir.U;
			break;
		case KeyEvent.VK_RIGHT:
			if(head.dir!=Dir.L)
				head.dir=Dir.R;
			break;
		case KeyEvent.VK_DOWN:
			if(head.dir!=Dir.U)
				head.dir=Dir.D;
			break;
		}
	}
}