package be.pxl.minecraftguide;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import be.pxl.minecraftguide.providers.RecipeProvider;

public class RecipeDetails extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
			setContentView(R.layout.recipedetails);
		else
			setContentView(R.layout.recipedetails_landscape);
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		String id = Integer.toString(extras.getInt("recipeID"));
		
		ImageView imgResult = (ImageView) findViewById(R.id.imgResult);
		ImageView imgIngr1 = (ImageView) findViewById(R.id.imgIngr1);
		ImageView imgIngr2 = (ImageView) findViewById(R.id.imgIngr2);
		ImageView imgIngr3 = (ImageView) findViewById(R.id.imgIngr3);
		ImageView imgIngr4 = (ImageView) findViewById(R.id.imgIngr4);
		ImageView imgIngr5 = (ImageView) findViewById(R.id.imgIngr5);
		ImageView imgIngr6 = (ImageView) findViewById(R.id.imgIngr6);
		ImageView imgIngr7 = (ImageView) findViewById(R.id.imgIngr7);
		ImageView imgIngr8 = (ImageView) findViewById(R.id.imgIngr8);
		ImageView imgIngr9 = (ImageView) findViewById(R.id.imgIngr9);
		
		
		ContentResolver cr = getContentResolver();
		Cursor cursor = cr.query(RecipeProvider.CONTENT_URI, null, id, null, null);
		if (cursor.moveToFirst()) {
			imgResult.setImageResource(cursor.getInt(2));
			setTitle(cursor.getString(3));
			
			int[] imgLocations = createIntArrayFromString(cursor.getString(4));
			int[] imgUsedImages = createIntArrayFromString(cursor.getString(5));
			imgIngr1.setImageResource(imgUsedImages[imgLocations[0]]);
			imgIngr2.setImageResource(imgUsedImages[imgLocations[1]]);
			imgIngr3.setImageResource(imgUsedImages[imgLocations[2]]);
			imgIngr4.setImageResource(imgUsedImages[imgLocations[3]]);
			imgIngr5.setImageResource(imgUsedImages[imgLocations[4]]);
			imgIngr6.setImageResource(imgUsedImages[imgLocations[5]]);
			imgIngr7.setImageResource(imgUsedImages[imgLocations[6]]);
			imgIngr8.setImageResource(imgUsedImages[imgLocations[7]]);
			imgIngr9.setImageResource(imgUsedImages[imgLocations[8]]);
		}
		
		cursor = cr.query(RecipeProvider.CONTENT_URI, null, null, new String[] { Integer.toString(cursor.getInt(2)) }, null);
		String[] from = {RecipeProvider.COL_RECID, RecipeProvider.COL_RECIMGID, RecipeProvider.COL_RECDESC};
		int[] to = { R.id.txtID, R.id.imgItem, R.id.txtDescription };
		SimpleCursorAdapter lijstAdaptor = new SimpleCursorAdapter(getApplicationContext(), R.layout.rowview, cursor, from, to, 0);
		setListAdapter(lijstAdaptor);
		
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View row, int rowIndex, long arg3) {
				Intent recipeDetailsIntent = new Intent(getApplicationContext(), RecipeDetails.class);
				TextView txtID = (TextView)row.findViewById(R.id.txtID);
				recipeDetailsIntent.putExtra("recipeID", Integer.parseInt(txtID.getText().toString()));
				startActivity(recipeDetailsIntent);
				finish();
			}
			
		});
	}
	
	public int[] createIntArrayFromString(String values) {
		String[] arrSplittedValues = values.split(",");
		int[] arrIntArray = new int[arrSplittedValues.length];
		
		for (int counter = 0; counter < arrSplittedValues.length; counter++) {
			arrIntArray[counter] = Integer.parseInt(arrSplittedValues[counter]);
		}
		
		return arrIntArray;
	}
}
