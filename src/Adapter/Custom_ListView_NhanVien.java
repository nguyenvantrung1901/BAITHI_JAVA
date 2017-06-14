package Adapter;

import java.util.List;

import com.example.qlnhanvien.R;

import DTO.NhanVienDTO;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Custom_ListView_NhanVien extends ArrayAdapter<NhanVienDTO> {
	Context context;
	int resource;
	List<NhanVienDTO> objects;
	
	public Custom_ListView_NhanVien(Context context, int resource,
			List<NhanVienDTO> objects) {
		super(context, resource, objects);
		this.context=context;
		this.resource=resource;
		this.objects=objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewrow = inflater.inflate(R.layout.custom_layout_nhanvien, parent,false);
		
		TextView vTenNhanVien = (TextView)viewrow.findViewById(R.id.tvTenNhanVien);
		TextView vGioiTinh = (TextView)viewrow.findViewById(R.id.vGioiTinh);
		TextView vSoDienThoai = (TextView)viewrow.findViewById(R.id.vSoDienThoai);
		
		NhanVienDTO nhanvien = objects.get(position);
		
		vTenNhanVien.setText(nhanvien.getTennv());
		vGioiTinh.setText(nhanvien.getGioitinh());
		vSoDienThoai.setText(nhanvien.getSdt());
		
		return viewrow;
	}
}
