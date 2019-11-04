package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;



import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText ed_ID, ed_pass, ed_role;
    Button btnSave, btnSelect,btnDelete, btnUpdate;
    //ListView listView;

    ArrayList<NhanVien> list;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_ID = findViewById(R.id.ed_ID);
        ed_pass = findViewById(R.id.ed_pass);
        ed_role = findViewById(R.id.ed_role);

        btnSave = findViewById(R.id.btnSave);
        btnSelect = findViewById(R.id.btnSelect);
        btnDelete = findViewById(R.id.btnDeltete);
        btnUpdate = findViewById(R.id.btnUpdate);
        //listView = findViewById(R.id.listView);
        gridView = findViewById(R.id.listView);

        list = selectAll();
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayGirdView();

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NhanVien nv = new NhanVien(ed_ID.getText().toString(), ed_pass.getText().toString(), ed_role.getText().toString());
                insert(nv);
                displayGirdView();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNV(ed_ID.getText().toString());
                displayGirdView();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NhanVien nv = new NhanVien(ed_ID.getText().toString(), ed_pass.getText().toString(), ed_role.getText().toString());
                updateNV(nv);
            }
        });



    }
    public void displayGirdView(){
        // setAdapters();
        ArrayList<String> arrayList = new ArrayList<>();
        list = selectAll();
        for(NhanVien a : list){
            arrayList.add(a.getId() + "");
            arrayList.add(a.getName());
            arrayList.add(a.getAddress());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
        gridView.setAdapter(adapter);
    }
//    public void setAdapters() {
//        if (adapters == null) {
//            adapters = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
//            listView.setAdapter(adapters);
//        }
//        else
//            adapters.notifyDataSetChanged();
//    }
    public ArrayList<NhanVien> selectAll() {
        ArrayList<NhanVien> ds = new ArrayList<>();
        Uri uri = NVProvider.CONTENT_URI;
        Cursor cur = getContentResolver().query(uri, null, "Select * from nhanvien", null, null);

        if (cur != null)
            cur.moveToFirst();

        while (cur.isAfterLast() == false) {
            ds.add(new NhanVien(cur.getString(0),cur.getString(1), cur.getString(2)));
            cur.moveToNext();
        }
        cur.close();
        return ds;
    }
    public void insert(NhanVien nv){

        if (list.contains(nv))
            Toast.makeText(MainActivity.this, "Trùng mã", Toast.LENGTH_SHORT).show();
        else {

            Uri uri = NVProvider.CONTENT_URI;
            ContentValues values = new ContentValues();

            values.put("id", nv.getId());
            values.put("name", nv.getName());
            values.put("address", nv.getAddress());

            getContentResolver().insert(uri, values);

            list.add(nv);
            Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteNV(String id) {
        if (id.equals(""))
            Toast.makeText(this, "ID không được rỗng", Toast.LENGTH_SHORT).show();
        else {
            Uri uri = NVProvider.CONTENT_URI;
            int xoa = getContentResolver().delete(uri, id, null);
            Log.d("vị trí: ", "" + xoa);
            if (xoa > 0) {
                Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                NhanVien nv = new NhanVien(id);
                list.remove(nv);

               displayGirdView();
            }
            else
                Toast.makeText(this, "Không có account này", Toast.LENGTH_SHORT).show();
        }
    }
    public void updateNV(NhanVien nv) {

        Uri uri = NVProvider.CONTENT_URI;
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", nv.getName());
        contentValues.put("address", nv.getAddress());

        int kt = getContentResolver().update(uri, contentValues, nv.getId(), null);
        if (kt > 0) {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

            int vt = list.indexOf(nv);
            list.get(vt).setName(nv.getName());
            list.get(vt).setAddress(nv.getAddress());

            displayGirdView();
        }
        else
            Toast.makeText(this, "Không có nv này", Toast.LENGTH_SHORT).show();
    }
}
