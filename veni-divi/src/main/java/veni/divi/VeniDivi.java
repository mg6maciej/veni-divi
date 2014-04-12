/*
 * The MIT License (MIT)
 *
 *   Copyright (c) 47 B.C. Maciej Górski
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package veni.divi;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public final class VeniDivi {

    public interface OnGoBackListener {

        void onGoBack(int backCount);
    }

    private static final String KEY_STATE = "veni.divi.state";
    private static final String KEY_VISIBLE = "veni.divi.visible";

    public static VeniDivi create(Activity activity) {
        return builder(activity)
                .separator(R.layout.veni_divi_separator)
                .build();
    }

    public static VeniDiviBuilder builder(Activity activity) {
        return new VeniDiviBuilder(activity);
    }

    private LayoutInflater inflater;
    private int element;
    private Integer separator;
    private Integer root;
    private ActionBar actionBar;
    private HorizontalScrollView scrollView;
    private ViewGroup container;
    private ArrayList<CharSequence> state = new ArrayList<CharSequence>();
    private OnGoBackListener listener;

    VeniDivi(Activity activity, int element, Integer separator, Integer root, boolean scrollBarEnabled) {
        inflater = activity.getLayoutInflater();
        this.element = element;
        this.separator = separator;
        this.root = root;
        actionBar = activity.getActionBar();
        actionBar.setCustomView(R.layout.veni_divi_layout);
        scrollView = (HorizontalScrollView) actionBar.getCustomView();
        scrollView.setHorizontalScrollBarEnabled(scrollBarEnabled);
        scrollView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View a, int b, int c, int e, int h, int m, int t, int an, int cw) {
                scrollView.fullScroll(View.FOCUS_RIGHT);
            }
        });
        container = (ViewGroup) scrollView.findViewById(R.id.veni_divi_container);
        addRoot();
        setVisible(true);
    }

    public void setOnGoBackListener(OnGoBackListener listener) {
        this.listener = listener;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        ArrayList<String> savedState = savedInstanceState.getStringArrayList(KEY_STATE);
        for (String element : savedState) {
            addElement(element);
        }
        setVisible(savedInstanceState.getBoolean(KEY_VISIBLE));
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putCharSequenceArrayList(KEY_STATE, state);
        outState.putBoolean(KEY_VISIBLE, isVisible());
    }

    public void addElement(CharSequence text) {
        View view = inflater.inflate(element, container, false);
        TextView textView = ViewUtils.findViewByClass(view, TextView.class);
        if (textView == null) {
            throw new IllegalStateException("Cannot find TextView.");
        }
        view.setOnClickListener(elementClickListener);
        addSeparatorIfNotEmpty();
        state.add(text);
        textView.setText(text);
        container.addView(view);
    }

    public boolean goBack() {
        if (state.size() > 0) {
            removeLastElement();
            notifyListener(1);
            return true;
        } else {
            return false;
        }
    }

    public boolean reset() {
        int backCount = state.size();
        if (backCount > 0) {
            state.clear();
            if (!hasRoot()) {
                container.removeAllViews();
            } else {
                while (container.getChildCount() > 1) {
                    container.removeViewAt(1);
                }
            }
            notifyListener(backCount);
            return true;
        } else {
            return false;
        }
    }

    private boolean isVisible() {
        return (actionBar.getDisplayOptions() & ActionBar.DISPLAY_SHOW_CUSTOM) == ActionBar.DISPLAY_SHOW_CUSTOM;
    }

    private void setVisible(boolean visible) {
        actionBar.setDisplayShowTitleEnabled(!visible);
        actionBar.setDisplayShowCustomEnabled(visible);
    }

    private void removeLastElement() {
        state.remove(state.size() - 1);
        container.removeViewAt(container.getChildCount() - 1);
        if (hasSeparators() && container.getChildCount() > 0) {
            container.removeViewAt(container.getChildCount() - 1);
        }
    }

    private void notifyListener(int backCount) {
        if (listener != null) {
            listener.onGoBack(backCount);
        }
    }

    private void addSeparatorIfNotEmpty() {
        if (hasSeparators() && container.getChildCount() > 0) {
            inflater.inflate(separator, container);
        }
    }

    private void addRoot() {
        if (hasRoot()) {
            View rootView = inflater.inflate(root, container, false);
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reset();
                }
            });
            container.addView(rootView);
        }
    }

    private boolean hasSeparators() {
        return separator != null;
    }

    private boolean hasRoot() {
        return root != null;
    }

    private View.OnClickListener elementClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int backCount = 0;
            int index = container.indexOfChild(v);
            while (index != container.getChildCount() - 1) {
                removeLastElement();
                backCount++;
            }
            notifyListener(backCount);
        }
    };
}
