package org.zankio.cculife.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.zankio.ccudata.bus.model.BusLineRequest;
import org.zankio.cculife.R;

public class CovidFragment extends Fragment {

            JSONObject jsonObject;

    public static Fragment getInstance(BusLineRequest requests) {
        return getInstance(new BusLineRequest[]{ requests });
    }

    public static Fragment getInstance(BusLineRequest[] requests) {
        CovidFragment fragment = new CovidFragment();
        //fragment.setArguments(getArguments(requests));
        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_covid, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        fetchdata();
    }

    public void fetchdata(){
        String url = "https://disease.sh/v3/covid-19/all";

        StringRequest request
                = new StringRequest(
                Request.Method.GET,
                url,
                response -> {

                    // Handle the JSON object and
                    // handle it inside try and catch
                    try {

                        // Creating object of JSONObject
                        jsonObject
                                = new JSONObject(
                                response.toString());

                        // Set the data in text view
                        // which are available in JSON format
                        // Note that the parameter inside
                        // the getString() must match
                        // with the name given in JSON format
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(
                            null,
                            error.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();
                });

        RequestQueue requestQueue
                = Volley.newRequestQueue(null);
        requestQueue.add(request);
    }

    public View getView(int position, View convertView, ViewGroup parent) throws JSONException {

        ((TextView) convertView.findViewById(R.id.tvCases)).setText(jsonObject.getString("cases"));
        ((TextView) convertView.findViewById(R.id.tvRecovered)).setText(jsonObject.getString("recovered"));
        ((TextView) convertView.findViewById(R.id.tvCritical)).setText(jsonObject.getString("critical"));
        ((TextView) convertView.findViewById(R.id.tvActive)).setText(jsonObject.getString("active"));
        ((TextView) convertView.findViewById(R.id.tvTodayCases)).setText(jsonObject.getString("todayCases"));
        ((TextView) convertView.findViewById(R.id.tvTotalDeaths)).setText(jsonObject.getString("deaths"));
        ((TextView) convertView.findViewById(R.id.tvTodayDeaths)).setText(jsonObject.getString("todayDeaths"));
        ((TextView) convertView.findViewById(R.id.tvAffectedCountries)).setText(jsonObject.getString("affectedCountries"));

        return convertView;
    }
}
