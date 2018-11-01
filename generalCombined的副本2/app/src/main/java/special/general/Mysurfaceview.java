package special.general;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import special.general.other.Gwcreat;
import special.general.other.Data;
import special.general.other.MyMap;


/**
 * Created by 郭昊然 on 2016/5/19.
 */
public class Mysurfaceview extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private MyMap mymap1,mymap2,mymap3;
    private SurfaceHolder sfh;//surfaceholder
    private Paint paint;//声明一个画笔
    private Thread thread;//声明一个线程
    private Canvas canvas;//声明一个画布
    private int ScreenH,ScreenW;//屏幕高和宽
    private boolean isRunning;//在运行的话为真
    private int gamestate;//当前游戏状态
    private final int index=-1;//主页面
    private final int setting=-2;//设置页面
    private final int selectgame=0;//选关界面
    private final int game1=1;//第一关
    private final int game2=2;//第二关
    private final int game3=3;//第三关
    private boolean startOK=false;//允许开始游戏？
    float pstX,pstY;//抬起的坐标

    //下面是自己修改的属性
    private int flashtime=50;//每隔flashtime时间刷新一次 单位 ms

    //region 要加载的图片和其他内容
    //一切坐标为比例或百分比形式，便于处理
    //region 主页图片
    private Bitmap indexback;//主页图片
    private Bitmap togame;//进入游戏图片
    private Bitmap togame1;//进入游戏图片按压
    private Bitmap tosetting;//进入设置图片
    private Bitmap tosetting1;//进入设置图片
    private int togameX=75;//进入游戏按钮横坐标 百分之90
    private int togameY=80;//进入游戏按钮纵坐标
    private int togameW=30;//进入游戏按钮宽
    private int togameH=15;//进入游戏按钮高
    private boolean ptogame=false;//按压进入按钮时为true
    private boolean ptosetting=false;//按压进入按钮时为true
    private int tosettingX=30;//进入游戏按钮横坐标
    private int tosettingY=80;//进入游戏按钮纵坐标
    private int tosettingW=30;//进入设置按钮横坐标
    private int tosettingH=15;//进入设置按钮纵坐标
    //endregion

    //region 设置页图片
    private Bitmap yinlaing;//主yinliang图片
    private Bitmap dd;//进入游戏图片
    //endregion

    //region 选择页图片
    private Bitmap selectback;//beijing 图片
    private Bitmap guaiwu1;
    private int guaiwu1X=20;//按钮横坐标 百分之
    private int guaiwu1Y=56;//按钮纵坐标
    private int guaiwu1W=25;//按钮宽
    private int guaiwu1H=45;//按钮高
    private boolean guaiwu1shake=false;//yaohuang
    private Bitmap guaiwu2;
    private int guaiwu2X=50;//按钮横坐标 百分之
    private int guaiwu2Y=55;//按钮纵坐标
    private int guaiwu2W=25;//按钮宽
    private int guaiwu2H=45;//按钮高
    private boolean guaiwu2shake=false;
    private Bitmap guaiwu3;
    private int guaiwu3X=80;//按钮横坐标 百分之
    private int guaiwu3Y=50;//按钮纵坐标
    private int guaiwu3W=40;//按钮宽
    private int guaiwu3H=50;//按钮高
    private boolean guaiwu3shake=false;
    private Bitmap shakebitmap;//暂存的抖动图画
    private int deegre;//当前旋转的角度
    private boolean shaketo=true;//抖动的方向
    private final int Shaketimezong=2;//总共抖动的次数u,下面也设置相同
    private int shaketime=2;//当前抖动第几次

    private Bitmap home;
    private Bitmap home1;
    private int homeX=8;//按钮横坐标 百分之
    private int homeY=6;//按钮纵坐标
    private int homeW=10;//按钮宽
    private int homeH=14;//按钮高
    private boolean phome=false;//按压home按钮时为true

    private Bitmap jianke1;
    private int jianke1X=20;
    private int jianke1Y=20;
    private int jianke1W=18;
    private int jianke1H=20;
    private Bitmap jianke2;
    private int jianke2X=50;
    private int jianke2Y=20;
    private int jianke2W=18;
    private int jianke2H=20;
    private Bitmap jianke3;
    private int jianke3X=80;
    private int jianke3Y=20;
    private int jianke3W=18;
    private int jianke3H=20;

    private Bitmap baowei1;//保卫图片
    private Bitmap baowei11;//保卫图片
    private int baowei1X=20;
    private int baowei1Y=90;
    private int baowei1W=18;
    private int baowei1H=13;
    private boolean pbaowei1=false;//按压baowei按钮时为true
    private Bitmap baowei2;
    private Bitmap baowei21;
    private int baowei2X=50;
    private int baowei2Y=90;
    private int baowei2W=18;
    private int baowei2H=13;
    private boolean pbaowei2=false;//按压baowei按钮时为true
    private Bitmap baowei3;
    private Bitmap baowei31;
    private int baowei3X=80;
    private int baowei3Y=90;
    private int baowei3W=18;
    private int baowei3H=13;
    private boolean pbaowei3=false;//按压baowei按钮时为true

    private int money=2000;//初始金钱
    //endregion

    //endregion
    Gwcreat opGwcreate = new Gwcreat();



    public Mysurfaceview(Context context)
    {
        super(context);
        sfh=this.getHolder();//实例化surfaceholder
        sfh.addCallback(this);//添加监听
        paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        //paint.setAntiAlias(true);
        paint.setStrokeWidth(4);
        paint.setColor(Color.WHITE);
        setFocusable(true);

//        soundPool=new SoundPool(4, AudioManager.STREAM_MUSIC,100);//最大同时播放几个文件，声音类型，声音品质
//        guai1=soundPool.load(context,R.raw.guai1,1);//实例 音乐文件ID，优先级
//        guai2=soundPool.load(context,R.raw.guai2,1);//实例 音乐文件ID，优先级
//        guai3=soundPool.load(context,R.raw.guai3,1);//实例 音乐文件ID，优先级
//        diaoxue=soundPool.load(context,R.raw.diaoxue,1);//实例 音乐文件ID，优先级
        Data.chaungjian(context);//创建声音
        this.gamestate=index;//进入页面设置为主页
        initializie();//初始化，加载文件进内存节省时间
        //放这里是否合适？还是放在creat里面？？？？
    }
    public void setselectgame()
    {
        this.gamestate=selectgame;
        TouchListener(selectgame);
    }
    public void initializie()//初始化
    {
        ScreenH=this.getHeight();//获取屏幕宽高
        ScreenW=this.getWidth();
        indexback= BitmapFactory.decodeResource(getResources(),R.drawable.indexback);
        togame= BitmapFactory.decodeResource(getResources(),R.drawable.togame);
        togame1= BitmapFactory.decodeResource(getResources(),R.drawable.togame1);
        tosetting= BitmapFactory.decodeResource(getResources(),R.drawable.tosetting);
        tosetting1= BitmapFactory.decodeResource(getResources(),R.drawable.tosetting1);
        home=BitmapFactory.decodeResource(getResources(),R.drawable.home);
        home1=BitmapFactory.decodeResource(getResources(),R.drawable.home1);
        selectback=BitmapFactory.decodeResource(getResources(),R.drawable.selectback);
        guaiwu1=BitmapFactory.decodeResource(getResources(),R.drawable.guaiwu1);
        guaiwu2=BitmapFactory.decodeResource(getResources(),R.drawable.guaiwu2);
        guaiwu3=BitmapFactory.decodeResource(getResources(),R.drawable.guaiwu3);
        jianke1=BitmapFactory.decodeResource(getResources(),R.drawable.jianke1);
        jianke2=BitmapFactory.decodeResource(getResources(),R.drawable.jianke2);
        jianke3=BitmapFactory.decodeResource(getResources(),R.drawable.jianke3);
        baowei1=BitmapFactory.decodeResource(getResources(),R.drawable.baowei1);
        baowei2=BitmapFactory.decodeResource(getResources(),R.drawable.baowei2);
        baowei3=BitmapFactory.decodeResource(getResources(),R.drawable.baowei3);
        baowei11=BitmapFactory.decodeResource(getResources(),R.drawable.baowei11);
        baowei21=BitmapFactory.decodeResource(getResources(),R.drawable.baowei21);
        baowei31=BitmapFactory.decodeResource(getResources(),R.drawable.baowei31);
        TouchListener(index);
        Data.beijing();
    }
    public void TouchListener(int gamestates)//屏幕监听直接集合到一个方法里面了
    {
        if(gamestates==index)
        {
            this.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //按下
                            //break;//不需要所以直接跳过，且执行下面的
                        case MotionEvent.ACTION_MOVE:
                            //移动
                            pstX=event.getX();
                            pstY=event.getY();
                            if(Math.abs(pstX-tosettingX*ScreenW/100)<ScreenW/10&&Math.abs(pstY-tosettingY*ScreenH/100)<ScreenH/10)//进入setting按钮
                            {ptosetting=true;
                            }else
                            {ptosetting=false;
                            }
                            if(Math.abs(pstX-togameX*ScreenW/100)<ScreenW/10&&Math.abs(pstY-togameY*ScreenH/100)<ScreenH/10)//进入togame按钮
                            {ptogame=true;

                            }else
                            {ptogame=false;

                            }

                            break;
                        case MotionEvent.ACTION_UP:
                            pstX=event.getX();
                            pstY=event.getY();
                            ptogame=false;
                            ptosetting=false;
                            if(Math.abs(pstX-tosettingX*ScreenW/100)<ScreenW/10&&Math.abs(pstY-tosettingY*ScreenH/100)<ScreenH/10)//离开setting按钮
                            {
                                gamestate=setting;
                                TouchListener(setting);
                            }
                            if(Math.abs(pstX-togameX*ScreenW/100)<ScreenW/10&&Math.abs(pstY-togameY*ScreenH/100)<ScreenH/10)//离开togame按钮
                            {
                                Data.anniu();
                                gamestate=selectgame;  //其他逻辑
                                TouchListener(selectgame);
                            }
                            break;
                    }
                    return true;
                }
            });
        }else if(gamestates==selectgame)
        {
            this.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //按下
                            pstX=event.getX();
                            pstY=event.getY();
                            if(Math.abs(pstX-guaiwu1X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-guaiwu1Y*ScreenH/100)<ScreenH/7)//进入guaiwu按钮
                            {guaiwu1shake=true;
                                Data.doudong1();
                            }
                            if(Math.abs(pstX-guaiwu2X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-guaiwu2Y*ScreenH/100)<ScreenH/7)//进入guaiwu按钮
                            {guaiwu2shake=true;
                                Data.doudong2();
                            }
                            if(Math.abs(pstX-guaiwu3X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-guaiwu3Y*ScreenH/100)<ScreenH/7)//进入guaiwu按钮
                            {guaiwu3shake=true;
                                Data.doudong3();
                            }
                            //break;//不需要所以直接跳过，且执行下面的
                        case MotionEvent.ACTION_MOVE:
                            //移动
                            pstX=event.getX();
                            pstY=event.getY();
                            if(Math.abs(pstX-homeX*ScreenW/100)<ScreenW/10&&Math.abs(pstY-homeY*ScreenH/100)<ScreenH/10)//进入home按钮
                            {phome=true;
                            }else
                            {phome=false;
                            }

                            if(Math.abs(pstX-baowei1X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-baowei1Y*ScreenH/100)<ScreenH/7)//进入baowei按钮
                            {pbaowei1=true;
                            }else
                            {pbaowei1=false;
                            }
                            if(Math.abs(pstX-baowei2X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-baowei2Y*ScreenH/100)<ScreenH/7)
                            {pbaowei2=true;
                            }else
                            {pbaowei2=false;
                            }
                            if(Math.abs(pstX-baowei3X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-baowei3Y*ScreenH/100)<ScreenH/7)
                            {pbaowei3=true;
                            }else
                            {pbaowei3=false;
                            }
                            break;
                        case MotionEvent.ACTION_UP://最重要
                            pstX=event.getX();
                            pstY=event.getY();
                            phome=false;
                            if(Math.abs(pstX-homeX*ScreenW/100)<ScreenW/10&&Math.abs(pstY-homeY*ScreenH/100)<ScreenH/10)//进入home按钮
                            {
                                Data.anniu();
                                gamestate=index;TouchListener(index);

                            }
                            pbaowei1=false;
                            pbaowei2=false;
                            pbaowei3=false;
//                        if(Math.abs(pstX-guaiwu1X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-guaiwu1Y*ScreenH/100)<ScreenH/7)//进入guaiwu按钮
//                        {
//                            if(startOK)
//                            {
//                                gamestate=game1;
//                                Log.d("Hh",ScreenH+"");
//                            }
//                        }
//                        if(Math.abs(pstX-guaiwu2X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-guaiwu2Y*ScreenH/100)<ScreenH/7)//进入guaiwu按钮
//                        {
//                            if(startOK)
//                            {
//                                gamestate=game2;
//                            }
//                        }
//                        if(Math.abs(pstX-guaiwu3X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-guaiwu3Y*ScreenH/100)<ScreenH/7)//进入guaiwu按钮
//                        {
//                            if(startOK)
//                            {
//                                gamestate=game3;
//                            }
//                        }

                            if(Math.abs(pstX-baowei1X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-baowei1Y*ScreenH/100)<ScreenH/7)//进入baowei按钮
                            {
                                Data.beijingpause();
                                gamestate=game1;
                                Data.anniu1();
                                Data.gamestart();
                                TouchListener(game1);
                            }
                            if(Math.abs(pstX-baowei2X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-baowei2Y*ScreenH/100)<ScreenH/7)
                            {
                                Data.beijingpause();
                                gamestate=game2;
                                Data.anniu1();
                                Data.gamestart();
                                TouchListener(game2);
                            }
                            if(Math.abs(pstX-baowei3X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-baowei3Y*ScreenH/100)<ScreenH/7)
                            {
                                Data.beijingpause();
                                gamestate=game3;
                                Data.anniu1();
                                Data.gamestart();
                                TouchListener(game3);
                            }
                            break;
                    }
                    return true;
                }
            });
        }
        else if(gamestates==game1)
        {
            this.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //按下
                            pstX=event.getX();
                            pstY=event.getY();
                            //mymap.pushdown((int)pstX,(int)pstY);
                            break;//不需要所以直接跳过，且执行下面的
                        case MotionEvent.ACTION_MOVE:
                            //移动
                            pstX=event.getX();
                            pstY=event.getY();
                            if(mymap1!=null)
                                mymap1.move((int)pstX,(int)pstY);
                            break;
                        case MotionEvent.ACTION_UP:
                            pstX=event.getX();
                            pstY=event.getY();
                            if(mymap1!=null)
                                mymap1.pushup((int)pstX,(int)pstY);
                            break;
                    }
                    for(int j = 0; j < 10; j++){
                        for(int i = 0; i < 16; i++){
                            Gwcreat.map[j][i] = MyMap.map1[j][i];
                        }
                    }
                    return true;
                }
            });
        }else if(gamestates==game2)
        {
            this.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //按下
                            pstX=event.getX();
                            pstY=event.getY();
                            //mymap.pushdown((int)pstX,(int)pstY);
                            break;//不需要所以直接跳过，且执行下面的
                        case MotionEvent.ACTION_MOVE:
                            //移动
                            pstX=event.getX();
                            pstY=event.getY();
                            if(mymap2!=null)
                                mymap2.move((int)pstX,(int)pstY);
                            break;
                        case MotionEvent.ACTION_UP:
                            pstX=event.getX();
                            pstY=event.getY();
                            if(mymap2!=null)
                                mymap2.pushup((int)pstX,(int)pstY);
                            break;
                    }
                    for(int j = 0; j < 10; j++){
                        for(int i = 0; i < 16; i++){
                            Gwcreat.map[j][i] = MyMap.map2[j][i];
                        }
                    }
                    return true;
                }
            });
        }else if(gamestates==game3)
        {
            this.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //按下
                            pstX=event.getX();
                            pstY=event.getY();
                            //mymap.pushdown((int)pstX,(int)pstY);
                            break;//不需要所以直接跳过，且执行下面的
                        case MotionEvent.ACTION_MOVE:
                            //移动
                            pstX=event.getX();
                            pstY=event.getY();
                            if(mymap3!=null)
                                mymap3.move((int)pstX,(int)pstY);
                            break;
                        case MotionEvent.ACTION_UP:
                            pstX=event.getX();
                            pstY=event.getY();
                            if(mymap3!=null)
                                mymap3.pushup((int)pstX,(int)pstY);
                            break;
                    }
                    for(int j = 0; j < 10; j++){
                        for(int i = 0; i < 16; i++){
                            Gwcreat.map[j][i] = MyMap.map3[j][i];
                        }
                    }
                    return true;
                }
            });
        }

