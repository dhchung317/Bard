package com.hyunki.bard;

import android.content.Context;

import com.hyunki.bard.database.Database;
import com.hyunki.bard.model.Song;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import androidx.test.core.app.ApplicationProvider;

@RunWith(RobolectricTestRunner.class)
public class TestDatabase {

    private static final String TEST_SONG_TITLE = "test";
    Context context;
    Database database;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        database = new Database(context);
    }

    @Test
    public void test_database_return_test_song_title() {
        database.addSong(new Song(TEST_SONG_TITLE));
        String input = TEST_SONG_TITLE;
        String expected = TEST_SONG_TITLE;
        String result = database.getSong(input).getSongTitle();
        Assert.assertEquals(expected,result);
    }
}
