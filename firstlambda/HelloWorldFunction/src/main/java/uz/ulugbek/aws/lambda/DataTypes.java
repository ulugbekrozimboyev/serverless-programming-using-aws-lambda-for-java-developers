package uz.ulugbek.aws.lambda;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataTypes {

    public int getNumber(float number) {
        return (int) number;
    }

    public List<Integer> getScores(List<String> names) {
        HashMap<String, Integer> studentScores = new HashMap<>();
        studentScores.put("Ali", 90);
        studentScores.put("Vali", 80);
        studentScores.put("Bali", 90);
        studentScores.put("Jali", 75);
        studentScores.put("G'ali", 100);

        List<Integer> matchingScores = new LinkedList<>();
        names.forEach(name -> {
            matchingScores.add(studentScores.get(name));
        });

        return matchingScores;
    }

    public void saveEmployeeData(Map<String, Integer> map) {
        System.out.println(map);
    }

    public Map<String, List<Integer>> getStudentsScores() {
        Map<String, List<Integer>> studentScores = new HashMap<>();
        studentScores.put("Ali", List.of(90, 80, 70));
        studentScores.put("Vali", List.of(60, 80, 80));
        studentScores.put("Bali", List.of(50, 85, 75));
        studentScores.put("Jali", List.of(75, 80, 70));
        studentScores.put("G'ali", List.of(50, 30, 40));

        return studentScores;
    }

    public ClinicalData getClinicals(Patient patient) {

        System.out.println(patient);

        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setBp("80/120");
        clinicalData.setHeartRate("80");

        return clinicalData;
    }
}
