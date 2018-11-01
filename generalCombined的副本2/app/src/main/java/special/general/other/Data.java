package special.general.other;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import special.general.R;

/**
 * Created by 郭昊然 on 2016/5/23.
 */
public class Data {
    public static int score = 0;
    public static int money = 8000;//初始金钱
    public static int jianke1blood=10;//剑客的总血量
    public static int jianke2blood=10;//剑客的总血量
    public static int jianke3blood=10;//剑客的总血量
    public static int jk1bloodnow=10;//剑客的当前血量
    public static int jk2bloodnow=10;//剑客的当前血量
    public static int jk3bloodnow=10;//剑客的当前血量
    public static MediaPlayer mediaPlayer,mediaPlayergame;
    public static AudioManager au;
    //打怪挣钱 怪物1：100，怪物2:200 怪物3：400
    // 卖塔挣钱 塔1：1级200,2级300 3级400
    // 卖塔挣钱 塔2：1级:300,2级400 3级500
    // 卖塔挣钱 塔3：1级:400,2级500 3级600

    //买塔花钱：塔1：1级:300,2级200 3级300  升级花钱
    //买塔花钱：塔2：1级:400,2级200 3级300  升级花钱
    //买塔花钱：塔3：1级:500,2级300 3级400  升级花钱
    public static SoundPool soundPool;
    public static int guai1,guai2,guai3,diaoxue,moneysongdid,jianzhu,shengji,beijing,anniu,anniu1,doudong1,doudong2,doudong3;

    public static void chaungjian(Context context)
    {
        soundPool=new SoundPool(6,AudioManager.STREAM_MUSIC, 100);//最大同时播放几个文件，声音类型，声音品质
        guai1=soundPool.load(context,R.raw.guai1,1);//实例 音乐文件ID，优先级
        guai2=soundPool.load(context,R.raw.guai2,1);//实例 音乐文件ID，优先级
        guai3=soundPool.load(context,R.raw.guai3,1);//实例 音乐文件ID，优先级
        diaoxue=soundPool.load(context,R.raw.diaoxue,1);//实例 音乐文件ID，优先级
        moneysongdid=soundPool.load(context,R.raw.money,1);//实例 音乐文件ID，优先级
        jianzhu=soundPool.load(context,R.raw.jianzhu,1);//实例 音乐文件ID，优先级
        shengji=soundPool.load(context,R.raw.shengji,1);//实例 音乐文件ID，优先级
        //beijing=soundPool.load(context,R.raw.beijing,1);//背景音乐声音
        anniu=soundPool.load(context,R.raw.anniu,1);//按钮声音
        anniu1=soundPool.load(context,R.raw.anniu1,1);//按钮1声音
        doudong1=soundPool.load(context,R.raw.doudong1,1);//按钮1声音
        doudong2=soundPool.load(context,R.raw.doudong2,1);//按钮1声音
        doudong3=soundPool.load(context,R.raw.doudong3,1);//按钮1声音
        mediaPlayer=MediaPlayer.create(context,R.raw.beijing);
        mediaPlayer.setLooping(true);
        mediaPlayergame=MediaPlayer.create(context,R.raw.game);
        mediaPlayergame.setLooping(true);


        //mediaPlayer.prepare();//prepare();
        //au = (AudioManager) MainActivity.instance.get
    }

    public static void guai1()//怪物死亡的发出声音
    {
        soundPool.play(guai1,1,1,0,0,1);//id,左声道，右声道，优先级，（循环次数-1无线0一次，234几次），速率
    }
    public static void guai2()
    {
        soundPool.play(guai2,1,1,0,0,1);
    }
    public static void guai3()//
    {
        soundPool.play(guai3,1,1,0,0,1);
    }
    public static void diaoxue()//剑客掉血的声音
    {
        soundPool.play(diaoxue,1,1,0,0,1);
    }
    public static void moneyjia()//加钱的声音
    {
        soundPool.play(moneysongdid,1,1,0,0,1);
    }
    public  static void jianzhu()//放塔的声音
    {
        soundPool.play(jianzhu,1,1,0,2,2);
    }
    public static void shengji()//升级的声音
    {
        soundPool.play(shengji,1,1,0,0,1);
    }
    public static void anniu()//点击按钮声音
    {
        soundPool.play(anniu,1,1,0,0,1);
    }
    public static void anniu1()//点击按钮声音
    {
        soundPool.play(anniu1,1,1,0,0,1);
    }
    public static void beijing()//背景音乐声音
    {
       mediaPlayer.start();
    }
    public static void beijingpause()
    {
        mediaPlayer.pause();
    }
    public static void gamestart()
    {
        mediaPlayergame.start();
    }
    public static void gamepause()
    {
        mediaPlayergame.pause();
    }
    public static void doudong1()
    {
        soundPool.play(doudong1,1,1,0,0,1);
    }
    public static void doudong2()
    {
        soundPool.play(doudong2,1,1,0,0,1);
    }
    public static void doudong3()
    {
        soundPool.play(doudong3,1,1,0,0,1);
    }

    public void Addmoney()
    {

    }
}
