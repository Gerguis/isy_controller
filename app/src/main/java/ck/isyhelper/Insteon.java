package ck.isyhelper;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class Insteon
{

    private static Insteon instance;
    private Context context;
    private RequestQueue queue;


    private ArrayList<Node> nodes;

    public static Insteon getInstance(Context context)
    {
        if(instance == null)
            instance = new Insteon(context);

        return instance;
    }



    private Insteon(Context context)
    {
        this.context = context.getApplicationContext();
        queue = Volley.newRequestQueue(this.context);
    }




    private void sendRequest(StringRequest request)
    {
        queue.add(request);
    }





    public void getNodes()
    {
        String url = "/nodes";
    }

    public void getPrograms()
    {

    }


    public void getVariables()
    {

    }



    private class Node
    {

    }

}
