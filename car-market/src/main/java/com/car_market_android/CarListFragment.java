package com.car_market_android;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.car_market_android.model.Listing;
import com.car_market_android.network.CarMarketClient;
import com.car_market_android.util.EventsBus;
import com.car_market_android.views.ICarListView;

import java.util.LinkedList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class CarListFragment extends CarMarketFragment implements OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener,
        ICarListView {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private ListView Vehicle_Listview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CarListAdapter vadp;
    private LinkedList<Listing> data = new LinkedList<Listing>();

    /**
     * isLoadingMore is used to prevent the LOAD_MORE action being perform again
     * while it is performing.
     */
    private boolean isLoadingMore = false;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CarListFragment newInstance(int sectionNumber) {
        CarListFragment fragment = new CarListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public CarListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        EventsBus.getInstance().register(this);

        View rootView = inflater.inflate(R.layout.marketplace_fragment, container, false);

        this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_vehicle_index);
        this.swipeRefreshLayout.setColorSchemeResources(R.color.dark_blue, R.color.dark_green, R.color.dark_red, R.color.dark_yellow);
        this.swipeRefreshLayout.setEnabled(false);

        this.Vehicle_Listview = (ListView) rootView.findViewById(R.id.vehicle_index_list);

        this.vadp = new CarListAdapter(getActivity(), data);
        this.Vehicle_Listview.setAdapter(this.vadp);

        this.swipeRefreshLayout.setOnRefreshListener(this);

        this.Vehicle_Listview.setOnScrollListener(this);
        this.Vehicle_Listview.requestFocus();

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                Toast.makeText(getActivity(), "Unexpected button pressed...", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDestroy() {
        EventsBus.getInstance().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (this.data.size() == 0) {
            this.onRefresh();
        }
    }

    @Override
    public void onRefresh() {

        this.swipeRefreshLayout.setRefreshing(true);
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {

                // TODO: add swipe to reload feature
                data.clear();
                // new GetRequest(R.string.REFRESH_MARKETPLACE).execute(getString(R.string.CM_API_ADDRESS) + "/listings");

                /**
                 * Modify the limit and offset parameters to enable the "load more" feature
                 * */
                CarMarketClient.getInstance(getActivity()).getListings(1, 0, new Callback<List<Listing>>() {

                    @Override
                    public void success(List<Listing> listings, Response response) {
                        for (Listing each : listings) {
                            data.add(each);
                        }

                        vadp.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        /**
                         * This message is for debug mode.
                         * */
                        Toast.makeText(getActivity(), retrofitError.getMessage(), Toast.LENGTH_SHORT).show();
                        /**
                         * This message is for production mode.
                         * */
                        Toast.makeText(getActivity(), "Connection failed, please try again :(", Toast.LENGTH_SHORT).show();
                    }

                });


                // swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.swipeRefreshLayout.setEnabled(firstVisibleItem == 0);

        boolean loadMore = (firstVisibleItem + visibleItemCount >= totalItemCount);

        // TODO: add swipe to load more feature
        if (loadMore && !isLoadingMore && this.data.size() > 0) {

            // 1. limiter used to stop the load more feature
            if (this.data.size() >= 6) {
                return;
            }

            this.isLoadingMore = true;
            showProgressDialog(R.string.loading_more);

            // 2. download additional data
            /**
             * Modify the limit and offset parameters to enable the "load more" feature
             * */
            CarMarketClient.getInstance(getActivity()).getListings(1, 0, new Callback<List<Listing>>() {

                /*
                 * the implementation is different from "Swipe to Reload"
                 * */
                @Override
                public void success(List<Listing> listings, Response response) {
                    onReceiveCarListSucceed(listings);
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    onReceiveCarListFailed(retrofitError.getMessage());
                }

            });
        }
    }

    @Override
    public void onReceiveCarListSucceed(List<Listing> listings) {
        data.addAll(listings);
        vadp.notifyDataSetChanged();
        isLoadingMore = false;
        dismissProgressDialog();
    }

    @Override
    public void onReceiveCarListFailed(String errorMessage) {
        dismissProgressDialog();
        /**
         * This message is for debug mode.
         * */
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
        /**
         * This message is for production mode.
         * */
        Toast.makeText(getActivity(), "Connection failed, please try again :(", Toast.LENGTH_SHORT).show();
    }
}
