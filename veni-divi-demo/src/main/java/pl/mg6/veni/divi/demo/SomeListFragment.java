package pl.mg6.veni.divi.demo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SomeListFragment extends Fragment {

    private static final String KEY_ELEMENTS = "elements";

    private ArrayList<CharSequence> randomElements;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            randomElements = savedInstanceState.getCharSequenceArrayList(KEY_ELEMENTS);
        } else {
            randomElements = generateRandomElements();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequenceArrayList(KEY_ELEMENTS, randomElements);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView listView = new ListView(getActivity());
        final ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_list_item_1, randomElements);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CharSequence element = adapter.getItem(position);
                // casting to concrete Activity class done for brevity
                // use EventBus or Otto in real code
                ((FragmentsInBackStackActivity) getActivity()).onElementClick(element);
            }
        });
        return listView;
    }

    private ArrayList<CharSequence> generateRandomElements() {
        Random r = new Random();
        ArrayList<CharSequence> randomElements = new ArrayList<CharSequence>();
        int count = r.nextInt(28);
        for (int i = 0; i < count; i++) {
            randomElements.add(String.format("Element with id [%d]", r.nextInt(10000)));
        }
        return randomElements;
    }
}
