package com.example.finalwork.bottomnavigation.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalwork.R;

/**
 * BaseActivity: 该抽象类定义所有活动均拥有的共同属性。
 * 本 APP 中所有活动对象均继承此类。
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * onCreate(): 重写父类的 onCreate() 方法，向应用管理器中添加本活动。
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.get().addActivity(this);
    } // onCreate()

    /**
     * onDestroy(): 重写父类的 onDestroy() 方法，从应用管理器中移除本活动。
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.get().removeActivity(this);
    } // onDestroy()

    /**
     * jumpTo(): 实现不传参的活动间跳转。
     *
     * @param dst 要跳转到的活动的类名（xxxActivity.class）。
     */
    protected void jumpTo(Class dst) {
        Intent intent = new Intent(this, dst);
        startActivity(intent);
        AppManager.get().finishAllExcept(dst);
    } // jumpTo()

    /**
     * open(): 将当前活动压入堆栈，打开一个新活动。
     *
     * @param dst 要打开的活动的类名（xxxActivity.class）。
     */
    protected void open(Class dst) {
        Intent intent = new Intent(this, dst);
        startActivity(intent);
    } // open()

    /**
     * showExitDialog(): 显示退出程序对话框，询问用户是否退出程序。
     */
    protected void showExitDialog() {
        AlertDialog dialog;
        DialogInterface.OnClickListener onClick;

        onClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // 确定按钮
                        dialog.dismiss();
                        AppManager.get().finishAllActivities();
                        break; // case DialogInterface.BUTTON_POSITIVE

                    case DialogInterface.BUTTON_NEGATIVE:
                        // 取消按钮
                        dialog.dismiss();
                        break; // case DialogInterface.BUTTON_NEGATIVE

                    default:
                        break; // default
                } // switch (which)
            } // onClick()
        }; // onClick = new DialogInterface.OnClickListener()

        // 显示对话框
        dialog = new AlertDialog.Builder(this)
                .setMessage(R.string.dlgExitMsg) // "确定要退出吗？"
                .setPositiveButton(android.R.string.ok, onClick)
                .setNegativeButton(android.R.string.cancel, onClick)
                .create(); // dialog = new AlertDialog.Builder(this)...
        dialog.show();
    } // showExitDialog()

}
