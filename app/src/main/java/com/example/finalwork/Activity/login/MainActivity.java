package com.example.finalwork.Activity.login;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalwork.R;
import com.example.finalwork.Database.SQLiteHelper;
import com.example.finalwork.Activity.share.RecommendActivity;

public class MainActivity extends AppCompatActivity {
    TextView tv_register,tv_forgotPassword;
    Intent intent_re,intent_fo;
    EditText etname,etpwd;
    Button login;
    public static String post_userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_login);
        ForgetInit();
        RegisterInit();
        ViewInit();
    }

    private void ViewInit() {
        etname=this.findViewById(R.id.etname);
        etpwd=this.findViewById(R.id.etpwd);
        login = this.findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String username=etname.getText().toString();
                String pwd=etpwd.getText().toString();
                if(username.length()*pwd.length()>0) {
//					SharedPreferences sp = MainActivity.this.getSharedPreferences(username, MODE_PRIVATE);
                    SQLiteHelper shelper=new SQLiteHelper(MainActivity.this,null,2);
                    Cursor cr=shelper.selectuser(username);
//					if(sp.contains("username"))
                    if(cr.getCount()>0)
                    {
                        cr.moveToFirst();
                        String rpwd=cr.getString(2);
//						String rpwd=sp.getString("pwd", "");
                        if(pwd.equals(rpwd)) {
                            post_userid = username;
                            Intent intent=new Intent(MainActivity.this,RecommendActivity.class);
                            intent.putExtra("username", username);
                            intent.putExtra("pwd", rpwd);
                            MainActivity.this.startActivity(intent);
                        }else {
                            Toast.makeText(MainActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "用户名不存在", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "请登录账号或密码", Toast.LENGTH_SHORT).show();
                }
            }});
    }


    private void ForgetInit() {
        tv_forgotPassword=this.findViewById(R.id.tv_forgotPassword);
        SpannableString forgetPassword=new SpannableString("Forgot Password?");
        forgetPassword.setSpan(new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                // TODO Auto-generated method stub
                intent_fo=new Intent(MainActivity.this, ForgetPasswordActivity.class);
                startActivityForResult(intent_fo,1234);
            }
        }, 0, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_forgotPassword.setText(forgetPassword);
        tv_forgotPassword.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void RegisterInit() {
        tv_register=this.findViewById(R.id.tv_register);
        SpannableString register=new SpannableString("Don't have an account? Register Here");
        register.setSpan(new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                // TODO Auto-generated method stub
                intent_re=new Intent(MainActivity.this, RegisterActivity.class);
                startActivityForResult(intent_re,1234);
            }
        }, 23, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_register.setText(register);
        tv_register.setMovementMethod(LinkMovementMethod.getInstance());
    }
}