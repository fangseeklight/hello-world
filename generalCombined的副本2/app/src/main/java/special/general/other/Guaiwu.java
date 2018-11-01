package special.general.other;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import special.general.R;

/**
 * Created by 郭昊然 on 2016/5/22.
 */
public class Guaiwu {
    private int position_x,position_y;//怪物的位置,指的是图片左下角的坐标
    private int toX,toY;//怪物要去的位置
    private int leixing;//怪物的类型
    private Bitmap gw1,gw2,gw3;
    private int xW,xH;//小地图的宽和高
    private Bitmap jl1[][]=new Bitmap[4][4];
    private Bitmap jl2[][]=new Bitmap[4][4];
    private Bitmap jl3[][]=new Bitmap[4][4];
    private int fangxiang=2;//怪物行走的方向，向下为0，向左为1，向右为2，向上为3默认为向右
    private float dangqianzhen;//dangqian是第几张图片
    private final float xingzousudu=(float)0.2;//设置行走摆动速度，0-1之间且不为0
    static int sudu=6;//设置行走速度，1以上
    private final float gw1Wbili=1,gw1Hbili=1;//设置怪物宽和高是小地图的几倍
    private final float gw2Wbili=(float)1.2,gw2Hbili=(float)1.2;//设置怪物宽和高是小地图的几倍
    private final float gw3Wbili=(float)1.5,gw3Hbili=(float)1.5;//设置怪物宽和高是小地图的几倍
    private Resources resources;
    private int gwW,gwH;//怪物的宽和高
    private boolean alive=true;//还活着吗？
    private boolean arrived=true;//抵达目的地了吗
    private int map[][];
    private int gw1W,gw1H;//怪物的图片宽高度，包含16个小图的图片
    private int gw2W,gw2H;
    private int gw3W,gw3H;

    private int zuo,you,shang,xia;//定义在范围内的几个小地图的位置

    private int blood;//怪物的血量为
    private int nowblood;//怪物当前血量

