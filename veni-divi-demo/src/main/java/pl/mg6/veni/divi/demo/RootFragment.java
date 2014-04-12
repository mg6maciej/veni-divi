package pl.mg6.veni.divi.demo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RootFragment extends Fragment {

    private static final int[] BUTTON_IDS = {R.id.red_button, R.id.green_button, R.id.blue_button, R.id.yellow_button};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.root_fragment, container, false);
        for (int id : BUTTON_IDS) {
            view.findViewById(id).setOnClickListener(onClickListener);
        }
        return view;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CharSequence tag = (CharSequence) v.getTag();
            // casting to concrete Activity class done for brevity
            // use EventBus or Otto in real code
            ((FragmentsInBackStackActivity) getActivity()).onElementClick(tag);
        }
    };
}
