package ck.isyhelper;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.PreferenceChangeEvent;

import ck.isyhelper.nodes.ISYNode;

import static ck.isyhelper.Preferences.*;

public class ISYClient  {
    private static ISYClient instance;

    public interface ISYClientListener {
        void onRequestSent();
        void onResponseReceived(String response, int requestCode);
        void onError();
    }

    private RequestQueue queue;
    private String url;
    private String user;
    private String password;

    private ISYClientListener listener;

    public static ISYClient getInstance() {
        if(instance == null)
            instance = new ISYClient();

        return instance;
    }

    private ISYClient() {

    }

    public void initialize(Context context) {
        queue = Volley.newRequestQueue(context);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        url = prefs.getString(ISY_IP, null);
        user = prefs.getString(ISY_USER, null);
        password = prefs.getString(ISY_PASSWORD, null);
    }



    public void getNodes(final ISYClientListener listener, final int requestCode) {
        executeCommand("/rest/nodes/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onResponseReceived(response, requestCode);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        });
    }


    public void executeCommand(String command) {
        executeCommand(command, responseListener, errorListener);
    }

    public void executeCommand(String command, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(Request.Method.GET, "http://" + url + command, responseListener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                String credentials = user + ":" + password;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        //listener.onRequestSent();
        queue.add(request);
    }



    private Response.Listener<String> responseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            //listener.onResponseReceived();
            Log.w("test", response);
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //listener.onError();
        }
    };
}
