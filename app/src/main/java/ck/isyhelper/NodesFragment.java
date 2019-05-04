package ck.isyhelper;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.FileInputStream;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import ck.isyhelper.nodes.ISYNode;


/**
 * A simple {@link Fragment} subclass.
 */
public class NodesFragment extends Fragment {

    private static final int REQUEST_NODES = 0;

    public NodesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nodes, container, false);

        ISYClient client = ISYClient.getInstance();

        client.getNodes(new ISYClient.ISYClientListener() {
            @Override
            public void onRequestSent() {}

            @Override
            public void onResponseReceived(String response, int requestCode) {
                if(requestCode == REQUEST_NODES)
                    parseNodesResponse(response);

            }

            @Override
            public void onError() {}
        }, REQUEST_NODES);

        return v;
    }


    private void parseNodesResponse(String response){
        ArrayList<ISYNode> retrievedNodes = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            // use the factory to create a documentbuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(new InputSource(new StringReader(response)));

            // get the first element
            Element element = doc.getDocumentElement();

            // get all child nodes
            NodeList nodes = element.getElementsByTagName("node");

            // print the text content of each child
            for (int i = 0; i < nodes.getLength(); i++) {



                    Element cNode = (Element) nodes.item(i);

                    String address = cNode.getElementsByTagName("address").item(0).getTextContent();
                    Log.w("Address", address + " ");

                    NodeList children = nodes.item(i).getChildNodes();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
