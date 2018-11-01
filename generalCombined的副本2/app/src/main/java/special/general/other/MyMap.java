package special.general.other;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import special.general.Mysurfaceview;
import special.general.R;

/**
 * Created by 郭昊然 on 2016/5/20.
 */
public class MyMap {
    private Gwcreat guaiwucreat;//创造怪物的类
    private boolean pause=true;//游戏暂停吗？
    private Mysurfaceview mysurfaceview;//控制上一个类的方法
    private Context context;
    //region 游戏数据自己设定
    private final int JL1=250;//新建塔的花费为：JL代表建立
    private final int SJ1_11=200;//升级塔的花费为：
    private final int SJ11_111=300;//升级塔的花费为：
    private final int M1_0=200;//卖塔挣钱
    private final int M11_0=450;//卖塔挣钱
    private final int M111_0=600;//卖塔挣钱

    private final int JL2=500;//新建塔的花费为：
    private final int SJ2_22=350;//升级塔的花费为：
    private final int SJ22_222=450;//升级塔的花费为：
    private final int M2_0=400;//卖塔挣钱
    private final int M22_0=650;//卖塔挣钱
    private final int M222_0=1000;//卖塔挣钱

    private final int JL3=800;//新建塔的花费为：
    private final int SJ3_33=600;//升级塔的花费为：
    private final int SJ33_333=900;//升级塔的花费为：
    private final int M3_0=650;//卖塔挣钱
    private final int M33_0=1100;//卖塔挣钱
    private final int M333_0=1850;//卖塔挣钱

    private int backX=1;//后退图标和暂停图标的具体位置
    private int backY=0;
    private int pauseX=14;
    private int pauseY=0;
    private int moneytextX=3;//定义money坐标
    private int moneytextY=1;
    private int tishitextX=8;//定义提示信息坐标
    private int tishitextY=1;
    private final int tishinumzong=30;//定义提示坚持的时间 ：tishinum * 50 （ms）

    //endregion
    private int tishinum=-1;//当前的次数 用于实时记录
    private int tishimoney;//暂时需要的钱 显示   钱不够了，还需要tishimoney钱

    private Canvas canvas;
    private Paint paint;
    Resources resources;
    private Bitmap m1,m2,m3,m4,m5,m6,m7,m8,m0,ta1,ta11,ta111,ta2,ta22,ta222,ta3,ta33,ta333,ta1c,ta2c,ta3c,update,delete,pausepic,startpic,back;//暂停开始后退图标
    private int ScreenH,ScreenW;//屏幕高和宽
    private int num;//num为第几幅地图
    private final int W=16;//定义宽分为16份
    private final int H=10;//定义高分为10份
    private int xW,xH;//小图的宽和高
    private int onX,onY;//当前指针的的坐标，刚刚按过的位置
    private boolean showtamenu=false,showupdate=false;//是否绘制塔选项？升级选项？
    public static   int[][] map1={//-1,-2-,-3为不同的塔,-11,-111为不同等级的塔
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {1,5,5,5,5,5,5,5,5,5,5,5,2,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,1,8,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    };//H行W列
    public static int[][] map2={
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {1,5,2,0,1,5,2,0,0,0,0,0,0,0,0,0},
            {0,0,7,0,7,0,7,0,0,1,5,5,2,0,0,0},
            {0,0,7,0,7,0,6,0,0,7,0,0,7,0,0,0},
            {0,0,7,0,7,0,6,0,0,7,0,0,7,0,0,0},
            {0,0,7,0,7,0,6,0,0,7,0,0,1,8,2,0},
            {0,0,7,0,7,0,6,0,0,7,0,0,0,0,0,0},
            {0,0,7,0,7,0,6,0,0,7,0,0,0,0,0,0},
            {0,0,6,0,7,0,6,0,1,4,0,0,0,0,0,0},
            {0,0,1,8,4,0,1,1,4,0,0,0,0,0,0,0}
    };//H行W列
    public static int[][] map3={
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,1,5,2,0,0,0,1,5,2,0},
            {0,0,0,0,0,0,6,0,7,0,0,0,7,0,7,0},
            {0,1,5,5,2,0,6,0,7,0,0,0,7,0,7,0},
            {0,7,0,0,7,0,6,0,1,8,2,0,7,0,6,0},
            {1,4,0,0,6,0,6,0,0,0,6,0,7,0,6,0},
            {0,0,0,0,6,0,6,0,0,0,6,0,7,0,0,0},
            {0,0,0,0,6,0,6,0,0,0,6,0,7,0,0,0},
            {0,0,0,0,6,0,6,0,0,0,1,8,4,0,0,0},
            {0,0,0,0,1,8,4,0,0,0,0,0,0,0,0,0}
    };//H行W列
/*    int[][] map4 = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    };
*/
   //声音
    Gwcreat opGwcreat = new Gwcreat();


