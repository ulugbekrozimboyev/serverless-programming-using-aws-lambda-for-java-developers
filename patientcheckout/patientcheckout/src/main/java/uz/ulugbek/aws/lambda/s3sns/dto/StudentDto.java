package uz.ulugbek.aws.lambda.s3sns.dto;

public class StudentDto {

    private Integer rollNo;
    private String name;
    private Integer testScore;
    private String grade;

    public StudentDto() {
    }

    public StudentDto(Integer rollNo, String name, Integer testScore) {
        this.rollNo = rollNo;
        this.name = name;
        this.testScore = testScore;
    }

    public StudentDto(Integer rollNo, String name, Integer testScore, String grade) {
        this.rollNo = rollNo;
        this.name = name;
        this.testScore = testScore;
        this.grade = grade;
    }

    public Integer getRollNo() {
        return rollNo;
    }

    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTestScore() {
        return testScore;
    }

    public void setTestScore(Integer testScore) {
        this.testScore = testScore;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "StudentDto{" +
                "rollNo=" + rollNo +
                ", name='" + name + '\'' +
                ", testScore=" + testScore +
                ", grade=" + grade +
                '}';
    }
}
