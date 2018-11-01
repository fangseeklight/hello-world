package special.general.other;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import special.general.LoginActivity;
import special.general.R;

/**
 * Created by 郭昊然 on 2016/5/22.
 * 怪物创建爱你类，通过这个类控制怪物的出现顺序，出现个数，出现的波数，出现的位置
 * 不同地图出现总波数为5,10,15
 */
public class Gwcreat{//} implements Runnable{

    //自己设置的变量
    private int xW,xH;//小图的宽和高
    private int timejiange=3200;//时间间隔
    private int gwjiange=30;//guaiwu间隔
    private int numofgwalive=0;//活着的怪物的个数
    private int zheyibowan=1;//这一波完了吗？1完了 0没玩
    private Resources resources;
    private Guaiwu[] guaiwu1=new Guaiwu[5];//每次出现的怪物数量
//    private Guaiwu[] guaiwu2=new Guaiwu[5];//每次出现的怪物数量
//    private Guaiwu[] guaiwu3=new Guaiwu[5];//每次出现的怪物数量

    AImonster opAI = new AImonster();
    private Bitmap jianke;
    private int numofgw=0;//定义当前怪物的个数
    public static int[][] map;
    private int map123;//第几幅地图
    private int numofwave;//波总数 分别为5,10,15
    private int nowwave=0;//当前波数
    private int sX,sY;//开始的坐标
    private Canvas canvas;
    private Paint paint;
    private int time=0;
    private int timePause = 0;
    private int win=0;//赢了么？ 赢了1 在玩0 输了-1
    //private Thread thread;
    private boolean pause=false;//怪物暂停出现
    public Gwcreat(){}
    public Gwcreat(Context context,int[][] map, int map123, Resources resources, Canvas canvas, Paint paint, int numofwave, int sX, int sY, int xW, int xH)
    {
        this.numofwave=numofwave;//总波数
        this.sX=sX;
        this.sY=sY;
        this.xW=xW;
        this.xH=xH;
        this.canvas=canvas;
        this.paint=paint;
        this.resources=resources;
        this.map123=map123;
        this.map=map;


        if(map123==1)
        {
            this.jianke=BitmapFactory.decodeResource(resources,R.drawable.guaiwu1);
        }else if(map123==2)
        {
            this.jianke=BitmapFactory.decodeResource(resources,R.drawable.guaiwu2);
            this.timejiange=1500;
        }else
        {
            this.jianke=BitmapFactory.decodeResource(resources,R.drawable.guaiwu3);
            this.timejiange=1600;
        }

//        for(int i=0;i<5;i++)
//        {
//            guaiwu1[i]=new Guaiwu(1,resources,xW,xH);
//            guaiwu1[i].setPosition_x(xW*0); guaiwu1[i].setPosition_y(xH*4);//设置开始坐标
////            guaiwu2[i]=new Guaiwu(2,resources,xW,xH);
////            guaiwu2[i].setPosition_x(xW*0); guaiwu1[i].setPosition_y(xH*2);
////            guaiwu3[i]=new Guaiwu(3,resources,xW,xH);
////            guaiwu3[i].setPosition_x(xW*0); guaiwu1[i].setPosition_y(xH*6);
//        }

       // thread=new Thread(this);

    }
    public void setmap(int [][] map)
    {
        this.map=map;
    }
    public void newguaiwu(int map123,int nowwave,int numofgw)//第map123幅地图（不同地图初始坐标不同，1为0,4 2为0,1 3为0,5）；当前第几波怪物;new 一个第几个怪物
    {
      //  guaiwu1[numofgw]=new Guaiwu(2,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*4);//break;//设置开始坐标0,4
        if(map123==1)
        {
            switch(nowwave)//当前为第nowwave波怪物
            {
                case 0:
                    /*
                    System.out.println("Wave one");
                    for(int j = 0; j < 10; j++){
                        for(int i = 0; i < 16; i++){
                            System.out.print(map[j][i]+"  ");
                        }
                        System.out.println(" ");
                    }
                    */
                    guaiwu1[numofgw]=new Guaiwu(map,1,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*4);break;//设置开始坐标0,4

                case 1: guaiwu1[numofgw]=new Guaiwu(map,1,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*4);break;//设置开始坐标0,4
                case 2:guaiwu1[numofgw]=new Guaiwu(map,numofgw%2+1,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*4);break;//设置开始坐标0,4
                case 3:guaiwu1[numofgw]=new Guaiwu(map,numofgw%2+1,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*4);break;//设置开始坐标0,4
                case 4:guaiwu1[numofgw]=new Guaiwu(map,numofgw%3+1,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*4);break;//设置开始坐标0,4
            }

        }else if(map123==2)
        {
            switch(nowwave)//当前为第nowwave波怪物
            {
                case 0:guaiwu1[numofgw]=new Guaiwu(map,numofgw%2+1,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*2);break;//设置开始坐标0,2
                case 1:guaiwu1[numofgw]=new Guaiwu(map,numofgw%2+1,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*2);break;//设置开始坐标0,2
                case 2:guaiwu1[numofgw]=new Guaiwu(map,numofgw%3+1,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*2);break;//设置开始坐标0,2
                case 3:guaiwu1[numofgw]=new Guaiwu(map,numofgw%2+2,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*2);break;//设置开始坐标0,2
                case 4:guaiwu1[numofgw]=new Guaiwu(map,numofgw%2+2,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*2);break;//设置开始坐标0,2
                case 5:guaiwu1[numofgw]=new Guaiwu(map,numofgw%3+1,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*2);break;//设置开始坐标0,2
                case 6:guaiwu1[numofgw]=new Guaiwu(map,numofgw%3+1,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*2);break;//设置开始坐标0,2
                case 7:guaiwu1[numofgw]=new Guaiwu(map,numofgw%2+2,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*2);break;//设置开始坐标0,2
                case 8:guaiwu1[numofgw]=new Guaiwu(map,3,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*2);break;//设置开始坐标0,2
                case 9:guaiwu1[numofgw]=new Guaiwu(map,3,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*2);break;//设置开始坐标0,2
            }
        }else
        {
            switch(nowwave)//当前为第nowwave波怪物
            {
                case 0:guaiwu1[numofgw]=new Guaiwu(map,numofgw%2,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*6);break;//设置开始坐标0,6
                case 1:guaiwu1[numofgw]=new Guaiwu(map,numofgw%2,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*6);break;//设置开始坐标0,6
                case 2:guaiwu1[numofgw]=new Guaiwu(map,numofgw%3,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*6);break;//设置开始坐标0,6
                case 3:guaiwu1[numofgw]=new Guaiwu(map,numofgw%2+1,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*6);break;//设置开始坐标0,6
                case 4:guaiwu1[numofgw]=new Guaiwu(map,numofgw%2+1,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*6);break;//设置开始坐标0,6
                case 5:guaiwu1[numofgw]=new Guaiwu(map,numofgw%3,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*6);break;//设置开始坐标0,6
                case 6:guaiwu1[numofgw]=new Guaiwu(map,numofgw%3,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*6);break;//设置开始坐标0,6
                case 7:guaiwu1[numofgw]=new Guaiwu(map,numofgw%2+1,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*6);break;//设置开始坐标0,6
                case 8:guaiwu1[numofgw]=new Guaiwu(map,3,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*6);break;//设置开始坐标0,6
                case 9:guaiwu1[numofgw]=new Guaiwu(map,3,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*6);break;//设置开始坐标0,6
                case 10:guaiwu1[numofgw]=new Guaiwu(map,numofgw%3,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*6);break;//设置开始坐标0,6
                case 11:guaiwu1[numofgw]=new Guaiwu(map,numofgw%2+1,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*6);break;//设置开始坐标0,6
                case 12:guaiwu1[numofgw]=new Guaiwu(map,3,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*6);break;//设置开始坐标0,6
                case 13:guaiwu1[numofgw]=new Guaiwu(map,3,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*6);break;//设置开始坐标06
                case 14:guaiwu1[numofgw]=new Guaiwu(map,3,resources,xW,xH);guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*6);break;//设置开始坐标0,6
            }
         }
//        for(int i=0;i<5;i++)
//        {
//            guaiwu1[i]=new Guaiwu(1,resources,xW,xH);
//            guaiwu1[i].setPosition_x(xW*0); guaiwu1[i].setPosition_y(xH*4);//设置开始坐标
//            guaiwu2[i]=new Guaiwu(2,resources,xW,xH);
//            guaiwu2[i].setPosition_x(xW*0); guaiwu1[i].setPosition_y(xH*2);
//            guaiwu3[i]=new Guaiwu(3,resources,xW,xH);
//            guaiwu3[i].setPosition_x(xW*0); guaiwu1[i].setPosition_y(xH*6);
//        }
    }
    public void start() throws InterruptedException//启动绘制
    {
       // thread.start();
//        guaiwu2[0]=new Guaiwu(2,resources,xW,xH);
//        guaiwu2[0].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*4);//设置开始坐标
//        guaiwu2[0].drawgw(canvas,paint);
        Paint textPaint=new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(xH/2);

        canvas.drawText(""+(timejiange-time%timejiange),sY*xH,(sX-1)*xW,textPaint);//绘制下一波还剩多少时间

//        textPaint.setTextSize(xH*2);
//        textPaint.setColor(Color.BLUE);
//        canvas.drawText("你赢了！！！",4*xH,5*xW,textPaint);
        if(nowwave>=numofwave&&win!=-1)//当前波数大于总波数，且没输说明执行完毕
        {
            textPaint.setTextSize(xH*2);
            textPaint.setColor(Color.BLUE);
            win=1;
            canvas.drawText("你赢了！！！",4*xH,5*xW,textPaint);
            pause=true;
            //跳转到结束页
            //赢了这一关
        }
        if(win==-1)
        {
            textPaint.setTextSize(xH*2);
            textPaint.setColor(Color.RED);
            canvas.drawText("你输了！！！",4*xH,5*xW,textPaint);
        }
        if(map123==1)
        {
            int dou=0;
           // pause=true;
            if(timejiange-time%timejiange<530)
            {
               dou=4*(time%4);
            }
            canvas.drawBitmap(jianke,null,new Rect(13*xW+dou,(int)((6+(1-Data.jk1bloodnow*1.0/Data.jianke1blood))*xH),16*xW+dou,9*xH),paint);//剑客位置
            textPaint.setTextSize(xH/2);
            canvas.drawText("❤×"+Data.jk1bloodnow,14*xW,6*xH,textPaint);//绘制下一波还剩多少时间
            canvas.drawText("No"+(nowwave+1)+"wave of "+numofwave+"waves",6*xW,1*xH,textPaint);//绘制第几波
        }else if(map123==2)
        {
            int dou=0;
            // pause=true;
            if(timejiange-time%timejiange<530)
            {
                dou=4*(time%4);
            }
            canvas.drawBitmap(jianke,null,new Rect(13*xW+dou,(int)((6+(1-Data.jk2bloodnow*1.0/Data.jianke2blood))*xH),16*xW+dou,9*xH),paint);//剑客位置
            textPaint.setTextSize(xH/2);
            canvas.drawText("❤×"+Data.jk2bloodnow,14*xW,6*xH,textPaint);//绘制下一波还剩多少时间
            canvas.drawText("No"+(nowwave+1)+"wave of "+numofwave+"waves",6*xW,1*xH,textPaint);//绘制第几波
        }else
        {
            int dou=0;
            // pause=true;
            if(timejiange-time%timejiange<530)
            {
                dou=4*(time%4);
            }
            canvas.drawBitmap(jianke,null,new Rect(13*xW+dou,(int)((6+(1-Data.jk3bloodnow*1.0/Data.jianke3blood))*xH),16*xW+dou,9*xH),paint);//剑客位置
            textPaint.setTextSize(xH/2);
            canvas.drawText("❤×"+Data.jk3bloodnow,14*xW,6*xH,textPaint);//绘制下一波还剩多少时间
            canvas.drawText("No"+(nowwave+1)+"wave of "+numofwave+"waves",6*xW,1*xH,textPaint);//绘制第几波
            canvas.drawText("score:"+ Data.score,11*xW,1*xH,textPaint);//绘制第几波
        }
       if(!pause)
        {//逻辑
            time++;
            timePause++;
            numofgwalive=0;
            for(int x=0;x<5;x++)
            {
                if(guaiwu1[x]!=null)
                {
                    if(guaiwu1[x].isalive())
                    {
                        numofgwalive++;
                        zheyibowan=0;
                    }
                }
            }
          // canvas.drawText(zheyibowan+"¥"+numofgwalive,2*xW,2*xH,textPaint);//绘制金钱

            if(zheyibowan==0)//如果有怪物，不管死活，这一波没玩
            {
                if(numofgwalive<=0)//如果活着的怪物为0  （没有活着的怪物了）那么这一波完了 初始时间
                {
                    time=timejiange-100;//100为初始时间的长短
                    zheyibowan=1;

                    if(Data.score > LoginActivity.currentUser.userScore) {
                        DatabaseReference mDatabase;
                        mDatabase = FirebaseDatabase.getInstance().getReference();


                        Map userUpdate = new HashMap();//(currentId,currentUser.userName,currentUser.userPassword,currentUser.email,currentUser.userScore,currentUser.userEnergy-20);
                        userUpdate.put("email", LoginActivity.currentUser.email);
                        userUpdate.put("userEnergy", LoginActivity.currentUser.userEnergy);
                        userUpdate.put("userId", LoginActivity.currentUser.userId);
                        userUpdate.put("userName", LoginActivity.currentUser.userName);
                        userUpdate.put("userPassword", LoginActivity.currentUser.userPassword);
                        userUpdate.put("userScore", Data.score);
                        Map childUpdates = new HashMap();
                        childUpdates.put("/users/" + LoginActivity.currentUser.userId, userUpdate);
                        mDatabase.updateChildren(childUpdates);
                    }
                }
            }

            if(time%timejiange==0&&time>0)
            {
                if(map123 == 1){
                    for(int j = 0; j < 10; j++){
                        for(int i = 0; i < 16; i++){
                            map[j][i] = MyMap.map1[j][i];
                        }
                    }
                }else if(map123 == 2){
                    for(int j = 0; j < 10; j++){
                        for(int i = 0; i < 16; i++){
                            map[j][i] = MyMap.map2[j][i];
                        }
                    }
                }else if(map123 == 3){
                    for(int j = 0; j < 10; j++){
                        for(int i = 0; i < 16; i++){
                            map[j][i] = MyMap.map3[j][i];
                        }
                    }
                }
                int[][] map11 = new int[10][16];
                for(int j = 0; j < 10; j++){
                    for(int i = 0; i < 16; i++){
                        map11[j][i] = map[j][i];
                        System.out.print(map11[j][i]+ ", ");
                    }
                    System.out.println("");
                }
                map11 = opAI.initialStart(map11,map123-1);
                System.out.println("working time");
                /*
                for(int j = 0; j < 10; j++){
                    for(int i = 0; i < 16; i++){
                        map[j][i] = map11[j][i];
                        System.out.print(map[j][i]+"  ");
                    }
                    System.out.println(" ");
                }
                */
                if(map123 == 1){
                    for(int j = 0; j < 10; j++){
                        for(int i = 0; i < 16; i++){
                            MyMap.map1[j][i] = map11[j][i];
                        }
                    }
                }else if(map123 == 2){
                    for(int j = 0; j < 10; j++){
                        for(int i = 0; i < 16; i++){
                            MyMap.map2[j][i] = map11[j][i];
                        }
                    }
                }else if(map123 == 3){
                    for(int j = 0; j < 10; j++){
                        for(int i = 0; i < 16; i++){
                            MyMap.map3[j][i] = map11[j][i];
                        }
                    }
                }
                nowwave++;//波数增加
                numofgw=0;
            }
            if(numofgw<5)
            {
                if(time%gwjiange==0&&time>0)
                {
//                    guaiwu1[numofgw]=new Guaiwu(2,resources,xW,xH);
//                    guaiwu1[numofgw].setPosition_x(xW*0); guaiwu1[numofgw].setPosition_y(xH*4);//设置开始坐标
                    if(map123==1)
                    {
                        newguaiwu(1,nowwave,numofgw);
                    }else if(map123==2)
                    {
                        newguaiwu(2,nowwave,numofgw);
                    }else
                    {
                        newguaiwu(3,nowwave,numofgw);
                    }
                    numofgw++;
                }
            }
            //region 绘制怪物 运动绘制  后来想到不用那么多guaiwu1 guaiwu2 所以就用guaiwu1代替所有怪物 不改了
            for(int i=0;i<=numofgw;i++)//绘制怪物
            {
                if(guaiwu1[i].isalive())
                {
                    if( guaiwu1[i].isArrived())
                    {
                        if(map[fangY(guaiwu1[i].getPosition_y())][fangX(guaiwu1[i].getPosition_x())]==2)//向下运动
                        {
                            Guaiwu.sudu = 6;
                            guaiwu1[i].setFangxiang(0);
                            guaiwu1[i].setArrived(false);
                            guaiwu1[i].settoPositopn(zuoxiaX(fangX(guaiwu1[i].getPosition_x())),zuoxiaY(fangY(guaiwu1[i].getPosition_y())+1));//新目的地
                      //      System.out.println("fangY :"+fangY(guaiwu1[i].getPosition_y())+" fangX : "+ fangX(guaiwu1[i].getPosition_x()));
                        }else
                        if(map[fangY(guaiwu1[i].getPosition_y())][fangX(guaiwu1[i].getPosition_x())]==4)//向上运动
                        {
                            Guaiwu.sudu = 6;
                            guaiwu1[i].setFangxiang(3);
                            guaiwu1[i].setArrived(false);
                            guaiwu1[i].settoPositopn(zuoxiaX(fangX(guaiwu1[i].getPosition_x())),zuoxiaY(fangY(guaiwu1[i].getPosition_y())-1));//新目的地
                        }else if(map[fangY(guaiwu1[i].getPosition_y())][fangX(guaiwu1[i].getPosition_x())]==1 )//向右运动
                        {
                            Guaiwu.sudu = 6;
                            guaiwu1[i].setFangxiang(2);
                            guaiwu1[i].setArrived(false);
                            guaiwu1[i].settoPositopn(zuoxiaX(fangX(guaiwu1[i].getPosition_x())+1),zuoxiaY(fangY(guaiwu1[i].getPosition_y())));//新目的地
                        }else if( map[fangY(guaiwu1[i].getPosition_y())][fangX(guaiwu1[i].getPosition_x())]==3 )//向左运动
                        {
                            Guaiwu.sudu = 6;
                            guaiwu1[i].setFangxiang(1);
                            guaiwu1[i].setArrived(false);
                            guaiwu1[i].settoPositopn(zuoxiaX(fangX(guaiwu1[i].getPosition_x())-1),zuoxiaY(fangY(guaiwu1[i].getPosition_y())));//新目的地
                     //       System.out.println("fangY :"+fangY(guaiwu1[i].getPosition_y())+" fangX : "+ fangX(guaiwu1[i].getPosition_x()));
                        }else if( map[fangY(guaiwu1[i].getPosition_y())][fangX(guaiwu1[i].getPosition_x())]==-1||
                                map[fangY(guaiwu1[i].getPosition_y())][fangX(guaiwu1[i].getPosition_x())]==-11||
                                map[fangY(guaiwu1[i].getPosition_y())][fangX(guaiwu1[i].getPosition_x())]==-111||
                                map[fangY(guaiwu1[i].getPosition_y())][fangX(guaiwu1[i].getPosition_x())]==-2||
                                map[fangY(guaiwu1[i].getPosition_y())][fangX(guaiwu1[i].getPosition_x())]==-22||
                                map[fangY(guaiwu1[i].getPosition_y())][fangX(guaiwu1[i].getPosition_x())]==-222||
                                map[fangY(guaiwu1[i].getPosition_y())][fangX(guaiwu1[i].getPosition_x())]==-3||
                                map[fangY(guaiwu1[i].getPosition_y())][fangX(guaiwu1[i].getPosition_x())]==-33||
                                map[fangY(guaiwu1[i].getPosition_y())][fangX(guaiwu1[i].getPosition_x())]==-333
                                )// pause for two seconds
                        {
                            Guaiwu.sudu = 2;
                            /*
                            try {
                                //thread to sleep for the specified number of milliseconds
                                Thread.sleep(2000);
                            } catch ( java.lang.InterruptedException ie) {
                                System.out.println(ie);
                                System.out.println("暂停失败");
                            }
                            */
                          //  timePause;
                            guaiwu1[i].setArrived(false);
                            if(guaiwu1[i].getFangxiang()==0)//向下走
                            {
                                guaiwu1[i].settoPositopn(zuoxiaX(fangX(guaiwu1[i].getPosition_x())),zuoxiaY(fangY(guaiwu1[i].getPosition_y())+1));//新目的地
                            }else if(guaiwu1[i].getFangxiang()==3)//向上走
                            {
                                guaiwu1[i].settoPositopn(zuoxiaX(fangX(guaiwu1[i].getPosition_x())),zuoxiaY(fangY(guaiwu1[i].getPosition_y())-1));//新目的地
                            }else if(guaiwu1[i].getFangxiang()==1)//向左走
                            {
                                guaiwu1[i].settoPositopn(zuoxiaX(fangX(guaiwu1[i].getPosition_x())-1),zuoxiaY(fangY(guaiwu1[i].getPosition_y())));//新目的地
                            }else//向右走
                            {
                                guaiwu1[i].settoPositopn(zuoxiaX(fangX(guaiwu1[i].getPosition_x())+1),zuoxiaY(fangY(guaiwu1[i].getPosition_y())));//新目的地
                            }
                        }else
                        {
                            Guaiwu.sudu = 6;
                            guaiwu1[i].setArrived(false);
                            if(guaiwu1[i].getFangxiang()==0)//向下走
                            {
                                guaiwu1[i].settoPositopn(zuoxiaX(fangX(guaiwu1[i].getPosition_x())),zuoxiaY(fangY(guaiwu1[i].getPosition_y())+1));//新目的地
                            }else if(guaiwu1[i].getFangxiang()==3)//向上走
                            {
                                guaiwu1[i].settoPositopn(zuoxiaX(fangX(guaiwu1[i].getPosition_x())),zuoxiaY(fangY(guaiwu1[i].getPosition_y())-1));//新目的地
                            }else if(guaiwu1[i].getFangxiang()==1)//向左走
                            {
                                guaiwu1[i].settoPositopn(zuoxiaX(fangX(guaiwu1[i].getPosition_x())-1),zuoxiaY(fangY(guaiwu1[i].getPosition_y())));//新目的地
                            }else//向右走
                            {
                                guaiwu1[i].settoPositopn(zuoxiaX(fangX(guaiwu1[i].getPosition_x())+1),zuoxiaY(fangY(guaiwu1[i].getPosition_y())));//新目的地
                            }

                        }

                        if(map[fangY(guaiwu1[i].getPosition_y())][fangX(guaiwu1[i].getPosition_x())]==0)//走到草地上就死了 不测试说明
                        {
                            guaiwu1[i].gotodeath();

                            if(map123==1)
                            {
                                Data.jk1bloodnow-=1;
                                Data.diaoxue();//吼叫一声
                                if(Data.jk1bloodnow==0)
                                {
                                    win=-1;
                                    nowwave=5;
                                    //gameover 输了
                                }
                            }else if(map123==2)
                            {
                                Data.jk2bloodnow-=1;
                                Data.diaoxue();//吼叫一声
                                if(Data.jk2bloodnow==0)
                                {
                                    win=-1;
                                    nowwave=5;
                                    //gameover 输了
                                }
                            }else
                            {
                                Data.jk3bloodnow-=1;
                                Data.diaoxue();//吼叫一声
                                if(Data.jk3bloodnow==0)
                                {
                                    win=-1;
                                    nowwave=5;
                                    //gameover 输了
                                }
                            }

                        }
                        // guaiwu1[0].drawgw(canvas,paint);
                    }
                    guaiwu1[i].drawgw(canvas,paint);
                    guaiwu1[i].setmap(map);
                }
            }
            //endregion
//            guaiwu1[0].drawgw(canvas,paint);
//            guaiwu1[1].drawgw(canvas,paint);
//            guaiwu1[2].drawgw(canvas,paint);
//            guaiwu1[3].drawgw(canvas,paint);
//            guaiwu1[4].drawgw(canvas,paint);

//            gw1= BitmapFactory.decodeResource(resources, R.drawable.jingling1);
//            jl1[i][j]= Bitmap.createBitmap(gw1,j*gw1W/4,i*gw1H/4,gw1W/4,gw1H/4);
//      canvas.drawBitmap(jl1[fangxiang][(int)dangqianzhen],null,new Rect(position_x,position_y+gwH,position_x+gwW,position_y),paint);
           // guaiwu1[numofgw].setArrived(false);
           // guaiwu1[numofgw].settoPositopn(200,200);

            //guaiwu1[numofgw].drawtest(canvas,paint);

        }else//否则按了暂停键
       {

       }

    }
    public int[][] getMap(){
        return map;
    }
    private void drawguaiwu()
    {

    }
    public void Pause()//设置暂停
    {
       this.pause=true;
    }
    public void Resume()//继续游戏
    {
        this.pause=false;
    }
    private int fangX(int x)//返回x,y所在的方块的横坐标
    {
        return (x+xW/5)/xW;
    }
    private int fangY(int y)//返回x,y所在的方块的纵坐标
    {
        return (y+xH/5)/xH-1;
    }
    private int zuoxiaX(int X)//根据所在方块 返回方块左下角横坐标
    {
        return (X)*xW;
    }
    private int zuoxiaY(int Y)//根据所在方块 返回方块左下角纵坐标
    {
        return (Y+1)*xH;
    }

//    @Override
//    public void run() {
//        Log.d("!Pause","");
//        if(!pause)//如果没有暂停
//        {
//            Log.d("!Pause","");
//        }
//    }
}
