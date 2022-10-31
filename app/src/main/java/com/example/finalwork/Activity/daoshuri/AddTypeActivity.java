package com.example.finalwork.Activity.daoshuri;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalwork.Activity.course.MainActivity;
import com.example.finalwork.R;
import com.example.finalwork.bottomnavigation.base.BaseActivity;
import com.example.finalwork.Activity.card.CardViewPagerActivity;
import com.example.finalwork.Adapter.Daoshuri.TypeImageAdapter;
import com.example.finalwork.model.TypeBean;
import com.example.finalwork.Activity.share.RecommendActivity;
import com.example.finalwork.Activity.todolist.todolistMainActivity;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 添加新的倒数本分类
 */
    public class AddTypeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    /**
     * 保存
     */
    private TextView mTvTypeSave;
    /**
     * 点击这里输入本子名字
     */
    private EditText mMatterContentInput;
    private RecyclerView mRvTypeImage;
    private String imageRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daoshuri_activity_add_type);
        initView();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTypeSave = (TextView) findViewById(R.id.tv_type_save);
        mTvTypeSave.setOnClickListener(this);
        mMatterContentInput = (EditText) findViewById(R.id.matter_content_input);
        mRvTypeImage = (RecyclerView) findViewById(R.id.rv_type_image);
        mRvTypeImage.setLayoutManager(new GridLayoutManager(this, 6));
        final TypeImageAdapter adapter = new TypeImageAdapter();
        adapter.setOnItemClick(new TypeImageAdapter.OnItemClick() {
            @Override
            public void onClick(int position) {
                imageRes = adapter.getTypeBeans().get(position);
                adapter.setSelectPosition(position);
                adapter.notifyDataSetChanged();
            }
        });
        try {
            List<String> book_icon = Arrays.asList(getAssets().list("book_icon"));
            imageRes = book_icon.get(0);
            adapter.setTypeBeans(book_icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRvTypeImage.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_type_save:
                if (mMatterContentInput.getText().toString().isEmpty()) {
                    Toast.makeText(this, "请输入名字", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(imageRes)) {
                    Toast.makeText(this, "请选择图标", Toast.LENGTH_SHORT).show();
                    return;
                }
                //保存分类
                new TypeBean(mMatterContentInput.getText().toString(), "book_icon/" + imageRes).save();
                finish();
                break;
        }
    }

//    // 底部导航栏，请注意本方法内容的变化
    public void onNavButtonsTapped(View v) {
        switch (v.getId()) {
            case R.id.btnNavStudy:
                open(todolistMainActivity.class);
                break; // case R.id.btnNavStudy
            case R.id.btnNavShare:
                open(RecommendActivity.class);
                break; // case R.id.btnNavShare
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