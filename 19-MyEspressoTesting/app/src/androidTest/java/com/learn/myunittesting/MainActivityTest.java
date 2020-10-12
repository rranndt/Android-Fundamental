package com.learn.myunittesting;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {
    private final String dummyVolume = "504.0";
    private final String dummyCircumference = "100.0";
    private final String dummySurfaceArea = "396.0";
    private final String dummyLength = "12.0";
    private final String dummyWidth = "7.0";
    private final String dummyHeight = "6.0";
    private final String emptyInput = "";
    private final String fieldEmpty = "Field ini tidak boleh kosong";

    // Memerintahkan Activity mana yang akan dijalankan
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    // ViewMatchers (onView(ViewMatcher))
    // Untuk menemukan elemen atau komponen antar muka yang diuji

    // ViewActions (perform(ViewAction))
    // Untuk memberikan event untuk melakukan sebuah aksi pada komponen antar muka yang diuji

    // ViewAssertions(check(ViewAssertion))
    // Untuk mengecek sebuah kondisi atau state dari komponen yang diuji

    @Test
    public void assertGetCircumference() {
        // Dibaca
        // Sebuah view dengan id edt_length diberi tindakan input dengan sebuah teks dummyLength dan
        // menutup secara perlahan keyboard android
        onView(withId(R.id.edt_length)).perform(typeText(dummyLength), closeSoftKeyboard());
        onView(withId(R.id.edt_width)).perform(typeText(dummyWidth), closeSoftKeyboard());
        onView(withId(R.id.edt_height)).perform(typeText(dummyHeight), closeSoftKeyboard());

        // Dibaca
        // Memastikan sebiah view dengan id btn_save dealam keadaan tampil
        onView(withId(R.id.btn_save)).check(matches(isDisplayed()));
        // Dibaca
        // Sebuah view dengan id btn_save diberi aksi klik
        onView((withId(R.id.btn_save))).perform(click());

        onView(withId(R.id.btn_calculate_circumference)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_calculate_circumference)).perform(click());

        onView(withId(R.id.tv_result)).check(matches(isDisplayed()));
        // Dibaca
        // Memastikan sebiha view dengan id tv_result mempunyai teks yang sama dengan dummyCircumference
        onView(withId(R.id.tv_result)).check(matches(withText(dummyCircumference)));
    }

    @Test
    public void assertGetSurfaceArea() {
        onView(withId(R.id.edt_length)).perform(typeText(dummyLength), closeSoftKeyboard());
        onView(withId(R.id.edt_width)).perform(typeText(dummyWidth), closeSoftKeyboard());
        onView(withId(R.id.edt_height)).perform(typeText(dummyHeight), closeSoftKeyboard());

        onView(withId(R.id.btn_save)).check(matches(isDisplayed()));
        onView((withId(R.id.btn_save))).perform(click());

        onView(withId(R.id.btn_calculate_surface_area)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_calculate_surface_area)).perform(click());

        onView(withId(R.id.tv_result)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_result)).check(matches(withText(dummySurfaceArea)));
    }

    @Test
    public void assertGetVolume() {
        onView(withId(R.id.edt_length)).perform(typeText(dummyLength), closeSoftKeyboard());
        onView(withId(R.id.edt_width)).perform(typeText(dummyWidth), closeSoftKeyboard());
        onView(withId(R.id.edt_height)).perform(typeText(dummyHeight), closeSoftKeyboard());

        onView(withId(R.id.btn_save)).check(matches(isDisplayed()));
        onView((withId(R.id.btn_save))).perform(click());

        onView(withId(R.id.btn_calculate_volume)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_calculate_volume)).perform(click());

        onView(withId(R.id.tv_result)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_result)).check(matches(withText(dummyVolume)));
    }

    // Pengecekan untuk empty input
    @Test
    public void assertEmptyInput() {
        // Pengecekan input untuk length
        onView(withId(R.id.edt_length)).perform(typeText(emptyInput), closeSoftKeyboard());

        onView(withId(R.id.btn_save)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_save)).perform(click());

        // Dibaca
        // Memastikan sebuah view dengan id edt_length mempunyai [esan error yang sama dengan fieldEmpty
        onView(withId(R.id.edt_length)).check(matches(hasErrorText(fieldEmpty)));
        onView(withId(R.id.edt_length)).perform(typeText(dummyLength), closeSoftKeyboard());

        // Pengecekan input untuk width
        onView(withId(R.id.edt_width)).perform(typeText(emptyInput), closeSoftKeyboard());

        onView(withId(R.id.btn_save)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_save)).perform(click());

        onView(withId(R.id.edt_width)).check(matches(hasErrorText(fieldEmpty)));
        onView(withId(R.id.edt_width)).perform(typeText(dummyWidth), closeSoftKeyboard());

        // Pengecekan untuk input height
        onView(withId(R.id.edt_height)).perform(typeText(emptyInput), closeSoftKeyboard());

        onView(withId(R.id.btn_save)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_save)).perform(click());

        onView(withId(R.id.edt_height)).check(matches(hasErrorText(fieldEmpty)));
        onView(withId(R.id.edt_height)).perform(typeText(dummyHeight), closeSoftKeyboard());

        onView(withId(R.id.btn_save)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_save)).perform(click());
    }

}