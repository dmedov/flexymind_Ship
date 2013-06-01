package com.example.ship.game.highscores;

import android.content.Context;
import android.util.Log;
import com.example.ship.commons.A;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Vasya
 * Date: 26.05.13
 * Time: 21:56
 * To change this template use File | Settings | File Templates.
 */
public class HighScoresManager {

    private static final String FILE_NAME = "highscores.txt";
    private static final int MAX_SIZE = 10;

    private ArrayList<ScoreRecord> highScores;
    private String lastPlayerName = "";

    public HighScoresManager() {

        highScores = new ArrayList<ScoreRecord>();
        parseFile();
        sortHighScores();
    }

    public ArrayList<ScoreRecord> getHighScores() {
        return highScores;
    }

    public void addScore(ScoreRecord scoreRecord) {
        if (testScore(scoreRecord.getScore())) {
            highScores.add(scoreRecord);
            sortHighScores();
            if (highScores.size() > MAX_SIZE) {
                highScores.remove(highScores.size() - 1);
            }
            lastPlayerName = scoreRecord.getPlayerName();
            saveFile();
        }
    }

    public boolean testScore(int score) {
        return (highScores.size() < MAX_SIZE || highScores.get(highScores.size() - 1).getScore() < score)
               && score > 0;
    }

    public void clearScores() {
        highScores.clear();
        saveFile();
    }

    public String getLastPlayerName() {
        return lastPlayerName;
    }

    private void sortHighScores() {
        Collections.sort(highScores, new Comparator<ScoreRecord>() {
            @Override
            public int compare(ScoreRecord lhs, ScoreRecord rhs) {
                return rhs.getScore() - lhs.getScore();
            }
        });
    }

    private void parseFile() {
        try {
            FileInputStream fis = A.a.openFileInput(FILE_NAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String line;
            int separatorPosition;
            int score;
            String name;
            while ((line = br.readLine()) != null) {

                Log.d("1log", "строка:" + line);

                if (line.startsWith("lp=")) {
                    lastPlayerName = line.substring(3);
                } else {

                    separatorPosition = line.indexOf('\t');
                    if (separatorPosition > -1) {
                        name = line.substring(separatorPosition + 1);
                        try {
                            score = Integer.parseInt(line.substring(0, separatorPosition));
                        } catch (NumberFormatException e) {
                            continue;
                        }
                        highScores.add(new ScoreRecord(name, score));
                    }
                }
            }

            fis.close();

        } catch (FileNotFoundException e) {
            Log.d("1log", "Файл рекордов отсутствует (чтение)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFile() {

        sortHighScores();
        StringBuilder text = new StringBuilder();
        text.append("");
        for (ScoreRecord record: highScores) {
            text.append(record).append("\r\n");
        }
        text.append("lp=").append(lastPlayerName).append("\r\n");

        try {
            FileOutputStream fos = A.a.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(text.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("1log", "Файл рекордов отсутствует (запись)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