    private final int SW1=100;//定义怪物死亡挣到的钱为
    private final int SW2=250;//定义怪物死亡挣到的钱为
    private final int SW3=450;//定义怪物死亡挣到的钱为
    private final int gw1blood=550;//定义怪物1血量
    private final int gw2blood=1450;//定义怪物2血量
    private final int gw3blood=4950;//定义怪物12血量
    public Guaiwu(){}
    public Guaiwu(int[][] map,int leixing, Resources resources,int xW,int xH)
    {
      this.leixing=leixing;
        this.map=map;
        if(leixing==1)
        {
            this.blood=gw1blood;
            this.nowblood=gw1blood;
        }else if(leixing==2)
        {
            this.blood=gw2blood;
            this.nowblood=gw2blood;
        }else
        {
            this.blood=gw3blood;
            this.nowblood=gw3blood;
        }
      this.resources=resources;
        this.xW=xW;
        this.xH=xH;
       initializie();//初始化
       // gw1= BitmapFactory.decodeResource(resources, R.drawable.jingling1);
    }
    private void initializie()
    {
        if(leixing==1)
        {
            gw1= BitmapFactory.decodeResource(resources, R.drawable.jingling1);
            gw1W=gw1.getWidth();gw1H=gw1.getHeight();
            for(int i=0;i<4;i++)//将怪物图分割为4*4的图像
            {
                for(int j=0;j<4;j++)
                {
                    jl1[i][j]=Bitmap.createBitmap(gw1,j*gw1W/4,i*gw1H/4,gw1W/4,gw1H/4);
                }
            }
            gwH=(int)(xH*gw1Hbili);gwW=(int)(xW*gw1Wbili);//怪物的高和宽
        }else if(leixing==2)
        {
            gw2= BitmapFactory.decodeResource(resources, R.drawable.jingling2);
            gw2W=gw2.getWidth();gw2H=gw2.getHeight();
            for(int i=0;i<4;i++)//将怪物图分割为4*4的图像
            {
                for(int j=0;j<4;j++)
                {
                    jl2[i][j]=Bitmap.createBitmap(gw2,j*gw2W/4,i*gw2H/4,gw2W/4,gw2H/4);
                }
            }
            gwH=(int)(xH*gw2Hbili);gwW=(int)(xW*gw2Wbili);
        }else
        {
              gw3= BitmapFactory.decodeResource(resources, R.drawable.jingling3);
              gw3W=gw3.getWidth();gw3H=gw3.getHeight();
            for(int i=0;i<4;i++)//将怪物图分割为4*4的图像
            {
                for(int j=0;j<4;j++)
                {
                    jl3[i][j]=Bitmap.createBitmap(gw3,j*gw3W/4,i*gw3H/4,gw3W/4,gw3H/4);
                }
            }
            gwH=(int)(xH*gw3Hbili);gwW=(int)(xW*gw3Wbili);
        }

//        if(leixing==1)
//    {
//        gwH=(int)(xH*gw1Hbili);gwW=(int)(xW*gw1Wbili);//position_x=xW*0;position_y=xH*4;
//    }
////        else if(leixing==2)
////        {
////            gwH=(int)(xH*gw2Hbili);gwW=(int)(xW*gw2Wbili);//position_x=xW*0;position_y=xH*2;
////        }
////        else{
////            gwH=(int)(xH*gw3Hbili);gwW=(int)(xW*gw3Wbili);//position_x=xW*0;position_y=xH*6;
////        }

    }
    public void setmap(int[][] map)//设置map
    {
        this.map=map;
    }
    public void canbeattacked()//可以遭受攻击或者说在打击范围内
    {

    }
    public void settoPositopn(int tx,int ty)//设置走到哪里
    {
        this.toX=tx;
        this.toY=ty;
    }
    public int getFangxiang()
    {
        return this.fangxiang;
    }
    public boolean isalive()//还活着吗？
    {
        return alive;
    }
    public void gotodeath()//死了
    {
        this.alive=false;
    }
    public void setFangxiang(int fangxiang)
    {
        this.fangxiang=fangxiang;
    }
    public void drawgw(Canvas canvas, Paint paint)//绘制自己
    {
       if(alive)//如果还活着？
        {
            if(leixing==1)//第一种类型的怪物
            {
               canvas.drawBitmap(jl1[fangxiang][(int)dangqianzhen],null,new Rect(position_x,position_y-gwH,position_x+gwW,position_y),paint);
            }else if(leixing==2)
            {
                canvas.drawBitmap(jl2[fangxiang][(int)dangqianzhen],null,new Rect(position_x,position_y-gwH,position_x+gwW,position_y),paint);
            }else
            {
               canvas.drawBitmap(jl3[fangxiang][(int)dangqianzhen],null,new Rect(position_x,position_y-gwH,position_x+gwW,position_y),paint);
            }
            paint.setColor(Color.RED);
            paint.setStrokeWidth(1+xW/23);
            canvas.drawRect(position_x,position_y-gwH,position_x+(int)(gwW*(nowblood*1.0/blood)),position_y-gwH*8/9,paint);//绘制血量  设置
            if(!arrived)//没有抵达那么就运动
            {
                if(fangxiang==0)
                {
                    this.position_y+=sudu;
                }else if(fangxiang==1)
                {
                    this.position_x-=sudu;
                }else if(fangxiang==2)
                {
                    this.position_x+=sudu;
                }else
                {
                    this.position_y-=sudu;
                }
                dangqianzhen+=xingzousudu;//变换步形
                if(dangqianzhen>4)
                {
                    dangqianzhen=0;
                }

            }else//正常抵达
            {

            }
            if(Math.abs(position_x-toX)+Math.abs(position_y-toY)<=2*sudu)//如果···则抵达了
            {
                arrived=true;
                position_x=toX;
                position_y=toY;
            }
         //region 下面判断并绘制子弹  绘制激光代替子弹 子弹有点麻烦 其实就是画线
            zuo=zuoX(this.position_x)>0?zuoX(this.position_x):0;
            you=youX(this.position_x)<15?youX(this.position_x):15;
            shang=shangY(this.position_y)>0?shangY(this.position_y):0;
            xia=xiaY(this.position_y)<9?xiaY(this.position_y):9;
            //zuo=1;you=4;shang=2;xia=5;
            for(int i=shang;i<=xia;i++)
            {
                for(int j=zuo;j<=you;j++)
                {
                    if(length(this.position_x+xW/2,this.position_y-xH/2,zhongX(j),zhongY(i))<3*xW)//如果在范围内
                    {
//                        if(map[i][j]<0)
//                        {
//                            canvas.drawLine(this.position_x+xW/2,this.position_y-xH/2,zhongX(j),zhongY(i),paint);
//                            this.nowblood-=1;
//                        }
                        if(map[i][j]==-1)
                        {
                            paint.setStrokeWidth(1+xW/33);
                            paint.setColor(Color.rgb(170,6,245));//紫色
                            canvas.drawLine(this.position_x+xW/2,this.position_y-xH/2,zhongX(j),zhongY(i),paint);
                            this.nowblood-=1;
                            // canvas.drawLine(this.position_x,this.position_y,0,0,paint);
                        }else if(map[i][j]==-11)
                        {
                            paint.setStrokeWidth(1+xW/23);
                            paint.setColor(Color.RED);//紫色
                            canvas.drawLine(this.position_x+xW/2,this.position_y-xH/2,zhongX(j),zhongY(i),paint);
                            this.nowblood-=2;
                        }else if(map[i][j]==-111)
                        {
                            paint.setStrokeWidth(1+xW/16);
                            paint.setColor(Color.BLUE);
                            canvas.drawLine(this.position_x+xW/2,this.position_y-xH/2,zhongX(j),zhongY(i),paint);
                            this.nowblood-=3;
                        }else if(map[i][j]==-2)
                        {
                            paint.setStrokeWidth(1+xW/17);
                            paint.setColor(Color.YELLOW);
                            canvas.drawLine(this.position_x+xW/2,this.position_y-xH/2,zhongX(j),zhongY(i),paint);
                            this.nowblood-=2;
                        }else if(map[i][j]==-22)
                        {
                            paint.setStrokeWidth(1+xW/13);
                            paint.setColor(Color.rgb(243,104,11));//橘色
                            canvas.drawLine(this.position_x+xW/2,this.position_y-xH/2,zhongX(j),zhongY(i),paint);
                            this.nowblood-=4;
                        }else if(map[i][j]==-222)
                        {
                            paint.setStrokeWidth(1+xW/8);
                            paint.setColor(Color.rgb(22,124,227));//青色
                            canvas.drawLine(this.position_x+xW/2,this.position_y-xH/2,zhongX(j),zhongY(i),paint);
                            this.nowblood-=6;
                        }else if(map[i][j]==-3)
                        {
                            paint.setStrokeWidth(1+xW/12);
                            paint.setColor(Color.rgb(226,116,19));//黄色
                            canvas.drawLine(this.position_x+xW/2,this.position_y-xH/2,zhongX(j),zhongY(i),paint);
                            this.nowblood-=3;
                        }else if(map[i][j]==-33)
                        {
                            paint.setStrokeWidth(1+xW/7);
                            paint.setColor(Color.rgb(56,224,64));//青色
                            canvas.drawLine(this.position_x+xW/2,this.position_y-xH/2,zhongX(j),zhongY(i),paint);
                            this.nowblood-=6;
                        }else if(map[i][j]==-333)
                        {
                            paint.setStrokeWidth(1+xW/5);
                            paint.setColor(Color.rgb(72,207,201));//青色
                            canvas.drawLine(this.position_x+xW/2,this.position_y-xH/2,zhongX(j),zhongY(i),paint);
                            this.nowblood-=9;
                        }

                    }

                }
            }
            //endregion
            if(this.nowblood<=0)//如果被打死了
            {
                alive=false;
                if(this.leixing==1)
                {
                    Data.money+=SW1;
                    Data.score+=SW1;
                    Data.guai1();//吼叫一声
                }else if(this.leixing==2)
                {
                    Data.money+=SW2;
                    Data.score+=SW2;
                    Data.guai2();//吼叫一声
                }else
                {
                    Data.money+=SW3;
                    Data.score+=SW3;
                    Data.guai3();//吼叫一声
                }

            }
        }
       // canvas.drawBitmap(gw1,null,new Rect(0,0,350,350),paint);
    }
    public void drawtest(Canvas canvas,Paint paint)
    {
        //canvas.drawBitmap(gw1,null,new Rect(0,0,550,550),paint);
    }
    public int getPosition_x()
    {
        return position_x;
    }
    public int getPosition_y()
    {
        return position_y;
    }
    public void setPosition_x(int x)
    {
        this.position_x=x;
    }
    public void setPosition_y(int y)
    {
        this.position_y=y;
    }
    public boolean isArrived()//抵达了目的地了吗？
    {
        return arrived;
    }
    public void setArrived(boolean arrived)
    {
        this.arrived=arrived;
    }

    private int zuoX(int x)//返回x,y所在的方块的左三格的横格子位置
    {
        return (x-3*xW+xW/5)/xW;
    }
    private int youX(int x)//返回x,y所在的方块的右三格的横格子位置
    {
        return (x+3*xW+xW/5)/xW;
    }
    private int shangY(int y)//返回x,y所在的方块的上三格的纵格子位置
    {
        return (y-3*xH+xH/5)/xH-1;
    }
    private int xiaY(int y)///返回x,y所在的方块的下三格的纵格子位置
    {
        return (y+3*xH+xH/5)/xH-1;
    }

//    private int fangY(int y)//返回x,y所在的方块的纵坐标
//    {
//        return (y+xH/5)/xH-1;
//    }
    private int zhongX(int X)//根据所在方块 返回方块中间横坐标
    {
        return (X)*xW+xW/2;
    }
    private int zhongY(int Y)//根据所在方块 返回方块中间纵坐标
    {
        return (Y)*xH+xH/2;
    }
    private int length(int x1,int y1,int x2,int y2)//两点间的距离
    {
        double xx=x1-x2;
        double yy=y1-y1;
        return (int)Math.sqrt(xx*xx+yy*yy);

    }
}
