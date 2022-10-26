package uz.ulugbek.aws.lambda;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
}
