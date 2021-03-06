package fi.metropolia.lbs.travist;

import travist.pack.R;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import fi.metropolia.lbs.travist.database.LBSContentProvider;
import fi.metropolia.lbs.travist.database.PlaceTableClass;
import fi.metropolia.lbs.travist.offline_map.TestOfflineMapActivity;

public class TravistIstanbulActivity extends Activity {
	private Button createButton(final Class<?> testCaseClass) {
		Button button = new Button(this);
		button.setText(testCaseClass.getSimpleName());
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				startActivity(new Intent(TravistIstanbulActivity.this,
						testCaseClass));
			}
		});
		return button;
	}

	public void openMap(View view) {
		Intent intent = new Intent(this, TestOfflineMapActivity.class);
		startActivity(intent);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Make buttons according to Activities of test cases
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_view_layout);
		linearLayout.addView(createButton(fi.metropolia.lbs.travist.foursquare_api.FoursquareActivity.class));
		linearLayout.addView(createButton(fi.metropolia.lbs.travist.offline_map.TestOfflineMapActivity.class));
		
		
		linearLayout.addView(createButton(fi.metropolia.lbs.travist.offline_map.TestOfflineMapActivity.class));
	}
	
	//Simple examples how to use CRUD methods (Content provider)
	public void insertIntoDb(View v) {
		ContentValues cv = new ContentValues();
		cv.put(PlaceTableClass.PLACE_ID, "asd");
		cv.put(PlaceTableClass.PLACE_NAME, "CHIGAGO");
		this.getContentResolver().insert(LBSContentProvider.PLACES_URI, cv);
		Toast.makeText(getApplicationContext(), "click query to check if it returns anything", Toast.LENGTH_SHORT).show();
	}
	
	public void queryFromDb(View v) {
		Cursor cursor = this.getContentResolver().query(LBSContentProvider.PLACES_URI, null, null, null, null);
		if (cursor.getCount() > 0 && cursor.moveToNext()) {
			int index = cursor.getColumnIndex(PlaceTableClass.PLACE_NAME);
			String placeName = cursor.getString(index);
			Toast.makeText(getApplicationContext(), "From db:" + placeName, Toast.LENGTH_SHORT).show();
		}
		cursor.close();
	}
	
	public void deleteFromDb(View v) {
		this.getContentResolver().delete(LBSContentProvider.PLACES_URI, PlaceTableClass.PLACE_ID + " = 'asd'", null);
		Toast.makeText(getApplicationContext(), "click query to check if it returns anything", Toast.LENGTH_SHORT).show();
	}
	
	public void updateDb(View v) {
		ContentValues cv = new ContentValues();
		cv.put(PlaceTableClass.PLACE_ID, "asd");
		cv.put(PlaceTableClass.PLACE_NAME, "MOSCOW");
		this.getContentResolver().update(LBSContentProvider.PLACES_URI, cv, PlaceTableClass.PLACE_ID + " = 'asd'", null);
		Toast.makeText(getApplicationContext(), "Click query to check if placename changed", Toast.LENGTH_SHORT).show();
	}
}