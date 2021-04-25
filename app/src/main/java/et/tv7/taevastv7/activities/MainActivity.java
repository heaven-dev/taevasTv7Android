package et.tv7.taevastv7.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import et.tv7.taevastv7.BuildConfig;
import et.tv7.taevastv7.R;
import et.tv7.taevastv7.TaevasTv7;
import et.tv7.taevastv7.fragments.AboutFragment;
import et.tv7.taevastv7.fragments.ArchiveMainFragment;
import et.tv7.taevastv7.fragments.ArchivePlayerFragment;
import et.tv7.taevastv7.fragments.CategoriesFragment;
import et.tv7.taevastv7.fragments.ChannelInfoFragment;
import et.tv7.taevastv7.fragments.ErrorFragment;
import et.tv7.taevastv7.fragments.ExitFragment;
import et.tv7.taevastv7.fragments.FavoritesFragment;
import et.tv7.taevastv7.fragments.GuideFragment;
import et.tv7.taevastv7.fragments.ProgramInfoFragment;
import et.tv7.taevastv7.fragments.SearchFragment;
import et.tv7.taevastv7.fragments.SearchResultFragment;
import et.tv7.taevastv7.fragments.SeriesFragment;
import et.tv7.taevastv7.fragments.TvMainFragment;
import et.tv7.taevastv7.fragments.TvPlayerFragment;
import et.tv7.taevastv7.helpers.Utils;
import et.tv7.taevastv7.interfaces.EpgDataLoadedListener;
import et.tv7.taevastv7.model.ProgramScheduleViewModel;

import static et.tv7.taevastv7.helpers.Constants.EXIT_OVERLAY_FRAGMENT;
import static et.tv7.taevastv7.helpers.Constants.LOG_TAG;
import static et.tv7.taevastv7.helpers.Constants.TV_MAIN_FRAGMENT;
import static et.tv7.taevastv7.helpers.Constants.PROGRESS_BAR_SIZE;

/**
 * Main activity.
 *  - Load epg data, shows logo and progressbar.
 *  - Opens main fragment.
 *  - Handles keydown events.
 */
public class MainActivity extends AppCompatActivity implements EpgDataLoadedListener {

    private FragmentManager fragmentManager = null;
    private ProgramScheduleViewModel viewModel = null;
    private EpgDataLoadedListener epgDataLoadedListener = null;

    private View fragmentContainer = null;
    private ImageView startupLogo = null;
    private ProgressBar progressBar = null;

    /**
     * onCreate() - Android lifecycle method.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            if (BuildConfig.DEBUG) {
                Log.d(LOG_TAG, "MainActivity.onCreate() called.");
            }

            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            fragmentManager = Utils.getFragmentManager(this);

            viewModel = ViewModelProviders.of(this).get(ProgramScheduleViewModel.class);
            this.setEpgDataLoadedListener(this);

            setContentView(R.layout.activity_main);

            fragmentContainer = findViewById(R.id.fragment_container);
            startupLogo = findViewById(R.id.startupLogo);

            progressBar = findViewById(R.id.startupProgressBar);
            progressBar.setScaleY(PROGRESS_BAR_SIZE);
            progressBar.setScaleX(PROGRESS_BAR_SIZE);

            TaevasTv7.getInstance().setActivity(this);

            viewModel.getEpgData(this);
        }
        catch(Exception e) {
            if (BuildConfig.DEBUG) {
                Log.d(LOG_TAG, "MainActivity.onCreate(): Exception: " + e);
            }
            Utils.toErrorPage(this);
        }
    }

    /**
     * Handles key down events and sends events to visible fragment.
     * @param keyCode
     * @param events
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent events) {
        try {
            if (BuildConfig.DEBUG) {
                Log.d(LOG_TAG, "MainActivity.onKeyDown(): keyCode: " + keyCode);
            }

            Fragment fragment = this.getVisibleFragment();
            if (fragment != null) {
                if (fragment instanceof TvMainFragment) {
                    // TV main fragment visible
                    return ((TvMainFragment) fragment).onKeyDown(keyCode, events);
                }
                else if (fragment instanceof ArchiveMainFragment) {
                    // Archive main fragment visible
                    return ((ArchiveMainFragment) fragment).onKeyDown(keyCode, events);
                }
                else if (fragment instanceof TvPlayerFragment) {
                    // Video player fragment visible
                    return ((TvPlayerFragment) fragment).onKeyDown(keyCode, events);
                }
                else if (fragment instanceof ArchivePlayerFragment) {
                    // Archive player fragment visible
                    return ((ArchivePlayerFragment) fragment).onKeyDown(keyCode, events);
                }
                else if (fragment instanceof ProgramInfoFragment) {
                    // Program info fragment visible
                    return ((ProgramInfoFragment) fragment).onKeyDown(keyCode, events);
                }
                else if (fragment instanceof CategoriesFragment) {
                    // Categories fragment visible
                    return ((CategoriesFragment) fragment).onKeyDown(keyCode, events);
                }
                else if (fragment instanceof SeriesFragment) {
                    // Series fragment visible
                    return ((SeriesFragment) fragment).onKeyDown(keyCode, events);
                }
                else if (fragment instanceof GuideFragment) {
                    // Guide fragment visible
                    return ((GuideFragment) fragment).onKeyDown(keyCode, events);
                }
                else if (fragment instanceof SearchFragment) {
                    // Search fragment visible
                    return ((SearchFragment) fragment).onKeyDown(keyCode, events);
                }
                else if (fragment instanceof SearchResultFragment) {
                    // Search result fragment visible
                    return ((SearchResultFragment) fragment).onKeyDown(keyCode, events);
                }
                else if (fragment instanceof FavoritesFragment) {
                    // Favorites fragment visible
                    return ((FavoritesFragment) fragment).onKeyDown(keyCode, events);
                }
                else if (fragment instanceof ChannelInfoFragment) {
                    // Channel info fragment visible
                    return ((ChannelInfoFragment) fragment).onKeyDown(keyCode, events);
                }
                else if (fragment instanceof AboutFragment) {
                    // About fragment visible
                    return ((AboutFragment) fragment).onKeyDown(keyCode, events);
                }
                else if (fragment instanceof ErrorFragment) {
                    // Error fragment visible
                    return ((ErrorFragment) fragment).onKeyDown(keyCode, events);
                }
                else if (fragment instanceof ExitFragment) {
                    // Exit overlay fragment visible
                    return ((ExitFragment) fragment).onKeyDown(keyCode, events);
                }
            }
        }
        catch(Exception e) {
            if (BuildConfig.DEBUG) {
                Log.d(LOG_TAG, "MainActivity.onKeyDown(): Exception: " + e);
            }
            Utils.toErrorPage(this);
        }

        return super.onKeyDown(keyCode, events);
    }

    /**
     * Remote home button pressed. If playback ongoing - stop it.
     */
    @Override
    public void onUserLeaveHint()
    {
        super.onUserLeaveHint();

        Fragment fragment = this.getVisibleFragment();
        if (fragment != null) {
            if (fragment instanceof TvPlayerFragment) {
                ((TvPlayerFragment) fragment).onHomeButtonPressed();
            }
            else if (fragment instanceof ArchivePlayerFragment) {
                // Archive player fragment visible
                ((ArchivePlayerFragment) fragment).onHomeButtonPressed();
            }
        }
    }

