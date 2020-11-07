package org.maktab.photogallery.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.maktab.photogallery.R;
import org.maktab.photogallery.adapter.PhotoAdapter;
import org.maktab.photogallery.databinding.FragmentPhotoGalleryBinding;
import org.maktab.photogallery.model.GalleryItem;
import org.maktab.photogallery.viewmodel.PhotoGalleryViewModel;

import java.util.List;

public class PhotoGalleryFragment extends Fragment {

    private static final String TAG = "PGF";
    private static final int SPAN_COUNT = 3;

    private FragmentPhotoGalleryBinding mBinding;
    private PhotoGalleryViewModel mViewModel;

    public PhotoGalleryFragment() {
        // Required empty public constructor
    }

    public static PhotoGalleryFragment newInstance() {
        PhotoGalleryFragment fragment = new PhotoGalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(PhotoGalleryViewModel.class);

        mViewModel.fetchPopularItemsAsync();
        mViewModel.getPopularItemsLiveData().observe(this, new Observer<List<GalleryItem>>() {
            @Override
            public void onChanged(List<GalleryItem> items) {
                setupAdapter(items);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_photo_gallery,
                container,
                false);

        initViews();

        return mBinding.getRoot();
    }

    private void initViews() {
        mBinding.recyclerViewPhotoGallery
                .setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
    }

    private void setupAdapter(List<GalleryItem> items) {
        PhotoAdapter adapter = new PhotoAdapter(getContext(), items);
        mBinding.recyclerViewPhotoGallery.setAdapter(adapter);
    }
}