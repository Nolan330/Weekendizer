package example.fragments;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.UrlConnectionClient;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import example.web.model.City;
import example.web.requests.WeekendPlannerRequest;
import example.web.responses.CityInfoResponse;
import example.web.responses.WeekendPlannerResponse;
import example.web.services.WeekendPlannerService;
import example.weekendizer.R;

/**
 * The Fragment responsible for initially prompting the user
 * for their current city and a budget
 * TODO: deduce location based on GPS
 */
public class PromptFragment extends Fragment
						    implements OnClickListener {
	/**
	 * Fragment manager to swap out fragments 
	 */
	//private FragmentManager mFragmentManager;
	
	/**
	 * Dialog indicating the server is working
	 */
	private AlertDialog mAlertDialog;
	
	/**
	 * Cached references to relevant UI widgets
	 */
	private Spinner mOriginCityPrompt;
	private Spinner mDestinationCityPrompt;
	private LinearLayout mDestinationLayout;
	private EditText mBudgetPrompt;
	
	private Button mEnterDestinationButton;
	private Button mRemoveDestinationButton;
	private Button mWeekendizeButton;
	
	/**
	 * Retrofit service communicate with our server
	 */
	private WeekendPlannerService mWeekendPlannerService;
	
	/**
	 * A map of City names to IATA Codes for airport identification
	 */
	private Map<String, City> mCities;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);

        //mFragmentManager = getFragmentManager();
        
        mAlertDialog = 
			new AlertDialog.Builder(getActivity()).create();

		mOriginCityPrompt =
			(Spinner) rootView.findViewById(R.id.originCityPrompt);
		
		mDestinationCityPrompt = 
			(Spinner) rootView.findViewById(R.id.destinationCityPrompt);
		
		mDestinationLayout =
			(LinearLayout) rootView.findViewById(R.id.destinationCity);
		
		mBudgetPrompt = 
			(EditText) rootView.findViewById(R.id.budgetPrompt);
		
		mEnterDestinationButton =
			(Button) rootView.findViewById(R.id.enterDestination);
		mEnterDestinationButton.setOnClickListener(this);
		
		mRemoveDestinationButton =
			(Button) rootView.findViewById(R.id.removeDestination);
		mRemoveDestinationButton.setOnClickListener(this);
		
		mWeekendizeButton =
			(Button) rootView.findViewById(R.id.weekendize);
		mWeekendizeButton.setOnClickListener(this);

		OkHttpClient client = new OkHttpClient();
		RestAdapter weekendPlannerAdapter =
    		new RestAdapter.Builder()
				.setClient(new OkClient(client))
				.setLogLevel(LogLevel.FULL)
				.setLog(new AndroidLog("MYREQUESTS"))
				.setEndpoint("http://10.0.3.2:8080/WeekendPlanner/")
				.build();
        
        mWeekendPlannerService =
        	weekendPlannerAdapter.create(WeekendPlannerService.class);
        
        mCities = new HashMap<String, City>();
        
		return rootView;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		new CityInfoTask().execute();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.enterDestination:
		case R.id.removeDestination:
			toggleVisibility();
			break;
		case R.id.weekendize:
			if(isValidInput())
				new WeekendPlannerTask().execute();
			break;
		}
	}
	
	private void toggleVisibility() {
		mDestinationLayout.setVisibility(
			mDestinationLayout.getVisibility() == View.GONE ?
				View.VISIBLE : View.GONE);
		
		mEnterDestinationButton.setVisibility(
			mEnterDestinationButton.getVisibility() == View.GONE ?
				View.VISIBLE : View.GONE);
		
		mRemoveDestinationButton.setVisibility(
			mRemoveDestinationButton.getVisibility() == View.GONE ?
				View.VISIBLE : View.GONE);
	}
	
	private boolean isValidInput() {
		String budget = mBudgetPrompt.getText().toString();
	
		try {
			if(Double.valueOf(budget).equals(null)) {
				showToast("Invalid Budget");
				return false;
			}
		} catch(NumberFormatException e) {
			showToast("Invalid Budget");
			return false;
		}
		
		return true;
	}
    
    private class WeekendPlannerTask
    	extends AsyncTask<Void, Void, WeekendPlannerResponse> {
    	
    	@Override
    	protected void onPreExecute() {
    		showDialog("Weekendizing",
    				   "Please wait while your weekend is being planned");
    	}

		@Override
		protected WeekendPlannerResponse doInBackground(Void... params) {
			return mWeekendPlannerService.weekendize(buildRequest());
		}
		
		@Override
		protected void onPostExecute(WeekendPlannerResponse response) {
			mAlertDialog.cancel();
			// transition fragment based on response
		}
		
		// assumes input has been verified
		private WeekendPlannerRequest buildRequest() {
			return new WeekendPlannerRequest(
				getBudget(),
				getOrigin(),
				getDestination());
		}
		
		private String getBudget() {
			return mBudgetPrompt.getText().toString();
		}
		
		private City getOrigin() {
			return mCities.get(
				mOriginCityPrompt.getSelectedItem().toString());
		}
		
		private City getDestination() {
			return mDestinationLayout.getVisibility() == View.GONE ?
				mCities.get(
					mDestinationCityPrompt.getItemAtPosition(
						new Random().nextInt(mCities.size())).toString())
				: mCities.get(
					mDestinationCityPrompt.getSelectedItem().toString());
		}
    }
    
    private class CityInfoTask
    	extends AsyncTask<Void, Void, CityInfoResponse> {
    	
    	private String mCountry;
    	private final String DEFAULT_COUNTRY = "US";
    	
    	public CityInfoTask() {
    		mCountry = DEFAULT_COUNTRY;
    	}
     	
    	@SuppressWarnings("unused")
		public CityInfoTask(String country) {
    		mCountry = country;
    	}
    	
    	@Override
    	protected void onPreExecute() {
    		showDialog("Gathering City Info",
 				   	   "Finding eligible cities for your country");
    	}

		@Override
		protected CityInfoResponse doInBackground(Void... params) {
			return mWeekendPlannerService.queryCities(mCountry);
		}
		
		@Override
		protected void onPostExecute(CityInfoResponse response) {
			for(City city : response.Cities) {
				mCities.put(city.name, city);
			}
			
			ArrayAdapter<String> cityAdapter =
				new ArrayAdapter<String>(
					getActivity(),
					android.R.layout.simple_spinner_item,
					mCities.keySet().toArray(
						new String[mCities.size()]));
			cityAdapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
			
			mOriginCityPrompt.setAdapter(cityAdapter);
			mDestinationCityPrompt.setAdapter(cityAdapter);
						
			mAlertDialog.cancel();
		}
    }
    
    /**
     * Shows a dialog to the user, indicating that a long
     * running background operation is in progress
     */
    private void showDialog(String title, String msg) {
    	mAlertDialog.setTitle(title);
		mAlertDialog.setMessage(msg);
		mAlertDialog.show();
    }
    
    /**
     * Show a toast to the user.
     */
    private void showToast(String msg) {
        Toast.makeText(this.getActivity(),
                       msg,
                       Toast.LENGTH_SHORT).show();
    }
    
}
