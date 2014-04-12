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

import android.app.Activity;

public final class VeniDiviBuilder {

    private Activity activity;
    private int element = R.layout.veni_divi_element;
    private Integer separator = null;
    private boolean scrollBarEnabled = true;

    VeniDiviBuilder(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity cannot be null.");
        }
        this.activity = activity;
    }

    public VeniDiviBuilder element(int elementLayoutId) {
        this.element = elementLayoutId;
        return this;
    }

    public VeniDiviBuilder separator(int separatorLayoutId) {
        this.separator = separatorLayoutId;
        return this;
    }

    public VeniDiviBuilder hideScrollBar() {
        this.scrollBarEnabled = false;
        return this;
    }

    public VeniDivi build() {
        return new VeniDivi(activity, element, separator, scrollBarEnabled);
    }
}
