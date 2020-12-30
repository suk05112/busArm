package com.example.busalarm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link busStopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class busStopFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView text;
    //Button findbtn;
    EditText findedt;


    String key = "DBjO5k2HDl6f15lqMZ4HMO2QKB0mKd1YAeVI7HnRBAs042IBLA%2Fl8v1Qj7Gbea7VucOImrBug1gRKwKOOKTbiw%3D%3D";
    String data;

    private InputStream is;
    private XmlPullParserFactory factory;
    private XmlPullParser xpp;

    // xml의 값 입력 변수
    private String busNum; // 버스 번호
    private String stationArsno = ""; //출발 정류장 arsNo
    private StringBuffer buffer;
    // 데이터 검색
    private String busNumId; // 버스 번호 Id
    private String stationId;// 출발 정류소명 Id
    private String sStationArriveTime; // 버스의 정류장 도착정보
    String StationName;
    String mobileNo;

    ArrayList<BusData> busDataArrayList = new ArrayList<BusData>();
    ArrayList<SearchStop> searchStop = new ArrayList<SearchStop>();

    ListView listView;

    public busStopFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.d("tag", "this is stop fragment");
        //ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.fragment_bus_stop,container,false);
        View view = inflater.inflate(R.layout.fragment_bus_stop, container, false);
        //findedt = (EditText)view.findViewById(R.id.findedt);
        //findbtn = (Button)view.findViewById(R.id.findbtn);
        text = (TextView)view.findViewById(R.id.text);


        listView = (ListView)view.findViewById(R.id.busStopList);
        final MyAdapter myAdapter = new MyAdapter(getActivity(), searchStop);

        ((MainActivity)getActivity()).findbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("tag", "press button");
                switch(view.getId() ){
                    case R.id.findbtn:
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                data = getXmlData();//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기

                                getActivity().runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub
                                        if(data == null){
                                            Log.d("tag", "데이터 널");
                                        }

                                        myAdapter.notifyDataSetChanged();
                                        text.setText(data); //TextView에 문자열  data 출력
                                    }
                                });

                            }
                        }).start();

                        break;
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //String strText = (String)adapterView.getItemAtPosition(i) ;

                Log.d("tag", "click listview " + adapterView.getItemAtPosition(i));
            }
        });
        myAdapter.notifyDataSetChanged();

        listView.setAdapter(myAdapter);

        return view;
        //return inflater.inflate(R.layout.fragment_bus_stop, container, false);
    }

    String getXmlData(){
        Log.d("tag", "in function");
        StringBuffer buffer=new StringBuffer();

        String str= ((MainActivity)getActivity()).findedt.getText().toString();//EditText에 작성된 Text얻어오기
        String location = URLEncoder.encode(str);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding     //지역 검색 위한 변수


       /* String queryUrl="http://openapi.gbis.go.kr/ws/rest/" +
                "busrouteservice?serviceKey=DBjO5k2HDl6f15lqMZ4HMO2QKB0mKd1YAeVI7HnRBAs042IBLA%2Fl8v1Qj7Gbea7VucOImrBug1gRKwKOOKTbiw" +
                "%3D%3D&" +
                "routeId=200000085";//요청 URL
//                +"addr="+location
//                +"&pageNo=1&numOfRows=1000&ServiceKey=" + key;
*/

       /*
       //노선정보조회
        String queryUrl="http://openapi.gbis.go.kr/ws/rest/busrouteservice/station?" +
                "serviceKey=DBjO5k2HDl6f15lqMZ4HMO2QKB0mKd1YAeVI7HnRBAs042IBLA%2Fl8v1Qj7Gbea7VucOImrBug1gRKwKOOKTbiw%3D%3D&&" +
                "routeId=200000085";

        */
       //정류소 조회
        String queryUrl = "http://openapi.gbis.go.kr/ws/rest/busstationservice?" +
                "serviceKey=DBjO5k2HDl6f15lqMZ4HMO2QKB0mKd1YAeVI7HnRBAs042IBLA%2Fl8v1Qj7Gbea7VucOImrBug1gRKwKOOKTbiw%3D%3D" +
                "&keyword=" +
                ((MainActivity)getActivity()).findedt.getText();
        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;
            String str1;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("stationId")){
                            buffer.append("정류소 아이디 : ");
                            xpp.next();
                            stationId = xpp.getText();
                            buffer.append(xpp.getText());//addr 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }

                        else if(tag.equals("stationName")){
                            buffer.append("정류소명 :");
                            xpp.next();
                            StationName = xpp.getText();

                            buffer.append(xpp.getText());//


                            buffer.append("\n");
                        }
                        else if(tag.equals("mobileNo")){
                            buffer.append("정류소번호 :");
                            xpp.next();
                            mobileNo = xpp.getText();
                            buffer.append(xpp.getText());//
                            buffer.append("\n");
                        }
                        else if(tag.equals("staOrder")){
                            buffer.append("정류소순번 :");
                            xpp.next();
                            buffer.append(xpp.getText());//
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //태그 이름 얻어오기

                        if(tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈

                        break;
                }
                searchStop.add(new SearchStop(StationName, mobileNo, "방향"));
                eventType= xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }

        buffer.append("파싱 끝\n");

        return buffer.toString();//StringBuffer 문자열 객체 반환

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment busStopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static busStopFragment newInstance(String param1, String param2) {
        busStopFragment fragment = new busStopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


}