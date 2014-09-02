package com.car_market_android;

import java.util.LinkedList;

import com.car_market_android.model.Listing;
import com.car_market_android.model.Vehicle;
import com.car_market_android.util.EventsBus;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MyVehicle extends Activity {
	
	private ListView MyVehicle_Listview;
	private SwipeRefreshLayout swipeRefreshLayout;
	private MyVehicle_VehicleAdapter adapter;
	private LinkedList<Vehicle> data = new LinkedList<Vehicle>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_vehicles);
		
		EventsBus.getInstance().register(this);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		this.swipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.my_vehicle_swipe);
		this.swipeRefreshLayout.setColorScheme(R.color.dark_blue, R.color.dark_green, R.color.dark_red, R.color.dark_yellow);
		this.swipeRefreshLayout.setEnabled(false);

		this.MyVehicle_Listview = (ListView) this.findViewById(R.id.my_vehicle_list);

		this.adapter = new MyVehicle_VehicleAdapter(this, data);
		this.MyVehicle_Listview.setAdapter(this.adapter);
		this.MyVehicle_Listview.requestFocus();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_add:
			Toast.makeText(this, "add button on action bar is clicked...", Toast.LENGTH_LONG).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
