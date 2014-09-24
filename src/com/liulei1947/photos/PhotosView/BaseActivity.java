
package com.liulei1947.photos.PhotosView;


import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import com.liulei1947.photos.ResideMenu.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public abstract class BaseActivity extends SwipeBackActivity  {
	protected AbsListView listView;

	protected static ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.item_clear_memory_cache:
				imageLoader.clearMemoryCache();
				return true;
			case R.id.item_clear_disc_cache:
				imageLoader.clearDiscCache();
				return true;
			default:
				return false;
		}
	}
}
