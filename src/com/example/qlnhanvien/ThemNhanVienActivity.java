package com.example.qlnhanvien;

import java.util.ArrayList;
import java.util.List;

import Adapter.Custom_Spinner_ThemNhanVien;
import DAO.NhanVienDAO;
import DAO.PhongBanDAO;
import DTO.NhanVienDTO;
import DTO.PhongBanDTO;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class ThemNhanVienActivity extends Activity {
	Spinner spinnerPhongBan;
	EditText txtTenNhanVien, txtDiaChi, txtSoDienThoai, txtNgaySinh,txtLuong, txtEmail;
	Button btnThem, btnThoat;
	List<PhongBanDTO> list;
	PhongBanDAO dbPhongBan;
	NhanVienDAO dbNhanVien;
	int vitri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_themnhanvien);
		
		btnThem = (Button)findViewById(R.id.btnThemNhanVien);
		btnThoat= (Button)findViewById(R.id.btnThoatNhanVien);
		txtTenNhanVien = (EditText)findViewById(R.id.editThemTenNhanVien);
		txtSoDienThoai  = (EditText)findViewById(R.id.editThemSoDienThoai);
		txtNgaySinh  = (EditText)findViewById(R.id.editThemNgaySinh);
		txtLuong  = (EditText)findViewById(R.id.editThemLuong);
		txtEmail  = (EditText)findViewById(R.id.editThemEmail);
		txtDiaChi  = (EditText)findViewById(R.id.editThemDiaChi);
		
		dbPhongBan = new PhongBanDAO(this);
		dbNhanVien = new NhanVienDAO(this);
		
		spinnerPhongBan = (Spinner)findViewById(R.id.spinnerPhongBan);
		list = new ArrayList<PhongBanDTO>();
		list = dbPhongBan.LayAllPhongBan();
		
		Custom_Spinner_ThemNhanVien adapter = new Custom_Spinner_ThemNhanVien(this,R.layout.custom_layout_spinner,list);
		spinnerPhongBan.setAdapter(adapter);
		
		spinnerPhongBan.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				vitri = arg2;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btnThem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				RadioGroup rdGroup = (RadioGroup)findViewById(R.id.rdGioiTinh);
				int rdID = rdGroup.getCheckedRadioButtonId();
				RadioButton rdChecked = (RadioButton)findViewById(rdID);
				String gioitinh = rdChecked.getText().toString();
				int mapb = list.get(vitri).getMaphongban();
				
				try{
				NhanVienDTO nhanvien = new NhanVienDTO();
				nhanvien.setDiachi(txtDiaChi.getText().toString());
				nhanvien.setEmail(txtEmail.getText().toString());
				nhanvien.setGioitinh(gioitinh);
				nhanvien.setLuong(Integer.parseInt(txtLuong.getText().toString()));
				nhanvien.setMapb(mapb);
				nhanvien.setNgaysinh(txtNgaySinh.getText().toString());
				nhanvien.setSdt(txtSoDienThoai.getText().toString());
				nhanvien.setTennv(txtTenNhanVien.getText().toString());
				
				dbNhanVien.ThemNhanVien(nhanvien);
				Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
				finish();
				}catch(Exception e){
					Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btnThoat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
}
