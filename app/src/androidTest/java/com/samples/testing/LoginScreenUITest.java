package com.samples.testing;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.samples.testing.annotation.EspressoTest;
import com.samples.testing.login.LoginActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class LoginScreenUITest {

    @Rule
    public IntentsTestRule<LoginActivity> mIntentTestRule = new IntentsTestRule<>(LoginActivity.class);

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void testLaunchWelcomeScreen() throws InterruptedException {
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.name)).perform(typeText("sample"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.email)).perform(typeText("support@sample.com"));

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.login)).perform(click());

        intended(hasComponent("com.samples.androidtesting.WelcomeActivity"));
    }

    @Test
    public void testShowErrorWhenNameIsEmpty() throws InterruptedException {
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.name)).perform(typeText(""));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.email)).perform(typeText("support@sample.com"));

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.login)).perform(click());

        onView(withText("Name is empty")).inRoot(withDecorView(not(is(mIntentTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @EspressoTest
    @Test
    public void testSomething(){

    }
}
