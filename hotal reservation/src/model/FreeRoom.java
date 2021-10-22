package model;

public class FreeRoom {
    private Integer price;

    public FreeRoom(){
        this.price=0;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return "This room price is: " + price;
   }

}
