package com.example.qlnhanvien;

import java.util.ArrayList;
import java.util.List;

import Adapter.Custom_ListView_NhanVien;
import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class NhanVienActivity extends Activity {
	NhanVienDAO dbNhanVien;
	List<NhanVienDTO> listNhanVien;
	Custom_ListView_NhanVien adapter;
	ListView listviewNhanVien;
	int vitri;
	int idnhanvien;
	public static int RESULT_CAPNHATNHANVIEN = 100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_nhanvien);
		
		LinearLayout layout_nhanvien = (LinearLayout)findViewById(R.id.layout_nhanvien);
		
		
		dbNhanVien = new NhanVienDAO(this);
		listNhanVien = new ArrayList<NhanVienDTO>();
		
		LoadListviewNhanVien();
		registerForContextMenu(layout_nhanvien);
		registerForContextMenu(listviewNhanVien);
		
		listviewNhanVien.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				vitri=arg2;
				return false;
			}
		});
		
		listviewNhanVien.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent iChiTietNhanVien = new Intent(NhanVienActivity.this,ChiTietNhanVienActivity.class);
				iChiTietNhanVien.putExtra("manv", listNhanVien.get(arg2).getManv());
				startActivity(iChiTietNhanVien);
			}
		});
		
	}
	
	private void LoadListviewNhanVien(){
		listNhanVien = dbNhanVien.LoadAllNhanVien();
		adapter = new Custom_ListView_NhanVien(this,R.layout.custom_layout_nhanvien,listNhanVien);
		listviewNhanVien = (ListView)findViewById(R.id.listNhanVien);
		listviewNhanVien.setAdapter(adapter);
	}
	
	private void XoaNhanVien(){
		 idnhanvien = listNhanVien.get(vitri).getManv();
		if(dbNhanVien.XoaNhanVien(idnhanvien)!=-1){
			Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getApplicationContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.menu_chucnang, menu);
		
		if(v.getId()==R.id.listNhanVien){
			menu.getItem(0).setVisible(false);
			menu.getItem(1).setVisible(false);
			menu.getItem(2).setVisible(false);
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.menuThem){
			Intent iThemNhanVien = new Intent(NhanVienActivity.this, ThemNhanVienActivity.class);
			startActivity(iThemNhanVien);
		}
		if(id==R.id.menuXoa){
			XoaNhanVien();
			LoadListviewNhanVien();
		}
		if(id==R.id.menuSua){
			Intent iCapNhatNhanVien = new Intent (NhanVienActivity.this,CapNhatNhanVien.class);
			int idnhanvien = listNhanVien.get(vitri).getManv();
			iCapNhatNhanVien.putExtra("manv", idnhanvien);
			startActivityForResult(iCapNhatNhanVien, RESULT_CAPNHATNHANVIEN);
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == RESULT_CAPNHATNHANVIEN && resultCode==RESULT_OK){
			LoadListviewNhanVien();
		}
	}
}
