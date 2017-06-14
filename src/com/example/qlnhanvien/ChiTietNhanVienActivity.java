package com.example.qlnhanvien;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode.VmPolicy;
import android.widget.TextView;

public class ChiTietNhanVienActivity extends Activity {
	TextView vTenNV, vGioiTinh, vSoDienThoai, vDiaChi, vNgaySinh, vPhongBan, vEmail, vLuong;
	NhanVienDAO dbNhanVien;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_chitietnhanvien);
	
		vTenNV = (TextView)findViewById(R.id.vCTTenNhanVien);
		vGioiTinh = (TextView)findViewById(R.id.vCTGioiTinh);
		vSoDienThoai = (TextView)findViewById(R.id.vCTSoDienThoai);
		vDiaChi = (TextView)findViewById(R.id.vHTDiaChi);
		vPhongBan = (TextView)findViewById(R.id.vHTPhongBan);
		vEmail = (TextView)findViewById(R.id.vHTEmail);
		vNgaySinh = (TextView)findViewById(R.id.vHTNgaySinh);
		vLuong = (TextView)findViewById(R.id.vHTLuong);
	
		dbNhanVien = new NhanVienDAO(this);
		
		int id = getIntent().getExtras().getInt("manv");
		NhanVienDTO nhanvien = new NhanVienDTO();
		nhanvien=dbNhanVien.LayNhanVienTheoMa(id);
		
		vTenNV.setText(nhanvien.getTennv().toString());
		vGioiTinh.setText(nhanvien.getGioitinh().toString());
		vSoDienThoai.setText(nhanvien.getSdt().toString());
		vDiaChi.setText(nhanvien.getDiachi().toString());
		vEmail.setText(nhanvien.getEmail().toString());
		vNgaySinh.setText(nhanvien.getNgaysinh());
		vPhongBan.setText(nhanvien.getTenphongban().toString());
		vLuong.setText(String.valueOf(nhanvien.getLuong()) + " VNĐ");
	}
}
