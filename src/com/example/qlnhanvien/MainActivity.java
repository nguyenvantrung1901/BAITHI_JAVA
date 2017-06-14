package com.example.qlnhanvien;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	SharedPreferences sharePreferences;
	Button btnDangKy, btnThoatDK, btnDangNhap, btnThoatDN;
	EditText editTaiKhoanDK, editMatKhauDK, editNhapLaiMatKhauDK, editMatKhauDN, editTaiKhoanDN;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sharePreferences = getSharedPreferences("config", Context.MODE_PRIVATE);
		editTaiKhoanDN = (EditText)findViewById(R.id.editTenDangNhap);
		editMatKhauDN = (EditText)findViewById(R.id.editMatKhau);
		
	
	}
	
	public void DangNhap(View v){
		String matkhauconfig,taikhoanconfig, taikhoanDN, matkhauDN;
		taikhoanconfig = sharePreferences.getString("TaiKhoan", "");
		matkhauconfig = sharePreferences.getString("MatKhau", "");
		taikhoanDN = editTaiKhoanDN.getText().toString();
		matkhauDN = editMatKhauDN.getText().toString();
		
		if(taikhoanDN.equals(taikhoanconfig) && matkhauDN.equals(matkhauconfig)){
			Intent iPhongBan = new Intent(MainActivity.this,PhongBanActivity.class);
			startActivity(iPhongBan);
			
			Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == R.id.menuDangKy) {
			String taikhoan, matkhau;
			taikhoan = sharePreferences.getString("TaiKhoan", "");
			matkhau = sharePreferences.getString("MatKhau", "");
			
			if(taikhoan.trim().length()==0 || matkhau.trim().length()==0){
				final Dialog dal = new Dialog(this);
				dal.setContentView(R.layout.layout_dangky);
				dal.setTitle(R.string.titleDangKy);
				
				editMatKhauDK = (EditText)dal.findViewById(R.id.editMatKhauDK);
				editNhapLaiMatKhauDK = (EditText) dal.findViewById(R.id.editNhapLaiMatKhauDK);
				editTaiKhoanDK = (EditText) dal.findViewById(R.id.editTaiKhoanDK);
				
				btnDangKy = (Button)dal.findViewById(R.id.btnDangKy);
				btnDangKy.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						String matkhaudk, nhaplaimatkhaudk;
						
						matkhaudk = editMatKhauDK.getText().toString();
						nhaplaimatkhaudk = editNhapLaiMatKhauDK.getText().toString();
						
						if(!matkhaudk.equals(nhaplaimatkhaudk) ){
							Toast.makeText(getApplicationContext(), "Mật khẩu nhập lại không giống", Toast.LENGTH_LONG).show();
						}else{
							Editor edit = sharePreferences.edit();
							edit.putString("MatKhau", editMatKhauDK.getText().toString());
							edit.putString("TaiKhoan", editTaiKhoanDK.getText().toString());
							edit.commit();
							Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_LONG).show();
						}
					}
				});
				dal.show();
			}else{
				Toast.makeText(getApplicationContext(), "Bạn không thể đăng ký vì đã có tài khoản", Toast.LENGTH_LONG).show();
			}
			
		}
		return super.onOptionsItemSelected(item);
	}
	
}

	
