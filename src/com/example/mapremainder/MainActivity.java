package com.example.mapremainder;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;





import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;




import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements LocationListener, SensorEventListener {
 DialogInterface d1;
	 public final Notes[] mr=new Notes[2000];
	LatLng p;
	private ArrayList<Marker> mMarkerArray = new ArrayList<Marker>();
	Marker marker12[]=new Marker[2000];
	EditText e;
	Marker marker23;
	double lat1[]=new double[2000];
	public int markersl=1;
	double long1[]=new double[2000];
	private GoogleMap googleMap;
	double lati,latitude,longitude;
	double longi;
	 public String qer;
	public String s1[]=new String[2000];
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			markersl=1;
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			
			LatLng latlng;
			GPSTracker gps = new GPSTracker(MainActivity.this);
			 if(gps.canGetLocation())
				{
				 if(gps.getLatitude()==0&&gps.getLongitude()==0)
					 latlng = new LatLng(9.7000, 76.7000);
				 else
				 latlng = new LatLng(gps.getLatitude(),gps.getLongitude());
				//	Toast.makeText(getApplicationContext(), gps.getLatitude()+"  "+gps.getLongitude(),Toast.LENGTH_LONG).show();
									}
			 else
				latlng = new LatLng(9.7000, 76.7000);
			
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
		                  R.id.map)).getMap();
			
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
			
			//googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			
			if(gps.canGetLocation())
			{
				marker23 = googleMap.addMarker(new MarkerOptions().position(latlng).title("we are here")
                        .snippet("here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

			}
			
			
			
			 final DatabaseHandler db1 = new DatabaseHandler(this);
		        List<Notes> contacts = db1.getAllContacts(); 
		        if(contacts.isEmpty())Toast.makeText(getApplicationContext(), "empty",Toast.LENGTH_SHORT).show();
		      final ArrayList<String> list = new ArrayList<String>();
		       int p1=1;
		        for (Notes cn : contacts) {
		       	   String[] tokens = cn.getName().split("_");
		       	   s1[p1]=cn.getName();
		       	   lat1[p1]=cn.getLatitude();
		       	   long1[p1]=cn.getLongitude();
		       	   mr[p1]=cn;
		       	   
		           list.add(tokens[0]);
		           System.out.print(cn.getName());
		           String log1 = "Id: "+cn.getID()+" ,Name: " + cn.getName().toString() + " ,Latitude: " + cn.getLatitude()+ " ,Longitude: " + cn.getLongitude();
		          Log.d("Name: ", log1);
		          
		             
		              marker12[p1] = googleMap.addMarker(new MarkerOptions().position(new LatLng(lat1[p1], long1[p1])).title(s1[p1])
	                          .snippet("here"));
marker12[p1].setVisible(true);
//Toast.makeText(getApplicationContext(),"latitude:"+lat1[p1]+" longitude:"+long1[p1]+"  "+p1, Toast.LENGTH_LONG).show();

	                
		             p1++;
		             markersl++;
		          
		        }
			
		      
		        Toast.makeText(getApplicationContext(), "here marks :"+markersl, Toast.LENGTH_SHORT).show();
			
			
		        final DatabaseHandler db = new DatabaseHandler(this);
		        googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
		            @Override
		            public void onInfoWindowClick(Marker marker) {
		            	
		            	for(int i=1;i<markersl;i++)
		            	{
		            		 Toast.makeText(getApplicationContext(), "check :"+i+" "+markersl, Toast.LENGTH_SHORT).show();
		            	if(marker12[i].equals(marker))
		            	{
		            		final int u=i;
		            		String h1[]=s1[i].split("_");
		            		
		            		AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
		            		 
		                    // Setting Dialog Title
		                    alertDialog.setTitle(h1[0]);
		             
		                    // Setting Dialog Message
		                    alertDialog.setMessage(s1[i]);
		             
		                    
		                    // Setting Positive "Yes" Button
		                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		                        public void onClick(DialogInterface dialog,int which) {
		             
		                        // Write your code here to invoke YES event
		                     
		                        }
		                    });
		             
		                    // Setting Negative "NO" Button
		                    alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
		                        public void onClick(DialogInterface dialog, int which) {
		                      
		                        	// Write your code here to invoke NO event
		                      	 db.deleteContact(mr[u]);
		                      	marker12[u].setVisible(false);
		                        	if(u<(markersl-1))
		                        	{
		                        		Toast.makeText(getApplicationContext(), "oops:"+u, Toast.LENGTH_SHORT).show();
		                        		 
		                        		for(int j=u;j<markersl;j++)
		                        {
		                        	marker12[j]=marker12[j+1];
		                        	s1[j]=s1[j+1];
		                        	lat1[j]=lat1[j+1];
		                        	long1[j]=long1[j+1];
		                        	mr[j]=mr[j+1];
		                        	marker12[j]=marker12[j+1];
		                        	
		                        	 Toast.makeText(getApplicationContext(), "check :"+mr[u].getName(), Toast.LENGTH_SHORT).show();
		                        }
		                        	}
		                        	else{
		                        		Toast.makeText(getApplicationContext(), "fine", Toast.LENGTH_SHORT).show();
		                        	}
		                        
		                        markersl--;
		                       
		                        Toast.makeText(getApplicationContext(), "here marks :"+markersl, Toast.LENGTH_SHORT).show();
		                      //  Toast.makeText(getApplicationContext(), "oops:"+  mr[markersl-2].getLatitude(), Toast.LENGTH_SHORT).show();
		                      
		                     
		                        dialog.cancel();
		                        }
		                    });
		             
		                    // Showing Alert Message
		                    alertDialog.show();
		            		
		            		
		            	}
		            	//Toast.makeText(getApplicationContext(), s[i+1],Toast.LENGTH_SHORT).show();
		            	}
		            }
		        });
			
			
			
			
			
			
			
			
			
			
			
			googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
				
		        @Override
		        public void onMapClick(LatLng point) {
		            // TODO Auto-generated method stub
		        	 p=point;
		   /*     	 MarkerOptions marker = new MarkerOptions().position(
		                        new LatLng(point.latitude, point.longitude)).title("New Marker");
		        	

		                googleMap.addMarker(marker);*/
		       //  Toast.makeText(getApplicationContext(), point.latitude+"---"+ point.longitude, Toast.LENGTH_SHORT).show();
		       lati=point.latitude;
		       longi=point.longitude;
		         AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
		 		alert.setTitle("Add note");
		 	
		 		e = new EditText(MainActivity.this);
		 		e.setSingleLine(false); 
		 		e.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
		 		e.setHeight(500);
		 		alert.setView(e);
		 		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		 			public void onClick(DialogInterface dialog, int whichButton) {

		 			 addnote();
		 			  }
		 			});

		 			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		 			  public void onClick(DialogInterface dialog, int whichButton) {
		 			    // Canceled.
		 			  }
		 			});

		         	alert.show();
		        
		        }
		        
		    });

		    }
		 public void addnote(){
			 
			 GPSTracker gps = new GPSTracker(MainActivity.this);
			 if(gps.canGetLocation())
				{
				 
				//	Toast.makeText(getApplicationContext(), gps.getLatitude()+"  "+gps.getLongitude(),Toast.LENGTH_LONG).show();
									}

			 
			 String s=e.getText().toString().trim();
		  latitude =lati;
          longitude = longi;
         // Toast.makeText(getApplicationContext(),"latitude:"+latitude+" longitude:"+longitude, Toast.LENGTH_LONG).show();   
          Log.d("Insert: ", "Inserting ..");
          Context context = this;  
          Geocoder gcd = new Geocoder(context, Locale.getDefault());
             // addresses;
			try {
				List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
				 
				if (addresses.size() > 0) 
				{
					
					qer=addresses.get(0).getAddressLine(0).toString();
					String h=s;
					s=qer+"_\n";
					s=s+h;
				//	Toast.makeText(getApplicationContext(),qer, Toast.LENGTH_LONG).show();   
					
					
				}
				
	                 // System.out.println(addresses.get(0).getLocality());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
              
			 marker12[markersl] = googleMap.addMarker(new MarkerOptions().position(new LatLng(p.latitude, p.longitude)).title(s)
                     .snippet("here"));
marker12[markersl].setVisible(true);
			
			s1[markersl]=s;
			lat1[markersl]=p.latitude;
			long1[markersl]=p.longitude;
			markersl++;
			   Toast.makeText(getApplicationContext(), "here marks :"+markersl, Toast.LENGTH_SHORT).show();
 		 DatabaseHandler db = new DatabaseHandler(this);
 		 
          db.addNote(new Notes(s, latitude,longitude,0.00));
         // Toast.makeText(getApplicationContext(), e.getText().toString(), Toast.LENGTH_LONG).show();  
          
  Log.d("Reading: ", "Reading all contacts..");
          List<Notes> contacts = db.getAllContacts();       
          markersl=1;
          for (Notes cn : contacts) {
        	  
              String log1 = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Latitude: " + cn.getLatitude()+ " ,Longitude: " + cn.getLongitude();
                  // Writing Contacts to log
              Log.d("Name: ", log1);	
              mr[markersl]=cn;
              markersl++;
	}
          
		 }
		 
		 @Override
			public boolean onCreateOptionsMenu(Menu menu) {
				MenuInflater inflater = getMenuInflater();
				inflater.inflate(R.menu.activity_main_actions, menu);
				return super.onCreateOptionsMenu(menu);
			}
		    public boolean onOptionsItemSelected(MenuItem item) {
			     switch (item.getItemId()) {
			            case R.id.action_location_found:
			            	
			            ddemo();
			            return true;
			            
			            case R.id.find:
			            	checkin();
			            	return true;
			            case R.id.tracker:
			            	trackme();
			            	return true;
			       
			        default:
			            return super.onOptionsItemSelected(item);
			        }
			 }
		    public void ddemo()
		    {
		    	
		    	// setContentView(R.layout.listitem);
		    	 AlertDialog.Builder alert=new AlertDialog.Builder(this);
		 		alert.setTitle("Your Checks");
		 		ListView l=new ListView(this);
		 		 final DatabaseHandler db1 = new DatabaseHandler(this);
			        List<Notes> contacts = db1.getAllContacts(); 
			      final ArrayList<String> list = new ArrayList<String>();
			       int p=1;
			        for (Notes cn : contacts) {
			       	   String[] tokens = cn.getName().split("_");
			       	   s1[p]=cn.getName();
			       	   lat1[p]=cn.getLatitude();
			       	   long1[p]=cn.getLongitude();
			       	   mr[p]=cn;
			       	   p++;
			           list.add(tokens[0]);
			           System.out.print(cn.getName());
			           String log1 = "Id: "+cn.getID()+" ,Name: " + cn.getName().toString() + " ,Latitude: " + cn.getLatitude()+ " ,Longitude: " + cn.getLongitude();
			          Log.d("Name: ", log1);
			        }
			        final DatabaseHandler db = new DatabaseHandler(this);
				       final ArrayAdapter adapter = new ArrayAdapter(this,
					        android.R.layout.simple_list_item_1, list);
					       l.setAdapter(adapter);
					       alert.setView(l);
							alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int whichButton) {
									d1=dialog;
									dialog.cancel();
							
								  }
								});

								alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
								  public void onClick(DialogInterface dialog, int whichButton) {
								    // Canceled.
								  }
								});

					        	alert.show();
					       l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						 @Override
				     public void onItemClick(AdapterView<?> parent, final View view,
				         int position, long id) {
					
				    	final int ui=position;
				    	
				    	 Toast.makeText(getApplicationContext(),lat1[position+1]+" "+long1[position+1], Toast.LENGTH_LONG).show();   
							
				    	 LatLng latlng11 = new LatLng(lat1[position+1], long1[position+1]);
				    	 googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng11, 20));
							googleMap.animateCamera(CameraUpdateFactory.zoomTo(20), 2000, null);
				   	                 }
				           });
		     /*   final DatabaseHandler db1 = new DatabaseHandler(this);
		        List<Notes> contacts = db1.getAllContacts(); 
		      final ArrayList<String> list = new ArrayList<String>();
		       int p=1;
		        for (Notes cn : contacts) {
		       	   String[] tokens = cn.getName().split("_");
		       	   s[p]=cn.getName();
		       	   mr[p]=cn;
		       	   p++;
		           list.add(tokens[0]);
		           System.out.print(cn.getName());
		           String log1 = "Id: "+cn.getID()+" ,Name: " + cn.getName().toString() + " ,Latitude: " + cn.getLatitude()+ " ,Longitude: " + cn.getLongitude();
		          Log.d("Name: ", log1);
		        }
		       ListView l=(ListView) findViewById(R.id.listView1);
		       final DatabaseHandler db = new DatabaseHandler(this);
		       final ArrayAdapter adapter = new ArrayAdapter(this,
			        android.R.layout.simple_list_item_1, list);
			       l.setAdapter(adapter);
			       l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				 @Override
		     public void onItemClick(AdapterView<?> parent, final View view,
		         int position, long id) {
		    	final int ui=position;
		    	 AlertDialog.Builder show = new AlertDialog.Builder(MainActivity.this);
		    	 show.setTitle("Your check-in");
				    show.setMessage(s[position+1]);
				    show.setPositiveButton("Back", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				           dialog.cancel();
				        }
				     });
		    	 show.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            db.deleteContact(mr[ui+1]);
				           ddemo();
				          
				        }
				     });
		    	 show.show();
		   	                 }
		           });*/
		          
		    }
		 
		    
		    public boolean haveNetworkConnection() {
		        boolean haveConnectedWifi = false;
		        boolean haveConnectedMobile = false;

		        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		        for (NetworkInfo ni : netInfo) {
		            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
		                if (ni.isConnected())
		                    haveConnectedWifi = true;
		            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
		                if (ni.isConnected())
		                    haveConnectedMobile = true;
		        }
		        return haveConnectedWifi || haveConnectedMobile;
		    }
			@Override
			public void onLocationChanged(Location arg0) {
				// TODO Auto-generated method stub
				marker23.remove();
				marker23 = googleMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(),arg0.getLongitude())).title("we are here")
                        .snippet("here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
				
			}
			@Override
			public void onAccuracyChanged(Sensor arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onSensorChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				
			}
			public void checkin()
			{
				GPSTracker gps = new GPSTracker(MainActivity.this);
				if(gps.canGetLocation()){
			           
		             latitude = gps.getLatitude();
		              longitude = gps.getLongitude();
		              Log.d("1 "+latitude,"fwef");
			 final DatabaseHandler db = new DatabaseHandler(this);
			 int y=0;
			 List<Notes> contacts = db.getAllContacts(); 
			 
			 for (Notes cn : contacts) {
				
				 final Notes me=cn;
				 double km=distance(cn.getLatitude(),cn.getLongitude(),latitude,longitude);
			 if(km<0.1){
				 new AlertDialog.Builder(this)
				    .setTitle("Your check-in")
				    .setMessage(cn.getName())
				    .setPositiveButton("Back", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				           dialog.cancel();
				        }
				     })
				    .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            db.deleteContact(me);
				        }
				     })
				    
				     .show();
				 y=1;
				 
			 }
			 }
			 if(y==0)
			 {
				   Toast.makeText(getApplicationContext(),"No Checkin", Toast.LENGTH_LONG).show();   
		            y=0;
			 }
			 
			/* setContentView(R.layout.listitem);
			 in=1;
	         List<Notes> contacts = db.getAllContacts(); 
	         final ArrayList<String> list = new ArrayList<String>();
	         for (Notes cn : contacts) {
	        	 String[] tokens = cn.getName().split(" ");
	            list.add(tokens[0]);
	            System.out.print(cn.getName());
	            String log1 = "Id: "+cn.getID()+" ,Name: " + cn.getName().toString() + " ,Latitude: " + cn.getLatitude()+ " ,Longitude: " + cn.getLongitude();
	            // Writing Contacts to log
	        Log.d("Name: ", log1);
	         }
	  ListView l=(ListView) findViewById(R.id.listView1);
	  final ArrayAdapter adapter = new ArrayAdapter(this,
		        android.R.layout.simple_list_item_1, list);
		    l.setAdapter(adapter);*/
		    
	     }
			}
			 private double distance(double lat1, double lon1, double lat2, double lon2) {
		    	  double theta = lon1 - lon2;
		    	  double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		    	  dist = Math.acos(dist);
		    	  dist = rad2deg(dist);
		    	  dist = dist * 60 * 1.1515;
		    	
		    	    dist = dist * 1.609344;
		    	  
		    	  return (dist);
		    	}
			 private double deg2rad(double deg) {
		    	  return (deg * Math.PI / 180.0);
		    	}
		    private double rad2deg(double rad) {
		    	  return (rad * 180 / Math.PI);
		    	}
		    public void trackme()
		    {
		    	if(!isMyServiceRunning()){
		    	  Intent i= new Intent(getApplicationContext(), MyService.class);
		    		// potentially add data to the intent
		    		i.putExtra("KEY1", "Value to be used by the service");
		    		this.startService(i); 
		    		finish();
		    	}
		    	else
		    	{
		    		 stopService(new Intent(MainActivity.this, MyService.class));
		    	}
		    }
		    private boolean isMyServiceRunning() {
			    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
			    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			        if (MyService.class.getName().equals(service.service.getClassName())) {
			            return true;
			        }
			    }
			    return false;
			}
		 }