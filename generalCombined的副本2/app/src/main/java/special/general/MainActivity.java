package special.general;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends Activity {
    public Mysurfaceview mysurfaceview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        mysurfaceview=new Mysurfaceview(this);
        setContentView(mysurfaceview);

       // setContentView(new Mysurfaceview(this));
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        }

}
