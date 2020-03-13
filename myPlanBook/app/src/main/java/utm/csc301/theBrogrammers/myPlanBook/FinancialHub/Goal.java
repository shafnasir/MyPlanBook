package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

public class Goal {

    String goal_name, price, currently_at;

    public Goal(){

    }

    public Goal(String price, String goal_name){
        this.price = price;
        this.goal_name = goal_name;
    }

    public String getCurrently_at() {
        return currently_at;
    }

    public void setCurrently_at(String currently_at) {
        this.currently_at = currently_at;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String currently_at) {
        this.currently_at = currently_at;
    }

    public String getGoal_name() {
        return goal_name;
    }

    public void setGoal_name(String goal_name) {
        this.goal_name = goal_name;
    }
}
