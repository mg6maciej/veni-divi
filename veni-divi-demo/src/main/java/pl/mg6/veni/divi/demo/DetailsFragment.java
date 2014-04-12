package pl.mg6.veni.divi.demo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    private static final String KEY_ELEMENT = "element";

    public static DetailsFragment newInstance(CharSequence element) {
        DetailsFragment f = new DetailsFragment();
        Bundle args = new Bundle();
        args.putCharSequence(KEY_ELEMENT, element);
        f.setArguments(args);
        return f;
    }

    private CharSequence element;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        element = getArguments().getCharSequence(KEY_ELEMENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        TextView title = (TextView) view.findViewById(R.id.details_title);
        title.setText(element);
        view.findViewById(R.id.more_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // casting done for brevity, use EventBus or Otto in real code
                ((OnElementClickListener) getActivity()).onElementClick("More");
            }
        });
        return view;
    }
}
