package org.jbtc.gshop.ui.categories;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jbtc.gshop.R;
import org.jbtc.gshop.databinding.FragmentCategoriesBinding;

public class CategoriesFragment extends Fragment {
    private FragmentCategoriesBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);

    }
}