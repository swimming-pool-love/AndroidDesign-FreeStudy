package com.example.finalwork.Activity.login;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalwork.R;
import com.example.finalwork.Database.SQLiteHelper;


public class RegisterActivity extends AppCompatActivity {

    EditText rname,rpwd,rpwd2,rphone;
    Button btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ViewInit();
    }

    private void ViewInit() {
        rname=this.findViewById(R.id.rpname);
        rpwd=this.findViewById(R.id.rpwd);
        rpwd2=this.findViewById(R.id.rpwd2);
        rphone=this.findViewById(R.id.rphone);
        btnOK=this.findViewById(R.id.btn_register);
        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String username=rname.getText().toString();
                String pwd=rpwd.getText().toString();
                String pwd2=rpwd2.getText().toString();
                String phone=rphone.getText().toString();
                if(username.length()*pwd.length()*pwd2.length()>0) {
                    if(pwd.equals(pwd2)) {
                        SQLiteHelper shelper=new SQLiteHelper(RegisterActivity.this,null,2);
                        Cursor cr=shelper.selectuser(username);

                        if(cr.getCount()==0)
                        {
                            shelper.insertuser(username, pwd, phone);
                            showMsg();
                        }else {
                            Toast.makeText(RegisterActivity.this, "该用户已被注册", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(RegisterActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this, "请填写必要信息", Toast.LENGTH_SHORT).show();
                }
            }});
    }

    protected void showMsg() {
        // TODO Auto-generated method stub
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("用户注册");
        builder.setMessage("恭喜你，注册成功，点击确定返回登录界面");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                RegisterActivity.this.finish();
            }
        });
        builder.create().show();
    }

}


