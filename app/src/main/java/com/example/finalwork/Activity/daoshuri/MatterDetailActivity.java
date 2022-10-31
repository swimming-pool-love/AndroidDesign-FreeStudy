package com.example.finalwork.Activity.daoshuri;

import static java.lang.Math.abs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;


import com.example.finalwork.Activity.course.MainActivity;
import com.example.finalwork.R;
import com.example.finalwork.Activity.card.CardViewPagerActivity;
import com.example.finalwork.Helper.Utility;
import com.example.finalwork.model.Matter;
import com.example.finalwork.Activity.share.RecommendActivity;
import com.example.finalwork.Activity.todolist.todolistMainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * 倒数本详情
 */

public class MatterDetailActivity extends BaseActivity{


    private TextView detailDate,detailAfter,detailBefore,detailContent,detailDays;
    private static Matter mMatter;
    private static int matpos;
    private static Context mContext;
    private static List<Matter> sMatterList;
    private LinearLayout detailHeader;
    private String SHOWCASE_ID = "detail_showcase";
    private Random ran =new Random(System.currentTimeMillis());
    private int SHOWCASE_MORE = ran.nextInt(100);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.layoutId = R.layout.daoshuri_activity_matter_detail;
        super.title = "MatterDetail";
        super.onCreate(savedInstanceState);
        super.mToolbar.setBackgroundColor(0);
        setAllText();


    }

    /**
     * 设置向导的设置（连续向导）
     * @param showCaseID
     */

    private void setShowCaseConfig(String showCaseID){
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, showCaseID);

        sequence.setConfig(config);


        sequence.addSequenceItem(detailDate,
                "这是目标日期", "GOT IT");

        sequence.addSequenceItem(detailDays,
                "这是倒数的日子", "GOT IT");

        sequence.addSequenceItem(findViewById(R.id.delete_matter),"点击这里删除事件","GOT IT");
        sequence.addSequenceItem(findViewById(R.id.edit_matter),"点击这里编辑事件","GOT IT");

        sequence.start();
    }

    /**
     * 设置所有textView在这
     */

    private void setAllText(){


        String matterContent = mMatter.getMatterContent();
        long matterDateMs = mMatter.getTargetDate().getTime();
        long daysCount = Utility.getDateInterval(mMatter.getTargetDate());


        detailContent = (TextView) findViewById(R.id.matter_detail_content);
        detailDays = (TextView) findViewById(R.id.matter_detail_days);
        detailDate = (TextView) findViewById(R.id.matter_target_date);
        detailAfter = (TextView) findViewById(R.id.detail_after_text);
        detailBefore = (TextView) findViewById(R.id.detail_before_text);
        detailHeader = (LinearLayout) findViewById(R.id.detail_header);


        long duration = Utility.getDateInterval(mMatter.getTargetDate());
        String beforetext = "";
        String aftertext = "";
        if(duration < 0) {
            aftertext = "已经";
            ColorStateList list = ContextCompat.getColorStateList(mContext, R.color.expired);
            detailHeader.setBackgroundTintList(list);
        } else if(duration >=0) {
            aftertext = "还有";
            beforetext = "距离";
            ColorStateList list = ContextCompat.getColorStateList(mContext, R.color.future);
            detailHeader.setBackgroundTintList(list);
        }
        detailAfter.setText(aftertext);
        detailBefore.setText(beforetext);

        Calendar c = Calendar.getInstance();
        new SimpleDateFormat("yyyy年MM月dd日").format(c.getTime());

        detailDate.setText(new SimpleDateFormat("yyyy年MM月dd日").
                format(new Date(matterDateMs)));

        detailContent.setText(matterContent);

        detailDays.setText(Long.toString(abs(daysCount)));
    }

    /**
     * 静态方法启动此活动
     * @param context
     * @param matter
     * @param matterList
     * @param position
     */

    public static void actionStart(Context context, Matter matter, List<Matter> matterList,int position){
        Intent i = new Intent(context,MatterDetailActivity.class);
        mContext = context;
        mMatter = matter;
        sMatterList = matterList;
        matpos = position;
        context.startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.daoshuri_detail_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_matter:
                AlertDialog.Builder builder = new AlertDialog.Builder(MatterDetailActivity.this);
                builder.setMessage("确定要删除吗？");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mMatter.delete();
                        sMatterList.remove(matpos);
                        Log.i("INFO","quit after delet matter in list of this activity");
                        finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                return true;
            case R.id.edit_matter:
                 MatterEditActivity.actionStart(this,mMatter);
                return true;
            case R.id.show_guide:
                setShowCaseConfig(SHOWCASE_ID+SHOWCASE_MORE);
                SHOWCASE_MORE++;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        setAllText();

    }

    // 底部导航栏，请注意本方法内容的变化
    public void onNavButtonsTapped(View v) {
        switch (v.getId()) {
            case R.id.btnNavStudy:
                open(todolistMainActivity.class);
                break; // case R.id.btnNavStudy
            case R.id.btnNavShare:
                open(RecommendActivity.class);
                break; // case R.id.btnNavTime
            case R.id.btnNavData:
                open(MainActivity.class);
                break; // case R.id.btnNavData
            case R.id.btnNavCard:
                open(CardViewPagerActivity.class);
                break; // case R.id.btnNavCard

        } // switch (v.getId())
    } // onNavButtonsTapped()

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showExitDialog();
            return true;
        } // if (keyCode == KeyEvent.KEYCODE_BACK)
        else {
            return super.onKeyDown(keyCode, event);
        } // else
    } // onKeyDown()


}
