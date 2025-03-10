package com.w3itexperts.ombe.modals;

public class AddressModal {
    private int addressTypeIcon;
    private String addressTypeLabel;
    private String fullAddress;
    private boolean isSelected;

    public AddressModal(int addressTypeIcon, String addressTypeLabel, String fullAddress, boolean isSelected) {
        this.addressTypeIcon = addressTypeIcon;
        this.addressTypeLabel = addressTypeLabel;
        this.fullAddress = fullAddress;
        this.isSelected = isSelected;
    }

    public int getAddressTypeIcon() {
        return addressTypeIcon;
    }

    public void setAddressTypeIcon(int addressTypeIcon) {
        this.addressTypeIcon = addressTypeIcon;
    }

    public String getAddressTypeLabel() {
        return addressTypeLabel;
    }

    public void setAddressTypeLabel(String addressTypeLabel) {
        this.addressTypeLabel = addressTypeLabel;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
