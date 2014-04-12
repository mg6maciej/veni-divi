package pl.mg6.veni.divi.demo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import veni.divi.VeniDivi;

public class ConfigurationOptionsActivity extends Activity implements OnElementClickListener {

    private VeniDivi veniDivi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragments_in_back_stack_activity);
        veniDivi = VeniDivi.builder(this)
                //.root(R.layout.title_root)
                .element(R.layout.custom_element)
                .hideScrollBar()
                .build();
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
        if (savedInstanceState == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction tx = fm.beginTransaction();
            tx.replace(R.id.content, new RootFragment());
            tx.commit();
        }
    }

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

    @Override
    public void onBackPressed() {
        if (!veniDivi.goBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onElementClick(CharSequence element) {
        veniDivi.addElement(element);
        replaceContent(getFragment(element));
    }

    private void replaceContent(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.replace(R.id.content, fragment);
        tx.addToBackStack(null);
        tx.commit();
    }

    private Fragment getFragment(CharSequence element) {
        Fragment f = null;
        if ("Color: Red".contentEquals(element) || "Color: Green".contentEquals(element)
                || "Color: Blue".contentEquals(element) || "Color: Yellow".contentEquals(element)) {
            f = new SomeListFragment();
        } else if ("More".equals(element)) {
            f = new EmptyFragment();
        } else {
            f = DetailsFragment.newInstance(element);
        }
        return f;
    }
}
