package com.example.qlnhanvien;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ThietLapMatKhau extends Activity {
	EditText editTenDangNhap, editMatKhau, editMatKhauMoi, editNhapLaiMatKhau, editTenDangNhapMoi;
	ToggleButton toggleTrangThai;
	RelativeLayout layoutThayDoi;
	Button btnLuu;
	SharedPreferences sharePreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_thaydoimatkhau);
		
		layoutThayDoi = (RelativeLayout)findViewById(R.id.khungThayDoi);
		editTenDangNhap = (EditText)findViewById(R.id.editText1);
		editMatKhau = (EditText)findViewById(R.id.editText2);
		editTenDangNhapMoi =(EditText)findViewById(R.id.editText3);
		editMatKhauMoi = (EditText)findViewById(R.id.editText4);
		editNhapLaiMatKhau = (EditText)findViewById(R.id.editText5);
		toggleTrangThai = (ToggleButton)findViewById(R.id.toggleButton1);
		btnLuu = (Button)findViewById(R.id.button1);
		
		sharePreferences = getSharedPreferences("config", 0);
		
		toggleTrangThai.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					layoutThayDoi.setVisibility(RelativeLayout.VISIBLE);
				}else{
					layoutThayDoi.setVisibility(RelativeLayout.GONE);
				}
			}
		});
		
		btnLuu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				thaydoimatkhau();
			}
		});
	}
	
	private void thaydoimatkhau(){
		String tentaikhoan = sharePreferences.getString("TaiKhoan", "");
		String matkhau = sharePreferences.getString("MatKhau", "");
		String tentaikhoannhap = editTenDangNhap.getText().toString();
		String matkhaunhap = editMatKhau.getText().toString();
		
		if(tentaikhoan.equals(tentaikhoannhap) && matkhau.equals(matkhaunhap)){
			String tentaikhoanmoi = editTenDangNhapMoi.getText().toString();
			String matkhaumoi = editMatKhauMoi.getText().toString();
			String nhaplaimatkhaumoi = editNhapLaiMatKhau.getText().toString();
			if(nhaplaimatkhaumoi.equals(matkhaumoi)){
				Editor edit = sharePreferences.edit();
				edit.putString("TaiKhoan", tentaikhoanmoi);
				edit.putString("MatKhau", matkhaumoi);
				edit.commit();
				Toast.makeText(getApplicationContext(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getApplicationContext(), "Mật khẩu nhập lại không giống!", Toast.LENGTH_SHORT).show();
			}
		}else{
			Toast.makeText(getApplicationContext(), "Tài khoản và mật khẩu không hợp lệ!", Toast.LENGTH_SHORT).show();
		}
	}
}
