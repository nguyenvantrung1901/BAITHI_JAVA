package com.example.qlnhanvien;

import java.util.ArrayList;
import java.util.List;

import Adapter.Custom_Listview_PhongBan;
import DAO.PhongBanDAO;
import DTO.PhongBanDTO;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class PhongBanActivity extends Activity{
	ListView listviewPhongBan;
	LinearLayout layout_phongban;
	EditText editTenPhongBan;
	Button btnThem;
	PhongBanDAO dbPhongBan;
	Custom_Listview_PhongBan adapter;
	List<PhongBanDTO> listPhongBan;
	int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_phongban);
		
		listviewPhongBan = (ListView)findViewById(R.id.listPhongBan);
		layout_phongban = (LinearLayout)findViewById(R.id.layout_phongban);
		
		registerForContextMenu(listviewPhongBan);
		registerForContextMenu(layout_phongban);
		
		dbPhongBan = new PhongBanDAO(this);
		
		LoadListViewPhongBan();
		
		listviewPhongBan.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				position = arg2;
				return false;
			}
		});
		
		listviewPhongBan.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent iNhanVienPhongBan = new Intent(PhongBanActivity.this,NhanVienPhongBanActivity.class);
				iNhanVienPhongBan.putExtra("maphongban", String.valueOf(listPhongBan.get(arg2).getMaphongban()));
				startActivity(iNhanVienPhongBan);
			}
		});
	}
	
	public void LoadListViewPhongBan(){
		listPhongBan = new ArrayList<PhongBanDTO>();
		listPhongBan = dbPhongBan.LayAllPhongBan();
		adapter = new Custom_Listview_PhongBan(this,R.layout.custom_lisview_phongban,listPhongBan);
		listviewPhongBan.setAdapter(adapter);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("Chức năng");
		menu.setHeaderIcon(R.drawable.ic_menu_home);
		getMenuInflater().inflate(R.menu.menu_chucnang, menu);
		if(v.getId() == R.id.listPhongBan){
			menu.getItem(0).setVisible(false);
			menu.getItem(1).setVisible(false);
			menu.getItem(2).setVisible(false);
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.menuThem){
			final Dialog dal = new Dialog(this);
			dal.setTitle("Thêm phòng ban");
			dal.setContentView(R.layout.layout_themphongban);
			editTenPhongBan = (EditText)dal.findViewById(R.id.editTenPhongBan);
			btnThem = (Button)dal.findViewById(R.id.btnThem);
			dal.show();
			
			btnThem.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					PhongBanDTO phongban = new PhongBanDTO();
					phongban.setTenphongban(editTenPhongBan.getText().toString());
					dbPhongBan.ThemPhongBan(phongban);
					Toast.makeText(getApplicationContext(),"Thêm thành công", Toast.LENGTH_LONG).show();
					LoadListViewPhongBan();
					dal.dismiss();
				}
			});
			
		}
		if(id == R.id.menuXoa){
			int maphongban = listPhongBan.get(position).getMaphongban();
			if(dbPhongBan.XoaPhongBanTheoMa(maphongban) != -1){
				Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_LONG).show();
				LoadListViewPhongBan();
			}else{
				Toast.makeText(getApplicationContext(), "Xóa thất bại", Toast.LENGTH_LONG).show();
			}
			
		}
		if(id == R.id.menuSua){
			final Dialog dalsua = new Dialog(this);
			dalsua.setTitle("Sữa phòng ban");
			dalsua.setContentView(R.layout.layout_suaphongban);
			dalsua.show();
			final EditText editMaPhongBanSua = (EditText)dalsua.findViewById(R.id.editMaPhongBanSua);
			final EditText editTenPhongBanSua = (EditText)dalsua.findViewById(R.id.editTenPhongBanSua);
			Button btnSua = (Button)dalsua.findViewById(R.id.btnSua);
			
			editMaPhongBanSua.setEnabled(false);
			final String maphongban = String.valueOf(listPhongBan.get(position).getMaphongban());
			editMaPhongBanSua.setText(maphongban);
			
			btnSua.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					final PhongBanDTO phongban = new PhongBanDTO();
					
					phongban.setMaphongban(Integer.parseInt(maphongban));
					phongban.setTenphongban(editTenPhongBanSua.getText().toString());
					
					AlertDialog.Builder alb = new AlertDialog.Builder(PhongBanActivity.this);
					alb.setTitle("Thông báo");
					alb.setMessage("Bạn có muốn sữa không ?");
					alb.setPositiveButton("Có", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if(dbPhongBan.SuaPhongBanTheoMa(phongban) != -1){
								Toast.makeText(getApplicationContext(), "Sữa thành công", Toast.LENGTH_LONG).show();
								LoadListViewPhongBan();
								dalsua.dismiss();
							}else{
								Toast.makeText(getApplicationContext(), "Sữa thất bại", Toast.LENGTH_LONG).show();
							}
							
						}
					});
					alb.setNegativeButton("Không", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					alb.show();
				}
			});
			
			
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_hethong, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.menuPhongBan){
			Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
		}
		if(id == R.id.menuNhanVien){
			Intent iNhanVienActivity = new Intent(PhongBanActivity.this,NhanVienActivity.class);
			startActivity(iNhanVienActivity);
		}
		if(id == R.id.menuLienHe){
			Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
		}
		if(id == R.id.menuHeThong){
			Intent iHeThong = new Intent(PhongBanActivity.this,ThietLapMatKhau.class);
			startActivity(iHeThong);
		}
		return super.onOptionsItemSelected(item);
	}
}
