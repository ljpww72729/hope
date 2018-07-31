/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cc.lkme.hope.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This provides methods to help Activities load their UI.
 */
public class ActivityUtils {

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void replaceFragmentInActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();
    }

    /**
     * 切换传入的fragment显示，其他隐藏
     *
     * @param fragmentManager FragmentManager
     * @param fragment        要显示的fragment
     * @param frameId         FrameLayout视图id
     * @param fragmentTag     fragment标签，用于标识fragment
     */
    public static void showFragmentInActivity(@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId, @NonNull String fragmentTag) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        checkNotNull(fragmentTag);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragmentManager.findFragmentByTag(fragmentTag) == null) {
            transaction.add(frameId, fragment, fragmentTag);
        }
        Fragment needHideFragment = null;
        for (int i = 0; i < fragmentManager.getFragments().size(); i++) {
            if (fragmentManager.getFragments().get(i).isVisible() && fragmentManager.getFragments().get(i) != fragment) {
                needHideFragment = fragmentManager.getFragments().get(i);
                break;
            }
        }
        if (needHideFragment != null) {
            transaction.hide(needHideFragment);
        }
        transaction.show(fragment);
        transaction.commit();

    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void replaceFragmentInActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment, String tag) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragment, tag);
        transaction.commit();
    }
}