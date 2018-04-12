package com.bat.fractal.catchwordgame7d.di.component;


import com.bat.fractal.catchwordgame7d.di.module.NetworkModule;
import com.bat.fractal.catchwordgame7d.presentation.main.MainActivity;
import com.bat.fractal.catchwordgame7d.presentation.start.StartActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tung phan on 3/12/17.
 */
@Singleton
@Component(modules = {NetworkModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(StartActivity startActivity);

}
