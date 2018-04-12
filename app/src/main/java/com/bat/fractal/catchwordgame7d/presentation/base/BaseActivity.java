package com.bat.fractal.catchwordgame7d.presentation.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.bat.fractal.catchwordgame7d.R;
import com.bat.fractal.catchwordgame7d.common.Constant;
import com.bat.fractal.catchwordgame7d.common.Strings;
import com.bat.fractal.catchwordgame7d.datalayer.model.Question;
import com.bat.fractal.catchwordgame7d.presentation.widget.DialogHelper;
import com.bat.fractal.catchwordgame7d.presentation.widget.SnackbarHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Tung Phan
 * @since 06/30/2017
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity
        implements BaseView {

    private final String LANGUAGE = "en";
    private Unbinder unbinder;
    private List<ProgressDialog> loadingDialog = new ArrayList<>();
    protected boolean isBackEnable = false;
    protected MediaPlayer musicPlayer;
    protected DialogHelper dialogHelper;
    protected SnackbarHelper snackbarHelper;
    protected static boolean volumeIsOn = true;

    @Inject
    P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setLanguageForApp(LANGUAGE);
        if (getPresenter() != null) {
            getPresenter().onTakeView(this);
            getPresenter().setCompositeDisposable(new CompositeDisposable());
        }
        initMusic();
        dialogHelper = new DialogHelper(this, getLayoutInflater());
        snackbarHelper = new SnackbarHelper();
    }

    private void hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initMusic() {
        if (musicPlayer == null) {
            musicPlayer = MediaPlayer.create(this, R.raw.better_day_ahead);
            musicPlayer.setLooping(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!musicPlayer.isPlaying()) {
            musicPlayer.start();
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        if (getPresenter() != null) {
            getPresenter().onDestroyView();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (getPresenter() != null) {
            getPresenter().unsubscribe();
        }
        if (musicPlayer.isPlaying()) {
            musicPlayer.stop();
            try {
                musicPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getPresenter() != null && getPresenter().getCompositeDisposable() == null) {
            getPresenter().setCompositeDisposable(new CompositeDisposable());
        }
        adjustVolume();
    }

    private void adjustVolume() {
        if (volumeIsOn) {
            musicPlayer.setVolume(Constant.MAX_VOLUME, Constant.MAX_VOLUME);
        } else {
            musicPlayer.setVolume(Constant.MIN_VOLUME, Constant.MIN_VOLUME);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (getPresenter() != null && getPresenter().getCompositeDisposable() == null) {
            getPresenter().setCompositeDisposable(new CompositeDisposable());
        }
    }

    protected void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    final public P getPresenter() {
        return presenter;
    }

    protected void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    protected void showActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
        }
    }

    protected void hideBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
    }

    protected void showBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        if (isBackEnable) {
            super.onBackPressed();
        }
    }

    protected void hideLoadingDialog() {
        if (loadingDialog.size() > 0) {
            loadingDialog.get(0).dismiss();
            loadingDialog.remove(0);
        }
    }

    protected void showLoadingDialog(int resId) {
        loadingDialog.add(ProgressDialog.show(this, Strings.EMPTY,
                getString(resId), true));
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Configuration setConfiguration(Locale locale) {
        Configuration config = new Configuration();
        config.setLocale(locale);
        return config;
    }

    @SuppressWarnings("deprecation")
    private Configuration setConfigurationDeprecate(Locale locale) {
        Configuration config = new Configuration();
        config.locale = locale;
        return config;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setConfiguaration(Configuration configuaration) {
        createConfigurationContext(configuaration);
    }

    private void setLanguageForApp(String languageToLoad) {
        Locale locale;
        if (languageToLoad.equals(LANGUAGE)) { //use any value for default
            locale = Locale.getDefault();
        } else {
            locale = new Locale(languageToLoad);
        }
        Locale.setDefault(locale);
        Configuration config;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config = setConfiguration(locale);
        } else {
            config = setConfigurationDeprecate(locale);
        }
        setConfiguaration(config);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (getPresenter().getCompositeDisposable() == null) {
            getPresenter().setCompositeDisposable(new CompositeDisposable());
        }
    }
}
