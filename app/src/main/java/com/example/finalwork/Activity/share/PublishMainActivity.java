package com.example.finalwork.Activity.share;

import static com.example.finalwork.Activity.login.MainActivity.post_userid;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.finalwork.R;
import com.example.finalwork.bottomnavigation.base.BaseActivity;
import com.example.finalwork.Database.SQLiteHelper;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PublishMainActivity extends BaseActivity {
    private static final byte REQUEST_SYSTEM_PIC = 10;
    private SQLiteHelper dbHelper;
    private Spinner sp;
    private ImageButton imageButton;
    private byte[] image;

    //监听返回按钮，如果点击返回按钮则关闭当前Activity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_share_content);
        //设置返回按钮的actionbar
        ActionBar actionBar = getSupportActionBar(); //获取ActionBar
        if(actionBar != null){
            actionBar.setTitle("Share with you~");//设置ActionBar的标题
            actionBar.setSubtitle("happy study every day");//副标题
            actionBar.setDisplayHomeAsUpEnabled(true);//设置返回按钮
        }

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        dbHelper = new SQLiteHelper(this);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] ctype = new String[]{"经验栏","计划栏","记录栏"};
        //创建一个数组适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,ctype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//设置下拉列表框的下拉选项样式
        Spinner spinner = (Spinner) super.findViewById(R.id.m1_style);
        spinner.setAdapter(adapter);
        sp = (Spinner) findViewById(R.id.m1_style);
        final String[] label = new String[1];
        //添加Spinner事件监听
        sp.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                label[0] = ctype[position];
                parent.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
//

        imageButton = (ImageButton) findViewById(R.id.m1_image);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(PublishMainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PublishMainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                }
                else {
                    //打开系统相册
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,1);
                }
            }
        });

        Button fabu = (Button) findViewById(R.id.fabu);
        fabu.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(image == null){
                    Toast.makeText(getApplicationContext(),"发布失败，请上传图片",Toast.LENGTH_SHORT).show();
                }else{
                    EditText shareTitle = (EditText)findViewById(R.id.m1_title);
                    EditText shareContent = (EditText) findViewById(R.id.m1_nr);
                    Date curDate = new Date(System.currentTimeMillis());
                    String time = formatter.format(curDate);
                    ContentValues values = new ContentValues();
                    values.put("shareTitle",shareTitle.getText().toString());
                    values.put("userName",post_userid);
                    values.put("shareLabel", label[0]);
                    values.put("publishTime",time);
                    values.put("shareContent",shareContent.getText().toString());
                    values.put("shareImg",image);
                    db.insert("share",null,values);
                    Intent intent = new Intent(PublishMainActivity.this,RecommendActivity.class);
                    Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
    }

    //加载图片
    private void showImage(String imagePath) {
        Bitmap bm = BitmapFactory.decodeFile(imagePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        image = baos.toByteArray();
        imageButton.setImageBitmap(bm);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

}

