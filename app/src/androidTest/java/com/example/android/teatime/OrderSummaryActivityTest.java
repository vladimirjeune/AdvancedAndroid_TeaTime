/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.teatime;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
//import android.support.test.espresso.

// TODO (1) Add annotation to specify AndroidJUnitRunner class as the default test runner
@RunWith(AndroidJUnit4.class)
public class OrderSummaryActivityTest {

    private static final String INTENT_DATA_EMAIL = Uri.parse("mailto:").toString();
    private static final String PACKAGE_ANDROID_EMAIL = "com.android.email";
    // TODO (2) Add the rule that indicates we want to use Espresso-Intents APIs in functional UI tests
    @Rule
    public IntentsTestRule<OrderSummaryActivity> mIntentsTestRule
            = new IntentsTestRule<>(OrderSummaryActivity.class);


    // TODO (3) Finish this method which runs before each test and will stub all external
    // intents so all external intents will be blocked
    @Before
    public void stubAllExternalIntents() {

        // It uses the intending() method associated with stubbing and takes not(isInternal()) as
        // its IntentMatcher parameter. isInternal() matches an intent if its package is the same
        // as the target package for the instrumentation test, therefore not(isInternal()) checks
        // that the intent's package does not match the target package for the test.

        intending((not(isInternal())))
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));  // par1 OK signal, par2 No data returned
    }


    // TODO (4) Finish this method which verifies that the intent sent by clicking the send email
    // button matches the intent sent by the application
    @Test
    public void clickSendEmailButton_SendsEmail() {
        String emailSubject = InstrumentationRegistry.getTargetContext().getString(R.string.order_summary_email_subject);
        String emailMessage = InstrumentationRegistry.getTargetContext().getString(R.string.email_message);

        onView(withId(R.id.send_email_button)).perform(click());

        intended(allOf(hasAction(Intent.ACTION_SENDTO),
                hasData(INTENT_DATA_EMAIL),
                hasExtra(Intent.EXTRA_SUBJECT, emailSubject),
                hasExtra(Intent.EXTRA_TEXT, emailMessage)
                )
        );
    }
}