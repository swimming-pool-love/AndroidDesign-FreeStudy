package com.example.finalwork.Activity.daoshuri;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.finalwork.Activity.course.MainActivity;
import com.example.finalwork.R;
import com.example.finalwork.bottomnavigation.base.BaseActivity;
import com.example.finalwork.Activity.card.CardViewPagerActivity;
import com.example.finalwork.Helper.AssetsUtils;
import com.example.finalwork.model.Matter;
import com.example.finalwork.model.TypeBean;
import com.example.finalwork.Activity.share.RecommendActivity;
import com.example.finalwork.Activity.todolist.todolistMainActivity;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 编辑倒数本
 */

public class MatterEditActivity extends BaseActivity implements View.OnClickListener {


    private Button saveMatter;

    private EditText matterContent;

    public static TextView datetime;


    public static Matter sMatter;
    private RelativeLayout rlCaretory;
    private ImageView ivTypeRes;
    private TextView tvTypeName;
    TypeBean typeBean;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daoshuri_activity_add_matter);
        // Find button in add event layout and set it the click listener
        saveMatter = (Button) findViewById(R.id.save_event);
        saveMatter.setOnClickListener(this);

        matterContent = (EditText) findViewById(R.id.matter_content_input);
        matterContent.setText(sMatter.getMatterContent());


        // find the datetime text view element in add event layout
        datetime = (TextView) findViewById(R.id.datetime);

        // Getting current date time and set for text view component
        Date date = sMatter.getTargetDate();
        String dateStr = new SimpleDateFormat("yyy-MM-dd").format(date);
        String weekdayStr = new SimpleDateFormat("EEEE").format(date);
        datetime.setText(dateStr + " " + weekdayStr);

        // Datetime text view component click listener that to show the date time picker
        datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        rlCaretory = findViewById(R.id.rl_category);
        rlCaretory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MatterEditActivity.this, TypeManagerActivity.class), 100);
            }
        });
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView tvTitle=findViewById(R.id.tv_title);
        tvTitle.setText("编辑倒数本");
        matterContent = (EditText) findViewById(R.id.matter_content_input);

        ivTypeRes = findViewById(R.id.iv_type_res);
        tvTypeName = findViewById(R.id.tv_type_name);
         typeBean = DataSupport.find(TypeBean.class, sMatter.getTypeId());
        if (typeBean!=null){
            ivTypeRes.setImageBitmap(AssetsUtils.readBitmapFromAssets(getResources().getAssets(), typeBean.getImageRes()));
            tvTypeName.setText(typeBean.getName());
        }else {
            ivTypeRes.setImageBitmap(AssetsUtils.readBitmapFromAssets(getResources().getAssets(), "book_icon/ico_0@3x.png"));
            tvTypeName.setText("生活");
        }

    }

    public static void actionStart(Context context, Matter matter) {
        Intent i = new Intent(context, MatterEditActivity.class);
        sMatter = matter;
        context.startActivity(i);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        typeBean = (TypeBean) data.getSerializableExtra("type");
        ivTypeRes.setImageBitmap(AssetsUtils.readBitmapFromAssets(getResources().getAssets(), typeBean.getImageRes()));
        tvTypeName.setText(typeBean.getName());
    }

    @Override
    public void onClick(View view) {
        sMatter.setMatterContent(matterContent.getText().toString());
        String dateStr = datetime.getText().toString().split(" ")[0];
        try {
            sMatter.setTargetDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sMatter.setTypeId(typeBean.getId());
        sMatter.save();
        finish();
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String[] dateStrArr = datetime.getText().toString().split(" ")[0].split("-");
            int year = Integer.parseInt(dateStrArr[0]);
            int month = Integer.parseInt(dateStrArr[1]) - 1;
            int day = Integer.parseInt(dateStrArr[2]);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String dateStr = "";
            String weekdayStr = "";
            try {
                dateStr = i + "-" + (i1 + 1) + "-" + i2;
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                weekdayStr = new SimpleDateFormat("EEEE").format(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            datetime.setText(dateStr + " " + weekdayStr);
        }
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
