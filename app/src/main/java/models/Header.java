package models;
//recycler view header item

public class Header extends RecyclerViewItem{
    private String HeaderText;

    public Header(String headerText) {
        HeaderText = headerText;
    }

    public String getHeaderText() {
        return HeaderText;
    }

    public void setHeaderText(String headerText) {
        HeaderText = headerText;
    }
}
