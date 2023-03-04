package persistence;

import model.BloodSugarReading;
import model.LogBook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Code here is modelled after example JsonReader Class in sample code for CPSC 210 at:
 * https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads logbook from file and returns it;
    // throws IOException if an error occurs reading data from file
    public LogBook read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLogBook(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses logbook from JSON object and returns it
    private LogBook parseLogBook(JSONObject jsonObject) {
        LogBook logBook = new LogBook();
        addReadings(logBook, jsonObject);
        return logBook;
    }

    // MODIFIES: logBook
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addReadings(LogBook logBook, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("reading");
        for (Object json : jsonArray) {
            JSONObject nextReading = (JSONObject) json;
            addReading(logBook, nextReading);
        }
    }

    // MODIFIES: logBook
    // EFFECTS: parses blood sugar reading from JSON object and adds it to  logbook
    private void addReading(LogBook logBook, JSONObject jsonObject) {
        double value = jsonObject.getDouble("value");
        String date = jsonObject.getString("date");
        String time = jsonObject.getString("time");
        String category = jsonObject.getString("category");
        String notes = jsonObject.getString("notes");
        BloodSugarReading bsr = new BloodSugarReading(value, date, time, category);
        bsr.setNotes(notes);
        logBook.addReading(bsr);
    }
}
