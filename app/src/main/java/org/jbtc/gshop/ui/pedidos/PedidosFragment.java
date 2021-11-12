package org.jbtc.gshop.ui.pedidos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.jbtc.gshop.R;
import org.jbtc.gshop.adapter.PedidosAdapter;
import org.jbtc.gshop.databinding.FragmentPedidosBinding;
import org.jbtc.gshop.db.entity.Pedido;
import org.jbtc.gshop.db.viewmodel.PedidosViewModel;

import java.util.List;

import io.reactivex.functions.BiConsumer;

public class PedidosFragment extends Fragment {
    private FragmentPedidosBinding binding;
    private PedidosAdapter adapter;
    private PedidosViewModel pedidosViewModel;


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPedidosBinding.inflate(inflater,container,false);

        pedidosViewModel = new ViewModelProvider(this).get(PedidosViewModel.class);
        initAdapter();

        pedidosViewModel.getAllPedido()

                .subscribe(new BiConsumer<List<Pedido>, Throwable>() {
                    @Override
                    public void accept(List<Pedido> pedidos, Throwable throwable) throws Exception {
                        if (throwable==null)
                            adapter.setItems(pedidos);
                        else Log.e("TAG", "accept: Error: ", throwable);
                    }
                });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        closeKeyboard();
    }

    private void initAdapter() {
        binding.rvPedList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rvPedList.setLayoutManager(linearLayoutManager);
        adapter = new PedidosAdapter(new PedidosAdapter.OnClick() {

            @Override
            public void onClickCard(Pedido pedido) {
                Bundle b = new Bundle();
                b.putLong("id",pedido.id);
                NavHostFragment.findNavController(PedidosFragment.this)
                        .navigate(R.id.action_nav_pedidos_to_pedidoMostrarFragment,b);
            }
        });
        binding.rvPedList.setAdapter(adapter);
    }

    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void closeKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

}
