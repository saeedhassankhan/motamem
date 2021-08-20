package org.matamem.di;

import android.app.Application;

import androidx.room.Room;


import org.jetbrains.annotations.NotNull;
import org.matamem.db.DB;
import org.matamem.db.GroupDao;
import org.matamem.db.NoteDao;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;


@Module
@InstallIn(SingletonComponent.class)
public class DataBaseModule {

    @Provides
    @Singleton
    public  DB providePokemonDB(Application application){
         return Room.databaseBuilder(application, DB.class,"Favorite Database")
                 .fallbackToDestructiveMigration()
                 .allowMainThreadQueries()
                 .build();
    }

    @Provides
    @Singleton
    public GroupDao providePokeDao(@NotNull DB Db){
        return Db.groupDao();
    }

    @Provides
    @Singleton
    public NoteDao provideTextDao(DB Db){
        return Db.textDao();
    }
}
