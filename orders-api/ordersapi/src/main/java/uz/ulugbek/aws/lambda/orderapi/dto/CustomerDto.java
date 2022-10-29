package uz.ulugbek.aws.lambda.orderapi.dto;

public class CustomerDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private Double rewardPoints;

    public CustomerDto() {
    }

    public CustomerDto(Integer id, String firstName, String lastName, Double rewardPoints) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rewardPoints = rewardPoints;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(Double rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", rewardPoints=" + rewardPoints +
                '}';
    }
}
