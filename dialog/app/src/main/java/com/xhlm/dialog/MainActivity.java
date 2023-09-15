package com.xhlm.dialog;

import androidx.activity.ComponentDialog;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Presentation;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.google.android.material.sidesheet.SideSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final Handler handler = new Handler(Looper.getMainLooper());
    private PopupWindow noCancelPopupWindow;
    TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建时间定时器
        tvTime = findViewById(R.id.tv_time);
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
        handler.post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                tvTime.setText("当前时间：" + dateFormat.format(new Date()));
                handler.postDelayed(this, 1000);
            }
        });
        //dialog
        initView();
    }

    private void initView() {

        //基础dialog
        findViewById(R.id.tv_dialog).setOnClickListener(v -> {
            AppCompatDialog dialog = new AppCompatDialog(this);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_test, null);
            TextView tvTitle = view.findViewById(R.id.tv_title);
            TextView tvContent = view.findViewById(R.id.tv_content);
            TextView tvCancel = view.findViewById(R.id.tv_cancel);
            TextView tvOk = view.findViewById(R.id.tv_ok);
            tvTitle.setText("标题");
            tvContent.setText("内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容");
            tvCancel.setOnClickListener(v1 -> {
                dialog.dismiss();
            });
            tvOk.setOnClickListener(v1 -> {
                dialog.dismiss();
                Snackbar.make(v, "点击了确认", Snackbar.LENGTH_SHORT).show();
            });
            dialog.setContentView(view);
            dialog.show();
        });



        //基础PopupWindow
        findViewById(R.id.tv_popup).setOnClickListener(v -> {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_test, null);
            TextView tvTitle = view.findViewById(R.id.tv_title);
            TextView tvContent = view.findViewById(R.id.tv_content);
            TextView tvCancel = view.findViewById(R.id.tv_cancel);
            TextView tvOk = view.findViewById(R.id.tv_ok);

            PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

            tvTitle.setText("标题");
            tvContent.setText("内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容");
            tvCancel.setOnClickListener(v1 -> {
                popupWindow.dismiss();
            });
            tvOk.setOnClickListener(v1 -> {
                popupWindow.dismiss();
                Snackbar.make(v, "点击了确认", Snackbar.LENGTH_SHORT).show();
            });

            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        });



        //禁止返回键关闭的dialog
        findViewById(R.id.tv_dialog_no_cancel).setOnClickListener(v -> {
            AppCompatDialog dialog = new AppCompatDialog(this);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_test, null);
            TextView tvTitle = view.findViewById(R.id.tv_title);
            TextView tvContent = view.findViewById(R.id.tv_content);
            TextView tvCancel = view.findViewById(R.id.tv_cancel);
            TextView tvOk = view.findViewById(R.id.tv_ok);
            tvTitle.setText("标题");
            tvContent.setText("内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容");
            tvCancel.setOnClickListener(v1 -> {
                dialog.dismiss();
            });
            tvOk.setOnClickListener(v1 -> {
                dialog.dismiss();
                Snackbar.make(v, "点击了确认", Snackbar.LENGTH_SHORT).show();
            });
            dialog.setCancelable(false);//禁止返回键关闭+禁止触碰弹窗外部关闭
            dialog.setContentView(view);
            dialog.show();
        });



        //禁止返回键关闭的PopupWindow
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_test, null);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvContent = view.findViewById(R.id.tv_content);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        TextView tvOk = view.findViewById(R.id.tv_ok);
        //要禁止返回键，则不允许popupWindow获取焦点，构造函数第四个参数设为false
        //popupWindow没有焦点的情况下，不会拦截返回键，此时需要做处理，
        //并且在该情况下，点击UI的其他位置，会进行响应，所以contentView的布局最外层需设置为match_parent,match_parent
        //将整个屏幕占满，并且添加相应的背景色或者透明色定制化来做到禁止触摸外部的效果
        noCancelPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
        tvTitle.setText("标题");
        tvContent.setText("内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容");
        tvCancel.setOnClickListener(v1 -> {
            noCancelPopupWindow.dismiss();
        });
        tvOk.setOnClickListener(v1 -> {
            noCancelPopupWindow.dismiss();
            Snackbar.make(tvTime, "点击了确认", Snackbar.LENGTH_SHORT).show();
        });
        findViewById(R.id.tv_popup_no_cancel).setOnClickListener(v -> {
            noCancelPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        });



        //alertDialog普通提示对话框
        findViewById(R.id.tv_alert_dialog).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("标题")
                    .setMessage("这是消息！")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确认", (dialog, which) -> {
                        Snackbar.make(tvTime, "点击了确认", Snackbar.LENGTH_SHORT).show();
                    });
            builder.show();
        });



        //alertDialog普通列表对话框
        findViewById(R.id.tv_alert_dialog_list).setOnClickListener(v -> {
            String[] foods = {"鱼", "鸭肉", "鸡肉", "苹果", "香蕉"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("选择你喜欢的食物：")
                    .setItems(foods, (dialog, which) -> {
                        Snackbar.make(tvTime, "你选择了：" + foods[which], Snackbar.LENGTH_SHORT).show();
                    });
            builder.show();
        });



        //alertDialog单选对话框
        findViewById(R.id.tv_alert_dialog_single_choice).setOnClickListener(v -> {
            String[] cities = {"北京", "上海", "广州", "深圳", "杭州"};
            AtomicInteger select = new AtomicInteger();
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("你所在的居住地是：")
                    .setSingleChoiceItems(cities, 0, (dialog, which) -> {//第二个参数是默认第几个
                        //单选则不会关闭弹窗
                        select.set(which);
                    })
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确认", (dialog, which) -> {
                        //一般在这里处理业务逻辑，这里的which只会是-1，所以需要setSingleChoiceItems的which
                        Snackbar.make(tvTime, "你选择了：" + cities[select.get()], Snackbar.LENGTH_SHORT).show();
                    });
            builder.show();
        });



        //alertDialog复选对话框
        findViewById(R.id.tv_alert_dialog_multi_choice).setOnClickListener(v -> {
            String[] colors = {"红色", "橙色", "黄色", "绿色", "蓝色", "靛色", "紫色"};
            List<String> select = new ArrayList<>();
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("选择你喜欢的颜色：")
                    .setMultiChoiceItems(colors, null, (dialog, which, isChecked) -> {
                        if (isChecked) {
                            select.add(colors[which]);
                        } else {
                            select.remove(colors[which]);
                        }
                    })
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确认", (dialog, which) -> {
                        Snackbar.make(tvTime, "你选择了：" + Arrays.toString(select.toArray()), Snackbar.LENGTH_SHORT).show();
                    });
            builder.show();
        });



        //bottomSheetDialog
        findViewById(R.id.tv_bottom_sheet_dialog).setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View view2 = LayoutInflater.from(this).inflate(R.layout.dialog_test, null);
            TextView tvTitle2 = view2.findViewById(R.id.tv_title);
            TextView tvContent2 = view2.findViewById(R.id.tv_content);
            TextView tvCancel2 = view2.findViewById(R.id.tv_cancel);
            TextView tvOk2 = view2.findViewById(R.id.tv_ok);
            tvTitle2.setText("这是底部弹窗");
            tvContent2.setText("底部弹窗的内容");
            tvCancel2.setOnClickListener(v1 -> {
                dialog.dismiss();
            });
            tvOk2.setOnClickListener(v1 -> {
                dialog.dismiss();
                Snackbar.make(tvTime, "点击了确认", Snackbar.LENGTH_SHORT).show();
            });
            dialog.setContentView(view2);
            dialog.show();
        });



        //CharacterPickerDialog
        EditText etCharacterPickerDialog = findViewById(R.id.et_character_picker_dialog);
        etCharacterPickerDialog.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                return false;
            }
            char base = (char) event.getUnicodeChar();
            Log.e(TAG, "keyCode：" + keyCode);
            Log.e(TAG, "base：" + base);
            String accentedString = AccentedStringUtils.getAccentedString(base);
            Log.e(TAG, "accentedString:" + accentedString);
            if (accentedString == null) {
                return false;
            }
            CharacterPickerDialog dialog = new CharacterPickerDialog(this, new View(this), etCharacterPickerDialog.getText(), accentedString, false) {
                @Override
                public void onClick(View v) {
                    super.onClick(v);
                }

                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {
                    super.onItemClick(parent, view, position, id);
                }
            };
            dialog.show();
            return false;
        });



        //DatePickerDialog
        findViewById(R.id.tv_date_picker_dialog).setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this, (view1, year, month, dayOfMonth) -> {
                String message = "选择了" + year + "年" + (month + 1) + "月" + dayOfMonth + "日";
                Snackbar.make(tvTime, message, Snackbar.LENGTH_SHORT).show();
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });



        //MaterialStyledDatePickerDialog
        findViewById(R.id.tv_material_styled_date_picker_dialog).setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            @SuppressLint("RestrictedApi")
            MaterialStyledDatePickerDialog dialog = new MaterialStyledDatePickerDialog(this, (view1, year, month, dayOfMonth) -> {
                String message = "选择了" + year + "年" + (month + 1) + "月" + dayOfMonth + "日";
                Snackbar.make(tvTime, message, Snackbar.LENGTH_SHORT).show();
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });



        //SideSheetDialog
        findViewById(R.id.tv_side_sheet_dialog).setOnClickListener(v -> {
            SideSheetDialog dialog = new SideSheetDialog(this);
            View view2 = LayoutInflater.from(this).inflate(R.layout.dialog_test, null);
            TextView tvTitle2 = view2.findViewById(R.id.tv_title);
            TextView tvContent2 = view2.findViewById(R.id.tv_content);
            TextView tvCancel2 = view2.findViewById(R.id.tv_cancel);
            TextView tvOk2 = view2.findViewById(R.id.tv_ok);
            tvTitle2.setText("这是侧边弹窗");
            tvContent2.setText("侧边弹窗的内容");
            tvCancel2.setOnClickListener(v1 -> {
                dialog.dismiss();
            });
            tvOk2.setOnClickListener(v1 -> {
                dialog.dismiss();
                Snackbar.make(tvTime, "点击了确认", Snackbar.LENGTH_SHORT).show();
            });
            dialog.setContentView(view2);
            dialog.show();
        });



        //TimePickerDialog
        findViewById(R.id.tv_time_picker_dialog).setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog dialog = new TimePickerDialog(this, (view12, hourOfDay, minute) -> {
                String message = "选择了" + hourOfDay + "时" + (minute + 1) + "分";
                Snackbar.make(tvTime, message, Snackbar.LENGTH_SHORT).show();
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            dialog.show();
        });
    }

    @Override
    public void onBackPressed() {
        if (noCancelPopupWindow != null && noCancelPopupWindow.isShowing()) {
            //PopupWindow显示的时候禁用返回键
        } else {
            super.onBackPressed();
        }
    }
}