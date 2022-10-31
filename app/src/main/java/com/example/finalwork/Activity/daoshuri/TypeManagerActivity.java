package com.example.finalwork.Activity.daoshuri;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalwork.Activity.course.MainActivity;
import com.example.finalwork.R;
import com.example.finalwork.bottomnavigation.base.BaseActivity;
import com.example.finalwork.Activity.card.CardViewPagerActivity;
import com.example.finalwork.Adapter.Daoshuri.TypeManagerAdapter;
import com.example.finalwork.model.TypeBean;
import com.example.finalwork.Activity.share.RecommendActivity;
import com.example.finalwork.Activity.todolist.todolistMainActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 分类管理，进行添加删除
 */
public class TypeManagerActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private RecyclerView mRvType;
    /**
     * 添加新的倒数本
     */
    private Button mBtnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daoshuri_activity_type_manager);
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
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnAdd.setOnClickListener(this);
        mRvType.setLayoutManager(new LinearLayoutManager(this));
        TypeManagerAdapter adapter = new TypeManagerAdapter();
        adapter.setTypeBeans(getDate());
        mRvType.setAdapter(adapter);
        adapter.setOnLoginClick(new TypeManagerAdapter.OnLoginClick() {
            @Override
            public void onLongClick(int position) {//长按删除该分类
                showNormalDialog(position);
            }

            @Override
            public void onClick(int position) {//点击返回上一页面 并且把选择的分类传递过去
                Intent data = new Intent();
                TypeBean value = getDate().get(position);
                data.putExtra("type", value);
                setResult(101, data);
                finish();
            }
        });
    }

    private void showNormalDialog(final int position) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(TypeManagerActivity.this);
        normalDialog.setTitle("删除");
        normalDialog.setMessage("是否要删除此分类?");
        normalDialog.setPositiveButton("确定",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDate().get(position).delete();
                        initView();
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

    /**
     * 获取所有的分类列表数据
     */
    public List<TypeBean> getDate() {

        List<TypeBean> all = DataSupport.findAll(TypeBean.class);
        if (all.size() == 0) {//如果没有分类数据默认添加三条
            new TypeBean("生活", "book_icon/ico_0@3x.png").save();
            new TypeBean("学习", "book_icon/ico_1@3x.png").save();
            new TypeBean("纪念日", "book_icon/ico_2@3x.png").save();
        }
        all = DataSupport.findAll(TypeBean.class);
        return all;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_add://跳转到添加分类
                startActivity(new Intent(TypeManagerActivity.this, AddTypeActivity.class));
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