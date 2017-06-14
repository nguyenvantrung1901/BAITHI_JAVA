package Adapter;

import java.util.List;

import com.example.qlnhanvien.R;

import DTO.PhongBanDTO;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Custom_Listview_PhongBan extends ArrayAdapter<PhongBanDTO>{
	Context context;
	int resource;
	List<PhongBanDTO> objects;
	
	public Custom_Listview_PhongBan(Context context, int resource,
			List<PhongBanDTO> objects) {
		super(context, resource, objects);
		this.context=context;
		this.resource=resource;
		this.objects=objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewrow = inflater.inflate(R.layout.custom_lisview_phongban, parent,false);
		
		TextView vTenPhongBan = (TextView)viewrow.findViewById(R.id.viewListTenPhongBan);
		TextView vMaPhongBan = (TextView)viewrow.findViewById(R.id.viewListMaPhongBan);
		TextView vSoNhanVien = (TextView)viewrow.findViewById(R.id.viewListSoNhanVien);
		
		PhongBanDTO phongban = objects.get(position);
		vTenPhongBan.setText(phongban.getTenphongban());
		vMaPhongBan.setText("Mã phòng ban : " + String.valueOf(phongban.getMaphongban()));
		vSoNhanVien.setText(String.valueOf(phongban.getSonhanvien()) + "Nhân viên");
		
		return viewrow;
	}

}
