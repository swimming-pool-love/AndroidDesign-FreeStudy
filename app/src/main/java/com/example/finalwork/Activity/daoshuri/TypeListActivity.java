package com.example.finalwork.Activity.daoshuri;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalwork.Activity.course.MainActivity;
import com.example.finalwork.R;
import com.example.finalwork.bottomnavigation.base.BaseActivity;
import com.example.finalwork.Activity.card.CardViewPagerActivity;
import com.example.finalwork.Adapter.Daoshuri.TypeListAdapter;
import com.example.finalwork.model.TypeBean;
import com.example.finalwork.Activity.share.RecommendActivity;
import com.example.finalwork.Activity.todolist.todolistMainActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 分类列表。后面跟该分类有几个倒数本
 */
public class TypeListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private RecyclerView mRvType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daoshuri_activity_type_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mRvType = (RecyclerView) findViewById(R.id.rv_type);
        mRvType.setLayoutManager(new LinearLayoutManager(this));
        TypeListAdapter adapter = new TypeListAdapter();
        adapter.setTypeBeans(getDate());
        mRvType.setAdapter(adapter);
        adapter.setOnLoginClick(new TypeListAdapter.OnLoginClick() {
            @Override
            public void onLongClick(int position) {
            }

            @Override
            public void onClick(int position) {
                TypeBean value = getDate().get(position);
                Intent intent = new Intent(TypeListActivity.this, QueryByTypeActivity.class);
                intent.putExtra("type",value);
                startActivity(intent);
            }
        });
    }



    /**
     * 获取所有的分类列表数据
     */
    public List<TypeBean> getDate() {
        List<TypeBean> all = DataSupport.findAll(TypeBean.class);
        return all;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_back:
                finish();
                break;

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