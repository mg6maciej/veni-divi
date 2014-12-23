Veni Divi [![Build Status](https://travis-ci.org/mg6maciej/veni-divi.svg?branch=develop)](https://travis-ci.org/mg6maciej/veni-divi)
=========

DEPRECATED: this library uses `Activity.getActionBar` and you may want to switch to `Toolbar`. Feel free to fork this and adjust to new patterns.

This is a library for Android that contains sectioned aciton bar title.

[![Veni Divi Demo](http://developer.android.com/images/brand/en_generic_rgb_wo_60.png "Veni Divi Demo")](https://play.google.com/store/apps/details?id=pl.mg6.veni.divi.demo)

![](http://mg6.pl/veni-divi/images/default_title.png "Title similar to Google Drive Android app")
![](http://mg6.pl/veni-divi/images/custom_title.png "Can be customized in many ways")

Usage
=====

Using this component is fairly straighforward.

Create `VeniDivi` object using `create` factory method:

```Java
    private VeniDivi veniDivi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ...
        veniDivi = VeniDivi.create(this);
    }
```

or customize it using `builder`:

```Java
        veniDivi = VeniDivi.builder(this)
                .root(R.layout.title_root)
                .element(R.layout.custom_element)
                .hideScrollBar()
                .build();
```

Then make sure to notify it of lifecycle changes in the following methods of your `Activity`:

 * `onRestoreInstanceState` (or `onCreate`)
 * `onSaveInstanceState`

```Java
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        veniDivi.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        veniDivi.onSaveInstanceState(outState);
    }
```

And now the fun begins. Every time you go deeper into the hierarchy, call

```Java
        veniDivi.addElement(CharSequence element)
```

To be notified when user wants to go back, create an instance of `OnGoBackListener` and assign it
to `VeniDivi` object. If you are using fragments put on back stack, your code will be similar to this:

```Java
        veniDivi.setOnGoBackListener(new VeniDivi.OnGoBackListener() {
            @Override
            public void onGoBack(int backCount) {
                FragmentManager fm = getFragmentManager();
                while (backCount > 0) {
                    fm.popBackStackImmediate();
                    backCount--;
                }
            }
        });
```

Most of the time you will also want to block `Activity` from finishing when there are elements in the title.

```Java
    @Override
    public void onBackPressed() {
        if (!veniDivi.goBack()) {
            super.onBackPressed();
        }
    }
```

Note: when using `VeniDivi.goBack` or `VeniDivi.reset`, your `OnGoBackListener` will also be called,
so you can keep your back navigation logic in a single place.

Gradle
------

The library is available on Maven Central.

```Groovy
dependencies {
    compile 'pl.mg6.veni.divi:veni-divi:0.1'
}
```

License
=======

    The MIT License (MIT)

      Copyright (c) 47 B.C. Maciej Górski

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