    /**
     * Callback to success epg date load.
     */
    @Override
    public void onEpgDataLoaded() {
        try {
            if (BuildConfig.DEBUG) {
                Log.d(LOG_TAG, "MainActivity.onEpgDataLoaded(): EpgData load/parse ok.");
            }

            this.prepareUi();

            Utils.toPage(TV_MAIN_FRAGMENT, this, false, false, null);
        }
        catch(Exception e) {
            if (BuildConfig.DEBUG) {
                Log.d(LOG_TAG, "MainActivity.onEpgDataLoaded(): Exception: " + e);
            }

            Utils.toErrorPage(this);
        }
    }

    /**
     * Callback to error epg date load.
     */
    @Override
    public void onEpgDataLoadError(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(LOG_TAG, "MainActivity.onEpgDataLoadError(): EpgData load/parse error: " + message);
        }

        this.prepareUi();

        Utils.toErrorPage(this);
    }

    /**
     * Network error callback.
     */
    @Override
    public void onNetworkError() {
        if (BuildConfig.DEBUG) {
            Log.d(LOG_TAG, "MainActivity.onNetworkError(): ***Network error!***");
        }

        this.prepareUi();

        Utils.toErrorPage(this);
    }

    /**
     * Hides logo and progressbar. Shows fragment container.
     */
    private void prepareUi() {
        startupLogo.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        fragmentContainer.setVisibility(View.VISIBLE);
    }

    /**
     * Returns visible fragment.
     * @return
     */
    private Fragment getVisibleFragment(){
        // Possible multiple fragments visible because exit overlay fragment
        List<Fragment> visibleFragments = new ArrayList<>();

        if (fragmentManager != null) {
            List<Fragment> fragments = fragmentManager.getFragments();
            if(fragments != null) {
                for(Fragment fragment : fragments){
                    if(fragment != null && fragment.isVisible())
                        visibleFragments.add(fragment);
                }
            }

            Fragment visibleFragment = null;
            if (visibleFragments.size() > 1) {
                visibleFragment = fragmentManager.findFragmentByTag(EXIT_OVERLAY_FRAGMENT);
            }

            if (visibleFragment != null) {
                // return exit overlay fragment
                return visibleFragment;
            }
        }

        return visibleFragments.size() > 0 ? visibleFragments.get(0) : null;
    }

    /**
     * Creates epg data load listener.
     * @param epgDataLoadedListener
     */
    private void setEpgDataLoadedListener(EpgDataLoadedListener epgDataLoadedListener) {
        this.epgDataLoadedListener = epgDataLoadedListener;
    }
}
