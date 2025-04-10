package com.w3itexperts.ombe.methods;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.adapter.yourGroupsAdapter;
import com.w3itexperts.ombe.modals.AddressModal;
import com.w3itexperts.ombe.modals.CardModal;
import com.w3itexperts.ombe.modals.CartModal;
import com.w3itexperts.ombe.modals.CategoriesModal;
import com.w3itexperts.ombe.modals.CoffeeItem;
import com.w3itexperts.ombe.modals.FeaturedModal;
import com.w3itexperts.ombe.modals.MostOrderedModal;
import com.w3itexperts.ombe.modals.MyOrderModal;
import com.w3itexperts.ombe.modals.OurStoresModal;
import com.w3itexperts.ombe.modals.rewardModal;
import com.w3itexperts.ombe.modals.wishlistModal;
import com.w3itexperts.ombe.modals.yourGroupsModal;
import com.w3itexperts.ombe.modals.yourSessionsModal;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    public static List<CategoriesModal> generateCategoriesList() {
        List<CategoriesModal> categoriesList = new ArrayList<>();

        categoriesList.add(createCategory(R.drawable.tea, "Beverages", "20 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Foods", "41 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Snacks", "15 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Desserts", "25 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Salads", "30 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Soups", "18 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Grains", "12 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Meats", "22 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Pasta", "19 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Seafood", "16 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Vegetarian", "21 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Vegan", "17 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Breakfast", "28 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Lunch", "35 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Dinner", "24 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Appetizers", "26 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Main Course", "40 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Side Dishes", "13 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Breads", "14 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Beverages", "20 Menus"));
        categoriesList.add(createCategory(R.drawable.coffeeicon1, "Soups & Salads", "27 Menus"));

        return categoriesList;
    }

    private static CategoriesModal createCategory(int imageRes, String coffeeName, String menusName) {
        CategoriesModal category = new CategoriesModal();
        category.setCoffeeImage(imageRes);
        category.setCoffeeName(coffeeName);
        category.setMenusName(menusName);
        return category;
    }

    public static List<CoffeeItem> coffeeItems() {
        List<CoffeeItem> coffeeItems = new ArrayList<>();
        coffeeItems.add(new CoffeeItem("Creamy Ice Coffee", "$5.8", R.drawable.ombecap1));
        return coffeeItems;
    }

    public static List<FeaturedModal> generateFeaturedList() {
        List<FeaturedModal> featuredList = new ArrayList<>();

        featuredList.add(createFeaturedItem(R.drawable.coffeeimage1, "Creamy Mocha", "4.5", "$12.6", "50 Pts"));
        featuredList.add(createFeaturedItem(R.drawable.coffeeimage1, "Vanilla Latte", "4.7", "$13.5", "60 Pts"));
        featuredList.add(createFeaturedItem(R.drawable.coffeeimage1, "Cappuccino", "4.3", "$11.0", "45 Pts"));
        featuredList.add(createFeaturedItem(R.drawable.coffeeimage1, "Creamy Mocha", "4.5", "$12.6", "50 Pts"));
        featuredList.add(createFeaturedItem(R.drawable.coffeeimage1, "Vanilla Latte", "4.7", "$13.5", "60 Pts"));
        featuredList.add(createFeaturedItem(R.drawable.coffeeimage1, "Cappuccino", "4.3", "$11.0", "45 Pts"));
        featuredList.add(createFeaturedItem(R.drawable.coffeeimage1, "Creamy Mocha", "4.5", "$12.6", "50 Pts"));
        featuredList.add(createFeaturedItem(R.drawable.coffeeimage1, "Vanilla Latte", "4.7", "$13.5", "60 Pts"));
        featuredList.add(createFeaturedItem(R.drawable.coffeeimage1, "Cappuccino", "4.3", "$11.0", "45 Pts"));

        return featuredList;
    }

    private static FeaturedModal createFeaturedItem(int imageRes, String coffeeName, String rating, String price, String pts) {
        FeaturedModal featuredItem = new FeaturedModal();
        featuredItem.setCoffeeImage(imageRes);
        featuredItem.setCoffeeName(coffeeName);
        featuredItem.setCoffeeRating(rating);
        featuredItem.setCoffeePrice(price);
        featuredItem.setCoffeePts(pts);
        return featuredItem;
    }


    private static FeaturedModal createItemViewList(int imageRes, String coffeeName, String rating, String price, String tag) {
        FeaturedModal featuredItem = new FeaturedModal();
        featuredItem.setCoffeeImage(imageRes);
        featuredItem.setCoffeeName(coffeeName);
        featuredItem.setCoffeeRating(rating);
        featuredItem.setCoffeePrice(price);
        featuredItem.setCoffeeTag(tag);
        return featuredItem;
    }

    public static List<FeaturedModal> ItemViewList() {
        List<FeaturedModal> featuredList = new ArrayList<>();

        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Creamy Mocha", "4.5", "$12.6", "Tea, Lemon"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Espresso Delight", "4.7", "$10.0", "Coffee, Espresso"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Caramel Latte", "4.6", "$14.2", "Caramel, Milk"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Iced Americano", "4.4", "$11.5", "Iced Coffee, Espresso"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Vanilla Frappe", "4.8", "$13.8", "Vanilla, Milk"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Hazelnut Cappuccino", "4.5", "$12.0", "Hazelnut, Milk"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Choco Mocha", "4.9", "$15.5", "Chocolate, Coffee"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Pumpkin Spice Latte", "4.3", "$13.0", "Pumpkin, Spices"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Cinnamon Roll Latte", "4.7", "$14.6", "Cinnamon, Milk"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Mint Chocolate Chip Frappe", "4.6", "$13.9", "Mint, Chocolate"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Irish Coffee", "4.8", "$16.0", "Whiskey, Coffee"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Toffee Nut Latte", "4.5", "$12.4", "Toffee, Nut"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Maple Latte", "4.6", "$14.3", "Maple, Coffee"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Peanut Butter Mocha", "4.7", "$15.2", "Peanut Butter, Chocolate"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Coconut Macchiato", "4.4", "$12.7", "Coconut, Coffee"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Mocha Mint", "4.5", "$13.5", "Mint, Chocolate"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "White Chocolate Mocha", "4.9", "$15.7", "White Chocolate, Coffee"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Almond Joy Latte", "4.7", "$14.8", "Almond, Coconut"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Peppermint Mocha", "4.6", "$13.6", "Peppermint, Chocolate"));
        featuredList.add(createItemViewList(R.drawable.coffeeimage1, "Gingerbread Latte", "4.8", "$14.5", "Gingerbread, Spices"));
        return featuredList;
    }

    public static List<OurStoresModal> OurStoresItems() {

        List<OurStoresModal> storesList = new ArrayList<>();

        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Centre Point Plaza", "09:00 AM - 10:00 PM", "3.7 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Park Avenue Store", "08:00 AM - 09:00 PM", "2.3 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Downtown Mall", "10:00 AM - 11:00 PM", "5.1 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Sunset Boulevard", "07:00 AM - 08:00 PM", "1.8 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Maple Leaf Plaza", "06:00 AM - 07:00 PM", "4.4 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "City Square", "09:00 AM - 10:00 PM", "3.2 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Highland Park", "11:00 AM - 09:00 PM", "6.7 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Greenwood Avenue", "08:00 AM - 10:00 PM", "2.1 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Oceanview Mall", "09:00 AM - 11:00 PM", "7.3 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Broadway Street", "10:00 AM - 09:00 PM", "4.9 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Silverstone Plaza", "07:00 AM - 08:00 PM", "3.0 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Willow Creek", "08:00 AM - 09:00 PM", "5.2 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Oakridge Mall", "09:00 AM - 10:00 PM", "6.0 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Riverside Mall", "07:00 AM - 08:00 PM", "3.8 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Eastside Plaza", "10:00 AM - 09:00 PM", "2.5 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Westside Market", "11:00 AM - 07:00 PM", "4.7 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Northern Lights", "08:00 AM - 10:00 PM", "5.4 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Southgate Mall", "09:00 AM - 09:00 PM", "7.1 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Hillside Plaza", "06:00 AM - 07:00 PM", "3.6 km"));
        storesList.add(createStoreItem(R.drawable.coffeeimage1, "Lakeshore Market", "09:00 AM - 10:00 PM", "4.2 km"));

        return storesList;
    }

    private static OurStoresModal createStoreItem(int imageRes, String storeName, String storeTiming, String storeDistance) {
        OurStoresModal item = new OurStoresModal();
        item.setCoffeeImage(imageRes);
        item.setStoreName(storeName);
        item.setStoreTiming(storeTiming);
        item.setStoreDistance(storeDistance);
        return item;
    }

    public static List<rewardModal> rewardItems() {

        List<rewardModal> rewardsList = new ArrayList<>();

        rewardsList.add(createRewardItem("Extra Deluxe Gayo Coffee Packages", "June 18, 2020 | 4:00 AM", "+250"));
        rewardsList.add(createRewardItem("Superb Arabica Coffee Beans", "July 22, 2020 | 3:30 PM", "+150"));
        rewardsList.add(createRewardItem("Premium Mocha Coffee Blend", "August 10, 2020 | 1:15 PM", "+200"));
        rewardsList.add(createRewardItem("Exclusive Coffee Gift Set", "September 12, 2020 | 11:00 AM", "+300"));
        rewardsList.add(createRewardItem("Classic Espresso Collection", "October 05, 2020 | 9:45 AM", "+100"));
        rewardsList.add(createRewardItem("Special Reserve Coffee Pack", "November 19, 2020 | 2:25 PM", "+180"));
        rewardsList.add(createRewardItem("Organic Fair Trade Coffee", "December 08, 2020 | 10:00 AM", "+220"));
        rewardsList.add(createRewardItem("Artisan Roasted Coffee Set", "January 15, 2021 | 5:00 PM", "+170"));
        rewardsList.add(createRewardItem("World's Finest Coffee Box", "February 20, 2021 | 8:30 AM", "+140"));
        rewardsList.add(createRewardItem("Luxury Coffee Experience", "March 14, 2021 | 4:45 PM", "+260"));
        rewardsList.add(createRewardItem("Exclusive Coffee Workshop", "April 23, 2021 | 7:00 PM", "+120"));
        rewardsList.add(createRewardItem("Elite Coffee Subscription", "May 30, 2021 | 9:00 AM", "+230"));
        rewardsList.add(createRewardItem("Handpicked Coffee Variety", "June 09, 2021 | 12:15 PM", "+210"));
        rewardsList.add(createRewardItem("Specialty Coffee Box", "July 21, 2021 | 10:30 AM", "+250"));
        rewardsList.add(createRewardItem("Gourmet Coffee Collection", "August 29, 2021 | 6:45 AM", "+240"));
        rewardsList.add(createRewardItem("Premium Coffee Sampler", "September 17, 2021 | 3:15 PM", "+130"));
        rewardsList.add(createRewardItem("Rare Coffee Experience", "October 25, 2021 | 1:30 PM", "+280"));
        rewardsList.add(createRewardItem("Exotic Coffee Blend", "November 11, 2021 | 2:00 PM", "+160"));
        rewardsList.add(createRewardItem("Coffee Lovers Gift Set", "December 22, 2021 | 5:30 PM", "+190"));
        rewardsList.add(createRewardItem("Ultimate Coffee Collection", "January 05, 2022 | 8:00 AM", "+300"));

        return rewardsList;
    }

    private static rewardModal createRewardItem(String title, String dateTime, String points) {
        rewardModal reward = new rewardModal();
        reward.setRewardTitle(title);
        reward.setRewardDateTime(dateTime);
        reward.setRewardPoints(points);
        return reward;
    }

    //public static List<yourGroupsModal> AllGroupsList() {
        //List<yourGroupsModal> GroupList = new ArrayList<>();
        //GroupList.add(new yourGroupsModal("12","15",R.drawable.tempgroupimg, "The MOFOs"));
        //GroupList.add(new yourGroupsModal("13","14",R.drawable.tempgroupimg, "The DOFOs"));
        //GroupList.add(new yourGroupsModal("13","14",R.drawable.tempgroupimg, "Toni Fandom"));
        //GroupList.add(new yourGroupsModal("12","15",R.drawable.tempgroupimg, "Erika Fandom"));
        //GroupList.add(new yourGroupsModal("12","15",R.drawable.tempgroupimg, "QX Fandom"));
        //GroupList.add(new yourGroupsModal("13","14",R.drawable.tempgroupimg, "JY Fandom"));
        //GroupList.add(new yourGroupsModal("12","15",R.drawable.tempgroupimg, "JZ Fandom"));
        //GroupList.add(new yourGroupsModal("12","15",R.drawable.tempgroupimg, "Gynn Fandom"));
        //GroupList.add(new yourGroupsModal("12","15",R.drawable.tempgroupimg, "Shonim Fandom"));





        //return GroupList;
    //}

    public static List<yourSessionsModal> AllSessionsList() {
        List<yourSessionsModal> SessionList = new ArrayList<>();
        SessionList.add(new yourSessionsModal("Lola's Cafe","19 February 25, 3pm, Tampines", "The MOFOs","Ongoing","lunchu lunchee lunchyy"));
        SessionList.add(new yourSessionsModal("Lola's Cafe","19 February 25, 3pm, Tampines", "The MOFOs","Ongoing","lunchu lunchee lunchyy"));
        SessionList.add(new yourSessionsModal("Lola's Cafe","19 February 25, 3pm, Tampines", "The MOFOs","Ongoing","lunchu lunchee lunchyy"));
        SessionList.add(new yourSessionsModal("Lola's Cafe","19 February 25, 3pm, Tampines", "The MOFOs","Ongoing","lunchu lunchee lunchyy"));
        SessionList.add(new yourSessionsModal("Lola's Cafe","19 February 25, 3pm, Tampines", "The MOFOs","Ongoing","lunchu lunchee lunchyy"));
        SessionList.add(new yourSessionsModal("Lola's Cafe","19 February 25, 3pm, Tampines", "The MOFOs","Ongoing","lunchu lunchee lunchyy"));
        SessionList.add(new yourSessionsModal("Lola's Cafe","19 February 25, 3pm, Tampines", "The MOFOs","Ongoing","lunchu lunchee lunchyy"));
        SessionList.add(new yourSessionsModal("Lola's Cafe","19 February 25, 3pm, Tampines", "The MOFOs","Ongoing","lunchu lunchee lunchyy"));
        SessionList.add(new yourSessionsModal("Lola's Cafe","19 February 25, 3pm, Tampines", "The MOFOs","Ongoing","lunchu lunchee lunchyy"));
        SessionList.add(new yourSessionsModal("Lola's Cafe","19 February 25, 3pm, Tampines", "The MOFOs","Ongoing","lunchu lunchee lunchyy"));

        return SessionList;
    }

    public static List<MostOrderedModal> generateMostOrderedList() {

        ArrayList<MostOrderedModal> mostOrderedList = new ArrayList<>();
        mostOrderedList.add(createMostOrderedItem(R.drawable.coffeecup, "Creamy Latte Coffee", "Beverages"));
        mostOrderedList.add(createMostOrderedItem(R.drawable.coffeecup, "Espresso", "Beverages"));
        mostOrderedList.add(createMostOrderedItem(R.drawable.coffeecup, "Cappuccino", "Beverages"));
        mostOrderedList.add(createMostOrderedItem(R.drawable.coffeecup, "Mocha", "Beverages"));
        mostOrderedList.add(createMostOrderedItem(R.drawable.coffeecup, "Americano", "Beverages"));
        mostOrderedList.add(createMostOrderedItem(R.drawable.coffeecup, "Flat White", "Beverages"));
        mostOrderedList.add(createMostOrderedItem(R.drawable.coffeecup, "Macchiato", "Beverages"));
        mostOrderedList.add(createMostOrderedItem(R.drawable.coffeecup, "Irish Coffee", "Beverages"));
        mostOrderedList.add(createMostOrderedItem(R.drawable.coffeecup, "Iced Coffee", "Beverages"));
        mostOrderedList.add(createMostOrderedItem(R.drawable.coffeecup, "Affogato", "Beverages"));


        return mostOrderedList;
    }


    private static MostOrderedModal createMostOrderedItem(int coffeeimage1, String creamyLatteCoffee, String beverages) {
        MostOrderedModal modal = new MostOrderedModal();
        modal.setCoffeeImage(coffeeimage1);
        modal.setCoffeeName(creamyLatteCoffee);
        modal.setCoffeeTag(beverages);
        return modal;
    }

    public static List<wishlistModal> createWishlistItems() {
        List<wishlistModal> wishlistItems = new ArrayList<>();

        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage1, "Creamy Mocha", "Tea, Lemon", "$12.6", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage2, "Vanilla Latte", "Milk, Coffee", "$10.2", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage1, "Espresso", "Strong, Black", "$8.4", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage1, "Caramel Macchiato", "Sweet, Milk", "$11.9", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage1, "Hazelnut Cappuccino", "Nutty, Milk", "$13.7", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage1, "Pumpkin Spice Latte", "Spicy, Cream", "$14.1", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage1, "Iced Americano", "Cold, Black", "$7.5", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage1, "Mocha Frappuccino", "Chocolate, Cold", "$9.8", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage1, "Turkish Coffee", "Strong, Bitter", "$6.9", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage1, "Irish Coffee", "Whiskey, Cream", "$15.3", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage1, "Affogato", "Espresso, Ice Cream", "$13.5", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage1, "Flat White", "Smooth, Creamy", "$10.0", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage1, "Café au Lait", "Mild, Milk", "$9.2", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage1, "Cold Brew", "Strong, Cold", "$8.7", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage1, "Ristretto", "Short, Bold", "$7.8", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage1, "Cortado", "Balanced, Espresso", "$8.5", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage1, "Doppio", "Double Shot, Bold", "$9.3", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage1, "Red Eye", "Coffee, Espresso", "$10.4", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage1, "Vienna Coffee", "Whipped Cream, Strong", "$12.0", true));
        wishlistItems.add(createWishlistItem(R.drawable.coffeeimage2, "Macchiato", "Espresso, Foam", "$11.2", true));

        return wishlistItems;
    }

    private static wishlistModal createWishlistItem(int coffeeImage, String coffeeName, String coffeeTag, String coffeePrice, boolean isInWishlist) {
        wishlistModal item = new wishlistModal();
        item.setCoffeeImage(coffeeImage);
        item.setCoffeeName(coffeeName);
        item.setCoffeeTag(coffeeTag);
        item.setCoffeePrice(coffeePrice);
        item.setInWishlist(isInWishlist);
        return item;
    }

    public static List<CartModal> generateCartItems() {

        List<CartModal> cartItems = new ArrayList<>();

        cartItems.add(new CartModal(R.drawable.coffeeimage1, "Creamy Mocha", "$12.6", 1));
        cartItems.add(new CartModal(R.drawable.coffeeimage2, "Espresso Delight", "$15.0", 2));
        cartItems.add(new CartModal(R.drawable.coffeeimage1, "Latte Art", "$10.0", 1));
        cartItems.add(new CartModal(R.drawable.coffeeimage1, "Caramel Macchiato", "$14.5", 3));
        cartItems.add(new CartModal(R.drawable.coffeeimage1, "Vanilla Bean Frappe", "$13.8", 2));

        return cartItems;
    }

    public static List<AddressModal> addressList() {


        ArrayList<AddressModal> addressList = new ArrayList<>();
        addressList.add(new AddressModal(R.drawable.ic_home, "Home Address", "123 Main Street, Anytown, USA 12345", false));
        addressList.add(new AddressModal(R.drawable.location, "Work Address", "456 Office Plaza, Business City, USA 54321", false));
        addressList.add(new AddressModal(R.drawable.our_stores, "Shop Address", "789 Apartment Complex, Big City, USA 67890", false));
        return addressList;
    }


    public static List<CardModal> cardList() {
        List<CardModal> cardList;  cardList = new ArrayList<>();
        // Populate the card list with data
        cardList.add(createCard("Credit Card", "**** **** **** 4532", "CardHolderName", "10/28", "123", false));
        cardList.add(createCard("Debit Card", "**** **** **** 5678", "HolderName", "11/29", "456", false));
        // Add more cards as needed
        return cardList;
    }
    private static CardModal createCard(String type, String number, String holder, String expDate, String cvv, boolean isSelected) {
        CardModal card = new CardModal();
        card.setCardTypeName(type);
        card.setCardNumber(number);
        card.setCardHolderName(holder);
        card.setExpDate(expDate);
        card.setCvv(cvv);
        card.setSelected(isSelected);
        return card;
    }

    public static List<MyOrderModal> myOrderList() {
        List<MyOrderModal> myOrderList = new ArrayList<>();
        myOrderList.add(createOrder("Creamy Mocha", "Tea, Lemon", "$12.6", "4.5", R.drawable.coffeeimage1, "Ongoing"));
        myOrderList.add(createOrder("Espresso", "Rich, Strong", "$9.5", "4.8", R.drawable.coffeeimage2, "Completed"));
        return myOrderList;
    }
    private static MyOrderModal createOrder(String name, String tag, String price, String rating, int image, String status) {
        MyOrderModal order = new MyOrderModal();
        order.setCoffeeName(name);
        order.setCoffeeTag(tag);
        order.setCoffeePrice(price);
        order.setCoffeeRating(rating);
        order.setCoffeeImage(image);
        order.setOrderStatus(status);
        return order;
    }
}
