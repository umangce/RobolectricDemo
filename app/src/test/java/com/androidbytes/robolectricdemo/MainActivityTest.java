package com.androidbytes.robolectricdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.util.ActivityController;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by deepak on 23/2/17.
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private Activity activity;
    private ActivityController activityController;

    // @Before => JUnit 4 annotation that specifies this method should run before each test is run
    // Useful to do setup for objects that are needed in the test
    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()

        //activity = Robolectric.buildActivity(MainActivity.class).create().get();

        activityController = Robolectric.buildActivity(MainActivity.class).create().start();
        activity = (Activity) activityController.get();
    }

    // @Test => JUnit 4 annotation specifying this is a test to be run
    // The test simply checks that our TextView exists and has the text "Hello world!"
    @Test
    public void validateTextViewContent() {
        TextView tvHelloWorld = (TextView) activity.findViewById(R.id.text_view);
        assertNotNull("TextView could not be found", tvHelloWorld);
        assertTrue("TextView contains incorrect text",
                "Hello World!".equals(tvHelloWorld.getText().toString()));
    }

    @Test
    public void validateButtonClick() {
        Button button = (Button) activity.findViewById(R.id.button_simple);
        button.performClick();

        ShadowActivity shadowActivity = shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertThat(shadowIntent.getIntentClass().getName(), equalTo(SampleActivity.class.getName()));
    }
}