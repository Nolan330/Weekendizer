package example.fragments;

import example.web.model.TripVariant;
import example.weekendizer.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TripDetailFragment extends Fragment {
	
	/**
	 * A tag by which to find the Fragment in the stack
	 */
	public static final String FRAGMENT_TAG = "tripdetail_fragment";

	public static TripDetailFragment newInstance(TripVariant variant) {
		return new TripDetailFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
			R.layout.fragment_tripdetail, container, false);
		
		return rootView;
	}
	
}
