package com.example.weeamawad.parking;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
	private ArrayList<String> resultList;

	public AutoCompleteAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);

	}

	@Override
	public int getCount() {
		return resultList.size();
	}

	@Override
	public String getItem(int index) {
		return resultList.get(index);
	}

	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults = new FilterResults();
				if (constraint != null) {
					// Retrieve the autocomplete results.
					//resultList = autoComplete(constraint.toString());

					TextPrediction predictions=new TextPrediction(constraint.toString());
					resultList=predictions.getPredictions();
					//new getTextPredictions(constraint.toString()).execute();
					// Assign the data to the FilterResults
					filterResults.values = resultList;
					filterResults.count = resultList.size();
				}
				return filterResults;
			}

			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				if (results != null && results.count > 0) {
					notifyDataSetChanged();
				}
				else {
					notifyDataSetInvalidated();
				}
			}};
			return filter;
	}

	private class getTextPredictions extends AsyncTask <Void,Void,ArrayList<String>>
	{
		TextPrediction predictions;
		ArrayList<String> results;
		public getTextPredictions(String input)
		{
			predictions=new TextPrediction(input);
		}
		@Override
		protected ArrayList<String> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			results=new ArrayList<String>();
			results=predictions.getPredictions();
			//System.out.println(results.get(0));
			return results;
		}


	}
}