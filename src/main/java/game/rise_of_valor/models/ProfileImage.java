package game.rise_of_valor.models;

public class ProfileImage {
    String imageLink;
    boolean isPurchased;
    boolean isSet;
    int price;

    public ProfileImage(String imageLink, boolean isPurchased, boolean isSet, int price) {
        this.imageLink = imageLink;
        this.isPurchased = isPurchased;
        this.isSet = isSet;
        this.price = price;
    }

    public String getImageLink() {
        return imageLink;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public boolean isSet() {
        return isSet;
    }
    public int getPrice() {
        return price;
    }
    public void setSet(boolean set) {
        isSet = set;
    }
    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }
}
