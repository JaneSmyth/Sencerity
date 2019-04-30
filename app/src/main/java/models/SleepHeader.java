package models;
//recycler view header item

public class SleepHeader extends RecyclerViewItem{
    private String HeaderText;

    public SleepHeader(String headerText) {
        HeaderText = headerText;
    }

    public String getHeaderText() {
        return HeaderText;
    }

    public void setHeaderText(String headerText) {
        HeaderText = headerText;
    }
}