    public MyMap(Canvas canvas, Paint paint,Mysurfaceview mysurfaceview,Resources resources,int num)//num为第几幅地图
    {
        this.canvas=canvas;
        this.paint=paint;
        this.num=num;
        this.ScreenH=canvas.getHeight();
        this.ScreenW=canvas.getWidth();
        this.resources=resources;
        this.mysurfaceview=mysurfaceview;
        //My code part
//        AImonster opAI = new AImonster();
//        map4 = opAI.createMap();

        initializie();
        if(num==1)
        {
            guaiwucreat=new Gwcreat(this.context,map1,num,resources,canvas,paint,5,4,0,xW,xH);guaiwucreat.setmap(map1);///////删除试试
        }else if(num==2)
        {
            guaiwucreat=new Gwcreat(this.context,map2,num,resources,canvas,paint,10,2,0,xW,xH);guaiwucreat.setmap(map2);///
        }else
        {
            guaiwucreat=new Gwcreat(this.context,map3,num,resources,canvas,paint,20,6,0,xW,xH);guaiwucreat.setmap(map3);///
        }

//             if(num==1) {guaiwucreat=new Gwcreat(resources,canvas,paint,10,4,0,xW,xH);guaiwucreat.setmap(map1);}
//           else if(num==2){guaiwucreat=new Gwcreat(resources,canvas,paint,20,2,0,xW,xH);guaiwucreat.setmap(map2);}
//                   else {guaiwucreat=new Gwcreat(resources,canvas,paint,30,6,0,xW,xH);guaiwucreat.setmap(map3);}

    }
    private void initializie()//初始化
    {

        xW=ScreenW/W;
        xH=ScreenH/H;
        m0= BitmapFactory.decodeResource(resources, R.drawable.m0);
        m1= BitmapFactory.decodeResource(resources, R.drawable.m1);
        m2= BitmapFactory.decodeResource(resources, R.drawable.m2);
        m3= BitmapFactory.decodeResource(resources, R.drawable.m3);
        m4= BitmapFactory.decodeResource(resources, R.drawable.m4);
        m5= BitmapFactory.decodeResource(resources, R.drawable.m5);
        m6= BitmapFactory.decodeResource(resources, R.drawable.m6);
        m7= BitmapFactory.decodeResource(resources, R.drawable.m7);
        m8= BitmapFactory.decodeResource(resources, R.drawable.m8);
        ta1= BitmapFactory.decodeResource(resources, R.drawable.ta1);
        ta11= BitmapFactory.decodeResource(resources, R.drawable.ta11);
        ta111= BitmapFactory.decodeResource(resources, R.drawable.ta111);
        ta2= BitmapFactory.decodeResource(resources, R.drawable.ta2);
        ta22= BitmapFactory.decodeResource(resources, R.drawable.ta22);
        ta222= BitmapFactory.decodeResource(resources, R.drawable.ta222);
        ta3= BitmapFactory.decodeResource(resources, R.drawable.ta3);
        ta33= BitmapFactory.decodeResource(resources, R.drawable.ta33);
        ta333= BitmapFactory.decodeResource(resources, R.drawable.ta333);
        update= BitmapFactory.decodeResource(resources, R.drawable.update);
        delete= BitmapFactory.decodeResource(resources, R.drawable.delete);
        ta1c= BitmapFactory.decodeResource(resources, R.drawable.ta1c);
        ta2c= BitmapFactory.decodeResource(resources, R.drawable.ta2c);
        ta3c= BitmapFactory.decodeResource(resources, R.drawable.ta3c);
        pausepic=BitmapFactory.decodeResource(resources, R.drawable.pause);
        startpic=BitmapFactory.decodeResource(resources, R.drawable.start);
        back=BitmapFactory.decodeResource(resources, R.drawable.back);
       // guaiwucreat=new Gwcreat(resources,canvas,paint,10,4,0,xW,xH);//guaiwucreat.setmap(map1);///
    }

