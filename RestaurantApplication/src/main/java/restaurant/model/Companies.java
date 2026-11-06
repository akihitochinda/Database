package restaurant.model;

public class Companies {

    protected String companyName;
    protected String description;

    public Companies(String companyName, String description) {
        this.companyName = companyName;
        this.description = description;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(
            String companyName) {

        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description) {

        this.description = description;
    }
}