package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.adapter.CardStackAdapter;
import com.w3itexperts.ombe.databinding.FragmentSwipingBinding;
import com.w3itexperts.ombe.modals.RestaurantCard;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class SwipingFragment extends Fragment {

    private FragmentSwipingBinding b;
    private CardStackLayoutManager layoutManager;
    private CardStackAdapter adapter;
    private static SwipingViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentSwipingBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(SwipingViewModel.class);
        viewModel.reset(); // Reset ViewModel state on fragment creation

        setupBackButton();
        setupCardStack();
        setupSwipeButtons();

        return b.getRoot();
    }

    private void setupBackButton() {
        b.backBtn.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
    }

    private void setupCardStack() {
        layoutManager = new CardStackLayoutManager(getContext(), new CardStackListener() {
            @Override
            public void onCardSwiped(Direction direction) {
                viewModel.incrementSwipedCards();
                checkIfSwipingFinished();
            }

            @Override
            public void onCardDragging(Direction direction, float ratio) {}

            @Override
            public void onCardRewound() {}

            @Override
            public void onCardCanceled() {}

            @Override
            public void onCardAppeared(View view, int position) {}

            @Override
            public void onCardDisappeared(View view, int position) {}
        });

        layoutManager.setStackFrom(StackFrom.Top);
        layoutManager.setVisibleCount(3);
        layoutManager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        layoutManager.setDirections(Direction.HORIZONTAL);

        b.cardStackView.setLayoutManager(layoutManager);

        List<RestaurantCard> cards = new ArrayList<>();
        cards.add(new RestaurantCard("Lola’s Cafe", "Tampines Mall", "$", R.drawable.lolacafe));
        cards.add(new RestaurantCard("Green Bites", "Orchard Central", "$$", R.drawable.greenbites));
        cards.add(new RestaurantCard("Sushi Palace", "Raffles City", "$$$", R.drawable.sushi));

        viewModel.setTotalCards(cards.size());

        adapter = new CardStackAdapter(cards);
        b.cardStackView.setAdapter(adapter);
    }

    private void setupSwipeButtons() {
        b.likeBtn.setOnClickListener(v -> {
            SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .setDuration(200)
                    .build();
            layoutManager.setSwipeAnimationSetting(setting);
            b.cardStackView.swipe();
        });

        b.dislikeBtn.setOnClickListener(v -> {
            SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .setDuration(200)
                    .build();
            layoutManager.setSwipeAnimationSetting(setting);
            b.cardStackView.swipe();
        });
    }

    private void checkIfSwipingFinished() {
        if (viewModel.getSwipedCards() == viewModel.getTotalCards()) {
            viewModel.setSwipingFinished(true);
            Toast.makeText(getContext(), "Swiping complete. You can now finalize.", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean hasFinishedSwiping() {
        return viewModel.isSwipingFinished();
    }

    // ViewModel to manage swiping state
    public static class SwipingViewModel extends androidx.lifecycle.ViewModel {
        private boolean swipingFinished = false;
        private int swipedCards = 0;
        private int totalCards = 0;

        public boolean isSwipingFinished() {
            return swipingFinished;
        }

        public void setSwipingFinished(boolean finished) {
            this.swipingFinished = finished;
        }

        public int getSwipedCards() {
            return swipedCards;
        }

        public void incrementSwipedCards() {
            this.swipedCards++;
        }

        public void setTotalCards(int totalCards) {
            this.totalCards = totalCards;
        }

        public int getTotalCards() {
            return totalCards;
        }

        public void reset() {
            this.swipedCards = 0;
            this.swipingFinished = false;
        }
    }
}