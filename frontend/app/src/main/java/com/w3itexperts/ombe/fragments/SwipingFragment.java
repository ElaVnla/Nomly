package com.w3itexperts.ombe.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.SessionService.SessionManager;
import com.w3itexperts.ombe.adapter.CardStackAdapter;
import com.w3itexperts.ombe.databinding.FragmentSwipingBinding;
import com.w3itexperts.ombe.modals.RestaurantCard;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import android.util.Base64;

import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.APIservice.ApiService;

import com.w3itexperts.ombe.apimodals.locationDTO; // NEW
import com.w3itexperts.ombe.apimodals.userVoteDTO; // NEW
import com.w3itexperts.ombe.apimodals.eateries;        // NEW

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Collections;
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
        viewModel.reset();

        setupBackButton();

        double lat = getArguments().getDouble("lat", 0);
        double lng = getArguments().getDouble("lng", 0);
        int sessionId = getArguments().getInt("sessionId", -1);

        setupCardStack(lat, lng, sessionId);
        setupSwipeButtons();
        return b.getRoot();
    }

    private void setupBackButton() {
        b.backBtn.setOnClickListener(v -> {
            if (!viewModel.isSwipingFinished()) {
                Toast.makeText(requireContext(), "Please finish swiping through all restaurants!", Toast.LENGTH_SHORT).show();
            } else {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void setupCardStack(double lat, double lng, int sessionId) {
        layoutManager = new CardStackLayoutManager(getContext(), new CardStackListener() {
            @Override
            public void onCardSwiped(Direction direction) {
                viewModel.incrementSwipedCards();
                checkIfSwipingFinished(sessionId);

                int position = layoutManager.getTopPosition() - 1;
                if (position >= 0 && adapter != null && position < adapter.getItemCount()) {
                    RestaurantCard swipedCard = adapter.getCardAt(position);

                    String userId = String.valueOf(SessionManager.getInstance(requireContext()).getCurrentUser().getUserId()); // NEW
                    String sessionIdStr = String.valueOf(sessionId); // UPDATED
                    String eateryId = swipedCard.getEateryId(); // NEW
                    boolean liked = (direction == Direction.Right);

                    userVoteDTO vote = new userVoteDTO(userId, sessionIdStr, eateryId, liked); // NEW
                    ApiClient.getApiService().sendUserVote(vote).enqueue(new Callback<Void>() {


                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Log.d("VOTE_SENT", new Gson().toJson(vote));

                                Log.d("VOTE_SUCCESS", "Vote stored for: " + vote.getEateryId());
                            } else {
                                Log.e("VOTE_FAIL", "Vote failed: " + response.code());
                            }
                        }
                        //                        @Override
//                        public void onResponse(Call<Void> call, Response<Void> response) {
//                            if (!response.isSuccessful()) {
//                                Toast.makeText(getContext(), "Failed to save vote", Toast.LENGTH_SHORT).show();
//                            }
//                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(), "Vote API error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override public void onCardDragging(Direction direction, float ratio) {}
            @Override public void onCardRewound() {}
            @Override public void onCardCanceled() {}
            @Override public void onCardAppeared(View view, int position) {}
            @Override public void onCardDisappeared(View view, int position) {}
        });

        Log.d("TESTING","here1");

        layoutManager.setStackFrom(StackFrom.Top);
        layoutManager.setVisibleCount(3);
        layoutManager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        layoutManager.setDirections(Direction.HORIZONTAL);

        b.cardStackView.setLayoutManager(layoutManager);
        Log.d("TESTING","here2");


        ApiService apiService = ApiClient.getApiService();
        locationDTO dto = new locationDTO(lat, lng, sessionId); // NEW
        Log.d("DTO_SENT", new Gson().toJson(dto));
        Log.d("DEBUG_REQ", "lat=" + lat + " lng=" + lng + " sessionId=" + sessionId);
        Log.d("TESTING","here3");


        apiService.findEateries(dto).enqueue(new Callback<List<eateries>>() {

            @Override
            public void onResponse(Call<List<eateries>> call, Response<List<eateries>> response) {
                Log.d("TESTING","here4");

                Log.d("DEBUG_HTTP", "Response code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("TESTING","here5");


                    Log.d("DEBUG_RES THIS ONE", new Gson().toJson(response.body()));

                    List<eateries> eateriesList = response.body();

                    Log.d("DEBUG_RES", "eateriesList=" + eateriesList.size());

                    List<RestaurantCard> cards = new ArrayList<>();
                    viewModel.setTotalCards(eateriesList.size());

                    Log.d("TESTING","here6");

                    Log.d("DEBUG_REQ", "lat=" + lat + " lng=" + lng + " sessionId=" + sessionId);

                    Log.d("DEBUG_RES", "Eateries count: " + eateriesList.size());
                    if (eateriesList.isEmpty()) {
                        Log.w("DEBUG_RES", "No eateries returned from API — list is empty.");
                    }

                    for (eateries eatery : eateriesList) {
                        Log.d("TESTING", "here7");

                        double safeRating = eatery.getRating() != null ? eatery.getRating() : 0.0;
                        if (safeRating > 9.9) {
                            safeRating = 9.9;
                        }

                        Log.d("DEBUG_SAFE_RATING", "Using rating: " + safeRating);
                        Log.d("TESTING", "here8");

                        apiService.getEateryImages(eatery.getEateryId()).enqueue(new Callback<List<String>>() {
                            @Override
                            public void onResponse(Call<List<String>> call, Response<List<String>> imgResponse) {
                                Log.d("IMAGE_API", "response code = " + imgResponse.code());

                                if (imgResponse.isSuccessful() && imgResponse.body() != null && !imgResponse.body().isEmpty()) {
                                    Log.d("IMAGE_API", "Image list size: " + imgResponse.body().size());

                                    String base64 = imgResponse.body().get(0);  // use first image
                                    byte[] imageBytes = android.util.Base64.decode(base64, android.util.Base64.DEFAULT);
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                                    RestaurantCard card = new RestaurantCard(
                                            eatery.getDisplayName(),
                                            eatery.getCuisine(),
                                            eatery.getPriceLevel(),
                                            //Collections.singletonList(bitmap),
                                            bitmap,
                                            eatery.getEateryId()
                                    );
                                    cards.add(card);

                                    if (cards.size() == eateriesList.size()) {
                                        Log.d("IMAGE_API", "All cards prepared, attaching adapter.");

                                        Bundle result = new Bundle();
                                        result.putParcelableArrayList("restaurantCards", new ArrayList<>(cards));
                                        getParentFragmentManager().setFragmentResult("restaurantCardsResult", result);

                                        adapter = new CardStackAdapter(cards);
                                        b.cardStackView.setAdapter(adapter);
                                    }
                                } else {
                                    Log.d("IMAGE_API", "Image fetch failed or empty response");
                                }
                            }

                            @Override
                            public void onFailure(Call<List<String>> call, Throwable t) {
                                Log.e("IMAGE_API", "Failed to get images: " + t.getMessage());
                            }
                        });
//                        apiService.getEateryImages(eatery.getEateryId()).enqueue(new Callback<List<byte[]>>() {
//                            @Override
//                            public void onResponse(Call<List<byte[]>> call, Response<List<byte[]>> imgResponse) {
//                                Log.d("TESTING", "here9");
//
//                                Bitmap bitmap;
//
//                                if (imgResponse.isSuccessful() && imgResponse.body() != null && !imgResponse.body().isEmpty()) {
//                                    Log.d("TESTING", "Image fetch successful for eatery: " + eatery.getEateryId());
//
//                                    byte[] imageBytes = imgResponse.body().get(0);
//                                    bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//                                } else {
//                                    Log.d("TESTING", "Image response empty, using placeholder for: " + eatery.getEateryId());
//
//                                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lolacafe);
//                                }
//
//                                RestaurantCard card = new RestaurantCard(
//                                        eatery.getDisplayName(),
//                                        eatery.getPriceLevel(),
//                                        bitmap,
//                                        eatery.getEateryId()
//                                );
//                                cards.add(card);
//                                Log.d("DEBUG_CARD_ADDED", "Card added for: " + eatery.getDisplayName());
//
//                                if (cards.size() == eateriesList.size()) {
//                                    Log.d("TESTING", "here11 — All cards added, setting adapter.");
//                                    adapter = new CardStackAdapter(cards);
//                                    b.cardStackView.setAdapter(adapter);
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<List<byte[]>> call, Throwable t) {
//                                Log.d("TESTING", "here12 — Image fetch failed for: " + eatery.getEateryId());
//                                Log.e("ImageLoadError", "Failed to fetch image", t);
//
//                                Bitmap fallback = BitmapFactory.decodeResource(getResources(), R.drawable.lolacafe);
//                                RestaurantCard card = new RestaurantCard(
//                                        eatery.getDisplayName(),
//                                        eatery.getPriceLevel(),
//                                        fallback,
//                                        eatery.getEateryId()
//                                );
//                                cards.add(card);
//
//                                if (cards.size() == eateriesList.size()) {
//                                    Log.d("TESTING", "here11 — All cards added (with fallback), setting adapter.");
//                                    adapter = new CardStackAdapter(cards);
//                                    b.cardStackView.setAdapter(adapter);
//                                }
//                            }
//                        });


                    }
                } else {
                    Log.d("TESTING","here13");

                    Toast.makeText(getContext(), "No eateries found nearby", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<eateries>> call, Throwable t) {
                Log.d("TESTING","here14");

                Toast.makeText(getContext(), "API error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("eateryerror",t.getMessage());
            }
        });
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

    private void checkIfSwipingFinished(int sessionId) {
        if (viewModel.getSwipedCards() == viewModel.getTotalCards()) {
            viewModel.setSwipingFinished(true);

            Toast.makeText(getContext(), "Swiping complete. You can now finalize.", Toast.LENGTH_SHORT).show();

            // Step: Collect eateryIds
            ArrayList<String> eateryIds = new ArrayList<>();
            for (int i = 0; i < adapter.getItemCount(); i++) {
                eateryIds.add(adapter.getCardAt(i).getEateryId());
            }

            // Step: Store in arguments so ViewSessionFragment can access it later
            requireActivity().getSupportFragmentManager().setFragmentResult("eateryIdsResult", createEateryBundle(eateryIds));

        }
    }

    // Helper method to create the bundle
    private Bundle createEateryBundle(ArrayList<String> eateryIds) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("eateryIds", eateryIds);
        return bundle;
    }

    public static boolean hasFinishedSwiping() {
        return viewModel.isSwipingFinished();
    }

    public static class SwipingViewModel extends androidx.lifecycle.ViewModel {
        private boolean swipingFinished = false;
        private int swipedCards = 0;
        private int totalCards = 0;

        public boolean isSwipingFinished() { return swipingFinished; }
        public void setSwipingFinished(boolean finished) { this.swipingFinished = finished; }
        public int getSwipedCards() { return swipedCards; }
        public void incrementSwipedCards() { this.swipedCards++; }
        public void setTotalCards(int totalCards) { this.totalCards = totalCards; }
        public int getTotalCards() { return totalCards; }
        public void reset() {
            this.swipedCards = 0;
            this.swipingFinished = false;
        }
    }
}