//        else if(gamestates==game1||gamestates==game2||gamestates==game3)
//        {
//            this.setOnTouchListener(new OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    switch (event.getAction()) {
//                        case MotionEvent.ACTION_DOWN:
//                            //按下
//                            pstX=event.getX();
//                            pstY=event.getY();
//                            //mymap.pushdown((int)pstX,(int)pstY);
//                            break;//不需要所以直接跳过，且执行下面的
//                        case MotionEvent.ACTION_MOVE:
//                            //移动
//                            pstX=event.getX();
//                            pstY=event.getY();
//                            if(mymap!=null)
//                                mymap.move((int)pstX,(int)pstY);
//                            break;
//                        case MotionEvent.ACTION_UP:
//                            pstX=event.getX();
//                            pstY=event.getY();
//                            if(mymap!=null)
//                                mymap.pushup((int)pstX,(int)pstY);
//                            break;
//                    }
//                    return true;
//                }
//            });
//        }
    }
    public void mydraw()//主要逻辑 根据情况绘制各个页面
    {
        //        trycatch起来是因为为了线程的控制，
        //        防止重复新建一个新线程造成画面显
        //        示失误。

        try {
            canvas=sfh.lockCanvas();//锁定画布
            if(canvas!=null)
            {
                canvas.drawColor(Color.WHITE);//surfaceview用这种方法来刷屏

                switch(gamestate)
                {
                    case index:drawindex(canvas,paint);break;//主页面
                    case selectgame:drawselectgame(canvas,paint);break;//画选择关的界面
                    case setting:drawsetting(canvas,paint);break;
                    case game1:
                        drawgame(1);
                       // drawgame1(canvas,paint);
                        break;
                    case game2:
                        drawgame(2);
                       // drawgame2(canvas,paint);
                        break;
                    case game3:
                        drawgame(3);
                       // drawgame3(canvas,paint);
                        break;
                }
            }

        }catch(Exception e)
        {

        }finally {
            if(canvas!=null)
            {
                sfh.unlockCanvasAndPost(canvas);//解锁画布
            }
        }
    }

    public void drawindex(Canvas canvas,Paint paint)
    {

        canvas.drawBitmap(indexback,null,new Rect(0,0,ScreenW,ScreenH),paint);
        if(ptogame==false)//如果没有按压了togame按钮
        {canvas.drawBitmap(togame,null,new Rect((togameX-togameW/2)*ScreenW/100,(togameY-togameH/2)*ScreenH/100,(togameX+togameW/2)*ScreenW/100,(togameY+togameH/2)*ScreenH/100),paint);
        }else
        {canvas.drawBitmap(togame1,null,new Rect((togameX-togameW/2)*ScreenW/100,(togameY-togameH/2)*ScreenH/100,(togameX+togameW/2)*ScreenW/100,(togameY+togameH/2)*ScreenH/100),paint);
        }
        if(ptosetting==false)//如果没有按压了tosetting按钮
        {canvas.drawBitmap(tosetting,null,new Rect((tosettingX-tosettingW/2)*ScreenW/100,(tosettingY-tosettingH/2)*ScreenH/100,(tosettingX+tosettingW/2)*ScreenW/100,(tosettingY+tosettingH/2)*ScreenH/100),paint);
        }else
        {canvas.drawBitmap(tosetting1,null,new Rect((tosettingX-tosettingW/2)*ScreenW/100,(tosettingY-tosettingH/2)*ScreenH/100,(tosettingX+tosettingW/2)*ScreenW/100,(tosettingY+tosettingH/2)*ScreenH/100),paint);
         }
       // TouchListener(index);
//        this.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        //按下
//                        //break;//不需要所以直接跳过，且执行下面的
//                    case MotionEvent.ACTION_MOVE:
//                        //移动
//                        pstX=event.getX();
//                        pstY=event.getY();
//                        if(Math.abs(pstX-tosettingX*ScreenW/100)<ScreenW/10&&Math.abs(pstY-tosettingY*ScreenH/100)<ScreenH/10)//进入setting按钮
//                        {ptosetting=true;
//                        }else
//                        {ptosetting=false;
//                        }
//                        if(Math.abs(pstX-togameX*ScreenW/100)<ScreenW/10&&Math.abs(pstY-togameY*ScreenH/100)<ScreenH/10)//进入togame按钮
//                        {ptogame=true;
//                        }else
//                        {ptogame=false;
//                        }
//
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        pstX=event.getX();
//                        pstY=event.getY();
//                        ptogame=false;
//                        ptosetting=false;
//                        if(Math.abs(pstX-tosettingX*ScreenW/100)<ScreenW/10&&Math.abs(pstY-tosettingY*ScreenH/100)<ScreenH/10)//离开setting按钮
//                        {
//                            gamestate=setting;
//                        }
//                        if(Math.abs(pstX-togameX*ScreenW/100)<ScreenW/10&&Math.abs(pstY-togameY*ScreenH/100)<ScreenH/10)//离开togame按钮
//                        {
//                          gamestate=selectgame;  //其他逻辑
//                        }
//                        break;
//                }
//                return true;
//            }
//        });

    }
    public void drawselectgame(Canvas canvas,Paint paint)
    {
        Matrix matrix = new Matrix();
        canvas.drawBitmap(selectback,null,new Rect(0,0,ScreenW,ScreenH),paint);
        canvas.drawBitmap(jianke1,null,new Rect((jianke1X-jianke1W/2)*ScreenW/100,(jianke1Y-jianke1H/2)*ScreenH/100,(jianke1X+jianke1W/2)*ScreenW/100,(jianke1Y+jianke1H/2)*ScreenH/100),paint);
        canvas.drawBitmap(jianke2,null,new Rect((jianke2X-jianke2W/2)*ScreenW/100,(jianke2Y-jianke2H/2)*ScreenH/100,(jianke2X+jianke1W/2)*ScreenW/100,(jianke2Y+jianke2H/2)*ScreenH/100),paint);
        canvas.drawBitmap(jianke3,null,new Rect((jianke3X-jianke3W/2)*ScreenW/100,(jianke3Y-jianke3H/2)*ScreenH/100,(jianke3X+jianke3W/2)*ScreenW/100,(jianke3Y+jianke3H/2)*ScreenH/100),paint);

        //region 绘制怪物图标和怪物抖动
        if(guaiwu1shake==false)
        {
            canvas.drawBitmap(guaiwu1,null,new Rect((guaiwu1X-guaiwu1W/2)*ScreenW/100,(guaiwu1Y-guaiwu1H/2)*ScreenH/100,(guaiwu1X+guaiwu1W/2)*ScreenW/100,(guaiwu1Y+guaiwu1H/2)*ScreenH/100),paint);
        }else
        {
                    matrix.postRotate(deegre);
                    shakebitmap = Bitmap.createBitmap(guaiwu1,0,0,guaiwu1.getWidth(),guaiwu1.getHeight(),matrix, true);
                    canvas.drawBitmap(shakebitmap,null,new Rect((guaiwu1X-guaiwu1W/2)*ScreenW/100,(guaiwu1Y-guaiwu1H/2)*ScreenH/100,(guaiwu1X+guaiwu1W/2)*ScreenW/100,(guaiwu1Y+guaiwu1H/2)*ScreenH/100),paint);
        }
        if(guaiwu2shake==false)
        {
            canvas.drawBitmap(guaiwu2,null,new Rect((guaiwu2X-guaiwu2W/2)*ScreenW/100,(guaiwu2Y-guaiwu2H/2)*ScreenH/100,(guaiwu2X+guaiwu2W/2)*ScreenW/100,(guaiwu2Y+guaiwu2H/2)*ScreenH/100),paint);
        }else
        {
            matrix.postRotate(deegre);
            shakebitmap = Bitmap.createBitmap(guaiwu2,0,0,guaiwu2.getWidth(),guaiwu2.getHeight(),matrix, true);
            canvas.drawBitmap(shakebitmap,null,new Rect((guaiwu2X-guaiwu2W/2)*ScreenW/100,(guaiwu2Y-guaiwu2H/2)*ScreenH/100,(guaiwu2X+guaiwu2W/2)*ScreenW/100,(guaiwu2Y+guaiwu2H/2)*ScreenH/100),paint);
        }
        if(guaiwu3shake==false)
        {
            canvas.drawBitmap(guaiwu3,null,new Rect((guaiwu3X-guaiwu3W/2)*ScreenW/100,(guaiwu3Y-guaiwu3H/2)*ScreenH/100,(guaiwu3X+guaiwu3W/2)*ScreenW/100,(guaiwu3Y+guaiwu3H/2)*ScreenH/100),paint);
        }else
        {
            matrix.postRotate(deegre);
            shakebitmap = Bitmap.createBitmap(guaiwu3,0,0,guaiwu3.getWidth(),guaiwu3.getHeight(),matrix, true);
            canvas.drawBitmap(shakebitmap,null,new Rect((guaiwu3X-guaiwu3W/2)*ScreenW/100,(guaiwu3Y-guaiwu3H/2)*ScreenH/100,(guaiwu3X+guaiwu3W/2)*ScreenW/100,(guaiwu3Y+guaiwu3H/2)*ScreenH/100),paint);
        }
        //endregion
        //region 绘制home键图标
        if(phome==false)//如果没有按压了togame按钮
        {canvas.drawBitmap(home,null,new Rect((homeX-homeW/2)*ScreenW/100,(homeY-homeH/2)*ScreenH/100,(homeX+homeW/2)*ScreenW/100,(homeY+homeH/2)*ScreenH/100),paint);
        }else
        {canvas.drawBitmap(home1,null,new Rect((homeX-homeW/2)*ScreenW/100,(homeY-homeH/2)*ScreenH/100,(homeX+homeW/2)*ScreenW/100,(homeY+homeH/2)*ScreenH/100),paint);
        }
        //endregion
        //region 绘制保卫他图标
        if(!pbaowei1)//不按着保卫1
        {
            canvas.drawBitmap(baowei1,null,new Rect((baowei1X-baowei1W/2)*ScreenW/100,(baowei1Y-baowei1H/2)*ScreenH/100,(baowei1X+baowei1W/2)*ScreenW/100,(baowei1Y+baowei1H/2)*ScreenH/100),paint);
        }else
        {
            canvas.drawBitmap(baowei11,null,new Rect((baowei1X-baowei1W/2)*ScreenW/100,(baowei1Y-baowei1H/2)*ScreenH/100,(baowei1X+baowei1W/2)*ScreenW/100,(baowei1Y+baowei1H/2)*ScreenH/100),paint);
        }
        if(!pbaowei2)//不按着保卫2
        {
            canvas.drawBitmap(baowei2,null,new Rect((baowei2X-baowei2W/2)*ScreenW/100,(baowei2Y-baowei2H/2)*ScreenH/100,(baowei2X+baowei2W/2)*ScreenW/100,(baowei2Y+baowei2H/2)*ScreenH/100),paint);
        }else
        {
            canvas.drawBitmap(baowei21,null,new Rect((baowei2X-baowei2W/2)*ScreenW/100,(baowei2Y-baowei2H/2)*ScreenH/100,(baowei2X+baowei2W/2)*ScreenW/100,(baowei2Y+baowei2H/2)*ScreenH/100),paint);
        }
        if(!pbaowei3)//不按着保卫3
        {
            canvas.drawBitmap(baowei3,null,new Rect((baowei3X-baowei3W/2)*ScreenW/100,(baowei3Y-baowei3H/2)*ScreenH/100,(baowei3X+baowei3W/2)*ScreenW/100,(baowei3Y+baowei3H/2)*ScreenH/100),paint);
        }else
        {
            canvas.drawBitmap(baowei31,null,new Rect((baowei3X-baowei3W/2)*ScreenW/100,(baowei3Y-baowei3H/2)*ScreenH/100,(baowei3X+baowei3W/2)*ScreenW/100,(baowei3Y+baowei3H/2)*ScreenH/100),paint);
        }
        //endregion
//        this.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        //按下
//                        pstX=event.getX();
//                        pstY=event.getY();
//                        if(Math.abs(pstX-guaiwu1X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-guaiwu1Y*ScreenH/100)<ScreenH/7)//进入guaiwu按钮
//                        {guaiwu1shake=true;
//                        }
//                        if(Math.abs(pstX-guaiwu2X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-guaiwu2Y*ScreenH/100)<ScreenH/7)//进入guaiwu按钮
//                        {guaiwu2shake=true;
//                        }
//                        if(Math.abs(pstX-guaiwu3X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-guaiwu3Y*ScreenH/100)<ScreenH/7)//进入guaiwu按钮
//                        {guaiwu3shake=true;
//                        }
//                        //break;//不需要所以直接跳过，且执行下面的
//                    case MotionEvent.ACTION_MOVE:
//                        //移动
//                        pstX=event.getX();
//                        pstY=event.getY();
//                        if(Math.abs(pstX-homeX*ScreenW/100)<ScreenW/10&&Math.abs(pstY-homeY*ScreenH/100)<ScreenH/10)//进入home按钮
//                        {phome=true;
//                        }else
//                        {phome=false;
//                        }
//
//                        if(Math.abs(pstX-baowei1X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-baowei1Y*ScreenH/100)<ScreenH/7)//进入baowei按钮
//                        {pbaowei1=true;
//                        }else
//                        {pbaowei1=false;
//                        }
//                        if(Math.abs(pstX-baowei2X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-baowei2Y*ScreenH/100)<ScreenH/7)
//                        {pbaowei2=true;
//                        }else
//                        {pbaowei2=false;
//                        }
//                        if(Math.abs(pstX-baowei3X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-baowei3Y*ScreenH/100)<ScreenH/7)
//                        {pbaowei3=true;
//                        }else
//                        {pbaowei3=false;
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP://最重要
//                        pstX=event.getX();
//                        pstY=event.getY();
//                        phome=false;
//                        if(Math.abs(pstX-homeX*ScreenW/100)<ScreenW/10&&Math.abs(pstY-homeY*ScreenH/100)<ScreenH/10)//进入home按钮
//                        {gamestate=index;
//                        }
//                        pbaowei1=false;
//                        pbaowei2=false;
//                        pbaowei3=false;
////                        if(Math.abs(pstX-guaiwu1X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-guaiwu1Y*ScreenH/100)<ScreenH/7)//进入guaiwu按钮
////                        {
////                            if(startOK)
////                            {
////                                gamestate=game1;
////                                Log.d("Hh",ScreenH+"");
////                            }
////                        }
////                        if(Math.abs(pstX-guaiwu2X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-guaiwu2Y*ScreenH/100)<ScreenH/7)//进入guaiwu按钮
////                        {
////                            if(startOK)
////                            {
////                                gamestate=game2;
////                            }
////                        }
////                        if(Math.abs(pstX-guaiwu3X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-guaiwu3Y*ScreenH/100)<ScreenH/7)//进入guaiwu按钮
////                        {
////                            if(startOK)
////                            {
////                                gamestate=game3;
////                            }
////                        }
//
//                        if(Math.abs(pstX-baowei1X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-baowei1Y*ScreenH/100)<ScreenH/7)//进入baowei按钮
//                        {
//                            gamestate=game1;
//                        }
//                        if(Math.abs(pstX-baowei2X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-baowei2Y*ScreenH/100)<ScreenH/7)
//                        {
//                            gamestate=game2;
//                        }
//                        if(Math.abs(pstX-baowei3X*ScreenW/100)<ScreenW/7&&Math.abs(pstY-baowei3Y*ScreenH/100)<ScreenH/7)
//                        {
//                            gamestate=game3;
//                        }
//                        break;
//                }
//                return true;
//            }
//        });
    }
    public void drawsetting(Canvas canvas,Paint paint)
    {

    }
    public void drawgame(int i) throws InterruptedException//画第几个游戏
    {

        if(i==1)
        {
            drawgame1(canvas,paint);
        }else if(i==2)
        {
           drawgame2(canvas,paint);
        }else
        {
            drawgame3(canvas,paint);
        }
//        this.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        //按下
//                        pstX=event.getX();
//                        pstY=event.getY();
//                        //mymap.pushdown((int)pstX,(int)pstY);
//                        break;//不需要所以直接跳过，且执行下面的
//                    case MotionEvent.ACTION_MOVE:
//                        //移动
//                        pstX=event.getX();
//                        pstY=event.getY();
//                        if(mymap!=null)
//                        mymap.move((int)pstX,(int)pstY);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        pstX=event.getX();
//                        pstY=event.getY();
//                        if(mymap!=null)
//                        mymap.pushup((int)pstX,(int)pstY);
//                        break;
//                }
//                return true;
//            }
//        });
    }
    public void drawgame1(Canvas canvas,Paint paint) throws InterruptedException {
        mymap2=null;
        mymap3=null;
        if(mymap1==null)
        {
           // mymap=null;
            mymap1=new MyMap(canvas,paint,this,getResources(),1);//绘制第一幅地图
        }
        mymap1.drawmap();
    }
    public void drawgame2(Canvas canvas,Paint paint) throws InterruptedException {
        mymap1=null;
        mymap3=null;
        if(mymap2==null)
        {
            //mymap=null;
            mymap2=new MyMap(canvas,paint,this,getResources(),2);//绘制第一幅地图
        }
        mymap2.drawmap();
//        this.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        //按下
//                        pstX=event.getX();
//                        pstY=event.getY();
//                        mymap.pushdown((int)pstX,(int)pstY);
//                        //break;//不需要所以直接跳过，且执行下面的
//                    case MotionEvent.ACTION_MOVE:
//                        //移动
//                        pstX=event.getX();
//                        pstY=event.getY();
//                        mymap.move((int)pstX,(int)pstY);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        pstX=event.getX();
//                        pstY=event.getY();
//                        mymap.pushup((int)pstX,(int)pstY);
//                        break;
//                }
//                return true;
//            }
//        });
    }
    public void drawgame3(Canvas canvas,Paint paint) throws InterruptedException {
        mymap2=null;
        mymap1=null;
       if(mymap3==null)
        {
           // mymap=null;
            mymap3=new MyMap(canvas,paint,this,getResources(),3);//绘制第一幅地图
        }
        mymap3.drawmap();
//        this.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        //按下
//                        pstX=event.getX();
//                        pstY=event.getY();
//                        mymap.pushdown((int)pstX,(int)pstY);
//                        //break;//不需要所以直接跳过，且执行下面的
//                    case MotionEvent.ACTION_MOVE:
//                        //移动
//                        pstX=event.getX();
//                        pstY=event.getY();
//                        mymap.move((int)pstX,(int)pstY);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        pstX=event.getX();
//                        pstY=event.getY();
//                        mymap.pushup((int)pstX,(int)pstY);
//                        break;
//                }
//                return true;
//            }
//        });
    }

    private void logic()
    {

      if((guaiwu1shake==true||guaiwu2shake==true||guaiwu3shake==true)&&shaketime>0)//只要有一个为真就角度变化
      {   startOK=false;
          if(deegre<-10)
          {
              shaketime--;
          }
          if(shaketo==true)
          {
              deegre+=10;
              if(deegre>10)
              {
                  shaketo=false;
              }
          }else
          {
              deegre-=10;
              if(deegre<-10)
              {
                  shaketo=true;
              }
          }
      }
        if(shaketime==0)
        {

            guaiwu1shake=false;
            guaiwu2shake=false;
            guaiwu3shake=false ;
            shaketime=Shaketimezong;//设置为初始shaketime
            startOK=true;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder sfh)//创造surfaceview的时候
    {
        isRunning=true;
        ScreenH=this.getHeight();//获取屏幕宽高
        ScreenW=this.getWidth();
        thread=new Thread(this);//新建线程
        thread.start();//开启
    }
    @Override
    public void surfaceChanged(SurfaceHolder sfh,int format,int width,int Height)//视图改变时相应此函数
    {
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder sfh)//视图销毁时相应此函数
    {
        isRunning=false;//销毁时不再运行了
    }

    @Override
    public void run()//线程运行
    {
        while(isRunning)//如果在运行
        {
            long starttime=System.currentTimeMillis();
            //下面是逻辑
            mydraw();
            logic();
            long endtime=System.currentTimeMillis();
            try {
                if(endtime-starttime<flashtime)//运行时间不够刷新隔的时间，那么说明有多余的时间，此时等待，否则不等待
                {
                    thread.sleep(flashtime-(endtime-starttime));
                }
            }catch(Exception e)
            {e.printStackTrace();}
        }
    }
}