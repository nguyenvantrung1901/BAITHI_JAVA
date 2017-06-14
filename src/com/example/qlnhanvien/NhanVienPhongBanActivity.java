package com.example.qlnhanvien;

import java.util.ArrayList;
import java.util.List;

import Adapter.Custom_ListView_NhanVien;
import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class NhanVienPhongBanActivity extends Activity{
	ListView listviewNhanVienPhongBan;
	NhanVienDAO dbNhanVien;
	List<NhanVienDTO> listNhanVien;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_nhanvien_phongban);
		
		listviewNhanVienPhongBan = (ListView)findViewById(R.id.listNhanVienPhongBan);
		dbNhanVien = new NhanVienDAO(this);
		listNhanVien = new ArrayList<NhanVienDTO>();
		
		Intent intent = getIntent();
		int mapb = Integer.parseInt(intent.getExtras().getString("maphongban"));
		listNhanVien = dbNhanVien.LayNhanVienTheoPhongBan(mapb);
		
		Custom_ListView_NhanVien adapter = new Custom_ListView_NhanVien(this,R.layout.custom_layout_nhanvien,listNhanVien);
		listviewNhanVienPhongBan.setAdapter(adapter);
		
		listviewNhanVienPhongBan.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent iChiTietNhanVien = new Intent(NhanVienPhongBanActivity.this,ChiTietNhanVienActivity.class);
				iChiTietNhanVien.putExtra("manv", listNhanVien.get(arg2).getManv());
				startActivity(iChiTietNhanVien);
				startActivity(iChiTietNhanVien);
			}
		});
	}
}