    public void drawmap() throws InterruptedException {

        //region 绘制地图
        if(num==1)
        {
            for(int i=0;i<H;i++)
            {
                for(int j=0;j<W;j++)
                {
                    if(map1[i][j]==0) {canvas.drawBitmap(m0,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map1[i][j]==1) {canvas.drawBitmap(m1,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map1[i][j]==2) {canvas.drawBitmap(m2,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map1[i][j]==3) {canvas.drawBitmap(m3,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map1[i][j]==4) {canvas.drawBitmap(m4,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map1[i][j]==5) {canvas.drawBitmap(m5,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map1[i][j]==6) {canvas.drawBitmap(m6,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map1[i][j]==7) {canvas.drawBitmap(m7,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map1[i][j]==8) {canvas.drawBitmap(m8,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map1[i][j]==-1) {canvas.drawBitmap(ta1,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map1[i][j]==-2) {canvas.drawBitmap(ta2,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map1[i][j]==-3) {canvas.drawBitmap(ta3,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map1[i][j]==-11) {canvas.drawBitmap(ta11,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map1[i][j]==-22) {canvas.drawBitmap(ta22,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map1[i][j]==-33) {canvas.drawBitmap(ta33,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map1[i][j]==-111) {canvas.drawBitmap(ta111,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map1[i][j]==-222) {canvas.drawBitmap(ta222,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map1[i][j]==-333) {canvas.drawBitmap(ta333,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}

                }
            }
        }else if(num==2)
        {
            for(int i=0;i<H;i++)
            {
                for(int j=0;j<W;j++)
                {
                    if(map2[i][j]==0) {canvas.drawBitmap(m0,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map2[i][j]==1) {canvas.drawBitmap(m1,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map2[i][j]==2) {canvas.drawBitmap(m2,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map2[i][j]==3) {canvas.drawBitmap(m3,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map2[i][j]==4) {canvas.drawBitmap(m4,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map2[i][j]==5) {canvas.drawBitmap(m5,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map2[i][j]==6) {canvas.drawBitmap(m6,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map2[i][j]==7) {canvas.drawBitmap(m7,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map2[i][j]==8) {canvas.drawBitmap(m8,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map2[i][j]==-1) {canvas.drawBitmap(ta1,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map2[i][j]==-2) {canvas.drawBitmap(ta2,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map2[i][j]==-3) {canvas.drawBitmap(ta3,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map2[i][j]==-11) {canvas.drawBitmap(ta11,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map2[i][j]==-22) {canvas.drawBitmap(ta22,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map2[i][j]==-33) {canvas.drawBitmap(ta33,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map2[i][j]==-111) {canvas.drawBitmap(ta111,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map2[i][j]==-222) {canvas.drawBitmap(ta222,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map2[i][j]==-333) {canvas.drawBitmap(ta333,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}
                }
            }
        }else
        {
            for(int i=0;i<H;i++)
            {
                for(int j=0;j<W;j++)
                {
                    if(map3[i][j]==0) {canvas.drawBitmap(m0,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map3[i][j]==1) {canvas.drawBitmap(m1,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map3[i][j]==2) {canvas.drawBitmap(m2,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map3[i][j]==3) {canvas.drawBitmap(m3,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map3[i][j]==4) {canvas.drawBitmap(m4,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map3[i][j]==5) {canvas.drawBitmap(m5,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map3[i][j]==6) {canvas.drawBitmap(m6,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map3[i][j]==7) {canvas.drawBitmap(m7,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map3[i][j]==8) {canvas.drawBitmap(m8,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map3[i][j]==-1) {canvas.drawBitmap(ta1,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map3[i][j]==-2) {canvas.drawBitmap(ta2,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map3[i][j]==-3) {canvas.drawBitmap(ta3,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map3[i][j]==-11) {canvas.drawBitmap(ta11,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map3[i][j]==-22) {canvas.drawBitmap(ta22,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map3[i][j]==-33) {canvas.drawBitmap(ta33,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map3[i][j]==-111) {canvas.drawBitmap(ta111,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map3[i][j]==-222) {canvas.drawBitmap(ta222,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}else
                    if(map3[i][j]==-333) {canvas.drawBitmap(ta333,null,new Rect(j*xW,i*xH,j*xW+xW,i*xH+xH),paint);}
                }
            }
        }
        //endregion

        if(showtamenu)//如果有塔菜单
        {
            canvas.drawBitmap(ta1c,null,new Rect((onX-1)*xW,onY*xH,(onX-1)*xW+xW,onY*xH+xH),paint);
            canvas.drawBitmap(ta2c,null,new Rect((onX)*xW,onY*xH,(onX)*xW+xW,onY*xH+xH),paint);
            canvas.drawBitmap(ta3c,null,new Rect((onX+1)*xW,onY*xH,(onX+1)*xW+xW,onY*xH+xH),paint);
            //根据onX和onY
           // drawtamenew必须
        }
        if(showupdate)//有升级菜单
        {
            canvas.drawBitmap(update,null,new Rect((onX)*xW,(onY-1)*xH,(onX)*xW+xW,(onY-1)*xH+xH),paint);
            canvas.drawBitmap(delete,null,new Rect((onX)*xW,(onY+1)*xH,(onX)*xW+xW,(onY+1)*xH+xH),paint);
            paint.setColor(Color.BLACK);
            canvas.drawCircle(onX*xW+xW/2,onY*xH+xH/2,xW*3,paint);
        }
        //下面绘制暂停开始和返回菜单

        canvas.drawBitmap(back,null,new Rect(backX*xW,backY*xH,backX*xW+xW,backY*xH+xH),paint);
//        if(num==1)//如果是第一幅地图
//        {
//            guaiwucreat=new Gwcreat(map1);
//            guaiwucreat.start();//开始进行
//
//        }else if(num==2)
//        {
//
//        }else
//        {
//
//        }
        if(pause)
        {
            canvas.drawBitmap(pausepic,null,new Rect((pauseX)*xW,(pauseY)*xH,pauseX*xW+xW*2,pauseY*xH+xH),paint);
            guaiwucreat.Pause();
        }else
        {
            canvas.drawBitmap(startpic,null,new Rect((pauseX)*xW,(pauseY)*xH,pauseX*xW+xW*2,pauseY*xH+xH),paint);
            guaiwucreat.Resume();
        }
        Paint textPaint=new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(xH/2);
        canvas.drawText("$$$: "+Data.money,moneytextX*xW,moneytextY*xH,textPaint);//绘制金钱
        if(tishinum>=0)
        {
            textPaint.setColor(Color.RED);
            canvas.drawText("not enough Money，need $ "+tishimoney,tishitextX*xW,tishitextY*xH,textPaint);//绘制金钱
            tishinum-=1;
        }
        guaiwucreat.start();



    }
    public void pushdown(int x,int y)//按下的动作
    {
     // canvas.drawColor(Color.WHITE);
    }
    public void move(int x,int y)//移动的动作
    {

    }
    public void pushup(int x,int y)//离开屏幕的动作
    {
        if(fangY(y)>=1)//如果点击的是屏幕第二行或以下
        {
            if(num==1)
            {

                //region 绘制选塔菜单
                if(!showupdate||(showupdate&&(onX!=fangX(x)||Math.abs(onY-fangY(y))>1)))
                {
                    if(map1[fangY(y)][fangX(x)]==0)//如果可以画。注意这里横纵坐标竟然对调！！！
                    {
                        if(!showtamenu)//没有显示选塔菜单
                        {
                            onX=fangX(x);
                            onY=fangY(y);
                            canvas.drawBitmap(ta1c,null,new Rect((onX-1)*xW,onY*xH,(onX-1)*xW+xW,onY*xH+xH),paint);
                            canvas.drawBitmap(ta2c,null,new Rect((onX)*xW,onY*xH,(onX)*xW+xW,onY*xH+xH),paint);
                            canvas.drawBitmap(ta3c,null,new Rect((onX+1)*xW,onY*xH,(onX+1)*xW+xW,onY*xH+xH),paint);
                            showtamenu=true;
                        }else//有菜单
                        {
                            if(fangY(y)==onY&&Math.abs(fangX(x)-onX)<=1)//在菜单内
                            {
                                if(fangX(x)==onX)
                                {
                                    if(!qianbugou(JL2))//钱够用了
                                    {
                                        map1[fangY(y)][fangX(x)]=-2;//////这里修改  建立塔 减钱
                                        Data.jianzhu();//放塔的声音
                                        Data.money-=JL2;
                                    }
                                }else if(fangX(x)==(onX-1))
                                {
                                    if(!qianbugou(JL1))//钱够用了
                                    {
                                        map1[fangY(y)][fangX(x) + 1] = -1;
                                        Data.jianzhu();//放塔的声音
                                        Data.money -= JL1;
                                    }
                                }else if(fangX(x)==(onX+1))
                                {
                                    if(!qianbugou(JL3))//钱够用了
                                    {
                                        map1[fangY(y)][fangX(x) - 1] = -3;
                                        Data.jianzhu();//放塔的声音
                                        Data.money -= JL3;
                                    }
                                }
                                //减钱
                                showtamenu=false;
                            }else
                            {
                                onX=fangX(x);
                                onY=fangY(y);
                                canvas.drawBitmap(ta1c,null,new Rect((onX-1)*xW,onY*xH,(onX-1)*xW+xW,onY*xH+xH),paint);
                                canvas.drawBitmap(ta2c,null,new Rect((onX)*xW,onY*xH,(onX)*xW+xW,onY*xH+xH),paint);
                                canvas.drawBitmap(ta3c,null,new Rect((onX+1)*xW,onY*xH,(onX+1)*xW+xW,onY*xH+xH),paint);
                                showtamenu=true;
                            }
                        }
                        //画塔菜单和
//                    canvas.drawBitmap(ta1,null,new Rect((onX-1)*xW,onY*xH,(onX-1)*xW+xW,onY*xH+xH),paint);
//                    canvas.drawBitmap(ta2,null,new Rect((onX)*xW,onY*xH,(onX)*xW+xW,onY*xH+xH),paint);
//                    canvas.drawBitmap(ta3,null,new Rect((onX+1)*xW,onY*xH,(onX+1)*xW+xW,onY*xH+xH),paint);
//                    showtamenu=true;
                    }else//地图不是土地
                    {
                        if(showtamenu)
                        {
                            if(fangY(y)==onY&&Math.abs(fangX(x)-onX)<=1)//在菜单内
                            {
                                if(fangX(x)==onX)
                                {
                                    if(!qianbugou(JL2))//钱够用了
                                    {
                                        map1[fangY(y)][fangX(x)] = -2;//////这里修改
                                        Data.jianzhu();//放塔的声音
                                        Data.money -= JL2;
                                    }
                                }else if(fangX(x)==(onX-1))
                                {
                                    if(!qianbugou(JL1))//钱够用了
                                    {
                                        map1[fangY(y)][fangX(x) + 1] = -1;
                                        Data.jianzhu();//放塔的声音
                                        Data.money -= JL1;
                                    }
                                }else if(fangX(x)==(onX+1))
                                {
                                    if(!qianbugou(JL3))//钱够用了
                                    {
                                        map1[fangY(y)][fangX(x) - 1] = -3;
                                        Data.jianzhu();//放塔的声音
                                        Data.money -= JL3;
                                    }
                                }
                                //减钱
                            }
                            showtamenu=false;
                        }
                    }
                }

                //endregion
                //region 绘制升级菜单

                if(showupdate)//有升级菜单
                {
                    if(onX==fangX(x))
                    {
                        // map1[onY][onX]=-111;
                        // Log.d("减钱",fangX(x)+"");
                        if(onY==(fangY(y)+1))//升级减钱
                        {

                            if(map1[onY][onX]==-1){if(!qianbugou(SJ1_11)) {map1[onY][onX]=-11;Data.money -= SJ1_11;Data.shengji();}}//减钱 如果钱不够就不升级 下同
                            else if(map1[onY][onX]==-11){if(!qianbugou(SJ11_111)) {map1[onY][onX]=-111;Data.money-=SJ11_111;Data.shengji();}}//减钱
                            else if(map1[onY][onX]==-111){map1[onY][onX]=-111;}//
                            else if(map1[onY][onX]==-2){if(!qianbugou(SJ2_22)) {map1[onY][onX]=-22;Data.money-=SJ2_22;Data.shengji();}}//减钱
                            else if(map1[onY][onX]==-22){if(!qianbugou(SJ22_222)) {map1[onY][onX]=-222;Data.money-=SJ22_222;Data.shengji();}}//减钱
                            else if(map1[onY][onX]==-222){map1[onY][onX]=-222;}//
                            else if(map1[onY][onX]==-3){if(!qianbugou(SJ3_33)) {map1[onY][onX]=-33;Data.money-=SJ3_33;Data.shengji();}}//减钱
                            else if(map1[onY][onX]==-33){if(!qianbugou(SJ33_333)) {map1[onY][onX]=-333;Data.money-=SJ33_333;Data.shengji();}}//减钱
                            else if(map1[onY][onX]==-333){map1[onY][onX]=-333;}//
                            showtamenu=false;
                            //showupdate=false;
                        }else if(onY==fangY(y))
                        {
                            showtamenu=false;
                            //showupdate=false;
                        }else if(onY==(fangY(y)-1))//删除加钱
                        {
                            if(map1[onY][onX]==-1){map1[onY][onX]=0;Data.money+=M1_0;Data.moneyjia();}//加钱
                            else if(map1[onY][onX]==-11){map1[onY][onX]=0;Data.money+=M11_0;Data.moneyjia();}
                            else if(map1[onY][onX]==-111){map1[onY][onX]=0;Data.money+=M111_0;Data.moneyjia();}
                            else if(map1[onY][onX]==-2){map1[onY][onX]=0;Data.money+=M2_0;Data.moneyjia();}
                            else if(map1[onY][onX]==-22){map1[onY][onX]=0;Data.money+=M22_0;Data.moneyjia();}
                            else if(map1[onY][onX]==-222){map1[onY][onX]=0;Data.money+=M222_0;Data.moneyjia();}
                            else if(map1[onY][onX]==-3){map1[onY][onX]=0;Data.money+=M3_0;Data.moneyjia();}
                            else if(map1[onY][onX]==-33){map1[onY][onX]=0;Data.money+=M33_0;Data.moneyjia();}
                            else if(map1[onY][onX]==-333){map1[onY][onX]=0;Data.money+=M333_0;Data.moneyjia();}
                            showtamenu=false;
                            // showupdate=false;
                        }
                    }
                }
                // if(!showupdate)
                if(!showtamenu)//没有选择菜单
                {

                    if(map1[fangY(y)][fangX(x)]<0&&!showupdate)//如果是塔楼
                    {
                        onX=fangX(x);
                        onY=fangY(y);
                        canvas.drawBitmap(update,null,new Rect((onX)*xW,(onY-1)*xH,(onX)*xW+xW,(onY-1)*xH+xH),paint);
                        canvas.drawBitmap(delete,null,new Rect((onX)*xW,(onY+1)*xH,(onX)*xW+xW,(onY+1)*xH+xH),paint);
                        canvas.drawCircle(onX*xW+xW/2,onY*xH+xH/2,xW*3,paint);
                        showupdate=true;
                    }else
                    {
                        showupdate=false;
                    }
                }else
                {
                    if(map1[fangY(y)][fangX(x)]<0&&fangY(y)!=onY&&Math.abs(onX-fangX(x))>1)//如果是塔楼并且不在菜单里面
                    {
                        onX=fangX(x);
                        onY=fangY(y);
                        paint.setColor(Color.BLACK);
                        canvas.drawBitmap(update,null,new Rect((onX)*xW,(onY-1)*xH,(onX)*xW+xW,(onY-1)*xH+xH),paint);
                        canvas.drawBitmap(delete,null,new Rect((onX)*xW,(onY+1)*xH,(onX)*xW+xW,(onY+1)*xH+xH),paint);
                        canvas.drawCircle(onX*xW+xW/2,onY*xH+xH/2,xW*3,paint);
                        showupdate=true;
                        showtamenu=false;
                    }else
                    {
                        showupdate=false;
                    }
                }
                for(int j = 0; j < 10; j++){
                    for(int i = 0; i < 16; i++){
                        Gwcreat.map[j][i] = map1[j][i];
                    }
                }

                //endregion
//            if(showtamenu)//如果有菜单
//            {
//               if(fangY(y)==onY&&fangX(x)==onX)
//               {
//                  map1[fangY(y)][fangX(x)]=-2;//////这里修改
//               }else if(fangY(y)==onY&&fangX(x)==(onX-1))
//               {
//                 map1[fangY(y)][fangX(x)]=-1;
//               }else if(fangY(y)==onY&&fangX(x)==(onX+1))
//               {
//                  map1[fangY(y)][fangX(x)]=-3;
//               }
//            }
            }else if(num==2)
            {
                //region 绘制选塔菜单
                if(!showupdate||(showupdate&&(onX!=fangX(x)||Math.abs(onY-fangY(y))>1)))
                {
                    if(map2[fangY(y)][fangX(x)]==0)//如果可以画。注意这里横纵坐标竟然对调！！！
                    {
                        if(!showtamenu)//没有显示选塔菜单
                        {
                            onX=fangX(x);
                            onY=fangY(y);
                            canvas.drawBitmap(ta1c,null,new Rect((onX-1)*xW,onY*xH,(onX-1)*xW+xW,onY*xH+xH),paint);
                            canvas.drawBitmap(ta2c,null,new Rect((onX)*xW,onY*xH,(onX)*xW+xW,onY*xH+xH),paint);
                            canvas.drawBitmap(ta3c,null,new Rect((onX+1)*xW,onY*xH,(onX+1)*xW+xW,onY*xH+xH),paint);
                            showtamenu=true;
                        }else//有菜单
                        {
                            if(fangY(y)==onY&&Math.abs(fangX(x)-onX)<=1)//在菜单内
                            {
                                if(fangX(x)==onX)
                                {
                                    if(!qianbugou(JL2))//钱够用了
                                    {
                                        map2[fangY(y)][fangX(x)]=-2;//////这里修改  建立塔 减钱
                                        Data.jianzhu();//放塔的声音
                                        Data.money-=JL2;
                                    }
                                }else if(fangX(x)==(onX-1))
                                {
                                    if(!qianbugou(JL1))//钱够用了
                                    {
                                        map2[fangY(y)][fangX(x) + 1] = -1;
                                        Data.jianzhu();//放塔的声音
                                        Data.money -= JL1;
                                    }
                                }else if(fangX(x)==(onX+1))
                                {
                                    if(!qianbugou(JL3))//钱够用了
                                    {
                                        map2[fangY(y)][fangX(x) - 1] = -3;
                                        Data.jianzhu();//放塔的声音
                                        Data.money -= JL3;
                                    }
                                }
                                //减钱
                                showtamenu=false;
                            }else
                            {
                                onX=fangX(x);
                                onY=fangY(y);
                                canvas.drawBitmap(ta1c,null,new Rect((onX-1)*xW,onY*xH,(onX-1)*xW+xW,onY*xH+xH),paint);
                                canvas.drawBitmap(ta2c,null,new Rect((onX)*xW,onY*xH,(onX)*xW+xW,onY*xH+xH),paint);
                                canvas.drawBitmap(ta3c,null,new Rect((onX+1)*xW,onY*xH,(onX+1)*xW+xW,onY*xH+xH),paint);
                                showtamenu=true;
                            }
                        }
                        //画塔菜单和
//                    canvas.drawBitmap(ta1,null,new Rect((onX-1)*xW,onY*xH,(onX-1)*xW+xW,onY*xH+xH),paint);
//                    canvas.drawBitmap(ta2,null,new Rect((onX)*xW,onY*xH,(onX)*xW+xW,onY*xH+xH),paint);
//                    canvas.drawBitmap(ta3,null,new Rect((onX+1)*xW,onY*xH,(onX+1)*xW+xW,onY*xH+xH),paint);
//                    showtamenu=true;
                    }else//地图不是土地
                    {
                        if(showtamenu)
                        {
                            if(fangY(y)==onY&&Math.abs(fangX(x)-onX)<=1)//在菜单内
                            {
                                if(fangX(x)==onX)
                                {
                                    if(!qianbugou(JL2))//钱够用了
                                    {
                                        map2[fangY(y)][fangX(x)] = -2;//////这里修改
                                        Data.jianzhu();//放塔的声音
                                        Data.money -= JL2;
                                    }
                                }else if(fangX(x)==(onX-1))
                                {
                                    if(!qianbugou(JL1))//钱够用了
                                    {
                                        map2[fangY(y)][fangX(x) + 1] = -1;
                                        Data.jianzhu();//放塔的声音
                                        Data.money -= JL1;
                                    }
                                }else if(fangX(x)==(onX+1))
                                {
                                    if(!qianbugou(JL3))//钱够用了
                                    {
                                        map2[fangY(y)][fangX(x) - 1] = -3;
                                        Data.jianzhu();//放塔的声音
                                        Data.money -= JL3;
                                    }
                                }
                                //减钱
                            }
                            showtamenu=false;
                        }
                    }
                }

                //endregion
                //region 绘制升级菜单

                if(showupdate)//有升级菜单
                {
                    if(onX==fangX(x))
                    {
                        // map1[onY][onX]=-111;
                        // Log.d("减钱",fangX(x)+"");
                        if(onY==(fangY(y)+1))//升级减钱
                        {

                            if(map2[onY][onX]==-1){if(!qianbugou(SJ1_11)) {map2[onY][onX]=-11;Data.money -= SJ1_11;Data.shengji();}}//减钱 如果钱不够就不升级 下同
                            else if(map2[onY][onX]==-11){if(!qianbugou(SJ11_111)) {map2[onY][onX]=-111;Data.money-=SJ11_111;Data.shengji();}}//减钱
                            else if(map2[onY][onX]==-111){map2[onY][onX]=-111;}//
                            else if(map2[onY][onX]==-2){if(!qianbugou(SJ2_22)) {map2[onY][onX]=-22;Data.money-=SJ2_22;Data.shengji();}}//减钱
                            else if(map2[onY][onX]==-22){if(!qianbugou(SJ22_222)) {map2[onY][onX]=-222;Data.money-=SJ22_222;Data.shengji();}}//减钱
                            else if(map2[onY][onX]==-222){map2[onY][onX]=-222;}//
                            else if(map2[onY][onX]==-3){if(!qianbugou(SJ3_33)) {map2[onY][onX]=-33;Data.money-=SJ3_33;Data.shengji();}}//减钱
                            else if(map2[onY][onX]==-33){if(!qianbugou(SJ33_333)) {map2[onY][onX]=-333;Data.money-=SJ33_333;Data.shengji();}}//减钱
                            else if(map2[onY][onX]==-333){map2[onY][onX]=-333;}//
                            showtamenu=false;
                            //showupdate=false;
                        }else if(onY==fangY(y))
                        {
                            showtamenu=false;
                            //showupdate=false;
                        }else if(onY==(fangY(y)-1))//删除加钱
                        {
                            if(map2[onY][onX]==-1){map2[onY][onX]=0;Data.money+=M1_0;Data.moneyjia();}//加钱
                            else if(map2[onY][onX]==-11){map2[onY][onX]=0;Data.money+=M11_0;Data.moneyjia();}
                            else if(map2[onY][onX]==-111){map2[onY][onX]=0;Data.money+=M111_0;Data.moneyjia();}
                            else if(map2[onY][onX]==-2){map2[onY][onX]=0;Data.money+=M2_0;Data.moneyjia();}
                            else if(map2[onY][onX]==-22){map2[onY][onX]=0;Data.money+=M22_0;Data.moneyjia();}
                            else if(map2[onY][onX]==-222){map2[onY][onX]=0;Data.money+=M222_0;Data.moneyjia();}
                            else if(map2[onY][onX]==-3){map2[onY][onX]=0;Data.money+=M3_0;Data.moneyjia();}
                            else if(map2[onY][onX]==-33){map2[onY][onX]=0;Data.money+=M33_0;Data.moneyjia();}
                            else if(map2[onY][onX]==-333){map2[onY][onX]=0;Data.money+=M333_0;Data.moneyjia();}
                            showtamenu=false;
                            // showupdate=false;
                        }
                    }
                }
                // if(!showupdate)
                if(!showtamenu)//没有选择菜单
                {

                    if(map2[fangY(y)][fangX(x)]<0&&!showupdate)//如果是塔楼
                    {
                        onX=fangX(x);
                        onY=fangY(y);
                        canvas.drawBitmap(update,null,new Rect((onX)*xW,(onY-1)*xH,(onX)*xW+xW,(onY-1)*xH+xH),paint);
                        canvas.drawBitmap(delete,null,new Rect((onX)*xW,(onY+1)*xH,(onX)*xW+xW,(onY+1)*xH+xH),paint);
                        canvas.drawCircle(onX*xW+xW/2,onY*xH+xH/2,xW*3,paint);
                        showupdate=true;
                    }else
                    {
                        showupdate=false;
                    }
                }else
                {
                    if(map2[fangY(y)][fangX(x)]<0&&fangY(y)!=onY&&Math.abs(onX-fangX(x))>1)//如果是塔楼并且不在菜单里面
                    {
                        onX=fangX(x);
                        onY=fangY(y);
                        paint.setColor(Color.BLACK);
                        canvas.drawBitmap(update,null,new Rect((onX)*xW,(onY-1)*xH,(onX)*xW+xW,(onY-1)*xH+xH),paint);
                        canvas.drawBitmap(delete,null,new Rect((onX)*xW,(onY+1)*xH,(onX)*xW+xW,(onY+1)*xH+xH),paint);
                        canvas.drawCircle(onX*xW+xW/2,onY*xH+xH/2,xW*3,paint);
                        showupdate=true;
                        showtamenu=false;
                    }else
                    {
                        showupdate=false;
                    }
                }
                for(int j = 0; j < 10; j++){
                    for(int i = 0; i < 16; i++){
                        Gwcreat.map[j][i] = map2[j][i];
                    }
                }


                //endregion
            }else
            {
                //region 绘制选塔菜单
                if(!showupdate||(showupdate&&(onX!=fangX(x)||Math.abs(onY-fangY(y))>1)))
                {
                    if(map3[fangY(y)][fangX(x)]==0)//如果可以画。注意这里横纵坐标竟然对调！！！
                    {
                        if(!showtamenu)//没有显示选塔菜单
                        {
                            onX=fangX(x);
                            onY=fangY(y);
                            canvas.drawBitmap(ta1c,null,new Rect((onX-1)*xW,onY*xH,(onX-1)*xW+xW,onY*xH+xH),paint);
                            canvas.drawBitmap(ta2c,null,new Rect((onX)*xW,onY*xH,(onX)*xW+xW,onY*xH+xH),paint);
                            canvas.drawBitmap(ta3c,null,new Rect((onX+1)*xW,onY*xH,(onX+1)*xW+xW,onY*xH+xH),paint);
                            showtamenu=true;
                        }else//有菜单
                        {
                            if(fangY(y)==onY&&Math.abs(fangX(x)-onX)<=1)//在菜单内
                            {
                                if(fangX(x)==onX)
                                {
                                    if(!qianbugou(JL2))//钱够用了
                                    {
                                        map3[fangY(y)][fangX(x)]=-2;//////这里修改  建立塔 减钱
                                        Data.jianzhu();//放塔的声音
                                        Data.money-=JL2;
                                    }
                                }else if(fangX(x)==(onX-1))
                                {
                                    if(!qianbugou(JL1))//钱够用了
                                    {
                                        map3[fangY(y)][fangX(x) + 1] = -1;
                                        Data.jianzhu();//放塔的声音
                                        Data.money -= JL1;
                                    }
                                }else if(fangX(x)==(onX+1))
                                {
                                    if(!qianbugou(JL3))//钱够用了
                                    {
                                        map3[fangY(y)][fangX(x) - 1] = -3;
                                        Data.jianzhu();//放塔的声音
                                        Data.money -= JL3;
                                    }
                                }
                                //减钱
                                showtamenu=false;
                            }else
                            {
                                onX=fangX(x);
                                onY=fangY(y);
                                canvas.drawBitmap(ta1c,null,new Rect((onX-1)*xW,onY*xH,(onX-1)*xW+xW,onY*xH+xH),paint);
                                canvas.drawBitmap(ta2c,null,new Rect((onX)*xW,onY*xH,(onX)*xW+xW,onY*xH+xH),paint);
                                canvas.drawBitmap(ta3c,null,new Rect((onX+1)*xW,onY*xH,(onX+1)*xW+xW,onY*xH+xH),paint);
                                showtamenu=true;
                            }
                        }
                        //画塔菜单和
//                    canvas.drawBitmap(ta1,null,new Rect((onX-1)*xW,onY*xH,(onX-1)*xW+xW,onY*xH+xH),paint);
//                    canvas.drawBitmap(ta2,null,new Rect((onX)*xW,onY*xH,(onX)*xW+xW,onY*xH+xH),paint);
//                    canvas.drawBitmap(ta3,null,new Rect((onX+1)*xW,onY*xH,(onX+1)*xW+xW,onY*xH+xH),paint);
//                    showtamenu=true;
                    }else//地图不是土地
                    {
                        if(showtamenu)
                        {
                            if(fangY(y)==onY&&Math.abs(fangX(x)-onX)<=1)//在菜单内
                            {
                                if(fangX(x)==onX)
                                {
                                    if(!qianbugou(JL2))//钱够用了
                                    {
                                        map3[fangY(y)][fangX(x)] = -2;//////这里修改
                                        Data.jianzhu();//放塔的声音
                                        Data.money -= JL2;
                                    }
                                }else if(fangX(x)==(onX-1))
                                {
                                    if(!qianbugou(JL1))//钱够用了
                                    {
                                        map3[fangY(y)][fangX(x) + 1] = -1;
                                        Data.jianzhu();//放塔的声音
                                        Data.money -= JL1;
                                    }
                                }else if(fangX(x)==(onX+1))
                                {
                                    if(!qianbugou(JL3))//钱够用了
                                    {
                                        map3[fangY(y)][fangX(x) - 1] = -3;
                                        Data.jianzhu();//放塔的声音
                                        Data.money -= JL3;
                                    }
                                }
                                //减钱
                            }
                            showtamenu=false;
                        }
                    }
                }

                //endregion
                //region 绘制升级菜单

                if(showupdate)//有升级菜单
                {
                    if(onX==fangX(x))
                    {
                        // map1[onY][onX]=-111;
                        // Log.d("减钱",fangX(x)+"");
                        if(onY==(fangY(y)+1))//升级减钱
                        {

                            if(map3[onY][onX]==-1){if(!qianbugou(SJ1_11)) {map3[onY][onX]=-11;Data.money -= SJ1_11;Data.shengji();}}//减钱 如果钱不够就不升级 下同
                            else if(map3[onY][onX]==-11){if(!qianbugou(SJ11_111)) {map3[onY][onX]=-111;Data.money-=SJ11_111;Data.shengji();}}//减钱
                            else if(map3[onY][onX]==-111){map3[onY][onX]=-111;}//
                            else if(map3[onY][onX]==-2){if(!qianbugou(SJ2_22)) {map3[onY][onX]=-22;Data.money-=SJ2_22;Data.shengji();}}//减钱
                            else if(map3[onY][onX]==-22){if(!qianbugou(SJ22_222)) {map3[onY][onX]=-222;Data.money-=SJ22_222;Data.shengji();}}//减钱
                            else if(map3[onY][onX]==-222){map3[onY][onX]=-222;}//
                            else if(map3[onY][onX]==-3){if(!qianbugou(SJ3_33)) {map3[onY][onX]=-33;Data.money-=SJ3_33;Data.shengji();}}//减钱
                            else if(map3[onY][onX]==-33){if(!qianbugou(SJ33_333)) {map3[onY][onX]=-333;Data.money-=SJ33_333;Data.shengji();}}//减钱
                            else if(map3[onY][onX]==-333){map3[onY][onX]=-333;}//
                            showtamenu=false;
                            //showupdate=false;
                        }else if(onY==fangY(y))
                        {
                            showtamenu=false;
                            //showupdate=false;
                        }else if(onY==(fangY(y)-1))//删除加钱
                        {
                            if(map3[onY][onX]==-1){map3[onY][onX]=0;Data.money+=M1_0;Data.moneyjia();}//加钱
                            else if(map3[onY][onX]==-11){map3[onY][onX]=0;Data.money+=M11_0;Data.moneyjia();}
                            else if(map3[onY][onX]==-111){map3[onY][onX]=0;Data.money+=M111_0;Data.moneyjia();}
                            else if(map3[onY][onX]==-2){map3[onY][onX]=0;Data.money+=M2_0;Data.moneyjia();}
                            else if(map3[onY][onX]==-22){map3[onY][onX]=0;Data.money+=M22_0;Data.moneyjia();}
                            else if(map3[onY][onX]==-222){map3[onY][onX]=0;Data.money+=M222_0;Data.moneyjia();}
                            else if(map3[onY][onX]==-3){map3[onY][onX]=0;Data.money+=M3_0;Data.moneyjia();}
                            else if(map3[onY][onX]==-33){map3[onY][onX]=0;Data.money+=M33_0;Data.moneyjia();}
                            else if(map3[onY][onX]==-333){map3[onY][onX]=0;Data.money+=M333_0;Data.moneyjia();}
                            showtamenu=false;
                            // showupdate=false;
                        }
                    }
                }
                // if(!showupdate)
                if(!showtamenu)//没有选择菜单
                {

                    if(map3[fangY(y)][fangX(x)]<0&&!showupdate)//如果是塔楼
                    {
                        onX=fangX(x);
                        onY=fangY(y);
                        canvas.drawBitmap(update,null,new Rect((onX)*xW,(onY-1)*xH,(onX)*xW+xW,(onY-1)*xH+xH),paint);
                        canvas.drawBitmap(delete,null,new Rect((onX)*xW,(onY+1)*xH,(onX)*xW+xW,(onY+1)*xH+xH),paint);
                        canvas.drawCircle(onX*xW+xW/2,onY*xH+xH/2,xW*3,paint);
                        showupdate=true;
                    }else
                    {
                        showupdate=false;
                    }
                }else
                {
                    if(map3[fangY(y)][fangX(x)]<0&&fangY(y)!=onY&&Math.abs(onX-fangX(x))>1)//如果是塔楼并且不在菜单里面
                    {
                        onX=fangX(x);
                        onY=fangY(y);
                        paint.setColor(Color.BLACK);
                        canvas.drawBitmap(update,null,new Rect((onX)*xW,(onY-1)*xH,(onX)*xW+xW,(onY-1)*xH+xH),paint);
                        canvas.drawBitmap(delete,null,new Rect((onX)*xW,(onY+1)*xH,(onX)*xW+xW,(onY+1)*xH+xH),paint);
                        canvas.drawCircle(onX*xW+xW/2,onY*xH+xH/2,xW*3,paint);
                        showupdate=true;
                        showtamenu=false;
                    }else
                    {
                        showupdate=false;
                    }
                }
                for(int j = 0; j < 10; j++){
                    for(int i = 0; i < 16; i++){
                        Gwcreat.map[j][i] = map3[j][i];
                    }
                }


                //endregion
            }
        }else//否则点击的是第一行
        {
            if(fangX(x)==backX&&fangY(y)==backY)//点击返回按键
            {
                Data.anniu();
                Data.beijing();
                Data.gamepause();
                mysurfaceview.setselectgame();
               // mysurfaceview.TouchListener();
                //设置gamestate为选择
            }
            if((fangX(x)==pauseX||fangX(x)==(pauseX+1))&&fangY(y)==pauseY)//点击暂停或返回
            {

                if(pause)
                {
                    Data.gamestart();
                    pause=false;
                   guaiwucreat.Resume();

                }else
                {
                    Data.gamepause();
                    pause=true;
                   guaiwucreat.Pause();
                }
            }
        }

        if(num==1) {guaiwucreat.setmap(map1);}//更新信息
        else if(num==2){guaiwucreat.setmap(map2);}
        else {guaiwucreat.setmap(map3);}
//        Log.d("fx",fangX(x)+"");
//        Log.d("fy",fangY(y)+"");
    }
    private int fangX(int x)//返回x,y所在的方块的横坐标
    {
        return x/xW;
    }
    private int fangY(int y)//返回x,y所在的方块的纵坐标
    {
      return y/xH;
    }
    private boolean qianbugou(int costmoney)
    {
        if(Data.money-costmoney<0)//钱不够用
        {
            Paint textPaint=new Paint();
            textPaint.setColor(Color.RED);
            textPaint.setTextSize(xH/2);
            this.tishinum=tishinumzong;
            this.tishimoney=costmoney;
            return true;
        }else//钱够用
        {
            return false;
        }

    }

}
