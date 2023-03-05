package com.example.BSafe;



import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class fragment1 extends Fragment implements SelectListener {

    View view;
    LinearLayout linearLayout;
    RecyclerView recyclerView;
    AdapterRecycler adapter;
    ArrayList<ModelClass> modelClassArrayList;

    protected FragmentActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MainActivity){
            mActivity =(MainActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_fragment1, container, false);

       // setContentView(R.layout.fragment_fragment1);

        recyclerView = view.findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        linearLayout = view.findViewById(R.id.linearContent);

        getFetch();
        return view;
    }
    private void getFetch() {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(mActivity,new String[]{Manifest.permission.READ_CONTACTS},0);
        }

        ContentResolver resolver = mActivity.getContentResolver(); //creating an resolver object.............
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        @SuppressLint("Recycle") Cursor cursor = resolver.query(uri, null, null, null, null);

        modelClassArrayList = new ArrayList<>();
        modelClassArrayList.clear();
        int counter = 0;

        if (cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                ModelClass modelClass = new ModelClass(name, number);
                modelClassArrayList.add(counter, modelClass);

                counter++;
            }
        }
        //Log.e("ContactArrayList", modelClassArrayList.toString());

        adapter = new AdapterRecycler(mActivity, modelClassArrayList,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(ModelClass myModel) {

        Toast.makeText(mActivity, myModel.getName(), Toast.LENGTH_SHORT).show();
        String phoneno= myModel.getNumber();
        Intent i=new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:"+phoneno));
        startActivity(i);

    }
}